package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.SysAttendanceRules;
import com.hna.hka.archive.management.assetsSystem.service.SysAttendanceRulesService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.controller
 * @ClassName: AttendanceRulesController
 * @Author: 郭凯
 * @Description: 考勤规则控制层
 * @Date: 2021/6/3 11:00
 * @Version: 1.0
 */
@RequestMapping("/system/attendanceRules")
@Controller
public class AttendanceRulesController extends PublicUtil {

    @Autowired
    private SysAttendanceRulesService sysAttendanceRulesService;


    /**
     * @Method getAttendanceRulesList
     * @Author 郭凯
     * @Version  1.0
     * @Description 考勤规则列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/6/3 11:09
     */
    @RequestMapping("/getAttendanceRulesList")
    @ResponseBody
    public PageDataResult getAttendanceRulesList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, SysAttendanceRules sysAttendanceRules) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("scenicSpotId",sysAttendanceRules.getScenicSpotId());
            search.put("attendanceRulesName",sysAttendanceRules.getAttendanceRulesName());
            pageDataResult = sysAttendanceRulesService.getAttendanceRulesList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("考勤规则列表查询失败",e);
        }
        return pageDataResult;
    }

}
