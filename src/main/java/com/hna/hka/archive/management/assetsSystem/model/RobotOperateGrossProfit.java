package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/11/10 15:58
 */
@Data
public class RobotOperateGrossProfit {

    @Excel(name = "景区名称" , width = 20 , orderNum = "1")
    private String spotName;
    @Excel(name = "时间" , width = 20 , orderNum = "2")
    private String   cycleTime;
    @Excel(name = "交易金额" , width = 20 , orderNum = "3")
    private String  transactionprice;
    @Excel(name = "运营时长" , width = 20 , orderNum = "4")
    private String operationDuration;
    @Excel(name = "平均单台交易金额" , width = 20 , orderNum = "5")
    private String averagePrice;
    @Excel(name = "平均运营时长" , width = 20 , orderNum = "6")
    private String averageTime;
    @Excel(name = "机器人折旧金额" , width = 20 , orderNum = "7")
    private String cost;
    @Excel(name = "综合成本" , width = 20 , orderNum = "8")
    private Double sumPrice;
    @Excel(name = "台均和成" , width = 20 , orderNum = "9")
    private String comprehensiveCost;
    @Excel(name = "公司所得分润金额" , width = 20 , orderNum = "10")
    private String divide;
    @Excel(name = "机-人配比" , width = 20 , orderNum = "11")
    private String robotPeopleRatio;
    @Excel(name = "公司所得分润金额" , width = 20 , orderNum = "12")
    private String grossProfit;
    @Excel(name = "净利润" , width = 20 , orderNum = "13")
    private String netProfit;
    @Excel(name = "台均毛利" , width = 20 , orderNum = "14")
    private String robotGrossProfit;
    @Excel(name = "同比" , width = 20 , orderNum = "15")
    private String yearOnYear;
    @Excel(name = "环比" , width = 20 , orderNum = "16")
    private String monthOnMonth;
    @Excel(name = "毛利率" , width = 20 , orderNum = "17")
    private String grossProfitMargin;
    @Excel(name = "净利率" , width = 20 , orderNum = "18")
    private String netInterestRate;


    private Long type;

    private Long spotId;

    private String startTime;

    private String endTime;

    private String sort;


}
