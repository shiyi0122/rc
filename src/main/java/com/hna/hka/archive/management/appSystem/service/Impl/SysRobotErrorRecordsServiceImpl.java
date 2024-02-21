package com.hna.hka.archive.management.appSystem.service.Impl;

import com.alibaba.druid.sql.visitor.functions.If;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import com.hna.hka.archive.management.appSystem.dao.SysRobotAccessoriesApplicationDetailMapper;
import com.hna.hka.archive.management.appSystem.dao.SysRobotErrorRecordsApprovalLogMapper;
import com.hna.hka.archive.management.appSystem.dao.SysRobotErrorRecordsMapper;
import com.hna.hka.archive.management.appSystem.model.SysRobotAccessoriesApplicationDetail;
import com.hna.hka.archive.management.appSystem.model.SysRobotErrorRecordDetaIl;
import com.hna.hka.archive.management.appSystem.model.SysRobotErrorRecordsApprovalLog;
import com.hna.hka.archive.management.appSystem.service.SysRobotErrorRecordsService;
import com.hna.hka.archive.management.appYXBSystem.dao.SysGuideAppUsersMapper;
import com.hna.hka.archive.management.appYXBSystem.model.SysGuideAppUsers;
import com.hna.hka.archive.management.assetsSystem.dao.*;
import com.hna.hka.archive.management.assetsSystem.model.*;
import com.hna.hka.archive.management.assetsSystem.service.InventoryDetailService;
import com.hna.hka.archive.management.assetsSystem.service.SysOrderExceptionManagementService;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotAccessoriesApplicationService;
import com.hna.hka.archive.management.managerApp.dao.SysAppUsersMapper;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.system.dao.CommonMapper;
import com.hna.hka.archive.management.system.dao.SysRobotMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotMapper;
import com.hna.hka.archive.management.system.model.SysRobot;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.service.CommonService;


import com.hna.hka.archive.management.system.util.*;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.appSystem.service.Impl
 * @ClassName: SysRobotErrorRecordsServiceImpl
 * @Author: 郭凯
 * @Description: 配件损坏业务层（实现）
 * @Date: 2021/6/24 16:12
 * @Version: 1.0
 */
@Service
@Transactional
public class SysRobotErrorRecordsServiceImpl implements SysRobotErrorRecordsService {

    @Autowired
    private SysRobotErrorRecordsMapper sysRobotErrorRecordsMapper;

    @Autowired
    private SysRobotServiceRecordsMapper sysRobotServiceRecordsMapper;

    @Autowired
    private SysRobotErrorRepairUserMapper sysRobotErrorRepairUserMapper;
    @Autowired
    private SysRobotPartsManagementMapper sysRobotPartsManagementMapper;
    @Autowired
    private SysRobotErrorPartsMapper sysRobotErrorPartsMapper;
    @Autowired
    private SysOrderExceptionManagementMapper sysOrderExceptionManagementMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private SysScenicSpotMapper sysScenicSpotMapper;
    @Autowired
    private GoodsStockMapper goodsStockMapper;
    @Autowired
    SysAppUsersMapper sysAppUsersMapper;

    @Autowired
    InventoryDetailMapper inventoryDetailMapper;

    @Autowired
    SysRobotAccessoriesApplicationDetailMapper sysRobotAccessoriesApplicationDetailMapper;

    @Autowired
    SysAppFlowPathDetailsMapper sysAppFlowPathDetailsMapper;

    @Autowired
    SysRobotErrorRecordsApprovalLogMapper sysRobotErrorRecordsApprovalLogMapper;

    @Autowired
    CommonService service;

    @Autowired
    SysRobotAccessoriesApplicationService sysRobotAccessoriesApplicationService;


    @Autowired
    private SysRobotMapper sysRobotMapper;

    @Value("${PHOTOS_OF_ACCESSORIES_PAHT}")
    private String photosOfAccessoriesPaht;
    @Value("${PHOTOS_OF_ACCESSORIES_URL}")
    private String photosOfAccessoriesUrl;

    /**
     * @Method addRobotErrorRecord
     * @Author 郭凯
     * @Version 1.0
     * @Description 机器人上报损坏
     * @Return int
     * @Date 2021/6/24 16:18
     */
    @Override
    public int addRobotErrorRecord(SysRobotErrorRecords sysRobotErrorRecords) {
        SysRobot robot = sysRobotMapper.getRobotCodeBy(sysRobotErrorRecords.getRobotCode());
        if (ToolUtil.isEmpty(robot)) {
            return 2;
        }
        sysRobotErrorRecords.setErrorRecordsId(IdUtils.getSeqId());
        sysRobotErrorRecords.setErrorRecordsNo(IdUtils.getSeqId().toString());
        sysRobotErrorRecords.setErrorRecordsDate(DateUtil.currentDateTime());
        sysRobotErrorRecords.setCreateTime(DateUtil.currentDateTime());

        return sysRobotErrorRecordsMapper.insertSelective(sysRobotErrorRecords);
    }

    /**
     * @Method getRobotErrorRecordList
     * @Author 郭凯
     * @Version 1.0
     * @Description APP查询上报配件损坏列表
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.SysRobotErrorRecords>
     * @Date 2021/6/24 17:18
     */
    @Override
    public PageInfo<SysRobotErrorRecords> getRobotErrorRecordList(BaseQueryVo BaseQueryVo) {
        PageHelper.startPage(BaseQueryVo.getPageNum(), BaseQueryVo.getPageSize());
        List<SysRobotErrorRecords> robotErrorRecordsList = sysRobotErrorRecordsMapper.getRobotErrorRecordList(BaseQueryVo.getSearch());
        for (SysRobotErrorRecords sysRobotErrorRecords : robotErrorRecordsList) {
            List<SysRobotErrorParts> list = sysRobotErrorPartsMapper.selectErrorPartsByRecordsId(sysRobotErrorRecords.getErrorRecordsId());
            sysRobotErrorRecords.setDetails(list);
        }
        PageInfo<SysRobotErrorRecords> page = new PageInfo<>(robotErrorRecordsList);
        return page;
    }

    /**
     * @Method robotErrorRecordApproval
     * @Author 郭凯
     * @Version 1.0
     * @Description 机器人配件申请审批
     * @Return int
     * @Date 2021/6/24 17:41
     */
    @Override
    public int robotErrorRecordApproval(SysRobotErrorRecords sysRobotErrorRecords) {
        if (ToolUtil.isEmpty(sysRobotErrorRecords.getErrorRecordsId())) {
            return 2;
        }


        sysRobotErrorRecords.setUpdateTime(DateUtil.currentDateTime());
        return sysRobotErrorRecordsMapper.updateByPrimaryKeySelective(sysRobotErrorRecords);
    }

