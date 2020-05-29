package com.jyzt.srm.srm.service;

import com.jyzt.srm.srm.bean.SrmUser;

public interface SrmUserService {

    SrmUser getUserByUserRefId(String userRefId);
}
