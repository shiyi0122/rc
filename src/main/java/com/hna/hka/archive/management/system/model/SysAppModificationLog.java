package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysAppModificationLog {
    private Long modificationLogId;

    private String modificationLogLoginName;

    private String modificationLogRobotCode;

    private String modificationLogFront;

    private String modificationLogAfter;

    private String createDate;

    private String updateDate;

}