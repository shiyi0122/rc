package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.EquipmentAttendanceStatistics;

import java.util.List;

public interface EquipmentAttendanceStatisticsMapper {

    List<EquipmentAttendanceStatistics> spotList(EquipmentAttendanceStatistics equipmentAttendanceStatisticsc);

    List<EquipmentAttendanceStatistics> list(EquipmentAttendanceStatistics equipmentAttendanceStatistics);

    int selAttendance(EquipmentAttendanceStatistics equipmentAttendanceStatistics);

    int selOrder(EquipmentAttendanceStatistics equipmentAttendanceStatistics);

    EquipmentAttendanceStatistics getSpotIdLastAttendance(String scenicSpotId);

}
