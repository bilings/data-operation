package com.hifo.dataoperation.controller;

import com.hifo.dataoperation.base.BasicController;
import com.hifo.dataoperation.entity.ExtEmployee;
import com.hifo.dataoperation.service.base.BasicService;
import com.hifo.dataoperation.service.crm.EmployeeService;
import com.hifo.dataoperation.util.RequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户登录、登出
 *
 * @author whc
 * @date 2019/3/14 9:10
 */
@Api(tags = "登录退出")
@RestController
@RequestMapping
public class LoginController extends BasicController {

    @Autowired
    private BasicService basicService;
    @Autowired
    private EmployeeService employeeService;

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return code=0：成功
     */
    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public ApiResult login(String username, String password) {
        ExtEmployee employee = employeeService.getEmployee(username, password);
        if (employee != null) {
            RequestUtil.session(RequestUtil.LOGIN_EMPLOYEE, employee);
            return ApiResult.success(employee.getId());
        } else {
            return ApiResult.failed();
        }
    }

    /**
     * 登出
     *
     * @return 成功
     */
    @ApiOperation(value = "登出")
    @PostMapping("/logout")
    public ApiResult logout() {
        System.out.println("用户登出：" + basicService.getLoginEmployee().getName());
        RequestUtil.removeSession(RequestUtil.LOGIN_EMPLOYEE);
        return ApiResult.success();
    }

}
