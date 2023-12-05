package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportIgnored;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

import java.util.Date;

@Getter
@Setter
@ExportName(name = "Сведения о земельном участке (земельных участках), в границах которого (которых) находится объект по обращению с ТКО, и размещенных на нем (на них) зданиях")
public class BorderLandPlotDto extends UuidEntityDto {

    @ExportName(name = "Наличие земельного участка для реализации проекта")
    private boolean hasLandPlotForProjectRealization;
    @ExportName(name = "Срок приведения в соответствие категории земли")
    private Date compliancePeriod;
    @ExportName(name = "Наличие градостроительного плана земельного участка")
    private boolean hasUrbanDevelopmentPlan;
    @ExportName(name = "Наличие санитарно-защитной зоны")
    private boolean hasSanityProtectionZone;
    @ExportName(name = "Размер санитарно-защитной зоны")
    private Double sanitaryProtectionZoneSize;
    @ExportIgnored
    private FileInfoDto egrp;
    @ExportName(name = "Наименование правоустанавливающего документа на земельный участок")
    private String rightsGivingDocumentName;
    @ExportName(name = "Дата выдачи правоустанавливающего документа на земельный участок")
    private Date rightsGivingDocumentStartDate;
    @ExportName(name = "Номер правоустанавливающего документа на земельный участок")
    private String rigthsGivingDocumentNumber;
    @ExportIgnored
    private FileInfoDto rigthsGivingDocument;
    @ExportName(name = "Площадь земельного участка")
    private Double landPotArea;
    @ExportName(name = "Площадь участка пригодная для ведения хозяйственной деятельности")
    private Double suitableForExonomicActivitiesLandPloArea;
    @ExportName(name = "Наличие энергоснабжения")
    private Boolean hasPowerSupply;
    @ExportName(name = "Выделенная электрическая мощность")
    private Double dedicatedElecricalPower;
    @ExportName(name = "Срок подключения к электроснабжению")
    private Date powerSupplyConnectionDate;
    @ExportName(name = "Наличие водоснабжения и водоотведения")
    private Boolean hasWaterSupply;
    @ExportName(name = "Мощность водоснабжения и водоотведения")
    private Double waterSupplyPower;
    @ExportName(name = "Срок подключения к водоснабжению")
    private Date watterSupplyConnectionDate;
    @ExportName(name = "Наличие газоснабжения")
    private Boolean hasGasSupply;
    @ExportName(name = "Мощность газоснабжения")
    private Double gasSupplyPower;
    @ExportName(name = "Срок подключения к газоснабжению")
    private Date gasSupplyConnectionDate;
    @ExportName(name = "Наличие подключения к тепловым сетям")
    private Boolean hasHeatPipelinesConnection;
    @ExportName(name = "Срок подключения к тепловым сетям")
    private Date heatPipelinesConnectionStartDate;
    @ExportName(name = "Наличие автомобильных подъездных путей")
    private Boolean hasAutomobileAccessRoads;
    @ExportName(name = "Срок организации подъездных путей")
    private Date automobileAccessRoadsConnectionDate;
    @ExportName(name = "Наличие железнодорожных путей")
    private Boolean hasRailway;
    @ExportName(name = "Расстояние от объекта, до ближайшей действующей ж/д ветки")
    private Double distanceToNearestRailway;
    @ExportName(name = "Расстояние до действующего пункта перевалки на железнодорожном транспорте")
    private Double distanceToNearestTransshipment;
    @ExportName(name = "Расстояние до ближайшего аэропорта")
    private Double distanceToNearestAirport;
    @ExportName(name = "Наименование аэропорта")
    private String nearestAirportName;
    @ExportName(name = "Наименование ближайшего населенного пункта")
    private String nearestSettlementName;
    @ExportName(name = "Расстояние до ближайшего населенного пункта")
    private Double distanceToNearestSettlement;
    @ExportName(name = "Адрес здания (сооружения) на территории земельного участка, на котором расположен объект")
    private String address;
    @ExportName(name = "Площадь здания (сооружения) на территории земельного участка, на котором расположен объект")
    private Double area;
    @ExportName(name = "Кадастровый номер земельного участка")
    private String cadastralNumber;
    @ExportName(name = "Кадастровый номер здания (сооружения) на территории земельного участка, на котором расположен объект")
    private String buildingCadastralNumber;

    @ExportName(name = "Вид собственности на земельный участок")
    private ReferenceDto ownershipType;
    @ExportName(name = "Категория земель")
    private ReferenceDto landCategory;
    @ExportName(name = "Вид разрешенного использования земельного участка")
    private ReferenceDto allowedUsingType;
    //private ReferenceDto automobileRoadType;
}
