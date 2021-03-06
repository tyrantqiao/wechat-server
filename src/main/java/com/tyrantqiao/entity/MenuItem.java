package com.tyrantqiao.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tyrantqiao
 * date: 2018/10/27
 * blog: tyrantqiao.com
 * contact: tyrantqiao@icloud.com
 */
public class MenuItem {
    private String type;
    private String name;
    private String url;
    private String appid;
    private String pagepath;
    private String key;
    private List<MenuItem> menuItems = new ArrayList<>(5);

    public boolean addMenuItem(MenuItem menuItem) {
        return menuItems.add(menuItem);
    }

    public boolean removeMenuItem(MenuItem menuItem) {
        return menuItems.remove(menuItem);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPagepath() {
        return pagepath;
    }

    public void setPagepath(String pagepath) {
        this.pagepath = pagepath;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
