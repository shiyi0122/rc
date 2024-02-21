package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.SysOrderExceptionManagement;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service
 * @ClassName: SysOrderExceptionManagementService
 * @Author: 郭凯
 * @Description: 异常订单管理业务层（接口）
 * @Date: 2021/6/2 14:40
 * @Version: 1.0
 */
public interface SysOrderExceptionManagementService {

    PageDataResult getOrderExceptionManagementList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    int addOrderExceptionManagement(SysOrderExceptionManagement sysOrderExceptionManagement);

    int editOrderExceptionManagement(SysOrderExceptionManagement sysOrderExceptionManagement);

    int delOrderExceptionManagement(Long orderExceptionManagementId);

    List<SysOrderExceptionManagement> getOderExceptionManagementExcel(Map<String, Object> search);

    List<SysOrderExceptionManagement> getOrderExceptionManagementVoList();

}
