package com.hna.hka.archive.management.appSystem.dao;

import com.hna.hka.archive.management.appSystem.model.SysRobotErrorRecordsApprovalLog;

/**
 * @Author zhang
 * @Date 2023/6/28 15:09
 */
public interface SysRobotErrorRecordsApprovalLogMapper {


    int insertSelective(SysRobotErrorRecordsApprovalLog sysRobotErrorRecordsApprovalLog);


    SysRobotErrorRecordsApprovalLog getErrorRecordsIdAndUserIdByOne(String sysAppUserId, String errorRecordsId);

}
