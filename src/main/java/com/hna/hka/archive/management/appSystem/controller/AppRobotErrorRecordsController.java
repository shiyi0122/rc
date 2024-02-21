package com.hna.hka.archive.management.appSystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hna.hka.archive.management.appSystem.model.SysRobotErrorRecordsApprovalLog;
import com.hna.hka.archive.management.appSystem.service.AppUserService;
import com.hna.hka.archive.management.appSystem.service.SysRobotErrorRecordsService;
import com.hna.hka.archive.management.assetsSystem.model.*;
import com.hna.hka.archive.management.assetsSystem.service.SysAppFlowPathService;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.managerApp.service.SysAppUsersService;
import com.hna.hka.archive.management.system.model.SysRobot;
import com.hna.hka.archive.management.system.service.SysRobotService;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName：AppRobotErrorRecordsController
 * @Author: gouteng
 * @Date: 2023-01-28 18:15
 * @Description: 必须描述类做什么事情, 实现什么功能
 */
@Api(tags = "移动端故障记录")
@Slf4j
@Controller
@RequestMapping("/system/appRobotErrorRecords")
public class AppRobotErrorRecordsController {

//    @Autowired
//    private AppRobotErrorRecords appRobotErrorRecords;
    @Autowired
    private SysRobotErrorRecordsService sysRobotErrorRecordsService;
    @Autowired
    private AppUserService appUserService;

    @Autowired
    private SysAppUsersService sysAppUsersService;

    @Autowired
    private SysAppFlowPathService sysAppFlowPathService;

    @Autowired
    private SysRobotService sysRobotService;

