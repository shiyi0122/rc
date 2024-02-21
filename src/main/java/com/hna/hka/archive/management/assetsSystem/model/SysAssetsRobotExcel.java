package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.model
 * @ClassName: SysAssetsRobotExcel
 * @Author: 郭凯
 * @Description: 机器人运营信息数据导出实体
 * @Date: 2021/5/27 14:34
 * @Version: 1.0
 */
@Data
public class SysAssetsRobotExcel {

    @Excel(name = "机器人编号",width = 10,orderNum = "0")
    private String robotCode;

    @Excel(name = "景区名称",width = 10,orderNum = "1")
    private String scenicSpotName;

    @Excel(name = "运行状态",width = 10,orderNum = "2")
    private String robotRunStateName;

    @Excel(name = "故障状态",width = 10,orderNum = "3")
    private String robotFaultStateName;

    @Excel(name = "当天运营时长",width = 10,orderNum = "4")
    private String totalTime;

    @Excel(name = "机器人PAD版本号",width = 10,orderNum = "5")
    private String clientVersion;

    @Excel(name = "机器人型号",width = 10,orderNum = "6")
    private String robotModel;

    @Excel(name = "机器人版本号",width = 10,orderNum = "7")
    private String robotVersionNumber;

    private String robotPowerState;

    @Excel(name = "添加时间",width = 10,orderNum = "9")
    private String createDate;

    private String robotRunState;

    private String robotFaultState;

}
