package com.hna.hka.archive.management.appSystem.controller;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.service.AppUserService;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotErrorRecords;
import com.hna.hka.archive.management.managerApp.model.SysRobotAdministratorsVersion;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.service.SysOrderService;
import com.hna.hka.archive.management.system.service.SysRobotAdministratorsVersionService;
import com.hna.hka.archive.management.system.service.SysRobotFauleService;
import com.hna.hka.archive.management.system.service.SysRobotService;

import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;
import com.hna.hka.archive.management.wenYuRiverInterface.model.WenYuRiver;
import com.hna.hka.archive.management.wenYuRiverInterface.model.WenYuRiverOrder;
import com.hna.hka.archive.management.wenYuRiverInterface.util.HttpClientToInterface;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.system.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.validation.constraints.NotNull;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.appSystem.controller
 * @ClassName: AppRobotController
 * @Author: 郭凯
 * @Description:
 * @Date: 2020/11/23 17:30
 * @Version: 1.0
 */
@RequestMapping("/appSystem/appRobot")
@Controller
public class AppRobotController extends PublicUtil {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private SysRobotService sysRobotService;

    @Autowired
    private SysOrderService sysOrderService;

    @Autowired
    private SysRobotAdministratorsVersionService sysRobotAdministratorsVersionService;

    @Autowired
    private SysRobotFauleService sysRobotFauleService;

