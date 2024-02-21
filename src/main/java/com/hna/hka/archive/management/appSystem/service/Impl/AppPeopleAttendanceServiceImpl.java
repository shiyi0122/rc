package com.hna.hka.archive.management.appSystem.service.Impl;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.dao.SysAppPeopleAttendanceMapper;
import com.hna.hka.archive.management.appSystem.model.IndividualAndAttendance;
import com.hna.hka.archive.management.appSystem.service.AppPeopleAttendanceService;
import com.hna.hka.archive.management.assetsSystem.dao.AddressMapper;
import com.hna.hka.archive.management.assetsSystem.model.Address;
import com.hna.hka.archive.management.assetsSystem.model.AttendanceTime;
import com.hna.hka.archive.management.assetsSystem.model.EmployeeAttendance;
import com.hna.hka.archive.management.assetsSystem.model.RobotAttendance;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.ReturnModel;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName：AppAttendanceRecordServiceImpl
 * @Author: gouteng
 * @Date: 2022-11-24 9:38
 * @Description: 必须描述类做什么事情, 实现什么功能
 */
@Service
public class AppPeopleAttendanceServiceImpl implements AppPeopleAttendanceService {
    @Value("${filePatheGetAttendancePicPaht}")
    private String filePatheGetAttendancePicPaht;

    @Value("${filePatheGetAttendancePicUrl}")
    private String filePatheGetAttendancePicUrl;

    @Autowired
    private SysAppPeopleAttendanceMapper sysAppPeopleAttendanceMapper;

    @Autowired
    private AddressMapper addressMapper;


    @Override
    public int inAttendanceRecord(MultipartFile file, EmployeeAttendance employeeAttendance,String userId,AttendanceTime attendanceTime) {
        List<String> list = new ArrayList<>();
        List<EmployeeAttendance> employeeAttendanceList1 = new ArrayList<>();
        IndividualAndAttendance individualAndAttendance = new IndividualAndAttendance();
//        获取当前年月日
        String format = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now());
//        获取当前时分秒
//        String format1 = DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDate.now());
        String format1 = new SimpleDateFormat("HH:mm:ss").format(new Date());
//        获取所有未禁止的考勤时间，并按照升序排列
        List<AttendanceTime> attendanceTimeList = sysAppPeopleAttendanceMapper.selectAttendanceTimes(attendanceTime);
//        遍历所有考勤时间
        for (int i = 0; i < attendanceTimeList.size(); i++) {
//            判断前端有没有传年月日过来,传了就用传过来的年月日,没传就用当前年月日
//            if (attendanceTime.getStartSpecificDate() == null && attendanceTime.getEndSpecificDate() == null) {
            employeeAttendance.setStartDate(format + " " + attendanceTimeList.get(i).getStartDate());
            employeeAttendance.setEndDate(format + " " + attendanceTimeList.get(i).getEndDate());
//            } else {
//                employeeAttendance.setStartDate(attendanceTime.getStartSpecificDate() + " " + attendanceTimeList.get(i).getStartDate());
//                employeeAttendance.setEndDate(attendanceTime.getStartSpecificDate() + " " + attendanceTimeList.get(i).getEndDate());
//            }
//            根据考勤时间查询，以及个人userId来查询考勤记录
            List<EmployeeAttendance> employeeAttendanceList = sysAppPeopleAttendanceMapper.timePeriod(employeeAttendance.getStartDate(), employeeAttendance.getEndDate(), userId);
            if (employeeAttendanceList.size() > 3) {
                return 3;
            }
        }
//        自动生成考勤主键ID
            employeeAttendance.setRecordId(IdUtils.getSeqId());
//        获取当前打卡时间
            employeeAttendance.setWorkTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
//        单张图片上传
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".png") || type.equals(".jpg")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = filePatheGetAttendancePicPaht + filename;// 存放位置
                File destFile = new File(path);
                try {
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }//复制文件到指定目录
//        多张打卡图片上传
//        if (files.length > 0) {
//            for (MultipartFile file : files) {
//                if (!file.isEmpty()) {
//                    String typepic = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
//                    if (typepic.equals(".png") || typepic.equals(".jpg")) {
//                        String filename = System.currentTimeMillis() + typepic;// 取当前时间戳作为文件名
//                        String path = filePatheGetAttendancePicPaht + filename;// 存放位置
//                        File destFile = new File(path);
//                        try {
//                            FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        list.add(filePatheGetAttendancePicUrl + filename);
//                    } else {
//                        return 2;
//                    }
//                }
//            }
//        }
//        将图片地址字符串进行拼接
//        String collect = list.stream().collect(Collectors.joining(";"));
//        添加单张图片
                employeeAttendance.setWorkPhoto(filePatheGetAttendancePicUrl + filename);
