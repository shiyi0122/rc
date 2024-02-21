package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysScenicSpotRestrictedAreaWithBLOBs;
import com.hna.hka.archive.management.system.service.SysScenicSpotRestrictedAreaService;
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
 * @ClassName: RestrictedAreaController
 * @Author: 郭凯
 * @Description: 景区告警日志控制层
 * @Date: 2020/5/31 16:26
 * @Version: 1.0
 */
@RequestMapping("/system/restrictedAreat")
@Controller
public class RestrictedAreaController extends PublicUtil {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysScenicSpotRestrictedAreaService sysScenicSpotRestrictedAreaService;

    /**
     * @Author 郭凯
     * @Description 禁区告警日志列表查询
     * @Date 16:30 2020/5/31
     * @Param [pageNum, pageSize, sysScenicSpotRestrictedAreaWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getRestrictedAreatList")
    @ResponseBody
    public PageDataResult getRestrictedAreatList(@RequestParam("pageNum") Integer pageNum,
                                                        @RequestParam("pageSize") Integer pageSize, SysScenicSpotRestrictedAreaWithBLOBs sysScenicSpotRestrictedAreaWithBLOBs) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("restrictedWarningUserPhone",sysScenicSpotRestrictedAreaWithBLOBs.getRestrictedWarningUserPhone());
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            search.put("startTime", startTime);
            search.put("endTime", endTime);
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
            	if (ToolUtil.isEmpty(sysScenicSpotRestrictedAreaWithBLOBs.getRestrictedWarningUserPhone())) {
            		search.put("time", DateUtil.crutDate());
				}
            }
            pageDataResult = sysScenicSpotRestrictedAreaService.getRestrictedAreatList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("禁区告警日志列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 下载禁区告警日志Excel表
     * @Date 16:57 2020/5/31
     * @Param [response, sysScenicSpotRestrictedAreaWithBLOBs]
     * @return void
    **/
    @RequestMapping("/uploadExcelRestrictedArea")
    public void uploadExcelRestrictedArea(HttpServletResponse response, SysScenicSpotRestrictedAreaWithBLOBs sysScenicSpotRestrictedAreaWithBLOBs) throws Exception {
        List<SysScenicSpotRestrictedAreaWithBLOBs> restrictedAreaExcel = null;
        Map<String,Object> search = new HashMap<>();
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        search.put("restrictedWarningUserPhone",sysScenicSpotRestrictedAreaWithBLOBs.getRestrictedWarningUserPhone());
        search.put("startTime", startTime);
        search.put("endTime", endTime);
        //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
        if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
        	if (ToolUtil.isEmpty(sysScenicSpotRestrictedAreaWithBLOBs.getRestrictedWarningUserPhone())) {
        		search.put("time", DateUtil.crutDate());
			}
        }

        //查询下载所需要的的数据
        restrictedAreaExcel = sysScenicSpotRestrictedAreaService.getUploadExcelRestrictedArea(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("禁区告警日志", "禁区告警日志", SysScenicSpotRestrictedAreaWithBLOBs.class, restrictedAreaExcel),"禁区告警日志"+ dateTime +".xls",response);
    }


}
