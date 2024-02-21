package com.hna.hka.archive.management.business.service;

import com.hna.hka.archive.management.business.model.BusinessOrder;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service
 * @ClassName: BusinessOrderService
 * @Author: 郭凯
 * @Description: 订单管理业务层（接口）
 * @Date: 2020/8/13 13:54
 * @Version: 1.0
 */
public interface BusinessOrderService {

    PageDataResult getOrderList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int editOrder(BusinessOrder businessOrder);

    int delOrder(Long id);
}
