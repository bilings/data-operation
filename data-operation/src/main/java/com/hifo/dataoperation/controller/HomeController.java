package com.hifo.dataoperation.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 主页
 *
 * @author whc
 * 2019/3/12
 */
@Controller
@RequestMapping
@Api(hidden = true)
public class HomeController {

    @Autowired
    private Environment env;

    /**
     * 主页跳转
     *
     * @return
     */
    @RequestMapping("/")
    @ApiOperation(value = "主页", hidden = true)
    public String home() {
        return "redirect:/views/home/login.html";
    }

    /**
     * 获取系统配置
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "系统配置", notes = "系统配置")
    @PostMapping("/getConfig")
    @ResponseBody
    public ApiResult<Map<String, String>> getConfig(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>(1);
        map.put("profile", env.getProperty("spring.profiles.active"));
        return ApiResult.success(map);
    }
}
