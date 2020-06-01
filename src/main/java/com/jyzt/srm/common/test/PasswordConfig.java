//package com.jyzt.srm.common.test;
//
//
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.security.SecureRandom;
//
//@Data
//@Slf4j
//@ConfigurationProperties(prefix = "ly.encoder.crypt")
//public class PasswordConfig {
//    private String secret;
//    private Integer strength;
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder(){
//        // 利用密钥生成随机安全码
//        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
//        // 初始化BCryptPasswordEncoder
//        return new BCryptPasswordEncoder(strength, secureRandom);
//    }
//
//}
