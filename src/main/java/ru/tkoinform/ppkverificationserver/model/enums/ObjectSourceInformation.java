package ru.tkoinform.ppkverificationserver.model.enums;

public enum ObjectSourceInformation implements LabeledEnum{

    ARM("АРМ"),

    IMPORTED("Импортированный");

    private String displayName;

    ObjectSourceInformation(final String displayName) {
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
