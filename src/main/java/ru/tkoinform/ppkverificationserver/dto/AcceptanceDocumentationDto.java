package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportIgnored;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

import java.util.Date;

@Getter
@Setter
@ExportName(name = "Сведения о проектно-разрешительной документации, разрешениях и заключениях на объект")
public class AcceptanceDocumentationDto extends UuidEntityDto {

    @ExportName(name = "Наличие финансово-экономического обоснования проекта")
    private Boolean hasFinancialJustification;
    @ExportIgnored
    private FileInfoDto financialJustification;
    @ExportName(name = "Причина отсутствия финансово-экономического обоснования проекта")
    private String noJustificationReason;
    //private Date justificationCompletedDate;
    @ExportName(name = "Наличие финансовой модели проекта")
    private Boolean hasFinModel;
    @ExportName(name = "Причина отсутствия финансовой модели проекта")
    private String noFinModelReason;
    @ExportIgnored
    private FileInfoDto finModel;
    //private Date finModelCompletedDate;
    @ExportName(name = "Наличие технического задания на проектирование")
    private Boolean hasTechnicalSpecification;
    @ExportIgnored
    private FileInfoDto technicalSpecification;
    //private Date technicalSpecificationCompletedDate;
    @ExportName(name = "Причина отсутствия технического задания на проектирование")
    private String noTechnicalSpecificationReason;
    @ExportName(name = "Наличие проектной документации на проектирование")
    private Boolean hasProjectDocumentation;
    @ExportIgnored
    private FileInfoDto projectDocumentation;
    @ExportName(name = "Причина отсутствия проектной документации на проектирование")
    private String noProjectDocumentationReason;
    //private Date projectDocumentationCompletedDate;
    //private Boolean hasProjectOrganization;
    @ExportName(name = "Наименование проектной организации")
    private String projectOrganizationName;
    @ExportName(name = "ИНН проектной организации")
    private String projectOrganizationInn;
    @ExportName(name = "Наличие проведенных исследований по оценке воздействия на окружающую среду")
    private Boolean hasEnvironmentalImpactResearch;
    @ExportIgnored
    private FileInfoDto environmentalImpactResearchResults;
    @ExportName(name = "Причина отсутствия проведенных исследований по оценке воздействия на окружающую среду")
    private String noResearchReason;
    //private Date researchCompletedDate;
    //private Boolean hasConclusion;
    //private FileInfoDto conclusion;
    //private String noConclusionReason;
    @ExportName(name = "Наличие проведенных публичных слушаний проектной документации")
    private Boolean publicHearingsCompleted;
    @ExportIgnored
    private FileInfoDto publicHearingsResults;
    @ExportName(name = "Причина отсутствия проведенных публичных слушаний проектной документации")
    private String noPublicHearingsReason;
    @ExportName(name = "Наличие заключения Главгосэкспертизы")
    private Boolean hasGovernmentConclusion;
    @ExportIgnored
    private FileInfoDto governmentConclusion;
    @ExportName(name = "Причина отсутствия заключения Главгосэкспертизы")
    private String noGovernmentConclusionReason;
    @ExportName(name = "Наименование учреждения, выдавшего заключение Главгосэкспертизы")
    private String governmentConclusionIssuer;
    @ExportName(name = "Номер заключения Главгосэкспертизы")
    private String governmentConclusionNumber;
    @ExportName(name = "Дата заключения Главгосэкспертизы")
    private Date governmentConclusionDate;
    @ExportName(name = "Наличие заключения государственной экологической экспертизы проектной документации")
    private Boolean hasGovernmentEnvironmentalConclusion;
    @ExportIgnored
    private FileInfoDto governmentEnvironmentalConclusion;
    @ExportName(name = "Причина отсутствия заключения государственной экологической экспертизы проектной документации")
    private String noGovernmentEnvironmentalConclusionReason;
    //private Date governmentEnvironmentalExpertisesDate;
    @ExportName(name = "Наличие постановки на государственный учет объекта оказывающего негативное воздействие на окружающую среду")
    private Boolean hasStateRegistration;
    @ExportName(name = "Причина отсутствия постановки на государственный учет объекта оказывающего негативное воздействие на окружающую среду")
    private String noStateRegistrationReason;
    @ExportName(name = "Номер выдачи свидетельства о постановке на государственный учет объекта оказывающего негативное воздействие на окружающую среду")
    private String stateRegistrationNumber;
    @ExportName(name = "Дата выдачи свидетельства о постановке на государственный учет объекта оказывающего негативное воздействие на окружающую среду")
    private Date stateRegistrationDate;
    @ExportIgnored
    private FileInfoDto stateRegistration;
    @ExportName(name = "Наличие комплексного экологического разрешения, декларации и иных документов в части нормирования отходов")
    private Boolean hasIntegratedEnvironmentalPermission;
    @ExportIgnored
    private FileInfoDto integratedEnvironmentalPermission;
    @ExportName(name = "Причина отсутствия комплексного экологического разрешения, декларации и иных документов в части нормирования отходов")
    private String noIntegratedEnvironmentalPermissionReason;
    //private Date integratedEnvironmentalPermissionDate;
    @ExportName(name = "Наличие утвержденной инвестиционной и производственной программы в области обращения с ТКО")
    private Boolean hasInvestmentProgram;
    @ExportIgnored
    private FileInfoDto investmentProgram;
    @ExportName(name = "Причина отсутствия утвержденной инвестиционной и производственной программы в области обращения с ТКО")
    private String noInvestmentProgramReason;
    //private Date investmentProgramDate;
    @ExportName(name = "Наличие программы производственного экологического контроля (ПЭК) и отчета об организации и о результатах осуществления ПЭК")
    private Boolean hasEnvironmentControlProgram;
    @ExportIgnored
    private FileInfoDto environmentControlProgram;
    @ExportName(name = "Причина отсутствия программы производственного экологического контроля (ПЭК) и отчета об организации и о результатах осуществления ПЭК")
    private String noEnvironmentControlProgramReason;
    //private Date environmentControlProgramDate;
    @ExportName(name = "Наличие санитарно-эпидемиологического заключения о соответствии санитарным правилам зданий, строений, сооружений, помещений, оборудования, которые планируется использовать для выполнения заявленных работ, составляющих деятельность по обращению с отходами")
    private Boolean hasSanPinConclusion;
    @ExportIgnored
    private FileInfoDto sanPinConclusion;
    @ExportName(name = "Причина отсутствия санитарно-эпидемиологического заключения")
    private String noSanPinConclusionReason;
    //private Date sanPinConclusionDate;
    @ExportName(name = "Наличие лицензии на осуществление деятельности по сбору, использованию, обезвреживанию, транспортировке, размещению отходов I - IV классов опасности")
    private Boolean hasLicence;
    @ExportName(name = "Дата выдачи лицензии на деятельность по сбору, использованию, обезвреживанию, транспортировке, размещению отходов I - IV классов опасности (при наличии лицензии)")
    private Date licenceStartDate;
    @ExportName(name = "Номер лицензии на деятельность по сбору, использованию, обезвреживанию, транспортировке, размещению отходов I - IV классов опасности (при наличии лицензии)")
    private String licenceNumber;
    @ExportName(name = "Наименование органа, выдавшего лицензию на деятельность по сбору, использованию, обезвреживанию, транспортировке, размещению отходов I - IV классов опасности (при наличии лицензии)")
    private String licenceAuthor;
    @ExportName(name = "Причина отсутствия лицензии на деятельность по сбору")
    private String noLicenceReason;
    @ExportName(name = "Срок получения лицензии на осуществление деятельности по сбору, использованию, обезвреживанию, транспортировке, размещению отходов I - IV классов опасности")
    private Date licenceWillBeGivenDate;
    @ExportName(name = "Наличие включения объекта в Государственный реестр объектов размещения отходов (ГРОРО)")
    private Boolean isInRegistry;
    //private String groroNumber;
    @ExportName(name = "Номер объекта в ГРОРО")
    private ReferenceDto groro;
    @ExportName(name = "Реквизиты приказа уполномоченного органа о включении объекта в ГРОРО")
    private String groroRequestRequisites;
    @ExportName(name = "Причина отсутствия объекта в ГРОРО")
    private String noGroroReason;
    //private Date includeInGroroDate;
    @ExportName(name = "Наличие объекта в территориальной схеме обращения с отходами")
    private Boolean isInTerritorialScheme;
    @ExportName(name = "Реквизиты документа, закрепляющего включение объекта в территориальную схему")
    private String territorialSchemeDocumentRequisites;
    @ExportName(name = "Ссылка на раздел (страницу) действующей территориальной схемы, в котором упомянут объект")
    private String territorialSchemeLink;
    @ExportName(name = "Ссылка на электронную модель территориальной схемы обращения с отходами")
    private String territorialSchemeModelLink;
    @ExportName(name = "Наличие специальных (геологических, гидрологических и иных) изысканий (исследований)")
    private Boolean hasSpecialInvestigations;
    @ExportName(name = "Наименование органа, организации, проводившей специальные изыскания (исследования)")
    private String specialInvestigator;
    @ExportName(name = "Период проведения специальных (геологических, гидрологических и иных) изысканий (исследований)")
    private Date specialInvestigationsDate;
    @ExportName(name = "Земельный участок располагается в границах населенных пунктов, лесопарковых, курортных, лечебно-оздоровительных, рекреационных зон, а также водоохранных зон, на водосборных площадях подземных водных объектов, которые используются в целях питьевого и хозяйственно-бытового водоснабжения, в местах залегания полезных ископаемых и ведения горных работ")
    private Boolean isOnBorder;

    @ExportName(name = "Наличие результатов проб воды, воздуха, почвы")
    private Boolean hasSamples;
    @ExportIgnored
    private FileInfoDto samplesResults;

    @ExportName(name = "Категория и код объекта о постановке на государственный учет")
    private ReferenceDto objectCategory;
    @ExportName(name = "Класс опасности предприятия (указывается в соответствии с Федеральным законом от 21 июля 1997 г. № 116-ФЗ «О промышленной безопасности опасных производственных объектов»)")
    private ReferenceDto dangerType;
}
