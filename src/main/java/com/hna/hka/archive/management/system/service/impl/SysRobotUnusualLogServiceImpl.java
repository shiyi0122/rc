package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotErrorRecords;
import com.hna.hka.archive.management.business.dao.BusinessChartDataRecordMapper;
import com.hna.hka.archive.management.business.model.BusinessChartDataRecord;
import com.hna.hka.archive.management.managerApp.dao.SysAppUsersMapper;
import com.hna.hka.archive.management.managerApp.dao.SysAppUsersSpotRoleMapper;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.managerApp.model.SysAppUsersSpotRole;
import com.hna.hka.archive.management.managerApp.service.SysAppUsersService;
import com.hna.hka.archive.management.system.dao.*;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.service.SysRobotUnusualLogService;
import com.hna.hka.archive.management.system.service.SysRobotUnusualTimeService;
import com.hna.hka.archive.management.system.util.*;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/3/3 10:14
 * 机器人异常监控接口
 */
@Service
public class SysRobotUnusualLogServiceImpl implements SysRobotUnusualLogService {

    @Autowired
    SysRobotUnusualLogMapper sysRobotUnusualLogMapper;

    @Autowired
    SysAppUsersSpotRoleMapper sysAppUsersSpotRoleMapper;

    @Autowired
    SysAppUsersMapper sysAppUsersMapper;

    @Autowired
    SysScenicSpotMapper sysScenicSpotMapper;

    @Autowired
    SysRobotMapper sysRobotMapper;

