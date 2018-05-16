package com.sixi.oaplatform.common.utils;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * excel 导出
 * @Author 蟑螂
 * @Create 2017-11-23 20:03
 */
public class ExcelExport {
    /**
     * 导出通用
     * @param response
     * @param fileName
     * @param format
     * @param data
     */
    public void export(HttpServletResponse response, String fileName, Map<String, String> format, List<Map<String, Object>> data){
        XSSFWorkbook wb = this.compositing(fileName, format, data);

        try{
            fileName = new String(fileName.getBytes("gbk"), "iso8859-1");
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }

        OutputStream output;
        response.reset(); //清除buffer缓存
        // 指定下载的文件名
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //导出Excel对象

        try {
            output = response.getOutputStream();
            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
            bufferedOutPut.flush();
            wb.write(bufferedOutPut);
            bufferedOutPut.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Excel文件导出失败");
        }
    }

    /**
     * excel 生成excel
     * @param fileName 文件名称
     * @param format 文件的格式
     *               键（字段名）：值（csv 文件表头）
     * @param data 需要导出的shuju
     * @return
     */
    public XSSFWorkbook compositing(String fileName, Map<String, String> format, List<Map<String, Object>> data){
        // 创建一个webbook，对应一个Excel文件
        XSSFWorkbook wb = new XSSFWorkbook();
        // 在webbook中添加一个sheet,对应Excel文件中的sheet
        XSSFSheet sheet = wb.createSheet(fileName);
        // 在sheet中添加表头第0行,注意老版本poi 对Excel 的行数列数有限制short
        XSSFRow row = sheet.createRow( 0);

        // 创建单元格，并设置值表头 设置表头居中
//        XSSFCellStyle style = wb.createCellStyle();//样式设置
//        style.setAlignment(HorizontalAlignment.CENTER); // 居中
//        XSSFFont font = wb.createFont();//字体设置
//        font.setBold(true);
//        font.setFontHeightInPoints((short) 18);//设置字体大小

        // 设置每列宽
//        sheet.setColumnWidth(1, 8000);

        // 行数
        int rownum = 0;

        List<Map.Entry<String,String>> formatList = new ArrayList<Map.Entry<String,String>>(format.entrySet());

        // 设置头部信息
        row = sheet.createRow(rownum);
        // 列数
        int cellnum = 0;
        for(Map.Entry<String, String> item : formatList){
            XSSFCell cell = row.createCell(cellnum);
            cell.setCellValue(item.getValue());
            cellnum ++;
        }
        rownum ++;
        cellnum = 0;

        for(Map<String, Object> itemRow : data){
            row = sheet.createRow(rownum);

            for(Map.Entry<String, String> itemCell : formatList){
                XSSFCell cell = row.createCell(cellnum);
                cell.setCellValue(Fn.toString(itemRow.get(itemCell.getKey())));
                cellnum ++;
            }

            rownum ++;
            cellnum = 0;
        }

        return wb;
    }
}
