package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import java.util.Set;

@Getter
@Setter
@Audited
@Entity
public class SecondaryResources extends UuidEntity {

    @Digits(integer = 20, fraction = 2)
    private Double extractPercent;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "secondary_resource_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Set<SecondaryResourceType> types;

}
