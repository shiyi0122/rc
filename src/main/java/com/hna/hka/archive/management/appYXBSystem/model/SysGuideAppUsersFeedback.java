package com.hna.hka.archive.management.appYXBSystem.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
@ApiOperation("用户反馈")
public class SysGuideAppUsersFeedback {

  @ApiModelProperty("主键id")
  private Long feedbackId;
  @ApiModelProperty("用户id")
  private Long   appUserId;
  @ApiModelProperty("反馈内容")
  private String   feedbackContent;
  @ApiModelProperty("创建时间")
  private String  createDate;
  @ApiModelProperty("修改是就")
  private String  updateDate;



}
