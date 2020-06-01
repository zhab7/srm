//package com.jyzt.srm.common.shiro.config;
//
//import com.alibaba.fastjson.JSON;
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import com.jyzt.srm.srm.bean.entry.SrmUser;
//import com.jyzt.srm.srm.service.SrmUserService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.apache.shiro.util.ByteSource;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.Objects;
//
//@Slf4j
//@Component
//public class ShiroRealmConfig extends AuthorizingRealm {
//
//    @Resource
//    private SrmUserService srmUserService;
//
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        log.debug(" ———— 登录用户权限认证 ———— ");
//
//        return null;
//    }
//
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        log.debug(" ———— 登录用户身份认证 ———— ");
//        String userRefId = (String) token.getPrincipal();
//        SrmUser srmUser = srmUserService.getUserByUserRefId(userRefId);
//        log.info(JSON.toJSONString(srmUser));
//        if (srmUser == null) {
//            log.error("该用户不存在");
//            return null;
//        }
//        String password = Objects.toString(token.getCredentials());
//        if (StringUtils.isEmpty(password) || !srmUser.checkPass(password)) {
//            log.error("密码不正确");
//            return null;
//        }
//
//        return new SimpleAuthenticationInfo(srmUser, srmUser.getPassword(), ByteSource.Util.bytes(srmUser.getSalt()), this.getClass().getName());
//    }
//}
