package ru.tkoinform.ppkverificationserver.mapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkoinform.ppkverificationserver.dto.FileInfoDto;
import ru.tkoinform.ppkverificationserver.model.FileInfo;

@Service
public class FileMapper {

    @Autowired
    private GlobalMapper globalMapper;

    public FileInfoDto map(final FileInfo fileInfo) {
        return globalMapper.map(fileInfo, FileInfoDto.class);
    }
}
