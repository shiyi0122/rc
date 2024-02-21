package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.business.model.BusinessScenicSpotExpand;
import com.hna.hka.archive.management.system.dao.SysRobotMapper;
import com.hna.hka.archive.management.system.dao.SysRobotUnusualTimeMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotMapper;
import com.hna.hka.archive.management.system.model.SysRobot;
import com.hna.hka.archive.management.system.model.SysRobotUnusualTime;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.service.SysRobotUnusualTimeService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.constraints.AssertFalse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * @Author zhang
 * @Date 2023/3/2 15:05
 */
@Service
public class SysRobotUnusualTimeServiceImpl implements SysRobotUnusualTimeService {

    @Autowired
    SysRobotUnusualTimeMapper sysRobotUnusualTimeMapper;
    @Autowired
    SysRobotMapper sysRobotMapper;
    @Autowired
    SysScenicSpotMapper sysScenicSpotMapper;


    /**
     * 机器人异常状态时间配置列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysRobotUnusualTimeList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageNum,pageSize);
        List<SysRobotUnusualTime> list = sysRobotUnusualTimeMapper.getSysRobotUnusualTimeList(search);

        if (list.size()>0){
            PageInfo<SysRobotUnusualTime> pageInfo = new PageInfo<>(list);

            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }

        return pageDataResult;
    }

    /**
     * 添加
     * @param sysRobotUnusualTime
     * @return
     */
    @Override
    public int addSysRobotUnusualTime(SysRobotUnusualTime sysRobotUnusualTime) {

        SysRobotUnusualTime spotIdByUnusualTime = sysRobotUnusualTimeMapper.getSpotIdByUnusualTime(sysRobotUnusualTime.getSysScenicSpotId());
        if (StringUtils.isEmpty(spotIdByUnusualTime)){
            sysRobotUnusualTime.setId(IdUtils.getSeqId());
            sysRobotUnusualTime.setCreateDate(DateUtil.currentDateTime());
            sysRobotUnusualTime.setUpdateDate(DateUtil.currentDateTime());

            int i = sysRobotUnusualTimeMapper.insert(sysRobotUnusualTime);
            return i;
        }else{
            return 2;
        }




    }

    /**
     * 修改
     * @param sysRobotUnusualTime
     * @return
     */
    @Override
    public int editSysRobotUnusualTime(SysRobotUnusualTime sysRobotUnusualTime) {

        sysRobotUnusualTime.setUpdateDate(DateUtil.currentDateTime());

        int i = sysRobotUnusualTimeMapper.update(sysRobotUnusualTime);

        return i;
    }

    /**
     * pad端输入机器人编号，获取景区监控异常时间
     * @param robotCode
     * @return
     */
    @Override
    public SysRobotUnusualTime getUnusualTime(String robotCode) {

        SysRobot robotCodeBy = sysRobotMapper.getRobotCodeBy(robotCode);

        if (!StringUtils.isEmpty(robotCodeBy.getScenicSpotId())){
           SysRobotUnusualTime sysRobotUnusualTime =  sysRobotUnusualTimeMapper.getSpotIdByUnusualTime(robotCodeBy.getScenicSpotId());
           return sysRobotUnusualTime;
        }else{
            return null;
        }


    }

    /**
     * 删除机器人异常配置信息
     * @param sysRobotUnusualTime
     * @return
     */
    @Override
    public int delSysRobotUnusualTime(SysRobotUnusualTime sysRobotUnusualTime) {

        int i = sysRobotUnusualTimeMapper.deleteByPrimaryKey(sysRobotUnusualTime.getId());
        return i;
    }

    /**
     * 获取未配置异常时间的景区
     * @return
     */
    @Override
    public List<SysScenicSpot> getUnusualSpotList() {
        List<SysScenicSpot> list  = sysRobotUnusualTimeMapper.getSysRobotUnusualTimeSpotList();

        List<SysScenicSpot> list1 = sysScenicSpotMapper.getScenicSpotUnusualList();
        //取集合结果差
        List<SysScenicSpot> scenicSpotExpandList = list1.stream().filter(item -> !list.contains(item)).collect(toList());
        return scenicSpotExpandList;


    }


}
