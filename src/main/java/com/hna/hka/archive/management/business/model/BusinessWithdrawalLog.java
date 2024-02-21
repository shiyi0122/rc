package com.hna.hka.archive.management.business.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BusinessWithdrawalLog {
    private Long id;

    private Long userId;

    private Long operatorId;

    @Excel(name = "银行卡号" , orderNum = "1" ,width = 20)
    private String bankAccount;

    @Excel(name = "开户行信息" , orderNum = "2" ,width = 20)
    private String bankInformation;

    @Excel(name = "身份证号码" , orderNum = "3" ,width = 20)
    private String identityCard;

    @Excel(name = "提现金额" , orderNum = "4" , type=10)
    private BigDecimal money;

    private String reason;

    private String state;

    @Excel(name = "创建时间" , orderNum = "6")
    private String createTime;

    private String updateTime;

    /**
     * 用户名称
     */
    @Excel(name = "用户名称" , orderNum = "0")
    private String userName;

    /**
     * 状态名称
     */
    @Excel(name = "提现状态" , orderNum = "5")
    private String stateName;

}