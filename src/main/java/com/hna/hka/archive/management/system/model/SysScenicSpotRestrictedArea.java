package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysScenicSpotRestrictedArea {
    private Long restrictedWarningId;

    private Long restrictedWarningScenicSpotId;

    @Excel(name = "机器人编号", width = 20 , orderNum = "1")
    private String restrictedWarningRobotCode;

    @Excel(name = "禁区名称", width = 20 , orderNum = "2")
    private String restrictedWarningInnercircleName;

    @Excel(name = "用户手机号", width = 20 , orderNum = "4")
    private String restrictedWarningUserPhone;
    
    @Excel(name = "告警信息", width = 40 , orderNum = "3")
    private String restrictedWarningMsg;

    @Excel(name = "创建时间", width = 20 , orderNum = "7")
    private String createDate;

    private String updateDate;

    /**
     * 景区名称
     */
    @Excel(name = "景区名称", width = 20 , orderNum = "0")
    private String scenicSpotName;
}