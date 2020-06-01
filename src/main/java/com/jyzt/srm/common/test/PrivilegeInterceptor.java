package com.jyzt.srm.common.test;

import com.jyzt.srm.common.entity.Payload;
import com.jyzt.srm.common.jwt.JwtUtils;
import com.jyzt.srm.srm.bean.entry.SrmUser;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
public class PrivilegeInterceptor implements HandlerInterceptor {

    private JwtProperties props;

    public PrivilegeInterceptor(JwtProperties jwtProp) {
        this.props = jwtProp;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
            String token = request.getHeader(props.getApp().getHeaderName());

            Payload<SrmUser> payload = JwtUtils.getInfoFromToken(token, props.getPublicKey(), SrmUser.class);
            SrmUser info = payload.getInfo();
            List<Long> targetList = Lists.newArrayList(Long.valueOf(info.getAuthorityRefId()));
            if (null==targetList||!targetList.contains(props.getApp().getId())){
                log.error("目标资源不允许此请求访问");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
