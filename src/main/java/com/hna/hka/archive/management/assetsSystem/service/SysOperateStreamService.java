package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.ReportForm;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service
 * @ClassName: SysOperateStreamService
 * @Author: 郭凯
 * @Description: 运营流水业务层
 * @Date: 2021/7/4 18:51
 * @Version: 1.0
 */
public interface SysOperateStreamService {


    PageDataResult getOperateStreamListShowQoQ(Integer currPage, Integer pageSize, Map<String, String> search) throws ParseException;

    PageDataResult getRobotOperateStreamShowQoQ(Integer currPage, Integer pageSize, Map<String, String> search) throws ParseException;

    PageDataResult getOperateStreamList(Integer currPage, Integer pageSize, Map<String, String> search) throws ParseException;

    PageDataResult getRobotOperateStreamList(Integer currPage, Integer pageSize, Map<String, String> search) throws ParseException;

    List<ReportForm> getQueryReportStreamShowQoQ(ReportForm reportForm) throws ParseException;

    List<ReportForm> getQueryReportStreamNotShowQoQ(ReportForm reportForm) throws ParseException;
}
