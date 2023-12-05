package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class UuidEntityNotGenerated {

    @Id
    private UUID id;

}
