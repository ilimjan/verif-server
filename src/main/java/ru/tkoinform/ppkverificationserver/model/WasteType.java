package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Audited
@Entity
public class WasteType extends UuidEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waste_info_id", insertable = false, updatable = false)
    private WasteInfo wasteInfo;

    @ManyToOne
    @JoinColumn(name = "waste_type_reference_id")
    private Reference wasteType;

    /*@ManyToOne
    @JoinColumn(name = "danger_type_reference_id")
    private Reference dangerType;*/


//    @ManyToOne
//    @JoinColumn(name = "danger_type_reference_id")
//    private Reference dangerType;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "waste_danger",
            joinColumns = @JoinColumn(name = "waste_type_reference_id"),
            inverseJoinColumns = @JoinColumn(name = "danger_type_reference_id"))
    private Set<Reference> dangerType;
}
