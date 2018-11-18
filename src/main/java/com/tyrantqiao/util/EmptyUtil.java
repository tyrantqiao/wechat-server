package com.tyrantqiao.util;

import java.util.Collection;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

/**
 * @author tyrantqiao [tyrantqiao@gmail.com]
 * @see <a href="https://github.com/tyrantqiao">qiao's github</a>
 */
public class EmptyUtil {
    /**
     * @return
     */
    public static boolean isEmpty(Collection<?> list) {
        if (list == null || list.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param number
     * @return
     */
    public static boolean isEmpty(Long number) {
        if (number == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param arr
     * @return
     */
    public static boolean isEmpty(Object[] arr) {
        if (arr == null || arr.length == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 这里null和""都判断是因为，前端有时候难以处理空字符串
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param number
     * @return
     */
    public static boolean isEmpty(Integer number) {
        if (number == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param map
     * @return
     */
    public static boolean isEmpty(Map<?, ?> map) {
        if (map == null || map.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param <T>
     * @param queue
     * @return
     */
    public static <T> boolean isEmpty(BlockingQueue<T> queue) {
        if (queue == null || queue.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param list
     * @return
     */
    public static boolean isNotEmpty(Collection<?> list) {
        return !EmptyUtil.isEmpty(list);
    }

    /**
     * @param arr
     * @return
     */
    public static boolean isNotEmpty(Object[] arr) {
        return !EmptyUtil.isEmpty(arr);
    }

    /**
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !EmptyUtil.isEmpty(str);
    }

    /**
     * @param number
     * @return
     */
    public static boolean isNotEmpty(Integer number) {
        return !EmptyUtil.isEmpty(number);
    }

    /**
     * @param number
     * @return
     */
    public static boolean isNotEmpty(Long number) {
        return !EmptyUtil.isEmpty(number);
    }

    /**
     * @param map
     * @return
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !EmptyUtil.isEmpty(map);
    }

    /**
     * @param queue
     * @return
     */
    public static <T> boolean isNotEmpty(BlockingQueue<T> queue) {
        return !EmptyUtil.isEmpty(queue);
    }

    /**
     * 首字母大写
     *
     * @param string
     * @return
     */
    public static String firstLetterUpper(String string) {
        if (string == null) {
            return "";
        }
        char[] c = string.toCharArray();
        if (c[0] >= 'a' && c[0] <= 'z') {
            c[0] = (char) (c[0] - 32);
        }
        return new String(c);
    }

    public static boolean isEmpty(Set<String> set) {
        if (set == null || set.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotEmpty(Set<String> set) {
        return !EmptyUtil.isEmpty(set);
    }

    /**
     * 把list按实体T的property属性的值进行分组
     *
     * @param list
     * @param property
     * @return
     */
    public static <T> Map<String, List<T>> group(List<T> list, String property) {
        Map<String, List<T>> map = new HashMap<>();
        if (EmptyUtil.isNotEmpty(list)) {
            String key = null;
            List<T> value = null;
            Method getter = null;
            Class<?> type = list.get(0).getClass();
            try {
                getter = type.getMethod("get" + EmptyUtil.firstLetterUpper(property));
            } catch (NoSuchMethodException | SecurityException e) {

                throw new RuntimeException(e.getMessage());
            }
            try {
                for (T t : list) {
                    key = (String) getter.invoke(t);
                    value = map.get(key);
                    if (value == null) {
                        value = new ArrayList<>();
                        value.add(t);
                        map.put(key, value);
                    } else {
                        value.add(t);
                    }
                }
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

                throw new RuntimeException(e.getMessage());
            }
        }
        return map;
    }

    /**
     * sort 排序
     *
     * @param sort
     */
    public static <T extends Comparable<? super T>> void sort(List<T> sort) {
        if (EmptyUtil.isEmpty(sort)) {
            return;
        }
        Collections.sort(sort);
    }
}
