package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.model.BigPadSpot;
import com.hna.hka.archive.management.assetsSystem.dao.RealTimeMapper;
import com.hna.hka.archive.management.assetsSystem.dao.SysScenicSpotOperationRulesMapper;
import com.hna.hka.archive.management.assetsSystem.model.ScenicSpot;
import com.hna.hka.archive.management.assetsSystem.model.SysScenicSpotOperationRules;
import com.hna.hka.archive.management.business.dao.BusinessScenicSpotAreaMapper;
import com.hna.hka.archive.management.business.dao.BusinessScenicSpotExpandMapper;
import com.hna.hka.archive.management.business.model.BusinessScenicSpotArea;
import com.hna.hka.archive.management.business.model.BusinessScenicSpotExpand;
import com.hna.hka.archive.management.system.dao.*;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.service.SysScenicSpotService;
import com.hna.hka.archive.management.system.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysScenicSpotServiceImpl
 * @Author: 郭凯
 * @Description: 景区管理业务层（实现）
 * @Date: 2020/5/21 11:30
 * @Version: 1.0
 */
@Service
@Transactional
public class SysScenicSpotServiceImpl implements SysScenicSpotService {

    @Autowired
    private SysScenicSpotMapper sysScenicSpotMapper;

    @Autowired
    private SysScenicSpotBindingMapper sysScenicSpotBindingMapper;

    @Autowired
    private SysScenicSpotPriceModificationLogMapper sysScenicSpotPriceModificationLogMapper;

    @Autowired
    private SysScenicSpotResourceVersionMapper sysScenicSpotResourceVersionMapper;

    @Autowired
    private SysUsersRoleSpotMapper sysUsersRoleSpotMapper;

    @Autowired
    private SysScenicSpotCapPriceMapper sysScenicSpotCapPriceMapper;

    @Autowired
    private SysScenicSpotCapPriceLogMapper sysScenicSpotCapPriceLogMapper;

    @Autowired
    private SysScenicSpotImgMapper sysScenicSpotImgMapper;

    @Autowired
    private SysRobotAppVersionMapper sysRobotAppVersionMapper;

    @Autowired
    private SysRobotPadMapper sysRobotPadMapper;

    @Autowired
    private SysRobotMapper sysRobotMapper;

    @Autowired
    private SysScenicSpotOperationRulesMapper sysScenicSpotOperationRulesMapper;

    @Autowired
    private SysScenicSpotCertificateSpotMapper sysScenicSpotCertificateSpotMapper;

    @Autowired
    private BusinessScenicSpotExpandMapper businessScenicSpotExpandMapper;

    @Autowired
    private BusinessScenicSpotAreaMapper businessScenicSpotAreaMapper;

    @Autowired
    private SysUsersRoleSpotExamineMapper sysUsersRoleSpotExamineMapper;

    @Autowired
    private SysOrderMapper sysOrderMapper;

    @Autowired
    private RealTimeMapper realTimeMapper;

    @Autowired
    private SysScenicSpotTreasureHuntMapper sysScenicSpotTreasureHuntMapper;

    @Value("${GET_SYS_SCENIC_SPOT_IMG_PATH}")
    private String GET_SYS_SCENIC_SPOT_IMG_PATH;

    @Value("${GET_SYS_SCENIC_SPOT_IMG_URL}")
    private String GET_SYS_SCENIC_SPOT_IMG_URL;

//    @Autowired
//    private FileUploadUtil fileUploadUtil;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * @return com.hna.hka.archive.management.system.model.SysScenicSpot
     * @Author 郭凯
     * @Description 根据ID查询景区
     * @Date 11:33 2020/5/21
     * @Param [scenicSpotId]
     **/
    @Override
    public SysScenicSpot getSysScenicSpotById(Long scenicSpotId) {
        return sysScenicSpotMapper.selectByPrimaryKey(scenicSpotId);
    }

    /**
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     * @Author 郭凯
     * @Description 景区列表查询
     * @Date 13:35 2020/5/21
     * @Param [pageNum, pageSize, sysScenicSpot]
     **/
    @Override
    public PageDataResult getScenicSpotList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
//        List<SysScenicSpot> scenicSpotList = sysScenicSpotMapper.selectScenicSpotList(search);
        List<SysScenicSpotExpand> scenicSpotList = sysScenicSpotMapper.selectScenicSpotListNew(search);

        for (SysScenicSpotExpand sysScenicSpotExpand : scenicSpotList) {
            String workTime = sysScenicSpotExpand.getWorkTime();
            String closingTime = sysScenicSpotExpand.getClosingTime();

            int i = cn.hutool.core.date.DateUtil.thisHour(true);

            if (!StringUtils.isEmpty(workTime) && !StringUtils.isEmpty(closingTime)) {
                Integer work = Integer.parseInt(workTime.substring(0, 2));
                Integer closing = Integer.parseInt(closingTime.substring(0, 2));
                if (work <= i && i <= closing) {
                    sysScenicSpotExpand.setBusinessStatus("1");
                } else if (work > i && i > closing) {
                    sysScenicSpotExpand.setBusinessStatus("2");
                } else if (work > i && i < closing) {
                    sysScenicSpotExpand.setBusinessStatus("2");
                } else if (work < i && i > closing) {
                    sysScenicSpotExpand.setBusinessStatus("2");
                }
            }
        }

