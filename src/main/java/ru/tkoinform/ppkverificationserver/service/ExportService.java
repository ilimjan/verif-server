package ru.tkoinform.ppkverificationserver.service;

import com.google.zxing.WriterException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.tkoinform.ppkverificationserver.annotations.ExportIgnored;
import ru.tkoinform.ppkverificationserver.annotations.ExportName;
import ru.tkoinform.ppkverificationserver.dto.FileInfoDto;
import ru.tkoinform.ppkverificationserver.dto.FnsDto;
import ru.tkoinform.ppkverificationserver.dto.InfrastructureObjectDto;
import ru.tkoinform.ppkverificationserver.dto.ReferenceDto;
import ru.tkoinform.ppkverificationserver.dto.base.LabeledEnumDto;
import ru.tkoinform.ppkverificationserver.mapping.GlobalMapper;
import ru.tkoinform.ppkverificationserver.model.Reference;
import ru.tkoinform.ppkverificationserver.model.enums.ReferenceType;
import ru.tkoinform.ppkverificationserver.util.QrCodeUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExportService  {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final int FONT_SIZE = 12;
    private static final String FONT_NAME = "Arial";

    private final static DateFormat exportDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private final static String YES = "Да";
    private final static String NO = "Нет";

    @Autowired
    private GlobalMapper globalMapper;

    @Autowired
    private ReferenceService referenceService;

    @Autowired
    private InfrastructureObjectService infrastructureObjectService;

    @Transactional(readOnly = true)
    public File exportExcel(final UUID objectId, String sourceUrl) {
        InfrastructureObjectDto infrastructureObject = globalMapper.map(infrastructureObjectService.getInfrastructureObject(objectId), InfrastructureObjectDto.class);
        File file = new File(String.format("%s_%s.xlsx", infrastructureObject.getName(), exportDateFormat.format(new Date())));
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();

            CellStyle cellStyle = createCellStyle(workbook, false, null);
            CellStyle headerCellStyle = createCellStyle(workbook, true, IndexedColors.GREY_25_PERCENT.getIndex());

            exportObject(workbook, InfrastructureObjectDto.class, infrastructureObject, cellStyle, headerCellStyle, null);

            // Рисуем QR
            XSSFSheet pictureSheet = workbook.getSheetAt(0);
            byte[] qrCode = QrCodeUtils.generateQrCode(String.format("%s/request?requestId=%s", sourceUrl, infrastructureObject.getId().toString()));
            int pictureIdx = workbook.addPicture(qrCode, Workbook.PICTURE_TYPE_JPEG);
            CreationHelper helper = workbook.getCreationHelper();
            Drawing drawing = pictureSheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(0);
            anchor.setRow1(2);
            anchor.setCol2(1);
            anchor.setRow2(20);
            drawing.createPicture(anchor, pictureIdx);

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workbook.write(new FileOutputStream(file));
            fileOutputStream.close();
            workbook.close();
        } catch (IllegalAccessException | IOException | WriterException e) {
            e.printStackTrace();
            // Для отправки ошибки на фронт
            throw new IllegalStateException(e.getMessage());
        }

        return file;
    }
