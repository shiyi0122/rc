package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysCurrentUserIntegral {

    //主键
    private Long integralId;

    //关联用户ID
    private Long userId;

    private String userPhone;

    //金币积分额度
    private Long integral;

    private String createDate;

    private String updateDate;

    //后台管理系统账号
    private String userAccount;
    private String userPhone1;

    private Integer pageSize;
    private Integer pageNum;
}
