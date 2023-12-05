package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Digits;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Audited
@Entity
public class Tariff extends UuidEntity {

    @Digits(integer = 20, fraction = 2)
    private Double tariffPerTon;
    //@Digits(integer = 20, fraction = 2)
    //private Double tariffPerMeter;
    private Date tariffStartDate;
    private Date tariffEndDate;

    @OneToMany
    @JoinTable(
            name = "tariff_document_photo",
            joinColumns = @JoinColumn(name = "tariff_id"),
            inverseJoinColumns = @JoinColumn(name = "file_info_id")
    )
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    private Set<FileInfo> tariffDocumentPhotos;

    @OneToOne
    @JoinColumn(name = "tariff_document_file_info_id")
    @Cascade({CascadeType.MERGE})
    private FileInfo tariffDocument;
}
