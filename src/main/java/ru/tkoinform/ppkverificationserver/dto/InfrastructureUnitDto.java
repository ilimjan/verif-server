package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportIgnored;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ExportName(name = "Объекты инфраструктуры объекта по обращению с ТКО")
public class InfrastructureUnitDto extends UuidEntityDto {

    @ExportName(name = "Количество")
    private Integer count;
    @ExportName(name = "Марка")
    private String brand;
    @ExportName(name = "Производитель")
    private String manufacturer;
    @ExportName(name = "Длина")
    private Double length;
    @ExportIgnored
    private List<FileInfoDto> photos = new ArrayList<FileInfoDto>();
    @ExportName(name = "Модификация")
    private ReferenceDto type;
    @ExportName(name = "Иная модификация")
    private String otherType;
    @ExportName(name = "Мощность")
    private Double capacity;
    @ExportIgnored
    private FileInfoDto passport;
    @ExportIgnored
    private List<FileInfoDto> passportPhotos = new ArrayList<FileInfoDto>();
    @ExportIgnored
    private FileInfoDto scheme;
    @ExportIgnored
    private List<FileInfoDto> schemePhotos = new ArrayList<FileInfoDto>();
    @ExportName(name = "Назначение")
    private String purpose;
    @ExportName(name = "Функции")
    private String functions;
}
