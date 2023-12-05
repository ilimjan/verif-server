package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Audited
@Entity
public class Indicator extends UuidEntity {
    //private Double projectCapacity;
    @Digits(integer = 20, fraction = 3)
    private Double productionCapacity;
    @Digits(integer = 20, fraction = 3)
    private Double freeCapacity;
    @Digits(integer = 20, fraction = 2)
    private Double previousYearTkoWeight;
    @Digits(integer = 20, fraction = 2)
    private Double previousYearNonTkoWeight;
    //@Digits(integer = 20, fraction = 2)
    //private Double fullPojectCapacity;
    @Digits(integer = 20, fraction = 2)
    private Double nextYearFreeCapacity;
    @Digits(integer = 20, fraction = 2)
    private Double secondaryRawMaterialsPerYear;
    @Digits(integer = 20, fraction = 2)
    private Double utilizationPercent;
    @Digits(integer = 20, fraction = 3)
    private Double area;

    @OneToMany
    @JoinTable(
            name = "indicator_photos",
            joinColumns = @JoinColumn(name = "indicator_id"),
            inverseJoinColumns = @JoinColumn(name = "file_info_id")
    )
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    private Set<FileInfo> photos;
    @Digits(integer = 20, fraction = 3)
    private Double productionPartArea;

    @OneToMany
    @JoinTable(
            name = "indicator_production_part_photos",
            joinColumns = @JoinColumn(name = "indicator_id"),
            inverseJoinColumns = @JoinColumn(name = "file_info_id")
    )
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    private Set<FileInfo> productionPartPhotos;
    @Digits(integer = 20, fraction = 3)
    private Double unloadingZoneArea;
    @Digits(integer = 20, fraction = 3)
    private Double sortingZoneArea;
    private Boolean hasCompostingZone;
    @Digits(integer = 20, fraction = 2)
    private Double compostingZoneCapacityPerYear;
    @Size(max = 400)
    @Column(length = 400)
    private String compostingMaterialName;
    @Size(max = 400)
    @Column(length = 400)
    private String noCompostingZoneReason;
    @Size(max = 400)
    @Column(length = 400)
    private String compostPurpose;
    private Boolean hasRdfZone;
    @Digits(integer = 20, fraction = 2)
    private Double rdfZoneCapacityPerYear;
    @Size(max = 400)
    @Column(length = 400)
    private String rdfPurpose;
    //@Size(max = 400)
    //@Column(length = 400)
    //private String noRdfZoneReason;

    @OneToOne
    @JoinColumn(name = "tech_scheme_file_info_id")
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    private FileInfo technologicalScheme;

    @OneToMany
    @JoinTable(
            name = "technological_scheme_photos",
            joinColumns = @JoinColumn(name = "indicator_id"),
            inverseJoinColumns = @JoinColumn(name = "file_info_id")
    )
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    private Set<FileInfo> technologicalSchemePhotos;

    @OneToOne
    @JoinColumn(name = "general_scheme_file_info_id")
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    private FileInfo generalScheme;

    @OneToMany
    @JoinTable(
            name = "general_scheme_photos",
            joinColumns = @JoinColumn(name = "indicator_id"),
            inverseJoinColumns = @JoinColumn(name = "file_info_id")
    )
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    private Set<FileInfo> generalSchemePhotos;

    @OneToOne
    @JoinColumn(name = "production_zone_scheme_file_info_id")
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    private FileInfo productionZoneScheme;

    @OneToMany
    @JoinTable(
            name = "production_zone_scheme_photos",
            joinColumns = @JoinColumn(name = "indicator_id"),
            inverseJoinColumns = @JoinColumn(name = "file_info_id")
    )
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    private Set<FileInfo> productionZoneSchemePhotos;

    private Boolean isImportSubstitutionNeeded;
    @Size(max = 400)
    @Column(length = 400)
    private String equipmentType;
    private Boolean isNdtEquipmentUsed;
    @Size(max = 400)
    @Column(length = 400)
    private String ndtEquipmentType;
    @Size(max = 400)
    @Column(length = 400)
    private String ndtDictionaryName;

    // Новое

    // Только для "размещения"
    @Digits(integer = 20, fraction = 3)
    private Double placingArea;
    @Digits(integer = 20, fraction = 3)
    private Double projectHeight;

    private Boolean hasMineSurveying;
    @Size(max = 400)
    @Column(length = 400)
    private String mineSurveyingOrganizationName;
    @Size(max = 12)
    @Column(length = 12)
    private String mineSurveyingOrganizationInn;

    @OneToOne
    @JoinColumn(name = "mine_surveying_conclusion_file_info_id")
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    private FileInfo mineSurveyingConclusion;

//    @Digits(integer = 20, fraction = 0)
    private Integer mapCount;
    @OneToMany
    @JoinTable(
            name = "indicator_placing_map",
            joinColumns = @JoinColumn(name = "indicator_id"),
            inverseJoinColumns = @JoinColumn(name = "placing_map_id")
    )
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Set<PlacingMap> maps;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waterproofing_type_reference_id")
    private Reference waterproofingType;
//    @Size(max = 400)
//@Column(length = 400)
    private String otherWaterproofingType;

    @Size(max = 400)
    @Column(length = 400)
    private String relief;
    @Digits(integer = 20, fraction = 2)
    private Double groundwaterDepth;
    private Boolean floodingPossibility;

    private Boolean hasWasteCompactor;
    @Size(max = 400)
    @Column(length = 400)
    private String wasteCompactorType;
    @Digits(integer = 20, fraction = 2)
    private Double wasteCompactorMass;

    @OneToMany
    @JoinTable(
            name = "indicator_waste_compactor_photos",
            joinColumns = @JoinColumn(name = "indicator_id"),
            inverseJoinColumns = @JoinColumn(name = "file_info_id")
    )
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    private Set<FileInfo> wasteCompactorPhotos;

    // Утилизация или нейтрализация
    @Size(max = 400)
    @Column(length = 400)
    private String techProcessDescription;

    private Boolean hasTemporaryWasteStorage;
    @Digits(integer = 20, fraction = 3)
    private Double temporaryWasteStorageArea;
    @Size(max = 400)
    @Column(length = 400)
    private String temporaryWasteStorageWasteTypes;

    // Только для утилизации
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilization_type_reference_id")
    private Reference utilizationType;

    // Только для нейтрализации
    private Boolean hasThermalRecycling;
    @Digits(integer = 20, fraction = 3)
    private Double thermalRecyclingPower;
    private Boolean hasRecycling;
    @Digits(integer = 20, fraction = 3)
    private Double recyclingPower;

}
