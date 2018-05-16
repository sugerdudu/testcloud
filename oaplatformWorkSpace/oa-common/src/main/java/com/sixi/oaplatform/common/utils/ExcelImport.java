package com.sixi.oaplatform.common.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel 导入
 * @Author 蟑螂
 * @Create 2017-11-23 19:58
 */
public class ExcelImport {
    private final static String excel2003 =".xls";    //2003- 版本的excel
    private final static String excel2007 =".xlsx";   //2007+ 版本的excel

    //region 解析IO 流中的数据，获取所有数据
    /**
     * 解析IO 流中的数据，获取所有数据
     * 此通用方法只支持xls 与 xlsx 格式的Excel
     * 支持多个sheet
     * @param file
     * @return
     * @throws Exception
     */
    public List<List<List<Object>>> importAll(File file) throws Exception {
        Workbook work = this.getWorkbook(file);
        if(null == work){
            throw new Exception("不能导入空的Excel");
        }

        List<List<List<Object>>> data = null;
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        // 遍历Excel中所有的sheet
        for(int i = 0, sheetNum = work.getNumberOfSheets(); i < sheetNum; i++){
            sheet = work.getSheetAt(i);
            if(null == sheet){
                continue;
            }

            List<List<Object>> sheetList = null;
            // 遍历当前sheet中的所有行
            for(int j = sheet.getFirstRowNum(), rowNum = sheet.getLastRowNum(); j < rowNum; j++){
                row = sheet.getRow(j);
                if(row == null || row.getFirstCellNum() == j){
                    continue;
                }

                // 遍历所有的列
                List<Object> cellList = new ArrayList<Object>();
                for (int y = row.getFirstCellNum(), cellNum = row.getLastCellNum(); y < cellNum; y++){
                    cell = row.getCell(y);
                    cellList.add(this.getCellValue(cell));
                }
                sheetList.add(cellList);
            }
            data.add(sheetList);
        }
        work.close();

        return data;
    }
    //endregion

    /**
     * 解析IO 流中的数据，获取所有数据
     * 此通用方法只支持xls 与 xlsx 格式的Excel
     * 支持多个sheet
     * @param file
     * @param formatList 文件的格式
     *              键（字段名）：值（csv 文件表头）
     * @return
     */
    public List<List<Map<String, Object>>> importCommonByFormat(File file, List<Map<String, String>> formatList) throws Exception {
        Workbook work = this.getWorkbook(file);
        if(null == work){
            throw new Exception("不能导入空的Excel");
        }

        List<List<Map<String, Object>>> data = new ArrayList<>();
        Sheet sheet = null;
        Row row = null;

        // 遍历Excel中所有的sheet
        for(int i = 0, sheetNum = work.getNumberOfSheets(); i < sheetNum; i++){
            // 获取sheet 的头部信息
            List<Map.Entry<String,String>> format = new ArrayList<Map.Entry<String,String>>(formatList.get(i).entrySet());

            sheet = work.getSheetAt(i);
            if(null == sheet){
                continue;
            }

            // 设置头信息
            Map<String, Integer> sheetTitleMap = new HashMap<>();
            Row sheetTitleRow = sheet.getRow(0);
            if(sheetTitleRow == null || sheetTitleRow.getFirstCellNum() == 0){
                throw new Exception("第" + i + "个sheet的表头有问题");
            }
            for(int y = sheetTitleRow.getFirstCellNum(), cellNum = sheetTitleRow.getLastCellNum(); y < cellNum; y++){
                for(Map.Entry<String, String> item : format){
                    if(this.getCellValue(sheetTitleRow.getCell(y)).equals(item.getValue())){
                        sheetTitleMap.put(item.getKey(), y);
                    }
                }
            }
            List<Map.Entry<String, Integer>> sheetTitle = new ArrayList<Map.Entry<String,Integer>>(sheetTitleMap.entrySet());

            List<Map<String, Object>> sheetList = new ArrayList<>();
            // 遍历当前sheet中的所有行
            for(int j = 1, rowNum = sheet.getLastRowNum(); j < rowNum; j++){
                row = sheet.getRow(j);
                if(row == null || row.getFirstCellNum() == j){
                    continue;
                }

                // 遍历所有的列
                Map<String, Object> cellList = new HashMap<>();
                for (int y = row.getFirstCellNum(), cellNum = row.getLastCellNum(); y < cellNum; y++){
                    for(Map.Entry<String, Integer> item : sheetTitle){
                        cellList.put(item.getKey(), this.getCellValue(row.getCell(item.getValue())));
                    }
                }
                sheetList.add(cellList);
            }
            data.add(sheetList);
        }
        work.close();

        return data;
    }

