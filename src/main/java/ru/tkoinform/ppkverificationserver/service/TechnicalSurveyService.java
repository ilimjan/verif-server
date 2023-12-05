package ru.tkoinform.ppkverificationserver.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkoinform.ppkverificationserver.exception.ObjectNotFoundException;
import ru.tkoinform.ppkverificationserver.model.TechnicalSurvey;
import ru.tkoinform.ppkverificationserver.model.enums.TechnicalSurveyStatus;
import ru.tkoinform.ppkverificationserver.repository.InfrastructureObjectRepository;
import ru.tkoinform.ppkverificationserver.repository.TechnicalSurveyRepository;

import java.util.UUID;

@Service
public class TechnicalSurveyService {

    @Autowired
    private TechnicalSurveyRepository technicalSurveyRepository;

    @Autowired
    private InfrastructureObjectRepository objectRepository;

    public TechnicalSurvey createTechnicalSurvey(final UUID objectId, final boolean startIt) {
        val objectOptional = objectRepository.findById(objectId);
        objectOptional.orElseThrow(ObjectNotFoundException::new);

        val object = objectOptional.get();

        TechnicalSurvey technicalSurvey = new TechnicalSurvey();
        technicalSurvey.setInfrastructureObject(object);
        object.setTechnicalSurvey(technicalSurvey);
        technicalSurvey = objectRepository.save(object).getTechnicalSurvey();

        if (startIt) {
            return startSurvey(technicalSurvey.getId());
        }

        return technicalSurvey;
    }

    public TechnicalSurvey startSurvey(final UUID surveyId) {
        val surveyOptional = technicalSurveyRepository.findById(surveyId);
        surveyOptional.orElseThrow(ObjectNotFoundException::new);

        val survey = surveyOptional.get();
        survey.setStatus(TechnicalSurveyStatus.IN_PROGRESS);

        return technicalSurveyRepository.save(survey);
    }

    public TechnicalSurvey endSurvey(final UUID surveyId) {
        val surveyOptional = technicalSurveyRepository.findById(surveyId);
        surveyOptional.orElseThrow(ObjectNotFoundException::new);

        val survey = surveyOptional.get();
        survey.setStatus(TechnicalSurveyStatus.COMPLETED);

        return technicalSurveyRepository.save(survey);
    }
}