//
    @Transactional
    public void importExcel(final MultipartFile file, String regionId) {
        try {
            Boolean isClearIds = false;
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            Class objectClass = InfrastructureObjectDto.class;
            InfrastructureObjectDto infrastructureObject = new InfrastructureObjectDto();
            infrastructureObject = (InfrastructureObjectDto) importObject(workbook, objectClass, infrastructureObject, null, 1, isClearIds);

            infrastructureObjectService.addObject(infrastructureObject, regionId, true);
            workbook.close();
        } catch (IllegalAccessException | IOException | InstantiationException | ParseException | ClassNotFoundException | NoSuchFieldException e) {
            e.printStackTrace();
            // Для отправки ошибки на фронт
            throw new IllegalStateException(e.getMessage());
        }
    }

    /**
     * Рекурсивно проходится по вложенным полям и их полям, строя excel-файл
     *
     * @param workbook
     * @param objectClass
     * @param object
     * @throws IllegalAccessException
     */
    private void exportObject(XSSFWorkbook workbook, Class objectClass, Object object, CellStyle cellStyle, CellStyle headerCellStyle, String customName) throws IllegalAccessException {
        Field[] objectFields = getClassFields(objectClass);
        String sheetName = objectClass.getSimpleName();
        if (StringUtils.isEmpty(customName)) {
            ExportName classExportName = (ExportName) objectClass.getAnnotation(ExportName.class);
            if (classExportName != null) {
                sheetName = classExportName.name();
            }
        } else {
            sheetName = customName;
        }

        XSSFSheet classSheet = getOrCreateSheet(workbook, sheetName, objectFields, headerCellStyle);

        int cellNumber = 0;
        XSSFRow row = classSheet.createRow(classSheet.getPhysicalNumberOfRows());
        for (Field field : objectFields) {
            field.setAccessible(true);

            if (!isIgnoredField(field)) {
                if (!isFlatField(field)) {
                    // Рекурсивно оздаются новые таблицы для полей-таблиц
                    Class fieldObjectClass = getObjectClass(field);
                    if (fieldObjectClass != null) {
                        ExportName fieldExportName = field.getAnnotation(ExportName.class);
                        String fieldCustomName = fieldExportName != null ? fieldExportName.name() : null;
                        exportObjectOrCollection(workbook, fieldObjectClass, getFieldValueFromObject(field, object), cellStyle, headerCellStyle, fieldCustomName);
                    } else {
                        logger.warn("Field '{}' is empty", field.getName());
                    }
                } else {
                    // Пишутся "плоские поля"
                    if (object != null) {
                        Object fieldValue = getFieldValueFromObject(field, object);
                        XSSFCell cell = row.createCell(cellNumber);
                        cell.setCellStyle(cellStyle);
                        //cell.setCellValue(stringifyFieldValue(fieldValue));
                        setCellValue(cell, fieldValue);
                        //classSheet.autoSizeColumn(cellNumber);
                        cellNumber++;
                    }
                }
            }
        }
    }

    private void exportObjectOrCollection(XSSFWorkbook workbook, Class objectClass, Object objectOrCollection, CellStyle cellStyle, CellStyle headerCellStyle, String customName) throws IllegalAccessException {
        boolean isCollection = objectOrCollection != null && isCollection(objectOrCollection.getClass());
        if (isCollection) {
            Collection objectCollection = (Collection) objectOrCollection;
            for (Object objectInCollection : objectCollection) {
                exportObject(workbook, objectClass, objectInCollection, cellStyle, headerCellStyle, customName);
            }
        } else {
            exportObject(workbook, objectClass, objectOrCollection, cellStyle, headerCellStyle, customName);
        }
    }

    /**
     * Рекурсивно проходится по полям, пытаясь найти их в excel-файле
     *
     * @param workbook
     * @param objectClass
     * @param object
     * @param customName
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private Object importObject(XSSFWorkbook workbook, Class objectClass, Object object, String customName, int rowIndex, Boolean isClearIds) throws IllegalAccessException, InstantiationException, ParseException, NoSuchFieldException, ClassNotFoundException {
        XSSFSheet classSheet = getSheetByClass(workbook, objectClass, customName);

        if (classSheet == null) {
            logger.warn("Sheet for '{}' not found!", objectClass.getName());
            return object;
            //throw new IllegalStateException(String.format("Sheet for '%s' not found!", sheetName));
        }
        if (classSheet.getPhysicalNumberOfRows() == 1) {
            logger.warn("Sheet for '{}' is empty", objectClass.getName());
            return null;
        }

        Field[] objectFields = getClassFields(object.getClass());
        for (Field field : objectFields) {
            field.setAccessible(true);
            ExportName fieldExportName = field.getAnnotation(ExportName.class);

            if (!isIgnoredField(field)) {
                Class fieldClass = field.getType();

                if (!isFlatField(field)) {
                    // Поле-таблица, рекурсиво вызываем эту же функцию

                    Class fieldObjectClass = getObjectClass(field);
                    Object fieldObject;
                    if (isCollection(fieldClass)) {
                        fieldObject = new ArrayList<>();
                    } else {
                        fieldObject = fieldObjectClass.newInstance();
                    }
                    String fieldCustomName = fieldExportName != null ? fieldExportName.name() : null;

                    fieldObject = importObjectOrCollection(workbook, fieldObjectClass, fieldObject, fieldCustomName, isClearIds);
                    field.set(object, fieldObject);
                } else {

                    // Плоское поле
                    Object fieldValue = importFlatField(classSheet, field, rowIndex, objectClass);
                    if(objectClass.getSimpleName().contains("InfrastructureObjectDto")){
                        if(field.getName().contains("id") && fieldValue == null){
                            isClearIds = true;
                        }
                    }

                    if(isClearIds && !objectClass.getSimpleName().contains("InfrastructureObjectDto")
                       && field.getName().contains("id")){
                        if(objectClass != ReferenceDto.class){
                            fieldValue = null;
                        }
                    }
                    field.set(object, fieldValue);
                }
            }
        }

        return object;
    }

    private Object importObjectOrCollection(XSSFWorkbook workbook, Class objectClass, Object objectOrCollection, String customName, Boolean isClearIds) throws IllegalAccessException, InstantiationException, ParseException, NoSuchFieldException, ClassNotFoundException {
        if (isCollection(objectOrCollection.getClass())) {
            XSSFSheet classSheet = getSheetByClass(workbook, objectClass, customName);
            if (classSheet == null) {
                logger.warn("Sheet for '{}' not found!", objectClass.getName());
                return objectOrCollection;
                //throw new IllegalStateException(String.format("Sheet for '%s' not found!", sheetName));
            }

            for (int i = 1; i < classSheet.getPhysicalNumberOfRows(); i++) {
                Object objectOfCollection = objectClass.newInstance();
                objectOfCollection = importObject(workbook, objectClass, objectOfCollection, customName, i, isClearIds);
                ((Collection) objectOrCollection).add(objectOfCollection);
            }

            return objectOrCollection;

        } else {
            objectOrCollection = importObject(workbook, objectClass, objectOrCollection, customName, 1, isClearIds);
            return objectOrCollection;
        }
    }

    private Object importFlatField(XSSFSheet classSheet, Field field, int rowIndex, Class objectClass) throws ParseException, NoSuchFieldException, ClassNotFoundException {
        XSSFRow headRow = classSheet.getRow(0);
        XSSFRow row = classSheet.getRow(rowIndex);

        Class fieldClass = getObjectClass(field);

        if (row != null) {
            ExportName fieldExportName = field.getAnnotation(ExportName.class);

            String fieldExportNameString = fieldExportName.name();

            for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
                XSSFCell headCell = headRow.getCell(i);
                String headValue = headCell.getStringCellValue();

                if (!StringUtils.isEmpty(fieldExportNameString)) {
                    if (headValue.equals(fieldExportNameString)) {
                        XSSFCell cell = row.getCell(i);
                        return getStringValue(cell, fieldClass, fieldExportNameString, field.getName(), objectClass);
                    }
                } else {
                    logger.warn("Empty head cell at {}!", classSheet.getSheetName());
                }
            }
        } else {
            logger.error("Row {} not found for '{}'", rowIndex, classSheet.getSheetName());
        }

        return defaultValue(fieldClass);
    }

    private CellStyle createCellStyle(XSSFWorkbook workbook, boolean bold, Short background) {
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) FONT_SIZE);
        font.setFontName(FONT_NAME);
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBold(bold);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);

        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);

        if (background != null) {
            FillPatternType fillPattern = FillPatternType.SOLID_FOREGROUND;
            cellStyle.setFillPattern(fillPattern);
            cellStyle.setFillBackgroundColor(background);
            cellStyle.setFillForegroundColor(background);
        }

        return cellStyle;
    }

    private boolean isCollection(Class c) {
        return Collection.class.isAssignableFrom(c) || c == List.class;
    }

    private Object getFieldValueFromObject(Field field, Object object) throws IllegalAccessException {
        return object == null ? null : field.get(object);
    }

    private void setCellValue(XSSFCell cell, Object fieldValue) {
        if (fieldValue != null) {
            Class fieldClass = fieldValue.getClass();

            if (fieldClass == Double.class) {
                cell.setCellValue((Double) fieldValue);
            } else if (fieldClass == double.class) {
                cell.setCellValue((double) fieldValue);
            } else if (fieldClass == Float.class) {
                cell.setCellValue((Float) fieldValue);
            } else if (fieldClass == float.class) {
                cell.setCellValue((float) fieldValue);
            } else if (fieldClass == Integer.class) {
                cell.setCellValue((Integer) fieldValue);
            } else if (fieldClass == int.class) {
                cell.setCellValue((int) fieldValue);
            } else if (fieldClass == Long.class) {
                cell.setCellValue((Long) fieldValue);
            } else if (fieldClass == long.class) {
                cell.setCellValue((long) fieldValue);
            /*
            } else if (fieldClass == Boolean.class) {
                cell.setCellValue((Boolean) fieldValue);
            } else if (fieldClass == boolean.class) {
                cell.setCellValue((boolean) fieldValue);
            } else if (fieldClass == Timestamp.class || fieldClass == Date.class) {
                cell.setCellValue((Timestamp) fieldValue);
            */
            } else {

                cell.setCellValue(stringifyFieldValue(fieldValue));
            }
        }
    }

    private String stringifyFieldValue(Object fieldValue) {
        if (fieldValue != null) {
            Class fieldClass = fieldValue.getClass();

            if (fieldClass == Boolean.class || fieldClass == boolean.class) {
                Boolean booleanValue = (Boolean) fieldValue;
                if (Boolean.TRUE.equals(booleanValue)) {
                    return YES;
                } else if (Boolean.FALSE.equals(booleanValue)) {
                    return NO;
                } else {
                    return null;
                }

            } else if (fieldClass == LabeledEnumDto.class) {
                LabeledEnumDto labeledEnumValue = (LabeledEnumDto) fieldValue;
                return labeledEnumValue.getDisplayName();

            } else if (fieldClass == ReferenceDto.class) {
                ReferenceDto referenceValue = (ReferenceDto) fieldValue;
                return referenceValue.getValue();

            } else if (fieldClass == Timestamp.class || fieldClass == Date.class) {
                Timestamp dateValue = (Timestamp) fieldValue;
                return exportDateFormat.format(dateValue);

            } else {
                return String.valueOf(fieldValue);
            }
        }

        return null;
    }

    private Object getStringValue(XSSFCell cell, Class fieldClass, String fieldName, String fieldNameOriginal, Class objectClass) throws ParseException, NoSuchFieldException, ClassNotFoundException {
        logger.info("Importing cell: {}", fieldName);
        if (fieldClass == Double.class) {
            return cell.getNumericCellValue();
        } else if (fieldClass == double.class) {
            return cell.getNumericCellValue();
        } else if (fieldClass == Float.class) {
            return (float) cell.getNumericCellValue();
        } else if (fieldClass == float.class) {
            return (float) cell.getNumericCellValue();
        } else if (fieldClass == Integer.class) {
            return (int) cell.getNumericCellValue();
        } else if (fieldClass == int.class) {
            return (int) cell.getNumericCellValue();
        } else if (fieldClass == Long.class) {
            return (long) cell.getNumericCellValue();
        } else if (fieldClass == long.class) {
            return (long) cell.getNumericCellValue();
        /*
        } else if (fieldClass == Boolean.class) {
            return cell.getBooleanCellValue();
        } else if (fieldClass == boolean.class) {
            return cell.getBooleanCellValue();
        } else if (fieldClass == Timestamp.class || fieldClass == Date.class) {
            return cell.getDateCellValue();
        */
        } else if (fieldClass == String.class) {
            // Хак. Бывает что вводят строку как число в excel (если она содержит только цифры)
            if (CellType.NUMERIC.equals(cell.getCellType())) {
                return String.valueOf(cell.getNumericCellValue());
            } else {
                return cell.getStringCellValue();
            }
        } else {
            return parseCellValue(cell.getStringCellValue(), fieldClass, fieldName, fieldNameOriginal, objectClass);
        }
    }

    private Object parseCellValue(String cellValue, Class fieldClass, String fieldName, String fieldNameOriginal, Class objectClass) throws ClassNotFoundException, NoSuchFieldException, ParseException {
        if (StringUtils.isEmpty(cellValue)) {
            return defaultValue(fieldClass);
        /*
        } else if (fieldClass == Double.class || fieldClass == double.class) {
            return Double.valueOf(cellValue);

        } else if (fieldClass == Float.class || fieldClass == float.class) {
            return Float.valueOf(cellValue);

        } else if (fieldClass == Integer.class || fieldClass == int.class) {
            return Integer.valueOf(cellValue);

        */
        } else if (fieldClass == Boolean.class || fieldClass == boolean.class) {
            if (cellValue.trim().toLowerCase().equals(YES.toLowerCase())) {
                return Boolean.TRUE;
            } else if (cellValue.trim().toLowerCase().equals(NO.toLowerCase())) {
                return Boolean.FALSE;
            } else {
                return null;
            }

        } else if (fieldClass == LabeledEnumDto.class) {

            //Class nonDtoClass = Class.forName(Reference.class.getName());
            Class nonDtoClass = Class.forName("ru.tkoinform.ppkverificationserver.model." + objectClass.getSimpleName().replace("Dto", ""));
            Field field  = nonDtoClass.getDeclaredField(fieldNameOriginal);
            Class enumClass = field.getType();

            //Class nonDtoClass = Class.forName(fieldClass.getName().replace("Dto", ""));
            //Class nonDtoClass = Class.forName(fieldClass.getName());
            //Field field = nonDtoClass.getDeclaredField(fieldNameOriginal);
            //Field field  = nonDtoClass.getField(fieldName);
            //Class enumClass = field.getType();



            if (enumClass.isEnum()) {
                // TODO: сейчас таких полей нет так что в общем-то это и не нужно
                //LabeledEnumDto labeledEnumValue = (LabeledEnumDto) fieldValue;
                //return labeledEnumValue.getDisplayName();
                //Enum e = Enum.valueOf(enumClass, ReferenceType.);
                //Enum e = Enum.valueOf(LabeledEnumDto.class, ReferenceType.WASTE_CATEGORY);
                //Enum e = Enum.valueOf(ReferenceType.class, ReferenceType.values().st);
                if(enumClass.getSimpleName().equals("ReferenceType")) {
                    return globalMapper.map(ReferenceType.valueOfDisplayName(cellValue), fieldClass);
                }
            }
            //Enum e = Enum.valueOf(LabeledEnumDto.class, cellValue);
            return null;

        } else if (fieldClass == ReferenceDto.class) {
            Reference reference = referenceService.getReferenceByValue(cellValue);
            if (reference != null) {
                return globalMapper.map(reference, ReferenceDto.class);
            }
            return null;

        } else if (fieldClass == Date.class || fieldClass == Timestamp.class) {
            return exportDateFormat.parse(cellValue);

        } else if (fieldClass == UUID.class) {
            return UUID.fromString(cellValue);

        } else if (fieldClass == String.class) {
            return cellValue;

        } else {
            logger.error("Unknown class on import: " + fieldClass);
        }

        return null;
    }

    private Object defaultValue(Class fieldClass) {
        if (fieldClass.isPrimitive()) {
            if (fieldClass == boolean.class) {
                return false;
            } else if (fieldClass == double.class || fieldClass == float.class || fieldClass == int.class) {
                return 0;
            }
        }
        return null;
    }

    private Class getObjectClass(Field field) {
        Class<?> objectClass = field.getType();
        if (isCollection(objectClass)) {
            ParameterizedType collectionType = (ParameterizedType) field.getGenericType();
            Class<?> collectionClass = (Class<?>) collectionType.getActualTypeArguments()[0];
            return collectionClass;
        }
        return objectClass;
    }

    private XSSFSheet getOrCreateSheet(XSSFWorkbook workbook, String rawName, Field[] objectFields, CellStyle headerCellStyle) {
        String name = getSheetName(rawName);
        XSSFSheet sheet = workbook.getSheet(name);
        if (sheet == null) {
            sheet = workbook.createSheet(name);

            // Рисуем шапку таблицы
            XSSFRow headRow = sheet.createRow(sheet.getPhysicalNumberOfRows());
            int cellCount = 0;
            for (Field field : objectFields) {
                if (!isIgnoredField(field)) {
                    if (isFlatField(field)) {
                        ExportName fieldExportName = field.getAnnotation(ExportName.class);

                        XSSFCell cell = headRow.createCell(cellCount++);
                        cell.setCellStyle(headerCellStyle);

                        String value = field.getName();
                        if (fieldExportName != null) {
                            value = fieldExportName.name();
                        }

                        cell.setCellValue(value);
                    }
                }
            }
        }

        return sheet;
    }

    private XSSFSheet getSheetByClass(XSSFWorkbook workbook, Class objectClass, String customName) {
        String sheetName = objectClass.getSimpleName();
        if (StringUtils.isEmpty(customName)) {
            ExportName classExportName = (ExportName) objectClass.getAnnotation(ExportName.class);
            if (classExportName != null) {
                sheetName = classExportName.name();
            }
        } else {
            sheetName = customName;
        }

        return workbook.getSheet(getSheetName(sheetName));
    }

    private Field[] getClassFields(Class objectClass) {
        Class superclass = objectClass.getSuperclass();
        Field[] objectFields = objectClass.getDeclaredFields();
        if (superclass != null) {
            objectFields = ArrayUtils.addAll(superclass.getDeclaredFields(), objectFields);
        }
        return objectFields;
    }

    private String getSheetName(String rawName) {
        String name;
        if (rawName.length() > 31) {
            // TODO: алгоритм сокращения строк?
            name = rawName.substring(0, 31);
            logger.warn("Sheet name can not be longer than 31 symbols! Truncating '{}' to '{}'", rawName, name);
        } else {
            name = rawName;
        }
        return name;
    }

    private boolean isEntityField(Field field) {
        return getObjectClass(field).getName().contains("tkoinform");
    }

    private boolean isIgnoredField(Field field) {
        Class fieldClass = field.getType();
        return fieldClass == FileInfoDto.class || field.getAnnotation(ExportIgnored.class) != null;
    }

    private boolean isFlatEntityField(Field field) {
        Class fieldClass = field.getType();
        return fieldClass == ReferenceDto.class || fieldClass == LabeledEnumDto.class;
    }

    private boolean isFlatField(Field field) {
        return !isEntityField(field) || isFlatEntityField(field);
    }
}
