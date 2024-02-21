package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 签约信息
 */
@Data
@ApiModel(value = "签约信息" , description = "签约信息")
public class SubscriptionInformation {

  @ApiModelProperty(value = "主键" , name = "id")
  private long id;

  @ApiModelProperty(value = "合作公司ID" , name = "companyId")
  private Long companyId;

  @ApiModelProperty(value = "合作公司名称" , name = "companyName")
  @Excel(name = "合作单位" , width = 50 , orderNum = "1")
  private String companyName;

  @ApiModelProperty(value = "景区ID" , name = "spotId")
  private long spotId;

  @ApiModelProperty(value = "景区名称" , name = "spotName")
  @Excel(name = "景区名称" , width = 50 , orderNum = "3")
  private String spotName;

  @ApiModelProperty(value = "手续费" , name = "charge")
  private double charge;

  @ApiModelProperty(value = "税点" , name = "tax")
  private double tax;

  @ApiModelProperty(value = "分润比例" , name = "proportion")
  @Excel(name = "分成比例" , width = 50 , orderNum = "4")
  private double proportion;

  @ApiModelProperty(value = "收支类型：1、收入；2、支出" , name = "revenueExpenditure")
  private long revenueExpenditure;

  @ApiModelProperty(value = "微信支付：1、是；2、否" , name = "wechat")
  private long wechat;

  @ApiModelProperty(value = "储值支付：1、是；2、否" , name = "saving")
  private long saving;

  @ApiModelProperty(value = "储值+微信支付：1、是；2、否" , name = "savingWechat")
  private long savingWechat;

  @ApiModelProperty(value = "押金支付：1、是；2、否" , name = "deposit")
  private long deposit;

  @ApiModelProperty(value = "合同编号" , name = "contractNumber")
  private String contractNumber;

  @ApiModelProperty(value = "合同有效期" , name = "contractValidity")
  private String contractValidity;

  @ApiModelProperty(value = "签约日期" , name = "contractSignDate")
  private String contractSignDate;

  @ApiModelProperty(value = "对账日" , name = "reconciliationDate")
  private String reconciliationDate;

  @ApiModelProperty(value = "结算日" , name = "settlement")
  private String settlement;

  @ApiModelProperty(value = "主体公司ID" , name = "subjectId")
  private long subjectId;

  @ApiModelProperty(value = "主体公司名称" , name = "subjectName")
  @Excel(name = "我方公司" , width = 50 , orderNum = "2")
  private String subjectName;

  @ApiModelProperty(value = "备注" , name = "notes")
  @Excel(name = "特殊事项说明" , width = 50 , orderNum = "14")
  private String notes;

  @ApiModelProperty(value = "创建日期" , name = "createDate")
  private String createDate;

  @ApiModelProperty(value = "发票类型：1、无发票；2、增值税普通发票；3、增值税专用发票" , name = "invoiceType")
  private long invoiceType;

  @ApiModelProperty(value = "景区配件费用比例" , name = "stopManagementScale")
  private double spotAccessoryRatio ;

  @ApiModelProperty(value = "景区维修费用比例" , name = "stopServiceScale")
  private double  spotRepairRatio;

  @ApiModelProperty(value = "税率公式id" , name = "taxRateMethod")
  private Long  taxRateMethod;


  @ApiModelProperty(value = "收款账号" , name = "collectionAccountNumber")
  @Excel(name = "开户行" , width = 50 , orderNum = "15")
  private String collectionAccountNumber;

  @ApiModelProperty(value = "开户行",name = "bank")
  @Excel(name = "收款账号" , width = 50 , orderNum = "16")
  private String  bank;
  @ApiModelProperty(value = "税率公式名称",name = "bank")
  private String  taxRateMethodName;

}
