package WebDriverUtils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {

    private static Workbook workbook;
    private static Sheet sheet;
    private static String filePath; // Store the file path globally

    /**
     * Loads the Excel workbook from the specified file path
     *
     * @param path Path to the Excel file
     */
    public static void loadWorkbook(String path) {
        filePath = path; // Store the file path
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File(filePath));
            workbook = new XSSFWorkbook(fileInputStream);
        } catch (IOException e) {
            handleException("Error loading Excel workbook", e);
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                handleException("Error closing FileInputStream", e);
            }
        }
    }

    /**
     * Gets the value from a cell in the Excel sheet
     *
     * @param sheetName  Name of the sheet
     * @param rowIndex   Row index (0-based)
     * @param columnIndex Column index (0-based)
     * @return Cell value as a String
     */
    public static String getCellValue(String sheetName, int rowIndex, int columnIndex) {
        try {
            sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rowIndex);
            Cell cell = row.getCell(columnIndex);
            return cell.toString();
        } catch (Exception e) {
            handleException("Error getting cell value", e);
            return null;
        }
    }

    /**
     * Sets the value to a cell in the Excel sheet
     *
     * @param sheetName  Name of the sheet
     * @param rowIndex   Row index (0-based)
     * @param columnIndex Column index (0-based)
     * @param value      Value to set
     */
    public static void setCellValue(String sheetName, int rowIndex, int columnIndex, String value) {
        try {
            sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                row = sheet.createRow(rowIndex);
            }
            Cell cell = row.createCell(columnIndex);
            cell.setCellValue(value);

            FileOutputStream outputStream = new FileOutputStream(new File(filePath));
            workbook.write(outputStream);
            outputStream.close();

        } catch (Exception e) {
            handleException("Error setting cell value", e);
        }
    }

    /**
     * Handles exceptions by logging the message and exception details
     *
     * @param message   Error message
     * @param exception Exception object
     */
    private static void handleException(String message, Exception exception) {
        System.err.println(message);
        exception.printStackTrace();
    }

    
}
