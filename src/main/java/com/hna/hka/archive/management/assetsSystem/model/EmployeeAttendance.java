package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：employeeAttendance
 * @Author: gouteng
 * @Date: 2022-11-11 18:05
 * @Description: 必须描述类做什么事情, 实现什么功能
 */
@Data
public class EmployeeAttendance {
    @ApiModelProperty("员工记录ID")
    private Long recordId;
    @ApiModelProperty("景区ID")
    private Long scenicSpotId;

    @ApiModelProperty("归属景区")
    private String belongingScenicSpot;
    @ApiModelProperty("景区名称")
    private String scenicSpotName;
    @ApiModelProperty("员工ID")
    private Long userId;
    @Excel(name = "员工名称" , width = 15 , orderNum = "4")
    @ApiModelProperty("员工名称")
    private String userName;
    @ApiModelProperty("专职,兼职")
    private String employeeType;
    @Excel(name = "员工岗位" , width = 15 , orderNum = "5")
    @ApiModelProperty("员工岗位")
    private String employeePost;
    @ApiModelProperty("打卡情况")
    private String type;
    @Excel(name = "是否打卡" , width = 15 , orderNum = "6")
    @ApiModelProperty("是否打卡")
    private String signInOrNot;
    @Excel(name = "打卡照片" , width = 15 , orderNum = "7")
    @ApiModelProperty("打卡照片")
    private String workPhoto;
    @Excel(name = "打卡地点" , width = 15 , orderNum = "8")
    @ApiModelProperty("打卡地点")
    private String workPlace;
    @Excel(name = "打卡时间" , width = 15 , orderNum = "8")
    @ApiModelProperty("打卡时间")
    private String workTime;
    @Excel(name = "开始日期" , width = 15 , orderNum = "2")
    @ApiModelProperty("开始日期")
    private String startDate;
    @Excel(name = "结束日期" , width = 15 , orderNum = "3")
    @ApiModelProperty("结束日期")
    private String endDate;
    @ApiModelProperty("创建时间")
    private String createDate;

    @ApiModelProperty("时间段")
    private String timeQuantum;
    @ApiModelProperty("打卡次数")
    private String signInNum;

    @ApiModelProperty("打卡照片")
    private String[] workPhotos;

}
