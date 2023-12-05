package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ExportName(name = "Сведения о видах отходов, размещаемых на объекте по обращению с ТКО")
public class WasteInfoDto extends UuidEntityDto {

    private List<WasteTypeDto> wasteTypes = new ArrayList<WasteTypeDto>(); // +

    @ExportName(name = "Количество принятых отходов по видам")
    private Double wasteCount;
    //private Double wastePrecentage;
    //private String wasteDirection;

    private List<WasteDirectionDto> wasteDirections = new ArrayList<WasteDirectionDto>(); // +

    @ExportName(name = "Масса образуемых отходов от деятельности объекта (указывается общая планируемая масса образуемых объектом отходов за текущий год)")
    private Double wasteThisYear;
    @ExportName(name = "Масса образуемых отходов от деятельности объекта (указывается общая планируемая масса образуемых объектом отходов за предыдущий год)")
    private Double wastePreviousYear;

    @ExportName(name = "Масса планируемых к принятию ВМР в текущем году")
    private Double vmrThisYear;
    @ExportName(name = "Масса принятых ВМР в предыдущем году")
    private Double vmrPreviousYear;
    @ExportName(name = "Среднегодовая стоимость приема ВМР по видам")
    private Double vmrCost;
}
