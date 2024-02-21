package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.dao.SysAppRobotQualityTestingMapper;

import com.hna.hka.archive.management.appSystem.model.vo.SysAppRobotQualityTesting;
import com.hna.hka.archive.management.assetsSystem.dao.InspectionRecordMapper;
import com.hna.hka.archive.management.assetsSystem.model.InspectionRecord;
import com.hna.hka.archive.management.assetsSystem.model.InspectionRecordDetail;

import com.hna.hka.archive.management.assetsSystem.model.RobotQualityTesting;
import com.hna.hka.archive.management.assetsSystem.service.InspectionRecordService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-18 10:33
 **/
@Service
public class InspectionRecordServiceImpl implements InspectionRecordService {

    @Autowired
    InspectionRecordMapper mapper;
    @Autowired
    private SysAppRobotQualityTestingMapper sysAppRobotQualityTestingMapper;

    @Override
    public PageInfo<InspectionRecord> list(String robotCode, String startTime, String endTime, Integer type, Integer result, Integer pageNum, Integer pageSize) throws Exception{
        PageHelper.offsetPage(pageNum , pageSize);
        List<InspectionRecord> list = mapper.list(robotCode , startTime , endTime , type , result,null);
        for (InspectionRecord inspectionRecord : list) {
            InspectionRecordDetail[] lists = mapper.lists(inspectionRecord.getId());
            inspectionRecord.setDetails(lists);
        }
        PageInfo<InspectionRecord> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public Integer add(SysAppRobotQualityTesting sysAppRobotQualityTesting) {
//        record.setId(IdUtils.getSeqId());
//        for (InspectionRecordDetail detail : record.getDetails()) {
//            detail.setId(IdUtils.getSeqId());
//            detail.setStandardId(record.getId());
//        }
//
//        return mapper.add(record);
//        生成ID
        sysAppRobotQualityTesting.setId(IdUtils.getSeqId());
        for (InspectionRecordDetail detail : sysAppRobotQualityTesting.getDetails()) {
            detail.setId(IdUtils.getSeqId());
            detail.setStandardId(sysAppRobotQualityTesting.getId());
        }
//        获取日期(年月日时分秒)
        sysAppRobotQualityTesting.setCreateDate(DateUtil.currentDateTime());
//        根据userId获取检测员信息
//        EmployeeAttendance employeeAttendance1 = sysAppPeopleAttendanceMapper.selseUser(userId);
//        获取检测员姓名
//        sysAppRobotQualityTesting.setInspectorName(employeeAttendance1.getUserName());
//        添加创建时间
        sysAppRobotQualityTesting.setCreateDate(DateUtil.currentDateTime());
//        获取
        boolean b = sysAppRobotQualityTesting.getScanCodeTounlock() + sysAppRobotQualityTesting.getForward() + sysAppRobotQualityTesting.getBackOff() + sysAppRobotQualityTesting.getBrack() + sysAppRobotQualityTesting.getAuxiliaryBrake() + sysAppRobotQualityTesting.getPreultrasound() + sysAppRobotQualityTesting.getPostultrasound() + sysAppRobotQualityTesting.getLedLight() + sysAppRobotQualityTesting.getBreathingLamp() + sysAppRobotQualityTesting.getAppearance() + sysAppRobotQualityTesting.getFastening()+sysAppRobotQualityTesting.getEndService() != 0;
        if (b){
            sysAppRobotQualityTesting.setResult(0);
        }else {
            sysAppRobotQualityTesting.setResult(1);
        }
//        添加
        int i = sysAppRobotQualityTestingMapper.insertSelective(sysAppRobotQualityTesting);
        int r = mapper.addRobotQualityTesting(sysAppRobotQualityTesting);

        if (i == 1 && r == 1) {
            return 1;
        }
        return 2;
    }

    @Override
    public InspectionRecord preview(Long id) {
        return mapper.findByKey(id);
    }

    @Override
    public List<InspectionRecordDetail> showDetail(long inspectionId) {
        return mapper.showDetail(inspectionId);
    }

    @Override
    public RobotQualityTesting detailList(Long id) {
        RobotQualityTesting robotQualityTesting = sysAppRobotQualityTestingMapper.detailList(id);
        return robotQualityTesting;
    }

    @Override
    public Integer delect(Long id) {
        return mapper.delect(id);
    }
}
