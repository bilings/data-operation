package com.hifo.dataoperation.util;

import com.hifo.dataoperation.mapper.base.Field;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 获客宝专用工具类（业务强耦合）
 *
 * @author whc
 * @date 2019/3/20 15:37
 */
public class HkbUtil {
    /**
     * Java对象转换成Field对象数组
     *
     * @param object 对象
     * @return Field[]
     */
    public static Field[] obj2FieldArray(Object object) {
        Map<String, Object> map = JSONUtil.java2Map(object);
        List<Field> fieldList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                fieldList.add(new Field(entry.getKey(), entry.getValue().toString()));
            }
        }
        Field[] fields = new Field[fieldList.size()];
        return fieldList.toArray(fields);
    }

    /**
     * 把source的属性复制到target（只复制target为空的对象）
     *
     * @param source 源
     * @param target 目标
     * @param <T>    泛型
     * @return T
     */
    @SuppressWarnings(value = "unchecked")
    public static <T> T copyPropertiesIfNull(T source, T target) {
        Map<String, Object> sourceMap = JSONUtil.java2Map(source);
        Map<String, Object> targetMap = JSONUtil.java2Map(target);
        // 遍历targetMap
        for (Map.Entry<String, Object> entry : targetMap.entrySet()) {
            // 如果targetMap的值为空
            if (StringUtil.isNull(entry.getValue())) {
                // 把sourceMap的值设置到targetMap
                Object value = sourceMap.get(entry.getKey());
                entry.setValue(value);
            }
        }
        return JSONUtil.map2Java(targetMap, (Class<T>) target.getClass());
    }

    /**
     * 判断2个名称是否是同一栋楼
     */
    public static boolean isTheSameBuilding(String name1, String name2) {
        name1 = stdBuildingName(name1);
        name2 = stdBuildingName(name2);
        return name1.equalsIgnoreCase(name2);
    }

    /**
     * 楼栋名称标准化
     */
    public static String stdBuildingName(String name) {
        return StringUtil.isNull(name) ? "" :
                name.replace("栋", "@")
                        .replace("幢", "@")
                        .replace("号楼", "%")
                        .replace("号", "%")
                        .replace("附", "");
    }

    /**
     * 把Map的值的List转换为Key的List
     *
     * @param title 值的List
     * @return Key的List
     */
    public static List<String> titleInDb(List<String> title, Map<String, String> map) throws Exception {
        List<String> titleInDb = new ArrayList<>();
        for (String str : title) {
            boolean find = false;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getValue().equals(str)) {
                    titleInDb.add(entry.getKey());
                    find = true;
                    break;
                }
            }
            if (!find) {
                throw new Exception("错误的表头：" + str);
            }
        }
        return titleInDb;
    }

    /**
     * 下载楼盘模板
     *
     * @param response HttpServletResponse
     */
    public static void downTemplate(HttpServletResponse response, String flag) {
        Set<Map.Entry<String, String>> map;
        String fileName;
        String sheetName;
        String estate = "estate";
        String building = "building";
        String room = "room";
        String avgPrice = "avgPrice";
        if (estate.equals(flag)) {
            map = Constant.TITLE.entrySet();
            fileName = "楼盘模板.xlsx";
            sheetName = "楼盘";
        } else if (building.equals(flag)) {
            map = Constant.BUILDING.entrySet();
            fileName = "楼栋模板.xlsx";
            sheetName = "楼栋";
        } else if (room.equals(flag)) {
            map = Constant.ROOM.entrySet();
            fileName = "房号模板.xlsx";
            sheetName = "房号";
        } else if (avgPrice.equals(flag)) {
            map = Constant.AVGPRICE.entrySet();
            fileName = "均价模板.xlsx";
            sheetName = "均价";
        } else {
            return;
        }
        try {
            // 新建Excel
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(sheetName);
            // 设置表头
            XSSFRow row = sheet.createRow(0);
            int col = 0;
            for (Map.Entry<String, String> entry : map) {
                XSSFCell cell = row.createCell(col);
                cell.setCellValue(entry.getValue());
                col++;
            }
            // 下载
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            workbook.write(response.getOutputStream());
            // 释放内存
            workbook.close();
            response.getOutputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
