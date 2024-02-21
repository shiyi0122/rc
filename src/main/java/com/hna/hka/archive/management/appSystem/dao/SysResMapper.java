package com.hna.hka.archive.management.appSystem.dao;

import com.hna.hka.archive.management.system.model.SysResource;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.appSystem.dao
 * @ClassName: SysResMapper
 * @Author: 郭凯
 * @Description:
 * @Date: 2020/11/23 14:36
 * @Version: 1.0
 */
public interface SysResMapper {

    List<SysResource> getMenuByAppLoginName(Map<String, Object> map);
}
