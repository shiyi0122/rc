package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysRobotGpsDispatchLogMapper;
import com.hna.hka.archive.management.system.model.SysRobot;
import com.hna.hka.archive.management.system.model.SysRobotGpsDispatchLog;
import com.hna.hka.archive.management.system.service.SysRobotGpsDispatchLogService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/30 13:25
 */
@Service
public class SysRobotGpsDispatchLogServiceImpl implements SysRobotGpsDispatchLogService {

    @Autowired
    SysRobotGpsDispatchLogMapper sysRobotGpsDispatchLogMapper;

    /**
     * 列表查询
     *
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getRobotGpsDispatchList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageNum,pageSize);
        List<SysRobotGpsDispatchLog> list =  sysRobotGpsDispatchLogMapper.getRobotGpsDispatchList(search);

        if (list.size() > 0){

            PageInfo<SysRobotGpsDispatchLog> pageInfo = new PageInfo<>(list);
            pageDataResult.setList(list);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int)pageInfo.getTotal());

        }
        return pageDataResult;
    }
}
