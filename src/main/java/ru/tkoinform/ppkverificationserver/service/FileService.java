package ru.tkoinform.ppkverificationserver.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.tkoinform.ppkverificationserver.model.FileInfo;
import ru.tkoinform.ppkverificationserver.repository.FileRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static ru.tkoinform.ppkverificationserver.controller.FileController.FILE_CONTROLLER_PATH;

@Slf4j
@Service
public class FileService implements InitializingBean {

    @Autowired
    private Environment environment;

    @Autowired
    private FileRepository fileRepository;

    private File localStorage;

    public File getLocalStorage() {
        return localStorage;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String path = environment.getProperty("files-path");
        if (StringUtils.isNotEmpty(path)) {
            localStorage = new File(path);
            if (!localStorage.exists()) {
                localStorage.mkdirs();
            }
        } else {
            log.warn("Default file storage path used, provide file storage path!");
            localStorage = SystemUtils.getJavaIoTmpDir();
        }
    }

    @Transactional
    public FileInfo uploadFile(final MultipartFile file, final Double latitude, final Double longitude,
                               final Date creationDate) throws IOException {
        final FileInfo fileInfo = new FileInfo();
        fileInfo.setId(UUID.randomUUID());
        fileInfo.setSize(file.getSize());
        if (creationDate != null) {
            fileInfo.setCreated(creationDate);
        } else {
            fileInfo.setCreated(new Date());
        }
        fileInfo.setFileName(file.getOriginalFilename());
        fileInfo.setFileType(file.getContentType());
        fileInfo.setLongitude(longitude);
        fileInfo.setLatitude(latitude);
        /*
        fileInfo.setFileDownloadUri(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(FILE_CONTROLLER_PATH + "/")
                .path(fileInfo.getId().toString())
                .toUriString());
        */
        fileRepository.save(fileInfo);

        if (!localStorage.exists()) {
            localStorage.mkdirs();
        }

        File targetFile = new File(localStorage, String.format("%s", fileInfo.getId()));

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(targetFile);
            IOUtils.copyLarge(file.getInputStream(), fileOutputStream);
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
        }

        return fileRepository.save(fileInfo);
    }

    @Transactional
    public void deleteFile(UUID id)  {
        FileInfo fileInfo = fileRepository.getOne(id);
        fileInfo.setFinishTime(new Date());
        fileRepository.save(fileInfo);
    }

    @Transactional(readOnly = true)
    public FileInfo getFileInfo(final UUID id) {
        FileInfo fileInfo = fileRepository.getOne(id);
        if (fileInfo.getFinishTime() != null) {
            throw new IllegalStateException("Deleted file");
        } else {
            return fileInfo;
        }
    }
}
