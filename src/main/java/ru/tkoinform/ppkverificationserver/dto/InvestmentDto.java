package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

@Getter
@Setter
@ExportName(name = "Инвестор")
public class InvestmentDto extends UuidEntityDto {

    //private boolean hasInvestor;
    @ExportName(name = "Наименование инвестора объекта по обращению с ТКО")
    private String name;
    @ExportName(name = "ИНН инвестора объекта по обращению с ТКО")
    private String inn;
    @ExportName(name = "КПП инвестора объекта по обращению с ТКО")
    private String kpp;
    @ExportName(name = "ОГРН инвестора объекта по обращению с ТКО")
    private String ogrn;
    //private String ogrnip;
    @ExportName(name = "Юридический адрес инвестора объекта по обращению с ТКО")
    private String legalAddress;
    @ExportName(name = "Фактический адрес инвестора объекта по обращению с ТКО")
    private String actualAddress;
    @ExportName(name = "Телефон/факс инвестора объекта по обращению с ТКО")
    private String phone;
    @ExportName(name = "Интернет сайт инвестора объекта по обращению с ТКО")
    private String website;
    @ExportName(name = "Электронная почта инвестора объекта по обращению с ТКО")
    private String email;
    @ExportName(name = "ФИО руководителя инвестора объекта по обращению с ТКО")
    private String directorFio;
    @ExportName(name = "ФИО уполномоченного представителя инвестора объекта по обращению с ТКО")
    private String representatioveFio;
    @ExportName(name = "Должность уполномоченного представителя инвестора объекта по обращению с ТКО")
    private String representatiovePosition;
    @ExportName(name = "Опыт работы инвестора в сфере обращения с отходами")
    private String experience;
    @ExportName(name = "Полная сумма инвестиций для строительства и запуска объекта в промышленную эксплуатацию")
    private Double fullInvestmentAmount;
    @ExportName(name = "Сумма собственных вложений инвестора")
    private Double personalInvestmentAmount;
    @ExportName(name = "Планируемый срок окупаемости объекта")
    private Double plannedPaybackPeriod;
    @ExportName(name = "Необходимая валовая выручка")
    private Double requiredGrossRevenue;
    //private Boolean hasAgreementOfIntent;
    @ExportName(name = "Реквизиты соглашения о намерениях")
    private String agreementRequisites;
    @ExportName(name = "Риски реализации проекта (в графе перечисляются потенциальные риски реализации проекта)")
    private String risks;
    @ExportName(name = "Конкурентные преимущества проекта (указать какие именно)")
    private String competitiveAdvantages;
    @ExportName(name = "Сотовый телефон инвестора объекта по обращению с ТКО")
    private String mobile;
}
