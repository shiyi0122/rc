package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.appSystem.model.SysAppRobot;
import com.hna.hka.archive.management.appSystem.model.SysAppRobotOperationTime;
import com.hna.hka.archive.management.assetsSystem.model.OperateState;
import com.hna.hka.archive.management.assetsSystem.model.SysAssetsRobotExcel;
import com.hna.hka.archive.management.system.dto.SysRobotIdDTO;
import com.hna.hka.archive.management.system.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface SysRobotMapper {
    int deleteByPrimaryKey(Long robotId);

    int insert(SysRobot record);

    int insertSelective(SysRobot record);

    SysRobot selectByPrimaryKey(Long robotId);

    int updateByPrimaryKeySelective(SysRobot record);

    int updateByPrimaryKey(SysRobot record);

    /**
     * 查询机器人列表
     * @param search
     * @return
     */
    List<SysRobot> getRobotList(Map<String,Object> search);

    /**
     * 根据机器人编码查询是否有机器人
     * @param robotCode
     * @return
     */
    SysRobot getRobotCodeBy(String robotCode);

    /**
     * 查询SIM卡是否重复
     * @param robotCodeSim
     * @return
     */
    SysRobot getRobotSimById(String robotCodeSim);

    /**
     * EXCEL表下载数据查询
     * @param search
     * @return
     */
    List<SysRobotExcel> getRobotExcel(Map<String, Object> search);

    List<SysRobot> getRobotListBy(@Param("scenicSpotId") Long scenicSpotId,@Param("robotCode") String robotCode);

    List<SysRobot> getAppRoleList(Map<String, Object> search);

	List<SysRobot> getRobotListByScenicSpotId(Long scenicSpotId);

    List<SysRobot> getRobotGpsList(Map<String, Object> search);

    SysRobotGPS getRobotGPSByRobotCode(String robotCode);

    List<SysRobot> getRobotOperatingInformationList(Map<String, Object> search);

    List<SysAssetsRobotExcel> getAssetsRobotExcel(Map<String, Object> search);

    List<SysAppRobot> getAppRobotListVo(Map<String, String> search);

    List<SysAppRobotOperationTime> getAppRobotOperationTime(Map<String, String> search);

    int getRobotState(Map<String, Object> search);

    List<OperateState> getOperateStateList(Map<String, String> search);

    HashMap getOperateStateSpotSum(Long spotId, String beginDate, String endDate);

    List<HashMap> getOperateStateSpotList(Long spotId, String beginDate, String endDate, String field);

    List<HashMap> getOperateStateRobotList(Long spotId, String beginDate, String endDate, Integer type);

    HashMap getOperateStateRobotSum(Long spotId, String beginDate, String endDate);

    int updateSysRobotEquipmentStatus(String robotCode, String equipmentStatus);

    List<SysRobotIdDTO> getRobotIdList();


    Integer getRobotStatisticsState(Integer type);

    List<SysRobot> getRobotListAll(Long spotId);

    Long getSpotIdByRobotCount(Long scenicSpotId);

    List<SysAppRobot> getAppRobotListVoNew(Map<String, String> search);

    int getOperateRobotCount();


    List<SysRobot> getRestrictedAretList(Map<String, Object> search);

    Long getSpotIdByRobotCountFree(String spotId);

    Integer getSpotIdByRobotCountWorking(String spotId);

    Integer getRobotCount();


    List<SysRobot> getSpotIdByRobotList(Long scenicSpotId);

    List<SysRobot> getRobotZGCList(Map<String, Object> search);

    List<SysRobotExcel> getRobotZGCExcel(Map<String, Object> search);

    List<SysRobot> getRunSpotIdRobotList();


    List<SysRobot> getRobotUpgrade(@Param("scenicSpotId") Long scenicSpotId,@Param("robotId") Long robotId);

    int updateRobotUpgrade(@Param("scenicSpotId") Long scenicSpotId, @Param("robotId") Long robotId);

    List<SysRobotAppVersion> getRobotVersionPad(@Param("scenicSpotId") Long scenicSpotId);

    List<SysScenicSpot> timingRobotAuto();
}