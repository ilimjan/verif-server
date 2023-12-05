package ru.tkoinform.ppkverificationserver.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.model.enums.ObjectFlowStatus;
import ru.tkoinform.ppkverificationserver.model.enums.TechnicalSurveyStatus;

import java.util.UUID;

@Getter
@Setter
public class ObjectFilter {
    private UUID federalDistrictName;
    private UUID subjectName;
    private String oktmoCode;
    private UUID status;
    private UUID type;
    private String flowStatusName;
    private String operatorName;
//    private TechnicalSurveyStatus technicalSurveyStatus;
}
