package com.tyrantqiao.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author tyrantqiao [tyrantqiao@gmail.com]
 * @see <a href="https://github.com/tyrantqiao">qiao's github</a>
 */
public class ConvertUtil {
    private static Logger logger = LoggerFactory.getLogger(ConvertUtil.class);
    public static final String DecimalFormat = "###,###,###,##0.00";
    public static final String DecimalFormat2 = "###,###,###,##0.0";
    public static final String DecimalFormat1 = "###0";
    public static final String DateFormat = "yyyy-MM-dd";

    public static String objectToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    /**
     * Description: 把字符串转换为整数。
     *
     * @param str 待转换的字符串
     * @return int数
     */
    public static int stringToInt(String str) throws NumberFormatException {
        if (str == null || str.equals("")) {
            return 0;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    /**
     * Description: 把字符串转换为长整形
     *
     * @param str 待转换的字符串
     * @return long数
     */
    public static long stringToLong(String str) throws ParseException {
        if (str == null || str.equals("")) {
            return 0;
        }
        return ConvertUtil.stringToNumber(str).longValue();

    }

    /**
     * Description: 把字符串转换为DOUBLE
     *
     * @param str 待转换的字符串
     * @return double数
     */
    public static double stringToDouble(String str) throws ParseException {
        if (str == null || str.equals("")) {
            return 0;
        }
        return ConvertUtil.stringToNumber(str).doubleValue();
    }

    /**
     * Description: 把字符串转换为Decimal
     *
     * @param str 待转换的字符串
     * @return 整数
     */
    public static BigDecimal stringToDecimal(String str) throws ParseException {
        return new BigDecimal(ConvertUtil.stringToNumber(str).doubleValue());
    }

    /**
     * Description: 把字符串转换为NUMBER
     *
     * @param str 待转换的字符串
     * @return Number数
     */
    public static Number stringToNumber(String str) throws ParseException {
        if (str == null) {
            return null;
        }
        if (str.equals("")) {
            str = "0";
        }
        Number number;
        DecimalFormat df = new DecimalFormat(DecimalFormat);
        try {
            number = df.parse(str);
        } catch (ParseException e) {
            throw e;
        }
        return new BigDecimal(number.toString());
    }

    /**
     * Description: 把BigDecimal类型转换为字符串###,###,###,##0.00
     *
     * @param number
     * @return 字符串
     */
    public static String decimalToString(BigDecimal number) {
        if (number == null) {
            return "0.00";
        }
        DecimalFormat df = new DecimalFormat(DecimalFormat);
        return df.format(number);
    }

    /**
     * Description: 把BigDecimal类型转换为字符串###0
     *
     * @param number
     * @return 字符串
     */
    public static String decimalToString1(BigDecimal number) {
        if (number == null) {
            return "0";
        }
        DecimalFormat df = new DecimalFormat(DecimalFormat1);

        return df.format(number);
    }

    /**
     * Description: 把Number类型转换为字符串
     *
     * @param number
     * @return
     */
    public static String decimalToString(Number number) {
        if (number == null) {
            return "0.00";
        }
        DecimalFormat df = new DecimalFormat(DecimalFormat);

        return df.format(number);
    }

    /**
     * Description: 把double类型转换为字符串
     *
     * @param number
     * @return
     */
    public static String decimalToString(double number) {
        DecimalFormat df = new DecimalFormat(DecimalFormat);
        return df.format(number);
    }

    public static String decimalToString2(double number) {
        DecimalFormat df = new DecimalFormat(DecimalFormat2);
        return df.format(number);
    }

    /**
     * Description: 把long类型转换为字符串
     *
     * @param number
     * @return
     */
    public static String decimalToString(long number) {
        DecimalFormat df = new DecimalFormat(DecimalFormat);
        return df.format(number);
    }

    /**
     * Description: 把java.util.Date类型转换为字符串yyyy-MM-dd
     * 不建议使用，推荐使用 DateUtil.parseDate(Date date, String fm)
     *
     * @param date
     * @return
     */
    @Deprecated
    public static String dateToString(java.util.Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DateFormat);
        return sdf.format(date);
    }

    /**
     * Description: 将OBJECT转换为STRING
     *
     * @param obj
     * @return
     */
    public static String objToString(Object obj) {
        return ConvertUtil.objToString(obj, null);
    }

    /**
     * Description: 将OBJECT转换为STRING
     *
     * @param obj
     * @param retStr 为空时的返回值
     * @return 如果OBJ为NULL， 则返回retStr
     */
    public static String objToString(Object obj, String retStr) {
        if (obj == null) {
            return retStr;
        }
        return obj.toString();
    }

    /**
     * Description: 将传入的INT值，装换为固定字符补齐的字符串
     *
     * @param num   长度
     * @param value INT值
     * @return
     */
    public static String intToString(int value, int num, char vchar) {
        StringBuffer result = new StringBuffer();
        result.append(Integer.toString(value));
        while (num > result.length()) {
            result.insert(0, vchar);
        }
        return result.toString();
    }

    /**
     * 〈一句话功能简述〉 〈功能详细描述〉
     *
     * @param object
     * @return
     */
    public static boolean objToBoolean(Object object) {
        if (object != null && StringUtils.isNotBlank(object.toString())) {
            return Boolean.parseBoolean(object.toString());
        }
        return false;
    }

    /**
     * 将对象格式化成Json格式，其中日期格式化成：yyyy-MM-dd HH:mm:ss
     *
     * @param bean
     * @return
     */
    public static String objToJsonStrWithDateFormat(Object bean) {
        String result = ConvertUtil.objToJsonStrWithDateFormat(bean, DateUtil.NORMAL_DATETIME_FORMAT);
        if (StringUtils.isNotBlank(result)) {
            /*
             * try { result = UrlCodeing.urlDecode(UrlCodeing.urlEncode(new
             * String(result.getBytes("UTF-8")))); } catch (UnsupportedEncodingException e) {
             * logger.error("转换日志输入信息时出现异常：", e); }
             */
        }
        return result;
    }

    public static String objToJsonStrWithDateFormat(Object bean, String format) {
        return JSONObject.toJSONStringWithDateFormat(bean, format, SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * 转换对象类型
     *
     * @param sourceObject 源对象
     * @param clz          目标对象类型
     * @return
     */
    public static <T> T convertType(Object sourceObject, Class<T> clz) {
        if (sourceObject == null) {
            return null;
        }
        if (sourceObject instanceof String) {
            return JSONObject.parseObject(sourceObject.toString(), clz);
        }
        return JSONObject.parseObject(ConvertUtil.objToJsonStrWithDateFormat(sourceObject), clz);

    }

    /**
     * 转换对象列表
     *
     * @param sourceArray 源对象列表
     * @param clz         目标对象类型
     * @return
     */
    public static <T, E> List<T> convertTypeArray(List<E> sourceArray, Class<T> clz) {
        if (EmptyUtil.isEmpty(sourceArray)) {
            return null;
        }
        List<T> result = new ArrayList<T>();
        for (E source : sourceArray) {
            result.add(ConvertUtil.convertType(source, clz));
        }
        return result;

    }

    /**
     * 转换对象列表
     *
     * @param sourceArrayStr 源对象列表,格式:[{},{}]
     * @param clz            目标对象类型
     * @return
     */
    public static <T, E> List<T> convertTypeArray(String sourceArrayStr, Class<T> clz) {
        if (EmptyUtil.isEmpty(sourceArrayStr)) {
            return null;
        }
        List<T> result = null;
        try {
            result = JSONObject.parseArray(sourceArrayStr, clz);
        } catch (Exception e) {
            logger.error("转换json数组出现异常：", e);
        }
        return result;

    }

    /**
     * 将List转成Map的通用方法 author:qinlu 2016年2月25日
     *
     * @param coll
     * @param keyType
     * @param valueType
     * @param keyMethodName
     * @return
     */
    public static <K, V> Map<K, V> listToMap(final java.util.Collection<V> coll, final Class<K> keyType,
                                             final Class<V> valueType, final String keyMethodName) {
        final HashMap<K, V> map = new HashMap<K, V>();
        Method method = null;

        if (coll == null || coll.size() == 0) {
            return map;
        }

        try {
            method = valueType.getMethod(keyMethodName);
        } catch (final NoSuchMethodException e) {
            String message = "List2Map转换失败，方法不存在";
            logger.error(message, e);
            throw new IllegalArgumentException(message, e);
        }
        try {
            for (final V value : coll) {

                Object object;
                object = method.invoke(value);
                @SuppressWarnings("unchecked") final K key = (K) object;
                map.put(key, value);
            }
        } catch (final Exception e) {
            final String message = "List2Map转换失败，字段不存在";
            logger.error(message, e);
            throw new IllegalArgumentException(message, e);
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> listToMap(final java.util.Collection<V> coll, final Class<K> keyType,
                                             final Class<V> valueType, final String keyMethodName, String keySplitChar) {
        final HashMap<K, V> map = new HashMap<K, V>();
        Method method = null;

        if (coll == null || coll.size() == 0) {
            return map;
        }

        try {
            method = valueType.getMethod(keyMethodName);
        } catch (final NoSuchMethodException e) {
            String message = "List2Map转换失败，方法不存在";
            logger.error(message, e);
            throw new IllegalArgumentException(message, e);
        }
        try {
            for (final V value : coll) {
                Object object;
                object = method.invoke(value);
                final K key = (K) object;
                if (EmptyUtil.isNotEmpty(keySplitChar)) {
                    for (String subKey : key.toString().split(String.format("[%s]", keySplitChar))) {
                        map.put((K) subKey, value);
                    }
                    continue;
                }
                map.put(key, value);
            }
        } catch (final Exception e) {
            final String message = "List2Map转换失败，字段不存在";
            logger.error(message, e);
            throw new IllegalArgumentException(message, e);
        }
        return map;
    }

    public static <T> List<T> setToList(Set<T> set) {
        List<T> retVal = new ArrayList<T>(set);
        return retVal;
    }

    public static <T> Set<T> listToSet(List<T> l) {
        Set<T> retVal = new HashSet<T>(l);
        return retVal;
    }

    /**
     * 方法名称:transStringToMap author:qinlu 2016年3月14日 传入参数:mapString 形如
     * username'chenziwen^password'1234
     *
     * @param mapString
     * @param firstLevelSeparate  以上例子来看，第一层为^
     * @param secondLevelSeparate 第二层为'
     * @return Map
     */
    public static Map<String, String> transStringToMap(String mapString, String firstLevelSeparate,
                                                       String secondLevelSeparate) {
        Map<String, String> map = new HashMap<String, String>();
        java.util.StringTokenizer items;
        for (StringTokenizer entrys = new StringTokenizer(mapString, firstLevelSeparate); entrys.hasMoreTokens(); map
                .put(items.nextToken(), (String) (items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))) {
            items = new StringTokenizer(entrys.nextToken(), secondLevelSeparate);
        }
        return map;
    }

    /**
     * 方法名称:transMapToString author:qinlu 2016年3月14日
     *
     * @param map
     * @param firstLevelSeparate
     * @param secondLevelSeparate
     * @return 返回值:String 形如 username'chenziwen^password'1234 ^为第一层，'为第二层。
     */
    @SuppressWarnings("rawtypes")
    public static String transMapToString(Map map, String firstLevelSeparate, String secondLevelSeparate) {
        java.util.Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            entry = (java.util.Map.Entry) iterator.next();
            sb.append(entry.getKey().toString()).append(secondLevelSeparate).append(null == entry
                    .getValue() ? "" : entry.getValue().toString()).append(iterator
                    .hasNext() ? firstLevelSeparate : "");
        }
        return sb.toString();
    }

    public static List<Integer> stringListToIntegerList(List<String> inList) {
        List<Integer> iList = new ArrayList<Integer>(inList.size());
        try {
            for (int i = 0, j = inList.size(); i < j; i++) {
                iList.add(Integer.parseInt(inList.get(i)));
            }
        } catch (Exception e) {
        }
        return iList;
    }


    @SuppressWarnings("unchecked")
    public static <T> List<Map<String, Object>> exportConvertor(List<T> dts) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        if (dts != null && !dts.isEmpty()) {
            for (T dto : dts) {
                if (dto != null) {
                    String dtoStr = ConvertUtil.objToJsonStrWithDateFormat(dto);
                    Map<String, Object> m = ConvertUtil.convertType(dtoStr, Map.class);
                    result.add(m);
                }
            }
        }
        return result;
    }

    /**
     * @param
     * @param jsonResult
     * @param
     * @return List<Object> 返回类型
     * @throws
     * @Title: getListResultFromJson
     * @Description: (这里用一句话描述这个方法的作用)
     */
    public static <T> List<T> getListFromJson(String jsonResult, Class<T> type) {
        List<T> result = JSONObject.parseArray(jsonResult, type);
        return result;
    }

    public static Map<String, Object> transJsonToMap(String msg) {
        return JSONObject.parseObject(msg);
    }
}
