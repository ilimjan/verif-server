package ru.tkoinform.ppkverificationserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.tkoinform.ppkverificationserver.CrossOrigins;
import ru.tkoinform.ppkverificationserver.dto.InfrastructureObjectDto;
import ru.tkoinform.ppkverificationserver.dto.ObjectFilter;
import ru.tkoinform.ppkverificationserver.dto.ObjectSearch;
import ru.tkoinform.ppkverificationserver.dto.StatusChangingReasonDto;
import ru.tkoinform.ppkverificationserver.dto.base.PagingHistoryResult;
import ru.tkoinform.ppkverificationserver.dto.base.PagingResult;
import ru.tkoinform.ppkverificationserver.dto.other.ImportInfrastructureObjectDto;
import ru.tkoinform.ppkverificationserver.mapping.GlobalMapper;
import ru.tkoinform.ppkverificationserver.mapping.InfrastructureObjectMapper;
import ru.tkoinform.ppkverificationserver.model.InfrastructureObject;
import ru.tkoinform.ppkverificationserver.model.TechnicalSurvey;
import ru.tkoinform.ppkverificationserver.model.enums.ObjectFlowStatus;
import ru.tkoinform.ppkverificationserver.service.ExportService;
import ru.tkoinform.ppkverificationserver.service.InfrastructureObjectService;
import ru.tkoinform.ppkverificationserver.service.TechnicalSurveyService;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@RestController
@RequestMapping("/api/objects")
@CrossOrigin(origins = CrossOrigins.value)
public class InfrastructureObjectsController {

    @Autowired
    private InfrastructureObjectService objectService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GlobalMapper globalMapper;

    @Autowired
    private InfrastructureObjectMapper infrastructureObjectMapper;

    @Autowired
    private ExportService exportService;

    @Autowired
    private TechnicalSurveyService technicalSurveyService;

    @PostMapping
    public ResponseEntity<InfrastructureObjectDto> addObject(@AuthenticationPrincipal Jwt jwt, @RequestBody final InfrastructureObjectDto object) {
        final InfrastructureObject createdObject = objectService.addObject(object, jwt.getClaimAsString("region_id"), false);
        final InfrastructureObjectDto dto = infrastructureObjectMapper.map(createdObject);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{objectId}")
    public void deleteObject(@PathVariable final UUID objectId) {
        objectService.deleteObject(objectId);
    }


    @PostMapping("/import")
    public ResponseEntity<InfrastructureObject> importObject(@AuthenticationPrincipal Jwt jwt, @RequestBody final ImportInfrastructureObjectDto importObject) {
        final InfrastructureObject importedObject = objectService.importObject(importObject, jwt.getClaimAsString("region_id"));
        return ResponseEntity.ok(importedObject);
    }

    @GetMapping("/all")
    public ResponseEntity<List<InfrastructureObjectDto>> getAllObjects(@AuthenticationPrincipal Jwt jwt) {

        final List<InfrastructureObject> objects = objectService.getAll();
        final List<InfrastructureObjectDto> dtos = infrastructureObjectMapper.mapAll(objects);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/autocomplete")
    //@PreAuthorize("hasRole('ROLE_fs_monitoring')")
    public ResponseEntity<List<String>> getObjectsAutocomplete(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(name = "name") String name
    ) {
        final List<String> autocompleteResults = objectService.getAutocompleteStrings(name);
        return ResponseEntity.ok(autocompleteResults);
    }



    @GetMapping("history")
    public ResponseEntity<?> getHistory(
            @RequestParam(name = "id") UUID id,
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size
    ) throws IllegalAccessException {
        PagingHistoryResult history = objectService.getHistory(id, page, size);
        return ResponseEntity.ok(history);
    }
    
    @GetMapping("aza")
    public ResponseEntity<PagingResult<InfrastructureObjectDto>> getObjectsByFiltersFront2(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "search", required = false) String searchJson,
            @RequestParam(name = "filter", required = false) String filterJson
    ) throws JsonProcessingException {
        ObjectFilter filter = null;
        if (filterJson != null) {
            filter = objectMapper.readValue(filterJson, ObjectFilter.class);
        }
        ObjectSearch objectSearch = null;
        if(searchJson != null){
            objectSearch = objectMapper.readValue(searchJson, ObjectSearch.class);
        }
        final PagingResult<InfrastructureObjectDto> objects = objectService.getInfrastructurePage(page, size, sort,
                filter, objectSearch);
        return ResponseEntity.ok(objects);
    }

    @GetMapping
    //@PreAuthorize("hasRole('ROLE_fs_monitoring')")
    public ResponseEntity<PagingResult<InfrastructureObjectDto>> getObjects(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "filter", required = false) String filterJson
    ) throws JsonProcessingException {
        String regionId = null;
        if(jwt != null && jwt.getClaimAsString("role").contains("fs_operator")){
            if(jwt.getClaimAsString("region_id") != null) {
                regionId = jwt.getClaimAsString("region_id");
            }
        }

        ObjectFilter filter = null;
        if (filterJson != null) {
            filter = objectMapper.readValue(filterJson, ObjectFilter.class);
        }
        final PagingResult<InfrastructureObjectDto> objects = objectService.getPage(page, size, sort, search,
                filter, regionId);
        return ResponseEntity.ok(objects);
    }

