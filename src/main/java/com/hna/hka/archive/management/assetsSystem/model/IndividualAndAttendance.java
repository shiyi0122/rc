package com.hna.hka.archive.management.appSystem.model;

import com.hna.hka.archive.management.assetsSystem.model.EmployeeAttendance;
import com.hna.hka.archive.management.assetsSystem.model.RobotAttendance;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName：PersonalInformationAndAttendance
 * @Author: gouteng
 * @Date: 2022-12-08 16:47
 * @Description: 必须描述类做什么事情, 实现什么功能
 */
@Data
public class IndividualAndAttendance {
    @ApiModelProperty("员工姓名")
    private String userName;
    @ApiModelProperty("员工职位")
    private String employeePost;
    @ApiModelProperty("归属景区")
    private String belongingScenicSpot;
    @ApiModelProperty("总条数")
    private int total;
//    人员考勤统计
    private List<EmployeeAttendance> employeeAttendances;
//    机器人考勤统计
    private List<RobotAttendance> robotAttendance;
}
