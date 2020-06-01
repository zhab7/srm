//package com.jyzt.srm.common.jwt;
//
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//
//import java.security.PublicKey;
//
//@Data
//@Slf4j
//@ConfigurationProperties(prefix = "jwt")
//public class JwtProperties implements InitializingBean {
//
//    private String pubKeyPath;
//    private PublicKey publicKey;
//    private AppInterceptor app = new AppInterceptor();
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        try {
//            publicKey = RsaUtils.getPublicKey(pubKeyPath);
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("获取公钥失败");
//        }
//    }
//
//    @Data
//    public class AppInterceptor{
//        private String secret;
//        private String headerName;
//        private Long id;
//    }
//
//
//}
