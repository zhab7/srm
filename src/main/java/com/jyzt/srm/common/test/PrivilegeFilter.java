//package com.jyzt.srm.common.test;
//
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
//import org.springframework.stereotype.Component;
//
//@Component
//public class PrivilegeFilter extends ZuulFilter {
//
//    @Autowired
//    private JwtProperties props;
//    @Autowired
//    private PrivilegeTokenHolder privilege;
//
//    @Override
//    public String filterType() {
//        return FilterConstants.PRE_TYPE;
//    }
//
//    @Override
//    public int filterOrder() {
//        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        return true;
//    }
//
//    @Override
//    public Object run() throws ZuulException {
//
//        RequestContext currentContext = RequestContext.getCurrentContext();
//        currentContext.addZuulRequestHeader(props.getApp().getHeaderName(),privilege.getToken());
//
//        return null;
//    }
//}
