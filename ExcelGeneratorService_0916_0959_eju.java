// 代码生成时间: 2025-09-16 09:59:42
package com.example.demo.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExcelGeneratorService {

    private static final String EXCEL_FILE_EXTENSION = ".xlsx";

    public Workbook generateExcelWorkbook(List<String> data) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Generated Data");
        Row headerRow = sheet.createRow(0);

        // Create header cells
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Data");

        int rowNum = 1;
        for (String cellData : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(rowNum - 1);
            row.createCell(1).setCellValue(cellData);
        }

        return workbook;
    }

    public byte[] generateExcelFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // Check if the file is a valid Excel file
        if (!file.getOriginalFilename().toLowerCase().endsWith(EXCEL_FILE_EXTENSION)) {
            throw new IllegalArgumentException("File format is not supported");
        }

        // Convert MultipartFile to byte array
        byte[] fileContent = file.getBytes();

        // You can add more processing here if needed

        return fileContent;
    }

    // Other methods can be added here for additional Excel operations
}