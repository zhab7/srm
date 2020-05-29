package com.jyzt.srm;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;
import java.nio.charset.StandardCharsets;

@Slf4j
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class}, scanBasePackages = "com.jyzt.srm")
@MapperScan({"com.jyzt.srm.srm.mapper", "com.jyzt.srm.gs.mapper"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SRMApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SRMApplication.class, args);
    }

    @Bean
    public ResourceBundleMessageSource i18nMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename("lang/message");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.toString());

        log.info("---> i18n message source loaded ");
        return messageSource;
    }

    @Bean
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(i18nMessageSource());
        return validator;
    }
}
