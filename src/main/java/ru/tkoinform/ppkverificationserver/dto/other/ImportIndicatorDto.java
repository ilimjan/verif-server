package ru.tkoinform.ppkverificationserver.dto.other;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportIndicatorDto {
    private String id;
    private String objectId;
    private Integer year;
    private Integer power;
    private Integer freePower;
    private Integer volume;
    private Integer balanceVolume;
    private Integer massTko;
    private Integer massNotTko;
    private Integer projectMassWaste;
    private Integer projectMassRecycled;
    private Integer recyclingRate;
    private Integer marginalRateTon;
    private Integer marginalRateCubicMeter;
}
