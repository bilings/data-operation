package com.hifo.dataoperation.intercept;

import java.lang.annotation.*;

/**
 * 与拦截器结合使用 验证权限
 *
 * @author whc
 * @date 2019/3/18 11:32
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RequiredPermission {
    String value();
}
