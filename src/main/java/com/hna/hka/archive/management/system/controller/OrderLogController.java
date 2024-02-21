package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysOrderLog;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.service.SysOrderLogService;
import com.hna.hka.archive.management.system.util.*;

import org.apache.commons.lang.time.DateFormatUtils;
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
 * @ClassName: OrderLogController
 * @Author: 郭凯
 * @Description: 订单退款日志控制层
 * @Date: 2020/5/29 10:18
 * @Version: 1.0
 */
@RequestMapping("/system/orderLog")
@Controller
public class OrderLogController extends PublicUtil {

    @Autowired
    private SysOrderLogService sysOrderLogService;

    @Autowired
    private HttpServletRequest request;

    /**
     * @Author 郭凯
     * @Description 订单退款日志列表查询
     * @Date 10:25 2020/5/29
     * @Param [pageNum, pageSize, sysOrderLog]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getOperationLogRefundOrderList")
    @ResponseBody
    public PageDataResult getOperationLogRefundOrderList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize, SysOrderLog sysOrderLog) {
        PageDataResult pageDataResult = new PageDataResult();
        SysUsers SysUsers = this.getSysUser();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("userId",SysUsers.getUserId().toString());
            search.put("orderLogPhone",sysOrderLog.getOrderLogPhone());
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            search.put("startTime", startTime);
            search.put("endTime", endTime);
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
            	if (ToolUtil.isEmpty(sysOrderLog.getOrderLogPhone())) {
            		search.put("time", DateUtil.crutDate());
				}
            }
            pageDataResult = sysOrderLogService.getOperationLogRefundOrderList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("订单退款日志列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 下载订单退款日志Excel表
     * @Date 13:22 2020/5/29
     * @Param [response]
     * @return void
    **/
    @RequestMapping("/uploadExcelOrderLog")
    public void uploadExcelOrderLog(HttpServletResponse response,SysOrderLog sysOrderLog) throws Exception {
        List<SysOrderLog> orderLogExcel = null;
        Map<String,Object> search = new HashMap<>();
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        search.put("orderLogPhone",sysOrderLog.getOrderLogPhone());
        search.put("startTime", startTime);
        search.put("endTime", endTime);
        //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
        if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
        	if (ToolUtil.isEmpty(sysOrderLog.getOrderLogPhone())) {
        		search.put("time", DateUtil.crutDate());
			}
        }
        orderLogExcel = sysOrderLogService.getUploadExcelOrderLog(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("订单退款日志", "订单退款日志", SysOrderLog.class, orderLogExcel),"订单退款日志"+ dateTime +".xls",response);
    }

}
