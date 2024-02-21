package com.hna.hka.archive.management.assetsSystem.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotBelarcAdvisor;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotBelarcAdvisorService;
import com.hna.hka.archive.management.system.model.SysRobot;
import com.hna.hka.archive.management.system.model.SysRobotId;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.controller
 * @ClassName: RobotBelarcAdvisorController
 * @Author: 郭凯
 * @Description: 机器人软硬件管理控制层
 * @Date: 2021/5/27 11:30
 * @Version: 1.0
 */
@RequestMapping("/system/robotBelarcAdvisor")
@Controller
public class  RobotBelarcAdvisorController extends PublicUtil {

    @Autowired
    private SysRobotBelarcAdvisorService sysRobotBelarcAdvisorService;

    /**
     * @Method getRobotBelarcAdvisorList
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人软硬件管理列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/5/27 13:09
     */
    @RequestMapping("/getRobotBelarcAdvisorList")
    @ResponseBody
    public PageDataResult getRobotBelarcAdvisorList(@RequestParam("pageNum") Integer pageNum,
                                                           @RequestParam("pageSize") Integer pageSize, SysRobotBelarcAdvisor sysRobotBelarcAdvisor) {
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
            search.put("upgradeModule",sysRobotBelarcAdvisor.getChargerModel());
            pageDataResult = sysRobotBelarcAdvisorService.getRobotBelarcAdvisorList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("机器人软硬件管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Method editRobotBelarcAdvisor
     * @Author 郭凯
     * @Version  1.0
     * @Description 编辑机器人软硬件信息
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/5/27 16:09
     */
    @RequestMapping("/editRobotBelarcAdvisor")
    @ResponseBody
    public ReturnModel editRobotBelarcAdvisor(SysRobotBelarcAdvisor sysRobotBelarcAdvisor){
        ReturnModel returnModel = new ReturnModel();
        try {
            //根据机器人ID查询机器人信息是否存在
            SysRobotBelarcAdvisor robotBelarcAdvisor = sysRobotBelarcAdvisorService.getRobotBelarcAdvisorByRobotId(sysRobotBelarcAdvisor.getRobotId());
            if (ToolUtil.isNotEmpty(robotBelarcAdvisor)){
                sysRobotBelarcAdvisor.setRobotBelarcAdvisorId(robotBelarcAdvisor.getRobotBelarcAdvisorId());
                int i = sysRobotBelarcAdvisorService.updateRobotBelarcAdvisor(sysRobotBelarcAdvisor);
                if (i == 1){
                    returnModel.setData("");
                    returnModel.setMsg("机器人软硬件信息编辑成功！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                }else{
                    returnModel.setData("");
                    returnModel.setMsg("机器人软硬件信息编辑失败！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
            }else{
                int i = sysRobotBelarcAdvisorService.addRobotBelarcAdvisor(sysRobotBelarcAdvisor);
                if (i == 1){
                    returnModel.setData("");
                    returnModel.setMsg("机器人软硬件信息编辑成功！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                }else{
                    returnModel.setData("");
                    returnModel.setMsg("机器人软硬件信息编辑失败！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
            }
        }catch (Exception e){
            logger.info("editRobotBelarcAdvisor",e);
            returnModel.setData("");
            returnModel.setMsg("机器人软硬件信息编辑失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method delAdvertising
     * @Author 郭凯
     * @Version  1.0
     * @Description 删除机器人软硬件信息
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/5/27 16:27
     */
   /* @RequestMapping("/delRobotBelarcAdvisor")
    @ResponseBody
    public ReturnModel delRobotBelarcAdvisor(Long robotBelarcAdvisorId){
        ReturnModel returnModel = new ReturnModel();
        try {
            if (ToolUtil.isEmpty(robotBelarcAdvisorId)){
                returnModel.setData("");
                returnModel.setMsg("ID为空，请确认是否添加！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
            int i = sysRobotBelarcAdvisorService.delRobotBelarcAdvisor(robotBelarcAdvisorId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("机器人软硬件信息删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("机器人软硬件信息删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delRobotBelarcAdvisor",e);
            returnModel.setData("");
            returnModel.setMsg("机器人软硬件信息删除失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }*/

    /**
     * @Method uploadExcelRobotBelarcAdvisor
     * @Author 郭凯
     * @Version  1.0
     * @Description 导出机器人软硬件信息
     * @Return void
     * @Date 2021/5/27 17:28
     */
    @RequestMapping(value = "/uploadExcelRobotBelarcAdvisor")
    public void  uploadExcelRobotBelarcAdvisor(HttpServletResponse response, SysRobotBelarcAdvisor sysRobotBelarcAdvisor) throws Exception {
        List<SysRobotBelarcAdvisor> robotBelarcAdvisorExcelList = null;
        SysUsers SysUsers = this.getSysUser();
        Map<String,Object> search = new HashMap<>();
        search.put("userId",SysUsers.getUserId().toString());
        search.put("scenicSpotId",sysRobotBelarcAdvisor.getScenicSpotId());
        search.put("robotCode",sysRobotBelarcAdvisor.getRobotCode());
        search.put("robotCodeSim",sysRobotBelarcAdvisor.getRobotCodeSim());
        robotBelarcAdvisorExcelList = sysRobotBelarcAdvisorService.getRobotBelarcAdvisorExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("机器人软硬件信息", "机器人软硬件信息", SysRobotBelarcAdvisor.class, robotBelarcAdvisorExcelList),"机器人软硬件信息"+ dateTime,response);
    }

    /**
     * @Method upload
     * @Author 郭凯
     * @Version  1.0
     * @Description 批量导入机器人软硬件信息
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/5/27 18:09
     */
    @RequestMapping("/upload")
    @ResponseBody
    public ReturnModel upload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        ReturnModel returnModel = new ReturnModel();
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            List<SysRobotBelarcAdvisor> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(),SysRobotBelarcAdvisor.class, params);
            for (SysRobotBelarcAdvisor sysRobotBelarcAdvisor:result){
                //查询机器人是否存在软硬件信息
                SysRobotBelarcAdvisor robotBelarcAdvisor = sysRobotBelarcAdvisorService.getRobotBelarcAdvisorByRobotCode(sysRobotBelarcAdvisor.getRobotCode());
                if (ToolUtil.isNotEmpty(robotBelarcAdvisor.getRobotBelarcAdvisorId())){
                    sysRobotBelarcAdvisor.setRobotBelarcAdvisorId(robotBelarcAdvisor.getRobotBelarcAdvisorId());
                    sysRobotBelarcAdvisorService.updateRobotBelarcAdvisor(sysRobotBelarcAdvisor);
                }else{
                    sysRobotBelarcAdvisor.setRobotId(robotBelarcAdvisor.getRobotId());
                    sysRobotBelarcAdvisorService.addRobotBelarcAdvisor(sysRobotBelarcAdvisor);
                }
            }
            returnModel.setData("");
            returnModel.setMsg("导入成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            logger.info("upload", e);
            returnModel.setData("");
            returnModel.setMsg("导入失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }



}