//        添加创建时间
                employeeAttendance.setCreateDate(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
//        将以上信息添加到数据库
                int i1 = sysAppPeopleAttendanceMapper.inAttendanceRecord(employeeAttendance);
                return i1;
            } else {
                return 2;
            }
        }

    @Override
    public IndividualAndAttendance  selAttendanceRecord(String userId, EmployeeAttendance employeeAttendance, AttendanceTime attendanceTime) {
        List<EmployeeAttendance> employeeAttendanceList1 = new ArrayList<>();
        IndividualAndAttendance individualAndAttendance = new IndividualAndAttendance();
//        获取当前年月日
        String format = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now());
//        获取当前时分秒
//        String format1 = DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDate.now());
        String format1 = new SimpleDateFormat("HH:mm:ss").format(new Date());
//        获取所有未禁止的考勤时间，并按照升序排列
        List<AttendanceTime> attendanceTimeList = sysAppPeopleAttendanceMapper.selectAttendanceTimes(attendanceTime);
//        遍历所有考勤时间
        for (int i = 0; i < attendanceTimeList.size(); i++) {
//            判断前端有没有传年月日过来,传了就用传过来的年月日,没传就用当前年月日
//            if (attendanceTime.getStartSpecificDate() == null && attendanceTime.getEndSpecificDate() == null) {
                employeeAttendance.setStartDate(format + " " + attendanceTimeList.get(i).getStartDate());
                employeeAttendance.setEndDate(format + " " + attendanceTimeList.get(i).getEndDate());
//            } else {
//                employeeAttendance.setStartDate(attendanceTime.getStartSpecificDate() + " " + attendanceTimeList.get(i).getStartDate());
//                employeeAttendance.setEndDate(attendanceTime.getStartSpecificDate() + " " + attendanceTimeList.get(i).getEndDate());
//            }
//            根据考勤时间查询，以及个人userId来查询考勤记录
            List<EmployeeAttendance> employeeAttendanceList = sysAppPeopleAttendanceMapper.timePeriod(employeeAttendance.getStartDate(), employeeAttendance.getEndDate(), userId);
//            记录为0，且当前时间大于考勤结束时间
            EmployeeAttendance employeeAttendances = new EmployeeAttendance();
            if (employeeAttendanceList.size() == 0 && format1.compareTo(attendanceTimeList.get(i).getEndDate()) > 0) {
                employeeAttendances.setTimeQuantum(employeeAttendance.getStartDate() + "~" + employeeAttendance.getEndDate());
                employeeAttendances.setType("未打卡");
                employeeAttendances.setSignInNum("0/3");

//             记录数为0,当前时间在考勤时间区间内
            } else if (employeeAttendanceList.size() == 0 && format1.compareTo(attendanceTimeList.get(i).getStartDate()) >= 0 && format1.compareTo(attendanceTimeList.get(i).getEndDate()) <= 0) {
                employeeAttendances.setTimeQuantum(employeeAttendance.getStartDate() + "~" + employeeAttendance.getEndDate());
                employeeAttendances.setType("点击打卡");
                employeeAttendances.setSignInNum("0/3");

//                有考勤记录,当前时间在考勤时间区间内
            } else if (employeeAttendanceList.size() > 0 && format1.compareTo(attendanceTimeList.get(i).getEndDate()) >= 0) {
                String collect = employeeAttendanceList.stream().map(EmployeeAttendance::getUserName).distinct().collect(Collectors.joining());
                employeeAttendances.setUserName(collect);
                employeeAttendances.setTimeQuantum(employeeAttendance.getStartDate() + "~" + employeeAttendance.getEndDate());
                employeeAttendances.setType("已打卡");
                employeeAttendances.setSignInNum(employeeAttendanceList.size() + "/3");
//                拼接图片地址
                String join = StringUtils.join(employeeAttendanceList.stream().map(EmployeeAttendance::getWorkPhoto).collect(Collectors.joining(",")));
                String[] split = join.split(",", 3);
                employeeAttendances.setWorkPhotos(split);

            }else if(employeeAttendanceList.size() > 0 && format1.compareTo(attendanceTimeList.get(i).getStartDate()) >= 0 && format1.compareTo(attendanceTimeList.get(i).getEndDate()) <= 0){
                String collect = employeeAttendanceList.stream().map(EmployeeAttendance::getUserName).distinct().collect(Collectors.joining());
                employeeAttendances.setUserName(collect);
                employeeAttendances.setTimeQuantum(employeeAttendance.getStartDate() + "~" + employeeAttendance.getEndDate());
                employeeAttendances.setType("已打卡");
                employeeAttendances.setSignInNum(employeeAttendanceList.size() + "/3");
//                拼接图片地址
                String join = StringUtils.join(employeeAttendanceList.stream().map(EmployeeAttendance::getWorkPhoto).collect(Collectors.joining(",")));
                String[] split = join.split(",", 3);
                employeeAttendances.setWorkPhotos(split);
//                当前时间小于考勤开始时间
            } else if (employeeAttendanceList.size() == 0 && format1.compareTo(attendanceTimeList.get(i).getStartDate()) < 0) {
                employeeAttendances.setTimeQuantum(employeeAttendance.getStartDate() + "~" + employeeAttendance.getEndDate());
                employeeAttendances.setType("未开始");
                employeeAttendances.setSignInNum("0/3");
            }
//            for (EmployeeAttendance attendance : employeeAttendanceList) {
//                employeeAttendances.setUserName(attendance.getUserName());
//                employeeAttendances.setWorkPhoto(attendance.getWorkPhoto());
//            }
            employeeAttendanceList1.add(employeeAttendances);
            //        查询人员所属景区、以及职位
            EmployeeAttendance employeeAttendance1 = sysAppPeopleAttendanceMapper.selseUser(userId);
            individualAndAttendance.setUserName(employeeAttendance1.getUserName());
            individualAndAttendance.setEmployeePost(employeeAttendance1.getEmployeePost());
            individualAndAttendance.setBelongingScenicSpot(employeeAttendance1.getScenicSpotName());
            individualAndAttendance.setEmployeeAttendances(employeeAttendanceList1);
        }
        return individualAndAttendance;
    }

    @Override
    public ReturnModel selNoClocking(EmployeeAttendance employeeAttendance) {
        List<EmployeeAttendance> employeeAttendanceList = new ArrayList<>();
        ReturnModel returnModel = new ReturnModel();
//        查询有时间区间的
        if (employeeAttendance.getStartDate() != null && employeeAttendance.getEndDate() != null) {
            employeeAttendanceList = sysAppPeopleAttendanceMapper.selectEmployeeAttendance(employeeAttendance);
            employeeAttendanceList.stream().forEach(employeeAttendance1 -> employeeAttendance1.setStartDate(employeeAttendance.getStartDate()));
            employeeAttendanceList.stream().forEach(employeeAttendance1 -> employeeAttendance1.setEndDate(employeeAttendance.getEndDate()));
            for (EmployeeAttendance attendance : employeeAttendanceList) {
                if (Objects.equals(attendance.getRecordId(), null)) {
                    attendance.setSignInOrNot("未打卡");
                } else {
                    attendance.setSignInOrNot("已打卡");
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
            employeeAttendanceList = sysAppPeopleAttendanceMapper.selectEmployeeAttendances(employeeAttendance);
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
                    attendance.setSignInOrNot("未打卡");
                } else {
                    attendance.setSignInOrNot("已打卡");
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
    public IndividualAndAttendance selRobotClocking( String userId,RobotAttendance robotAttendance) {
        IndividualAndAttendance individualAndAttendance = new IndividualAndAttendance();
        List<RobotAttendance> robotAttendanceList = new ArrayList<>();
        ReturnModel returnModel = new ReturnModel();
//        查询有无时间区间
        if (robotAttendance.getStartDate() != null && robotAttendance.getEndDate() != null) {
            robotAttendanceList = sysAppPeopleAttendanceMapper.RobotAttendance(robotAttendance);
            robotAttendanceList.stream().forEach(s -> s.setStartDate(robotAttendance.getStartDate()));
            robotAttendanceList.stream().forEach(s -> s.setEndDate(robotAttendance.getEndDate()));
            for (RobotAttendance attendance : robotAttendanceList) {
                if (Objects.equals(attendance.getRecordId(), null)) {
                    attendance.setRobotStatus("关机");
                } else {
                    attendance.setRobotStatus("开机");
                }
            }
            EmployeeAttendance employeeAttendance1 = sysAppPeopleAttendanceMapper.selseUser(userId);
            individualAndAttendance.setUserName(employeeAttendance1.getUserName());
            individualAndAttendance.setEmployeePost(employeeAttendance1.getEmployeePost());
            individualAndAttendance.setBelongingScenicSpot(employeeAttendance1.getScenicSpotName());
            individualAndAttendance.setTotal(robotAttendanceList.size());
            individualAndAttendance.setRobotAttendance(robotAttendanceList);
            return individualAndAttendance;
//            if (robotAttendanceList.size() != 0) {
//                PageInfo<RobotAttendance> pageInfo = new PageInfo<>(robotAttendanceList);
//                returnModel.setData(pageInfo.getList());
//                returnModel.setTotal((int) pageInfo.getTotal());
//            }
//            return returnModel;
        } else {
            robotAttendanceList = sysAppPeopleAttendanceMapper.RobotAttendances(robotAttendance);
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
//            if (robotAttendanceList.size() != 0) {
//                PageInfo<RobotAttendance> pageInfo = new PageInfo<>(robotAttendanceList);
//                returnModel.setData(pageInfo.getList());
//                returnModel.setTotal((int) pageInfo.getTotal());
//            }
            EmployeeAttendance employeeAttendance1 = sysAppPeopleAttendanceMapper.selseUser(userId);
            individualAndAttendance.setUserName(employeeAttendance1.getUserName());
            individualAndAttendance.setEmployeePost(employeeAttendance1.getEmployeePost());
            individualAndAttendance.setBelongingScenicSpot(employeeAttendance1.getScenicSpotName());
            individualAndAttendance.setTotal(robotAttendanceList.size());
            individualAndAttendance.setRobotAttendance(robotAttendanceList);
            return individualAndAttendance;
        }
    }

    @Override
    public int upUserInformation(String userId,EmployeeAttendance employeeAttendance) {
        employeeAttendance.setUserId(Long.valueOf(userId));

        int i = sysAppPeopleAttendanceMapper.upUserInformation(employeeAttendance);
        return i;
    }

    @Override
    public IndividualAndAttendance selectUser(EmployeeAttendance employeeAttendance) {
        IndividualAndAttendance individualAndAttendance = new IndividualAndAttendance();
        EmployeeAttendance employeeAttendance1 = sysAppPeopleAttendanceMapper.selseUser(String.valueOf(employeeAttendance.getUserId()));
        individualAndAttendance.setUserName(employeeAttendance1.getUserName());
        individualAndAttendance.setEmployeePost(employeeAttendance1.getEmployeePost());
        individualAndAttendance.setBelongingScenicSpot(employeeAttendance1.getScenicSpotName());
        return individualAndAttendance;
    }

    //获取库房地址
    @Override
    public   List<Address> selScenicSpot(String scenicSpotName) {
//        sysAppPeopleAttendanceMapper.selScenicSpot(scenicSpotName);
        List<Address> factoryAll = addressMapper.getFactoryAll("1");

        return factoryAll;
    }
}