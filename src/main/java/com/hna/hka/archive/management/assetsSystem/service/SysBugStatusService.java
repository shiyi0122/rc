package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service
 * @ClassName: SysBugStatusService
 * @Author: 郭凯
 * @Description: 订单故障业务层（接口）
 * @Date: 2021/7/11 16:32
 * @Version: 1.0
 */
public interface SysBugStatusService {

    PageDataResult getBugStatusCausesList(Integer currPage, Integer pageSize, Map<String, String> search);

    PageDataResult getBugStatusCausesSpotList(Integer currPage, Integer pageSize, Map<String, String> search);

    PageDataResult getBugStatusReasonsList(Integer currPage, Integer pageSize, Map<String, String> search);

    PageDataResult getBugStatusReasonsSpotList(Integer currPage, Integer pageSize, Map<String, String> search);
}
