package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysCurrentUserAccountDeduction;
import com.hna.hka.archive.management.system.model.SysOrderOperationLog;
import com.hna.hka.archive.management.system.model.SysRobotFaule;
import com.hna.hka.archive.management.system.service.SysCurrentUserAccountDeductionService;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @Author zhang
 * @Date 2022/12/8 17:07
 * 储值用户账户金额变动日志
 */
@Api(tags = "储值用户账户金额变动日志")
@RequestMapping("/system/currentUserAccountDeduction")
@Controller
public class CurrentUserAccountDeductionController  extends PublicUtil {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    SysCurrentUserAccountDeductionService sysCurrentUserAccountDeductionService;

    @ApiOperation("查询用户储值变动金额")
    @RequestMapping("/getCurrentUserAccountDeductionList")
    @ResponseBody
    public PageDataResult getCurrentUserAccountDeductionList(@RequestParam("pageNum") Integer pageNum,
                                                   @RequestParam("pageSize") Integer pageSize, SysCurrentUserAccountDeduction sysCurrentUserAccountDeduction) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("userPhone",sysCurrentUserAccountDeduction.getUserPhone());
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");

            search.put("startTime", startTime);
            search.put("endTime", endTime);
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
//            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
//                if (ToolUtil.isEmpty(sysOrderOperationLog.getOperationPhone())) {
//                    search.put("time", DateUtil.crutDate());
//                }
//            }
            pageDataResult = sysCurrentUserAccountDeductionService.getCurrentUserAccountDeductionList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("储值金额变动日志列表查询失败",e);
        }
        return pageDataResult;
    }


    /**
     * @Author 郭凯
     * @Description 用户变动金额日志Excel表查询
     * @Date 15:29 2020/5/29
     * @Param [response, sysRobotFaule]
     * @return void
     **/
    @RequestMapping("/uploadExcelUserPhoneLog")
    public void uploadExceluserPhoneLog(HttpServletResponse response , SysCurrentUserAccountDeduction sysCurrentUserAccountDeduction) throws Exception {
        List<SysCurrentUserAccountDeduction> currentUserAccountDeductions = null;
        Map<String,Object> search = new HashMap<>();
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        search.put("userPhone",sysCurrentUserAccountDeduction.getUserPhone());
        search.put("startTime", startTime);
        search.put("endTime", endTime);
        //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
        if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
                search.put("time", DateUtil.crutDate());
        }
        currentUserAccountDeductions = sysCurrentUserAccountDeductionService.uploadExceluserPhoneLog(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("储值金额变动日志", "储值金额变动日志", SysCurrentUserAccountDeduction.class, currentUserAccountDeductions),"储值金额变动日志"+ dateTime +".xls",response);
    }







}
