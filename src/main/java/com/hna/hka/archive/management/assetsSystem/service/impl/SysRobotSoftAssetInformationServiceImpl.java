package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.dao.SysRobotSoftAssetInformationMapper;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotSoftAssetInformation;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotSoftAssetInformationService;
import com.hna.hka.archive.management.system.dao.*;
import com.hna.hka.archive.management.system.model.SysOrder;
import com.hna.hka.archive.management.system.model.SysRobot;
import com.hna.hka.archive.management.system.model.SysRobotCompanyAscription;
import com.hna.hka.archive.management.system.model.SysScenicSpotAscriptionCompany;
import com.hna.hka.archive.management.system.service.SysRobotCompanyAscriptionService;
import com.hna.hka.archive.management.system.service.SysScenicSpotAscriptionCompanyService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.ToolUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service.impl
 * @ClassName: SysRobotSoftAssetInformationServiceImpl
 * @Author: 郭凯
 * @Description: 机器人软资产信息业务层（实现）
 * @Date: 2021/5/28 14:25
 * @Version: 1.0
 */
@Service
@Transactional
public class SysRobotSoftAssetInformationServiceImpl implements SysRobotSoftAssetInformationService {

    @Autowired
    private SysRobotSoftAssetInformationMapper robotSoftAssetInformationMapper;

    @Autowired
    private SysRobotCompanyAscriptionMapper sysRobotCompanyAscriptionMapper;
    @Autowired
    private SysOrderMapper sysOrderMapper;

    @Autowired
    private SysRobotMapper sysRobotMapper;
    @Autowired
    private SysScenicSpotAscriptionCompanyMapper sysScenicSpotAscriptionCompanyMapper;
    @Autowired
    private SysRobotIdMapper sysRobotIdMapper;

    /**
     * @Method getRobotSoftAssetInformationList
     * @Author 郭凯
     * @Version 1.0
     * @Description 机器人软资产信息列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/5/28 14:38
     */
    @Override
    public PageDataResult getRobotSoftAssetInformationList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotSoftAssetInformation> robotSoftAssetInformationList = robotSoftAssetInformationMapper.getRobotSoftAssetInformationList(search);

        Integer type = (Integer) search.get("type");

