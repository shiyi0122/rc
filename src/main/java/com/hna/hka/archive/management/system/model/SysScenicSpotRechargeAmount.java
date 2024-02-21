package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysScenicSpotRechargeAmount {
    private Long rechargeId;

    private Long rechargeScenicSpotId;

    private String rechargeScenicSpotName;

    private String rechargeAmount;

    private String rechargeDiscount;

    private String createDate;

    private String updateDate;

    private String rechargeRule;

}