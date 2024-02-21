package com.hna.hka.archive.management.assetsSystem.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.util.PageDataResult;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service
 * @ClassName: SysOperateStateService
 * @Author: 郭凯
 * @Description: 运营状态业务层（接口）
 * @Date: 2021/7/11 22:52
 * @Version: 1.0
 */
public interface SysOperateStateService {

    PageDataResult getOperateStateList(Integer currPage, Integer pageSize, Map<String, String> search);

    PageInfo<HashMap> getOperateStateSpotList(Long spotId, String beginDate, String endDate, Integer pageNum, Integer pageSize, String field);

    List getOperateStateSpotList(Long spotId, String beginDate, String endDate);

    PageInfo<HashMap> getOperateStateRobotList(Long spotId, String beginDate, String endDate, Integer page, Integer limit, Integer type);

    List getDetailBySpot(Long spotId, Integer type, String beginDate, String endDate);

    void exportExcel(HttpServletResponse response, Long spotId, Integer type, String beginDate, String endDate);
}
