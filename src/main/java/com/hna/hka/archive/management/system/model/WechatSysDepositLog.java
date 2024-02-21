package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
public class WechatSysDepositLog implements Serializable {
    private Long depositLogId;

    private Long depositId;

    private String depositPhone;

    @Excel(name = "押金金额", width = 20)
    private String depositMoney;

    @Excel(name = "押金订单编号",width = 60)
    private String outTradeNo;

    @Excel(name = "手机号",width = 20)
    private String refundClient;

    @Excel(name = "退款原因",width = 20)
    private String reason;

    private String returnResultCode;

    @Excel(name = "创建时间",width = 20)
    private String createDate;

    private String updateDate;

}