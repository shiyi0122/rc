package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotObstacleAvoidanceModule;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service
 * @ClassName: SysRobotObstacleAvoidanceModuleService
 * @Author: 郭凯
 * @Description: 避障模块管理业务层（接口）
 * @Date: 2021/5/28 9:52
 * @Version: 1.0
 */
public interface SysRobotObstacleAvoidanceModuleService {

    PageDataResult getRobotObstacleAvoidanceModuleList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    int addRobotObstacleAvoidanceModule(SysRobotObstacleAvoidanceModule robotObstacleAvoidanceModule);

    int editRobotObstacleAvoidanceModule(SysRobotObstacleAvoidanceModule robotObstacleAvoidanceModule);

    int delRobotObstacleAvoidanceModule(Long obstacleAvoidanceModularId);

    List<SysRobotObstacleAvoidanceModule> getRobotObstacleAvoidanceModuleExcel(Map<String, Object> search);
}
