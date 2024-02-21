package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysParkingExcel;
import com.hna.hka.archive.management.system.model.SysScenicSpotParking;
import com.hna.hka.archive.management.system.model.SysScenicSpotParkingWithBLOBs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysScenicSpotParkingMapper {
    int deleteByPrimaryKey(Long parkingId);

    int insert(SysScenicSpotParkingWithBLOBs record);

    int insertSelective(SysScenicSpotParkingWithBLOBs record);

    SysScenicSpotParkingWithBLOBs selectByPrimaryKey(Long parkingId);

    int updateByPrimaryKeySelective(SysScenicSpotParkingWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysScenicSpotParkingWithBLOBs record);

    int updateByPrimaryKey(SysScenicSpotParking record);

    List<SysScenicSpotParkingWithBLOBs> getParkingList(Map<String, String> search);

    List<SysScenicSpotParkingWithBLOBs> getAllSportParkingByScenicSpotId(@Param("scenicSpotId") Long scenicSpotId,@Param("parkingType") String parkingType);

    List<SysScenicSpotParkingWithBLOBs> getParkingListByScenicSpotId(Long scenicSpotId);


    List<SysScenicSpotParkingWithBLOBs> getParkingCoordList(Map<String, String> search);

    List<SysScenicSpotParkingWithBLOBs> getStorageRoomListBy(long scenicSpotId);

    List<SysScenicSpotParkingWithBLOBs> uploadScenicSpotCapPriceExcel(Map<String, String> search);

    List<SysParkingExcel> getParkingExcel(Map<String, Object> search);

    SysParkingExcel selScenicSpotParking(SysParkingExcel parking);

    int addParkingExcel(SysParkingExcel parking);

    int upParking(SysParkingExcel parking);

    List<SysScenicSpotParkingWithBLOBs> getScenicSpotParkingAll();


    List<SysScenicSpotParking> parkingDropDown(Long scenicSpotId);


}