    //region 将File 数据转为Workbook
    /**
     * 将File 数据转为Workbook
     * @param file
     * @return
     * @throws Exception
     */
    private Workbook getWorkbook(File file) throws Exception {
        String fileName = file.getName();
        String fileType = fileName.substring(fileName.lastIndexOf("0"));
        Workbook wb = null;
        if(excel2003.equals(fileType)){
            wb = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(file)));
        }else if(excel2007.equals(fileType)){
            wb = new XSSFWorkbook(new FileInputStream(file));
        }else{
            throw new Exception("错误的文件格式" + fileType);
        }
        return wb;
    }
    //endregion

    //region 格式化单元格数据
    /**
     * 格式化单元格数据
     * @param cell
     * @return
     */
    private Object getCellValue(Cell cell){
        Object value = null;
        // 格式化number String字符
        DecimalFormat df = new DecimalFormat("0");
        // 日期格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        // 格式化数字
        DecimalFormat df2 = new DecimalFormat("0.00");

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if("General".equals(cell.getCellStyle().getDataFormatString())){
                    value = df.format(cell.getNumericCellValue());
                }else if("m/d/yy".equals(cell.getCellStyle().getDataFormatString())){
                    value = sdf.format(cell.getDateCellValue());
                }else{
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            default:
                break;
        }
        return value;
    }
    //endregion

    //region 此方法只支持csv 格式的Excel

    /**
     * 此方法只支持csv 格式的Excel
     * 暂时只支持导入一个sheet
     * @param file 需要转化的csv 文件
     * @param format 文件的格式
     *               键（字段名）：值（csv 文件表头）
     * @return
     */
    public List<Map<String, Object>> importCsvByFormat(File file, Map<String, String> format) {
        List<Map.Entry<String,String>> formatList = new ArrayList<Map.Entry<String,String>>(format.entrySet());
        List<String> fileHear = new ArrayList<>();
        for(Map.Entry<String, String> item : formatList){
            fileHear.add(item.getValue());
        }

        // Map<String, Object> data = new HashMap<>();
        FileReader fileReader = null;
        CSVParser csvFileParser = null;
        // 创建CSVFormat（header mapping）
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader((String[])fileHear.toArray());
        // CSV
        List<Map<String, Object>> rowList = new ArrayList<>();
        try{
            // 初始化FileReader object
            fileReader = new FileReader(file);
            // 初始化 CSVParser object
            csvFileParser = new CSVParser(fileReader, csvFileFormat);
            // CSV文件records
            List<CSVRecord> csvRecords = csvFileParser.getRecords();
            //
            for (int i = 1, rowNum = csvRecords.size(); i < rowNum; i++) {
                CSVRecord record = csvRecords.get(i);
                Map<String, Object> cellList = new HashMap<>();
                // 循环填充数据
                for (Map.Entry<String, String> item : formatList) {
                    cellList.put(item.getKey(), Fn.toString(record.get(item.getValue())));
                }
                rowList.add(cellList);
            }
            // data.put("list", rowList);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                fileReader.close();
                csvFileParser.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return rowList;
    }
}
