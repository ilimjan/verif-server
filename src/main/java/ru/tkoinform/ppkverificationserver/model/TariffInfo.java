package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import java.util.Set;

@Getter
@Setter
@Audited
@Entity
public class TariffInfo extends UuidEntity {
    @Digits(integer = 20, fraction = 2)
    private Double averageYearWasteReceiving;
    @Digits(integer = 20, fraction = 2)
    private Double burial;

    @OneToMany
    @JoinTable(
            name = "tariff_info_tariff",
            joinColumns = @JoinColumn(name = "tariff_info_id"),
            inverseJoinColumns = @JoinColumn(name = "tariff_id")
    )
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Set<Tariff> tariffs;

    /*
    @OneToMany
    @JoinTable(
            name = "tariff_info_operator_tariff",
            joinColumns = @JoinColumn(name = "tariff_info_id"),
            inverseJoinColumns = @JoinColumn(name = "tariff_id")
    )
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Set<Tariff> operatorTariffs;
    */
}
