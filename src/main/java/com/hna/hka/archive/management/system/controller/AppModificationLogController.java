package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysAppModificationLog;
import com.hna.hka.archive.management.system.service.SysAppModificationLogService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: AppModificationLogController
 * @Author: 郭凯
 * @Description: APP操作日志控制层
 * @Date: 2020/9/10 13:20
 * @Version: 1.0
 */
@RequestMapping("/system/appModificationLog")
@Controller
public class AppModificationLogController extends PublicUtil {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysAppModificationLogService sysAppModificationLogService;


    /**
     * @Author 郭凯
     * @Description APP操作日志列表查询
     * @Date 13:25 2020/9/10
     * @Param [pageNum, pageSize, sysAppModificationLog]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getAppModificationLogList")
    @ResponseBody
    public PageDataResult getAppModificationLogList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, SysAppModificationLog sysAppModificationLog) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("modificationLogRobotCode",sysAppModificationLog.getModificationLogRobotCode());
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            search.put("startTime", startTime);
            search.put("endTime", endTime);
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
                if (ToolUtil.isEmpty(sysAppModificationLog.getModificationLogRobotCode())){
                    search.put("time", DateUtil.crutDate());
                }
            }
            pageDataResult = sysAppModificationLogService.getAppModificationLogList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("APP操作日志列表查询失败",e);
        }
        return pageDataResult;
    }

}
