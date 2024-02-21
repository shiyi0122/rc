package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.SysInvoice;
import com.hna.hka.archive.management.assetsSystem.service.SysInvoiceService;
import com.hna.hka.archive.management.system.model.SysOrder;
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
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.controller
 * @ClassName: InvoiceController
 * @Author: 郭凯
 * @Description: 发票申请记录管理
 * @Date: 2021/6/25 18:39
 * @Version: 1.0
 */
@RequestMapping("/system/invoice")
@Controller
public class InvoiceController extends PublicUtil {

    @Autowired
    private SysInvoiceService sysInvoiceService;

    @Autowired
    private HttpServletRequest request;


    /**
     * @Method getOperatingTimeListByMonth
     * @Author 郭凯
     * @Version  1.0
     * @Description 发票申请管理列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/6/25 18:45
     */
    @RequestMapping("/getInvoiceList")
    @ResponseBody
    public PageDataResult getInvoiceList(@RequestParam("pageNum") Integer pageNum,
                                                       @RequestParam("pageSize") Integer pageSize, SysInvoice sysInvoice){
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            search.put("scenicSpotId",sysInvoice.getScenicSpotId());
            search.put("phone",sysInvoice.getPhone());
            search.put("invoiceType",sysInvoice.getInvoiceType());
            search.put("processingProgress",sysInvoice.getProcessingProgress());
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
//            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
//                if (ToolUtil.isEmpty(sysInvoice.getScenicSpotId()) && ToolUtil.isEmpty(sysInvoice.getPhone()) && ToolUtil.isEmpty(sysInvoice.getInvoiceType()) && ToolUtil.isEmpty(sysInvoice.getProcessingProgress())) {
//                    startTime = DateUtil.crutDate();
//                    endTime = DateUtil.crutDate();
//                }
//            }
            search.put("startTime",startTime);
            search.put("endTime",endTime);
            pageDataResult = sysInvoiceService.getInvoiceList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("getInvoiceList",e);
        }
        return pageDataResult;
    }


    /**
     * @Method editBilling
     * @Author 郭凯
     * @Version  1.0
     * @Description 修改为已开票状态
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/26 12:00
     */
    @RequestMapping("/editBilling")
    @ResponseBody
    public ReturnModel editBilling(SysInvoice sysInvoice){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysInvoiceService.editBilling(sysInvoice);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("开票成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("开票失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("editBilling",e);
            returnModel.setData("");
            returnModel.setMsg("系统错误！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method uploadExcelInvoice
     * @Author 郭凯
     * @Version  1.0
     * @Description 开票管理下载Excel表
     * @Return void
     * @Date 2021/6/26 15:16
     */
    @RequestMapping("/uploadExcelInvoice")
    public void uploadExcelInvoice(HttpServletResponse response, SysInvoice sysInvoice){
        Map<String,Object> search = new HashMap<>();
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        search.put("scenicSpotId",sysInvoice.getScenicSpotId());
        search.put("phone",sysInvoice.getPhone());
        search.put("invoiceType",sysInvoice.getInvoiceType());
        search.put("processingProgress",sysInvoice.getProcessingProgress());
        //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
        if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
            if (ToolUtil.isEmpty(sysInvoice.getScenicSpotId()) && ToolUtil.isEmpty(sysInvoice.getPhone()) && ToolUtil.isEmpty(sysInvoice.getInvoiceType()) && ToolUtil.isEmpty(sysInvoice.getProcessingProgress())) {
                startTime = DateUtil.crutDate();
                endTime = DateUtil.crutDate();
            }
        }
        search.put("startTime",startTime);
        search.put("endTime",endTime);
        List<SysInvoice> invoiceList = sysInvoiceService.getInvoiceExcelList(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("开票信息", "开票信息", SysInvoice.class, invoiceList),"开票信息"+ dateTime,response);

    }

}
