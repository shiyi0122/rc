package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysRobotMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotWifiDataMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpotWifiData;
import com.hna.hka.archive.management.system.service.SysScenicSpotWifiDataService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/3/19 11:22
 */
@Service
public class SysScenicSpotWifiDataServiceImpl implements SysScenicSpotWifiDataService {

    @Autowired
    SysScenicSpotWifiDataMapper scenicSpotWifiDataMapper;
    @Autowired
    private SysRobotMapper sysRobotMapper;

    /**
     * 列表展示 WIFI探针数据统计
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getScenicSpotWifiData(Integer pageNum, Integer pageSize, HashMap<String, String> search) {

        PageHelper.startPage(pageNum,pageSize);

        List<SysScenicSpotWifiData> list = scenicSpotWifiDataMapper.getScenicSpotWifiData(search);
        PageDataResult pageDataResult = new PageDataResult();
        if (list.size()>0){
            PageInfo<SysScenicSpotWifiData> sysScenicSpotWifiDataPageInfo = new PageInfo<>(list);
            pageDataResult.setList(sysScenicSpotWifiDataPageInfo.getList());
            pageDataResult.setTotals((int)sysScenicSpotWifiDataPageInfo.getTotal());

        }

        return pageDataResult;

    }

    @Override
    public Map<String, Object> getPeopleCounting(String spotId) {


       Map<String , Object> map = new HashMap<>();

//       Integer morningSum =   scenicSpotWifiDataMapper.getMorningPeopleCounting(spotId);
//
//       Integer afternoonSum =  scenicSpotWifiDataMapper.getAfternoonPeopleCounting(spotId);
//
//       Integer currentCountingSum =  scenicSpotWifiDataMapper.getCurrentPeopleCounting(spotId);
//
//       Integer peopleAll = morningSum + afternoonSum;

       Integer robotWorkIngSum = sysRobotMapper.getSpotIdByRobotCountWorking(spotId);

//       if (StringUtils.isEmpty(morningSum)){
//           map.put("morningSum",0);
//       }else{
//           map.put("morningSum",morningSum);
//       }
//       if (StringUtils.isEmpty(afternoonSum)){
//           map.put("afternoonSum",0);
//       }else{
//           map.put("afternoonSum",afternoonSum);
//       }
//       if (StringUtils.isEmpty(currentCountingSum)){
//           map.put("currentCountingSum",0);
//       }else{
//           map.put("currentCountingSum",currentCountingSum);
//       }
//       if (StringUtils.isEmpty(peopleAll)){
//           map.put("peopleAll",0);
//       }else{
//           map.put("peopleAll",peopleAll);
//       }
       if (StringUtils.isEmpty(robotWorkIngSum)){
           map.put("robotWorkIngSum",0);
       }else{
           map.put("robotWorkIngSum",robotWorkIngSum);
       }


       map.put("code","200");

        return map;
    }


}
