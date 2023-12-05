package ru.tkoinform.ppkverificationserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tkoinform.ppkverificationserver.model.enums.ReferenceType;
import ru.tkoinform.ppkverificationserver.model.Reference;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference, UUID> {
    List<Reference> getAllByType(final ReferenceType type);
    List<Reference> getAllByTypeAndParent(final ReferenceType type, final Reference parent);
    List<Reference> getAllByValue(final String value);
    List<Reference> findByIsUpdated(Boolean isUpdated);
}
