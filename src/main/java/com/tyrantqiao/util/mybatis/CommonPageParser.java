package com.tyrantqiao.util.mybatis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

/**
 * @author tyrantqiao
 * date: 2018/11/11
 * blog: tyrantqiao.com
 * contact: tyrantqiao@icloud.com
 */
public class CommonPageParser {
    private static VelocityEngine ve;// = VelocityEngineUtil.getVelocityEngine();

    private final static String CONTENT_ENCODING = "UTF-8";

    // private static final Log log = LogFactory.getLog(CommonPageParser.class);

    private static boolean isReplace = true;
    // 是否可以替换文件 true =可以替换，false =不可以替换

    /* 打印项目跟路径 */
    public static void main(String[] args) {
        System.out.println(MybatisCreator.getRootPath());
    }

    static {
        try {
            // 获取文件模板根路径
            // String templateBasePath = MybatisCreator.getRootPath()+"\\template" ;
            String templateBasePath = MybatisCreator.workSpaceUrl + "/gosuncn-creater/src/main/resources/template";
            Properties properties = new Properties();
            properties.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
            properties.setProperty("file.resource.loader.description", "Velocity File Resource Loader");
            properties.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, templateBasePath);
            properties.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, "true");
            properties.setProperty("file.resource.loader.modificationCheckInterval", "30");
            properties
                    .setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.Log4JLogChute");
            properties.setProperty("runtime.log.logsystem.log4j.logger", "org.apache.velocity");
            properties.setProperty("directive.set.null.allowed", "true");
            VelocityEngine velocityEngine = new VelocityEngine();
            velocityEngine.init(properties);
            ve = velocityEngine;
        } catch (Exception e) {

        }
    }

    /**
     * <br>
     * <b>功能：</b>生成页面文件<br>
     * <b>作者：</b>李禄燊<br>
     * <b>日期：</b> 2011-7-23 <br>
     *
     * @param context      内容上下文
     * @param templateName 模板文件路径（相对路径）article\\article_main.html
     * @param targetPage   生成页面文件路径（相对路径） vowo\index_1.html
     */
    public static void WriterPage(VelocityContext context, String templateName, String fileDirPath, String targetFile) {
        try {
            File file = new File(fileDirPath + targetFile);
            if (!file.exists()) {
                new File(file.getParent()).mkdirs();
            } else {
                if (isReplace) {
                    System.out.println("替换文件" + file.getAbsolutePath());
                } else {
                    System.out.println("页面生成失败" + file.getAbsolutePath() + "文件已存在");
                    return;
                }
            }

            Template template = ve.getTemplate(templateName, CONTENT_ENCODING);
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, CONTENT_ENCODING));
            template.merge(context, writer);
            writer.flush();
            writer.close();
            fos.close();
            System.out.println("页面生成成功" + file.getAbsolutePath());
        } catch (Exception e) {

        }
    }

    public static void WriterPageDoc(VelocityContext context, String templateName, String fileDirPath,
                                     String targetFile) {
        try {
            File file = new File(fileDirPath + targetFile);
            if (!file.exists()) {
                new File(file.getParent()).mkdirs();
            } else {
                if (isReplace) {

                } else {

                    return;
                }
            }

            Template template = ve.getTemplate(templateName, CONTENT_ENCODING);
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, CONTENT_ENCODING));
            template.merge(context, writer);
            writer.flush();
            writer.close();
            fos.close();
            // System.out.println("页面生成成功"+file.getAbsolutePath());
        } catch (Exception e) {

        }
    }
}
