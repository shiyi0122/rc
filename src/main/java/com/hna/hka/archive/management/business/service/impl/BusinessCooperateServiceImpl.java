package com.hna.hka.archive.management.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.business.dao.BusinessCooperateMapper;
import com.hna.hka.archive.management.business.model.BusinessCooperate;
import com.hna.hka.archive.management.business.service.BusinessCooperateService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service.impl
 * @ClassName: BusinessCooperateServiceImpl
 * @Author: 郭凯
 * @Description: 供应商和已有景区我想合作管理业务层（实现）
 * @Date: 2020/6/19 14:34
 * @Version: 1.0
 */
@Service
public class BusinessCooperateServiceImpl implements BusinessCooperateService {

    @Autowired
    private BusinessCooperateMapper businessCooperateMapper;

    /**
     * @Author 郭凯
     * @Description 供应商和已有景区我想合作管理列表查询
     * @Date 14:57 2020/6/19
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getBusinessCooperateList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<BusinessCooperate> businessCooperateList = businessCooperateMapper.getBusinessCooperateList(search);
        if (businessCooperateList.size() != 0){
            PageInfo<BusinessCooperate> pageInfo = new PageInfo<>(businessCooperateList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 删除
     * @Date 16:15 2020/6/19
     * @Param [id]
     * @return int
    **/
    @Override
    public int delCooperate(Long id) {
        return businessCooperateMapper.deleteByPrimaryKey(id);
    }
}