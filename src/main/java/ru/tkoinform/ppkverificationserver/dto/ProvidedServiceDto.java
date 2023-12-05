package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

@Setter
@Getter
@ExportName(name = "Предоставляемые услуги")
public class ProvidedServiceDto extends UuidEntityDto {

    @ExportName(name = "Наименование предоставлемой услуги")
    private String name;
    @ExportName(name = "Объем предоставляемой услуги")
    private Double volume;
}
