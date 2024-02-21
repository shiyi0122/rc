package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.controller.SysRobotVersionController;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

public interface SysRobotVersionService {
    List<SysRobotVersionController> getRobotVersionList(String upgradeModule);

    PageDataResult getRobotBelarcAdvisorList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    PageDataResult getRobotVersionsAll(Map<String, Object> search, Integer pageNum, Integer pageSize);

}
