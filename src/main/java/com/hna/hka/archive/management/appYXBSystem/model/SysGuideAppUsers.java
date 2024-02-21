package com.hna.hka.archive.management.appYXBSystem.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
@ApiOperation("用户信息")
public class SysGuideAppUsers {

  @ApiModelProperty("用户id")
  private Long userId;
  @ApiModelProperty("用户手机号")
  private String  userPhone;
  @ApiModelProperty("token")
  private String  longinTokenId;
  @ApiModelProperty("推送id")
  private String userClientGtId;
  @ApiModelProperty("手机唯一标识")
  private String  phoneSign;
  @ApiModelProperty("创建时间")
  private String createDate;
  @ApiModelProperty("更新时间")
  private String  updateDate;


}
