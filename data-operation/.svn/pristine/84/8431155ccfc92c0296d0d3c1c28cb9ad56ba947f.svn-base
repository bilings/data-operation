package com.hifo.dataoperation.controller;

import com.hifo.dataoperation.base.BasicController;
import com.hifo.dataoperation.entity.BusSetting;
import com.hifo.dataoperation.intercept.RequiredPermission;
import com.hifo.dataoperation.service.setting.BusSettingService;
import com.hifo.dataoperation.vo.BusSettingVO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * 机构设置
 *
 * @author jinzhichen
 * @date 2019/4/12 15:12
 */
@Api(tags = "机构管理")
@RestController
@RequestMapping("/busSetting")
public class BusSettingController extends BasicController {

    @Autowired
    private BusSettingService busSettingService;

    /**
     * 询价单-查询机构设置信息
     *
     * @return BusSetting
     */
    @RequiredPermission("602")
    @ApiOperation(value = "询价单-查询机构设置信息")
    @GetMapping("/query")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictionaryId", value = "客户分类id(关联字典表dictionary主键)", paramType = "query", dataType = "int", required = true)
    })
    public ApiResult<BusSetting> query(@ApiIgnore BusSettingVO vo) {
        return busSettingService.query(vo);
    }

    /**
     * 询价单-保存或更新机构设置信息
     *
     * @param busSetting BusSetting
     * @return ApiResult
     */
    @RequiredPermission("602")
    @ApiOperation(value = "询价单-保存或更新机构设置信息")
    @PostMapping("/saveOrUpdate")
    public ApiResult saveOrUpdate(@ApiParam(name = "询价单-保存或更新机构设置信息", value = "json", required = true) @RequestBody BusSetting busSetting) {
        return busSettingService.updateBusSetting(busSetting);
    }

    /**
     * 询价单-上传文件
     *
     * @param request HttpServletRequest
     * @return ApiResult
     */
    @RequiredPermission("602")
    @ApiOperation(value = "询价单-上传文件")
    @PostMapping("/logoUpload")
    @ResponseBody
    public ApiResult logoUpload(HttpServletRequest request) {
        try {
            String fileName = saveUploadFile(request).get("relativePath").replace("static", "");
            return ApiResult.success(fileName);
        } catch (Exception e) {
            return ApiResult.failed(e.getMessage());
        }
    }

}
