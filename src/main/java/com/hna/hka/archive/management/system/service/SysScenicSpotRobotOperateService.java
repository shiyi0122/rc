package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/2/11 15:28
 */
public interface SysScenicSpotRobotOperateService {

    PageDataResult getSpotRobotOperateList(Integer pageNum, Integer pageSize , Map<String, Object> search);

}
