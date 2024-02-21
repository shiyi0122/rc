package com.hna.hka.archive.management.business.service;

import com.hna.hka.archive.management.business.model.BusinessDataMaintain;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service
 * @ClassName: BusinessDataMaintainService
 * @Author: 郭凯
 * @Description: 数据维护业务层（接口）
 * @Date: 2020/8/11 16:07
 * @Version: 1.0
 */
public interface BusinessDataMaintainService {

    PageDataResult getDataMaintainList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int editState(BusinessDataMaintain businessDataMaintain);

    int delDataMaintain(Long id);
}