        if (scenicSpotList.size() != 0) {
//            for (SysScenicSpot sysScenicSpot : scenicSpotList) {
//                if (sysScenicSpot.getScenicSpotAscriptionId()!=0){
//                    SysScenicSpot sysScenicSpotA = sysScenicSpotMapper.selectByPrimaryKey(sysScenicSpot.getScenicSpotAscriptionId());
//                    sysScenicSpot.setScenicSpotAscriptionName(sysScenicSpotA.getScenicSpotName());
//                }
//            }
//            PageInfo<SysScenicSpot> pageInfo = new PageInfo<>(scenicSpotList);
            PageInfo<SysScenicSpotExpand> pageInfo = new PageInfo<>(scenicSpotList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }


    /**
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysScenicSpotBinding>
     * @Author 郭凯
     * @Description 查询景区归属地
     * @Date 14:40 2020/5/21
     * @Param []
     **/
    @Override
    public List<SysScenicSpotBinding> getScenicSpotBindingList() {
        List<SysScenicSpotBinding> SysScenicSpotBinding = sysScenicSpotBindingMapper.getScenicSpotRole();
        List<SysScenicSpotBinding> scenicSpots = new ArrayList<SysScenicSpotBinding>();
        for (SysScenicSpotBinding scenicSpotBinding : SysScenicSpotBinding) {
            if (ToolUtil.isEmpty(scenicSpotBinding.getScenicSpotPid())) {
                scenicSpots.add(scenicSpotBinding);
            }
        }
        return scenicSpots;
    }

    /**
     * 新增景区
     *
     * @param sysScenicSpot
     * @return
     */
    @Override
    public int addScenicSpot(SysScenicSpot sysScenicSpot, SysScenicSpotPriceModificationLog modificationLog) {
        Long id = IdUtils.getSeqId();
        SysScenicSpotResourceVersion resourceVersion = new SysScenicSpotResourceVersion();
        resourceVersion.setResId(IdUtils.getSeqId());
        resourceVersion.setScenicSpotId(id);
        resourceVersion.setResVersion("0.1");
        resourceVersion.setResType("1");
        resourceVersion.setCreateDate(DateUtil.currentDateTime());
        resourceVersion.setUpdateDate(DateUtil.currentDateTime());
        sysScenicSpotResourceVersionMapper.insertSelective(resourceVersion);
        SysScenicSpotResourceVersion scenicSpotResourceVersion = new SysScenicSpotResourceVersion();
        scenicSpotResourceVersion.setResId(IdUtils.getSeqId());
        scenicSpotResourceVersion.setScenicSpotId(id);
        scenicSpotResourceVersion.setResVersion("0.1");
        scenicSpotResourceVersion.setResType("2");
        scenicSpotResourceVersion.setCreateDate(DateUtil.currentDateTime());
        scenicSpotResourceVersion.setUpdateDate(DateUtil.currentDateTime());
        sysScenicSpotResourceVersionMapper.insertSelective(scenicSpotResourceVersion);
        SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
        sysScenicSpotResourceVersion.setResId(IdUtils.getSeqId());
        sysScenicSpotResourceVersion.setScenicSpotId(id);
        sysScenicSpotResourceVersion.setResVersion("0.1");
        sysScenicSpotResourceVersion.setResType("3");
        sysScenicSpotResourceVersion.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotResourceVersion.setUpdateDate(DateUtil.currentDateTime());
        sysScenicSpotResourceVersionMapper.insertSelective(sysScenicSpotResourceVersion);

        /*
        2021.9.3新增景区时同步关联证书
         */
//        SysScenicSpotCertificateSpot sysScenicSpotCertificateSpot = new SysScenicSpotCertificateSpot();
//        sysScenicSpotCertificateSpot.setCertificateSpotId(IdUtils.getSeqId());
//        sysScenicSpotCertificateSpot.setCertificateId(sysScenicSpot.getCertificateId());
//        sysScenicSpotCertificateSpot.setScenicSpotId(id);
//        sysScenicSpotCertificateSpot.setCreateDate(DateUtil.currentDateTime());
//        sysScenicSpotCertificateSpot.setUpdateDate(DateUtil.currentDateTime());
//        sysScenicSpotCertificateSpotMapper.insert(sysScenicSpotCertificateSpot);


        SysScenicSpotBinding binding = sysScenicSpotBindingMapper.selectByPrimaryKey(sysScenicSpot.getScenicSpotFid());

        if (ToolUtil.isNotEmpty(binding)) {
            modificationLog.setScenicSpotProvince(binding.getScenicSpotFname());
        }
        modificationLog.setPriceModificationId(IdUtils.getSeqId());
        modificationLog.setPriceModificationSpotName(sysScenicSpot.getScenicSpotName());
        modificationLog.setScenicSpotPhone(sysScenicSpot.getScenicSpotPhone());
        modificationLog.setScenicSpotContact(sysScenicSpot.getScenicSpotContact());
        modificationLog.setTestStartTime(sysScenicSpot.getTestStartTime());
        modificationLog.setType("1");
        modificationLog.setTrialOperationsTime(sysScenicSpot.getTrialOperationsTime());
        modificationLog.setFormalOperationTime(sysScenicSpot.getFormalOperationTime());
        modificationLog.setStopOperationTime(sysScenicSpot.getStopOperationTime());
        modificationLog.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotPriceModificationLogMapper.insertSelective(modificationLog);
        sysScenicSpot.setScenicSpotId(id);
        sysScenicSpot.setScenicSpotStatus("10");
        sysScenicSpot.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpot.setUpdateDate(DateUtil.currentDateTime());
        SysScenicSpotBinding SysScenicSpotBinding = new SysScenicSpotBinding();
        SysScenicSpotBinding.setScenicSpotFid(id);
        SysScenicSpotBinding.setScenicSpotFname(sysScenicSpot.getScenicSpotName());
        SysScenicSpotBinding.setScenicSpotPid(sysScenicSpot.getScenicSpotFid());
        int i = sysScenicSpotMapper.insertSelective(sysScenicSpot);
        sysScenicSpotBindingMapper.insertSelective(SysScenicSpotBinding);
        return i;
    }

    @Override
    public int addScenicSpotNew(SysScenicSpotExpand sysScenicSpotExpand, SysScenicSpotPriceModificationLog modificationLog, BusinessScenicSpotArea businessScenicSpotArea) {
        Long id = IdUtils.getSeqId();
        SysScenicSpotResourceVersion resourceVersion = new SysScenicSpotResourceVersion();
        resourceVersion.setResId(IdUtils.getSeqId());
        resourceVersion.setScenicSpotId(id);
        resourceVersion.setResVersion("0.1");
        resourceVersion.setResType("1");
        resourceVersion.setCreateDate(DateUtil.currentDateTime());
        resourceVersion.setUpdateDate(DateUtil.currentDateTime());
        sysScenicSpotResourceVersionMapper.insertSelective(resourceVersion);
        SysScenicSpotResourceVersion scenicSpotResourceVersion = new SysScenicSpotResourceVersion();
        scenicSpotResourceVersion.setResId(IdUtils.getSeqId());
        scenicSpotResourceVersion.setScenicSpotId(id);
        scenicSpotResourceVersion.setResVersion("0.1");
        scenicSpotResourceVersion.setResType("2");
        scenicSpotResourceVersion.setCreateDate(DateUtil.currentDateTime());
        scenicSpotResourceVersion.setUpdateDate(DateUtil.currentDateTime());
        sysScenicSpotResourceVersionMapper.insertSelective(scenicSpotResourceVersion);
        SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
        sysScenicSpotResourceVersion.setResId(IdUtils.getSeqId());
        sysScenicSpotResourceVersion.setScenicSpotId(id);
        sysScenicSpotResourceVersion.setResVersion("0.1");
        sysScenicSpotResourceVersion.setResType("3");
        sysScenicSpotResourceVersion.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotResourceVersion.setUpdateDate(DateUtil.currentDateTime());
        sysScenicSpotResourceVersionMapper.insertSelective(sysScenicSpotResourceVersion);

        /*
        2021.9.3新增景区时同步关联证书
         */
//        SysScenicSpotCertificateSpot sysScenicSpotCertificateSpot = new SysScenicSpotCertificateSpot();
//        sysScenicSpotCertificateSpot.setCertificateSpotId(IdUtils.getSeqId());
//        sysScenicSpotCertificateSpot.setCertificateId(sysScenicSpotExpand.getCertificateId());
//        sysScenicSpotCertificateSpot.setScenicSpotId(id);
//        sysScenicSpotCertificateSpot.setCreateDate(DateUtil.currentDateTime());
//        sysScenicSpotCertificateSpot.setUpdateDate(DateUtil.currentDateTime());
//        sysScenicSpotCertificateSpotMapper.insert(sysScenicSpotCertificateSpot);

        SysScenicSpotBinding binding = sysScenicSpotBindingMapper.selectByPrimaryKey(sysScenicSpotExpand.getScenicSpotFid());
        if (ToolUtil.isNotEmpty(binding)) {
            modificationLog.setScenicSpotProvince(binding.getScenicSpotFname());
        }
        modificationLog.setPriceModificationId(IdUtils.getSeqId());
        modificationLog.setPriceModificationSpotName(sysScenicSpotExpand.getScenicSpotName());
        modificationLog.setScenicSpotPhone(sysScenicSpotExpand.getScenicSpotPhone());
        modificationLog.setScenicSpotContact(sysScenicSpotExpand.getScenicSpotContact());
        modificationLog.setTestStartTime(sysScenicSpotExpand.getTestStartTime());
        modificationLog.setType("1");
        modificationLog.setTrialOperationsTime(sysScenicSpotExpand.getTrialOperationsTime());
        modificationLog.setFormalOperationTime(sysScenicSpotExpand.getFormalOperationTime());
        modificationLog.setStopOperationTime(sysScenicSpotExpand.getStopOperationTime());
        modificationLog.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotPriceModificationLogMapper.insertSelective(modificationLog);
        sysScenicSpotExpand.setScenicSpotId(id);
        sysScenicSpotExpand.setScenicSpotStatus("10");
        sysScenicSpotExpand.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotExpand.setUpdateDate(DateUtil.currentDateTime());
        SysScenicSpotBinding SysScenicSpotBinding = new SysScenicSpotBinding();
        SysScenicSpotBinding.setScenicSpotFid(id);
        SysScenicSpotBinding.setScenicSpotFname(sysScenicSpotExpand.getScenicSpotName());
        SysScenicSpotBinding.setScenicSpotFid(id);
        SysScenicSpotBinding.setScenicSpotFname(sysScenicSpotExpand.getScenicSpotName());
        SysScenicSpotBinding.setScenicSpotPid(sysScenicSpotExpand.getScenicSpotFid());
//        SysScenicSpotBinding.setScenicSpotSid(sysScenicSpotExpand.getScenicSpotSid());
//        SysScenicSpotBinding.setScenicSpotQid(sysScenicSpotExpand.getScenicSpotQid());
        //扩展景区
        if (ToolUtil.isNotEmpty(businessScenicSpotArea.getMergerName()) && ToolUtil.isNotEmpty(sysScenicSpotExpand.getScenicSpotArea()) && ToolUtil.isNotEmpty(sysScenicSpotExpand.getRevenueYear()) && ToolUtil.isNotEmpty(sysScenicSpotExpand.getRewardRate())) {
            //查询此景区是否存在拓展信息
            BusinessScenicSpotExpand scenicSpotExpand = businessScenicSpotExpandMapper.selectScenicSpotExpandByScenicId(sysScenicSpotExpand.getScenicSpotId());
            if (ToolUtil.isNotEmpty(scenicSpotExpand)) {
                return 2;
            } else {
                //查询此景区是否存在所在地
                BusinessScenicSpotArea scenicSpotArea = businessScenicSpotAreaMapper.selectAreaScenicSpotId(sysScenicSpotExpand.getScenicSpotId());
                if (scenicSpotArea != null) {
                    businessScenicSpotAreaMapper.deleteByPrimaryKey(scenicSpotArea.getId());
                }
                businessScenicSpotArea.setId(IdUtils.getSeqId());
                businessScenicSpotArea.setScenicSpotId(sysScenicSpotExpand.getScenicSpotId());
                businessScenicSpotArea.setCreateTime(DateUtil.currentDateTime());
                businessScenicSpotArea.setUpdateTime(DateUtil.currentDateTime());
                businessScenicSpotAreaMapper.insertSelective(businessScenicSpotArea);
            }
            BusinessScenicSpotExpand businessScenicSpotExpand = new BusinessScenicSpotExpand();
            businessScenicSpotExpand.setId(IdUtils.getSeqId());
            businessScenicSpotExpand.setImageId(sysScenicSpotExpand.getImageId());
            businessScenicSpotExpand.setScenicSpotId(sysScenicSpotExpand.getScenicSpotId());
            businessScenicSpotExpand.setScenicSpotIntroduce(sysScenicSpotExpand.getScenicSpotIntroduce());
            businessScenicSpotExpand.setRewardRate(sysScenicSpotExpand.getRewardRate());
            businessScenicSpotExpand.setRevenueYear(sysScenicSpotExpand.getRevenueYear());
            businessScenicSpotExpand.setScenicSpotArea(sysScenicSpotExpand.getScenicSpotArea());
            businessScenicSpotExpand.setCreateTime(DateUtil.currentDateTime());
            businessScenicSpotExpand.setUpdateTime(DateUtil.currentDateTime());
            int j = businessScenicSpotExpandMapper.insertSelective(businessScenicSpotExpand);

        }

        int i = sysScenicSpotMapper.insertSelective(sysScenicSpotExpand);
        sysScenicSpotBindingMapper.insertSelective(SysScenicSpotBinding);
        return i;
    }

    /**
     * @return int
     * @Author 郭凯
     * @Description 修改景区信息
     * @Date 17:19 2020/5/21
     * @Param [sysScenicSpot]
     **/
    @Override
    public int editScenicSpot(SysScenicSpot sysScenicSpot, SysScenicSpotPriceModificationLog modificationLog) {


        sysScenicSpot.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotMapper.updateByPrimaryKeySelective(sysScenicSpot);

        //修改景区归属地信息
        SysScenicSpotBinding binding = new SysScenicSpotBinding();
        binding.setScenicSpotFid(sysScenicSpot.getScenicSpotId());
        binding.setScenicSpotFname(sysScenicSpot.getScenicSpotName());
        binding.setScenicSpotPid(sysScenicSpot.getScenicSpotFid());
//        binding.setScenicSpotSid(sysScenicSpot.getScenicSpotSid());
//        binding.setScenicSpotQid(sysScenicSpot.getScenicSpotQid());
        sysScenicSpotBindingMapper.updateByPrimaryKeySelective(binding);
        //添加修改日志
        if (ToolUtil.isNotEmpty(modificationLog.getType()) && "2".equals(modificationLog.getType())) {
            modificationLog.setPriceModificationId(IdUtils.getSeqId());
            modificationLog.setPriceModificationSpotName(sysScenicSpot.getScenicSpotName());
            modificationLog.setScenicSpotDeposit(sysScenicSpot.getScenicSpotDeposit());
            modificationLog.setScenicSpotWeekendPrice(sysScenicSpot.getScenicSpotWeekendPrice());
            modificationLog.setScenicSpotNormalPrice(sysScenicSpot.getScenicSpotNormalPrice());
            modificationLog.setScenicSpotWeekendRentPrice(sysScenicSpot.getScenicSpotWeekendRentPrice());
            modificationLog.setScenicSpotNormalRentPrice(sysScenicSpot.getScenicSpotNormalRentPrice());
            modificationLog.setScenicSpotRentTime(sysScenicSpot.getScenicSpotRentTime());
            modificationLog.setScenicSpotBeyondPrice(sysScenicSpot.getScenicSpotBeyondPrice());
            modificationLog.setScenicSpotNormalTime(sysScenicSpot.getScenicSpotNormalTime());
            modificationLog.setScenicSpotWeekendTime(sysScenicSpot.getScenicSpotWeekendTime());
            modificationLog.setRandomBroadcastTime(sysScenicSpot.getRandomBroadcastTime());
            modificationLog.setScenicSpotFrequency(sysScenicSpot.getScenicSpotFrequency());
            modificationLog.setScenicSpotFenceTime(sysScenicSpot.getScenicSpotFenceTime());
            modificationLog.setScenicSpotForbiddenZoneTime(sysScenicSpot.getScenicSpotForbiddenZoneTime());
            modificationLog.setCreateDate(DateUtil.currentDateTime());
            sysScenicSpotPriceModificationLogMapper.insertSelective(modificationLog);
        }
        if (ToolUtil.isNotEmpty(modificationLog.getType()) && "1".equals(modificationLog.getType())) {
            SysScenicSpotBinding scenicSpotBinding = sysScenicSpotBindingMapper.selectByPrimaryKey(sysScenicSpot.getScenicSpotFid());
            if (ToolUtil.isNotEmpty(scenicSpotBinding)) {
                modificationLog.setScenicSpotProvince(scenicSpotBinding.getScenicSpotFname());
            }
            modificationLog.setPriceModificationId(IdUtils.getSeqId());
            modificationLog.setPriceModificationSpotName(sysScenicSpot.getScenicSpotName());
            modificationLog.setScenicSpotContact(sysScenicSpot.getScenicSpotContact());
            modificationLog.setScenicSpotPhone(sysScenicSpot.getScenicSpotPhone());
            modificationLog.setScenicSpotProvince(scenicSpotBinding.getScenicSpotFname());
            modificationLog.setTestStartTime(sysScenicSpot.getTestStartTime());
            modificationLog.setTrialOperationsTime(sysScenicSpot.getTrialOperationsTime());
            modificationLog.setFormalOperationTime(sysScenicSpot.getFormalOperationTime());
            modificationLog.setStopOperationTime(sysScenicSpot.getStopOperationTime());
            modificationLog.setCreateDate(DateUtil.currentDateTime());
            sysScenicSpotPriceModificationLogMapper.insertSelective(modificationLog);
        }
        return i;
    }


    /**
     * @return int
     * @Author 郭凯
     * @Description 修改景区信息 （改）
     * @Date 17:19 2020/5/21
     * @Param [sysScenicSpot]
     **/
    @Override
    public int editScenicSpotNew(BusinessScenicSpotArea businessScenicSpotArea, SysScenicSpotExpand sysScenicSpotExpand, SysScenicSpotPriceModificationLog modificationLog) {

        //查询此景区是否存在所在地businessScenicSpotArea
        BusinessScenicSpotArea scenicSpotArea = businessScenicSpotAreaMapper.selectAreaScenicSpotId(sysScenicSpotExpand.getScenicSpotId());
        if (ToolUtil.isNotEmpty(scenicSpotArea)) {
            int i = businessScenicSpotAreaMapper.deleteByPrimaryKey(scenicSpotArea.getId());
        }
        businessScenicSpotArea.setId(IdUtils.getSeqId());
        businessScenicSpotArea.setScenicSpotId(sysScenicSpotExpand.getScenicSpotId());
        businessScenicSpotArea.setCreateTime(DateUtil.currentDateTime());
        businessScenicSpotArea.setUpdateTime(DateUtil.currentDateTime());
        int t = businessScenicSpotAreaMapper.insertSelective(businessScenicSpotArea);

        //查询此景区是否存在扩展景区
        BusinessScenicSpotExpand businessScenicSpotExpandNew = businessScenicSpotExpandMapper.selectScenicSpotExpandByScenicId(sysScenicSpotExpand.getScenicSpotId());
        if (ToolUtil.isNotEmpty(businessScenicSpotExpandNew)) {
            BusinessScenicSpotExpand businessScenicSpotExpand = new BusinessScenicSpotExpand();
            businessScenicSpotExpand.setImageId(sysScenicSpotExpand.getImageId());
            businessScenicSpotExpand.setScenicSpotId(sysScenicSpotExpand.getScenicSpotId());
            businessScenicSpotExpand.setImageId(sysScenicSpotExpand.getImageId());
            businessScenicSpotExpand.setCityId(sysScenicSpotExpand.getCityId());
            businessScenicSpotExpand.setProvinceId(sysScenicSpotExpand.getProvinceId());
            businessScenicSpotExpand.setRegionId(sysScenicSpotExpand.getRegionId());
            businessScenicSpotExpand.setScenicSpotArea(sysScenicSpotExpand.getScenicSpotArea());
            businessScenicSpotExpand.setRevenueYear(sysScenicSpotExpand.getRevenueYear());
            businessScenicSpotExpand.setRewardRate(sysScenicSpotExpand.getRewardRate());
            businessScenicSpotExpand.setScenicSpotIntroduce(sysScenicSpotExpand.getScenicSpotIntroduce());
            businessScenicSpotExpand.setUpdateTime(DateUtil.currentDateTime());
            int j = businessScenicSpotExpandMapper.updateByPrimaryKeySelectiveBySpotId(businessScenicSpotExpand);
        } else {
            BusinessScenicSpotExpand businessScenicSpotExpand = new BusinessScenicSpotExpand();
            businessScenicSpotExpand.setId(IdUtils.getSeqId());
            businessScenicSpotExpand.setImageId(sysScenicSpotExpand.getImageId());
            businessScenicSpotExpand.setScenicSpotId(sysScenicSpotExpand.getScenicSpotId());
            businessScenicSpotExpand.setImageId(sysScenicSpotExpand.getImageId());
            businessScenicSpotExpand.setCityId(sysScenicSpotExpand.getCityId());
            businessScenicSpotExpand.setProvinceId(sysScenicSpotExpand.getProvinceId());
            businessScenicSpotExpand.setRegionId(sysScenicSpotExpand.getRegionId());
            businessScenicSpotExpand.setScenicSpotArea(sysScenicSpotExpand.getScenicSpotArea());
            businessScenicSpotExpand.setRevenueYear(sysScenicSpotExpand.getRevenueYear());
            businessScenicSpotExpand.setRewardRate(sysScenicSpotExpand.getRewardRate());
            businessScenicSpotExpand.setScenicSpotIntroduce(sysScenicSpotExpand.getScenicSpotIntroduce());
            businessScenicSpotExpand.setCreateTime(DateUtil.currentDateTime());
            businessScenicSpotExpand.setUpdateTime(DateUtil.currentDateTime());
            int j = businessScenicSpotExpandMapper.insertSelective(businessScenicSpotExpand);

        }

        sysScenicSpotExpand.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotMapper.updateByPrimaryKeySelective(sysScenicSpotExpand);
        //修改景区归属地信息
        SysScenicSpotBinding binding = new SysScenicSpotBinding();
        binding.setScenicSpotFid(sysScenicSpotExpand.getScenicSpotId());
        binding.setScenicSpotFname(sysScenicSpotExpand.getScenicSpotName());
        binding.setScenicSpotType(0);
        binding.setScenicSpotPid(sysScenicSpotExpand.getScenicSpotFid());
//        binding.setScenicSpotSid(sysScenicSpotExpand.getScenicSpotSid());
//        binding.setScenicSpotQid(sysScenicSpotExpand.getScenicSpotQid());
        sysScenicSpotBindingMapper.updateByPrimaryKeySelective(binding);
        //添加修改日志
        if (ToolUtil.isNotEmpty(modificationLog.getType()) && "2".equals(modificationLog.getType())) {
            modificationLog.setPriceModificationId(IdUtils.getSeqId());
            modificationLog.setPriceModificationSpotName(sysScenicSpotExpand.getScenicSpotName());
            modificationLog.setScenicSpotDeposit(sysScenicSpotExpand.getScenicSpotDeposit());
            modificationLog.setScenicSpotWeekendPrice(sysScenicSpotExpand.getScenicSpotWeekendPrice());
            modificationLog.setScenicSpotNormalPrice(sysScenicSpotExpand.getScenicSpotNormalPrice());
            modificationLog.setScenicSpotWeekendRentPrice(sysScenicSpotExpand.getScenicSpotWeekendRentPrice());
            modificationLog.setScenicSpotNormalRentPrice(sysScenicSpotExpand.getScenicSpotNormalRentPrice());
            modificationLog.setScenicSpotRentTime(sysScenicSpotExpand.getScenicSpotRentTime());
            modificationLog.setScenicSpotBeyondPrice(sysScenicSpotExpand.getScenicSpotBeyondPrice());
            modificationLog.setScenicSpotNormalTime(sysScenicSpotExpand.getScenicSpotNormalTime());
            modificationLog.setScenicSpotWeekendTime(sysScenicSpotExpand.getScenicSpotWeekendTime());
            modificationLog.setRandomBroadcastTime(sysScenicSpotExpand.getRandomBroadcastTime());
            modificationLog.setScenicSpotFrequency(sysScenicSpotExpand.getScenicSpotFrequency());
            modificationLog.setScenicSpotFenceTime(sysScenicSpotExpand.getScenicSpotFenceTime());
            modificationLog.setScenicSpotForbiddenZoneTime(sysScenicSpotExpand.getScenicSpotForbiddenZoneTime());
            modificationLog.setCreateDate(DateUtil.currentDateTime());
            sysScenicSpotPriceModificationLogMapper.insertSelective(modificationLog);
        }
        if (ToolUtil.isNotEmpty(modificationLog.getType()) && "1".equals(modificationLog.getType())) {
            SysScenicSpotBinding scenicSpotBinding = sysScenicSpotBindingMapper.selectByPrimaryKey(sysScenicSpotExpand.getScenicSpotFid());
            if (ToolUtil.isNotEmpty(scenicSpotBinding)) {
                modificationLog.setScenicSpotProvince(scenicSpotBinding.getScenicSpotFname());
            }
            modificationLog.setPriceModificationId(IdUtils.getSeqId());
            modificationLog.setPriceModificationSpotName(sysScenicSpotExpand.getScenicSpotName());
            modificationLog.setScenicSpotContact(sysScenicSpotExpand.getScenicSpotContact());
            modificationLog.setScenicSpotPhone(sysScenicSpotExpand.getScenicSpotPhone());
            modificationLog.setScenicSpotProvince(scenicSpotBinding.getScenicSpotFname());
            modificationLog.setTestStartTime(sysScenicSpotExpand.getTestStartTime());
            modificationLog.setTrialOperationsTime(sysScenicSpotExpand.getTrialOperationsTime());
            modificationLog.setFormalOperationTime(sysScenicSpotExpand.getFormalOperationTime());
            modificationLog.setStopOperationTime(sysScenicSpotExpand.getStopOperationTime());
            modificationLog.setCreateDate(DateUtil.currentDateTime());
            sysScenicSpotPriceModificationLogMapper.insertSelective(modificationLog);
        }
        return i;
    }

    /**
     * @return int
     * @Author 郭凯
     * @Description 删除景区
     * @Date 17:32 2020/5/21
     * @Param [scenicSpotId]
     **/
    @Override
    public int delScenicSpot(Long scenicSpotId) {
        int i = sysScenicSpotMapper.deleteByPrimaryKey(scenicSpotId);
        int j = sysUsersRoleSpotMapper.deleteByScenicSpotId(scenicSpotId);
        int h = sysScenicSpotBindingMapper.deleteByPrimaryKey(scenicSpotId);
        int k = sysScenicSpotCertificateSpotMapper.deleteBySpotId(scenicSpotId);
        int g = sysUsersRoleSpotExamineMapper.deleteByScenicSpotId(scenicSpotId);
        int m = businessScenicSpotExpandMapper.deleteBySpotId(scenicSpotId);
        int n = businessScenicSpotAreaMapper.deleteBySpotId(scenicSpotId);
        return i;
    }

    /**
     * @return int
     * @Author 郭凯
     * @Description 修改景区状态
     * @Date 10:12 2020/5/22
     * @Param [sysScenicSpot]
     **/
    @Override
    public int updateScenicSpotState(SysScenicSpot sysScenicSpot) {
        int i = sysScenicSpotMapper.updateByPrimaryKeySelective(sysScenicSpot);
        if ("1".equals(sysScenicSpot.getRobotWakeupWords()) || "3".equals(sysScenicSpot.getRobotWakeupWords())) {
            List<SysRobot> robotListAll = sysRobotMapper.getRobotListAll(sysScenicSpot.getScenicSpotId());
            for (SysRobot sysRobot : robotListAll) {
                sysRobot.setEquipmentStatus("30");
                sysRobotMapper.updateByPrimaryKeySelective(sysRobot);
            }

        }


        return i;
    }

    /**
     * @return com.hna.hka.archive.management.system.model.SysScenicSpot
     * @Author 郭凯
     * @Description 获取当前景区
     * @Date 11:01 2020/5/31
     * @Param [scenicSpotId]
     **/
    @Override
    public SysScenicSpot getCurrentScenicSpot(long scenicSpotId) {
        return sysScenicSpotMapper.selectByPrimaryKey(scenicSpotId);
    }

    /**
     * @return com.hna.hka.archive.management.system.model.SysScenicSpotCapPrice
     * @Author 郭凯
     * @Description 查询当前景区是否有封顶价格
     * @Date 13:28 2020/7/23
     * @Param [scenicSpotId]
     **/
    @Override
    public SysScenicSpotCapPrice getCapPriceByScenicSpotId(Long scenicSpotId) {
        return sysScenicSpotCapPriceMapper.selectCapPriceByScenicSpotId(scenicSpotId);
    }

    /**
     * @return int
     * @Author 郭凯
     * @Description 新增景区封顶价格
     * @Date 13:44 2020/7/23
     * @Param [capPrice]
     **/
    @Override
    public int addCapPrice(SysScenicSpotCapPrice capPrice, SysUsers user) {
        capPrice.setCapPriceId(IdUtils.getSeqId());
        capPrice.setCreateDate(DateUtil.currentDateTime());
        capPrice.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotCapPriceMapper.insertSelective(capPrice);
        if (i == 1) {
            SysScenicSpotCapPriceLog sysScenicSpotCapPriceLog = new SysScenicSpotCapPriceLog();
            sysScenicSpotCapPriceLog.setCapPriceId(IdUtils.getSeqId());
            sysScenicSpotCapPriceLog.setScenicSpotId(capPrice.getScenicSpotId());
            sysScenicSpotCapPriceLog.setLoginName(user.getUserName());
            sysScenicSpotCapPriceLog.setNormalCapOneTime(capPrice.getNormalCapOneTime());
            sysScenicSpotCapPriceLog.setNormalCapOnePrice(capPrice.getNormalCapOnePrice());
            sysScenicSpotCapPriceLog.setNormalCapOneUnitPrice(capPrice.getNormalCapOneUnitPrice());
            sysScenicSpotCapPriceLog.setNormalCapTwoTime(capPrice.getNormalCapTwoTime());
            sysScenicSpotCapPriceLog.setNormalCapTwoPrice(capPrice.getNormalCapTwoPrice());
            sysScenicSpotCapPriceLog.setNormalCapTwoUnitPrice(capPrice.getNormalCapTwoUnitPrice());
            sysScenicSpotCapPriceLog.setWeekendCapOneTime(capPrice.getWeekendCapOneTime());
            sysScenicSpotCapPriceLog.setWeekendCapOnePrice(capPrice.getWeekendCapOnePrice());
            sysScenicSpotCapPriceLog.setWeekendCapOneUnitPrice(capPrice.getWeekendCapOneUnitPrice());
            sysScenicSpotCapPriceLog.setWeekendCapTwoTime(capPrice.getWeekendCapTwoTime());
            sysScenicSpotCapPriceLog.setWeekendCapTwoPrice(capPrice.getWeekendCapTwoPrice());
            sysScenicSpotCapPriceLog.setWeekendCapTwoUnitPrice(capPrice.getWeekendCapTwoUnitPrice());
            sysScenicSpotCapPriceLog.setCreateDate(DateUtil.currentDateTime());
            sysScenicSpotCapPriceLog.setUpdateDate(DateUtil.currentDateTime());
            sysScenicSpotCapPriceLogMapper.insertSelective(sysScenicSpotCapPriceLog);
        }
        return i;
    }

    /**
     * @return com.hna.hka.archive.management.system.model.SysScenicSpotCapPrice
     * @Author 郭凯
     * @Description 查询景区封顶价格
     * @Date 11:40 2020/7/24
     * @Param [scenicSpotId]
     **/
    @Override
    public SysScenicSpotCapPrice getScenicSpotCapPriceByScenicSpotId(Long scenicSpotId) {
        return sysScenicSpotCapPriceMapper.selectCapPriceByScenicSpotId(scenicSpotId);
    }

    /**
     * @return int
     * @Author 郭凯
     * @Description 编辑景区封顶价格
     * @Date 13:44 2020/7/24
     * @Param [capPrice]
     **/
    @Override
    public int editCapPrice(SysScenicSpotCapPrice capPrice, SysUsers user) {
        capPrice.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotCapPriceMapper.updateByPrimaryKeySelective(capPrice);
        if (i == 1) {
            SysScenicSpotCapPriceLog sysScenicSpotCapPriceLog = new SysScenicSpotCapPriceLog();
            sysScenicSpotCapPriceLog.setCapPriceId(IdUtils.getSeqId());
            sysScenicSpotCapPriceLog.setScenicSpotId(capPrice.getScenicSpotId());
            sysScenicSpotCapPriceLog.setLoginName(user.getUserName());
            sysScenicSpotCapPriceLog.setNormalCapOneTime(capPrice.getNormalCapOneTime());
            sysScenicSpotCapPriceLog.setNormalCapOnePrice(capPrice.getNormalCapOnePrice());
            sysScenicSpotCapPriceLog.setNormalCapOneUnitPrice(capPrice.getNormalCapOneUnitPrice());
            sysScenicSpotCapPriceLog.setNormalCapTwoTime(capPrice.getNormalCapTwoTime());
            sysScenicSpotCapPriceLog.setNormalCapTwoPrice(capPrice.getNormalCapTwoPrice());
            sysScenicSpotCapPriceLog.setNormalCapTwoUnitPrice(capPrice.getNormalCapTwoUnitPrice());
            sysScenicSpotCapPriceLog.setWeekendCapOneTime(capPrice.getWeekendCapOneTime());
            sysScenicSpotCapPriceLog.setWeekendCapOnePrice(capPrice.getWeekendCapOnePrice());
            sysScenicSpotCapPriceLog.setWeekendCapOneUnitPrice(capPrice.getWeekendCapOneUnitPrice());
            sysScenicSpotCapPriceLog.setWeekendCapTwoTime(capPrice.getWeekendCapTwoTime());
            sysScenicSpotCapPriceLog.setWeekendCapTwoPrice(capPrice.getWeekendCapTwoPrice());
            sysScenicSpotCapPriceLog.setWeekendCapTwoUnitPrice(capPrice.getWeekendCapTwoUnitPrice());
            sysScenicSpotCapPriceLog.setCreateDate(DateUtil.currentDateTime());
            sysScenicSpotCapPriceLog.setUpdateDate(DateUtil.currentDateTime());
            sysScenicSpotCapPriceLogMapper.insertSelective(sysScenicSpotCapPriceLog);
        }
        return i;
    }

    /**
     * @return int
     * @Author 郭凯
     * @Description 添加景区图片
     * @Date 16:37 2020/11/25
     * @Param [file, sysScenicSpotImg]
     **/
    @Override
    public int addScenicSpotPicture(MultipartFile file, SysScenicSpotImg sysScenicSpotImg) {
        //根据景区ID查询景区图片
        SysScenicSpotImg scenicSpotImg = sysScenicSpotImgMapper.getScenicSpotImgByScenicSpotId(sysScenicSpotImg.getScenicSpotId());
        if (ToolUtil.isNotEmpty(scenicSpotImg)) {
            sysScenicSpotImgMapper.deleteByPrimaryKey(scenicSpotImg.getScneicSpotImgId());
        }
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".png") || type.equals(".jpg")) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = GET_SYS_SCENIC_SPOT_IMG_PATH + filename;// 存放位置
            File destFile = new File(path);
            try {

                // 限制大小
//                long size = file.getSize() / 1024;//kb
//                if (size >= 2048){
//                    return 3;
//                }

                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);


