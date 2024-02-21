package com.hna.hka.archive.management.managerApp.dao;

import com.hna.hka.archive.management.managerApp.model.SysManagerAppRes;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;
import java.util.Map;

public interface SysManagerAppResMapper {
    int deleteByPrimaryKey(Long resId);

    int insert(SysManagerAppRes record);

    int insertSelective(SysManagerAppRes record);

    SysManagerAppRes selectByPrimaryKey(Long resId);

    int updateByPrimaryKeySelective(SysManagerAppRes record);

    int updateByPrimaryKey(SysManagerAppRes record);

    List<SysManagerAppRes> getManagerAppResList(SysManagerAppRes managerAppRes);

    List<SysManagerAppRes> getEchoAppZtree(@Param("roleId") Long roleId);

    List<SysManagerAppRes> getAppUserResource(Map<String, Object> search);

    List<Map<String, String>> getAppUserPermissions(Map<String, Object> search);
}