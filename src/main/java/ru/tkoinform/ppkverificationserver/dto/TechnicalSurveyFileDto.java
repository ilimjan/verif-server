package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TechnicalSurveyFileDto extends UuidEntityDto {

    private FileInfoDto file;
    private Date creationDate;
    private String latitude;
    private String longitude;
    private FileInfoDto signedAct;
}
