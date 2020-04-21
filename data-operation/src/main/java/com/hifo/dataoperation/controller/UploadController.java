package com.hifo.dataoperation.controller;

import com.hifo.dataoperation.base.BasicController;
import com.hifo.dataoperation.intercept.RequiredPermission;
import com.hifo.dataoperation.pagination.Pagination;
import com.hifo.dataoperation.service.upload.UploadService;
import com.hifo.dataoperation.vo.PlUploadVO;
import io.swagger.annotations.*;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author whc
 * @date 2019/4/8 18:09
 */
@Api(tags = "上传下载")
@RequestMapping("upload")
@RestController
public class UploadController extends BasicController {

    @Autowired
    private UploadService uploadService;

    /**
     * 日志
     *
     * @return List
     */
    @ApiOperation(value = "日志列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "excel类型", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "createName", value = "操作人", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页数", paramType = "query", dataType = "int", example = "1", required = true),
            @ApiImplicitParam(name = "limit", value = "每页条数", paramType = "query", dataType = "int", example = "5", required = true)
    })
    @GetMapping("logList")
    @RequiredPermission("200")
    public String logList(Pagination pages, String type, String createName) {
        var page = uploadService.logList(pages, type, createName);
        return pageData(page.getTotal(), page.getRecords());
    }

    /**
     * 下载上传失败的数据
     */
    @ApiOperation(value = "下载上传失败的数据")
    @GetMapping("downloadFailed")
    public void downloadFailed(HttpServletResponse response, String id) {
        uploadService.downloadFailed(response, id);
    }

    /**
     * 上传单文件
     */
    @ApiOperation(value = "上传单文件")
    @PostMapping(value = "uploadFile", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    public ApiResult uploadFile(@ApiParam(name = "file", value = "上传单文件", required = true) MultipartFile file) {
        try {
            return ApiResult.success(saveFile(file));
        } catch (Exception e) {
            return ApiResult.failed(e.getMessage());
        }

    }

    /**
     * 上传多文件
     */
    @ApiOperation(value = "上传多文件")
    @PostMapping(value = "uploadFiles", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    public ApiResult uploadFiles(@ApiParam(name = "files", value = "上传多文件", required = true) MultipartFile[] files) {
        try {
            return ApiResult.success(saveFiles(files));
        } catch (Exception e) {
            return ApiResult.failed(e.getMessage());
        }
    }

    /**
     * 大文件分段上传测试
     */
    @ApiOperation(value = "大文件分段上传测试")
    @PostMapping(value = "uploadBigFile", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    public ApiResult uploadBigFile(@ApiParam(name = "大文件属性对象") PlUploadVO plUpload, @ApiParam(name = "file", value = "上传大文件", required = true) MultipartFile file) {
        if (plUpload.getChunks() != null && plUpload.getChunks() != 0) {
            return ApiResult.success(uploadWithBlock(plUpload, file));
        } else {
            return ApiResult.success(saveFile(file));
        }
    }
}
