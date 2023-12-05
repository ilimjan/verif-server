package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportIgnored;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ExportName(name = "Сведения об объектах инфраструктуры объекта по обращению с ТКО")
public class InfrastructureObjectInfoDto extends UuidEntityDto {

    // Наличие весового контроля
    @ExportName(name = "Наличие весового контроля на объекте")
    private Boolean hasWeightControl;
    @ExportName(name = "Количество оборудования весового контроля на объекте")
    private Integer weightControlCount;
    @ExportName(name = "Причина отсутствия весового контроля")
    private String noWeightControlReason;
    @ExportName(name = "Об. инф. - весовой контроль")
    private List<InfrastructureUnitDto> weightControls = new ArrayList<InfrastructureUnitDto>();

    // Наличие пункта мойки колес
    @ExportName(name = "Наличие пункта мойки колес")
    private Boolean hasWheelsWashingPoint;
    @ExportName(name = "Количество оборудования пункта мойки колес")
    private Integer wheelsWashingPointCount;
    @ExportName(name = "Причина отсутствия пункта мойки колес")
    private String noWheelsWashingPointReason;
    @ExportName(name = "Об. инф. - пункты мойки колес")
    private List<InfrastructureUnitDto> wheelsWashingPoints = new ArrayList<InfrastructureUnitDto>();

    // Наличие систем видеонаблюдения
    @ExportName(name = "Наличие систем видеонаблюдения")
    private Boolean hasVideoEquipment;
    @ExportName(name = "Причина отсутствия систем видеонаблюдения")
    private String noVideoEquipmentReason;
    @ExportName(name = "Об. инф. - системы видеонаблюдения")
    private InfrastructureUnitDto videoEquipment;

    // Наличие локальных очистных сооружений
    @ExportName(name = "Наличие локальных очистных сооружений")
    private Boolean hasLocalTreatmentFacilities;
    @ExportName(name = "Количество локальных очистных сооружений")
    private Integer localTreatmentFacilitiesCount;
    @ExportName(name = "Причина отсутствия локальных очистных сооружений")
    private String noLocalTreatmentFacilitiesReason;
    @ExportName(name = "Об. инф. - локальные очистные сооружения")
    private List<InfrastructureUnitDto> localTreatmentFacilities = new ArrayList<InfrastructureUnitDto>();

    // Наличие радиационного контроля
    @ExportName(name = "Наличие радиационного контроля")
    private Boolean hasRadiationControl;
    @ExportName(name = "Количество оборудования радиационного контроля")
    private Integer radiationControlCount;
    @ExportName(name = "Причина отсутствия радиационного контроля")
    private String noRadiationControlReason;
    @ExportName(name = "Об. инф. - радиационный контроль")
    private List<InfrastructureUnitDto> radiationControls = new ArrayList<InfrastructureUnitDto>();

    // Наличие обустроенной сети дорог
    @ExportName(name = "Наличие обустроенной сети дорог на объекте")
    private Boolean hasRoads;
    @ExportName(name = "Причина отсутствия обустроенной сети дорог")
    private String noRoadsReason;
    @ExportName(name = "Об. инф. - обустроенные сети дорог на объекте")
    private InfrastructureUnitDto road;

    // Наличие ограждения по периметру
    @ExportName(name = "Наличие ограждения по периметру объекта по обращению с ТКО")
    private Boolean hasFences;
    @ExportName(name = "Причина отсутствия ограждения по периметру объекта")
    private String noFencesReason;
    @ExportName(name = "Об. инф. - ограждения по периметру объекта")
    private InfrastructureUnitDto fence;

    // Наличие освещения
    @ExportName(name = "Наличие освещения на объекте для работы в темное время суток")
    private Boolean hasLights;
    @ExportName(name = "Причина отсутствия освещения на объекте")
    private String noLightsReason;
    @ExportName(name = "Об. инф. - освещение")
    private InfrastructureUnitDto light;

    // Наличие пункта охраны
    @ExportName(name = "Наличие пункта охраны")
    private Boolean hasSecurity;
    @ExportName(name = "Причина отсутствия пункта охраны")
    private String noSecurityReason;
    @ExportName(name = "Об. инф. - пункт охраны")
    private InfrastructureUnitDto security;

    // Наличие АСУ
    @ExportName(name = "Наличие автоматизированной системы управления (АСУ) объектом по обращению с ТКО")
    private Boolean hasAsu;
    @ExportName(name = "Причина отсутствия автоматизированной системы управления (АСУ)")
    private String noAsuReason;
    @ExportName(name = "Об. инф. - АСУ")
    private InfrastructureUnitDto asu;

    // Наличие системы мониторинга окружающей среды
    @ExportName(name = "Наличие системы мониторинга окружающей среды на объекте по обращению с ТКО")
    private Boolean hasEnvironmentMonitoringSystem;
    @ExportName(name = "Количество систем мониторинга окружающей среды на объекте")
    private Integer environmentMonitoringSystemCount;
    @ExportName(name = "Причина отсутствия систем мониторинга окружающей среды")
    private String noEnvironmentMonitoringSystemReason;
    @ExportName(name = "Об. инф. - системы мониторинга окружающей среды")
    private List<InfrastructureUnitDto> environmentMonitoringSystems = new ArrayList<InfrastructureUnitDto>();

}
