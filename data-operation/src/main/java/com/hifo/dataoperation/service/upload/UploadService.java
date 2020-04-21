package com.hifo.dataoperation.service.upload;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hifo.dataoperation.entity.BusUploadLog;
import com.hifo.dataoperation.pagination.Pagination;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Collection;

/**
 * @author xmw
 * @date 2019/4/28 18:04
 */
public interface UploadService extends IService<BusUploadLog> {

    /**
     * 上传日志列表
     *
     * @param pages
     * @param type
     * @param createName
     * @return
     */
    IPage logList(Pagination pages, String type, String createName);

    /**
     * 保存上传日志
     *
     * @param fileName   文件名（不要路径）
     * @param successNo  成功数
     * @param failedNo   失败数
     * @param failedFile 错误文件（包含路径，用以下载）
     * @param type       类型（楼盘，楼栋，房号，案例）
     */
    void save(String fileName, int successNo, int failedNo, String failedFile, String type);

    /**
     * 下载Excel文件
     *
     * @param response HttpServletResponse
     * @param id       BusUploadLog.id
     */
    void downloadFailed(HttpServletResponse response, String id);

    /**
     * 获取上传、下载文件的临时路径
     *
     * @return
     * @throws Exception
     */
    String getTempPath() throws Exception;

    /**
     * 下载导入的错误数据excel
     *
     * @param entity
     * @param pojoClass
     * @param dataSet
     * @return
     */
    File downloadFailedFile(ExportParams entity, Class<?> pojoClass, Collection<?> dataSet);
}
