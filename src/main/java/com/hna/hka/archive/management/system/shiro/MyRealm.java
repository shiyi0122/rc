package com.hna.hka.archive.management.system.shiro;

import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.service.LoginService;
import com.hna.hka.archive.management.system.service.SysRoleService;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class MyRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LoginService loginService;

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //授权
        logger.info("授予角色和权限");
        // 添加权限 和 角色信息
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUsers users = (SysUsers) principalCollection.getPrimaryPrincipal();
        authorizationInfo.setRoles(sysRoleService.getRoleByLoginName(users.getLoginName()));
        authorizationInfo.setStringPermissions(sysRoleService.getStringResPrmsByLoginName(users.getLoginName()));

        return authorizationInfo;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //UsernamePasswordToken用于存放提交的登录信息
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        logger.info("用户登录认证：验证当前Subject时获取到token为：" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
        String username = token.getUsername();
        // 调用数据层
        SysUsers sysUser = loginService.findByLoginName(username);
        logger.debug("用户登录认证！用户信息user：" + sysUser);
        if (sysUser == null){
            //用户不存在
            return null;
        }
        logger.info(getName());
        return new SimpleAuthenticationInfo(sysUser, sysUser.getPassword(), ByteSource.Util.bytes(sysUser.getSaltValue()), getName());
    }

    /**
     * @Author 郭凯
     * @Description 清除权限
     * @Date 16:38 2020/8/14
     * @Param 
     * @return 
    **/
    public void clearAllCachedAuthorizationInfo2() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                System.out.println(key+":"+key.toString());
                cache.remove(key);
            }
        }
    }





}
