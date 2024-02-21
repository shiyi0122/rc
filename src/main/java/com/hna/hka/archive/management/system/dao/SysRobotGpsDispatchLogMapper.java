package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRobotGpsDispatchLog;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/30 13:25
 */
public interface SysRobotGpsDispatchLogMapper {


    List<SysRobotGpsDispatchLog> getRobotGpsDispatchList(Map<String, Object> search);


}
