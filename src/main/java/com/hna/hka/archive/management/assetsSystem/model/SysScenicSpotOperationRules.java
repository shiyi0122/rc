package com.hna.hka.archive.management.assetsSystem.model;


import lombok.Data;

@Data
public class SysScenicSpotOperationRules {
    private Long operationRulesId;

    private Long scenicSpotId;

    private String operatePeople;

    private String operateStartTime;

    private String operateEndTime;

    private String operatingTime;

    private String operatingType;

    private String robotNumber;

    private String createDate;

    private String updateDate;

    private String date;
}