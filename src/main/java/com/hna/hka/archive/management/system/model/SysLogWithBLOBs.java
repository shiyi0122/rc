package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysLogWithBLOBs extends SysLog {
    private String logCoordinateOuterring;

    private String logCoordinateOuterringBaiDu;
}