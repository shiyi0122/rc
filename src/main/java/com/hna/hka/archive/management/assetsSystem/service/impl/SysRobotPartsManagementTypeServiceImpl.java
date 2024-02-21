package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.dao.SysRobotPartsManagementTypeMapper;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotPartsManagementType;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotPartsManagementTypeService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/12/11 16:54
 */
@Service
public class SysRobotPartsManagementTypeServiceImpl implements SysRobotPartsManagementTypeService {

    @Autowired
    SysRobotPartsManagementTypeMapper sysRobotPartsManagementTypeMapper;

    @Override
    public int addRobotPartsManagementType(SysRobotPartsManagementType sysRobotPartsManagementType) {

        sysRobotPartsManagementType.setId(IdUtils.getSeqId());
        sysRobotPartsManagementType.setCreateDate(DateUtil.currentDateTime());

        int i = sysRobotPartsManagementTypeMapper.insert(sysRobotPartsManagementType);
        return i;
    }

    @Override
    public int editRobotPartsManagementType(SysRobotPartsManagementType sysRobotPartsManagementType) {

        int i = sysRobotPartsManagementTypeMapper.updateByPrimaryKeySelective(sysRobotPartsManagementType);
        return i;
    }

    @Override
    public int delRobotPartsManagementType(Long id) {

       int i =  sysRobotPartsManagementTypeMapper.del(id);

       return i;
    }

    @Override
    public PageDataResult list(SysRobotPartsManagementType sysRobotPartsManagementType,Integer pageNum,Integer pageSize) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
       List<SysRobotPartsManagementType> list =  sysRobotPartsManagementTypeMapper.list(sysRobotPartsManagementType.getPartsManagementType());

       if (list.size()>0){
           PageInfo<SysRobotPartsManagementType> pageInfo = new PageInfo<>(list);
           pageDataResult.setList(list);
           pageDataResult.setTotals((int)pageInfo.getTotal());
       }
       return pageDataResult;
    }

    @Override
    public List<SysRobotPartsManagementType> getPartsManagementTypeDropDown() {

        List<SysRobotPartsManagementType> list = sysRobotPartsManagementTypeMapper.list(null);
        return list;

    }

    @Override
    public SysRobotPartsManagementType getPartsManagementTypeByOne(String accessoriesTypeName) {

       SysRobotPartsManagementType sysRobotPartsManagementType =  sysRobotPartsManagementTypeMapper.getPartsManagementTypeByOne(accessoriesTypeName);

       return sysRobotPartsManagementType;
    }
}

