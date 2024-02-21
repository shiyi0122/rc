package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.system.model.SysOrderRealTime;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-10-29 14:51
 **/
public interface RealTimeMapper {
    List<HashMap> line(Long spotId);



    List<Double> lineDRYS(String spotId,String time);

    List<Integer> lineJXZ(String spotId, String time);

    List<String> lineDate(@Param("time") String time);


    List<String> hourMinuteAndSecond();

    int  insert(SysOrderRealTime sysOrderRealTime);


}
