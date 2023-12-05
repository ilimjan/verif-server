package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ExportName(name = "Сведения о вторичных материальных ресурсах (ВМР), извлекаемых объектом по обращению с ТКО")
public class SecondaryResourcesDto extends UuidEntityDto {

    @ExportName(name = "Процент извлекаемых вторичных материальных ресурсов по массе")
    private Double extractPercent;
    private List<SecondaryResourceTypeDto> types = new ArrayList<SecondaryResourceTypeDto>(); // +

}
