package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysLogWithBLOBs;
import com.hna.hka.archive.management.system.model.SysRobotDownloadLog;
import com.hna.hka.archive.management.system.service.SysLogService;
import com.hna.hka.archive.management.system.service.SysRobotDownloadLogServer;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/2/25 9:30
 * 排查流量
 */
@RequestMapping("/system/sysRobotDownloadLog")
@Controller
public class SysRobotDownloadLogController {

    @Autowired
    private SysRobotDownloadLogServer sysRobotDownloadLogServer;

    @Autowired
    private HttpServletRequest request;

    /**
     * 机器人下载表
     * @param pageNum
     * @param pageSize
     * @param sysRobotDownloadLog
     * @return
     */
    @RequestMapping("/getSysRobotDownloadLogList")
    @ResponseBody
    public PageDataResult getSysRobotDownloadLogList(@RequestParam("pageNum") Integer pageNum,
                                                      @RequestParam("pageSize") Integer pageSize, SysRobotDownloadLog sysRobotDownloadLog) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            if (!StringUtils.isEmpty(sysRobotDownloadLog.getScenicSpotId())){
                search.put("spotId",sysRobotDownloadLog.getScenicSpotId().toString());
            }
            if (!StringUtils.isEmpty(sysRobotDownloadLog.getRobotCode())){
                search.put("robotCode",sysRobotDownloadLog.getRobotCode());
            }
            pageDataResult = sysRobotDownloadLogServer.getSysRobotDownloadLogList(pageNum,pageSize,search);
        }catch (Exception e){
           e.printStackTrace();
        }
        return pageDataResult;
    }



}
