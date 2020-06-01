package com.jyzt.srm;


import com.jyzt.srm.common.entity.Payload;
import com.jyzt.srm.common.entity.UserInfo;
import com.jyzt.srm.common.jwt.JwtUtils;
import com.jyzt.srm.common.jwt.RsaUtils;
import com.jyzt.srm.common.utils.CookieUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.nio.file.Files;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest
@RunWith(SpringRunner.class)
public class JwtTest {

//    @Value("${srm.jwt.privateKeyPath}")
//    String privateKeyPath;
    @Value("${srm.jwt.publicKeyPath}")
    String publicKeyPath;


    @Test
    public void test() throws Exception {
        String privateKeyPath  = "D:\\key\\id_rsa";

        byte[] bytes = Files.readAllBytes(new File(privateKeyPath).toPath());
        bytes = Base64.getDecoder().decode(bytes);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = factory.generatePrivate(spec);
//        Map<String, Object> stringObjectMap = initKey();
//        PrivateKey privateKey = (PrivateKey) stringObjectMap.get("private_key");

        UserInfo userInfo = new UserInfo(1L, "zhangsan", "role");
        String token = JwtUtils.generateTokenExpireInMinutes(userInfo, privateKey, 30);
        System.out.println("token = " + token);

    }

    @Test
    public void publicKeyTest() throws Exception {
//        Map<String, Object> stringObjectMap = initKey();
//        PublicKey publicKey = (PublicKey) stringObjectMap.get("public_key");
//        String publicPath  = "D:\\key\\id_rsa.pub";
        PublicKey publicKey = RsaUtils.getPublicKey(publicKeyPath);

        String token = "eyJhbGciOiJSUzI1NiJ9.eyJ1c2VyIjoie1wiaWRcIjoxLFwidXNlcm5hbWVcIjpcInpoYW5nc2FuXCIsXCJyb2xlXCI6XCJyb2xlXCJ9IiwianRpIjoiT0dSa01qbGxNVEV0TlRaaU5TMDBNVEEwTFRsbFpqa3RZV0ZqTVRBNFlUazVZamxqIiwiZXhwIjoxNTkwOTgzMjA2fQ.hgNPV04VUDr-swWgy1qHbR6j1mPC3k2-gGrxH4Bl3wXWGABLjiz_hBVpdHtGCZ9IkjjTv6LpIQVCYX54bPMrA-kExsuwLGjYO2MkWs4i7GFZ6RLP2Eo3DPLxMdVoXfAXf41SxbaYAJezcKAKbHkYN6XDii8DMSLYmpMfl52g-jOX5REHWU-80GXBfgF5tIB8l_JE_MUFH1CHMu3LT2MtC5-YARatHzu56NfrzyYY42Mej0Lh1tUKf-E7CrDxwRUgIVNxXRk248KjE0pHvI_JDr5mwYVbpFvuh-5wj7bvhkHWXBRYw130n-HPmwMz6WaYOA13nNGz6J2CR7MAgs4Ojw";

        // 获取token信息
        Payload<UserInfo> payLoad = JwtUtils.getInfoFromToken(token, publicKey, UserInfo.class);
        System.out.println("payLoad = " + payLoad);
    }

    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance("RSA");
        keyPairGen.initialize(2048);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put("public_key", publicKey);
        keyMap.put("private_key", privateKey);
        return keyMap;
    }

    @Test
    public void testPublic() throws Exception {
        String publicPath  = "D:\\key\\id_rsa.pub";
        String privateKeyPath  = "D:\\key\\id_rsa";
        RsaUtils.generateKey(publicPath, privateKeyPath, "user", 2048);
    }
}