                //图片压缩上传


//                CompressUtils.compress(file.getInputStream(),destFile,10000);


                //   阿里OSS文件存储_图片上传
                //   String upload = fileUploadUtil.upload(file, GET_SYS_SCENIC_SPOT_IMG_PATH.substring(1) + filename);
//                System.out.println(upload);

            } catch (Exception e) {
                e.printStackTrace();
            }//复制文件到指定目录
            sysScenicSpotImg.setScneicSpotImgId(IdUtils.getSeqId());
            sysScenicSpotImg.setScneicSpotImgUrl(GET_SYS_SCENIC_SPOT_IMG_URL + filename);
            sysScenicSpotImg.setCreateDate(DateUtil.currentDateTime());
            sysScenicSpotImg.setUpdateDate(DateUtil.currentDateTime());
            return sysScenicSpotImgMapper.insertSelective(sysScenicSpotImg);
        } else {
            return 2;
        }
    }

    /**
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysScenicSpot>
     * @Author 郭凯
     * @Description 下载景区计费规则Excel表
     * @Date 13:10 2020/12/7
     * @Param [search]
     **/
    @Override
    public List<SysScenicSpot> getScenicSpotBillingRulesList(Map<String, Object> search) {
        return sysScenicSpotMapper.getScenicSpotBillingRulesList(search);
    }

    /**
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     * @Author 郭凯
     * @Description 景区封顶价格列表查询
     * @Date 15:33 2020/12/7
     * @Param [pageNum, pageSize, search]
     **/
    @Override
    public PageDataResult getScenicSpotCapPriceList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotCapPrice> scenicSpotCapPriceList = sysScenicSpotCapPriceMapper.getScenicSpotCapPriceList(search);
        if (scenicSpotCapPriceList.size() != 0) {
            PageInfo<SysScenicSpotCapPrice> pageInfo = new PageInfo<>(scenicSpotCapPriceList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     * @Author 郭凯
     * @Description 景区PAD管理列表查询
     * @Date 12:43 2020/12/15
     * @Param [pageNum, pageSize, search]
     **/
    @Override
    public PageDataResult getScenicSpotPadList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotAppVersion> sysRobotAppVersionList = sysRobotAppVersionMapper.getScenicSpotPadList(search);
        if (sysRobotAppVersionList.size() != 0) {
            PageInfo<SysRobotAppVersion> pageInfo = new PageInfo<>(sysRobotAppVersionList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @return com.hna.hka.archive.management.system.model.SysRobotAppVersion
     * @Author 郭凯
     * @Description 根据景区ID查询景区PAD信息
     * @Date 13:50 2020/12/15
     * @Param [scenicSpotId]
     **/
    @Override
    public SysRobotAppVersion getRobotAppVersionById(Long scenicSpotId) {
        return sysRobotAppVersionMapper.getAppVersionNumber(scenicSpotId);
    }

    /**
     * @return int
     * @Author 郭凯
     * @Description 添加景区PAD
     * @Date 13:54 2020/12/15
     * @Param [scenicSpotId, padId]
     **/
    @Override
    public int addScenicSpotPad(Long scenicSpotId, Long padId) {
        SysRobotPad sysRobotPad = sysRobotPadMapper.selectByPrimaryKey(padId);
        if (ToolUtil.isEmpty(sysRobotPad)) {
            return 2;
        }
        SysRobotAppVersion sysRobotAppVersion = new SysRobotAppVersion();
        sysRobotAppVersion.setVersionId(IdUtils.getSeqId());
        sysRobotAppVersion.setScenicSpotId(scenicSpotId);
        sysRobotAppVersion.setVersionUrl(sysRobotPad.getPadUrl());
        sysRobotAppVersion.setVersionDescription(sysRobotPad.getPadDescription());
        sysRobotAppVersion.setVersionNumber(sysRobotPad.getPadNumber());
        sysRobotAppVersion.setCreateDate(DateUtil.currentDateTime());
        return sysRobotAppVersionMapper.insertSelective(sysRobotAppVersion);
    }

    /**
     * @return int
     * @Author 郭凯
     * @Description 编辑景区PAD
     * @Date 14:29 2020/12/15
     * @Param [versionId, scenicSpotId, padId]
     **/
    @Override
    public int editScenicSpotPad(Long versionId, Long scenicSpotId, Long padId) {
        SysRobotPad sysRobotPad = sysRobotPadMapper.selectByPrimaryKey(padId);
        if (ToolUtil.isEmpty(sysRobotPad)) {
            return 2;
        }
        SysRobotAppVersion sysRobotAppVersion = new SysRobotAppVersion();
        sysRobotAppVersion.setVersionId(versionId);
        sysRobotAppVersion.setVersionUrl(sysRobotPad.getPadUrl());
        sysRobotAppVersion.setVersionDescription(sysRobotPad.getPadDescription());
        sysRobotAppVersion.setVersionNumber(sysRobotPad.getPadNumber());
        return sysRobotAppVersionMapper.updateByPrimaryKeySelective(sysRobotAppVersion);
    }

    /**
     * @param @param  pageNum
     * @param @param  pageSize
     * @param @param  search
     * @param @return
     * @throws
     * @Author 郭凯
     * @Description: 景区语义交互资源管理列表查询
     * @Title: getSemanticResourcesList
     * @date 2020年12月30日 上午10:24:00
     */
    @Override
    public PageDataResult getSemanticResourcesList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        // TODO Auto-generated method stub
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpot> scenicSpotList = sysScenicSpotMapper.selectScenicSpotList(search);
        if (scenicSpotList.size() != 0) {
            PageInfo<SysScenicSpot> pageInfo = new PageInfo<>(scenicSpotList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @param @param  search
     * @param @return
     * @throws
     * @Author 郭凯
     * @Description: 下载Excel表数据查询
     * @Title: getScenicSpotExcel
     * @date 2021年1月11日 下午4:30:53
     */
    @Override
    public List<SysScenicSpot> getScenicSpotExcel(Map<String, String> search) {
        // TODO Auto-generated method stub
        List<SysScenicSpot> scenicSpotList = sysScenicSpotMapper.selectScenicSpotList(search);
        for (SysScenicSpot scenicSpot : scenicSpotList) {
            scenicSpot.setRobotWakeupWordsName(DictUtils.getScenicSpotStateMap().get(scenicSpot.getRobotWakeupWords()));
        }
        return scenicSpotList;
    }

    /**
     * @Method addScenicSpotOperationRules
     * @Author 郭凯
     * @Version 1.0
     * @Description 定时录入景区运营规则
     * @Return void
     * @Date 2021/7/4 18:19
     */
    @Override
    public void addScenicSpotOperationRules() {
        List<SysScenicSpot> scenicSpotList = sysScenicSpotMapper.getScenicSpotList();
        for (SysScenicSpot scenicSpot : scenicSpotList) {
            SysScenicSpotOperationRules scenicSpotOperationRules = new SysScenicSpotOperationRules();
            scenicSpotOperationRules.setOperationRulesId(IdUtils.getSeqId());
            scenicSpotOperationRules.setScenicSpotId(scenicSpot.getScenicSpotId());
            scenicSpotOperationRules.setOperateStartTime(DateUtil.crutDate() + " " + "08:00:00");
            scenicSpotOperationRules.setOperateEndTime(DateUtil.crutDate() + " " + "18:00:00");
            List<SysRobot> robotList = sysRobotMapper.getRobotListByScenicSpotId(scenicSpot.getScenicSpotId());
            scenicSpotOperationRules.setRobotNumber(String.valueOf(robotList.size()));
            scenicSpotOperationRules.setCreateDate(DateUtil.currentDateTime());
            scenicSpotOperationRules.setUpdateDate(DateUtil.currentDateTime());
            sysScenicSpotOperationRulesMapper.insertSelective(scenicSpotOperationRules);
        }
    }

    /**
     * @Method
     * @Author 张
     * @Version 1.0
     * @Description 查询归属景区
     * @Return
     * @Date
     */
    @Override
    public List<SysScenicSpotBinding> getScenicSpotBindingListA() {
        List<SysScenicSpotBinding> SysScenicSpotBinding = sysScenicSpotBindingMapper.getScenicSpotRole();
        List<SysScenicSpotBinding> scenicSpots = new ArrayList<SysScenicSpotBinding>();
        for (SysScenicSpotBinding scenicSpotBinding : SysScenicSpotBinding) {
            if (ToolUtil.isEmpty(scenicSpotBinding.getScenicSpotType()) || scenicSpotBinding.getScenicSpotType() == 0) {
                scenicSpots.add(scenicSpotBinding);
            }
        }
        return scenicSpots;
    }


    @Override
    public List<SysScenicSpotCapPrice> getScenicSpotCapPriceExcel(Map<String, String> search) {
        // TODO Auto-generated method stub
        List<SysScenicSpotCapPrice> scenicSpotCapPriceList = sysScenicSpotCapPriceMapper.getScenicSpotCapPriceList(search);
        return scenicSpotCapPriceList;
    }

    /**
     * 修改寻宝活动开启与关闭
     *
     * @param sysScenicSpotid
     * @return
     */
    @Override
    public int updateScenicSpotSwitchs(Long sysScenicSpotid, String switchs) {
//        boolean b = this.soptRedis();
//        System.out.println(b);
        SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(sysScenicSpotid);
        sysScenicSpot.setHuntSwitch(switchs);
        if (switchs.equals("2")) {
            int i = sysScenicSpotMapper.updateByPrimaryKeySelective(sysScenicSpot);
            return 2;
        } else if (switchs.equals("0")) {
            int i = sysScenicSpotMapper.updateByPrimaryKeySelective(sysScenicSpot);
            return 0;
        } else {
            int i = sysScenicSpotMapper.updateByPrimaryKeySelective(sysScenicSpot);
            return 1;
        }
    }

    /**
     * 获取景区名称和id
     *
     * @return
     */
    @Override
    public List<SysScenicSpot> getScenicSpotNameList() {

        List<SysScenicSpot> scenicSpotList = sysScenicSpotMapper.getScenicSpotNameList();
        return scenicSpotList;
    }

    @Override
    public SysScenicSpotAndCap getSysScenicSpotAndCap(Long scenicSpotId) {

        SysScenicSpotAndCap sysScenicSpot = sysScenicSpotMapper.getSysScenicSpotAndCap(scenicSpotId);

        return sysScenicSpot;
    }

    /**
     * 查询当前景区是否寻宝活动状态
     *
     * @param scenicSpotId
     * @return
     */
    @Override
    public int getScenicSpotSwitchs(long scenicSpotId) {

        int i = sysScenicSpotMapper.getScenicSpotSwitchs(scenicSpotId);


        return i;
    }


    @Override
    public int delCapPrice(SysScenicSpotCapPrice capPrice) {


//        SysScenicSpotCapPrice sysScenicSpotCapPrice = sysScenicSpotCapPriceMapper.selectCapPriceByScenicSpotId(capPrice.getScenicSpotId());
        SysScenicSpotCapPrice sysScenicSpotCapPrice = sysScenicSpotCapPriceMapper.selectByPrimaryKey(capPrice.getCapPriceId());
        if (StringUtils.isEmpty(sysScenicSpotCapPrice)) {
            return 0;
        }

        int i = sysScenicSpotCapPriceMapper.deleteByPrimaryKey(capPrice.getCapPriceId());

        return i;

    }

    /**
     * 大屏中获取最新景区列表
     *
     * @return
     */
    @Override
    public List<BigPadSpot> getSpotIdList() {

        List<BigPadSpot> scenicSpotList = sysScenicSpotMapper.getBigPidSpotDropDownBox();

        for (BigPadSpot bigPidSpot : scenicSpotList) {
            SysScenicSpotBinding sysScenicSpotBinding = sysScenicSpotBindingMapper.selectByPrimaryKey(bigPidSpot.getScenicSpotId());

            if (!StringUtils.isEmpty(sysScenicSpotBinding)) {
                if (!StringUtils.isEmpty(sysScenicSpotBinding.getScenicSpotPid())) {
                    SysScenicSpotBinding sysScenicSpotBinding1 = sysScenicSpotBindingMapper.selectByPrimaryKey(sysScenicSpotBinding.getScenicSpotPid());
                    if (!StringUtils.isEmpty(sysScenicSpotBinding1.getScenicSpotFname())) {
                        bigPidSpot.setScenicSpotFname(sysScenicSpotBinding1.getScenicSpotFname());
                        bigPidSpot.setScenicSpotPid(sysScenicSpotBinding1.getScenicSpotFid());
                    }
                }
            } else {
                bigPidSpot.setScenicSpotFname("");

            }
        }

        return scenicSpotList;

    }

    /**
     * 获取景区计费规则
     *
     * @param spotId
     * @return
     */
    @Override
    public Map getSpotIdPrice(String spotId) {

        Map<String, Object> map = new HashMap<>();
        SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByScenicSpotId(Long.parseLong(spotId));
        SysScenicSpotCapPrice sysScenicSpotCapPrice = sysScenicSpotCapPriceMapper.selectCapPriceByScenicSpotId(Long.parseLong(spotId));

        if (!StringUtils.isEmpty(sysScenicSpot)) {
            map.put("weekendRent", sysScenicSpot.getScenicSpotWeekendRentPrice());
            map.put("normalRent", sysScenicSpot.getScenicSpotNormalRentPrice());
            map.put("weekend", sysScenicSpot.getScenicSpotWeekendPrice());
            map.put("normal", sysScenicSpot.getScenicSpotNormalPrice());
        }
        if (!StringUtils.isEmpty(sysScenicSpotCapPrice)) {
            map.put("normalCappedPrice", sysScenicSpotCapPrice.getNormalCapOnePrice());
            map.put("weekendCappedPirce", sysScenicSpotCapPrice.getWeekendCapOnePrice());
        }
        return map;

    }

    @Override
    public SysScenicSpot getSpotNameById(String scenicSpotName) {

        SysScenicSpot sysScenicSpot = sysScenicSpotMapper.getSpotNameById(scenicSpotName);

        return sysScenicSpot;
    }

    //获取所有景区
    @Override
    public List<SysScenicSpot> getSysScenicSpotAll() {
        List<SysScenicSpot> scenicSpotList = sysScenicSpotMapper.getScenicSpotList();
        return scenicSpotList;
    }

    //获取归属地表全部数据
    @Override
    public List<SysScenicSpotBinding> getScenicSpotBindingAllList() {

        List<SysScenicSpotBinding> list = sysScenicSpotBindingMapper.selectBindingsList();
        return list;
    }

    @Override
    public void timingScenicSpotOrder() {

        List<SysScenicSpot> scenicSpotListWords = sysScenicSpotMapper.getScenicSpotList();
        Integer jxz = 0;
        Double price = 0d;
        String date = DateUtil.crutDate();
        SysOrderRealTime sysOrderRealTime = new SysOrderRealTime();
        String dateTime = DateUtil.currentDateTime();
        String dateTimeN = dateTime.substring(0, 16);
        for (SysScenicSpot scenicSpotListWord : scenicSpotListWords) {
            sysOrderRealTime = new SysOrderRealTime();
            //进行中订单
            jxz = sysOrderMapper.getJXZOrder(scenicSpotListWord.getScenicSpotId(), date);
            //收入
            price = sysOrderMapper.getOrderPrice(scenicSpotListWord.getScenicSpotId(), date);
            sysOrderRealTime.setRealTime(dateTimeN);
            sysOrderRealTime.setSpotId(scenicSpotListWord.getScenicSpotId());
            sysOrderRealTime.setJxz(jxz);
            sysOrderRealTime.setDrys(price);
            realTimeMapper.insert(sysOrderRealTime);
        }

    }

    /**
     * 一键开启或关闭寻宝活动
     *
     * @param switchs
     * @return
     */
    @Override
    public int oneTouchUpdateScenicSpotSwitchs(String switchs) {

        List<SysScenicSpot> sysScenicSpotList = sysScenicSpotMapper.getScenicSpotList();
        int i = 0;
        for (SysScenicSpot sysScenicSpot : sysScenicSpotList) {
            sysScenicSpot.setHuntSwitch(switchs);
            i = sysScenicSpotMapper.updateByPrimaryKeySelective(sysScenicSpot);

        }

        if (switchs.equals("2")) {
            if (i > 0) {
                return 2;
            } else {
                return 3;
            }
        } else if (switchs.equals("0")) {

            if (i > 0) {
                return 0;
            } else {
                return 3;
            }
        } else {
            if (i > 0) {
                return 1;
            } else {
                return 3;
            }
        }
    }

    @Override
    public List<SysScenicSpot> getScenicSpotById(ScenicSpot scenicSpot) {
        return sysScenicSpotMapper.selectById(scenicSpot);
    }



    /**
     * 将景区导入redis
     */
    public boolean soptRedis() {
        boolean set = false;
        List<SysScenicSpotAndCap> sysScenicSpotAndCapList = sysScenicSpotMapper.getSysScenicSpotAndCapList();

        try {
            for (SysScenicSpotAndCap sysScenicSpotAndCap : sysScenicSpotAndCapList) {

                if (StringUtils.isEmpty(sysScenicSpotAndCap.getScenicSpotId())) {
                    SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectScenicNameByName(sysScenicSpotAndCap.getScenicSpotName());
                    JSONObject jsonArray = JSONObject.fromObject(sysScenicSpotAndCap);
                    String s = JsonUtils.toString(jsonArray);
                    set = redisUtil.set(sysScenicSpot.getScenicSpotId().toString(), s);
                    if (set != true) {
                        System.out.println(sysScenicSpotAndCap.getScenicSpotId());
                    }
                } else {
                    JSONObject jsonArray = JSONObject.fromObject(sysScenicSpotAndCap);
                    String s = JsonUtils.toString(jsonArray);
                    set = redisUtil.set(sysScenicSpotAndCap.getScenicSpotId().toString(), s);
                    if (set != true) {
                        System.out.println(sysScenicSpotAndCap.getScenicSpotId());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }


}
