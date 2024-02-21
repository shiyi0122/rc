package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysLogMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotGpsCoordinateMapper;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.service.SysScenicSpotGpsCoordinateService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysScenicSpotGpsCoordinateServiceImpl
 * @Author: 郭凯
 * @Description: 景区电子围栏业务层（实现层）
 * @Date: 2020/5/22 11:22
 * @Version: 1.0
 */
@Service
@Transactional
public class SysScenicSpotGpsCoordinateServiceImpl implements SysScenicSpotGpsCoordinateService {

    @Autowired
    private SysScenicSpotGpsCoordinateMapper sysScenicSpotGpsCoordinateMapper;

    @Autowired
    private SysLogMapper sysLogMapper;

    /**
     * @Author 郭凯
     * @Description 查询此景区是否已经有电子围栏
     * @Date 11:25 2020/5/22
     * @Param [coordinateScenicSpotId]
     * @return com.hna.hka.archive.management.system.model.SysScenicSpotGpsCoordinate
    **/
    @Override
    public SysScenicSpotGpsCoordinateWithBLOBs selectByPrimaryKey(Long coordinateScenicSpotId) {
        return sysScenicSpotGpsCoordinateMapper.selectByPrimaryKeyScenicSpotId(coordinateScenicSpotId);
    }

    /**
     * @Author 郭凯
     * @Description 添加景区围栏
     * @Date 11:30 2020/5/22
     * @Param [sysScenicSpotGpsCoordinateWithBLOBs]
     * @return int
    **/
    @Override
    public int addBtnSubmitScenicSpotGpsCoordinate(SysScenicSpotGpsCoordinateWithBLOBs sysScenicSpotGpsCoordinateWithBLOBs) {
        sysScenicSpotGpsCoordinateWithBLOBs.setCoordinateId(IdUtils.getSeqId());
        sysScenicSpotGpsCoordinateWithBLOBs.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotGpsCoordinateWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotGpsCoordinateMapper.insertSelective(sysScenicSpotGpsCoordinateWithBLOBs);
    }

    /**
     * @Author 郭凯
     * @Description 修改景区电子围栏
     * @Date 14:10 2020/5/22
     * @Param [sysScenicSpotGpsCoordinateWithBLOBs]
     * @return int
    **/
    @Override
    public int editBtnSubmitScenicSpotGpsCoordinate(SysScenicSpotGpsCoordinateWithBLOBs sysScenicSpotGpsCoordinateWithBLOBs) {
        sysScenicSpotGpsCoordinateWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotGpsCoordinateMapper.updateByPrimaryKeySelective(sysScenicSpotGpsCoordinateWithBLOBs);
    }

    /**
     * @Author 郭凯
     * @Description 查询电子围栏列表
     * @Date 17:03 2020/12/5
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getScenicSpotGpsCoordinateList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotGpsCoordinateWithBLOBs> sysScenicSpotGpsCoordinateWithBLOBsList = sysScenicSpotGpsCoordinateMapper.getScenicSpotGpsCoordinateList(search);
        if (sysScenicSpotGpsCoordinateWithBLOBsList.size() != 0){
            PageInfo<SysScenicSpotGpsCoordinateWithBLOBs> pageInfo = new PageInfo<>(sysScenicSpotGpsCoordinateWithBLOBsList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 添加修改日志
     * @Date 17:37 2020/12/5
     * @Param [sysLog]
     * @return void
    **/
    @Override
    public void addSysLog(SysLogWithBLOBs sysLog) {
        sysLog.setCreateDate(DateUtil.currentDateTime());
        sysLogMapper.insertSelective(sysLog);
    }

    /**
     * 获取全部围栏
     * @return
     */
    @Override
    public List<SysScenicSpotGpsCoordinateWithBLOBs> getScenicSpotGpsCoordinateAll() {

       List<SysScenicSpotGpsCoordinateWithBLOBs> list = sysScenicSpotGpsCoordinateMapper.getScenicSpotGpsCoordinateAll();

       return list;
    }
}
