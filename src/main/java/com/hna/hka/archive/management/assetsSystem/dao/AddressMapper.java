package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.Address;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotAccessoriesApplication;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-22 16:40
 **/
public interface AddressMapper {
    List<Address> list(String spotId, String name, String phone, Integer type) throws Exception;

    Integer add(Address address) throws Exception;

    Integer edit(Address address) throws Exception;

    Integer delete(Long id) throws Exception;

    HashMap<String, Long> getRoleId(Long id, long spotId) throws Exception;

    Address getByKey(Long spotId,String type);

    List<Address> getByKeyList(Long spotId);


    List<Address> getAll();

    List<SysAppUsers> getAppUserBySpot(Long spotId);

    Long getSpotIdByName(String spotName);

    HashMap getUserIdByName(String userName , Long spotId,Long userId);

    Long getCompanyIdByName(String companyName);

    List<Address> getFactoryAll(@Param("type")String type);


    List<Address> getFactoryType();

    Address selectById(Long spotId);

    List<Address> getFactoryTypeByCompanyList(long type);

    List<Address> getFactoryCompanyBySpotList(long companyId, long type);

    List<Address> getFactoryCompanyAndSpotAndTypeByAddressList(long type, long companyId, long spotId);

    List<Address> lists(Map<String, Object> dataMap);

    List<Address> getUserIdByAddress(String userId);

    Address selectByIds(Long spotId);

    SysRobotAccessoriesApplication selectByOut(String accessoriesApplicationId);
}
