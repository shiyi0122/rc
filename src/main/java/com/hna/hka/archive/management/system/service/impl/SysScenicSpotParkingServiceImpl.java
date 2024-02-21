package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysScenicSpotParkingMapper;
import com.hna.hka.archive.management.system.model.SysParkingExcel;
import com.hna.hka.archive.management.system.model.SysScenicSpotParking;
import com.hna.hka.archive.management.system.model.SysScenicSpotParkingWithBLOBs;
import com.hna.hka.archive.management.system.service.SysScenicSpotParkingService;
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
 * @ClassName: SysScenicSpotParkingServiceImpl
 * @Author: 郭凯
 * @Description: 停放点管理业务层（实现）
 * @Date: 2020/6/12 14:22
 * @Version: 1.0
 */
@Service
@Transactional
public class SysScenicSpotParkingServiceImpl implements SysScenicSpotParkingService {

    @Autowired
    private SysScenicSpotParkingMapper sysScenicSpotParkingMapper;


    /**
     * @Author 郭凯
     * @Description 停放点管理列表查询
     * @Date 14:27 2020/6/12
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     **/
    @Override
    public PageDataResult getParkingList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotParkingWithBLOBs> parkingWithBLOBsList = sysScenicSpotParkingMapper.getParkingList(search);
        if (parkingWithBLOBsList.size() != 0){
            PageInfo<SysScenicSpotParkingWithBLOBs> pageInfo = new PageInfo<>(parkingWithBLOBsList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 添加停放点信息
     * @Date 15:54 2020/6/12
     * @Param [sysScenicSpotParkingWithBLOBs]
     * @return int
    **/
    @Override
    public int addParking(SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs) {
        sysScenicSpotParkingWithBLOBs.setParkingId(IdUtils.getSeqId());
        sysScenicSpotParkingWithBLOBs.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotParkingWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotParkingMapper.insertSelective(sysScenicSpotParkingWithBLOBs);
    }

    /**
     * @Author 郭凯
     * @Description 修改停放点信息
     * @Date 16:10 2020/6/12
     * @Param [sysScenicSpotParkingWithBLOBs]
     * @return int
    **/
    @Override
    public int editParking(SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs) {
        sysScenicSpotParkingWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotParkingMapper.updateByPrimaryKeySelective(sysScenicSpotParkingWithBLOBs);
    }

    /**
     * @Author 郭凯
     * @Description 删除停放点信息
     * @Date 16:15 2020/6/12
     * @Param [parkingId]
     * @return int
    **/
    @Override
    public int delParking(Long parkingId) {
        return sysScenicSpotParkingMapper.deleteByPrimaryKey(parkingId);
    }

    /**
     * @Author 郭凯
     * @Description 根据景区ID查询停放点
     * @Date 10:48 2020/6/23
     * @Param [scenicSpotId, parkingType]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysScenicSpotParkingWithBLOBs>
    **/
    @Override
    public List<SysScenicSpotParkingWithBLOBs> getAllSportParkingByScenicSpotId(Long scenicSpotId, String parkingType) {
        return sysScenicSpotParkingMapper.getAllSportParkingByScenicSpotId(scenicSpotId,parkingType);
    }

    /**
     * @Author 郭凯
     * @Description 停车点坐标查询接口
     * @Date 14:07 2020/7/24
     * @Param [scenicSpotId]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysScenicSpotParkingWithBLOBs>
    **/
    @Override
    public List<SysScenicSpotParkingWithBLOBs> getParkingListBy(Long scenicSpotId) {
        return sysScenicSpotParkingMapper.getParkingListByScenicSpotId(scenicSpotId);
    }

    /**
     * 库房坐标列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getParkingCoordList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotParkingWithBLOBs> parkingWithBLOBsList = sysScenicSpotParkingMapper.getParkingCoordList(search);
        if (parkingWithBLOBsList.size() != 0){
            PageInfo<SysScenicSpotParkingWithBLOBs> pageInfo = new PageInfo<>(parkingWithBLOBsList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 添加库房坐标
     * @param sysScenicSpotParkingWithBLOBs
     * @return
     */
    @Override
    public int addParkingCoord(SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs) {
        sysScenicSpotParkingWithBLOBs.setParkingId(IdUtils.getSeqId());
        sysScenicSpotParkingWithBLOBs.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotParkingWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        sysScenicSpotParkingWithBLOBs.setCoordinateType("1");
        return sysScenicSpotParkingMapper.insertSelective(sysScenicSpotParkingWithBLOBs);
    }

    @Override
    public List<SysScenicSpotParkingWithBLOBs> getStorageRoomListBy(long scenicSpotId) {


        return sysScenicSpotParkingMapper.getStorageRoomListBy(scenicSpotId);

    }

    /**
     * 导出
     * @param search
     * @return
     */
    @Override
    public List<SysScenicSpotParkingWithBLOBs> uploadScenicSpotCapPriceExcel(Map<String, String> search) {
        List<SysScenicSpotParkingWithBLOBs> parkingCoordList = sysScenicSpotParkingMapper.uploadScenicSpotCapPriceExcel(search);


        return parkingCoordList;
    }
    @Override
    public int addParkingExcel(SysParkingExcel parking) {
        return sysScenicSpotParkingMapper.addParkingExcel(parking);
    }

    @Override
    public int upParking(SysParkingExcel parking) {
        return sysScenicSpotParkingMapper.upParking(parking);
    }

    /**
     * 获取所有停放点
     * @return
     */
    @Override
    public List<SysScenicSpotParkingWithBLOBs> getScenicSpotParkingAll() {

        List<SysScenicSpotParkingWithBLOBs> list = sysScenicSpotParkingMapper.getScenicSpotParkingAll();

        return list;
    }

    /**
     * 停放点下拉选
     * @return
     */
    @Override
    public List<SysScenicSpotParking> parkingDropDown(Long scenicSpotId) {
       List<SysScenicSpotParking> list =  sysScenicSpotParkingMapper.parkingDropDown(scenicSpotId);


       return list;
    }

    @Override
    public List<SysScenicSpotParking> getSpotIdParkingList(long spotId) {

        List<SysScenicSpotParking> parkingListByScenicSpotId = sysScenicSpotParkingMapper.parkingDropDown(spotId);
        return parkingListByScenicSpotId;
    }

    @Override
    public SysParkingExcel selScenicSpotParking(SysParkingExcel parking) {
        return sysScenicSpotParkingMapper.selScenicSpotParking(parking);
    }

    @Override
    public List<SysParkingExcel> getParkingExcel(Map<String, Object> search) {
        return sysScenicSpotParkingMapper.getParkingExcel(search);
    }


}
