package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotRestrictedArea;
import com.hna.hka.archive.management.system.model.SysScenicSpotRestrictedAreaWithBLOBs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysScenicSpotRestrictedAreaMapper {
    int deleteByPrimaryKey(Long restrictedWarningId);

    int insert(SysScenicSpotRestrictedAreaWithBLOBs record);

    int insertSelective(SysScenicSpotRestrictedAreaWithBLOBs record);

    SysScenicSpotRestrictedAreaWithBLOBs selectByPrimaryKey(Long restrictedWarningId);

    int updateByPrimaryKeySelective(SysScenicSpotRestrictedAreaWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysScenicSpotRestrictedAreaWithBLOBs record);

    int updateByPrimaryKey(SysScenicSpotRestrictedArea record);

    /**
     * 禁区告警日志列表查询
     * @param search
     * @return
     */
    List<SysScenicSpotRestrictedAreaWithBLOBs> getRestrictedAreatList(Map<String, Object> search);
}