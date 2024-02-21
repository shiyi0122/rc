package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysOrder;
import com.hna.hka.archive.management.system.model.SysOrderVo;
import com.hna.hka.archive.management.system.service.SysOrderService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: PassengerFlowControlller
 * @Author: 郭凯
 * @Description: 客流量管理控制层
 * @Date: 2020/11/10 10:51
 * @Version: 1.0
 */
@RequestMapping("/system/passengerFlow")
@Controller
public class PassengerFlowController extends PublicUtil {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpSession session;

    @Autowired
    private SysOrderService sysOrderService;

    /**
     * @Author 郭凯
     * @Description 客流量管理列表查询
     * @Date 11:13 2020/11/10
     * @Param [pageNum, pageSize, sysOrder]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getPassengerFlowList")
    @ResponseBody
    public PageDataResult getOrderList(@RequestParam("pageNum") Integer pageNum,
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
            search.put("currentUserPhone",sysOrder.getCurrentUserPhone());
            search.put("orderScenicSpotId",session.getAttribute("scenicSpotId"));
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");

            if(org.springframework.util.StringUtils.isEmpty(startTime) || org.springframework.util.StringUtils.isEmpty(endTime)){
                search.put("startTime",DateUtil.crutDate());
                search.put("endTime",DateUtil.addDay(DateUtil.crutDate(), 1));
            }else{
                search.put("startTime",startTime);
                search.put("endTime",DateUtil.addDay(endTime, 1));
            }


//            search.put("startTime", startTime);
//            search.put("endTime", endTime);
//            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
//            if (startTime == null || "".equals(endTime)) {
//                if (ToolUtil.isEmpty(sysOrder.getCurrentUserPhone())) {
//                    search.put("time", DateUtil.crutDate());
//                }
//            }
            pageDataResult = sysOrderService.getPassengerFlowList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("客流量管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 客流量管理Excel表下载
     * @Date 11:33 2020/11/10
     * @Param [response, sysOrder]
     * @return void
    **/
    @RequestMapping(value = "/uploadExcelOrder")
    public void  uploadExcelOrder(HttpServletResponse response, SysOrder sysOrder) throws Exception {
        List<SysOrder> orderListByExample = null;
        Map<String,Object> search = new HashMap<>();
        search.put("currentUserPhone",sysOrder.getCurrentUserPhone());
        search.put("orderScenicSpotId",session.getAttribute("scenicSpotId"));
        String startTime = request.getParameter("startTime");//获取开始时间
        String endTime = request.getParameter("endTime");//获取结束时间
        search.put("startTime",startTime);
        search.put("endTime",endTime);
        //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
        if (startTime == null || "".equals(endTime)) {
            if (ToolUtil.isEmpty(sysOrder.getCurrentUserPhone())) {
                search.put("time", DateUtil.crutDate());
            }
        }
        orderListByExample = sysOrderService.getOrderVoExcel(search);
        List<SysOrderVo> sysOrderVoList = new ArrayList<SysOrderVo>();
        for(SysOrder order : orderListByExample){
            SysOrderVo sysOrderVo = new SysOrderVo();
            sysOrderVo.setCurrentUserPhone(order.getCurrentUserPhone());
            sysOrderVo.setOrderScenicSpotName(order.getOrderScenicSpotName());
            sysOrderVo.setOrderStartTime(order.getOrderStartTime());
            sysOrderVoList.add(sysOrderVo);
        }
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("客流量管理", "客流量管理", SysOrderVo.class, sysOrderVoList),"客流量管理"+ dateTime +".xls",response);
    }


}
