package com.jyzt.srm.common.shiro.config;


import com.jyzt.srm.common.shiro.filter.ShiroFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.assertj.core.util.Lists;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Configuration
@Slf4j

public class ShiroConfig {

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        return new DefaultShiroFilterChainDefinition();
    }


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
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // rememberMe cookie加密的密钥
        cookieRememberMeManager.setCipherKey(Base64.decode("sBv2t3okbdm3U0r2EVcSzB=="));
        return cookieRememberMeManager;
    }

    @Bean("rememberMeCookieTemplate")
    public SimpleCookie rememberMeCookie() {
        // 设置cookie名称
        SimpleCookie cookie = new SimpleCookie("gs_remember_me");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        // 设置cookie的过期时间，单位为秒
        cookie.setMaxAge(259200);
        return cookie;
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

    private ShiroSessionDAO shiroSessionDAO() {
        return new ShiroSessionDAO();
    }

    private Cookie sessionCookie() {
        SimpleCookie cookie = new SimpleCookie("GSJSESSIONID");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        // 设置cookie的过期时间，单位为秒
        cookie.setMaxAge(259200);
        return cookie;
    }

//    @Bean
//    public Authorizer authorizer(){
//        return new ModularRealmAuthorizer();
//    }

    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(Environment env, MessageSource i18nMessageSource) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        // 拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置不会被拦截的链接 顺序判断

//        String profiles = env.getProperty("spring.profiles.active");
//        log.info("spring.profiles.active = {}", profiles);
//        if (profiles.startsWith("local")) {
//            filterChainDefinitionMap.put("/nc/**", "anon"); //排除anon打头的接口
//        }

        filterChainDefinitionMap.put("/rest/bill/shop/push", "anon"); //提供给商城的供应商接口，无需验证
        filterChainDefinitionMap.put("/rest/user/getUser", "anon"); //提供给商城的供应商接口，无需验证
        filterChainDefinitionMap.put("/rest/dept/sc/search", "anon"); //提供给商城的部门接口，无需验证
        filterChainDefinitionMap.put("/rest/sys/login", "anon"); //登录接口排除
        filterChainDefinitionMap.put("/rest/sys/register", "anon"); // 用戶注册接口
        filterChainDefinitionMap.put("/rest/sys/checkCode", "anon"); // 注册用户激活链接接口
        filterChainDefinitionMap.put("/rest/sys/sendResetPasswordLink/*", "anon"); // 发送重置密码链接
        filterChainDefinitionMap.put("/rest/sys/resetPassword", "anon"); // 重置密码
        filterChainDefinitionMap.put("/auth/2step-code", "anon");//登录验证码
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/doc.html", "anon");
        filterChainDefinitionMap.put("/**/*.js", "anon");
        filterChainDefinitionMap.put("/**/*.css", "anon");
        filterChainDefinitionMap.put("/**/*.html", "anon");
        filterChainDefinitionMap.put("/**/*.svg", "anon");
        filterChainDefinitionMap.put("/**/*.jpg", "anon");
        filterChainDefinitionMap.put("/**/*.png", "anon");
        filterChainDefinitionMap.put("/**/*.ico", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger**/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");

        //性能监控
        filterChainDefinitionMap.put("/actuator/metrics/**", "anon");
        filterChainDefinitionMap.put("/actuator/httptrace/**", "anon");
        filterChainDefinitionMap.put("/actuator/redis/**", "anon");

        Map<String, Filter> filterMap = new HashMap<>(1);
        filterMap.put("gsFilter", new ShiroFilter(shiroSessionDAO(), i18nMessageSource));
        shiroFilterFactoryBean.setFilters(filterMap);

        // <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
        filterChainDefinitionMap.put("/**", "gsFilter");
//        filterChainDefinitionMap.put("/**", "user");

        // 未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/rest/sys/common/403");
        shiroFilterFactoryBean.setLoginUrl("/rest/sys/common/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 下面的代码是添加注解支持
     *
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager());
        return advisor;
    }
}
