package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import ru.tkoinform.ppkverificationserver.model.enums.ReferenceType;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Audited
@Entity
public class Reference extends UuidEntity {

    @Enumerated(EnumType.STRING)
    private ReferenceType type;

    private String value;

    private Date finishTime;

    private Integer index;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Reference parent;

    // Неудаляемый
    private Boolean indelible;

    @Column(columnDefinition = "boolean default false")
    private Boolean isUpdated = false;

    private Integer regionId;
}
