package com.hna.hka.archive.management.appSystem.model;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/6/28 11:11
 */
@Data
public class SysRobotErrorRecordsApprovalLog {


    private Long id;

    //故障id
    private Long  robotErrorRecordsId;
    //审批结果 1通过，2驳回
    private String approvalResults;
    //审批人id
    private String  approvedUser;
    //原因
    private String reason;

    private String  createTime;

    private String updateTime;

}
