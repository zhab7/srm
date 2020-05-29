package com.jyzt.srm;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class}, scanBasePackages = "com.jyzt.srm")
@MapperScan("com.jyzt.srm.srm.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SRMApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SRMApplication.class, args);
    }
}
