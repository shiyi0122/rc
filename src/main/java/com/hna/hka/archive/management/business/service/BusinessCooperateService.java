package com.hna.hka.archive.management.business.service;

import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service
 * @ClassName: BusinessCooperateService
 * @Author: 郭凯
 * @Description: 供应商管理业务层（接口）
 * @Date: 2020/6/19 14:33
 * @Version: 1.0
 */
public interface BusinessCooperateService {

    PageDataResult getBusinessCooperateList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int delCooperate(Long id);
}
