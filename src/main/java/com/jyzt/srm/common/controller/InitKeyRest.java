package com.jyzt.srm.common.controller;

import com.jyzt.srm.common.jwt.RsaUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/initKey")
public class InitKeyRest {
    @Value("${srm.jwt.privateKeyPath}")
    String privateKeyPath;
    @Value("${srm.jwt.publicKeyPath}")
    String publicKeyPath;

    @GetMapping("/init")
    public void initKey() {

        try {
            RsaUtils.generateKey(publicKeyPath, privateKeyPath, "user", 2048);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
