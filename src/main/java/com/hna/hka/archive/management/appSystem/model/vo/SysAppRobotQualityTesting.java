package com.hna.hka.archive.management.appSystem.model.vo;

import com.hna.hka.archive.management.assetsSystem.model.InspectionRecordDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysAppRobotQualityTesting {
    @ApiModelProperty("检验单编号")
    private long id;
    @ApiModelProperty("扫码解锁 0：通过 1：未通过")
    private int scanCodeTounlock;
    @ApiModelProperty("前进 0：通过 1：未通过")
    private long forward;
    @ApiModelProperty("后退 0：通过 1：未通过")
    private long backOff;
    @ApiModelProperty("刹车 0：通过 1：未通过")
    private long brack;
    @ApiModelProperty("副刹 0：通过 1：未通过")
    private long auxiliaryBrake;
    @ApiModelProperty("前超声 0：通过 1：未通过")
    private long preultrasound;
    @ApiModelProperty("后超声 0：通过 1：未通过")
    private long postultrasound;
    @ApiModelProperty("LED灯 0：通过 1：未通过")
    private long ledLight;
    @ApiModelProperty("呼吸灯 0：通过 1：未通过")
    private long breathingLamp;
    @ApiModelProperty("外观 0：通过 1：未通过")
    private long appearance;
    @ApiModelProperty("紧固 0：通过 1：未通过")
    private long fastening;
    @ApiModelProperty("结束服务 0：通过 1：未通过")
    private long endService;
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
    @ApiModelProperty("检验类型：1、工厂质检；2、景区验收")
    private long type;
    @ApiModelProperty("检验标准ID")
    private long inspectionId;
    @ApiModelProperty("检验标准NAME")
    private String inspectionName;
    @ApiModelProperty("检验结果：0、不合格；1、合格")
    private long result;
    @ApiModelProperty("设备编号")
    private String robotCode;
    @ApiModelProperty("机器人型号")
    private String robotModel;
    @ApiModelProperty("检验员")
    private long inspectorId;
    @ApiModelProperty("检验员")
    private String inspectorName;
    @ApiModelProperty("角色")
    private String inspectorRole;
    @ApiModelProperty("检验时间")
    private String inspectonTime;
    @ApiModelProperty("备注")
    private String notes;
    @ApiModelProperty("生产批次")
    private String productionInfo;

    private InspectionRecordDetail[] details;

}
