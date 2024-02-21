package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotBelarcAdvisor;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotObstacleAvoidanceModule;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotObstacleAvoidanceModuleService;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.controller
 * @ClassName: RobotObstacleAvoidanceModuleController
 * @Author: 郭凯
 * @Description: 避障模块管理控制层
 * @Date: 2021/5/28 9:49
 * @Version: 1.0
 */
@RequestMapping("/system/robotObstacleAvoidanceModule")
@Controller
public class RobotObstacleAvoidanceModuleController extends PublicUtil {

    @Autowired
    private SysRobotObstacleAvoidanceModuleService sysRobotObstacleAvoidanceModuleService;

    /**
     * @Method getRobotObstacleAvoidanceModuleList
     * @Author 郭凯
     * @Version  1.0
     * @Description 避障模块管理列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/5/28 9:55
     */
    @RequestMapping("/getRobotObstacleAvoidanceModuleList")
    @ResponseBody
    public PageDataResult getRobotObstacleAvoidanceModuleList(@RequestParam("pageNum") Integer pageNum,
                                                           @RequestParam("pageSize") Integer pageSize, SysRobotObstacleAvoidanceModule robotObstacleAvoidanceModule) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("obstacleAvoidanceId",robotObstacleAvoidanceModule.getObstacleAvoidanceId());
            pageDataResult = sysRobotObstacleAvoidanceModuleService.getRobotObstacleAvoidanceModuleList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("避障模块管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Method addRobotObstacleAvoidanceModule
     * @Author 郭凯
     * @Version  1.0
     * @Description 新增避障模块信息
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/5/28 11:05
     */
    @RequestMapping("/addRobotObstacleAvoidanceModule")
    @ResponseBody
    public ReturnModel addRobotObstacleAvoidanceModule(SysRobotObstacleAvoidanceModule robotObstacleAvoidanceModule){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotObstacleAvoidanceModuleService.addRobotObstacleAvoidanceModule(robotObstacleAvoidanceModule);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("避障模块信息新增成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("避障模块信息新增失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("addRobotObstacleAvoidanceModule",e);
            returnModel.setData("");
            returnModel.setMsg("避障模块信息新增失败，请联系管理员");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method editRobotObstacleAvoidanceModule
     * @Author 郭凯
     * @Version  1.0
     * @Description 避障模块信息编辑
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/5/28 11:28
     */
    @RequestMapping("/editRobotObstacleAvoidanceModule")
    @ResponseBody
    public ReturnModel editRobotObstacleAvoidanceModule(SysRobotObstacleAvoidanceModule robotObstacleAvoidanceModule){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotObstacleAvoidanceModuleService.editRobotObstacleAvoidanceModule(robotObstacleAvoidanceModule);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("避障模块信息编辑成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("避障模块信息编辑失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("editRobotObstacleAvoidanceModule",e);
            returnModel.setData("");
            returnModel.setMsg("避障模块信息编辑失败，请联系管理员");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method delRobotObstacleAvoidanceModule
     * @Author 郭凯
     * @Version  1.0
     * @Description 删除避障模块信息
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/5/28 13:18
     */
    @RequestMapping("/delRobotObstacleAvoidanceModule")
    @ResponseBody
    public ReturnModel delRobotObstacleAvoidanceModule(Long obstacleAvoidanceModularId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotObstacleAvoidanceModuleService.delRobotObstacleAvoidanceModule(obstacleAvoidanceModularId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("避障模块信息删除成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("避障模块信息删除失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delRobotObstacleAvoidanceModule",e);
            returnModel.setData("");
            returnModel.setMsg("避障模块信息删除失败，请联系管理员");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method uploadExcelRobotObstacleAvoidanceModule
     * @Author 郭凯
     * @Version  1.0
     * @Description 避障模块信息Excel下载
     * @Return void
     * @Date 2021/5/28 13:27
     */
    @RequestMapping(value = "/uploadExcelRobotObstacleAvoidanceModule")
    public void  uploadExcelRobotObstacleAvoidanceModule(HttpServletResponse response, SysRobotObstacleAvoidanceModule robotObstacleAvoidanceModule) throws Exception {
        List<SysRobotObstacleAvoidanceModule> robotObstacleAvoidanceModuleExcelList = null;
        Map<String,Object> search = new HashMap<>();
        search.put("obstacleAvoidanceId",robotObstacleAvoidanceModule.getObstacleAvoidanceId());
        robotObstacleAvoidanceModuleExcelList = sysRobotObstacleAvoidanceModuleService.getRobotObstacleAvoidanceModuleExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("避障模块信息", "避障模块信息", SysRobotObstacleAvoidanceModule.class, robotObstacleAvoidanceModuleExcelList),"避障模块信息"+ dateTime,response);
    }

}
