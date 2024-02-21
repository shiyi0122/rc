package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.AttendanceTime;
import com.hna.hka.archive.management.system.util.ReturnModel;


public interface AttendanceTimeService {

    int addAttendanceTime(AttendanceTime attendanceTime);

    int upAttendanceTime(AttendanceTime attendanceTime);

    int deAttendanceTime(AttendanceTime attendanceTime);

    ReturnModel selectAttendanceTime(int pageNum, int pageSize, AttendanceTime attendanceTime);
}
