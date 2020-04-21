package com.hifo.dataoperation.controller;

import com.hifo.dataoperation.base.BasicController;
import com.hifo.dataoperation.entity.CustomerOrg;
import com.hifo.dataoperation.intercept.RequiredPermission;
import com.hifo.dataoperation.pagination.Pagination;
import com.hifo.dataoperation.service.setting.CustomerOrgService;
import io.swagger.annotations.*;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 客户机构控制类
 *
 * @Author: xmw
 * @Date: 2019/5/5 16:49
 */
@Api(tags = "客户机构管理")
@RestController
@RequestMapping("/customerOrg")
@RequiredPermission("502")
public class CustomerOrgController extends BasicController {

    @Autowired
    private CustomerOrgService customerOrgService;

    /**
     * 新增或更新客户机构
     */
    @ApiOperation(value = "新增更新客户机构")
    @PutMapping("/update")
    public ApiResult update(@ApiParam(name = "客户机构信息", value = "json", required = true) @RequestBody @Validated CustomerOrg org) {
        return customerOrgService.update(org);
    }

    /**
     * 批量删除客户机构
     *
     * @param ids id集合，以英文逗号,分隔
     * @return ApiResult
     */
    @ApiOperation(value = "批量删除客户机构")
    @ApiImplicitParam(name = "ids", value = "客户机构id集合，以英文逗号,分隔", paramType = "query", dataType = "String", example = "1,2", required = true)
    @PutMapping("/delete")
    public ApiResult delete(String[] ids) {
        return customerOrgService.delete(ids);
    }

    /**
     * 客户机构信息详情
     *
     * @param id 客户机构id
     * @return ApiResult
     */
    @ApiOperation(value = "根据ID获取客户机构详细信息")
    @ApiImplicitParam(name = "id", value = "客户机构id", paramType = "path", dataType = "String", example = "1", required = true)
    @GetMapping("detail/{id}")
    public ApiResult detail(@PathVariable("id") Long id) {
        return ApiResult.success(customerOrgService.getDetailById(id));
    }

    /**
     * 查询客户机构列表
     *
     * @param org
     * @return
     */
    @ApiOperation(value = "查询客户机构列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "机构名称", paramType = "query", dataType = "String", example = "重庆"),
            @ApiImplicitParam(name = "type", value = "机构类型", paramType = "query", dataType = "String", example = "银行"),
            @ApiImplicitParam(name = "rank", value = "机构层级(客户机构id) 祖级查子孙级包含祖级", paramType = "query", dataType = "String", example = "1"),
            @ApiImplicitParam(name = "parentId", value = "上级机构id(0表示最上级) 父級查子集", paramType = "query", dataType = "int", example = "1")
    })
    @GetMapping("/list")
    public ApiResult list(@ApiIgnore CustomerOrg org) {
        return ApiResult.success(customerOrgService.list(org));
    }

    /**
     * 分页查询客户机构列表
     *
     * @param pages
     * @param org
     * @return
     */
    @ApiOperation(value = "分页查询客户机构列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "机构名称", paramType = "query", dataType = "String", example = "重庆"),
            @ApiImplicitParam(name = "type", value = "机构类型", paramType = "query", dataType = "String", example = "银行"),
            @ApiImplicitParam(name = "rank", value = "机构层级(客户机构id) 祖级查子孙级包含祖级", paramType = "query", dataType = "String", example = "1"),
            @ApiImplicitParam(name = "parentId", value = "上级机构id(0表示最上级) 父級查子集", paramType = "query", dataType = "int", example = "1"),
            @ApiImplicitParam(name = "page", value = "当前页数", paramType = "query", dataType = "int", example = "1", required = true),
            @ApiImplicitParam(name = "limit", value = "每页条数", paramType = "query", dataType = "int", example = "5", required = true)
    })
    @GetMapping("/findByPage")
    public String findByPage(Pagination pages,@ApiIgnore CustomerOrg org) {
        // 分页数据
        var page = customerOrgService.findByPage(pages, org);
        return pageData(page.getTotal(), page.getRecords());
    }

    /**
     * 查询客户机构树
     *
     * @param org
     * @return
     */
    @ApiOperation(value = "查询客户机构树")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "机构名称", paramType = "query", dataType = "String", example = "重庆"),
            @ApiImplicitParam(name = "type", value = "机构类型", paramType = "query", dataType = "String", example = "银行")
    })
    @GetMapping("/findByTree")
    public ApiResult findByTree(@ApiIgnore CustomerOrg org) {
        return ApiResult.success(customerOrgService.findByTree(org));
    }

}
