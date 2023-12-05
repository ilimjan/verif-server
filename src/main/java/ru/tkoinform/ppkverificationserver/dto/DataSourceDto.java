package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportIgnored;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

import java.util.Date;

@Getter
@Setter
@ExportName(name = "Источники данных об объекте по обращению с ТКО")
public class DataSourceDto extends UuidEntityDto {

    @ExportName(name = "Наименование структурного подразделения Оператора по обращению с ТКО - поставщика сведений")
    private String name;
    @ExportIgnored
    private Date uploadDate;
    @ExportName(name = "ФИО уполномоченного сотрудника поставщика сведений")
    private String representativeFio;
    @ExportName(name = "Должность уполномоченного сотрудника поставщика сведений")
    private String representativePosition;
    @ExportName(name = "Наименование региона (субъекта РФ)")
    private ReferenceDto subjectName;
}
