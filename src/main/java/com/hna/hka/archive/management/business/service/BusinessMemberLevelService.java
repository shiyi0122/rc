package com.hna.hka.archive.management.business.service;

import com.hna.hka.archive.management.business.model.BusinessMemberLevel;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service
 * @ClassName: BusinessMemberLevelService
 * @Author: 郭凯
 * @Description: 积分规则业务层（接口）
 * @Date: 2020/8/15 17:10
 * @Version: 1.0
 */
public interface BusinessMemberLevelService {

    PageDataResult getMemberLevelList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int delMemberLevel(Long id);

    int addMemberLevel(BusinessMemberLevel businessMemberLevel);

    int editMemberLevel(BusinessMemberLevel businessMemberLevel);
}
