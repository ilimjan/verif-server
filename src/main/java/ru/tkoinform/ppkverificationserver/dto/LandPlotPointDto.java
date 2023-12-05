package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

@Getter
@Setter
@ExportName(name = "Точки")
public class LandPlotPointDto extends UuidEntityDto {

    @ExportName(name = "Номер точки")
    private String number;
    @ExportName(name = "Координата точки X")
    private String x;
    @ExportName(name = "Координата точки Y")
    private String y;

    @ExportName(name = "Описание закрепления на местности")
    private ReferenceDto description;
}
