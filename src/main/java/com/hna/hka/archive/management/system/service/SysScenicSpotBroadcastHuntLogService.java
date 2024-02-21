package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcastHuntLog;
import com.hna.hka.archive.management.system.util.PageDataResult;

/**
 * @Author zhang
 * @Date 2022/3/11 17:19
 */
public interface SysScenicSpotBroadcastHuntLogService {
    PageDataResult getScenicSpotBroadcastHuntLogList(Integer pageNum, Integer pageSize, SysScenicSpotBroadcastHuntLog sysScenicSpotBroadcastHuntLog);

}
