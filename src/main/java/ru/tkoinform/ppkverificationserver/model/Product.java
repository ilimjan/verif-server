package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Set;

@Entity
@Audited
@Getter
@Setter
public class Product extends UuidEntity {

    private String name;
    private Double volume;

    @OneToMany
    @JoinTable(
            name = "product_photos",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "file_info_id")
    )
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    private Set<FileInfo> photos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "products_info_id", insertable = false, updatable = false)
    private ProductsInfo productsInfo;

}
