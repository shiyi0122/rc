package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysInvoice {
    private Long invoiceId;

    private Long scenicSpotId;

    @Excel(name = "手机号" , orderNum = "1" , width = 20)
    private String phone;

    @Excel(name = "发票金额" , orderNum = "2" , width = 20)
    private String invoiceAmount;

    private String orderNumber;

    @Excel(name = "税号" , orderNum = "4" , width = 20)
    private String dutyParagraph;

    private String invoiceType;

    private String riseType;

    @Excel(name = "发票抬头" , orderNum = "6" , width = 20)
    private String invoiceRise;

    @Excel(name = "发票内容" , orderNum = "7" , width = 20)
    private String invoiceContent;

    private String processingProgress;

    @Excel(name = "备注" , orderNum = "10" , width = 20)
    private String remarks;

    @Excel(name = "申请时间" , orderNum = "9" , width = 20)
    private String createTime;

    private String updateTime;

    @Excel(name = "景区名称" , orderNum = "0" , width = 20)
    private String scenicSpotName;

    @Excel(name = "发票类型" , orderNum = "3" , width = 20)
    private String invoiceTypeName;

    @Excel(name = "抬头类型" , orderNum = "5" , width = 20)
    private String riseTypeName;

    @Excel(name = "处理进度" , orderNum = "8" , width = 20)
    private String processingProgressName;

    private String receivingInformation;

    private String emailAddress;
}