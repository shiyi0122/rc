package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.model.*;
import com.hna.hka.archive.management.assetsSystem.service.AttendanceRecordService;
import com.hna.hka.archive.management.assetsSystem.service.AttendanceTimeService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.FileUtil;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Api(tags = "考勤记录")
@Controller
@Slf4j
@RequestMapping("/sys/AttendanceRecord")
public class AttendanceRecordController extends PublicUtil {

    @Autowired
    private AttendanceRecordService attendanceRecordService;

    @Autowired
    private AttendanceTimeService attendanceTimeService;


    @ApiOperation("考勤记录新增")
    @RequestMapping("/add")
    @ResponseBody
    public ReturnModel add(AttendanceRecord attendanceRecord) {
        ReturnModel returnModel = new ReturnModel();
        int i = attendanceRecordService.add(attendanceRecord);
        if (i == 1) {
            returnModel.setData("");
            returnModel.setMsg("考勤记录新增成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } else {
            returnModel.setData("");
            returnModel.setMsg("考勤记录新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @ApiOperation("考勤记录修改")
    @RequestMapping("/up")
    @ResponseBody
    public ReturnModel up(AttendanceRecord attendanceRecord) {
        ReturnModel returnModel = new ReturnModel();
        int i = attendanceRecordService.upEquipment(attendanceRecord);
        if (i == 1) {
            returnModel.setData("");
            returnModel.setMsg("考勤记录修改成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } else {
            returnModel.setData("");
            returnModel.setMsg("考勤记录修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @ApiOperation("园长字段查询")
    @RequestMapping("/selApp")
    @ResponseBody
    public AttendanceRecord selApp(AttendanceRecord attendanceRecord) {
        return attendanceRecordService.selApp(attendanceRecord);
    }


    @ApiOperation("查询考勤记录")
    @RequestMapping("/list")
    @ResponseBody
    public PunchInRecordPage list(AttendanceRecordUtil attendanceRecordUtil, Integer pageNum, Integer pageSize) {
        PunchInRecordPage pageDataResult = new PunchInRecordPage();
        try {
            if (null == pageNum) {
                pageNum = 1;
            }
            if (null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = attendanceRecordService.list(attendanceRecordUtil, pageNum, pageSize);
        } catch (Exception e) {
            logger.info("考勤记录列表查询失败", e);
        }
        return pageDataResult;
    }

    @ApiOperation("打卡详情记录")
    @RequestMapping("/selDetails")
    @ResponseBody
    public ReturnModel selDetails(AttendanceRecordUtil attendanceRecordUtil) {
        try {
            List<AttendanceRecord> attendanceRecords = attendanceRecordService.selDetails(attendanceRecordUtil);
            return new ReturnModel(attendanceRecords, "获取打卡详情记录成功", Constant.STATE_SUCCESS, null);
        } catch (Exception e) {
            return new ReturnModel(null, "获取打卡详情记录失败", Constant.STATE_SUCCESS, null);
        }
    }

    @ApiOperation("统计报表")
    @RequestMapping("/report")
    @ResponseBody
    public ReturnModel report(AttendanceRecordUtil attendanceRecordUtil) {
        try {
            List<StatisticalReport> statisticalReportList = attendanceRecordService.report(attendanceRecordUtil);
            return new ReturnModel(statisticalReportList, "获取统计报表成功", Constant.STATE_SUCCESS, null);
        } catch (Exception e) {
            logger.error("获取结账流水列表失败", e);
            return new ReturnModel(e.getMessage(), "获取统计报表失败", "500", null);
        }
    }

    @ApiOperation("导出列表")
    @RequestMapping("/getExcel")
    @ResponseBody
    public void getExcel(HttpServletResponse Response, AttendanceRecordUtil attendanceRecordUtil) {

        List<AttendanceRecord> attendanceRecords = attendanceRecordService.selDetails(attendanceRecordUtil);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("考勤记录", "考勤列表", AttendanceRecord.class, attendanceRecords), "考勤记录" + dateTime + ".xls", Response);
    }


    @ApiOperation("机器人考勤查询")
    @RequestMapping("/RobotAttendance")
    @ResponseBody
    public ReturnModel RobotAttendance(Integer pageNum, Integer pageSize, RobotAttendance robotAttendance) {
        ReturnModel returnModel = new ReturnModel();
        try {
            if (null == pageNum) {
                pageNum = 1;
            }
            if (null == pageSize) {
                pageSize = 10;
            }
            returnModel = attendanceRecordService.robotAttendance(pageNum, pageSize, robotAttendance);
            returnModel.setMsg("success");
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setType("");
        } catch (Exception e) {
            logger.info("景区拓展管理列表查询失败", e);
        }
        return returnModel;
    }

    @ApiOperation("机器人版本修改")
    @RequestMapping("/updateRobotAttendance")
    @ResponseBody
    public ReturnModel updateRobotAttendance(RobotAttendance robotAttendance) {
        ReturnModel returnModel = new ReturnModel();
        int i = attendanceRecordService.updateRobotAttendance(robotAttendance);
        if (i == 1) {
            returnModel.setData("");
            returnModel.setMsg("考勤记录修改成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } else {
            returnModel.setData("");
            returnModel.setMsg("考勤记录修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @ApiOperation("添加机器人考勤")
    @RequestMapping("/insertRobotAttendance")
    @ResponseBody
    public ReturnModel insertRobotAttendance(RobotAttendance robotAttendance) {
        ReturnModel returnModel = new ReturnModel();
        int i = attendanceRecordService.insertRobotAttendance(robotAttendance);
        if (i == 1) {
            returnModel.setData("");
            returnModel.setMsg("考勤记录添加成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } else {
            returnModel.setData("");
            returnModel.setMsg("考勤记录添加失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }

    }

    @ApiOperation("导出机器人考勤")
    @RequestMapping("/exportRobotAttendance")
    @ResponseBody
    public void exportRobotAttendance(HttpServletResponse response, RobotAttendance robotAttendance) {
        List<RobotAttendance> list = attendanceRecordService.robotAttendances(robotAttendance);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("机器人考勤信息", "考勤信息", RobotAttendance.class, list), "考勤信息" + dateTime, response);
    }

    @ApiOperation("删除机器人考勤")
    @RequestMapping("/deleteRobotAttendance")
    @ResponseBody
    public ReturnModel deleteRobotAttendance(RobotAttendance robotAttendance) {
        ReturnModel returnModel = new ReturnModel();
        int i = attendanceRecordService.deleteRobotAttendance(robotAttendance);
        if (i == 1) {
            returnModel.setData("");
            returnModel.setMsg("考勤删除成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } else {
            returnModel.setData("");
            returnModel.setMsg("考勤删除失败");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @ApiOperation("员工考勤查询")
    @RequestMapping("/selectEmployeeAttendance")
    @ResponseBody
    public ReturnModel selectEmployeeAttendance(Integer pageNum, Integer pageSize, EmployeeAttendance employeeAttendance) {
        log.info(pageNum.toString(),pageSize.toString(),employeeAttendance);
        ReturnModel returnModel = new ReturnModel();
        try {
            if (null == pageNum) {
                pageNum = 1;
            }
            if (null == pageSize) {
                pageSize = 10;
            }
            returnModel = attendanceRecordService.selectEmployeeAttendance(pageNum, pageSize, employeeAttendance);
            returnModel.setMsg("success");
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setType("");
        } catch (Exception e) {
            logger.info("员工考勤列表查询失败", e);
        }
        return returnModel;
    }

    @ApiOperation("员工考勤删除")
    @RequestMapping("/deleteEmployeeAttendance")
    @ResponseBody
    public ReturnModel deleteEmployeeAttendance(EmployeeAttendance employeeAttendance) {
        ReturnModel returnModel = new ReturnModel();
        int i = attendanceRecordService.deleteEmployeeAttendance(employeeAttendance);
        if (i == 1) {
            returnModel.setData("");
            returnModel.setMsg("考勤时间删除成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } else {
            returnModel.setData("");
            returnModel.setMsg("考勤时间删除失败");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @ApiOperation("员工考勤导出")
    @RequestMapping("/exportEmployeeAttendance")
    @ResponseBody
    public void exportEmployeeAttendance(HttpServletResponse response, EmployeeAttendance employeeAttendance) throws ParseException {
        List<EmployeeAttendance> employeeAttendanceList = null;
        employeeAttendanceList = attendanceRecordService.selectEmployeeAttendances(employeeAttendance);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("员工考勤信息", "考勤信息", EmployeeAttendance.class, employeeAttendanceList), "外设信息" + dateTime, response);
    }


    @ApiOperation("运营考勤")
    @RequestMapping("/selectSurvey")
    @ResponseBody
    public ReturnModel selectSurvey(OperationAttendance operation, AttendanceTime attendanceTime) {
        OperationAttendance operationAttendance = null;
        try {
            operationAttendance = attendanceRecordService.selectSurvey(operation, attendanceTime);
            return new ReturnModel(operationAttendance, "考勤获取成功", Constant.STATE_SUCCESS, null);
        } catch (Exception e) {
            logger.error("考勤获取失败", e);
            return new ReturnModel("", "考勤获取失败", Constant.STATE_FAILURE, null);
        }
    }

    @ApiOperation("考勤时间添加")
    @RequestMapping("/addAttendanceTime")
    @ResponseBody
    public ReturnModel addAttendanceTime(AttendanceTime attendanceTime) {
        ReturnModel returnModel = new ReturnModel();
        int i = attendanceTimeService.addAttendanceTime(attendanceTime);
        if (i == 1) {
            returnModel.setData("");
            returnModel.setMsg("考勤时间添加成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } else {
            returnModel.setData("");
            returnModel.setMsg("考勤时间添加失败");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @ApiOperation("考勤时间编辑")
    @RequestMapping("/upAttendanceTime")
    @ResponseBody
    public ReturnModel upAttendanceTime(AttendanceTime attendanceTime) {
        ReturnModel returnModel = new ReturnModel();
        int i = attendanceTimeService.upAttendanceTime(attendanceTime);
        if (i == 1) {
            returnModel.setData("");
            returnModel.setMsg("考勤编辑成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } else {
            returnModel.setData("");
            returnModel.setMsg("考勤编辑失败");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @ApiOperation("考勤时间删除")
    @RequestMapping("/deAttendanceTime")
    @ResponseBody
    public ReturnModel deAttendanceTime(AttendanceTime attendanceTime) {
        ReturnModel returnModel = new ReturnModel();
        int i = attendanceTimeService.deAttendanceTime(attendanceTime);
        if (i == 1) {
            returnModel.setData("");
            returnModel.setMsg("考勤删除成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } else {
            returnModel.setData("");
            returnModel.setMsg("考勤删除失败");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @ApiOperation("考勤时间查询")
    @RequestMapping("/selectAttendanceTime")
    @ResponseBody
    public ReturnModel selectAttendanceTime(int pageNum,int pageSize,AttendanceTime attendanceTime) {
        ReturnModel returnModel = new ReturnModel();
        try {
            returnModel = attendanceTimeService.selectAttendanceTime(pageNum,pageSize,attendanceTime);
            returnModel.setMsg("success");
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setType("");
            return returnModel;
        } catch (Exception e) {
            logger.error("考勤时间查询失败", e);
            returnModel.setMsg("message");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType("");
            return returnModel;
        }
    }
}

