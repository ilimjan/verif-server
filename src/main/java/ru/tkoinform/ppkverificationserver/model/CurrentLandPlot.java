package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Audited
@Entity
public class CurrentLandPlot extends UuidEntity implements Comparable<CurrentLandPlot> {
    @Size(max = 32)
    @Column(length = 32)
    private String zoneNumber;

    @OneToMany(fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @JoinColumn(name = "current_land_plot_id")
    private List<LandPlotPoint> points;

    @OneToMany(fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @JoinColumn(name = "current_land_plot_imported_id")
    private List<LandPlotPointImported> importedPoints;

    @Digits(integer = 20, fraction = 2)
    private Double averageInnacuracy;
    private String cadastralNumber;

    @OneToOne
    @JoinColumn(name = "scheme_file_info_id")
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    private FileInfo scheme;

    @ManyToOne
    @JoinColumn(name = "coordinates_system_reference_id")
    private Reference coordinatesSystem;

    //@ManyToOne
    //@JoinColumn(name = "land_description_reference_id")
    //private Reference description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "infrastructure_object_id", insertable = false, updatable = false)
    private InfrastructureObject infrastructureObject;

    private Date created;

    @Override
    public int compareTo(CurrentLandPlot o) {
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
