package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysAppModificationLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysAppModificationLogMapper {
    int deleteByPrimaryKey(Long modificationLogId);

    int insert(SysAppModificationLog record);

    int insertSelective(SysAppModificationLog record);

    SysAppModificationLog selectByPrimaryKey(Long modificationLogId);

    int updateByPrimaryKeySelective(SysAppModificationLog record);

    int updateByPrimaryKey(SysAppModificationLog record);

    List<SysAppModificationLog> getAppModificationLogList(Map<String, Object> search);
}