package com.hna.hka.archive.management.assetsSystem.model;


import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("结账流水")
public class AccountClose {

  @ApiModelProperty(name = "id" , value = "主键id")
  private long id;

  @ApiModelProperty(name = "companyId" , value = "收款单位id")
  private long companyId;

  @Excel(name = "收款单位" , width = 50 , orderNum = "0")
  @ApiModelProperty(name = "companyName" , value = "收款单位名称")
  private String companyName;

  @ApiModelProperty(name = "spotId" , value = "景区id")
  private long spotId;

  @Excel(name = "景区名称" , width = 50 , orderNum = "1")
  @ApiModelProperty(name = "spotName" , value = "景区名称")
  private String spotName;

  @Excel(name = "月份" , width = 20 , orderNum = "2")
  @ApiModelProperty(name = "month" , value = "月份")
  private String month;

  @Excel(name = "微信支付金额/元" , width = 20 , orderNum = "3")
  @ApiModelProperty(name = "wechat" , value = "微信支付金额")
  private double wechat;

  @Excel(name = "储值支付金额/元" , width = 20 , orderNum = "4")
  @ApiModelProperty(name = "saving" , value = "储值支付金额")
  private double saving;

  @Excel(name = "储值抵扣+微信支付金额/元" , width = 20 , orderNum = "5")
  @ApiModelProperty(name = "savingWechat" , value = "微信+储值支付金额")
  private double savingWechat;

  @Excel(name = "押金抵扣金额/元" , width = 20 , orderNum = "6")
  @ApiModelProperty(name = "deposit" , value = "押金抵扣金额")
  private double deposit;

  @Excel(name = "总分润基数金额/元" , width = 20 , orderNum = "7")
  @ApiModelProperty(name = "total" , value = "总分润基数金额")
  private double total;

  @Excel(name = "手续费费率" , width = 20 , orderNum = "8")
  @ApiModelProperty(name = "charge" , value = "手续费")
  private double charge;

  @Excel(name = "可分润金额(手续费已扣)" , width = 20 , orderNum = "9")
  @ApiModelProperty(name = "totalCharge" , value = "可分润金额")
  private double totalCharge;

  @Excel(name = "分润比例" , width = 20 , orderNum = "10")
  @ApiModelProperty(name = "proportion" , value = "分润比例")
  private double proportion;

  @Excel(name = "分润金额（手续费已扣、未扣税点）/元" , width = 20 , orderNum = "11")
  @ApiModelProperty(name = "totalProportion" , value = "分润金额")
  private double totalProportion;

  @Excel(name = "税点" , width = 20 , orderNum = "12")
  @ApiModelProperty(name = "tax" , value = "税率")
  private double tax;

  @Excel(name = "扣税后总流水金额/元(收入)" , width = 20 , orderNum = "13")
  @ApiModelProperty(name = "totalTax" , value = "税后金额(收入)")
  private double totalTax;

  @Excel(name = "扣税后总流水金额/元(支出)" , width = 20 , orderNum = "13")
  @ApiModelProperty(name = "totalTaxEx" , value = "税后金额(支出)")
  private double totalTaxEx;

  @Excel(name = "机器数量" , width = 20 , orderNum = "14")
  @ApiModelProperty(name = "robotCount" , value = "机器人总数")
  private long robotCount;

  @ApiModelProperty(name = "accountCheckId" , value = "流水核实人")
  private long accountCheckId;

  @Excel(name = "流水金额核实人" , width = 20 , orderNum = "15")
  @ApiModelProperty(name = "accountCheckName" , value = "流水核实人")
  private String accountCheckName;

  @Excel(name = "配件申请费用/元" , width = 20 , orderNum = "16")
  @ApiModelProperty(name = "parts" , value = "配件申请费用")
  private double parts;

  @Excel(name = "上报故障新发配件费用/元" , width = 20 , orderNum = "17")
  @ApiModelProperty(name = "partsNew" , value = "上报故障新发配件费用")
  private double partsNew;

  @Excel(name = "上报故障维修费用/元" , width = 20 , orderNum = "18")
  @ApiModelProperty(name = "fault" , value = "上报故障维修费用")
  private double fault;

  @Excel(name = "损坏赔偿配件费用/元(配件价格-游客已支付费用)" , width = 20 , orderNum = "19")
  @ApiModelProperty(name = "compensate" , value = "损坏赔偿配件费用")
  private double compensate;

  @Excel(name = "总配件维修金额/元" , width = 20 , orderNum = "20")
  @ApiModelProperty(name = "repair" , value = "总配件维修金额")
  private double repair;

  @Excel(name = "景区承担配件维修金额" , width = 20 , orderNum = "21")
  @ApiModelProperty(name = "spotRepair" , value = "景区承担配件维修金额")
  private double spotRepair;

  @Excel(name = "财务收配件维修金额" , width = 20 , orderNum = "22")
  @ApiModelProperty(name = "spotRepairIn" , value = "财务收配件维修金额")
  private double  spotRepairIn;

  @ApiModelProperty(name = "repairCheckId" , value = "配件维修金额核实人")
  private long repairCheckId;


  @Excel(name = "配件维修金额核实人" , width = 20 , orderNum = "23")
  @ApiModelProperty(name = "repairCheckName" , value = "配件维修金额核实人")
  private String repairCheckName;

  @Excel(name = "付款金额/元" , width = 20 , orderNum = "24")
  @ApiModelProperty(name = "moneyOut" , value = "付款金额")
  private double moneyOut;

  @Excel(name = "收款金额/元" , width = 20 , orderNum = "25")
  @ApiModelProperty(name = "moneyIn" , value = "收款金额")
  private double moneyIn;

  @ApiModelProperty(name = "rpStatus" , value = "配件维修费结算状态")
  private Integer rpStatus;

  @ApiModelProperty(name = "psStatus" , value = "分润结算状态")
  private Integer psStatus;

  @ApiModelProperty(name = "psUserId" , value = "分润付款人id")
  private Integer psUserId;

  @Excel(name = "支出类型" , width = 20 , orderNum = "26")
  @ApiModelProperty(name = "type" , value = "支出类型")
  private long type;

  @Excel(name = "备注" , width = 20 , orderNum = "27")
  @ApiModelProperty(name = "notes" , value = "备注")
  private String notes;

  @Excel(name = "付款人" , width = 20 , orderNum = "28")
  @ApiModelProperty(name = "paymentName" , value = "付款人")
  private String paymentName;

  @Excel(name = "状态" , width = 20 , orderNum = "29")
  @ApiModelProperty(name = "status" , value = "状态")
  private long status;

  @Excel(name = "运营对账时间" , width = 20 , orderNum = "30")
  @ApiModelProperty(name = "reconciliationTime" , value = "运营对账时间")
  private String reconciliationTime;

  @Excel(name = "财务付款时间" , width = 20 , orderNum = "31")
  @ApiModelProperty(name = "payTime" , value = "财务付款时间")
  private String payTime;

  @ApiModelProperty(name = "createTime" , value = "创建时间")
  private String createTime;

}
