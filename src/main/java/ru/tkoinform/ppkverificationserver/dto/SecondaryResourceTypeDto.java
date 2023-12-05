package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

@Getter
@Setter
@ExportName(name = "Вторичные материалы")
public class SecondaryResourceTypeDto extends UuidEntityDto {

    @ExportName(name = "Тип")
    private ReferenceDto reference;
    @ExportName(name = "Иной тип материала")
    private String otherSecondaryResourceTypeName;
    @ExportName(name = "Процент от массы входного потока")
    private Double percent;

}
