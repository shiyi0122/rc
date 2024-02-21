package com.hna.hka.archive.management.appSystem.dao;


import com.hna.hka.archive.management.assetsSystem.model.AttendanceTime;
import com.hna.hka.archive.management.assetsSystem.model.EmployeeAttendance;
import com.hna.hka.archive.management.assetsSystem.model.RobotAttendance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysAppPeopleAttendanceMapper {

    int inAttendanceRecord(EmployeeAttendance employeeAttendance);
    List<AttendanceTime> selectAttendanceTimes(AttendanceTime attendanceTime);

    List<EmployeeAttendance> timePeriod(@Param("startDate")String startDate, @Param("endDate")String endDate, @Param("userId")String userId);

    List<EmployeeAttendance> selectEmployeeAttendance(EmployeeAttendance employeeAttendance);

    List<EmployeeAttendance> selectEmployeeAttendances(EmployeeAttendance employeeAttendance);

    List<RobotAttendance> RobotAttendance(RobotAttendance robotAttendance);

    List<RobotAttendance> RobotAttendances(RobotAttendance robotAttendance);

    int upUserInformation(EmployeeAttendance employeeAttendance);

    EmployeeAttendance selseUser(String userId);

    List<EmployeeAttendance> selScenicSpot(String scenicSpotName);

}
