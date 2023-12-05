package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

import java.util.List;

@Getter
@Setter
@ExportName(name = "Сведения о выпускаемых объектом по обращению с ТКО товарах")
public class ProductsInfoDto extends UuidEntityDto {

    @ExportName(name = "Общее количество выпускаемых товаров в год")
    private Double totalCountPerYear;
    private List<ProductDto> products; // +
    private List<ProvidedServiceDto> providedServices; // +
}
