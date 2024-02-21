package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.controller.SysRobotVersionController;

import com.hna.hka.archive.management.assetsSystem.dao.SysRobotVersionsMapper;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotBelarcAdvisor;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotVersion;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotVersionService;

import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysRobotVersionServiceImpl implements SysRobotVersionService {

    @Autowired
    private SysRobotVersionsMapper robotVersionMapper;

    @Override
    public PageDataResult getRobotBelarcAdvisorList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotBelarcAdvisor> robotBelarcAdvisorList = robotVersionMapper.getRobotBelarcAdvisorList(search);
        for (String key:search.keySet()){
            if (search.get("robotCode")!=""&&search.get("masterControlFirmwareVersion")==""&&search.get("rangingModularVersion")==""){
                for (SysRobotBelarcAdvisor robotBelarcAdvisor:robotBelarcAdvisorList){
                    for (int i=1;i<=3;i++){
                        if (i==2){
                            List<SysRobotBelarcAdvisor> vsersionList = robotVersionMapper.getVsersionList(search);
                            if (ToolUtil.isNotEmpty(vsersionList)){
                                for (SysRobotBelarcAdvisor version:vsersionList) {
                                    robotBelarcAdvisor.setMasterControlFirmwareVersion(version.getUpgradedVersion());
                                    robotBelarcAdvisor.setUpdateDate(version.getUpdateDate());
                                }
                            }
                        }else if (i==3){
                            List<SysRobotBelarcAdvisor> vsersionList = robotVersionMapper.getVsersionList(search);
                            if (ToolUtil.isNotEmpty(vsersionList)) {
                                for (SysRobotBelarcAdvisor version:vsersionList) {
                                    robotBelarcAdvisor.setMasterControlFirmwareVersion(version.getUpgradedVersion());
                                    robotBelarcAdvisor.setUpdateDate(version.getUpdateDate());
                                }
                            }
                        }
                    }

                }
            }/*else if (search.get("robotCode")!=null&&search.get("masterControlFirmwareVersion")!=null&&search.get("rangingModularVersion")!=null) {
                for (SysRobotBelarcAdvisor robotBelarcAdvisor:robotBelarcAdvisorList){}
                List<SysRobotBelarcAdvisor> vsersionList = robotVersionMapper.getVsersionList(robotBelarcAdvisor.getRobotCode(), "2");


            }*/
            /*for (SysRobotBelarcAdvisor robotBelarcAdvisor:robotBelarcAdvisorList){

            }*/
        }
        if (robotBelarcAdvisorList.size() > 0){
            PageInfo<SysRobotBelarcAdvisor> pageInfo = new PageInfo<>(robotBelarcAdvisorList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 搜索
     *
     * @param search
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageDataResult getRobotVersionsAll(Map<String, Object> search, Integer pageNum, Integer pageSize) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotBelarcAdvisor> list = new ArrayList<>();
        List<SysRobotBelarcAdvisor> listN = new ArrayList<>();
        if (StringUtils.isEmpty(search.get("robotCode"))){
            if(!StringUtils.isEmpty(search.get("rangingModularVersion"))){
                list = robotVersionMapper.getSysRobotVersion(search);
            }else if (!StringUtils.isEmpty(search.get("masterControlFirmwareVersion"))){
                list = robotVersionMapper.getSysRobotVersionN(search);
            }else{
                list = robotVersionMapper.getSysRobotVersion(search);
            }
        }else{
                list =  robotVersionMapper.getSysRobotVersionCode(search);
        }

        if (list.size() > 0){
            PageInfo<SysRobotBelarcAdvisor> pageInfo = new PageInfo<>(list);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }


        return pageDataResult;
    }

    @Override
    public List<SysRobotVersionController> getRobotVersionList(String upgradeModule) {

        return robotVersionMapper.getRobotVersionList(upgradeModule);
    }
}

