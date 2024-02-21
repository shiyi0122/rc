package com.hna.hka.archive.management.appSystem.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.hna.hka.archive.management.appSystem.dao.SysAppPeopleAttendanceMapper;
import com.hna.hka.archive.management.appSystem.dao.SysAppRobotQualityTestingMapper;
import com.hna.hka.archive.management.appSystem.dao.SysRobotInspectionRecordLogMapper;
import com.hna.hka.archive.management.appSystem.model.SysRobotInspectionRecordLog;
import com.hna.hka.archive.management.appSystem.service.AppRobotQualityTestingService;

import com.hna.hka.archive.management.assetsSystem.dao.InspectionRecordMapper;
import com.hna.hka.archive.management.assetsSystem.dao.ProductionInfoMapper;
import com.hna.hka.archive.management.assetsSystem.dao.SRQISMapper;
import com.hna.hka.archive.management.assetsSystem.model.*;
import com.hna.hka.archive.management.appSystem.model.vo.SysAppRobotQualityTesting;
import com.hna.hka.archive.management.assetsSystem.service.SRQISService;
import com.hna.hka.archive.management.system.dao.SysRobotMapper;
import com.hna.hka.archive.management.system.dao.SysRobotProblemExtendMapper;
import com.hna.hka.archive.management.system.model.SysRobot;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.JsonUtils;
import io.swagger.models.auth.In;
import net.sf.json.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.util.unit.DataUnit;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName：AppRobotQualityTestingServiceImpl
 * @Author: gouteng
 * @Date: 2022-12-11 18:13
 * @Description: 必须描述类做什么事情, 实现什么功能
 */
@Service
public class AppRobotQualityTestingServiceImpl implements AppRobotQualityTestingService {
    @Autowired
    private SysAppPeopleAttendanceMapper sysAppPeopleAttendanceMapper;
    @Autowired
    private SysAppRobotQualityTestingMapper sysAppRobotQualityTestingMapper;
    @Autowired
    private InspectionRecordMapper inspectionRecordMapper;
    @Autowired
    private SysRobotMapper sysRobotMapper;
    @Autowired
    private SysRobotInspectionRecordLogMapper sysRobotInspectionRecordLogMapper;

    @Autowired
    private SRQISMapper srqisMapper;

    @Autowired
    private ProductionInfoMapper productionInfoMapper;

