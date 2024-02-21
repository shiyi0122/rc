package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/4/24 17:00
 */
@Data
public class ChartReturnClass {

    private double yearOnYear;

    private double monthOnMonth;

    private String spotName;

    private Long spotId;

    private String date;

    private double utilizationRate ;




}
