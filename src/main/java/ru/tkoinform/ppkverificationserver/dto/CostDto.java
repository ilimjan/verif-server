package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

@Getter
@Setter
@ExportName(name = "Сведения о стоимости объекта по обращению с ТКО")
public class CostDto extends UuidEntityDto {

    @ExportName(name = "Стоимость объекта всего")
    private Double total;
    @ExportName(name = "Стоимость оборудования всего")
    private Double equipment;
    @ExportName(name = "Стоимость оборудования российского производства (всего)")
    private Double russianEquipment;
    @ExportName(name = "Стоимость монтажа и пусконаладки")
    private Double installation;
    @ExportName(name = "Стоимость монтажа и пусконаладки произведенного российской компанией")
    private Double russianInstallation;
}
