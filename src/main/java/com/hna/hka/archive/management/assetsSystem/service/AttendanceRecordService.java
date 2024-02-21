package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.*;
import com.hna.hka.archive.management.system.util.ReturnModel;

import java.text.ParseException;
import java.util.List;

public interface AttendanceRecordService {


    int add(AttendanceRecord attendanceRecord);

    AttendanceRecord selApp(AttendanceRecord attendanceRecord);

    int upEquipment(AttendanceRecord attendanceRecord);

    PunchInRecordPage list(AttendanceRecordUtil attendanceRecordUtil, Integer pageNum, Integer pageSize);

    List<AttendanceRecord> selDetails(AttendanceRecordUtil attendanceRecordUtil);

    List<StatisticalReport> report(AttendanceRecordUtil attendanceRecordUtil);

    //    机器人考勤
    ReturnModel robotAttendance(Integer pageNum,Integer pageSize,RobotAttendance robotAttendance);

    int updateRobotAttendance(RobotAttendance robotAttendance);

    int insertRobotAttendance(RobotAttendance robotAttendance);

    int robotAttendance2(RobotAttendance robotAttendance);

    //    查询员工考勤列表
    ReturnModel selectEmployeeAttendance(Integer pageNum, Integer pageSize, EmployeeAttendance employeeAttendance) throws ParseException;

    //    根据考勤记录主键ID删除员工考勤
    int deleteEmployeeAttendance(EmployeeAttendance employeeAttendance);

    List<EmployeeAttendance> getexportEmployeeAttendance(EmployeeAttendance employeeAttendance);

    List<EmployeeAttendance> selectEmployeeAttendances(EmployeeAttendance employeeAttendance) throws ParseException;

//    机器人考勤导出方法
    List<RobotAttendance> robotAttendances(RobotAttendance robotAttendance);

    OperationAttendance  selectSurvey(OperationAttendance operation,AttendanceTime attendanceTime);

    int deleteRobotAttendance(RobotAttendance robotAttendance);
}
