package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.assetsSystem.model.ChinaMap;
import com.hna.hka.archive.management.system.model.SysScenicSpotBinding;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysScenicSpotBindingMapper {
    int deleteByPrimaryKey(Long scenicSpotFid);

    int insert(SysScenicSpotBinding record);

    int insertSelective(SysScenicSpotBinding record);

    SysScenicSpotBinding selectByPrimaryKey(Long scenicSpotFid);

    int updateByPrimaryKeySelective(SysScenicSpotBinding record);

    int updateByPrimaryKey(SysScenicSpotBinding record);

    /**
     * 查询角色景区
     * @return
     */
    List<SysScenicSpotBinding> getScenicSpotRole();

    /**
     * 景区归属地列表查询
     * @param sysScenicSpotBinding
     * @return
     */
    List<SysScenicSpotBinding> getScenicSpotBindingList(@Param("sysScenicSpotBinding") SysScenicSpotBinding sysScenicSpotBinding);

    List<SysScenicSpotBinding> selectBindingsList();

    List<SysScenicSpotBinding> selectUserName(String loginName);

    List<SysScenicSpotBinding> selectUserPid(Long scenicSpotPid);

    List<SysScenicSpotBinding> selectUserFid(Long scenicSpotFid);

    List<ChinaMap> getChinaMapList();

    List<SysScenicSpotBinding> getScenicSpotById(String id);

    List<SysScenicSpotBinding> spotFactoryList();


    List<SysScenicSpotBinding> getSpotBindingProvince(@Param("pid") String  pid);


    /**
     * 新修改查询
     * @param sysScenicSpotBinding
     * @return
     */
    List<SysScenicSpotBinding> getScenicSpotBindingListN(@Param("sysScenicSpotBinding")SysScenicSpotBinding sysScenicSpotBinding);


    List<SysScenicSpotBinding> getSpotBindingCity(@Param("pid") String pid);


    List<SysScenicSpotBinding> getSpotBindingArea(@Param("pid")String pid);


    List<SysScenicSpotBinding> selectBindingsByRecordList();


    List<SysScenicSpotBinding> selectUserFidByRecord(Long scenicSpotFid);

    List<SysScenicSpotBinding> selectUserNameByRecord(String loginName);

}