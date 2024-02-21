package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysRobot {
	private Long robotId;

    @Excel(name = "机器人ICCID",width = 10,orderNum = "1")
    private String robotCodeSim;

    private Long scenicSpotId;

    private String robotCodeCid;

    private String newRobotCodeCid;

    private String robotAppType;

    @Excel(name = "机器人编号",width = 10,orderNum = "2")
    private String robotCode;

    private String robotRunState;

    private String robotPowerState;

    private String robotFaultState;

    private String robotType;

    private String robotPollingType;

    private String robotGpsSmallApp;

    private String robotGpsBaiDu;

    private String robotGpsGpgga;

    private String robotAdminLocking;

    private String pushStatus;

    private String updatePushDate;

    private String robotOperationStatus;

    private String createDate;

    private String updateDate;

    private String robotRemarks;

    private String clientVersion;

    /**
     *  机器人蓝牙编号
     */
    private String  robotBluetooth;

    /**
     * 机器人状态名称
     */
    private String robotRunStateName;

    /**
     * 机器人版本号
     */
    @Excel(name = "机器人版本号",width = 10,orderNum = "3")
    private String robotVersionNumber;

    /**
     * 景区名称
     */
    private String scenicSpotName;

    @Excel(name = "机器人批次号")
    private String robotBatchNumber;

    //设备状态,10在产、20库存、30运营、40报废
    private String  equipmentStatus;

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人当日订单总时长
     * @Return
     * @Date 2021/5/26 15:44
     */
    private String totalTime;

    @Excel(name = "机器人型号" ,width = 10,orderNum = "3" )
    private String robotModel;

    //是否影响使用
    private String errorRecordsAffect;
    //故障单号创建时间
    private String CreatTime;
}
