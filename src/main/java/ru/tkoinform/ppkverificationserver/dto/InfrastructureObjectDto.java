package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportIgnored;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;
import ru.tkoinform.ppkverificationserver.dto.base.LabeledEnumDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ExportName(name = "Объект размещения отходов")
public class InfrastructureObjectDto extends UuidEntityDto {

    @ExportName(name = "ID федеральной схемы")
    private UUID federalSchemeId;
    @ExportName(name = "Внеш. ID федеральной схемы")
    private String federalSourceId;
    @ExportName(name = "Название")
    private String name;
    //private Double power;
    @ExportName(name = "Наименование правообладателя объекта по обращению с ТКО")
    private String ownerName;
    @ExportName(name = "ИНН правообладателя объекта по обращению с ТКО")
    private String ownerInn;
    @ExportName(name = "Текстовое описание адресного ориентира")
    private String addressDescription;
    @ExportName(name = "Координаты объекта в WGS 84: широта")
    private String latitude;
    @ExportName(name = "Координаты объекта в WGS 84: долгота")
    private String longitude;
    @ExportName(name = "Дата начала строительства объекта")
    private Date constructionStartDate;
    @ExportName(name = "Дата ввода объекта в эксплуатацию")
    private Date exploitationStartDate;
    @ExportName(name = "Наименование и реквизиты о вводе объекта в эксплуатацию")
    private String exploitationStartActNumber;
    @ExportName(name = "Срок эксплуатации объекта, лет")
    private Double exploitationPeriod;
    @ExportName(name = "Дата вывода из эксплуатации")
    private Date exploitationEndDate;
    private FileInfoDto exploitationAct;
    @ExportName(name = "Принадлежность объекта к федеральными региональным программам")
    private Boolean isInFederalOrRegionalPrograms;
    @ExportName(name = "Программы с указанием их реквизитов")
    private String programs;
    @ExportName(name = "Наличие аналогичных объектов в рассматриваемом субъекте РФ")
    private Boolean hasSimilarObjectsInSubject;
    //private Integer similarObjectsCount;
    @ExportName(name = "Действующие и планируемые аналогичные объекты в рассматриваемом субъекте РФ")
    private String currentAndPlannedSimilarObjects;
    //private boolean isVerified;

    @ExportIgnored
    private LabeledEnumDto flowStatus;
    @ExportIgnored
    private LabeledEnumDto sourceInformation;

    @ExportIgnored
    private LabeledEnumDto updateSource;

    @ExportIgnored
    private Date flowStatusChanged;
    @ExportIgnored
    private String statusChangingReason;
    @ExportIgnored
    private List<String> verifiedFields;

    private List<WasteCategoryInfoDto> wasteCategoryInfos; // +
    private VerificationStatusInfoDto verificationStatusInfo; // +

    @ExportName(name = "Вид обращения с отходами")
    private ReferenceDto wasteManagementType;
    @ExportName(name = "Категория объекта")
    private ReferenceDto type;
    @ExportName(name = "Тип объекта по обращению с ТКО")
    private ReferenceDto tkoObjectType;

    private FiasAddressDto fiasAddress;  // +
    private OperatorDto operator;  // +
    //private InvestmentDto investment;
    private List<InvestmentDto> investments = new ArrayList<InvestmentDto>(); // +
    private CostDto cost; // +
    private List<BorderLandPlotDto> borderLandPlots; // +
    //private CurrentLandPlotDto currentLandPlot;
    private List<CurrentLandPlotDto> currentLandPlots = new ArrayList<CurrentLandPlotDto>(); // +
    private AcceptanceDocumentationDto acceptanceDocumentation; // +
    private WorkScheduleDto workSchedule; // +
    private TariffInfoDto tariffInfo; // +
    private IndicatorDto indicator; // +
    private WasteInfoDto wasteInfo; // +
    private InfrastructureObjectInfoDto infrastructureObjectInfo; // +
    private List<EquipmentDto> equipment; // +

    @ExportName(name = "Количество оборудования")
    private Integer equipmentCount;
    @ExportName(name = "Наличие основного производственного оборудования")
    private Boolean hasCoreEquipment;
    @ExportName(name = "Наличие вспомогательного производственного оборудования")
    private Boolean hasAuxiliaryEquipment;

    private SecondaryResourcesDto secondaryResources; // +
    private RoInfoDto roInfo; // +
    @ExportIgnored
    private TechnicalSurveyDto technicalSurvey;
    private DataSourceDto dataSource; // +
    private ProductsInfoDto productsInfo; // +

    @ExportName(name = "Статус объекта по обращению с ТКО")
    private ReferenceDto status;
    @ExportName(name = "Иной статус")
    private String otherObjectStatusName;
    @ExportName(name = "Наименование федерального округа")
    private ReferenceDto federalDistrictName;
    @ExportName(name = "Наименование субъекта Российской Федерации, на территории которого расположен объект")
    private ReferenceDto subjectName;
    @ExportName(name = "Наименование района субъекта Российской Федерации, на территории которого расположен объект")
    private ReferenceDto subjectAreaName;

    @ExportName(name = "Категория отходов")
    private List<ReferenceDto> category = new ArrayList<ReferenceDto>();

    @ExportName(name = "Дата ревизии до")
    private Date revisionBefore;

    @ExportIgnored
    private String regionId;

    @ExportIgnored
    private String revisionType;

    @ExportIgnored
    private String revisionDateTime;

    @ExportIgnored
    private String revisionUserName;
    //private ReferenceDto oktmoNumber;
    //private ReferenceDto category;
}
