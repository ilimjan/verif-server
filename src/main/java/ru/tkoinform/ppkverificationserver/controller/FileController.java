package ru.tkoinform.ppkverificationserver.controller;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.tkoinform.ppkverificationserver.CrossOrigins;
import ru.tkoinform.ppkverificationserver.dto.FileInfoDto;
import ru.tkoinform.ppkverificationserver.mapping.FileMapper;
import ru.tkoinform.ppkverificationserver.model.FileInfo;
import ru.tkoinform.ppkverificationserver.service.FileService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(FileController.FILE_CONTROLLER_PATH)
@CrossOrigin(origins = CrossOrigins.value)
public class FileController {

    public static final String FILE_CONTROLLER_PATH = "/api/file";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FileService fileService;

    @Autowired
    private FileMapper fileMapper;

    @PostMapping(value = "/upload")
    public ResponseEntity<FileInfoDto> uploadFile(@AuthenticationPrincipal Jwt jwt,
                                                  @RequestParam final MultipartFile file,
                                                  @RequestParam(required = false) final Double latitude,
                                                  @RequestParam(required = false) final Double longitude,
                                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final Date creationDate) throws IOException {
        FileInfo fileInfo = fileService.uploadFile(file, latitude, longitude, creationDate);

        FileInfoDto dto = fileMapper.map(fileInfo);

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/uploadMultiple")
    public ResponseEntity<List<FileInfoDto>> uploadMultipleFiles(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam("files") MultipartFile[] files,
            @RequestParam(required = false) final Double latitude,
            @RequestParam(required = false) final Double longitude,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final Date creationDate) throws IOException {

        List<FileInfoDto> resultList = new ArrayList<>();
        for (MultipartFile file : files) {
            resultList.add(fileMapper.map(fileService.uploadFile(file, latitude, longitude, creationDate)));
        }
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/{fileId}")
    public void downloadFile(@AuthenticationPrincipal Jwt jwt,
                             @PathVariable final UUID fileId,
                             final HttpServletResponse response) throws IOException {
        FileInfo fileInfo = fileService.getFileInfo(fileId);
        if (Objects.isNull(fileInfo)) {
            throw new IllegalArgumentException("fileId");
        }

        response.setContentType(fileInfo.getFileType());
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileInfo.getFileName() + "\"");


        File storage = fileService.getLocalStorage();
        if (!storage.exists()) {
            storage.mkdirs();
        }
        File targetFile = new File(storage, String.format("%s", fileInfo.getId()));
        if (!targetFile.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(targetFile);
            FileCopyUtils.copy(fileInputStream, response.getOutputStream());
        } finally {
            IOUtils.closeQuietly(fileInputStream);
        }
    }

    @GetMapping("/image/{fileId}")
    public void loadImage(@PathVariable UUID fileId,
                             HttpServletResponse response) throws IOException {

        FileInfo fileInfo = fileService.getFileInfo(fileId);
        if (Objects.isNull(fileInfo)) {
            throw new IllegalArgumentException("fileId");
        }

        //ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        File storage = fileService.getLocalStorage();
        if (!storage.exists()) {
            storage.mkdirs();
        }
        File targetFile = new File(storage, String.format("%s", fileInfo.getId()));
        if (!targetFile.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        FileInputStream fileInputStream = null;
        fileInputStream = new FileInputStream(targetFile);

        response.setContentType(fileInfo.getFileType());
        response.setHeader("Content-Transfer-Encoding", "binary");

        try {
            fileInputStream = new FileInputStream(targetFile);
            FileCopyUtils.copy(fileInputStream, response.getOutputStream());
        } finally {
            IOUtils.closeQuietly(fileInputStream);
        }

        /*
        ByteArrayInputStream in = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        BufferedImage bufferedImage = ImageIO.read(in);

        int orientation = FileUtils.getImageOrientation(targetFile);

        AffineTransform t = new AffineTransform();
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        switch (orientation) {
            case 1:
                // no correction necessary skip and return the image
                break;
            case 2: // Flip X
                t.scale(-1.0, 1.0);
                t.translate(-width, 0);
                bufferedImage = transform(bufferedImage, t, false);
                break;
            case 3: // PI rotation
                t.translate(width, height);
                t.rotate(Math.PI);
                bufferedImage = transform(bufferedImage, t, false);
                break;
            case 4: // Flip Y
                t.scale(1.0, -1.0);
                t.translate(0, -height);
                bufferedImage = transform(bufferedImage, t, false);
                break;
            case 5: // - PI/2 and Flip X
                t.rotate(-Math.PI / 2);
                t.scale(-1.0, 1.0);
                bufferedImage = transform(bufferedImage, t, true);
                break;
            case 6: // -PI/2 and -width
                t.translate(height, 0);
                t.rotate(Math.PI / 2);
                bufferedImage = transform(bufferedImage, t, true);
                break;
            case 7: // PI/2 and Flip
                t.scale(-1.0, 1.0);
                t.translate(height, 0);
                t.translate(0, width);
                t.rotate(  3 * Math.PI / 2);
                bufferedImage = transform(bufferedImage, t, true);
                break;
            case 8: // PI / 2
                t.translate(0, width);
                t.rotate(  3 * Math.PI / 2);
                bufferedImage = transform(bufferedImage, t, true);
                break;
        }

        byte[] result = null;
        String contentType = null;

        List<Pair<String, String>> formats = Arrays.asList(
                Pair.of("jpeg", "image/jpeg"),
                Pair.of("jpg", "image/jpeg"),
                Pair.of("png", "image/png")
        );
        for (Pair<String, String> formatMimeType : formats) {
            final String format = formatMimeType.getLeft();
            final String mimeType = formatMimeType.getRight();

            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, format, baos);

                result = baos.toByteArray();
                contentType = mimeType;
                break;

            } catch (Exception e) {
                logger.error("Image write failure. Format: {}. Error message: {}.", format, ExceptionUtils.getRootCauseMessage(e));
            }
        }

        Validate.isTrue((result != null), "Could not write the image.");
        Validate.isTrue(StringUtils.isNotBlank(contentType), String.format("Empty contentType. Despite the fact that result is not null (%s bytes).", result.length));

        response.setContentType(contentType);
        response.setHeader("Content-Transfer-Encoding", "binary");
        FileCopyUtils.copy(result, response.getOutputStream());
        */
    }

    /*
    private static BufferedImage transform(BufferedImage bimage, AffineTransform transform, boolean rotate) {
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage destinationImage = new BufferedImage(bimage.getWidth(), bimage.getHeight(), bimage.getType());
        op.filter(bimage, destinationImage);
        return destinationImage;
    }
    */

    @DeleteMapping("/{fileId}")
    public void deleteFile(@AuthenticationPrincipal Jwt jwt,
                           @PathVariable("fileId") UUID fileId) throws IOException {
        fileService.deleteFile(fileId);
    }
}
