package com.hna.hka.archive.management.managerApp.service.impl;

import com.hna.hka.archive.management.managerApp.dao.SysManagerAppResMapper;
import com.hna.hka.archive.management.managerApp.model.SysManagerAppRes;
import com.hna.hka.archive.management.managerApp.service.SysManagerAppResService;
import com.hna.hka.archive.management.system.model.SysResource;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.managerApp.service.impl
 * @ClassName: SysManagerAppResServiceImpl
 * @Author: 郭凯
 * @Description: 管理者APP菜单管理业务层（实现）
 * @Date: 2021/6/4 10:23
 * @Version: 1.0
 */
@Service
@Transactional
public class SysManagerAppResServiceImpl implements SysManagerAppResService {

    @Autowired
    private SysManagerAppResMapper sysManagerAppResMapper;

    /**
     * @Method getManagerAppResList
     * @Author 郭凯
     * @Version  1.0
     * @Description 管理者APP菜单管理列表查询
     * @Return java.util.List<com.hna.hka.archive.management.managerApp.model.SysManagerAppRes>
     * @Date 2021/6/4 10:34
     */
    @Override
    public List<SysManagerAppRes> getManagerAppResList(SysManagerAppRes managerAppRes) {
        return sysManagerAppResMapper.getManagerAppResList(managerAppRes);
    }

    /**
     * @Method addManagerAppRes
     * @Author 郭凯
     * @Version  1.0
     * @Description APP菜单栏新增
     * @Return int
     * @Date 2021/6/4 14:17
     */
    @Override
    public int addManagerAppRes(SysManagerAppRes sysResource) {
        sysResource.setResId(IdUtils.getSeqId());
        sysResource.setCreateTime(DateUtil.currentDateTime());
        sysResource.setUpdateTime(DateUtil.currentDateTime());
        return sysManagerAppResMapper.insertSelective(sysResource);
    }

    /**
     * @Method getEchoAppZtree
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询回显数据
     * @Return java.util.List<com.hna.hka.archive.management.managerApp.model.SysManagerAppRes>
     * @Date 2021/6/4 17:07
     */
    @Override
    public List<SysManagerAppRes> getEchoAppZtree(Long roleId) {
        return sysManagerAppResMapper.getEchoAppZtree(roleId);
    }

    /**
     * @Method getAppUserResource
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询用户菜单权限
     * @Return java.util.List<com.hna.hka.archive.management.managerApp.model.SysManagerAppRes>
     * @Date 2021/6/7 17:04
     */
    @Override
    public List<SysManagerAppRes> getAppUserResource(Map<String, Object> search) {
        return sysManagerAppResMapper.getAppUserResource(search);
    }

    /**
     * @Method getAppUserRole
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询遍历用户所拥有的操作按钮权限
     * @Return java.util.Set<java.lang.String>
     * @Date 2021/6/8 10:40
     */
    @Override
    public Set<String> getAppUserPermissions(Map<String, Object> search) {
        List<Map<String, String>> permissionsSet = sysManagerAppResMapper.getAppUserPermissions(search);
        Set<String> set = new HashSet<String>();
        for (Map<String, String> rp : permissionsSet) {
            if (null == rp) {
                continue;
            }
            String permissions = rp.get("RES_CODE");
            set.add(permissions);
        }
        return set;
    }


    /**
     * 删除管理者app菜单
     * @param resId
     * @return
     */
    @Override
    public int delManagerAppRes(Long resId) {

        int i = sysManagerAppResMapper.deleteByPrimaryKey(resId);

        return i;

    }
}
