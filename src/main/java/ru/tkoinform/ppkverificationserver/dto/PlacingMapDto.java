package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

import java.util.Date;

@Getter
@Setter
@ExportName(name = "Карты для размещения отходов")
public class PlacingMapDto extends UuidEntityDto {

    @ExportName(name = "Размеры карты для размещения отходов")
    private Double size;
    @ExportName(name = "Период ввода карты для размещения отходов в эксплуатацию")
    private Date period;
}
