package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysLogWithBLOBs;
import com.hna.hka.archive.management.system.model.SysScenicSpotGpsCoordinateWithBLOBs;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysScenicSpotGpsCoordinateService
 * @Author: 郭凯
 * @Description: 景点电子围栏业务层（接口）
 * @Date: 2020/5/22 11:22
 * @Version: 1.0
 */
public interface SysScenicSpotGpsCoordinateService {

    /**
     * 查询此景区是否已经有电子围栏
     * @param coordinateScenicSpotId
     * @return
     */
    SysScenicSpotGpsCoordinateWithBLOBs selectByPrimaryKey(Long coordinateScenicSpotId);

    /**
     * 添加景区围栏
     * @param sysScenicSpotGpsCoordinateWithBLOBs
     * @return
     */
    int addBtnSubmitScenicSpotGpsCoordinate(SysScenicSpotGpsCoordinateWithBLOBs sysScenicSpotGpsCoordinateWithBLOBs);

    /**
     * 修改景区电子围栏
     * @param sysScenicSpotGpsCoordinateWithBLOBs
     * @return
     */
    int editBtnSubmitScenicSpotGpsCoordinate(SysScenicSpotGpsCoordinateWithBLOBs sysScenicSpotGpsCoordinateWithBLOBs);

    PageDataResult getScenicSpotGpsCoordinateList(Integer pageNum, Integer pageSize, Map<String, String> search);

    void addSysLog(SysLogWithBLOBs sysLog);

    List<SysScenicSpotGpsCoordinateWithBLOBs> getScenicSpotGpsCoordinateAll();

}
