package common;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;

public class ExcelHelpers {
    private static Logger logger = LoggerHelpers.getLogger();
    private FileInputStream fileIn;
    private FileOutputStream fileOut;
    private Workbook workbook;
    private Sheet sheet;
    private Cell cell;
    private Row row;
    private CellStyle cellStyle;
    private Color color;
    private String excelFilePath;
    private final Map<String, Integer> columns = new HashMap<>();

    //Setup
    public void setExcelFile(String ExcelPath, String SheetName){
        try {
            File file = new File(ExcelPath);

            if(!file.exists()){
                file.createNewFile();
                logger.info("create New File");
            }

            fileIn = new FileInputStream(ExcelPath);
            workbook = WorkbookFactory.create(fileIn);
            sheet = workbook.getSheet(SheetName);

            if(sheet == null){
                sheet = workbook.createSheet(SheetName);
            }
            this.excelFilePath = ExcelPath;

            sheet.getRow(0).forEach(cell -> {
                columns.put(cell.getStringCellValue(), cell.getColumnIndex());
            });
        } catch (Exception error){
            logger.info(error.getMessage());
        }
    }

    //Lấy data trong file excel
    public String getCellData(int row_num, int col_num) throws Exception{
        try {
            cell = sheet.getRow(row_num).getCell(col_num);
            String CellData = null;
            switch (cell.getCellType()){
                case STRING:
                    CellData = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)){
                        CellData = String.valueOf(cell.getDateCellValue());
                    } else {
                        CellData = String.valueOf((long)cell.getNumericCellValue());
                    }
                    break;
                case BOOLEAN:
                    CellData = Boolean.toString(cell.getBooleanCellValue());
                    break;
                case BLANK:
                    CellData = "";
                    break;
            }
            return CellData;
        } catch (Exception error){
            return "";
        }
    }
    public String getCellData(String columnName ,int row_num) throws Exception{
        return getCellData(row_num, columns.get(columnName));
    }

    // Ghi data vào file excel
    public void setCellData(String text, int row_num ,int col_num) throws Exception{
        try {
            row = sheet.getRow(row_num);
            if(row == null){
                row = sheet.createRow(row_num);
            }
            cell = row.getCell(col_num);

            if(cell == null){
                cell = row.createCell(row_num);
            }
            cell.setCellValue(text);

            fileOut = new FileOutputStream(excelFilePath);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception error){
            throw(error);
        }
    }
}
