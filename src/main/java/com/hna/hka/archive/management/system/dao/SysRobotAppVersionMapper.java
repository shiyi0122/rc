package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRobotAppVersion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysRobotAppVersionMapper {
    int deleteByPrimaryKey(Long versionId);

    int insert(SysRobotAppVersion record);

    int insertSelective(SysRobotAppVersion record);

    SysRobotAppVersion selectByPrimaryKey(Long versionId);

    int updateByPrimaryKeySelective(SysRobotAppVersion record);

    int updateByPrimaryKey(SysRobotAppVersion record);

    /**
     * PAD版本管理列表查询
     * @param sysRobotAppVersion
     * @return
     */
    List<SysRobotAppVersion> getRobotVersionPadList(@Param("sysRobotAppVersion") SysRobotAppVersion sysRobotAppVersion);

    /**
     * 查询当前版本号
     * @param scenicSpotId
     * @return
     */
    SysRobotAppVersion getAppVersionNumber(Long scenicSpotId);

    List<SysRobotAppVersion> getScenicSpotPadList(Map<String, String> search);
}