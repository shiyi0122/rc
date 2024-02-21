package com.hna.hka.archive.management.assetsSystem.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.dao.AddressMapper;
import com.hna.hka.archive.management.assetsSystem.model.Address;
import com.hna.hka.archive.management.assetsSystem.service.AddressService;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.util.IdUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-22 16:39
 **/
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressMapper mapper;


    @Override
    public PageInfo<Address> list(String spotId, String name, String phone, Integer type, Integer pageNum, Integer pageSize) throws Exception{
        PageHelper.offsetPage(pageNum , pageSize);
        List<Address> list = mapper.list(spotId , name , phone , type);
        return new PageInfo<Address>(list);
    }

    @Override
    public Integer add(Address address) throws Exception{
        address.setId(IdUtils.getSeqId());
        HashMap hashMap = mapper.getUserIdByName(address.getName() , address.getSpotId(),address.getUserId());

        address.setUserId((long)hashMap.get("userId"));
        address.setRoleId(Long.parseLong(hashMap.get("roleId").toString()));

        return mapper.add(address);
    }

    @Override
    public Integer edit(Address address) throws Exception{
        return mapper.edit(address);
    }

    @Override
    public Integer delete(Long id) throws Exception {
        return mapper.delete(id);
    }

    @Override
    public Address getByKey(Long spotId,String type) {
        return mapper.getByKey(spotId,type);
    }

    @Override
    public List<Address> getAll() {
        return mapper.getAll();
    }

    @Override
    public List<SysAppUsers> getAppUserBySpot(Long spotId) {
        return mapper.getAppUserBySpot(spotId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upload(MultipartFile file) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<Address> addresses = ExcelImportUtil.importExcel(file.getInputStream(), Address.class, params);
        for (Address address : addresses) {
            address.setId(IdUtils.getSeqId());
            address.setSpotId(mapper.getSpotIdByName(address.getSpotName()));

            HashMap hashMap = mapper.getUserIdByName(address.getName() , address.getSpotId(),address.getUserId());

            address.setUserId((long)hashMap.get("userId"));
            address.setUserName((String) hashMap.get("userName"));
            address.setRoleId(Long.parseLong(hashMap.get("roleId").toString()));
            address.setRoleName((String) hashMap.get("roleName"));
            address.setCompanyId(mapper.getCompanyIdByName(address.getCompanyName()));
            mapper.add(address);
        }
    }

    /**
     * 发货工厂下拉选
     * @return
     */
    @Override
    public List<Address> getFactoryAll(String type) {

       List<Address> list =  mapper.getFactoryAll(type);

       return list;

    }

    /**
     * 四级联动下拉选
     * @return
     */
    @Override
    public List<Address> getFactoryFour() {


        List<Address> list = mapper.getFactoryType();
        for (Address address : list) {
            if (address.getType() == 1){
                address.setId(address.getType());
                address.setLabel("景区地址");
            }else if (address.getType()==2){
                address.setId(address.getType());
                address.setLabel("工厂地址");
            }else if (address.getType() == 3){
                address.setId(address.getType());
                address.setLabel("库房地址");
            }else if (address.getType() == 4){
                address.setId(address.getType());
                address.setLabel("配件供应商地址");
            }else if (address.getType() == 5){
                address.setId(address.getType());
                address.setLabel("研发地址");
            }
            List<Address> listCompany = mapper.getFactoryTypeByCompanyList(address.getType());

            for (Address company : listCompany) {

                List<Address> listSpot = mapper.getFactoryCompanyBySpotList(company.getId(),address.getType());

                for (Address spot : listSpot) {
                  List<Address> listAddress =  mapper.getFactoryCompanyAndSpotAndTypeByAddressList(address.getType(),company.getId(),spot.getId());
                  spot.setAddressList(listAddress);
                }
                company.setAddressList(listSpot);
            }
            address.setAddressList(listCompany);
        }
        return  list;
    }


    @Override
    public List<Address> lists(Map<String, Object> dataMap) {
        List<Address> list = mapper.lists(dataMap);
        return list;
    }

}
