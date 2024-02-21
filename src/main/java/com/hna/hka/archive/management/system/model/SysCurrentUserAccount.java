package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysCurrentUserAccount {
    private Long accountId;

    private Long accountUserId;

    @Excel(name = "储值余额",width = 20,orderNum = "3")
    private String accountAmount;

    private String startTime;

    private String endTime;

    private String userType;

    private String userName;

    private String password;

    private String accountType;

    private String loginStatus;

    @Excel(name = "折扣",width = 20,orderNum = "4")
    private String discount;

    @Excel(name = "创建时间",width = 20,orderNum = "5")
    private String createDate;

    private String updateDate;

    /**
     * 客户手机号
     */
    @Excel(name = "客户手机号",width = 20,orderNum = "1")
    private String currentUserPhone;

    /**
     * 储值总额
     */
    @Excel(name = "储值总额",width = 20,orderNum = "2")
    private String accumulatedAmount;

}