package com.jyzt.srm.srm.service;

import com.jyzt.srm.srm.bean.entry.SrmUser;

public interface SrmUserService {

    SrmUser getUserByUserRefId(String userRefId);

    SrmUser getUserByUserName(String userName);
}
