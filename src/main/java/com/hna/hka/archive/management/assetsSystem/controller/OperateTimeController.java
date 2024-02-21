package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.OperatingTime;
import com.hna.hka.archive.management.assetsSystem.model.ReportForm;
import com.hna.hka.archive.management.assetsSystem.service.SysOperatingTimeService;
import com.hna.hka.archive.management.system.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.controller
 * @ClassName: OperateTimeController
 * @Author: 郭凯
 * @Description: 景区运营时长控制层
 * @Date: 2021/6/22 15:58
 * @Version: 1.0
 */
@RequestMapping("/system/scenicSpotOperatingTime")
@Controller
public class OperateTimeController extends PublicUtil {

    @Autowired
    private SysOperatingTimeService sysOperatingTimeService;


    /**
     * @Method getOperatingTimeListByMonth
     * @Author 郭凯
     * @Version  1.0
     * @Description 景区运营时长列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     */
    @RequestMapping("/getOperatingTimeList")
    @ResponseBody
    public PageDataResult getOperatingTimeList (Integer currPage, Integer pageSize , OperatingTime operatingTime){
        PageDataResult pageDataResult = new PageDataResult();
        try {
            if(null == currPage) {
                currPage = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            Map<String,String> search = new HashMap<>();
            search.put("scenicSpotId",operatingTime.getScenicSpotId());
            search.put("opeTimeInputValue",operatingTime.getOpeTimeInputValue());
            search.put("dateType",operatingTime.getChooseDate());
            search.put("operateTimeWay",operatingTime.getOperateTimeWay());
            search.put("YoYOrderBy",operatingTime.getYoYOrderBy());
            search.put("QoQOrderBy",operatingTime.getQoQOrderBy());
            search.put("robotUseRatioOrderBy",operatingTime.getRobotUseRatioOrderBy());
            search.put("startTime",operatingTime.getStartTime());
            search.put("endTime",operatingTime.getEndTime());
            if (ToolUtil.isEmpty(operatingTime.getYoYOrderBy()) && ToolUtil.isEmpty(operatingTime.getQoQOrderBy()) && ToolUtil.isEmpty(operatingTime.getRobotUseRatioOrderBy())){
                search.put("operatingTimeBy","avgOperateTime + 0 desc");
            }
            if (operatingTime.getShowQoQ()){
                if ("1".equals(operatingTime.getNewStateWay())){
                    pageDataResult = sysOperatingTimeService.getOperatingTimeListShowQoQ(currPage,pageSize,search);
                }else if ("0".equals(operatingTime.getNewStateWay())){
                    pageDataResult = sysOperatingTimeService.getRobotOperatingTimeListShowQoQ(currPage,pageSize,search);
                }
            }else{
                if ("1".equals(operatingTime.getNewStateWay())){
                    pageDataResult = sysOperatingTimeService.getOperatingTimeList(currPage,pageSize,search);
                }else if ("0".equals(operatingTime.getNewStateWay())){
                    pageDataResult = sysOperatingTimeService.getRobotOperatingTimeList(currPage,pageSize,search);
                }
            }
            return pageDataResult;
        }catch (Exception e){
            logger.info("getOperatingTimeList",e);
        }
        return pageDataResult;
    }


    /**
     * @Method getQueryReport
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询运营时长统计报表
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/7/10 11:04
     */
    @RequestMapping("/getQueryReport")
    @ResponseBody
    @CrossOrigin
    public ReturnModel getQueryReport(ReportForm reportForm){
        ReturnModel returnModel = new ReturnModel();
        try {
            //显示环比
            if (reportForm.getShowQoQ()){
                List<ReportForm> reportFormList = sysOperatingTimeService.getQueryReportShowQoQ(reportForm);
                returnModel.setData(reportFormList);
                returnModel.setMsg("");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{//不显示环比
                List<ReportForm> reportFormList = sysOperatingTimeService.getQueryReportNotShowQoQ(reportForm);
                returnModel.setData(reportFormList);
                returnModel.setMsg("");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("getQueryReport",e);
            returnModel.setData("");
            returnModel.setMsg("");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

}
