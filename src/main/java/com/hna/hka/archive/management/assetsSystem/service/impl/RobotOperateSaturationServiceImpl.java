package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.hna.hka.archive.management.assetsSystem.dao.RobotOperateSaturationMapper;
import com.hna.hka.archive.management.assetsSystem.service.RobotOperateSaturationService;
import com.hna.hka.archive.management.system.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author zhang
 * @Date 2022/3/3 13:22
 * 机器人运营饱和度
 *
 */
@Service
public class RobotOperateSaturationServiceImpl implements RobotOperateSaturationService {

    @Autowired
    RobotOperateSaturationMapper robotOperateSaturationMapper;


    /**
     * 根据景区查询机器人饱和度
     * @param spotId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<HashMap> getOperateSaturationList(Long spotId, Integer pageNum, Integer pageSize) {

        if (StringUtils.isEmpty(pageNum) || pageNum == 1){
            pageNum=0;
        }else{
            pageNum = pageNum * pageSize;
        }
        if (StringUtils.isEmpty(pageSize)){
            pageSize=10;
        }


        List<HashMap> list =  robotOperateSaturationMapper.getOperateSaturationList(spotId,pageNum,pageSize);

        return list;
    }

    /**
     * 机器人饱和度(折线图)
     * * @param spotId
     * @return
     */
    @Override
    public List<String> getOperateSaturationCensus(List<String> list,Long spotId) {
        HashMap map = new HashMap();
        List<String> saturationList = new ArrayList<>();
        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar c = Calendar.getInstance();

            for (String s : list) {
                Date parse = simpleDateFormat.parse(s);
                c.setTime(parse);
                /*将设置的时间加上60分钟*/
                c.add(Calendar.MINUTE, 59);
                c.add(Calendar.SECOND,59);
                Date dateTime = c.getTime();
                String format2 = simpleDateFormat.format(dateTime);
                String saturation =  robotOperateSaturationMapper.getOperateSaturationCensus(s,format2,spotId);
                saturationList.add(saturation);
            }


        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }


        return saturationList;
    }

    //获取总条数
    @Override
    public int getOperateSaturationListCount(Long spotId) {
        int i  =  robotOperateSaturationMapper.getOperateSaturationListCount(spotId);
        return i;
    }

    /**
     * 统计饱和度
     * @param spotId
     * @return
     */
    @Override
    public HashMap getOperateSaturationSettlement(Long spotId) {

       HashMap map =  robotOperateSaturationMapper.getOperateSaturationSettlement(spotId);

       return map;
    }
}
