package ru.tkoinform.ppkverificationserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.expr.Instanceof;
import lombok.val;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.history.Revision;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.tkoinform.ppkverificationserver.dto.InfrastructureObjectDto;
import ru.tkoinform.ppkverificationserver.dto.ObjectFilter;
import ru.tkoinform.ppkverificationserver.dto.ObjectSearch;
import ru.tkoinform.ppkverificationserver.dto.ReferenceDto;
import ru.tkoinform.ppkverificationserver.dto.base.LabeledEnumDto;
import ru.tkoinform.ppkverificationserver.dto.base.PagingHistoryResult;
import ru.tkoinform.ppkverificationserver.dto.base.PagingResult;
import ru.tkoinform.ppkverificationserver.dto.other.ImportInfrastructureObjectDto;
import ru.tkoinform.ppkverificationserver.exception.ObjectNotFoundException;
import ru.tkoinform.ppkverificationserver.mapping.InfrastructureObjectMapper;
import ru.tkoinform.ppkverificationserver.model.CustomRevisionEntity;
import ru.tkoinform.ppkverificationserver.model.DataSource;
import ru.tkoinform.ppkverificationserver.model.InfrastructureObject;
import ru.tkoinform.ppkverificationserver.model.Reference;
import ru.tkoinform.ppkverificationserver.model.enums.ObjectFlowStatus;
import ru.tkoinform.ppkverificationserver.model.enums.ObjectSourceInformation;
import ru.tkoinform.ppkverificationserver.model.enums.ObjectUpdateSource;
import ru.tkoinform.ppkverificationserver.model.enums.TechnicalSurveyStatus;
import ru.tkoinform.ppkverificationserver.repository.FiasAddressRepository;
import ru.tkoinform.ppkverificationserver.repository.InfrastructureObjectRepository;
import ru.tkoinform.ppkverificationserver.repository.ReferenceRepository;
import ru.tkoinform.ppkverificationserver.repository.specification.InfrastructureObjectSpecification;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class InfrastructureObjectService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InfrastructureObjectRepository repository;

    @Autowired
    private FiasAddressRepository fiasAddressRepository;

    @Autowired
    private InfrastructureObjectMapper mapper;

    @Autowired
    private ObjectMapper jacksonMapper;

    @Autowired
    ReferenceRepository referenceRepository;

    @Autowired
    private PpkService ppkService;

    private Boolean isUpdatedInfrastructureObject = false;

    // 2020-12-09T21:00:00.000+00:00
    private final DateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    @Transactional
    public InfrastructureObject addObject(final InfrastructureObjectDto requestDto, String regionId, Boolean isImport) {
        requestDto.setFlowStatusChanged(new Date());

        InfrastructureObject infrastructureObject = mapper.map(requestDto);
        if (infrastructureObject.getFiasAddress() != null) {
            if(infrastructureObject.getFiasAddress().getId() != null) {
                infrastructureObject.setFiasAddress(fiasAddressRepository.save(infrastructureObject.getFiasAddress()));
            }else{
                infrastructureObject.setFiasAddress(null);
            }
        }
        infrastructureObject.setFlowStatus(ObjectFlowStatus.DRAFT);
        infrastructureObject.setRegionId(regionId);
        if(isImport){
            infrastructureObject.setSourceInformation(ObjectSourceInformation.IMPORTED);
        }else{
            infrastructureObject.setSourceInformation(ObjectSourceInformation.ARM);
        }

        return repository.save(infrastructureObject);
    }

    public void deleteObject(final UUID objectId) {
        repository.deleteById(objectId);
    }

    public InfrastructureObject changeStatus(final UUID objectId, final ObjectFlowStatus status, final String statusChangingReason) {
        val object = repository.findById(objectId).orElseThrow(ObjectNotFoundException::new);
        object.setStatusChangingReason(statusChangingReason);
        object.setFlowStatus(status);
        object.setFlowStatusChanged(new Date());

        if (status == ObjectFlowStatus.MODERATION && (object.getDataSource() == null || object.getDataSource().getUploadDate() == null)) {
            if (object.getDataSource() == null) {
                object.setDataSource(new DataSource());
            }
            object.getDataSource().setUploadDate(new Date());
        }
        return repository.save(object);
    }

    public InfrastructureObject importObject(final ImportInfrastructureObjectDto importObject, String regionId) {
        InfrastructureObject infrastructureObject = mapper.map(importObject);
        infrastructureObject.setRegionId(regionId);
        return repository.save(infrastructureObject);
    }

    public List<InfrastructureObject> getAll() {
        return repository.findAll();
    }

    public List<InfrastructureObject> getByFlowStatus(final ObjectFlowStatus flowStatus) {
        return repository.findAllByFlowStatus(flowStatus);
    }

    public List<InfrastructureObject> getAll(List<UUID> ids) {
        return repository.findAllById(ids);
    }

    public PagingHistoryResult getHistory(UUID id, int page, int size) throws IllegalAccessException {

        List<Reference> references = referenceRepository.findByIsUpdated(false);
        for (Reference reference : references){
            reference.setIsUpdated(true);
            referenceRepository.save(reference);
        }

        List<InfrastructureObject> historyList = new ArrayList<InfrastructureObject>();
        Page<Revision<Integer, InfrastructureObject>> histories = repository.findRevisions(id, PageRequest.of(page, size));
        histories.getContent().forEach(x ->{
            x.getEntity().setRevisionType(x.getMetadata().getRevisionType().toString());
            CustomRevisionEntity customRevisionEntity = x.getMetadata().getDelegate();
            x.getEntity().setRevisionUserName(customRevisionEntity.getUserName());

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            x.getEntity().setRevisionDateTime(formatter.format(customRevisionEntity.getRevisionDate()));

            historyList.add(x.getEntity());
        });
        return new PagingHistoryResult(mapper.mapAll(historyList), histories.getTotalElements());
    }

    public List<String> getAutocompleteStrings(String name) {
        Set<InfrastructureObject> objects = repository.findAllByNameContains(name);

        List<String> autocompleterResults = new ArrayList<>();
        for (InfrastructureObject object : objects) {
            autocompleterResults.add(object.getName());
        }
        return autocompleterResults;
    }

    public PagingResult<InfrastructureObjectDto> getInfrastructurePage(int page, int size, String sort,
                                                          ObjectFilter filter, ObjectSearch objectSearch){
        PageRequest pageable;
        if (StringUtils.isEmpty(sort)) {
            pageable = PageRequest.of(page, size);
        } else {
            if (sort.startsWith("-")) {
                sort = sort.substring(1);
                pageable = PageRequest.of(page, size, Sort.by(sort).descending());
            } else {
                pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
            }
        }

        Page<InfrastructureObject> objectPage = repository.findAll(InfrastructureObjectSpecification
                .byFilter(filter).and(InfrastructureObjectSpecification.byInfrastructureSearch(objectSearch)), pageable);

        return new PagingResult<InfrastructureObjectDto>(
                mapper.mapAll(objectPage.getContent()),
                objectPage.getTotalElements()
        );
    }

    public PagingResult<InfrastructureObjectDto> getPage(int page, int size, String sort,
                                                         String search, ObjectFilter filter, String regionId) {

        PageRequest pageable;
        if (StringUtils.isEmpty(sort)) {
            pageable = PageRequest.of(page, size);
        } else {
            if (sort.startsWith("-")) {
                sort = sort.substring(1);
                pageable = PageRequest.of(page, size, Sort.by(sort).descending());
            } else {
                pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
            }
        }
        Page<InfrastructureObject> objectPage = repository.findAll(InfrastructureObjectSpecification
                .byFilter(filter).and(InfrastructureObjectSpecification.bySearch(search)).and(InfrastructureObjectSpecification.byRegion(regionId)), pageable);

        return new PagingResult<InfrastructureObjectDto>(
                mapper.mapAll(objectPage.getContent()),
                objectPage.getTotalElements()
        );
    }

    @Transactional(readOnly = true)
    public List<InfrastructureObject> findAllByIds(List<UUID> infrastructureObjectIds) {
        return repository.findAllById(infrastructureObjectIds);
    }

    public InfrastructureObject updateInfrastructureObject(final Map<String, Object> updates, final UUID objectId, @AuthenticationPrincipal Jwt jwt) throws NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        InfrastructureObject object = repository.findById(objectId).orElseThrow(ObjectNotFoundException::new);
        Date oldChangeDate = object.getChangeDate();
        InfrastructureObjectDto dto = mapper.map(object);
        LabeledEnumDto oldUpdateSource = dto.getUpdateSource();

        merge(dto, updates);


        LabeledEnumDto flowStatusDto = dto.getFlowStatus();
        LabeledEnumDto sourceInformationDto = dto.getSourceInformation();
        LabeledEnumDto updateSource = dto.getUpdateSource();

        LabeledEnumDto technicalSurveyStatus = dto.getTechnicalSurvey() != null ? dto.getTechnicalSurvey().getStatus() : null;
        dto.setFlowStatus(null);
        dto.setSourceInformation(null);
        dto.setUpdateSource(null);
        if (dto.getTechnicalSurvey() != null) {
            dto.getTechnicalSurvey().setStatus(null);
        }

        InfrastructureObject updatedObject = mapper.map(dto);

        if (flowStatusDto != null) {
            updatedObject.setFlowStatus(ObjectFlowStatus.valueOf(flowStatusDto.getName()));
        }
        if (sourceInformationDto != null) {
            updatedObject.setSourceInformation(ObjectSourceInformation.valueOf(sourceInformationDto.getName()));
        }
        if (technicalSurveyStatus != null) {
            updatedObject.getTechnicalSurvey().setStatus(TechnicalSurveyStatus.valueOf(technicalSurveyStatus.getName()));
        }

        if (updatedObject.getFiasAddress() != null) {
            updatedObject.setFiasAddress(fiasAddressRepository.save(updatedObject.getFiasAddress()));
        }
        if(isUpdatedInfrastructureObject){
            logger.warn("yes");
            updatedObject.setChangeDate(new Date());
            if(updateSource != null){
                updatedObject.setUpdateSource(ObjectUpdateSource.valueOf(updateSource.getName()));
            }
        }else{
            if(oldUpdateSource != null){
                updatedObject.setUpdateSource(ObjectUpdateSource.valueOf(oldUpdateSource.getName()));
            }
            updatedObject.setChangeDate(oldChangeDate);
        }
        isUpdatedInfrastructureObject = false;
        InfrastructureObject newObject = repository.save(updatedObject);
        // Отправка письма на почтовый ящик
        if(newObject.getFlowStatus().equals(ObjectFlowStatus.VERIFIED)){
            File file = null;
            try {
                file = new ClassPathResource("title.docx").getFile();
                StringBuilder body = new StringBuilder();
                body
                        .append("<div>")
                        .append("Данные по Вашей анкете-заявки на включение объекта по обращению с ТКО в Реестр объектов были верифицированы.")
                        .append("</div>")
                        .append("<div>")
                        .append("<strong>Наименование объекта по обращению с ТКО: </strong>" + newObject.getName())
                        .append("</div>")
                        .append("<div>")
                        .append("<strong>Дата верификации: </strong>" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(newObject.getFlowStatusChanged()))
                        .append("</div>");

                ppkService.sendEmail(jwt.getClaimAsString("email"), "Уведомление по \"Модуль верификация\".",
                        body.toString(), file, jwt.getTokenValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newObject;
    }

    public void testMerge(){

    }

    public InfrastructureObject getInfrastructureObject(final UUID objectId) {
        return repository.findById(objectId).orElseThrow(ObjectNotFoundException::new);
    }

    public InfrastructureObject getInfrastructureObjectByFederalScheme(final UUID objectId) {
        return repository.findByFederalSchemeId(objectId).orElseThrow(ObjectNotFoundException::new);
    }

    public void merge(final Object source, final Map<String, Object> changes) throws IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        logger.info("infrastructureObject");
        merge(source, changes, 0);
    }

    public void merge(final Object source, final Map<String, Object> changes, int level) throws NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        Map<String, Object> notDeepChanges = new HashMap<>();
        Map<String, Object> deepChanges = new HashMap<>();

        Field[] fields = getClassFields(source.getClass());
        for (Map.Entry<String, Object> entry : changes.entrySet()) {
            if (entry.getValue() instanceof List) {
                List values = new ArrayList();
                for (Object listElement : (List) entry.getValue()) {
                    val elementValue = jacksonMapper.convertValue(listElement,
                            (Class) ((ParameterizedType) source.getClass().getDeclaredField(entry.getKey()).getGenericType()).getActualTypeArguments()[0]);
                    values.add(elementValue);
                }
                val field = source.getClass().getDeclaredField(entry.getKey());
                field.setAccessible(true);
                checkChangeListObject(source, (List) entry.getValue(), fields, entry.getKey());
                field.set(source, values);
            } else if (entry.getValue() instanceof Map) {
                deepChanges.put(entry.getKey(), entry.getValue());
            } else {

                Object value = entry.getValue();
                if (value instanceof String) {
                    try {
                        value = UUID.fromString((String) entry.getValue());
                    } catch (Exception e) {
                        // do nothing
                    }
                    try {
                        value = isoDateFormat.parse((String) entry.getValue());
                    } catch (Exception e2) {
                        // do nothing
                    }
                }
                notDeepChanges.put(entry.getKey(), value);
                if(!isUpdatedInfrastructureObject)
                    checkChangeObject(source, fields, value, entry.getKey());
            }
        }

        BeanUtils.copyProperties(source, notDeepChanges);

        for (Map.Entry<String, Object> entry : deepChanges.entrySet()) {

            String logStr = "";
            for (int i = 0; i < level; i++) {
                logStr += "+───────";
            }
            logStr += "+──── " + entry.getKey();
            logger.info(logStr);

            val field = source.getClass().getDeclaredField(entry.getKey());
            field.setAccessible(true);
            Object fieldValue = field.get(source);
            if (fieldValue == null) {
                fieldValue = jacksonMapper.convertValue(new HashMap<>(), field.getType());
                field.set(source, fieldValue);
            }
            merge(fieldValue, (Map<String, Object>) entry.getValue(), level + 1);
        }
    }

    private void checkChangeObject(final Object source, Field[] fields, Object value, String fieldName) throws IllegalAccessException {
        val fieldObject = getFieldByName(fields, fieldName);

        if(fieldObject != null){
            fieldObject.setAccessible(true);
            Object oldFieldValue = fieldObject.get(source);
            checkChangesInObject(fieldObject, value, oldFieldValue);
        }
    }

    private void checkChangeListObject(final Object source, List newListValues, Field[] fields, String fieldName) throws IllegalAccessException, NoSuchFieldException {
        if(!isUpdatedInfrastructureObject) {
            val fieldObject = getFieldByName(fields, fieldName);
            if (fieldObject != null) {
                fieldObject.setAccessible(true);
                // Получаем старый список обьектов
                List<Object> oldListValue = (List) fieldObject.get(source);

                if (newListValues.size() == oldListValue.size()) {
                    if (newListValues.size() > 0) {
                        // Получаем список полей одного обьекта из старого списка обьектов
                        Field[] fieldsOneOfElementOldListValue = getClassFields(oldListValue.get(0).getClass());
                        // проходим каждый новый список
                        for (Object newListElement : newListValues) {
                            if(isUpdatedInfrastructureObject)break;
                            if (!isUpdatedInfrastructureObject) {
                                if(newListElement.getClass() == String.class)
                                    continue;
                                logger.info("Going through the cycle");
                                logger.info(newListElement.getClass().getSimpleName());
                                logger.info(fieldObject.getName());
                                logger.info(fieldObject.getType().getSimpleName());
                                // Конвертируем в MAP
                                Map<String, Object> newListElementInMap = (Map<String, Object>) newListElement;

                                // Получаем старый элемент со списка
                                Object oldListElement = getOldListElement(fieldObject, newListElementInMap, oldListValue);
                                if (oldListElement != null) {
                                    // Конвертируем в MAP
                                    Map<String, Object> oldListElementInMap = jacksonMapper.convertValue(oldListElement, Map.class);

                                    // проходим по каждому полю из элемента обьекта
                                    for (Map.Entry<String, Object> entry : newListElementInMap.entrySet()) {
                                        if(isUpdatedInfrastructureObject) break;
                                        if (!isUpdatedInfrastructureObject) {

                                            val simpleFieldObject = getFieldByName(fieldsOneOfElementOldListValue, entry.getKey());
                                            if(simpleFieldObject != null) {
                                                if (entry.getValue() instanceof List) {
                                                    checkChangeListObject(oldListElement, (List) entry.getValue(), fieldsOneOfElementOldListValue, entry.getKey());
                                                } else if (entry.getValue() instanceof Map) {
                                                    checkChangedMap(oldListElement, entry.getValue(), entry.getKey());
                                                } else {
                                                    Object value = entry.getValue();
                                                    Object oldValue = getObjectValueByFieldName(oldListElementInMap, entry.getKey());
                                                    checkChangesInObject(simpleFieldObject, value, oldValue);
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    isUpdatedInfrastructureObject = true;
                                }
                            }
                        }
                    }
                } else {
                    isUpdatedInfrastructureObject = true;
                }
            }
        }
    }

    private void checkChangedMap(Object source, Object newValue, String fieldName) throws IllegalAccessException, NoSuchFieldException {

        // Конвертируем новый обьект в MAP
        Map<String, Object> newObjectInMap = (Map<String, Object>) newValue;
        val mainFieldObject = source.getClass().getDeclaredField(fieldName);
        mainFieldObject.setAccessible(true);

        // Получаем старое значение
        Object mainOldValue = mainFieldObject.get(source);
        if (mainOldValue != null) {
            // Поучаем список полей обьекта
            Field[] fieldsFromOldValue = getClassFields(mainOldValue.getClass());

            // Проходим по каждому полю из обьекта
            for (Map.Entry<String, Object> entry : newObjectInMap.entrySet()) {
                if(isUpdatedInfrastructureObject) break;
                if (!isUpdatedInfrastructureObject) {
                    Field childFieldObject = getFieldByName(fieldsFromOldValue, entry.getKey());
                    if(childFieldObject != null) {
                        childFieldObject.setAccessible(true);
                        Object childOldValue = childFieldObject.get(mainOldValue);

                        if (childOldValue != null) {
                            if (entry.getValue() instanceof List) {
                                checkChangeListObject(mainOldValue, (List) entry.getValue(), fieldsFromOldValue, entry.getKey());
                            } else if (entry.getValue() instanceof Map) {
                                checkChangedMap(mainOldValue, entry.getValue(), entry.getKey());
                            } else {
                                checkChangesInObject(childFieldObject, entry.getValue(), childOldValue);
                            }
                        }
                    }
                }
            }
        }else{
            isUpdatedInfrastructureObject = true;
        }
    }

    private void checkChangesInObject(Field fieldObject, Object value, Object oldValue){
        if(fieldObject.getType() == List.class){
            if(oldValue != null) {
                List oldValueList = (List) oldValue;
                if (oldValueList.size() > 0) {
                    logger.info("change");
                    logger.info("New list is empty");
                    logger.info(fieldObject.getName());
                    isUpdatedInfrastructureObject = true;
                }
            }
        }else {
            if (value == null && oldValue != null) {
                isUpdatedInfrastructureObject = true;
                logger.info("change");
                logger.info("New value is null");
                logger.info(fieldObject.getName());
            } else if (oldValue == null && value != null) {
                isUpdatedInfrastructureObject = true;
                logger.info("change");
                logger.info("Old value is null");
                logger.info(value.toString());
                logger.info(fieldObject.getName());
            } else if (oldValue != null && value != null) {
                if (fieldObject.getType() == Double.class) {
                    Double valueDouble = Double.valueOf(value.toString());
                    if (!valueDouble.toString().equals(oldValue.toString())) {
                        logger.info("change");
                        logger.info("Double fields not equal");
                        logger.info(valueDouble.toString());
                        logger.info(oldValue.toString());
                        logger.info(fieldObject.getName());
                        isUpdatedInfrastructureObject = true;
                    }
                } else if (fieldObject.getType() == Date.class) {

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String oldValueStr = format.format(oldValue);
                    String valueStr = format.format(value);
                    if (!String.valueOf(Timestamp.valueOf(oldValueStr).getTime()).equals(String.valueOf(Timestamp.valueOf(valueStr).getTime()))) {
                        isUpdatedInfrastructureObject = true;
                        logger.info("change");
                        logger.info("Date fields not equal");
                        logger.info(String.valueOf(Timestamp.valueOf(oldValueStr).getTime()));
                        logger.info(String.valueOf(Timestamp.valueOf(valueStr).getTime()));
                        logger.info(fieldObject.getName());
                    }
                } else {
                    if (!value.toString().equals(oldValue.toString())) {
                        logger.info("change");
                        logger.info("Simple fields not equal");
                        logger.info(value.toString());
                        logger.info(oldValue.toString());
                        logger.info(fieldObject.getName());
                        isUpdatedInfrastructureObject = true;
                    }
                }
            }
        }
    }

    private Object getObjectValueByFieldName(Map<String, Object> listElement, String fieldName){
        for (Map.Entry<String, Object> entry : listElement.entrySet()){
            if(entry.getKey().equals(fieldName)){
                return entry.getValue();
            }
        }
        return null;
    }

    private Object getOldListElement(Field fieldObject, Map<String, Object> listElement, List<Object> oldFieldValue){
        String id = getIdElement(listElement);
        for (Object element : oldFieldValue){
            Map<String, Object> oldElement = jacksonMapper.convertValue(element, Map.class);
            for (Map.Entry<String, Object> entry : oldElement.entrySet()){
                if(entry.getKey().equals("id") && entry.getValue().equals(id)){
                    return element;
                }
            }
        }
        return null;
    }

    private String getIdElement(Map<String, Object> listElement){
        for (Map.Entry<String, Object> entry : listElement.entrySet()){
            Object value = entry.getValue();
            if(entry.getKey().equals("id")){
                return value.toString();
            }
        }
        return null;
    }

    private Field[] getClassFields(Class objectClass) {
        Class superclass = objectClass.getSuperclass();
        Field[] objectFields = objectClass.getDeclaredFields();
        if (superclass != null) {
            objectFields = ArrayUtils.addAll(superclass.getDeclaredFields(), objectFields);
        }
        return objectFields;
    }

    private Field getFieldByName(Field[] fields, String fieldName){
        for (Field field : fields){
            if(field.getName() == fieldName){
                return field;
            }
        }
        return null;
    }
}
