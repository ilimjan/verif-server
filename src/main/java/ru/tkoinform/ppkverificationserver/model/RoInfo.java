package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Audited
@Entity
public class RoInfo extends UuidEntity {

    private UUID roId;
    @Size(max = 4000)
    @Column(length = 4000)
    private String name;
    @Size(max = 12)
    @Column(length = 12)
    private String inn;
    //private Date startDate;
    //private Date endDate;
    //private Double pricingPerTonn;
    //private Double pricingPerCoub;
    private String zoneName;

    //TODO: тут фиас должен быть, вроде
    //@ManyToOne
    //@JoinColumn(name = "municipal_name_reference_id")
    //private Reference municipalName;

    //@ManyToOne
    //@JoinColumn(name = "oktmo_number_reference_id")
    //private Reference oktmoNumber;

}
