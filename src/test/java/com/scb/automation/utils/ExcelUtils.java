package com.scb.automation.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;

public class ExcelUtils {

    public static Object[][] getLoginData() {

        Object[][] data = null;

        try {
            InputStream inputStream =
                    ExcelUtils.class.getClassLoader()
                            .getResourceAsStream("testdata/LoginData.xlsx");

            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet("LOGIN_DATA");

            int rows = sheet.getPhysicalNumberOfRows();
            int cols = sheet.getRow(0).getPhysicalNumberOfCells();

            data = new Object[rows - 1][cols - 1];

            for (int i = 1; i < rows; i++) {
                Row row = sheet.getRow(i);
                for (int j = 1; j < cols; j++) {
                    data[i - 1][j - 1] =
                            row.getCell(j).getStringCellValue();
                }
            }

            workbook.close();

        } catch (Exception e) {
            throw new RuntimeException("Failed to read LoginData.xlsx", e);
        }

        return data;
    }
}
