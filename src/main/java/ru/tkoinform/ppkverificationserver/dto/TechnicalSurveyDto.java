package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.dto.base.LabeledEnumDto;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TechnicalSurveyDto extends UuidEntityDto {

    private String roRepresentativeName;
    private String roRepresentativePosition;
    private String operatorRepresentativeName;
    private String operatorRepresentativePosition;
    private String omsuName;
    private String omsuRepresentativeName;
    private String omsuRepresentativePosition;
    private String otherParticipantName;
    private String otherParticipantRepresentativeName;
    private String otherParticipantRepresentativePosition;

    private Date startDate;
    private Date endDate;

    private LabeledEnumDto status;
    private ReferenceDto result;
    private String otherResult;
    private List<TechnicalSurveyFileDto> files;
}
