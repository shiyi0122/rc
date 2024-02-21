package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotErrorRecords;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotServiceRecords;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotServiceRecordsService;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.controller
 * @ClassName: RobotServiceRecordsController
 * @Author: 郭凯
 * @Description: 维修记录控制层
 * @Date: 2021/6/26 16:58
 * @Version: 1.0
 */
@RequestMapping("/system/robotServiceRecords")
@Controller
public class RobotServiceRecordsController extends PublicUtil {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysRobotServiceRecordsService sysRobotServiceRecordsService;

    /**
     * @Method getRobotServiceRecordsList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询维修记录列表
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/6/26 19:31
     */
    @RequestMapping("/getRobotServiceRecordsList")
    @ResponseBody
    public PageDataResult getRobotServiceRecordsList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, SysRobotServiceRecords sysRobotServiceRecords){
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
            search.put("robotCode",sysRobotServiceRecords.getServiceRecordsCode());
            search.put("scenicSpotId",sysRobotServiceRecords.getScenicSpotId());
            search.put("errorRecordsName",sysRobotServiceRecords.getErrorRecordsName());
            search.put("errorRecordsModel",sysRobotServiceRecords.getErrorRecordsModel());
            search.put("serviceRecordsPersonnel",sysRobotServiceRecords.getServiceRecordsPersonnel());
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
                if (ToolUtil.isEmpty(sysRobotServiceRecords.getServiceRecordsCode()) && ToolUtil.isEmpty(sysRobotServiceRecords.getScenicSpotId()) && ToolUtil.isEmpty(sysRobotServiceRecords.getErrorRecordsName()) && ToolUtil.isEmpty(sysRobotServiceRecords.getErrorRecordsModel()) && ToolUtil.isEmpty(sysRobotServiceRecords.getServiceRecordsPersonnel())) {
                    startTime = DateUtil.crutDate();
                    endTime = DateUtil.crutDate();
                }
            }
            search.put("startTime",startTime);
            search.put("endTime",endTime);
            pageDataResult = sysRobotServiceRecordsService.getRobotServiceRecordsList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("getRobotServiceRecordsList",e);
        }
        return pageDataResult;
    }

    @ApiOperation("机器人故障上报")
    @RequestMapping("/addRobotServiceRecords")
    @ResponseBody
    public ReturnModel addRobotServiceRecords(SysRobotServiceRecords sysRobotServiceRecords){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotServiceRecordsService.addRobotServiceRecords(sysRobotServiceRecords);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("机器人维修信息添加成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("机器人维修信息添加失败！");
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

}
