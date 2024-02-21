package com.hna.hka.archive.management.assetsSystem.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("机器人检验记录")
public class InspectionRecord {

  @ApiModelProperty("检验单编号")
  private Long id;
  @ApiModelProperty("检验类型：1、工厂质检；2、景区验收")
  private Long type;
  @ApiModelProperty("检验标准ID")
  private Long inspectionId;
  @ApiModelProperty("检验标准NAME")
  private String inspectionName;
  @ApiModelProperty("检验结果：0、不合格；1、合格")
  private Long result;
  @ApiModelProperty("设备编号")
  private String robotCode;
  @ApiModelProperty("机器人型号")
  private String robotModel;
  @ApiModelProperty("检验员")
  private Long inspectorId;
  @ApiModelProperty("检验员")
  private String inspectorName;
  @ApiModelProperty("角色")
  private String inspectorRole;
  @ApiModelProperty("检验时间")
  private String inspectonTime;
  @ApiModelProperty("备注")
  private String notes;
  @ApiModelProperty("生产批次")
  private String productionInfo;

  private String createTime;
  private String updateTime;

  private String pictureUrl;

  private String companyName;

  private String scenicSpotName;

  @ApiModelProperty("是否删除 0未删除 1删除")
  private Long isDelected;

  private  InspectionRecordDetail[] details;

  private String detailsN;


}
