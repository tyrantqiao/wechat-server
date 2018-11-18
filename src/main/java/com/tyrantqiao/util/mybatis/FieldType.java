package com.tyrantqiao.util.mybatis;

import lombok.Getter;

/**
 * @author tyrantqiao
 * date: 2018/11/10
 * blog: tyrantqiao.com
 * contact: tyrantqiao@icloud.com
 */
public enum FieldType {
    //用作mysql各种类型的比较，方便对比。
    CHAR("char", "CHAR"), VARCHAR("varchar", "VARCHAR"), INT("int", "INT"),
    DATETIME("datetime", "DATETIME"), BLOB("blob", "BLOB");
    private String fieldType;
    private String filedCapitalType;

    FieldType(String fieldType, String fieldCapitalType) {
        this.fieldType = fieldType;
        this.filedCapitalType = fieldCapitalType;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFiledCapitalType() {
        return filedCapitalType;
    }

    public void setFiledCapitalType(String filedCapitalType) {
        this.filedCapitalType = filedCapitalType;
    }
}
