package com.jyzt.srm.common.shiro.filter;

import com.jyzt.srm.common.shiro.config.ShiroSessionDAO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class ShiroFilter extends AccessControlFilter {

    private ShiroSessionDAO shiroSessionDAO;
    private MessageSource i18nMessageSource;

    public ShiroFilter(ShiroSessionDAO shiroSessionDAO, MessageSource i18nMessageSource) {
        this.shiroSessionDAO = shiroSessionDAO;
        this.i18nMessageSource = i18nMessageSource;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        if (isLoginRequest(servletRequest, servletResponse)) {
            return true;
        }
        Subject subject = getSubject(servletRequest, servletResponse);
        // If principal is not null, then the user is known and should be allowed access.
        if (subject.getPrincipal() != null) {
            return true;
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String token = httpServletRequest.getHeader("X-Access-Token");
        if (StringUtils.isEmpty(token)) {
            return false;
        }
//        GsSimpleToken gsSimpleToken = new GsSimpleToken(token);
//        try {
//            subject.login(gsSimpleToken);
//        } catch (Exception e) {
//            log.error("", e);
//            return false;
//        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        log.debug("onAccessDenied called");
        HttpServletResponse httpResponse = WebUtils.toHttp(servletResponse);
        httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }

    @Override
    public void afterCompletion(ServletRequest request, ServletResponse response, Exception exception) throws Exception {
        super.afterCompletion(request, response, exception);
        shiroSessionDAO.clearSessionInMem();
    }
}
