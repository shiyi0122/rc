package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.dao.AttendanceRecordMapper;
import com.hna.hka.archive.management.assetsSystem.dao.AttendanceTimeMapper;
import com.hna.hka.archive.management.assetsSystem.dao.RobotAttendanceMapper;
import com.hna.hka.archive.management.assetsSystem.model.*;
import com.hna.hka.archive.management.assetsSystem.service.AttendanceRecordService;
import com.hna.hka.archive.management.system.dao.SysRobotIdMapper;
import com.hna.hka.archive.management.system.model.SysRobot;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.ReturnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class AttendanceRecordServiceImpl implements AttendanceRecordService {


    @Autowired
    private AttendanceRecordMapper attendanceRecordMapper;
    @Autowired
    private RobotAttendanceMapper robotAttendanceMapper;
    @Autowired
    private AttendanceTimeMapper attendanceTimeMapper;

    //修改机器人考勤字段
    @Override
    public int upEquipment(AttendanceRecord attendanceRecord) {
        int i = attendanceRecordMapper.upEquipment(attendanceRecord);
        if (i > 0) {
            return 1;
        } else {
            return -1;
        }

    }

    //查询机器人考勤字段
    @Override
    public AttendanceRecord selApp(AttendanceRecord attendanceRecord) {
        AttendanceRecord attendanceRecord1 = attendanceRecordMapper.selApp(attendanceRecord);
        Integer robottotalNum = attendanceRecordMapper.robotNum(attendanceRecord.getScenicSpotId(), null);
        Integer robotNum = attendanceRecordMapper.robotNum(attendanceRecord.getScenicSpotId(), "10");
        Integer robotNum1 = robottotalNum - robotNum;
        attendanceRecord1.setTotalNumberRobot(robottotalNum.toString());
        attendanceRecord1.setFaultRobotNumber(robotNum1.toString());
        attendanceRecord1.setNormalRobotNumber(robotNum.toString());
        return attendanceRecord1;
    }

    //新增打卡记录
    @Override
    public int add(AttendanceRecord attendanceRecord) {
//        是兼职员工
        if (attendanceRecord.getEmployeeType().equals("2")) {
//            生成考勤记录ID
            attendanceRecord.setRecordId(IdUtils.getSeqId());
//            生成当前时间
            attendanceRecord.setCreateDate(DateUtil.crutDate());
//
            attendanceRecordMapper.addPeoPle(attendanceRecord);
            return 1;
//        非兼职员工
        } else {
//            生成考勤记录ID
            attendanceRecord.setRecordId(IdUtils.getSeqId());
//            生成创建时间
            attendanceRecord.setCreateDate(DateUtil.crutDate());
            attendanceRecordMapper.addPeoPle(attendanceRecord);
//
            if (attendanceRecord.getTodayDate() != null) {
                attendanceRecordMapper.addEquipment(attendanceRecord);
            }
            return 1;
        }
    }

    //后台查询考勤记录
    @Override
    public PunchInRecordPage list(AttendanceRecordUtil attendanceRecordUtil, Integer pageNum, Integer pageSize) {
        PunchInRecordPage punchInRecordPage = new PunchInRecordPage();
        PageHelper.startPage(pageNum, pageSize);
        int ountAttendancec = 0, dates = 0, totalpeople = 0;
        double tatisticalAttendances = 0.00;
        List<PunchInRecord> punchInRecordList = new ArrayList<>();
        if (attendanceRecordUtil.getDateType().equals("1")) {
            String startYear = attendanceRecordUtil.getStartDate();
            String startMonth = startYear + "-" + "01";
            String endYear = attendanceRecordUtil.getEndDate();
            String endMonth = endYear + "-" + "12";
            String startDay = startMonth + "-" + "01";
            String monthDay = String.valueOf(DateUtil.getMonthDay(endMonth));
            String endDay = endMonth + "-" + monthDay;
            attendanceRecordUtil.setStartDate(startDay);
            attendanceRecordUtil.setEndDate(endDay);
        }
        List<AttendanceRecord> users = attendanceRecordMapper.selUser(attendanceRecordUtil);
        for (AttendanceRecord user : users) {
            if (user.getEmployeeType().equals("2")) {
                PunchInRecord punch = new PunchInRecord();
                List<AttendanceRecord> list = attendanceRecordMapper.list(attendanceRecordUtil);
                if (list.size() > 0) {
                    AttendanceRecord attendanceRecord = list.get(0);
                    for (int j = 0; j < 3; j++) {
                        if (j == 0) {
                            attendanceRecordUtil.setType("1");
                            int shijiAttendanceDays = attendanceRecordMapper.selTotal(attendanceRecordUtil);
                            punch.setShijiAttendanceDays(shijiAttendanceDays);
                            punch.setDaysOfAttendance(shijiAttendanceDays);
                            punch.setAttendance(1.00);
                        } else if (j == 1) {
                            attendanceRecordUtil.setType("1");
                            attendanceRecordUtil.setEmployeeAttendance("2");
                            int lateDays = attendanceRecordMapper.selTotal(attendanceRecordUtil);
                            punch.setLateDays(lateDays);
                        } else {
                            attendanceRecordUtil.setType("2");
                            attendanceRecordUtil.setEmployeeAttendance("3");
                            int leaveEarly = attendanceRecordMapper.selTotal(attendanceRecordUtil);
                            punch.setLeaveEarly(leaveEarly);
                        }
                    }
                    punch.setDate(attendanceRecord.getTodayDate());
                    punch.setScenicSpotName(attendanceRecord.getScenicSpotName());
                    punch.setUserName(attendanceRecord.getUserName());
                    punch.setEmployeeType(attendanceRecord.getEmployeeType());
                    punchInRecordList.add(punch);
                    if (punchInRecordList.size() > 0) {
                        PageInfo<PunchInRecord> pageInfo = new PageInfo<>(punchInRecordList);
                        punchInRecordPage.setList(pageInfo.getList());
                        punchInRecordPage.setTotals((int) pageInfo.getTotal());
                        punchInRecordPage.setDates(DateUtil.currentDateTime());
                        punchInRecordPage.setTatisticalAttendances(1.00);
                        punchInRecordPage.setOuntAttendancec(list.size());


                    }
                }
            } else {
                if (attendanceRecordUtil.getDateType().equals("1")) {
//                    String startYear = attendanceRecordUtil.getStartDate();
//                    String startMonth=startYear+"-"+"01"+"01";
//                    String endYear = attendanceRecordUtil.getEndDate();
//                    String endMonth=endYear+"-"+"12";
//                    String startDay=startMonth+"-"+"01";
//                    String monthDay = String.valueOf(DateUtil.getMonthDay(endMonth));
//                    String endDay=endMonth+"-"+monthDay;
//                    attendanceRecordUtil.setStartDate(startDay);
//                    attendanceRecordUtil.setEndDate(endDay);
                    PunchInRecord punch = new PunchInRecord();
                    attendanceRecordUtil.setUserId(user.getUserId());
                    attendanceRecordUtil.setType("1");
                    attendanceRecordUtil.setEmployeeAttendance("");
                    List<AttendanceRecord> list = attendanceRecordMapper.list(attendanceRecordUtil);
                    if (list.size() > 0) {
                        AttendanceRecord attendanceRecord = list.get(0);
                        try {
                            dates = Integer.parseInt(DateUtil.findDates(attendanceRecordUtil.getStartDate(), attendanceRecordUtil.getEndDate()));
                            int dates1 = Integer.parseInt(DateUtil.findDates(attendanceRecord.getCreateDate(), attendanceRecordUtil.getEndDate()));
                            int i = attendanceRecord.getCreateDate().compareTo(attendanceRecordUtil.getStartDate());
                            for (int j = 0; j < 3; j++) {
                                if (j == 0) {
                                    attendanceRecordUtil.setType("1");
                                    attendanceRecordUtil.setEmployeeAttendance("");
                                    int shijiAttendanceDays = attendanceRecordMapper.selTotal(attendanceRecordUtil);
                                    ountAttendancec += shijiAttendanceDays;
                                    if (i > 0) {
                                        int totalDay = (dates1 - (int) Math.floor(dates1 / 7));
                                        punch.setDaysOfAttendance(totalDay);
                                        punch.setShijiAttendanceDays(shijiAttendanceDays);
                                        double attendance = ((double) shijiAttendanceDays / totalDay);
                                        punch.setAttendance(attendance);
                                        totalpeople += dates1;
                                    } else {
                                        int totalDay = (dates - (int) Math.floor(dates / 7));
                                        punch.setDaysOfAttendance(totalDay);
                                        punch.setShijiAttendanceDays(shijiAttendanceDays);
                                        double attendance = ((double) shijiAttendanceDays / totalDay);
                                        punch.setAttendance(attendance);
                                        totalpeople += dates;
                                    }
                                } else if (j == 1) {
                                    attendanceRecordUtil.setType("1");
                                    attendanceRecordUtil.setEmployeeAttendance("2");
                                    int lateDays = attendanceRecordMapper.selTotal(attendanceRecordUtil);
                                    punch.setLateDays(lateDays);
                                } else {
                                    attendanceRecordUtil.setType("2");
                                    attendanceRecordUtil.setEmployeeAttendance("3");
                                    int leaveEarly = attendanceRecordMapper.selTotal(attendanceRecordUtil);
                                    punch.setLeaveEarly(leaveEarly);
                                }
                            }
                            punch.setDate(attendanceRecordUtil.getStartDate() + "-" + attendanceRecordUtil.getEndDate());
                            punch.setScenicSpotName(attendanceRecord.getScenicSpotName());
                            punch.setUserName(attendanceRecord.getUserName());
                            punch.setEmployeeType(attendanceRecord.getEmployeeType());
                            punchInRecordList.add(punch);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    String startMonth = attendanceRecordUtil.getStartDate();
                    String startDay = startMonth + "-" + "01";
                    String endMonth = attendanceRecordUtil.getEndDate();
                    String monthDay = String.valueOf(DateUtil.getMonthDay(endMonth));
                    String endDay = endMonth + "-" + monthDay;
                    attendanceRecordUtil.setStartDate(startDay);
                    attendanceRecordUtil.setEndDate(endDay);
                    PunchInRecord punch = new PunchInRecord();
                    attendanceRecordUtil.setUserId(user.getUserId());
                    attendanceRecordUtil.setType("1");
                    attendanceRecordUtil.setEmployeeAttendance("");
                    List<AttendanceRecord> list = attendanceRecordMapper.list(attendanceRecordUtil);
                    if (list.size() > 0) {
                        AttendanceRecord attendanceRecord = list.get(0);
                        try {
                            dates = Integer.parseInt(DateUtil.findDates(attendanceRecordUtil.getStartDate(), attendanceRecordUtil.getEndDate()));
                            int dates1 = Integer.parseInt(DateUtil.findDates(attendanceRecord.getCreateDate(), attendanceRecordUtil.getEndDate()));
                            int i = attendanceRecord.getCreateDate().compareTo(attendanceRecordUtil.getStartDate());
                            for (int j = 0; j < 3; j++) {
                                if (j == 0) {
                                    attendanceRecordUtil.setType("1");
                                    attendanceRecordUtil.setEmployeeAttendance("");
                                    int shijiAttendanceDays = attendanceRecordMapper.selTotal(attendanceRecordUtil);
                                    ountAttendancec += shijiAttendanceDays;
                                    if (i > 0) {
                                        int totalDay = (dates1 - (int) Math.floor(dates1 / 7));
                                        punch.setDaysOfAttendance(totalDay);
                                        punch.setShijiAttendanceDays(shijiAttendanceDays);
                                        double attendance = ((double) shijiAttendanceDays / totalDay);
                                        punch.setAttendance(attendance);
                                        totalpeople += dates1;
                                    } else {
                                        int totalDay = (dates - (int) Math.floor(dates / 7));
                                        punch.setDaysOfAttendance(totalDay);
                                        punch.setShijiAttendanceDays(shijiAttendanceDays);
                                        double attendance = ((double) shijiAttendanceDays / totalDay);
                                        punch.setAttendance(attendance);
                                        totalpeople += dates;
                                    }
                                } else if (j == 1) {
                                    attendanceRecordUtil.setType("1");
                                    attendanceRecordUtil.setEmployeeAttendance("2");
                                    int lateDays = attendanceRecordMapper.selTotal(attendanceRecordUtil);
                                    punch.setLateDays(lateDays);
                                } else {
                                    attendanceRecordUtil.setType("2");
                                    attendanceRecordUtil.setEmployeeAttendance("3");
                                    int leaveEarly = attendanceRecordMapper.selTotal(attendanceRecordUtil);
                                    punch.setLeaveEarly(leaveEarly);
                                }
                            }
                            punch.setDate(attendanceRecordUtil.getStartDate() + "-" + attendanceRecordUtil.getEndDate());
                            punch.setScenicSpotName(attendanceRecord.getScenicSpotName());
                            punch.setUserName(attendanceRecord.getUserName());
                            punch.setEmployeeType(attendanceRecord.getEmployeeType());
                            punchInRecordList.add(punch);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
               /* PunchInRecord punch = new PunchInRecord();
                attendanceRecordUtil.setUserId(user.getUserId());
                attendanceRecordUtil.setType("1");
                attendanceRecordUtil.setEmployeeAttendance("");
                List<AttendanceRecord> list = attendanceRecordMapper.list(attendanceRecordUtil);
                if (list.size() > 0) {
                    AttendanceRecord attendanceRecord = list.get(0);
                    try {
                        dates = Integer.parseInt(DateUtil.findDates(attendanceRecordUtil.getStartDate(), attendanceRecordUtil.getEndDate()));
                        int dates1 = Integer.parseInt(DateUtil.findDates(attendanceRecord.getCreateDate(), attendanceRecordUtil.getEndDate()));
                        int i = attendanceRecord.getCreateDate().compareTo(attendanceRecordUtil.getStartDate());
                        for (int j = 0; j < 3; j++) {
                            if (j == 0) {
                                attendanceRecordUtil.setType("1");
                                attendanceRecordUtil.setEmployeeAttendance("");
                                int shijiAttendanceDays = attendanceRecordMapper.selTotal(attendanceRecordUtil);
                                ountAttendancec += shijiAttendanceDays;
                                if (i>0){
                                    int totalDay = (dates1 - (int) Math.floor(dates1 / 7));
                                    punch.setDaysOfAttendance(totalDay);
                                    punch.setShijiAttendanceDays(shijiAttendanceDays);
                                    double attendance = ((double) shijiAttendanceDays / totalDay);
                                    punch.setAttendance(attendance);
                                    totalpeople += dates1;
                                }else {
                                    int totalDay = (dates - (int) Math.floor(dates / 7));
                                    punch.setDaysOfAttendance(totalDay);
                                    punch.setShijiAttendanceDays(shijiAttendanceDays);
                                    double attendance = ((double) shijiAttendanceDays / totalDay);
                                    punch.setAttendance(attendance);
                                    totalpeople += dates;
                                }
                            } else if (j == 1) {
                                attendanceRecordUtil.setType("1");
                                attendanceRecordUtil.setEmployeeAttendance("2");
                                int lateDays = attendanceRecordMapper.selTotal(attendanceRecordUtil);
                                punch.setLateDays(lateDays);
                            } else {
                                attendanceRecordUtil.setType("2");
                                attendanceRecordUtil.setEmployeeAttendance("3");
                                int leaveEarly = attendanceRecordMapper.selTotal(attendanceRecordUtil);
                                punch.setLeaveEarly(leaveEarly);
                            }
                        }
                        punch.setDate(attendanceRecordUtil.getStartDate() + "-" + attendanceRecordUtil.getEndDate());
                        punch.setScenicSpotName(attendanceRecord.getScenicSpotName());
                        punch.setUserName(attendanceRecord.getUserName());
                        punch.setEmployeeType(attendanceRecord.getEmployeeType());
                        punchInRecordList.add(punch);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }*/
            }
        }
        tatisticalAttendances = ((double) ountAttendancec / totalpeople);
        if (punchInRecordList.size() > 0) {
            PageInfo<PunchInRecord> pageInfo = new PageInfo<>(punchInRecordList);
            punchInRecordPage.setList(pageInfo.getList());
            punchInRecordPage.setTotals((int) pageInfo.getTotal());
            punchInRecordPage.setDates(DateUtil.currentDateTime());
            punchInRecordPage.setTatisticalAttendances(tatisticalAttendances);
            punchInRecordPage.setOuntAttendancec(ountAttendancec);
        }
        return punchInRecordPage;
    }

    //打卡记录详情
    @Override
    public List<AttendanceRecord> selDetails(AttendanceRecordUtil attendanceRecordUtil) {
        return attendanceRecordMapper.selDetails(attendanceRecordUtil);
    }

    //统计报表
    @Override
    public List<StatisticalReport> report(AttendanceRecordUtil attendanceRecordUtil) {
        List<StatisticalReport> statisticalReports = new ArrayList<>();
        attendanceRecordUtil.setEmployeeType("1");
        if (attendanceRecordUtil.getDateType().equals("1")) {
            String startYear = attendanceRecordUtil.getStartDate();
            String startMonth = startYear + "-" + "01";
            String endYear = attendanceRecordUtil.getEndDate();
            String endMonth = endYear + "-" + "12";
            int ountAttendancec = 0;
            double tatisticalAttendances = 0.00;
            try {
                List<String> months = DateUtil.betweenMonths(startMonth, endMonth);
                for (int i = 0; i < months.size(); i++) {
                    StatisticalReport statisticalReport = new StatisticalReport();
                    attendanceRecordUtil.setStartDate(months.get(i));
                    String s = null;
                    int dates = 0;
                    if (i == 11) {
                        s = DateUtil.addMouth(months.get(i), 1);
                        attendanceRecordUtil.setEndDate(s);
                    } else {
                        attendanceRecordUtil.setEndDate(months.get(i + 1));
                    }
                    List<AttendanceRecord> attendanceRecords = attendanceRecordMapper.selUser(attendanceRecordUtil);
                    ountAttendancec = attendanceRecordMapper.selTotal(attendanceRecordUtil);
                    if (i == 11) {
                        dates = DateUtil.getMonthDay(s);
                    } else {
                        dates = DateUtil.getMonthDay(months.get(i));
                    }
                    tatisticalAttendances = (double) ountAttendancec / dates;
                    if (i == 11) {
                        String date = months.get(i) + "-" + s;
                        statisticalReport.setDates(date);
                    } else {
                        String date = months.get(i) + "-" + months.get(i + 1);
                        statisticalReport.setDates(date);
                    }
                    statisticalReport.setOuntAttendancec(ountAttendancec);
                    statisticalReport.setTatisticalAttendances(tatisticalAttendances);
                    statisticalReports.add(statisticalReport);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (attendanceRecordUtil.getDateType().equals("2")) {
            List<AttendanceRecord> attendanceRecords = attendanceRecordMapper.selUser(attendanceRecordUtil);
            String startMonth = attendanceRecordUtil.getStartDate();
            String startDay = startMonth + "-" + "01";
            String endMonth = attendanceRecordUtil.getEndDate();
            String monthDay = String.valueOf(DateUtil.getMonthDay(endMonth));
            String endDay = endMonth + "-" + monthDay;
            int ountAttendancec = 0;
            double tatisticalAttendances = 0.00;
            try {
                List<String> dates = DateUtil.betweenDays(startDay, endDay);
                for (int i = 0; i < dates.size(); i++) {
                    StatisticalReport statisticalReport = new StatisticalReport();
                    attendanceRecordUtil.setStartDate(dates.get(i));
                    String s = null;
                    if (i + 1 == dates.size()) {
                        s = DateUtil.addDay(endDay, 1);
                        attendanceRecordUtil.setEndDate(s);
                    } else {
                        attendanceRecordUtil.setEndDate(dates.get(i + 1));
                    }
                    ountAttendancec = attendanceRecordMapper.selTotal(attendanceRecordUtil);
                    if (attendanceRecords.size() == 0) {
                        tatisticalAttendances = 0.0;
                    } else {
                        tatisticalAttendances = (double) ountAttendancec / (attendanceRecords.size());
                    }
                    if (i + 1 == dates.size()) {
                        String date = dates.get(i) + "-" + s;
                        statisticalReport.setDates(date);
                    } else {
                        String date = dates.get(i) + "-" + dates.get(i + 1);
                        statisticalReport.setDates(date);
                    }
                    statisticalReport.setOuntAttendancec(ountAttendancec);
                    statisticalReport.setTatisticalAttendances(tatisticalAttendances);
                    statisticalReports.add(statisticalReport);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            int ountAttendancec = 0;
            double tatisticalAttendances = 0.00;
            int months = 0, totaldates = 0;
            List<AttendanceRecord> spotNameList = attendanceRecordMapper.selSpotName(attendanceRecordUtil);
            List<AttendanceRecord> attendanceRecords = attendanceRecordMapper.selUser(attendanceRecordUtil);
            for (AttendanceRecord soptName : spotNameList) {
                StatisticalReport statisticalReport = new StatisticalReport();
                String startDate = attendanceRecordUtil.getStartDate();
                String startDay = startDate + "-" + "01";
                String endDate = attendanceRecordUtil.getEndDate();
                String monthDay = String.valueOf(DateUtil.getMonthDay(endDate));
                String endDay = endDate + "-" + monthDay;
                ountAttendancec = attendanceRecordMapper.selTotal(attendanceRecordUtil);
                try {
                    months = Integer.parseInt(DateUtil.findMonths(startDate, endDate));
                    totaldates = Integer.parseInt(DateUtil.findDates(startDay, endDay));
                    for (int i = 0; i < months; i++) {
                        totaldates = totaldates - 4;
                    }
                    if (attendanceRecords.size() == 0) {
                        tatisticalAttendances = 0.0;
                    } else {
                        tatisticalAttendances = (double) ountAttendancec / (totaldates * attendanceRecords.size());
                    }
                    statisticalReport.setScenicSpotName(soptName.getScenicSpotName());
                    statisticalReport.setOuntAttendancec(ountAttendancec);
                    statisticalReport.setTatisticalAttendances(tatisticalAttendances);
                    statisticalReports.add(statisticalReport);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return statisticalReports;
    }

    @Autowired
    private SysRobotIdMapper sysRobotIdMapper;

    @Override
    public ReturnModel robotAttendance(Integer pageNum, Integer pageSize, RobotAttendance robotAttendance) {
        List<RobotAttendance> robotAttendanceList = new ArrayList<>();
        ReturnModel returnModel = new ReturnModel();
        PageHelper.startPage(pageNum, pageSize);
//        查询有无时间区间
        if (robotAttendance.getStartDate() != null && robotAttendance.getEndDate() != null) {
            robotAttendanceList = robotAttendanceMapper.RobotAttendance(robotAttendance);
            for (int i = 0; i < robotAttendanceList.size(); i++) {
                Long robotCode = robotAttendanceList.get(i).getRobotCode();
                SysRobot byCode = sysRobotIdMapper.getByCode(robotCode.toString());
                if (byCode == null){
                    robotAttendanceList.get(i).setErrorRecordsAffect("2");
                }else {
                    robotAttendanceList.get(i).setErrorRecordsAffect(byCode.getErrorRecordsAffect());
                }
            }
            robotAttendanceList.stream().forEach(s -> s.setStartDate(robotAttendance.getStartDate()));
            robotAttendanceList.stream().forEach(s -> s.setEndDate(robotAttendance.getEndDate()));
            for (RobotAttendance attendance : robotAttendanceList) {
                if (Objects.equals(attendance.getRecordId(), null)) {
                    attendance.setRobotStatus("关机");
                } else {
                    attendance.setRobotStatus("开机");
                }
            }
            if (robotAttendanceList.size() != 0) {
                PageInfo<RobotAttendance> pageInfo = new PageInfo<>(robotAttendanceList);
                returnModel.setData(pageInfo.getList());
                returnModel.setTotal((int) pageInfo.getTotal());
            }
            return returnModel;
        } else {
            robotAttendanceList = robotAttendanceMapper.RobotAttendances(robotAttendance);
            for (int i = 0; i < robotAttendanceList.size(); i++) {
                Long robotCode = robotAttendanceList.get(i).getRobotCode();
                SysRobot byCode = sysRobotIdMapper.getByCode(robotCode.toString());
                if (byCode == null){
                    robotAttendanceList.get(i).setErrorRecordsAffect("2");
                }else {
                    robotAttendanceList.get(i).setErrorRecordsAffect(byCode.getErrorRecordsAffect());
                }
            }

            LocalDate date = LocalDate.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String format = dateTimeFormatter.format(date);
            robotAttendanceList.stream().forEach(employeeAttendance1 -> employeeAttendance1.setStartDate(format + " 00:00:00"));
            robotAttendanceList.stream().forEach(employeeAttendance1 -> employeeAttendance1.setEndDate(format + " 24:00:00"));
            for (RobotAttendance attendance : robotAttendanceList) {
                if (Objects.equals(attendance.getRecordId(), null)) {
                    attendance.setRobotStatus("关机");
                } else {
                    attendance.setRobotStatus("开机");
                }
            }
            if (robotAttendanceList.size() != 0) {
                PageInfo<RobotAttendance> pageInfo = new PageInfo<>(robotAttendanceList);
                returnModel.setData(pageInfo.getList());
                returnModel.setTotal((int) pageInfo.getTotal());
            }
            return returnModel;
        }
    }

    @Override
    public int updateRobotAttendance(RobotAttendance robotAttendance) {
        int i = robotAttendanceMapper.updateRobotAttendance(robotAttendance);
        return i;
    }

    @Override
    public int insertRobotAttendance(RobotAttendance robotAttendance) {
//        生成考勤记录id
        robotAttendance.setRecordId(IdUtils.getSeqId());
////        生成创建时间
//        LocalDate date = LocalDate.now();
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss:mm");
//        String format = dateTimeFormatter.format(date);
//        robotAttendance.setCreateDate(format);
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        robotAttendance.setCreateDate(format);
        if (Objects.equals(robotAttendance.getRobotStartTime(), null)) {
            robotAttendance.setRobotStartTime(format);
        }
        int i = robotAttendanceMapper.insertRobotAttendance(robotAttendance);
        return i;
    }

    @Override
    public int robotAttendance2(RobotAttendance robotAttendance) {
        int i = robotAttendanceMapper.RobotAttendance2(robotAttendance);
        return i;
    }

    @Override
    public ReturnModel selectEmployeeAttendance(Integer pageNum, Integer pageSize, EmployeeAttendance employeeAttendance) throws ParseException {
        List<EmployeeAttendance> employeeAttendanceList = new ArrayList<>();
        ReturnModel returnModel = new ReturnModel();
        PageHelper.startPage(pageNum, pageSize);
//        查询有时间区间的
        if ((employeeAttendance.getStartDate() != null&&employeeAttendance.getStartDate().length()<1) && employeeAttendance.getEndDate() != null&&employeeAttendance.getEndDate().length()<1) {
            employeeAttendanceList = attendanceRecordMapper.selectEmployeeAttendance(employeeAttendance);
            employeeAttendanceList.stream().forEach(employeeAttendance1 -> employeeAttendance1.setStartDate(employeeAttendance.getStartDate()));
            employeeAttendanceList.stream().forEach(employeeAttendance1 -> employeeAttendance1.setEndDate(employeeAttendance.getEndDate()));
            for (EmployeeAttendance attendance : employeeAttendanceList) {
                if (Objects.equals(attendance.getRecordId(), null)) {
                    attendance.setSignInOrNot("0");
                } else {
                    attendance.setSignInOrNot("1");
                }
            }
            if (employeeAttendanceList.size() != 0) {
                PageInfo<EmployeeAttendance> pageInfo = new PageInfo<>(employeeAttendanceList);
                returnModel.setData(pageInfo.getList());
                returnModel.setTotal((int) pageInfo.getTotal());
            }
            return returnModel;
        } else {
//            查询无时间区间的，查询当天的
            employeeAttendanceList = attendanceRecordMapper.selectEmployeeAttendances(employeeAttendance);
//            获取当前的时间戳
            LocalDate date = LocalDate.now();
//            转化为 固定格式的日期
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String format = date.format(formatter);
//            进行赋值
            employeeAttendanceList.stream().forEach(employeeAttendance1 -> employeeAttendance1.setStartDate(format + " 00:00:00"));
            employeeAttendanceList.stream().forEach(employeeAttendance1 -> employeeAttendance1.setEndDate(format + " 24:00:00"));
            for (EmployeeAttendance attendance : employeeAttendanceList) {
                if (Objects.equals(attendance.getRecordId(), null)) {
                    attendance.setSignInOrNot("0");
                } else {
                    attendance.setSignInOrNot("1");
                }
            }
            if (employeeAttendanceList.size() != 0) {
                PageInfo<EmployeeAttendance> pageInfo = new PageInfo<>(employeeAttendanceList);
                returnModel.setData(pageInfo.getList());
                returnModel.setTotal((int) pageInfo.getTotal());
            }
            return returnModel;
        }
    }

    @Override
    public int deleteEmployeeAttendance(EmployeeAttendance employeeAttendance) {
        int i = attendanceRecordMapper.deleteEmployeeAttendance(employeeAttendance);
        return i;
    }

    @Override
    public List<EmployeeAttendance> getexportEmployeeAttendance(EmployeeAttendance employeeAttendance) {
        List<EmployeeAttendance> employeeAttendanceList = attendanceRecordMapper.selectEmployeeAttendance(employeeAttendance);
        employeeAttendanceList.stream().forEach(employeeAttendance1 -> employeeAttendance1.setStartDate(employeeAttendance.getStartDate()));
        employeeAttendanceList.stream().forEach(employeeAttendance1 -> employeeAttendance1.setEndDate(employeeAttendance.getEndDate()));
        return employeeAttendanceList;
    }

    @Override
    public List<EmployeeAttendance> selectEmployeeAttendances(EmployeeAttendance employeeAttendance) throws ParseException {
        List<EmployeeAttendance> employeeAttendanceList = new ArrayList<>();
//        查询有时间区间的
        if ((employeeAttendance.getStartDate() != null&&employeeAttendance.getStartDate().length()<1) && employeeAttendance.getEndDate() != null&&employeeAttendance.getEndDate().length()<1) {
            employeeAttendanceList = attendanceRecordMapper.selectEmployeeAttendance(employeeAttendance);
            employeeAttendanceList.stream().forEach(employeeAttendance1 -> employeeAttendance1.setStartDate(employeeAttendance.getStartDate()));
            employeeAttendanceList.stream().forEach(employeeAttendance1 -> employeeAttendance1.setEndDate(employeeAttendance.getEndDate()));
            for (EmployeeAttendance attendance : employeeAttendanceList) {
                if (Objects.equals(attendance.getRecordId(), null)) {
                    attendance.setSignInOrNot("0");
                } else {
                    attendance.setSignInOrNot("1");
                }
            }
            return employeeAttendanceList;
        } else {
//            查询无时间区间的，查询当天的
            employeeAttendanceList = attendanceRecordMapper.selectEmployeeAttendances(employeeAttendance);
//            获取当前的时间戳
            LocalDate date = LocalDate.now();
//
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String format = date.format(formatter);

            employeeAttendanceList.stream().forEach(employeeAttendance1 -> employeeAttendance1.setStartDate(format + " 00:00:00"));
            employeeAttendanceList.stream().forEach(employeeAttendance1 -> employeeAttendance1.setEndDate(format + " 24:00:00"));
            for (EmployeeAttendance attendance : employeeAttendanceList) {
                if (Objects.equals(attendance.getRecordId(), null)) {
                    attendance.setSignInOrNot("0");
                } else {
                    attendance.setSignInOrNot("1");
                }
            }
            return employeeAttendanceList;
        }
    }

    @Override
    public List<RobotAttendance> robotAttendances(RobotAttendance robotAttendance) {
        List<RobotAttendance> robotAttendanceList = new ArrayList<>();
//        查询有无时间区间
        if (robotAttendance.getStartDate() != null && robotAttendance.getEndDate() != null) {
            robotAttendanceList = robotAttendanceMapper.RobotAttendance(robotAttendance);
            robotAttendanceList.stream().forEach(s -> s.setStartDate(robotAttendance.getStartDate()));
            robotAttendanceList.stream().forEach(s -> s.setEndDate(robotAttendance.getEndDate()));
            for (RobotAttendance attendance : robotAttendanceList) {
                if (Objects.equals(attendance.getRecordId(), null)) {
                    attendance.setRobotStatus("关机");
                } else {
                    attendance.setRobotStatus("开机");
                }
            }
            return robotAttendanceList;
        } else {
            robotAttendanceList = robotAttendanceMapper.RobotAttendances(robotAttendance);
            LocalDate date = LocalDate.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String format = dateTimeFormatter.format(date);
            robotAttendanceList.stream().forEach(employeeAttendance1 -> employeeAttendance1.setStartDate(format + " 00:00:00"));
            robotAttendanceList.stream().forEach(employeeAttendance1 -> employeeAttendance1.setEndDate(format + " 24:00:00"));
            for (RobotAttendance attendance : robotAttendanceList) {
                if (Objects.equals(attendance.getRecordId(), null)) {
                    attendance.setRobotStatus("关机");
                } else {
                    attendance.setRobotStatus("开机");
                }
            }
            return robotAttendanceList;
        }
    }

    @Override
    public OperationAttendance selectSurvey(OperationAttendance operation,AttendanceTime attendanceTime) {
        OperationAttendance operationAttendance = new OperationAttendance();
        List<RobotAttendance> robotAttendanceList = new ArrayList<>();
        List<RobotVersion> robotVersions = new ArrayList<>();

        List<NumAndTime> numAndTimeList = new ArrayList<>();
        List<Integer> i1 = new ArrayList<>();
//        判断传参是否有景区，有景区则根据景区id获取景区名称返回
        if (Objects.equals(operation.getScenicSpotId(),null)){
            operationAttendance.setScenicSpotName("全部景区");
        }else {
            operation.setScenicSpotId(operation.getScenicSpotId());
            String scenicSpotName = attendanceRecordMapper.selectScenicSpotName(operation.getScenicSpotId());
            operationAttendance.setScenicSpotName(scenicSpotName);
        }
//        获取当前年月日
        String format = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now());

        List<AttendanceTime> attendanceTimeList = attendanceTimeMapper.selectAttendanceTimes(attendanceTime);
//        for (int i = 0; i < attendanceTimeList.size(); i++) {
        for (AttendanceTime time : attendanceTimeList) {
            NumAndTime numAndTime = new NumAndTime();
            numAndTime.setTime(time.getStartDate()+"~"+time.getEndDate());
            operation.setStartDate(format +" "+ time.getStartDate());
            operation.setEndDate(format +" "+ time.getEndDate());
            int i2 = attendanceRecordMapper.timePeriod(operation);
            numAndTime.setNum(i2);
            numAndTimeList.add(numAndTime);
            operationAttendance.setTimePeriod(numAndTimeList);
        }

        operation.setStartDate(format + " 00:00:00");
        operation.setEndDate(format + " 23:59:59");
        robotAttendanceList = robotAttendanceMapper.timePeriod(operation);
//        根据 机器人编号 去重
        List<RobotAttendance> list = robotAttendanceList.stream().collect(
                Collectors.collectingAndThen(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(r -> r.getRobotCode()))), ArrayList::new));
//        获取去重后的集合元素总数
        int totalElements = list.size();
//        进行筛选 机器人考勤主键为null的是未启动的
        List<RobotAttendance> collect = list.stream().distinct().filter(r -> Objects.equals(r.getRecordId(),null)).collect(Collectors.toList());
//        得到未启动的元素总数
        int notStarted = collect.size();
//        机器人总数 - 未启动的总数 = 启动的总数
        int numberOfStarts = totalElements - notStarted;
        operationAttendance.setStartupStatus(numberOfStarts);
        operationAttendance.setClosedState(notStarted);

//        获取数据总数量
        int i6 = robotAttendanceMapper.selectWholeNum(operation);
//        获取全部机器人版本号
        List<RobotVersion> wholeClientVersion = robotAttendanceMapper.wholeClientVersion(operation);

        int num = operationAttendance.getStartupStatus() + operationAttendance.getClosedState() - wholeClientVersion.stream()
                .distinct()
                .map(author -> author.getVersionNum())
                .reduce(0, (result, element) -> result + element);
        for (RobotVersion robotVersion : wholeClientVersion) {
            if (Objects.equals(robotVersion.getClientVersion(),null)){
                robotVersion.setVersionNum(num);
            }
        }
        operationAttendance.setRobotVersionAndNum(wholeClientVersion);
        return operationAttendance;
    }

    @Override
    public int deleteRobotAttendance(RobotAttendance robotAttendance) {
        int i = attendanceRecordMapper.deleteRobotAttendance(robotAttendance);
        return i;
    }
}
