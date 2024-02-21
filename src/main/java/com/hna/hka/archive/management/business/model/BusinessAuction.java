package com.hna.hka.archive.management.business.model;

import lombok.Data;

@Data
public class BusinessAuction {
    private Long id;

    private Long scenicSpotId;

    private String robotTables;

    private String stockNumber;

    private String totalPrice;

    private String unitPrice;

    private String type;

    private String bond;

    private String state;

    private String startTime;

    private String endTime;

    private String createDate;

    private String updateDate;

    private String agreement;

    /**
     * 景区名称
     */
    private String scenicSpotName;

    /**
     * 是否有效
     */
    private String effective;

}