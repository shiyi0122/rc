package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotCustomType;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotCustomTypeMapper {
    int deleteByPrimaryKey(Long typeId);

    int insert(SysScenicSpotCustomType record);

    int insertSelective(SysScenicSpotCustomType record);

    SysScenicSpotCustomType selectByPrimaryKey(Long typeId);

    int updateByPrimaryKeySelective(SysScenicSpotCustomType record);

    int updateByPrimaryKey(SysScenicSpotCustomType record);

    List<SysScenicSpotCustomType> getCustomTypeList(Map<String, String> search);

    SysScenicSpotCustomType getCustomTypeById(@Param("sysScenicSpotCustomType") SysScenicSpotCustomType sysScenicSpotCustomType);

    List<SysScenicSpotCustomType> getCueToneTypeList(Map<String, String> search);

    SysScenicSpotCustomType getCustomTypeByType(String typeNameValue);

    SysScenicSpotCustomType selectCustomTypeByType(String serviceType);

    List<SysScenicSpotCustomType> selectCustomType();

	List<SysScenicSpotCustomType> getCueToneTypeByResId();
}