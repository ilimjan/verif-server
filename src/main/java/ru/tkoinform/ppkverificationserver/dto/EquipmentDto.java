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
@ExportName(name = "Сведения об оборудовании объекта по обращению с ТКО")
public class EquipmentDto extends UuidEntityDto implements Comparable<EquipmentDto> {

    @ExportName(name = "Модификация оборудования")
    private ReferenceDto type;
    @ExportName(name = "Вид оборудования")
    private ReferenceDto kind;
    @ExportName(name = "Иная модификация")
    private String otherType;
    @ExportName(name = "Наименование")
    private String name;
    @ExportName(name = "Количество оборудования")
    private Integer count;
    @ExportName(name = "Марка оборудования")
    private String brand;
    @ExportName(name = "Производитель оборудования")
    private String manufacturer;
    @ExportName(name = "Длина конвейера")
    private Double length;
    @ExportName(name = "Ширина конвейера")
    private Double width;
    @ExportName(name = "Рабочая скорость линии конвейера")
    private Double speed;
    @ExportName(name = "Механизм загрузки отходов на подающий конвейер")
    private String loadingMechanism;
    @ExportName(name = "Толщина слоя отходов, подающихся на конвейер")
    private Double wasteWidth;
    @ExportName(name = "Количество постов сортировки")
    private Integer sortingPostsCount;
    @ExportIgnored
    private Date created;
    @ExportIgnored
    private List<FileInfoDto> photos = new ArrayList<>();
    @ExportIgnored
    private FileInfoDto passport;
    @ExportIgnored
    private List<FileInfoDto> passportPhotos = new ArrayList<>();
    @ExportName(name = "Проектная мощность оборудования")
    private Double projectPower;
    @ExportName(name = "Краткое описание технологического процесса")
    private String processDescription;
    @ExportName(name = "Выпускаемая продукция")
    private String production;

    @Override
    public int compareTo(EquipmentDto o) {
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
