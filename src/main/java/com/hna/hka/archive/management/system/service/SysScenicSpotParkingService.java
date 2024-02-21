package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysParkingExcel;
import com.hna.hka.archive.management.system.model.SysScenicSpotParking;
import com.hna.hka.archive.management.system.model.SysScenicSpotParkingWithBLOBs;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysScenicSpotParkingService
 * @Author: 郭凯
 * @Description: 停放点管理业务层（接口）
 * @Date: 2020/6/12 14:21
 * @Version: 1.0
 */
public interface SysScenicSpotParkingService {

    PageDataResult getParkingList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int addParking(SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs);

    int editParking(SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs);

    int delParking(Long parkingId);

    List<SysScenicSpotParkingWithBLOBs> getAllSportParkingByScenicSpotId(Long scenicSpotId, String parkingType);

    List<SysScenicSpotParkingWithBLOBs> getParkingListBy(Long scenicSpotId);

    PageDataResult getParkingCoordList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int addParkingCoord(SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs);

    List<SysScenicSpotParkingWithBLOBs> getStorageRoomListBy(long scenicSpotId);

    List<SysScenicSpotParkingWithBLOBs> uploadScenicSpotCapPriceExcel(Map<String, String> search);

    List<SysParkingExcel> getParkingExcel(Map<String, Object> search);

    SysParkingExcel selScenicSpotParking(SysParkingExcel parking);

    int addParkingExcel(SysParkingExcel parking);

    int upParking(SysParkingExcel parking);

    List<SysScenicSpotParkingWithBLOBs> getScenicSpotParkingAll();

    List<SysScenicSpotParking> parkingDropDown(Long scenicSpotId);

    List<SysScenicSpotParking> getSpotIdParkingList(long spotId);



//    int editParkingCoord(SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs);

}
