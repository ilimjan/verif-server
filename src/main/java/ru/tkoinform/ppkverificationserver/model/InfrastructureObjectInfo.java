package ru.tkoinform.ppkverificationserver.model;

import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Audited
@Entity
public class InfrastructureObjectInfo extends UuidEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "infrastructure_object_id")
    private InfrastructureObject infrastructureObject;

    // Наличие весового контроля
    private Boolean hasWeightControl;
//    @Digits(integer = 20, fraction = 2)
    private Integer weightControlCount;
    @Size(max = 400)
    @Column(length = 400)
    private String noWeightControlReason;
    @OneToMany
    @JoinTable(
            name = "infrastructure_object_info_weight_control",
            joinColumns = @JoinColumn(name = "infrastructure_object_info_id"),
            inverseJoinColumns = @JoinColumn(name = "infrastructure_unit_id")
    )
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Set<InfrastructureUnit> weightControls;
    /*
    private Double weightControlEquipmentCount;
    private String weightControlEquipmentBrand;
    private String weightControlEquipmentManufacturer;
    private Double weightPlatformLength;
    @OneToMany
    @JoinTable(
            name = "infrastructure_object_info_equipment_photos",
            joinColumns = @JoinColumn(name = "infrastructure_object_info_id"),
            inverseJoinColumns = @JoinColumn(name = "file_info_id")
    )
    private Set<FileInfo> equipmentPhotos;
    */

    // Наличие пункта мойки колес
    private Boolean hasWheelsWashingPoint;
