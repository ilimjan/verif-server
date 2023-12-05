package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@Audited
@Entity
public class Operator extends UuidEntity {
    private String name;
    private String inn;
    private String kpp;
    private String ogrn;
    //private String ogrnip;
    private String legalAddress;
    private String actualAddress;
    private String phone;
    private String email;
    private String directorFio;
    private String representativeOfOperatorFio;
    private String representativeOfOperatorPosition;
    private String mobile;

    @OneToMany(mappedBy = "operator", fetch = FetchType.LAZY)
    private Set<InfrastructureObject> infrastructureObject;
}
