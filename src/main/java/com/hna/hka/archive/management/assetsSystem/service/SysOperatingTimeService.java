package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.ReportForm;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service
 * @ClassName: SysOperatingTimeService
 * @Author: 郭凯
 * @Description: 景区运营时长业务层（接口）
 * @Date: 2021/6/22 16:25
 * @Version: 1.0
 */
public interface SysOperatingTimeService {

    PageDataResult getOperatingTimeList(Integer currPage, Integer pageSize, Map<String, String> search) throws ParseException;

    PageDataResult getOperatingTimeListShowQoQ(Integer currPage, Integer pageSize, Map<String, String> search) throws ParseException;

    PageDataResult getRobotOperatingTimeListShowQoQ(Integer currPage, Integer pageSize, Map<String, String> search) throws ParseException;

    PageDataResult getRobotOperatingTimeList(Integer currPage, Integer pageSize, Map<String, String> search) throws ParseException;

    List<ReportForm> getQueryReportShowQoQ(ReportForm reportForm) throws ParseException;

    List<ReportForm> getQueryReportNotShowQoQ(ReportForm reportForm) throws ParseException;
}
