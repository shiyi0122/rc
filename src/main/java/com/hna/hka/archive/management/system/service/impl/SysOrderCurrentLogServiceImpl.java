package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysOrderCurrentLogMapper;
import com.hna.hka.archive.management.system.model.SysOrderCurrentLog;
import com.hna.hka.archive.management.system.service.SysOrderCurrentLogService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/12/21 18:07
 */
@Service
public class SysOrderCurrentLogServiceImpl implements SysOrderCurrentLogService {

    @Autowired
    SysOrderCurrentLogMapper sysOrderCurrentLogMapper;

    @Override
    public int add(SysOrderCurrentLog sysOrderCurrentLog) {

        int i = sysOrderCurrentLogMapper.add(sysOrderCurrentLog);
        return i;
    }

    /**
     * 列表查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysOrderCurrentLogList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageNum, pageSize);
        List<SysOrderCurrentLog> list = sysOrderCurrentLogMapper.getSysOrderCurrentLogList(search);

        if (list.size() > 0) {
            PageInfo<SysOrderCurrentLog> pageInfo = new PageInfo<>(list);
            pageDataResult.setList(list);
            pageDataResult.setTotals((int) pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;
    }
}