    @ApiOperation(value = "查询故障记录方法")
    @ResponseBody
    @PostMapping("/get")
    public String getAppRobotErrorRecords(String content, SysRobotErrorRecords sysRobotErrorRecords,Integer pageNum,Integer pageSize){
        log.info("sysRobotErrorRecords:{}",sysRobotErrorRecords);
            ReturnModel dataModel = new ReturnModel();
            try {
                if (content == null || "".equals(content)) {
                    dataModel.setData("");
                    dataModel.setMsg("加密参数不能为空！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    dataModel.setType(Constant.STATE_FAILURE);
                    String model = JsonUtils.toString(dataModel);//对象转JSON
                    return AES.encode(model);//加密返回
                }
                String decode = AES.decode(content);//参数加密
                //解密后进行JSON解析
                JSONObject jsonobject = JSON.parseObject(decode);
                //TokenId
                String longinTokenId = jsonobject.getString("longinTokenId");
                //判断longinTokenId是否为空，如果为空，直接return
                if (ToolUtil.isEmpty(longinTokenId)) {
                    dataModel.setData("");
                    dataModel.setMsg("TokenId为空，请传入TokenId!");
                    dataModel.setState(Constant.STATE_FAILURE);
                    dataModel.setType(Constant.STATE_FAILURE);
                    String model = JsonUtils.toString(dataModel);//对象转JSON
                    return AES.encode(model);//加密返回
                }
                //查询longinTokenId，是否一致
                SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
                if (ToolUtil.isEmpty(appUsers)) {
                    dataModel.setData("");
                    dataModel.setMsg("TokenId已过期，请重新登陆!");
                    dataModel.setState(Constant.STATE_FAILURE);
                    dataModel.setType(Constant.STATE_FAILURE);
                    String model = JsonUtils.toString(dataModel);//对象转JSON
                    return AES.encode(model);//加密返回
                }
                Map<String,Object> search = new HashMap<>();
                search.put("scenicSpotId",sysRobotErrorRecords.getScenicSpotId());
                search.put("robotCode",sysRobotErrorRecords.getRobotCode());
                search.put("errorRecordsName",sysRobotErrorRecords.getErrorRecordsName());
                search.put("errorRecordsStatus",sysRobotErrorRecords.getErrorRecordsStatus());
                search.put("faultStatus",sysRobotErrorRecords.getFaultStatus());
                log.info("appUsers.getLoginName():{}",appUsers.getUserName());
//                search.put("errorRecordsPersonnel",appUsers.getUserName());
                search.put("userId",appUsers.getUserId());
                search.put("pageNum",pageNum);
                search.put("pageSize",pageSize);
                search.put("appFlowPathDetailsList",sysRobotErrorRecords.getAppFlowPathDetailsList());
                PageDataResult list = sysRobotErrorRecordsService.getAppRobotErrorRecords(search);
                dataModel.setData(list);
                dataModel.setMsg("");
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }catch (Exception e){
                log.info("getAppAccessoriesApplicationType",e);
                dataModel.setData("");
                dataModel.setMsg("接口超时");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
    }

    /**
     * 添加故障上报，（无发货单）
     * @param content
     * @param sysRobotErrorRecords
     * @return
     */
    @ApiOperation(value = "添加故障记录")
    @PostMapping("/add")
    @ResponseBody
    public String addAppRobotErrorRecords(String content, SysRobotErrorRecords sysRobotErrorRecords) {
        log.info("content:{} sysRobotErrorRecords:{}",content,sysRobotErrorRecords);
        ReturnModel dataModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            Map<String, Object> search = new HashMap<>();
            search.put("scenicSpotId", sysRobotErrorRecords.getScenicSpotId());
            search.put("robotCode", sysRobotErrorRecords.getRobotCode());
            search.put("errorRecordsName", sysRobotErrorRecords.getErrorRecordsName());
            search.put("errorRecordsStatus", sysRobotErrorRecords.getErrorRecordsStatus());
            search.put("errorRecordsPersonnel", appUsers.getLoginName());
            int i = sysRobotErrorRecordsService.addAppRobotErrorRecords(sysRobotErrorRecords);
            if (i==1){
                dataModel.setData(i);
                dataModel.setMsg("新增记录成功");
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            dataModel.setData(i);
            dataModel.setMsg("新增记录失败");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            log.info("getAppAccessoriesApplicationType", e);
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * 添加故障上报，有发货单
     * @param content
     * @param sysRobotErrorRecords
     * @return
     */
    @ApiOperation(value = "添加故障记录(新)")
    @PostMapping("/addNew")
    @ResponseBody
    public String addAppRobotErrorRecordsNew(String content, SysRobotErrorRecords sysRobotErrorRecords) {
        log.info("content:{} sysRobotErrorRecords:{}",content,sysRobotErrorRecords);
        ReturnModel dataModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

//            //机器人编号
//            String robotCode = jsonobject.getString("robotCode");
//            if (ToolUtil.isNotEmpty(robotCode)) {
//                SysRobot robot = sysRobotService.getRobotCodeBy(robotCode);
//                if (ToolUtil.isEmpty(robot)) {
//                    dataModel.setData("");
//                    dataModel.setMsg("未查询到此机器人！");
//                    dataModel.setState(Constant.STATE_FAILURE);
//                    dataModel.setType(Constant.STATE_FAILURE);
//                    String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
//                    return AES.encode(model);//加密返回
//                }
//            }

            Map<String, Object> search = new HashMap<>();
            search.put("scenicSpotId", sysRobotErrorRecords.getScenicSpotId());
            search.put("robotCode", sysRobotErrorRecords.getRobotCode());
            search.put("errorRecordsName", sysRobotErrorRecords.getErrorRecordsName());
            search.put("errorRecordsStatus", sysRobotErrorRecords.getErrorRecordsStatus());
            search.put("errorRecordsPersonnel", appUsers.getLoginName());
            search.put("errorRecordsNewId",sysRobotErrorRecords.getErrorRecordsNewId());
            search.put("errorRecordsSource",sysRobotErrorRecords.getErrorRecordsSource());
            int i = sysRobotErrorRecordsService.addAppRobotErrorRecords(sysRobotErrorRecords);
            if (i==1){
                dataModel.setData(i);
                dataModel.setMsg("新增记录成功");
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            dataModel.setData(i);
            dataModel.setMsg("新增记录失败");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {

            log.info("getAppAccessoriesApplicationType", e);
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * @Method robotErrorRecordApproval
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人配件申请审批
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/24 17:38
     */
    @RequestMapping("/robotErrorRecordApproval")
    @ResponseBody
    public String robotErrorRecordApproval(String content, SysRobotErrorRecords sysRobotErrorRecords){
        ReturnModel returnModel = new ReturnModel();
        try {

            if (content == null || "".equals(content)) {
                returnModel.setData("");
                returnModel.setMsg("加密参数不能为空！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                returnModel.setData("");
                returnModel.setMsg("TokenId为空，请传入TokenId!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON

                return AES.encode(model);//加密返回
            }

            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId已过期，请重新登陆!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
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
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
            }else{
                returnModel.setData("");
                returnModel.setMsg("审批失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
            }
        }catch (Exception e){
//            logger.info("robotErrorRecordApproval",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);
        }
    }

    /**
     * @Author zhang
     *  机器人故障申请详情
     * @return
     */
    @RequestMapping("/robotErrorRecordDetails")
    @ResponseBody
    public String robotErrorRecordDetails(String content, String errorRecordsId){
        ReturnModel returnModel = new ReturnModel();
        try{
            if (content == null || "".equals(content)) {
                returnModel.setData("");
                returnModel.setMsg("加密参数不能为空！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                returnModel.setData("");
                returnModel.setMsg("TokenId为空，请传入TokenId!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON

                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId已过期，请重新登陆!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON

                return AES.encode(model);
            }

             SysRobotErrorRecords sysRobotErrorRecords = sysRobotErrorRecordsService.robotErrorRecordDetails(errorRecordsId);
            returnModel.setData(sysRobotErrorRecords);
            returnModel.setMsg("成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);

        }catch (Exception e){
//            logger.info("robotErrorRecordEvaluate",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);
        }

    }


    /**
     * @Author 张
     * @Description 管理者app故障派单
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     */
    @RequestMapping("/robotErrorRecordDispatch")
    @ResponseBody
    public String robotErrorRecordDispatch(String content, SysRobotErrorRepairUser sysRobotErrorRepairUser){
        ReturnModel returnModel = new ReturnModel();
        try {

            if (content == null || "".equals(content)) {
                returnModel.setData("");
                returnModel.setMsg("加密参数不能为空！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                returnModel.setData("");
                returnModel.setMsg("TokenId为空，请传入TokenId!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }


            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId已过期，请重新登陆!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
            }
            int i = sysRobotErrorRecordsService.editRobotErrorRecords(sysRobotErrorRepairUser);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("派单成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                SysAppUsers sysAppUsers = sysAppUsersService.getAppUserById(sysRobotErrorRepairUser.getUserId());

                SysRobotErrorRecords sysRobotErrorRecords = sysRobotErrorRecordsService.selectById(sysRobotErrorRepairUser.getErrorId());

                // 个推推送消息到APP端
                String isSuccess = WeChatGtRobotAppPush.singlePushApp(sysAppUsers.getUserClientGtId(), "维修单", sysRobotErrorRecords.getScenicSpotName() +"，"+ sysRobotErrorRepairUser.getRobotCode() +"，"+ sysRobotErrorRecords.getErrorRecordsDescription());
                if ("1".equals(isSuccess)) {
                    returnModel.setData("");
                    returnModel.setMsg("发送成功！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    String model = JsonUtils.toString(returnModel);//对象转JSON
                    return AES.encode(model);
                } else {
                    returnModel.setData("");
                    returnModel.setMsg("发送失败！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    String model = JsonUtils.toString(returnModel);//对象转JSON
                    return AES.encode(model);
                }
            }else{
                returnModel.setData("");
                returnModel.setMsg("派单失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
            }
        }catch (Exception e){
//            logger.info("editRobotErrorRecords",e);
            e.printStackTrace();
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);
        }
    }



    /**
     * @Author zhang
     *  机器人故障类型下拉选
     * @return
     */
    @RequestMapping("/getOrderExceptionManagement")
    @ResponseBody
    public String getOrderExceptionManagement(String content,String type){
        ReturnModel returnModel = new ReturnModel();

        try{
            if (content == null || "".equals(content)) {
                returnModel.setData("");
                returnModel.setMsg("加密参数不能为空！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                returnModel.setData("");
                returnModel.setMsg("TokenId为空，请传入TokenId!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId已过期，请重新登陆!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
            }
            List<SysOrderExceptionManagement> orderExceptionManagementList = sysRobotErrorRecordsService.getOrderExceptionManagement(type);
            returnModel.setData(orderExceptionManagementList);
            returnModel.setMsg("成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);
        }catch (Exception e){
//            logger.info("robotErrorRecordEvaluate",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);
        }
    }

    /**
     * @Author 张
     * @Description 管理者app维修信息添加
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     */
    @RequestMapping("/robotErrorRecordRepair")
    @ResponseBody
    public String robotErrorRecordRepair(String content, SysRobotRepair sysRobotRepair){
        ReturnModel returnModel = new ReturnModel();
        try {

            if (content == null || "".equals(content)) {
                returnModel.setData("");
                returnModel.setMsg("加密参数不能为空！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                returnModel.setData("");
                returnModel.setMsg("TokenId为空，请传入TokenId!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }


            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId已过期，请重新登陆!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
            }
            int i = sysRobotErrorRecordsService.robotErrorRecordRepair(sysRobotRepair);
            returnModel.setData("");
            returnModel.setMsg("添加成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);
        }catch (Exception e){
            e.printStackTrace();
//            logger.info("editRobotErrorRecords",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);
        }
    }






    /**
     * @Author zhang
     *  根据用户信息获取收货地址
     * @return
     */
    @RequestMapping("/getUserIdByAddress")
    @ResponseBody
    public String getUserIdByAddress(String content){
        ReturnModel returnModel = new ReturnModel();

        try{
            if (content == null || "".equals(content)) {
                returnModel.setData("");
                returnModel.setMsg("加密参数不能为空！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                returnModel.setData("");
                returnModel.setMsg("TokenId为空，请传入TokenId!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId已过期，请重新登陆!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
            }
            List<Address> addresseList = sysRobotErrorRecordsService.getUserIdByAddress(appUsers.getUserId().toString());
            returnModel.setData(addresseList);
            returnModel.setMsg("成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);
        }catch (Exception e){
//            logger.info("robotErrorRecordEvaluate",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);
        }
    }

    /**
     * @Author zhang
     *  维修人员下拉选
     * @return
     */
    @RequestMapping("/getRepairUserList")
    @ResponseBody
    public String getRepairUserList(String content){
        ReturnModel returnModel = new ReturnModel();

        try{
            if (content == null || "".equals(content)) {
                returnModel.setData("");
                returnModel.setMsg("加密参数不能为空！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                returnModel.setData("");
                returnModel.setMsg("TokenId为空，请传入TokenId!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId已过期，请重新登陆!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
            }
            List<SysAppUsers> sysAppUsers = sysAppUsersService.getSysAppUsers();
            returnModel.setData(sysAppUsers);
            returnModel.setMsg("成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);
        }catch (Exception e){
//            logger.info("robotErrorRecordEvaluate",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);
        }
    }

    /**
     * 库房下拉选
     * @param content
     * @return
     */
    @RequestMapping("/getStorageRoomList")
    @ResponseBody
    public String getStorageRoomList(String content,String  errorRecordsId){
        ReturnModel returnModel = new ReturnModel();

        try{
            if (content == null || "".equals(content)) {
                returnModel.setData("");
                returnModel.setMsg("加密参数不能为空！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                returnModel.setData("");
                returnModel.setMsg("TokenId为空，请传入TokenId!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId已过期，请重新登陆!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
            }
            List<GoodsStock> goodsStockList = sysRobotErrorRecordsService.getStorageRoomList(errorRecordsId);
            returnModel.setData(goodsStockList);
            returnModel.setMsg("成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);
        }catch (Exception e){
//            logger.info("robotErrorRecordEvaluate",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);
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
    public String robotErrorRecordEvaluate(String content, SysRobotServiceRecords sysRobotServiceRecords){
        ReturnModel returnModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                returnModel.setData("");
                returnModel.setMsg("加密参数不能为空！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId已过期，请重新登陆!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
            }
            int i = sysRobotErrorRecordsService.robotErrorRecordEvaluate(sysRobotServiceRecords);
            if (i == 1){
                returnModel.setMsg("成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setType(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
            }else{
                returnModel.setMsg("失败");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
            }
        }catch (Exception e){
//            logger.info("robotErrorRecordEvaluate",e);
            returnModel.setMsg("系统错误");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);
        }
    }



    /**
     * 库房下拉选
     * @param content
     * @return
     */
    @RequestMapping("/getErrorRecordPartsList")
    @ResponseBody
    public String getErrorRecordPartsList(String content,String  errorRecordsId){
        ReturnModel returnModel = new ReturnModel();

        try{
            if (content == null || "".equals(content)) {
                returnModel.setData("");
                returnModel.setMsg("加密参数不能为空！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                returnModel.setData("");
                returnModel.setMsg("TokenId为空，请传入TokenId!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId已过期，请重新登陆!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
            }
            List<GoodsStock> goodsStockList = sysRobotErrorRecordsService.getStorageRoomList(errorRecordsId);
            returnModel.setData(goodsStockList);
            returnModel.setMsg("成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);
        }catch (Exception e){
//            logger.info("robotErrorRecordEvaluate",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);
        }
    }



    /**
     * @Author 张
     * @Description 管理者app上传快递单号以及快递费
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     */
    @RequestMapping("/robotErrorRecordCourierNumber")
    @ResponseBody
    public String robotErrorRecordCourierNumber(String content, SysRobotErrorRepairUser sysRobotErrorRepairUser){
        ReturnModel returnModel = new ReturnModel();
        try {

            if (content == null || "".equals(content)) {
                returnModel.setData("");
                returnModel.setMsg("加密参数不能为空！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                returnModel.setData("");
                returnModel.setMsg("TokenId为空，请传入TokenId!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }


            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId已过期，请重新登陆!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
            }
            int i = sysRobotErrorRecordsService.robotErrorRecordCourierNumber(sysRobotErrorRepairUser);
            returnModel.setData("");
            returnModel.setMsg("上传成功");
            returnModel.setState(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);

        }catch (Exception e){
//            logger.info("editRobotErrorRecords",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);
        }
    }





    /**
     * @Author 张
     * @Description 管理者app查询发货单
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     */
    @RequestMapping("/getInvoiceList")
    @ResponseBody
    public String getInvoiceList(String content, SysRobotErrorRepairUser sysRobotErrorRepairUser){
        ReturnModel returnModel = new ReturnModel();
        try {

            if (content == null || "".equals(content)) {
                returnModel.setData("");
                returnModel.setMsg("加密参数不能为空！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                returnModel.setData("");
                returnModel.setMsg("TokenId为空，请传入TokenId!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }


            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId已过期，请重新登陆!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
            }
            int i = sysRobotErrorRecordsService.robotErrorRecordCourierNumber(sysRobotErrorRepairUser);
                returnModel.setData("");
                returnModel.setMsg("上传成功");
                returnModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);

        }catch (Exception e){
//            logger.info("editRobotErrorRecords",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);
        }
    }


    /**
     * @Author 张
     * @Description 管理者app故障配件发货单签收
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     */
    @RequestMapping("/editErrorAccessory")
    @ResponseBody
    public String editErrorAccessory(String content, @NotNull(message = "故障申请ID不能为空") String id, String signInPicture){
        ReturnModel returnModel = new ReturnModel();
        try {

            if (content == null || "".equals(content)) {
                returnModel.setData("");
                returnModel.setMsg("加密参数不能为空！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                returnModel.setData("");
                returnModel.setMsg("TokenId为空，请传入TokenId!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }


            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId已过期，请重新登陆!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
            }
            int i = sysRobotErrorRecordsService.editErrorAccessory(id,signInPicture);
            returnModel.setData("");
            returnModel.setMsg("签收成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);

        }catch (Exception e){
//            logger.info("editRobotErrorRecords",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);
        }
    }


    /**
     * @Author 张
     * @Description 管理者app 获取审批流程（暂未测试使用）
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     */
    @RequestMapping("/getSysAppFlowPathDrop")
    @ResponseBody
    public String getSysAppFlowPathDrop(String content, @NotNull(message = "景区ID不能为空") String scenicSpotId){
        ReturnModel returnModel = new ReturnModel();
        try {

            if (content == null || "".equals(content)) {
                returnModel.setData("");
                returnModel.setMsg("加密参数不能为空！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                returnModel.setData("");
                returnModel.setMsg("TokenId为空，请传入TokenId!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId已过期，请重新登陆!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
            }
            List<SysAppFlowPath> list = sysAppFlowPathService.getSysAppFlowPathDrop(scenicSpotId);
            returnModel.setData(list);
            returnModel.setMsg("查询成功!");
            returnModel.setState(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);

        }catch (Exception e){
//            logger.info("editRobotErrorRecords",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);
        }
    }





    /**
     * @Author 张
     * @Description 管理者app 故障单审批(新)
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     */
    @RequestMapping("/errorRecordsToExamine")
    @ResponseBody
    public String errorRecordsToExamine(String content, @NotNull(message = "故障申请ID不能为空") String id, SysRobotErrorRecordsApprovalLog sysRobotErrorRecordsApprovalLog){
        ReturnModel returnModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                returnModel.setData("");
                returnModel.setMsg("加密参数不能为空！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                returnModel.setData("");
                returnModel.setMsg("TokenId为空，请传入TokenId!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId已过期，请重新登陆!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
            }
            int i =  sysRobotErrorRecordsService.errorRecordsToExamine(sysRobotErrorRecordsApprovalLog);

            if (i > 0){
                returnModel.setData(i);
                returnModel.setMsg("审核成功!");
                returnModel.setState(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
            }else{
                returnModel.setData(i);
                returnModel.setMsg("审核失败!");
                returnModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
            }
        }catch (Exception e){
//            logger.info("editRobotErrorRecords",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);
        }
    }







}
