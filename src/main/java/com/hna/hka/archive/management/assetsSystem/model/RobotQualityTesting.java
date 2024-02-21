package com.hna.hka.archive.management.assetsSystem.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RobotQualityTesting {
    @ApiModelProperty("检验单编号")
    private Long id;
    @ApiModelProperty("扫码解锁 0：通过 1：未通过")
    private String scanCodeTounlock;
    @ApiModelProperty("前进 0：通过 1：未通过")
    private String forward;
    @ApiModelProperty("后退 0：通过 1：未通过")
    private String backOff;
    @ApiModelProperty("刹车 0：通过 1：未通过")
    private String brack;
    @ApiModelProperty("副刹 0：通过 1：未通过")
    private String auxiliaryBrake;
    @ApiModelProperty("前超声 0：通过 1：未通过")
    private String preultrasound;
    @ApiModelProperty("后超声 0：通过 1：未通过")
    private String postultrasound;
    @ApiModelProperty("LED灯 0：通过 1：未通过")
    private String ledLight;
    @ApiModelProperty("呼吸灯 0：通过 1：未通过")
    private String breathingLamp;
    @ApiModelProperty("外观 0：通过 1：未通过")
    private String appearance;
    @ApiModelProperty("紧固 0：通过 1：未通过")
    private String fastening;
    @ApiModelProperty("结束服务 0：通过 1：未通过")
    private String endService;
    @ApiModelProperty("质检或者验收 0：质检 1：验收")
    private String qcOrAt;
    @ApiModelProperty("检验说明")
    private String testExplain;
    @ApiModelProperty("角色")
    private String role;
    @ApiModelProperty("创建时间")
    private String createDate;
    @ApiModelProperty("更新时间")
    private String updateDate;
}