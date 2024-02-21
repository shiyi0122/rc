package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysOrderCurrentLog;
import com.hna.hka.archive.management.system.model.SysOrderLog;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.service.SysOrderCurrentLogService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ToolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/12/21 18:31
 * 普通订单修改为储值订单日志
 */

@Api(tags = "普通订单修改为储值订单日志")
@RequestMapping("/system/orderCurrentLog")
@Controller
public class OrderCurrentLogController extends PublicUtil {

    @Autowired
    SysOrderCurrentLogService sysOrderCurrentLogService;

    @Autowired
    private HttpServletRequest request;


    @ApiOperation("日志列表")
    @RequestMapping("/getOperationLogRefundOrderList")
    @ResponseBody
    public PageDataResult getOperationLogRefundOrderList(@RequestParam("pageNum") Integer pageNum,
                                                         @RequestParam("pageSize") Integer pageSize, SysOrderCurrentLog sysOrderCurrentLog) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("afterPhone",sysOrderCurrentLog.getAfterPhone());
            search.put("orderNumber",sysOrderCurrentLog.getOrderNumber());
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            search.put("startTime", startTime);
            search.put("endTime", endTime);
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
//            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
//                if (ToolUtil.isEmpty(sysOrderLog.getOrderLogPhone())) {
//                    search.put("time", DateUtil.crutDate());
//                }
//            }
            pageDataResult = sysOrderCurrentLogService.getSysOrderCurrentLogList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("日志列表查询失败",e);
        }
        return pageDataResult;
    }



}
