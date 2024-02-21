package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/2/1 15:38
 */
@Data
public class OrderMoney extends SubscriptionInformation {

    //微信金额
    @Excel(name = "微信金额" , width = 50 , orderNum = "6")
    private Double wechatMoney;
    //储值金额
    @Excel(name = "储值金额" , width = 50 , orderNum = "7")
    private Double savingMoney;
    //微信储值金额
    @Excel(name = "储值抵扣金额" , width = 50 , orderNum = "8")
    private Double savingWechatMoney;
    //押金
    @Excel(name = "押金金额" , width = 50 , orderNum = "9")
    private Double depositMoney;
    // 合计
    @Excel(name = "收入合计" , width = 50 , orderNum = "10")
    private Double totalMoney;
    // 手续费金额
    @Excel(name = "扣手续费" , width = 50 , orderNum = "11")
    private Double serviceCharge;
    // 税金额
    @Excel(name = "扣增值税差额" , width = 50 , orderNum = "12")
    private Double taxMoney;
    // 分成金额
    @Excel(name = "分成金额" , width = 50 , orderNum = "13")
    private Double shareAmount;
//    //可分成金额
//    @Excel(name = "可分成金额" , width = 50 , orderNum = "13")
//    private Double shareableMoney;

    @Excel(name = "期间" , width = 50 , orderNum = "5")
    private String date;





}
