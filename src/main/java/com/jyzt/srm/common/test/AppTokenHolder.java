//package com.jyzt.srm.common.test;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Slf4j
//@Component
//public class AppTokenHolder {
//    /**
//     * token刷新间隔
//     */
//    private static final long TOKEN_REFRESH_INTERVAL = 86400000L;
//    /**
//     * token获取失败后重试的间隔
//     */
//    private static final long TOKEN_RETRY_INTERVAL = 10000L;
//
//    private String token;
//
//    @Autowired
//    private JwtProperties props;
//    @Autowired
//    private AppMapper appMapper;
//
//    @Scheduled(fixedDelay = TOKEN_REFRESH_INTERVAL)
//    public void loadTokenTask() throws InterruptedException {
//        while (true) {
//            try {
//                AppInfo appInfo = new AppInfo();
//                List<Long> list = appMapper.selectAllowedListById(props.getApp().getId());
//                appInfo.setId(props.getApp().getId());
//                appInfo.setServiceName(props.getApp().getSecret());
//                appInfo.setTargetList(list);
//
//                token = JwtUtils.generateTokenExpireInMinutes(appInfo, props.getPrivateKey(), props.getApp().getExpire());
//                log.info("【auth服务】申请token成功！");
//                break;
//
//            } catch (Exception e) {
//                log.info("【auth服务】申请token失败！", e);
//            }
//            Thread.sleep(TOKEN_RETRY_INTERVAL);
//        }
//    }
//    public String getToken(){
//        return token;
//    }
//}
