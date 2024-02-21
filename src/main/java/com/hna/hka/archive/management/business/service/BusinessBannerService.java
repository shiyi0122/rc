package com.hna.hka.archive.management.business.service;

import com.hna.hka.archive.management.business.model.BusinessBanner;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service
 * @ClassName: BusinessBannerService
 * @Author: 郭凯
 * @Description: Banner图管理业务层（接口）
 * @Date: 2020/8/4 14:22
 * @Version: 1.0
 */
public interface BusinessBannerService {

    PageDataResult getBannerList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int addBanner(BusinessBanner businessBanner);

    int delBanner(Long id);

    int editBanner(BusinessBanner businessBanner);
}
