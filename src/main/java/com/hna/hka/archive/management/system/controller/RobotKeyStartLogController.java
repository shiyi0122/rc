package com.hna.hka.archive.management.system.controller;

import com.google.inject.internal.util.$Nullable;
import com.hna.hka.archive.management.system.model.SysRobotDispatchLog;
import com.hna.hka.archive.management.system.model.SysRobotKeyStartLog;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.service.RobotKeyStartLogService;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/6/14 10:23
 */
@Api(tags = "机器人启动状态日志")
@RequestMapping("/system/keyStart")
@Controller
public class RobotKeyStartLogController extends PublicUtil {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    RobotKeyStartLogService robotKeyStartLogService;


    /**
     * @Author 郭凯
     * @Description 机器人启动状态日志列表查询
     * @Date 15:45 2020/6/1
     * @Param [pageNum, pageSize, sysRobotDispatchLog]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     **/
    @RequestMapping("/getSysKeyStartLogList")
    @ResponseBody
    public PageDataResult getSysKeyStartLogList(@RequestParam("pageNum") Integer pageNum,
                                               @RequestParam("pageSize") Integer pageSize, SysRobotKeyStartLog sysRobotKeyStartLog) {
        PageDataResult pageDataResult = new PageDataResult();
        SysUsers SysUsers = this.getSysUser();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("userId",SysUsers.getUserId().toString());
            search.put("scenicSpotId",sysRobotKeyStartLog.getScenicSpotId());
            search.put("robotCode",sysRobotKeyStartLog.getRobotCode());
//            String startTime = request.getParameter("startTime");
//            String endTime = request.getParameter("endTime");
//            search.put("startTime", startTime);
//            search.put("endTime", endTime);
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询

            pageDataResult = robotKeyStartLogService.getRobotKeyStartList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("机器人启动状态日志列表查询失败",e);
        }
        return pageDataResult;
    }


    /**
     * 机器人启动状态日志Excel表下载
     * @param response
     * @param
     * @throws Exception
     */
    @RequestMapping("/uploadExcelKeyStartLog")
    public void uploadExcelKeyStartLog(HttpServletResponse response, SysRobotKeyStartLog sysRobotKeyStartLog) throws Exception {
        List<SysRobotKeyStartLog> keyStartLogList = null;
        Map<String,Object> search = new HashMap<>();
//        search.put("userId",SysUsers.getUserId().toString());
        search.put("scenicSpotId",sysRobotKeyStartLog.getScenicSpotId());
        search.put("robotCode",sysRobotKeyStartLog.getRobotCode());
        //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
//        if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
//            if (ToolUtil.isEmpty(sysRobotDispatchLog.getRobotDispatchCode())) {
//                search.put("time", DateUtil.crutDate());
//            }
//        }
        keyStartLogList = robotKeyStartLogService.uploadExcelKeyStartLog(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("机器人解锁状态日志", "机器人解锁状态日志", SysRobotKeyStartLog.class, keyStartLogList),"机器人状态日志"+ dateTime +".xls",response);
    }






}
