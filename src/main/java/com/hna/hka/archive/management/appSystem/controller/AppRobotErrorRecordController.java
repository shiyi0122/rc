package com.hna.hka.archive.management.appSystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.service.AppUserService;
import com.hna.hka.archive.management.appSystem.service.SysRobotErrorRecordsService;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotErrorRecords;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotErrorRepairUser;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotRepair;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotServiceRecords;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.managerApp.service.SysAppUsersService;
import com.hna.hka.archive.management.system.model.SysRobotFaule;
import com.hna.hka.archive.management.system.util.*;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.appSystem.controller
 * @ClassName: AppRobotErrorRecordController
 * @Author: 郭凯
 * @Description: 故障，损坏控制层接口
 * @Date: 2021/6/24 15:44
 * @Version: 1.0
 */
@RequestMapping("/system/appRobotErrorRecord")
@Controller
public class AppRobotErrorRecordController extends PublicUtil {

    @Autowired
    private SysRobotErrorRecordsService sysRobotErrorRecordsService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private SysAppUsersService sysAppUsersService;

    /**
     * @Method addRobotErrorRecord
     * @Author 郭凯
     * @Version  1.0
     * @Description 上报配件损坏（机器人故障）
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/24 16:31
     */
    @RequestMapping("/addRobotErrorRecord")
    @ResponseBody
    public ReturnModel addRobotErrorRecord(@RequestParam("longinTokenId") String longinTokenId,@RequestBody SysRobotErrorRecords sysRobotErrorRecords){
        ReturnModel returnModel = new ReturnModel();
        try {
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId已过期，请重新登陆!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                return returnModel;
            }
            int i = sysRobotErrorRecordsService.addRobotErrorRecord(sysRobotErrorRecords);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("上报成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setType(Constant.STATE_SUCCESS);
                Map<String,Object> search = new HashMap<>();
                search.put("scenicSpotId",sysRobotErrorRecords.getScenicSpotId());
                search.put("resCode","APPROVED_BY");
                List<SysAppUsers> appUsersList = appUserService.getAppUsersByScenicIdList(search);
                if (ToolUtil.isNotEmpty(appUsersList) && appUsersList.size() > 0){
                    for (SysAppUsers sysAppUsers : appUsersList){
                        // 个推推送消息到APP端
                        String isSuccess = WeChatGtRobotAppPush.singlePushApp(sysAppUsers.getUserClientGtId(), "故障上报", sysAppUsers.getScenicSpotName() + "," + sysRobotErrorRecords.getRobotCode() + "," + sysRobotErrorRecords.getErrorRecordsName());
                        if ("1".equals(isSuccess)) {
                            returnModel.setData("");
                            returnModel.setMsg("发送成功！");
                            returnModel.setState(Constant.STATE_SUCCESS);
                        } else {
                            returnModel.setData("");
                            returnModel.setMsg("发送失败！");
                            returnModel.setState(Constant.STATE_FAILURE);
                        }
                    }
                }
                return returnModel;
            }else if (i == 2){
                returnModel.setData("");
                returnModel.setMsg("未查询到此机器人！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("上报失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("addRobotErrorRecord",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method getRobotErrorRecordList
     * @Author 郭凯
     * @Version  1.0
     * @Description APP查询上报故障损坏列表
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/24 17:21
     */
    @RequestMapping("/getRobotErrorRecordList")
    @ResponseBody
    public String getRobotErrorRecordList(@RequestParam("longinTokenId") String longinTokenId, SysRobotErrorRecords sysRobotErrorRecords, BaseQueryVo BaseQueryVo){
        ReturnModel returnModel = new ReturnModel();
        try {
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId已过期，请重新登陆!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JSON.toJSONString(returnModel, SerializerFeature.WriteNullStringAsEmpty);
                return model;
            }
            Map<String,String> search = new HashMap<>();
            search.put("robotCode",sysRobotErrorRecords.getRobotCode());
            search.put("scenicSpotId",String.valueOf(sysRobotErrorRecords.getScenicSpotId()));
            BaseQueryVo.setSearch(search);
            PageInfo<SysRobotErrorRecords> page = sysRobotErrorRecordsService.getRobotErrorRecordList(BaseQueryVo);
            returnModel.setData(page);
            returnModel.setMsg("上报成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setType(Constant.STATE_SUCCESS);
            String model = JSON.toJSONString(returnModel, SerializerFeature.WriteNullStringAsEmpty);
            return model;
        }catch (Exception e){
            logger.info("getRobotErrorRecordList",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            String model = JSON.toJSONString(returnModel, SerializerFeature.WriteNullStringAsEmpty);
            return model;
        }
    }

    /**
     * @Method robotErrorRecordApproval
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人故障申请审批
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/24 17:38
     */
    @RequestMapping("/robotErrorRecordApproval")
    @ResponseBody
    public ReturnModel robotErrorRecordApproval(@RequestParam("longinTokenId") String longinTokenId, SysRobotErrorRecords sysRobotErrorRecords){
        ReturnModel returnModel = new ReturnModel();
        try {
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId已过期，请重新登陆!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                return returnModel;
            }
            int i = sysRobotErrorRecordsService.robotErrorRecordApproval(sysRobotErrorRecords);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("审批成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setType(Constant.STATE_SUCCESS);
                Map<String,Object> search = new HashMap<>();
                search.put("scenicSpotId",sysRobotErrorRecords.getScenicSpotId());
                search.put("resCode","AFTER_SALES_PERSON_IN_CHARGE");
                List<SysAppUsers> appUsersList = appUserService.getAppUsersByScenicIdList(search);
                if (ToolUtil.isNotEmpty(appUsersList) && appUsersList.size() > 0){
                    for (SysAppUsers sysAppUsers : appUsersList){
                        // 个推推送消息到APP端
                        String isSuccess = WeChatGtRobotAppPush.singlePushApp(sysAppUsers.getUserClientGtId(), "审批结果", sysAppUsers.getScenicSpotName() + "," + "故障审批结果" + "," + DictUtils.getErrorRecordsApproveMap().get(sysRobotErrorRecords.getErrorRecordsApprove()));
                        if ("1".equals(isSuccess)) {
                            returnModel.setData("");
                            returnModel.setMsg("发送成功！");
                            returnModel.setState(Constant.STATE_SUCCESS);
                        } else {
                            returnModel.setData("");
                            returnModel.setMsg("发送失败！");
                            returnModel.setState(Constant.STATE_FAILURE);
                        }
                    }
                }
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("审批失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("robotErrorRecordApproval",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method robotErrorRecordRepair
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人配件维修信息
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/24 18:34
     */
    @RequestMapping("/robotErrorRecordRepair")
    @ResponseBody
    public ReturnModel robotErrorRecordRepair(@RequestParam("longinTokenId") String longinTokenId, SysRobotRepair sysRobotRepair){
        ReturnModel returnModel = new ReturnModel();
        try {
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId已过期，请重新登陆!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                return returnModel;
            }
            int i = sysRobotErrorRecordsService.robotErrorRecordRepair(sysRobotRepair);

            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("维修成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setType(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("维修失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("robotErrorRecordRepair",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method robotErrorRecordEvaluate
     * @Author 郭凯
     * @Version  1.0
     * @Description 评价星级
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/24 19:57
     */
    @RequestMapping("/robotErrorRecordEvaluate")
    @ResponseBody
    public ReturnModel robotErrorRecordEvaluate(@RequestParam("longinTokenId") String longinTokenId, SysRobotServiceRecords sysRobotServiceRecords){
        ReturnModel returnModel = new ReturnModel();
        try {
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId已过期，请重新登陆!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                return returnModel;
            }
            int i = sysRobotErrorRecordsService.robotErrorRecordEvaluate(sysRobotServiceRecords);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("评价成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setType(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("评价失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("robotErrorRecordEvaluate",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author zhang
     *  机器人故障申请详情
     * @return
     */
    @RequestMapping("/robotErrorRecordDetails")
    @ResponseBody
        public ReturnModel robotErrorRecordDetails(@RequestParam("longinTokenId") String longinTokenId, String errorRecordsId){
        ReturnModel returnModel = new ReturnModel();
        try{
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId已过期，请重新登陆!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                return returnModel;
            }

            SysRobotErrorRecords sysRobotErrorRecords = sysRobotErrorRecordsService.robotErrorRecordDetails(errorRecordsId);
            returnModel.setData(sysRobotErrorRecords);
            returnModel.setMsg("成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setType(Constant.STATE_SUCCESS);

            return returnModel;

        }catch (Exception e){
            logger.info("robotErrorRecordEvaluate",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            return returnModel;
        }

    }


    /**
     * @Author 张
     * @Description 管理者app故障派单
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     */
    @RequestMapping("/robotErrorRecordDispatch")
    @ResponseBody
    public ReturnModel robotErrorRecordDispatch(@RequestParam("longinTokenId") String longinTokenId, SysRobotErrorRepairUser sysRobotErrorRepairUser){
        ReturnModel returnModel = new ReturnModel();
        try {
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId已过期，请重新登陆!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                return returnModel;
            }
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




}
