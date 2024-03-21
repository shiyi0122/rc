package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysCurrentUserExchangeLog {

    private Long exchangeLogId;

    //用户ID
    private Long userId;

    //奖品ID
    private Long prizeId;

    //景区ID
    private Long scenicSpotId;

    //1管理者APP 2后台管理系统 3微信小程序
    private String exchangePort;

    //操作状态：0绑定地址 1已发货 2已收货 3现场兑换
    private String operationalState;

    //账户名：小程序用用户手机号，后台和管理者则用用户账户名
    private String accountName;

    //收件人地址
    private String mailingAddress;

    //电话
    private String userPhone;

    //用户名
    private String fullName;

    private String createDate;

    private String updateDate;

    private String userPhone1;

    private String scenicSpotName;

    private String treasureName;

    private Integer pageSize;
    private Integer pageNum;
    private String jackpotName;
}
