package com.hna.hka.archive.management.assetsSystem.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@ApiModel("工厂发货")
public class FactorySend {

  @ApiModelProperty("主键")
  private Long id;
  @ApiModelProperty("机器人数量")
  private Long robotCount;
  @ApiModelProperty("发货进度：1、待发货；2、已发货；3、已收货；4、已关闭")
  private Long type;
  @ApiModelProperty("申请人")
  private Long applicantId;
  @ApiModelProperty("发货工厂")
  private Long factoryId;
  @ApiModelProperty("发货人")
  private Long consignorId;
  @ApiModelProperty("收货景区")
  private Long receivingId;
  @ApiModelProperty("收货人")
  private Long consigneeId;

  @ApiModelProperty("申请人")
  private String applicantName;

  @ApiModelProperty("发货工厂")
  private String factoryName;
  @ApiModelProperty("发货人")
  private String consignorName;
  @ApiModelProperty("收货景区")
  private String receivingName;
  @ApiModelProperty("收货人")
  private String consigneeName;


  @ApiModelProperty("收货人电话")
  private String consigneePhone;
  @ApiModelProperty("收货地址")
  private String receiningAddress;
  @ApiModelProperty("申请时间")
  private String applicantTime;
  @ApiModelProperty("设备编码")
  private String robotCodes;
  @ApiModelProperty("发货件数")
  private Long consignmentCount;
  @ApiModelProperty("货车类型")
  private String carType;
  @ApiModelProperty("车牌号")
  private String license;
  @ApiModelProperty("装车方式：1、人工装车；2、叉车装车")
  private Long loadingType;
  @ApiModelProperty("运费")
  private Double loadingCost;
  @ApiModelProperty("驾驶员姓名")
  private String driverName;
  @ApiModelProperty("驾驶员电话")
  private String driverPhone;
  @ApiModelProperty("发货图片")
  private String consignmentPicture;
  @ApiModelProperty("发货备注")
  private String comsignmentNotes;
  @ApiModelProperty("发货时间")
  private String consignmentDate;
  @ApiModelProperty("收货数量")
  private Long receivingCount;
  @ApiModelProperty("收货机器人数量")
  private Long receivingRobotCount;
  @ApiModelProperty("收货备注")
  private String receivingNotes;
  @ApiModelProperty("收货图片")
  private String receivingPicture;
  @ApiModelProperty("收货时间")
  private String receivingDate;

//  @ApiModelProperty("发货图片文件")
//  private MultipartFile[] consignments;
//
//  @ApiModelProperty("收货图片文字")
//  private MultipartFile[] receivings;

  @ApiModelProperty("类型1、工厂发货；2、景区调度")
  private Long form;

  @ApiModelProperty("发货人手机号")
  private Long  consignorPhone;

  @ApiModelProperty("机器人型号,数量")
  private List<FactorySendRobotModel> robotList ;

  private String applicableModel;
}
