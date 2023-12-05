package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

@Getter
@Setter
@ExportName(name = "Категория и кол-во отходов")
public class WasteCategoryInfoDto extends UuidEntityDto {

    @ExportName(name = "Категория отходов")
    private ReferenceDto wasteCategory;
    @ExportName(name = "Количество отходов")
    private Double count;
}
