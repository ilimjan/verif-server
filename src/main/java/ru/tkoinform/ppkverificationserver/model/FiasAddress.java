package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;

@Getter
@Setter
@Audited
@Entity
public class FiasAddress extends UuidEntityNotGenerated {
    private String regionName;
    private String municipalRegionName;
    private String city;
    private String innerCityTerritory;
    private String area;
    private String planningStructureElement;
    private String streetRoadElement;
    private String postalCode;
    private String buildingNumber;
    private String roomNumber;
    private String landPlotNumber;
    private String oktmo;
    private String cadastralNumber;
}
