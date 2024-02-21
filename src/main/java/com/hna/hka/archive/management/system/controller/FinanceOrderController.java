package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysOrder;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.service.SysOrderService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: FinanceOrderController
 * @Author: 郭凯
 * @Description: 财务订单查看控制层
 * @Date: 2021/5/21 14:28
 * @Version: 1.0
 */
@RequestMapping("/system/financeOrder")
@Controller
public class FinanceOrderController extends PublicUtil {

    @Autowired
    private SysOrderService sysOrderService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpSession session;




    /**
     * @Method getFinanceOrderList
     * @Author 郭凯
     * @Version  1.0
     * @Description 财务订单查看列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/5/21 15:48
     */
    @RequestMapping("/getFinanceOrderList")
    @ResponseBody
    public PageDataResult getFinanceOrderList(@RequestParam("pageNum") Integer pageNum,
                                              @RequestParam("pageSize") Integer pageSize, SysOrder sysOrder) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        SysUsers SysUsers = this.getSysUser();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("orderStatus",sysOrder.getOrderStatus());
            search.put("userId",SysUsers.getUserId());
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            String scenicSpotId = request.getParameter("scenicSpotId");
            String companyId = request.getParameter("companyId");
            search.put("startTime", startTime);
            if (!StringUtils.isEmpty(endTime)){
                search.put("endTime",DateUtil.addDay(endTime,1) );
            }

            search.put("companyId",companyId);
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
            if (startTime == null || "".equals(endTime)) {
                if (ToolUtil.isEmpty(scenicSpotId) && ToolUtil.isEmpty(sysOrder.getOrderStatus()) && ToolUtil.isEmpty(companyId)) {
                    search.put("time", DateUtil.crutDate());
                }
            }
            if (StringUtils.isEmpty(scenicSpotId)){
                scenicSpotId = session.getAttribute("scenicSpotId").toString();
            }
            search.put("orderScenicSpotId",scenicSpotId);
            pageDataResult = sysOrderService.getFinanceOrderList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("财务订单查看列表查询失败",e);
        }
        return pageDataResult;
    }
}
