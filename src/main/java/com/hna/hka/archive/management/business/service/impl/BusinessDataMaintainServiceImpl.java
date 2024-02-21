package com.hna.hka.archive.management.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.business.dao.BusinessDataMaintainMapper;
import com.hna.hka.archive.management.business.model.BusinessDataMaintain;
import com.hna.hka.archive.management.business.service.BusinessDataMaintainService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service.impl
 * @ClassName: BusinessDataMaintainServiceImpl
 * @Author: 郭凯
 * @Description: 数据维护业务层（实现）
 * @Date: 2020/8/11 16:08
 * @Version: 1.0
 */
@Service
public class BusinessDataMaintainServiceImpl implements BusinessDataMaintainService {

    @Autowired
    private BusinessDataMaintainMapper businessDataMaintainMapper;

    /**
     * @Author 郭凯
     * @Description 数据维护列表查询
     * @Date 16:40 2020/8/11
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getDataMaintainList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<BusinessDataMaintain> businessDataMaintainList = businessDataMaintainMapper.getDataMaintainList(search);
        if (businessDataMaintainList.size() != 0){
            PageInfo<BusinessDataMaintain> pageInfo = new PageInfo<>(businessDataMaintainList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 修改已读状态
     * @Date 17:29 2020/8/11
     * @Param [businessDataMaintain]
     * @return int
    **/
    @Override
    public int editState(BusinessDataMaintain businessDataMaintain) {
        businessDataMaintain.setUpdateTime(DateUtil.currentDateTime());
        return businessDataMaintainMapper.updateByPrimaryKeySelective(businessDataMaintain);
    }

    /**
     * @Author 郭凯
     * @Description 数据维护删除
     * @Date 17:41 2020/8/11
     * @Param [id]
     * @return int
    **/
    @Override
    public int delDataMaintain(Long id) {
        return businessDataMaintainMapper.deleteByPrimaryKey(id);
    }
}
