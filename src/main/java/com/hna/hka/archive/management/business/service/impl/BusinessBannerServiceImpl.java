package com.hna.hka.archive.management.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.business.dao.BusinessBannerMapper;
import com.hna.hka.archive.management.business.model.BusinessBanner;
import com.hna.hka.archive.management.business.service.BusinessBannerService;
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
 * @ClassName: BusinessBannerServiceInpl
 * @Author: 郭凯
 * @Description: Banner图管理业务层（实现）
 * @Date: 2020/8/4 14:22
 * @Version: 1.0
 */
@Service
public class BusinessBannerServiceImpl implements BusinessBannerService {

    @Autowired
    private BusinessBannerMapper businessBannerMapper;

    /**
     * @Author 郭凯
     * @Description Banner图管理列表查询
     * @Date 14:25 2020/8/4
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getBannerList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<BusinessBanner> businessBannerList = businessBannerMapper.getBannerList(search);
        if (businessBannerList.size() != 0){
            PageInfo<BusinessBanner> pageInfo = new PageInfo<>(businessBannerList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description Banner图新增
     * @Date 14:53 2020/8/4
     * @Param [businessBanner]
     * @return int
    **/
    @Override
    public int addBanner(BusinessBanner businessBanner) {
        businessBanner.setId(IdUtils.getSeqId());
        businessBanner.setCreateTime(DateUtil.currentDateTime());
        businessBanner.setUpdateTime(DateUtil.currentDateTime());
        return businessBannerMapper.insertSelective(businessBanner);
    }

    /**
     * @Author 郭凯
     * @Description Banner图删除
     * @Date 15:35 2020/8/4
     * @Param [id]
     * @return int
    **/
    @Override
    public int delBanner(Long id) {
        return businessBannerMapper.deleteByPrimaryKey(id);
    }

    /**
     * @Author 郭凯
     * @Description Banner图编辑
     * @Date 16:00 2020/8/4
     * @Param [businessBanner]
     * @return int
    **/
    @Override
    public int editBanner(BusinessBanner businessBanner) {
        businessBanner.setUpdateTime(DateUtil.currentDateTime());
        return businessBannerMapper.updateByPrimaryKeySelective(businessBanner);
    }
}
