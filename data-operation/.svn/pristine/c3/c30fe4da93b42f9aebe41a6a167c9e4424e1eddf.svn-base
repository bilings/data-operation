package com.hifo.dataoperation.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hifo.dataoperation.entity.ExtEmployee;

import javax.swing.text.Document;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JSON的处理类
 *
 * @author whc
 * @date 2019/2/25 10:50
 */
public class JSONUtil {

    /**
     * 对象转换成json字符串
     *
     * @param object 任意对象
     * @return json字符串
     */
    public static String obj2Str(Object object) {
        return JSONObject.toJSONString(object, SerializerFeature.WriteMapNullValue);
    }

    /**
     * json字符串转换成List
     *
     * @param jsonStr json字符串
     * @return list
     */
    public static List str2List(String jsonStr) {
        return (List) JSONObject.parse(jsonStr);
    }

    /**
     * json字符串转换成map，如果不是json字符串则返回null
     *
     * @param jsonStr json字符串
     * @return map
     */
    public static Map<String, Object> str2Map(String jsonStr) {
        try {
            return JSONObject.parseObject(jsonStr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * json字符串转换成java对象，如果不是json字符串则返回null
     *
     * @param jsonStr json字符串
     * @param clazz   java对象的class
     * @param <T>     泛型
     * @return <T>对象
     */
    public static <T> T str2Java(String jsonStr, Class<T> clazz) {
        JSONObject json = JSONObject.parseObject(jsonStr);
        return JSONObject.toJavaObject(json, clazz);
    }

    /**
     * map转换成java对象，如果不是json字符串则返回null
     *
     * @param map   map
     * @param clazz java对象的class
     * @param <T>   泛型
     * @return <T>对象
     */
    public static <T> T map2Java(Map<String, Object> map, Class<T> clazz) {
        JSONObject json = JSONObject.parseObject(obj2Str(map));
        return JSONObject.toJavaObject(json, clazz);
    }

    /**
     * java对象转换成map对象
     *
     * @param object java对象
     * @return Map
     */
    public static Map<String, Object> java2Map(Object object) {
        String jsonStr = obj2Str(object);
        return JSONObject.parseObject(jsonStr);
    }

    public static org.bson.Document object2Doc(Object obj) {
        org.bson.Document document=new org.bson.Document();
        if (obj == null) {
            return document;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                document.append(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * 一个java对象转换为另一个java对象
     *
     * @param object 源java对象
     * @param clazz  目标java对象
     * @param <T>    泛型
     * @return 目标java对象
     */
    public static <T> T obj2Java(Object object, Class<T> clazz) {
        String jsonStr = obj2Str(object);
        return str2Java(jsonStr, clazz);
    }

    /**
     * 把Map列表转换为Java列表
     *
     * @param mapList 源数据
     * @param clazz   目标java对象
     * @param <T>     泛型
     * @return 目标java对象列表
     */
    public static <T> List<T> mapList2JavaList(List<Map<String, Object>> mapList, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            list.add(map2Java(map, clazz));
        }
        return list;
    }

    public static Map<String, Object> obj2Map(Object object) {
        String jsonStr = obj2Str(object);
        return str2Map(jsonStr);
    }
}
