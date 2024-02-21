package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.OperateStream;
import com.hna.hka.archive.management.assetsSystem.model.OperatingTime;
import com.hna.hka.archive.management.assetsSystem.model.ReportForm;
import com.hna.hka.archive.management.assetsSystem.service.SysOperateStreamService;
import com.hna.hka.archive.management.system.dao.SysOrderMapper;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service.impl
 * @ClassName: SysOperateStreamServiceImpl
 * @Author: 郭凯
 * @Description: 运营流水业务层（实现）
 * @Date: 2021/7/4 18:51
 * @Version: 1.0
 */
@Service
@Transactional
public class SysOperateStreamServiceImpl implements SysOperateStreamService {

    @Autowired
    private SysOrderMapper sysOrderMapper;

    /**
     * @Method getOperateStreamListShowQoQ
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询景区运营流水（显示环比）
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/7/4 18:55
     */
    @Override
    public PageDataResult getOperateStreamListShowQoQ(Integer currPage, Integer pageSize, Map<String, String> search) throws ParseException {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(currPage, pageSize);
        if ("1".equals(search.get("dateType"))){
            search.put("cycle",DateUtil.findMonths(search.get("startTime").toString(),search.get("endTime").toString()));
        }
        if ("2".equals(search.get("dateType"))){
            search.put("cycle",DateUtil.findDates(search.get("startTime").toString(),search.get("endTime").toString()));
        }
        if ("3".equals(search.get("dateType"))){
            search.put("cycle",DateUtil.findYears(search.get("startTime").toString(),search.get("endTime").toString()));
        }
        String time = search.get("startTime") + "，" + search.get("endTime");
        if ("1".equals(search.get("dateType"))){
            search.put("endTime", DateUtil.calcLastMonth(search.get("endTime")));
        }else if ("2".equals(search.get("dateType"))){
            search.put("endTime", DateUtil.calcYesterday(search.get("endTime")));
        }
        List<OperateStream> operateStreamList = sysOrderMapper.getOperateStreamListShowQoQ(search);
        for(OperateStream operatingTime : operateStreamList){
            operatingTime.setDate(time);
        }
        if (operateStreamList.size() > 0){
            PageInfo<OperateStream> pageInfo = new PageInfo<>(operateStreamList);
            pageDataResult.setData(pageInfo.getList());
            pageDataResult.setCount((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method getOperateStreamListShowQoQ
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询机器人运营流水（显示环比）
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/7/4 18:55
     */
    @Override
    public PageDataResult getRobotOperateStreamShowQoQ(Integer currPage, Integer pageSize, Map<String, String> search) throws ParseException {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> searchN = new HashMap<>();
        PageHelper.startPage(currPage, pageSize);
        String time = search.get("startTime") + "，" + search.get("endTime");
        if ("1".equals(search.get("dateType"))){
            String startTime = search.get("startTime");
            String endTime = search.get("endTime");
            search.put("endTime", DateUtil.calcLastMonth(search.get("endTime")));

            //后添加
            endTime = DateUtil.addMouth(endTime, 1);
            searchN.put("startTime",startTime);
            searchN.put("endTime",endTime);
        }else if ("2".equals(search.get("dateType"))){
            String startTime = search.get("startTime");
            String endTime = search.get("endTime");
            search.put("endTime", DateUtil.calcYesterday(search.get("endTime")));

            //后添加
            endTime = DateUtil.addDay(endTime, 1);
            searchN.put("startTime",startTime);
            searchN.put("endTime",endTime);
        }
        List<OperateStream> operatingTimeList = sysOrderMapper.getRobotOperateStreamShowQoQ(search);
        for(OperateStream operatingTime : operatingTimeList){
            operatingTime.setDate(time);
            searchN.put("robotId",operatingTime.getRobotId());
            //查询机器人订单数和运营时长,后添加
            if (!StringUtils.isEmpty(operatingTime.getRobotId())){
                Integer orderCount = sysOrderMapper.getSysRobotOrderCount(searchN);
                Integer robotTotalTimeSum = sysOrderMapper.getSysRobotTotalTime(searchN);
                if (StringUtils.isEmpty(orderCount)){
                    orderCount = 0;
                }
                if (StringUtils.isEmpty(robotTotalTimeSum)){
                    robotTotalTimeSum = 0;
                }
                operatingTime.setOrderCount(orderCount.toString());
                operatingTime.setRobotTotalTimeSum(robotTotalTimeSum.toString());
            }

        }
        if (operatingTimeList.size() > 0){
            PageInfo<OperateStream> pageInfo = new PageInfo<>(operatingTimeList);
            pageDataResult.setData(pageInfo.getList());
            pageDataResult.setCount((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method getOperateStreamList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询景区运营流水（不显示环比）
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/7/4 18:58
     */
    @Override
    public PageDataResult getOperateStreamList(Integer currPage, Integer pageSize, Map<String, String> search) throws ParseException {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(currPage, pageSize);
        if ("1".equals(search.get("dateType"))){
            search.put("cycle",DateUtil.findMonths(search.get("startTime").toString(),search.get("endTime").toString()));
        }
        if ("2".equals(search.get("dateType"))){
            search.put("cycle",DateUtil.findDates(search.get("startTime").toString(),search.get("endTime").toString()));
        }
        if ("3".equals(search.get("dateType"))){
            search.put("cycle",DateUtil.findYears(search.get("startTime").toString(),search.get("endTime").toString()));
        }
        List<OperateStream> operatingTimeList = sysOrderMapper.getOperateStreamList(search);
        for(OperateStream operatingTime : operatingTimeList){
            operatingTime.setDate(search.get("startTime") + "，" + search.get("endTime"));
        }
        if (operatingTimeList.size() > 0){
            PageInfo<OperateStream> pageInfo = new PageInfo<>(operatingTimeList);
            pageDataResult.setData(pageInfo.getList());
            pageDataResult.setCount((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method getOperateStreamList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询机器人运营流水（不显示环比）
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/7/4 18:58
     */
    @Override
    public PageDataResult getRobotOperateStreamList(Integer currPage, Integer pageSize, Map<String, String> search) throws ParseException {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> searchN = new HashMap<>();
        PageHelper.startPage(currPage, pageSize);
        if ("1".equals(search.get("dateType"))){
            search.put("cycle",DateUtil.findMonths(search.get("startTime").toString(),search.get("endTime").toString()));

            String startTime = search.get("startTime");
            String endTime = search.get("endTime");
            endTime = DateUtil.addMouth(endTime, 1);
            searchN.put("startTime",startTime);
            searchN.put("endTime",endTime);
        }
        if ("2".equals(search.get("dateType"))){
            search.put("cycle",DateUtil.findDates(search.get("startTime").toString(),search.get("endTime").toString()));

            String startTime = search.get("startTime");
            String endTime = search.get("endTime");
            endTime = DateUtil.addDay(endTime, 1);
            searchN.put("startTime",startTime);
            searchN.put("endTime",endTime);
         }
        if ("3".equals(search.get("dateType"))){
            search.put("cycle",DateUtil.findYears(search.get("startTime").toString(),search.get("endTime").toString()));

            String startTime = search.get("startTime");
            String endTime = search.get("endTime");
            endTime = DateUtil.addYear(endTime, 1);
            searchN.put("startTime",startTime);
            searchN.put("endTime",endTime);
        }
        List<OperateStream> operatingTimeList = sysOrderMapper.getRobotOperateStreamList(search);
        for(OperateStream operatingTime : operatingTimeList){
            operatingTime.setDate(search.get("startTime") + "，" + search.get("endTime"));
            //查询机器人订单数和运营时长
            if (!StringUtils.isEmpty(operatingTime.getRobotId())){

                searchN.put("robotId",operatingTime.getRobotId());
              Integer orderCount = sysOrderMapper.getSysRobotOrderCount(searchN);
              Integer robotTotalTimeSum = sysOrderMapper.getSysRobotTotalTime(searchN);
              if (StringUtils.isEmpty(orderCount)){
                  orderCount = 0;
              }

              if (StringUtils.isEmpty(robotTotalTimeSum)){
                  robotTotalTimeSum = 0;
              }
              operatingTime.setOrderCount(orderCount.toString());
              operatingTime.setRobotTotalTimeSum(robotTotalTimeSum.toString());
            }
        }
        if (operatingTimeList.size() > 0){
            PageInfo<OperateStream> pageInfo = new PageInfo<>(operatingTimeList);
            pageDataResult.setData(pageInfo.getList());
            pageDataResult.setCount((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method getQueryReportStreamShowQoQ
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询运营流水统计报表（显示环比）
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.ReportForm>
     * @Date 2021/7/10 20:28
     */
    @Override
    public List<ReportForm> getQueryReportStreamShowQoQ(ReportForm reportForm) throws ParseException {
        if ("1".equals(reportForm.getChooseDate())){
            reportForm.setCycle(DateUtil.findMonths(reportForm.getStartTime(),reportForm.getEndTime()));
        }
        if ("2".equals(reportForm.getChooseDate())){
            reportForm.setCycle(DateUtil.findDates(reportForm.getStartTime(),reportForm.getEndTime()));
        }
        if ("3".equals(reportForm.getChooseDate())){
            reportForm.setCycle(DateUtil.findYears(reportForm.getStartTime(),reportForm.getEndTime()));
        }
        String time = reportForm.getStartTime() + "，" + reportForm.getEndTime();
        if ("1".equals(reportForm.getChooseDate())){
            reportForm.setEndTime(DateUtil.calcLastMonth(reportForm.getEndTime()));
        }else if ("2".equals(reportForm.getChooseDate())){
            reportForm.setEndTime(DateUtil.calcYesterday(reportForm.getEndTime()));
        }
        List<ReportForm> operatingStreamList = sysOrderMapper.getQueryReportStreamShowQoQ(reportForm);
        for(ReportForm reportForms : operatingStreamList){
            reportForms.setDate(time);
        }
        return operatingStreamList;
    }

    /**
     * @Method getQueryReportStreamNotShowQoQ
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询运营流水统计报表（不显示环比）
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.ReportForm>
     * @Date 2021/7/10 20:29
     */
    @Override
    public List<ReportForm> getQueryReportStreamNotShowQoQ(ReportForm reportForm) throws ParseException {
        if ("1".equals(reportForm.getChooseDate())){
            reportForm.setCycle(DateUtil.findMonths(reportForm.getStartTime(),reportForm.getEndTime()));
        }
        if ("2".equals(reportForm.getChooseDate())){
            reportForm.setCycle(DateUtil.findDates(reportForm.getStartTime(),reportForm.getEndTime()));
        }
        if ("3".equals(reportForm.getChooseDate())){
            reportForm.setCycle(DateUtil.findYears(reportForm.getStartTime(),reportForm.getEndTime()));
        }
        String time = reportForm.getStartTime() + "，" + reportForm.getEndTime();
        List<ReportForm> operatingStreamList = sysOrderMapper.getQueryReportStreamNotShowQoQ(reportForm);
        for(ReportForm reportForms : operatingStreamList){
            reportForms.setDate(time);
        }
        return operatingStreamList;
    }
}
