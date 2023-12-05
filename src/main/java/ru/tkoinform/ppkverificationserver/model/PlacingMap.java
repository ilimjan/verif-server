package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import java.util.Date;

@Getter
@Setter
@Audited
@Entity
public class PlacingMap extends UuidEntity {

    private Double size;
    private Date period;
}
