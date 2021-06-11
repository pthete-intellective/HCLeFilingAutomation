package com.stateofconnecticut.selenium.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class readExcelDataFile {

    private static XSSFWorkbook wb;
    private static XSSFSheet sh;
    private int Rowcnt;

    private boolean Exist = false;

    readExcelDataFile(String FilePath) {
        try{
//            InputStream in = null;
//            try {
//                in = getStream(FilePath);
//                wb=new XSSFWorkbook(in);
////                return p;
//            } finally {
//                if (in != null) {
//                    in.close();
//                }
//            }
            File Src = new File(FilePath);
//            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
//            File Src = new File(classLoader.getResource(FilePath).getFile());

            FileInputStream Fis = new FileInputStream(Src);
            wb=new XSSFWorkbook(Fis);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static InputStream getStream(String name) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
    }
    public String getExcelData(String SheetName, int RowNo, String ColumnValue){
        String CellValue=null;
        try {
            int ColNo = ColumnValue(ColumnValue, SheetName);
            sh = wb.getSheet(SheetName);
            CellValue = sh.getRow(RowNo).getCell(ColNo).getStringCellValue();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return CellValue;
    }
    public String getExcelDataCategory(String SheetName, int RowNo, String ColumnValue){
        String CellValue=null;
        try {
            int ColNo = ColumnValueCategory(ColumnValue, SheetName);
            sh = wb.getSheet(SheetName);
            CellValue = sh.getRow(RowNo).getCell(ColNo).getStringCellValue();
        }catch (NullPointerException e){
            e.getStackTrace();
        }
        return CellValue;
    }
    String getExcelDataint(String SheetName, int RowNo, int ColNo){
        String CellValue=null;
        try {
            // int ColNo = ColumnValue(colNo);
            sh = wb.getSheet(SheetName);
            Cell cell=sh.getRow(RowNo).getCell(ColNo);
            CellValue = sh.getRow(RowNo).getCell(ColNo).getStringCellValue();
        }catch (Exception ex){
//            ex.printStackTrace();
        }
        return CellValue;
    }
    public int RowCount(String SheetName){
        sh = wb.getSheet(SheetName);
        Rowcnt = sh.getLastRowNum();
        return  Rowcnt;
    }

    public int ColCount(String SheetName){
        sh = wb.getSheet(SheetName);
        return sh.getRow(0).getLastCellNum();

    }

    public int ColumnValue(String ColumnName, String shName){

        sh = wb.getSheet(shName);
        int cnt = sh.getRow(0).getLastCellNum();
        for (int j = 0 ; j<=cnt ; j++ ) {

            String Value = sh.getRow(0).getCell(j).getStringCellValue().trim();
            if (ColumnName.trim().equals(Value.trim())) {
                Exist = true;
                return j;
            }
        }
        if (!Exist){
            System.out.println(ColumnName+" This column is not exist.");
        }

        return -1;
    }
    public int ColumnValueCategory(String ColumnName, String shName){

        sh = wb.getSheet(shName);
        int cnt = sh.getRow(1).getLastCellNum();
        for (int j = 0 ; j<=cnt ; j++ ) {

            String Value = sh.getRow(1).getCell(j).getStringCellValue().trim();
            if (ColumnName.trim().equals(Value.trim())) {
                Exist = true;
                return j;
            }
        }
        if (!Exist){
            System.out.println(ColumnName+" This column is not exist.");
        }
        return -1;
    }
    public int rowValue(String RowData, int colNo){
        Rowcnt = sh.getLastRowNum();
        for (int j = 0 ; j<=Rowcnt ; j++ ) {

            String Value = sh.getRow(j).getCell(colNo).getStringCellValue().trim();
            if (RowData.trim().equals(Value.trim())) {
                Exist = true;
                return j;
            }
        }
        if (!Exist){
            System.out.println(RowData+" This row is not exist.");
        }
        return -1;
    }
    int RowNo(String Sheet,String RowValue,String ColValue1){
        int RCount = RowCount(Sheet);
        int Cocnt = ColumnValue(ColValue1,Sheet);
        for (int p = 1; p<=RCount; p++){
            String Value = sh.getRow(p).getCell(Cocnt).getStringCellValue().trim();
            if (RowValue.trim().equals(Value.trim())) {
                Exist = true;
                return p;
            }
        }
        if (!Exist){
            System.out.println(RowValue+" This row value is not exist.");
        }
        return -1;
    }
    public void setCellData(String Data, int row , int col){

        DataFormatter formatter = new DataFormatter();
        String data1 = formatter.formatCellValue(sh.getRow(row).getCell(col));
        if (!data1.isEmpty())
            sh.getRow(row).getCell(col).setCellValue("");
        if (!(Data.isEmpty())){
            sh.getRow(row).getCell(col).setCellValue(Data);

            try{
                FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\TestData\\TestData.xlsx");
                wb.write(fileOut);
                fileOut.flush();
                fileOut.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

}


