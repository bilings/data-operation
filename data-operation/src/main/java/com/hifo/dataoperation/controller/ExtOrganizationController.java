package com.hifo.dataoperation.controller;

import com.hifo.dataoperation.base.BasicController;
import com.hifo.dataoperation.entity.ExtOrganization;
import com.hifo.dataoperation.intercept.RequiredPermission;
import com.hifo.dataoperation.pagination.Pagination;
import com.hifo.dataoperation.service.setting.ExtOrganizationService;
import com.hifo.dataoperation.vo.ExtOrganizationVO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


/**
 * 评估机构
 *
 * @author jinzhichen
 * @date 2019/4/12 15:12
 */
@Api(tags = "评估机构管理")
@RestController
@RequestMapping("/extOrganization")
public class ExtOrganizationController extends BasicController {

    @Autowired
    private ExtOrganizationService extOrganizationService;

    /**
     * 新增评估机构
     */
    @ApiOperation(value = "新增评估机构")
    @PostMapping("/extOrganizationAdd")
    public ApiResult extOrganizationAdd(@ApiParam(name = "新增评估机构", value = "json", required = true) @RequestBody ExtOrganizationVO vo) {
        return extOrganizationService.extOrganizationAdd(vo);
    }

    /**
     * 删除评估机构
     */
    @ApiOperation(value = "删除评估机构")
    @ApiImplicitParam(name = "id", value = "主键id，int类型", paramType = "path", dataType = "int")
    @DeleteMapping("/extOrganizationDel/{id}")
    public ApiResult extOrganizationDel(@PathVariable("id") Long id) {
        return extOrganizationService.extOrganizationDel(id);
    }

    /**
     * 编辑评估机构
     */
    @ApiOperation(value = "编辑评估机构")
    @ApiImplicitParam(name = "id", value = "主键id，int类型", paramType = "path", dataType = "int")
    @PutMapping("/extOrganizationUpdate/{id}")
    public ApiResult extOrganizationUpdate(@PathVariable("id") Long id, @ApiParam(name = "编辑评估机构", value = "json", required = true) @RequestBody ExtOrganizationVO vo) {
        return extOrganizationService.extOrganizationUpdate(id, vo);
    }

    /**
     * 批量编辑评估机构
     */
    @ApiOperation(value = "批量编辑评估机构")
    @PostMapping("/extOrganizationBatchUpdate")
    public ApiResult extOrganizationBatchUpdate(@ApiParam(name = "批量编辑评估机构", value = "json", required = true) @RequestBody List<ExtOrganization> list) {
        return ApiResult.success(extOrganizationService.extOrganizationBatchUpdate(list));
    }

    /**
     * 分页查询评估机构(自定义sql)
     */
    @ApiOperation(value = "分页查询评估机构(自定义sql)")
    @GetMapping(value = "/extOrganizationList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "地址id", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "机构名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "times", value = "使用次数", paramType = "query", dataType = "int")
    })
    public ApiResult extOrganizationList(@ApiParam(name = "分页对象") Pagination page, @ApiIgnore @RequestBody ExtOrganizationVO vo) {
        extOrganizationService.extOrganizationList(page, vo);
        return ApiResult.success(page);
    }

    /**
     * 分页查询评估机构(wrapper方式)
     */
    @ApiOperation(value = "分页查询评估机构(wrapper方式)")
    @GetMapping(value = "/extOrganizationListWrapper")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "地址id", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "机构名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "times", value = "使用次数", paramType = "query", dataType = "int")
    })
    public ApiResult extOrganizationListWrapper(@ApiParam(name = "分页对象") Pagination page, @ApiIgnore @RequestBody ExtOrganizationVO vo) {
        extOrganizationService.extOrganizationListWrapper(page, vo);
        return ApiResult.success(page);
    }


    /**
     * 评估机构详情
     */
    @ApiOperation(value = "评估机构详情")
    @GetMapping("/detail/{id}")
    @ApiImplicitParam(name = "id", value = "主键id，int类型", paramType = "path", dataType = "int")
    public ApiResult detail(@PathVariable("id") int id) {
        return ApiResult.success(extOrganizationService.getById(id));
    }


    /**
     * 数据订阅-查询机构信息
     */
    @RequiredPermission("601")
    @ApiOperation(value = "数据订阅-查询机构信息")
    @PostMapping("/qryExtOrganization")
    public ApiResult<ExtOrganization> qryExtOrganization() {
        return extOrganizationService.qryExtOrganization();
    }

    /**
     * 数据订阅-更新机构信息
     */
    @RequiredPermission("601")
    @ApiOperation(value = "数据订阅-更新机构信息")
    @PostMapping("/saveDatafeed")
    public ApiResult saveDatafeed(@ApiParam(name = "数据订阅-更新机构信息", value = "json", required = true) @RequestBody ExtOrganizationVO vo) {
        return extOrganizationService.update(vo);
    }

}
