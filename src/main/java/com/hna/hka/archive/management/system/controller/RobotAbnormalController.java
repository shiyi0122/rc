package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysOrder;
import com.hna.hka.archive.management.system.model.SysRobot;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.service.SysOrderService;
import com.hna.hka.archive.management.system.service.SysRobotService;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/5/7 19:33
 * 异常机器人管理
 */
@Api(tags = "异常机器人管理")
@RequestMapping("/system/restrictedAreat")
@Controller
public class RobotAbnormalController extends PublicUtil {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpSession session;

    @Autowired
    private SysRobotService sysRobotService;

    @Autowired
    private SysOrderService sysOrderService;

    /**
     * 异常列表
     * @param pageNum
     * @param pageSize
     * @param sysRobot
     * @return
     *
     */

    @ApiOperation("异常列表")
    @RequestMapping("/getRestrictedAretList")
    @ResponseBody
    public PageDataResult getRestrictedAretList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize, SysRobot sysRobot) {
            PageDataResult pageDataResult = new PageDataResult();

        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("scenicSpotId",sysRobot.getScenicSpotId());
//            if (StringUtils.isEmpty(sysRobot.getScenicSpotId())){
//                search.put("scenicSpotId",session.getAttribute("scenicSpotId"));
//            }else{
//                search.put("scenicSpotId",sysRobot.getScenicSpotId());
//            }
            search.put("robotCode",sysRobot.getRobotCode());

            pageDataResult = sysRobotService.getRestrictedAretList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("机器人异常列表查询失败",e);
        }
        return pageDataResult;
    }


    /**
     * 修改机器人状态
     * @param request
     * @param robotId
     * @param robotRunState
     * @return
     */
    @RequestMapping("/updateRobotRunState")
    @ResponseBody
    public ReturnModel editRobot(HttpServletRequest request, String robotId, String robotRunState) {
        ReturnModel returnModel = new ReturnModel();
        SysRobot sysRobot = new SysRobot();
        sysRobot.setRobotId(Long.parseLong(robotId));
        sysRobot.setRobotRunState(robotRunState);
        try {
            // 根据机器人id查询机器人
            SysRobot robotCode = sysRobotService.getRobotRunState(Long.parseLong(robotId));
            if (ToolUtil.isEmpty(robotCode)) {
                returnModel.setData("");
                returnModel.setMsg("未查询到机器人");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else {
                if (sysRobot.getRobotRunState().equals("10")) {// 如果传进来是闲置
                    if (sysRobot.getRobotRunState().equals(robotCode.getRobotRunState())) {
                        returnModel.setData("");
                        returnModel.setMsg("机器人已是当前状态，不能重复操作！");
                        returnModel.setState(Constant.STATE_FAILURE);
                        return returnModel;
                    }
                    //查询机器人是否有正在进行中订单
                    SysOrder order = sysOrderService.getOrderStateByRobotCode(robotCode.getRobotCode(),"10");
                    if (order != null) {
                        returnModel.setData("");
                        returnModel.setMsg("当前机器人正在运行中，无法修改闲置状态");
                        returnModel.setState(Constant.STATE_FAILURE);
                        return returnModel;
                    }
                    if (robotCode.getRobotRunState().equals("40") || robotCode.getRobotRunState().equals("50") || robotCode.getRobotRunState().equals("70") || robotCode.getRobotRunState().equals("20") || robotCode.getRobotRunState().equals("30") || "90".equals(robotCode.getRobotRunState()) || robotCode.getRobotRunState().equals("100")) {
                        returnModel.setData("");
                        returnModel.setMsg("管理系统推送机器人状态！");
                        returnModel.setState(Constant.STATE_FAILURE);
                        returnModel.setType(Constant.ROBOT_IDLE_STATE);
                        // 转JSON格式发送到个推
                        String robotUnlock = JsonUtils.toString(returnModel);
                        String encode = AES.encode(robotUnlock);// 加密推送
                        // 个推推送消息到APP端
                        String isSuccess = WeChatGtRobotAppPush.singlePush(robotCode.getRobotCodeCid(), encode, "成功!");
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
                    }else {
                        returnModel.setData("");
                        returnModel.setMsg("不能执行此操作！");
                        returnModel.setState(Constant.STATE_FAILURE);
                        return returnModel;
                    }
                }
//                else if (sysRobot.getRobotRunState().equals("20")) {// 如果传进来是用户解锁
//                    if (robotCode.getRobotRunState().equals("30") || robotCode.getRobotRunState().equals("100")) {
//                        returnModel.setData("");
//                        returnModel.setMsg("管理系统推送机器人状态！");
//                        returnModel.setState(Constant.STATE_SUCCESS);
//                        returnModel.setType(Constant.ROBOT_UNLOCKING);
//                        // 转JSON格式发送到个推
//                        String robotUnlock = JsonUtils.toString(returnModel);
//                        String encode = AES.encode(robotUnlock);// 加密推送
//                        // 个推推送消息到APP端
//                        String isSuccess = WeChatGtRobotAppPush.singlePush(robotCode.getRobotCodeCid(), encode, "成功!");
//                        if ("1".equals(isSuccess)) {
//                            returnModel.setData("");
//                            returnModel.setMsg("发送成功！");
//                            returnModel.setState(Constant.STATE_SUCCESS);
//                            return returnModel;
//                        } else {
//                            returnModel.setData("");
//                            returnModel.setMsg("发送失败！");
//                            returnModel.setState(Constant.STATE_FAILURE);
//                            return returnModel;
//                        }
//                    }else {
//                        returnModel.setData("");
//                        returnModel.setMsg("不能执行此操作！");
//                        returnModel.setState(Constant.STATE_FAILURE);
//                        return returnModel;
//                    }
//                } else if (sysRobot.getRobotRunState().equals("30")) {// 如果传进来是用户临时锁定,则不能修改
//                    returnModel.setData("");
//                    returnModel.setMsg("不能执行此操作！");
//                    returnModel.setState(Constant.STATE_FAILURE);
//                    return returnModel;
//                } else if (sysRobot.getRobotRunState().equals("40")) {// 如果传进来是管理员解锁
//                    if (sysRobot.getRobotRunState().equals(robotCode.getRobotRunState())) {
//                        returnModel.setData("");
//                        returnModel.setMsg("机器人已是当前状态，不能重复操作！");
//                        returnModel.setState(Constant.STATE_FAILURE);
//                        return returnModel;
//                    }
//                    if (robotCode.getRobotRunState().equals("10") || robotCode.getRobotRunState().equals("50")) {// 只能是状态为显示的状态下修改
//                        returnModel.setData("");
//                        returnModel.setMsg("管理系统推送机器人状态！");
//                        returnModel.setState(Constant.STATE_SUCCESS);
//                        returnModel.setType(Constant.ADMINISTRATOR_UNLOCKING_ROBOT);
//                        // 转JSON格式发送到个推
//                        String robotUnlock = JsonUtils.toString(returnModel);
//                        String encode = AES.encode(robotUnlock);// 加密推送
//                        // 个推推送消息到APP端
//                        String isSuccess = WeChatGtRobotAppPush.singlePush(robotCode.getRobotCodeCid(), encode, "成功!");
//                        if ("1".equals(isSuccess)) {
//                            returnModel.setData("");
//                            returnModel.setMsg("发送成功！");
//                            returnModel.setState(Constant.STATE_SUCCESS);
//                            return returnModel;
//                        } else {
//                            returnModel.setData("");
//                            returnModel.setMsg("发送失败！");
//                            returnModel.setState(Constant.STATE_FAILURE);
//                            return returnModel;
//                        }
//                    }else {
//                        //无法修改状态
//                        returnModel.setData("");
//                        returnModel.setMsg("无法修改状态！");
//                        returnModel.setState(Constant.STATE_FAILURE);
//                        return returnModel;
//                    }
//                } else if (sysRobot.getRobotRunState().equals("50")) {// 如果传进来是管理员锁定
//                    if (sysRobot.getRobotRunState().equals(robotCode.getRobotRunState())) {
//                        returnModel.setData("");
//                        returnModel.setMsg("机器人已是当前状态，不能重复操作！");
//                        returnModel.setState(Constant.STATE_FAILURE);
//                        return returnModel;
//                    }
//                    if (robotCode.getRobotRunState().equals("10") || robotCode.getRobotRunState().equals("40")) {
//                        returnModel.setData("");
//                        returnModel.setMsg("管理系统推送机器人状态！");
//                        returnModel.setState(Constant.STATE_SUCCESS);
//                        returnModel.setType(Constant.ADMINISTRATOR_LOCKS_ROBOT);
//                        // 转JSON格式发送到个推
//                        String robotUnlock = JsonUtils.toString(returnModel);
//                        String encode = AES.encode(robotUnlock);// 加密推送
//                        // 个推推送消息到APP端
//                        String isSuccess = WeChatGtRobotAppPush.singlePush(robotCode.getRobotCodeCid(), encode, "成功!");
//                        if ("1".equals(isSuccess)) {
//                            returnModel.setData("");
//                            returnModel.setMsg("发送成功！");
//                            returnModel.setState(Constant.STATE_SUCCESS);
//                            return returnModel;
//                        } else {
//                            returnModel.setData("");
//                            returnModel.setMsg("发送失败！");
//                            returnModel.setState(Constant.STATE_FAILURE);
//                            return returnModel;
//                        }
//                    }else {
//                        //无法修改状态
//                        returnModel.setData("");
//                        returnModel.setMsg("无法修改状态！");
//                        returnModel.setState(Constant.STATE_FAILURE);
//                        return returnModel;
//                    }
//                } else if (sysRobot.getRobotRunState().equals("60")) {
//                    returnModel.setData("");
//                    returnModel.setMsg("后台管理人员无法操作此状态  必须有运营人员修复机器人或者管理者APP下发自检指令时修复！");
//                    returnModel.setState(Constant.STATE_FAILURE);
//                    return returnModel;
//                }  else if (sysRobot.getRobotRunState().equals("70")) {
//                    returnModel.setData("");
//                    returnModel.setMsg("此状态不能修改！");
//                    returnModel.setState(Constant.STATE_FAILURE);
//                    return returnModel;
//                }  else if (sysRobot.getRobotRunState().equals("80")) {
//                    returnModel.setData("");
//                    returnModel.setMsg("此状态不能修改，需要运营人员手动恢复！");
//                    returnModel.setState(Constant.STATE_FAILURE);
//                    return returnModel;
//                }	else if (sysRobot.getRobotRunState().equals("90")) {//运营人员维护
//                    if (sysRobot.getRobotRunState().equals(robotCode.getRobotRunState())) {
//                        returnModel.setData("");
//                        returnModel.setMsg("机器人已是当前状态，不能重复操作！");
//                        returnModel.setState(Constant.STATE_FAILURE);
//                        return returnModel;
//                    }
//                    if (robotCode.getRobotRunState().equals("10")) {
//                        returnModel.setData("");
//                        returnModel.setMsg("管理系统推送机器人状态！");
//                        returnModel.setState(Constant.STATE_SUCCESS);
//                        returnModel.setType(Constant.OPERATOR_MAINTENANCE);
//                        // 转JSON格式发送到个推
//                        String robotUnlock = JsonUtils.toString(returnModel);
//                        String encode = AES.encode(robotUnlock);// 加密推送
//                        // 个推推送消息到APP端
//                        String isSuccess = WeChatGtRobotAppPush.singlePush(robotCode.getRobotCodeCid(), encode, "成功!");
//                        if ("1".equals(isSuccess)) {
//                            returnModel.setData("");
//                            returnModel.setMsg("发送成功！");
//                            returnModel.setState(Constant.STATE_SUCCESS);
//                            return returnModel;
//                        } else {
//                            returnModel.setData("");
//                            returnModel.setMsg("发送失败！");
//                            returnModel.setState(Constant.STATE_FAILURE);
//                            return returnModel;
//                        }
//                    }else {
//                        //无法修改状态
//                        returnModel.setData("");
//                        returnModel.setMsg("无法修改状态！");
//                        returnModel.setState(Constant.STATE_FAILURE);
//                        return returnModel;
//                    }
//                } else if (sysRobot.getRobotRunState().equals("100")){
//                    returnModel.setData("");
//                    returnModel.setMsg("不能执行此操作！");
//                    returnModel.setState(Constant.STATE_FAILURE);
//                    return returnModel;
//                }
            }
        } catch (Exception e) {
            logger.error("updateRobotRunState", e);
            returnModel.setData("");
            returnModel.setMsg("状态修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
        return returnModel;
    }














}
