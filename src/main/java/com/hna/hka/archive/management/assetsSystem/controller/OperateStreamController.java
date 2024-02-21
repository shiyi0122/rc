package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.OperatingTime;
import com.hna.hka.archive.management.assetsSystem.model.ReportForm;
import com.hna.hka.archive.management.assetsSystem.service.SysOperateStreamService;
import com.hna.hka.archive.management.system.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.controller
 * @ClassName: OperateStreamController
 * @Author: 郭凯
 * @Description: 运营流水控制层
 * @Date: 2021/7/4 18:49
 * @Version: 1.0
 */
@RequestMapping("/system/operateStream")
@Controller
public class OperateStreamController extends PublicUtil {

    @Autowired
    private SysOperateStreamService sysOperateStreamService;

    /**
     * @Method getOperateStreamList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询运营流水列表
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/7/4 18:53
     */
    @RequestMapping("/getOperateStreamList")
    @ResponseBody
    public PageDataResult getOperateStreamList(Integer currPage, Integer pageSize , OperatingTime operatingTime){
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
            search.put("YoYOrderBy",operatingTime.getYoYOrderBy());
            search.put("QoQOrderBy",operatingTime.getQoQOrderBy());
            search.put("robotUseRatioOrderBy",operatingTime.getRobotUseRatioOrderBy());
            search.put("dateType",operatingTime.getChooseDate());
            search.put("amountWay",operatingTime.getAmountWay());
            search.put("startTime",operatingTime.getStartTime());
            search.put("endTime",operatingTime.getEndTime());
            search.put("completeRInputValue",operatingTime.getCompleteRInputValue());
            if (ToolUtil.isEmpty(operatingTime.getYoYOrderBy()) && ToolUtil.isEmpty(operatingTime.getQoQOrderBy()) && ToolUtil.isEmpty(operatingTime.getRobotUseRatioOrderBy())){
                search.put("operatingTimeBy","amount + 0 desc");
            }
            if (operatingTime.getShowQoQ()){
                if ("1".equals(operatingTime.getNewStateWay())){
                    pageDataResult = sysOperateStreamService.getOperateStreamListShowQoQ(currPage,pageSize,search);
                }else if ("0".equals(operatingTime.getNewStateWay())){
                    pageDataResult = sysOperateStreamService.getRobotOperateStreamShowQoQ(currPage,pageSize,search);
                }
            }else{
                if ("1".equals(operatingTime.getNewStateWay())){
                    pageDataResult = sysOperateStreamService.getOperateStreamList(currPage,pageSize,search);
                }else if ("0".equals(operatingTime.getNewStateWay())){
                    pageDataResult = sysOperateStreamService.getRobotOperateStreamList(currPage,pageSize,search);
                }
            }
            return pageDataResult;
        }catch (Exception e){
            logger.info("getOperateStreamList",e);
        }
        return pageDataResult;
    }

    /**
     * @Method getQueryReport
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询运营流水统计报表
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/7/10 20:26
     */
    @RequestMapping("/getQueryReportStream")
    @ResponseBody
    public ReturnModel getQueryReportStream(ReportForm reportForm){
        ReturnModel returnModel = new ReturnModel();
        try {
            //显示环比
            if (reportForm.getShowQoQ()){
                List<ReportForm> reportFormList = sysOperateStreamService.getQueryReportStreamShowQoQ(reportForm);
                returnModel.setData(reportFormList);
                returnModel.setMsg("");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{//不显示环比
                List<ReportForm> reportFormList = sysOperateStreamService.getQueryReportStreamNotShowQoQ(reportForm);
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
