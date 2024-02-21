package com.hna.hka.archive.management.appSystem.service.Impl;

import com.hna.hka.archive.management.appSystem.dao.SysAppLoginLogMapper;
import com.hna.hka.archive.management.appSystem.model.SysAppLoginLog;
import com.hna.hka.archive.management.appSystem.service.SysAppLoginLogService;
import com.hna.hka.archive.management.business.dao.BusinessChartDataRecordMapper;
import com.hna.hka.archive.management.business.model.BusinessChartDataRecord;
import com.hna.hka.archive.management.managerApp.dao.SysAppUsersMapper;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.system.dao.SysRobotUnusualLogMapper;
import com.hna.hka.archive.management.system.dao.SysRobotUnusualTimeMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotBindingMapper;
import com.hna.hka.archive.management.system.model.SysRobotUnusualTime;
import com.hna.hka.archive.management.system.model.SysScenicSpotBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.appSystem.service
 * @ClassName: SysAppLoginLogServiceImpl
 * @Author: 郭凯
 * @Description:
 * @Date: 2020/11/23 13:46
 * @Version: 1.0
 */
@Service
public class SysAppLoginLogServiceImpl implements SysAppLoginLogService {

    @Autowired
    private SysAppLoginLogMapper sysAppLoginLogMapper;

    @Autowired
    private SysAppUsersMapper sysAppUsersMapper;

    @Autowired
    private SysScenicSpotBindingMapper sysScenicSpotBindingMapper;

    @Autowired
    private BusinessChartDataRecordMapper businessChartDataRecordMapper;

    @Autowired
    private SysRobotUnusualTimeMapper sysRobotUnusualTimeMapper;

    /**
     * @Author 郭凯
     * @Description APP用户登录日志（新增）
     * @Date 13:48 2020/11/23
     * @Param [sysLoginLog]
     * @return void
    **/
    @Override
    public void insertSysAppLoginLog(SysAppLoginLog sysLoginLog) {
        sysAppLoginLogMapper.insertSelective(sysLoginLog);
    }

    @Override
    /**
     * @Author 郭凯
     * @Description 查询节点
     * @Date 13:53 2020/11/23
     * @Param [loginName]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysScenicSpotBinding>
    **/
    public List<SysScenicSpotBinding> selectbindingsList(String loginName) {
        // TODO Auto-generated method stub
        BusinessChartDataRecord businessChartDataRecord = new BusinessChartDataRecord();
        SysRobotUnusualTime sysRobotUnusualTime = new SysRobotUnusualTime();
        List<SysScenicSpotBinding> scenicSpotBindings=new ArrayList<SysScenicSpotBinding>();
        HashMap<Long, String> hashMap = new HashMap<Long, String>();
        SysAppUsers appUsers = sysAppUsersMapper.getSysAppUserByLoginName(loginName);
        if ("3".equals(appUsers.getUserType())) {

//            scenicSpotBindings = sysScenicSpotBindingMapper.selectBindingsList();
            scenicSpotBindings = sysScenicSpotBindingMapper.selectBindingsByRecordList();
            for (SysScenicSpotBinding scenicSpotBinding : scenicSpotBindings) {
                //获取景区饱和度和饱和度范围
                businessChartDataRecord =  businessChartDataRecordMapper.getSpotIdByRecordList(scenicSpotBinding.getScenicSpotFid());
                scenicSpotBinding.setRecordReceptionDesk(businessChartDataRecord.getRecordReceptionDesk());
            }

        }else {
            //查询是否有父子节点
//            List<SysScenicSpotBinding> scenicSpotBinding = sysScenicSpotBindingMapper.selectUserName(loginName);
            List<SysScenicSpotBinding> scenicSpotBinding = sysScenicSpotBindingMapper.selectUserNameByRecord(loginName);
            for(SysScenicSpotBinding SysScenicSpotBinding : scenicSpotBinding) {
                Long scenicSpotFid = SysScenicSpotBinding.getScenicSpotFid();
                Long scenicSpotPid = SysScenicSpotBinding.getScenicSpotPid();
                if (scenicSpotPid != null) {//景区
                    //获取景区饱和度
                    businessChartDataRecord =  businessChartDataRecordMapper.getSpotIdByRecordList(scenicSpotFid);
                    if (!StringUtils.isEmpty(businessChartDataRecord)){
                        SysScenicSpotBinding.setRecordReceptionDesk(businessChartDataRecord.getRecordReceptionDesk());
                    }else{
                        SysScenicSpotBinding.setRecordReceptionDesk("0");
                    }
                    scenicSpotBindings.add(SysScenicSpotBinding);
                    //查询归属地
                    List<SysScenicSpotBinding> spotBindings = sysScenicSpotBindingMapper.selectUserPid(scenicSpotPid);//查询Fid
                    List<SysScenicSpotBinding> Bindings=new ArrayList<SysScenicSpotBinding>();
                    for(SysScenicSpotBinding spotBinding : spotBindings) {
                        Bindings.add(spotBinding);
                    }
                    for(SysScenicSpotBinding spotBinding : Bindings) {
                        hashMap.put(spotBinding.getScenicSpotFid(), spotBinding.getScenicSpotFname());
                    }
                }
                else if(scenicSpotPid == null) {//城市
                    scenicSpotBindings.add(SysScenicSpotBinding);
                    List<SysScenicSpotBinding> listFid = sysScenicSpotBindingMapper.selectUserFid(scenicSpotFid);//查询Pid
//                    List<SysScenicSpotBinding> listFid = sysScenicSpotBindingMapper.selectUserFidByRecord(scenicSpotFid);//查询Pid
                    for(SysScenicSpotBinding Binding : listFid) {
                        //获取景区饱和度和饱和度范围
//                        businessChartDataRecord =  businessChartDataRecordMapper.getSpotIdByRecordList(Binding.getScenicSpotFid());
//                        Binding.setRecordReceptionDesk(businessChartDataRecord.getRecordReceptionDesk());
//                        sysRobotUnusualTime = sysRobotUnusualTimeMapper.getSpotIdByUnusualTime(Binding.getScenicSpotFid());
//                        if (!StringUtils.isEmpty(sysRobotUnusualTime)){
//                            Binding.setSaturationLow(sysRobotUnusualTime.getSaturationLow());
//                            Binding.setSaturationHigh(sysRobotUnusualTime.getSaturationHigh());
//                        }
                        scenicSpotBindings.add(Binding);
                    }
                }
            }
            Set<Map.Entry<Long, String>> entrySet=hashMap.entrySet();
            for(Map.Entry<Long, String> entry: entrySet){
                SysScenicSpotBinding sysScenicSpotBinding = new SysScenicSpotBinding();
                sysScenicSpotBinding.setScenicSpotFid(entry.getKey());
                sysScenicSpotBinding.setScenicSpotFname(entry.getValue());
                scenicSpotBindings.add(sysScenicSpotBinding);
            }
        }
        return scenicSpotBindings;
    }

}
