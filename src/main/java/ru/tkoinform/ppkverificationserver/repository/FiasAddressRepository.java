package ru.tkoinform.ppkverificationserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tkoinform.ppkverificationserver.model.FiasAddress;

import java.util.Set;
import java.util.UUID;

@Repository
public interface FiasAddressRepository extends JpaRepository<FiasAddress, UUID>, JpaSpecificationExecutor<FiasAddress> {

    @Query("SELECT DISTINCT f.oktmo FROM FiasAddress f")
    Set<String> findUniqueOktmoCodes();
}
