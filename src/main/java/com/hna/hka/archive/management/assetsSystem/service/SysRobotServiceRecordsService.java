package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotServiceRecords;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service
 * @ClassName: SysRobotServiceRecordsService
 * @Author: 郭凯
 * @Description: 维修记录业务层（接口）
 * @Date: 2021/6/26 19:29
 * @Version: 1.0
 */
public interface SysRobotServiceRecordsService {

    PageDataResult getRobotServiceRecordsList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    int addRobotServiceRecords(SysRobotServiceRecords sysRobotServiceRecords);

}
