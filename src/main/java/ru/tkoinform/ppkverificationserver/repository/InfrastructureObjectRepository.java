package ru.tkoinform.ppkverificationserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;
import ru.tkoinform.ppkverificationserver.model.InfrastructureObject;
import ru.tkoinform.ppkverificationserver.model.enums.ObjectFlowStatus;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface InfrastructureObjectRepository extends JpaRepository<InfrastructureObject, UUID>, RevisionRepository<InfrastructureObject, UUID, Integer>,
        JpaSpecificationExecutor<InfrastructureObject> {

    Set<InfrastructureObject> findAllByNameContains(String name);

    List<InfrastructureObject> findAllByFlowStatus(ObjectFlowStatus flowStatus);

    Optional<InfrastructureObject> findByFederalSchemeId(UUID federalSchemeId);
}
