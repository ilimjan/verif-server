package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportIgnored;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ExportName(name = "Координаты границ земельного участка, на котором расположен объект")
public class CurrentLandPlotDto extends UuidEntityDto implements Comparable<CurrentLandPlotDto> {

    @ExportName(name = "Номер зоны")
    private String zoneNumber;
    @ExportName(name = "Средняя квадратическая погрешность определения координат характерных точек границ земельного участка")
    private Double averageInnacuracy;
    @ExportName(name = "Кадастровый номер земельного участка, на котором расположен объект")
    private String cadastralNumber;

    private List<LandPlotPointDto> points = new ArrayList<LandPlotPointDto>(); //+
    private List<LandPlotPointImportedDto> importedPoints = new ArrayList<LandPlotPointImportedDto>();

    @ExportIgnored
    private FileInfoDto scheme;
    @ExportName(name = "Система координат")
    private ReferenceDto coordinatesSystem;
    //private ReferenceDto description;

    @ExportIgnored
    private Date created;

    @Override
    public int compareTo(CurrentLandPlotDto o) {
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
