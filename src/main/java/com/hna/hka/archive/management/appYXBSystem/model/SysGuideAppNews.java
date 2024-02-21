package com.hna.hka.archive.management.appYXBSystem.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
@ApiOperation("用户消息")
public class SysGuideAppNews {

  @ApiModelProperty("主键")
  private Long guideId;
  @ApiModelProperty("用户id")
  private Long guideUserId;
  @ApiModelProperty("标题")
  private String guideTitle;
  @ApiModelProperty("内容")
  private String guideContent;
  @ApiModelProperty("0 未读 1已读 2删除")
  private String guideState;
  @ApiModelProperty("创建时间")
  private String createDate;
  @ApiModelProperty("更新时间")
  private String updateDate;

  private String userPhone;


}
