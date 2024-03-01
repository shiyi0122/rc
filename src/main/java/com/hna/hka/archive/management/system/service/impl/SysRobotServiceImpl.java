package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.dao.AppRobotSoftAssetInformationMapper;
import com.hna.hka.archive.management.appSystem.model.AppRobotSoftAssetInformation;
import com.hna.hka.archive.management.appSystem.model.SysAppRobot;
import com.hna.hka.archive.management.appSystem.model.SysAppRobotOperationTime;
import com.hna.hka.archive.management.assetsSystem.model.ChartReturnClass;
import com.hna.hka.archive.management.assetsSystem.model.SysAssetsRobotExcel;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotBelarcAdvisor;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotErrorRecords;
import com.hna.hka.archive.management.managerApp.dao.SysAppUsersSpotRoleMapper;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.system.dao.*;
import com.hna.hka.archive.management.system.dto.SysRobotIdDTO;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.service.SysRobotService;
import com.hna.hka.archive.management.system.util.*;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;
import net.sf.json.JSONObject;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.xml.crypto.Data;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysRobotServiceImpl
 * @Author: 郭凯
 * @Description: 机器人管理业务层（实现层）
 * @Date: 2020/5/20 9:30
 * @Version: 1.0
 */
@Service
@Transactional
public class SysRobotServiceImpl implements SysRobotService {

    @Autowired
    private SysRobotMapper sysRobotMapper;

    @Autowired
    private SysOrderMapper sysOrderMapper;

    @Autowired
    private SysRobotIdMapper sysRobotIdMapper;

    @Autowired
    private SysRobotVersionMapper sysRobotVersionMapper;

    @Autowired
    private SysAppModificationLogMapper sysAppModificationLogMapper;

    @Autowired
    private SysScenicSpotMapper sysScenicSpotMapper;

    @Autowired
    private SysScenicSpotParkingMapper sysScenicSpotParkingMapper;

    @Autowired
    private SysRobotUnusualLogMapper sysRobotUnusualLogMapper;
    @Autowired
    private SysRobotUnusualTimeMapper sysRobotUnusualTimeMapper;
    @Autowired
    private SysAppUsersSpotRoleMapper sysAppUsersSpotRoleMapper;
    @Autowired
    private AppRobotSoftAssetInformationMapper appRobotSoftAssetInformationMapper;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     * @Author 郭凯
     * @Description 查询机器人列表
     * @Date 9:32 2020/5/20
     * @Param [pageNum, pageSize, sysRobot]
     **/
    @Override
    public PageDataResult getRobotList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobot> sysRobotList = sysRobotMapper.getRobotList(search);
        SysRobot sysRobots = new SysRobot();

        for (SysRobot robots : sysRobotList) {
            String robotCode = robots.getRobotCode();
            SysRobot byCode = sysRobotIdMapper.getByCode(robotCode);
            if (byCode == null) {
                robots.setRobotFaultState("10");
                robots.setErrorRecordsAffect("2");
            } else if (byCode.getErrorRecordsAffect().equals("0")) {
                robots.setRobotFaultState("40");
                robots.setErrorRecordsAffect("0");
            } else {
                robots.setRobotFaultState("30");
                robots.setErrorRecordsAffect("1");
            }
            boolean hasKey = redisUtil.exists(robotCode);
            if (hasKey) {
                Object result = redisUtil.get(robotCode);
                JSONObject robot = JSONObject.fromObject(result);
                Object sysRobot = JSONObject.toBean(robot, SysRobot.class);
                JSONObject objJson = JSONObject.fromObject(sysRobot);
                sysRobots = (SysRobot) JSONObject.toBean(objJson, SysRobot.class);
                if (ToolUtil.isNotEmpty(sysRobots.getRobotPowerState())) {
                    robots.setRobotPowerState(sysRobots.getRobotPowerState());
                }
            }
        }


