package com.jyzt.srm.common.jwt;

import com.jyzt.srm.common.entity.Payload;
import com.jyzt.srm.common.entity.UserInfo;
import com.jyzt.srm.common.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.PublicKey;

@WebFilter(filterName = "CharsetFilter", urlPatterns = "/*", initParams = {
        @WebInitParam(name = "charset", value = "utf-8")/*这里可以放一些初始化的参数*/
})
public class CharsetFilter implements Filter {

    @Value("${srm.jwt.publicKeyPath}")
    String publicKeyPath;
    @Value("${srm.jwt.cookieName}")
    String cookieName;

    @Override
    public void destroy() {
        /*销毁时调用*/
        System.out.println("销毁");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        /*过滤方法 主要是对request和response进行一些处理，然后交给下一个过滤器或Servlet处理*/
        String token = CookieUtils.getCookieValue((HttpServletRequest) req, cookieName);
        PublicKey publicKey = null;
        try {
            publicKey = RsaUtils.getPublicKey(publicKeyPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Payload<UserInfo> payLoad = JwtUtils.getInfoFromToken(token, publicKey, UserInfo.class);

        chain.doFilter(req, resp);//交给下一个过滤器或servlet处理
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

        /*初始化方法  接收一个FilterConfig类型的参数 该参数是对Filter的一些配置*/

    }

}

