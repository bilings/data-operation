package com.hifo.dataoperation.util;

import com.hifo.dataoperation.vo.TreeVO;
import lombok.extern.java.Log;
import lombok.var;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * tree工具类
 *
 * @Author: xmw
 * @Date: 2019/5/5 18:11
 */
@Log
public class TreeUtils {

    /**
     * 根据Map获取其对应树对象
     *
     * @param map
     * @return
     */
    public static <T extends TreeVO> T getTreeByMap(LinkedHashMap map) {
        return getTreeByMap(map, false);
    }

    /**
     * 根据Map获取其对应树对象
     *
     * @param map
     * @param flg 对象数据是否返回tree
     * @return
     */
    public static <T extends TreeVO> T getTreeByMap(LinkedHashMap map, boolean flg) {
        List<T> list = new ArrayList<>();
        Iterator<Map.Entry<Long, T>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, T> next = iterator.next();
            list.add(next.getValue());
        }
        return getTreeByList(list, flg);
    }

    /**
     * 根据列表获取其对应树对象
     *
     * @param list
     * @return
     */
    public static <T extends TreeVO> T getTreeByList(List<?> list) {
        return getTreeByList(list, false);
    }

    /**
     * 根据列表获取其对应树对象
     *
     * @param list
     * @param flg  对象数据是否返回tree
     * @return
     */
    public static <T extends TreeVO> T getTreeByList(List<?> list, boolean flg) {
        var tree = new TreeVO();
        try {
            var voList = new ArrayList<TreeVO>();
            var size = list.size();
            TreeVO trees;
            Map<String, Object> map;
            for (var i = 0; i < size; i++) {
                map = getObjectPropertyValue(list.get(i));
                if (map.get("parentId") == null ) {
                    trees = new TreeVO();
                    trees.setId(map.get("id").toString());
                    trees.setName((String) map.get("name"));
                    trees.setTitle(trees.getName());
                    trees.setLabel(trees.getName());
                    trees.setChildren(formatTreeByParentId(list, trees.getId(), flg));
                    trees.setParentId(null);
                    trees.setExpand(true);
                    trees.setChecked(false);
                    if (flg) {
                        trees.setObj(list.get(i));
                    }
                    voList.add(trees);
                }
            }
            tree.setChildren(voList);
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
        return (T) tree;
    }

    /**
     * 根据父节点id获取子节点集合
     *
     * @param list
     * @param parentId
     * @param flg      对象数据是否返回tree
     * @return
     */
    public static <T extends List<TreeVO>> T formatTreeByParentId(List<?> list, String parentId, boolean flg) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var vosList = new ArrayList<TreeVO>();

        var size = list.size();
        TreeVO trees;
        Map<String, Object> map;
        for (var i = 0; i < size; i++) {
            map = getObjectPropertyValue(list.get(i));
            if (map.get("parentId") != null && map.get("parentId").toString().equals(parentId)) {
                trees = new TreeVO();
                trees.setId(map.get("id").toString());
                trees.setName((String) map.get("name"));
                trees.setTitle(trees.getName());
                trees.setLabel(trees.getTitle());
                trees.setChildren(formatTreeByParentId(list, trees.getId(), flg));
                trees.setParentId(parentId);
                trees.setExpand(true);
                trees.setChecked(false);
                if (flg) {
                    trees.setObj(list.get(i));
                }
                vosList.add(trees);
            }
        }
        return (T) vosList;
    }

    /**
     * obj 转属性map
     *
     * @param obj
     * @return
     */
    public static <T extends Map<String, Object>> T getObjectPropertyValue(Object obj) throws InvocationTargetException, IllegalAccessException {
        Map<String, Object> map = new HashMap<>(3);
        Class<? extends Object> tClass = obj.getClass();
        //得到所有属性
        var field = getClassFields(tClass);
        var length = field.length;
        for (var i = 0; i < length; i++) {
            //设置可以访问私有变量
            field[i].setAccessible(true);
            //获取属性的名字
            var name = field[i].getName();
            //将属性名字的首字母大写
            var fix = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
            //整合出 getId() 属性这个方法
            Method m = getMethodByName(tClass, "get" + fix);
            if (m != null) {
                map.put(name, m.invoke(obj));
            }
        }
        return (T) map;
    }

    /**
     * 获取实体所有属性
     *
     * @param cur_class
     * @return
     */
    public static Field[] getClassFields(Class cur_class) {
        Field[] fields = cur_class.getDeclaredFields();
        if (cur_class.getSuperclass() != null) {
            fields = ArrayUtils.addAll(fields, getClassFields(cur_class.getSuperclass()));
        }
        return fields;
    }

    /**
     * 根据实体属性名获取方法
     *
     * @param cur_class
     * @return
     */
    public static Method getMethodByName(Class cur_class, String name) {
        Method m = null;
        try {
            m = cur_class.getMethod(name);
        } catch (Exception e) {
            if (cur_class.getSuperclass() != null) {
                m = getMethodByName(cur_class.getSuperclass(), name);
            }
        }
        return m;
    }

}
