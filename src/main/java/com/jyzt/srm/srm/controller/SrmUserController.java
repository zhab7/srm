package com.jyzt.srm.srm.controller;

import com.jyzt.srm.common.entity.UserInfo;
import com.jyzt.srm.common.jwt.JwtUtils;
import com.jyzt.srm.common.jwt.RsaUtils;
import com.jyzt.srm.common.utils.CookieUtils;
import com.jyzt.srm.srm.bean.entry.SrmUser;
import com.jyzt.srm.srm.bean.req.SrmUserReq;
import com.jyzt.srm.srm.service.SrmUserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.PrivateKey;

@RestController
@RequestMapping("/rest/user")
public class SrmUserController {

    @Resource
    private SrmUserService srmUserService;

    @Value("${srm.jwt.privateKeyPath}")
    String privateKeyPath;
    @Value("${srm.jwt.publicKeyPath}")
    String publicKeyPath;
    @Value("${srm.jwt.cookieName}")
    String cookieName;


    @GetMapping("/getUser")
    public SrmUser getByUserRefId(@RequestParam String userRefId) {

        SrmUser srmUser = srmUserService.getUserByUserRefId(userRefId);
        if (srmUser == null) {
            return null;
        }
        return srmUser;
    }

    @PostMapping("/login")
    @ApiOperation("登录接口")
    public void login(@RequestBody SrmUserReq srmUserReq, HttpServletResponse response) {
        if (srmUserReq == null || StringUtils.isBlank(srmUserReq.getUserName()) || StringUtils.isBlank(srmUserReq.getPassword())) {
            throw new RuntimeException("用户名密码为空");
        }
        SrmUser srmUser = srmUserService.getUserByUserName(srmUserReq.getUserName());
        if (srmUser == null) {
            throw new RuntimeException("不存在该用户");
        }
        // 检验密码是否正确
        if (!srmUserReq.getPassword().equals(srmUser.getPassword())) {
            throw new RuntimeException("密码不正确");
        }
        // 用私钥生成token
        try {
            PrivateKey privateKey = RsaUtils.getPrivateKey(privateKeyPath);

            UserInfo userInfo = new UserInfo(1L, srmUser.getUserName(), srmUser.getAuthorityRefId());
            String token = JwtUtils.generateTokenExpireInMinutes(userInfo, privateKey, 30);
            CookieUtils.newBuilder()
                    .response(response) // response,用于写cookie
                    .httpOnly(true) // 保证安全防止XSS攻击，不允许JS操作cookie
                    .domain("http://localhost:3000") // 设置domain
                    .name(cookieName)
                    .value(token) // 设置cookie名称和值
                    .build();// 写cookie
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
