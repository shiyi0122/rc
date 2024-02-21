package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysScenicSpotBroadcastHuntLogMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpotBinding;
import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcastHuntLog;
import com.hna.hka.archive.management.system.service.SysScenicSpotBroadcastHuntLogService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/3/11 17:20
 */
@Service
public class SysScenicSpotBroadcastHuntLogServiceImpl implements SysScenicSpotBroadcastHuntLogService {

    @Autowired
    SysScenicSpotBroadcastHuntLogMapper sysScenicSpotBroadcastHuntLogMapper;

    @Override
    public PageDataResult getScenicSpotBroadcastHuntLogList(Integer pageNum, Integer pageSize, SysScenicSpotBroadcastHuntLog sysScenicSpotBroadcastHuntLog) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotBroadcastHuntLog> sysScenicSpotBroadcastHuntLogs = sysScenicSpotBroadcastHuntLogMapper.getScenicSpotBroadcastHuntLogList(sysScenicSpotBroadcastHuntLog);
        if(sysScenicSpotBroadcastHuntLogs.size() != 0){
            PageInfo<SysScenicSpotBroadcastHuntLog> pageInfo = new PageInfo<>(sysScenicSpotBroadcastHuntLogs);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;

    }
}
