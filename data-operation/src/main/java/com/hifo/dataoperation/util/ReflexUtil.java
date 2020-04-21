package com.hifo.dataoperation.util;

import java.lang.reflect.Method;

/**
 * 反射工具类
 *
 * @author whc
 * @date 2019/4/4 14:16
 */
public class ReflexUtil {

    /**
     * 通过反射为一个对象的某个属性设置值
     * 如果没有该属性，不做任何事
     *
     * @param t          需要设置属性的对象
     * @param methodName 设置属性的方法名
     * @param value      属性值
     * @param <T>        泛型
     */
    @SuppressWarnings("unchecked")
    public static <T> void callMethod(T t, String methodName, Integer value) {
        Class clazz = t.getClass();
        try {
            Method method = clazz.getMethod(methodName, Integer.class);
            method.invoke(t, value);
        } catch (Exception ignored) {
        }
    }

    /**
     * 通过反射为一个对象的某个属性设置值
     * 如果没有该属性，不做任何事
     *
     * @param t          需要设置属性的对象
     * @param methodName 设置属性的方法名
     * @param value      属性值
     * @param <T>        泛型
     */
    @SuppressWarnings("unchecked")
    public static <T> void callMethod(T t, String methodName, String value) {
        Class clazz = t.getClass();
        try {
            Method method = clazz.getMethod(methodName, String.class);
            if (method != null) {
                if (StringUtil.isNull(value)) {
                    value = null;
                }
                method.invoke(t, value);
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * 通过反射为一个对象的某个属性设置值
     * 如果没有该属性，不做任何事
     *
     * @param t          需要设置属性的对象
     * @param methodName 设置属性的方法名
     * @param value      属性值
     * @param <T>        泛型
     */
    @SuppressWarnings("unchecked")
    public static <T> void callMethod(T t, String methodName, Long value) {
        Class clazz = t.getClass();
        try {
            Method method = clazz.getMethod(methodName, Long.class);
            method.invoke(t, value);
        } catch (Exception ignored) {
        }
    }

    /**
     * 通过反射获取某个属性的值
     *
     * @param t
     * @param methodName
     * @param <T>
     * @return
     */
    public static <T> Object callMethodValue(T t, String methodName) {
        Class clazz = t.getClass();
        try {
            Method method = clazz.getMethod(methodName);
            return method.invoke(t);
        } catch (Exception ignored) {
        }
        return "";
    }
}
