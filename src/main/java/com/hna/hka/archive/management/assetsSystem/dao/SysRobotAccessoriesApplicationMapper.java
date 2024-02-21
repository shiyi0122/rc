package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.Address;
import com.hna.hka.archive.management.assetsSystem.model.StatementOfAccessories;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotAccessoriesApplication;

import java.util.List;
import java.util.Map;

public interface SysRobotAccessoriesApplicationMapper {
    int deleteByPrimaryKey(Long accessoriesApplicationId);

    int insert(SysRobotAccessoriesApplication record);

    int insertSelective(SysRobotAccessoriesApplication record);

    SysRobotAccessoriesApplication selectByPrimaryKey(Long accessoriesApplicationId);

    int updateByPrimaryKeySelective(SysRobotAccessoriesApplication record);

    int updateByPrimaryKey(SysRobotAccessoriesApplication record);

    List<SysRobotAccessoriesApplication> getRobotAccessoriesApplicationList(Map<String, String> search);

    List<SysRobotAccessoriesApplication> getRobotAccessoriesApplicationVoList(Map<String, Object> search);

    //修改上一个方法
    List<SysRobotAccessoriesApplication> getRobotAccessoriesApplicationVoListNew(Map<String, Object> search);

    List<SysRobotAccessoriesApplication> getRobotAccessoriesApplicationListNew(Map<String, String> search);

    List<StatementOfAccessories> getRobotAccessoriesApplicationVerifyList(Long spot, String beginDate);

    Map<String, Object> getCountNew(Long spot, String beginDate);


    int exitSettlementState(Long errorRecordsId, Long sysUserId);

    Map<String, Object> calculateTotal(Long spot, String beginDate);


    List<SysRobotAccessoriesApplication> getDateLikeByAccessoriesApplicationList(String date);


    int update(Map<String, Object> dataMap);

    List<Address> selectWarehouse(Map<String, Object> dataMap);

    int updateMany(String processingProgress,String accessoriesApplicationId);

}