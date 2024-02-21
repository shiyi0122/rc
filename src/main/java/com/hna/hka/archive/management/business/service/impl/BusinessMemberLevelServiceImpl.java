package com.hna.hka.archive.management.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.business.dao.BusinessMemberLevelMapper;
import com.hna.hka.archive.management.business.model.BusinessMemberLevel;
import com.hna.hka.archive.management.business.service.BusinessMemberLevelService;
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
 * @ClassName: BusinessMemberLevelServiceImpl
 * @Author: 郭凯
 * @Description: 积分规则业务层（实现）
 * @Date: 2020/8/15 17:10
 * @Version: 1.0
 */
@Service
public class BusinessMemberLevelServiceImpl implements BusinessMemberLevelService {

    @Autowired
    private BusinessMemberLevelMapper businessMemberLevelMapper;

    /**
     * @Author 郭凯
     * @Description 积分规则管理列表查询
     * @Date 17:33 2020/8/15
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getMemberLevelList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<BusinessMemberLevel> businessMemberLevelList = businessMemberLevelMapper.getMemberLevelList(search);
        if (businessMemberLevelList.size() != 0){
            PageInfo<BusinessMemberLevel> pageInfo = new PageInfo<>(businessMemberLevelList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 删除积分规则
     * @Date 10:03 2020/8/17
     * @Param [id]
     * @return int
    **/
    @Override
    public int delMemberLevel(Long id) {
        return businessMemberLevelMapper.deleteByPrimaryKey(id);
    }

    /**
     * @Author 郭凯
     * @Description 新增积分规则
     * @Date 10:17 2020/8/17
     * @Param [businessMemberLevel]
     * @return int
    **/
    @Override
    public int addMemberLevel(BusinessMemberLevel businessMemberLevel) {
        businessMemberLevel.setId(IdUtils.getSeqId());
        businessMemberLevel.setCreateTime(DateUtil.currentDateTime());
        businessMemberLevel.setUpdateTime(DateUtil.currentDateTime());
        return businessMemberLevelMapper.insertSelective(businessMemberLevel);
    }

    /**
     * @Author 郭凯
     * @Description 修改积分规则
     * @Date 10:31 2020/8/17
     * @Param [businessMemberLevel]
     * @return int
    **/
    @Override
    public int editMemberLevel(BusinessMemberLevel businessMemberLevel) {
        businessMemberLevel.setUpdateTime(DateUtil.currentDateTime());
        return businessMemberLevelMapper.updateByPrimaryKeySelective(businessMemberLevel);
    }
}
