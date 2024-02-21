package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysCurrentUserAgreementLog;
import com.hna.hka.archive.management.system.service.SysCurrentUserAgreementLogService;
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
 * @ClassName: CurrentUserAgreementLogController
 * @Author: 郭凯
 * @Description: 用户协议签订日志控制层
 * @Date: 2020/9/9 16:54
 * @Version: 1.0
 */
@RequestMapping("/system/currentUserAgreementLog")
@Controller
public class CurrentUserAgreementLogController extends PublicUtil {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysCurrentUserAgreementLogService sysCurrentUserAgreementLogService;


    /**
     * @Author 郭凯
     * @Description 用户协议签订日志列表查询
     * @Date 16:56 2020/9/9
     * @Param [pageNum, pageSize, sysCurrentUserAgreementLog]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getCurrentUserAgreementLogList")
    @ResponseBody
    public PageDataResult getCurrentUserAgreementLogList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, SysCurrentUserAgreementLog sysCurrentUserAgreementLog) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("userPhone",sysCurrentUserAgreementLog.getUserPhone());
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            search.put("startTime", startTime);
            search.put("endTime", endTime);
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
                if (ToolUtil.isEmpty(sysCurrentUserAgreementLog.getUserPhone())){
                    search.put("time", DateUtil.crutDate());
                }
            }
            pageDataResult = sysCurrentUserAgreementLogService.getCurrentUserAgreementLogList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("用户协议签订日志列表查询失败",e);
        }
        return pageDataResult;
    }

}
