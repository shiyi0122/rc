package com.hna.hka.archive.management.assetsSystem.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("生产批次")
public class ProductionInfo {

    @ApiModelProperty("id")
    private long id;
    @ApiModelProperty("生产批次")
    private String name;
    @ApiModelProperty("质检项id")
    private long inspectionId;
    @ApiModelProperty("质检项")
    private String inspectionName;
    @ApiModelProperty("验收项id")
    private String appectanceId;
    @ApiModelProperty("验收项")
    private String appectance;
    @ApiModelProperty("机器型号")
    private String applicableModel;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("备注")
    private String notes;
    @ApiModelProperty("工厂名称")
    private String factoryName;
    @ApiModelProperty("工厂id")
    private long factoryId;
    @ApiModelProperty("准生产机器人数量")
    private Long robotCount;
    @ApiModelProperty("是否有效标识:1.有效,2.无效")
    private Integer identification;
    @ApiModelProperty("批次是否完成状态:1.已完成,2.未完成")
    private Integer type;

    @ApiModelProperty("机器人型号数量列表")
    private List<ProductionInfoRobotModel> robotList;

}
