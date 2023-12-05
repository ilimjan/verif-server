package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Audited
@Entity
public class DataSource extends UuidEntity {

    private String name;
    private Date uploadDate;
    @Size(max = 400)
    @Column(length = 400)
    private String representativeFio;
    @Size(max = 400)
    @Column(length = 400)
    private String representativePosition;
    @ManyToOne
    @JoinColumn(name = "subject_name_reference_id")
    private Reference subjectName;
}
