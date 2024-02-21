package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.dao.SysRobotErrorRecordsMapper;
import com.hna.hka.archive.management.assetsSystem.dao.SysRobotDamagesMapper;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotDamages;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotErrorRecords;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotDamagesService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service.impl
 * @ClassName: SysRobotDamagesServiceImpl
 * @Author: 郭凯
 * @Description: 机器人损坏赔偿业务层（实现）
 * @Date: 2021/6/26 17:08
 * @Version: 1.0
 */
@Service
@Transactional
public class SysRobotDamagesServiceImpl implements SysRobotDamagesService {

    @Autowired
    private SysRobotDamagesMapper sysRobotDamagesMapper;

    @Autowired
    private SysRobotErrorRecordsMapper sysRobotErrorRecordsMapper;

    /**
     * @Method addRobotDamages
     * @Author 郭凯
     * @Version  1.0
     * @Description 新增机器人损坏赔偿信息
     * @Return int
     * @Date 2021/6/26 17:12
     */
    @Override
    public SysRobotDamages addRobotDamages(SysRobotDamages sysRobotDamages) {
        SysRobotDamages robotDamages = null;
        sysRobotDamages.setDamagesId(IdUtils.getSeqId());
        sysRobotDamages.setOrderNumber("PZ" + IdUtils.getUUID() + "PZ");
        sysRobotDamages.setLossTime(DateUtil.currentDateTime());
        sysRobotDamages.setCreateDate(DateUtil.currentDateTime());
        sysRobotDamages.setUpdateDate(DateUtil.currentDateTime());
        int i = sysRobotDamagesMapper.insertSelective(sysRobotDamages);
        SysRobotErrorRecords sysRobotErrorRecords = new SysRobotErrorRecords();
        sysRobotErrorRecords.setErrorRecordsId(IdUtils.getSeqId());
        sysRobotErrorRecords.setRobotCode(sysRobotDamages.getRobotCode());
        sysRobotErrorRecords.setErrorRecordsNo(IdUtils.getSeqId().toString());
        sysRobotErrorRecords.setErrorRecordsAffect(sysRobotDamages.getAffectUseType());
        sysRobotErrorRecords.setErrorRecordsType("易损易耗");
        sysRobotErrorRecords.setErrorRecordsReportSource("2");
        sysRobotErrorRecords.setErrorRecordsName(sysRobotDamages.getErrorRecordsName());
        sysRobotErrorRecords.setErrorRecordsDescription(sysRobotDamages.getErrorRecordsDescription());
        sysRobotErrorRecords.setErrorRecordsOrderNo(sysRobotDamages.getRobotOrderNumber());
        sysRobotErrorRecords.setErrorRecordsPersonnel(sysRobotDamages.getLossRater());
        sysRobotErrorRecords.setScenicSpotId(Long.parseLong(sysRobotDamages.getScenicSpotId()));
        sysRobotErrorRecords.setErrorRecordsDate(DateUtil.currentDateTime());
        sysRobotErrorRecords.setCreateTime(DateUtil.currentDateTime());
        sysRobotErrorRecords.setUpdateTime(DateUtil.currentDateTime());
        sysRobotErrorRecordsMapper.insertSelective(sysRobotErrorRecords);
        if (i == 1){
            robotDamages = sysRobotDamagesMapper.selectByPrimaryKey(sysRobotDamages.getDamagesId());
        }
        return robotDamages;
    }

    /**
     * @Method getRobotDamagesAppList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询损坏赔偿信息列表
     * @Return com.github.pagehelper.PageInfo<com.hna.hka.archive.management.assetsSystem.model.SysRobotDamages>
     * @Date 2021/6/27 15:24
     */
    @Override
    public PageInfo<SysRobotDamages> getRobotDamagesAppList(BaseQueryVo baseQueryVo) {
        PageHelper.startPage(baseQueryVo.getPageNum(), baseQueryVo.getPageSize());
        List<SysRobotDamages> robotDamagesList = sysRobotDamagesMapper.getRobotDamagesAppList(baseQueryVo.getSearch());
        PageInfo<SysRobotDamages> SysRobotDamages = new PageInfo<>(robotDamagesList);
        return SysRobotDamages;
    }

    /**
     * @Method getRobotDamagesList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询损坏赔偿单列表
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/6/27 21:37
     */
    @Override
    public PageDataResult getRobotDamagesList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotDamages> robotDamagesList = sysRobotDamagesMapper.getRobotDamagesList(search);
        if (robotDamagesList.size() > 0){
            PageInfo<SysRobotDamages> pageInfo = new PageInfo<>(robotDamagesList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method editCloseRobotDamages
     * @Author 郭凯
     * @Version  1.0
     * @Description 关闭损坏赔偿
     * @Return int
     * @Date 2021/7/9 17:02
     */
    @Override
    public int editCloseRobotDamages(SysRobotDamages sysRobotDamages) {
        sysRobotDamages.setUpdateDate(DateUtil.currentDateTime());
        return sysRobotDamagesMapper.updateByPrimaryKeySelective(sysRobotDamages);
    }

    /**
     * @Method getRobotDamagesExcel
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询下载Excel表数据
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.SysRobotDamages>
     * @Date 2021/7/19 18:22
     */
    @Override
    public List<SysRobotDamages> getRobotDamagesExcel(Map<String, Object> search) {
        List<SysRobotDamages> robotDamagesList = sysRobotDamagesMapper.getRobotDamagesList(search);
        for (SysRobotDamages robotDamages : robotDamagesList){
            if ("1".equals(robotDamages.getDamagesType())){
                robotDamages.setDamagesTypeName("未支付");
            }else if ("2".equals(robotDamages.getDamagesType())){
                robotDamages.setDamagesTypeName("已支付");
            }else if ("3".equals(robotDamages.getDamagesType())){
                robotDamages.setDamagesTypeName("已关闭");
            }else{
                robotDamages.setDamagesTypeName("未知");
            }
            if ("1".equals(robotDamages.getPaymentPlatform())){
                robotDamages.setPaymentPlatformName("微信");
            }else if ("2".equals(robotDamages.getPaymentPlatform())){
                robotDamages.setPaymentPlatformName("余额");
            }else if ("3".equals(robotDamages.getPaymentPlatform())){
                robotDamages.setPaymentPlatformName("管理员代收");
            }else if ("4".equals(robotDamages.getPaymentPlatform())){
                robotDamages.setPaymentPlatformName("支付宝");
            }else{
                robotDamages.setPaymentPlatformName("未知");
            }
        }
        return robotDamagesList;
    }
}
