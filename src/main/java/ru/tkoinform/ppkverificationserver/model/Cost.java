package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.validation.constraints.Digits;

@Getter
@Setter
@Audited
@Entity
public class Cost extends UuidEntity {
    @Digits(integer = 20, fraction = 2)
    private Double total;
    @Digits(integer = 20, fraction = 2)
    private Double equipment;
    @Digits(integer = 20, fraction = 2)
    private Double russianEquipment;
    @Digits(integer = 20, fraction = 2)
    private Double installation;
    @Digits(integer = 20, fraction = 2)
    private Double russianInstallation;
}
