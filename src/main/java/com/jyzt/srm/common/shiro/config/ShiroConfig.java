package com.jyzt.srm.common.shiro.config;


import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.assertj.core.util.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class ShiroConfig {

    @Bean
    public AuthorizingRealm shiroRealm() {
        return new ShiroRealmConfig();
    }

    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealms(Lists.newArrayList(shiroRealm()));
        securityManager.setSessionManager(sessionManager());
        securityManager.setRememberMeManager(rememberMeManager());

        return securityManager;
    }

    private RememberMeManager rememberMeManager() {
        return null;
    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager webSessionManager = new DefaultWebSessionManager();
        webSessionManager.setGlobalSessionTimeout(18000000);
        webSessionManager.setSessionIdCookie(sessionCookie());
        webSessionManager.setSessionDAO(shiroSessionDAO());
        //是否开启删除无效的session对象  默认为true
        webSessionManager.setDeleteInvalidSessions(false);
        //是否开启定时调度器进行检测过期session 默认为true
        webSessionManager.setSessionValidationSchedulerEnabled(false);
        //取消url 后面的 GSSESSIONID
        webSessionManager.setSessionIdUrlRewritingEnabled(false);
        return webSessionManager;
    }

    private SessionDAO shiroSessionDAO() {
        return null;
    }

    private Cookie sessionCookie() {
        return null;
    }

}
