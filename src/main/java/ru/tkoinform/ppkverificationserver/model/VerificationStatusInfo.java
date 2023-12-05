package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Entity
@Audited
@Getter
@Setter
public class VerificationStatusInfo extends UuidEntity {

    private boolean isCardCreated;
    private boolean infoByOperator;
    private boolean isFromPpk;
    private boolean isVerifiedByOperator;
    private boolean isFromTerritorialScheme;
    @Size(max = 4000)
    @Column(length = 4000)
    private String comment;

}
