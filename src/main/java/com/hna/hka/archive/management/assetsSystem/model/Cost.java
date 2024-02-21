package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

@Data
public class Cost {

    private String scenicSpotId;

    private String scenicSpotName;

    private String date;

    //总条数
    private int total;

    //流水
    private double flowingWater;

    //毛利
    private double grossProfit;

    //出厂日期
    private String dateProduction;

    //机器人成本
    private double cost;

    //实际投入成本
    private double actualCost;

    //开始时间
    private String start;

    //结束时间
    private String end;
}
