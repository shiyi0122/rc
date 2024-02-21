package com.hna.hka.archive.management.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.business.model.BusinessRole;
import com.hna.hka.archive.management.business.service.BusinessRoleService;
import com.hna.hka.archive.management.business.dao.BusinessRoleMapper;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service.impl
 * @ClassName: BusinessRoleServiceImpl
 * @Author: 郭凯
 * @Description: 招商平台角色管理业务层（实现）
 * @Date: 2020/10/12 14:09
 * @Version: 1.0
 */
@Service
public class BusinessRoleServiceImpl implements BusinessRoleService {

    @Autowired
    private BusinessRoleMapper businessRoleMapper;

    /**
     * @Author 郭凯
     * @Description 角色管理列表查询
     * @Date 14:10 2020/10/12
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getBusinessRoleList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<BusinessRole> businessRoleList = businessRoleMapper.getBusinessRoleList(search);
        if (businessRoleList.size() != 0){
            PageInfo<BusinessRole> pageInfo = new PageInfo<>(businessRoleList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }


    /**
     * @Author 郭凯
     * @Description 角色删除
     * @Date 16:26 2020/10/12
     * @Param [roleId]
     * @return int
    **/
    @Override
    public int delRole(Long roleId) {
        return businessRoleMapper.deleteByPrimaryKey(roleId);
    }
}
