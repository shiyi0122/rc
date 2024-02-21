package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.WechatSysDepositLog;
import com.hna.hka.archive.management.system.service.WechatSysDepositLogService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: DepositLogController
 * @Author: 郭凯
 * @Description: 押金退款日志控制层
 * @Date: 2020/5/28 17:34
 * @Version: 1.0
 */
@RequestMapping("/system/depositLog")
@Controller
public class DepositLogController extends PublicUtil {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private WechatSysDepositLogService wechatSysDepositLogService;

    /**
     * @Author 郭凯
     * @Description 押金退款日志列表查询
     * @Date 17:41 2020/5/28
     * @Param [pageNum, pageSize, wechatSysDepositLog]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getSysDepositLogList")
    @ResponseBody
    public PageDataResult getSysDepositLogList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, WechatSysDepositLog wechatSysDepositLog) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("depositPhone",wechatSysDepositLog.getDepositPhone());
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            search.put("startTime", startTime);
            search.put("endTime", endTime);
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
            	if (ToolUtil.isEmpty(wechatSysDepositLog.getDepositPhone())) {
            		search.put("time", DateUtil.crutDate());
				}
            }
            pageDataResult = wechatSysDepositLogService.getSysDepositLogList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("押金退款日志列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 客户退款日志下载Excel
     * @Date 14:26 2020/5/29
     * @Param [response, wechatSysDepositLog]
     * @return void
    **/
    @RequestMapping("/uploadExcelPaybackLog")
    public void uploadExcelPaybackLog(HttpServletResponse response,WechatSysDepositLog wechatSysDepositLog) throws Exception {
        List<WechatSysDepositLog> depositLogExcel = null;
        Map<String,Object> search = new HashMap<>();
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        search.put("depositPhone",wechatSysDepositLog.getDepositPhone());
        search.put("startTime", startTime);
        search.put("endTime", endTime);
        //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
        if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
        	if (ToolUtil.isEmpty(wechatSysDepositLog.getDepositPhone())) {
        		search.put("time", DateUtil.crutDate());
			}
        }
        depositLogExcel = wechatSysDepositLogService.getUploadExcelPaybackLog(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("押金退款日志", "押金退款日志", WechatSysDepositLog.class, depositLogExcel),"押金退款日志"+ dateTime +".xls",response);
    }

}
