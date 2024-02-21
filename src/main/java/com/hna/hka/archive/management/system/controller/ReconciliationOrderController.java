package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysOrder;
import com.hna.hka.archive.management.system.model.UploadOrderExcelBean;
import com.hna.hka.archive.management.system.model.UploadOrderExcelVoBean;
import com.hna.hka.archive.management.system.service.SysOrderService;
import com.hna.hka.archive.management.system.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: ReconciliationOrderController
 * @Author: 郭凯
 * @Description: 对账订单管理控制层
 * @Date: 2020/11/4 15:59
 * @Version: 1.0
 */
@RequestMapping("/system/reconciliationOrder")
@Controller
public class ReconciliationOrderController extends PublicUtil {

    @Autowired
    private SysOrderService sysOrderService;
    
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpSession session;

    /**
     * @Author 郭凯
     * @Description 对账订单管理列表查询
     * @Date 16:02 2020/11/4
     * @Param [pageNum, pageSize, sysOrder]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getReconciliationOrderList")
    @ResponseBody
    public PageDataResult getReconciliationOrderList(@RequestParam("pageNum") Integer pageNum,
                                       @RequestParam("pageSize") Integer pageSize, SysOrder sysOrder) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("subMethod",sysOrder.getSubMethod());
            search.put("orderScenicSpotId",session.getAttribute("scenicSpotId").toString());
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
//            if(StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)){
//                search.put("startTime",DateUtil.crutDate());
//                search.put("endTime",DateUtil.addDay(DateUtil.crutDate(), 1));
//            }else{
//                search.put("startTime",startTime);
//                search.put("endTime",DateUtil.addDay(endTime, 1));
//            }
            search.put("startTime", startTime);
            search.put("endTime", endTime);
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
                search.put("time", DateUtil.crutDate());
            }
            pageDataResult = sysOrderService.getReconciliationOrderList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("对账订单管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 下载对账订单Excel表
     * @Date 17:30 2020/11/4
     * @Param [response, request, SysOrder, session]
     * @return void
    **/
    @RequestMapping(value = "/uploadExcelReconciliationOrder")
    public void uploadExcelReconciliationOrder(HttpServletResponse response, @ModelAttribute SysOrder SysOrder) throws Exception {
        Map<String,Object> search = new HashMap<>();
        search.put("data",request.getParameter("data"));

//        search.put("startDate","2022-11-01");
//        search.put("endDate","2022-11-30");

        search.put("subMethod",SysOrder.getSubMethod());
        search.put("scenicSpotId",session.getAttribute("scenicSpotId").toString());
        List<UploadOrderExcelBean> orderListByExample = sysOrderService.getReconciliationOrderVoExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("景区订单", "景区订单", UploadOrderExcelBean.class, orderListByExample),"景区订单"+ dateTime +".xls",response);
    }
    
    /**
    * @Author 郭凯
    * @Description: 下载储值抵扣的订单
    * @Title: uploadExcelReconciliationDeductionOrder
    * @date  2021年1月12日 下午3:54:06 
    * @param @param response
    * @param @param SysOrder
    * @param @throws Exception
    * @return void
    * @throws
     */
    @RequestMapping(value = "/uploadExcelReconciliationDeductionOrder")
    public void uploadExcelReconciliationDeductionOrder(HttpServletResponse response, @ModelAttribute SysOrder SysOrder) throws Exception {
        Map<String,Object> search = new HashMap<>();
        search.put("data",request.getParameter("data"));
        search.put("subMethod",SysOrder.getSubMethod());
        search.put("scenicSpotId",session.getAttribute("scenicSpotId").toString());
        List<UploadOrderExcelVoBean> orderListByExample = sysOrderService.getReconciliationDeductionOrderExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("景区订单", "景区订单", UploadOrderExcelVoBean.class, orderListByExample),"景区订单"+ dateTime +".xls",response);
    }



}
