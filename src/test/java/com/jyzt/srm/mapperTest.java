package com.jyzt.srm;

import com.jyzt.srm.srm.bean.SrmUser;
import com.jyzt.srm.srm.service.SrmUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class mapperTest {
    @Resource
    private SrmUserService srmUserService;

    @Test
    public void test(){
        SrmUser srmUser = srmUserService.getUserByUserRefId("123");
        System.out.println("srmUser = " + srmUser);

    }
}
