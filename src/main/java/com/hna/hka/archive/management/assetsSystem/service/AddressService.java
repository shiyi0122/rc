package com.hna.hka.archive.management.assetsSystem.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.Address;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-22 16:39
 **/
public interface AddressService {
    PageInfo<Address> list(String spotId, String name, String phone, Integer type, Integer pageNum, Integer pageSize) throws Exception;

    Integer add(Address address) throws Exception;

    Integer edit(Address address) throws Exception;

    Integer delete(Long id) throws Exception;

    Address getByKey(Long spotId,String type);

    List<Address> getAll();

    List<SysAppUsers> getAppUserBySpot(Long spotId);

    void upload(MultipartFile file) throws Exception;

    List<Address> getFactoryAll(String type);


    List<Address> getFactoryFour();

    List<Address> lists(Map<String, Object> dataMap);


}
