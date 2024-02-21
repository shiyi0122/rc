package com.hna.hka.archive.management.appSystem.model;



import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AttendanceRecord {

    @ApiModelProperty("ID")
    private Long recordId;

    @ApiModelProperty("景区ID")
    private Long scenicSpotId;

    @ApiModelProperty("景区ID")
    @Excel(name = "景区名称",width = 20)
    private String scenicSpotName;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户名称")
    @Excel(name = "用户名称",width = 20)
    private String userName;

    @ApiModelProperty("打卡类型")
    @Excel(name = "打卡类型",width = 20)
    private String type;

    @ApiModelProperty("打卡时间")
    @Excel(name = "打卡时间",width = 20)
    private String workTime;

    @ApiModelProperty("打卡地点")
    @Excel(name = "打卡地点",width = 20)
    private String workPlace;

    @ApiModelProperty("打卡图片")
    @Excel(name = "打卡图片",width = 20)
    private String workPhoto;

    @ApiModelProperty("岗位")
    @Excel(name = "岗位",width = 20)
    private String employeePost;

    @ApiModelProperty("职位")
    @Excel(name = "职位",width = 20)
    private String employeeType;

    @ApiModelProperty("工作日志")
    @Excel(name = "工作日志",width = 20)
    private String workJournal;

    @ApiModelProperty("打卡结果")
    @Excel(name = "打卡结果",width = 20)
    private String employeeAttendAnce;

    @ApiModelProperty("创建时间")
    @Excel(name = "创建时间",width = 20)
    private String createDate;

    @ApiModelProperty("今日天气")
    private String weather;

    @ApiModelProperty("运营总人数")
    private String totalNumberPeople;

    @ApiModelProperty("实际到岗人数")
    private String actualNumberPeople;

    @ApiModelProperty("调休人数")
    private String compensatoryLeavePeople;

    @ApiModelProperty("兼职人数")
    private String parttimePeople;

    @ApiModelProperty("机器人总台数")
    private String totalNumberRobot;

    @ApiModelProperty("正常机器人台数")
    private String NormalRobotNumber;

    @ApiModelProperty("故障机器人台数")
    private String faultRobotNumber;

    @ApiModelProperty("出勤机器人台数")
    private String attendanceRobotNumber;

    @ApiModelProperty("开始运营时间")
    private String startTime;

    @ApiModelProperty("暂停运营时间")
    private String suspendTime;

    @ApiModelProperty("恢复运营时间")
    private String RecoveryTime;

    @ApiModelProperty("结束运营时间")
    private String endTime;

    @ApiModelProperty("充电负责人")
    private String chargeDutyPeople;

    @ApiModelProperty("防火负责人")
    private String fireperventionDutyPeople;

    @ApiModelProperty("备注")
    private String remarks;

    @ApiModelProperty("日期")
    private String todayDate;


}
