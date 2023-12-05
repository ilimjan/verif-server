package ru.tkoinform.ppkverificationserver.model.enums;


public enum ObjectFlowStatus implements LabeledEnum {

    // оператор
    DRAFT("Черновик"),
    // админ
    MODERATION("Модерация"),
    // аналитик
    AUDIT("Запрошена ревизия данных аналитиком"),
    // оператор
    TECHNICAL_SURVEY_REQUESTED("Ожидание отправки на техническое обследование"),
    // мобилка
    TECHNICAL_SURVEY_CREATED("Отправлено на техническое обследование"),
    // оператор
    TECHNICAL_SURVEY_ENDED("Техническое обследование завершено"),
    // готово
    VERIFIED("Верифицирован"),
    ARCHIVE("Архив"),
    // оператор
    REJECTED("Отклонено");

    private String displayName;

    ObjectFlowStatus(final String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
