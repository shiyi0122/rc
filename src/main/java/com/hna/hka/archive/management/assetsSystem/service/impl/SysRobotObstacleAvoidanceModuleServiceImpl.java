package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.dao.SysRobotObstacleAvoidanceModuleMapper;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotObstacleAvoidanceModule;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotObstacleAvoidanceModuleService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service.impl
 * @ClassName: SysRobotObstacleAvoidanceModuleServiceImpl
 * @Author: 郭凯
 * @Description: 避障模块管理业务层（实现）
 * @Date: 2021/5/28 9:52
 * @Version: 1.0
 */
@Service
public class SysRobotObstacleAvoidanceModuleServiceImpl implements SysRobotObstacleAvoidanceModuleService {

    @Autowired
    private SysRobotObstacleAvoidanceModuleMapper robotObstacleAvoidanceModuleMapper;


    /**
     * @Method getRobotObstacleAvoidanceModuleList
     * @Author 郭凯
     * @Version  1.0
     * @Description 避障模块管理列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/5/28 9:57
     */
    @Override
    public PageDataResult getRobotObstacleAvoidanceModuleList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotObstacleAvoidanceModule> robotObstacleAvoidanceModuleList = robotObstacleAvoidanceModuleMapper.getRobotObstacleAvoidanceModuleList(search);
        if (robotObstacleAvoidanceModuleList.size() > 0){
            PageInfo<SysRobotObstacleAvoidanceModule> pageInfo = new PageInfo<>(robotObstacleAvoidanceModuleList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method addRobotObstacleAvoidanceModule
     * @Author 郭凯
     * @Version  1.0
     * @Description 新增避障模块信息
     * @Return int
     * @Date 2021/5/28 11:07
     */
    @Override
    public int addRobotObstacleAvoidanceModule(SysRobotObstacleAvoidanceModule robotObstacleAvoidanceModule) {
        robotObstacleAvoidanceModule.setObstacleAvoidanceModularId(IdUtils.getSeqId());
        robotObstacleAvoidanceModule.setCreateDate(DateUtil.currentDateTime());
        robotObstacleAvoidanceModule.setUpdateDate(DateUtil.currentDateTime());
        return robotObstacleAvoidanceModuleMapper.insertSelective(robotObstacleAvoidanceModule);
    }

    /**
     * @Method editRobotObstacleAvoidanceModule
     * @Author 郭凯
     * @Version  1.0
     * @Description 避障模块信息编辑
     * @Return int
     * @Date 2021/5/28 11:29
     */
    @Override
    public int editRobotObstacleAvoidanceModule(SysRobotObstacleAvoidanceModule robotObstacleAvoidanceModule) {
        robotObstacleAvoidanceModule.setUpdateDate(DateUtil.currentDateTime());
        return robotObstacleAvoidanceModuleMapper.updateByPrimaryKeySelective(robotObstacleAvoidanceModule);
    }

    /**
     * @Method delRobotObstacleAvoidanceModule
     * @Author 郭凯
     * @Version  1.0
     * @Description 删除避障模块信息
     * @Return int
     * @Date 2021/5/28 13:19
     */
    @Override
    public int delRobotObstacleAvoidanceModule(Long obstacleAvoidanceModularId) {
        return robotObstacleAvoidanceModuleMapper.deleteByPrimaryKey(obstacleAvoidanceModularId);
    }

    /**
     * @Method getRobotObstacleAvoidanceModuleExcel
     * @Author 郭凯
     * @Version  1.0
     * @Description 避障模块信息Excel下载数据查询
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.SysRobotObstacleAvoidanceModule>
     * @Date 2021/5/28 13:27
     */
    @Override
    public List<SysRobotObstacleAvoidanceModule> getRobotObstacleAvoidanceModuleExcel(Map<String, Object> search) {
        List<SysRobotObstacleAvoidanceModule> robotObstacleAvoidanceModuleList = robotObstacleAvoidanceModuleMapper.getRobotObstacleAvoidanceModuleList(search);
        return robotObstacleAvoidanceModuleList;
    }
}
