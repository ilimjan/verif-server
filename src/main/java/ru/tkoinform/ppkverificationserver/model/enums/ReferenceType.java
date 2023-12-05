package ru.tkoinform.ppkverificationserver.model.enums;

public enum ReferenceType implements LabeledEnum {

    OBJECT_STATUS("Статусы объекта"),
    OBJECT_TYPE("Категория объекта"),
    TKO_OBJECT_TYPE("Тип объекта по обращению с ТКО"),
    FEDERAL_DISTRICT("Федеральный округ"),
    SUBJECT_NAME("Субъект РФ"),
    SUBJECT_AREA("Наименование района субъекта Российской Федерации, на территории которого расположен объект"),
    OKTMO_NUMBER("ОКТМО"),
    WASTE_CATEGORY("Категория отходов"),
    NEGATIVE_OBJECT_CATEGORY("Категории объектов, оказывающих негативное воздействие на окружающую среду"),
    OWNERSHIP_TYPE("Вид собственности на земельный участок"),
    EGRN("ЕГРН"),
    LAND_CATEGORY("Категории земель"),
    ALLOWED_USING_TYPE("Разрешенное использование земельного участка"),
    DANGER_TYPE("Классификация опасных производственных объектов"),
    ROAD_TYPE("Класс автомобильных дорог"),
    GRORO("ГРОРО"),
    COORDINATE_SYSTEM("Системы координат"),
    LAND_DESCRIPTION("Описание земельного участка"),
    WASTE_TYPE("Вид отходов"),
    WASTE_DANGER_CLASS("Класс опасности отходов"),
    FIAS("ФИАС"),
    ZONE("Земли"),
    TREATMENT_FACILITIES("Системы защиты окружающей среды на объекте"),
    ROAD_SURFACE_TYPE("Виды покрытия дорог"),
    FENCE_TYPE("Типы ограждения"),
    FENCE_FUNCTIONAL("Классификатор ограждений по функциональному назначению"),
    FENCE_MOBILITY("Классификатор ограждений по степени мобильности"),
    FENCE_CANVAS("Классификатор ограждений по конструкции полотна"),
    FENCE_VIEWABILITY("Классификатор ограждений по степени просматриваемости полотна"),
    FENCE_FABRIC_MATERIAL("Классификатор ограждений по материалу изготовления полотна"),
    FENCE_FOUNDATION("Классификатор ограждений по виду фундамента"),
    FENCE_SUPPORTS_MATERIAL("Классификатор ограждений по материалу изготовления опор ограждения"),
    LIGHTS_FUNCTIONAL_TYPE("Тип освещения по функциональному назначению"),
    LIGHTS_SOURCE_TYPE("Тип освещения по типу источника света"),
    SECURITY_SOURCE("Классификатор источников услуг охраны, применяемых на объекте по обращению с ТКО"),
    ENVIRONMENT_MONITORING_SYSTEM("Системы мониторинга окружающей среды на объекте по обращению с ТКО"),
    TECHNICAL_SURVEY_STATUS("Статус проведения технического обследования (инвентаризации)"),
    TECHNICAL_SURVEY_RESULT("Результаты проведения технического обследования (инвентаризации)"),
    OBJECT_CATEGORY("Категория объекта"),
    WASTE_MANAGEMENT_TYPE("Вид обращения с отходами"),
    REGION("Субъекты РФ"),
    REGION_MUNICIPAL_NAME(" Наименование муниципального образования, входящего в зону деятельности регионального оператора"),
    REGION_MUNICIPAL_OKTMO("Код ОКТМО муниципального образования, входящего в зону деятельности регионального оператора"),
    REGIONAL_OPERATOR_ZONE_NAME("Наименование зоны деятельности регионального оператора"),
    DOT_DESCRIPTION("«Описание закрепления точки на местности» (Способы закрепления точек границы на местности)"),
    SECONDARY_RESOURCES("Вторичные материальные ресурсы (ВМР), извлекаемые объектами по обращению с ТКО"),
    UTILIZATION_TYPE("Вид утилизации"),
    WATERPROOFING_TYPE("Тип гидроизоляции участка размещения отходов"),
    OBJECT_REQUEST_STATUS("Статус анкеты-заявки"),
    IMPORT_STATUS("Статус импорта данных"),
    CONVEYOR("Конвееры"),
    SEPARATOR("Сепараторы"),
    PRESS("Прессы"),
    ADDITIONAL_EQUIPMENT("Дополнительное оборудование"),
    PLACEMENT_OBJECT_EQUIPMENT("Оборудование объектов размещения"),
    UTILIZATION_OBJECT_EQUIPMENT("Оборудование объектов утилизации"),
    NEUTRALIZATION_OBJECT_EQUIPMENT("Оборудование объектов обезвреживания"),
    EQUIPMENT_KIND("Вид оборудования"),
    PACKET_REAPER("Разрыватель пакетов"),
    NONFERROUS_MENATLS_SEPARATOR("Сепаратор цветных металлов"),
    BLACK_METALS_SEPARATOR("Сепаратор черных металлов"),
    OPTICAL_SEPARATOR("Оптический сепаратор"),
    EDDY_CURRENT_SEPARATOR("Вихретоковой сепаратор"),
    BALLISTIC_SEPARATOR("Баллистический сепаратор"),
    LIGHT_AND_HEAVY_FRACTIONS_SEPARATOR("Сепаратор легкой и тяжелой фракции"),
    VIBRATION_SEPARATOR("Вибрационный сепаратор"),
    PET_PERFORATOR("Перфоратор ПЭТ-тары"),
    OTHER_SEPARATOR("Иной сепаратор"),
    HORIZONTAL_PRESS("Горизонтальный пресс"),
    VERTICAL_PRESS("Вертикальный пресс"),
    PRES_COMPACTOR("Пресс-компактор"),
    PACKAGING_MACHINE("Упаковочная машина для ТКО"),
    GIVING("Подающий"),
    SORTING("Сортирующий"),
    REVERSE("Реверсивный"),
    GIVING_BMP("Подающий бмп"),
    OTHER("Другой"),
    DATA_SOURCE_NAME("Поставщик сведений"),
    UTILIZATION_OBJECT_EQUIPMENT_KIND("Вид оборудования объекта утилизации"),
    CORE_EQUIPMENT_TYPE("Модификация оборудования основного вида"),
    AUXILIARY_EQUIPMENT_TYPE("Модификация оборудования вспомогательного вида");


    private String displayName;

    ReferenceType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }
    
    public static ReferenceType valueOfDisplayName(String displayName) {
        for (ReferenceType e : values()) {
            if (e.displayName.equals(displayName)) {
                return e;
            }
        }
        return null;
    }
}