    /**
     * @Method roleResourcePrms
     * @Author 郭凯
     * @Version 1.0
     * @Description 查询机器人列表
     * @Return java.lang.String
     * @Date 2021/3/24 10:28
     */
    @RequestMapping(value = "/appRobotInterface.do", method = RequestMethod.POST)
    @ResponseBody
    public String roleResourcePrms(String content) {
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
            //用户名称
            String loginName = jsonobject.getString("loginName");
            //判断loginName是否为空，如果为空，直接return
            if (loginName == null || loginName == "") {
                dataModel.setData("");
                dataModel.setMsg("登录名称为空，请传入登录名称!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //景区ID
            String scenicSpotId = jsonobject.getString("scenicSpotId");
            //判断scenicSpotId是否为空，如果为空，直接return
            if (scenicSpotId == "" || scenicSpotId == null) {
                dataModel.setData("");
                dataModel.setMsg("景区ID为空，请传入景区ID!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //机器人编号
            String robotCode = jsonobject.getString("robotCode");
            //userType
            String userType = jsonobject.getString("userType");
            //判断账户分类是否为空，如果为空，直接return
            if (userType == "" || userType == null) {
                dataModel.setData("");
                dataModel.setMsg("userType为空，请传入userType!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            List<SysRobot> robots = null;
            robots = sysRobotService.getAppRobotList(scenicSpotId, robotCode);
            dataModel.setData(robots);
            dataModel.setMsg("机器人列表查询成功!");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            // TODO: handle exception
            dataModel.setData("");
            dataModel.setMsg("机器人列表查询失败!");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * @return java.lang.String
     * @Author 郭凯
     * @Description 管理者APP修改机器人状态
     * @Date 9:04 2020/11/24
     * @Param [content]
     **/
    @RequestMapping(value = "/editRobotInterface.do")
    @ResponseBody
    public String editRobotInterface(String content) {
        ReturnModel dataModel = new ReturnModel();
        try {
            SysRobot sysRobot = new SysRobot();
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
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //机器人ID
            String robotId = jsonobject.getString("robotId");
            //判断机器人ID是否为空，如果为空，直接return
            if (robotId == null || robotId == "") {
                dataModel.setData("");
                dataModel.setMsg("机器人ID为空，请传入机器人ID!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //机器人状态
            String robotRun = jsonobject.getString("robotRun");
            //判断机器人状态是否为空，如果为空，直接return
            if (robotRun == null || robotRun == "") {
                dataModel.setData("");
                dataModel.setMsg("机器人状态为空，请传入机器人状态!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            sysRobot.setRobotCode(robotId);
            sysRobot.setRobotRunState(robotRun);
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            // 根据机器人id查询机器人
            SysRobot robotCode = sysRobotService.getRobotCodeBy(sysRobot.getRobotCode());
            if (robotCode == null) {
                dataModel.setData("");
                dataModel.setMsg("未查询到机器人");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            } else {
                if (sysRobot.getRobotRunState().equals("10")) {// 如果传进来是闲置
                    if (sysRobot.getRobotRunState().equals(robotCode.getRobotRunState())) {
                        dataModel.setData("");
                        dataModel.setMsg("机器人已是当前状态，不能重复操作！");
                        dataModel.setState(Constant.STATE_FAILURE);
                        dataModel.setType(Constant.STATE_FAILURE);
                        String model = JsonUtils.toString(dataModel);//对象转JSON
                        return AES.encode(model);//加密返回
                    }
                    // 查询机器人正在进行中订单
                    SysOrder order = sysOrderService.getOrderStateByRobotCode(robotCode.getRobotCode(), "10");
                    if (order != null) {
                        dataModel.setData("");
                        dataModel.setMsg("当前机器人正在运行中，无法修改闲置状态");
                        dataModel.setState(Constant.STATE_FAILURE);
                        dataModel.setType(Constant.STATE_FAILURE);
                        String model = JsonUtils.toString(dataModel);//对象转JSON
                        return AES.encode(model);//加密返回
                    }
                    if (robotCode.getRobotRunState().equals("40") || robotCode.getRobotRunState().equals("50") || robotCode.getRobotRunState().equals("70") || robotCode.getRobotRunState().equals("20") || robotCode.getRobotRunState().equals("30") || "90".equals(robotCode.getRobotRunState()) || "100".equals(robotCode.getRobotRunState())) {
                        dataModel.setData("");
                        dataModel.setMsg("管理系统推送机器人状态！");
                        dataModel.setState(Constant.STATE_SUCCESS);
                        dataModel.setType(Constant.ROBOT_IDLE_STATE);
                        // 转JSON格式发送到个推
                        String robotUnlock = JsonUtils.toString(dataModel);
                        String encode = AES.encode(robotUnlock);// 加密推送
                        // 个推推送消息到APP端
                        //判断pad为老版本还是新版本
                        if ("0".equals(robotCode.getRobotAppType())) {
                            String isSuccess = WeChatGtRobotAppPush.singlePush(robotCode.getRobotCodeCid(), encode, "成功!");
                            if ("1".equals(isSuccess)) {
                                SysAppModificationLog modificationLog = new SysAppModificationLog();
                                modificationLog.setModificationLogId(IdUtils.getSeqId());
                                modificationLog.setModificationLogLoginName(appUsers.getLoginName());
                                modificationLog.setModificationLogRobotCode(robotCode.getRobotCode());
                                modificationLog.setModificationLogFront(DictUtils.getRobotRunMap().get(robotCode.getRobotRunState()));
                                modificationLog.setModificationLogAfter("闲置");
                                modificationLog.setCreateDate(DateUtil.currentDateTime());
                                modificationLog.setUpdateDate(DateUtil.currentDateTime());
                                //添加修改日志
                                sysRobotService.saveModificationLog(modificationLog);
                                dataModel.setData("");
                                dataModel.setMsg("发送成功！");
                                dataModel.setState(Constant.STATE_SUCCESS);
                                dataModel.setType(Constant.STATE_SUCCESS);
                                String model = JsonUtils.toString(dataModel);//对象转JSON
                                return AES.encode(model);//加密返回
                            } else {
                                dataModel.setData("");
                                dataModel.setMsg("发送失败！");
                                dataModel.setState(Constant.STATE_FAILURE);
                                dataModel.setType(Constant.STATE_FAILURE);
                                String model = JsonUtils.toString(dataModel);//对象转JSON
                                return AES.encode(model);//加密返回
                            }
                        } else if ("1".equals(robotCode.getRobotAppType())){
                            String isSuccessN = WeChatGtRobotAppPush.singlePushNewPadApp(robotCode.getNewRobotCodeCid(), encode, "成功!");
                            if ("1".equals(isSuccessN)) {
                                SysAppModificationLog modificationLog = new SysAppModificationLog();
                                modificationLog.setModificationLogId(IdUtils.getSeqId());
                                modificationLog.setModificationLogLoginName(appUsers.getLoginName());
                                modificationLog.setModificationLogRobotCode(robotCode.getRobotCode());
                                modificationLog.setModificationLogFront(DictUtils.getRobotRunMap().get(robotCode.getRobotRunState()));
                                modificationLog.setModificationLogAfter("闲置");
                                modificationLog.setCreateDate(DateUtil.currentDateTime());
                                modificationLog.setUpdateDate(DateUtil.currentDateTime());
                                //添加修改日志
                                sysRobotService.saveModificationLog(modificationLog);
                                dataModel.setData("");
                                dataModel.setMsg("发送成功！");
                                dataModel.setState(Constant.STATE_SUCCESS);
                                dataModel.setType(Constant.STATE_SUCCESS);
                                String model = JsonUtils.toString(dataModel);//对象转JSON
                                return AES.encode(model);//加密返回
                            } else {
                                dataModel.setData("");
                                dataModel.setMsg("发送失败！");
                                dataModel.setState(Constant.STATE_FAILURE);
                                dataModel.setType(Constant.STATE_FAILURE);
                                String model = JsonUtils.toString(dataModel);//对象转JSON
                                return AES.encode(model);//加密返回
                            }

                        }else if ("2".equals(robotCode.getRobotAppType())){
                            String isSuccessN = WeChatGtRobotAppPush.singlePushAuto(robotCode.getNewRobotCodeCid(), encode, "成功!");
                            if ("1".equals(isSuccessN)) {
                                SysAppModificationLog modificationLog = new SysAppModificationLog();
                                modificationLog.setModificationLogId(IdUtils.getSeqId());
                                modificationLog.setModificationLogLoginName(appUsers.getLoginName());
                                modificationLog.setModificationLogRobotCode(robotCode.getRobotCode());
                                modificationLog.setModificationLogFront(DictUtils.getRobotRunMap().get(robotCode.getRobotRunState()));
                                modificationLog.setModificationLogAfter("闲置");
                                modificationLog.setCreateDate(DateUtil.currentDateTime());
                                modificationLog.setUpdateDate(DateUtil.currentDateTime());
                                //添加修改日志
                                sysRobotService.saveModificationLog(modificationLog);
                                dataModel.setData("");
                                dataModel.setMsg("发送成功！");
                                dataModel.setState(Constant.STATE_SUCCESS);
                                dataModel.setType(Constant.STATE_SUCCESS);
                                String model = JsonUtils.toString(dataModel);//对象转JSON
                                return AES.encode(model);//加密返回
                            } else {
                                dataModel.setData("");
                                dataModel.setMsg("发送失败！");
                                dataModel.setState(Constant.STATE_FAILURE);
                                dataModel.setType(Constant.STATE_FAILURE);
                                String model = JsonUtils.toString(dataModel);//对象转JSON
                                return AES.encode(model);//加密返回
                            }
                        }
                    }
                } else if (sysRobot.getRobotRunState().equals("20")) {// 如果传进来是用户解锁
                    if (robotCode.getRobotRunState().equals("30") || robotCode.getRobotRunState().equals("100")) {
                        dataModel.setData("");
                        dataModel.setMsg("管理系统推送机器人状态！");
                        dataModel.setState(Constant.STATE_SUCCESS);
                        dataModel.setType(Constant.ROBOT_UNLOCKING);
                        // 转JSON格式发送到个推
                        String robotUnlock = JsonUtils.toString(dataModel);
                        String encode = AES.encode(robotUnlock);// 加密推送
                        // 个推推送消息到APP端
                        // 判断pad为老版本还是新版本
                        if ("0".equals(robotCode.getRobotAppType())) {
                            String isSuccess = WeChatGtRobotAppPush.singlePush(robotCode.getRobotCodeCid(), encode, "成功!");
                            if ("1".equals(isSuccess)) {
                                SysAppModificationLog modificationLog = new SysAppModificationLog();
                                modificationLog.setModificationLogId(IdUtils.getSeqId());
                                modificationLog.setModificationLogLoginName(appUsers.getLoginName());
                                modificationLog.setModificationLogRobotCode(robotCode.getRobotCode());
                                modificationLog.setModificationLogFront(DictUtils.getRobotRunMap().get(robotCode.getRobotRunState()));
                                modificationLog.setModificationLogAfter("闲置");
                                modificationLog.setCreateDate(DateUtil.currentDateTime());
                                modificationLog.setUpdateDate(DateUtil.currentDateTime());
                                //添加修改日志
                                sysRobotService.saveModificationLog(modificationLog);
                                dataModel.setData("");
                                dataModel.setMsg("发送成功！");
                                dataModel.setState(Constant.STATE_SUCCESS);
                                dataModel.setType(Constant.STATE_SUCCESS);
                                String model = JsonUtils.toString(dataModel);//对象转JSON
                                return AES.encode(model);//加密返回
                            } else {
                                dataModel.setData("");
                                dataModel.setMsg("发送失败！");
                                dataModel.setState(Constant.STATE_FAILURE);
                                dataModel.setType(Constant.STATE_FAILURE);
                                String model = JsonUtils.toString(dataModel);//对象转JSON
                                return AES.encode(model);//加密返回
                            }
                        } else if ("1".equals(robotCode.getRobotAppType())){
                            String isSuccessN = WeChatGtRobotAppPush.singlePushNewPadApp(robotCode.getNewRobotCodeCid(), encode, "成功!");
                            if ("1".equals(isSuccessN)) {
                                SysAppModificationLog modificationLog = new SysAppModificationLog();
                                modificationLog.setModificationLogId(IdUtils.getSeqId());
                                modificationLog.setModificationLogLoginName(appUsers.getLoginName());
                                modificationLog.setModificationLogRobotCode(robotCode.getRobotCode());
                                modificationLog.setModificationLogFront(DictUtils.getRobotRunMap().get(robotCode.getRobotRunState()));
                                modificationLog.setModificationLogAfter("闲置");
                                modificationLog.setCreateDate(DateUtil.currentDateTime());
                                modificationLog.setUpdateDate(DateUtil.currentDateTime());
                                //添加修改日志
                                sysRobotService.saveModificationLog(modificationLog);
                                dataModel.setData("");
                                dataModel.setMsg("发送成功！");
                                dataModel.setState(Constant.STATE_SUCCESS);
                                dataModel.setType(Constant.STATE_SUCCESS);
                                String model = JsonUtils.toString(dataModel);//对象转JSON
                                return AES.encode(model);//加密返回
                            } else {
                                dataModel.setData("");
                                dataModel.setMsg("发送失败！");
                                dataModel.setState(Constant.STATE_FAILURE);
                                dataModel.setType(Constant.STATE_FAILURE);
                                String model = JsonUtils.toString(dataModel);//对象转JSON
                                return AES.encode(model);//加密返回
                            }
                        }else if ("2".equals(robotCode.getRobotAppType())){
                            String isSuccessN = WeChatGtRobotAppPush.singlePushAuto(robotCode.getNewRobotCodeCid(), encode, "成功!");
                            if ("1".equals(isSuccessN)) {
                                SysAppModificationLog modificationLog = new SysAppModificationLog();
                                modificationLog.setModificationLogId(IdUtils.getSeqId());
                                modificationLog.setModificationLogLoginName(appUsers.getLoginName());
                                modificationLog.setModificationLogRobotCode(robotCode.getRobotCode());
                                modificationLog.setModificationLogFront(DictUtils.getRobotRunMap().get(robotCode.getRobotRunState()));
                                modificationLog.setModificationLogAfter("闲置");
                                modificationLog.setCreateDate(DateUtil.currentDateTime());
                                modificationLog.setUpdateDate(DateUtil.currentDateTime());
                                //添加修改日志
                                sysRobotService.saveModificationLog(modificationLog);
                                dataModel.setData("");
                                dataModel.setMsg("发送成功！");
                                dataModel.setState(Constant.STATE_SUCCESS);
                                dataModel.setType(Constant.STATE_SUCCESS);
                                String model = JsonUtils.toString(dataModel);//对象转JSON
                                return AES.encode(model);//加密返回
                            } else {
                                dataModel.setData("");
                                dataModel.setMsg("发送失败！");
                                dataModel.setState(Constant.STATE_FAILURE);
                                dataModel.setType(Constant.STATE_FAILURE);
                                String model = JsonUtils.toString(dataModel);//对象转JSON
                                return AES.encode(model);//加密返回
                            }
                        }

                    } else {
                        dataModel.setData("");
                        dataModel.setMsg("不能执行此操作！");
                        dataModel.setState(Constant.STATE_FAILURE);
                        dataModel.setType(Constant.STATE_FAILURE);
                        String model = JsonUtils.toString(dataModel);//对象转JSON
                        return AES.encode(model);//加密返回
                    }
                } else if (sysRobot.getRobotRunState().equals("30")) {// 如果传进来是用户临时锁定，则不能修改
                    dataModel.setData("");
                    dataModel.setMsg("不能执行此操作！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    dataModel.setType(Constant.STATE_FAILURE);
                    String model = JsonUtils.toString(dataModel);//对象转JSON
                    return AES.encode(model);//加密返回
                } else if (sysRobot.getRobotRunState().equals("40")) {// 如果传进来是管理员解锁
                    if (sysRobot.getRobotRunState().equals(robotCode.getRobotRunState())) {
                        dataModel.setData("");
                        dataModel.setMsg("机器人已是当前状态，不能重复操作！");
                        dataModel.setState(Constant.STATE_FAILURE);
                        dataModel.setType(Constant.STATE_FAILURE);
                        String model = JsonUtils.toString(dataModel);//对象转JSON
                        return AES.encode(model);//加密返回
                    }
                    if (robotCode.getRobotRunState().equals("10") || robotCode.getRobotRunState().equals("50") || robotCode.getRobotRunState().equals("70")) {// 只能是状态为显示的状态下修改
                        dataModel.setData("");
                        dataModel.setMsg("管理系统推送机器人状态！");
                        dataModel.setState(Constant.STATE_SUCCESS);
                        dataModel.setType(Constant.ADMINISTRATOR_UNLOCKING_ROBOT);
                        // 转JSON格式发送到个推
                        String robotUnlock = JsonUtils.toString(dataModel);
                        String encode = AES.encode(robotUnlock);// 加密推送
                        // 个推推送消息到APP端
                        if ("0".equals(robotCode.getRobotAppType())) {
                            String isSuccess = WeChatGtRobotAppPush.singlePush(robotCode.getRobotCodeCid(), encode, "成功!");
                            if ("1".equals(isSuccess)) {
                                SysAppModificationLog modificationLog = new SysAppModificationLog();
                                modificationLog.setModificationLogId(IdUtils.getSeqId());
                                modificationLog.setModificationLogLoginName(appUsers.getLoginName());
                                modificationLog.setModificationLogRobotCode(robotCode.getRobotCode());
                                modificationLog.setModificationLogFront(DictUtils.getRobotRunMap().get(robotCode.getRobotRunState()));
                                modificationLog.setModificationLogAfter("闲置");
                                modificationLog.setCreateDate(DateUtil.currentDateTime());
                                modificationLog.setUpdateDate(DateUtil.currentDateTime());
                                //添加修改日志
                                sysRobotService.saveModificationLog(modificationLog);
                                dataModel.setData("");
                                dataModel.setMsg("发送成功！");
                                dataModel.setState(Constant.STATE_SUCCESS);
                                dataModel.setType(Constant.STATE_SUCCESS);
                                String model = JsonUtils.toString(dataModel);//对象转JSON
                                return AES.encode(model);//加密返回
                            } else {
                                dataModel.setData("");
                                dataModel.setMsg("发送失败！");
                                dataModel.setState(Constant.STATE_FAILURE);
                                dataModel.setType(Constant.STATE_FAILURE);
                                String model = JsonUtils.toString(dataModel);//对象转JSON
                                return AES.encode(model);//加密返回
                            }
                        } else {
                            String isSuccessN = WeChatGtRobotAppPush.singlePushNewPadApp(robotCode.getNewRobotCodeCid(), encode, "成功!");
                            if ("1".equals(isSuccessN)) {
                                SysAppModificationLog modificationLog = new SysAppModificationLog();
                                modificationLog.setModificationLogId(IdUtils.getSeqId());
                                modificationLog.setModificationLogLoginName(appUsers.getLoginName());
                                modificationLog.setModificationLogRobotCode(robotCode.getRobotCode());
                                modificationLog.setModificationLogFront(DictUtils.getRobotRunMap().get(robotCode.getRobotRunState()));
                                modificationLog.setModificationLogAfter("闲置");
                                modificationLog.setCreateDate(DateUtil.currentDateTime());
                                modificationLog.setUpdateDate(DateUtil.currentDateTime());
                                //添加修改日志
                                sysRobotService.saveModificationLog(modificationLog);
                                dataModel.setData("");
                                dataModel.setMsg("发送成功！");
                                dataModel.setState(Constant.STATE_SUCCESS);
                                dataModel.setType(Constant.STATE_SUCCESS);
                                String model = JsonUtils.toString(dataModel);//对象转JSON
                                return AES.encode(model);//加密返回
                            } else {
                                dataModel.setData("");
                                dataModel.setMsg("发送失败！");
                                dataModel.setState(Constant.STATE_FAILURE);
                                dataModel.setType(Constant.STATE_FAILURE);
                                String model = JsonUtils.toString(dataModel);//对象转JSON
                                return AES.encode(model);//加密返回
                            }
                        }
                    } else {
                        //无法修改状态
                        dataModel.setData("");
                        dataModel.setMsg("无法修改状态！");
                        dataModel.setState(Constant.STATE_FAILURE);
                        dataModel.setType(Constant.STATE_FAILURE);
                        String model = JsonUtils.toString(dataModel);//对象转JSON
                        return AES.encode(model);//加密返回
                    }
                } else if (sysRobot.getRobotRunState().equals("50")) {// 如果传进来是管理员锁定
                    if (sysRobot.getRobotRunState().equals(robotCode.getRobotRunState())) {
                        dataModel.setData("");
                        dataModel.setMsg("机器人已是当前状态，不能重复操作！");
                        dataModel.setState(Constant.STATE_FAILURE);
                        dataModel.setType(Constant.STATE_FAILURE);
                        String model = JsonUtils.toString(dataModel);//对象转JSON
                        return AES.encode(model);//加密返回
                    }
                    if (robotCode.getRobotRunState().equals("10") || robotCode.getRobotRunState().equals("40")) {
                        dataModel.setData("");
                        dataModel.setMsg("管理系统推送机器人状态！");
                        dataModel.setState(Constant.STATE_SUCCESS);
                        dataModel.setType(Constant.ADMINISTRATOR_LOCKS_ROBOT);
                        // 转JSON格式发送到个推
                        String robotUnlock = JsonUtils.toString(dataModel);
                        String encode = AES.encode(robotUnlock);// 加密推送
                        // 个推推送消息到APP端

                        if ("0".equals(robotCode.getRobotAppType())) {
                            String isSuccess = WeChatGtRobotAppPush.singlePush(robotCode.getRobotCodeCid(), encode, "成功!");
                            if ("1".equals(isSuccess)) {
                                SysAppModificationLog modificationLog = new SysAppModificationLog();
                                modificationLog.setModificationLogId(IdUtils.getSeqId());
                                modificationLog.setModificationLogLoginName(appUsers.getLoginName());
                                modificationLog.setModificationLogRobotCode(robotCode.getRobotCode());
                                modificationLog.setModificationLogFront(DictUtils.getRobotRunMap().get(robotCode.getRobotRunState()));
                                modificationLog.setModificationLogAfter("闲置");
                                modificationLog.setCreateDate(DateUtil.currentDateTime());
                                modificationLog.setUpdateDate(DateUtil.currentDateTime());
                                //添加修改日志
                                sysRobotService.saveModificationLog(modificationLog);
                                dataModel.setData("");
                                dataModel.setMsg("发送成功！");
                                dataModel.setState(Constant.STATE_SUCCESS);
                                dataModel.setType(Constant.STATE_SUCCESS);
                                String model = JsonUtils.toString(dataModel);//对象转JSON
                                return AES.encode(model);//加密返回
                            } else {
                                dataModel.setData("");
                                dataModel.setMsg("发送失败！");
                                dataModel.setState(Constant.STATE_FAILURE);
                                dataModel.setType(Constant.STATE_FAILURE);
                                String model = JsonUtils.toString(dataModel);//对象转JSON
                                return AES.encode(model);//加密返回
                            }
                        } else {
                            String isSuccessN = WeChatGtRobotAppPush.singlePushNewPadApp(robotCode.getNewRobotCodeCid(), encode, "成功!");
                            if ("1".equals(isSuccessN)) {
                                SysAppModificationLog modificationLog = new SysAppModificationLog();
                                modificationLog.setModificationLogId(IdUtils.getSeqId());
                                modificationLog.setModificationLogLoginName(appUsers.getLoginName());
                                modificationLog.setModificationLogRobotCode(robotCode.getRobotCode());
                                modificationLog.setModificationLogFront(DictUtils.getRobotRunMap().get(robotCode.getRobotRunState()));
                                modificationLog.setModificationLogAfter("闲置");
                                modificationLog.setCreateDate(DateUtil.currentDateTime());
                                modificationLog.setUpdateDate(DateUtil.currentDateTime());
                                //添加修改日志
                                sysRobotService.saveModificationLog(modificationLog);
                                dataModel.setData("");
                                dataModel.setMsg("发送成功！");
                                dataModel.setState(Constant.STATE_SUCCESS);
                                dataModel.setType(Constant.STATE_SUCCESS);
                                String model = JsonUtils.toString(dataModel);//对象转JSON
                                return AES.encode(model);//加密返回
                            } else {
                                dataModel.setData("");
                                dataModel.setMsg("发送失败！");
                                dataModel.setState(Constant.STATE_FAILURE);
                                dataModel.setType(Constant.STATE_FAILURE);
                                String model = JsonUtils.toString(dataModel);//对象转JSON
                                return AES.encode(model);//加密返回
                            }
                        }
                    } else {
                        //无法修改状态
                        dataModel.setData("");
                        dataModel.setMsg("无法修改状态！");
                        dataModel.setState(Constant.STATE_FAILURE);
                        dataModel.setType(Constant.STATE_FAILURE);
                        String model = JsonUtils.toString(dataModel);//对象转JSON
                        return AES.encode(model);//加密返回
                    }
                } else if (sysRobot.getRobotRunState().equals("60")) {
                    dataModel.setData("");
                    dataModel.setMsg("后台管理人员无法操作此状态  必须有运营人员修复机器人或者管理者APP下发自检指令时修复！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    dataModel.setType(Constant.STATE_FAILURE);
                    String model = JsonUtils.toString(dataModel);//对象转JSON
                    return AES.encode(model);//加密返回
                } else if (sysRobot.getRobotRunState().equals("70")) {
                    dataModel.setData("");
                    dataModel.setMsg("此状态不能修改！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    String model = JsonUtils.toString(dataModel);//对象转JSON
                    return AES.encode(model);//加密返回
                } else if (sysRobot.getRobotRunState().equals("80")) {
                    dataModel.setData("");
                    dataModel.setMsg("此状态不能修改，需要运营人员手动恢复！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    dataModel.setType(Constant.STATE_FAILURE);
                    String model = JsonUtils.toString(dataModel);//对象转JSON
                    return AES.encode(model);//加密返回
                } else if (sysRobot.getRobotRunState().equals("90")) {//运营人员维护
                    if (sysRobot.getRobotRunState().equals(robotCode.getRobotRunState())) {
                        dataModel.setData("");
                        dataModel.setMsg("机器人已是当前状态，不能重复操作！");
                        dataModel.setState(Constant.STATE_FAILURE);
                        String model = JsonUtils.toString(dataModel);//对象转JSON
                        return AES.encode(model);//加密返回
                    }
                    if (robotCode.getRobotRunState().equals("10")) {
                        dataModel.setData("");
                        dataModel.setMsg("管理系统推送机器人状态！");
                        dataModel.setState(Constant.STATE_SUCCESS);
                        dataModel.setType(Constant.OPERATOR_MAINTENANCE);
                        // 转JSON格式发送到个推
                        String robotUnlock = JsonUtils.toString(dataModel);
                        String encode = AES.encode(robotUnlock);// 加密推送
                        // 个推推送消息到APP端
                        if ("0".equals(robotCode.getRobotAppType())) {
                            String isSuccess = WeChatGtRobotAppPush.singlePush(robotCode.getRobotCodeCid(), encode, "成功!");
                            if ("1".equals(isSuccess)) {
                                SysAppModificationLog modificationLog = new SysAppModificationLog();
                                modificationLog.setModificationLogId(IdUtils.getSeqId());
                                modificationLog.setModificationLogLoginName(appUsers.getLoginName());
                                modificationLog.setModificationLogRobotCode(robotCode.getRobotCode());
                                modificationLog.setModificationLogFront(DictUtils.getRobotRunMap().get(robotCode.getRobotRunState()));
                                modificationLog.setModificationLogAfter("闲置");
                                modificationLog.setCreateDate(DateUtil.currentDateTime());
                                modificationLog.setUpdateDate(DateUtil.currentDateTime());
                                //添加修改日志
                                sysRobotService.saveModificationLog(modificationLog);
                                dataModel.setData("");
                                dataModel.setMsg("发送成功！");
                                dataModel.setState(Constant.STATE_SUCCESS);
                                dataModel.setType(Constant.STATE_SUCCESS);
                                String model = JsonUtils.toString(dataModel);//对象转JSON
                                return AES.encode(model);//加密返回
                            } else {
                                dataModel.setData("");
                                dataModel.setMsg("发送失败！");
                                dataModel.setState(Constant.STATE_FAILURE);
                                dataModel.setType(Constant.STATE_FAILURE);
                                String model = JsonUtils.toString(dataModel);//对象转JSON
                                return AES.encode(model);//加密返回
                            }
                        } else {
                            String isSuccessN = WeChatGtRobotAppPush.singlePushNewPadApp(robotCode.getNewRobotCodeCid(), encode, "成功!");
                            if ("1".equals(isSuccessN)) {
                                SysAppModificationLog modificationLog = new SysAppModificationLog();
                                modificationLog.setModificationLogId(IdUtils.getSeqId());
                                modificationLog.setModificationLogLoginName(appUsers.getLoginName());
                                modificationLog.setModificationLogRobotCode(robotCode.getRobotCode());
                                modificationLog.setModificationLogFront(DictUtils.getRobotRunMap().get(robotCode.getRobotRunState()));
                                modificationLog.setModificationLogAfter("闲置");
                                modificationLog.setCreateDate(DateUtil.currentDateTime());
                                modificationLog.setUpdateDate(DateUtil.currentDateTime());
                                //添加修改日志
                                sysRobotService.saveModificationLog(modificationLog);
                                dataModel.setData("");
                                dataModel.setMsg("发送成功！");
                                dataModel.setState(Constant.STATE_SUCCESS);
                                dataModel.setType(Constant.STATE_SUCCESS);
                                String model = JsonUtils.toString(dataModel);//对象转JSON
                                return AES.encode(model);//加密返回
                            } else {
                                dataModel.setData("");
                                dataModel.setMsg("发送失败！");
                                dataModel.setState(Constant.STATE_FAILURE);
                                dataModel.setType(Constant.STATE_FAILURE);
                                String model = JsonUtils.toString(dataModel);//对象转JSON
                                return AES.encode(model);//加密返回
                            }
                        }
                    } else {
                        //无法修改状态
                        dataModel.setData("");
                        dataModel.setMsg("无法修改状态！");
                        dataModel.setState(Constant.STATE_FAILURE);
                        String model = JsonUtils.toString(dataModel);//对象转JSON
                        return AES.encode(model);//加密返回
                    }
                } else if (sysRobot.getRobotRunState().equals("100")) {
                    //无法修改状态
                    dataModel.setData("");
                    dataModel.setMsg("不能执行此操作！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    String model = JsonUtils.toString(dataModel);//对象转JSON
                    return AES.encode(model);//加密返回
                }
            }
        } catch (Exception e) {
            logger.info("修改状态", e);
            dataModel.setData("");
            dataModel.setMsg("状态修改失败，请联系后台开发人员！");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
        return content;
    }

    /**
     * @return java.lang.String
     * @Author 郭凯
     * @Description 更新userClientGtId
     * @Date 9:04 2020/11/24
     * @Param [content]
     **/
    @RequestMapping("/updateUserClientGtId.do")
    @ResponseBody
    public String updateUserClientGtId(String content) {
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
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //用户ID
            String userId = jsonobject.getString("userId");
            //判断用户ID是否为空，如果为空，直接return
            if (userId == "" || userId == null) {
                dataModel.setData("");
                dataModel.setMsg("userId为空，请传入userId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //userClientGtId
            String userClientGtId = jsonobject.getString("userClientGtId");
            //判断userClientGtId是否为空，如果为空，直接return
            if (userClientGtId == "" || userClientGtId == null) {
                dataModel.setData("");
                dataModel.setMsg("userClientGtId为空，请传入userClientGtId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            SysAppUsers sysAppUsers = new SysAppUsers();
            sysAppUsers.setUserId(Long.parseLong(userId));
            sysAppUsers.setUserClientGtId(userClientGtId);
            sysAppUsers.setUpdateDate(DateUtil.currentDateTime());
            int i = appUserService.updateUserClientGtId(sysAppUsers);
            if (i > 0) {
                dataModel.setData("");
                dataModel.setMsg("userClientGtId更新成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            } else {
                dataModel.setData("");
                dataModel.setMsg("userClientGtId更新失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
        } catch (Exception e) {
            // TODO: handle exception
            dataModel.setData("");
            dataModel.setMsg("userClientGtId更新失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * @return java.lang.String
     * @Author 郭凯
     * @Description APP最新资源下载
     * @Date 9:13 2020/11/24
     * @Param [content]
     **/
    @RequestMapping("/AppAdministratorsVersion.do")
    @ResponseBody
    public String AppAdministratorsVersion(String content) {
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
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            SysRobotAdministratorsVersion administratorsVersion = sysRobotAdministratorsVersionService.getAdministratorsVersion();
            dataModel.setData(administratorsVersion);
            dataModel.setMsg("APP资源查询成功！");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            // TODO: handle exception
            dataModel.setData("");
            dataModel.setMsg("APP资源查询成功！");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * @return java.lang.String
     * @Author 郭凯
     * @Description VIP启动
     * @Date 9:17 2020/11/24
     * @Param [content]
     **/
    @RequestMapping(value = "/editRobotVipInterface.do")
    @ResponseBody
    public String editRobotVipInterface(String content) {
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
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //机器人ID
            String robotCode = jsonobject.getString("robotCode");
            //判断机器人ID是否为空，如果为空，直接return
            if (robotCode == null || robotCode == "") {
                dataModel.setData("");
                dataModel.setMsg("机器人编号为空，请传入机器人编号!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            // 查询机器人正在进行中订单
            SysOrder order = sysOrderService.getOrderStateByRobotCode(robotCode, "10");
            if (order != null) {
                dataModel.setData("");
                dataModel.setMsg("当前机器人正在运行中，无法VIP启动");
                dataModel.setState(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            // 根据机器人id查询机器人
            SysRobot robot = sysRobotService.getRobotCodeBy(robotCode);
            if (robot == null) {
                dataModel.setData("");
                dataModel.setMsg("未查询到机器人");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            } else {
                if ("10".equals(robot.getRobotRunState())) {
                    dataModel.setData(appUsers);
                    dataModel.setMsg("管理系统推送机器人状态！");
                    dataModel.setState(Constant.STATE_SUCCESS);
                    dataModel.setType(Constant.VIP_UNLOCKING);
                    // 转JSON格式发送到个推
                    String robotUnlock = JsonUtils.toString(dataModel);
                    String encode = AES.encode(robotUnlock);// 加密推送
                    // 个推推送消息到APP端
                    String isSuccess = WeChatGtRobotAppPush.singlePush(robot.getRobotCodeCid(), encode, "成功!");
                    if ("1".equals(isSuccess)) {
                        SysAppModificationLog modificationLog = new SysAppModificationLog();
                        modificationLog.setModificationLogId(IdUtils.getSeqId());
                        modificationLog.setModificationLogLoginName(appUsers.getLoginName());
                        modificationLog.setModificationLogRobotCode(robot.getRobotCode());
                        modificationLog.setModificationLogFront(DictUtils.getRobotRunMap().get(robot.getRobotRunState()));
                        modificationLog.setModificationLogAfter("VIP解锁");
                        modificationLog.setCreateDate(DateUtil.currentDateTime());
                        modificationLog.setUpdateDate(DateUtil.currentDateTime());
                        //添加修改日志
                        sysRobotService.saveModificationLog(modificationLog);
                        dataModel.setData("");
                        dataModel.setMsg("发送成功！");
                        dataModel.setState(Constant.STATE_SUCCESS);
                        String model = JsonUtils.toString(dataModel);//对象转JSON
                        return AES.encode(model);//加密返回
                    } else {
                        dataModel.setData("");
                        dataModel.setMsg("发送失败！");
                        dataModel.setState(Constant.STATE_FAILURE);
                        String model = JsonUtils.toString(dataModel);//对象转JSON
                        return AES.encode(model);//加密返回
                    }
                } else {
                    dataModel.setData("");
                    dataModel.setMsg("当前机器人不是闲置状态！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    dataModel.setType(Constant.STATE_FAILURE);
                    String model = JsonUtils.toString(dataModel);//对象转JSON
                    return AES.encode(model);//加密返回
                }
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("状态修改失败，请联系后台开发人员！");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * @param @param  content
     * @param @return
     * @return String
     * @throws
     * @Author 郭凯
     * @Description: 刷新修改管理员当前位置
     * @Title: updateUserGPS
     * @date 2021年2月25日 上午10:24:10
     */
    @RequestMapping("/updateUserGPS.do")
    @ResponseBody
    public String updateUserGPS(String content) {
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
            //userTtpe
            String userGps = jsonobject.getString("userGps");
            //判断longinTokenId是否为空，如果为空，直接return
            if (userGps == "" || userGps == null) {
                dataModel.setData("");
                dataModel.setMsg("userGps为空，请传入userGps!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            appUsers.setUserGps(userGps);
            appUsers.setUpdateDate(DateUtil.currentDateTime());
            int i = appUserService.updateUserClientGtId(appUsers);
            if (i > 0) {
                dataModel.setData("");
                dataModel.setMsg("管理员GPS更新成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            } else {
                dataModel.setData("");
                dataModel.setMsg("管理员GPS更新失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
        } catch (Exception e) {
            // TODO: handle exception
            dataModel.setData("");
            dataModel.setMsg("管理员GPS更新失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * @Method getRobotGPS
     * @Author 郭凯
     * @Version 1.0
     * @Description 在redis获取机器人位置
     * @Return java.lang.String
     * @Date 2021/3/24 12:57
     */
    @RequestMapping("/getRobotGPS.do")
    @ResponseBody
    public String getRobotGPS(String content) {
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
            //景区ID
            String scenicSpotId = jsonobject.getString("scenicSpotId");
            //机器人编号
            String robotCode = jsonobject.getString("robotCode");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(scenicSpotId) && ToolUtil.isEmpty(robotCode)) {
                dataModel.setData("");
                dataModel.setMsg("景区ID和机器人编号为空!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            Map<String, Object> search = new HashMap<>();
            search.put("scenicSpotId", scenicSpotId);
            search.put("robotCode", robotCode);
            //查询机器人列表
            List<SysRobotGPS> robotList = sysRobotService.getRobotGpsList(search);
            if (robotList.size() > 0) {
                dataModel.setMsg("成功");
                dataModel.setData(robotList);
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            } else {
                dataModel.setData("");
                dataModel.setMsg("当前景区未查询到机器人");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("日志：", e);
            dataModel.setData("");
            dataModel.setMsg("错误");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * @Method getRobotInformation
     * @Author 郭凯
     * @Version 1.0
     * @Description 机器人详情页面数据接口
     * @Return java.lang.String
     * @Date 2021/3/24 17:58
     */
    @RequestMapping("/getRobotInformation.do")
    @ResponseBody
    public String getRobotInformation(String content) {
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
            //机器人编号
            String robotCode = jsonobject.getString("robotCode");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(robotCode)) {
                dataModel.setData("");
                dataModel.setMsg("机器人编号为空!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String scenicSpotId = jsonobject.getString("scenicSpotId");
            String unusualId = jsonobject.getString("unusualId");
            Map<String, Object> search = new HashMap<>();
            search.put("robotCode", robotCode);
            if (!StringUtils.isEmpty(scenicSpotId)) {
                search.put("scenicSpotId", scenicSpotId);
            }
            if (!StringUtils.isEmpty(unusualId)) {
                search.put("unusualId", unusualId);
            }
            //查询机器人列表
            List<SysRobotGPS> robotList = sysRobotService.getRobotGpsList(search);
            if (robotList.size() > 0) {
                dataModel.setMsg("成功");
                dataModel.setData(robotList);
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            } else {
                dataModel.setData("");
                dataModel.setMsg("未查询到数据");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("日志：", e);
            dataModel.setData("");
            dataModel.setMsg("错误");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * @Method orderHistoryRevenue
     * @Author 郭凯
     * @Version 1.0
     * @Description 管理者APP机器人报警日志列表查询
     * @Return java.lang.String
     * @Date 2021/5/21 16:27
     */
    @RequestMapping("/getRobotFauleList.do")
    @ResponseBody
    public String getRobotFauleList(BaseQueryVo BaseQueryVo, String content) {
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
            //机器人编号
            String robotCode = jsonobject.getString("robotCode");
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //开始时间
            String startTime = jsonobject.getString("startTime");
            //结束时间
            String endTime = jsonobject.getString("endTime");
            Map<String, String> search = new HashMap<>();
            search.put("startTime", startTime);
            search.put("endTime", endTime);
            search.put("robotCode", robotCode);
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
                if (ToolUtil.isEmpty(robotCode)) {
                    search.put("time", DateUtil.crutDate());
                }
            }
            BaseQueryVo.setSearch(search);
            PageInfo<SysRobotFaule> page = sysRobotFauleService.getRobotFauleList(BaseQueryVo);
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("list", page.getList());
            dataMap.put("pages", page.getPages());
            dataMap.put("pageNum", page.getPageNum());
            dataModel.setData(dataMap);
            dataModel.setMsg("");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * @Method orderHistoryRevenue
     * @Author 郭凯
     * @Version 1.0
     * @Description 管理者APP获取停放点机器人列表
     * @Return java.lang.String
     * @Date 2021/5/21 16:27
     */
    @RequestMapping("/getRobotParkingList.do")
    @ResponseBody
    public String getRobotParkingList(BaseQueryVo BaseQueryVo, String content) {
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
            //机器人编号
            String spotId = jsonobject.getString("spotId");

            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            if (spotId == null || spotId == "") {
                dataModel.setData("");
                dataModel.setMsg("景区id为空，请传入景区id!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }


            Map<String, Object> map = sysRobotService.getRobotParkingList(spotId);
            dataModel.setData(map);
            dataModel.setMsg("");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }

    }

    /**
     * 根据机器人编号获取机器人卡号
     *
     * @param BaseQueryVo
     * @param content
     * @return
     */
    @RequestMapping("/getRobotCodeSim")
    @ResponseBody
    public ReturnModel getRobotCodeSim(String robotCode) {

        ReturnModel returnModel = new ReturnModel();

        SysRobot robotCodeBy = sysRobotService.getRobotCodeBy(robotCode);

        returnModel.setData(robotCodeBy.getRobotCodeSim());
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功");

        return returnModel;
    }


    @ApiOperation(value = "根据景区查询机器人下拉选")
    @ResponseBody
    @PostMapping("/getSpotIdByRobotList")
    public String getSpotIdByRobotList(String content) {

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
            String scenicSpotId = jsonobject.getString("scenicSpotId");
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

            List<SysRobot> list = sysRobotService.getSpotIdByRobotList(scenicSpotId);
            dataModel.setData(list);
            dataModel.setMsg("");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
//            log.info("getAppAccessoriesApplicationType",e);
            e.printStackTrace();
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }


}
