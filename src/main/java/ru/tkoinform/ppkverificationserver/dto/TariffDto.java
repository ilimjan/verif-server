package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportIgnored;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ExportName(name = "Тарифы")
public class TariffDto extends UuidEntityDto {

    @ExportName(name = "Значение предельного тарифа (за тонну)")
    private Double tariffPerTon;
    //private Double tariffPerMeter;
    @ExportName(name = "Период действия тарифа с")
    private Date tariffStartDate;
    @ExportName(name = "Период действия тарифа по")
    private Date tariffEndDate;

    @ExportIgnored
    private List<FileInfoDto> tariffDocumentPhotos = new ArrayList<FileInfoDto>();
    @ExportIgnored
    private FileInfoDto tariffDocument;
}
