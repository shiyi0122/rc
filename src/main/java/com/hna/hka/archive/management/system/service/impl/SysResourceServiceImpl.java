package com.hna.hka.archive.management.system.service.impl;

import com.hna.hka.archive.management.system.dao.SysResourceMapper;
import com.hna.hka.archive.management.system.model.SysResource;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.service.SysResourceService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysResourceServiceImpl implements SysResourceService {

    @Autowired
    private SysResourceMapper sysResourceMapper;

    @Autowired
    private HttpSession session;


    /**
     * 功能描述: 查询头部菜单栏
     * @Param:
     * @Return:
     * @Author: 郭凯
     * @Date: 2020/4/26 14:23
     */
    @Override
    public List<SysResource> getResourceNav() {
        String scenicSpotId = (String) session.getAttribute("scenicSpotId");
        Subject subject = SecurityUtils.getSubject();
        SysUsers user = (SysUsers) subject.getPrincipal();
        return sysResourceMapper.getResourceNav(scenicSpotId,user.getLoginName());
    }

    /**
     * 功能描述: 根据顶级菜单查询二三级菜单
     * @Param:
     * @Return:
     * @Author: 郭凯
     * @Date: 2020/4/26 14:23
     */
    @Override
    public List<SysResource> getResourceLeft(Map<String, String> search) {
        Subject subject = SecurityUtils.getSubject();
        SysUsers user = (SysUsers) subject.getPrincipal();
        search.put("loginName",user.getLoginName());
            search.put("scenicSpotId",(String) session.getAttribute("scenicSpotId"));
        return sysResourceMapper.getResourceLeft(search);
    }

    /**
     * 功能描述: 菜单列表查询
     * @Param:
     * @Return:
     * @Author: 郭凯
     * @Date: 2020/4/26 14:23
     */
    @Override
    public List<SysResource> getResourceList(SysResource sysResource) {
        return sysResourceMapper.getResourceList(sysResource);
    }

    /**
     * 功能描述: 添加菜单栏
     * @Param:
     * @Return:
     * @Author: 郭凯
     * @Date: 2020/4/26 14:23
     */
    @Override
    public int addResource(SysResource sysResource) {
        SysResource sysRes = new SysResource();
        //查询父节点数据
        SysResource resources = sysResourceMapper.selectByPrimaryKeyCode(sysResource.getResPcode());
        sysRes.setResId(IdUtils.getSeqId());
        sysRes.setResCode(sysResource.getResCode());
        sysRes.setResPcode(resources.getResCode());
        sysRes.setResName(sysResource.getResName());
        sysRes.setResIcon(sysResource.getResIcon());
        sysRes.setResUrl(sysResource.getResUrl());
        sysRes.setResSort(sysResource.getResSort());
        sysRes.setResMenuFlag(sysResource.getResMenuFlag());
        sysRes.setResDescription(sysResource.getResDescription());
        if ("-1".equals(resources.getResPcode())){
            sysRes.setResSystemType(resources.getResCode());
        }else if (!"-1".equals(resources.getResPcode())){
            sysRes.setResSystemType(resources.getResSystemType());
        }
        sysRes.setResSystemStatus("0");
        sysRes.setCreateTime(DateUtil.currentDateTime());
        sysRes.setUpdateTime(DateUtil.currentDateTime());
        return sysResourceMapper.insertSelective(sysRes);
    }

    /**
     * 功能描述: 删除菜单栏
     * @Param:
     * @Return:
     * @Author: 郭凯
     * @Date: 2020/4/26 15:53
     */
    @Override
    public int delResource(Long resId) {
        return sysResourceMapper.deleteByPrimaryKey(resId);
    }


    /**
     * 功能描述:根据ID查询回显对象
     * @Param: [resId]
     * @Return: com.hna.hka.archive.management.system.model.SysResource
     * @Author: 郭凯
     * @Date: 2020/4/27 14:53
     */
    @Override
    public SysResource getResourceById(Long resId) {
        return sysResourceMapper.selectByPrimaryKey(resId);
    }

    /**
     * 功能描述: 菜单栏修改
     * @Param: [sysResource]
     * @Return: int
     * @Author: 郭凯
     * @Date: 2020/4/27 15:50
     */
    @Override
    public int updateResource(SysResource sysResource) {
        SysResource sysRes = new SysResource();
        //查询父节点数据
        SysResource resources = sysResourceMapper.selectByPrimaryKeyCode(sysResource.getResPcode());
        if (resources != null){
            sysRes.setResPcode(resources.getResCode());
            if ("-1".equals(resources.getResPcode())){
                sysRes.setResSystemType(resources.getResCode());
            }else if (!"-1".equals(resources.getResPcode())){
                sysRes.setResSystemType(resources.getResSystemType());
            }
        }
        sysRes.setResId(sysResource.getResId());
        sysRes.setResCode(sysResource.getResCode());
        sysRes.setResName(sysResource.getResName());
        sysRes.setResIcon(sysResource.getResIcon());
        sysRes.setResUrl(sysResource.getResUrl());
        sysRes.setResSort(sysResource.getResSort());
        sysRes.setResMenuFlag(sysResource.getResMenuFlag());
        sysRes.setResDescription(sysResource.getResDescription());
        sysRes.setUpdateTime(DateUtil.currentDateTime());
        return sysResourceMapper.updateByPrimaryKeySelective(sysRes);
    }
    /**
     * 根据角色获取角色下的所有权限
     * @param roleId
     * @return
     */

    @Override
    public List<SysResource> getRoleResourceList(Long roleId) {


        return sysResourceMapper.getRoleResourceList(roleId);


    }
}
