//package com.jyzt.srm.common.jwt;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@EnableConfigurationProperties(JwtProperties.class)
//public class MvcConfig implements WebMvcConfigurer {
//    @Autowired
//    private JwtProperties props;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new PrivilegeInterceptor(props)).excludePathPatterns("/swagger-ui.html");
//    }
//}
