package com.hna.hka.archive.management.managerApp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.dao.SysAppRoleMapper;
import com.hna.hka.archive.management.appSystem.model.SysAppRole;
import com.hna.hka.archive.management.managerApp.dao.SysAppRoleResourceMapper;
import com.hna.hka.archive.management.managerApp.model.SysAppRoleResource;
import com.hna.hka.archive.management.managerApp.model.SysManagerAppRes;
import com.hna.hka.archive.management.managerApp.service.SysManagerAppRoleService;
import com.hna.hka.archive.management.system.model.SysRoleResource;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.managerApp.service.impl
 * @ClassName: SysAppRoleServiceImpl
 * @Author: 郭凯
 * @Description: APP角色管理业务层（实现）
 * @Date: 2021/6/4 15:17
 * @Version: 1.0
 */
@Service
@Transactional
public class SysManagerAppRoleServiceImpl implements SysManagerAppRoleService {

    @Autowired
    private SysAppRoleMapper sysAppRoleMapper;

    @Autowired
    private SysAppRoleResourceMapper sysAppRoleResourceMapper;
    
    /**
     * @Method getAppRoleList
     * @Author 郭凯
     * @Version  1.0
     * @Description APP角色管理列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/6/4 15:21
     */
    @Override
    public PageDataResult getAppRoleList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysAppRole> sysAppRoleList = sysAppRoleMapper.getAppUsersList(search);
        if (sysAppRoleList.size() != 0){
            PageInfo<SysAppRole> pageInfo = new PageInfo<>(sysAppRoleList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method addAppRole
     * @Author 郭凯
     * @Version  1.0
     * @Description 新增管理者APP角色
     * @Return int
     * @Date 2021/6/4 16:36
     */
    @Override
    public int addAppRole(SysAppRole sysAppRole) {
        sysAppRole.setRoleId(IdUtils.getSeqId());
        sysAppRole.setCreateDate(DateUtil.currentDateTime());
        sysAppRole.setUpdateDate(DateUtil.currentDateTime());
        return sysAppRoleMapper.insertSelective(sysAppRole);
    }

    /**
     * @Method addRoleResource
     * @Author 郭凯
     * @Version  1.0
     * @Description 批量提交权限
     * @Return int
     * @Date 2021/6/4 17:23
     */
    @Override
    public int addRoleResource(Long roleId, String resIds) {
        //删除权限
        sysAppRoleResourceMapper.deleteByRoleId(roleId);
        int a = 0;
        String[] resId = resIds.split(",");
        for (int j = 0; j < resId.length; j++) {
            SysAppRoleResource sysRoleResource = new SysAppRoleResource();
            sysRoleResource.setRelationId(IdUtils.getSeqId());
            sysRoleResource.setRoleId(roleId);
            sysRoleResource.setResId(Long.parseLong(resId[j]));
            sysRoleResource.setCreateDate(DateUtil.currentDateTime());
            sysRoleResource.setUpdateDate(DateUtil.currentDateTime());
            a = sysAppRoleResourceMapper.insertSelective(sysRoleResource);
        }
        return a;
    }

    /**
     * @Method getAppRoleLists
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询角色下拉框
     * @Return java.util.List<com.hna.hka.archive.management.appSystem.model.SysAppRole>
     * @Date 2021/6/4 17:48
     */
    @Override
    public List<SysAppRole> getAppRoleLists() {
        Map<String, Object> search = new HashMap<>();
        List<SysAppRole> sysAppRoleList = sysAppRoleMapper.getAppUsersList(search);
        return sysAppRoleList;
    }

}
