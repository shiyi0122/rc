package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.dao.SysRobotBelarcAdvisorMapper;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotBelarcAdvisor;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotBelarcAdvisorService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service.impl
 * @ClassName: SysRobotBelarcAdvisorServiceImpl
 * @Author: 郭凯
 * @Description: 机器人软硬件管理业务层（实现）
 * @Date: 2021/5/27 12:42
 * @Version: 1.0
 */
@Service
@Transactional
public class SysRobotBelarcAdvisorServiceImpl implements SysRobotBelarcAdvisorService {

    @Autowired
    private SysRobotBelarcAdvisorMapper sysRobotBelarcAdvisorMapper;



    /**
     * @Method getRobotBelarcAdvisorList
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人软硬件管理列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/5/27 13:10
     */
    @Override
    public PageDataResult getRobotBelarcAdvisorList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotBelarcAdvisor> robotBelarcAdvisorList = sysRobotBelarcAdvisorMapper.selectBySearch(search);
        for (SysRobotBelarcAdvisor robotBelarcAdvisor:robotBelarcAdvisorList){
            for (int i=1;i<=3;i++){
                if (i==2){
                    SysRobotBelarcAdvisor robotVersion = sysRobotBelarcAdvisorMapper.getRobotVersion(robotBelarcAdvisor.getRobotCode(), "2");
                    if (ToolUtil.isNotEmpty(robotVersion)){
                        robotBelarcAdvisor.setMasterControlFirmwareVersion(robotVersion.getUpgradedVersion());
                        robotBelarcAdvisor.setUpdateDate(robotVersion.getUpdateDate());
                    }
                }else if (i==3){
                    SysRobotBelarcAdvisor robotVersion = sysRobotBelarcAdvisorMapper.getRobotVersion(robotBelarcAdvisor.getRobotCode(), "3");
                    if (ToolUtil.isNotEmpty(robotVersion)) {
                        robotBelarcAdvisor.setRangingModularVersion(robotVersion.getUpgradedVersion());
                        robotBelarcAdvisor.setUpdateDate(robotVersion.getUpdateDate());
                    }
                }
            }
        }
        if (robotBelarcAdvisorList.size() > 0){
            PageInfo<SysRobotBelarcAdvisor> pageInfo = new PageInfo<>(robotBelarcAdvisorList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method getRobotBelarcAdvisorByRobotId
     * @Author 郭凯
     * @Version  1.0
     * @Description 根据机器人ID查询机器人信息是否存在
     * @Return com.hna.hka.archive.management.assetsSystem.model.SysRobotBelarcAdvisor
     * @Date 2021/5/27 16:10
     */
    @Override
    public SysRobotBelarcAdvisor getRobotBelarcAdvisorByRobotId(Long robotId) {
        return sysRobotBelarcAdvisorMapper.getRobotBelarcAdvisorByRobotId(robotId);
    }

    /**
     * @Method updateRobotBelarcAdvisor
     * @Author 郭凯
     * @Version  1.0
     * @Description 编辑机器人软硬件信息
     * @Return int
     * @Date 2021/5/27 16:17
     */
    @Override
    public int updateRobotBelarcAdvisor(SysRobotBelarcAdvisor sysRobotBelarcAdvisor) {
        sysRobotBelarcAdvisor.setUpdateDate(DateUtil.currentDateTime());
        return sysRobotBelarcAdvisorMapper.updateByPrimaryKeySelective(sysRobotBelarcAdvisor);
    }

    /**
     * @Method addRobotBelarcAdvisor
     * @Author 郭凯
     * @Version  1.0
     * @Description 新增机器人软硬件信息
     * @Return int
     * @Date 2021/5/27 16:20
     */
    @Override
    public int addRobotBelarcAdvisor(SysRobotBelarcAdvisor sysRobotBelarcAdvisor) {
        sysRobotBelarcAdvisor.setRobotBelarcAdvisorId(IdUtils.getSeqId());
        sysRobotBelarcAdvisor.setCreateDate(DateUtil.currentDateTime());
        sysRobotBelarcAdvisor.setUpdateDate(DateUtil.currentDateTime());
        return sysRobotBelarcAdvisorMapper.insertSelective(sysRobotBelarcAdvisor);
    }

    /**
     * @Method delRobotBelarcAdvisor
     * @Author 郭凯
     * @Version  1.0
     * @Description 删除机器人软硬件信息
     * @Return int
     * @Date 2021/5/27 16:29
     */
    @Override
    public int delRobotBelarcAdvisor(Long robotBelarcAdvisorId) {
        return sysRobotBelarcAdvisorMapper.deleteByPrimaryKey(robotBelarcAdvisorId);
    }

    /**
     * @Method getRobotBelarcAdvisorExcel
     * @Author 郭凯
     * @Version  1.0
     * @Description 导出机器人软硬件信息数据查询
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.SysRobotBelarcAdvisor>
     * @Date 2021/5/27 17:45
     */
    @Override
    public List<SysRobotBelarcAdvisor> getRobotBelarcAdvisorExcel(Map<String, Object> search) {
        List<SysRobotBelarcAdvisor> robotBelarcAdvisorList = sysRobotBelarcAdvisorMapper.getRobotBelarcAdvisorList(search);
        return robotBelarcAdvisorList;
    }

    /**
     * @Method getRobotBelarcAdvisorByRobotCode
     * @Author 郭凯
     * @Version  1.0
     * @Description 根据机器人编号查询软硬件信息
     * @Return com.hna.hka.archive.management.assetsSystem.model.SysRobotBelarcAdvisor
     * @Date 2021/5/27 18:12
     */
    @Override
    public SysRobotBelarcAdvisor getRobotBelarcAdvisorByRobotCode(String robotCode) {
        return sysRobotBelarcAdvisorMapper.getRobotBelarcAdvisorByRobotCode(robotCode);
    }

    /**
     * @Method getRobotSoftwareAndHardwareInformation
     * @Author 郭凯
     * @Version  1.0
     * @Description 根据机器人编号查询软硬件信息
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.SysRobotBelarcAdvisor>
     * @Date 2021/6/10 13:31
     */
    @Override
    public SysRobotBelarcAdvisor getRobotSoftwareAndHardwareInformation(Map<String, Object> search) {
        SysRobotBelarcAdvisor robotBelarcAdvisorList = sysRobotBelarcAdvisorMapper.getRobotSoftwareAndHardwareInformation(search);
        return ToolUtil.isEmpty(robotBelarcAdvisorList) ? new SysRobotBelarcAdvisor() :robotBelarcAdvisorList;
    }

    /**
     * @Method getRobotSoftwareAndHardwareByCode
     * @Author 郭凯
     * @Version  1.0
     * @Description 管理者APP根据机器人ID查询软硬件信息
     * @Return com.hna.hka.archive.management.assetsSystem.model.SysRobotBelarcAdvisor
     * @Date 2021/7/6 9:35
     */
    @Override
    public SysRobotBelarcAdvisor getRobotSoftwareAndHardwareByCode(Map<String, Object> search) {
        SysRobotBelarcAdvisor robotBelarcAdvisorList = sysRobotBelarcAdvisorMapper.getRobotSoftwareAndHardwareByCode(search);
        return ToolUtil.isEmpty(robotBelarcAdvisorList) ? new SysRobotBelarcAdvisor() :robotBelarcAdvisorList;
    }
}
