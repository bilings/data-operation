package com.hifo.dataoperation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 * 启动类
 *
 * @author whc
 * @date 2020/03/19
 */
@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
@MapperScan({"com.hifo.*.mapper"})
public class DataOperationApplication  {
    public static void main(String[] args) {
        SpringApplication.run(DataOperationApplication.class, args);
    }
}