package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

@Data
public class FixedAssets {

    private String scenicSpotId;

    private String scenicSpotName;

    //时间
    private String date;

    //周期
    private String cycle;

    //净收益
    private double netProfit;

    //回报率
    private double repay;



}
