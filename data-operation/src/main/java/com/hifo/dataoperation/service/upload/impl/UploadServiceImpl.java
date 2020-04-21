package com.hifo.dataoperation.service.upload.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hifo.dataoperation.base.MybatisPlusService;
import com.hifo.dataoperation.entity.BusUploadLog;
import com.hifo.dataoperation.mapper.upload.BusUploadLogMapper;
import com.hifo.dataoperation.pagination.Pagination;
import com.hifo.dataoperation.service.base.BasicService;
import com.hifo.dataoperation.service.upload.UploadService;
import com.hifo.dataoperation.util.DateUtil;
import com.hifo.dataoperation.util.StringUtil;
import lombok.extern.java.Log;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author whc
 * @date 2019/4/9 8:44
 */
@Log
@Service
public class UploadServiceImpl extends MybatisPlusService<BusUploadLogMapper, BusUploadLog> implements UploadService {

    @Value("${web.upload-path}")
    private String FilePath;
    @Value("${web.rel-path}")
    private String RelPath;

    @Autowired
    private BasicService basicService;


    @Override
    public IPage logList(Pagination pages, String type, String createName) {
        QueryWrapper query = new QueryWrapper();
        query.eq("organizationId", basicService.getLoginEmployee().getOrganizationId());
        if (!StringUtil.isNull(type)) {
            query.eq("type", type);
        }
        if (!StringUtil.isNull(createName)) {
            query.like("createName", createName);
        }
        IPage page = baseMapper.selectPage(pages, query);
        ((List<BusUploadLog>) pages.getRecords()).stream().forEach(e -> {
            e.setCreateTime(DateUtil.defaultStr(e.getCreateTime()));
        });
        return page;
    }

    @Override
    public void save(String fileName, int successNo, int failedNo, String failedFile, String type) {
        BusUploadLog uploadLog = new BusUploadLog();
        uploadLog.setOrganizationId(basicService.getLoginEmployee().getOrganizationId());
        uploadLog.setFileName(fileName);
        uploadLog.setType(type);
        uploadLog.setTotal(successNo + failedNo);
        uploadLog.setSuccess(successNo);
        uploadLog.setFailed(failedNo);
        uploadLog.setFailedFile(failedFile);
        uploadLog.setCreateId(basicService.getLoginEmployee().getId().intValue());
        uploadLog.setCreateName(basicService.getLoginEmployee().getName());
        save(uploadLog);
    }

    @Override
    public void downloadFailed(HttpServletResponse response, String id) {
        try {
            BusUploadLog uploadLog = baseMapper.selectById(id);
            // 判断当前用户是否有权限
            if (uploadLog.getOrganizationId().intValue() != basicService.getLoginEmployee().getOrganizationId().intValue()) {
                return;
            }

            // 下载
            File downloadFile = new File(uploadLog.getFailedFile());
            Workbook workBook = WorkbookFactory.create(new FileInputStream(downloadFile));
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(downloadFile.getName(), "UTF-8"));
            workBook.write(response.getOutputStream());
        } catch (Exception e) {
            log.warning("文件下载失败。");
        }
    }

    @Override
    public String getTempPath() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return FilePath + "excel/" + sdf.format(new Date());
    }

    @Override
    public File downloadFailedFile(ExportParams entity, Class<?> pojoClass, Collection<?> dataSet) {
        File failedFile = null;
        Workbook workbook = null;
        OutputStream os = null;
        try {
            // 检查文件夹路径是否存在
            File targetFile = new File(getTempPath());
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }

            failedFile = new File(getTempPath(), "failed_" + System.currentTimeMillis() + ".xls");
            if (dataSet.size() > 0) {
                // 把错误的数据保存为文件
                workbook = ExcelExportUtil.exportExcel(entity, pojoClass, dataSet);
                os = new FileOutputStream(failedFile);
                workbook.write(os);
            }
        } catch (Exception e) {
            log.warning(e.getMessage());
        } finally {
            // 释放内存
            if (workbook != null) {
                try {
                    workbook.close();
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e) {
                    log.warning(e.getMessage());
                }
            }
        }
        return failedFile;
    }
}
