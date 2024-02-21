package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.InnercricleExcel;
import com.hna.hka.archive.management.system.model.SysScenicSpotInnercircleCoordinateGroup;
import com.hna.hka.archive.management.system.model.SysScenicSpotInnercircleCoordinateGroupWithBLOBs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysScenicSpotInnercircleCoordinateGroupMapper {
    int deleteByPrimaryKey(Long coordinateInnercircleId);

    int insert(SysScenicSpotInnercircleCoordinateGroupWithBLOBs record);

    int insertSelective(SysScenicSpotInnercircleCoordinateGroupWithBLOBs record);

    SysScenicSpotInnercircleCoordinateGroupWithBLOBs selectByPrimaryKey(Long coordinateInnercircleId);

    int updateByPrimaryKeySelective(SysScenicSpotInnercircleCoordinateGroupWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysScenicSpotInnercircleCoordinateGroupWithBLOBs record);

    int updateByPrimaryKey(SysScenicSpotInnercircleCoordinateGroup record);

    List<SysScenicSpotInnercircleCoordinateGroupWithBLOBs> getInnercircleList(Map<String, String> search);

    List<SysScenicSpotInnercircleCoordinateGroupWithBLOBs> getcoordinateGroupListBy(Long scenicSpotId);

    List<SysScenicSpotInnercircleCoordinateGroupWithBLOBs> getCoordinateGroupListByScenicSpotId(Long scenicSpotId);

    List<InnercricleExcel> getInnercricleExcel(Map<String, Object> search);

    InnercricleExcel getcoordinateInnercircleIdByName(InnercricleExcel inn);

    int setInnercricleExcel(InnercricleExcel inn);

    int upInnercricleExcel(InnercricleExcel inn);
}