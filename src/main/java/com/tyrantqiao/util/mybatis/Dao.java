package com.tyrantqiao.util.mybatis;

/**
 * @author tyrant
 */
public enum Dao {
    //用户表
    USER("user", "t_user");
    private String name;
    private String path;

    Dao(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
