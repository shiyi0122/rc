package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysRobotKeyStartLog;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/6/14 10:24
 */
public interface RobotKeyStartLogService {
    PageDataResult getRobotKeyStartList(Integer pageNum, Integer pageSize, Map<String, Object> search);


    List<SysRobotKeyStartLog> uploadExcelKeyStartLog(Map<String, Object> search);

}
