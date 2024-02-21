package com.hna.hka.archive.management.system.controller;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.service.SysOrderOperationLogService;
import com.hna.hka.archive.management.system.util.*;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: OrderOperationLogController
 * @Author: 郭凯
 * @Description: 订单操作日志控制层
 * @Date: 2020/6/1 17:03
 * @Version: 1.0
 */
@RequestMapping("/system/orderOperationLog")
@Controller
public class OrderOperationLogController extends PublicUtil {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysOrderOperationLogService sysOrderOperationLogService;

    /**
     * @Author 郭凯
     * @Description 订单操作日志列表查询
     * @Date 9:22 2020/6/2
     * @Param [pageNum, pageSize, sysOrderOperationLog]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getOrderOperationLogList")
    @ResponseBody
    public PageDataResult getOrderOperationLogList(@RequestParam("pageNum") Integer pageNum,
                                                        @RequestParam("pageSize") Integer pageSize, SysOrderOperationLog sysOrderOperationLog) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("operationPhone",sysOrderOperationLog.getOperationPhone());
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            search.put("startTime", startTime);
            search.put("endTime", endTime);
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
            	if (ToolUtil.isEmpty(sysOrderOperationLog.getOperationPhone())) {
            		search.put("time", DateUtil.crutDate());
				}
            }
            pageDataResult = sysOrderOperationLogService.getOrderOperationLogList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("订单操作日志列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 订单操作日志列表下载Excel表
     * @Date 17:14 2020/5/27
     * @Param [response]
     * @return void
     **/
    @RequestMapping(value = "/uploadExcelOrderOperationLog")
    public void  uploadExcelOrderOperationLog(HttpServletResponse response, SysOrderOperationLog sysOrderOperationLog) throws Exception {
        try {
            long start = System.currentTimeMillis() / 1000;//单位秒
            List<SysOrderOperationLog> orderListByExample = null;
            Map<String,Object> search = new HashMap<>();
            SysUsers SysUsers = this.getSysUser();
//            search.put("currentUserPhone",sysOrder.getCurrentUserPhone());
            search.put("operationPhone",sysOrderOperationLog.getOperationPhone());
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            search.put("startTime", startTime);
            search.put("endTime", endTime);
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
                if (ToolUtil.isEmpty(sysOrderOperationLog.getOperationPhone())) {
                    search.put("time", DateUtil.crutDate());
                }
            }
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
//
            OutputStream out = EasyExcel.getOutputStream(response,"订单操作日志"+ DateFormatUtils.format(new Date(), "yyyyMMddHHmm"), ExcelTypeEnum.XLSX);
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
            Sheet sheet = new Sheet(1, 0, SysOrderOperationLogExcel.class);
            //设置自适应宽度
            sheet.setAutoWidth(Boolean.TRUE);
            //设置表格样式
            sheet.setTableStyle(EasyExcel.createTableStyle());
            //设置sheetName
            sheet.setSheetName("订单操作日志");
            List<SysOrderOperationLogExcel> orderList1 = sysOrderOperationLogService.getOrderOperationLogExcel(search);
            writer.write(orderList1, sheet);
            //关闭writer的输出流
            writer.finish();
            long end = System.currentTimeMillis() / 1000;
            logger.info("导出耗时：" + (end - start) +" 秒");
        }catch (Exception e){
            logger.info("导出异常",e);
        }
    }


}
