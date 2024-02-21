package com.hna.hka.archive.management.business.service;

import com.hna.hka.archive.management.business.model.BusinessArticle;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service
 * @ClassName: BusinessArticleService
 * @Author: 郭凯
 * @Description: 文章管理业务层（接口）
 * @Date: 2020/8/5 10:27
 * @Version: 1.0
 */
public interface BusinessArticleService {

    PageDataResult getArticleList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int addArticle(BusinessArticle businessArticle);

    int editArticle(BusinessArticle businessArticle);

    int delArticle(Long id);
}
