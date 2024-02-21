package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysLogWithBLOBs;
import com.hna.hka.archive.management.system.service.SysLogService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
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
 * @ClassName: StoredValueAmountLogController
 * @Author: 郭凯
 * @Description: 储值金额修改日志控制层
 * @Date: 2020/12/2 9:21
 * @Version: 1.0
 */
@RequestMapping("/system/storedValueAmountLog")
@Controller
public class StoredValueAmountLogController extends PublicUtil {

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private HttpServletRequest request;

    /**
     * @Author 郭凯
     * @Description 储值金额修改日志列表查询
     * @Date 9:43 2020/12/2
     * @Param [pageNum, pageSize, sysLog]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getStoredValueAmountLogList")
    @ResponseBody
    public PageDataResult getStoredValueAmountLogList(@RequestParam("pageNum") Integer pageNum,
                                                @RequestParam("pageSize") Integer pageSize, SysLogWithBLOBs sysLog) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("logUserPhone",sysLog.getLogUserPhone());
            search.put("logType",sysLog.getLogType());
            String scenicSpotName = request.getParameter("scenicSpotName");
            search.put("scenicSpotName",scenicSpotName);
            pageDataResult = sysLogService.getStoredValueAmountLogList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("储值金额修改日志列表查询失败",e);
        }
        return pageDataResult;
    }
}
