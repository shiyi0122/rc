package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.dao.SysRobotSoftwareVersionMapper;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotSoftwareVersion;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotSoftwareVersionService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service.impl
 * @ClassName: SysRobotSoftwareVersionServiceImpl
 * @Author: 郭凯
 * @Description: 机器人升级管理业务层（实现）
 * @Date: 2021/6/26 20:14
 * @Version: 1.0
 */
@Service
@Transactional
public class SysRobotSoftwareVersionServiceImpl implements SysRobotSoftwareVersionService {

    @Autowired
    private SysRobotSoftwareVersionMapper sysRobotSoftwareVersionMapper;

    /**
     * @Method getRobotSoftwareVersionList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询机器人升级列表
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/6/26 20:17
     */
    @Override
    public PageDataResult getRobotSoftwareVersionList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotSoftwareVersion> robotSoftwareVersionList =  sysRobotSoftwareVersionMapper.getRobotSoftwareVersionList(search);
        if (robotSoftwareVersionList.size() > 0){
            PageInfo<SysRobotSoftwareVersion> pageInfo = new PageInfo<>(robotSoftwareVersionList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method getRobotSoftwareVersionExcel
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询导出Excel表数据
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.SysRobotSoftwareVersion>
     * @Date 2021/6/27 9:45
     */
    @Override
    public List<SysRobotSoftwareVersion> getRobotSoftwareVersionExcel(Map<String, Object> search) {
        List<SysRobotSoftwareVersion> robotSoftwareVersionList =  sysRobotSoftwareVersionMapper.getRobotSoftwareVersionList(search);
        for (SysRobotSoftwareVersion robotSoftwareVersion : robotSoftwareVersionList){
            if ("1".equals(robotSoftwareVersion.getUpgradeModule())){
                robotSoftwareVersion.setUpgradeModuleName("APP端");
            }else if ("2".equals(robotSoftwareVersion.getUpgradeModule())){
                robotSoftwareVersion.setUpgradeModuleName("主控");
            }else if ("3".equals(robotSoftwareVersion.getUpgradeModule())){
                robotSoftwareVersion.setUpgradeModuleName("超声");
            }
        }
        return robotSoftwareVersionList;
    }
}
