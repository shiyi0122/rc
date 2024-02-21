package com.hna.hka.archive.management.appSystem.dao;

import com.hna.hka.archive.management.appSystem.model.AppRobotGpsDispatchLog;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/25 17:45
 */
public interface AppRobotGpsDispatchLogMapper {

    List<AppRobotGpsDispatchLog> getAppRobotGpsDispatchLogList(Map<String, String> search);

}