        for (SysRobotSoftAssetInformation srsai : robotSoftAssetInformationList) {
            //当天运行时间
            Integer dayTime = sysOrderMapper.getRobotCodeByDayTime(srsai.getRobotCode());
            srsai.setDayTime(dayTime);
            //当月运行时间
            Integer monthTime = sysOrderMapper.getRobotCodeByMonthTime(srsai.getRobotCode());
            srsai.setMonthTime(monthTime);

            //当年运行时间
            Integer yearTime = sysOrderMapper.getRobotCodeByYearTime(srsai.getRobotCode());
            srsai.setYearTime(yearTime);

            //累积运行时长
            Integer runTime = sysOrderMapper.getRobotCodeByAccumulateTime(srsai.getRobotCode());
            srsai.setRunTime(runTime);

            SysRobot byCode = sysRobotIdMapper.getByCode(srsai.getRobotCode());
            if (byCode == null) {
                srsai.setErrorRecordsAffect("2");
            } else {
                srsai.setErrorRecordsAffect(byCode.getErrorRecordsAffect());
            }
        }
        if (robotSoftAssetInformationList.size() > 0) {
            PageInfo<SysRobotSoftAssetInformation> pageInfo = new PageInfo<>(robotSoftAssetInformationList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method addRobotSoftAssetInformation
     * @Author 郭凯
     * @Version 1.0
     * @Description 机器人软资产信息新增
     * @Return int
     * @Date 2021/5/28 14:39
     */
    @Override
    public int addRobotSoftAssetInformation(SysRobotSoftAssetInformation robotSoftAssetInformation) {

        SysRobotCompanyAscription robotCodeByCompany = sysRobotCompanyAscriptionMapper.getAscriptionCompanyId(robotSoftAssetInformation.getRobotId().toString());

        SysRobotCompanyAscription companyAscription = new SysRobotCompanyAscription();

        if (StringUtils.isEmpty(robotCodeByCompany)) {
            if (!StringUtils.isEmpty(robotSoftAssetInformation.getAscriptionCompanyName())) {

                SysScenicSpotAscriptionCompany company = sysScenicSpotAscriptionCompanyMapper.selectComponyNameById(robotSoftAssetInformation.getAscriptionCompanyName());
                companyAscription.setCompanyId(company.getCompanyId().toString());
            }

            companyAscription.setRobotId(robotSoftAssetInformation.getRobotId().toString());
            companyAscription.setRobotCompanyAscriptionId(IdUtils.getSeqId());
            companyAscription.setRobotCode(robotSoftAssetInformation.getRobotCode());
//            companyAscription.setCompanyId(robotSoftAssetInformation.getAscriptionCompanyId());
            companyAscription.setCreateDate(DateUtil.currentDateTime());
            companyAscription.setUpdateDate(DateUtil.currentDateTime());
            int i = sysRobotCompanyAscriptionMapper.insertSelective(companyAscription);
        } else {

            SysScenicSpotAscriptionCompany company = sysScenicSpotAscriptionCompanyMapper.selectComponyNameById(robotSoftAssetInformation.getAscriptionCompanyName());
            robotCodeByCompany.setCompanyId(company.getCompanyId().toString());
            robotCodeByCompany.setUpdateDate(DateUtil.currentDateTime());
            int i = sysRobotCompanyAscriptionMapper.updateByPrimaryKeySelective(robotCodeByCompany);
        }

        int i = sysRobotMapper.updateSysRobotEquipmentStatus(robotSoftAssetInformation.getRobotCode(), robotSoftAssetInformation.getEquipmentStatus());

        robotSoftAssetInformation.setSoftAssetInformationId(IdUtils.getSeqId());
        robotSoftAssetInformation.setCreateDate(DateUtil.currentDateTime());
        robotSoftAssetInformation.setUpdateDate(DateUtil.currentDateTime());
        return robotSoftAssetInformationMapper.insertSelective(robotSoftAssetInformation);
    }

    /**
     * @Method editRobotSoftAssetInformation
     * @Author 郭凯
     * @Version 1.0
     * @Description 机器人软资产信息编辑
     * @Return int
     * @Date 2021/5/28 14:39
     */
    @Override
    public int editRobotSoftAssetInformation(SysRobotSoftAssetInformation robotSoftAssetInformation) {

        SysRobotCompanyAscription robotCodeByCompany = sysRobotCompanyAscriptionMapper.getAscriptionCompanyId(robotSoftAssetInformation.getRobotId().toString());

        SysRobotCompanyAscription companyAscription = new SysRobotCompanyAscription();
        if (StringUtils.isEmpty(robotCodeByCompany)) {
//            if (!StringUtils.isEmpty(robotSoftAssetInformation.getAscriptionCompanyName())){
//                SysScenicSpotAscriptionCompany company = sysScenicSpotAscriptionCompanyMapper.selectComponyNameById(robotSoftAssetInformation.getAscriptionCompanyName());
//                robotCodeByCompany.setCompanyId(company.getCompanyId().toString());
//            }
            companyAscription.setRobotId(robotSoftAssetInformation.getRobotId().toString());
            companyAscription.setRobotCompanyAscriptionId(IdUtils.getSeqId());
            companyAscription.setRobotCode(robotSoftAssetInformation.getRobotCode());
//            companyAscription.setCompanyId(robotSoftAssetInformation.getAscriptionCompanyId());

            companyAscription.setCompanyId(robotSoftAssetInformation.getAscriptionCompanyId());
            companyAscription.setCreateDate(DateUtil.currentDateTime());
            companyAscription.setUpdateDate(DateUtil.currentDateTime());
            int i = sysRobotCompanyAscriptionMapper.insertSelective(companyAscription);
        } else {
//            if (!StringUtils.isEmpty(robotSoftAssetInformation.getAscriptionCompanyName())){
//                           SysScenicSpotAscriptionCompany company = sysScenicSpotAscriptionCompanyMapper.selectComponyNameById(robotSoftAssetInformation.getAscriptionCompanyName());
//            }
            robotCodeByCompany.setCompanyId(robotSoftAssetInformation.getAscriptionCompanyId());
            robotCodeByCompany.setUpdateDate(DateUtil.currentDateTime());
            int i = sysRobotCompanyAscriptionMapper.updateByPrimaryKeySelective(robotCodeByCompany);
        }

        int i = sysRobotMapper.updateSysRobotEquipmentStatus(robotSoftAssetInformation.getRobotCode(), robotSoftAssetInformation.getEquipmentStatus());

        this.calculateDepreciation(robotSoftAssetInformation);
        robotSoftAssetInformation.setUpdateDate(DateUtil.currentDateTime());
        return robotSoftAssetInformationMapper.updateByPrimaryKeySelective(robotSoftAssetInformation);
    }

    /**
     * @Method delRobotSoftAssetInformation
     * @Author 郭凯
     * @Version 1.0
     * @Description 机器人软资产信息删除
     * @Return int
     * @Date 2021/5/28 14:39
     */
    @Override
    public int delRobotSoftAssetInformation(Long softAssetInformationId) {
        return robotSoftAssetInformationMapper.deleteByPrimaryKey(softAssetInformationId);
    }

    /**
     * @Method getRobotSoftAssetInformationByRobotId
     * @Author 郭凯
     * @Version 1.0
     * @Description 根据机器人编号查询数据是否存在
     * @Return com.hna.hka.archive.management.assetsSystem.model.SysRobotSoftAssetInformation
     * @Date 2021/5/28 16:24
     */
    @Override
    public SysRobotSoftAssetInformation getRobotSoftAssetInformationByRobotId(Long robotId) {


        return robotSoftAssetInformationMapper.getRobotSoftAssetInformationByRobotId(robotId);
    }

    /**
     * @Method getRobotSoftAssetInformationExcel
     * @Author 郭凯
     * @Version 1.0
     * @Description 下载机器人软资产信息Excel表信息查询
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.SysRobotSoftAssetInformation>
     * @Date 2021/5/28 16:38
     */
    @Override
    public List<SysRobotSoftAssetInformation> getRobotSoftAssetInformationExcel(Map<String, Object> search) {
        List<SysRobotSoftAssetInformation> robotSoftAssetInformationList = robotSoftAssetInformationMapper.getRobotSoftAssetInformationList(search);

        for (SysRobotSoftAssetInformation srsai : robotSoftAssetInformationList) {
            //当天运行时间
            Integer dayTime = sysOrderMapper.getRobotCodeByDayTime(srsai.getRobotCode());
            srsai.setDayTime(dayTime);
            //当月运行时间
            Integer monthTime = sysOrderMapper.getRobotCodeByMonthTime(srsai.getRobotCode());
            srsai.setMonthTime(monthTime);

            //当年运行时间
            Integer yearTime = sysOrderMapper.getRobotCodeByYearTime(srsai.getRobotCode());
            srsai.setYearTime(yearTime);

            //累积运行时长
            Integer runTime = sysOrderMapper.getRobotCodeByAccumulateTime(srsai.getRobotCode());
            srsai.setRunTime(runTime);
        }


        return robotSoftAssetInformationMapper.getRobotSoftAssetInformationList(search);
    }

    /**
     * @Method getAppRobotSoftAssetInformation
     * @Author 郭凯
     * @Version 1.0
     * @Description 根据机器人编号查询机器人软资产(APP接口)
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.SysRobotSoftAssetInformation>
     * @Date 2021/6/10 14:50
     */
    @Override
    public SysRobotSoftAssetInformation getAppRobotSoftAssetInformation(Map<String, Object> search) {
        SysRobotSoftAssetInformation robotSoftAssetInformation = robotSoftAssetInformationMapper.getAppRobotSoftAssetInformation(search);
        return ToolUtil.isEmpty(robotSoftAssetInformation) ? new SysRobotSoftAssetInformation() : robotSoftAssetInformation;
    }

    /**
     * @Method getRobotSoftAssetInformationByRobotCode
     * @Author 郭凯
     * @Version 1.0
     * @Description 根据机器人编号查询软资产信息
     * @Return com.hna.hka.archive.management.assetsSystem.model.SysRobotSoftAssetInformation
     * @Date 2021/7/5 17:03
     */
    @Override
    public SysRobotSoftAssetInformation getRobotSoftAssetInformationByRobotCode(String robotCode) {
        return robotSoftAssetInformationMapper.getRobotSoftAssetInformationByRobotCode(robotCode);
    }


    /**
     * 定时更新机器人软资产信息的折旧和源值
     */

    public void updateRobotSoftAssetInformation() {

        List<SysRobotSoftAssetInformation> list = robotSoftAssetInformationMapper.getRobotSoftAssetInformationListAll();

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        String data = year + "-" + month;
        try {
            for (SysRobotSoftAssetInformation sysRobotSoftAssetInformation : list) {

                if (StringUtils.isEmpty(sysRobotSoftAssetInformation.getFactoryCost()) || StringUtils.isEmpty(sysRobotSoftAssetInformation.getServiceLife()) || StringUtils.isEmpty(sysRobotSoftAssetInformation.getDateProduction())) {
                    continue;
                }
                //已折旧月份
                String months = DateUtil.findMonths(sysRobotSoftAssetInformation.getDateProduction(), data);
                Double monthsD = Double.valueOf(months) - 1;

                Double cost = Double.valueOf(sysRobotSoftAssetInformation.getFactoryCost());
                Double serviceLife = Double.valueOf(sysRobotSoftAssetInformation.getServiceLife()) * 12;
                if (monthsD > serviceLife) {
                    sysRobotSoftAssetInformation.setAccumulate(cost);
                    sysRobotSoftAssetInformation.setNetPrice(0d);
                    sysRobotSoftAssetInformation.setUpdateDate(DateUtil.currentDateTime());
                    int i = robotSoftAssetInformationMapper.updateByPrimaryKeySelective(sysRobotSoftAssetInformation);
                } else {
                    //累积折旧
                    Double accumulate = cost / serviceLife * monthsD;
                    //净值
                    Double netPrice = cost - accumulate;

                    System.out.println(sysRobotSoftAssetInformation.getRobotCode() + ":" + accumulate + " - " + netPrice);
                    sysRobotSoftAssetInformation.setAccumulate(accumulate);
                    sysRobotSoftAssetInformation.setNetPrice(netPrice);
                    sysRobotSoftAssetInformation.setUpdateDate(DateUtil.currentDateTime());
                    int i = robotSoftAssetInformationMapper.updateByPrimaryKeySelective(sysRobotSoftAssetInformation);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据机器人编号，获取软资产信息
     *
     * @param robotCode
     * @return
     */
    @Override
    public SysRobotSoftAssetInformation selectRobotSoftByRobotCode(String robotCode) {

        SysRobotSoftAssetInformation sysRobotSoftAssetInformation = robotSoftAssetInformationMapper.selectRobotSoftByRobotCode(robotCode);

        return sysRobotSoftAssetInformation;
    }

    //导入需要的方法，添加机器人资产信息
    @Override
    public int addRobotSoftAssetInformationImport(SysRobotSoftAssetInformation robotSoftAssetInformation) {
        SysRobotCompanyAscription robotCodeByCompany = sysRobotCompanyAscriptionMapper.getAscriptionCompanyId(robotSoftAssetInformation.getRobotId().toString());

        SysRobotCompanyAscription companyAscription = new SysRobotCompanyAscription();

        if (StringUtils.isEmpty(robotCodeByCompany)) {
            if (!StringUtils.isEmpty(robotSoftAssetInformation.getAscriptionCompanyName())) {

                SysScenicSpotAscriptionCompany company = sysScenicSpotAscriptionCompanyMapper.selectComponyNameById(robotSoftAssetInformation.getAscriptionCompanyName());
                companyAscription.setCompanyId(company.getCompanyId().toString());
            }

            companyAscription.setRobotId(robotSoftAssetInformation.getRobotId().toString());
            companyAscription.setRobotCompanyAscriptionId(IdUtils.getSeqId());
            companyAscription.setRobotCode(robotSoftAssetInformation.getRobotCode());
//            companyAscription.setCompanyId(robotSoftAssetInformation.getAscriptionCompanyId());
            companyAscription.setCreateDate(DateUtil.currentDateTime());
            companyAscription.setUpdateDate(DateUtil.currentDateTime());
            int i = sysRobotCompanyAscriptionMapper.insertSelective(companyAscription);
        } else {

            SysScenicSpotAscriptionCompany company = sysScenicSpotAscriptionCompanyMapper.selectComponyNameById(robotSoftAssetInformation.getAscriptionCompanyName());
            robotCodeByCompany.setCompanyId(company.getCompanyId().toString());
            robotCodeByCompany.setUpdateDate(DateUtil.currentDateTime());
            int i = sysRobotCompanyAscriptionMapper.updateByPrimaryKeySelective(robotCodeByCompany);
        }

//        int i = sysRobotMapper.updateSysRobotEquipmentStatus(robotSoftAssetInformation.getRobotCode(), robotSoftAssetInformation.getEquipmentStatus());

        robotSoftAssetInformation.setSoftAssetInformationId(IdUtils.getSeqId());
        robotSoftAssetInformation.setCreateDate(DateUtil.currentDateTime());
        robotSoftAssetInformation.setUpdateDate(DateUtil.currentDateTime());
        return robotSoftAssetInformationMapper.insertSelective(robotSoftAssetInformation);

    }

    //导入需要的方法，修改机器人资产信息
    @Override
    public int editRobotSoftAssetInformationImport(SysRobotSoftAssetInformation robotSoftAssetInformation) {
        SysRobotCompanyAscription robotCodeByCompany = sysRobotCompanyAscriptionMapper.getAscriptionCompanyId(robotSoftAssetInformation.getRobotId().toString());

        SysRobotCompanyAscription companyAscription = new SysRobotCompanyAscription();

        if (StringUtils.isEmpty(robotCodeByCompany)) {
            if (!StringUtils.isEmpty(robotSoftAssetInformation.getAscriptionCompanyName())) {

                SysScenicSpotAscriptionCompany company = sysScenicSpotAscriptionCompanyMapper.selectComponyNameById(robotSoftAssetInformation.getAscriptionCompanyName());
                robotCodeByCompany.setCompanyId(company.getCompanyId().toString());
            }

            companyAscription.setRobotId(robotSoftAssetInformation.getRobotId().toString());
            companyAscription.setRobotCompanyAscriptionId(IdUtils.getSeqId());
            companyAscription.setRobotCode(robotSoftAssetInformation.getRobotCode());

//            companyAscription.setCompanyId(robotSoftAssetInformation.getAscriptionCompanyId());
            companyAscription.setCreateDate(DateUtil.currentDateTime());
            companyAscription.setUpdateDate(DateUtil.currentDateTime());
            int i = sysRobotCompanyAscriptionMapper.insertSelective(companyAscription);
        } else {
            if (!StringUtils.isEmpty(robotSoftAssetInformation.getAscriptionCompanyName())) {
                SysScenicSpotAscriptionCompany company = sysScenicSpotAscriptionCompanyMapper.selectComponyNameById(robotSoftAssetInformation.getAscriptionCompanyName());
                robotCodeByCompany.setCompanyId(company.getCompanyId().toString());

            }
//            robotCodeByCompany.setCompanyId(robotSoftAssetInformation.getAscriptionCompanyId());
            robotCodeByCompany.setUpdateDate(DateUtil.currentDateTime());
            int i = sysRobotCompanyAscriptionMapper.updateByPrimaryKeySelective(robotCodeByCompany);


        }

        int i = sysRobotMapper.updateSysRobotEquipmentStatus(robotSoftAssetInformation.getRobotCode(), robotSoftAssetInformation.getEquipmentStatus());

        robotSoftAssetInformation.setUpdateDate(DateUtil.currentDateTime());
        return robotSoftAssetInformationMapper.updateByPrimaryKeySelective(robotSoftAssetInformation);

    }


    public SysRobotSoftAssetInformation calculateDepreciation(SysRobotSoftAssetInformation robotSoftAssetInformation) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        String data = year + "-" + month;
        try {

            //已折旧月份
            String months = DateUtil.findMonths(robotSoftAssetInformation.getDateProduction(), data);
            Double monthsD = Double.valueOf(months) - 1;


            Double cost = Double.valueOf(robotSoftAssetInformation.getFactoryCost());
            Double serviceLife = Double.valueOf(robotSoftAssetInformation.getServiceLife()) * 12;

            //累积折旧
            Double accumulate = cost / serviceLife * monthsD;
            //净值
            Double netPrice = cost - accumulate;

            robotSoftAssetInformation.setAccumulate(accumulate);
            robotSoftAssetInformation.setNetPrice(netPrice);
            robotSoftAssetInformation.setUpdateDate(DateUtil.currentDateTime());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return robotSoftAssetInformation;

    }


}
