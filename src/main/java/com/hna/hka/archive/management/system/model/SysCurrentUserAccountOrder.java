package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysCurrentUserAccountOrder {
    private Long accountOrderId;

    private Long userId;

    private String orderStart;

    @Excel(name = "充值金额",width = 20,orderNum = "3")
    private String rechargeAmount;

    @Excel(name = "折扣",width = 20,orderNum = "4")
    private String donationAmount;

    private Long scenicSpotId;

    private String paymentMethod;

    @Excel(name = "订单编号",width = 20,orderNum = "0")
    private String accountOrderNumber;

    @Excel(name = "用户手机号",width = 20,orderNum = "1")
    private String userPhone;

    @Excel(name = "创建时间",width = 20,orderNum = "5")
    private String createDate;

    private String updateDate;
    @Excel(name = "退款金额",width = 20,orderNum = "6")
    private String refundAmount;
    @Excel(name = "收入金额",width = 20,orderNum = "7")
    private String revenueAmount;
    /**
     * 景区名称
     */
    @Excel(name = "景区名称",width = 20,orderNum = "2")
    private String scenicSpotName;

}