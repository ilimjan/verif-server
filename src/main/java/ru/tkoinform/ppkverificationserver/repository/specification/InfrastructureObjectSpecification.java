package ru.tkoinform.ppkverificationserver.repository.specification;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import ru.tkoinform.ppkverificationserver.dto.ObjectFilter;
import ru.tkoinform.ppkverificationserver.dto.ObjectSearch;
import ru.tkoinform.ppkverificationserver.model.InfrastructureObject;
import ru.tkoinform.ppkverificationserver.model.enums.ObjectFlowStatus;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class InfrastructureObjectSpecification {


    public static Specification<InfrastructureObject> bySearch(final String search) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (StringUtils.isBlank(search)) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();

            UUID uuid;
            try{
                uuid = UUID.fromString(search);
                predicates.add(criteriaBuilder.equal(root.get("id"), uuid));
                predicates.add(criteriaBuilder.equal(root.join("operator", JoinType.LEFT).get("id"), uuid));
                predicates.add(criteriaBuilder.equal(root.get("technicalSurvey").get("id"), uuid));
                predicates.add(criteriaBuilder.equal(root.get("fiasAddress").get("id"), uuid));
            } catch (IllegalArgumentException exception){
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + search + "%"));
                predicates.add(criteriaBuilder.like(root.get("type").get("value"), "%" + search + "%"));
                predicates.add(criteriaBuilder.like(root.get("addressDescription"), "%" + search + "%"));
                predicates.add(criteriaBuilder.like(root.join("operator", JoinType.LEFT).get("name"), "%" + search + "%"));
                predicates.add(criteriaBuilder.like(root.join("operator", JoinType.LEFT).get("inn"), "%" + search + "%"));
                predicates.add(criteriaBuilder.like(root.join("operator", JoinType.LEFT).get("ogrn"), "%" + search + "%"));
                predicates.add(criteriaBuilder.like(root.join("currentLandPlots", JoinType.LEFT).get("cadastralNumber"), "%" + search + "%"));
            }
            criteriaQuery.groupBy(root.get("id"));

            return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public static Specification<InfrastructureObject> byInfrastructureSearch(final ObjectSearch objectSearch){
        return (root, criteriaQuery, criteriaBuilder) ->{
            if(objectSearch == null){
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if(objectSearch.getDataSourceName() != null){
                predicates.add(criteriaBuilder.equal(root.get("dataSource").get("name"), objectSearch.getDataSourceName()));
            }

            if(objectSearch.getDataSourceRepresentativeFio() != null){
                predicates.add(criteriaBuilder.equal(root.get("dataSource").get("representativeFio"), objectSearch.getDataSourceRepresentativeFio()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public static Specification<InfrastructureObject> byRegion(final String regionId){
        return (root, criteriaQuery, criteriaBuilder) ->{
            if(regionId == null){
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("regionId"), regionId));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public static Specification<InfrastructureObject> byFilter(final ObjectFilter filter) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            if (filter == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getFederalDistrictName() != null) {
                predicates.add(criteriaBuilder.equal(root.get("federalDistrictName").get("id"), filter.getFederalDistrictName()));
            }
            if (filter.getType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("type").get("id"), filter.getType()));
            }
            if (filter.getOktmoCode() != null) {
                predicates.add(criteriaBuilder.equal(root.get("fiasAddress").get("oktmo"), filter.getOktmoCode()));
            }
            if (filter.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status").get("id"), filter.getStatus()));
            }
            if (filter.getSubjectName() != null) {
                predicates.add(criteriaBuilder.equal(root.get("subjectName").get("id"), filter.getSubjectName()));
            }
            if (filter.getFlowStatusName() != null) {
                predicates.add(criteriaBuilder.equal(root.get("flowStatus"), ObjectFlowStatus.valueOf(filter.getFlowStatusName())));
            }
            if (filter.getOperatorName() != null) {
                predicates.add(criteriaBuilder.equal(root.get("operator").get("name"), filter.getOperatorName()));
            }
//            if (filter.getWasteCategory() != null) {
//                predicates.add(criteriaBuilder.equal(root.get("wasteCategory"), filter.getWasteCategoryRef()));
//            }
//            if (filter.getVerified() != null) {
//                predicates.add(criteriaBuilder.equal(root.get("isVerified"), filter.getVerified()));
//            }
//            if (filter.getTechnicalSurveyStatus() != null) {
//                predicates.add(criteriaBuilder.equal(root.get("technicalSurvey").get("status"), filter.getTechnicalSurveyStatus()));
//            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
    }
}
