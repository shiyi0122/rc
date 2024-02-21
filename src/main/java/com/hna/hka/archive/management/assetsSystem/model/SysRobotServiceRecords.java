package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

@Data
public class SysRobotServiceRecords {
    private Long serviceRecordsId;

    private Long scenicSpotId;

    private String serviceRecordsCode;

    private String serviceRecordsModel;

    private String errorRecordsModel;

    private String errorRecordsName;

    private String serviceRecordsResult;

    private String serviceRecordsDetails;

    private String serviceRecordsPersonnel;

    private String serviceRecordsTel;

    private String serviceRecordsLevel;

    private String serviceRecordsServiceDate;

    private String serviceRecordsSendDate;

    private String serviceRecordsRemark;

    private String serviceRecordsDelete;

    private String createTime;

    private String updateTime;

    private String scenicSpotName;

    private String serviceRecordsPersonnelName;
    //故障ID
    private Long errorRecordsId;

    private String robotModel;

    private String faultStatus;

}