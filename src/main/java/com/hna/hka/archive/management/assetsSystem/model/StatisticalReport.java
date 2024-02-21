package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

@Data
public class StatisticalReport {

    private String scenicSpotName;

    //统计时间
    private String dates;

    //统计出勤人次
    private int ountAttendancec;

    //统计出勤率
    private Double tatisticalAttendances;
}
