package com.hna.hka.archive.management.system.dao;

import java.util.List;

import com.hna.hka.archive.management.system.model.SysSpotResources;

public interface SysSpotResourcesMapper {
    int deleteByPrimaryKey(Long spotResourcesId);

    int insert(SysSpotResources record);

    int insertSelective(SysSpotResources record);

    SysSpotResources selectByPrimaryKey(Long spotResourcesId);

    int updateByPrimaryKeySelective(SysSpotResources record);

    int updateByPrimaryKey(SysSpotResources record);

	List<SysSpotResources> getSpotResourcesByScenicSpotId(Long scenicSpotId);
}