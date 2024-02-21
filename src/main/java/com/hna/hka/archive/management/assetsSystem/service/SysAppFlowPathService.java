package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.SysAppFlowPath;
import com.hna.hka.archive.management.assetsSystem.model.SysAppFlowPathDetails;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/6/27 16:22
 */
public interface SysAppFlowPathService {

    PageDataResult getSysAppFlowPathList(Integer pageNum, Integer pageSize, Map<String, Object> search);


    int addSysAppFlowPath(SysAppFlowPath sysAppFlowPath);


    int editSysAppFlowPath(SysAppFlowPath sysAppFlowPath);

    int delSysAppFlowPath(Long id);

    List<SysAppFlowPath> getSysAppFlowPathDrop(String scenicSpotId);


    List<SysAppFlowPathDetails> getAppFlowPathIdByPeople(Long id);

}
