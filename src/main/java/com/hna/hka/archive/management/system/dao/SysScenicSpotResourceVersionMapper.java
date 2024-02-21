package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotResourceVersion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysScenicSpotResourceVersionMapper {
    int deleteByPrimaryKey(Long resId);

    int insert(SysScenicSpotResourceVersion record);

    int insertSelective(SysScenicSpotResourceVersion record);

    SysScenicSpotResourceVersion selectByPrimaryKey(Long resId);

    int updateByPrimaryKeySelective(SysScenicSpotResourceVersion record);

    int updateByPrimaryKey(SysScenicSpotResourceVersion record);

    int updateResourceVersion(SysScenicSpotResourceVersion sysScenicSpotResourceVersion2);

    SysScenicSpotResourceVersion selectResourceVersion(@Param("sysScenicSpotResourceVersion") SysScenicSpotResourceVersion sysScenicSpotResourceVersion);



}