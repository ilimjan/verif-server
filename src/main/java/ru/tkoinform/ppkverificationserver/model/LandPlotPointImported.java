package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.validation.constraints.Digits;

@Getter
@Setter
@Entity
@Audited
public class LandPlotPointImported extends UuidEntity {
    private String number;
    @Digits(integer = 20, fraction = 2)
    private String x;
    @Digits(integer = 20, fraction = 2)
    private String y;
}
