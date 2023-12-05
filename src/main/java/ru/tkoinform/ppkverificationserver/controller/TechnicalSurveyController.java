package ru.tkoinform.ppkverificationserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tkoinform.ppkverificationserver.CrossOrigins;
import ru.tkoinform.ppkverificationserver.dto.InfrastructureObjectDto;
import ru.tkoinform.ppkverificationserver.dto.base.LabeledEnumDto;
import ru.tkoinform.ppkverificationserver.mapping.GlobalMapper;
import ru.tkoinform.ppkverificationserver.mapping.InfrastructureObjectMapper;
import ru.tkoinform.ppkverificationserver.model.InfrastructureObject;
import ru.tkoinform.ppkverificationserver.model.TechnicalSurvey;
import ru.tkoinform.ppkverificationserver.model.enums.ObjectFlowStatus;
import ru.tkoinform.ppkverificationserver.model.enums.TechnicalSurveyStatus;
import ru.tkoinform.ppkverificationserver.service.InfrastructureObjectService;
import ru.tkoinform.ppkverificationserver.service.TechnicalSurveyService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/survey")
@CrossOrigin(origins = CrossOrigins.value)
public class TechnicalSurveyController {

    @Autowired
    private TechnicalSurveyService technicalSurveyService;

    @Autowired
    private InfrastructureObjectService objectService;

    @Autowired
    private InfrastructureObjectMapper infrastructureObjectMapper;

    @Autowired
    private GlobalMapper globalMapper;


    @PostMapping()
//    @PreAuthorize("hasRole('ROLE_fs_operator')")
    public ResponseEntity<InfrastructureObjectDto> createTechnicalSurvey(@RequestParam final UUID objectId) {
        InfrastructureObject infrastructureObject = objectService.getInfrastructureObject(objectId);
        TechnicalSurvey technicalSurvey = technicalSurveyService.startSurvey(infrastructureObject.getTechnicalSurvey().getId());
        final InfrastructureObject resultObject = objectService.changeStatus(objectId, ObjectFlowStatus.TECHNICAL_SURVEY_CREATED, null);

        final InfrastructureObjectDto dto = infrastructureObjectMapper.map(resultObject);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/end")
//    @PreAuthorize("hasRole('ROLE_fs_mobile')")
    public ResponseEntity<InfrastructureObjectDto> endTechnicalSurvey(@RequestParam final UUID objectId) {
        InfrastructureObject infrastructureObject = objectService.getInfrastructureObject(objectId);
        TechnicalSurvey technicalSurvey = technicalSurveyService.endSurvey(infrastructureObject.getTechnicalSurvey().getId());
        final InfrastructureObject resultObject = objectService.changeStatus(objectId, ObjectFlowStatus.TECHNICAL_SURVEY_ENDED, null);

        final InfrastructureObjectDto dto = infrastructureObjectMapper.map(resultObject);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<LabeledEnumDto>> getAllStatuses() {
        List<LabeledEnumDto> statuses = globalMapper.mapAsList(Arrays.asList(TechnicalSurveyStatus.values()), LabeledEnumDto.class);
        return ResponseEntity.ok(statuses);
    }
}
