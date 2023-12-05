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
@Audited
@Entity
public class TechnicalSurveyFile extends UuidEntity {
    @OneToOne
    @JoinColumn(name = "file_info_id")
    @Cascade({CascadeType.MERGE})
    private FileInfo file;
    private Date creationDate;
    @Size(max = 17)
    @Column(length = 17)
    private String latitude;
    @Size(max = 17)
    @Column(length = 17)
    private String longitude;
    @OneToOne
    @JoinColumn(name = "signed_act_file_info_id")
    @Cascade({CascadeType.MERGE})
    private FileInfo signedAct;

    @ManyToOne
    @JoinColumn(name = "technical_survey_id")
    private TechnicalSurvey technicalSurvey;
}