        if (sysRobotList.size() != 0) {
            PageInfo<SysRobot> pageInfo = new PageInfo<>(sysRobotList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @return com.hna.hka.archive.management.system.model.SysRobot
     * @Author 郭凯
     * @Description 回显状态，数据查询
     * @Date 13:54 2020/5/20
     * @Param [robotId]
     **/
    @Override
    public SysRobot getRobotRunState(Long robotId) {
        return sysRobotMapper.selectByPrimaryKey(robotId);
    }

    /**
     * @return int
     * @Author 郭凯
     * @Description 删除机器人
     * @Date 14:58 2020/5/20
     * @Param [robotId]
     **/
    @Override
    public int delRobot(Long robotId) {
        return sysRobotMapper.deleteByPrimaryKey(robotId);
    }

    /**
     * @return com.hna.hka.archive.management.system.model.SysRobotId
     * @Author 郭凯
     * @Description 查询机器人ID
     * @Date 16:47 2020/5/20
     * @Param []
     **/
    @Override
    public SysRobotId getNewSysRobotId() {
        return sysRobotIdMapper.getNewSysRobotId();
    }

    /**
     * 根据机器人编码查询是否有机器人
     *
     * @param robotCode
     * @return
     */
    @Override
    public SysRobot getRobotCodeBy(String robotCode) {
        return sysRobotMapper.getRobotCodeBy(robotCode);
    }

    /**
     * 查询SIM卡是否重复
     *
     * @param robotCodeSim
     * @return
     */
    @Override
    public SysRobot getRobotSimById(String robotCodeSim) {
        return sysRobotMapper.getRobotSimById(robotCodeSim);
    }

    /**
     * @return int
     * @Author 郭凯
     * @Description 添加机器人
     * @Date 17:20 2020/5/20
     * @Param [robot]
     **/
    @Override
    public int addRobot(SysRobot robot) {
        Long robotId = IdUtils.getSeqId();
        robot.setRobotId(robotId);
        robot.setRobotRunState("10");
        robot.setRobotPowerState("10");
        robot.setRobotFaultState("10");
        robot.setRobotType("1");
        robot.setCreateDate(DateUtil.currentDateTime());
        robot.setUpdateDate(DateUtil.currentDateTime());
        SysRobotVersion sysRobotVersion = new SysRobotVersion();
        sysRobotVersion.setRobotVersionId(IdUtils.getSeqId());
        sysRobotVersion.setRobotId(robotId);
        sysRobotVersion.setRobotVersionNumber(robot.getRobotVersionNumber());
        sysRobotVersion.setCreateDate(DateUtil.currentDateTime());
        sysRobotVersion.setUpdateDate(DateUtil.currentDateTime());
        int b = sysRobotMapper.insertSelective(robot);
        sysRobotVersionMapper.insertSelective(sysRobotVersion);
        return b;
    }

    /**
     * @return int
     * @Author 郭凯
     * @Description 修改机器人编号
     * @Date 17:33 2020/5/20
     * @Param [newSysRobotId]
     **/
    @Override
    public int updateSysRobotId(SysRobotId newSysRobotId) {
        newSysRobotId.setStatus("1");
        return sysRobotIdMapper.updateByPrimaryKeySelective(newSysRobotId);
    }

    /**
     * @return int
     * @Author 郭凯
     * @Description 修改机器人编码
     * @Date 17:37 2020/5/20
     * @Param [robot]
     **/
    @Override
    public int editRobotCode(SysRobot robot) {
        return sysRobotMapper.updateByPrimaryKeySelective(robot);
    }

    /**
     * 修改机器人信息
     *
     * @param sysRobot
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(SysRobot sysRobot) {
        //根据机器人ID查询机器人版本号
        SysRobotVersion robotVersionByRobotId = sysRobotVersionMapper.getSysRobotVersionByRobotId(sysRobot.getRobotId());
        if (ToolUtil.isEmpty(robotVersionByRobotId)) {
            SysRobotVersion robotVersion = new SysRobotVersion();
            robotVersion.setRobotVersionId(IdUtils.getSeqId());
            robotVersion.setRobotId(sysRobot.getRobotId());
            robotVersion.setRobotVersionNumber(sysRobot.getRobotVersionNumber());
            robotVersion.setCreateDate(DateUtil.currentDateTime());
            robotVersion.setUpdateDate(DateUtil.currentDateTime());
            sysRobotVersionMapper.insertSelective(robotVersion);
        } else {
            SysRobotVersion robotVersion = new SysRobotVersion();
            robotVersion.setRobotVersionId(robotVersionByRobotId.getRobotVersionId());
            robotVersion.setRobotVersionNumber(sysRobot.getRobotVersionNumber());
            robotVersion.setUpdateDate(DateUtil.currentDateTime());
            sysRobotVersionMapper.updateByPrimaryKeySelective(robotVersion);
        }
        return sysRobotMapper.updateByPrimaryKeySelective(sysRobot);
    }

    /**
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysRobotExcel>
     * @Author 郭凯
     * @Description EXCEL下载数据查询
     * @Date 13:40 2020/5/25
     * @Param [search]
     **/
    @Override
    public List<SysRobotExcel> getRobotExcel(Map<String, Object> search) {
        List<SysRobotExcel> robotExcel = sysRobotMapper.getRobotExcel(search);
        for (SysRobotExcel sysRobotExcel : robotExcel) {
            if ("10".equals(sysRobotExcel.getRobotRunState())) {
                sysRobotExcel.setRobotRunState("闲置");
            } else if ("20".equals(sysRobotExcel.getRobotRunState())) {
                sysRobotExcel.setRobotRunState("用户解锁");
            } else if ("30".equals(sysRobotExcel.getRobotRunState())) {
                sysRobotExcel.setRobotRunState("用户临时锁定");
            } else if ("40".equals(sysRobotExcel.getRobotRunState())) {
                sysRobotExcel.setRobotRunState("管理员解锁");
            } else if ("50".equals(sysRobotExcel.getRobotRunState())) {
                sysRobotExcel.setRobotRunState("管理员锁定");
            } else if ("60".equals(sysRobotExcel.getRobotRunState())) {
                sysRobotExcel.setRobotRunState("自检故障报警");
            } else if ("80".equals(sysRobotExcel.getRobotRunState())) {
                sysRobotExcel.setRobotRunState("运营人员钥匙解锁");
            } else if ("90".equals(sysRobotExcel.getRobotRunState())) {
                sysRobotExcel.setRobotRunState("运营人员维护");
            } else if ("100".equals(sysRobotExcel.getRobotRunState())) {
                sysRobotExcel.setRobotRunState("禁区锁定");
            } else if ("70".equals(sysRobotExcel.getRobotRunState())) {
                sysRobotExcel.setRobotRunState("扫码解锁中");
            } else if ("90".equals(sysRobotExcel.getRobotRunState())) {
                sysRobotExcel.setRobotRunState("维修");
            }

        }

        return sysRobotMapper.getRobotExcel(search);
    }

    /**
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysRobot>
     * @Author 郭凯
     * @Description 机器人坐标查询接口
     * @Date 14:49 2020/7/24
     * @Param [scenicSpotId, robotCode]
     **/
    @Override
    public List<SysRobot> getRobotListBy(Long scenicSpotId, String robotCodes) {
        Map<String, Object> search = new HashMap<>();
        search.put("scenicSpotId", scenicSpotId);
        search.put("robotCode", robotCodes);
        List<SysRobot> robotList = sysRobotMapper.getRobotGpsList(search);
        SysRobotGPS sysRobotGPS = new SysRobotGPS();
        for (SysRobot robots : robotList) {
            String robotCode = robots.getRobotCode();
            boolean hasKey = redisUtil.exists(robotCode);
            if (hasKey) {
                Object result = redisUtil.get(robotCode);
                JSONObject robot = JSONObject.fromObject(result);
                Object sysRobot = JSONObject.toBean(robot, SysRobotGPS.class);
                JSONObject objJson = JSONObject.fromObject(sysRobot);
                sysRobotGPS = (SysRobotGPS) JSONObject.toBean(objJson, SysRobotGPS.class);
                if (ToolUtil.isNotEmpty(sysRobotGPS)) {
                    robots.setRobotGpsBaiDu(sysRobotGPS.getRobotGpsBaiDu());
                }
            }
        }
        return robotList;
    }

    /**
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysRobot>
     * @Author 郭凯
     * @Description 管理者APP查询机器人列表
     * @Date 17:34 2020/11/23
     * @Param [scenicSpotId, robotCode]
     **/
    @Override
    public List<SysRobot> getAppRobotList(String scenicSpotId, String robotCode) {
        Map<String, Object> search = new HashMap<>();
        search.put("scenicSpotId", scenicSpotId);
        search.put("robotCode", robotCode);
        List<SysRobot> sysRobotList = sysRobotMapper.getAppRoleList(search);
        return sysRobotList;
    }

    /**
     * @return void
     * @Author 郭凯
     * @Description 添加APP状态修改日志
     * @Date 17:47 2020/11/23
     * @Param [modificationLog]
     **/
    @Override
    public void saveModificationLog(SysAppModificationLog modificationLog) {
        sysAppModificationLogMapper.insertSelective(modificationLog);
    }

    /**
     * @param @param  search
     * @param @return
     * @throws
     * @Author 郭凯
     * @Description: 根据用户信息查询用户所有机器
     * @Title: getSysRobotList
     * @date 2021年1月14日 上午10:02:02
     */
    @Override
    public List<SysRobot> getSysRobotList(Map<String, Object> search) {
        // TODO Auto-generated method stub
        return sysRobotMapper.getRobotList(search);
    }

    /**
     * @Method getRobotGpsList
     * @Author 郭凯
     * @Version 1.0
     * @Description 根据机器人编号查询机器人坐标
     * @Return java.util.List<com.hna.hka.archive.management.system.model.SysRobot>
     * @Date 2021/3/24 11:01
     */
    @Override
    public List<SysRobotGPS> getRobotGpsList(Map<String, Object> search) {
        List<SysRobot> robotList = sysRobotMapper.getRobotGpsList(search);
        if (robotList.size() == 0) {
            return new ArrayList<SysRobotGPS>();
        }
        SysRobot sysRobot1 = robotList.get(robotList.size() - 1);
        SysRobot byCode = sysRobotIdMapper.getByCode(sysRobot1.getRobotCode());
        List<SysRobotGPS> robotLists = new ArrayList<SysRobotGPS>();
        SysRobotGPS sysRobotGPS = new SysRobotGPS();
        List<SysOrder> sysOrderList = new ArrayList<>();
        SysOrder sysOrder = new SysOrder();
        SysRobotUnusualLog sysRobotUnusualLog = new SysRobotUnusualLog();
        try {

            for (SysRobot robots : robotList) {
                String robotCode = robots.getRobotCode();
                boolean hasKey = redisUtil.exists(robotCode);
                if (hasKey) {
                    Object result = redisUtil.get(robotCode);
                    JSONObject robot = JSONObject.fromObject(result);
                    Object sysRobot = JSONObject.toBean(robot, SysRobotGPS.class);
                    JSONObject objJson = JSONObject.fromObject(sysRobot);
                    sysRobotGPS = (SysRobotGPS) JSONObject.toBean(objJson, SysRobotGPS.class);

                } else {
                    SysRobotGPS robotGPS = sysRobotMapper.getRobotGPSByRobotCode(robotCode);
                    sysRobotGPS = robotGPS;
                }
                if (ToolUtil.isNotEmpty(sysRobotGPS)) {
//                sysRobotUnusualLog =  sysRobotUnusualLogMapper.getRobotCodeByUnusualLogN(robotCode);
                    if (!StringUtils.isEmpty(search.get("unusualId"))) {
                        sysRobotUnusualLog = sysRobotUnusualLogMapper.selectById(search.get("unusualId").toString());
                        if (!StringUtils.isEmpty(sysRobotUnusualLog)) {
                            sysRobotGPS.setStatus(sysRobotUnusualLog.getStatus());
                            sysRobotGPS.setUnusualType(sysRobotUnusualLog.getUnusualType());
                            sysRobotGPS.setUnusualId(sysRobotUnusualLog.getId());
                            sysRobotGPS.setUnusualDate(sysRobotUnusualLog.getCreateDate());
                            sysRobotGPS.setPropellingContent(sysRobotUnusualLog.getPropellingContent());
                        }
                    }
                    sysOrderList = sysOrderMapper.getRobotCodeByOneOrder(sysRobotGPS.getRobotCode());
                    if (sysOrderList.size() > 0) {
                        sysOrder = sysOrderList.get(0);
                        if (!StringUtils.isEmpty(sysOrder)) {
                            if (!StringUtils.isEmpty(sysOrder.getRunningTrack())) {
                                sysRobotGPS.setRobotUpGps(sysOrder.getRunningTrack().split("!")[sysOrder.getRunningTrack().split("!").length - 1]);
                            }
                        }
                    }
                    SysRobotUnusualTime scenicSpotIdTime = sysRobotUnusualTimeMapper.getSpotIdByUnusualTime(robots.getScenicSpotId());
                    if (!StringUtils.isEmpty(scenicSpotIdTime) && !StringUtils.isEmpty(sysRobotGPS.getUpdateDate())) {

                        if (!StringUtils.isEmpty(scenicSpotIdTime.getOnOffStatus())) {

                            if (DateUtil.timeConversion(sysRobotGPS.getUpdateDate(), DateUtil.currentDateTime()) > Long.parseLong(scenicSpotIdTime.getOnOffStatus())) {
                                sysRobotGPS.setOnOffStatus("0");
                            } else {
                                sysRobotGPS.setOnOffStatus("1");
                            }

                        } else {
                            sysRobotGPS.setOnOffStatus("1");
                        }
                    } else {
                        sysRobotGPS.setOnOffStatus("0");
                    }
                    sysRobotGPS.setRobotRunState(robots.getRobotRunState());
                    sysRobotGPS.setClientVersion(robots.getClientVersion());
                    sysRobotGPS.setRobotRemarks(robots.getRobotRemarks());
                    sysRobotGPS.setScenicSpotName(robots.getScenicSpotName());
                    if (byCode == null) {
                        sysRobotGPS.setErrorRecordsAffect("2");
                    } else {
                        sysRobotGPS.setErrorRecordsAffect(byCode.getErrorRecordsAffect());
                    }
                    robotLists.add(sysRobotGPS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return robotLists;
    }

    /**
     * @Method updateSysRobot
     * @Author 郭凯
     * @Version 1.0
     * @Description 修改机器人信息
     * @Return int
     * @Date 2021/5/24 16:11
     */
    @Override
    public int updateSysRobot(SysRobot sysRobot) {
        return sysRobotMapper.updateByPrimaryKeySelective(sysRobot);
    }

    /**
     * @Method getRobotOperatingInformationList
     * @Author 郭凯
     * @Version 1.0
     * @Description 机器人运营信息列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/5/26 15:21
     */
    @Override
    public PageDataResult getRobotOperatingInformationList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobot> sysRobotList = sysRobotMapper.getRobotOperatingInformationList(search);
        SysRobot sysRobots = new SysRobot();
        for (SysRobot robots : sysRobotList) {
            String robotCode = robots.getRobotCode();
            boolean hasKey = redisUtil.exists(robotCode);
            if (hasKey) {
                Object result = redisUtil.get(robotCode);
                JSONObject robot = JSONObject.fromObject(result);
                Object sysRobot = JSONObject.toBean(robot, SysRobot.class);
                JSONObject objJson = JSONObject.fromObject(sysRobot);
                sysRobots = (SysRobot) JSONObject.toBean(objJson, SysRobot.class);
                if (ToolUtil.isNotEmpty(sysRobots.getRobotPowerState())) {
                    robots.setRobotPowerState(sysRobots.getRobotPowerState());
                }
            }
        }
        if (sysRobotList.size() != 0) {
            PageInfo<SysRobot> pageInfo = new PageInfo<>(sysRobotList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method getAssetsRobotExcel
     * @Author 郭凯
     * @Version 1.0
     * @Description 查询机器人运营信息导出Excel表信息
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.SysAssetsRobotExcel>
     * @Date 2021/5/27 14:40
     */
    @Override
    public List<SysAssetsRobotExcel> getAssetsRobotExcel(Map<String, Object> search) {
        List<SysAssetsRobotExcel> assetsRobotExcelList = sysRobotMapper.getAssetsRobotExcel(search);
        for (SysAssetsRobotExcel sysAssetsRobotExcel : assetsRobotExcelList) {
            sysAssetsRobotExcel.setRobotRunStateName(DictUtils.getRobotRunMap().get(sysAssetsRobotExcel.getRobotRunState()));
            sysAssetsRobotExcel.setRobotFaultStateName(DictUtils.getRobotFaultStateMap().get(sysAssetsRobotExcel.getRobotFaultState()));
        }
        return assetsRobotExcelList;
    }

    /**
     * @Method getAppRobotListVo
     * @Author 郭凯
     * @Version 1.0
     * @Description 管理者APP机器人管理列表查询
     * @Return com.github.pagehelper.PageInfo<com.hna.hka.archive.management.appSystem.model.SysAppRobot>
     * @Date 2021/6/8 13:55
     */
    @Override
    public PageInfo<SysAppRobot> getAppRobotListVo(BaseQueryVo baseQueryVo) {
        PageHelper.startPage(baseQueryVo.getPageNum(), baseQueryVo.getPageSize());
        List<SysAppRobot> sysRobotList = sysRobotMapper.getAppRobotListVo(baseQueryVo.getSearch());
        for (SysAppRobot appRobot : sysRobotList) {
            boolean hasKey = redisUtil.exists(appRobot.getRobotCode());
            if (hasKey) {
                Object result = redisUtil.get(appRobot.getRobotCode());
                JSONObject robot = JSONObject.fromObject(result);
                Object sysRobot = JSONObject.toBean(robot, SysRobot.class);
                JSONObject objJson = JSONObject.fromObject(sysRobot);
                SysRobot sysRobots = (SysRobot) JSONObject.toBean(objJson, SysRobot.class);
                if (ToolUtil.isNotEmpty(sysRobots)) {
                    appRobot.setRobotGpsGpgga(sysRobots.getRobotGpsGpgga());
                    appRobot.setRobotPowerState(sysRobots.getRobotPowerState());
                    appRobot.setRobotGpsSmallApp(sysRobots.getRobotGpsSmallApp());
                    appRobot.setRobotGpsBaiDu(sysRobots.getRobotGpsBaiDu());
                }
            }
        }
        PageInfo<SysAppRobot> page = new PageInfo<>(sysRobotList);
        return page;
    }

    /**
     * @Method getAppRobotAccessoriesManagement
     * @Author 郭凯
     * @Version 1.0
     * @Description 机器人运营时长列表查询(APP接口)
     * @Return com.github.pagehelper.PageInfo<com.hna.hka.archive.management.appSystem.model.SysAppRobotAccessoriesManagement>
     * @Date 2021/6/10 16:02
     */
    @Override
    public PageInfo<SysAppRobotOperationTime> getAppRobotOperationTime(BaseQueryVo baseQueryVo) {
        PageHelper.startPage(baseQueryVo.getPageNum(), baseQueryVo.getPageSize());
        List<SysAppRobotOperationTime> appRobotOperationTime = sysRobotMapper.getAppRobotOperationTime(baseQueryVo.getSearch());
        PageInfo<SysAppRobotOperationTime> page = new PageInfo<>(appRobotOperationTime);
        return page;
    }

    /**
     * @Method getRobotState
     * @Author 郭凯
     * @Version 1.0
     * @Description 查询机器信息（根据状态）
     * @Return int
     * @Date 2021/7/9 15:27
     */
    @Override
    public int getRobotState(Map<String, Object> search) {
        int i = sysRobotMapper.getRobotState(search);
        return ToolUtil.isEmpty(i) ? 0 : i;
    }


    /**
     * 此接口是机器人软资产批量导入时使用到
     *
     * @param robotCode
     * @param equipmentStatus
     * @return
     */
    @Override
    public int updateSysRobotEquipmentStatus(String robotCode, String equipmentStatus) {

        int i = sysRobotMapper.updateSysRobotEquipmentStatus(robotCode, equipmentStatus);
        return i;
    }

    //获取所有机器人id和编号
    @Override
    public List<SysRobotIdDTO> getRobotIdList() {

        return sysRobotMapper.getRobotIdList();
    }

    /**
     * 饼图统计设备状态
     */
    @Override
    public List<Map<String, String>> getRobotStatisticsState() {
        List<Map<String, String>> listM = new ArrayList<>();
        Integer number = sysRobotMapper.getRobotStatisticsState(10);
        Integer number1 = sysRobotMapper.getRobotStatisticsState(20);
        Integer number2 = sysRobotMapper.getRobotStatisticsState(30);
        Integer number3 = sysRobotMapper.getRobotStatisticsState(40);

        Map<String, String> map = new HashMap<>();
        for (int i = 1; i <= 4; i++) {
            map = new HashMap<>();
            if (i == 1) {
                map.put("value", number.toString());
                map.put("name", "在产");
            } else if (i == 2) {
                map.put("value", number1.toString());
                map.put("name", "库存");
            } else if (i == 3) {
                map.put("value", number2.toString());
                map.put("name", "运营");
            } else {
                map.put("value", number3.toString());
                map.put("name", "报废");
            }

            listM.add(map);
        }

        return listM;
    }

    /**
     * 获取机器人列表
     * zhang
     *
     * @param baseQueryVo
     * @return
     */
    @Override
    public PageInfo<SysAppRobot> getAppRobotListVoNew(BaseQueryVo baseQueryVo) {


        PageHelper.startPage(baseQueryVo.getPageNum(), baseQueryVo.getPageSize());
        List<SysAppRobot> sysRobotList = sysRobotMapper.getAppRobotListVoNew(baseQueryVo.getSearch());

        SysRobotUnusualTime scenicSpotIdTime = sysRobotUnusualTimeMapper.getSpotIdByUnusualTime(Long.parseLong(baseQueryVo.getSearch().get("scenicSpotId")));

        try {

            for (SysAppRobot appRobot : sysRobotList) {
                boolean hasKey = redisUtil.exists(appRobot.getRobotCode());
                if (hasKey) {
                    Object result = redisUtil.get(appRobot.getRobotCode());
                    JSONObject robot = JSONObject.fromObject(result);
                    Object sysRobot = JSONObject.toBean(robot, SysRobot.class);
                    JSONObject objJson = JSONObject.fromObject(sysRobot);
                    SysRobot sysRobots = (SysRobot) JSONObject.toBean(objJson, SysRobot.class);
                    if (ToolUtil.isNotEmpty(sysRobots)) {
                        appRobot.setRobotGpsGpgga(sysRobots.getRobotGpsGpgga());
                        appRobot.setRobotPowerState(sysRobots.getRobotPowerState());
                        appRobot.setRobotGpsSmallApp(sysRobots.getRobotGpsSmallApp());
                        appRobot.setRobotGpsBaiDu(sysRobots.getRobotGpsBaiDu());
                        appRobot.setUpdateDate(sysRobots.getUpdateDate());
                    }
                }
                if (!StringUtils.isEmpty(scenicSpotIdTime) && !StringUtils.isEmpty(appRobot.getUpdateDate())) {

                    if (!StringUtils.isEmpty(scenicSpotIdTime.getOnOffStatus())) {
                        long l = DateUtil.timeConversion(appRobot.getUpdateDate(), DateUtil.currentDateTime());

                        if (DateUtil.timeConversion(appRobot.getUpdateDate(), DateUtil.currentDateTime()) > Long.parseLong(scenicSpotIdTime.getOnOffStatus())) {
                            appRobot.setOnOffStatus("0");
                        } else {
                            appRobot.setOnOffStatus("1");
                        }

                    } else {
                        appRobot.setOnOffStatus("1");
                    }

                } else {
                    appRobot.setOnOffStatus("0");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> search = baseQueryVo.getSearch();
        String sort = search.get("sort");
        String sortField = search.get("sortField");
        if (sortField.equals("1")) {
            if (sort.equals("1")) {
                Collections.sort(sysRobotList, new Comparator<SysAppRobot>() {
                    @Override
                    public int compare(SysAppRobot o1, SysAppRobot o2) {
                        //升序排序，降序反写
                        return Integer.parseInt(o1.getRobotPowerState()) - Integer.parseInt(o2.getRobotPowerState());
                    }
                });
            } else {
                Collections.sort(sysRobotList, new Comparator<SysAppRobot>() {
                    @Override
                    public int compare(SysAppRobot o1, SysAppRobot o2) {
                        //升序排序，降序反写
                        return Integer.parseInt(o2.getRobotPowerState()) - Integer.parseInt(o1.getRobotPowerState());
                    }
                });
            }
        }


        PageInfo<SysAppRobot> page = new PageInfo<>(sysRobotList);
        return page;

    }


    /**
     * 异常机器人列表订单
     *
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getRestrictedAretList(Integer pageNum, Integer pageSize, Map<String, Object> search) {


//        PageHelper.startPage(pageNum, pageSize);

        PageDataResult pageDataResult = new PageDataResult();
        List<SysRobot> robotList = new ArrayList<>();
        List<SysRobot> list = sysRobotMapper.getRestrictedAretList(search);

        SysRobotGPS sysRobotGPS = new SysRobotGPS();


        if (list.size() > 0 && !StringUtils.isEmpty(list)) {
            String s = DateUtil.currentDateTime();
            String date = s.substring(0, 10);
            for (SysRobot sysRobot : list) {

                SysOrder sysOrder = sysOrderMapper.getCurrentDateOrder(sysRobot.getRobotCode(), date);

                if (!StringUtils.isEmpty(sysOrder)) {

                    if ("10".equals(sysOrder.getOrderStatus())) {
                        continue;
                    } else {

                        robotList.add(sysRobot);
                    }

                }
            }
        }

        List<SysRobot> page = this.page(robotList, pageSize, pageNum);

        for (SysRobot robots : page) {

            String robotCode = robots.getRobotCode();
            boolean hasKey = redisUtil.exists(robotCode);
            if (hasKey) {
                Object result = redisUtil.get(robotCode);
                JSONObject robot = JSONObject.fromObject(result);
                Object sysRobot = JSONObject.toBean(robot, SysRobotGPS.class);
                JSONObject objJson = JSONObject.fromObject(sysRobot);
                sysRobotGPS = (SysRobotGPS) JSONObject.toBean(objJson, SysRobotGPS.class);
            }
            if (ToolUtil.isNotEmpty(sysRobotGPS)) {

                robots.setRobotGpsBaiDu(sysRobotGPS.getRobotGpsBaiDu());
                robots.setRobotGpsGpgga(sysRobotGPS.getRobotGpsGpgga());
                robots.setRobotGpsSmallApp(sysRobotGPS.getRobotGpsSmallApp());
                robots.setRobotPowerState(sysRobotGPS.getRobotPowerState());

//                sysRobotGPS.setRobotRunState(robots.getRobotRunState());
//                sysRobotGPS.setClientVersion(robots.getClientVersion());
//                sysRobotGPS.setRobotRemarks(robots.getRobotRemarks());
//                sysRobotGPS.setScenicSpotName(robots.getScenicSpotName());

            }

        }

        pageDataResult.setList(page);
        pageDataResult.setTotals(robotList.size());

        return pageDataResult;
    }

    //大屏获取机器人数量
    @Override
    public Map<String, Object> getBigPidRobotCount(String spotId) {

        Map<String, Object> map = new HashMap<>();
        Long spotIdByRobotCount = sysRobotMapper.getSpotIdByRobotCount(Long.parseLong(spotId));
        Long freeRobotCount = sysRobotMapper.getSpotIdByRobotCountFree(spotId);
        if (!StringUtils.isEmpty(spotIdByRobotCount)) {
            map.put("robotCount", spotIdByRobotCount);
        } else {
            map.put("robotCount", 0);
        }

        if (!StringUtils.isEmpty(freeRobotCount)) {
            map.put("freeRobotCount", freeRobotCount);
        } else {
            map.put("freeRobotCount", 0);
        }
        map.put("code", "200");

        return map;
    }

    /**
     * 管理者app获取停放点机器人列表
     *
     * @param spotId
     * @return
     */
    @Override
    public Map<String, Object> getRobotParkingList(String spotId) {

        Map<String, Object> map = new HashMap<>();
        List<SysRobot> robotParkingList = new ArrayList<>();
        List<SysRobot> robotStorageRoomList = new ArrayList<>();
        List<SysRobot> robotUseList = new ArrayList<>();

        SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByScenicSpotId(Long.parseLong(spotId));

        //停放点
        List<SysScenicSpotParkingWithBLOBs> parkingList = sysScenicSpotParkingMapper.getParkingListByScenicSpotId(Long.parseLong(spotId));
        //库房
        List<SysScenicSpotParkingWithBLOBs> storageRoomList = sysScenicSpotParkingMapper.getStorageRoomListBy(Long.parseLong(spotId));

        //景区机器人列表
        List<SysRobot> robotList = sysRobotMapper.getRobotListByScenicSpotId(Long.parseLong(spotId));

        SysRobot sysRobots = new SysRobot();
        Point n1 = null;
        boolean flag = false;
        for (SysRobot sysRobot : robotList) {
            flag = false;
            boolean hasKey = redisUtil.exists(sysRobot.getRobotCode());
            if (hasKey) {
                Object result = redisUtil.get(sysRobot.getRobotCode());
                JSONObject robot = JSONObject.fromObject(result);
                Object sysRobotN = JSONObject.toBean(robot, SysRobot.class);
                JSONObject objJson = JSONObject.fromObject(sysRobot);
                sysRobots = (SysRobot) JSONObject.toBean(objJson, SysRobot.class);
                String[] split = sysRobots.getRobotGpsGpgga().split(",");
                n1 = new Point(Double.valueOf(split[0]), Double.valueOf(split[1]));//
                for (SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs : parkingList) {
                    String[] parkingWith = sysScenicSpotParkingWithBLOBs.getParkingCoordinateGroup().split("!");
                    if (parkingWith != null && parkingWith.length > 0) {
                        Point[] ps = new Point[parkingWith.length];
                        for (int i = 0; i < parkingWith.length; i++) {
                            String[] str = parkingWith[i].split(",");
                            ps[i] = new Point(Double.valueOf(str[0]), Double.valueOf(str[1]));
                        }
                        flag = JudgingCoordinates.isPtInPoly(n1.getX(), n1.getY(), ps);
                        if (flag) {
                            sysRobot.setRobotGpsGpgga(sysRobots.getRobotGpsGpgga());
                            sysRobot.setRobotGpsBaiDu(sysRobots.getRobotGpsBaiDu());
                            sysRobot.setRobotGpsSmallApp(sysRobots.getRobotGpsSmallApp());
                            robotParkingList.add(sysRobot);
                            break;
                        }
                    }
                }
                if (flag == false) {
                    for (SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs : storageRoomList) {
                        String[] parkingWith = sysScenicSpotParkingWithBLOBs.getParkingCoordinateGroup().split("!");
                        if (parkingWith != null && parkingWith.length > 0) {
                            Point[] ps = new Point[parkingWith.length];
                            for (int i = 0; i < parkingWith.length; i++) {
                                String[] str = parkingWith[i].split(",");
                                ps[i] = new Point(Double.valueOf(str[0]), Double.valueOf(str[1]));
                            }
                            flag = JudgingCoordinates.isPtInPoly(n1.getX(), n1.getY(), ps);
                            if (flag) {
                                sysRobot.setRobotGpsGpgga(sysRobots.getRobotGpsGpgga());
                                sysRobot.setRobotGpsBaiDu(sysRobots.getRobotGpsBaiDu());
                                sysRobot.setRobotGpsSmallApp(sysRobots.getRobotGpsSmallApp());
                                robotStorageRoomList.add(sysRobot);
                                break;
                            }
                        }
                    }
                }

                if (flag == false) {
                    sysRobot.setRobotGpsGpgga(sysRobots.getRobotGpsGpgga());
                    sysRobot.setRobotGpsBaiDu(sysRobots.getRobotGpsBaiDu());
                    sysRobot.setRobotGpsSmallApp(sysRobots.getRobotGpsSmallApp());
                    robotUseList.add(sysRobot);
                }
            }

        }

        map.put("Parking", robotParkingList);
        map.put("StorageRoom", robotStorageRoomList);
        map.put("Use", robotUseList);

        return map;

    }

    @Override
    public Map<String, Object> getRobotCount() {
        Map<String, Object> map = new HashMap<>();

        Integer count = sysRobotMapper.getRobotCount();

        map.put("robotCount", count);
        map.put("code", "200");

        return map;

    }

    /**
     * 根据景区id，获取机器人列表
     *
     * @param scenicSpotId
     * @return
     */
    @Override
    public List<SysRobot> getSpotIdByRobotList(String scenicSpotId) {

        List<SysRobot> robotListByScenicSpotId = sysRobotMapper.getSpotIdByRobotList(Long.parseLong(scenicSpotId));
        return robotListByScenicSpotId;
    }


    @Override
    public PageDataResult getRobotZGCList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobot> sysRobotList = sysRobotMapper.getRobotZGCList(search);
        SysRobot sysRobots = new SysRobot();

        for (SysRobot robots : sysRobotList) {
            String robotCode = robots.getRobotCode();
            boolean hasKey = redisUtil.exists(robotCode);
            if (hasKey) {
                Object result = redisUtil.get(robotCode);
                JSONObject robot = JSONObject.fromObject(result);
                Object sysRobot = JSONObject.toBean(robot, SysRobot.class);
                JSONObject objJson = JSONObject.fromObject(sysRobot);
                sysRobots = (SysRobot) JSONObject.toBean(objJson, SysRobot.class);
                if (ToolUtil.isNotEmpty(sysRobots.getRobotPowerState())) {
                    robots.setRobotPowerState(sysRobots.getRobotPowerState());
                }
            }
        }


        if (sysRobotList.size() != 0) {
            PageInfo<SysRobot> pageInfo = new PageInfo<>(sysRobotList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;


    }

    /**
     * 中关村租赁机器人表下载
     *
     * @param search
     * @return
     */
    @Override
    public List<SysRobotExcel> getRobotZGCExcel(Map<String, Object> search) {

        List<SysRobotExcel> robotExcel = sysRobotMapper.getRobotZGCExcel(search);
        for (SysRobotExcel sysRobotExcel : robotExcel) {
            if ("10".equals(sysRobotExcel.getRobotRunState())) {
                sysRobotExcel.setRobotRunState("闲置");
            } else if ("20".equals(sysRobotExcel.getRobotRunState())) {
                sysRobotExcel.setRobotRunState("用户解锁");
            } else if ("30".equals(sysRobotExcel.getRobotRunState())) {
                sysRobotExcel.setRobotRunState("用户临时锁定");
            } else if ("40".equals(sysRobotExcel.getRobotRunState())) {
                sysRobotExcel.setRobotRunState("管理员解锁");
            } else if ("50".equals(sysRobotExcel.getRobotRunState())) {
                sysRobotExcel.setRobotRunState("管理员锁定");
            } else if ("60".equals(sysRobotExcel.getRobotRunState())) {
                sysRobotExcel.setRobotRunState("自检故障报警");
            } else if ("80".equals(sysRobotExcel.getRobotRunState())) {
                sysRobotExcel.setRobotRunState("运营人员钥匙解锁");
            } else if ("90".equals(sysRobotExcel.getRobotRunState())) {
                sysRobotExcel.setRobotRunState("运营人员维护");
            } else if ("100".equals(sysRobotExcel.getRobotRunState())) {
                sysRobotExcel.setRobotRunState("禁区锁定");
            } else if ("70".equals(sysRobotExcel.getRobotRunState())) {
                sysRobotExcel.setRobotRunState("扫码解锁中");
            } else if ("90".equals(sysRobotExcel.getRobotRunState())) {
                sysRobotExcel.setRobotRunState("维修");
            }

        }

        return sysRobotMapper.getRobotZGCExcel(search);

    }

    /**
     * 每天晚上提示管理者app机器人充电
     */
    @Override
    public void timingRobotQuantityLog() {

        SysRobot sysRobots = new SysRobot();

        List<SysRobotUnusualTime> sysRobotUnusualTimeList = sysRobotUnusualTimeMapper.getSysRobotUnusualTimeList(null);

        List<SysAppUsers> sysAppUsers = sysAppUsersSpotRoleMapper.getScenicSpotTypeByUser();

        List<Long> unusualTimeList = sysRobotUnusualTimeList.stream().map(SysRobotUnusualTime::getSysScenicSpotId).collect(Collectors.toList());

        List<SysScenicSpot> scenicSpotListNew = sysScenicSpotMapper.getScenicSpotListNew(null);

        try {

            for (SysScenicSpot sysScenicSpot : scenicSpotListNew) {

                boolean contains = unusualTimeList.contains(sysScenicSpot.getScenicSpotId());
                int i = unusualTimeList.indexOf(sysScenicSpot.getScenicSpotId());
                if (!StringUtils.isEmpty(sysRobotUnusualTimeList.get(i).getBatteryReminder())) {

                    List<SysRobot> robotListAll = sysRobotMapper.getRobotListAll(sysScenicSpot.getScenicSpotId());

                    for (SysRobot robots : robotListAll) {

                        String robotCode = robots.getRobotCode();
                        boolean hasKey = redisUtil.exists(robotCode);
                        if (hasKey) {
                            Object result = redisUtil.get(robotCode);
                            JSONObject robot = JSONObject.fromObject(result);
                            Object sysRobot = JSONObject.toBean(robot, SysRobot.class);
                            JSONObject objJson = JSONObject.fromObject(sysRobot);
                            sysRobots = (SysRobot) JSONObject.toBean(objJson, SysRobot.class);

                            if (Long.parseLong(sysRobots.getRobotPowerState()) <= Long.parseLong(sysRobotUnusualTimeList.get(i).getBatteryReminder())) {


                                if (!StringUtils.isEmpty(sysAppUsers)) {
                                    for (SysAppUsers sysAppUser : sysAppUsers) {
                                        String isSuccess = WeChatGtRobotAppPush.singlePushApp(sysAppUser.getUserClientGtId(), "机器人充电提醒", sysScenicSpot.getScenicSpotName() + "，" + robots.getRobotCode() + "，" + "此机器人当前电量已低于后台规定电量,请充电处理!");
//                                    System.out.println(isSuccess);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 每天晚上同步下后台管理和资产管理中的机器人
     */
    @Override
    public void robotSynchronization() {

        AppRobotSoftAssetInformation appRobotSoftAssetInformation = new AppRobotSoftAssetInformation();

        List<SysRobot> robotList = sysRobotMapper.getRobotList(null);
        for (SysRobot robot : robotList) {
            appRobotSoftAssetInformation = appRobotSoftAssetInformationMapper.getRobotCodeByDetails(robot.getRobotCode());

            if (StringUtils.isEmpty(appRobotSoftAssetInformation)) {
                appRobotSoftAssetInformation = new AppRobotSoftAssetInformation();
                appRobotSoftAssetInformation.setSoftAssetInformationId(IdUtils.getSeqId());
                appRobotSoftAssetInformation.setRobotId(robot.getRobotId());
                appRobotSoftAssetInformation.setRobotCode(robot.getRobotCode());
                appRobotSoftAssetInformation.setCreateDate(DateUtil.currentDateTime());
                appRobotSoftAssetInformation.setUpdateDate(DateUtil.currentDateTime());
                appRobotSoftAssetInformationMapper.insertSelective(appRobotSoftAssetInformation);

            } else {
                continue;
            }

        }


    }

    @Override
    public List<SysRobot> getRobotUpgrade(Long scenicSpotId, Long robotId) {
        return sysRobotMapper.getRobotUpgrade(scenicSpotId, robotId);
    }

    @Override
    public int updateRobotUpgrade(Long scenicSpotId, Long robotId) {
        return sysRobotMapper.updateRobotUpgrade(scenicSpotId, robotId);
    }

    @Override
    public List<SysRobotAppVersion> getRobotVersionPad(Long scenicSpotId) {
        return sysRobotMapper.getRobotVersionPad(scenicSpotId);
    }

    @Override
    public void timingRobotAuto() throws Exception {
        List<SysScenicSpot> sysScenicSpots = sysRobotMapper.timingRobotAuto();
        if (sysScenicSpots.size() > 0) {
            ReturnModel returnModel = new ReturnModel();
            List<SysRobot> robots = sysRobotMapper.getRobotUpgrade(sysScenicSpots.get(0).getScenicSpotId(), null);
            if (ToolUtil.isNotEmpty(robots) && robots.size() > 0) {
                for (SysRobot sysRobot : robots) {
                    if (sysRobot.getRobotCodeCid() != null && !("1").equals(sysRobot.getAutoUpdateState())) {
                        returnModel.setData("");
                        returnModel.setMsg("机器人升级修改成功");
                        returnModel.setState(Constant.STATE_SUCCESS);
                        returnModel.setType(Constant.ROBOT_UPDATE);
                        // 转JSON格式发送到个推
                        String robotUnlock = JsonUtils.toString(returnModel);
                        String encode = AES.encode(robotUnlock);// 加密推送
                        String isSuccess = WeChatGtRobotAppPush.singlePush(sysRobot.getRobotCodeCid(), encode, "成功!");
                        if ("1".equals(isSuccess)) {
                            returnModel.setData("");
                            returnModel.setMsg("发送成功！");
                            returnModel.setState(Constant.STATE_SUCCESS);
                        } else {
                            returnModel.setData("");
                            returnModel.setMsg("发送失败！");
                            returnModel.setState(Constant.STATE_FAILURE);
                        }
                    }
                }
            }
        }
    }


    //list分页
    public List<SysRobot> page(List<SysRobot> dataList, int pageSize, int currentPage) {
        List<SysRobot> SysRobotList = new ArrayList<>();
        if (dataList != null && dataList.size() > 0) {
            int currIdx = (currentPage > 1 ? (currentPage - 1) * pageSize : 0);
            for (int i = 0; i < pageSize && i < dataList.size() - currIdx; i++) {
                SysRobot data = dataList.get(currIdx + i);
                SysRobotList.add(data);
            }
        }
        return SysRobotList;
    }

}
