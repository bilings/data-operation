package com.hifo.dataoperation.controller;

import com.hifo.dataoperation.intercept.RequiredPermission;
import com.hifo.dataoperation.service.setting.AdministrativeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(tags = "行政区划接口")

@RequestMapping("/city")
@RestController
/**
 * 
 * @author 杨捷
 * @date 2019年5月9日
 * @description
 */
public class AdministrativeController {

    @Autowired
    private AdministrativeService administrativeService;

//    @RequiredPermission("605")
//    @ApiOperation(value = "修改行政区域")
//    @PostMapping("/modify")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "mongoId", required = true, value = "行政区域id", paramType = "query", dataType = "int"),
//            @ApiImplicitParam(name = "name", required = false, value = "名称", paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "type", required = false, value = "类型", paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "simpleName", required = false, value = "简称", paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "parentName", required = false, value = "父级名称", paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "otherName", required = false, value = "曾用名", paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "comment", required = false, value = "备注", paramType = "query", dataType = "String")
//    })
//    public ApiResult updateAdministrative(BusAdministrative entity) {
//        boolean res = administrativeService.updateAdministrative(entity);
//        if (res) {
//            return ApiResult.success();
//        }
//
//        return ApiResult.failed();
//    }

    /**
     * 获取员工要开通的城市
     * @param key
     * @return
     */
    @ResponseBody
    @RequiredPermission("605")
    @GetMapping("/listByOpen")
    @ApiOperation(value = "获取行政区域列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", required = true, value = "搜索的城市名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "empId", required = true, value = "员工ID", paramType = "query", dataType = "String"),
    })
    public ApiResult list(String key,String empId) {
        List<Map> list = administrativeService.listByOpen(key,empId);
        return ApiResult.success(list);
    }
}
