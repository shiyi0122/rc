package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotRecommendedRoute;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotRecommendedRouteMapper {
    int deleteByPrimaryKey(Long routeId);

    int insert(SysScenicSpotRecommendedRoute record);

    int insertSelective(SysScenicSpotRecommendedRoute record);

    SysScenicSpotRecommendedRoute selectByPrimaryKey(Long routeId);

    int updateByPrimaryKeySelective(SysScenicSpotRecommendedRoute record);

    int updateByPrimaryKey(SysScenicSpotRecommendedRoute record);

    List<SysScenicSpotRecommendedRoute> getRecommendedRouteList(Map<String, String> search);

    List<SysScenicSpotRecommendedRoute> getScenicSpotRecommendedRouteAll();



}