package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttendanceRecordMapper {

    int addPeoPle(AttendanceRecord attendanceRecord);

    int addEquipment(AttendanceRecord attendanceRecord);

    AttendanceRecord selApp(AttendanceRecord attendanceRecord);

    int upEquipment(AttendanceRecord attendanceRecord);

    Integer robotNum(@Param("scenicSpotId") Long scenicSpotId,@Param("robotFaultState")String robotFaultState);

    List<AttendanceRecord> list(AttendanceRecordUtil attendanceRecordUtil);

    List<AttendanceRecord> selUser(AttendanceRecordUtil attendanceRecordUtil);

    int selTotal(AttendanceRecordUtil attendanceRecordUtil);

    List<AttendanceRecord> selDetails(AttendanceRecordUtil attendanceRecordUtil);

    List<AttendanceRecord> selSpotName(AttendanceRecordUtil attendanceRecordUtil);

    List<EmployeeAttendance> selectEmployeeAttendance(EmployeeAttendance employeeAttendance);

    int deleteEmployeeAttendance(EmployeeAttendance employeeAttendance);

    List<EmployeeAttendance> selectAllEmployees();

    List<EmployeeAttendance> selectAllRecords(EmployeeAttendance employeeAttendance);

    List<EmployeeAttendance> selectEmployeeAttendances(EmployeeAttendance employeeAttendance);


    int timePeriod(OperationAttendance operation);

    int deleteRobotAttendance(RobotAttendance robotAttendance);

    String selectScenicSpotName(Long scenicSpotId);
//  根据景区id获取景区名称

}
