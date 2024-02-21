package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysScenicSpotCapPriceLog;
import com.hna.hka.archive.management.system.service.SysScenicSpotCapPriceLogService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: ScenicSpotCapPriceController
 * @Author: 郭凯
 * @Description: 景区封顶价格修改日志控制层
 * @Date: 2020/9/10 9:44
 * @Version: 1.0
 */
@RequestMapping("/system/scenicSpotCapPrice")
@Controller
public class ScenicSpotCapPriceLogController extends PublicUtil {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysScenicSpotCapPriceLogService sysScenicSpotCapPriceLogService;

    /**
     * @Author 郭凯
     * @Description 景区封顶价格修改日志列表查询
     * @Date 9:50 2020/9/10
     * @Param [pageNum, pageSize, sysScenicSpotCapPrice]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping(value = "/getScenicSpotCapPriceList")
    @ResponseBody
    public PageDataResult getScenicSpotCapPriceList(@RequestParam("pageNum") Integer pageNum,
                                                   @RequestParam("pageSize") Integer pageSize, SysScenicSpotCapPriceLog sysScenicSpotCapPriceLog) {
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
            search.put("startTime", startTime);
            search.put("endTime", endTime);
            search.put("scenicSpotId", sysScenicSpotCapPriceLog.getScenicSpotId());
            //判断开始时间和结束是否为空，全部满足空的条件才已当前时间为条件查询
            if (ToolUtil.isEmpty(startTime) || ToolUtil.isEmpty(endTime)) {
                search.put("time", DateUtil.crutDate());
            }
            pageDataResult = sysScenicSpotCapPriceLogService.getScenicSpotCapPriceLogList(pageNum,pageSize,search);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("景区封顶价格修改日志列表查询异常！", e);
        }
        return pageDataResult;
    }

}
