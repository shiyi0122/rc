package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRobotKeyStartLog;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/6/14 10:46
 */
public interface SysRobotKeyStartLogMapper {

    List<SysRobotKeyStartLog> getRobotKeyStartList(Map<String, Object> search);


}
