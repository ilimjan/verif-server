package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ExportName(name = "Сведения о тарифах объекта по обращению с ТКО")
public class TariffInfoDto extends UuidEntityDto {

    @ExportName(name = "Среднегодовой тариф на приём отходов на объекте (указывается за одну тонну в текущем году)")
    private Double averageYearWasteReceiving;
    @ExportName(name = "Тариф на захоронение образованных объектом отходов")
    private Double burial;
    private List<TariffDto> tariffs = new ArrayList<TariffDto>(); // +
    //private List<TariffDto> operatorTariffs = new ArrayList<TariffDto>();
}
