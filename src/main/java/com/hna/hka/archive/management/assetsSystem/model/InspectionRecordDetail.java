package com.hna.hka.archive.management.assetsSystem.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("校验记录明细项")
public class InspectionRecordDetail {

  @ApiModelProperty("主键")
  private Long id;
  @ApiModelProperty("校验记录id")
  private Long standardId;
  @ApiModelProperty("序号")
  private Long serial;
  @ApiModelProperty("质检项")
  private String project;
  @ApiModelProperty("质检标准")
  private String parameter;
  @ApiModelProperty("创建时间")
  private String createTime;
  @ApiModelProperty("修改时间")
  private String updateTime;
  @ApiModelProperty("合格标志:0不合格;1合格")
  private Long qualified;
  //是否修改
  private String ifModify;

}
