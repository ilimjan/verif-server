package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Entity
@Audited
public class BorderLandPlot extends UuidEntity {
    private boolean hasLandPlotForProjectRealization;
    private Date compliancePeriod;
    private boolean hasUrbanDevelopmentPlan;
    private boolean hasSanityProtectionZone;
    private Double sanitaryProtectionZoneSize;
    @OneToOne
    @JoinColumn(name = "egrp_file_info_id")
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    private FileInfo egrp;
    @Size(max = 400)
    @Column(length = 400)
    private String rightsGivingDocumentName;
    private Date rightsGivingDocumentStartDate;
    private String rigthsGivingDocumentNumber;
    @OneToOne
    @JoinColumn(name = "rights_giving_document_file_info_id")
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    private FileInfo rigthsGivingDocument;
    @Digits(integer = 20, fraction = 3)
    private Double landPotArea;
    @Digits(integer = 20, fraction = 3)
    private Double suitableForExonomicActivitiesLandPloArea;
    private Boolean hasPowerSupply;
    @Digits(integer = 20, fraction = 3)
    private Double dedicatedElecricalPower;
    private Date powerSupplyConnectionDate;
    private Boolean hasWaterSupply;
    @Digits(integer = 20, fraction = 3)
    private Double waterSupplyPower;
    private Date watterSupplyConnectionDate;
    private Boolean hasGasSupply;
    @Digits(integer = 20, fraction = 3)
    private Double gasSupplyPower;
    private Date gasSupplyConnectionDate;
    private Boolean hasHeatPipelinesConnection;
    private Date heatPipelinesConnectionStartDate;
    private Boolean hasAutomobileAccessRoads;
    private Date automobileAccessRoadsConnectionDate;
    private Boolean hasRailway;
    @Digits(integer = 20, fraction = 3)
    private Double distanceToNearestRailway;
    @Digits(integer = 20, fraction = 3)
    private Double distanceToNearestTransshipment;
    @Digits(integer = 20, fraction = 3)
    private Double distanceToNearestAirport;
    @Size(max = 400)
    @Column(length = 400)
    private String nearestAirportName;
    @Size(max = 400)
    @Column(length = 400)
    private String nearestSettlementName;
    @Digits(integer = 20, fraction = 3)
    private Double distanceToNearestSettlement;
    @Size(max = 400)
    @Column(length = 400)
    private String address;
    @Digits(integer = 20, fraction = 3)
    private Double area;
    private String cadastralNumber;
    private String buildingCadastralNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "infrastructure_object_id", insertable = false, updatable = false)
    private InfrastructureObject infrastructureObject;

    @ManyToOne
    @JoinColumn(name = "ownership_type_reference_id")
    private Reference ownershipType;

    @ManyToOne
    @JoinColumn(name = "land_category_reference_id")
    private Reference landCategory;

    @ManyToOne
    @JoinColumn(name = "allowed_using_type_reference_id")
    private Reference allowedUsingType;

    //@ManyToOne
    //@JoinColumn(name = "road_type_reference_id")
    //private Reference automobileRoadType;
}
