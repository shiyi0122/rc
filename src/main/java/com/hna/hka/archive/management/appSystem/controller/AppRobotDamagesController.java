package com.hna.hka.archive.management.appSystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.service.AppUserService;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotDamages;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotDamagesService;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.system.util.*;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.appSystem.controller
 * @ClassName: AppRobotDamagesController
 * @Author: 郭凯
 * @Description: 损坏赔偿单控制层接口
 * @Date: 2021/6/26 17:04
 * @Version: 1.0
 */
@RequestMapping("/system/appRobotDamages")
@Controller
public class AppRobotDamagesController extends PublicUtil {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private SysRobotDamagesService sysRobotDamagesService;

    /**
     * @Method addRobotDamages
     * @Author 郭凯
     * @Version  1.0
     * @Description 新增机器人损坏赔偿信息
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/26 17:09
     */
    @RequestMapping("/addRobotDamages")
    @ResponseBody
    public String addRobotDamages(@RequestParam("longinTokenId") String longinTokenId , SysRobotDamages sysRobotDamages){
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
            SysRobotDamages robotDamages = sysRobotDamagesService.addRobotDamages(sysRobotDamages);
            if (ToolUtil.isNotEmpty(robotDamages)){
                returnModel.setData(robotDamages);
                returnModel.setMsg("损坏赔偿信息新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                returnModel.setType(Constant.STATE_SUCCESS);
                String model = JSON.toJSONString(returnModel, SerializerFeature.WriteNullStringAsEmpty);
                Map<String,Object> search = new HashMap<>();
                search.put("scenicSpotId",sysRobotDamages.getScenicSpotId());
                search.put("resCode","AFTER_SALES_PERSON_IN_CHARGE");
                List<SysAppUsers> appUsersList = appUserService.getAppUsersByScenicIdList(search);
                if (ToolUtil.isNotEmpty(appUsersList) && appUsersList.size() > 0){
                    for (SysAppUsers sysAppUsers : appUsersList){
                        // 个推推送消息到APP端
                        String isSuccess = WeChatGtRobotAppPush.singlePushApp(sysAppUsers.getUserClientGtId(), "损坏赔偿上报", sysAppUsers.getScenicSpotName() + "," + sysRobotDamages.getRobotCode() + "," +sysRobotDamages.getErrorRecordsName());
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
                return model;
            }else{
                returnModel.setData("");
                returnModel.setMsg("损坏赔偿信息新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                String model = JSON.toJSONString(returnModel, SerializerFeature.WriteNullStringAsEmpty);
                return model;
            }
        }catch (Exception e){
            logger.info("addRobotDamages",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            String model = JSON.toJSONString(returnModel, SerializerFeature.WriteNullStringAsEmpty);
            return model;
        }
    }

    /**
     * @Method getRobotDamagesAppList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询损坏赔偿信息列表
     * @Return java.lang.String
     * @Date 2021/6/27 15:12
     */
    @RequestMapping("/getRobotDamagesAppList")
    @ResponseBody
    public String getRobotDamagesAppList(@RequestParam("longinTokenId") String longinTokenId, SysRobotDamages sysRobotDamages, BaseQueryVo BaseQueryVo){
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
            search.put("robotCode",sysRobotDamages.getRobotCode());
            search.put("scenicSpotId",sysRobotDamages.getScenicSpotId());
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
            if (ToolUtil.isEmpty(sysRobotDamages.getStartTime()) || ToolUtil.isEmpty(sysRobotDamages.getEndTime())) {
                if (ToolUtil.isEmpty(sysRobotDamages.getRobotCode())) {
                    sysRobotDamages.setStartTime(DateUtil.crutDate());
                    sysRobotDamages.setEndTime(DateUtil.crutDate());
                }
            }
            search.put("startTime",sysRobotDamages.getStartTime());
            search.put("endTime",sysRobotDamages.getEndTime());
            BaseQueryVo.setSearch(search);
            PageInfo<SysRobotDamages> page = sysRobotDamagesService.getRobotDamagesAppList(BaseQueryVo);
            returnModel.setData(page);
            returnModel.setMsg("查询成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setType(Constant.STATE_SUCCESS);
            String model = JSON.toJSONString(returnModel, SerializerFeature.WriteNullStringAsEmpty);
            return model;
        }catch (Exception e){
            logger.info("getRobotDamagesAppList",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            String model = JSON.toJSONString(returnModel, SerializerFeature.WriteNullStringAsEmpty);
            return model;
        }

    }

}
