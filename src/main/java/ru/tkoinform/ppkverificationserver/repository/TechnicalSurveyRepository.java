package ru.tkoinform.ppkverificationserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tkoinform.ppkverificationserver.model.TechnicalSurvey;

import java.util.UUID;

@Repository
public interface TechnicalSurveyRepository extends JpaRepository<TechnicalSurvey, UUID> {
}
