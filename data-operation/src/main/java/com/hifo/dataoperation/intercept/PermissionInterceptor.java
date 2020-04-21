package com.hifo.dataoperation.intercept;

import com.hifo.dataoperation.entity.ExtEmployee;
import com.hifo.dataoperation.util.RequestUtil;
import com.hifo.dataoperation.util.JSONUtil;
import com.hifo.dataoperation.util.StringUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截器
 *
 * @author whc
 * @date 2019/3/18 11:33
 */
public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 验证权限
        if (this.hasPermission(handler)) {
            return true;
        }
        response.sendError(HttpStatus.FORBIDDEN.value(), "无权限");
        return false;
    }

    /**
     * 是否有权限
     *
     * @param handler 处理器
     * @return boolean
     */
    private boolean hasPermission(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法上的注解
            RequiredPermission requiredPermission = handlerMethod.getMethod().getAnnotation(RequiredPermission.class);
            // 如果方法上的注解为空 则获取类的注解
            if (requiredPermission == null) {
                requiredPermission = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequiredPermission.class);
            }
            // 没有注解
            if (requiredPermission == null || StringUtil.isNull(requiredPermission.value())) {
                return true;
            }
            // 如果标记了注解，则判断权限
            try {
                // Session中获取权限（应该在Redis中获取，但是当前项目权限为临时）
                ExtEmployee extEmployee = (ExtEmployee) RequestUtil.session("loginEmployee");
                if (extEmployee == null) {
                    return false;
                }
                return JSONUtil.str2List(extEmployee.getModuleCodes()).contains(requiredPermission.value());
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