//    @Digits(integer = 20, fraction = 2)
    private Integer wheelsWashingPointCount;
    @Size(max = 400)
    @Column(length = 400)
    private String noWheelsWashingPointReason;
    @OneToMany
    @JoinTable(
            name = "infrastructure_object_info_wheels_washing_points",
            joinColumns = @JoinColumn(name = "infrastructure_object_info_id"),
            inverseJoinColumns = @JoinColumn(name = "infrastructure_unit_id")
    )
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Set<InfrastructureUnit> wheelsWashingPoints;
    /*
    private Integer wheelsWashingEquipmentCount;
    private String wheelsWashingEquipmentBrand;
    private String wheelsWashingEquipmentManufacturer;

    @OneToMany
    @JoinTable(
            name = "infrastructure_object_info_wheels_washing_equipment_photos",
            joinColumns = @JoinColumn(name = "infrastructure_object_info_id"),
            inverseJoinColumns = @JoinColumn(name = "file_info_id")
    )
    private Set<FileInfo> wheelsWashingEquipmentPhotos;
    */

    // Наличие систем видеонаблюдения
    private Boolean hasVideoEquipment;
    @Size(max = 400)
    @Column(length = 400)
    private String noVideoEquipmentReason;
    @ManyToOne
    @JoinColumn(name = "video_equipment_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private InfrastructureUnit videoEquipment;
    /*
    private Integer videoEquipmentCount;
    */

    // Наличие локальных очистных сооружений
    private Boolean hasLocalTreatmentFacilities;
//    @Digits(integer = 20, fraction = 0)
    private Integer localTreatmentFacilitiesCount;
    @Size(max = 400)
    @Column(length = 400)
    private String noLocalTreatmentFacilitiesReason;
    @OneToMany
    @JoinTable(
            name = "infrastructure_object_info_local_treatment_facilities",
            joinColumns = @JoinColumn(name = "infrastructure_object_info_id"),
            inverseJoinColumns = @JoinColumn(name = "infrastructure_unit_id")
    )
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Set<InfrastructureUnit> localTreatmentFacilities;
    /*
    private Integer localTreatmentFacilitiesCount;
    private Double treatmentFacilitiesCapacity;
    @OneToMany
    @JoinTable(
            name = "infrastructure_object_info_treatment_facilities_photos",
            joinColumns = @JoinColumn(name = "infrastructure_object_info_id"),
            inverseJoinColumns = @JoinColumn(name = "file_info_id")
    )
    private Set<FileInfo> treatmentFacilitiesPhotos;
    @OneToOne
    @JoinColumn(name = "treatment_facilities_passport_file_info_id")
    private FileInfo treatmentFacilitiesPassport;
    private String noTreatmentFacilitiesReason;
    @ManyToOne
    @JoinColumn(name = "treatment_facilities_reference_id")
    private Reference treatmentFacilitiesType;
    */

    // Наличие радиационного контроля
    private Boolean hasRadiationControl;
//    @Digits(integer = 20, fraction = 0)
    private Integer radiationControlCount;
    @Size(max = 400)
    @Column(length = 400)
    private String noRadiationControlReason;
    @OneToMany
    @JoinTable(
            name = "infrastructure_object_info_radiation_controls",
            joinColumns = @JoinColumn(name = "infrastructure_object_info_id"),
            inverseJoinColumns = @JoinColumn(name = "infrastructure_unit_id")
    )
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Set<InfrastructureUnit> radiationControls;

    /*
    private Integer radiationControlEquipmentCount;
    private String radiationControlEquipmentBrand;
    private String radiationControlEquipmentManufacturer;
    @OneToMany
    @JoinTable(
            name = "infrastructure_object_info_radiation_control_equipment_photos",
            joinColumns = @JoinColumn(name = "infrastructure_object_info_id"),
            inverseJoinColumns = @JoinColumn(name = "file_info_id")
    )
    private Set<FileInfo> radiationControlEquipmentPhotos;
    @OneToOne
    @JoinColumn(name = "radiation_control_equipment_passport_file_info_id")
    private FileInfo radiationControlEquipmentPassport;
    */

    // Наличие обустроенной сети дорог
    private Boolean hasRoads;
    @Size(max = 400)
    @Column(length = 400)
    private String noRoadsReason;
    @ManyToOne
    @JoinColumn(name = "road_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private InfrastructureUnit road;
    /*
    private Double roadsLength;
    @OneToOne
    @JoinColumn(name = "road_scheme_file_info_id")
    private FileInfo roadsScheme;
    @ManyToOne
    @JoinColumn(name = "road_surface_type_reference_id")
    private Reference roadSurfaceType;
    */

    // Наличие ограждения по периметру
    private Boolean hasFences;
    @Size(max = 400)
    @Column(length = 400)
    private String noFencesReason;
    @ManyToOne
    @JoinColumn(name = "fence_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private InfrastructureUnit fence;
    /*
    @OneToMany
    @JoinTable(
            name = "infrastructure_object_info_fences_photos",
            joinColumns = @JoinColumn(name = "infrastructure_object_info_id"),
            inverseJoinColumns = @JoinColumn(name = "file_info_id")
    )
    private Set<FileInfo> fencesPhotos;
    @ManyToOne
    @JoinColumn(name = "fences_type_reference_id")
    private Reference fencesType;
    */

    // Наличие освещения
    private Boolean hasLights;
    @Size(max = 400)
    @Column(length = 400)
    private String noLightsReason;
    @ManyToOne
    @JoinColumn(name = "light_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private InfrastructureUnit light;
    /*
    @ManyToOne
    @JoinColumn(name = "lights_type_reference_id")
    private Reference lightsType;
    */

    // Наличие пункта охраны
    private Boolean hasSecurity;
    @Size(max = 400)
    @Column(length = 400)
    private String noSecurityReason;
    @ManyToOne
    @JoinColumn(name = "security_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private InfrastructureUnit security;
    /*
    private Integer securityEmployeeCount;
    @ManyToOne
    @JoinColumn(name = "security_source_reference_id")
    private Reference securitySource;
    */

    // Наличие АСУ
    private Boolean hasAsu;
    @Size(max = 400)
    @Column(length = 400)
    private String noAsuReason;
    @ManyToOne
    @JoinColumn(name = "asu_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private InfrastructureUnit asu;
    /*
    private String asuName;
    private String asuFunctions;
    */

    // Наличие системы мониторинга окружающей среды
    private Boolean hasEnvironmentMonitoringSystem;
//    @Digits(integer = 20, fraction = 0)
    private Integer environmentMonitoringSystemCount;
    @Size(max = 400)
    @Column(length = 400)
    private String noEnvironmentMonitoringSystemReason;
    @OneToMany
    @JoinTable(
            name = "infrastructure_object_info_environment_monitoring_systems",
            joinColumns = @JoinColumn(name = "infrastructure_object_info_id"),
            inverseJoinColumns = @JoinColumn(name = "infrastructure_unit_id")
    )
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Set<InfrastructureUnit> environmentMonitoringSystems;
    /*
    private Integer environmentMonitoringSystemsCount;
    @ManyToOne
    @JoinColumn(name = "environment_monitoring_system_reference_id")
    private Reference environmentMonitoringSystemUsed;
    */
}
