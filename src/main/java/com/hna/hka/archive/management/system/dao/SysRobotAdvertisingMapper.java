package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysRobotAdvertising;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysRobotAdvertisingMapper {
    int deleteByPrimaryKey(Long advertisingId);

    int insert(SysRobotAdvertising record);

    int insertSelective(SysRobotAdvertising record);

    SysRobotAdvertising selectByPrimaryKey(Long advertisingId);

    int updateByPrimaryKeySelective(SysRobotAdvertising record);

    int updateByPrimaryKey(SysRobotAdvertising record);

    List<SysRobotAdvertising> getAdvertisingList(Map<String, String> search);
}