package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.OperationAttendance;
import com.hna.hka.archive.management.assetsSystem.model.RobotAttendance;
import com.hna.hka.archive.management.assetsSystem.model.RobotVersion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName：RobotAttendanceMapper
 * @Author: gouteng
 * @Date: 2022-11-14 10:41
 * @Description: 必须描述类做什么事情, 实现什么功能
 */
@Mapper
public interface RobotAttendanceMapper {

    int insertRobotAttendance(RobotAttendance robotAttendance);

    List<RobotAttendance> RobotAttendance(RobotAttendance robotAttendance);

    int RobotAttendance2(RobotAttendance robotAttendance);

    int updateRobotAttendance(RobotAttendance robotAttendance);


    List<RobotAttendance> RobotAttendances(RobotAttendance robotAttendance);

    List<RobotAttendance> timePeriod(OperationAttendance operation);

    List<RobotVersion> wholeClientVersion(OperationAttendance operation);

    int selectWholeNum(OperationAttendance operation);
}
