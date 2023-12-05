package ru.tkoinform.ppkverificationserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkoinform.ppkverificationserver.model.Reference;
import ru.tkoinform.ppkverificationserver.model.enums.ReferenceType;
import ru.tkoinform.ppkverificationserver.repository.FiasAddressRepository;
import ru.tkoinform.ppkverificationserver.repository.OperatorRepository;
import ru.tkoinform.ppkverificationserver.repository.ReferenceRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ReferenceService {

    @Autowired
    private ReferenceRepository referenceRepository;

    @Autowired
    private FiasAddressRepository fiasAddressRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    public List<Reference> getAll() {
        return referenceRepository.findAll();
    }

    public List<Reference> getAllByType(final ReferenceType referenceType, final UUID parentId) {
        if (parentId == null) {
            return referenceRepository.getAllByType(referenceType);
        } else {
            Reference parent = referenceRepository.findById(parentId).orElse(null);
            return referenceRepository.getAllByTypeAndParent(referenceType, parent);
        }
    }

    public Reference save(final Reference reference) {
        return referenceRepository.save(reference);
    }

    public Reference change(final UUID id, final String value, final Integer index) {
        if (id == null){
            return null;
        }

        if (referenceRepository.existsById(id)) {
            Reference reference = referenceRepository.getOne(id);
            reference.setValue(value);
            reference.setIndex(index);
            return referenceRepository.save(reference);
        } else {
            return null;
        }
    }

    public Boolean delete(final UUID id) {
        if (id == null) {
            return false;
        }

        if (referenceRepository.existsById(id)) {
            Reference reference = referenceRepository.getOne(id);
            if (reference.getIndelible() != null && reference.getIndelible()){
                return false;
            }
            else {
                reference.setFinishTime(new Date(System.currentTimeMillis()));
                referenceRepository.save(reference);
                return true;
            }
        } else {
            return false;
        }
    }

    public Reference getReference(final UUID id) {
        if (id == null) {
            return null;
        }
        return referenceRepository.getOne(id);
    }

    public Reference getReferenceByValue(final String value) {
        if (value == null) {
            return null;
        }
        List<Reference> references = referenceRepository.getAllByValue(value);
        if (references.size() > 0) {
            return references.get(0);
        } else {
            return null;
        }
    }

    public List<String> findUniqueOktmoCodes() {
        return new ArrayList<>(fiasAddressRepository.findUniqueOktmoCodes());
    }

    public List<String> findUniqueOperatorNames() {
        return new ArrayList<>(operatorRepository.findUniqueOperatorNames());
    }
}
