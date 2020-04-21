package com.hifo.dataoperation.util;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Excel工具类
 *
 * @author whc
 * @date 2019/4/9 13:31
 */
public class ExcelUtil {

    /**
     * 把一行的数据按照顺序返回，碰到空就结束
     *
     * @param row Excel的一行
     * @return List
     */
    public static List<String> row2List(Row row) {
        try {
            List<String> list = new ArrayList<>();
            int columnIndex = 0;
            while (true) {
                Cell cell = row.getCell(columnIndex);
                String value = getCellValue(cell);
                if (StringUtil.isNull(value)) {
                    break;
                }
                list.add(value);
                columnIndex++;
            }
            return list;
        } catch (Exception ignored) {
            return new ArrayList<>();
        }
    }

    /**
     * 把一行的数据当成Map返回
     *
     * @param row       Excel的一行
     * @param titleInDb Map的Key
     * @return Map
     */
    public static Map<String, Object> row2Map(Row row, List<String> titleInDb) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (int i = 0; i < titleInDb.size(); i++) {
            Cell cell = row.getCell(i);
            String value = getCellValue(cell);
            map.put(titleInDb.get(i), value);
        }
        return map;
    }

    /**
     * 获取单元格各类型值，返回字符串类型
     *
     * @param cell Excel的单元格
     * @return 不管什么类型最后都返回String
     */
    private static String getCellValue(Cell cell) {
        //判断是否为null或空串
        if (StringUtil.isNull(cell)) {
            return "";
        }
        String cellValue;
        CellType cellType = cell.getCellType();
        switch (cellType) {
            //字符串类型
            case STRING:
                cellValue = cell.getStringCellValue().trim();
                cellValue = StringUtil.isNull(cellValue) ? "" : cellValue;
                break;
            //布尔类型
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            //数值类型
            case NUMERIC:
                //判断日期类型
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    cellValue = DateUtil.defaultStr(cell.getDateCellValue());
                } else {
                    //否
                    cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue());
                }
                break;
            //其它类型，取空串吧
            default:
                cellValue = "";
                break;
        }
        return cellValue;
    }

    /**
     * easy poi 自定义字段格式化
     *
     * @param headers
     * @return
     */
    public static List<ExcelExportEntity> formatExcelExportEntity(String[] headers) {
        List<ExcelExportEntity> entity = new ArrayList<>();
        int length = headers == null ? 0 : headers.length;
        for (int i = 0; i < length; i++) {
            String param = headers[i];
            if (param.indexOf("_") != -1) {
                String[] params = StringUtils.split(param, "_");
                ExcelExportEntity obj = new ExcelExportEntity(params[0], params[1]);
                obj.setNeedMerge(true);
                entity.add(obj);
            }
        }
        return entity;
    }

    /**
     * 导出excel
     *
     * @param list
     * @param title
     * @param sheetName
     * @param headers        String[] name_code,name_code
     * @param fileName
     * @param isCreateHeader
     * @param response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, String[] headers, String fileName, boolean isCreateHeader, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, formatExcelExportEntity(headers), fileName, response, exportParams);
    }

    /**
     * 导出excel
     *
     * @param list
     * @param title
     * @param sheetName
     * @param headers   String[] name_code,name_code
     * @param fileName
     * @param response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, String[] headers, String fileName, HttpServletResponse response) {
        defaultExport(list, formatExcelExportEntity(headers), fileName, response, new ExportParams(title, sheetName));
    }

    /**
     * 可选属性导出excel默认方法
     *
     * @param list
     * @param entity
     * @param fileName
     * @param response
     * @param exportParams
     */
    private static void defaultExport(List<?> list, List<ExcelExportEntity> entity, String fileName, HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, entity, list);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    /**
     * 导出excel
     *
     * @param list
     * @param title
     * @param sheetName
     * @param pojoClass
     * @param fileName
     * @param isCreateHeader
     * @param response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    /**
     * 导出excel
     *
     * @param list
     * @param title
     * @param sheetName
     * @param pojoClass
     * @param fileName
     * @param response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    /**
     * 导出excel
     *
     * @param list
     * @param fileName
     * @param response
     */
    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        defaultExport(list, fileName, response);
    }

    /**
     * 导出excel默认方法
     *
     * @param list
     * @param fileName
     * @param response
     */
    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    /**
     * 下载excel
     *
     * @param fileName
     * @param response
     * @param workbook
     */
    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 默认导出设置
     *
     * @param list
     * @param fileName
     * @param response
     */
    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    /**
     * 导入excel
     *
     * @param filePath
     * @param titleRows
     * @param headerRows
     * @param pojoClass
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        return ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
    }

    /**
     * 导入excel
     *
     * @param file
     * @param titleRows
     * @param headerRows
     * @param pojoClass
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) throws Exception {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        return ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
    }

}
