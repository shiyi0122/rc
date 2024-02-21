package com.hna.hka.archive.management.appYXBSystem.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
@ApiOperation("使用帮助")
public class SysGuideAppUsersHelp {

  @ApiModelProperty("主键")
  private Long  helpId;
  @ApiModelProperty("标题")
  private String  helpTitle;
  @ApiModelProperty("内容")
  private String helpContent;
  @ApiModelProperty("创建时间")
  private String createDate;
  @ApiModelProperty("修改时间")
  private String updateDate;



}
