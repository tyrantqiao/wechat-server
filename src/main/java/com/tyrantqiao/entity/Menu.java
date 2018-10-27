package com.tyrantqiao.entity;

import java.util.List;

/**
 * @author tyrantqiao
 * date: 2018/10/27
 * blog: tyrantqiao.com
 * contact: tyrantqiao@icloud.com
 */
public class Menu {
    private String type;
    private String name;
    private String key;
    private List<MenuItem> menuItems;

    public boolean addMenuItem(MenuItem menuItem) {
        return menuItems.add(menuItem);
    }

    public boolean removeMenuItem(MenuItem menuItem) {
        return menuItems.remove(menuItem);
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

