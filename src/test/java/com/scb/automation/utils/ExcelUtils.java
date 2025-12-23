package com.scb.automation.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;

/**
 * Utility class to read test data from Excel files.
 * This class is mainly used by TestNG DataProviders
 * to supply multiple sets of login credentials to test cases.
 */
public class ExcelUtils {

    /**
     * Reads login data from LoginData.xlsx file
     * and returns it in Object[][] format required by TestNG @DataProvider.
     */
    public static Object[][] getLoginData() {

        /*
         * Load the Excel file from classpath.
         * In both local execution and Azure pipeline,
         * files inside src/test/resources are available in classpath.
         */
        try (InputStream inputStream =
                     ExcelUtils.class
                             .getClassLoader()
                             .getResourceAsStream("testdata/LoginData.xlsx")) {

            // Safety check: if file is missing, fail fast with clear error
            if (inputStream == null) {
                throw new RuntimeException("LoginData.xlsx NOT found in classpath");
            }

            /*
             * Create Workbook object from the Excel file.
             * XSSFWorkbook is used because the file is .xlsx format.
             */
            Workbook workbook = new XSSFWorkbook(inputStream);

            /*
             * Get the specific sheet that contains login data.
             * Sheet name must EXACTLY match Excel sheet name.
             */
            Sheet sheet = workbook.getSheet("LOGIN_DATA");

            /*
             * Get total number of rows present in the sheet.
             * Includes header row.
             */
            int rows = sheet.getPhysicalNumberOfRows();

            /*
             * Get number of columns from the header row (row index 0).
             */
            int cols = sheet.getRow(0).getPhysicalNumberOfCells();

            /*
             * Create Object[][] array.
             * rows - 1 → exclude header row
             * cols - 1 → exclude first column (e.g. test case id)
             *
             * This format is REQUIRED by TestNG DataProvider.
             */
            Object[][] data = new Object[rows - 1][cols - 1];

            /*
             * Start loop from row index 1 to skip header.
             * Each row represents one test data set.
             */
            for (int i = 1; i < rows; i++) {
                Row row = sheet.getRow(i);

                /*
                 * Start column loop from index 1 to skip first column.
                 * Example:
                 * Column 0 → TestCaseId
                 * Column 1 → Username
                 * Column 2 → Password
                 */
                for (int j = 1; j < cols; j++) {
                    data[i - 1][j - 1] =
                            row.getCell(j).getStringCellValue();
                }
            }

            /*
             * Close workbook to avoid memory leaks.
             */
            workbook.close();

            // Return prepared data to TestNG DataProvider
            return data;

        } catch (Exception e) {
            /*
             * Wrap checked exceptions into RuntimeException
             * so that test execution fails clearly
             * both locally and in pipeline.
             */
            throw new RuntimeException("Failed to read LoginData.xlsx", e);
        }
    }
}