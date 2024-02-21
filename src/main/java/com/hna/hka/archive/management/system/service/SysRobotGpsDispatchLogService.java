package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/30 13:24
 */

public interface SysRobotGpsDispatchLogService {


    PageDataResult getRobotGpsDispatchList(Integer pageNum, Integer pageSize, Map<String, Object> search);

}
