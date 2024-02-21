package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

@Data
public class AttendanceRecordUtil {

    private String dateType;

    private String employeeAttendance;
    private String type;

    private String employeeType;

    private Long scenicSpotId;

    private Long userId;

    private String startDate;

    private String endDate;
}
