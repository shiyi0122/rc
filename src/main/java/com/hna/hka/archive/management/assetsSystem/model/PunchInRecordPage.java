package com.hna.hka.archive.management.assetsSystem.model;

import com.hna.hka.archive.management.system.util.PageDataResult;
import lombok.Data;

@Data
public class PunchInRecordPage extends PageDataResult {

    //统计时间
    private String dates;

    //统计出勤人次
    private int ountAttendancec;

    //统计出勤率
    private Double tatisticalAttendances;





}
