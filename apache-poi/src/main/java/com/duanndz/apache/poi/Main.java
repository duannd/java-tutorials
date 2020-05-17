package com.duanndz.apache.poi;

import com.duanndz.apache.poi.models.Student;
import com.duanndz.apache.poi.services.DataService;
import com.duanndz.apache.poi.services.DataServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created By duan.nguyen at 5/17/20 8:39 AM
 */
@Slf4j
public class Main {

    public static void main(String[] args) throws IOException {
        // ----- Parse arg
        log.info("Args {}", (Object) args);
        String filePath = null;
        for(String arg: args) {
            if (arg.startsWith("--file=")) {
                filePath = arg.replace("--file=", "");
            }
        }
        log.info("Excel File: {}", filePath);
        if (filePath == null) {
            log.info("Excel File not existed");
            System.exit(1);
        }

        // Get files
        Path path = Paths.get(filePath);
        Files.deleteIfExists(path);
        Path excelFile = Files.createFile(path);
        log.info("Absolute file path: {}", excelFile.toAbsolutePath().toString());
        if (!excelFile.toFile().exists()) {
            log.info("Unable to create a excel file");
            System.exit(1);
        }

        Workbook wb = new XSSFWorkbook();
        Sheet student = wb.createSheet("Student");
        CellStyle headerStyle = createCellStyle(wb);
        int index = 0;
        bindingDataToExcel(student, index, new Object[] {"ID", "Name", "Email"}, headerStyle);
        index++;

        DataService dataService = new DataServiceImpl();
        List<Student> students = dataService.getStudents();
        for(Student std: students) {
            bindingDataToExcel(student, index, new Object[]{std.getId(), std.getName(), std.getEmail()}, null);
            index++;
        }

        try (FileOutputStream out = new FileOutputStream(excelFile.toFile())) {
            wb.write(out);
            log.info("Write data success");
        }

    }

    private static Row createExcelRow(Sheet sheet, int index) {
        return sheet.createRow(index);
    }

    private static void bindingDataToExcel(Sheet sheet, int rowIndex, Object[] data, CellStyle cellStyle) {
        Row row = createExcelRow(sheet, rowIndex);
        int idx = 0;
        for(Object o: data) {
            Cell cell = row.createCell(idx);
            cell.setCellValue(String.valueOf(o));
            if (cellStyle != null) {
                cell.setCellStyle(cellStyle);
            }
            idx++;
        }
    }

    private static CellStyle createCellStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREEN.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLUE.getIndex());
        style.setBorderTop(BorderStyle.MEDIUM_DASHED);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        return style;
    }

}
