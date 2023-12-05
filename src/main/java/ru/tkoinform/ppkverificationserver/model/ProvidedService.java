package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@Audited
public class ProvidedService extends UuidEntity {
    private String name;
    private Double volume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "products_info_id", insertable = false, updatable = false)
    private ProductsInfo productsInfo;
}
