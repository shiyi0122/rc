package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.controller.SysRobotVersionController;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotBelarcAdvisor;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotVersion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysRobotVersionsMapper {
    List<SysRobotVersionController> getRobotVersionList(String upgradeModule);

    List<SysRobotBelarcAdvisor> getRobotBelarcAdvisorList(Map<String, Object> search);


    List<SysRobotBelarcAdvisor> getVsersionList(Map<String, Object> search);

    List<SysRobotBelarcAdvisor> getRobotVersionsAll(Map<String, Object> search);

    List<SysRobotBelarcAdvisor> getSysRobotVersion(Map<String, Object> search);

    List<SysRobotBelarcAdvisor> getSysRobotVersionN(Map<String, Object> search);

    List<SysRobotBelarcAdvisor> getSysRobotVersionCode(Map<String, Object> search);

}
