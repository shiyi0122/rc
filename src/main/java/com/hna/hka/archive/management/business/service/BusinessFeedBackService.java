package com.hna.hka.archive.management.business.service;

import com.hna.hka.archive.management.business.model.BusinessFeedBack;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service
 * @ClassName: BusinessFeedBackService
 * @Author: 郭凯
 * @Description: 意见反馈业务层（接口）
 * @Date: 2020/8/12 10:08
 * @Version: 1.0
 */
public interface BusinessFeedBackService {

    PageDataResult getFeedBackList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int editReply(BusinessFeedBack businessFeedBack);

    int delFeedBack(Long id);
}
