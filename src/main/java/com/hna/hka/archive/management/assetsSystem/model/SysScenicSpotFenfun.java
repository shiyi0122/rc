package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/4/26 11:22
 */
@Data
public class SysScenicSpotFenfun {

    private Long fenrunScenicSpotId;

    private Long subjectId;

    private Long companyId;
    @Excel(name = "收款单位" , width = 15 , orderNum = "3")
    private String payee;

    private Long  scenicSpotId;

    @Excel(name = "月份" , width = 15 , orderNum = "5")
    private String profitSharingMonth;
    @Excel(name = "平台微信支付金额/元" , width = 15 , orderNum = "6")
    private String wechatPaymentFlow;
    @Excel(name = "平台储值支付金额/元" , width = 15 , orderNum = "7")
    private String storedValuePaymentFlow;
    @Excel(name = "平台储值抵扣+微信支付金额/元" , width = 15 , orderNum = "8")
    private String wechatStoredPaymentFlow;
    @Excel(name = "平台押金支付金额/元" , width = 15 , orderNum = "9")
    private String depositDeductionPaymentFlow;
    @Excel(name = "平台总订单金额/元" , width = 15 , orderNum = "10")
    private String totalAmount;
    @Excel(name = "平台统计分润基数/元" , width = 15 , orderNum = "11")
    private String platformFenrunBase;
    @Excel(name = "平台统计分润支出/元" , width = 15 , orderNum = "12")
    private String platformFenrunExpenditure;
    @Excel(name = "平台统计分润收入/元" , width = 15 , orderNum = "13")
    private String platformFenrunIncome;
    @Excel(name = "平台统计配件维修金额/元" , width = 15 , orderNum = "14")
    private String platformStatisticsPartsMaintenance;
    @Excel(name = "平台统计结算(支出)金额/元" , width = 15 , orderNum = "15")
    private String platformStatisticsSettlementExpenditure;
    @Excel(name = "平台统计结算(收入)金额/元" , width = 15 , orderNum = "16")
    private String platformStatisticsSettlementIncome;
    @Excel(name = "收支类型" , width = 15 , orderNum = "17")
    private Long type;
    @Excel(name = "手续费率" , width = 15 , orderNum = "18")
    private String charge;
    @Excel(name = "分润比例" , width = 15 , orderNum = "19")
    private String proportion;
    @Excel(name = "税点" , width = 15 , orderNum = "20")
    private String taxPoint;
    @Excel(name = "对账备注" , width = 15 , orderNum = "21")
    private String reconciliationRemarks;
    @Excel(name = "实际分润基数金额/元" , width = 15 , orderNum = "22")
    private String actualFenrunBase;
    @Excel(name = "实际扣税后分润(支出)金额/元" , width = 15 , orderNum = "23")
    private String actualTaxFenrunExpenditure;
    @Excel(name = "实际扣税后分润(收入)金额/元" , width = 15 , orderNum = "24")
    private String actualTaxFenrunIncome;
    @Excel(name = "分润金额结算状态" , width = 15 , orderNum = "25")
    private Long fenrunSettlemenType;
    @Excel(name = "景区承担配件维修金额/元" , width = 15 , orderNum = "26")
    private String spotPartsMaintenance;
    @Excel(name = "配件维修费结算状态" , width = 15 , orderNum = "27")
    private String spotSettlemenType;
    @Excel(name = "实际结算(支出)金额/元" , width = 15 , orderNum = "28")
    private String actualSettlemenExpenditure;
    @Excel(name = "实际结算(收入)金额/元" , width = 15 , orderNum = "29")
    private String actualSettlemenIncome;
    @Excel(name = "分润备注" , width = 15 , orderNum = "30")
    private String fenrunRemarks;

    private String createDate;

    private String updateDate;
    @Excel(name = "合作主体公司" , width = 15 , orderNum = "1")
    private String subjectName;
    @Excel(name = "合作公司" , width = 15 , orderNum = "2")
    private String companyName;
    @Excel(name = "景区名称" , width = 15 , orderNum = "4")
    private String  scenicSpotName;





}
