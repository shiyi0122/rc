package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.dao.SysRobotServiceRecordsMapper;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotServiceRecords;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotServiceRecordsService;
import com.hna.hka.archive.management.managerApp.dao.SysAppUsersMapper;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service.impl
 * @ClassName: SysRobotServiceRecordsServiceImpl
 * @Author: 郭凯
 * @Description: 维修记录业务层（实现）
 * @Date: 2021/6/26 19:30
 * @Version: 1.0
 */
@Service
@Transactional
public class SysRobotServiceRecordsServiceImpl implements SysRobotServiceRecordsService {

    @Autowired
    private SysRobotServiceRecordsMapper sysRobotServiceRecordsMapper;
    @Autowired
    private SysAppUsersMapper sysAppUsersMapper;

    /**
     * @Method getRobotServiceRecordsList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询维修记录列表
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/6/26 19:32
     */
    @Override
    public PageDataResult getRobotServiceRecordsList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotServiceRecords> sysRobotServiceRecordsList = sysRobotServiceRecordsMapper.getRobotServiceRecordsList(search);
        for (SysRobotServiceRecords sysRobotServiceRecords : sysRobotServiceRecordsList) {
            SysAppUsers sysAppUsers = sysAppUsersMapper.selectByPrimaryKey(Long.parseLong(sysRobotServiceRecords.getServiceRecordsPersonnel()));
            sysRobotServiceRecords.setServiceRecordsPersonnelName(sysAppUsers.getUserName());
        }
        if (sysRobotServiceRecordsList.size() > 0){
            PageInfo<SysRobotServiceRecords> pageInfo = new PageInfo<>(sysRobotServiceRecordsList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    @Override
    public int addRobotServiceRecords(SysRobotServiceRecords sysRobotServiceRecords) {

        sysRobotServiceRecords.setServiceRecordsId(IdUtils.getSeqId());
        sysRobotServiceRecords.setCreateTime(DateUtil.currentDateTime());
        sysRobotServiceRecords.setUpdateTime(DateUtil.currentDateTime());
        int i = sysRobotServiceRecordsMapper.insertSelective(sysRobotServiceRecords);
        return i;
    }
}
