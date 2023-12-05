package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Audited
@Getter
@Setter
public class ProductsInfo extends UuidEntity {

    private Double totalCountPerYear;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "products_info_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Set<Product> products;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "products_info_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Set<ProvidedService> providedServices;
}
