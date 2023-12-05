package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

@Getter
@Setter
@ExportName(name = "Направления вывоза отходов")
public class WasteDirectionDto extends UuidEntityDto {

    @ExportName(name = "Направление вывоза отходов образуемых объектом отходов (указать координаты объектов или номера ГРОРО)")
    private String coordsOrGroro;
}
