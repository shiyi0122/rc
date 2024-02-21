package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysRobotKeyStartLogMapper;
import com.hna.hka.archive.management.system.model.SysRobotKeyStartLog;
import com.hna.hka.archive.management.system.service.RobotKeyStartLogService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/6/14 10:25
 */
@Service
public class RobotKeyStartLogServiceImpl implements RobotKeyStartLogService {

    @Autowired
    SysRobotKeyStartLogMapper robotKeyStartLogMapper;

    @Override
    public PageDataResult getRobotKeyStartList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotKeyStartLog> list = robotKeyStartLogMapper.getRobotKeyStartList(search);

        if (list.size() != 0){
            PageInfo<SysRobotKeyStartLog> pageInfo = new PageInfo<>(list);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;



    }

    @Override
    public List<SysRobotKeyStartLog> uploadExcelKeyStartLog(Map<String, Object> search) {

        List<SysRobotKeyStartLog> list = robotKeyStartLogMapper.getRobotKeyStartList(search);

        return list;
    }
}
