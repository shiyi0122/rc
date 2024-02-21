package com.hna.hka.archive.management.appSystem.service;

import com.hna.hka.archive.management.appSystem.model.IndividualAndAttendance;
import com.hna.hka.archive.management.assetsSystem.model.Address;
import com.hna.hka.archive.management.assetsSystem.model.AttendanceTime;
import com.hna.hka.archive.management.assetsSystem.model.EmployeeAttendance;
import com.hna.hka.archive.management.assetsSystem.model.RobotAttendance;
import com.hna.hka.archive.management.system.util.ReturnModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface AppPeopleAttendanceService {
    int inAttendanceRecord(MultipartFile file, EmployeeAttendance employeeAttendance,String userName,AttendanceTime attendanceTime);

    IndividualAndAttendance  selAttendanceRecord(String userId, EmployeeAttendance employeeAttendance, AttendanceTime attendanceTime);

    ReturnModel selNoClocking(EmployeeAttendance employeeAttendance);

    IndividualAndAttendance selRobotClocking(String userId,RobotAttendance robotAttendance);

    int upUserInformation(String userId,EmployeeAttendance employeeAttendance);

    IndividualAndAttendance selectUser(EmployeeAttendance employeeAttendance);

    List<Address> selScenicSpot(String scenicSpotName);

}
