package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysRobot;
import com.hna.hka.archive.management.system.model.SysRobotGpsDispatchLog;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.service.SysRobotGpsDispatchLogService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/30 13:23
 *
 * 机器人gps更新超时列表
 */
@RequestMapping("/system/sysRobotGpsDispatchLog")
@Controller
public class SysRobotGpsDispatchLogController extends PublicUtil {


    @Autowired
    SysRobotGpsDispatchLogService sysRobotGpsDispatchLogService;


    /**
     * @Author 郭凯
     * @Description 查询机器人列表
     * @Date 9:24 2020/5/20
     * @Param [pageNum, pageSize, sysRobot]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     **/
    @RequestMapping("/getRobotGpsDispatchList")
    @ResponseBody
    public PageDataResult getRobotGpsDispatchList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize, SysRobotGpsDispatchLog sysRobotGpsDispatchLog) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
                search.put("scenicSpotId",sysRobotGpsDispatchLog.getScenicSpotId());
                search.put("robotCode",sysRobotGpsDispatchLog.getRobotCode());
                pageDataResult = sysRobotGpsDispatchLogService.getRobotGpsDispatchList(pageNum,pageSize,search);

        }catch (Exception e){
            logger.info("机器人gps更新超时列表查询失败",e);
        }
        return pageDataResult;
    }





}
