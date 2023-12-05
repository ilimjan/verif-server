package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportIgnored;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ExportName(name = "Выпускаемые товары")
public class ProductDto extends UuidEntityDto {

    @ExportName(name = "Наименование выпускаемого товара")
    private String name;
    @ExportName(name = "Объем выпускаемого товара")
    private Double volume;
    @ExportIgnored
    private List<FileInfoDto> photos = new ArrayList<>();
}
