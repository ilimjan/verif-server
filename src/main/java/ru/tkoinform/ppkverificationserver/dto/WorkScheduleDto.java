package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

@Getter
@Setter
@ExportName(name = "Сведения о графике работы объекта по обращению с ТКО")
public class WorkScheduleDto extends UuidEntityDto {
    //private String scheduleStart;
    //private String scheduleEnd;
    //private String schedule;
    @ExportName(name = "График работы в формате JSON")
    private String scheduleJson;
    @ExportName(name = "Количество рабочих смен в сутки (указывается количество рабочих смен)")
    private Integer shiftsPerDayCount;
    @ExportName(name = "Количество рабочих дней в году (указывается без учёта плановых остановок оборудования)")
    private Integer daysPerYearCount;
    @ExportName(name = "Количество рабочих мест (указывается в количестве человек)")
    private Integer workplacesCount;
    @ExportName(name = "Количество управленческого персонала (указывается в количестве человек)")
    private Integer managersCount;
    @ExportName(name = "Количество производственного персонала (рабочих) (указывается в количестве человек)")
    private Integer workersCount;
}
