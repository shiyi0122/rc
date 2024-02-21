package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.EquipmentAttendanceStatistics;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

public interface EquipmentAttendanceStatisticsService {

    PageDataResult list(EquipmentAttendanceStatistics equipmentAttendanceStatistics,Integer pageNum,Integer pageSize);

    List<Map> listNew(EquipmentAttendanceStatistics equipmentAttendanceStatistics,Integer pageNum,Integer pageSize);


    List<Map<String, Object>> pieChartList(String type,String startTime,String endTime,String spotId);

    List<Map<String, Object>> histogramList(String type, String startTime, String endTime, String spotId,String sort);

    List<Map<String, Object>> histogramListNew(String type, String startTime, String endTime, String spotId, String sort);


    List<EquipmentAttendanceStatistics> listsN(EquipmentAttendanceStatistics equipmentAttendanceStatistics, Integer pageNum, Integer pageSize);

}