    @Autowired
    SysRobotUnusualTimeMapper sysRobotUnusualTimeMapper;
    @Autowired
    SysOrderMapper sysOrderMapper;
    @Autowired
    BusinessChartDataRecordMapper businessChartDataRecordMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public int addSysRobotUnusualLog(SysRobotUnusualLog sysRobotUnusualLog) {
        int i =0;
        sysRobotUnusualLog.setId(IdUtils.getSeqId());
        sysRobotUnusualLog.setCreateDate(DateUtil.currentDateTime());
        sysRobotUnusualLog.setUpdateDate(DateUtil.currentDateTime());

        String monthHourMonth = DateUtil.getMonthHourMonth(sysRobotUnusualLog.getUnusualTime());

        SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(sysRobotUnusualLog.getScenicSpotId());
        try{
            List<SysAppUsers> sysAppUsers = sysAppUsersSpotRoleMapper.getScenicSpotByUser(sysRobotUnusualLog.getScenicSpotId());
            for (SysAppUsers sysAppUsersN : sysAppUsers) {
                if ("1".equals(sysRobotUnusualLog.getUnusualType())){//禁区异常提醒
                    String isSuccess = WeChatGtRobotAppPush.singlePushApp(sysAppUsersN.getUserClientGtId(),   "机器人禁区异常提醒",sysScenicSpot.getScenicSpotName()+ "，" + sysRobotUnusualLog.getRobotCode() + "，" + ",此机器人已超出系统设置禁区异常时间 "+ monthHourMonth+"，请尽快处理!");
                    sysRobotUnusualLog.setPropellingContent(sysScenicSpot.getScenicSpotName()+ "，" + sysRobotUnusualLog.getRobotCode() + "，" + "此机器人已超出系统配置禁区异常时间 "+ monthHourMonth +"，请尽快处理!");
                } else if("2".equals(sysRobotUnusualLog.getUnusualType())) {//临时锁定异常
                    String isSuccess = WeChatGtRobotAppPush.singlePushApp(sysAppUsersN.getUserClientGtId(),  "机器人临时锁定异常提醒",sysScenicSpot.getScenicSpotName()+ "，" + sysRobotUnusualLog.getRobotCode() + "，" + ",此机器人已超出系统设置临时锁定异常时间 "+ monthHourMonth +"，请尽快处理!");
                    sysRobotUnusualLog.setPropellingContent(sysScenicSpot.getScenicSpotName()+ "，" + sysRobotUnusualLog.getRobotCode() + "，" + "此机器人已超出系统配置临时锁定异常时间 " +monthHourMonth+ "，请尽快处理!");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
//        if ("1".equals(sysRobotUnusualLog.getUnusualType())){
//            SysRobotUnusualLog robotCodeByUnusualLog = sysRobotUnusualLogMapper.getRobotCodeByUnusualLog(sysRobotUnusualLog.getRobotCode(), sysRobotUnusualLog.getUnusualType());
//            i = sysRobotUnusualLogMapper.insert(sysRobotUnusualLog);
//        }else{
//
//            i = sysRobotUnusualLogMapper.insert(sysRobotUnusualLog);
//        }
        i = sysRobotUnusualLogMapper.insert(sysRobotUnusualLog);
        return i;
    }

    /**
     * 后台列表查询
     * @param pageNum
     * @param pageSize
     * @param
     * @return
     */
    @Override
    public PageDataResult getSysRobotUnusualLogList(Integer pageNum, Integer pageSize,  Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageNum,pageSize);
        List<SysRobotUnusualLog> list = sysRobotUnusualLogMapper.backstageUnusualList(search);

        if (list.size()>0){

            Long num = sysRobotUnusualLogMapper.getTotalNumber();
            redisUtil.set("sysRobotUnusualNumber",num.toString());

            PageInfo<SysRobotUnusualLog> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;

    }

    /**
     * 修改异常状态
     * @param sysRobotUnusualLog
     * @return
     */
    @Override
    public int editSysRobotUnusualLog(SysRobotUnusualLog sysRobotUnusualLog) {

        int i = sysRobotUnusualLogMapper.update(sysRobotUnusualLog);
        return i;
    }

    /**
     * 管理者app查询机器人异常列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getAppSysRobotUnusualLogList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageNum,pageSize);
        List<SysRobotUnusualLog> list = sysRobotUnusualLogMapper.listApp(search);
        if (list.size()>0){
            PageInfo<SysRobotUnusualLog> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int) pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;

    }

    /**
     * 修改机器人异常状态
     * @param id
     * @param state
     * @param userId
     * @return
     */
    @Override
    public int editSysRobotUnusualLogState(String id, String state, Long userId) {
        int update = 0;
        SysRobotUnusualLog sysRobotUnusualLog =  sysRobotUnusualLogMapper.selectById(id);;

         if ("3".equals(state)){
            if (!StringUtils.isEmpty(sysRobotUnusualLog.getAppProcessorId())){
                if (sysRobotUnusualLog.getAppProcessorId().equals(userId)){
                    sysRobotUnusualLog.setUpdateDate(DateUtil.currentDateTime());
                    sysRobotUnusualLog.setStatus(state);
                    sysRobotUnusualLog.setAppProcessorId(userId);
                    update = sysRobotUnusualLogMapper.update(sysRobotUnusualLog);
                }else{
                    return  2;
                }
            }else{
                sysRobotUnusualLog.setUpdateDate(DateUtil.currentDateTime());
                sysRobotUnusualLog.setStatus(state);
                sysRobotUnusualLog.setAppProcessorId(userId);
                update = sysRobotUnusualLogMapper.update(sysRobotUnusualLog);
            }
        }else{
            sysRobotUnusualLog.setUpdateDate(DateUtil.currentDateTime());
            sysRobotUnusualLog.setStatus(state);
            sysRobotUnusualLog.setAppProcessorId(userId);
            update = sysRobotUnusualLogMapper.update(sysRobotUnusualLog);
        }


        return update;

    }

    /**
     * 每5分钟轮询机器人异常状态
     */
    @Override
    public void timingRobotSpotUnusualLog() {

        //获取已运营，欲运营景区
        List<SysScenicSpot> scenicSpotListNew = sysScenicSpotMapper.getScenicSpotListNew(null);

        SysRobotUnusualTime spotIdByUnusualTime = new SysRobotUnusualTime();
        String date = DateUtil.crutDate();
        SysRobotUnusualLog sysRobotUnusualLog = new SysRobotUnusualLog();
        SysOrder sysOrder = new SysOrder();
        List<SysOrder> sysOrderList = new ArrayList<>();
        try{

            for (SysScenicSpot sysScenicSpot : scenicSpotListNew) {

                spotIdByUnusualTime = sysRobotUnusualTimeMapper.getSpotIdByUnusualTime(sysScenicSpot.getScenicSpotId());
                //景区的园长
                List<SysAppUsers> sysAppUsers = sysAppUsersSpotRoleMapper.getScenicSpotByUser(sysScenicSpot.getScenicSpotId());

                //判断是否设置了异常监控时间
                if (!StringUtils.isEmpty(spotIdByUnusualTime)){
                    //获取景区中的机器人列表
                    List<SysRobot> robotListByScenicSpotId = sysRobotMapper.getRobotListByScenicSpotId(sysScenicSpot.getScenicSpotId());

                    for (SysRobot sysRobot : robotListByScenicSpotId) {

                        //查询机器人是否长时间未接单异常
                        sysOrderList = sysOrderMapper.getRobotAndDateByOrder(sysRobot.getRobotCode());
                        if (sysOrderList.size() > 0){
                            sysOrder = sysOrderList.get(0);
                        }
                        if (StringUtils.isEmpty(sysOrder)){

                        }else{
                            long l = DateUtil.timeConversion(sysOrder.getOrderEndTime(), DateUtil.currentDateTime());
                            if (l > Long.parseLong(spotIdByUnusualTime.getReceivingOrders())){

                                SysRobotUnusualLog sysRobotUnusualLogO = sysRobotUnusualLogMapper.getRobotCodeByUnusualLog(sysRobot.getRobotCode(),"3");

                                String timeHourMonth = DateUtil.getTimeHourMonth(sysOrder.getOrderEndTime(), DateUtil.currentDateTime());

                                if (StringUtils.isEmpty(sysRobotUnusualLogO)){
                                    String content =  sysScenicSpot.getScenicSpotName() +"，"+ sysRobot.getRobotCode() +"，"+ "此机器人已累计" + timeHourMonth + "未产生订单，请查看是否出现异常！";
                                    sysRobotUnusualLog = new SysRobotUnusualLog();
                                    sysRobotUnusualLog.setId(IdUtils.getSeqId());
                                    sysRobotUnusualLog.setRobotCode(sysRobot.getRobotCode());
                                    sysRobotUnusualLog.setScenicSpotId(sysScenicSpot.getScenicSpotId());
                                    sysRobotUnusualLog.setUnusualType("3");
                                    sysRobotUnusualLog.setPropellingContent(content);
                                    sysRobotUnusualLog.setUnusualTime(String.valueOf(l));
                                    sysRobotUnusualLog.setCreateDate(DateUtil.currentDateTime());
                                    sysRobotUnusualLog.setUpdateDate(DateUtil.currentDateTime());
                                    sysRobotUnusualLogMapper.insert(sysRobotUnusualLog);
                                    //推送逻辑
                                    //获取景有此景区，且是园长的管理者用户推送
                                    if (!StringUtils.isEmpty(sysAppUsers)){
                                        for (SysAppUsers sysAppUser : sysAppUsers) {
                                            String isSuccess = WeChatGtRobotAppPush.singlePushApp(sysAppUser.getUserClientGtId(), "机器人异常提醒", sysScenicSpot.getScenicSpotName() +"，"+ sysRobot.getRobotCode() +"，"+ "此机器人已累计" + timeHourMonth + "未产生订单，请查看是否出现异常！"  );

                                            System.out.println(isSuccess);
                                        }
                                    }

                                }else{

                                    long l1 = DateUtil.timeConversion(sysRobotUnusualLogO.getCreateDate(), DateUtil.currentDateTime());
                                    String timeHourMonthN = DateUtil.getTimeHourMonth(sysRobotUnusualLogO.getCreateDate(), DateUtil.currentDateTime());


                                    if (l1 >= Long.parseLong(spotIdByUnusualTime.getReceivingOrders())){
                                        sysRobotUnusualLog = new SysRobotUnusualLog();
                                        String content =  sysScenicSpot.getScenicSpotName() +"，"+ sysRobot.getRobotCode() +"，"+ "此机器人已累计" + timeHourMonthN + "未产生订单，请查看是否出现异常！";
                                        sysRobotUnusualLog.setId(IdUtils.getSeqId());
                                        sysRobotUnusualLog.setRobotCode(sysRobot.getRobotCode());
                                        sysRobotUnusualLog.setScenicSpotId(sysScenicSpot.getScenicSpotId());
                                        sysRobotUnusualLog.setUnusualType("3");
                                        sysRobotUnusualLog.setUnusualTime(String.valueOf(l));
                                        sysRobotUnusualLog.setCreateDate(DateUtil.currentDateTime());
                                        sysRobotUnusualLog.setUpdateDate(DateUtil.currentDateTime());
                                        sysRobotUnusualLog.setPropellingContent(content);
                                        sysRobotUnusualLogMapper.insert(sysRobotUnusualLog);
                                        // 推送逻辑
                                        //获取景有此景区，且是园长的管理者用户推送
                                        if (!StringUtils.isEmpty(sysAppUsers)){
                                            for (SysAppUsers sysAppUser : sysAppUsers) {
                                                String isSuccess = WeChatGtRobotAppPush.singlePushApp(sysAppUser.getUserClientGtId(), "机器人接单异常提醒", sysScenicSpot.getScenicSpotName() +"，"+ sysRobot.getRobotCode() +"，"+ "此机器人已累计" + timeHourMonthN + "未产生订单，请查看是否出现异常！"  );

                                            }
                                        }

                                    }

                                }
                            }
                        }


                        //查询 PAD App长时间不打开app 异常
                        long l = DateUtil.timeConversion(sysRobot.getUpdateDate(), DateUtil.currentDateTime());

                        String timeHourMonthT = DateUtil.getTimeHourMonth(sysRobot.getUpdateDate(), DateUtil.currentDateTime());

                        if ( l > Long.parseLong(spotIdByUnusualTime.getPadAppUsee())){

                            SysRobotUnusualLog sysRobotUnusualLogO = sysRobotUnusualLogMapper.getRobotCodeByUnusualLog(sysRobot.getRobotCode(), "4");

                            if (StringUtils.isEmpty(sysRobotUnusualLogO)){
                                sysRobotUnusualLog = new SysRobotUnusualLog();
                                String content  = sysScenicSpot.getScenicSpotName() +"，"+ sysRobot.getRobotCode() +"，"+ "此机器人已累计" + timeHourMonthT + "未启动App，请确认是否出现异常！";
                                sysRobotUnusualLog.setId(IdUtils.getSeqId());
                                sysRobotUnusualLog.setRobotCode(sysRobot.getRobotCode());
                                sysRobotUnusualLog.setScenicSpotId(sysScenicSpot.getScenicSpotId());
                                sysRobotUnusualLog.setUnusualType("4");
                                sysRobotUnusualLog.setUnusualTime(String.valueOf(l));
                                sysRobotUnusualLog.setCreateDate(DateUtil.currentDateTime());
                                sysRobotUnusualLog.setUpdateDate(DateUtil.currentDateTime());
                                sysRobotUnusualLog.setPropellingContent(content);
                                sysRobotUnusualLogMapper.insert(sysRobotUnusualLog);
                                //推送逻辑
                                //获取景有此景区，且是园长的管理者用户推送
                                if (!StringUtils.isEmpty(sysAppUsers)){
                                    for (SysAppUsers sysAppUser : sysAppUsers) {
                                        String isSuccess = WeChatGtRobotAppPush.singlePushApp(sysAppUser.getUserClientGtId(), "机器人PAD使用异常", sysScenicSpot.getScenicSpotName() +"，"+ sysRobot.getRobotCode() +"，"+ "此机器人已累计" + timeHourMonthT + "未启动App，请确认是否出现异常！");
                                        System.out.println(isSuccess);
                                    }
                                }

                            }else{

                                long l1 = DateUtil.timeConversion(sysRobotUnusualLogO.getCreateDate(), DateUtil.currentDateTime());

                                if (l1 >= Long.parseLong(spotIdByUnusualTime.getPadAppUsee())){
                                    sysRobotUnusualLog = new SysRobotUnusualLog();
                                    String content  = sysScenicSpot.getScenicSpotName() +"，"+ sysRobot.getRobotCode() +"，"+ "此机器人已累计" + timeHourMonthT + "未启动App，请确认是否出现异常！";
                                    sysRobotUnusualLog.setId(IdUtils.getSeqId());
                                    sysRobotUnusualLog.setRobotCode(sysRobot.getRobotCode());
                                    sysRobotUnusualLog.setScenicSpotId(sysScenicSpot.getScenicSpotId());
                                    sysRobotUnusualLog.setUnusualType("4");
                                    sysRobotUnusualLog.setUnusualTime(String.valueOf(l));
                                    sysRobotUnusualLog.setCreateDate(DateUtil.currentDateTime());
                                    sysRobotUnusualLog.setUpdateDate(DateUtil.currentDateTime());
                                    sysRobotUnusualLog.setPropellingContent(content);
                                    sysRobotUnusualLogMapper.insert(sysRobotUnusualLog);
                                    //推送逻辑
                                    //获取景有此景区，且是园长的管理者用户推送
                                    if (!StringUtils.isEmpty(sysAppUsers)){
                                        for (SysAppUsers sysAppUser : sysAppUsers) {
                                            String isSuccess = WeChatGtRobotAppPush.singlePushApp(sysAppUser.getUserClientGtId(), "机器人PAD使用异常", sysScenicSpot.getScenicSpotName() +"，"+ sysRobot.getRobotCode() +"，"+ "此机器人已累计" + timeHourMonthT + "未启动App，请确认是否出现异常！"  );
                                        }
                                    }
                                }
                            }

                        }
                    }

                }else{
                    continue;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     * 每7分钟和37分钟，执行一次 轮询景区饱和度状态
     */
    @Override
    public void timingScenicSpotSaturationLog() {

        //获取已运营，欲运营景区
        List<SysScenicSpot> scenicSpotListNew = sysScenicSpotMapper.getScenicSpotListNew(null);

//          businessChartDataRecordMapper.getTimeBySaturation(DateUtil.crutDate());
        SysRobotUnusualTime spotIdByUnusualTime = new SysRobotUnusualTime();
        String date = DateUtil.crutDate();
        SysRobotUnusualLog sysRobotUnusualLog = new SysRobotUnusualLog();
        try{
            for (SysScenicSpot sysScenicSpot : scenicSpotListNew) {

                spotIdByUnusualTime = sysRobotUnusualTimeMapper.getSpotIdByUnusualTime(sysScenicSpot.getScenicSpotId());
                //景区的园长
//                List<SysAppUsers> sysAppUsers = sysAppUsersSpotRoleMapper.getScenicSpotByUser(sysScenicSpot.getScenicSpotId());
                if (!StringUtils.isEmpty(spotIdByUnusualTime)){

                    if (!StringUtils.isEmpty(spotIdByUnusualTime.getSaturationTime()) && !StringUtils.isEmpty(spotIdByUnusualTime.getSaturationLow()) && !StringUtils.isEmpty(spotIdByUnusualTime.getSaturationHigh())){

                        List<BusinessChartDataRecord> list = businessChartDataRecordMapper.getTimeByRecords(spotIdByUnusualTime.getSaturationTime(),sysScenicSpot.getScenicSpotId(),DateUtil.crutDate());
                        int i  = 0;
                        for (BusinessChartDataRecord businessChartDataRecord : list) {
                            if (Long.parseLong(businessChartDataRecord.getRecordReceptionDesk()) >= Long.parseLong(spotIdByUnusualTime.getSaturationLow()) && Long.parseLong(businessChartDataRecord.getRecordReceptionDesk()) <= Long.parseLong(spotIdByUnusualTime.getSaturationHigh())){
                                continue;

                            }else{
                                i = 1 ;
                                break;
                            }
                        }

                        if (i == 0){

                            sysRobotUnusualLog = new SysRobotUnusualLog();
                            sysRobotUnusualLog.setId(IdUtils.getSeqId());
                            sysRobotUnusualLog.setScenicSpotId(sysScenicSpot.getScenicSpotId());

                            sysRobotUnusualLog.setUnusualType("7");
                            sysRobotUnusualLog.setCreateDate(DateUtil.currentDateTime());
                            sysRobotUnusualLog.setUpdateDate(DateUtil.currentDateTime());
                            sysRobotUnusualLogMapper.insert(sysRobotUnusualLog);
                        }

                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    /**
     * 判断是否展示红点
     * @return
     */
    @Override
    public Boolean ifSysRobotBadge() {

        Long totalNumber = sysRobotUnusualLogMapper.getTotalNumber();

        String sysRobotUnusualNumber = (String)redisUtil.get("sysRobotUnusualNumber");

        if (totalNumber >Long.parseLong(sysRobotUnusualNumber)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 一键处理
     * @param sysUser
     * @param scenicSpotId
     * @return
     */
    @Override
    public int oneClickProcessing(SysUsers sysUser, Long scenicSpotId,String startTime,String endTime) {

        String time = null;
        if (StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)){
            time = DateUtil.crutDate();
            startTime = null;
            endTime = null;
        }else{
            endTime = DateUtil.addDay(endTime,1);
        }
       int i = sysRobotUnusualLogMapper.oneClickProcessing(sysUser.getUserId(),scenicSpotId,startTime,endTime,time);

       return i;
    }

    /**
     * 超长订单轮询处理
     */
    @Override
    public void timingRobotSpotOrderLog() {

        //获取已运营， 欲运营景区
        List<SysScenicSpot> scenicSpotListNew = sysScenicSpotMapper.getScenicSpotListNew(null);

        SysRobotUnusualTime spotIdByUnusualTime = new SysRobotUnusualTime();
        String date = DateUtil.crutDate();
        SysRobotUnusualLog sysRobotUnusualLog = new SysRobotUnusualLog();
//        SysOrder sysOrder = new SysOrder();
        List<SysOrder> sysOrderList = new ArrayList<>();

        try {

            for (SysScenicSpot sysScenicSpot : scenicSpotListNew) {
                spotIdByUnusualTime = sysRobotUnusualTimeMapper.getSpotIdByUnusualTime(sysScenicSpot.getScenicSpotId());

                //景区的园长
                List<SysAppUsers> sysAppUsers = sysAppUsersSpotRoleMapper.getScenicSpotByUser(sysScenicSpot.getScenicSpotId());

                if (StringUtils.isEmpty(spotIdByUnusualTime)){
                    continue;
                }else{

                    if (StringUtils.isEmpty(spotIdByUnusualTime.getOrderAbnormalTime())){
                        continue;
                    }else{

                        //判断是否要执行查询逻辑
                        if ("2".equals(spotIdByUnusualTime.getOrderState()) ){
                            break;
                        }

                        //查询景区中超出订单列表
                        List<SysOrder> list = sysOrderMapper.getSpotIdAndStatusByList(sysScenicSpot.getScenicSpotId(),spotIdByUnusualTime.getOrderAbnormalTime());

                        for (SysOrder order : list) {
                            SysRobotUnusualLog sysRobotUnusualLogO = sysRobotUnusualLogMapper.getRobotCodeByUnusualLog(order.getOrderRobotCode(),"8");
                            long l = DateUtil.timeConversion(order.getOrderStartTime(), DateUtil.currentDateTime());

                            if (StringUtils.isEmpty(sysRobotUnusualLogO)){
                                sysRobotUnusualLog = new SysRobotUnusualLog();
                                String content  = sysScenicSpot.getScenicSpotName() +"，"+ order.getOrderRobotCode() +"，"+ "此机器人当前订单时长已超出后台规定时长,请查看处理!";
                                sysRobotUnusualLog.setId(IdUtils.getSeqId());
                                sysRobotUnusualLog.setRobotCode(order.getOrderRobotCode());
                                sysRobotUnusualLog.setScenicSpotId(sysScenicSpot.getScenicSpotId());
                                sysRobotUnusualLog.setUnusualType("8");
                                sysRobotUnusualLog.setUnusualTime(String.valueOf(l));
                                sysRobotUnusualLog.setCreateDate(DateUtil.currentDateTime());
                                sysRobotUnusualLog.setUpdateDate(DateUtil.currentDateTime());
                                sysRobotUnusualLog.setPropellingContent(content);
                                sysRobotUnusualLogMapper.insert(sysRobotUnusualLog);
                                //推送逻辑
                                //获取景有此景区，且是园长的管理者用户推送
                                if (!StringUtils.isEmpty(sysAppUsers)){
                                    for (SysAppUsers sysAppUser : sysAppUsers) {
                                        String isSuccess = WeChatGtRobotAppPush.singlePushApp(sysAppUser.getUserClientGtId(), "机器人超出时间报警",sysScenicSpot.getScenicSpotName() +"，"+ order.getOrderRobotCode() +"，"+ "此机器人当前订单时长已超出后台规定时长,请查看处理!");
                                        System.out.println(isSuccess);
                                    }
                                }
                            }else{

                                //计算上次添加记录的时间和当前时间相差多少时间，用来判断是否需要重新保存一条记录
                                long j = DateUtil.timeConversion(sysRobotUnusualLogO.getCreateDate(), DateUtil.currentDateTime());
                                if (j >=  Long.parseLong(spotIdByUnusualTime.getOrderAbnormalTime())){
                                    sysRobotUnusualLog = new SysRobotUnusualLog();
                                    String content  = sysScenicSpot.getScenicSpotName() +"，"+ order.getOrderRobotCode() +"，"+ "此机器人当前订单时长已超出后台规定时长,请查看处理!";
                                    sysRobotUnusualLog.setId(IdUtils.getSeqId());
                                    sysRobotUnusualLog.setRobotCode(order.getOrderRobotCode());
                                    sysRobotUnusualLog.setScenicSpotId(sysScenicSpot.getScenicSpotId());
                                    sysRobotUnusualLog.setUnusualType("8");
                                    sysRobotUnusualLog.setUnusualTime(String.valueOf(l));
                                    sysRobotUnusualLog.setCreateDate(DateUtil.currentDateTime());
                                    sysRobotUnusualLog.setUpdateDate(DateUtil.currentDateTime());
                                    sysRobotUnusualLog.setPropellingContent(content);
                                    sysRobotUnusualLogMapper.insert(sysRobotUnusualLog);
                                    //推送逻辑
                                    //获取景有此景区，且是园长的管理者用户推送
                                    if (!StringUtils.isEmpty(sysAppUsers)){
                                        for (SysAppUsers sysAppUser : sysAppUsers) {
                                            String isSuccess = WeChatGtRobotAppPush.singlePushApp(sysAppUser.getUserClientGtId(), "机器人超长时间报警",sysScenicSpot.getScenicSpotName() +"，"+ order.getOrderRobotCode() +"，"+ "此机器人当前订单时长已超出后台规定时长,请查看处理!");
                                            System.out.println(isSuccess);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
