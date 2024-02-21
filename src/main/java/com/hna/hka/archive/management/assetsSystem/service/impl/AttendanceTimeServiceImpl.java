package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.dao.AttendanceTimeMapper;
import com.hna.hka.archive.management.assetsSystem.model.AttendanceTime;
import com.hna.hka.archive.management.assetsSystem.service.AttendanceTimeService;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.ReturnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @ClassName：AttendanceTimeserviceImpl
 * @Author: gouteng
 * @Date: 2022-11-22 13:18
 * @Description: 必须描述类做什么事情, 实现什么功能
 */
@Service
public class AttendanceTimeServiceImpl implements AttendanceTimeService {

    @Autowired
    private AttendanceTimeMapper attendanceTimeMapper;
    @Override
    public int addAttendanceTime(AttendanceTime attendanceTime) {
        String format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
//        创建时间
        attendanceTime.setCreateDate(format);
//        更新时间
        attendanceTime.setUpdateDate(format);
//        使用id生成器，生成id
        attendanceTime.setId(IdUtils.getSeqId());
        int i = attendanceTimeMapper.addAttendanceTime(attendanceTime);
        return i;
    }

    @Override
    public int upAttendanceTime(AttendanceTime attendanceTime) {
//        获取当前时间
        String format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
//        修改更新时间
        attendanceTime.setUpdateDate(format);
        int i = attendanceTimeMapper.upAttendanceTime(attendanceTime);
        return i;
    }

    @Override
    public int deAttendanceTime(AttendanceTime attendanceTime) {
        int i = attendanceTimeMapper.deAttendanceTime(attendanceTime);
        return i;
    }

    @Override
    public ReturnModel selectAttendanceTime(int pageNum, int pageSize, AttendanceTime attendanceTime) {
        PageHelper.startPage(pageNum,pageSize);
//        PageDataResult pageDataResult = new PageDataResult();
        ReturnModel returnModel = new ReturnModel();

        List<AttendanceTime> list = attendanceTimeMapper.selectAttendanceTime(attendanceTime);
        if (list.size()>0) {
            PageInfo<AttendanceTime> pageInfo = new PageInfo<>(list);
            returnModel.setData(list);
            returnModel.setTotal((int) pageInfo.getTotal());
        }
        return returnModel;
    }

}
