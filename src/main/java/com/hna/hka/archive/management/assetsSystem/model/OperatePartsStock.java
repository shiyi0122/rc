package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/11/11 17:03
 */
@Data
public class OperatePartsStock {

    private Long id;

    private Long spotId;
    @Excel(name = "申请单号",orderNum = "1")
    private Long applyNumber;
    //配件id
    private Long partsManagementId;
    @Excel(name = "配件名称",orderNum = "2")
    private String  partsManagementName;
    @Excel(name = "配件型号",orderNum = "3")
    private String accessoryModel;
    @Excel(name = "配件类型" ,replace = {"出库_1" , "入库_2"},orderNum = "4")
    private String  type;
    @Excel(name = "数量",orderNum = "5")
    private String number;
    @Excel(name = "原因",orderNum = "6")
    private String  reason;

    private Long operatorUserId;
    @Excel(name = "设备编号",orderNum = "7")
    private String equipmentNo;
    @Excel(name = "设备余量",orderNum = "8")
    private String  inventoryBalance;
    @Excel(name = "创建时间",orderNum = "9")
    private String  createTime;
    @Excel(name = "修改时间",orderNum = "10")
    private String  updateTime;
    @Excel(name = "过质保",replace = {"是_1" , "否_0"},orderNum = "11")
    private String  overWarranty;
    @Excel(name = "阈值",orderNum = "12")
    private String  threshold;
    @Excel(name = "备注",orderNum = "13")
    private String  remarks;
    @Excel(name = "景区名称",orderNum = "2")
    private String spotName;
    @Excel(name = "操作人名称",orderNum = "6")
    private String operatorUserName;

}
