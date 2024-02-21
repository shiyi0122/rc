package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.*;
import com.hna.hka.archive.management.system.model.SysResource;
import com.hna.hka.archive.management.system.model.SysRole;
import com.hna.hka.archive.management.system.model.SysRoleResource;
import com.hna.hka.archive.management.system.model.SysUsersRoleSpot;
import com.hna.hka.archive.management.system.service.SysRoleService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.NewThreadAction;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
@Transactional
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private HttpSession session;

    @Autowired
    private SysResourceMapper sysResourceMapper;

    @Autowired
    private SysRoleResourceMapper sysRoleResourceMapper;

    @Autowired
    private SysUsersRoleSpotMapper sysUsersRoleSpotMapper;

    @Autowired
    private SysUsersRoleSpotExamineMapper sysUsersRoleSpotExamineMapper;


    /**
     *  根据登录名查询用户角色信息(以角色字符串返回)
     * @Author 郭凯
     * @Description
     * @Date 10:35 2020/4/30
     * @Param [loginName]
     * @return java.util.Set<java.lang.String>
    **/
    @Override
    public Set<String> getRoleByLoginName(String loginName) {
        String scenicSpotId = (String) session.getAttribute("scenicSpotId");
        List<SysRole> rolelist = getRolesByLoginName(loginName,scenicSpotId);
        Set<String> set = new HashSet<String>(rolelist.size());
        for (SysRole role : rolelist) {
            set.add(role.getRoleName());
        }
        return set;
    }

    /**
     *  根据登录名查询用户角色信息(以角色字符串返回)
     * @Author 郭凯
     * @Description
     * @Date 10:40 2020/4/30
     * @Param
     * @return
    **/
    public List<SysRole> getRolesByLoginName(String loginName,String scenicSpotId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("loginName", loginName);
        map.put("roleStatus", "10");
        map.put("scenicSpotId",scenicSpotId);
        return sysRoleMapper.getRolesByLoginName(map);
    }

    /**
     *  根据登录名查询用户的资源权限标识(以角色字符串返回)
     * @Author 郭凯
     * @Description
     * @Date 14:21 2020/4/30
     * @Param []
     * @return java.util.Set<java.lang.String>
     **/
    @Override
    public Set<String> getStringResPrmsByLoginName(String loginName) {
        Map<String, Object> map = new HashMap<String, Object>();
        String scenicSpotId = (String) session.getAttribute("scenicSpotId");
        map.put("loginName", loginName);
        //角色状态
        map.put("roleStatus", "10");
        // 资源类型：功能菜单
        map.put("resType", "10");
        //景区ID
        map.put("scenicSpotId",scenicSpotId);
        List<Map<String, String>> resPrmsIdentity = sysRoleMapper.getRoleResPrmsByLoginName(map);
        Set<String> set = new HashSet<String>();
        for (Map<String, String> rp : resPrmsIdentity) {
            if (null == rp) {
                continue;
            }
            String resIndentity = rp.get("RES_CODE");
            set.add(resIndentity);
        }
        return set;
    }


    /**
     * 查询角色列表
     * @Author 郭凯
     * @Description
     * @Date 14:49 2020/5/12
     * @Param [pageNum, pageSize, sysRole]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysRole>
    **/
    @Override
    public PageDataResult getRoleList(Integer pageNum, Integer pageSize, SysRole sysRole) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRole> sysRoles = sysRoleMapper.getRoleList(sysRole);
        if(sysRoles.size() != 0){
            PageInfo<SysRole> pageInfo = new PageInfo<>(sysRoles);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 添加角色
     * @Author 郭凯
     * @Description
     * @Date 16:26 2020/5/12
     * @Param [sysRole]
     * @return int
    **/
    @Override
    public int addRole(SysRole sysRole) {
        sysRole.setRoleId(IdUtils.getSeqId());
        sysRole.setCreateDate(DateUtil.currentDateTime());
        sysRole.setUpdateDate(DateUtil.currentDateTime());
        return sysRoleMapper.insertSelective(sysRole);
    }

    /**
     *  删除角色
     * @Author 郭凯
     * @Description
     * @Date 10:44 2020/5/15
     * @Param [roleId]
     * @return int
    **/
    @Override
    public int delRole(Long roleId) {
        sysRoleResourceMapper.deleteByPrimaryKeyRoleId(roleId);
        return sysRoleMapper.deleteByPrimaryKey(roleId);
    }

    /**
     *  修改角色信息
     * @Author 郭凯
     * @Description
     * @Date 10:45 2020/5/15
     * @Param [sysRole]
     * @return int
    **/
    @Override
    public int editRole(SysRole sysRole) {
        sysRole.setUpdateDate(DateUtil.currentDateTime());
        return sysRoleMapper.updateByPrimaryKeySelective(sysRole);
    }

    /**
     * @Author 郭凯
     * @Description 查询权限设置回显数据
     * @Date 15:36 2020/5/15
     * @Param [roleId]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysResource>
     **/
    @Override
    public List<SysResource> getEchoZtree(Long roleId) {
        return sysResourceMapper.getEchoZtree(roleId);
    }

    /**
     * @Author 郭凯
     * @Description 权限设置提交
     * @Date 16:14 2020/5/15
     * @Param [roleId, resId]
     * @return int
     **/
    @Override
    public int addRoleResource(Long roleId, String resIds) {
        //删除权限
        sysRoleResourceMapper.deleteByPrimaryKeyRoleId(roleId);
        int a = 0;
        String[] resId = resIds.split(",");
        for (int j = 0; j < resId.length; j++) {
            SysRoleResource sysRoleResource = new SysRoleResource();
            sysRoleResource.setRelationId(IdUtils.getSeqId());
            sysRoleResource.setRoleId(roleId);
            sysRoleResource.setResId(Long.parseLong(resId[j]));
            sysRoleResource.setCreateDate(DateUtil.currentDateTime());
            sysRoleResource.setUpdateDate(DateUtil.currentDateTime());
            a = sysRoleResourceMapper.insertSelective(sysRoleResource);
        }
        return a;
    }

    /**
     * @Author 郭凯
     * @Description 获取权限分配用户
     * @Date 10:52 2020/5/19
     * @Param []
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysRole>
    **/
    @Override
    public List<SysRole> getScenicSpotRole() {
        SysRole sysRole = new SysRole();
        return sysRoleMapper.getRoleList(sysRole);
    }
    /**
     * 根据用户获取用户所有角色
     * @param userId
     * @return
     * zhang
     */
    @Override
    public List<SysRole> getUserRole(Long userId) {

        List<SysRole> list = sysRoleMapper.getUserRole(userId);

        return list;
    }
    /**
     * 根据用户查询带审核景区角色
     * @param userId
     * @return
     * zhang
     */
    @Override
    public List<SysUsersRoleSpot> getUserExamineRole(Long userId) {
//        List<SysRole> list = sysRoleMapper.getUserExamineRole(userId);

        Map<String, Object> search = new HashMap<>();
        search.put("userId",userId);
        List<SysUsersRoleSpot> userRoleSpotList = sysUsersRoleSpotMapper.getUserRoleSpotList(search);

        List<SysUsersRoleSpot> userRoleSpotListNew = sysUsersRoleSpotExamineMapper.getUserRoleSpotList(search);

        userRoleSpotListNew.removeAll(userRoleSpotList);


        return userRoleSpotListNew;
    }

//    /**
//     * 根据角色获取角色下所有的权限
//     * @param roleId
//     * @return
//     */
//    @Override
//    public List<SysResource> getRoleRes(Long roleId) {
//
//        sysResourceMapper.
//
//        return null;
//    }
}
