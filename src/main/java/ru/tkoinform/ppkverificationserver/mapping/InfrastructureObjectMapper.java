package ru.tkoinform.ppkverificationserver.mapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkoinform.ppkverificationserver.dto.InfrastructureObjectDto;
import ru.tkoinform.ppkverificationserver.dto.other.ImportInfrastructureObjectDto;
import ru.tkoinform.ppkverificationserver.model.AcceptanceDocumentation;
import ru.tkoinform.ppkverificationserver.model.BorderLandPlot;
import ru.tkoinform.ppkverificationserver.model.Cost;
import ru.tkoinform.ppkverificationserver.model.FiasAddress;
import ru.tkoinform.ppkverificationserver.model.InfrastructureObject;
import ru.tkoinform.ppkverificationserver.model.InfrastructureObjectInfo;
import ru.tkoinform.ppkverificationserver.service.ReferenceService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InfrastructureObjectMapper {

    @Autowired
    private GlobalMapper globalMapper;

    @Autowired
    private ReferenceService referenceService;

    public InfrastructureObject map(final InfrastructureObjectDto dto) {
        return globalMapper.map(dto, InfrastructureObject.class);
    }

    public InfrastructureObjectDto map(final InfrastructureObject object) {
        //return globalMapper.map(object, InfrastructureObjectDto.class);

        InfrastructureObjectDto mappedObject = globalMapper.map(object, InfrastructureObjectDto.class);
        if (mappedObject.getEquipment() != null) {
            Collections.sort(mappedObject.getEquipment());
        }
        if (mappedObject.getCurrentLandPlots() != null) {
            Collections.sort(mappedObject.getCurrentLandPlots());
        }
        return mappedObject;
    }

    public List<InfrastructureObjectDto> mapAll(final List<InfrastructureObject> objectList) {
        return objectList.stream().map(this::map).collect(Collectors.toList());
    }

    public InfrastructureObject map(final ImportInfrastructureObjectDto dto) {
        InfrastructureObject object = new InfrastructureObject();
        object.setName(dto.getName());
        object.setLatitude(dto.getLatitude());
        object.setLongitude(dto.getLongitude());
        object.setExploitationStartDate(dto.getCommissioningDate());
        object.setExploitationEndDate(dto.getDecommissioningDate());

        // указать маппинг типов

        AcceptanceDocumentation acceptanceDocumentation = new AcceptanceDocumentation();
        acceptanceDocumentation.setGroroRequestRequisites(dto.getNumberGroro());
        acceptanceDocumentation.setHasLicence(dto.getIsLicenceExists());

        InfrastructureObjectInfo objectInfo = new InfrastructureObjectInfo();
        objectInfo.setHasWeightControl(dto.getIsWeightControl());

        BorderLandPlot borderLandPlot = new BorderLandPlot();
        borderLandPlot.setDistanceToNearestAirport(Double.valueOf(dto.getDistanceAirport()));

        FiasAddress fiasAddress = new FiasAddress();
        fiasAddress.setCadastralNumber(dto.getCadastralNumber());

        Cost cost = new Cost();
        cost.setTotal(Double.valueOf(dto.getObjectCost()));
        cost.setEquipment(Double.valueOf(dto.getCostEquipment()));
        cost.setInstallation(Double.valueOf(dto.getInstallationCost()));
        cost.setRussianEquipment(Double.valueOf(dto.getCostEquipmentRf()));
        cost.setRussianInstallation(Double.valueOf(dto.getInstallationCostRf()));

        object.setCost(cost);
        object.setInfrastructureObjectInfo(objectInfo);
        object.setFiasAddress(fiasAddress);
        object.setAcceptanceDocumentation(acceptanceDocumentation);
        return object;
    }
}
