package com.hna.hka.archive.management.appYXBSystem.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "UserOperationLogDTO对象", description = "APP用户操作记录查询体")
public class UserOperationLogDTO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "操作类型 0注册 1安装 2打开")
    private Integer type;

    @ApiModelProperty(value = "景区id")
    private String spotId;

    @ApiModelProperty(value = "景区名称")
    private String spotName;

    @ApiModelProperty(value = "创建时间")
    private String createTime;


    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    @ApiModelProperty(value = "微信")
    private String wxName;

    @ApiModelProperty(value = "当前IP")
    private String ipAddress;

    @ApiModelProperty(value = "用户归属地(手机号归属地/ip归属地)")
    private String userLocation;

    @ApiModelProperty(value = "用户当前位置")
    private String nowLocation;

    @ApiModelProperty(value = "第一次景区id")
    private String fristSpotId;

    @ApiModelProperty(value = "第一次景区名称")
    private String fristSpotName;



}
