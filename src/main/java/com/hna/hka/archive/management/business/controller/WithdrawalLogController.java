package com.hna.hka.archive.management.business.controller;

import com.hna.hka.archive.management.business.model.BusinessWithdrawalLog;
import com.hna.hka.archive.management.business.service.BusinessWithdrawalLogService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.controller
 * @ClassName: WithdrawalLogController
 * @Author: 郭凯
 * @Description: 提现申请管理控制层
 * @Date: 2020/8/13 10:35
 * @Version: 1.0
 */
@RequestMapping("/business/withdrawalLog")
@Controller
public class WithdrawalLogController extends PublicUtil {

    @Autowired
    private BusinessWithdrawalLogService businessWithdrawalLogService;

    /**
     * @Author 郭凯
     * @Description 提现申请管理列表查询
     * @Date 10:44 2020/8/13
     * @Param [pageNum, pageSize, businessWithdrawalLog]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getWithdrawalLogList")
    @ResponseBody
    public PageDataResult getWithdrawalLogList(@RequestParam("pageNum") Integer pageNum,
                                               @RequestParam("pageSize") Integer pageSize, BusinessWithdrawalLog businessWithdrawalLog) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("userName",businessWithdrawalLog.getUserName());
            search.put("state",businessWithdrawalLog.getState());
            pageDataResult = businessWithdrawalLogService.getWithdrawalLogList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("提现申请管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 通过或者驳回状态修改
     * @Date 13:30 2020/8/13
     * @Param [businessWithdrawalLog]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editAdopt")
    @ResponseBody
    public ReturnModel editAdopt(BusinessWithdrawalLog businessWithdrawalLog) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessWithdrawalLogService.editAdopt(businessWithdrawalLog);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("状态修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (i == 3) {
                returnModel.setData("");
                returnModel.setMsg("提现金额大于余额，请修改提现金额！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else if (i == 2) {
                returnModel.setData("");
                returnModel.setMsg("用户信息获取失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else if(i == 4){
                returnModel.setData("");
                returnModel.setMsg("账户余额或者提现金额为空！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("状态修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("editAdopt", e);
            returnModel.setData("");
            returnModel.setMsg("状态修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 下载提现申请Excel表
     * @Date 13:37 2020/12/10
     * @Param [response, businessWithdrawalLog]
     * @return void
    **/
    @RequestMapping(value = "/uploadExcelWithdrawalLog")
    public void  uploadExcelWithdrawalLog(HttpServletResponse response, BusinessWithdrawalLog businessWithdrawalLog) throws Exception {
        List<BusinessWithdrawalLog> businessWithdrawalLogs = null;
        Map<String,String> search = new HashMap<>();
        search.put("userName",businessWithdrawalLog.getUserName());
        search.put("state",businessWithdrawalLog.getState());
        businessWithdrawalLogs = businessWithdrawalLogService.getWithdrawalLogExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("提现申请", "提现申请", BusinessWithdrawalLog.class, businessWithdrawalLogs),"提现申请"+ dateTime +".xls",response);
    }

}
