package com.hifo.dataoperation.controller;

import com.hifo.dataoperation.base.BasicController;
import com.hifo.dataoperation.entity.CustomerUser;
import com.hifo.dataoperation.intercept.RequiredPermission;
import com.hifo.dataoperation.pagination.Pagination;
import com.hifo.dataoperation.service.setting.CustomerUserService;
import io.swagger.annotations.*;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 用户信息控制类
 *
 * @Author: xmw
 * @Date: 2019/5/5 16:49
 */
@Api(tags = "用户信息管理")
@RestController
@RequestMapping("/customerUser")
@RequiredPermission("501")
public class CustomerUserController extends BasicController {

    @Autowired
    private CustomerUserService customerUserService;

    /**
     * 新增或更新用户信息
     */
    @ApiOperation(value = "新增更新用户信息")
    @PutMapping("/update")
    public ApiResult update(@ApiParam(name = "用户信息", value = "json", required = true) @RequestBody @Validated CustomerUser user) {
        return customerUserService.update(user);
    }

    /**
     * 批量删除用户信息
     *
     * @param ids id集合，以英文逗号,分隔
     * @return ApiResult
     */
    @ApiOperation(value = "批量删除用户信息")
    @ApiImplicitParam(name = "ids", value = "用户信息id集合，以英文逗号,分隔", paramType = "query", dataType = "String", example = "1,2", required = true)
    @PutMapping("/delete")
    public ApiResult delete(String[] ids) {
        return customerUserService.delete(ids);
    }

    /**
     * 批量重置用户密码
     *
     * @param ids id集合，以英文逗号,分隔
     * @return ApiResult
     */
    @ApiOperation(value = "批量重置用户密码")
    @ApiImplicitParam(name = "ids", value = "用户信息id集合，以英文逗号,分隔", paramType = "query", dataType = "String", example = "1,2", required = true)
    @PutMapping("/resetPassword")
    public ApiResult resetPassword(String[] ids) {
        return customerUserService.resetPassword(ids);
    }

    /**
     * 修改用户密码
     *
     * @param id
     * @param password
     * @return
     */
    @ApiOperation(value = "修改用户密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", paramType = "query", dataType = "String", example = "1,2", required = true),
            @ApiImplicitParam(name = "pwd", value = "用户原密码", paramType = "query", dataType = "String", example = "1,2", required = true),
            @ApiImplicitParam(name = "password", value = "用户密码", paramType = "query", dataType = "String", example = "1,2", required = true)
    })
    @PutMapping("/resetPwd")
    public ApiResult resetPassword(String id, String password, String pwd) {
        return customerUserService.resetPassword(id, password, pwd);
    }

    /**
     * 用户信息详情
     *
     * @param id 用户id
     * @return ApiResult
     */
    @ApiOperation(value = "根据ID获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户id", paramType = "path", dataType = "String", example = "1", required = true)
    @GetMapping("detail/{id}")
    public ApiResult detail(@PathVariable("id") Long id) {
        return ApiResult.success(customerUserService.getDetailById(id));
    }

    /**
     * 查询用户信息列表
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "查询用户信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户信息条件查询", paramType = "query", dataType = "String", example = "重庆"),
            @ApiImplicitParam(name = "name", value = "机构名称", paramType = "query", dataType = "String", example = "重庆"),
            @ApiImplicitParam(name = "type", value = "机构类型", paramType = "query", dataType = "String", example = "103"),
            @ApiImplicitParam(name = "rank", value = "机构层级(客户机构id)  查询所属全部机构用户", paramType = "query", dataType = "String", example = "1"),
            @ApiImplicitParam(name = "orgId", value = "用户机构id 查询直属机构用户", paramType = "query", dataType = "String", example = "银行")
    })
    @GetMapping("/list")
    public ApiResult list(@ApiIgnore CustomerUser user) {
        return ApiResult.success(customerUserService.list(user));
    }

    /**
     * 分页查询用户信息列表
     *
     * @param pages
     * @param user
     * @return
     */
    @ApiOperation(value = "分页查询用户信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "客户信息条件查询", paramType = "query", dataType = "String", example = "重庆"),
            @ApiImplicitParam(name = "name", value = "机构名称", paramType = "query", dataType = "String", example = "重庆"),
            @ApiImplicitParam(name = "type", value = "机构类型", paramType = "query", dataType = "String", example = "103"),
            @ApiImplicitParam(name = "rank", value = "机构层级(客户机构id)  查询所属全部机构客户", paramType = "query", dataType = "String", example = "1"),
            @ApiImplicitParam(name = "orgId", value = "客户机构id 查询直属机构客户", paramType = "query", dataType = "String", example = "银行"),
            @ApiImplicitParam(name = "page", value = "当前页数", paramType = "query", dataType = "int", example = "1", required = true),
            @ApiImplicitParam(name = "limit", value = "每页条数", paramType = "query", dataType = "int", example = "5", required = true)
    })
    @GetMapping("/findByPage")
    public String findByPage(Pagination pages, @ApiIgnore CustomerUser user) {
        // 分页数据
        var page = customerUserService.findByPage(pages, user);
        return pageData(page.getTotal(), page.getRecords());
    }

    /**
     * 用户使用后减少相应可查询次数
     *
     * @param id 用户id
     * @return ApiResult
     */
    @ApiOperation(value = "用户使用后减少相应可查询次数（有所属机构减所属机构剩余查询次数，无所属机构减用户剩余查询次数）")
    @ApiImplicitParam(name = "id", value = "用户id", paramType = "query", dataType = "String", example = "1", required = true)
    @PutMapping("useSelectUpdateTimes")
    public ApiResult useSelectUpdateTimes(Long id) {
        return customerUserService.useSelectUpdateTimes(id);
    }

}
