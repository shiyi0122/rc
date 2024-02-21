package com.hna.hka.archive.management.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.business.dao.BusinessOrderMapper;
import com.hna.hka.archive.management.business.model.BusinessOrder;
import com.hna.hka.archive.management.business.service.BusinessOrderService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service.impl
 * @ClassName: BusinessOrderServiceImpl
 * @Author: 郭凯
 * @Description: 订单管理业务层（接口）
 * @Date: 2020/8/13 13:54
 * @Version: 1.0
 */
@Service
public class BusinessOrderServiceImpl implements BusinessOrderService {

    @Autowired
    private BusinessOrderMapper businessOrderMapper;

    /**
     * @Author 郭凯
     * @Description 订单管理列表查询
     * @Date 13:56 2020/8/13
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getOrderList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<BusinessOrder> businessOrderList = businessOrderMapper.getOrderList(search);
        if (businessOrderList.size() != 0){
            PageInfo<BusinessOrder> pageInfo = new PageInfo<>(businessOrderList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 修改订单
     * @Date 13:18 2020/8/15
     * @Param [businessOrder]
     * @return int
    **/
    @Override
    public int editOrder(BusinessOrder businessOrder) {
        businessOrder.setUpdateTime(DateUtil.currentDateTime());
        return businessOrderMapper.updateByPrimaryKeySelective(businessOrder);
    }

    /**
     * @Author 郭凯
     * @Description 删除订单
     * @Date 13:25 2020/8/15
     * @Param [id]
     * @return int
    **/
    @Override
    public int delOrder(Long id) {
        return businessOrderMapper.deleteByPrimaryKey(id);
    }
}
