package ru.tkoinform.ppkverificationserver.dto.other;

import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class ImportInfrastructureObjectDto {
    private String id; // не нужно
    private String name; //+
    private String versionState;
    private Integer versionStateId;
    private Integer objectStateId;
    private Date commissioningDate; // тут только год,а в тз это поле должно быть датой
    private Date decommissioningDate; // тут только год,а в тз это поле должно быть датой
    private Integer regionId;
    private Integer categoryId;
    private Integer typeId;
    private String typeName;
    private Integer areaId;
    private String address; // это что за адрес? фиас или description?
    private String latitude; // +
    private String longitude; // +
    private String organizationId;
    private String organizationName;
    private String numberGroro; // +
    private String wasteClassName;
    private List<ImportIndicatorDto> indicators;
    private String createDate; // версионность
    private String updateDate; // версионность
    private String publishDate; // ?
    private String sourceId; //
    private String cadastralNumber; //+
    private Integer areaObject;
    private List<ImportWasteCategoryDto> wasteCategories;
    private List<ImportFileDto> files; // ?
    private List<ImportContactDto> contacts; // нету
    private Integer objectsInCluster;
    private Boolean isWeightControl; //+
    private Boolean isSanitaryZone;
    private Boolean isProximityAirport;
    private Integer distanceAirport; // +
    private Boolean isLicenceExists; //+
    private Boolean isTariffExists;
    private String invertProjectId;
    private Integer objectCost; // +
    private Integer costEquipment; // +
    private Integer installationCostRf;
    private Integer costEquipmentRf; // +
    private Integer installationCost; // +
    private Boolean isProtocolSigned; // нету
    private Boolean isDeleted;
}
