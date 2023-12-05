package ru.tkoinform.ppkverificationserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tkoinform.ppkverificationserver.CrossOrigins;
import ru.tkoinform.ppkverificationserver.dto.ReferenceDto;
import ru.tkoinform.ppkverificationserver.dto.base.LabeledEnumDto;
import ru.tkoinform.ppkverificationserver.mapping.GlobalMapper;
import ru.tkoinform.ppkverificationserver.model.Reference;
import ru.tkoinform.ppkverificationserver.model.enums.ObjectFlowStatus;
import ru.tkoinform.ppkverificationserver.model.enums.ObjectSourceInformation;
import ru.tkoinform.ppkverificationserver.model.enums.ObjectUpdateSource;
import ru.tkoinform.ppkverificationserver.model.enums.ReferenceType;
import ru.tkoinform.ppkverificationserver.service.ReferenceService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/references")
@CrossOrigin(origins = CrossOrigins.value)
public class ReferenceController {

    @Autowired
    private ReferenceService referenceService;

    @Autowired
    private GlobalMapper globalMapper;

    @GetMapping
    public ResponseEntity<List<ReferenceDto>> getReferenceValues(@RequestParam final ReferenceType type,
                                                                 @RequestParam(required = false) final UUID parentId) {
        List<ReferenceDto> references = globalMapper.mapAsList(referenceService.getAllByType(type, parentId), ReferenceDto.class);
        return ResponseEntity.ok(references);
    }

    @GetMapping("/types")
    public ResponseEntity<List<LabeledEnumDto>> getAllReferenceTypes() {
        List<LabeledEnumDto> types = globalMapper.mapAsList(Arrays.asList(ReferenceType.values()), LabeledEnumDto.class);
        return ResponseEntity.ok(types);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReferenceDto>> getAllReferences() {
        return ResponseEntity.ok(globalMapper.mapAsList(referenceService.getAll(), ReferenceDto.class));
    }

    @GetMapping("/oktmo")
    public ResponseEntity<List<String>> getOktmoCodes() {
        List<String> oktmoCodes = referenceService.findUniqueOktmoCodes();
        return ResponseEntity.ok(oktmoCodes);
    }

    @PostMapping("/add")
    public ResponseEntity<Reference> addReferenceValue(@RequestParam final ReferenceType type,
                                                       @RequestParam final String value,
                                                       @RequestParam final Integer index) {
        Reference reference = new Reference();
        reference.setType(type);
        reference.setValue(value);
        reference.setIndex(index);
        reference = referenceService.save(reference);

        return ResponseEntity.ok(reference);
    }

    @GetMapping("/flowStatuses")
    public ResponseEntity<List<LabeledEnumDto>> getAllFlowStatuses() {
        List<LabeledEnumDto> flowStatuses = globalMapper.mapAsList(Arrays.asList(ObjectFlowStatus.values()), LabeledEnumDto.class);
        return ResponseEntity.ok(flowStatuses);
    }

    @GetMapping("/sourceInformation")
    public ResponseEntity<List<LabeledEnumDto>> getAllSourceInformation(){
        List<LabeledEnumDto> sourceInformation = globalMapper.mapAsList(Arrays.asList(ObjectSourceInformation.values()), LabeledEnumDto.class);
        return ResponseEntity.ok(sourceInformation);
    }

    @GetMapping("/updateSource")
    public ResponseEntity<List<LabeledEnumDto>> getAllUpdateSource(){
        List<LabeledEnumDto> updateSource = globalMapper.mapAsList(Arrays.asList(ObjectUpdateSource.values()), LabeledEnumDto.class);
        return ResponseEntity.ok(updateSource);
    }

    @GetMapping("/operatorNames")
    public ResponseEntity<List<String>> getUniqueOperatorNames() {
        List<String> names = referenceService.findUniqueOperatorNames();
        return ResponseEntity.ok(names);
    }

    @PostMapping("/change")
    public ResponseEntity<Reference> changeReferenceValue(@RequestParam final UUID id,
                                                       @RequestParam final String value,
                                                       @RequestParam final Integer index) {

        Reference reference = referenceService.change(id, value, index);
        if (reference != null) {
            return ResponseEntity.ok(reference);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteReferenceValue(@RequestParam final UUID id) {

        if (referenceService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}
