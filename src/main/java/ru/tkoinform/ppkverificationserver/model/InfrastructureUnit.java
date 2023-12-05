package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Audited
@Entity
public class InfrastructureUnit extends UuidEntity {

    // Количество
    @Digits(integer = 20, fraction = 2)
    private Integer count;

    // Марка
    @Size(max = 400)
    @Column(length = 400)
    private String brand;

    // Производитель
    @Size(max = 400)
    @Column(length = 400)
    private String manufacturer;

    // Длина
    @Digits(integer = 20, fraction = 2)
    private Double length;

    // Фотограции
    @OneToMany
    @JoinTable(
            name = "infrastructure_unit_photos",
            joinColumns = @JoinColumn(name = "infrastructure_unit_id"),
            inverseJoinColumns = @JoinColumn(name = "file_info_id")
    )
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    private Set<FileInfo> photos;

    // Тип
    @ManyToOne
    @JoinColumn(name = "type_reference_id")
    private Reference type;

    private String otherType;

    // Мощность
//    @Digits(integer = 20, fraction = 2)
    @Digits(integer = 20, fraction = 3)
    private Double capacity;

    // Паспорт
    @OneToOne
    @JoinColumn(name = "passport_file_info_id")
    @Cascade({CascadeType.MERGE})
    private FileInfo passport;

    @OneToMany
    @JoinTable(
            name = "passport_photos",
            joinColumns = @JoinColumn(name = "infrastructure_unit_id"),
            inverseJoinColumns = @JoinColumn(name = "file_info_id")
    )
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    private Set<FileInfo> passportPhotos;

    // Схема
    @OneToOne
    @JoinColumn(name = "scheme_file_info_id")
    @Cascade({CascadeType.MERGE})
    private FileInfo scheme;

    @OneToMany
    @JoinTable(
            name = "scheme_photos",
            joinColumns = @JoinColumn(name = "infrastructure_unit_id"),
            inverseJoinColumns = @JoinColumn(name = "file_info_id")
    )
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    private Set<FileInfo> schemePhotos;

    // Назначение
    @Size(max = 400)
    @Column(length = 400)
    private String purpose;

    // Функции
    @Size(max = 400)
    @Column(length = 400)
    private String functions;
}
