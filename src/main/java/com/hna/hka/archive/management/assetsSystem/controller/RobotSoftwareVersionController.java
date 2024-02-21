package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotSoftwareVersion;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotSoftwareVersionService;
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
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.controller
 * @ClassName: RobotSoftwareVersionController
 * @Author: 郭凯
 * @Description: 机器人升级记录控制层
 * @Date: 2021/6/26 20:07
 * @Version: 1.0
 */
@RequestMapping("/system/robotSoftwareVersion")
@Controller
public class RobotSoftwareVersionController extends PublicUtil {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysRobotSoftwareVersionService sysRobotSoftwareVersionService;

    /**
     * @Method getRobotSoftwareVersionList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询机器人升级列表
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/6/26 20:10
     */
    @RequestMapping("/getRobotSoftwareVersionList")
    @ResponseBody
    public PageDataResult getRobotSoftwareVersionList(@RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize, SysRobotSoftwareVersion sysRobotSoftwareVersion){
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
            search.put("robotCode",sysRobotSoftwareVersion.getRobotCode());
            search.put("scenicSpotId",sysRobotSoftwareVersion.getScenicSpotId());
            search.put("upgradeModule",sysRobotSoftwareVersion.getUpgradeModule());
            search.put("state",sysRobotSoftwareVersion.getState());
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
                if (ToolUtil.isEmpty(sysRobotSoftwareVersion.getRobotCode()) && ToolUtil.isEmpty(sysRobotSoftwareVersion.getScenicSpotId()) && ToolUtil.isEmpty(sysRobotSoftwareVersion.getUpgradeModule())) {
                    startTime = DateUtil.crutDate();
                    endTime = DateUtil.crutDate();
                }
            }
            search.put("state",sysRobotSoftwareVersion.getState());
            search.put("startTime",startTime);
            search.put("endTime",endTime);
            pageDataResult = sysRobotSoftwareVersionService.getRobotSoftwareVersionList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("getRobotSoftwareVersionList",e);
        }
        return pageDataResult;
    }

    /**
     * @Method uploadExcelRobotSoftwareVersion
     * @Author 郭凯
     * @Version  1.0
     * @Description 导出Excel表
     * @Return void
     * @Date 2021/6/27 9:45
     */
    @RequestMapping("/uploadExcelRobotSoftwareVersion")
    public void uploadExcelRobotSoftwareVersion(HttpServletResponse response,SysRobotSoftwareVersion sysRobotSoftwareVersion){
        Map<String,Object> search = new HashMap<>();
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        search.put("robotCode",sysRobotSoftwareVersion.getRobotCode());
        search.put("scenicSpotId",sysRobotSoftwareVersion.getScenicSpotId());
        search.put("upgradeModule",sysRobotSoftwareVersion.getUpgradeModule());
        search.put("state",sysRobotSoftwareVersion.getState());
        //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
        if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
            if (ToolUtil.isEmpty(sysRobotSoftwareVersion.getRobotCode()) && ToolUtil.isEmpty(sysRobotSoftwareVersion.getScenicSpotId()) && ToolUtil.isEmpty(sysRobotSoftwareVersion.getUpgradeModule())) {
                startTime = DateUtil.crutDate();
                endTime = DateUtil.crutDate();
            }
        }
        search.put("startTime",startTime);
        search.put("endTime",endTime);
        List<SysRobotSoftwareVersion> robotSoftwareVersionList = sysRobotSoftwareVersionService.getRobotSoftwareVersionExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("机器人升级记录", "机器人升级记录", SysRobotSoftwareVersion.class, robotSoftwareVersionList),"机器人升级记录"+ dateTime,response);
    }
}
