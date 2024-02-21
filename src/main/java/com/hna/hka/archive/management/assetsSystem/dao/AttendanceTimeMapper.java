package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.AttendanceTime;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttendanceTimeMapper {

    int addAttendanceTime(AttendanceTime attendanceTime);

    int upAttendanceTime(AttendanceTime attendanceTime);

    int deAttendanceTime(AttendanceTime attendanceTime);
    List<AttendanceTime> selectAttendanceTime(AttendanceTime attendanceTime);

    List<AttendanceTime> selectAttendanceTimes(AttendanceTime attendanceTime);
}
