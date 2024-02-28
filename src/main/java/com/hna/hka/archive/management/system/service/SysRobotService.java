package com.hna.hka.archive.management.system.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.model.SysAppRobot;
import com.hna.hka.archive.management.appSystem.model.SysAppRobotOperationTime;
import com.hna.hka.archive.management.assetsSystem.model.SysAssetsRobotExcel;
import com.hna.hka.archive.management.system.dto.SysRobotIdDTO;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysRobotService
 * @Author: 郭凯
 * @Description: 机器人管理业务层（接口）
 * @Date: 2020/5/20 9:29
 * @Version: 1.0
 */
public interface SysRobotService {

    /**
     * 查询机器人列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageDataResult getRobotList(Integer pageNum, Integer pageSize, Map<String,Object> search);

    /**
     * 回显状态，数据查询
     * @param robotId
     * @return
     */
    SysRobot getRobotRunState(Long robotId);

    /**
     * 删除机器人
     * @param robotId
     * @return
     */
    int delRobot(Long robotId);

    /**
     * 查询机器人ID
     * @return
     */
    SysRobotId getNewSysRobotId();

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
     * 添加机器人
     * @param robot
     * @return
     */
    int addRobot(SysRobot robot);

    int updateSysRobotId(SysRobotId newSysRobotId);

    /**
     * 修改机器人编码
     * @param robot
     * @return
     */
    int editRobotCode(SysRobot robot);

    /**
     * 修改机器人信息
     * @param sysRobot
     * @return
     */
    int updateByPrimaryKeySelective(SysRobot sysRobot);

    /**
     * 下载EXCEL表
     * @param search
     * @return
     */
    List<SysRobotExcel> getRobotExcel(Map<String, Object> search);

    List<SysRobot> getRobotListBy(Long scenicSpotId, String robotCode);

    List<SysRobot> getAppRobotList(String scenicSpotId, String robotCode);

    void saveModificationLog(SysAppModificationLog modificationLog);

	List<SysRobot> getSysRobotList(Map<String, Object> search);

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询机器人列表
     * @Return
     * @Date 2021/3/24 11:01
     */
    List<SysRobotGPS> getRobotGpsList(Map<String, Object> search);

    int updateSysRobot(SysRobot sysRobot);

    PageDataResult getRobotOperatingInformationList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    List<SysAssetsRobotExcel> getAssetsRobotExcel(Map<String, Object> search);

    PageInfo<SysAppRobot> getAppRobotListVo(BaseQueryVo baseQueryVo);

    PageInfo<SysAppRobotOperationTime> getAppRobotOperationTime(BaseQueryVo baseQueryVo);

    int getRobotState(Map<String, Object> search);

    int  updateSysRobotEquipmentStatus(String robotCode, String equipmentStatus);

    List<SysRobotIdDTO> getRobotIdList();


    List<Map<String, String>> getRobotStatisticsState();


    PageInfo<SysAppRobot> getAppRobotListVoNew(BaseQueryVo baseQueryVo);

    PageDataResult getRestrictedAretList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    Map<String, Object> getBigPidRobotCount(String spotId);


    Map<String,Object> getRobotParkingList(String spotId);

    Map<String, Object> getRobotCount();


    List<SysRobot> getSpotIdByRobotList(String scenicSpotId);


    PageDataResult getRobotZGCList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    List<SysRobotExcel> getRobotZGCExcel(Map<String, Object> search);


    /**
     * 每天晚上提示管理者app机器人充电
     */
    void timingRobotQuantityLog();

    /**
     * 每天晚上同步下后台管理和资产管理中的机器人
     */
    void robotSynchronization();


    List<SysRobot> getRobotUpgrade(Long scenicSpotId,Long robotId);

    int updateRobotUpgrade(Long scenicSpotId, Long robotId);

    List<SysRobotAppVersion> getRobotVersionPad(Long scenicSpotId);
}
