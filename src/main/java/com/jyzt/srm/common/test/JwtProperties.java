package com.jyzt.srm.common.test;

import com.jyzt.srm.common.jwt.RsaUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.PrivateKey;
import java.security.PublicKey;

@Data
@Slf4j
@ConfigurationProperties(prefix = "srm.jwt")
public class JwtProperties implements InitializingBean {
    private String pubKeyPath;
    private String priKeyPath;

    private PublicKey publicKey;
    private PrivateKey privateKey;
    private UserTokenProperties user = new UserTokenProperties();
    private AppCheckExpire app = new AppCheckExpire();
    @Data
    public class AppCheckExpire{
        private Integer expire;
        private Long id;
        private String secret;
        private String headerName;
    }

    @Data
    public class UserTokenProperties{
        private String cookieName;
        private int expire;
        private String cookieDomain;
        private int minRefreshInterval;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            privateKey = RsaUtils.getPrivateKey(priKeyPath);
            publicKey = RsaUtils.getPublicKey(pubKeyPath);
        } catch (Exception e) {
            log.error("初始化公钥和私钥失败！", e);
            throw new RuntimeException(e);
        }
    }
}