    /**
     * @Method robotErrorRecordRepair
     * @Author 郭凯
     * @Version 1.0
     * @Description 机器人配件维修信息
     * @Return int
     * @Date 2021/6/24 18:50
     */
    @Override
    public int robotErrorRecordRepair(SysRobotRepair sysRobotRepair) {

        if ("2".equals(sysRobotRepair.getServiceRecordsResult()) || "5".equals(sysRobotRepair.getServiceRecordsResult())) {
            SysRobot robotCodeBy = sysRobotMapper.getRobotCodeBy(sysRobotRepair.getRobotCode());
            robotCodeBy.setRobotFaultState("10");
            robotCodeBy.setRobotRunState("10");
            sysRobotMapper.updateByPrimaryKeySelective(robotCodeBy);
        }

        SysRobotErrorRecords sysRobotErrorRecords1 = sysRobotErrorRecordsMapper.selectByPrimaryKey(sysRobotRepair.getErrorRecordsId());

        //修改机器人故障记录
        SysRobotErrorRecords sysRobotErrorRecords = new SysRobotErrorRecords();
        sysRobotErrorRecords.setErrorRecordsId(sysRobotRepair.getErrorRecordsId());
        sysRobotErrorRecords.setErrorRecordsStatus(sysRobotRepair.getServiceRecordsResult());//维修结果
        sysRobotErrorRecords.setErrorRecordsReceive(sysRobotRepair.getErrorRecordsReceive());//配件是否收到
        sysRobotErrorRecords.setErrorRecordsReplace(sysRobotRepair.getErrorRecordsReplace());//是否更换配件
        sysRobotErrorRecords.setErrorRecordsSource(sysRobotRepair.getErrorRecordsSource());//配件来源
        sysRobotErrorRecords.setErrorRecordsUpkeepCost(sysRobotRepair.getErrorRecordsUpkeepCost());//维修费用
        sysRobotErrorRecords.setImproperOperation(sysRobotRepair.getImproperOperation());
        sysRobotErrorRecords.setFaultStatus(sysRobotRepair.getFaultStatus());
        sysRobotErrorRecords.setErrorRecordsAffect("2");
        int i = sysRobotErrorRecordsMapper.updateByPrimaryKeySelective(sysRobotErrorRecords);

        //修改机器人维修记录
        SysRobotServiceRecords sysRobotServiceRecords = new SysRobotServiceRecords();
//        sysRobotServiceRecords.setServiceRecordsId(sysRobotRepair.getServiceRecordsId());

        sysRobotServiceRecords.setErrorRecordsModel(sysRobotErrorRecords1.getErrorRecordsNo());
        sysRobotServiceRecords.setScenicSpotId(sysRobotRepair.getScenicSpotId());//景区
        sysRobotServiceRecords.setServiceRecordsCode(sysRobotRepair.getRobotCode());//机器人编号
        sysRobotServiceRecords.setServiceRecordsModel(sysRobotErrorRecords1.getErrorRecordsModel());//机器人型号
        sysRobotServiceRecords.setErrorRecordsName(sysRobotErrorRecords1.getErrorRecordsName());//故障名称
        sysRobotServiceRecords.setServiceRecordsResult(sysRobotRepair.getServiceRecordsResult());//维修结果
        sysRobotServiceRecords.setServiceRecordsDetails(sysRobotRepair.getServiceRecordsDetails());//维修详情
        SysAppUsers sysAppUsers = sysAppUsersMapper.getSysAppUserByUserName(sysRobotRepair.getServiceRecordsPersonnel());//查询维修人员
        sysRobotServiceRecords.setServiceRecordsPersonnel(sysAppUsers.getUserId().toString());//维修人员id
        sysRobotServiceRecords.setServiceRecordsTel(sysRobotRepair.getServiceRecordsTel());//维修人员电话
        sysRobotServiceRecords.setServiceRecordsServiceDate(DateUtil.currentDateTime());
        sysRobotServiceRecords.setUpdateTime(DateUtil.currentDateTime());
        int a = sysRobotServiceRecordsMapper.updateByPrimaryKeySelectiveNO(sysRobotServiceRecords);

        List<InventoryDetail> inventoryDetailList = JSONObject.parseArray(sysRobotRepair.getInventoryDetailList(), InventoryDetail.class);
        // 出库记录
        for (int i1 = 0; i1 < inventoryDetailList.size(); i1++) {
            GoodsStock stock = stockMapper.getByGoods(inventoryDetailList.get(i1).getGoodsId(), inventoryDetailList.get(i1).getSpotId());
            if (stock != null && stock.getAmount() < inventoryDetailList.get(i1).getGoodsAmount()) {
                //发货地址
//                            Address addressF = addressMapper.selectByIds(inventoryDetailList.get(0).getSpotId());
                //收货地址
                Address addressS = addressMapper.selectByIds(inventoryDetailList.get(i1).getSpotId());

                for (InventoryDetail inventoryDetail : inventoryDetailList) {
                    inventoryDetail.setId(IdUtils.getSeqId());
//                                inventoryDetail.setSpotId(addressF.getSpotId());
                    inventoryDetail.setReceivingId(addressS.getSpotId());
                    inventoryDetail.setOrderTime(DateUtil.currentDateTime());
                    inventoryDetail.setCreateTime(DateUtil.currentDateTime());
                    inventoryDetail.setUpdateTime(DateUtil.currentDateTime());
                    inventoryDetail.setReceivingName(addressS.getAddress());
                    inventoryDetail.setType(2l);
                    //入库单号自生成
                    SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
                    String today = format.format(new Date());
                    String prefer = orderType.getName(1) + today;
                    String result = mapper.getOrderNumber(prefer);
                    String fina = "";
                    if (org.apache.commons.lang3.StringUtils.isEmpty(result)) {
                        fina = prefer + "-" + "0001";
                    } else {
                        Integer suffix = Integer.valueOf(result.substring(13));
                        fina = prefer + "-" + String.format("%04d", (suffix + 1));
                    }
                    mapper.insertOrderNumber(fina);
                    inventoryDetail.setOrderNumber(fina);
                    inventoryDetailMapper.insert(inventoryDetail);

                    //修改库存
                    if (stock.getAmount() == 0) {
                        stock.setAmount(0l);
                    } else {
                        stock.setAmount(stock.getAmount() - inventoryDetail.getGoodsAmount());
                    }
                    stockMapper.edit(stock);

                    List<SysRobotErrorRecordDetaIl> sysRobotErrorRecordDetaIls = sysRobotAccessoriesApplicationDetailMapper.selectByIds(sysRobotErrorRecords.getErrorRecordsId());

                    for (int j = 0; j < sysRobotErrorRecordDetaIls.size(); j++) {
                        //将商品库存详情id添加到关联表中
                        sysRobotErrorRecordDetaIls.get(j).setGoodsInventoryDetailId(inventoryDetail.getId());
                        inventoryDetailMapper.updateSys(sysRobotErrorRecordDetaIls.get(j));
                    }

                }
            } else {
                GoodsStock byGoods = new GoodsStock();
                byGoods.setId(IdUtils.getSeqId());
                byGoods.setSpotId(inventoryDetailList.get(i).getReceivingId());
                byGoods.setManagementId(inventoryDetailList.get(i).getGoodsId());
                byGoods.setAmount(0l);
                byGoods.setNotes(inventoryDetailList.get(i).getNotes());
                byGoods.setUpdateTime(DateUtil.currentDateTime());
                stockMapper.add(byGoods);
            }
        }

        if (i == 1 && a == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * @Method robotErrorRecordEvaluate
     * @Author 郭凯
     * @Version 1.0
     * @Description 评价星级
     * @Return int
     * @Date 2021/6/24 19:57
     */
    @Override
    public int robotErrorRecordEvaluate(SysRobotServiceRecords sysRobotServiceRecords) {
        SysRobotErrorRecords sysRobotErrorRecords = sysRobotErrorRecordsMapper.selectByPrimaryKey(sysRobotServiceRecords.getErrorRecordsId());
        SysRobotErrorRecords robotErrorRecords = new SysRobotErrorRecords();
        robotErrorRecords.setErrorRecordsId(sysRobotServiceRecords.getErrorRecordsId());
        robotErrorRecords.setErrorRecordsStatus("6");
        robotErrorRecords.setFaultStatus(sysRobotServiceRecords.getFaultStatus());
        robotErrorRecords.setUpdateTime(DateUtil.currentDateTime());
        sysRobotErrorRecordsMapper.updateByPrimaryKeySelective(robotErrorRecords);
        sysRobotServiceRecords.setUpdateTime(DateUtil.currentDateTime());
        SysRobotServiceRecords sysRobotServiceRecords1 = sysRobotServiceRecordsMapper.selectByErrorRecordsModel(sysRobotErrorRecords.getErrorRecordsNo());
        sysRobotServiceRecords1.setServiceRecordsLevel(sysRobotServiceRecords.getServiceRecordsLevel());

        sysRobotServiceRecords1.setUpdateTime(DateUtil.currentDateTime());
        return sysRobotServiceRecordsMapper.updateByPrimaryKeySelective(sysRobotServiceRecords1);
    }

    /**
     * @Method getRobotErrorRecordsList
     * @Author 郭凯
     * @Version 1.0
     * @Description 故障记录列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/6/25 11:12
     */
    @Override
    public PageDataResult getRobotErrorRecordsList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotErrorRecords> robotErrorRecordsList = sysRobotErrorRecordsMapper.getRobotErrorRecordsList(search);
        if (robotErrorRecordsList.size() > 0) {
            PageInfo<SysRobotErrorRecords> pageInfo = new PageInfo<>(robotErrorRecordsList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method getRobotErrorRecordsList
     * @Author 郭凯
     * @Version 1.0
     * @Description 故障记录列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/6/25 11:12
     */
    @Override
    public PageDataResult getRobotErrorRecordsListNew(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotErrorRecords> robotErrorRecordsList = sysRobotErrorRecordsMapper.getRobotErrorRecordsList(search);

        if (robotErrorRecordsList.size() > 0) {
            PageInfo<SysRobotErrorRecords> pageInfo = new PageInfo<>(robotErrorRecordsList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }


    /**
     * @Method editRobotErrorRecords
     * @Author 郭凯
     * @Version 1.0
     * @Description 机器人故障派单
     * @Return int
     * @Date 2021/6/25 15:46
     */
    @Override
    public int editRobotErrorRecords(SysRobotErrorRepairUser sysRobotErrorRepairUser) {
        Long id = IdUtils.getSeqId();

        SysRobotErrorRecords sysRobotErrorRecords1 = sysRobotErrorRecordsMapper.selectByPrimaryKey(sysRobotErrorRepairUser.getErrorId());

        SysRobotErrorRepairUser robotErrorRepairUser = sysRobotErrorRepairUserMapper.getRobotErrorRepairUser(sysRobotErrorRepairUser);
        if (ToolUtil.isNotEmpty(robotErrorRepairUser)) {
            SysRobotErrorRecords sysRobotErrorRecords = new SysRobotErrorRecords();
            sysRobotErrorRecords.setErrorRecordsId(sysRobotErrorRepairUser.getErrorId());
            sysRobotErrorRecords.setErrorRecordsStatus("1");
            sysRobotErrorRecords.setFaultStatus(sysRobotErrorRepairUser.getFaultStatus());
            sysRobotErrorRecordsMapper.updateByPrimaryKeySelective(sysRobotErrorRecords);
            sysRobotErrorRepairUser.setErrorRepairUserId(robotErrorRepairUser.getErrorRepairUserId());
            sysRobotErrorRepairUser.setUpdateDate(DateUtil.currentDateTime());
            int i = sysRobotErrorRepairUserMapper.updateByPrimaryKeySelective(sysRobotErrorRepairUser);
            SysRobotServiceRecords robotServiceRecords = new SysRobotServiceRecords();
            robotServiceRecords.setServiceRecordsId(robotErrorRepairUser.getRepairId());
            robotServiceRecords.setServiceRecordsSendDate(DateUtil.currentDateTime());
            robotServiceRecords.setUpdateTime(DateUtil.currentDateTime());
            int a = sysRobotServiceRecordsMapper.updateByPrimaryKeySelective(robotServiceRecords);

            if (i == 1 && a == 1) {
                return 1;
            } else {
                return 0;
            }
        } else {

            SysRobotErrorRecords sysRobotErrorRecords = new SysRobotErrorRecords();
            sysRobotErrorRecords.setErrorRecordsId(sysRobotErrorRepairUser.getErrorId());
            sysRobotErrorRecords.setErrorRecordsStatus("1");
            sysRobotErrorRecords.setFaultStatus(sysRobotErrorRepairUser.getFaultStatus());
            int j = sysRobotErrorRecordsMapper.updateByPrimaryKeySelective(sysRobotErrorRecords);
            sysRobotErrorRepairUser.setErrorRepairUserId(IdUtils.getSeqId());
            sysRobotErrorRepairUser.setRepairId(id);
            sysRobotErrorRepairUser.setCreateDate(DateUtil.currentDateTime());
            sysRobotErrorRepairUser.setUpdateDate(DateUtil.currentDateTime());
            int i = sysRobotErrorRepairUserMapper.insertSelective(sysRobotErrorRepairUser);
            SysRobotServiceRecords robotServiceRecords = new SysRobotServiceRecords();
            robotServiceRecords.setServiceRecordsId(id);
            robotServiceRecords.setScenicSpotId(sysRobotErrorRecords1.getScenicSpotId());
            robotServiceRecords.setServiceRecordsSendDate(DateUtil.currentDateTime());
            robotServiceRecords.setServiceRecordsCode(sysRobotErrorRepairUser.getRobotCode());
            robotServiceRecords.setErrorRecordsModel(sysRobotErrorRecords1.getErrorRecordsNo());
            robotServiceRecords.setErrorRecordsName(sysRobotErrorRecords1.getErrorRecordsName());
            robotServiceRecords.setServiceRecordsPersonnel(sysRobotErrorRepairUser.getUserId().toString());
            robotServiceRecords.setCreateTime(DateUtil.currentDateTime());
            robotServiceRecords.setUpdateTime(DateUtil.currentDateTime());
            int a = sysRobotServiceRecordsMapper.insertSelective(robotServiceRecords);

            if (i == 1 && a == 1) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * @Method editErrorRecords
     * @Author 郭凯
     * @Version 1.0
     * @Description 机器人故障信息编辑
     * @Return int
     * @Date 2021/7/20 11:22
     */
    @Override
    public int editErrorRecords(SysRobotErrorRecords sysRobotErrorRecords) {
        sysRobotErrorRecords.setUpdateTime(DateUtil.currentDateTime());

        SysRobotErrorRecords sysRobotErrorRecordsN = sysRobotErrorRecordsMapper.selectByPrimaryKey(sysRobotErrorRecords.getErrorRecordsId());

        if ("1".equals(sysRobotErrorRecords.getFaultStatus())) {
            sysRobotErrorRecords.setErrorRecordsApprove("2");
            sysRobotErrorRecords.setErrorRecordsStatus("6");
            SysRobotServiceRecords sysRobotServiceRecords = sysRobotServiceRecordsMapper.selectByErrorRecordsModel(sysRobotErrorRecordsN.getErrorRecordsNo());
            if (!StringUtils.isEmpty(sysRobotServiceRecords)) {
                sysRobotServiceRecords.setServiceRecordsResult("6");
                sysRobotServiceRecords.setServiceRecordsServiceDate(DateUtil.currentDateTime());
                sysRobotServiceRecordsMapper.updateByPrimaryKeySelective(sysRobotServiceRecords);
            }
        }
        return sysRobotErrorRecordsMapper.updateByPrimaryKeySelective(sysRobotErrorRecords);
    }

    /**
     * @Method accessoriesDetails
     * @Author 张
     * @Version 1.0
     * @Description 机器人故障记录配件详情
     * @Return
     * @Date
     */
    @Override
    public List<SysRobotErrorParts> accessoriesDetails(Long errorRecordsId) {
        List<SysRobotPartsManagement> sysRobotPartsManagements = new ArrayList<>();
        List<SysRobotErrorParts> sysRobotErrorParts = sysRobotErrorPartsMapper.selectErrorPartsByRecordsId(errorRecordsId);
//        List<SysRobotErrorParts> arrayList = new ArrayList<SysRobotErrorParts>(sysRobotErrorParts.length);
//        for (SysRobotErrorParts robotErrorParts : arrayList) {
//           sysRobotPartsManagements.add(sysRobotPartsManagementMapper.selectByPrimaryKey(robotErrorParts.getPartsManagementId()));
//        }
//          Collections.addAll(arrayList, sysRobotErrorParts);
        return sysRobotErrorParts;

    }

    /**
     * @Method accessoriesDetails
     * @Author 张
     * @Version 1.0
     * @Description 管理者app机器人故障详情
     * @Return
     * @Date
     */
    @Override
    public SysRobotErrorRecords robotErrorRecordDetails(String errorRecordsId) {
        SysRobotErrorRecords sysRobotErrorRecords = sysRobotErrorRecordsMapper.selectByPrimaryKey(Long.parseLong(errorRecordsId));

//        List<SysAppFlowPathDetails> flowPathDetailsList = sysAppFlowPathDetailsMapper.getErrorRecordApprovalResults(errorRecordsId,sysRobotErrorRecords.getFlowPathId().toString(),"1");
        if (!StringUtils.isEmpty(sysRobotErrorRecords.getFlowPathId())) {

            List<SysAppFlowPathDetails> sysAppFlowPathDetails = sysAppFlowPathDetailsMapper.selectFlowPathIdByList(sysRobotErrorRecords.getFlowPathId(), "", "1");
            if (!StringUtils.isEmpty(sysAppFlowPathDetails)) {
                for (SysAppFlowPathDetails sysAppFlowPathDetail : sysAppFlowPathDetails) {
                    SysRobotErrorRecordsApprovalLog sysRobotErrorRecordsApprovalLog = sysRobotErrorRecordsApprovalLogMapper.getErrorRecordsIdAndUserIdByOne(sysAppFlowPathDetail.getSysAppUserId(), errorRecordsId);
                    if (!StringUtils.isEmpty(sysRobotErrorRecordsApprovalLog)) {
                        sysAppFlowPathDetail.setApprovalResults(sysRobotErrorRecordsApprovalLog.getApprovalResults());
                        sysAppFlowPathDetail.setReason(sysRobotErrorRecordsApprovalLog.getReason());
                    }
                    List<SysAppFlowPathDetails> sysAppFlowPathDetails1 = sysAppFlowPathDetailsMapper.selectFlowPathIdByList(sysAppFlowPathDetail.getFlowPathId(), sysAppFlowPathDetail.getSort(), "2");

                    sysAppFlowPathDetail.setAppFlowPathDetailsList(sysAppFlowPathDetails1);
                }
                sysRobotErrorRecords.setAppFlowPathDetailsList(sysAppFlowPathDetails);
            }
        } else {
            sysRobotErrorRecords.setAppFlowPathDetailsList(new ArrayList<>());
        }

        SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(sysRobotErrorRecords.getScenicSpotId());
        List<SysRobotAccessoriesApplicationDetail> sysRobotAccessoriesApplicationDetails = sysRobotAccessoriesApplicationDetailMapper.selectByAccessoriesApplicationId(Long.parseLong(errorRecordsId));
        SysRobotServiceRecords sysRobotServiceRecords = sysRobotServiceRecordsMapper.selectByErrorRecordsModel(sysRobotErrorRecords.getErrorRecordsNo());
        if (!StringUtils.isEmpty(sysRobotServiceRecords)) {
            //查询维修人员
            SysAppUsers sysAppUsers = sysAppUsersMapper.selectByPrimaryKey(Long.parseLong(sysRobotServiceRecords.getServiceRecordsPersonnel()));
            sysRobotErrorRecords.setServiceRecordsPersonnel(sysAppUsers.getUserName());//维修人员
            sysRobotErrorRecords.setServiceRecordsDetails(sysRobotServiceRecords.getServiceRecordsDetails());//详情
            sysRobotErrorRecords.setServiceRecordsLevel(sysRobotServiceRecords.getServiceRecordsLevel());//星级
        } else {
            sysRobotErrorRecords.setServiceRecordsPersonnel("");//维修人员
            sysRobotErrorRecords.setServiceRecordsDetails("");//详情
            sysRobotErrorRecords.setServiceRecordsLevel("");//星级
        }
        sysRobotErrorRecords.setScenicSpotName(sysScenicSpot.getScenicSpotName());
        sysRobotErrorRecords.setDetailList(sysRobotAccessoriesApplicationDetails);
        return sysRobotErrorRecords;
    }

    /**
     * @Method addRobotErrorRecords
     * @Author 张
     * @Version 1.0
     * @Description 添加故障信息
     * @Return
     * @Date
     */
    @Override
    public int addRobotErrorRecords(SysRobotErrorRecords sysRobotErrorRecords) {

        sysRobotErrorRecords.setErrorRecordsId(IdUtils.getSeqId());
        sysRobotErrorRecords.setCreateTime(DateUtil.currentDateTime());

        int i = sysRobotErrorRecordsMapper.insertSelective(sysRobotErrorRecords);

        return i;

    }

    /**
     * 后台添加故障记录
     *
     * @param file
     * @param sysRobotErrorRecords
     * @return
     */
    @Override
    public int addRobotErrorRecordFile(MultipartFile file, SysRobotErrorRecords sysRobotErrorRecords) {

        SysRobot robot = sysRobotMapper.getRobotCodeBy(sysRobotErrorRecords.getRobotCode());
        if (ToolUtil.isEmpty(robot)) {
            return 2;
        }

        if (!file.isEmpty()) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".png") || type.equals(".jpg")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = photosOfAccessoriesPaht + filename;// 存放位置
                File destFile = new File(path);
                try {

                    // 限制大小
                    long size = file.getSize() / 1024;//kb
                    if (size >= 2048) {
                        return 3;
                    }

                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

                    //压缩上传
//                    CompressUtils.compress(file.getInputStream(),destFile,10000);

                    //阿里OSS文件存储_上传图片
//                    String upload = fileUploadUtil.upload(file, GET_BROADCASTHUNT_PIC_PAHT.substring(1) + filename);
//                    System.out.println(upload);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                sysRobotErrorRecords.setErrorRecordsPic(photosOfAccessoriesUrl + filename);
            }
        }

        SysAppUsers sysAppUsers = sysAppUsersMapper.selectByPrimaryKey(Long.parseLong(sysRobotErrorRecords.getUserId()));

        sysRobotErrorRecords.setErrorRecordsPersonnel(sysAppUsers.getUserName());
        sysRobotErrorRecords.setErrorRecordsId(IdUtils.getSeqId());
        sysRobotErrorRecords.setErrorRecordsNo(IdUtils.getSeqId().toString());
        sysRobotErrorRecords.setErrorRecordsDate(DateUtil.currentDateTime());
        sysRobotErrorRecords.setCreateTime(DateUtil.currentDateTime());

        return sysRobotErrorRecordsMapper.insertSelective(sysRobotErrorRecords);
    }

    @Override
    public PageDataResult getFailureRecord(Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();

        List<SysRobotErrorRecords> sysRobotErrorRecords = sysRobotErrorRecordsMapper.getFailureRecord(search);
        if (sysRobotErrorRecords.size() != 0) {
            PageInfo<SysRobotErrorRecords> pageInfo = new PageInfo<>(sysRobotErrorRecords);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    @Override
    public PageDataResult getAppRobotErrorRecords(Map<String, Object> search) {

        PageHelper.startPage(Integer.parseInt(search.get("pageNum").toString()), Integer.parseInt(search.get("pageSize").toString()));
        List<SysRobotErrorRecords> list = sysRobotErrorRecordsMapper.getAppRobotErrorRecords(search);

        for (SysRobotErrorRecords sysRobotErrorRecords : list) {
            if (sysRobotErrorRecords.getErrorRecordsApprove() == null && ("1").equals(sysRobotErrorRecords.getFlowPathType())) {
                List<SysAppFlowPathDetails> sysAppFlowPathDetails1 = sysAppFlowPathDetailsMapper.selectFlowPathIdByList(sysRobotErrorRecords.getFlowPathId(), "1", "1");
                if (sysAppFlowPathDetails1.size() != 0 && sysAppFlowPathDetails1.get(0).getSysAppUserId() != null) {
                    String sysAppUserId = sysAppFlowPathDetails1.get(0).getSysAppUserId();
                    sysRobotErrorRecords.setCurrentFlowPathId(Long.valueOf(sysAppUserId));
                }

            } else {
                String flowPathType = sysRobotErrorRecords.getFlowPathType();
                List<SysAppFlowPathDetails> sysAppFlowPathDetails = sysAppFlowPathDetailsMapper.selectFlowPathIdByList(sysRobotErrorRecords.getFlowPathId(), flowPathType, "1");

                if (!StringUtils.isEmpty(sysAppFlowPathDetails) && sysAppFlowPathDetails.size() > 0) {
                    sysRobotErrorRecords.setCurrentFlowPathId(Long.parseLong(sysAppFlowPathDetails.get(0).getSysAppUserId()));
                }
            }
            List<SysRobotErrorParts> sysRobotErrorParts = sysRobotErrorPartsMapper.selectErrorPartsByRecordsId(sysRobotErrorRecords.getErrorRecordsId());
            sysRobotErrorRecords.setDetails(sysRobotErrorParts);

        }
        PageDataResult pageDataResult = new PageDataResult();
        PageInfo<SysRobotErrorRecords> pageInfo = new PageInfo<>(list);

        pageDataResult.setList(list);
        pageDataResult.setTotals((int) pageInfo.getTotal());
        pageDataResult.setCode(200);
        return pageDataResult;
    }

    @Autowired
    private GoodsStockMapper stockMapper;

    @Autowired
    CommonMapper mapper;


    public void addErrorRecords(SysRobotErrorRecords sysRobotErrorRecords) {
        sysRobotErrorRecords.setErrorRecordsId(IdUtils.getSeqId());
        sysRobotErrorRecords.setErrorRecordsNo(IdUtils.getSeqId().toString());
        sysRobotErrorRecords.setErrorRecordsDate(DateUtil.currentDateTime());
        sysRobotErrorRecords.setCreateTime(DateUtil.currentDateTime());
        sysRobotErrorRecordsMapper.insertSelective(sysRobotErrorRecords);
    }

    @Override
    public int addAppRobotErrorRecords(SysRobotErrorRecords sysRobotErrorRecords) {

        //是否有这台机器人
        SysRobot robot = sysRobotMapper.getRobotCodeBy(sysRobotErrorRecords.getRobotCode());
        if (ToolUtil.isEmpty(robot)) {
            return 2;
        }

        if (StringUtils.isEmpty(sysRobotErrorRecords.getErrorRecordsId())) {

            SysRobot robotCodeBy = sysRobotMapper.getRobotCodeBy(sysRobotErrorRecords.getRobotCode());
            //判断是否影响使用
            if ("1".equals(sysRobotErrorRecords.getErrorRecordsAffect())) {
                robotCodeBy.setRobotFaultState("20");
                robotCodeBy.setRobotRunState("60");
                sysRobotMapper.updateByPrimaryKeySelective(robotCodeBy);
            } else {
                robotCodeBy.setRobotRunState("90");
                sysRobotMapper.updateByPrimaryKeySelective(robotCodeBy);
            }

            //是否需要发配件
            if ("1".equals(sysRobotErrorRecords.getErrorRecordsPart())) {
                if ("2".equals(sysRobotErrorRecords.getErrorRecordsSource())) {
                    addErrorRecords(sysRobotErrorRecords);
                    return 1;
                }

                sysRobotErrorRecords.setErrorRecordsId(IdUtils.getSeqId());
                sysRobotErrorRecords.setErrorRecordsNo(IdUtils.getSeqId().toString());
                sysRobotErrorRecords.setErrorRecordsDate(DateUtil.currentDateTime());
                sysRobotErrorRecords.setCreateTime(DateUtil.currentDateTime());
                sysRobotErrorRecordsMapper.insertSelective(sysRobotErrorRecords);
                List<SysRobotAccessoriesApplicationDetail> sysRobotErrorPartsList = JSONObject.parseArray(sysRobotErrorRecords.getDetailsN(), SysRobotAccessoriesApplicationDetail.class);


//        List<SysRobotErrorParts>  details = sysRobotErrorRecords.getDetails();
                for (SysRobotAccessoriesApplicationDetail detail : sysRobotErrorPartsList) {
                    if (StringUtils.isEmpty(detail.getAccessoriesId())) {
                        break;
                    }
                    //添加配件申请记录
                    if ("1".equals(sysRobotErrorRecords.getErrorRecordsSource())) {
                        detail.setId(IdUtils.getSeqId());
                        detail.setAccessoriesApplicationId(sysRobotErrorRecords.getErrorRecordsId());
                        detail.setCreateDate(DateUtil.currentDateTime());
                        detail.setUpdateDate(DateUtil.currentDateTime());
                        detail.setType("2");
                        sysRobotAccessoriesApplicationDetailMapper.add(detail);
                    }
                    SysRobotErrorRecordDetaIl sysRobotErrorRecordDetaIl = new SysRobotErrorRecordDetaIl();
                    sysRobotErrorRecordDetaIl.setErrorRecordsId(sysRobotErrorRecords.getErrorRecordsId());
                    sysRobotAccessoriesApplicationDetailMapper.insertSys(sysRobotErrorRecordDetaIl);

//                    List<InventoryDetail> inventoryDetailList = JSONObject.parseArray(sysRobotErrorRecords.getInventoryDetailList(), InventoryDetail.class);
//                    //判断库存是否充足,添加入库记录
//                    for (int i = 0; i < sysRobotErrorPartsList.size(); i++) {
//                        Address addressS = addressMapper.selectByIds(inventoryDetailList.get(0).getSpotId());
//                        GoodsStock stock = stockMapper.getByGoods(detail.getAccessoriesId(), inventoryDetailList.get(i).getSpotId());
//                        if (stock != null) {
//                            if (stock.getAmount() < inventoryDetailList.get(i).getGoodsAmount()) {
//
//                                for (InventoryDetail inventoryDetail : inventoryDetailList) {
//                                    inventoryDetail.setId(IdUtils.getSeqId());
//                                    inventoryDetail.setReceivingId(addressS.getSpotId());
//                                    inventoryDetail.setOrderTime(DateUtil.currentDateTime());
//                                    inventoryDetail.setCreateTime(DateUtil.currentDateTime());
//                                    inventoryDetail.setUpdateTime(DateUtil.currentDateTime());
//                                    inventoryDetail.setType(1l);
//                                    //入库单号自生成
//                                    SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
//                                    String today = format.format(new Date());
//                                    String prefer = orderType.getName(1) + today;
//                                    String result = mapper.getOrderNumber(prefer);
//                                    String fina = "";
//                                    if (org.apache.commons.lang3.StringUtils.isEmpty(result)) {
//                                        fina = prefer + "-" + "0001";
//                                    } else {
//                                        Integer suffix = Integer.valueOf(result.substring(13));
//                                        fina = prefer + "-" + String.format("%04d", (suffix + 1));
//                                    }
//                                    mapper.insertOrderNumber(fina);
//                                    inventoryDetail.setOrderNumber(fina);
//                                    inventoryDetailMapper.insert(inventoryDetail);
//
//                                    List<SysRobotErrorRecordDetaIl> sysRobotErrorRecordDetaIls = sysRobotAccessoriesApplicationDetailMapper.selectByIds(sysRobotErrorRecords.getErrorRecordsId());
//
//                                    for (int j = 0; j < sysRobotErrorRecordDetaIls.size(); j++) {
//                                        //将商品库存详情id添加到关联表中
//                                        sysRobotErrorRecordDetaIls.get(j).setGoodsInventoryDetailId(inventoryDetail.getId());
//                                        inventoryDetailMapper.updateSys(sysRobotErrorRecordDetaIls.get(j));
//                                    }
//                                }
//                            }
//                        } else {
//                            GoodsStock byGoods = new GoodsStock();
//                            byGoods.setId(IdUtils.getSeqId());
//                            byGoods.setSpotId(inventoryDetailList.get(i).getReceivingId());
//                            byGoods.setManagementId(inventoryDetailList.get(i).getGoodsId());
//                            byGoods.setAmount(0l);
//                            byGoods.setNotes(inventoryDetailList.get(i).getNotes());
//                            byGoods.setUpdateTime(DateUtil.currentDateTime());
//                            stockMapper.add(byGoods);
//                        }
//                    }
                }

                return 1;

            } else {
                sysRobotErrorRecords.setErrorRecordsNo(IdUtils.getSeqId().toString());
                sysRobotErrorRecords.setErrorRecordsId(IdUtils.getSeqId());
                sysRobotErrorRecords.setCreateTime(DateUtil.currentDateTime());
                sysRobotErrorRecords.setUpdateTime(DateUtil.currentDateTime());
                sysRobotErrorRecords.setErrorRecordsDate(DateUtil.currentDateTime());
                return sysRobotErrorRecordsMapper.insertSelective(sysRobotErrorRecords);

            }
        } else {//修改


            int i = sysRobotAccessoriesApplicationDetailMapper.deleteByAccessoriesId(sysRobotErrorRecords.getErrorRecordsId());

            List<SysRobotAccessoriesApplicationDetail> sysRobotErrorPartsList = JSONObject.parseArray(sysRobotErrorRecords.getDetailsN(), SysRobotAccessoriesApplicationDetail.class);
            for (SysRobotAccessoriesApplicationDetail detail : sysRobotErrorPartsList) {
                detail.setId(IdUtils.getSeqId());
                detail.setAccessoriesApplicationId(sysRobotErrorRecords.getErrorRecordsId());
                detail.setCreateDate(DateUtil.currentDateTime());
                detail.setUpdateDate(DateUtil.currentDateTime());
                detail.setType("2");
                sysRobotAccessoriesApplicationDetailMapper.add(detail);
            }
            return sysRobotErrorRecordsMapper.updateByPrimaryKeySelective(sysRobotErrorRecords);
        }
    }

    /**
     * 故障类型下拉选
     *
     * @return
     */
    @Override
    public List<SysOrderExceptionManagement> getOrderExceptionManagement(String type) {

        List<SysOrderExceptionManagement> list = sysOrderExceptionManagementMapper.getCauseByList(type);
        return list;
    }

    /**
     * 根据用户信息获取收货地址
     *
     * @param userId
     * @return
     */
    @Override
    public List<Address> getUserIdByAddress(String userId) {

        List<Address> addressList = addressMapper.getUserIdByAddress(userId);

        return addressList;
    }

    /**
     * 根据配件id获取配件仓库
     *
     * @param errorRecordsId
     * @return
     */
    @Override
    public List<GoodsStock> getStorageRoomList(String errorRecordsId) {

        List<GoodsStock> list = new ArrayList<>();
        List<SysRobotErrorParts> sysRobotErrorPartsList = sysRobotErrorPartsMapper.selectErrorPartsByRecordsId(Long.parseLong(errorRecordsId));

        for (SysRobotErrorParts sysRobotErrorParts : sysRobotErrorPartsList) {

            GoodsStock goodsStock = goodsStockMapper.getPartIdAndNumberByStock(sysRobotErrorParts.getPartsManagementId(), sysRobotErrorParts.getQuantity());

            if (!StringUtils.isEmpty(goodsStock)) {
                if (list.size() > 0) {
                    int i = 0;
                    for (GoodsStock stock : list) {
                        if (stock.getSpotId() == goodsStock.getSpotId()) {
                            i = 1;
                            break;
                        } else {
                            continue;
                        }
                    }
                    if (i == 0) {
                        list.add(goodsStock);
                    }
                } else {
                    list.add(goodsStock);
                }
            }

        }

        return list;

    }

    @Override
    public int robotErrorRecordCourierNumber(SysRobotErrorRepairUser sysRobotErrorRepairUser) {

        return 0;
    }

    /**
     * 故障配件发货单签收
     *
     * @param id
     * @param signInPicture
     * @return
     */
    @Override
    public int editErrorAccessory(String id, String signInPicture) {

        SysRobotAccessoriesApplicationDetail sysRobotAccessoriesApplicationDetail = sysRobotAccessoriesApplicationDetailMapper.selectById(id);


        SysRobotErrorRecords sysRobotErrorRecords = sysRobotErrorRecordsMapper.selectByPrimaryKey(sysRobotAccessoriesApplicationDetail.getAccessoriesApplicationId());

        sysRobotAccessoriesApplicationDetail.setSignInPicture(signInPicture);

        sysRobotAccessoriesApplicationDetail.setAccessoriesReceivedType("1");

        sysRobotAccessoriesApplicationDetail.setUpdateDate(DateUtil.currentDateTime());

        SysRobotPartsManagement sysRobotPartsManagement = sysRobotPartsManagementMapper.selectByPrimaryKey(sysRobotAccessoriesApplicationDetail.getAccessoriesId());
        //添加入库单
        InventoryDetail detail = new InventoryDetail();
        detail.setId(IdUtils.getSeqId());
        detail.setOrderNumber(service.getOrderNumber(2));
        //查询发货地址
        Address byKey1 = addressMapper.getByKey(Long.parseLong(sysRobotAccessoriesApplicationDetail.getWarehouseId()), sysRobotAccessoriesApplicationDetail.getType());
        detail.setSpotId(byKey1.getSpotId());//库房发货单位
        detail.setSpotName(byKey1.getSpotName());// 发货单位名称
        detail.setPhone(byKey1.getPhone());//发货单位联系电话
        detail.setSpotAddressTypeId(byKey1.getId());
        detail.setDeliveryMan(byKey1.getName());//发货人
        detail.setNotes(sysRobotAccessoriesApplicationDetail.getShippingInstructions());//发货说明
        detail.setGoodsCode(sysRobotPartsManagement.getAccessoriesCode());//配件编码
        detail.setGoodsAmount(Long.parseLong(sysRobotAccessoriesApplicationDetail.getAccessoryNumber()));//数量

        detail.setUnitPirce(sysRobotPartsManagement.getAccessoryPriceOut());//单价
        detail.setInStockType("3");
        detail.setInStockReason("4");
        detail.setTotalAmount(sysRobotPartsManagement.getAccessoryPriceOut() * Long.parseLong(sysRobotAccessoriesApplicationDetail.getAccessoryNumber()));//合计金额
        detail.setGoodsName(sysRobotPartsManagement.getAccessoryName());//商品名称
        detail.setModel(sysRobotPartsManagement.getAccessoryModel());//商品编码
        detail.setUnit(sysRobotPartsManagement.getUnit());//单位
        detail.setGoodsId(sysRobotPartsManagement.getPartsManagementId());
//            detail.setSpotAddressTypeId(inventory.getSpotId());
//            detail.setReceivingId(addressS.getSpotId());
//            detail.setReceivingAddressTypeId(inventory.getReceivingId());
        //收货单位地址
        Address byKey = addressMapper.getByKey(sysRobotErrorRecords.getScenicSpotId(), sysRobotAccessoriesApplicationDetail.getType());
        detail.setReceivingName(byKey.getSpotName());//收货单位
        detail.setReceivingAddress(byKey.getAddress());//收货地址
        detail.setReceivingPhone(byKey.getPhone());//收货单位手机号
        detail.setOrderTime(DateUtil.currentDateTime());
        detail.setReceivingId(byKey.getSpotId());
        detail.setReceiver(byKey.getName());//收货人
        detail.setReceivingAddressTypeId(byKey.getId());
        detail.setType(1l);
        detail.setCreateTime(DateUtil.currentDateTime());
        detail.setUpdateTime(DateUtil.currentDateTime());

        GoodsStock byGoodsN = goodsStockMapper.getByGoods(sysRobotAccessoriesApplicationDetail.getAccessoriesId(), sysRobotErrorRecords.getScenicSpotId());
        if (!StringUtils.isEmpty(byGoodsN)) {
            byGoodsN.setAmount(new Double(byGoodsN.getAmount() + Long.parseLong(sysRobotAccessoriesApplicationDetail.getAccessoryNumber())).longValue());
            goodsStockMapper.edit(byGoodsN);
        } else {
            GoodsStock goodsStock = new GoodsStock();
            goodsStock.setId(IdUtils.getSeqId());
            goodsStock.setSpotId(sysRobotErrorRecords.getScenicSpotId());
            goodsStock.setManagementId(sysRobotAccessoriesApplicationDetail.getAccessoriesId());
            goodsStock.setAmount(Long.parseLong(sysRobotAccessoriesApplicationDetail.getAccessoryNumber()));
            goodsStock.setNotes("故障申请入库");
            goodsStock.setUpdateTime(DateUtil.currentDateTime());
            goodsStockMapper.add(goodsStock);
        }
        int g = inventoryDetailMapper.insert(detail);

        int i = sysRobotAccessoriesApplicationDetailMapper.updateByPrimaryKeySelective(sysRobotAccessoriesApplicationDetail);
        return i;

    }

    /**
     * 根据id获取故障详情
     *
     * @param errorId
     * @return
     */
    @Override
    public SysRobotErrorRecords selectById(Long errorId) {

        SysRobotErrorRecords sysRobotErrorRecords = sysRobotErrorRecordsMapper.selectByPrimaryKey(errorId);

        SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(sysRobotErrorRecords.getScenicSpotId());

        sysRobotErrorRecords.setScenicSpotName(sysScenicSpot.getScenicSpotName());

        return sysRobotErrorRecords;
    }

    /**
     * 删除故障信息
     *
     * @param errorRecordsId
     * @return
     */
    @Override
    public int delErrorRecords(Long errorRecordsId) {

        int i = sysRobotErrorRecordsMapper.deleteByPrimaryKey(errorRecordsId);

        return i;
    }

    /**
     * 管理者app故障单审批（新）
     *
     * @param sysRobotErrorRecordsApprovalLog
     * @return
     */
    @Override
    public int errorRecordsToExamine(SysRobotErrorRecordsApprovalLog sysRobotErrorRecordsApprovalLog) {

        Long robotErrorRecordsId = sysRobotErrorRecordsApprovalLog.getRobotErrorRecordsId();
        //获取故障详情
        SysRobotErrorRecords sysRobotErrorRecords = sysRobotErrorRecordsMapper.selectByPrimaryKey(robotErrorRecordsId);

        SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(sysRobotErrorRecords.getScenicSpotId());

        //上报人信息
        SysAppUsers sysAppUserByLoginName = sysAppUsersMapper.getSysAppUserByUserName(sysRobotErrorRecords.getErrorRecordsPersonnel());

        try {

            //审核通过
            if ("1".equals(sysRobotErrorRecordsApprovalLog.getApprovalResults()) || "2".equals(sysRobotErrorRecordsApprovalLog.getApprovalResults())) {

                //审批通过，结束此流程
                if ("2".equals(sysRobotErrorRecordsApprovalLog.getApprovalResults())) {
                    sysRobotErrorRecords.setFaultStatus("3");
                    sysRobotErrorRecords.setErrorRecordsApprove("2");
                }

                //流程id
                Long flowPathId = sysRobotErrorRecords.getFlowPathId();
                if (!StringUtils.isEmpty(flowPathId)) {

                    //审批流程总人数
                    Long number = sysAppFlowPathDetailsMapper.selectFlowPathIdByNumber(flowPathId, "1");
                    Long flowPathType = 0l;
                    //判断当前审核人后，是否还有审核人
                    if (number > Long.parseLong(sysRobotErrorRecords.getFlowPathType())) {
                        //下一个审核人位置

                        flowPathType = Long.parseLong(sysRobotErrorRecords.getFlowPathType()) + 1;

                    } else if (number == Long.parseLong(sysRobotErrorRecords.getFlowPathType())) {
                        //下一个审核人位置
                        flowPathType = Long.parseLong(sysRobotErrorRecords.getFlowPathType());
                    } else {
                        return 0;
                    }
                    //审批人
                    List<SysAppFlowPathDetails> spList = sysAppFlowPathDetailsMapper.selectFlowPathIdByList(flowPathId, flowPathType.toString(), "1");
                    //抄送人列表
                    List<SysAppFlowPathDetails> csList = sysAppFlowPathDetailsMapper.selectFlowPathIdByList(flowPathId, flowPathType.toString(), "2");

                    //审批人推送
                    for (SysAppFlowPathDetails sysAppFlowPathDetails : spList) {
                        SysAppUsers sysAppUsers = sysAppUsersMapper.selectByPrimaryKey(Long.parseLong(sysAppFlowPathDetails.getSysAppUserId()));
                        // 个推推送消息到APP端
//                    String isSuccess = WeChatGtRobotAppPush.singlePushApp(sysAppUsers.getUserClientGtId(), "审批通知", sysAppUsers.getScenicSpotName() + "," + "故障审批结果" + "," + DictUtils.getErrorRecordsApproveMap().get(sysRobotErrorRecords.getErrorRecordsApprove()));
                        String isSuccess = WeChatGtRobotAppPush.singlePushApp(sysAppUsers.getUserClientGtId(), "审批通知", sysScenicSpot.getScenicSpotName() + "," + "故障审批");

                    }
                    //抄送人推送
                    for (SysAppFlowPathDetails sysAppFlowPathDetails : csList) {
                        SysAppUsers sysAppUsers = sysAppUsersMapper.selectByPrimaryKey(Long.parseLong(sysAppFlowPathDetails.getSysAppUserId()));
                        // 个推推送消息到APP端
//                    String isSuccess = WeChatGtRobotAppPush.singlePushApp(sysAppUsers.getUserClientGtId(), "审批通知", sysAppUsers.getScenicSpotName() + "," + "故障审批结果" + "," + DictUtils.getErrorRecordsApproveMap().get(sysRobotErrorRecords.getErrorRecordsApprove()));
                        String isSuccess = WeChatGtRobotAppPush.singlePushApp(sysAppUsers.getUserClientGtId(), "审批通知", sysScenicSpot.getScenicSpotName() + "," + "故障审批");
                    }

                    sysRobotErrorRecordsApprovalLog.setId(IdUtils.getSeqId());
                    sysRobotErrorRecordsApprovalLog.setCreateTime(DateUtil.currentDateTime());
                    sysRobotErrorRecordsApprovalLog.setUpdateTime(DateUtil.currentDateTime());
                    int i = sysRobotErrorRecordsApprovalLogMapper.insertSelective(sysRobotErrorRecordsApprovalLog);

                    if (number == Long.parseLong(sysRobotErrorRecords.getFlowPathType())) {
                        sysRobotErrorRecords.setErrorRecordsApprove("1");
                        sysRobotErrorRecords.setFaultStatus("3");
                        sysRobotErrorRecords.setFlowPathType(number.toString());
                    } else {
                        Long flowPathTypeN = Long.parseLong(sysRobotErrorRecords.getFlowPathType()) + 1;
                        sysRobotErrorRecords.setFlowPathType(flowPathTypeN.toString());
                    }
                    int i1 = sysRobotErrorRecordsMapper.updateByPrimaryKeySelective(sysRobotErrorRecords);

                    //提交人推送
                    String isSuccess = WeChatGtRobotAppPush.singlePushApp(sysAppUserByLoginName.getUserClientGtId(), "审批通知", "您的审批进度更新了,请尽快查看.");

                    if (i > 0 && i1 > 0) {
                        return i;
                    } else {
                        return 0;
                    }
                } else {

                    sysRobotErrorRecordsApprovalLog.setId(IdUtils.getSeqId());
                    sysRobotErrorRecordsApprovalLog.setCreateTime(DateUtil.currentDateTime());
                    sysRobotErrorRecordsApprovalLog.setUpdateTime(DateUtil.currentDateTime());
                    int i = sysRobotErrorRecordsApprovalLogMapper.insertSelective(sysRobotErrorRecordsApprovalLog);

                    sysRobotErrorRecords.setErrorRecordsApprove("1");
                    sysRobotErrorRecords.setFaultStatus("3");
                    int i1 = sysRobotErrorRecordsMapper.updateByPrimaryKeySelective(sysRobotErrorRecords);
                    return i1;
                }
            } else {//审核失败

                sysRobotErrorRecordsApprovalLog.setId(IdUtils.getSeqId());
                sysRobotErrorRecordsApprovalLog.setCreateTime(DateUtil.currentDateTime());
                sysRobotErrorRecordsApprovalLog.setUpdateTime(DateUtil.currentDateTime());
                int i = sysRobotErrorRecordsApprovalLogMapper.insertSelective(sysRobotErrorRecordsApprovalLog);

                if ("2".equals(sysRobotErrorRecordsApprovalLog.getApprovalResults())) {
                    sysRobotErrorRecords.setErrorRecordsApprove("0");
                } else {
                    sysRobotErrorRecords.setErrorRecordsApprove(sysRobotErrorRecordsApprovalLog.getApprovalResults());
                }
                sysRobotErrorRecords.setFaultStatus("6");
                sysRobotErrorRecords.setErrorRecordsOpinion(sysRobotErrorRecordsApprovalLog.getReason());

                int j = sysRobotErrorRecordsMapper.updateByPrimaryKeySelective(sysRobotErrorRecords);

                //提交人推送
                String isSuccess = WeChatGtRobotAppPush.singlePushApp(sysAppUserByLoginName.getUserClientGtId(), "审批通知", "您的审批进度更新了,请尽快查看.");

                if (i > 0 && j > 0) {
                    return i;
                } else {
                    return 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    enum orderType {
        CH("SC-CH-"), SH("SC-SH-"), PJ("PJ-SQ-");

        private String name;

        orderType(String name) {
            this.name = name;
        }

        public static String getName(Integer type) {
            switch (type) {
                case 1:
                    return CH.name;
                case 2:
                    return SH.name;
                case 3:
                    return PJ.name;
                default:
                    return null;
            }
        }
    }


}