    @Override
    public int insertQcOrAt(String userId, InspectionRecord inspectionRecord) {

        SysRobot robotCodeBy = sysRobotMapper.getRobotCodeBy(inspectionRecord.getRobotCode());
        if (StringUtils.isEmpty(robotCodeBy)){
            return 2;
        }

        if (inspectionRecord.getType() == 1){//工厂质检

            InspectionRecord inspectionRecordN = inspectionRecordMapper.selectByRobotCodeAndType(inspectionRecord.getRobotCode(),inspectionRecord.getType());
            if (!StringUtils.isEmpty(inspectionRecordN)){//已有工厂质检，无法在添加工厂质检
                return 3;
            }else{

                //生成ID
                inspectionRecord.setId(IdUtils.getSeqId());
                String detailsN = inspectionRecord.getDetailsN();
                List<InspectionRecordDetail> inspectionRecordDetails = JSONObject.parseArray(detailsN, InspectionRecordDetail.class);
                //获取日期(年月日时分秒)
                inspectionRecord.setCreateTime(DateUtil.currentDateTime());
                inspectionRecord.setUpdateTime(DateUtil.currentDateTime());
                inspectionRecord.setInspectonTime(DateUtil.currentDateTime());

                Long o = 1l;

                for (InspectionRecordDetail detail : inspectionRecordDetails) {
                    if (detail.getQualified() == 0){
                        o = 0l;
                    }
                }
                inspectionRecord.setResult(o);

//        获取
//        boolean b = inspectionRecord.getScanCodeTounlock() + sysAppRobotQualityTesting.getForward() + sysAppRobotQualityTesting.getBackOff() + sysAppRobotQualityTesting.getBrack() + sysAppRobotQualityTesting.getAuxiliaryBrake() + sysAppRobotQualityTesting.getPreultrasound() + sysAppRobotQualityTesting.getPostultrasound() + sysAppRobotQualityTesting.getLedLight() + sysAppRobotQualityTesting.getBreathingLamp() + sysAppRobotQualityTesting.getAppearance() + sysAppRobotQualityTesting.getFastening()+sysAppRobotQualityTesting.getEndService() != 0;
//        if (b){
//            sysAppRobotQualityTesting.setResult(0);
//        }else {
//            sysAppRobotQualityTesting.setResult(1);
//        }
//        添加
//        int i = sysAppRobotQualityTestingMapper.insertSelective(sysAppRobotQualityTesting);
                Integer integer = inspectionRecordMapper.add(inspectionRecord);

                if (integer>0){
                    for (InspectionRecordDetail detail : inspectionRecordDetails) {
                        detail.setId(IdUtils.getSeqId());
                        detail.setStandardId(inspectionRecord.getId());
                        detail.setCreateTime(DateUtil.currentDateTime());
                        inspectionRecordMapper.addDetail(detail);
                    }
                }
                return integer;
            }

        }else{//景区验收

            InspectionRecord inspectionRecordN = inspectionRecordMapper.selectByRobotCodeAndType(inspectionRecord.getRobotCode(),1l);
            if (StringUtils.isEmpty(inspectionRecordN)){//没有工厂质检，无法景区验收
                return 5;
            }else{
                InspectionRecord inspectionRecordT = inspectionRecordMapper.selectByRobotCodeAndType(inspectionRecord.getRobotCode(),inspectionRecord.getType());

                if (!StringUtils.isEmpty(inspectionRecordT)){
                    return 3;
                }else{

                    if (inspectionRecordN.getResult() == 1){
                        //生成ID
                        inspectionRecord.setId(IdUtils.getSeqId());
                        String detailsN = inspectionRecord.getDetailsN();
                        List<InspectionRecordDetail> inspectionRecordDetails = JSONObject.parseArray(detailsN, InspectionRecordDetail.class);
                        //获取日期(年月日时分秒)
                        inspectionRecord.setCreateTime(DateUtil.currentDateTime());
                        inspectionRecord.setInspectonTime(DateUtil.currentDateTime());

                        Long o = 1l;
                        for (InspectionRecordDetail detail : inspectionRecordDetails) {
                            if (detail.getQualified() == 0){
                                o = 0l;
                            }
                        }

                        inspectionRecord.setResult(o);

                        Integer integer = inspectionRecordMapper.add(inspectionRecord);
                        if (integer>0){
                            for (InspectionRecordDetail detail : inspectionRecordDetails) {
                                detail.setId(IdUtils.getSeqId());
                                detail.setStandardId(inspectionRecord.getId());
                                detail.setCreateTime(DateUtil.currentDateTime());
                                inspectionRecordMapper.addDetail(detail);
                            }
                        }
                        return integer;
                    }else{
                        return 4;
                    }
                }
            }
        }
    }

    //返回最新的检测/验收记录(详情)
    @Override
    public InspectionRecord seInspectionDetails(Long id,String robotCode) {

        if (StringUtils.isEmpty(id)){

            InspectionRecord inspectionRecord = inspectionRecordMapper.selectByRobotCode(robotCode);

            if (StringUtils.isEmpty(inspectionRecord)){

                SysRobot robotCodeBy = sysRobotMapper.getRobotCodeBy(robotCode);
                if (StringUtils.isEmpty(robotCodeBy)){
                    return null;
                }else{
                    InspectionRecord inspectionRecord1 = new InspectionRecord();
                    inspectionRecord1.setRobotCode(robotCodeBy.getRobotCode());
                    inspectionRecord1.setRobotModel(robotCodeBy.getRobotModel());
                    return inspectionRecord1;
                }
            }else{
                InspectionRecordDetail[] lists = inspectionRecordMapper.lists(inspectionRecord.getId());
                inspectionRecord.setDetails(lists);
                return inspectionRecord;
            }
        }else{
            InspectionRecord inspectionRecord =  inspectionRecordMapper.selectById(id);

            InspectionRecordDetail[] lists = inspectionRecordMapper.lists(inspectionRecord.getId());

            inspectionRecord.setDetails(lists);

            return inspectionRecord;
        }

    }

