package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysRobotSimSupplierMapper;
import com.hna.hka.archive.management.system.model.SysRobotSimSupplier;
import com.hna.hka.archive.management.system.service.SysRobotSimSupplierService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/6/2 10:59
 */
@Service
public class SysRobotSimSupplierServiceImpl implements SysRobotSimSupplierService {

    @Autowired
    SysRobotSimSupplierMapper sysRobotSimSupplierMapper;


    /**
     * 添加sim卡号
     * @param sysRobotSimSupplier
     * @return
     */
    @Override
    public int addSysRobotSimSupplier(SysRobotSimSupplier sysRobotSimSupplier) {

        sysRobotSimSupplier.setId(IdUtils.getSeqId());
        sysRobotSimSupplier.setCreateTime(DateUtil.currentDateTime());
        sysRobotSimSupplier.setUpdateTime(DateUtil.currentDateTime());

        int i = sysRobotSimSupplierMapper.insertSelective(sysRobotSimSupplier);

        return i;
    }

    /**
     * 修改sim卡号信息
     * @param sysRobotSimSupplier
     * @return
     */
    @Override
    public int editSysRobotSimSupplier(SysRobotSimSupplier sysRobotSimSupplier) {

        sysRobotSimSupplier.setUpdateTime(DateUtil.currentDateTime());

        int i = sysRobotSimSupplierMapper.updateByPrimaryKeySelective(sysRobotSimSupplier);

        return i;
    }

    /**
     * sim卡号信息列表查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysRobotSimSupplierList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageNum,pageSize);

        List<SysRobotSimSupplier> list = sysRobotSimSupplierMapper.getSysRobotSimSupplierList(search);

        if (list.size() > 0){
            PageInfo<SysRobotSimSupplier> pageInfo = new PageInfo<>(list);
            pageDataResult.setList(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;

    }

    /**
     * 删除sim卡号信息
     * @param id
     * @return
     */
    @Override
    public int delSysRobotSimSupplier(Long id) {

       int i = sysRobotSimSupplierMapper.deleteByPrimaryKey(id);

       return i;

    }

    /**
     * 导出ICCID卡号列表
     * @param search
     * @return
     */
    @Override
    public List<SysRobotSimSupplier> getSimExcel(Map<String, Object> search) {

        List<SysRobotSimSupplier> list = sysRobotSimSupplierMapper.getSysRobotSimSupplierList(search);

        return list;
    }

    /**
     * 根据ICCID卡号，查询是否已存在
     * @param simCard
     * @return
     */
    @Override
    public SysRobotSimSupplier getSimById(String simCard) {

        SysRobotSimSupplier sysRobotSimSupplier = sysRobotSimSupplierMapper.getSimById(simCard);

        return sysRobotSimSupplier;
    }
}
