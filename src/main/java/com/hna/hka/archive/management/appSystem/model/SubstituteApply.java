package com.hna.hka.archive.management.appSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
@ApiOperation("配件申请")
public class SubstituteApply {

  @ApiModelProperty("申请单号")
  private String applyNumber;
  @ApiModelProperty("景区id")
  private Long spotId;
  @Excel(name = "景区名称" , width = 50 , orderNum = "1")
  @ApiModelProperty("景区名称")
  private String spotName;
  @ApiModelProperty("申请人id")
  private Long applyUserId;
  @Excel(name = "申请人名称" , width = 50 , orderNum = "2")
  @ApiModelProperty("申请人名称")
  private String applyUserName;
  @Excel(name = "申请时间" , width = 50 , orderNum = "3")
  @ApiModelProperty("申请时间")
  private String applyTime;
  @Excel(name = "申请原因" , width = 50 , orderNum = "4")
  @ApiModelProperty("申请原因")
  private String applyReasion;
  @ApiModelProperty("图片地址")
  private String picturePath;
//  @Excel(name = "总金额" , width = 50 , orderNum = "5")
  @ApiModelProperty("总金额")
  private Double totalAmount;
  @Excel(name = "审批结果(0审批中，1维修，2回收)" , width = 80 , orderNum = "6")
  @ApiModelProperty("审批结果(0审批中，1维修，2回收)")
  private String applyStat;
  @Excel(name = "审核意见" , width = 50 , orderNum = "7")
  @ApiModelProperty("审核意见")
  private String examineAggestion;
  @Excel(name = "收货人" , width = 50 , orderNum = "8")
  @ApiModelProperty("收货人")
  private String receivingName;
  @Excel(name = "收货电话" , width = 50 , orderNum = "9")
  @ApiModelProperty("收货电话")
  private String receivingPhone;
  @Excel(name = "入库单号" , width = 50 , orderNum = "10")
  @ApiModelProperty("入库单号")
  private String inventoryNumber;
  @Excel(name = "备注" , width = 50 , orderNum = "11")
  @ApiModelProperty("备注")
  private String note;
  @ApiModelProperty("创建时间")
  private String createTime;
  @ApiModelProperty("更新时间")
  private String updateTime;
  @ApiModelProperty("申请类型：3、大维修")//1、备件申请；2、上报故障；
  private Long type;
  @Excel(name = "故障单号" , width = 50 , orderNum = "12")
  @ApiModelProperty("故障单号")
  private String faultNumber;
//  @Excel(name = "机器人编号" , width = 50 , orderNum = "13")
  @ApiModelProperty("机器人编号,多个机器人逗号分隔")
  private String robotCode;
  @Excel(name = "上报电话" , width = 50 , orderNum = "14")
  @ApiModelProperty("上报电话")
  private String applyUserPhone;
  @Excel(name = "评估人" , width = 50 , orderNum = "15")
  @ApiModelProperty("评估人")
  private String assessorId;
  @Excel(name = "评估报告" , width = 50 , orderNum = "16")
  @ApiModelProperty("评估报告")
  private String assessmentReport;
  @Excel(name = "审批人" , width = 50 , orderNum = "17")
  @ApiModelProperty("审批人")
  private String approverId;
  @Excel(name = "处理结果确认人" , width = 50 , orderNum = "18")
  @ApiModelProperty("处理结果确认人")
  private String  finalId;
  @Excel(name = "处理结果（0未处理，1已修好，2已回收" , width = 50 , orderNum = "19")
  @ApiModelProperty("处理结果（0未处理，1已修好，2已回收）")
  private String processingResults;
  @Excel(name = "变卖金额/元" , width = 50 , orderNum = "20")
  @ApiModelProperty("变卖金额")
  private Double saleAmount;

  @Excel(name = "机器数量" , width = 50 , orderNum = "21")
  @ApiModelProperty("机器数量")
  private Integer robotNumber;

  @ApiModelProperty("审批结果")
  private String examineResult;
  @ApiModelProperty("申请报告")
  private String applicationReport;


  @ApiModelProperty("详情")
  private SubstituteApplyDetail[] details;

}
