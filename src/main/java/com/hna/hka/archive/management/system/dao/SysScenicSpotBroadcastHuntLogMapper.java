package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotBinding;
import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcastHuntLog;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/3/11 17:21
 */
public interface SysScenicSpotBroadcastHuntLogMapper {

    List<SysScenicSpotBroadcastHuntLog> getScenicSpotBroadcastHuntLogList(SysScenicSpotBroadcastHuntLog sysScenicSpotBroadcastHuntLog);

}
