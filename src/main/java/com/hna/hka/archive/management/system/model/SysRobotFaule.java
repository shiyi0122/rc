package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysRobotFaule {
    private Long robotAlarmId;

    @Excel(name = "机器人编号", width = 20, orderNum = "0")
    private String robotCode;

    private Long scenicSpotId;

    @Excel(name = "机器人报警信息", width = 20, orderNum = "2")
    private String robotAlarmInfo;

    @Excel(name = "机器人故障码", width = 20, orderNum = "3")
    private String robotFaultCode;

    private String robotVersion;

    @Excel(name = "录入时间", width = 20, orderNum = "4")
    private String createDate;

    private String updateDate;

    private String sourceType;

    /**
     * 景区名称
     */
    @Excel(name = "景区名称", width = 30, orderNum = "1")
    private String scenicSpotName;

}