package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.dao.SysRobotPeripheralMapper;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotPeripheral;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotPeripheralService;
import com.hna.hka.archive.management.system.dao.SysScenicSpotMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.ReturnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author zhang
 * @Date 2022/3/1 15:35
 * 机器人外设管理
 */
@Service
@Transactional
public class SysRobotPeripheralServiceImpl implements SysRobotPeripheralService {

    @Autowired
    SysRobotPeripheralMapper sysRobotPeripheralMapper;

    @Autowired
    SysScenicSpotMapper sysScenicSpotMapper;

    /**
     * 查询
     * @param search
     * @return
     */
    @Override
    public ReturnModel getRobotPeripheralList(Integer pageNum,Integer pageSize,Map<String, String> search) {
        ReturnModel returnModel = new ReturnModel();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotPeripheral> list = sysRobotPeripheralMapper.getRobotPeripheralList(search);
        if (list.size() != 0){
            PageInfo<SysRobotPeripheral> pageInfo = new PageInfo<>(list);
            returnModel.setData(pageInfo.getList());
            returnModel.setTotal((int) pageInfo.getTotal());
        }
        return returnModel ;
    }

    /**
     * 添加
     * @param sysRobotPeripheral
     * @return
     */
    @Override
    public int addRobotPeripheral(SysRobotPeripheral sysRobotPeripheral) {

        sysRobotPeripheral.setRobotPeripheralId(IdUtils.getSeqId());
        sysRobotPeripheral.setCreateDate(DateUtil.currentDateTime());
        sysRobotPeripheral.setUpdateDate(DateUtil.currentDateTime());

        int i = sysRobotPeripheralMapper.insertSelective(sysRobotPeripheral);

        return i;
    }

    /**
     * 修改
     * @param sysRobotPeripheral
     * @return
     */
    @Override
    public int editRobotPeripheral(SysRobotPeripheral sysRobotPeripheral) {

        sysRobotPeripheral.setUpdateDate(DateUtil.currentDateTime());

        int i = sysRobotPeripheralMapper.updateByPrimaryKeySelective(sysRobotPeripheral);

        return i;
    }

    /**
     * 删除
     * @param robotPeripheralId
     * @return
     */
    @Override
    public int delRobotPeripheral(Long robotPeripheralId) {

        int i = sysRobotPeripheralMapper.deleteByPrimaryKey(robotPeripheralId);

        return i;
    }

    /**
     * 导出EXCEL
     * @param
     * @return
     */
    @Override
    public List<SysRobotPeripheral> getRobotPeripheralExcelList(Map<String, String> search) {

        List<SysRobotPeripheral> list = sysRobotPeripheralMapper.getRobotPeripheralList(search);

        return list;

    }

    /**
     * 导入
     * @param sysRobotPeripheral
     * @return
     */
    @Override
    public int importExcel(SysRobotPeripheral sysRobotPeripheral) {
        List<SysRobotPeripheral> sysRobotPeripherals = new ArrayList<>();
//       查询表中所有外设id
        sysRobotPeripherals = sysRobotPeripheralMapper.getRobotPeripheralId(sysRobotPeripheral);
        boolean anyMatch = sysRobotPeripherals.stream().anyMatch(sysRobotPeripheral1 -> Objects.equals(sysRobotPeripheral1.getRobotPeripheralId(), sysRobotPeripheral.getRobotPeripheralId()));
        int i = 0;
        if (StringUtils.isEmpty(sysRobotPeripheral.getRobotPeripheralId())) {
            SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectScenicNameByName(sysRobotPeripheral.getScenicSpotName());
            sysRobotPeripheral.setScenicSpotId(sysScenicSpot.getScenicSpotId());
            sysRobotPeripheral.setRobotPeripheralId(IdUtils.getSeqId());
            sysRobotPeripheral.setCreateDate(DateUtil.currentDateTime());
            sysRobotPeripheral.setUpdateDate(DateUtil.currentDateTime());
            i = sysRobotPeripheralMapper.insertSelective(sysRobotPeripheral);

        } else if (anyMatch==false){
            SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectScenicNameByName(sysRobotPeripheral.getScenicSpotName());
            sysRobotPeripheral.setScenicSpotId(sysScenicSpot.getScenicSpotId());
            sysRobotPeripheral.setRobotPeripheralId(IdUtils.getSeqId());
            sysRobotPeripheral.setCreateDate(DateUtil.currentDateTime());
            sysRobotPeripheral.setUpdateDate(DateUtil.currentDateTime());
            i = sysRobotPeripheralMapper.insertSelective(sysRobotPeripheral);
        }else if (anyMatch==true){
//            修改
            i =  sysRobotPeripheralMapper.updateByPrimaryKeySelective(sysRobotPeripheral);
        }
       return  i;
    }
}
