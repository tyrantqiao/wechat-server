package com.tyrantqiao.util.mybatis;

import java.sql.*;
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
public class MysqlReader {
    private static String url;
    private static String username;
    private static String password;
    static String lineBreak = "\r\t";
    String sqlTables = "show tables";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {

        }
    }

    public String getKeyName() {
        return this.keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyJavaType() {
        return this.keyJavaType;
    }

    public void setKeyJavaType(String keyJavaType) {
        this.keyJavaType = keyJavaType;
    }

    public String getKeySqlType() {
        return this.keySqlType;
    }

    public void setKeySqlType(String keySqlType) {
        this.keySqlType = keySqlType;
    }

    public String getKeyFieldName() {
        return this.keyFieldName;
    }

    public void setKeyFieldName(String keyFieldName) {
        this.keyFieldName = keyFieldName;
    }

    private String keyName;
    private String keyFieldName;

    private String keyJavaType;
    private String keySqlType;

    @SuppressWarnings("static-access")
    public void setMysqlInfo(String url, String username, String password) {
        MysqlReader.url = url;
        MysqlReader.username = username;
        MysqlReader.password = password;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * 查询表的字段，封装成List
     *
     * @param tableName
     * @return
     * @throws SQLException
     */
    public List<FieldData> getFieldDatas(String schema, String tableName) throws SQLException {
        String sqlColumns = "SELECT distinct COLUMN_NAME, DATA_TYPE, COLUMN_COMMENT, COLUMN_KEY FROM information_schema.columns WHERE table_schema='"
                + schema + "' and table_name =  '" + tableName + "' ";
        Connection con = this.getConnection();
        PreparedStatement ps = con.prepareStatement(sqlColumns);
        List<FieldData> columnList = new ArrayList<FieldData>();
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String name = rs.getString(1);
            String fieldName = this.getColumnNameToFieldName(name);

            String type = rs.getString(2);
            String comment = rs.getString(3);
            type = this.getType(type);

            String jdbcType = this.getJdbcType(rs.getString(2));

            String key = rs.getString(4).toUpperCase();
            FieldData cd = new FieldData();
            if (key != null && key.contains("PRI")) {
                this.keyFieldName = fieldName;
                this.keyName = name;
                this.keySqlType = jdbcType;
                this.keyJavaType = this.getFullJavaType(rs.getString(2));
                cd.setIsPK(this.keyName);
            }

            cd.setJdbcType(jdbcType);
            cd.setColumnName(name);
            cd.setFieldName(fieldName);
            cd.setDataType(type);
            cd.setColumnComment(comment);
            columnList.add(cd);
        }
        rs.close();
        ps.close();
        con.close();
        return columnList;
    }

    /**
     * 获取数据库表名和备注list
     *
     * @return
     * @throws SQLException
     */
    public Map<String, String> getTablesInfo() {
        try {
            Connection con = this.getConnection();
            DatabaseMetaData dbMetaData = con.getMetaData();
            ResultSet rs = dbMetaData.getTables(null, null, null, new String[]{"TABLE"});
            Map<String, String> r = new HashMap<String, String>(16);
            while (rs.next()) {
                String key = rs.getString("TABLE_NAME");
                // String value = key + "(" + rs.getString("COMMENT") + ")";
                String value = key;
                r.put(key, value);
            }
            con.close();
            return r;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 获取数据库表名和备注list
     *
     * @return
     * @throws SQLException
     */
    public boolean testConnect() {
        try {
            Connection con = this.getConnection();
            if (con == null) {
                return false;
            }
            con.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getJdbcType(String type) {
        String originType = type;
        type = type.toLowerCase();
        if ("char".equals(type)) {
            return "CHAR";
        }

        if ("varchar".equals(type) || "enum".equals(type)) {
            return "VARCHAR";
        }

        if ("text".equals(type)) {
            return "VARCHAR";
        }

        if (type.contains("float")) {
            return "DOUBLE";
        }
        if (type.contains("decimal")) {
            return "NUMERIC";
        }
        if (type.contains("double")) {
            return "DOUBLE";
        }
        if ("int".equals(type)) {
            return "INTEGER";
        }

        if ("tinyint".equals(type)) {
            return "TINYINT";
        }

        if ("bigint".equals(type)) {
            return "BIGINT";
        }

        if ("timestamp".equals(type) || "datetime".equals(type)) {
            return "TIMESTAMP";
        }

        if ("date".equals(type)) {
            return "DATE";
        }

        return originType.toUpperCase();
    }

    public String getType(String type) {
        String otype = type;
        type = type.toLowerCase();
        if ("char".equals(type) || "varchar".equals(type) || type.contains("text") || "enum".equals(type)) {
            return "String";
        } else if (type.contains("float")) {
            return "Float";

        } else if (type.contains("decimal")) {
            return "java.math.BigDecimal";

        } else if (type.contains("double")) {
            return "Double";

        } else if ("int".equals(type) || "smallint".equals(type)) {
            return "Integer";
        } else if ("tinyint".equals(type)) {
            return "Integer";
        } else if ("bigint".equals(type)) {
            return "Long";
        } else if ("timestamp".equals(type) || "date".equals(type) || "datetime".equals(type)) {
            return "java.util.Date";
        }
        return otype;
    }

    public String getFullJavaType(String type) {
        String otype = type;
        type = type.toLowerCase();
        if ("char".equals(type) || "varchar".equals(type) || type.contains("text")) {
            return "java.lang.String";
        } else if (type.contains("float")) {
            return "java.lang.Float";

        } else if (type.contains("decimal")) {
            return "java.math.BigDecimal";

        } else if (type.contains("double")) {
            return "java.lang.Double";

        } else if ("int".equals(type)) {
            return "java.lang.Integer";
        } else if ("tinyint".equals(type)) {
            return "java.lang.Integer";
        } else if ("bigint".equals(type)) {
            return "java.lang.Long";
        } else if ("timestamp".equals(type) || "date".equals(type) || "datetime".equals(type)) {
            return "java.util.Date";
            // return "java.sql.Timestamp";
        }
        return otype;
    }

    /**
     * <br>
     * <b>功能：</b>字段名转换成类属性名 每_首字母大写<br>
     * <b>作者：</b><br>
     *
     * @param column
     * @return
     */
    public String getColumnNameToFieldName(String column) {
        String[] split = column.split("_");
        if (split.length > 1) {
            StringBuffer sb = new StringBuffer();
            for (String element : split) {
                String tempTableName = element.substring(0, 1).toUpperCase() + element.substring(1, element.length())
                        .toLowerCase();
                sb.append(tempTableName);
            }

            String tempField = sb.toString();
            System.out.println(tempField);

            return tempField.substring(0, 1).toLowerCase() + tempField.substring(1, tempField.length());
        } else {
            String tempField = split[0].substring(0, 1).toLowerCase() + split[0].substring(1, split[0].length())
                    .toLowerCase();
            return tempField;
        }
    }

    /**
     * <br>
     * <b>功能：</b>表名转换成类名 每_首字母大写<br>
     * <b>作者：</b><br>
     *
     * @param tableName
     * @return
     */
    public String getTablesNameToClassName(String tableName) {
        String[] split = tableName.split("_");
        if (split.length > 1) {
            StringBuffer sb = new StringBuffer();
            for (String element : split) {
                String tempTableName = element.substring(0, 1).toUpperCase() + element.substring(1, element.length());
                sb.append(tempTableName);
            }
            System.out.println(sb.toString());
            return sb.toString();
        } else {
            return split[0].substring(0, 1).toUpperCase() + split[0].substring(1, split[0].length());
        }
    }
}
