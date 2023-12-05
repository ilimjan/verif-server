package ru.tkoinform.ppkverificationserver.model.enums;

public enum ObjectUpdateSource implements LabeledEnum{

    ARM("АРМ"),

    MP("МП");

    private String displayName;

    ObjectUpdateSource(final String displayName) {
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
