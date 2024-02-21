package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class EquipmentAttendanceStatistics {


    @ApiModelProperty("景区ID")
    private String scenicSpotId;

    @Excel(name = "景区名称" , width = 20)
    @ApiModelProperty("景区名称")
    private String scenicSpotName;

    @Excel(name = "开业天数" , width = 20)
    @ApiModelProperty("开业天数")
    private String openingDays;

    @Excel(name = "运营天数" , width = 20)
    @ApiModelProperty("运营天数")
    private String operationDays;

    @Excel(name = "园区运营率" , width = 20)
    @ApiModelProperty("园区运营率")
    private Double scenicSpotNameOperatingRate;

    @Excel(name = "设备总台数" , width = 20)
    @ApiModelProperty("设备总台数")
    private String equipmentNum;

    @Excel(name = "设备出勤数" , width = 20)
    @ApiModelProperty("设备出勤数")
    private String equipmentOperatingNum;

    @Excel(name = "设备出勤率" , width = 20)
    @ApiModelProperty("设备出勤率")
    private Double equipmenOperatingRate;

    @ApiModelProperty("运营人数")
    private String operationPeople;

    @Excel(name = "园区状态" , width = 20)
    @ApiModelProperty("园区状态")
    private String pauseState;

    @ApiModelProperty("创建时间")
    private String createDate;

    @ApiModelProperty("备注")
    private String remarks;

    @ApiModelProperty("正式运营时间")
    private String formalOperationTime;

    @Excel(name = "景区状态" , width = 20)
    @ApiModelProperty("景区状态")
    private String robotWakeupWords;

    @ApiModelProperty("日期")
    private String todayDate;

    @ApiModelProperty("时间类型")
    private String dateType;
    @ApiModelProperty("查询类型，1景区，2时间")
    private String queryType;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;



}
