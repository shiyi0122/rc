package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysDepositRefundLog;
import com.hna.hka.archive.management.system.service.SysDepositRefundLogService;
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
 * @ClassName: DepositRefundLogController
 * @Author: 郭凯
 * @Description: 押金扣款控制层
 * @Date: 2020/9/9 15:04
 * @Version: 1.0
 */
@RequestMapping("/system/depositRefundLog")
@Controller
public class DepositRefundLogController extends PublicUtil {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysDepositRefundLogService sysDepositRefundLogService;

    /**
     * @Author 郭凯
     * @Description 押金扣款日志列表查询
     * @Date 15:19 2020/9/9
     * @Param [pageNum, pageSize, sysDepositRefundLog]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getDepositRefundLogList")
    @ResponseBody
    public PageDataResult getDepositRefundLogList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, SysDepositRefundLog sysDepositRefundLog) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("userPhone",sysDepositRefundLog.getUserPhone());
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            search.put("startTime", startTime);
            search.put("endTime", endTime);
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
                if (ToolUtil.isEmpty(sysDepositRefundLog.getUserPhone())){
                    search.put("time", DateUtil.crutDate());
                }
            }
            pageDataResult = sysDepositRefundLogService.getDepositRefundLogList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("押金扣款日志列表查询失败",e);
        }
        return pageDataResult;
    }

}
