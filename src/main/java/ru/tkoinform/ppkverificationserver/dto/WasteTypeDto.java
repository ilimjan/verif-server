package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ExportName(name = "Виды отходов")
public class WasteTypeDto extends UuidEntityDto {

    @ExportName(name = "Вид отходов")
    private ReferenceDto wasteType;
//    @ExportName(name = "Класс опасности отходов, которые перерабатываются на объекте")
//    private ReferenceDto dangerType;
    @ExportName(name = "Класс опасности отходов, которые перерабатываются на объекте")
    private List<ReferenceDto> dangerType = new ArrayList<ReferenceDto>();
    //private ReferenceDto dangerType;
}
