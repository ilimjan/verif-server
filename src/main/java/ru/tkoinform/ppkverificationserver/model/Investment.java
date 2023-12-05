package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

@Getter
@Setter
@Audited
@Entity
public class Investment extends UuidEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "infrastructure_object_id", insertable = false, updatable = false)
    private InfrastructureObject infrastructureObject;

    //private boolean hasInvestor;
    @Size(max = 400)
    @Column(length = 400)
    private String name;
    @Size(max = 12)
    @Column(length = 12)
    private String inn;
    @Size(max = 9)
    @Column(length = 9)
    private String kpp;
    @Size(max = 13)
    @Column(length = 13)
    private String ogrn;
    //@Size(max = 15)
    //@Column(length = 15)
    //private String ogrnip;
    @Size(max = 400)
    @Column(length = 400)
    private String legalAddress;
    @Size(max = 400)
    @Column(length = 400)
    private String actualAddress;
    @Size(max = 20)
    @Column(length = 20)
    private String phone;
    @Size(max = 40)
    @Column(length = 40)
    private String website;
    @Size(max = 40)
    @Column(length = 40)
    private String email;
    @Size(max = 40)
    @Column(length = 40)
    private String directorFio;
    @Size(max = 400)
    @Column(length = 400)
    private String representatioveFio;
    @Size(max = 400)
    @Column(length = 400)
    private String representatiovePosition;
    @Size(max = 400)
    @Column(length = 400)
    private String experience;
    @Digits(integer = 20, fraction = 2)
    private Double fullInvestmentAmount;
    @Digits(integer = 20, fraction = 2)
    private Double personalInvestmentAmount;
    @Digits(integer = 20, fraction = 2)
    private Double plannedPaybackPeriod;
    @Digits(integer = 20, fraction = 2)
    private Double requiredGrossRevenue;
    //private Boolean hasAgreementOfIntent;
    @Size(max = 4000)
    @Column(length = 4000)
    private String agreementRequisites;
    @Size(max = 4000)
    @Column(length = 4000)
    private String risks;
    @Size(max = 4000)
    @Column(length = 4000)
    private String competitiveAdvantages;
    @Size(max = 20)
    @Column(length = 20)
    private String mobile;
}
