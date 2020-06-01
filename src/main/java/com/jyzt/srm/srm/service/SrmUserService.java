package com.jyzt.srm.srm.service;

import com.jyzt.srm.srm.bean.entry.SrmUser;

public interface SrmUserService {

    SrmUser getUserByUserRefId(Long id);

    SrmUser getUserByUserName(String userName);

    void register(SrmUser srmUser);
}
