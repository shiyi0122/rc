package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/6/2 10:50
 */
@Data
public class SysRobotSimSupplier {

    private  Long id;

    @Excel(name = "机器人编号",width = 10,orderNum = "0")
    private  String robotCode;
    @Excel(name = "ICCID卡号",width = 10,orderNum = "1")
    private  String simCard;
    @Excel(name = "供应商",width = 10,orderNum = "2")
    private String supplierName;
    @Excel(name = "备注",width = 10,orderNum = "3")
    private String remarks;
    @Excel(name = "创建时间",width = 10,orderNum = "4")
    private String createTime;

    private String updateTime;



}
