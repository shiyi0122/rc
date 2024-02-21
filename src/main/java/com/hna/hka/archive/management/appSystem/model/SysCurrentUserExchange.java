package com.hna.hka.archive.management.appSystem.model;

import lombok.Data;

@Data
public class SysCurrentUserExchange {
    private Long exchangeId;

    private Long userId;

    private String userPhone;

    private String exchangeNumber;

    private Long scenicSpotId;

    private String shipmentStatus;

    private String exchangeState;

    private String exchangeName;

    private String startValidity;

    private String endValidity;

    private String createDate;

    private String updateDate;

    private String accountName;
    private String addressId;

}