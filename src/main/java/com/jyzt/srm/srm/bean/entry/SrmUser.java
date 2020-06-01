package com.jyzt.srm.srm.bean.entry;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


@Data
public class SrmUser {
    @TableId
    String userRefId;

    String userName;

    String password;

    String authorityRefId;

    String salt;


    public boolean checkPass(String loginPass) {
        return true;
    }
}
