package com.hna.hka.archive.management.appSystem.dao;

import com.hna.hka.archive.management.appSystem.model.SysAppLoginLog;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.appSystem.dao
 * @ClassName: SysAppLoginLogMapper
 * @Author: 郭凯
 * @Description:
 * @Date: 2020/11/23 13:48
 * @Version: 1.0
 */
public interface SysAppLoginLogMapper {

    int insertSelective(SysAppLoginLog sysLoginLog);
}
