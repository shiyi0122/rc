package com.hna.hka.archive.management.appSystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hna.hka.archive.management.appSystem.service.AppUserService;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotErrorRecords;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.managerApp.service.SysAppUsersService;
import com.hna.hka.archive.management.system.model.SysRobotUnusualLog;
import com.hna.hka.archive.management.system.model.SysRobotUnusualTime;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.service.SysRobotUnusualLogService;
import com.hna.hka.archive.management.system.service.SysRobotUnusualTimeService;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/3/2 17:15
 * PAD端获取机器人异常监控时间（减轻pad服务器压力，写到后台管理中）
 */

@Api(tags = "机器人异常监控")
@RequestMapping("/system/appPadRobotUnusual")
@Controller
public class AppPadRobotUnusualController {

    @Autowired
    SysRobotUnusualTimeService sysRobotUnusualTimeService;

    @Autowired
    SysRobotUnusualLogService sysRobotUnusualLogService;

    @Autowired
    SysAppUsersService sysAppUsersService;
    @Autowired
    AppUserService appUserService;
    /**
     * 获取机器人监控异常时间
     * @param
     * @param
     * @return
     */
    @ApiOperation(value = "获取机器人监控异常时间")
    @GetMapping("/getUnusualTime")
    @ResponseBody
    public ReturnModel getUnusualTime(String robotCode) {

        ReturnModel returnModel = new ReturnModel();

        SysRobotUnusualTime sysRobotUnusualTime = sysRobotUnusualTimeService.getUnusualTime(robotCode);
        returnModel.setData(sysRobotUnusualTime);
        returnModel.setState(Constant.STATE_SUCCESS);

        return returnModel;
    }

    @ApiOperation(value = "获取景区下拉选")
    @GetMapping("/getUnusualSpotList")
    @ResponseBody
    public ReturnModel getUnusualSpotList() {

        ReturnModel returnModel = new ReturnModel();

        List<SysScenicSpot> list  = sysRobotUnusualTimeService.getUnusualSpotList();

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);

        return returnModel;
    }



    /**
     * 添加机器人异常提醒
     * @param
     * @param
     * @return
     */
    @ApiOperation(value = "添加机器人故障提醒")
    @PostMapping("/addSysRobotUnusualLog")
    @ResponseBody
    public ReturnModel addSysRobotUnusualLog(@RequestBody SysRobotUnusualLog sysRobotUnusualLog) {

        ReturnModel returnModel = new ReturnModel();

        int i  = sysRobotUnusualLogService.addSysRobotUnusualLog(sysRobotUnusualLog);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败");
        }

        return returnModel;

    }

    @ApiOperation(value = "查询机器人异常记录")
    @ResponseBody
    @PostMapping("/getSysRobotUnusualLogList")
    public String getSysRobotUnusualLogList(String content,Integer pageNum,Integer pageSize){
//        log.info("sysRobotErrorRecords:{}",sysRobotErrorRecords);
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
            Map<String,Object> search = new HashMap<>();
            search.put("userId",appUsers.getUserId());
            search.put("scenicSpotId",scenicSpotId);
//            search.put("date",DateUtil.crutDate());
            PageDataResult list = sysRobotUnusualLogService.getAppSysRobotUnusualLogList(pageNum,pageSize,search);
            String model = JsonUtils.toString(list);//对象转JSON

            return AES.encode(model);//加密返回
        }catch (Exception e){
            e.printStackTrace();
            PageDataResult pageDataResult = new PageDataResult();
            pageDataResult.setCode(400);
            String model = JsonUtils.toString(pageDataResult);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }


    @ApiOperation(value = "修改机器人异常处理状态")
    @ResponseBody
    @PostMapping("/editSysRobotUnusualLogState")
    public String editSysRobotUnusualLogState(String content,String id,String state){
//        log.info("sysRobotErrorRecords:{}",sysRobotErrorRecords);
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

            int i = sysRobotUnusualLogService.editSysRobotUnusualLogState(id, state, appUsers.getUserId());
            dataModel.setData(i);
            if ("2".equals(state)) {
                dataModel.setMsg("已查看");
            } else if ("3".equals(state)) {
                dataModel.setMsg("已处理");
            } else if ("4".equals(state)) {
                dataModel.setMsg("处理中");
            }
            if (i == 1) {
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }else if (i ==2){
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }else {
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }


        }catch (Exception e){
            e.printStackTrace();
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }


    /**
     * 测试机器人异常提醒
     * @param
     * @param
     * @return
     */
    @ApiOperation(value = "测试机器人异常提醒")
    @GetMapping("/testSysRobotUnusualLog")
    @ResponseBody
    public void testSysRobotUnusualLog() {

        ReturnModel returnModel = new ReturnModel();

         sysRobotUnusualLogService.timingRobotSpotUnusualLog();


    }




}
