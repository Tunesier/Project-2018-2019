package Extern;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author yashuai
 */
public class ReadWriteExcel {
    /**
     * 
     * @param path
     * @throws IOException 
     */
    public static void readXLSFile(String path) throws IOException {
        
        InputStream ExcelFileRead = new FileInputStream(path);
        HSSFWorkbook wb = new HSSFWorkbook(ExcelFileRead);
        
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row;
        HSSFCell cell;
        
        Iterator rows = sheet.rowIterator();
        
        while (rows.hasNext()) {
            row = (HSSFRow) rows.next();
            Iterator cells = row.cellIterator();
            
            while (cells.hasNext()) {
                cell = (HSSFCell) cells.next();
                
                if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                    System.out.print(cell.getStringCellValue() + " ");
                } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                    System.out.print(cell.getNumericCellValue() + " ");
                } else {
                    // can Handle Boolean, formula, errors
                }
            }
            System.out.println();
        }
    }
    
    public static String[][] readXLSFileToArray(String path) throws FileNotFoundException, IOException {
        String[][] contentMatrix;
        HSSFRow row;
        HSSFCell cell;
        
        InputStream ExcelFileRead = new FileInputStream(path);
        HSSFWorkbook wb = new HSSFWorkbook(ExcelFileRead);
        //int sheets = wb.getNumberOfSheets();
        HSSFSheet sheet = wb.getSheetAt(0);
        int rowNum = sheet.getPhysicalNumberOfRows();
        int cellNum = sheet.getRow(0).getPhysicalNumberOfCells();
        System.out.println(rowNum + "   " + cellNum);
        contentMatrix = new String[rowNum][cellNum];
        for (int i = 0; i < rowNum; i++) {
            row = sheet.getRow(i);
            if (row != null) {
                for (int j = 0; j < cellNum; j++) {
                    cell = row.getCell(j);
                    if (cell != null) {
                        if (cell.getCellType()== HSSFCell.CELL_TYPE_STRING) {
                            contentMatrix[i][j] = cell.getStringCellValue();
                        } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            contentMatrix[i][j] = String.valueOf(cell.getNumericCellValue());
                        } else {
                            System.out.println(cell.getCellType());
                            System.err.println("unexpected value type in Excel file");
                        }
                    }
                }
            }
        }
        
        return contentMatrix;
    }
    
    public static void writeXLSFile(String[][] table, String path) throws IOException {
        String sheetName = "Sheet1";
        
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);
        
        // iterating r number of rows
        for (int r = 0; r < table.length; r++) {
            HSSFRow row = sheet.createRow(r);
            
            // iterating c number of colums
            for (int c = 0; c < table[r].length; c++) {
                HSSFCell cell = row.createCell(c);
                
                cell.setCellValue(table[r][c]);
            }
        }
        
        FileOutputStream fileout = new FileOutputStream(path);
        
        //write this workbook to an Outputstream
        wb.write(fileout);
        fileout.flush();
        fileout.close();
    }
    
    public static void writeXLSFile(int[][] table, String path) throws IOException {
        String sheetName = "Sheet1";
        
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);
        
        // iterating r number of rows
        for (int r = 0; r < table.length; r++) {
            HSSFRow row = sheet.createRow(r);
            
            // iterating c number of colums
            for (int c = 0; c < table[r].length; c++) {
                HSSFCell cell = row.createCell(c);
                
                cell.setCellValue(table[r][c]);
            }
        }
        
        FileOutputStream fileout = new FileOutputStream(path);
        
        //write this workbook to an Outputstream
        wb.write(fileout);
        fileout.flush();
        fileout.close();
    }
    
}
