package com.hna.hka.archive.management.system.controller;

import com.gexin.rp.sdk.template.IncTemplate;
import com.google.inject.internal.cglib.core.$ObjectSwitchCallback;
import com.hna.hka.archive.management.system.model.SysRobotUnusualLog;
import com.hna.hka.archive.management.system.model.SysRobotUnusualTime;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.service.SysRobotUnusualLogService;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/3/3 10:05
 */
@Api(tags = "机器人,景区异常状态监控日志接口")
@RequestMapping("/system/sysRobotUnusualLog")
@Controller
public class SysRobotUnusualLogController extends PublicUtil {

    @Autowired
    SysRobotUnusualLogService sysRobotUnusualLogService;

    @Autowired
    private HttpServletRequest request;
    /**
     * 添加机器人异常提醒
     * @param
     * @param
     * @return
     */
    @ApiOperation(value = "添加机器人故障提醒")
    @PostMapping("/addSysRobotUnusualLog")
    @ResponseBody
    public ReturnModel addSysRobotUnusualLog(SysRobotUnusualLog sysRobotUnusualLog) {

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


    @ApiOperation(value = "机器人异常故障提醒列表")
    @GetMapping("/getSysRobotUnusualLogList")
    @ResponseBody
    public PageDataResult getSysRobotUnusualLogList(Integer pageNum, Integer pageSize,SysRobotUnusualLog sysRobotUnusualLog) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        search.put("robotCode",sysRobotUnusualLog.getRobotCode());
        search.put("scenicSpotId",sysRobotUnusualLog.getScenicSpotId());
        search.put("unusualType",sysRobotUnusualLog.getUnusualType());
        if (StringUtils.isEmpty(sysRobotUnusualLog.getStartTime()) && StringUtils.isEmpty(sysRobotUnusualLog.getEndTime())){
            search.put("time", DateUtil.crutDate());
        }else{
            search.put("stateTime", sysRobotUnusualLog.getStartTime());
            search.put("endTime", DateUtil.addDay(sysRobotUnusualLog.getEndTime(),1));
        }
        pageDataResult = sysRobotUnusualLogService.getSysRobotUnusualLogList(pageNum,pageSize, search);

        return pageDataResult;

    }


    @ApiOperation(value = "判断是否需要是否展示红点")
    @GetMapping("/ifSysRobotBadge")
    @ResponseBody
    public Boolean ifSysRobotBadge() {

        Boolean aBoolean =  sysRobotUnusualLogService.ifSysRobotBadge();

        return aBoolean;

    }



    @ApiOperation(value = "后台修改机器人异常故障状态")
    @GetMapping("/editSysRobotUnusualLog")
    @ResponseBody
    public ReturnModel editSysRobotUnusualLog(SysRobotUnusualLog sysRobotUnusualLog) {

        ReturnModel returnModel = new ReturnModel();
        SysUsers sysUser = this.getSysUser();
        sysRobotUnusualLog.setBackstageProcessorId(sysUser.getUserId());
        int i   = sysRobotUnusualLogService.editSysRobotUnusualLog(sysRobotUnusualLog);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败");
        }
        return returnModel;

    }

    @ApiOperation(value = "一键处理")
    @GetMapping("/oneClickProcessing")
    @ResponseBody
    public ReturnModel oneClickProcessing(Long scenicSpotId,String startTime,String endTime) {

        ReturnModel returnModel = new ReturnModel();
        if (StringUtils.isEmpty(scenicSpotId)){
           scenicSpotId =Long.parseLong(request.getParameter("scenicSpotId"));
        }


        SysUsers sysUser = this.getSysUser();

        int i   = sysRobotUnusualLogService.oneClickProcessing(sysUser,scenicSpotId,startTime,endTime);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败");
        }
        return returnModel;

    }



    @ApiOperation(value = "测试景区饱和度异常")
    @GetMapping("/testSaturation")
    @ResponseBody
    public ReturnModel timingScenicSpotSaturationLog() {
        ReturnModel returnModel = new ReturnModel();

        sysRobotUnusualLogService.timingRobotSpotOrderLog();

        return returnModel;
    }

}
