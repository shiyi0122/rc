package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysScenicSpotPriceModificationLog;
import com.hna.hka.archive.management.system.model.SysScenicSpotPriceModificationLogVo;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.service.SysScenicSpotPriceModificationLogService;
import com.hna.hka.archive.management.system.util.*;

import org.apache.commons.lang3.time.DateFormatUtils;
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
 * @ClassName: PriceModificationLog
 * @Author: 郭凯
 * @Description: 景区操作日志控制层
 * @Date: 2020/6/1 13:39
 * @Version: 1.0
 */
@RequestMapping("/system/priceModificationLog")
@Controller
public class PriceModificationLogController extends PublicUtil {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysScenicSpotPriceModificationLogService SysScenicSpotPriceModificationLogService;

    /**
     * @Author 郭凯
     * @Description 景区操作日志列表查询
     * @Date 13:55 2020/6/1
     * @Param [pageNum, pageSize, sysScenicSpotPriceModificationLog]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getPriceModificationLogList")
    @ResponseBody
    public PageDataResult getPriceModificationLogList(@RequestParam("pageNum") Integer pageNum,
                                                         @RequestParam("pageSize") Integer pageSize, SysScenicSpotPriceModificationLog sysScenicSpotPriceModificationLog) {
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
            search.put("priceModificationSpotName",sysScenicSpotPriceModificationLog.getPriceModificationSpotName());
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            search.put("startTime", startTime);
            search.put("endTime", endTime);
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
            	if(ToolUtil.isEmpty(sysScenicSpotPriceModificationLog.getPriceModificationSpotName())) {
            		search.put("time", DateUtil.crutDate());
            	}
            }
            pageDataResult = SysScenicSpotPriceModificationLogService.getPriceModificationLogList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("景区操作日志列表查询失败",e);
        }
        return pageDataResult;
    }


    /**
     * @Author 郭凯
     * @Description 下载计费规则Excel表
     * @Date 15:10 2020/6/1
     * @Param [response, request, sysScenicSpotPriceModificationLog]
     * @return void
    **/
    @RequestMapping(value = "/uploadExcelPriceModificationLog")
    public void  uploadExcelPriceModificationLog(HttpServletResponse response, HttpServletRequest request,SysScenicSpotPriceModificationLog sysScenicSpotPriceModificationLog) throws Exception {
        Map<String,Object> search = new HashMap<>();
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        search.put("priceModificationSpotName",sysScenicSpotPriceModificationLog.getPriceModificationSpotName());
        search.put("startTime", startTime);
        search.put("endTime", endTime);
        //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
        if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
        	if(ToolUtil.isEmpty(sysScenicSpotPriceModificationLog.getPriceModificationSpotName())) {
        		search.put("time", DateUtil.crutDate());
        	}
        }
        //下载EXCEl数据查询
        List<SysScenicSpotPriceModificationLog> modificationLogs = SysScenicSpotPriceModificationLogService.getSysScenicSpotPriceModificationLogExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("景区价格修改日志", "景区价格修改日志", SysScenicSpotPriceModificationLog.class, modificationLogs),"景区价格修改日志"+ dateTime +".xls",response);
    }
    
    /**
    * @Author 郭凯
    * @Description: 下载基础档案Excel表
    * @Title: uploadExcelBasicArchivesLog
    * @date  2021年3月10日 下午2:19:10 
    * @param @param response
    * @param @param request
    * @param @param sysScenicSpotPriceModificationLog
    * @param @throws Exception
    * @return void
    * @throws
     */
    @RequestMapping(value = "/uploadExcelBasicArchivesLog")
    public void  uploadExcelBasicArchivesLog(HttpServletResponse response, HttpServletRequest request,SysScenicSpotPriceModificationLog sysScenicSpotPriceModificationLog) throws Exception {
    	Map<String,Object> search = new HashMap<>();
    	String startTime = request.getParameter("startTime");
    	String endTime = request.getParameter("endTime");
    	search.put("priceModificationSpotName",sysScenicSpotPriceModificationLog.getPriceModificationSpotName());
    	search.put("startTime", startTime);
    	search.put("endTime", endTime);
    	//判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
    	if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
    		if(ToolUtil.isEmpty(sysScenicSpotPriceModificationLog.getPriceModificationSpotName())) {
    			search.put("time", DateUtil.crutDate());
    		}
    	}
    	//下载EXCEl数据查询
    	List<SysScenicSpotPriceModificationLogVo> modificationLogs = SysScenicSpotPriceModificationLogService.getSysScenicSpotPriceModificationLogVoExcel(search);
    	String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
    	FileUtil.exportExcel(FileUtil.getWorkbook("景区档案修改日志", "景区档案修改日志", SysScenicSpotPriceModificationLogVo.class, modificationLogs),"景区档案修改日志"+ dateTime +".xls",response);
    }

}