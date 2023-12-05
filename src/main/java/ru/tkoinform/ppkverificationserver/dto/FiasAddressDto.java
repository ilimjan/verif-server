package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

@Getter
@Setter
@ExportName(name = "ФИАС")
public class FiasAddressDto extends UuidEntityDto {

    @ExportName(name = "Наименование региона")
    private String regionName;
    @ExportName(name = "Муниципальный район/округ")
    private String municipalRegionName;
    @ExportName(name = "Город")
    private String city;
    @ExportName(name = "Внутригородская территория")
    private String innerCityTerritory;
    @ExportName(name = "Населенный пункт")
    private String area;
    @ExportName(name = "Элемент планировочной структуры")
    private String planningStructureElement;
    @ExportName(name = "Элемент улично-дорожной сети")
    private String streetRoadElement;
    @ExportName(name = "Почтовый индекс")
    private String postalCode;
    @ExportName(name = "Номер здания/сооружения")
    private String buildingNumber;
    @ExportName(name = "Номер помещения")
    private String roomNumber;
    @ExportName(name = "Номер земельного участка")
    private String landPlotNumber;
    @ExportName(name = "Код ОКТМО")
    private String oktmo;
    @ExportName(name = "Кадастровый номер (ОКС/ЗУ)")
    private String cadastralNumber;
}
