package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysRobotVersion {
    private Long robotVersionId;

    private Long robotId;

    private String robotVersionNumber;

    private String createDate;

    private String updateDate;

}