package com.hna.hka.archive.management.appSystem.dao;


import com.hna.hka.archive.management.appSystem.model.vo.SysAppRobotQualityTesting;
import com.hna.hka.archive.management.assetsSystem.model.RobotQualityTesting;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysAppRobotQualityTestingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysAppRobotQualityTesting record);

    int insertSelective(SysAppRobotQualityTesting record);

    SysAppRobotQualityTesting selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysAppRobotQualityTesting record);

    int updateByPrimaryKey(SysAppRobotQualityTesting record);
    SysAppRobotQualityTesting seInspectionDetails(Long id);
    RobotQualityTesting detailList(Long id);

}