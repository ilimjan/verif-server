package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ExportName(name = "Сведения о региональном операторе, в зону деятельности которого попадает объект по обращению с ТКО")
public class RoInfoDto extends UuidEntityDto {

    @ExportName(name = "ID регионального оператора")
    private UUID roId;
    @ExportName(name = "Наименование зоны деятельности регионального оператора")
    private String name;
    @ExportName(name = "ИНН регионального оператора")
    private String inn;
    //private Date startDate;
    //private Date endDate;
    //private Double pricingPerTonn;
    //private Double pricingPerCoub;
    @ExportName(name = "Наименование зоны деятельности регионального оператора")
    private String zoneName;
    //private ReferenceDto municipalName;
    //private ReferenceDto oktmoNumber;
}
