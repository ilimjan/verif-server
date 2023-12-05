package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.envers.Audited;
import ru.tkoinform.ppkverificationserver.model.enums.ObjectFlowStatus;
import ru.tkoinform.ppkverificationserver.model.enums.ObjectSourceInformation;
import ru.tkoinform.ppkverificationserver.model.enums.ObjectUpdateSource;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Audited
public class InfrastructureObject extends UuidEntity {

    private UUID federalSchemeId;
    private String federalSourceId;
    @Size(max = 4000)
    @Column(length = 4000)
    private String name;
    // Не нужен, дублируется, уже есть в другом разделе
    //private Double power;
    @Size(max = 4000)
    @Column(length = 4000)
    private String ownerName;
    @Size(max = 12)
    @Column(length = 12)
    private String ownerInn;
    @Size(max = 4000)
    @Column(length = 4000)
    private String addressDescription;
    @Size(max = 17)
    @Column(length = 17)
    private String latitude;
    @Size(max = 17)
    @Column(length = 17)
    private String longitude;
    private Date constructionStartDate;
    private Date exploitationStartDate;
    private String exploitationStartActNumber;
    private Double exploitationPeriod;
    private Date exploitationEndDate;

    @OneToOne
    @JoinColumn(name = "exploitaton_act_file_info_id")
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    private FileInfo exploitationAct;

    private Boolean isInFederalOrRegionalPrograms;
    @Size(max = 4000)
    @Column(length = 4000)
    private String programs;
    private Boolean hasSimilarObjectsInSubject;
    //private Integer similarObjectsCount;
    @Size(max = 4000)
    @Column(length = 4000)
    private String currentAndPlannedSimilarObjects;
    //private boolean isVerified;

    @Enumerated(EnumType.STRING)
    private ObjectFlowStatus flowStatus;

    @Enumerated(EnumType.STRING)
    private ObjectSourceInformation sourceInformation;

    @Enumerated(EnumType.STRING)
    private ObjectUpdateSource updateSource;

    private Date flowStatusChanged;

    @Size(max = 1000)
    @Column(length = 1000)
    private String statusChangingReason;

    @ElementCollection
    private Set<String> verifiedFields;

    @OneToMany(fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @JoinColumn(name = "infrastructure_object_id")
    private List<WasteCategoryInfo> wasteCategoryInfos;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verification_status_info_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private VerificationStatusInfo verificationStatusInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_reference_id")
    private Reference type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tko_object_type_reference_id")
    private Reference tkoObjectType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waste_management_type_reference_id")
    private Reference wasteManagementType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fias_address_id")
    private FiasAddress fiasAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Operator operator;

    /*
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "investment_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Investment investment;
    */
    @OneToMany(fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @JoinColumn(name = "infrastructure_object_id")
    private Set<Investment> investments;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cost_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Cost cost;

    @OneToMany(fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @JoinColumn(name = "infrastructure_object_id")
    private Set<BorderLandPlot> borderLandPlots;

    /*
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_land_plot_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private CurrentLandPlot currentLandPlot;
    */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "infrastructure_object_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Set<CurrentLandPlot> currentLandPlots;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acceptace_documentation_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private AcceptanceDocumentation acceptanceDocumentation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_schedule_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private WorkSchedule workSchedule;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tariff_info_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private TariffInfo tariffInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indicator_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Indicator indicator;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waste_info_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private WasteInfo wasteInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private InfrastructureObjectInfo infrastructureObjectInfo;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Set<Equipment> equipment;
//    @Digits(integer = 20, fraction = 0)
    private Integer equipmentCount;
    private Boolean hasCoreEquipment;
    private Boolean hasAuxiliaryEquipment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secondary_resources_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private SecondaryResources secondaryResources;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ro_info_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private RoInfo roInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "infrastructure_object_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private TechnicalSurvey technicalSurvey;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "data_source_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private DataSource dataSource;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "products_info_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private ProductsInfo productsInfo;

    @ManyToOne
    @JoinColumn(name = "status_reference_id")
    private Reference status;
    private String otherObjectStatusName;

    @ManyToOne
    @JoinColumn(name = "federal_district_reference_id")
    private Reference federalDistrictName;

    @ManyToOne
    @JoinColumn(name = "subject_name_reference_id")
    private Reference subjectName;

    @ManyToOne
    @JoinColumn(name = "subject_area_name_reference_id")
    private Reference subjectAreaName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "infrastructure_object_category",
              joinColumns = @JoinColumn(name = "infrastructure_object_id"),
              inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Reference> category;


    //Дата ревизии до
    private Date revisionBefore;

    private String regionId;

    @Transient
    private String revisionType;

    @Transient
    private String revisionDateTime;

    @Transient
    private String revisionUserName;
    
    private Date changeDate;
    //@ManyToOne
    //@JoinColumn(name = "category_reference_id")
    //private Reference category;
}
