package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;

@Getter
@Setter
@Audited
@Entity
public class WorkSchedule extends UuidEntity {
    //private String scheduleStart;
    //private String scheduleEnd;
    //private String schedule;
    private String scheduleJson;
    private Integer shiftsPerDayCount;
    private Integer daysPerYearCount;
    private Integer workplacesCount;
    private Integer managersCount;
    private Integer workersCount;
}
