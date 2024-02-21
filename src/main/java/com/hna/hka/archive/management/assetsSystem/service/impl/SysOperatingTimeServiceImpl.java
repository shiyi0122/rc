package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.OperatingTime;
import com.hna.hka.archive.management.assetsSystem.model.ReportForm;
import com.hna.hka.archive.management.assetsSystem.service.SysOperatingTimeService;
import com.hna.hka.archive.management.system.dao.SysOrderMapper;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;
import java.util.Map;


/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service.impl
 * @ClassName: SysOperatingTimeServiceImpl
 * @Author: 郭凯
 * @Description: 景区运营时长业务层（实现）
 * @Date: 2021/6/22 16:25
 * @Version: 1.0
 */
@Service
@Transactional
public class SysOperatingTimeServiceImpl implements SysOperatingTimeService {

    @Autowired
    private SysOrderMapper sysOrderMapper;

    /**
     * @Method getOperatingTimeList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询景区运营时长（不显示环比）
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/7/4 15:04
     */
    @Override
    public PageDataResult getOperatingTimeList(Integer currPage, Integer pageSize, Map<String, String> search)  throws ParseException {
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
        List<OperatingTime> operatingTimeList = sysOrderMapper.getOperatingTimeList(search);
        for(OperatingTime operatingTime : operatingTimeList){
            operatingTime.setDate(search.get("startTime") + "，" + search.get("endTime"));
        }
        if (operatingTimeList.size() > 0){
            PageInfo<OperatingTime> pageInfo = new PageInfo<>(operatingTimeList);
            pageDataResult.setData(pageInfo.getList());
            pageDataResult.setCount((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method getOperatingTimeListShowQoQ
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询景区运营时长（显示环比）
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/7/4 15:02
     */
    @Override
    public PageDataResult getOperatingTimeListShowQoQ(Integer currPage, Integer pageSize, Map<String, String> search) throws ParseException {
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
        List<OperatingTime> operatingTimeList = sysOrderMapper.getOperatingTimeListShowQoQ(search);
        for(OperatingTime operatingTime : operatingTimeList){
            operatingTime.setDate(time);
        }
        if (operatingTimeList.size() > 0){
            PageInfo<OperatingTime> pageInfo = new PageInfo<>(operatingTimeList);
            pageDataResult.setData(pageInfo.getList());
            pageDataResult.setCount((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method getRobotOperatingTimeListShowQoQ
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询机器人运营时长（显示环比）
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/7/4 17:17
     */
    @Override
    public PageDataResult getRobotOperatingTimeListShowQoQ(Integer currPage, Integer pageSize, Map<String, String> search) throws ParseException {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(currPage, pageSize);
        String time = search.get("startTime") + "，" + search.get("endTime");
        if ("1".equals(search.get("dateType"))){
            search.put("endTime", DateUtil.calcLastMonth(search.get("endTime")));
        }else if ("2".equals(search.get("dateType"))){
            search.put("endTime", DateUtil.calcYesterday(search.get("endTime")));
        }
        List<OperatingTime> operatingTimeList = sysOrderMapper.getRobotOperatingTimeListShowQoQ(search);
        for(OperatingTime operatingTime : operatingTimeList){
            operatingTime.setDate(time);
        }
        if (operatingTimeList.size() > 0){
            PageInfo<OperatingTime> pageInfo = new PageInfo<>(operatingTimeList);
            pageDataResult.setData(pageInfo.getList());
            pageDataResult.setCount((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method getRobotOperatingTimeList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询机器人运营时长（不显示环比）
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/7/4 17:18
     */
    @Override
    public PageDataResult getRobotOperatingTimeList(Integer currPage, Integer pageSize, Map<String, String> search){
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(currPage, pageSize);
        List<OperatingTime> operatingTimeList = sysOrderMapper.getRobotOperatingTimeList(search);
        for(OperatingTime operatingTime : operatingTimeList){
            operatingTime.setDate(search.get("startTime") + "，" + search.get("endTime"));
        }
        if (operatingTimeList.size() > 0){
            PageInfo<OperatingTime> pageInfo = new PageInfo<>(operatingTimeList);
            pageDataResult.setData(pageInfo.getList());
            pageDataResult.setCount((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method getQueryReportShowQoQ
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询运营时长统计报表（显示环比）
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.ReportForm>
     * @Date 2021/7/10 11:29
     */
    @Override
    public List<ReportForm> getQueryReportShowQoQ(ReportForm reportForm) throws ParseException {
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
        List<ReportForm> operatingTimeList = sysOrderMapper.getQueryReportShowQoQ(reportForm);
        for(ReportForm reportForms : operatingTimeList){
            reportForms.setDate(time);
        }
        return operatingTimeList;
    }

    /**
     * @Method getQueryReportNotShowQoQ
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询运营时长统计报表（不显示环比）
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.ReportForm>
     * @Date 2021/7/10 13:36
     */
    @Override
    public List<ReportForm> getQueryReportNotShowQoQ(ReportForm reportForm) throws ParseException {
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
        List<ReportForm> operatingTimeList = sysOrderMapper.getQueryReportNotShowQoQ(reportForm);
        for(ReportForm reportForms : operatingTimeList){
            reportForms.setDate(time);
        }
        return operatingTimeList;
    }
}
