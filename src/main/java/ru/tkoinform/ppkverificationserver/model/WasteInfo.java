package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Digits;
import java.util.Set;

@Getter
@Setter
@Audited
@Entity
public class WasteInfo extends UuidEntity {

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "waste_info_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @OrderBy("id") // TODO: Не работает
    private Set<WasteType> wasteTypes;

    @Digits(integer = 20, fraction = 3)
    private Double wasteCount;
    //private Double wastePrecentage;
    //private String wasteDirection;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "waste_info_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Set<WasteDirection> wasteDirections;

    @Digits(integer = 20, fraction = 2)
    private Double wasteThisYear;
    @Digits(integer = 20, fraction = 2)
    private Double wastePreviousYear;

    @Digits(integer = 20, fraction = 3)
    private Double vmrThisYear;
    @Digits(integer = 20, fraction = 3)
    private Double vmrPreviousYear;
    @Digits(integer = 20, fraction = 2)
    private Double vmrCost;
}
