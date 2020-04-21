package com.hifo.dataoperation.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 会话工具类（业务强耦合）
 *
 * @author whc
 * @date 2019/3/20 15:19
 */
public class RequestUtil {
    /**
     * 登录成功后，ExtEmployee对象放入session中的key
     * 因为是临时，所以不使用Redis以及security
     */
    public final static String LOGIN_EMPLOYEE = "loginEmployee";

    public final static String LOGIN_CUSTOMERUSER = "loginCustomerUser";

    public final static String LOGIN_ORGANIZATION = "loginOrganization";

    /**
     * 获取当前请求的HttpServletRequest对象
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes != null ? servletRequestAttributes.getRequest() : null;
    }

    /**
     * 从HttpServletRequest中获取参数值
     *
     * @param key 键
     * @return 值
     */
    public static String param(String key) {
        try {
            String value = Objects.requireNonNull(getRequest()).getParameter(key);
            return StringUtil.str(value);
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * 设置session
     *
     * @param key   键
     * @param value 值
     */
    public static void session(String key, Object value) {
        try {
            Objects.requireNonNull(getRequest()).getSession().setAttribute(key, value);
        } catch (NullPointerException ignored) {
        }
    }

    /**
     * 获取session的值
     *
     * @param key 键
     * @return Object
     */
    public static Object session(String key) {
        try {
            return Objects.requireNonNull(getRequest()).getSession().getAttribute(key);
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * 从session移除
     *
     * @param key 键
     */
    public static void removeSession(String key) {
        try {
            Objects.requireNonNull(getRequest()).getSession().removeAttribute(key);
        } catch (NullPointerException ignored) {
        }
    }

    /**
     * 判断用户是否登录
     *
     * @return boolean
     */
    public static boolean isLogin() {
        return session(LOGIN_EMPLOYEE) != null;
    }

}
