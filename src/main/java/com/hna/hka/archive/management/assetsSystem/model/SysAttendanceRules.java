package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

@Data
public class SysAttendanceRules {
    private Long attendanceRulesId;

    private Long scenicSpotId;

    private String attendanceRulesName;

    private String workingHoursOnWeekdays;

    private String workingHoursOnHolidays;

    private String createDate;

    private String updateDate;

    //景区名称
    private String scenicSpotName;

}