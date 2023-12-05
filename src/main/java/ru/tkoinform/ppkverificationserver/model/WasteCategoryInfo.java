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
public class WasteCategoryInfo extends UuidEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waste_category_reference_id")
    private Reference wasteCategory;
    @Digits(integer = 20, fraction = 3)
    private Double count;

}