    @GetMapping("/mobile")
    public ResponseEntity<List<InfrastructureObjectDto>> getAllObjectsMobile() {
        List<InfrastructureObjectDto> dtos = infrastructureObjectMapper.mapAll(objectService.getByFlowStatus(ObjectFlowStatus.TECHNICAL_SURVEY_CREATED));
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{objectId}")
    public ResponseEntity<InfrastructureObjectDto> getInfrastructureObject(@PathVariable final UUID objectId) {
        final InfrastructureObject updatedObject = objectService.getInfrastructureObject(objectId);
        final InfrastructureObjectDto dto = infrastructureObjectMapper.map(updatedObject);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/federalScheme/{federalSchemeId}")
    public ResponseEntity<InfrastructureObjectDto> getInfrastructureObjectByFederalScheme(@PathVariable UUID federalSchemeId) {
        final InfrastructureObject updatedObject = objectService.getInfrastructureObjectByFederalScheme(federalSchemeId);
        final InfrastructureObjectDto dto = infrastructureObjectMapper.map(updatedObject);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{objectId}")
    public ResponseEntity<InfrastructureObjectDto> updateInfrastructureObject(@PathVariable final UUID objectId,
                                                                              @RequestBody final Map<String, Object> updates, @AuthenticationPrincipal Jwt jwt) throws NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        final InfrastructureObject updatedObject = objectService.updateInfrastructureObject(updates, objectId, jwt);
        final InfrastructureObjectDto dto = infrastructureObjectMapper.map(updatedObject);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{objectId}/send-to-moderation")
//    @PreAuthorize("hasRole('ROLE_fs_operator')")
    public ResponseEntity<InfrastructureObjectDto> sendToModeration(@PathVariable final UUID objectId) {
        final InfrastructureObject resultObject = objectService.changeStatus(objectId, ObjectFlowStatus.MODERATION, null);
        final InfrastructureObjectDto dto = infrastructureObjectMapper.map(resultObject);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{objectId}/request-audit")
//    @PreAuthorize("hasRole('ROLE_fs_admin')")
    public ResponseEntity<InfrastructureObjectDto> requestAudit(@PathVariable final UUID objectId) {
        final InfrastructureObject resultObject = objectService.changeStatus(objectId, ObjectFlowStatus.AUDIT, null);
        final InfrastructureObjectDto dto = infrastructureObjectMapper.map(resultObject);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{objectId}/set-verified")
//    @PreAuthorize("hasRole('ROLE_fs_admin')")
//    @PreAuthorize("hasRole('ROLE_fs_analyst')")
    public ResponseEntity<InfrastructureObjectDto> setVerified(@PathVariable final UUID objectId) {
        final InfrastructureObject resultObject = objectService.changeStatus(objectId, ObjectFlowStatus.VERIFIED, null);
        final InfrastructureObjectDto dto = infrastructureObjectMapper.map(resultObject);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{objectId}/archive")
//    @PreAuthorize("hasRole('ROLE_fs_admin')")
    public ResponseEntity<InfrastructureObjectDto> archive(@PathVariable final UUID objectId) {
        final InfrastructureObject resultObject = objectService.changeStatus(objectId, ObjectFlowStatus.ARCHIVE, null);
        final InfrastructureObjectDto dto = infrastructureObjectMapper.map(resultObject);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{objectId}/reject")
//    @PreAuthorize("hasRole('ROLE_fs_admin')")
//    @PreAuthorize("hasRole('ROLE_fs_analyst')")
    public ResponseEntity<InfrastructureObjectDto> reject(@PathVariable final UUID objectId,
                                                          @RequestBody final StatusChangingReasonDto statusChangingReason) {
        final InfrastructureObject resultObject = objectService.changeStatus(objectId, ObjectFlowStatus.REJECTED, statusChangingReason.getStatusChangingReason());
        final InfrastructureObjectDto dto = infrastructureObjectMapper.map(resultObject);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{objectId}/request-technical-survey")
//    @PreAuthorize("hasRole('ROLE_fs_admin')")
//    @PreAuthorize("hasRole('ROLE_fs_analyst')")
    public ResponseEntity<InfrastructureObjectDto> requestTechnicalSurvey(@PathVariable final UUID objectId,
                                                                          @RequestBody final StatusChangingReasonDto statusChangingReason) {
        // Тут надо создать обследование
        TechnicalSurvey technicalSurvey = technicalSurveyService.createTechnicalSurvey(objectId, false);

        final InfrastructureObject resultObject = objectService.changeStatus(objectId, ObjectFlowStatus.TECHNICAL_SURVEY_REQUESTED, statusChangingReason.getStatusChangingReason());
        final InfrastructureObjectDto dto = infrastructureObjectMapper.map(resultObject);
        return ResponseEntity.ok(dto);
    }
}
