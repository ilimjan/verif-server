package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Audited
@Entity
public class Equipment extends UuidEntity implements Comparable<Equipment> {

    @ManyToOne
    @JoinColumn(name = "type_reference_id")
    private Reference type;
    @ManyToOne
    @JoinColumn(name = "kind_reference_id")
    private Reference kind;
    private String otherType;
    @Size(max = 400)
    @Column(length = 400)
    private String name;
    private Integer count;
    @Size(max = 400)
    @Column(length = 400)
    private String brand;
    @Size(max = 400)
    @Column(length = 400)
    private String manufacturer;
    @Digits(integer = 20, fraction = 3)
    private Double length;
    @Digits(integer = 20, fraction = 3)
    private Double width;
    @Digits(integer = 20, fraction = 3)
    private Double speed;
    @Size(max = 400)
    @Column(length = 400)
    private String loadingMechanism;
    @Digits(integer = 20, fraction = 3)
    private Double wasteWidth;
    private Integer sortingPostsCount;
    private Date created;
    @Digits(integer = 20, fraction = 3)
    private Double projectPower;
    @Size(max = 400)
    @Column(length = 400)
    private String processDescription;
    @Size(max = 400)
    @Column(length = 400)
    private String production;

    @OneToMany
    @JoinTable(
            name = "equipment_photos",
            joinColumns = @JoinColumn(name = "equipment_id"),
            inverseJoinColumns = @JoinColumn(name = "file_info_id")
    )
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    private Set<FileInfo> photos;

    @OneToOne
    @JoinColumn(name = "passport_file_info_id")
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    private FileInfo passport;

    @OneToMany
    @JoinTable(
            name = "equipment_passport_photos",
            joinColumns = @JoinColumn(name = "equipment_id"),
            inverseJoinColumns = @JoinColumn(name = "file_info_id")
    )
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    private Set<FileInfo> passportPhotos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "infrastructure_object_id", insertable = false, updatable = false)
    private InfrastructureObject infrastructureObject;

    @Override
    public int compareTo(Equipment o) {
        if (this.created != null && o.created == null) {
            return -1;
        }
        if (this.created == null && o.created != null) {
            return 1;
        }
        if (this.created != null && o.created != null) {
            return this.created.compareTo(o.created);
        }
        return 0;
    }
}
