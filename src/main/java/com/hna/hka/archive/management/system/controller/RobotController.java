package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.dto.SysRobotIdDTO;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.service.SysOrderService;
import com.hna.hka.archive.management.system.service.SysRobotDispatchLogService;
import com.hna.hka.archive.management.system.service.SysRobotService;
import com.hna.hka.archive.management.system.service.SysScenicSpotService;
import com.hna.hka.archive.management.system.util.*;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: RobotController
 * @Author: 郭凯
 * @Description: 机器人管理控制层
 * @Date: 2020/5/20 9:21
 * @Version: 1.0
 */
@Api(tags = "机器人")
@RequestMapping("/system/robot")
@Controller
@CrossOrigin
public class RobotController extends PublicUtil {

    @Autowired
    private SysRobotService sysRobotService;

    @Autowired
    private SysScenicSpotService sysScenicSpotService;

    @Autowired
    private SysRobotDispatchLogService sysRobotDispatchLogService;

    @Autowired
    private SysOrderService sysOrderService;

    @Autowired
    private HttpServletRequest request;

    @Value("${uploadPath}")
    private String uploadPath;


    /**
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     * @Author 郭凯
     * @Description 查询机器人列表
     * @Date 9:24 2020/5/20
     * @Param [pageNum, pageSize, sysRobot]
     **/
    @RequestMapping("/getRobotList")
    @ResponseBody
    public PageDataResult getRoleList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, SysRobot sysRobot, String type) {
        PageDataResult pageDataResult = new PageDataResult();
        SysUsers SysUsers = this.getSysUser();
        Map<String, Object> search = new HashMap<>();
        try {
            if (null == pageNum) {
                pageNum = 1;
            }
            if (null == pageSize) {
                pageSize = 10;
            }

            if (StringUtils.isEmpty(type)) {
                search.put("userId", SysUsers.getUserId().toString());
                search.put("scenicSpotId", sysRobot.getScenicSpotId());
                search.put("robotCode", sysRobot.getRobotCode());
                search.put("robotCodeSim", sysRobot.getRobotCodeSim());
                search.put("robotRunState", sysRobot.getRobotRunState());
                pageDataResult = sysRobotService.getRobotList(pageNum, pageSize, search);
            } else {
                search.put("userId", SysUsers.getUserId().toString());
                search.put("scenicSpotId", sysRobot.getScenicSpotId());
                search.put("robotCode", sysRobot.getRobotCode());
                search.put("robotCodeSim", sysRobot.getRobotCodeSim());
                search.put("robotRunState", sysRobot.getRobotRunState());
                search.put("clientVersion", sysRobot.getClientVersion());
                search.put("type", type);
                pageDataResult = sysRobotService.getRobotList(pageNum, pageSize, search);

            }


        } catch (Exception e) {
            logger.info("机器人列表查询失败", e);
        }
        return pageDataResult;
    }


    /**
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     * @Author 郭凯
     * @Description 查询机器人列表
     * @Date 9:24 2020/5/20
     * @Param [pageNum, pageSize, sysRobot]
     **/
    @RequestMapping("/getRobotZFCList")
    @ResponseBody
    public PageDataResult getRobotZFCList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, SysRobot sysRobot, String type) {
        PageDataResult pageDataResult = new PageDataResult();
        SysUsers SysUsers = this.getSysUser();
        Map<String, Object> search = new HashMap<>();
        try {
            if (null == pageNum) {
                pageNum = 1;
            }
            if (null == pageSize) {
                pageSize = 10;
            }

            if (StringUtils.isEmpty(type)) {
                search.put("userId", SysUsers.getUserId().toString());
                search.put("scenicSpotId", sysRobot.getScenicSpotId());
                search.put("robotCode", sysRobot.getRobotCode());
                search.put("robotCodeSim", sysRobot.getRobotCodeSim());
                search.put("robotRunState", sysRobot.getRobotRunState());
                pageDataResult = sysRobotService.getRobotZGCList(pageNum, pageSize, search);
            } else {
                search.put("userId", SysUsers.getUserId().toString());
                search.put("scenicSpotId", sysRobot.getScenicSpotId());
                search.put("robotCode", sysRobot.getRobotCode());
                search.put("robotCodeSim", sysRobot.getRobotCodeSim());
                search.put("robotRunState", sysRobot.getRobotRunState());
                search.put("clientVersion", sysRobot.getClientVersion());
                search.put("type", type);
                pageDataResult = sysRobotService.getRobotList(pageNum, pageSize, search);

            }


        } catch (Exception e) {
            logger.info("机器人列表查询失败", e);
        }
        return pageDataResult;
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 查询机器人状态
     * @Date 13:34 2020/5/20
     * @Param []
     **/
    @RequestMapping("/getRobotRunStateName")
    @ResponseBody
    public ReturnModel getRobotRunStateName() {
        ReturnModel returnModel = new ReturnModel();
        try {
            List<SysRobot> sysRobotList = new ArrayList<SysRobot>();
            for (String key : DictUtils.getRobotRunMap().keySet()) {
                String value = DictUtils.getRobotRunMap().get(key);
                SysRobot sysRobot = new SysRobot();
                sysRobot.setRobotRunState(key);
                sysRobot.setRobotRunStateName(value);
                sysRobotList.add(sysRobot);
            }
            returnModel.setData(sysRobotList);
        } catch (Exception e) {
            logger.info("getRobotRunStateName", e);
        }
        return returnModel;
    }


    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 回显状态，数据查询
     * @Date 13:53 2020/5/20
     * @Param [robotId]
     **/
    @RequestMapping("/getRobotRunState")
    @ResponseBody
    public ReturnModel getRobotRunState(@RequestParam("robotId") Long robotId) {
        ReturnModel returnModel = new ReturnModel();
        try {
            SysRobot sysRobot = sysRobotService.getRobotRunState(robotId);
            if (!ToolUtil.isEmpty(sysRobot)) {
                returnModel.setData(sysRobot);
                returnModel.setMsg("成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("查询失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("getRobotRunState", e);
        }
        return returnModel;
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 删除机器人
     * @Date 14:57 2020/5/20
     * @Param [robotId]
     **/
    @RequestMapping("/delRobot")
    @ResponseBody
    public ReturnModel delRobot(@RequestParam("robotId") Long robotId) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotService.delRobot(robotId);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("机器人删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("机器人删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("delRobot", e);
            returnModel.setData("");
            returnModel.setMsg("机器人删除失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 查询机器人ID
     * @Date 16:46 2020/5/20
     * @Param []
     **/
    @RequestMapping("/getRobotId")
    @ResponseBody
    public ReturnModel getRobotId() {
        ReturnModel returnModel = new ReturnModel();
        try {
            SysRobotId newSysRobotId = sysRobotService.getNewSysRobotId();
            if (!ToolUtil.isEmpty(newSysRobotId)) {
                returnModel.setData(newSysRobotId);
                returnModel.setMsg("成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("机器人ID失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("getRobotId", e);
            returnModel.setData("");
            returnModel.setMsg("机器人ID失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 添加机器人
     * @Date 16:58 2020/5/20
     * @Param [sysRobot]
     **/
    @RequestMapping("/addRobot")
    @ResponseBody
    public ReturnModel addRobot(SysRobot robot) {
        ReturnModel returnModel = new ReturnModel();
        try {
            //根据机器人编码查询是否有机器人
            SysRobot sysRobot = sysRobotService.getRobotCodeBy(robot.getRobotCode());
            if (ToolUtil.isEmpty(sysRobot)) {
                //查询SIM卡是否重复
                SysRobot robots = sysRobotService.getRobotSimById(robot.getRobotCodeSim());
                if (!ToolUtil.isEmpty(robots)) {
                    returnModel.setData("");
                    returnModel.setMsg("请不要重复添加相同的SIM卡号,请及时核对！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
                if (QrCodeUtil.qrCode(robot.getRobotCode())) {
                    int i = sysRobotService.addRobot(robot);
                    if (i == 1) {
                        SysRobotId newSysRobotId = sysRobotService.getNewSysRobotId();
                        if (newSysRobotId.getRobotCode().equals(robot.getRobotCode())) {
                            sysRobotService.updateSysRobotId(newSysRobotId);
                            returnModel.setData("");
                            returnModel.setMsg("成功添加机器人编号！");
                            returnModel.setState(Constant.STATE_SUCCESS);
                            return returnModel;
                        } else {
                            returnModel.setData("");
                            returnModel.setMsg("成功添加机器人编号！");
                            returnModel.setState(Constant.STATE_SUCCESS);
                            return returnModel;
                        }
                    }
                }
            } else if (!ToolUtil.isEmpty(sysRobot)) {
                if (QrCodeUtil.qrCode(robot.getRobotCode())) {
                    robot.setRobotId(sysRobot.getRobotId());
                    int i = sysRobotService.editRobotCode(robot);
                    if (i == 1) {
                        returnModel.setData("");
                        returnModel.setMsg("成功添加机器人编号！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        return returnModel;
                    } else {
                        returnModel.setData("");
                        returnModel.setMsg("添加机器人编号失败！");
                        returnModel.setState(Constant.STATE_FAILURE);
                        return returnModel;
                    }
                }
            }
        } catch (Exception e) {
            logger.info("addRobot", e);
            returnModel.setData("");
            returnModel.setMsg("机器人添加失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
        return returnModel;
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 修改机器人信息
     * @Date 11:18 2020/5/21
     * @Param [sysRobot]
     **/
    @RequestMapping("/editRobot")
    @ResponseBody
    public ReturnModel editRobot(SysRobot sysRobot) {
        ReturnModel dataModel = new ReturnModel();
        Subject subject = SecurityUtils.getSubject();
        SysUsers user = (SysUsers) subject.getPrincipal();
        try {
            //查询机器人所在景区
            SysRobot robot = sysRobotService.getRobotCodeBy(sysRobot.getRobotCode());
            //修改机器人信息
            int i = sysRobotService.updateByPrimaryKeySelective(sysRobot);
            if (i > 0) {
                if (!sysRobot.getScenicSpotId().equals(robot.getScenicSpotId())) {
                    SysScenicSpot scenicSpot = sysScenicSpotService.getSysScenicSpotById(sysRobot.getScenicSpotId());
                    SysScenicSpot sysScenicSpot = sysScenicSpotService.getSysScenicSpotById(robot.getScenicSpotId());
                    String scenicSpotNameOut = sysScenicSpot.getScenicSpotName();
                    String scenicSpotNameIn = scenicSpot.getScenicSpotName();
                    sysRobotDispatchLogService.insertRobotDispatchLog(user.getUserName(), scenicSpotNameOut, scenicSpotNameIn, sysRobot.getRobotCode());
                }
                dataModel.setData("");
                dataModel.setMsg("修改成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            } else {
                dataModel.setData("");
                dataModel.setMsg("修改失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            logger.error("editRobot", e);
            dataModel.setData("");
            dataModel.setMsg("修改失败！（请联系管理员！）");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 修改机器人状态
     * @Date 14:32 2020/5/20
     * @Param [request, robotId, robotRunState]
     **/
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
                    SysOrder order = sysOrderService.getOrderStateByRobotCode(robotCode.getRobotCode(), "10");
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
                        // 判断pad是旧版本还是重构版
                        if ("0".equals(robotCode.getRobotAppType())) {
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
                        } else if ("1".equals(robotCode.getRobotAppType())) {
                            String isSuccess = WeChatGtRobotAppPush.singlePushNew(robotCode.getNewRobotCodeCid(), encode, "成功!");
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
                        } else if ("2".equals(robotCode.getRobotAppType())) {
                            String isSuccess = WeChatGtRobotAppPush.singlePushAuto(robotCode.getNewRobotCodeCid(), encode, "成功!");
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
                        }
                    } else {
                        returnModel.setData("");
                        returnModel.setMsg("不能执行此操作！");
                        returnModel.setState(Constant.STATE_FAILURE);
                        return returnModel;
                    }
                } else if (sysRobot.getRobotRunState().equals("20")) {// 如果传进来是用户解锁
                    if (robotCode.getRobotRunState().equals("30") || robotCode.getRobotRunState().equals("100")) {
                        returnModel.setData("");
                        returnModel.setMsg("管理系统推送机器人状态！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        returnModel.setType(Constant.ROBOT_UNLOCKING);
                        // 转JSON格式发送到个推
                        String robotUnlock = JsonUtils.toString(returnModel);
                        String encode = AES.encode(robotUnlock);// 加密推送
                        // 个推推送消息到APP端
                        // 判断pad是旧版本还是重构版
                        if ("0".equals(robotCode.getRobotAppType())) {
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
                        } else {
                            String isSuccess = WeChatGtRobotAppPush.singlePushNew(robotCode.getNewRobotCodeCid(), encode, "成功!");
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
                        }
                    } else {
                        returnModel.setData("");
                        returnModel.setMsg("不能执行此操作！");
                        returnModel.setState(Constant.STATE_FAILURE);
                        return returnModel;
                    }
                } else if (sysRobot.getRobotRunState().equals("30")) {// 如果传进来是用户临时锁定,则不能修改
                    returnModel.setData("");
                    returnModel.setMsg("不能执行此操作！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                } else if (sysRobot.getRobotRunState().equals("40")) {// 如果传进来是管理员解锁
                    if (sysRobot.getRobotRunState().equals(robotCode.getRobotRunState())) {
                        returnModel.setData("");
                        returnModel.setMsg("机器人已是当前状态，不能重复操作！");
                        returnModel.setState(Constant.STATE_FAILURE);
                        return returnModel;
                    }
                    if (robotCode.getRobotRunState().equals("10") || robotCode.getRobotRunState().equals("50") || robotCode.getRobotRunState().equals("70")) {// 只能是状态为显示的状态下修改
                        returnModel.setData("");
                        returnModel.setMsg("管理系统推送机器人状态！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        returnModel.setType(Constant.ADMINISTRATOR_UNLOCKING_ROBOT);
                        // 转JSON格式发送到个推
                        String robotUnlock = JsonUtils.toString(returnModel);
                        String encode = AES.encode(robotUnlock);// 加密推送
                        // 个推推送消息到APP端
                        // 判断pad是旧版本还是重构版
                        if ("0".equals(robotCode.getRobotAppType())) {
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
                        } else {
                            String isSuccess = WeChatGtRobotAppPush.singlePushNew(robotCode.getNewRobotCodeCid(), encode, "成功!");
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
                        }
                    } else {
                        //无法修改状态
                        returnModel.setData("");
                        returnModel.setMsg("无法修改状态！");
                        returnModel.setState(Constant.STATE_FAILURE);
                        return returnModel;
                    }
                } else if (sysRobot.getRobotRunState().equals("50")) {// 如果传进来是管理员锁定
                    if (sysRobot.getRobotRunState().equals(robotCode.getRobotRunState())) {
                        returnModel.setData("");
                        returnModel.setMsg("机器人已是当前状态，不能重复操作！");
                        returnModel.setState(Constant.STATE_FAILURE);
                        return returnModel;
                    }
                    if (robotCode.getRobotRunState().equals("10") || robotCode.getRobotRunState().equals("40")) {
                        returnModel.setData("");
                        returnModel.setMsg("管理系统推送机器人状态！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        returnModel.setType(Constant.ADMINISTRATOR_LOCKS_ROBOT);
                        // 转JSON格式发送到个推
                        String robotUnlock = JsonUtils.toString(returnModel);
                        String encode = AES.encode(robotUnlock);// 加密推送
                        // 个推推送消息到APP端
                        // 判断pad是旧版本还是重构版
                        if ("0".equals(robotCode.getRobotAppType())) {
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
                        } else {
                            String isSuccess = WeChatGtRobotAppPush.singlePushNew(robotCode.getNewRobotCodeCid(), encode, "成功!");
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
                        }
                    } else {
                        //无法修改状态
                        returnModel.setData("");
                        returnModel.setMsg("无法修改状态！");
                        returnModel.setState(Constant.STATE_FAILURE);
                        return returnModel;
                    }
                } else if (sysRobot.getRobotRunState().equals("60")) {
                    returnModel.setData("");
                    returnModel.setMsg("后台管理人员无法操作此状态  必须有运营人员修复机器人或者管理者APP下发自检指令时修复！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                } else if (sysRobot.getRobotRunState().equals("70")) {
                    returnModel.setData("");
                    returnModel.setMsg("此状态不能修改！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                } else if (sysRobot.getRobotRunState().equals("80")) {
                    returnModel.setData("");
                    returnModel.setMsg("此状态不能修改，需要运营人员手动恢复！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                } else if (sysRobot.getRobotRunState().equals("90")) {//运营人员维护
                    if (sysRobot.getRobotRunState().equals(robotCode.getRobotRunState())) {
                        returnModel.setData("");
                        returnModel.setMsg("机器人已是当前状态，不能重复操作！");
                        returnModel.setState(Constant.STATE_FAILURE);
                        return returnModel;
                    }
                    if (robotCode.getRobotRunState().equals("10")) {
                        returnModel.setData("");
                        returnModel.setMsg("管理系统推送机器人状态！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        returnModel.setType(Constant.OPERATOR_MAINTENANCE);
                        // 转JSON格式发送到个推
                        String robotUnlock = JsonUtils.toString(returnModel);
                        String encode = AES.encode(robotUnlock);// 加密推送
                        // 个推推送消息到APP端
                        // 判断pad是旧版本还是重构版
                        if ("0".equals(robotCode.getRobotAppType())) {
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
                        } else {
                            String isSuccess = WeChatGtRobotAppPush.singlePushNew(robotCode.getNewRobotCodeCid(), encode, "成功!");
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
                        }
                    } else {
                        //无法修改状态
                        returnModel.setData("");
                        returnModel.setMsg("无法修改状态！");
                        returnModel.setState(Constant.STATE_FAILURE);
                        return returnModel;
                    }
                } else if (sysRobot.getRobotRunState().equals("100")) {
                    returnModel.setData("");
                    returnModel.setMsg("不能执行此操作！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
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

    /**
     * @return void
     * @Author 郭凯
     * @Description 机器人运营维护下载EXCEL表
     * @Date 14:00 2020/5/25
     * @Param [response, request, SysRobot]
     **/
    @RequestMapping(value = "/uploadExcelRobot")
    public void uploadExcelRobot(HttpServletResponse response, @ModelAttribute SysRobot SysRobot, String type) throws Exception {
        List<SysRobotExcel> robotListByExample = null;
        SysUsers SysUsers = this.getSysUser();
        Map<String, Object> search = new HashMap<>();
        if (StringUtils.isEmpty(type)) {
            search.put("type", "1,3");
            search.put("userId", SysUsers.getUserId().toString());
            search.put("scenicSpotId", SysRobot.getScenicSpotId());
            search.put("robotCode", SysRobot.getRobotCode());
            robotListByExample = sysRobotService.getRobotExcel(search);
        } else {
//            search.put("type",type);
            search.put("userId", SysUsers.getUserId().toString());
            search.put("scenicSpotId", SysRobot.getScenicSpotId());
            search.put("robotCode", SysRobot.getRobotCode());
            search.put("robotRunState", SysRobot.getRobotRunState());
            robotListByExample = sysRobotService.getRobotExcel(search);
        }

        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("机器人列表", "机器人列表", SysRobotExcel.class, robotListByExample), "机器人列表" + dateTime + ".xls", response);
    }


    /**
     * @return void
     * @Author 郭凯
     * @Description 机器人档案下载EXCEL表
     * @Date 14:00 2020/5/25
     * @Param [response, request, SysRobot]
     **/
    @RequestMapping(value = "/uploadExcelArchivesRobot")
    public void uploadExcelArchivesRobot(HttpServletResponse response, @ModelAttribute SysRobot SysRobot, String type) throws Exception {
        List<SysRobotExcel> robotListByExample = null;
        SysUsers SysUsers = this.getSysUser();
        Map<String, Object> search = new HashMap<>();
        if (StringUtils.isEmpty(type)) {
            search.put("type", "1,2,3");
            search.put("userId", SysUsers.getUserId().toString());
            search.put("scenicSpotId", SysRobot.getScenicSpotId());
            search.put("robotCode", SysRobot.getRobotCode());
            robotListByExample = sysRobotService.getRobotExcel(search);
        } else {
//            search.put("type",type);
            search.put("userId", SysUsers.getUserId().toString());
            search.put("scenicSpotId", SysRobot.getScenicSpotId());
            search.put("robotCode", SysRobot.getRobotCode());
            search.put("robotRunState", SysRobot.getRobotRunState());
            robotListByExample = sysRobotService.getRobotExcel(search);
        }
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("机器人列表", "机器人列表", SysRobotExcel.class, robotListByExample), "机器人列表" + dateTime + ".xls", response);
    }


    /**
     * @return void
     * @Author 郭凯
     * @Description 机器人档案下载EXCEL表(中关村租赁)
     * @Date 14:00 2020/5/25
     * @Param [response, request, SysRobot]
     **/
    @RequestMapping(value = "/uploadExcelArchivesRobotZFGC")
    public void uploadExcelArchivesRobotZFGC(HttpServletResponse response, @ModelAttribute SysRobot SysRobot, String type) throws Exception {
        List<SysRobotExcel> robotListByExample = null;
        SysUsers SysUsers = this.getSysUser();
        Map<String, Object> search = new HashMap<>();
        if (StringUtils.isEmpty(type)) {
            search.put("type", "1,2,3");
            search.put("userId", SysUsers.getUserId().toString());
            search.put("scenicSpotId", SysRobot.getScenicSpotId());
            search.put("robotCode", SysRobot.getRobotCode());
            robotListByExample = sysRobotService.getRobotZGCExcel(search);
        } else {
//            search.put("type",type);
            search.put("userId", SysUsers.getUserId().toString());
            search.put("scenicSpotId", SysRobot.getScenicSpotId());
            search.put("robotCode", SysRobot.getRobotCode());
            search.put("robotRunState", SysRobot.getRobotRunState());
            robotListByExample = sysRobotService.getRobotZGCExcel(search);
        }
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("机器人列表", "机器人列表", SysRobotExcel.class, robotListByExample), "机器人列表" + dateTime + ".xls", response);
    }


    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 生成二维码
     * @Date 16:39 2020/6/18
     * @Param [robotCode]
     **/
    @RequestMapping(value = "/onQrCode")
    @ResponseBody
    public ReturnModel onQrCode(@RequestParam(value = "robotCode", required = true) String robotCode) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if (QrCodeUtil.qrCode(robotCode)) {
                dataModel.setData("");
                dataModel.setMsg("二维码生成成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            } else {
                dataModel.setData("");
                dataModel.setMsg("二维码生成失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            logger.error("onQrCode", e);
            dataModel.setData("");
            dataModel.setMsg("二维码生成失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @return void
     * @Author 郭凯
     * @Description 下载二维码
     * @Date 16:48 2020/6/18
     * @Param [response]
     **/
    @RequestMapping("/qrcode")
    public void qrcode(HttpServletResponse response) throws IOException {
        String fileName = request.getParameter("fileName").toString();
        //获得第一个点的位置
        int index = fileName.indexOf("/");
        //根据第一个点的位置 获得第二个点的位置
        index = fileName.indexOf("/", index + 1);
        //根据第二个点的位置，截取 字符串。得到结果 result
        String result = fileName.substring(index + 1);
        File file = new File(uploadPath + result);
        if (file.exists()) {
            response.setContentType("application/octet-stream");
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "utf8"));
            byte[] buffer = new byte[1024];
            //输出流
            OutputStream os = null;
            try (FileInputStream fis = new FileInputStream(file); BufferedInputStream bis = new BufferedInputStream(fis);) {
                os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            response.setContentType("text/html; charset=UTF-8"); //转码
            PrintWriter out = response.getWriter();
            out.flush();
            out.println("<script defer='defer' type='text/javascript'>");
            out.println("alert('文件不存在或已经被删除！');");
            out.println("window.location.href='/page/system/robot/html/robotList.html';");
            out.println("</script>");
        }
    }

    /**
     * @param @return
     * @return ReturnModel
     * @throws
     * @Author 郭凯
     * @Description: 一键生成所有二维码
     * @Title: generateQrCode
     * @date 2021年1月14日 上午10:11:35
     */
    @RequestMapping(value = "/generateQrCode")
    @ResponseBody
    public ReturnModel generateQrCode() {
        ReturnModel dataModel = new ReturnModel();
        try {
            SysUsers SysUsers = this.getSysUser();
            Map<String, Object> search = new HashMap<>();
            search.put("userId", SysUsers.getUserId());
            search.put("scenicSpotId", request.getParameter("scenicSpotId"));
            //根据用户信息查询用户所有机器
            List<SysRobot> robotList = sysRobotService.getSysRobotList(search);
            String robotCode = "";
            for (SysRobot robot : robotList) {
                if (!QrCodeUtil.qrCode(robot.getRobotCode())) {
                    robotCode += robot.getRobotCode() + "，";
                }
            }
            if (robotCode.length() > 0) {
                //sendDocNum.length() - 1 的原因：从零开始的，含头不含尾。
                robotCode = robotCode.substring(0, robotCode.length() - 1);
            } else {
                robotCode = "无";
            }
            dataModel.setData("");
            dataModel.setMsg("二维码生成成功" + robotCode + "生成失败");
            dataModel.setState(Constant.STATE_SUCCESS);
            return dataModel;
        } catch (Exception e) {
            logger.error("onQrCode", e);
            dataModel.setData("");
            dataModel.setMsg("二维码生成失败！请联系管理员");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @param @return
     * @return ReturnModel
     * @throws
     * @Author 郭凯
     * @Description: 一键生成金华所有机器人二维码
     * @Title: generateQrCode
     * @date 2021年1月14日 上午10:11:35
     */
    @RequestMapping(value = "/JINGHUAgenerateQrCode")
    @ResponseBody
    public ReturnModel JINGHUAgenerateQrCode() {
        ReturnModel dataModel = new ReturnModel();
        try {
            SysUsers SysUsers = this.getSysUser();
            Map<String, Object> search = new HashMap<>();
            search.put("userId", SysUsers.getUserId());
//            search.put("scenicSpotId",request.getParameter("scenicSpotId"));
            search.put("scenicSpotId", "121601250496737");

            //根据用户信息查询用户所有机器
            List<SysRobot> robotList = sysRobotService.getSysRobotList(search);
            String robotCode = "";
            for (SysRobot robot : robotList) {
                if (!QrCodeUtil.qrCode(robot.getRobotCode())) {
                    robotCode += robot.getRobotCode() + "，";
                }
            }
            if (robotCode.length() > 0) {
                //sendDocNum.length() - 1 的原因：从零开始的，含头不含尾。
                robotCode = robotCode.substring(0, robotCode.length() - 1);
            } else {
                robotCode = "无";
            }
            dataModel.setData("");
            dataModel.setMsg("二维码生成成功" + robotCode + "生成失败");
            dataModel.setState(Constant.STATE_SUCCESS);
            return dataModel;
        } catch (Exception e) {
            logger.error("onQrCode", e);
            dataModel.setData("");
            dataModel.setMsg("二维码生成失败！请联系管理员");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @param @param  multipartFile
     * @param @return
     * @param @throws Exception
     * @return ReturnModel
     * @throws
     * @Author 郭凯
     * @Description: 批量导入ICCID
     * @Title: upload
     * @date 2021年2月24日 上午10:54:23
     */
    @RequestMapping("/upload")
    @ResponseBody
    public ReturnModel upload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        ReturnModel returnModel = new ReturnModel();
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            List<SysRobot> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(), SysRobot.class, params);
            String robotCode = "";
            for (SysRobot sysRobot : result) {

                if (ToolUtil.isEmpty(sysRobot.getRobotCode())) {
                    //查询SIM卡是否重复
                    SysRobot robots = sysRobotService.getRobotSimById(sysRobot.getRobotCodeSim());
                    if (!ToolUtil.isEmpty(robots)) {
                        robotCode += sysRobot.getRobotCodeSim() + "，";
                    } else {
                        SysRobotId newSysRobotId = sysRobotService.getNewSysRobotId();
                        sysRobot.setRobotCode(newSysRobotId.getRobotCode());
//                        sysRobot.setRobotVersionNumber("0.0.0");
                        sysRobot.setScenicSpotId(Long.parseLong("121601250496737"));
                        sysRobotService.addRobot(sysRobot);
//                    if (QrCodeUtil.qrCode(newSysRobotId.getRobotCode())) {
                        sysRobotService.updateSysRobotId(newSysRobotId);
//                    }
                    }
                } else {

                    SysRobot robots = sysRobotService.getRobotCodeBy(sysRobot.getRobotCode());
                    if (ToolUtil.isEmpty(robots)) {
                        robotCode += sysRobot.getRobotCodeSim() + "，";
                    } else {
                        robots.setRobotCodeSim(sysRobot.getRobotCodeSim());
                        robots.setRobotVersionNumber(sysRobot.getRobotVersionNumber());
                        sysRobotService.updateByPrimaryKeySelective(robots);
                    }
                }

            }
            if (robotCode.length() > 0) {
                //sendDocNum.length() - 1 的原因：从零开始的，含头不含尾。
                robotCode = robotCode.substring(0, robotCode.length() - 1);
            } else {
                robotCode = "无";
            }
            returnModel.setData("");
            returnModel.setMsg("导入成功" + robotCode + "编号错误");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } catch (Exception e) {
            logger.info("upload", e);
            returnModel.setData("");
            returnModel.setMsg("导入失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method importBatchNumber
     * @Author 郭凯
     * @Version 1.0
     * @Description 批量导入批次号
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/5/24 15:45
     */
    @RequestMapping("/importBatchNumber")
    @ResponseBody
    public ReturnModel importBatchNumber(@RequestParam("file") MultipartFile multipartFile) {
        ReturnModel returnModel = new ReturnModel();
        try {
            ImportParams params = new ImportParams();
            SysRobot sysRobot = new SysRobot();
            params.setTitleRows(0);
            params.setHeadRows(1);
            List<SysRobot> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(), SysRobot.class, params);
            String robotCode = "";
            for (SysRobot sysRobotExcel : result) {
                //查询机器人是否存在
                SysRobot robots = sysRobotService.getRobotCodeBy(sysRobotExcel.getRobotCode());
                if (ToolUtil.isEmpty(robots)) {
                    robotCode += sysRobotExcel.getRobotCode() + "，";
                } else {
//                    sysRobot = new SysRobot();
                    sysRobot.setRobotId(robots.getRobotId());
//                    sysRobot.setRobotCode(sysRobotExcel.getRobotCode());
//                    sysRobot.setRobotCodeSim(sysRobotExcel.getRobotCodeSim());
//                    sysRobot.setRobotBluetooth(sysRobotExcel.getRobotBluetooth());
//                    sysRobot.setRobotVersionNumber(sysRobotExcel.getRobotVersionNumber());
//                    sysRobot.setRobotRunState(sysRobotExcel.getRobotRunState());
//                    sysRobot.setRobotRemarks(sysRobotExcel.getRobotRemarks());
//                    sysRobot.setClientVersion(sysRobotExcel.getClientVersion());
//                    sysRobot.setRobotBatchNumber(sysRobotExcel.getRobotBatchNumber());
//                    sysRobot.setUpdateDate(DateUtil.currentDateTime());
                    sysRobotService.updateSysRobot(sysRobot);
                }
            }
            if (robotCode.length() > 0) {
                //sendDocNum.length() - 1 的原因：从零开始的，含头不含尾。
                robotCode = robotCode.substring(0, robotCode.length() - 1);
            } else {
                robotCode = "无";
            }
            returnModel.setData("");
            returnModel.setMsg("导入成功" + robotCode + "编号错误");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } catch (Exception e) {
            logger.info("importBatchNumber", e);
            returnModel.setData("");
            returnModel.setMsg("导入失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 修改机器人设备状态
     *
     * @param request
     * @param robotId
     * @param robotRunState
     * @return
     */
    @RequestMapping("/equipmentStatus")
    @ResponseBody
    public ReturnModel editRobotEquipmentStatus(HttpServletRequest request, String robotId, String robotRunState) {
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
                    SysOrder order = sysOrderService.getOrderStateByRobotCode(robotCode.getRobotCode(), "10");
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
                    } else {
                        returnModel.setData("");
                        returnModel.setMsg("不能执行此操作！");
                        returnModel.setState(Constant.STATE_FAILURE);
                        return returnModel;
                    }
                } else if (sysRobot.getRobotRunState().equals("20")) {// 如果传进来是用户解锁
                    if (robotCode.getRobotRunState().equals("30") || robotCode.getRobotRunState().equals("100")) {
                        returnModel.setData("");
                        returnModel.setMsg("管理系统推送机器人状态！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        returnModel.setType(Constant.ROBOT_UNLOCKING);
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
                    } else {
                        returnModel.setData("");
                        returnModel.setMsg("不能执行此操作！");
                        returnModel.setState(Constant.STATE_FAILURE);
                        return returnModel;
                    }
                } else if (sysRobot.getRobotRunState().equals("30")) {// 如果传进来是用户临时锁定,则不能修改
                    returnModel.setData("");
                    returnModel.setMsg("不能执行此操作！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                } else if (sysRobot.getRobotRunState().equals("40")) {// 如果传进来是管理员解锁
                    if (sysRobot.getRobotRunState().equals(robotCode.getRobotRunState())) {
                        returnModel.setData("");
                        returnModel.setMsg("机器人已是当前状态，不能重复操作！");
                        returnModel.setState(Constant.STATE_FAILURE);
                        return returnModel;
                    }
                    if (robotCode.getRobotRunState().equals("10") || robotCode.getRobotRunState().equals("50")) {// 只能是状态为显示的状态下修改
                        returnModel.setData("");
                        returnModel.setMsg("管理系统推送机器人状态！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        returnModel.setType(Constant.ADMINISTRATOR_UNLOCKING_ROBOT);
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
                    } else {
                        //无法修改状态
                        returnModel.setData("");
                        returnModel.setMsg("无法修改状态！");
                        returnModel.setState(Constant.STATE_FAILURE);
                        return returnModel;
                    }
                } else if (sysRobot.getRobotRunState().equals("50")) {// 如果传进来是管理员锁定
                    if (sysRobot.getRobotRunState().equals(robotCode.getRobotRunState())) {
                        returnModel.setData("");
                        returnModel.setMsg("机器人已是当前状态，不能重复操作！");
                        returnModel.setState(Constant.STATE_FAILURE);
                        return returnModel;
                    }
                    if (robotCode.getRobotRunState().equals("10") || robotCode.getRobotRunState().equals("40")) {
                        returnModel.setData("");
                        returnModel.setMsg("管理系统推送机器人状态！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        returnModel.setType(Constant.ADMINISTRATOR_LOCKS_ROBOT);
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
                    } else {
                        //无法修改状态
                        returnModel.setData("");
                        returnModel.setMsg("无法修改状态！");
                        returnModel.setState(Constant.STATE_FAILURE);
                        return returnModel;
                    }
                } else if (sysRobot.getRobotRunState().equals("60")) {
                    returnModel.setData("");
                    returnModel.setMsg("后台管理人员无法操作此状态  必须有运营人员修复机器人或者管理者APP下发自检指令时修复！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                } else if (sysRobot.getRobotRunState().equals("70")) {
                    returnModel.setData("");
                    returnModel.setMsg("此状态不能修改！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                } else if (sysRobot.getRobotRunState().equals("80")) {
                    returnModel.setData("");
                    returnModel.setMsg("此状态不能修改，需要运营人员手动恢复！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                } else if (sysRobot.getRobotRunState().equals("90")) {//运营人员维护
                    if (sysRobot.getRobotRunState().equals(robotCode.getRobotRunState())) {
                        returnModel.setData("");
                        returnModel.setMsg("机器人已是当前状态，不能重复操作！");
                        returnModel.setState(Constant.STATE_FAILURE);
                        return returnModel;
                    }
                    if (robotCode.getRobotRunState().equals("10")) {
                        returnModel.setData("");
                        returnModel.setMsg("管理系统推送机器人状态！");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        returnModel.setType(Constant.OPERATOR_MAINTENANCE);
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
                    } else {
                        //无法修改状态
                        returnModel.setData("");
                        returnModel.setMsg("无法修改状态！");
                        returnModel.setState(Constant.STATE_FAILURE);
                        return returnModel;
                    }
                } else if (sysRobot.getRobotRunState().equals("100")) {
                    returnModel.setData("");
                    returnModel.setMsg("不能执行此操作！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
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

    @ApiOperation("机器人id列表")
    @RequestMapping("/getRobotIdList")
    @ResponseBody
    public ReturnModel getRobotIdList() {
        List<SysRobotIdDTO> list = sysRobotService.getRobotIdList();
        return new ReturnModel(list, "success", Constant.STATE_SUCCESS, null);
    }

    @ApiOperation("机器人升级列表查询")
    @GetMapping("/getRobotUpgrade")
    @ResponseBody
    public ReturnModel getRobotUpgrade(Long scenicSpotId) {
        ReturnModel returnModel = new ReturnModel();
        if (scenicSpotId != null) {
            List<SysRobot> robotUpgrade = sysRobotService.getRobotUpgrade(scenicSpotId, null);
            returnModel.setData(robotUpgrade);
            returnModel.setMsg("查询成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
        } else {
            returnModel.setData("");
            returnModel.setMsg("查询失败！");
            returnModel.setState(Constant.STATE_FAILURE);
        }
        return returnModel;
    }

    @ApiOperation("机器人升级修改")
    @PostMapping("/updateRobotUpgrade")
    @ResponseBody
    public ReturnModel updateRobotUpgrade(Long scenicSpotId, Long robotId) throws Exception {
        ReturnModel returnModel = new ReturnModel();
        if (scenicSpotId != null || robotId != null) {
            int i = sysRobotService.updateRobotUpgrade(scenicSpotId, robotId);
            if (i > 0) {
                returnModel.setData("");
                returnModel.setMsg("修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                List<SysRobot> robotUpgrade = sysRobotService.getRobotUpgrade(scenicSpotId,robotId);
                if (ToolUtil.isNotEmpty(robotUpgrade) && robotUpgrade.size() > 0) {
                    for (SysRobot sysRobot : robotUpgrade) {
                        if (sysRobot.getRobotCodeCid() != null) {
                            returnModel.setData("");
                            returnModel.setMsg("机器人升级修改成功");
                            returnModel.setState(Constant.STATE_SUCCESS);
                            returnModel.setType(Constant.ROBOT_UPDATE);
                            // 转JSON格式发送到个推
                            String robotUnlock = JsonUtils.toString(returnModel);
                            String encode = AES.encode(robotUnlock);// 加密推送
                            String isSuccess = WeChatGtRobotAppPush.singlePush(sysRobot.getRobotCodeCid(), encode, "成功!");
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
                }
            } else {
                returnModel.setData("");
                returnModel.setMsg("修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
            }
        } else {
            returnModel.setData("");
            returnModel.setMsg("请传景区id或机器人编号！");
            returnModel.setState(Constant.STATE_FAILURE);
        }
        return returnModel;
    }

    @ApiOperation("查询当前景区最新版本PAD")
    @GetMapping("/getRobotVersionPad")
    @ResponseBody
    public ReturnModel getRobotVersionPad(Long scenicSpotId) {
        ReturnModel returnModel = new ReturnModel();
        if (scenicSpotId != null) {
            List<SysRobotAppVersion> robotVersionPad = sysRobotService.getRobotVersionPad(scenicSpotId);
            returnModel.setData(robotVersionPad);
            returnModel.setMsg("查询成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
        } else {
            returnModel.setData("");
            returnModel.setMsg("请传景区id！");
            returnModel.setState(Constant.STATE_FAILURE);
        }
        return returnModel;
    }

}
