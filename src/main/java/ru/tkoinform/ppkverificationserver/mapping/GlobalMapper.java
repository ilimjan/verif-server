package ru.tkoinform.ppkverificationserver.mapping;

import com.kuliginstepan.dadata.client.domain.address.Address;
import lombok.val;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.MappingDirection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.tkoinform.ppkverificationserver.dto.FiasAddressDto;
import ru.tkoinform.ppkverificationserver.dto.ReferenceDto;
import ru.tkoinform.ppkverificationserver.dto.ReferenceParentDto;
import ru.tkoinform.ppkverificationserver.dto.base.LabeledEnumDto;
import ru.tkoinform.ppkverificationserver.model.Reference;
import ru.tkoinform.ppkverificationserver.model.enums.*;
import ru.tkoinform.ppkverificationserver.service.ReferenceService;

import java.util.UUID;

@Component("globalMapper")
public class GlobalMapper extends ConfigurableMapper implements InitializingBean {

    @Autowired
    private ReferenceService referenceService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public GlobalMapper() {
        super(false);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.init();
    }

    @Override
    protected void configureFactoryBuilder(DefaultMapperFactory.Builder factoryBuilder) {
        super.configureFactoryBuilder(factoryBuilder);
        factoryBuilder.captureFieldContext(true);
    }

