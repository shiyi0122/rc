package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.BugStatus;
import com.hna.hka.archive.management.assetsSystem.service.SysBugStatusService;
import com.hna.hka.archive.management.system.dao.SysOrderMapper;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service.impl
 * @ClassName: SysBugStatusServiceImpl
 * @Author: 郭凯
 * @Description: 订单故障业务层（实现）
 * @Date: 2021/7/11 16:33
 * @Version: 1.0
 */
@Service
public class SysBugStatusServiceImpl implements SysBugStatusService {

    @Autowired
    private SysOrderMapper sysOrderMapper;


    /**
     * @Method getBugStatusCausesList
     * @Author 郭凯
     * @Version  1.0
     * @Description 上报原因大类查询（不按照景区）
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/7/11 17:04
     */
    @Override
    public PageDataResult getBugStatusCausesList(Integer currPage, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(currPage, pageSize);
        List<BugStatus> bugStatusList = sysOrderMapper.getBugStatusCausesList(search);
        for(BugStatus bugStatus : bugStatusList){
            bugStatus.setDate(search.get("startTime") + "，" + search.get("endTime"));
            if ("10".equals(bugStatus.getCauses())){
                bugStatus.setReportReasonClass("常报故障");
            }else if ("20".equals(bugStatus.getCauses())){
                bugStatus.setReportReasonClass("硬件故障");
            }else if ("30".equals(bugStatus.getCauses())){
                bugStatus.setReportReasonClass("软件故障");
            }else if ("40".equals(bugStatus.getCauses())){
                bugStatus.setReportReasonClass("非故障");
            }
        }
        if (bugStatusList.size() > 0){
            PageInfo<BugStatus> pageInfo = new PageInfo<>(bugStatusList);
            pageDataResult.setData(pageInfo.getList());
            pageDataResult.setCount((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method getBugStatusCausesSpotList
     * @Author 郭凯
     * @Version  1.0
     * @Description 上报原因大类查询（按照景区）
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/7/11 18:43
     */
    @Override
    public PageDataResult getBugStatusCausesSpotList(Integer currPage, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(currPage, pageSize);
        List<BugStatus> bugStatusList = sysOrderMapper.getBugStatusCausesSpotList(search);
        for(BugStatus bugStatus : bugStatusList){
            bugStatus.setDate(search.get("startTime") + "，" + search.get("endTime"));
            if ("10".equals(bugStatus.getCauses())){
                bugStatus.setReportReasonClass("常报故障");
            }else if ("20".equals(bugStatus.getCauses())){
                bugStatus.setReportReasonClass("硬件故障");
            }else if ("30".equals(bugStatus.getCauses())){
                bugStatus.setReportReasonClass("软件故障");
            }else if ("40".equals(bugStatus.getCauses())){
                bugStatus.setReportReasonClass("非故障");
            }
        }
        if (bugStatusList.size() > 0){
            PageInfo<BugStatus> pageInfo = new PageInfo<>(bugStatusList);
            pageDataResult.setData(pageInfo.getList());
            pageDataResult.setCount((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method getBugStatusReasonsList
     * @Author 郭凯
     * @Version  1.0
     * @Description 上报原因查询（不按照景区）
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/7/11 21:26
     */
    @Override
    public PageDataResult getBugStatusReasonsList(Integer currPage, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(currPage, pageSize);
        List<BugStatus> bugStatusList = sysOrderMapper.getBugStatusReasonsList(search);
        for(BugStatus bugStatus : bugStatusList){
            bugStatus.setDate(search.get("startTime") + "，" + search.get("endTime"));
        }
        if (bugStatusList.size() > 0){
            PageInfo<BugStatus> pageInfo = new PageInfo<>(bugStatusList);
            pageDataResult.setData(pageInfo.getList());
            pageDataResult.setCount((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method getBugStatusReasonsSpotList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询上报原因，根据景区
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/7/11 22:14
     */
    @Override
    public PageDataResult getBugStatusReasonsSpotList(Integer currPage, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(currPage, pageSize);
        List<BugStatus> bugStatusList = sysOrderMapper.getBugStatusReasonsSpotList(search);
        for(BugStatus bugStatus : bugStatusList){
            bugStatus.setDate(search.get("startTime") + "，" + search.get("endTime"));
        }
        if (bugStatusList.size() > 0){
            PageInfo<BugStatus> pageInfo = new PageInfo<>(bugStatusList);
            pageDataResult.setData(pageInfo.getList());
            pageDataResult.setCount((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }
}
