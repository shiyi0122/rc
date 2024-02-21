package com.hna.hka.archive.management.system.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class SysOrderOperationLogExcel extends BaseRowModel {



    @ExcelProperty(value = "操作人名称",index = 1)
    private String operationName;
    @ExcelProperty(value = "订单编号",index = 2)
    private String operationNumber;
    @ExcelProperty(value = "用户手机号",index = 3)
    private String operationPhone;
    @ExcelProperty(value = "景区名称",index = 4)
    private String operationScenicSpotName;
    @ExcelProperty(value = "机器人编号",index = 5)
    private String operationRobotCode;
    @ExcelProperty(value = "使用时长",index = 6)
    private String operationTotalTime;
    @ExcelProperty(value = "修改前",index = 7)
    private String operationFront;
    @ExcelProperty(value = "修改后",index = 8)
    private String operationAfter;
    @ExcelProperty(value = "创建时间",index = 9)
    private String createDate;

}