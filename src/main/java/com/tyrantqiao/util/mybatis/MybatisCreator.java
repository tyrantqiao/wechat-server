package com.tyrantqiao.util.mybatis;

import org.apache.velocity.VelocityContext;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tyrantqiao
 * date: 2018/11/10
 * blog: tyrantqiao.com
 * contact: tyrantqiao@icloud.com
 */
public class MybatisCreator {
    private static String schema;
    private static String model;
    private static Dao dao;
    private static String createAdminFind;
    private static List<String> doTypeList;
    private static String tableComment;
    public static String workSpaceUrl;

    static {
        dao = Dao.USER;
        model = "user";
        workSpaceUrl = "D:/Java Project/wechat-server";
        schema = "server";
        createAdminFind = "no";
        doTypeList = new ArrayList<>();
        doTypeList.add("db");
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://193.112.28.19:3306/" + schema + "?characterEncoding=UTF-8";
        String username = "root";
        String password = "Tyrant";
        CreateBean createBean = new CreateBean();
        createBean.setMysqlInfo(url, username, password);
        /* key=表名，value=表注释 */
        Map<String, String> tableMap = new HashMap<String, String>(16);
        tableMap.put("t_user", "用户信息表");

        for (Map.Entry<String, String> entry : tableMap.entrySet()) {
            tableComment = entry.getValue();
            // type 是支持可以删除已生成的文件：C=生成，D=删除
            MybatisCreator.create(entry.getKey(), "C");
        }
    }

    public static void create(String tableName, String type) {

        CreateBean createBean = new CreateBean();

        // int prefix = 9;
        int prefix = tableName.indexOf("_") + 1;
        String className = createBean.getTablesNameToClassName(tableName.substring(prefix));

        String lowerName = className.substring(0, 1).toLowerCase() + className.substring(1, className.length());

        String xmlbeanpkg = "com.tyrantqiao.dao." + model + "";

        String beanBasePath = workSpaceUrl + "/src/main/java/com/tyrantqiao/repository/dao/" + model + "/";
        String mapBasePath = workSpaceUrl + "/src/main/java/com/tyrantqiao/repository/dao/" + model + "/";
        String serviceBasePath = workSpaceUrl + "/src/main/java/com/tyrantqiao/service/";
        String viewBasePath = workSpaceUrl + "/src/main/java/resources/static/";
        String controllerBasePath = workSpaceUrl + "/src/main/java/com/tyrantqiao/" + "/controller/";

        VelocityContext context = new VelocityContext();
        context.put("className", className); //
        context.put("lowerName", lowerName);
        context.put("tableComment", tableComment);
        context.put("tableName", tableName);
        context.put("beanpkg", xmlbeanpkg);
        context.put("model", model);
        context.put("createAdminFind", createAdminFind);
        try {
            context.put("columnDatas", createBean.getFieldDatas(schema, tableName)); // 生成bean
            context.put("pk", createBean);
        } catch (Exception e) {

        }

        // -------------------生成文件代码---------------------/
        for (String doType : doTypeList) {
            if ("db".equals(doType)) {
                String tblPath = "/entity/" + className + ".java";
                String sqlMapperPath = "/" + className + "Mapper.xml";
                if ("D".equals(type)) {
                    MybatisCreator.deleteFile(beanBasePath, tblPath);
                    MybatisCreator.deleteFile(mapBasePath, sqlMapperPath);
                } else {
                    CommonPageParser.WriterPage(context, "TempTbl.java", beanBasePath, tblPath);
                    // 生成实体modelbean
                    CommonPageParser.WriterPage(context, "TempMapper.xml", mapBasePath, sqlMapperPath);
                    // 生成Mybatis
                }
            } else if ("po".equals(doType)) {
                String poPath = "persistence/entity/" + className + ".java";
                if ("D".equals(type)) {
                    MybatisCreator.deleteFile(beanBasePath, poPath);
                } else {
                    CommonPageParser.WriterPage(context, "TempPo.java", beanBasePath, poPath);
                    // 生成实体modelbean
                }
            } else if ("service".equals(doType)) {
                String servicePath = "service/" + className + "Service.java";
                String serviceimplPath = "/" + className + "Service.java";
                if (model.equals("admin")) {
                    servicePath = "service/" + className + "AdminService.java";
                    serviceimplPath = "service/impl/" + className + "AdminServiceImpl.java";
                }
                if ("D".equals(type)) {
                    MybatisCreator.deleteFile(serviceBasePath, servicePath);
                    MybatisCreator.deleteFile(serviceBasePath, serviceimplPath);
                } else {
                    // CommonPageParser.WriterPage(context, "TempService.java", serviceBasePath,
                    // 生成Service
                    CommonPageParser.WriterPage(context, "TempServiceImpl.java", serviceBasePath, serviceimplPath);// 生成Service
                }
            } else if ("dao".equals(doType)) {
                String daoPath = "/" + className + "Dao.java";
                String daoimplPath = "/" + className + "Mapper.java";
                if ("D".equals(type)) {
                    MybatisCreator.deleteFile(beanBasePath, daoPath);
                    MybatisCreator.deleteFile(beanBasePath, daoimplPath);
                } else {
                    // CommonPageParser.WriterPage(context, "TempDAO.java", beanBasePath, daoPath);
                    // 生成Model
                    CommonPageParser.WriterPage(context, "TempDAOImpl.java", beanBasePath, daoimplPath); // 生成Model
                }
            } else if ("controller".equals(doType)) {
                String controllerPath = "/" + className + "Controller.java";
                if ("D".equals(type)) {
                    MybatisCreator.deleteFile(controllerBasePath, controllerPath);
                } else {
                    CommonPageParser.WriterPage(context, "TempController.java", controllerBasePath, controllerPath);// 生成Controller
                }
            } else if ("view".equals(doType)) {
                String pageEditPath = "WEB-INF/view/app/admin/" + lowerName + "/" + lowerName + "Edit.ftl";
                String pageListPath = "WEB-INF/view/app/admin/" + lowerName + "/" + lowerName + "List.ftl";
                String pageEditJsPath = "scripts/ql/app/admin/" + lowerName + ".js";
                if ("D".equals(type)) {
                    MybatisCreator.deleteFile(viewBasePath, pageListPath);
                    MybatisCreator.deleteFile(viewBasePath, pageEditPath);
                    MybatisCreator.deleteFile(viewBasePath, pageEditJsPath);
                } else {
                    CommonPageParser.WriterPage(context, "TempList.ftl", viewBasePath, pageListPath);//
                    CommonPageParser.WriterPage(context, "TempEdit.ftl", viewBasePath, pageEditPath);//
                    CommonPageParser.WriterPage(context, "TempEdit.js", viewBasePath, pageEditJsPath);//
                }
            }
        }
    }

    public static void deleteFile(String basePath, String filePath) {
        File file = new File(basePath + filePath);
        if (file.exists()) {
            System.out.println("删除文件" + file.getAbsolutePath());
            file.delete();
        }
    }

    /**
     * 获取项目的路径
     *
     * @return
     */
    public static String getRootPath() {
        String rootPath = "";
        try {
            File file = new File(CommonPageParser.class.getResource("/").getFile());

            rootPath = file.getParentFile().getAbsolutePath() + "/";

            rootPath = java.net.URLDecoder.decode(rootPath, "utf-8");
            return rootPath;
        } catch (Exception e) {

        }
        return rootPath;
    }
}
