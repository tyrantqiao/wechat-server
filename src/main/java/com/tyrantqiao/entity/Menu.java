package com.tyrantqiao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tyrantqiao
 * date: 2018/10/27
 * blog: tyrantqiao.com
 * contact: tyrantqiao@icloud.com
 */
@JsonFormat
public class Menu extends BaseEntity {
    /**
     * 一级菜单最多为3个，二级菜单最多五个
     */
    private List<MenuItem> menuItems = new ArrayList<>(3);

    public boolean addMenuItem(MenuItem menuItem) {
        return menuItems.add(menuItem);
    }

    public boolean removeMenuItem(MenuItem menuItem) {
        return menuItems.remove(menuItem);
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }
}

