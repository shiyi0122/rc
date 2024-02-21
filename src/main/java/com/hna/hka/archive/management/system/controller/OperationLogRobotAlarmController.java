package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysRobotFaule;
import com.hna.hka.archive.management.system.service.SysRobotFauleService;
import com.hna.hka.archive.management.system.util.*;

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
 * @ClassName: OperationLogRobotAlarmController
 * @Author: 郭凯
 * @Description: 机器人报警日志控制层
 * @Date: 2020/5/26 16:03
 * @Version: 1.0
 */

@RequestMapping("/system/operationLogRobotAlarm")
@Controller
public class OperationLogRobotAlarmController extends PublicUtil {

    @Autowired
    private SysRobotFauleService sysRobotFauleService;

    @Autowired
    private HttpServletRequest request;

    /**
     * @Author 郭凯
     * @Description 机器人报警日志列表查询
     * @Date 16:33 2020/5/26
     * @Param [pageNum, pageSize, sysRobotFaule]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getOperationLogRobotAlarmList")
    @ResponseBody
    public PageDataResult getOperationLogRobotAlarmList(@RequestParam("pageNum") Integer pageNum,
                                                        @RequestParam("pageSize") Integer pageSize, SysRobotFaule sysRobotFaule) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("robotCode",sysRobotFaule.getRobotCode());
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            search.put("startTime", startTime);
            search.put("endTime", endTime);
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
            	if (ToolUtil.isEmpty(sysRobotFaule.getRobotCode())) {
            		search.put("time", DateUtil.crutDate());
				}
            }
            pageDataResult = sysRobotFauleService.getOperationLogRobotAlarmList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("机器人报警日志列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 机器人报警日志Excel表查询
     * @Date 15:29 2020/5/29
     * @Param [response, sysRobotFaule]
     * @return void
    **/
    @RequestMapping("/uploadExcelRobotLog")
    public void uploadExcelRobotLog(HttpServletResponse response , SysRobotFaule sysRobotFaule) throws Exception {
        List<SysRobotFaule> robotFauleExcel = null;
        Map<String,Object> search = new HashMap<>();
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        search.put("robotCode",sysRobotFaule.getRobotCode());
        search.put("startTime", startTime);
        search.put("endTime", endTime);
        //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
        if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
        	if (ToolUtil.isEmpty(sysRobotFaule.getRobotCode())) {
        		search.put("time", DateUtil.crutDate());
			}
        }
        robotFauleExcel = sysRobotFauleService.getUploadExcelRobotLogExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("机器人报警日志", "机器人报警日志", SysRobotFaule.class, robotFauleExcel),"机器人报警日志"+ dateTime +".xls",response);
    }

}
