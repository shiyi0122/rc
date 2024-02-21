package com.hna.hka.archive.management.assetsSystem.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("质检标准")
@Data
public class SysRobotQualityInspectionStandard {

  @ApiModelProperty(name = "id" , value = "主键")
  private long id;
  @ApiModelProperty(name = "name" , value = "检验标准名称")
  private String name;
  @ApiModelProperty(name = "aplicableModel" , value = "适用机器型号")
  private String aplicableModel;
  @ApiModelProperty(name = "inspectionType" , value = "检验类型")
  private String inspectionType;
  @ApiModelProperty(name = "createTime" , value = "创建时间")
  private String createTime;
  @ApiModelProperty(name = "notes" , value = "备注")
  private String notes;

  private List<SysRobotQualityInspectionDetail> detailList;

}
