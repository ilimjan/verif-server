package ru.tkoinform.ppkverificationserver.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;

@Getter
@Setter
@ExportName(name = "Сведения о статусе верификации данных об объекте по обращению с ТКО")
public class VerificationStatusInfoDto extends UuidEntityDto {

    @ExportName(name = "Создана карточка объекта по обращению с ТКО")
    private boolean isCardCreated;
    @ExportName(name = "Данные объекта по обращению с ТКО внесены оператором")
    private boolean infoByOperator;
    @ExportName(name = "Данные объекта по обращению с ТКО содержат сведения об объекте, собранные ППК РЭО")
    private boolean isFromPpk;
    @ExportName(name = "Данные объекта по обращению с ТКО верифицированы оператором по обращению с ТКО в рамках технического обследования")
    private boolean isVerifiedByOperator;
    @ExportName(name = "Данные объекта по обращению с ТКО получены из территориальной схемы субъекта РФ")
    private boolean isFromTerritorialScheme;
    @ExportName(name = "Комментарий")
    private String comment;
}
