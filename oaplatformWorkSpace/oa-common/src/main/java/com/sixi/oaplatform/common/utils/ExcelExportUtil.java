package com.sixi.oaplatform.common.utils;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Excel操作类
 * @Author 艾翔
 * @Date 2017/8/28 14:41
 */
public class ExcelExportUtil {

    private XSSFWorkbook wb;
    private String fileName="excel1";

    /**
     * excel导出
     * @param response
     * @param map 键为导出的文件名，值XSSFWorkbook类
     */
    public void excelExport(HttpServletResponse response, Map<String, XSSFWorkbook> map){
        //Excel文件
        for (String str : map.keySet()) {
            try {
                this.fileName = new String(str.getBytes("gbk"),"iso8859-1");
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            this.wb = map.get(str);
        }

        OutputStream output;
        response.reset(); //清除buffer缓存
        // 指定下载的文件名
        response.setHeader("Content-Disposition", "attachment;filename=" + this.fileName + ".xlsx");
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
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Excel文件导出失败");
        }
    }
}