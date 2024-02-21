package com.hna.hka.archive.management.system.util;

import com.hna.hka.archive.management.system.model.SysUsers;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 公共方法
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.util
 * @ClassName: PublicUtil
 * @Author: 郭凯
 * @Description:
 * @Date: 2020/5/12 14:39
 * @Version: 1.0
 */
public class PublicUtil {

    public static final Logger logger = LoggerFactory.getLogger(PublicUtil.class);


    /**
     * 获取用户
     * @return
     */
    public SysUsers getSysUser(){
        SysUsers user = (SysUsers) SecurityUtils.getSubject().getPrincipal();
        return user;
    }

}
