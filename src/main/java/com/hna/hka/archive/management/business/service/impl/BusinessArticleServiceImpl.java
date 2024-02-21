package com.hna.hka.archive.management.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.business.dao.BusinessArticleMapper;
import com.hna.hka.archive.management.business.model.BusinessArticle;
import com.hna.hka.archive.management.business.service.BusinessArticleService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service.impl
 * @ClassName: BusinessArticleServiceImpl
 * @Author: 郭凯
 * @Description: 文章管理业务层（实现）
 * @Date: 2020/8/5 10:28
 * @Version: 1.0
 */
@Service
public class BusinessArticleServiceImpl implements BusinessArticleService {

    @Autowired
    private BusinessArticleMapper businessArticleMapper;

    /**
     * @Author 郭凯
     * @Description 文章管理列表查询
     * @Date 10:30 2020/8/5
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getArticleList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<BusinessArticle> businessArticleList = businessArticleMapper.getArticleList(search);
        if (businessArticleList.size() != 0){
            PageInfo<BusinessArticle> pageInfo = new PageInfo<>(businessArticleList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 文章新增
     * @Date 14:44 2020/8/5
     * @Param [businessArticle]
     * @return int
    **/
    @Override
    public int addArticle(BusinessArticle businessArticle) {
        businessArticle.setId(IdUtils.getSeqId());
        businessArticle.setCreateTime(DateUtil.currentDateTime());
        businessArticle.setUpdateTime(DateUtil.currentDateTime());
        return businessArticleMapper.insertSelective(businessArticle);
    }

    /**
     * @Author 郭凯
     * @Description 文章修改
     * @Date 15:18 2020/8/6
     * @Param [businessArticle]
     * @return int
    **/
    @Override
    public int editArticle(BusinessArticle businessArticle) {
        businessArticle.setUpdateTime(DateUtil.currentDateTime());
        return businessArticleMapper.updateByPrimaryKeySelective(businessArticle);
    }

    /**
     * @Author 郭凯
     * @Description 文章删除
     * @Date 15:40 2020/8/6
     * @Param [id]
     * @return int
    **/
    @Override
    public int delArticle(Long id) {
        return businessArticleMapper.deleteByPrimaryKey(id);
    }
}
