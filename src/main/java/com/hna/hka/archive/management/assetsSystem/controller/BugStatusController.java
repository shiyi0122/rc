package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.BugStatus;
import com.hna.hka.archive.management.assetsSystem.service.SysBugStatusService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.controller
 * @ClassName: BugStatusController
 * @Author: 郭凯
 * @Description: 订单故障统计控制层
 * @Date: 2021/7/11 16:07
 * @Version: 1.0
 */
@RequestMapping("/system/bugStatus")
@Controller
public class BugStatusController extends PublicUtil {

    @Autowired
    private SysBugStatusService sysBugStatusService;

    /**
     * @Method getBugStatusList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询订单故障统计列表
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/7/11 16:16
     */
    @RequestMapping("/getBugStatusList")
    @ResponseBody
    public PageDataResult getBugStatusList(Integer currPage, Integer pageSize , BugStatus bugStatus){
        PageDataResult pageDataResult = new PageDataResult();
        try {
            if(null == currPage) {
                currPage = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            Map<String,String> search = new HashMap<>();
            search.put("startTime",bugStatus.getStartTime());
            search.put("endTime",bugStatus.getEndTime());
            //查询上报原因大类，不根据景区
            if (ToolUtil.isNotEmpty(bugStatus.getCheckScenicWay()) && ToolUtil.isNotEmpty(bugStatus.getStatsOptionWay()) && "1".equals(bugStatus.getCheckScenicWay()) && "3".equals(bugStatus.getStatsOptionWay())) {
                pageDataResult = sysBugStatusService.getBugStatusCausesList(currPage, pageSize, search);
            }else if (ToolUtil.isNotEmpty(bugStatus.getCheckScenicWay()) && ToolUtil.isNotEmpty(bugStatus.getStatsOptionWay()) && "2".equals(bugStatus.getCheckScenicWay()) && "3".equals(bugStatus.getStatsOptionWay())){
                //查询上报原因大类，根据景区
                pageDataResult = sysBugStatusService.getBugStatusCausesSpotList(currPage, pageSize, search);
            }else if (ToolUtil.isNotEmpty(bugStatus.getCheckScenicWay()) && ToolUtil.isNotEmpty(bugStatus.getStatsOptionWay()) && "1".equals(bugStatus.getCheckScenicWay()) && "1".equals(bugStatus.getStatsOptionWay())){
                //查询上报原因，不根据景区
                pageDataResult = sysBugStatusService.getBugStatusReasonsList(currPage, pageSize, search);
            }else if (ToolUtil.isNotEmpty(bugStatus.getCheckScenicWay()) && ToolUtil.isNotEmpty(bugStatus.getStatsOptionWay()) && "2".equals(bugStatus.getCheckScenicWay()) && "1".equals(bugStatus.getStatsOptionWay())){
                //查询上报原因，根据景区
                pageDataResult = sysBugStatusService.getBugStatusReasonsSpotList(currPage, pageSize, search);
            }
        }catch (Exception e){
            logger.info("getBugStatusList",e);
        }
        return pageDataResult;
    }

}
