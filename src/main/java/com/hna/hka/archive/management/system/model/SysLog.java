package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysLog {
    private Long logId;

    private String userName;

    private String logUserPhone;

    private String logType;

    private String logAmount;

    private String logDiscount;

    private String logReasonsRefunds;

    private Long logScenicSpotId;

    private String logScenicSpotName;

    private String createDate;

}