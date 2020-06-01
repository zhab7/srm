package com.jyzt.srm.srm.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jyzt.srm.srm.bean.entry.SrmUser;
import com.jyzt.srm.srm.mapper.SrmUserMapper;
import com.jyzt.srm.srm.service.SrmUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@DS("srm")
@Service
public class SrmUserServiceImpl implements SrmUserService {

    @Resource
    private SrmUserMapper srmUserMapper;


    @Override
    public SrmUser getUserByUserRefId(Long id) {
        return srmUserMapper.selectById(id);
    }

    @Override
    public SrmUser getUserByUserName(String userName) {
        return srmUserMapper.selectOne(Wrappers.<SrmUser>lambdaQuery().eq(SrmUser::getUserName, userName));
    }

    @Override
    public void register(SrmUser srmUser) {
        srmUserMapper.insert(srmUser);
    }
}
