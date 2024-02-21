package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysRobotDownLoadLogMapper;
import com.hna.hka.archive.management.system.model.SysLog;
import com.hna.hka.archive.management.system.service.SysRobotDownloadLogServer;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/2/25 9:33
 */
@Service
@Transactional
public class SysRobotDownloadLogServerImpl implements SysRobotDownloadLogServer {

    @Autowired
    SysRobotDownLoadLogMapper sysRobotDownLoadMapper;

    @Override
    public PageDataResult getSysRobotDownloadLogList(Integer pageNum, Integer pageSize, Map<String, String> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysLog> sysLogList = sysRobotDownLoadMapper.getSysRobotDownloadLogList(search);
        if(sysLogList.size() != 0){
            PageInfo<SysLog> pageInfo = new PageInfo<>(sysLogList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;

    }
}
