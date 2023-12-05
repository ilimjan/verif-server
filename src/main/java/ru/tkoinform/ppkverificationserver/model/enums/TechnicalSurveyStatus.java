package ru.tkoinform.ppkverificationserver.model.enums;

public enum TechnicalSurveyStatus implements LabeledEnum {
    NOT_STARTED("Не начато"),
    IN_PROGRESS("В процессе"),
    COMPLETED("Выполнено");

    private String displayName;

    TechnicalSurveyStatus(final String displayName) {
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
