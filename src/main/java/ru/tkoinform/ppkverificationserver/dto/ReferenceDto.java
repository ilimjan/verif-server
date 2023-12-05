package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportIgnored;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;
import ru.tkoinform.ppkverificationserver.dto.base.LabeledEnumDto;
import ru.tkoinform.ppkverificationserver.model.Reference;

import java.util.Date;

@Getter
@Setter
public class ReferenceDto extends UuidEntityDto {

    @ExportName(name = "Тип")
    //@ExportIgnored
    private LabeledEnumDto type;
    @ExportName(name = "Значение")
    private String value;
    @ExportName(name = "Неизгладимый")
    private Boolean indelible;
    @ExportName(name = "Время окончания")
    private Date finishTime;
    @ExportName(name = "Индекс")
    private Integer index;

    @ExportName(name = "Номер региона")
    private Integer regionId;
    @ExportIgnored
    private ReferenceParentDto parent;

}
