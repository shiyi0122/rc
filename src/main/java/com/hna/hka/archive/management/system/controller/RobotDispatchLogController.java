package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysRobotDispatchLog;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.service.SysRobotDispatchLogService;
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
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: RobotDispatchLogController
 * @Author: 郭凯
 * @Description: 机器人调度日志控制层
 * @Date: 2020/6/1 15:36
 * @Version: 1.0
 */
@Api(tags = "机器人调度日志")
@RequestMapping("/system/dispatchLog")
@Controller
public class RobotDispatchLogController extends PublicUtil {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysRobotDispatchLogService sysRobotDispatchLogService;

    /**
     * @Author 郭凯
     * @Description 机器人调度日志列表查询
     * @Date 15:45 2020/6/1
     * @Param [pageNum, pageSize, sysRobotDispatchLog]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     **/
    @RequestMapping("/getSysDepositLogList")
    @ResponseBody
    public PageDataResult getSysDepositLogList(@RequestParam("pageNum") Integer pageNum,
                                               @RequestParam("pageSize") Integer pageSize, SysRobotDispatchLog sysRobotDispatchLog) {
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
            search.put("robotDispatchCode",sysRobotDispatchLog.getRobotDispatchCode());
            search.put("robotDispatchCallOutName",sysRobotDispatchLog.getRobotDispatchCallOutName());
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            search.put("startTime", startTime);
            search.put("endTime", endTime);
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
            	if (ToolUtil.isEmpty(sysRobotDispatchLog.getRobotDispatchCode())) {
            		search.put("time", DateUtil.crutDate());
				}
            }
            pageDataResult = sysRobotDispatchLogService.getSysDepositLogList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("机器人调度日志列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 下载机器人调度日志Excel表
     * @Date 16:20 2020/6/1
     * @Param [response, sysRobotDispatchLog]
     * @return void
    **/
    @RequestMapping("/uploadExcelDispatchtLog")
    public void uploadExcelDispatchtLog(HttpServletResponse response, SysRobotDispatchLog sysRobotDispatchLog) throws Exception {
        List<SysRobotDispatchLog> dispatchLogsList = null;
        Map<String,Object> search = new HashMap<>();
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        search.put("robotDispatchCode",sysRobotDispatchLog.getRobotDispatchCode());
        search.put("startTime", startTime);
        search.put("endTime", endTime);
        //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
        if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
        	if (ToolUtil.isEmpty(sysRobotDispatchLog.getRobotDispatchCode())) {
        		search.put("time", DateUtil.crutDate());
			}
        }
        dispatchLogsList = sysRobotDispatchLogService.getUploadExcelDispatchtLog(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("机器人调度日志", "机器人调度日志", SysRobotDispatchLog.class, dispatchLogsList),"机器人调度日志"+ dateTime +".xls",response);
    }
}