package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysCurrentUserAccountOrder;
import com.hna.hka.archive.management.system.service.SysCurrentUserAccountOrderService;
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
 * @ClassName: CurrentUserAccountOrderController
 * @Author: 郭凯
 * @Description: 储值记录管理控制层
 * @Date: 2020/11/5 10:25
 * @Version: 1.0
 */
@RequestMapping("/system/currentUserAccountOrder")
@Controller
public class CurrentUserAccountOrderController extends PublicUtil {

    @Autowired
    private SysCurrentUserAccountOrderService sysCurrentUserAccountOrderService;

    @Autowired
    private HttpServletRequest request;

    /**
     * @Author 郭凯
     * @Description 储值记录管理列表查询
     * @Date 11:40 2020/11/5
     * @Param [pageNum, pageSize, sysCurrentUserAccountOrder]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getCurrentUserAccountOrderList")
    @ResponseBody
    public PageDataResult getCurrentUserAccountOrderList(@RequestParam("pageNum") Integer pageNum,
                                             @RequestParam("pageSize") Integer pageSize, SysCurrentUserAccountOrder sysCurrentUserAccountOrder) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("userPhone",sysCurrentUserAccountOrder.getUserPhone());
            search.put("scenicSpotId",sysCurrentUserAccountOrder.getScenicSpotId());
            search.put("startTime",request.getParameter("startTime"));
            search.put("endTime",request.getParameter("endTime"));
            search.put("scenicSpotId",sysCurrentUserAccountOrder.getScenicSpotId());
            pageDataResult = sysCurrentUserAccountOrderService.getCurrentUserAccountOrderList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("储值记录管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 下载储值记录Excel表
     * @Date 13:44 2020/11/5
     * @Param [response, sysCurrentUserAccountOrder]
     * @return void
    **/
    @RequestMapping(value = "/uploadExcelCurrentUserAccountOrder")
    public void  uploadExcelCurrentUserAccountOrder(HttpServletResponse response, SysCurrentUserAccountOrder sysCurrentUserAccountOrder){
        List<SysCurrentUserAccountOrder> accountOrderListByExample = null;
        Map<String,Object> search = new HashMap<>();
        search.put("userPhone",sysCurrentUserAccountOrder.getUserPhone());
        search.put("scenicSpotId",sysCurrentUserAccountOrder.getScenicSpotId());
        String startTime = request.getParameter("startTime");//获取开始时间
        String endTime = request.getParameter("endTime");//获取结束时间
        search.put("startTime",startTime);
        search.put("endTime",endTime);
        accountOrderListByExample = sysCurrentUserAccountOrderService.getCurrentUserAccountOrderExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("储值记录", "储值记录", SysCurrentUserAccountOrder.class, accountOrderListByExample),"储值记录"+ dateTime +".xls",response);
    }

}
