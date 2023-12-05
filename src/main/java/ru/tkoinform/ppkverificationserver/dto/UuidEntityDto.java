package ru.tkoinform.ppkverificationserver.dto;

import ru.tkoinform.ppkverificationserver.annotations.ExportName;

import java.util.UUID;

public abstract class UuidEntityDto {

    @ExportName(name = "ID (UUID)")
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
