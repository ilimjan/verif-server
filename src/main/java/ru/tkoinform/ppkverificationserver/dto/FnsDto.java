package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FnsDto {

    private String inn;
    private String kpp;
    private String ogrn;
    private String name;

    private String actualAddress;
    private String legalAddress;

    private String phone;
    private String email;
}