    @Override
    protected void configure(MapperFactory mapperFactory) {
        super.configure(mapperFactory);

        mapperFactory.classMap(LabeledEnum.class, LabeledEnumDto.class)
                .byDefault(MappingDirection.A_TO_B)
                .customize(new CustomMapper<LabeledEnum, LabeledEnumDto>() {
                    @Override
                    public void mapAtoB(LabeledEnum labeledEnum, LabeledEnumDto enumDto, MappingContext context) {
                        super.mapAtoB(labeledEnum, enumDto, context);
                        enumDto.setName(labeledEnum.getName());
                        enumDto.setDisplayName(enumDto.getDisplayName());
                    }
                })
                .register();

        mapperFactory.classMap(Address.class, FiasAddressDto.class)
                .byDefault(MappingDirection.A_TO_B)
                .customize(new CustomMapper<Address, FiasAddressDto>() {
                    @Override
                    public void mapAtoB(Address address, FiasAddressDto addressDto, MappingContext context) {
                        super.mapAtoB(address, addressDto, context);
                        addressDto.setId(UUID.fromString(address.getFiasId()));
                        addressDto.setRegionName(address.getRegionWithType());
                        addressDto.setMunicipalRegionName(address.getAreaWithType());
                        addressDto.setCity(address.getCityWithType());
                        addressDto.setInnerCityTerritory(address.getCityDistrictWithType());
                        addressDto.setArea(address.getCityArea()); // Не уверен
                        addressDto.setPlanningStructureElement(null); // TODO
                        addressDto.setStreetRoadElement(address.getStreetWithType());
                        addressDto.setPostalCode(address.getPostalCode());
                        addressDto.setBuildingNumber(address.getHouse());
                        addressDto.setRoomNumber(address.getFlat());
                        addressDto.setLandPlotNumber(address.getArea()); // Не уверен
                        addressDto.setOktmo(address.getOktmo());
                        addressDto.setCadastralNumber(null); // TODO
                    }
                })
                .register();

        mapperFactory.classMap(ReferenceDto.class, Reference.class)
                .customize(new CustomMapper<ReferenceDto, Reference>() {
                    @Override
                    public void mapAtoB(ReferenceDto referenceDto, Reference reference, MappingContext context) {
                        reference.setId(referenceDto.getId());
                        reference.setValue(referenceDto.getValue());
                        reference.setType(ReferenceType.valueOf(referenceDto.getType().getName()));
                        reference.setIndelible(referenceDto.getIndelible());
                        reference.setFinishTime(referenceDto.getFinishTime());
                        reference.setIndex(referenceDto.getIndex());
                        reference.setRegionId(referenceDto.getRegionId());
                    }

                    @Override
                    public void mapBtoA(Reference reference, ReferenceDto referenceDto, MappingContext context) {
                        referenceDto.setId(reference.getId());
                        val type = new LabeledEnumDto();
                        type.setDisplayName(reference.getType().getDisplayName());
                        type.setName(reference.getType().getName());
                        referenceDto.setType(type);
                        referenceDto.setValue(reference.getValue());
                        referenceDto.setIndelible(reference.getIndelible());
                        referenceDto.setFinishTime(reference.getFinishTime());
                        referenceDto.setIndex(reference.getIndex());
                        referenceDto.setRegionId(reference.getRegionId());
                        if(reference.getParent() != null) {
                            ReferenceParentDto referenceParentDto = new ReferenceParentDto();
                            referenceParentDto.setId(reference.getParent().getId());
                            referenceDto.setParent(referenceParentDto);
                        }
                    }
                })
                .register();

        // TODO: придумать как мэпить LabeledEnumDto в LabeledEnum
        mapperFactory.classMap(LabeledEnumDto.class, ObjectFlowStatus.class)
                .byDefault(MappingDirection.A_TO_B)
                .customize(new CustomMapper<LabeledEnumDto, ObjectFlowStatus>() {
                    @Override
                    public void mapAtoB(LabeledEnumDto labeledEnumDto, ObjectFlowStatus status, MappingContext context) {
                        status = ObjectFlowStatus.valueOf(labeledEnumDto.getName());
//                        super.mapAtoB(labeledEnumDto, status, context);
                    }

                    @Override
                    public void mapBtoA(ObjectFlowStatus status, LabeledEnumDto labeledEnumDto, MappingContext context) {
                        labeledEnumDto.setName(status.getName());
                        labeledEnumDto.setDisplayName(status.getDisplayName());
//                        super.mapBtoA(status, labeledEnumDto, context);
                    }
                }).register();
        mapperFactory.classMap(LabeledEnumDto.class, TechnicalSurveyStatus.class)
                .byDefault(MappingDirection.A_TO_B)
                .customize(new CustomMapper<LabeledEnumDto, TechnicalSurveyStatus>() {
                    @Override
                    public void mapAtoB(LabeledEnumDto labeledEnumDto, TechnicalSurveyStatus status, MappingContext context) {
                        if (labeledEnumDto != null && !StringUtils.isEmpty(labeledEnumDto)) {
                            status = TechnicalSurveyStatus.valueOf(labeledEnumDto.getName());
                        }
//                        super.mapAtoB(labeledEnumDto, status, context);
                    }

                    @Override
                    public void mapBtoA(TechnicalSurveyStatus status, LabeledEnumDto labeledEnumDto, MappingContext context) {
                        labeledEnumDto.setName(status.getName());
                        labeledEnumDto.setDisplayName(status.getDisplayName());
//                        super.mapBtoA(status, labeledEnumDto, context);
                    }
                }).register();

        mapperFactory.classMap(LabeledEnumDto.class, ObjectUpdateSource.class)
                .byDefault(MappingDirection.A_TO_B)
                .customize(new CustomMapper<LabeledEnumDto, ObjectUpdateSource>() {
                    @Override
                    public void mapAtoB(LabeledEnumDto labeledEnumDto, ObjectUpdateSource updateSource, MappingContext context) {
                        if (labeledEnumDto != null && !StringUtils.isEmpty(labeledEnumDto)) {
                            updateSource = ObjectUpdateSource.valueOf(labeledEnumDto.getName());
                        }
//                        super.mapAtoB(labeledEnumDto, status, context);
                    }

                    @Override
                    public void mapBtoA(ObjectUpdateSource updateSource, LabeledEnumDto labeledEnumDto, MappingContext context) {
                        labeledEnumDto.setName(updateSource.getName());
                        labeledEnumDto.setDisplayName(updateSource.getDisplayName());
//                        super.mapBtoA(status, labeledEnumDto, context);
                    }
                }).register();

        mapperFactory.classMap(LabeledEnumDto.class, ObjectSourceInformation.class)
                .byDefault(MappingDirection.A_TO_B)
                .customize(new CustomMapper<LabeledEnumDto, ObjectSourceInformation>() {
                    @Override
                    public void mapAtoB(LabeledEnumDto labeledEnumDto, ObjectSourceInformation sourceInformation, MappingContext context) {
                        if (labeledEnumDto != null && !StringUtils.isEmpty(labeledEnumDto)) {
                            sourceInformation = ObjectSourceInformation.valueOf(labeledEnumDto.getName());
                        }
//                        super.mapAtoB(labeledEnumDto, status, context);
                    }

                    @Override
                    public void mapBtoA(ObjectSourceInformation sourceInformation, LabeledEnumDto labeledEnumDto, MappingContext context) {
                        labeledEnumDto.setName(sourceInformation.getName());
                        labeledEnumDto.setDisplayName(sourceInformation.getDisplayName());
//                        super.mapBtoA(status, labeledEnumDto, context);
                    }
                }).register();
    }
}
