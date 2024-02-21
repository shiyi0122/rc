package com.hna.hka.archive.management.appSystem.model;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/6/14 17:45
 */
@Data
public class SysRobotInspectionRecordLog {

    private Long id ;

    private Long standardId;

    private Long  standardDetailId;

    private String createTime ;

    private String updateTime;
}
