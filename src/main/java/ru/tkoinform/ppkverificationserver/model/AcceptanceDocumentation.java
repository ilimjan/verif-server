package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Entity
@Audited
public class AcceptanceDocumentation extends UuidEntity {
    private Boolean hasFinancialJustification;
    @OneToOne
    @JoinColumn(name = "financial_justification_file_info_id")
    @Cascade({CascadeType.MERGE})
    private FileInfo financialJustification;
    @Size(max = 400)
    @Column(length = 400)
    private String noJustificationReason;
    //private Date justificationCompletedDate;
    private Boolean hasFinModel;
    @Size(max = 400)
    @Column(length = 400)
    private String noFinModelReason;
    @OneToOne
    @JoinColumn(name = "fin_model_file_info_id")
    @Cascade({CascadeType.MERGE})
    private FileInfo finModel;
    //private Date finModelCompletedDate;
    private Boolean hasTechnicalSpecification;
    @OneToOne
    @JoinColumn(name = "tech_spec_file_info_id")
    @Cascade({CascadeType.MERGE})
    private FileInfo technicalSpecification;
    //private Date technicalSpecificationCompletedDate;
    @Size(max = 400)
    @Column(length = 400)
    private String noTechnicalSpecificationReason;
    private Boolean hasProjectDocumentation;
    @OneToOne
    @JoinColumn(name = "project_documentation_file_info_id")
    @Cascade({CascadeType.MERGE})
    private FileInfo projectDocumentation;
    @Size(max = 400)
    @Column(length = 400)
    private String noProjectDocumentationReason;
    //private Date projectDocumentationCompletedDate;
    //private Boolean hasProjectOrganization;
    @Size(max = 400)
    @Column(length = 400)
    private String projectOrganizationName;
    @Size(max = 12)
    @Column(length = 12)
    private String projectOrganizationInn;
    private Boolean hasEnvironmentalImpactResearch;
    @OneToOne
    @JoinColumn(name = "env_impact_research_file_info_id")
    @Cascade({CascadeType.MERGE})
    private FileInfo environmentalImpactResearchResults;
    @Size(max = 400)
    @Column(length = 400)
    private String noResearchReason;
    //private Date researchCompletedDate;
    //private Boolean hasConclusion;
    //@OneToOne
    //@JoinColumn(name = "conclusion_file_info_id")
    //@Cascade({CascadeType.MERGE})
    //private FileInfo conclusion;
    //@Size(max = 400)
    //@Column(length = 400)
    //private String noConclusionReason;
    private Boolean publicHearingsCompleted;
    @OneToOne
    @JoinColumn(name = "public_hearings_result_file_info_id")
    @Cascade({CascadeType.MERGE})
    private FileInfo publicHearingsResults;
    @Size(max = 400)
    @Column(length = 400)
    private String noPublicHearingsReason;
    private Boolean hasGovernmentConclusion;
    @OneToOne
    @JoinColumn(name = "government_coclusion_file_info_id")
    @Cascade({CascadeType.MERGE})
    private FileInfo governmentConclusion;
    @Size(max = 400)
    @Column(length = 400)
    private String noGovernmentConclusionReason;
    @Size(max = 400)
    @Column(length = 400)
    private String governmentConclusionIssuer;
    private String governmentConclusionNumber;
    private Date governmentConclusionDate;
    private Boolean hasGovernmentEnvironmentalConclusion;
    @OneToOne
    @JoinColumn(name = "government_env_file_info_id")
    @Cascade({CascadeType.MERGE})
    private FileInfo governmentEnvironmentalConclusion;
    @Size(max = 400)
    @Column(length = 400)
    private String noGovernmentEnvironmentalConclusionReason;
    //private Date governmentEnvironmentalExpertisesDate;
    private Boolean hasStateRegistration;
    private String stateRegistrationNumber;
    private Date stateRegistrationDate;
    @Size(max = 400)
    @Column(length = 400)
    private String noStateRegistrationReason;
    @OneToOne
    @JoinColumn(name = "state_registration_file_info_id")
    @Cascade({CascadeType.MERGE})
    private FileInfo stateRegistration;
    private Boolean hasIntegratedEnvironmentalPermission;
    @OneToOne
    @JoinColumn(name = "integrated_env_permission_file_info_id")
    @Cascade({CascadeType.MERGE})
    private FileInfo integratedEnvironmentalPermission;
    @Size(max = 400)
    @Column(length = 400)
    private String noIntegratedEnvironmentalPermissionReason;
    //private Date integratedEnvironmentalPermissionDate;
    private Boolean hasInvestmentProgram;
    @OneToOne
    @JoinColumn(name = "investment_program_file_info_id")
    @Cascade({CascadeType.MERGE})
    private FileInfo investmentProgram;
    @Size(max = 400)
    @Column(length = 400)
    private String noInvestmentProgramReason;
    //private Date investmentProgramDate;
    private Boolean hasEnvironmentControlProgram;
    @OneToOne
    @JoinColumn(name = "env_control_program_file_info_id")
    @Cascade({CascadeType.MERGE})
    private FileInfo environmentControlProgram;
    @Size(max = 400)
    @Column(length = 400)
    private String noEnvironmentControlProgramReason;
    //private Date environmentControlProgramDate;
    private Boolean hasSanPinConclusion;
    @OneToOne
    @JoinColumn(name = "san_pin_cocnlusion_file_info_id")
    @Cascade({CascadeType.MERGE})
    private FileInfo sanPinConclusion;
    @Size(max = 400)
    @Column(length = 400)
    private String noSanPinConclusionReason;
    //private Date sanPinConclusionDate;
    private Boolean hasLicence;
    private Date licenceStartDate;
    private String licenceNumber;
    @Size(max = 400)
    @Column(length = 400)
    private String licenceAuthor;
    @Size(max = 400)
    @Column(length = 400)
    private String noLicenceReason;
    private Date licenceWillBeGivenDate;
    private Boolean isInRegistry;

    //private String groroNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_reference_id")
    private Reference groro;

    @Size(max = 400)
    @Column(length = 400)
    private String groroRequestRequisites;
    @Size(max = 400)
    @Column(length = 400)
    private String noGroroReason;
    //private Date includeInGroroDate;
    private Boolean isInTerritorialScheme;
    @Size(max = 4000)
    @Column(length = 4000)
    private String territorialSchemeDocumentRequisites;
    private Boolean hasSpecialInvestigations;
    @Size(max = 400)
    @Column(length = 400)
    private String specialInvestigator;
    private Date specialInvestigationsDate;
    private Boolean isOnBorder;
    @Size(max = 400)
    @Column(length = 400)
    private String territorialSchemeLink;
    @Size(max = 400)
    @Column(length = 400)
    private String territorialSchemeModelLink;

    private Boolean hasSamples;

    @OneToOne
    @JoinColumn(name = "samples_results_file_info_id")
    @Cascade({CascadeType.MERGE})
    private FileInfo samplesResults;

    @ManyToOne
    @JoinColumn(name = "object_category_reference_id")
    private Reference objectCategory;

    @ManyToOne
    @JoinColumn(name = "danger_type_reference_id")
    private Reference dangerType;
}
