package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：RobotAttendance
 * @Author: gouteng
 * @Date: 2022-11-10 18:24
 * @Description: 机器人考勤实体类
 */
@Data
public class RobotAttendance {
    @ApiModelProperty("考勤记录主键ID")
    private Long recordId;

    @ApiModelProperty("景区ID")
    private Long scenicSpotId;

    @ApiModelProperty("景区名称")
    @Excel(name = "景区名称", width = 18,orderNum = "1")
    private String scenicSpotName;

    @ApiModelProperty("机器人编号")
    @Excel(name = "机器人编号", width = 15, orderNum = "4")
    private Long robotCode;

    @ApiModelProperty("机器人状态")
    @Excel(name = "机器人状态", width = 15,orderNum = "5")
    private String robotStatus;

    @ApiModelProperty("开始日期")
    @Excel(name = "开始日期", width = 18, orderNum = "2")
    private String startDate;

    @ApiModelProperty("结束日期")
    @Excel(name = "结束日期", width = 18, orderNum = "3")
    private String endDate;

    @ApiModelProperty("机器人打卡时间")
    private String robotStartTime;

    @ApiModelProperty("机器人点位")
    @Excel(name = "机器人点位", width = 18,orderNum = "6")
    private String robotPosition;

    @ApiModelProperty("机器人版本号")
    @Excel(name = "机器人版本号", width = 15,orderNum = "7")
    private String clientVersion;

    @ApiModelProperty("版本数量")
    private Integer versionNum;

    @ApiModelProperty("创建时间")
    private String createDate;

    //是否影响
    private String errorRecordsAffect;

}
