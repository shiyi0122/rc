package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.appSystem.service.SysRobotErrorRecordsService;
import com.hna.hka.archive.management.assetsSystem.model.SysAppFlowPath;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotErrorRecords;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotErrorRepairUser;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotPartsManagement;
import com.hna.hka.archive.management.assetsSystem.service.SysAppFlowPathService;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.managerApp.service.SysAppUsersService;
import com.hna.hka.archive.management.system.model.SysScenicSpotImg;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.controller
 * @ClassName: RobotErrorRecordsController
 * @Author: 郭凯
 * @Description: 故障记录控制层
 * @Date: 2021/6/24 20:40
 * @Version: 1.0
 */
@Api(tags = "故障记录")
@RequestMapping("/system/robotErrorRecords")
@Controller
public class RobotErrorRecordsController extends PublicUtil {

    @Autowired
    private SysRobotErrorRecordsService sysRobotErrorRecordsService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysAppUsersService sysAppUsersService;

    @Autowired
    private SysAppFlowPathService sysAppFlowPathService;

    /**
     * @Method getRobotErrorRecordsList
     * @Author 郭凯
     * @Version  1.0
     * @Description 故障记录列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/6/25 11:07
     */
    @RequestMapping("/getRobotErrorRecordsList")
    @ResponseBody
    public PageDataResult getRobotErrorRecordsList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, SysRobotErrorRecords sysRobotErrorRecords){
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            search.put("robotCode",sysRobotErrorRecords.getRobotCode());
            search.put("scenicSpotId",sysRobotErrorRecords.getScenicSpotId());
            search.put("errorRecordsName",sysRobotErrorRecords.getErrorRecordsName());
            search.put("errorRecordsStatus",sysRobotErrorRecords.getErrorRecordsStatus());
            search.put("errorRecordsQuality",sysRobotErrorRecords.getErrorRecordsQuality());
            search.put("errorRecordsReceive",sysRobotErrorRecords.getErrorRecordsReceive());
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
//            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
//                if (ToolUtil.isEmpty(sysRobotErrorRecords.getRobotCode()) && ToolUtil.isEmpty(sysRobotErrorRecords.getScenicSpotId()) && ToolUtil.isEmpty(sysRobotErrorRecords.getErrorRecordsName()) && ToolUtil.isEmpty(sysRobotErrorRecords.getErrorRecordsStatus())) {
//                    startTime = DateUtil.crutDate();
//                    endTime = DateUtil.crutDate();
//                }
//            }
            search.put("startTime",startTime);
            search.put("endTime",endTime);
            pageDataResult = sysRobotErrorRecordsService.getRobotErrorRecordsList(pageNum,pageSize,search);

        }catch (Exception e){
            logger.info("getRobotErrorRecordsList",e);
        }
        return pageDataResult;
    }

    /**
     * @Method editRobotErrorRecords
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人故障派单
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/25 15:38
     */
    @RequestMapping("/editRobotErrorRecords")
    @ResponseBody
    public ReturnModel editRobotErrorRecords(SysRobotErrorRepairUser sysRobotErrorRepairUser){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotErrorRecordsService.editRobotErrorRecords(sysRobotErrorRepairUser);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("派单成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                SysAppUsers sysAppUsers = sysAppUsersService.getAppUserById(sysRobotErrorRepairUser.getUserId());
                // 个推推送消息到APP端
                String isSuccess = WeChatGtRobotAppPush.singlePushApp(sysAppUsers.getUserClientGtId(), "维修单", sysRobotErrorRepairUser.getScenicSpotName() +"，"+ sysRobotErrorRepairUser.getRobotCode() +"，"+ sysRobotErrorRepairUser.getErrorRecordsName());
                if ("1".equals(isSuccess)) {
                    returnModel.setData("");
                    returnModel.setMsg("发送成功！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                } else {
                    returnModel.setData("");
                    returnModel.setMsg("发送失败！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
            }else{
                returnModel.setData("");
                returnModel.setMsg("派单失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("editRobotErrorRecords",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method editErrorRecords
     * @Author 郭凯
     * @Version  1.0
     * @Description 编辑机器人故障信息
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/7/20 11:21
     */
    @RequestMapping("/editErrorRecords")
    @ResponseBody
    public ReturnModel editErrorRecords(SysRobotErrorRecords sysRobotErrorRecords){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotErrorRecordsService.editErrorRecords(sysRobotErrorRecords);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("机器人故障信息编辑成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("机器人故障信息编辑失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("editErrorRecords",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method accessoriesDetails
     * @Author zhang
     * @Version  1.0
     * @Description 机器人故障记录配件详情
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date
     */

    @RequestMapping("/accessoriesDetails")
    @ResponseBody
    public ReturnModel accessoriesDetails(Long errorRecordsId){

        ReturnModel returnModel = new ReturnModel();
        List sysRobotErrorPartsList = sysRobotErrorRecordsService.accessoriesDetails(errorRecordsId);
        returnModel.setData(sysRobotErrorPartsList);
        returnModel.setMsg("机器人配件查询成功！");
        returnModel.setState(Constant.STATE_SUCCESS);
        return returnModel;
    }



    /**
     * @Method editErrorRecords
     * @Author 郭凯
     * @Version  1.0
     * @Description 编辑机器人故障信息
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/7/20 11:21
     */
    @RequestMapping("/addErrorRecords")
    @ResponseBody
    public ReturnModel addErrorRecords(SysRobotErrorRecords sysRobotErrorRecords){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotErrorRecordsService.editErrorRecords(sysRobotErrorRecords);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("机器人故障信息编辑成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("机器人故障信息编辑失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("editErrorRecords",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method editErrorRecords
     * @Author 郭凯
     * @Version  1.0
     * @Description 编辑机器人故障信息
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/7/20 11:21
     */
    @RequestMapping("/delErrorRecords")
    @ResponseBody
    public ReturnModel delErrorRecords(Long errorRecordsId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotErrorRecordsService.delErrorRecords(errorRecordsId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("机器人故障信息删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("机器人故障信息删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("editErrorRecords",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }




    /**
     * 机器人故障上报
     * @param sysRobotErrorRecords
     * @return
     */
    @ApiOperation("机器人故障上报")
    @RequestMapping("/addRobotErrorRecords")
    @ResponseBody
    public ReturnModel addRobotErrorRecords(@RequestParam("file") MultipartFile file , SysRobotErrorRecords sysRobotErrorRecords){

        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotErrorRecordsService.addRobotErrorRecordFile(file,sysRobotErrorRecords);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("机器人故障信息添加成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("机器人故障信息添加失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("addErrorRecords",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }

    }


    /**
     * 机器人故障单 后台审批
     * @param
     * @param sysRobotErrorRecords
     * @return
     */
    @ApiOperation("机器人故障单后台审批")
    @RequestMapping("/editRobotErrorRecordsFlowPathId")
    @ResponseBody
    public ReturnModel editRobotErrorRecordsFlowPathId( SysRobotErrorRecords sysRobotErrorRecords){

        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotErrorRecordsService.editErrorRecords(sysRobotErrorRecords);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("机器人故障信息审批成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("机器人故障信息审批失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("addErrorRecords",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }

    }

    /**
     * 获取管理者app账户下拉选
     * @return
     */
    @RequestMapping("getAppUserDrop")
    @ResponseBody
    public Map<String,Object> getAppUserDrop(){

        Map<String,Object> data = new HashMap<>();

        List<SysAppUsers> sysAppUsers = sysAppUsersService.getSysAppUsers();


        data.put("data",sysAppUsers);
        data.put("code",1);
        return data;
    }


    /**
     * 审批类型下拉选
     * @return
     */
    @RequestMapping("getAppFlowPathDrop")
    @ResponseBody
    public Map<String,Object> getAppFlowPathDrop(){

        Map<String,Object> data = new HashMap<>();

        List<SysAppFlowPath> sysAppFlowPathDrop = sysAppFlowPathService.getSysAppFlowPathDrop("");



        data.put("data",sysAppFlowPathDrop);
        data.put("code",1);
        return data;
    }






}
