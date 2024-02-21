package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysCurrentUserIntegralLog {

    private Long integralLogId;

    //用户ID
    private Long userId;

    //后台管理系统账号
    private String userAccount;

    //修改前积分数额
    private Long frontIntegral;

    //修改后积分数额
    private Long afterIntegral;

    private String createDate;

    private String updateDate;

    private String userPhone;
    private String userPhone1;
    private Integer pageNum;
    private Integer pageSize;
}
