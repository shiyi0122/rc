package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysCurrentUserAccountOrder;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysCurrentUserAccountOrderService
 * @Author: 郭凯
 * @Description: 储值记录管理业务层（接口）
 * @Date: 2020/11/5 11:41
 * @Version: 1.0
 */
public interface SysCurrentUserAccountOrderService {

    PageDataResult getCurrentUserAccountOrderList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    List<SysCurrentUserAccountOrder> getCurrentUserAccountOrderExcel(Map<String, Object> search);

    List<SysCurrentUserAccountOrder> getCurrentUserAccountOrderListByUserId(Long userId);

    int  updateCurrentUserAccountOrder(SysCurrentUserAccountOrder order);
}