    /**
     * 列表查询
     * @param robotCode
     * @param startTime
     * @param endTime
     * @param type
     * @param result
     * @return
     * @throws Exception
     */
    @Override
    public List<InspectionRecord> selectQcOrAts(String robotCode, String startTime, String endTime, Integer type, Integer result,String userId) {
        List<InspectionRecord> list = inspectionRecordMapper.list(robotCode , startTime , endTime , type , result,userId);
        for (InspectionRecord inspectionRecord : list) {
            InspectionRecordDetail[] lists = inspectionRecordMapper.lists(inspectionRecord.getId());
            inspectionRecord.setDetails(lists);
        }
        return list;
    }


    /**
     * 质检标准列表
     * @param type
     * @return
     */
    @Override
    public List<SysRobotQualityInspectionStandard> qualityStandard(String type) {


       List<SysRobotQualityInspectionStandard> list =  srqisMapper.qualityStandard(type);
        for (SysRobotQualityInspectionStandard sysRobotQualityInspectionStandard : list) {

            List<SysRobotQualityInspectionDetail> sysRobotQualityInspectionDetails = srqisMapper.showDetail(sysRobotQualityInspectionStandard.getId());
            sysRobotQualityInspectionStandard.setDetailList(sysRobotQualityInspectionDetails);

        }
        return list ;
    }
    /**
     * 生产批次列表
     * @return
     */
    @Override
    public List<ProductionInfo> productionBatch() {

        List<ProductionInfo> list = productionInfoMapper.getListAll();

        return list ;
    }

    /**
     * 复检
     * @param userId
     * @param inspectionRecord
     * @return
     */
    @Override
    public int reInspection(String userId, InspectionRecord inspectionRecord) {

        List<InspectionRecordDetail> inspectionRecordDetails = JSONObject.parseArray(inspectionRecord.getDetailsN(), InspectionRecordDetail.class);

        Long o = 1l;
        for (InspectionRecordDetail detail : inspectionRecordDetails) {
            if (detail.getQualified() == 0){
                o = 0l;
            }
        }
        inspectionRecord.setResult(o);
        inspectionRecord.setUpdateTime(DateUtil.currentDateTime());
        int i = inspectionRecordMapper.updateByPrimaryKeySelective(inspectionRecord);

        if (i > 0 ){

            SysRobotInspectionRecordLog sysRobotInspectionRecordLog = new SysRobotInspectionRecordLog();

            for (InspectionRecordDetail detail : inspectionRecordDetails) {

                sysRobotInspectionRecordLog = new SysRobotInspectionRecordLog();
                if ("1".equals(detail.getIfModify())){
                    sysRobotInspectionRecordLog.setId(IdUtils.getSeqId());
                    sysRobotInspectionRecordLog.setStandardId(detail.getStandardId());
                    sysRobotInspectionRecordLog.setStandardDetailId(detail.getId());
                    sysRobotInspectionRecordLog.setCreateTime(DateUtil.currentDateTime());
                    sysRobotInspectionRecordLog.setUpdateTime(DateUtil.currentDateTime());
                    sysRobotInspectionRecordLogMapper.insert(sysRobotInspectionRecordLog);
                }
                detail.setUpdateTime(DateUtil.currentDateTime());

                inspectionRecordMapper.editDetail(detail);
            }

        }

        return i;
    }
}
