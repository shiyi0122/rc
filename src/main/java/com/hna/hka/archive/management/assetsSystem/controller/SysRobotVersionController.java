package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotBelarcAdvisor;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotBelarcAdvisorService;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotVersionService;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机器人版本信息
 */

@Controller
@RequestMapping("/system/robotVersion")
public class SysRobotVersionController extends PublicUtil {

    @Autowired
    private SysRobotVersionService sysRobotVersionService;

    @Autowired
    private SysRobotBelarcAdvisorService robotBelarcAdvisorService;

    @RequestMapping("/getRobotVersionList")
    @ResponseBody
    public Map<String,Object> getRobotVersionList(){
        Map<String,Object> map=new HashMap<>();
        for (int i=0;i<2;i++){
            if (i==0){
                List<SysRobotVersionController> robotVersionList = sysRobotVersionService.getRobotVersionList("2");
                map.put("rangingModularVersion",robotVersionList);
            }else {
                List<SysRobotVersionController> robotVersionList = sysRobotVersionService.getRobotVersionList("3");
                map.put("masterControlFirmwareVersion",robotVersionList);
            }
        }
        map.put("code",1);
        return map;
    }

    @RequestMapping("/getRobotVersions")
    @ResponseBody
    public PageDataResult getRobotVersions(@RequestParam("pageNum") Integer pageNum,
                                          @RequestParam("pageSize") Integer pageSize, SysRobotBelarcAdvisor sysRobotBelarcAdvisor){
        PageDataResult pageDataResult = new PageDataResult();
        SysUsers SysUsers = this.getSysUser();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("userId",SysUsers.getUserId().toString());
            search.put("scenicSpotId",sysRobotBelarcAdvisor.getScenicSpotId());
            search.put("robotCode",sysRobotBelarcAdvisor.getRobotCode());
            search.put("robotCodeSim",sysRobotBelarcAdvisor.getRobotCodeSim());
            search.put("masterControlFirmwareVersion",sysRobotBelarcAdvisor.getMasterControlFirmwareVersion());
            search.put("rangingModularVersion",sysRobotBelarcAdvisor.getRangingModularVersion());
            if(sysRobotBelarcAdvisor.getMasterControlFirmwareVersion()== "" && sysRobotBelarcAdvisor.getRobotCode()==""&&sysRobotBelarcAdvisor.getRangingModularVersion()==""){
                pageDataResult = robotBelarcAdvisorService.getRobotBelarcAdvisorList(pageNum,pageSize,search);
            }else {
                pageDataResult = sysRobotVersionService.getRobotBelarcAdvisorList(pageNum,pageSize,search);
            }
        }catch (Exception e){
            logger.info("机器人软硬件管理列表查询失败",e);
        }
        return pageDataResult;
    }


    @RequestMapping("/getRobotVersionsAll")
    @ResponseBody
    public PageDataResult getRobotVersionsAll(@RequestParam("pageNum") Integer pageNum,
                                           @RequestParam("pageSize") Integer pageSize, SysRobotBelarcAdvisor sysRobotBelarcAdvisor){
        PageDataResult pageDataResult = new PageDataResult();
        SysUsers SysUsers = this.getSysUser();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("userId",SysUsers.getUserId().toString());
            search.put("scenicSpotId",sysRobotBelarcAdvisor.getScenicSpotId());
            search.put("robotCode",sysRobotBelarcAdvisor.getRobotCode());
//            search.put("robotCodeSim",sysRobotBelarcAdvisor.getRobotCodeSim());
            search.put("masterControlFirmwareVersion",sysRobotBelarcAdvisor.getMasterControlFirmwareVersion());
            search.put("rangingModularVersion",sysRobotBelarcAdvisor.getRangingModularVersion());
            pageDataResult = sysRobotVersionService.getRobotVersionsAll(search,pageNum,pageSize);


        }catch (Exception e){
            logger.info("机器人软硬件管理列表查询失败",e);
        }
        return pageDataResult;
    }





}
