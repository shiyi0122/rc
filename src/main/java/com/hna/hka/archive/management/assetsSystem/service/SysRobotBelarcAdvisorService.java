package com.hna.hka.archive.management.assetsSystem.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotBelarcAdvisor;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service
 * @ClassName: SysRobotBelarcAdvisorService
 * @Author: 郭凯
 * @Description: 机器人软硬件管理业务层（接口）
 * @Date: 2021/5/27 12:40
 * @Version: 1.0
 */
public interface SysRobotBelarcAdvisorService {

    PageDataResult getRobotBelarcAdvisorList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    SysRobotBelarcAdvisor getRobotBelarcAdvisorByRobotId(Long robotId);

    int updateRobotBelarcAdvisor(SysRobotBelarcAdvisor sysRobotBelarcAdvisor);

    int addRobotBelarcAdvisor(SysRobotBelarcAdvisor sysRobotBelarcAdvisor);

    int delRobotBelarcAdvisor(Long robotBelarcAdvisorId);

    List<SysRobotBelarcAdvisor> getRobotBelarcAdvisorExcel(Map<String, Object> search);

    SysRobotBelarcAdvisor getRobotBelarcAdvisorByRobotCode(String robotCode);

    SysRobotBelarcAdvisor getRobotSoftwareAndHardwareInformation(Map<String, Object> search);

    SysRobotBelarcAdvisor getRobotSoftwareAndHardwareByCode(Map<String, Object> search);
}
