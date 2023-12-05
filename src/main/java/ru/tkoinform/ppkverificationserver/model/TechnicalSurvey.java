package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import ru.tkoinform.ppkverificationserver.model.enums.TechnicalSurveyStatus;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Audited
@Entity
public class TechnicalSurvey extends UuidEntity {
    @Size(max = 40)
    @Column(length = 40)
    private String roRepresentativeName;
    @Size(max = 100)
    @Column(length = 100)
    private String roRepresentativePosition;
    @Size(max = 40)
    @Column(length = 40)
    private String operatorRepresentativeName;
    @Size(max = 100)
    @Column(length = 100)
    private String operatorRepresentativePosition;
    @Size(max = 100)
    @Column(length = 100)
    private String omsuName;
    @Size(max = 40)
    @Column(length = 40)
    private String omsuRepresentativeName;
    @Size(max = 100)
    @Column(length = 100)
    private String omsuRepresentativePosition;
    @Size(max = 100)
    @Column(length = 100)
    private String otherParticipantName;
    @Size(max = 40)
    @Column(length = 40)
    private String otherParticipantRepresentativeName;
    @Size(max = 100)
    @Column(length = 100)
    private String otherParticipantRepresentativePosition;

    private Date startDate;
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private TechnicalSurveyStatus status;

    @ManyToOne
    @JoinColumn(name = "result_reference_id")
    private Reference result;
    private String otherResult;

    @OneToMany(mappedBy = "technicalSurvey")
    private Set<TechnicalSurveyFile> files;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "infrastructure_object_id", insertable = false, updatable = false)
    private InfrastructureObject infrastructureObject;

}
