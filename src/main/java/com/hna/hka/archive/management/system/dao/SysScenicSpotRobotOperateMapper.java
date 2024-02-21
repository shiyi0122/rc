package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotRobotOperate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/2/11 15:32
 */
public interface SysScenicSpotRobotOperateMapper {
    List<SysScenicSpotRobotOperate> getSpotRobotOperateList(Map<String, Object> search);

}
