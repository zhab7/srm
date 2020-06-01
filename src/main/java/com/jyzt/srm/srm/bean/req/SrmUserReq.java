package com.jyzt.srm.srm.bean.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SrmUserReq {

    @NotEmpty
    String userName;

    @NotEmpty
    String password;

    Boolean rememberMe = false;
}
