package com.jyzt.srm.common.jwt;

import com.jyzt.srm.common.entity.Payload;
import com.jyzt.srm.common.entity.UserInfo;
import com.jyzt.srm.common.utils.CookieUtils;
import com.jyzt.srm.srm.bean.entry.SrmUser;
import com.jyzt.srm.srm.service.SrmUserService;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.PublicKey;
import java.util.Calendar;
import java.util.Date;

@WebFilter(filterName = "CharsetFilter", urlPatterns = "/*", initParams = {
        @WebInitParam(name = "charset", value = "utf-8")/*这里可以放一些初始化的参数*/
})
public class CharsetFilter implements Filter {

    @Value("${srm.jwt.publicKeyPath}")
    String publicKeyPath;
    @Value("${srm.jwt.privateKeyPath}")
    String privateKeyPath;
    @Value("${srm.jwt.cookieName}")
    String cookieName;
    @Resource
    private SrmUserService srmUserService;


    //不需要登录就可以访问的路径(比如:注册登录等)
    String[] includeUrls = new String[]{
            "/rest/user/login",
            "/rest/user/register",
            "/doc.html",
            "/swagger-ui.html",
            "/swagger-resources",
            "/webjars/bycdao-ui/images/api.ico",
            "/webjars/bycdao-ui/layer3.0.3/skin/default/loading-2.gif",
            "/webjars/springfox-swagger-ui/springfox.css",
            "/webjars/springfox-swagger-ui/swagger-ui-standalone-preset.js",
            "/webjars/springfox-swagger-ui/springfox.js",
            "/rest/initKey/init",
            "/v2/api-docs"
    };


    @Override
    public void destroy() {
        /*销毁时调用*/
        System.out.println("销毁");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        if (!isNeedFilter(request.getRequestURI())) {
            chain.doFilter(req, resp);
        } else {
            /*过滤方法 主要是对request和response进行一些处理，然后交给下一个过滤器或Servlet处理*/
            String token = CookieUtils.getCookieValue(request, cookieName);
            PublicKey publicKey = null;
            try {
                publicKey = RsaUtils.getPublicKey(publicKeyPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Payload<UserInfo> payLoad = null;
            try {
                payLoad = JwtUtils.getInfoFromToken(token, publicKey, UserInfo.class);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("请重新登录");
            }

            UserInfo info = payLoad.getInfo();
            if (info == null) {
                throw new RuntimeException("未登录");
            }
            SrmUser srmUser = srmUserService.getUserByUserName(info.getUsername());
            if (srmUser == null) {
                throw new RuntimeException("未登录");
            }
            // TODO 检验过期时间
            Date expiration = payLoad.getExpiration();
            System.out.println("expiration = " + expiration);
            Date time = getWeekday(expiration);
            System.out.println("time = " + time);
            if (!time.after(new Date())) {
                // 增加token过期时间
                try {
                    String newToken = JwtUtils.generateTokenExpireInMinutes(info, RsaUtils.getPrivateKey(privateKeyPath), 30);
                    CookieUtils.newBuilder()
                            .response(response) // response,用于写cookie
                            .httpOnly(true) // 保证安全防止XSS攻击，不允许JS操作cookie
    //                    .domain("127.0.0.1") // 设置domain
                            .name(cookieName)
                            .value(newToken) // 设置cookie名称和值
                            .build();// 写cookie
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            chain.doFilter(req, resp);//交给下一个过滤器或servlet处理
        }
    }

    private Date getWeekday(Date expiration) {

        Calendar c = Calendar.getInstance();
        c.setTime(expiration);

        c.add(Calendar.MINUTE, -5);
        return c.getTime();
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

        /*初始化方法  接收一个FilterConfig类型的参数 该参数是对Filter的一些配置*/

    }

    public boolean isNeedFilter(String uri) {
        for (String includeUrl : includeUrls) {
            if (includeUrl.startsWith(uri)) {
                return false;
            }
        }
        return true;
    }

}

