package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;

@Entity
@Audited
@Getter
@Setter
public class SecondaryResourceType extends UuidEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_reference_id")
    private Reference reference;
    private String otherSecondaryResourceTypeName;
    @Digits(integer = 20, fraction = 2)
    private Double percent;

}
