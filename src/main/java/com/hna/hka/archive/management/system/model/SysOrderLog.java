package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysOrderLog {
    private Long orderLogId;

    @Excel(name = "操作人账号" , orderNum = "1",width = 20)
    private String orderLogLoginname;

    @Excel(name = "操作人名称" , orderNum = "2",width = 20)
    private String orderLogUsername;

    @Excel(name = "客户手机号" , orderNum = "4",width = 20)
    private String orderLogPhone;

    private String orderLogClient;

    @Excel(name = "订单编号" , orderNum = "0",width = 20)
    private String orderLogNumber;

    @Excel(name = "退款金额" , orderNum = "5",width = 20)
    private String depositMoney;

    @Excel(name = "景区名称" , orderNum = "6",width = 20)
    private String scenicSpotName;

    @Excel(name = "退款状态" , orderNum = "8",width = 20)
    private String orderLogReason;

    private String returnResultCode;

    @Excel(name = "退款时间" , orderNum = "9",width = 20)
    private String createDate;

    private String updateDate;

    /**
     * 机器人编号
     */
    @Excel(name = "机器人编号" , orderNum = "3",width = 20)
    private String orderRobotCode;

    /**
     * 退款原因
     */
    @Excel(name = "退款原因" , orderNum = "7",width = 20)
    private  String reasonsRefunds;

}