package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysRobotObstacleAvoidanceModule {
    private Long obstacleAvoidanceModularId;

    @Excel(name = "避障模块ID",width = 10,orderNum = "0")
    private String obstacleAvoidanceId;

    @Excel(name = "避障模块名称",width = 10,orderNum = "1")
    private String obstacleAvoidanceName;

    @Excel(name = "模块型号",width = 10,orderNum = "2")
    private String modularModel;

    @Excel(name = "模块厂家",width = 10,orderNum = "3")
    private String modularManufactor;

    @Excel(name = "模块ID",width = 10,orderNum = "4")
    private String modularId;

    @Excel(name = "备注",width = 10,orderNum = "5")
    private String remarks;

    private String createDate;

    private String updateDate;
}