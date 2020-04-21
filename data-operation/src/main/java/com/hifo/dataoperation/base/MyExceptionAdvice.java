package com.hifo.dataoperation.base;

import com.hifo.dataoperation.controller.ApiResult;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常统一处理
 * @author xmw
 */
@Log
//@ControllerAdvice
public class MyExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ApiResult defaultException(HttpServletRequest request, Exception e) {
        // 错误信息为中文字符
        if (e.getMessage().matches("^[\\u4e00-\\u9fa5]+$")) {
            return ApiResult.failed(e.getMessage());
        }
        log.warning(e.getMessage());
        return ApiResult.failed("操作失败。");
    }
}
