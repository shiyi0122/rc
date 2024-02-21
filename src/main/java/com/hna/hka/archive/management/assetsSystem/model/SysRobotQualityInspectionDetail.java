package com.hna.hka.archive.management.assetsSystem.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("质检标准具体检测项")
public class SysRobotQualityInspectionDetail {

    @ApiModelProperty(name = "id", value = "id")
    private long id;
    @ApiModelProperty(name = "standardId", value = "质检标准id")
    private long standardId;
    @ApiModelProperty(name = "serial", value = "序号")
    private Integer serial;
    @ApiModelProperty(name = "project", value = "检测项")
    private String project;
    @ApiModelProperty(name = "parameter", value = "技术参数")
    private String parameter;
    @ApiModelProperty(name = "createTime", value = "创建时间")
    private String createTime;
    @ApiModelProperty(name = "类型", value = "类型:1上移,2下移")
    private Integer type;
}
