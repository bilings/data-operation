package com.hifo.dataoperation.config;

import com.hifo.dataoperation.intercept.PermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截器
 *
 * @author whc
 * @date 2019/3/18 13:31
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 自己定义的拦截器类
     *
     * @return PermissionInterceptor
     */
    @Bean
    PermissionInterceptor permissionInterceptor() {
        return new PermissionInterceptor();
    }

    /**
     * 添加拦截器
     *
     * @param registry registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(permissionInterceptor());
    }
}
