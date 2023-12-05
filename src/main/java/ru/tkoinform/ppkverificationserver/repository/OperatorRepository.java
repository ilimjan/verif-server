package ru.tkoinform.ppkverificationserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tkoinform.ppkverificationserver.model.Operator;

import java.util.Set;
import java.util.UUID;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, UUID> {

    @Query("SELECT DISTINCT o.name FROM Operator o")
    Set<String> findUniqueOperatorNames();
}
