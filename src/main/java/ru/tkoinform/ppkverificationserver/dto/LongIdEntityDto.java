package ru.tkoinform.ppkverificationserver.dto;

import ru.tkoinform.ppkverificationserver.annotations.ExportName;

public abstract class LongIdEntityDto {

    @ExportName(name = "ID (Число)")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

