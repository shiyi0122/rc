package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysOrderDetail {


    public String scenicSpotId;

    @Excel(name = "景区名称", width = 20, orderNum = "1")
    public String scenicSpotName;

    @Excel(name = "总订单数", width = 20, orderNum = "2")
    public String allOrder;

    @Excel(name = "寻宝订单数", width = 20, orderNum = "3")
    public String huntOrder;

    @Excel(name = "抽奖订单数", width = 20, orderNum = "4")
    public String lotteryOrder;

    @Excel(name = "寻宝订单占比", width = 20, orderNum = "5")
    public String huntProportion;

    @Excel(name = "抽奖订单占比", width = 20, orderNum = "6")
    public String lotteryProportion;

    @Excel(name = "客单价", width = 20, orderNum = "7")
    public String perCustomerTransaction;

    @Excel(name = "寻宝客单价", width = 20, orderNum = "8")
    public String huntPerCustomerTransaction;

    @Excel(name = "抽奖客单价", width = 20, orderNum = "9")
    public String lotteryPerCustomerTransaction;


}
