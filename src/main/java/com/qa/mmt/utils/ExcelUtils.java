package com.qa.mmt.utils;

import com.qa.mmt.base.BaseInitializer;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;

public class ExcelUtils extends BaseInitializer {

    private static Logger log = LogManager.getLogger(ExcelUtils.class.getName());

    public static XSSFRow row;
    public static Cell cell;
    public static FileInputStream fin = null;
    public static XSSFWorkbook workbook = null;
    public static XSSFSheet worksheet = null;
    public static FileOutputStream fout = null;



    /**
     * For fetching excel path from dynamic file handling with different module
     */
    public void updateModuleTestSpecificRecordRunTestData(String module) {
        File file = null;
        try {
            if (module.equalsIgnoreCase("TICKET_BOOKING")) {
                file = findExcelFile(MMT_TestSpecificRecordData);
                MMT_TestSpecificRecordData = String.valueOf(Paths.get(String.valueOf(file)));
            }
            if (file != null) {
                fin = new FileInputStream(file);
                workbook = new XSSFWorkbook(fin);
                int sheetCnt = workbook.getNumberOfSheets();
                for (int sheetNo = 0; sheetNo < sheetCnt; sheetNo++) {
                    String sheetName = workbook.getSheetName(sheetNo);
                    if (!sheetName.equalsIgnoreCase("DataPicker")) {
                        worksheet = workbook.getSheetAt(sheetNo);
                        int rowCtr = worksheet.getLastRowNum();
                        for (int startRow = 1; startRow <= rowCtr; startRow++) {
                            row = worksheet.getRow(startRow);
                            cell = row.getCell(0);
                            String testCaseName = cell.getStringCellValue();
                            if (StringUtils.equalsIgnoreCase(testCaseName, currentTestCaseName)) {
                                row.createCell(1).setCellValue(workbook.getCreationHelper().createRichTextString("Yes"));
                            } else {
                                row.createCell(1).setCellValue(workbook.getCreationHelper().createRichTextString("No"));
                            }
                            fout = new FileOutputStream(file);
                            workbook.write(fout);
                        }
                        fout.flush();
                        fout.close();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception in updateModuleTestSpecificRecordRunTestData: " + e);
        }
    }
    /**
     * For dynamic excel file name code
     */
    private File findExcelFile(String folderPath) {
        File excelFolder = new File(folderPath);
        File[] files = excelFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".xlsx"));
        deleteTempFiles(excelFolder);
        if (files != null && files.length == 1) {
            return files[0];
        } else {
            System.out.println("Error: Either no Excel file found or more than one file in the specified folder.");
            return null;
        }
    }

    /**
     * For deleting Temp Files
     */
    private static void deleteTempFiles(File folder) {
        File[] tempFiles = folder.listFiles((dir, name) -> name.startsWith("~$") && name.toLowerCase().endsWith(".xlsx"));
        if (tempFiles != null) {
            Arrays.stream(tempFiles).forEach(tempFile -> {tempFile.delete();});
        }
    }

    /**
     * For updating TestStatus For Pass/Fail/skipped
     */
    public void updateTestStatusForPassFail(String module, String status) {
        File file;
        try {
            String filePath = "";
            if (module.equalsIgnoreCase("ECommerce")) {
                filePath = MMT_TestSpecificRecordData;
            }

            if (!filePath.isEmpty()) {
                file = new File(filePath);
                if (file.exists()) {
                    fin = new FileInputStream(file);
                    workbook = new XSSFWorkbook(fin);
                    int sheetCnt = workbook.getNumberOfSheets();
                    for (int sheetNo = 0; sheetNo < sheetCnt; sheetNo++) {
                        String sheetName = workbook.getSheetName(sheetNo);
                        if (!sheetName.equalsIgnoreCase("DataPicker")) {
                            worksheet = workbook.getSheetAt(sheetNo);
                            int rowCtr = worksheet.getLastRowNum();
                            Row headerRow = worksheet.getRow(0);
                            int statusColumnIndex = findColumnIndex(headerRow, "Test Status");
                            if (statusColumnIndex != -1) {
                                for (int startRow = 1; startRow <= rowCtr; startRow++) {
                                    row = worksheet.getRow(startRow);
                                    if (row != null) {
                                        cell = row.getCell(0); // Assuming method is in the first column
                                        String method = cell.getStringCellValue();
                                        if (method != null && method.equalsIgnoreCase(currentTestCaseName)) {
                                            Cell statusCell = row.createCell(statusColumnIndex);
                                            statusCell.setCellValue(status);
                                            CellStyle style = workbook.createCellStyle();
                                            Font font = workbook.createFont();
                                            if (status.equalsIgnoreCase("Passed")) {
                                                font.setColor(IndexedColors.BLACK.getIndex());
                                                font.setBold(true);
                                                style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                                            } else if(status.equalsIgnoreCase("Failed")) {
                                                font.setColor(IndexedColors.BLACK.getIndex());
                                                font.setBold(true);
                                                style.setFillForegroundColor(IndexedColors.RED.getIndex());
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                                            } else{
                                                font.setColor(IndexedColors.BLACK.getIndex());
                                                font.setBold(true);
                                                style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            }
                                            style.setFont(font);
                                            statusCell.setCellStyle(style);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                fin.close();
                fout = new FileOutputStream(file);
                workbook.write(fout);
                fout.close();
            } else {
                System.out.println("File does not exist: " + filePath);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private int findColumnIndex(Row headerRow, String columnName) {
        int columnCount = headerRow.getPhysicalNumberOfCells();
        for (int i = 0; i < columnCount; i++) {
            Cell cell = headerRow.getCell(i);
            if (cell != null && cell.getCellType() == CellType.STRING && columnName.equalsIgnoreCase(cell.getStringCellValue().trim())) {
                return i;
            }
        }
        return -1; // Column not found
    }
}
