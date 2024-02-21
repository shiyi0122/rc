package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/5/7 16:12
 * 利用率
 */
@Data
public class SpotGrossProfitMargin {

    @Excel(name = "时间" , orderNum = "2" , width = 10)
    private String time;

    private String spotId;
    @Excel(name = "景区名称" , orderNum = "1" , width = 10)
    private String spotName;
    @Excel(name = "流水/元" , orderNum = "3" , width = 10)
    private double spotIdAndTimeByIncome;
    @Excel(name = "运营时长/小时" , orderNum = "4" , width = 10)
    private double spotIdAndTimeByOperationDuration;
    @Excel(name = "机器人利用率/%" , orderNum = "5" , width = 10)
    private double utilizationRate;
    @Excel(name = "同比/%" , orderNum = "6" , width = 10)
    private String yearOnYear;
    @Excel(name = "环比/%" , orderNum = "7" , width = 10)
    private String monthOnMonth;


}
