package com.hna.hka.archive.management.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.business.dao.BusinessFeedBackMapper;
import com.hna.hka.archive.management.business.model.BusinessFeedBack;
import com.hna.hka.archive.management.business.service.BusinessFeedBackService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service.impl
 * @ClassName: BusinessFeedBackServiceImpl
 * @Author: 郭凯
 * @Description: 意见反馈业务层（实现）
 * @Date: 2020/8/12 10:08
 * @Version: 1.0
 */
@Service
public class BusinessFeedBackServiceImpl implements BusinessFeedBackService {

    @Autowired
    private BusinessFeedBackMapper businessFeedBackMapper;

    /**
     * @Author 郭凯
     * @Description 意见反馈列表查询
     * @Date 10:10 2020/8/12
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getFeedBackList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<BusinessFeedBack> businessFeedBackList = businessFeedBackMapper.getFeedBackList(search);
        if (businessFeedBackList.size() != 0){
            PageInfo<BusinessFeedBack> pageInfo = new PageInfo<>(businessFeedBackList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 内容回复
     * @Date 11:07 2020/8/12
     * @Param [businessFeedBack]
     * @return int
    **/
    @Override
    public int editReply(BusinessFeedBack businessFeedBack) {
        businessFeedBack.setIsRead("1");
        businessFeedBack.setState("1");
        businessFeedBack.setUpdateTime(DateUtil.currentDateTime());
        return businessFeedBackMapper.updateByPrimaryKeySelective(businessFeedBack);
    }

    @Override
    /**
     * @Author 郭凯
     * @Description 意见反馈删除
     * @Date 11:40 2020/8/12
     * @Param [id]
     * @return int
    **/
    public int delFeedBack(Long id) {
        return businessFeedBackMapper.deleteByPrimaryKey(id);
    }
}
