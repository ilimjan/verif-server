package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

@Getter
@Setter
@ExportName(name = "Оператор")
public class OperatorDto extends UuidEntityDto {

    @ExportName(name = "Наименование оператора объекта по обращению с ТКО")
    private String name;
    @ExportName(name = "ИНН оператора объекта по обращению с ТКО")
    private String inn;
    @ExportName(name = "КПП оператора объекта по обращению с ТКО")
    private String kpp;
    @ExportName(name = "ОГРН оператора объекта по обращению с ТКО")
    private String ogrn;
    //private String ogrnip;
    @ExportName(name = "Юридический адрес оператора объекта по обращению с ТКО")
    private String legalAddress;
    @ExportName(name = "Фактический адрес оператора объекта по обращению с ТКО")
    private String actualAddress;
    @ExportName(name = "Телефон/факс инвестора объекта по обращению с ТКО")
    private String phone;
    @ExportName(name = "Сотовый телефон инвестора объекта по обращению с ТКО")
    private String email;
    @ExportName(name = "ФИО руководителя инвестора объекта по обращению с ТКО")
    private String directorFio;
    @ExportName(name = "ФИО уполномоченного представителя оператора объекта по обращению с ТКО")
    private String representativeOfOperatorFio;
    @ExportName(name = "Должность уполномоченного представителя оператора объекта по обращению с ТКО")
    private String representativeOfOperatorPosition;
    @ExportName(name = "Сотовый телефон оператора объекта по обращению с ТКО")
    private String mobile;
}
