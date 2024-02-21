package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysScenicSpotRobotOperateMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpotRobotOperate;
import com.hna.hka.archive.management.system.service.SysScenicSpotRobotOperateService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/2/11 15:30
 * 机器人运营日志管理
 */
@Service
@Transactional
public class SysScenicSpotRobotOperateServiceImpl implements SysScenicSpotRobotOperateService {


    @Autowired
    SysScenicSpotRobotOperateMapper sysScenicSpotRobotOperateMapper;

    @Override
    public PageDataResult getSpotRobotOperateList(Integer pageNum,Integer pageSize,Map<String, Object> search) {
        PageHelper.startPage(pageNum,pageSize);
        PageDataResult pageDataResult = new PageDataResult();
        List<SysScenicSpotRobotOperate> list =  sysScenicSpotRobotOperateMapper.getSpotRobotOperateList(search);

         if (list.size()!=0){
          PageInfo<SysScenicSpotRobotOperate> sysScenicSpotRobotOperatePageInfo = new PageInfo<>(list);
          pageDataResult = new PageDataResult();
          pageDataResult.setList(sysScenicSpotRobotOperatePageInfo.getList());
          pageDataResult.setTotals((int)sysScenicSpotRobotOperatePageInfo.getTotal());

          }
        return pageDataResult;
    }
}
