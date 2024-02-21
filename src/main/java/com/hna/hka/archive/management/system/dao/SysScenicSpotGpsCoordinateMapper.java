package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotGpsCoordinate;
import com.hna.hka.archive.management.system.model.SysScenicSpotGpsCoordinateWithBLOBs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysScenicSpotGpsCoordinateMapper {
    int deleteByPrimaryKey(Long coordinateId);

    int insert(SysScenicSpotGpsCoordinateWithBLOBs record);

    int insertSelective(SysScenicSpotGpsCoordinateWithBLOBs record);

    SysScenicSpotGpsCoordinateWithBLOBs selectByPrimaryKey(Long coordinateId);

    int updateByPrimaryKeySelective(SysScenicSpotGpsCoordinateWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysScenicSpotGpsCoordinateWithBLOBs record);

    int updateByPrimaryKey(SysScenicSpotGpsCoordinate record);

    SysScenicSpotGpsCoordinateWithBLOBs selectByPrimaryKeyScenicSpotId(Long coordinateScenicSpotId);

    List<SysScenicSpotGpsCoordinateWithBLOBs> getScenicSpotGpsCoordinateList(Map<String, String> search);

    List<SysScenicSpotGpsCoordinateWithBLOBs> getScenicSpotGpsCoordinateAll();


}