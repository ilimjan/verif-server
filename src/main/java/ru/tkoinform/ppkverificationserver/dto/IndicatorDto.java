package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportIgnored;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ExportName(name = "Сведения о характеристиках, индикаторах (показателях) деятельности, технологической части объекта обработки, утилизации, обезвреживания и размещения ТКО")
public class IndicatorDto extends UuidEntityDto {

    //private Double projectCapacity;
    @ExportName(name = "Производственная мощность объекта, тыс. тонн м в год")
    private Double productionCapacity;
    @ExportName(name = "Свободная мощность объекта, тыс. тонн в год")
    private Double freeCapacity;
    @ExportName(name = "Масса ТКО, принятых в предыдущем году")
    private Double previousYearTkoWeight;
    @ExportName(name = "Масса других видов отходов (не ТКО), принятых в предыдущем году")
    private Double previousYearNonTkoWeight;
    //private Double fullPojectCapacity;
    @ExportName(name = "Остаточная емкость для размещения отходов на начало следующего года")
    private Double nextYearFreeCapacity;
    @ExportName(name = "Проектная масса производимого вторичного сырья в год")
    private Double secondaryRawMaterialsPerYear;
    @ExportName(name = "Проектная доля утилизации")
    private Double utilizationPercent;
    @ExportName(name = "Общая площадь объекта")
    private Double area;
    @ExportIgnored
    private List<FileInfoDto> photos = new ArrayList<FileInfoDto>();
    @ExportName(name = "Площадь производственной части объекта")
    private Double productionPartArea;
    @ExportIgnored
    private List<FileInfoDto> productionPartPhotos = new ArrayList<FileInfoDto>();
    @ExportName(name = "Площадь зоны для разгрузки поступающих отходов")
    private Double unloadingZoneArea;
    @ExportName(name = "Площадь цеха сортировки отходов")
    private Double sortingZoneArea;
    @ExportName(name = "Наличие участка компостирования отходов")
    private Boolean hasCompostingZone;
    @ExportName(name = "Мощность участка")
    private Double compostingZoneCapacityPerYear;
    @ExportName(name = "Наименование входящего материала для компостирования")
    private String compostingMaterialName;
    @ExportName(name = "Причина отсутствия участка компостирования отходов")
    private String noCompostingZoneReason;
    @ExportName(name = "Назначение получаемого компоста")
    private String compostPurpose;
    @ExportName(name = "Наличие участка производства RDF (refuse derived fuel, твердое вторичное топливо)")
    private Boolean hasRdfZone;
    @ExportName(name = "Мощность участка")
    private Double rdfZoneCapacityPerYear;
    @ExportName(name = "Назначение получаемого RDF")
    private String rdfPurpose;
    //private String noRdfZoneReason;
    @ExportIgnored
    private FileInfoDto technologicalScheme;
    @ExportIgnored
    private List<FileInfoDto> technologicalSchemePhotos = new ArrayList<FileInfoDto>();
    @ExportIgnored
    private FileInfoDto generalScheme;
    @ExportIgnored
    private List<FileInfoDto> generalSchemePhotos = new ArrayList<FileInfoDto>();
    @ExportIgnored
    private FileInfoDto productionZoneScheme;
    @ExportIgnored
    private List<FileInfoDto> productionZoneSchemePhotos = new ArrayList<FileInfoDto>();
    @ExportName(name = "Наличие потребности в импортозамещении оборудования объекта")
    private Boolean isImportSubstitutionNeeded;
    @ExportName(name = "Тип оборудования")
    private String equipmentType;
    @ExportName(name = "Использование оборудования включенного в НДТ (наличие включения основного и вспомогательного оборудования в актуальные справочники наилучших доступных технологий)")
    private Boolean isNdtEquipmentUsed;
    @ExportName(name = "Тип оборудования НДТ")
    private String ndtEquipmentType;
    @ExportName(name = "Наименование справочника НДТ")
    private String ndtDictionaryName;

    // Новое

    // Только для "размещения"
    @ExportName(name = "Площадь тела объекта размещения отходов (указывается площадь на которой планируется производить размещение отходов, в кв. м)")
    private Double placingArea;
    @ExportName(name = "Полная проектная высота полигона (указывается в метрах)")
    private Double projectHeight;

    @ExportName(name = "Наличие проведенных маркшейдерских работ на объекте")
    private Boolean hasMineSurveying;
    @ExportName(name = "Наименование организации проводившей маркшейдерские работы")
    private String mineSurveyingOrganizationName;
    @ExportName(name = "ИНН организации проводившей маркшейдерские работы")
    private String mineSurveyingOrganizationInn;
    @ExportIgnored
    private FileInfoDto mineSurveyingConclusion;

    @ExportName(name = "Количество карт для размещения отходов")
    private Integer mapCount;
    private List<PlacingMapDto> maps = new ArrayList<PlacingMapDto>(); // +

    @ExportName(name = "Тип гидроизоляции участка размещения отходов")
    private ReferenceDto waterproofingType;
    @ExportName(name = "Иной тип гидроизоляции участка размещения отходов")
    private String otherWaterproofingType;

    @ExportName(name = "Рельеф участка (указывается перепад высот на участке, наличие заболоченных территорий, лесных массивов)")
    private String relief;
    @ExportName(name = "Глубина залегания грунтовых вод (указывается в метрах)")
    private Double groundwaterDepth;
    @ExportName(name = "Возможность подтопления участка")
    private Boolean floodingPossibility;

    @ExportName(name = "Наличие уплотнителя отходов")
    private Boolean hasWasteCompactor;
    @ExportName(name = "Тип и модель уплотнителя отходов")
    private String wasteCompactorType;
    @ExportName(name = "Эксплуатационная масса уплотнителя отходов")
    private Double wasteCompactorMass;
    @ExportIgnored
    private List<FileInfoDto> wasteCompactorPhotos = new ArrayList<FileInfoDto>();

    // Утилизация или нейтрализация
    @ExportName(name = "Краткое описание технологического процесса")
    private String techProcessDescription;

    @ExportName(name = "Наличие площадки временного накопления отходов")
    private Boolean hasTemporaryWasteStorage;
    @ExportName(name = "Площадь площадки (указывается в кв.м)")
    private Double temporaryWasteStorageArea;
    @ExportName(name = "Виды отходов накапливаемых на площадке")
    private String temporaryWasteStorageWasteTypes;

    // Только для утилизации
    @ExportName(name = "Вид утилизации")
    private ReferenceDto utilizationType;

    // Только для нейтрализации
    @ExportName(name = "Наличие выработки электроэнергии в процессе обезвреживания отходов термическим способом")
    private Boolean hasThermalRecycling;
    @ExportName(name = "Мощность выработки электроэнергии в процессе обезвреживания отходов термическим способом")
    private Double thermalRecyclingPower;
    @ExportName(name = "Наличие выработки электроэнергии в процессе обезвреживания отходов")
    private Boolean hasRecycling;
    @ExportName(name = "Мощность выработки электроэнергии в процессе обезвреживания отходов")
    private Double recyclingPower;

}
