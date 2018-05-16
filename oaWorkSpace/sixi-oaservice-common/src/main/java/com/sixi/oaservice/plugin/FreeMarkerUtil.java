package com.sixi.oaservice.plugin;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * FreeMarker 自动生成文件工具类
 * @author Administrator
 * create on 2018/02/25
 */
public class FreeMarkerUtil {

    /**
     * 自动合并生成输出文件
     * @param inputVmFilePath 输入文件
     * @param outputFilePath 输出文件
     * @param context 合并的上下文
     * @throws IOException
     * @throws TemplateException
     */
    public static boolean generate(String inputVmFilePath, String outputFilePath, Map<String,Object> context) throws IOException, TemplateException {
        freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_27);

        configuration.setDefaultEncoding("UTF-8");
        configuration.setDirectoryForTemplateLoading(new File(getPath(inputVmFilePath)));
        configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_27));


        Template template = configuration.getTemplate(getFile(inputVmFilePath), Locale.CHINA);
        //输出文件
        File file = new File(outputFilePath);
        if (!file.exists()){
            if (!file.getParentFile().exists()) {
                boolean mkdirs = file.getParentFile().mkdirs();
                if (!mkdirs) {
                    return false;
                }
            }
            boolean newFile = file.createNewFile();
            if (!newFile){
                return false;
            }
        }
        FileWriterWithEncoding writer = new FileWriterWithEncoding(file,"UTF-8");

        template.process(context,writer);

        writer.flush();
        writer.close();
        return true;
    }

    /**
     * 根据文件绝对路径获取目录
     * @param filePath 文件绝对路径
     * @return 目录
     */
    public static String getPath(String filePath) {
        String path = "";
        if (StringUtils.isNotBlank(filePath)) {
            path = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        }
        return path;
    }

    /**
     * 根据文件绝对路径获取文件
     * @param filePath 文件绝对路径
     * @return 文件
     */
    public static String getFile(String filePath) {
        String file = "";
        if (StringUtils.isNotBlank(filePath)) {
            file = filePath.substring(filePath.lastIndexOf("/") + 1);
        }
        return file;
    }

}
