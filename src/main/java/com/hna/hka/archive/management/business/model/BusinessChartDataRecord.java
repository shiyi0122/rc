package com.hna.hka.archive.management.business.model;

import lombok.Data;

@Data
public class BusinessChartDataRecord {

    private Long recordId;

    private Long scenicSpotId;

    private String recordReceptionDesk;

    private String recordRobotOnlineOrder;

    private String recordType;

    private String createDate;

    private String updateDate;

    private String timeName;
}
