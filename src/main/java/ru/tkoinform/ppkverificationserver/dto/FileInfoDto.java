package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class FileInfoDto {

    private UUID id;
    private String fileName;
    private String fileType;
    private long size;
    private Double longitude;
    private Double latitude;
    private Date created;
}
