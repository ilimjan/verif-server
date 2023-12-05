package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

@Getter
@Setter
@ExportName(name = "Импортированные точки")
public class LandPlotPointImportedDto extends UuidEntityDto {
    @ExportName(name = "Номер точки")
    private String number;
    @ExportName(name = "Координата точки X")
    private String x;
    @ExportName(name = "Координата точки Y")
    private String y;
}
