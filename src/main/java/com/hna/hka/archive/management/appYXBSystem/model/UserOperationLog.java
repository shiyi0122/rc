package com.hna.hka.archive.management.appYXBSystem.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserOperationLog对象", description = "表")
public class UserOperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "操作类型 0安装 1注册 2打开")
    private Integer type;

    @ApiModelProperty(value = "景区id")
    private String spotId;

    @ApiModelProperty(value = "景区名称")
    private String spotName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    //所有数据的总和
    private String allNumber;

    //安装总和
    private String installNumber;

    //注册总和
    private String registerNumber;

    //打开总和
    private String openNumber;
}
