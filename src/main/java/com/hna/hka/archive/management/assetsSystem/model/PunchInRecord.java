package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

@Data
public class PunchInRecord {

    private String date;

    private String scenicSpotName;

    private String employeeType;

    private String userName;

    private int daysOfAttendance;

    private int shijiAttendanceDays;

    private int lateDays;

    private int leaveEarly;

    private Double attendance;


}
