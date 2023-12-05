package ru.tkoinform.ppkverificationserver.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class UuidEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

}
