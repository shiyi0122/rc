package com.hna.hka.archive.management.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.business.dao.BusinessConfigsMapper;
import com.hna.hka.archive.management.business.model.BusinessConfigs;
import com.hna.hka.archive.management.business.service.BusinessConfigsService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/7/11 13:31
 */
@Service
public class BusinessConfigsServiceImpl implements BusinessConfigsService {

    @Autowired
    BusinessConfigsMapper businessConfigsMapper;

    /**
     * 修改关于我们
     * @param businessConfigs
     * @return
     */
    @Override
    public int editConfigs(BusinessConfigs businessConfigs) {

        businessConfigs.setId(6l);
        businessConfigs.setUpdateTime(DateUtil.currentDateTime());

        int i = businessConfigsMapper.updateByPrimaryKeySelective(businessConfigs);
        return i;
    }

    /**
     * 查询关于我们
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getConfigsList(Integer pageNum, Integer pageSize, Map<String, String> search) {

        PageDataResult pageDataResult = new PageDataResult();

        search.put("id","6");

        PageHelper.startPage(pageNum,pageSize);
        List<BusinessConfigs> list = businessConfigsMapper.getConfigsList(search);

        if (list.size() > 0){
            PageInfo<BusinessConfigs> pageInfo = new PageInfo<>(list);
            pageDataResult.setList(list);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int)pageInfo.getTotal());
        }
        return pageDataResult;
    }

}
