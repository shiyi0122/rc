package com.hna.hka.archive.management.business.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.business.dao.BusinessFilingSpotMapper;
import com.hna.hka.archive.management.business.model.BusinessFilingSpot;
import com.hna.hka.archive.management.business.service.BusinessFilingSpotService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/3/15 11:23
 */
@Service
public class BusinessFilingSpotServiceImpl implements BusinessFilingSpotService {

    @Autowired
    BusinessFilingSpotMapper businessFilingSpotMapper;

    @Override
    public PageDataResult getFilingSpotList(Integer pageNum, Integer pageSize, Map search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<BusinessFilingSpot> list = businessFilingSpotMapper.getFilingSpotList(search);
        if (list.size()>0){
            PageInfo<BusinessFilingSpot> pageInfo = new PageInfo<>(list);
            pageDataResult.setList(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;
    }

    /**
     * 优质景区添加
     * @param businessFilingSpot
     * @return
     */
    @Override
    public int addFilingSpot(BusinessFilingSpot businessFilingSpot) {

        businessFilingSpot.setId(IdUtils.getSeqId());
        businessFilingSpot.setCreateDate(DateUtil.currentDateTime());
        businessFilingSpot.setUpdateDate(DateUtil.currentDateTime());

        int i = businessFilingSpotMapper.insertSelective(businessFilingSpot);
        return i;
    }

    @Override
    public int editFilingSpot(BusinessFilingSpot businessFilingSpot) {

        businessFilingSpot.setUpdateDate(DateUtil.currentDateTime());
        int i = businessFilingSpotMapper.updateByPrimaryKeySelective(businessFilingSpot);
        return i;
    }

    @Override
    public int delFilingSpot(Long id) {

       int i =  businessFilingSpotMapper.deleteByPrimaryKey(id);
       return i;
    }
}
