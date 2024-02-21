package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotBelarcAdvisor;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysRobotBelarcAdvisorMapper {
    int deleteByPrimaryKey(Long robotBelarcAdvisorId);

    int insert(SysRobotBelarcAdvisor record);

    int insertSelective(SysRobotBelarcAdvisor record);

    SysRobotBelarcAdvisor selectByPrimaryKey(Long robotBelarcAdvisorId);

    int updateByPrimaryKeySelective(SysRobotBelarcAdvisor record);

    int updateByPrimaryKey(SysRobotBelarcAdvisor record);

    List<SysRobotBelarcAdvisor> getRobotBelarcAdvisorList(Map<String, Object> search);

    SysRobotBelarcAdvisor getRobotBelarcAdvisorByRobotId(Long robotId);

    SysRobotBelarcAdvisor getRobotBelarcAdvisorByRobotCode(String robotCode);

    SysRobotBelarcAdvisor getRobotSoftwareAndHardwareInformation(Map<String, Object> search);

    SysRobotBelarcAdvisor getRobotSoftwareAndHardwareByCode(Map<String, Object> search);

    SysRobotBelarcAdvisor getRobotVersion(@Param("robotCode") String robotCode,@Param("upgradeModule") String upgradeModule);

    List<SysRobotBelarcAdvisor> selectBySearch(Map<String, Object> search);


}