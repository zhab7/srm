package com.jyzt.srm.srm.controller;

import com.jyzt.srm.srm.bean.SrmUser;
import com.jyzt.srm.srm.service.SrmUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/rest/user")
public class SrmUserController {

    @Resource
    private SrmUserService srmUserService;

    @GetMapping("/getUser")
    public SrmUser getByUserRefId(@RequestParam String userRefId) {
        SrmUser srmUser = srmUserService.getUserByUserRefId(userRefId);
        if (srmUser == null) {
            return null;
        }
        return srmUser;
    }
}
