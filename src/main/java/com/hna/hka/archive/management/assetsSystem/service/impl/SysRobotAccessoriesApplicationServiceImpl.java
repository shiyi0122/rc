package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import com.hna.hka.archive.management.appSystem.dao.SysRobotAccessoriesApplicationDetailMapper;
import com.hna.hka.archive.management.appSystem.dao.SysRobotErrorRecordsMapper;
import com.hna.hka.archive.management.appSystem.model.SysRobotAccessoriesApplicationDetail;
import com.hna.hka.archive.management.assetsSystem.dao.*;
import com.hna.hka.archive.management.assetsSystem.model.*;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotAccessoriesApplicationService;
import com.hna.hka.archive.management.system.dao.SysScenicSpotMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.service.CommonService;
import com.hna.hka.archive.management.system.util.*;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service.impl
 * @ClassName: SysRobotAccessoriesApplicationServiceImpl
 * @Author: 郭凯
 * @Description: 配件申请业务层（实现）
 * @Date: 2021/6/16 11:22
 * @Version: 1.0
 */
@Service
@Transactional
public class SysRobotAccessoriesApplicationServiceImpl implements SysRobotAccessoriesApplicationService {

    @Autowired
    private SysRobotAccessoriesApplicationMapper sysRobotAccessoriesApplicationMapper;

    @Autowired
    private SysRobotPartsManagementMapper sysRobotPartsManagementMapper;

    @Autowired
    private GoodsStockMapper goodsStockMapper;

    @Autowired
    InventoryDetailMapper inventoryDetailMapper;
    @Autowired
    SysRobotAccessoriesApplicationDetailMapper sysRobotAccessoriesApplicationDetailMapper;

    @Autowired
    SysScenicSpotMapper sysScenicSpotMapper;

    @Autowired
    SysRobotErrorRecordsMapper sysRobotErrorRecordsMapper;


    @Autowired
    AddressMapper addressMapper;

    @Autowired
    CommonService service;

    /**
     * @Method addRobotAccessoriesApplication
     * @Author 郭凯
     * @Version 1.0
     * @Description 上传配件申请信息
     * @Return int
     * @Date 2021/6/16 11:27
     */
    @Override
    public int addRobotAccessoriesApplication(SysRobotAccessoriesApplication sysRobotAccessoriesApplication) {

        if (StringUtils.isEmpty(sysRobotAccessoriesApplication.getAccessoriesApplicationId())) {
            sysRobotAccessoriesApplication.setAccessoriesApplicationId(IdUtils.getSeqId());
            sysRobotAccessoriesApplication.setCreateDate(DateUtil.currentDateTime());
            sysRobotAccessoriesApplication.setUpdateDate(DateUtil.currentDateTime());

            String detailListN = sysRobotAccessoriesApplication.getDetailListN();
            List<SysRobotAccessoriesApplicationDetail> detailList = JSONObject.parseArray(detailListN, SysRobotAccessoriesApplicationDetail.class);
//            List<SysRobotAccessoriesApplicationDetail> detailList = sysRobotAccessoriesApplication.getDetailList();

            for (SysRobotAccessoriesApplicationDetail sysRobotAccessoriesApplicationDetail : detailList) {
                sysRobotAccessoriesApplicationDetail.setId(IdUtils.getSeqId());
                sysRobotAccessoriesApplicationDetail.setAccessoriesApplicationId(sysRobotAccessoriesApplication.getAccessoriesApplicationId());
                sysRobotAccessoriesApplicationDetail.setCreateDate(DateUtil.currentDateTime());
                sysRobotAccessoriesApplicationDetail.setUpdateDate(DateUtil.currentDateTime());
                sysRobotAccessoriesApplicationDetail.setType("1");
                sysRobotAccessoriesApplicationDetailMapper.add(sysRobotAccessoriesApplicationDetail);
            }

            return sysRobotAccessoriesApplicationMapper.insertSelective(sysRobotAccessoriesApplication);
        } else {

            int i = sysRobotAccessoriesApplicationDetailMapper.deleteByAccessoriesId(sysRobotAccessoriesApplication.getAccessoriesApplicationId());

            String detailListN = sysRobotAccessoriesApplication.getDetailListN();
            List<SysRobotAccessoriesApplicationDetail> detailList = JSONObject.parseArray(detailListN, SysRobotAccessoriesApplicationDetail.class);

            for (SysRobotAccessoriesApplicationDetail sysRobotAccessoriesApplicationDetail : detailList) {

                sysRobotAccessoriesApplicationDetail.setId(IdUtils.getSeqId());
                sysRobotAccessoriesApplicationDetail.setAccessoriesApplicationId(sysRobotAccessoriesApplication.getAccessoriesApplicationId());
                sysRobotAccessoriesApplicationDetail.setCreateDate(DateUtil.currentDateTime());
                sysRobotAccessoriesApplicationDetail.setUpdateDate(DateUtil.currentDateTime());
                sysRobotAccessoriesApplicationDetail.setType("1");
                sysRobotAccessoriesApplicationDetailMapper.add(sysRobotAccessoriesApplicationDetail);

            }

//            sysRobotAccessoriesApplication.setProcessingProgress("1");
            sysRobotAccessoriesApplication.setApprovalProgress("0");
            int j = sysRobotAccessoriesApplicationMapper.updateByPrimaryKeySelective(sysRobotAccessoriesApplication);

            return j;
        }

    }

    /**
     * @Method getRobotAccessoriesApplicationList
     * @Author 郭凯
     * @Version 1.0
     * @Description APP查询配件申请列表
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.SysRobotAccessoriesApplication>
     * @Date 2021/6/16 14:26
     */
    @Override
    public PageInfo<SysRobotAccessoriesApplication> getRobotAccessoriesApplicationList(BaseQueryVo baseQueryVo) {
        PageHelper.startPage(baseQueryVo.getPageNum(), baseQueryVo.getPageSize());
        List<SysRobotAccessoriesApplication> robotAccessoriesApplicationList = sysRobotAccessoriesApplicationMapper.getRobotAccessoriesApplicationListNew(baseQueryVo.getSearch());
        for (SysRobotAccessoriesApplication robotAccessoriesApplication : robotAccessoriesApplicationList) {
            robotAccessoriesApplication.setProcessingProgress(DictUtils.getAccessoryProcessingProgressMap().get(robotAccessoriesApplication.getProcessingProgress()));
            robotAccessoriesApplication.setApprovalProgress(DictUtils.getApplicantProcessingProgressMap().get(robotAccessoriesApplication.getApprovalProgress()));

            List<SysRobotAccessoriesApplicationDetail> list = sysRobotAccessoriesApplicationDetailMapper.selectByAccessoriesApplicationId(robotAccessoriesApplication.getAccessoriesApplicationId());

            robotAccessoriesApplication.setDetailList(list);

        }
        PageInfo<SysRobotAccessoriesApplication> page = new PageInfo<>(robotAccessoriesApplicationList);
        return page;
    }

    /**
     * @Method editApproval
     * @Author 郭凯
     * @Version 1.0
     * @Description APP审批配件申请，
     * @Return int
     * @Date 2021/6/16 15:16
     */
    @Override
    public int editApproval(SysRobotAccessoriesApplication sysRobotAccessoriesApplication) {


        SysRobotAccessoriesApplication sysRobotAccessoriesApplication1 = sysRobotAccessoriesApplicationMapper.selectByPrimaryKey(sysRobotAccessoriesApplication.getAccessoriesApplicationId());
        if ("1".equals(sysRobotAccessoriesApplication.getAccessoriesReceivedType())) {
            SysRobotPartsManagement sysRobotPartsManagement = sysRobotPartsManagementMapper.selectByPrimaryKey(sysRobotAccessoriesApplication1.getAccessoriesId());

            //添加入库单
            InventoryDetail detail = new InventoryDetail();
            detail.setId(IdUtils.getSeqId());
            detail.setOrderNumber(service.getOrderNumber(2));
            //查询发货地址
            Address byKey1 = addressMapper.getByKey(sysRobotAccessoriesApplication1.getWarehouseId(), null);
            detail.setSpotId(byKey1.getSpotId());//库房发货单位
            detail.setSpotName(byKey1.getSpotName());// 发货单位名称
            detail.setPhone(byKey1.getPhone());//发货单位联系电话
            detail.setSpotAddressTypeId(byKey1.getId());
            detail.setDeliveryMan(byKey1.getName());//发货人
            detail.setNotes(sysRobotAccessoriesApplication.getShippingInstructions());//发货说明
            detail.setGoodsCode(sysRobotPartsManagement.getAccessoriesCode());//配件编码
            detail.setGoodsAmount(Long.parseLong(sysRobotAccessoriesApplication1.getAccessoryNumber()));//数量

            detail.setUnitPirce(sysRobotPartsManagement.getAccessoryPriceOut());//单价
            detail.setInStockType("3");
            detail.setInStockReason("4");
            detail.setTotalAmount(sysRobotPartsManagement.getAccessoryPriceOut() * Long.parseLong(sysRobotAccessoriesApplication1.getAccessoryNumber()));//合计金额
            detail.setGoodsName(sysRobotPartsManagement.getAccessoryName());//商品名称
            detail.setModel(sysRobotPartsManagement.getAccessoryModel());//商品编码
            detail.setUnit(sysRobotPartsManagement.getUnit());//单位
            detail.setGoodsId(sysRobotPartsManagement.getPartsManagementId());
//            detail.setSpotAddressTypeId(inventory.getSpotId());
//            detail.setReceivingId(addressS.getSpotId());
//            detail.setReceivingAddressTypeId(inventory.getReceivingId());
            //收货单位地址
            Address byKey = addressMapper.getByKey(sysRobotAccessoriesApplication1.getScenicSpotId(), null);
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

            GoodsStock byGoodsN = goodsStockMapper.getByGoods(sysRobotAccessoriesApplication1.getAccessoriesId(), sysRobotAccessoriesApplication1.getScenicSpotId());
            if (!StringUtils.isEmpty(byGoodsN)) {
                byGoodsN.setAmount(new Double(byGoodsN.getAmount() + Long.parseLong(sysRobotAccessoriesApplication1.getAccessoryNumber())).longValue());
                goodsStockMapper.edit(byGoodsN);
            } else {
                GoodsStock goodsStock = new GoodsStock();
                goodsStock.setId(IdUtils.getSeqId());
                goodsStock.setSpotId(sysRobotAccessoriesApplication1.getScenicSpotId());
                goodsStock.setManagementId(sysRobotAccessoriesApplication1.getAccessoriesId());
                goodsStock.setAmount(Long.parseLong(sysRobotAccessoriesApplication1.getAccessoryNumber()));
                goodsStock.setNotes("配件申请入库");
                goodsStock.setUpdateTime(DateUtil.currentDateTime());
                goodsStockMapper.add(goodsStock);
            }
            int g = inventoryDetailMapper.insert(detail);
        }

        return sysRobotAccessoriesApplicationMapper.updateByPrimaryKeySelective(sysRobotAccessoriesApplication);
    }

    /**
     * @Method getRobotAccessoriesApplicationVoList
     * @Author 郭凯
     * @Version 1.0
     * @Description 查询机器人配件申请列表
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/6/27 18:48
     */
    @Override
    public PageDataResult getRobotAccessoriesApplicationVoList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotAccessoriesApplication> robotAccessoriesApplicationVoList = sysRobotAccessoriesApplicationMapper.getRobotAccessoriesApplicationVoListNew(search);

        if (robotAccessoriesApplicationVoList.size() > 0) {
            PageInfo<SysRobotAccessoriesApplication> pageInfo = new PageInfo<>(robotAccessoriesApplicationVoList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method getRobotAccessoriesApplicationExcel
     * @Author 郭凯
     * @Version 1.0
     * @Description 下载Excel表
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.SysOrderExceptionManagement>
     * @Date 2021/7/8 19:21
     */
    @Override
    public List<SysRobotAccessoriesApplication> getRobotAccessoriesApplicationExcel(Map<String, Object> search) {
        List<SysRobotAccessoriesApplication> robotAccessoriesApplicationList = sysRobotAccessoriesApplicationMapper.getRobotAccessoriesApplicationVoList(search);
        for (SysRobotAccessoriesApplication robotAccessoriesApplication : robotAccessoriesApplicationList) {
            robotAccessoriesApplication.setProcessingProgressName(DictUtils.getAccessoryProcessingProgressMap().get(robotAccessoriesApplication.getProcessingProgress()));
            robotAccessoriesApplication.setAccessoriesReceivedTypeName(DictUtils.getAccessoriesReceivedTypeMap().get(robotAccessoriesApplication.getAccessoriesReceivedType()));
        }
        return robotAccessoriesApplicationList;
    }

    /**
     * @Method editRobotAccessoriesApplication
     * @Author 郭凯
     * @Version 1.0
     * @Description 编辑配件申请处理状态
     * @Return int
     * @Date 2021/7/9 16:31
     */
    @Override
    public int editRobotAccessoriesApplication(SysRobotAccessoriesApplication sysRobotAccessoriesApplication) {
        sysRobotAccessoriesApplication.setUpdateDate(DateUtil.currentDateTime());
        return sysRobotAccessoriesApplicationMapper.updateByPrimaryKeySelective(sysRobotAccessoriesApplication);
    }

    /**
     * @Method
     * @Author zhang
     * @Version 1.0
     * @Description 根据姓名查询配件
     * @Return
     * @Date
     */
    @Override
    public List<SysRobotPartsManagement> robotAccessoriesManagementApplication(String accessoryName, String accessoriesCode) {

        List<SysRobotPartsManagement> sysRobotPartsManagementList = sysRobotPartsManagementMapper.selectRobotByName(accessoryName, accessoriesCode);

        return sysRobotPartsManagementList;
    }


    /**
     * 机器人配件申请详情
     *
     * @param accessoriesApplicationId
     * @return
     */
    @Override
    public SysRobotAccessoriesApplication accessoriesDetails(String accessoriesApplicationId, String scenicSpotName) {
        SysRobotAccessoriesApplication sysRobotAccessoriesApplication = sysRobotAccessoriesApplicationMapper.selectByPrimaryKey(Long.parseLong(accessoriesApplicationId));
        //有发货单
        if (sysRobotAccessoriesApplication != null) {
            SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(sysRobotAccessoriesApplication.getScenicSpotId());
            sysRobotAccessoriesApplication.setScenicSpotName(sysScenicSpot.getScenicSpotName());
            List<SysRobotAccessoriesApplicationDetail> sysRobotAccessoriesApplicationDetails = sysRobotAccessoriesApplicationDetailMapper.selectByAccessoriesApplicationId(Long.valueOf(accessoriesApplicationId));
            sysRobotAccessoriesApplication.setDetailList(sysRobotAccessoriesApplicationDetails);
            return sysRobotAccessoriesApplication;
        } else {
            SysRobotAccessoriesApplication sysRobotAccessoriesApplication1 = new SysRobotAccessoriesApplication();
            sysRobotAccessoriesApplication1.setScenicSpotName(scenicSpotName);
            List<SysRobotAccessoriesApplicationDetail> sysRobotAccessoriesApplicationDetails = sysRobotAccessoriesApplicationDetailMapper.selectByAccessoriesApplicationId(Long.valueOf(accessoriesApplicationId));
            sysRobotAccessoriesApplication1.setDetailList(sysRobotAccessoriesApplicationDetails);
            return sysRobotAccessoriesApplication1;
        }
    }

    @Override
    public int update(Map<String, Object> dataMap) {
//        log.info(dataMap.toString());
        String accessoriesApplicationId = (String) dataMap.get("accessoriesApplicationId");
        String warehouseId = (String) dataMap.get("warehouseId");

        SysRobotAccessoriesApplication sysRobotAccessoriesApplication1 = sysRobotAccessoriesApplicationMapper.selectByPrimaryKey(Long.parseLong(accessoriesApplicationId));
        //判断是否审核同意
        if ("1".equals(sysRobotAccessoriesApplication1.getApprovalProgress())) {
            SysRobotPartsManagement sysRobotPartsManagement = sysRobotPartsManagementMapper.selectByPrimaryKey(sysRobotAccessoriesApplication1.getAccessoriesId());

            //添加出库库单
            InventoryDetail detail = new InventoryDetail();
            detail.setId(IdUtils.getSeqId());
            detail.setOrderNumber(service.getOrderNumber(2));
            //查询发货地址
            Address byKey1 = addressMapper.getByKey(Long.parseLong(warehouseId), null);
            detail.setSpotId(byKey1.getSpotId());//库房发货单位
            detail.setSpotName(byKey1.getSpotName());// 发货单位名称
            detail.setPhone(byKey1.getPhone());//发货单位联系电话
            detail.setSpotAddressTypeId(byKey1.getId());
            detail.setDeliveryMan(byKey1.getName());//发货人
            detail.setNotes(sysRobotAccessoriesApplication1.getShippingInstructions());//发货说明
            detail.setGoodsCode(sysRobotPartsManagement.getAccessoriesCode());//配件编码
            detail.setGoodsAmount(Long.parseLong(sysRobotAccessoriesApplication1.getAccessoryNumber()));//数量
            detail.setUnitPirce(sysRobotPartsManagement.getAccessoryPriceOut());//单价
            detail.setInStockType("3");
            detail.setInStockReason("4");
            detail.setTotalAmount(sysRobotPartsManagement.getAccessoryPriceOut() * Long.parseLong(sysRobotAccessoriesApplication1.getAccessoryNumber()));//合计金额
            detail.setGoodsName(sysRobotPartsManagement.getAccessoryName());//商品名称
            detail.setModel(sysRobotPartsManagement.getAccessoryModel());//商品编码
            detail.setUnit(sysRobotPartsManagement.getUnit());//单位
            detail.setGoodsId(sysRobotPartsManagement.getPartsManagementId());
//            detail.setSpotAddressTypeId(inventory.getSpotId());
//            detail.setReceivingId(addressS.getSpotId());
//            detail.setReceivingAddressTypeId(inventory.getReceivingId());
            //收货单位地址
            Address byKey = addressMapper.getByKey(sysRobotAccessoriesApplication1.getScenicSpotId(), null);
            detail.setReceivingName(byKey.getSpotName());//收货单位
            detail.setReceivingAddress(byKey.getAddress());//收货地址
            detail.setReceivingPhone(byKey.getPhone());//收货单位手机号
            detail.setOrderTime(DateUtil.currentDateTime());
            detail.setReceivingId(byKey.getSpotId());
            detail.setReceiver(byKey.getName());//收货人
            detail.setReceivingAddressTypeId(byKey.getId());
            detail.setType(2l);
            detail.setCreateTime(DateUtil.currentDateTime());
            detail.setUpdateTime(DateUtil.currentDateTime());

            GoodsStock byGoodsN = goodsStockMapper.getByGoods(sysRobotAccessoriesApplication1.getAccessoriesId(), Long.parseLong(warehouseId));
            if (!StringUtils.isEmpty(byGoodsN)) {
                byGoodsN.setAmount(new Double(byGoodsN.getAmount() - Long.parseLong(sysRobotAccessoriesApplication1.getAccessoryNumber())).longValue());
                goodsStockMapper.edit(byGoodsN);
            } else {
                GoodsStock goodsStock = new GoodsStock();
                goodsStock.setId(IdUtils.getSeqId());
                goodsStock.setSpotId(sysRobotAccessoriesApplication1.getScenicSpotId());
                goodsStock.setManagementId(sysRobotAccessoriesApplication1.getAccessoriesId());
                goodsStock.setAmount(Long.parseLong(sysRobotAccessoriesApplication1.getAccessoryNumber()));
                goodsStock.setNotes("配件申请出库");
                goodsStock.setUpdateTime(DateUtil.currentDateTime());
                goodsStockMapper.add(goodsStock);
            }

            int g = inventoryDetailMapper.insert(detail);
        }


        int i = sysRobotAccessoriesApplicationMapper.update(dataMap);
        return i;
    }

    @Override
    public List<Address> selectWarehouse(Map<String, Object> dataMap) {
        List<Address> addressList = sysRobotAccessoriesApplicationMapper.selectWarehouse(dataMap);
        return addressList;
    }

    /**
     * 根据配件id，获取有此配件的仓库列表
     *
     * @param
     * @return
     */
    @Override
    public List<GoodsStock> getGoodsStock(String accessoriesApplicationId) {

        SysRobotAccessoriesApplication sysRobotAccessoriesApplication = sysRobotAccessoriesApplicationMapper.selectByPrimaryKey(Long.parseLong(accessoriesApplicationId));

        List<GoodsStock> list = goodsStockMapper.getPartIdAndNumberByStockList(sysRobotAccessoriesApplication.getAccessoriesId(), Double.parseDouble(sysRobotAccessoriesApplication.getAccessoryNumber()));

        return list;

    }

    @Override
    public int updateExPressfee(Map<String, Object> dataMap) {

        int i = sysRobotAccessoriesApplicationMapper.update(dataMap);
        return i;
    }

    /**
     * 根据景区id获取地址
     *
     * @param spotId
     * @return
     */
    @Override
    public Address getSpotIdByAddress(String spotId, String type) {

        Address byKey = addressMapper.getByKey(Long.parseLong(spotId), type);
        return byKey;

    }

    /**
     * 根据配件id，获取配件详情中的配件列表
     *
     * @param accessoriesApplicationId
     * @return
     */
    @Override
    public List<SysRobotAccessoriesApplicationDetail> getApplicationIdByDetailList(String accessoriesApplicationId) {

        List<SysRobotAccessoriesApplicationDetail> sysRobotAccessoriesApplicationDetails = sysRobotAccessoriesApplicationDetailMapper.selectByAccessoriesApplicationId(Long.parseLong(accessoriesApplicationId));

        return sysRobotAccessoriesApplicationDetails;
    }

    /**
     * 多配件发货
     *
     * @param sysRobotAccessoriesApplication
     * @return
     */
    @Override
    public int updateMany(SysRobotAccessoriesApplication sysRobotAccessoriesApplication) {

//        log.info(dataMap.toString());
//        String accessoriesApplicationId = (String) dataMap.get("accessoriesApplicationId");
//        String warehouseId = (String)dataMap.get("warehouseId");

        SysRobotAccessoriesApplication sysRobotAccessoriesApplication1 = sysRobotAccessoriesApplicationMapper.selectByPrimaryKey(sysRobotAccessoriesApplication.getAccessoriesApplicationId());

        String detailListN = sysRobotAccessoriesApplication.getDetailListN();
        List<SysRobotAccessoriesApplicationDetail> detailList = JSONObject.parseArray(detailListN, SysRobotAccessoriesApplicationDetail.class);

        //判断是否审核同意
        if ("1".equals(sysRobotAccessoriesApplication1.getApprovalProgress())) {

            for (SysRobotAccessoriesApplicationDetail sysRobotAccessoriesApplicationDetail : detailList) {

                SysRobotPartsManagement sysRobotPartsManagement = sysRobotPartsManagementMapper.selectByPrimaryKey(sysRobotAccessoriesApplicationDetail.getAccessoriesId());

                //添加出库库单
                InventoryDetail detail = new InventoryDetail();
                detail.setId(IdUtils.getSeqId());
                detail.setOrderNumber(service.getOrderNumber(2));
                //查询发货地址
                Address byKey1 = addressMapper.getByKey(Long.parseLong(sysRobotAccessoriesApplicationDetail.getWarehouseId()), null);
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
                Address byKey = addressMapper.getByKey(sysRobotAccessoriesApplication1.getScenicSpotId(), null);
                detail.setReceivingName(byKey.getSpotName());//收货单位
                detail.setReceivingAddress(byKey.getAddress());//收货地址
                detail.setReceivingPhone(byKey.getPhone());//收货单位手机号
                detail.setOrderTime(DateUtil.currentDateTime());
                detail.setReceivingId(byKey.getSpotId());
                detail.setReceiver(byKey.getName());//收货人
                detail.setReceivingAddressTypeId(byKey.getId());
                detail.setType(2l);
                detail.setCreateTime(DateUtil.currentDateTime());
                detail.setUpdateTime(DateUtil.currentDateTime());

                GoodsStock byGoodsN = goodsStockMapper.getByGoods(sysRobotAccessoriesApplicationDetail.getAccessoriesId(), Long.parseLong(sysRobotAccessoriesApplicationDetail.getWarehouseId()));
                if (!StringUtils.isEmpty(byGoodsN)) {
                    byGoodsN.setAmount(new Double(byGoodsN.getAmount() - Long.parseLong(sysRobotAccessoriesApplicationDetail.getAccessoryNumber())).longValue());
                    goodsStockMapper.edit(byGoodsN);
                } else {
                    GoodsStock goodsStock = new GoodsStock();
                    goodsStock.setId(IdUtils.getSeqId());
                    goodsStock.setSpotId(sysRobotAccessoriesApplication1.getScenicSpotId());
                    goodsStock.setManagementId(sysRobotAccessoriesApplicationDetail.getAccessoriesId());
                    goodsStock.setAmount(Long.parseLong(sysRobotAccessoriesApplicationDetail.getAccessoryNumber()));
                    goodsStock.setNotes("配件申请出库");
                    goodsStock.setUpdateTime(DateUtil.currentDateTime());
                    goodsStockMapper.add(goodsStock);
                }

                int g = inventoryDetailMapper.insert(detail);

                sysRobotAccessoriesApplicationDetailMapper.updateByPrimaryKeySelective(sysRobotAccessoriesApplicationDetail);

            }

        }

        int i = sysRobotAccessoriesApplicationMapper.updateMany(sysRobotAccessoriesApplication.getProcessingProgress(), sysRobotAccessoriesApplication.getAccessoriesApplicationId().toString());
        return i;

    }

    /**
     * 添加快递单号及快递费，（多配件）
     *
     * @param sysRobotAccessoriesApplication
     * @return
     */
    @Override
    public int updateExPressfeeMany(SysRobotAccessoriesApplication sysRobotAccessoriesApplication) {

        String detailListN = sysRobotAccessoriesApplication.getDetailListN();
        List<SysRobotAccessoriesApplicationDetail> detailList = JSONObject.parseArray(detailListN, SysRobotAccessoriesApplicationDetail.class);

        for (SysRobotAccessoriesApplicationDetail sysRobotAccessoriesApplicationDetail : detailList) {

            sysRobotAccessoriesApplicationDetailMapper.updateByPrimaryKeySelective(sysRobotAccessoriesApplicationDetail);

        }

        int i = sysRobotAccessoriesApplicationMapper.updateByPrimaryKeySelective(sysRobotAccessoriesApplication);
        return i;
    }

    /**
     * 修改为已确认收货(多配件)
     *
     * @param sysRobotAccessoriesApplication
     * @return
     */
    @Override
    public int editApprovalMany(SysRobotAccessoriesApplication sysRobotAccessoriesApplication) {


        return sysRobotAccessoriesApplicationMapper.updateByPrimaryKeySelective(sysRobotAccessoriesApplication);

    }

    /**
     * 根据配件id,和数量，获取有此配件的仓库
     *
     * @param managementId
     * @return
     */
    @Override
    public List<GoodsStock> getManagementByGoodsStock(String managementId, Double num) {

        List<GoodsStock> list = goodsStockMapper.getPartIdAndNumberByStockList(Long.parseLong(managementId), num);
        return list;
    }

    /**
     * 获取本景区的发货单
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysRobotAccessoriesApplicationDetail> getSpotIdSendOutGoodsList(Long userId, String scenicSpotId, String isSendOutGoods) {
//
//        PageHelper.startPage(pageNum,pageSize);
        List<SysRobotAccessoriesApplicationDetail> list = sysRobotAccessoriesApplicationDetailMapper.getSpotIdSendOutGoodsList(userId, scenicSpotId, isSendOutGoods);
        List<SysRobotAccessoriesApplicationDetail> listN = sysRobotAccessoriesApplicationDetailMapper.selectNullByList(scenicSpotId, isSendOutGoods);

        for (int i = 0; i < listN.size(); i++) {
            if (listN.get(i).getWarehouseId() != null) {
                if ("121601250496737".equals(listN.get(i).getWarehouseId())) {
                    listN.get(i).setWarehouseName("金华瑞迪车业有限公司");
                    listN.get(i).setWarehouseModelId(listN.get(i).getWarehouseId());
                } else {
                    listN.get(i).setWarehouseModelId(listN.get(i).getWarehouseId());
                    GoodsStock goodsStock = goodsStockMapper.listById(listN.get(i).getWarehouseId());
                    if (goodsStock == null) {
                        listN.get(i).setWarehouseName("");
                    } else {
                        listN.get(i).setWarehouseName(goodsStock.getSpotName());
                    }
                }
            }
        }

        list.addAll(listN);
        for (SysRobotAccessoriesApplicationDetail sysRobotAccessoriesApplicationDetail : list) {
            //配件申请发货单
            if ("1".equals(sysRobotAccessoriesApplicationDetail.getType())) {

                SysRobotAccessoriesApplication sysRobotAccessoriesApplication = sysRobotAccessoriesApplicationMapper.selectByPrimaryKey(sysRobotAccessoriesApplicationDetail.getAccessoriesApplicationId());
                if (StringUtils.isEmpty(sysRobotAccessoriesApplication)) {
                    continue;
                }
                sysRobotAccessoriesApplicationDetail.setScenicSpotId(String.valueOf(sysRobotAccessoriesApplication.getScenicSpotId()));
                sysRobotAccessoriesApplicationDetail.setApplicant(sysRobotAccessoriesApplication.getApplicant());
                sysRobotAccessoriesApplicationDetail.setApplicantPhone(sysRobotAccessoriesApplication.getApplicantPhone());
                sysRobotAccessoriesApplicationDetail.setReceivingAddress(sysRobotAccessoriesApplication.getReceivingAddress());
                SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(sysRobotAccessoriesApplication.getScenicSpotId());
                sysRobotAccessoriesApplicationDetail.setScenicSpotName(sysScenicSpot.getScenicSpotName());
//                sysRobotAccessoriesApplicationDetail.setWarehouseId();


            } else if ("2".equals(sysRobotAccessoriesApplicationDetail.getType())) {
                //故障申请发货单
                SysRobotErrorRecords sysRobotErrorRecords = sysRobotErrorRecordsMapper.selectByPrimaryKey(sysRobotAccessoriesApplicationDetail.getAccessoriesApplicationId());
                if (StringUtils.isEmpty(sysRobotErrorRecords)) {
                    continue;
                }
                sysRobotAccessoriesApplicationDetail.setApplicant(sysRobotErrorRecords.getErrorRecordsPersonnel());
                sysRobotAccessoriesApplicationDetail.setScenicSpotId(String.valueOf(sysRobotErrorRecords.getScenicSpotId()));
                sysRobotAccessoriesApplicationDetail.setApplicantPhone(sysRobotErrorRecords.getErrorRecordsTel());
                sysRobotAccessoriesApplicationDetail.setReceivingAddress(sysRobotErrorRecords.getErrorRecordsAddress());
                SysScenicSpot sysScenicSpot = sysScenicSpotMapper.selectByPrimaryKey(sysRobotErrorRecords.getScenicSpotId());
                sysRobotAccessoriesApplicationDetail.setScenicSpotName(sysScenicSpot.getScenicSpotName());

            }

        }

        return list;
    }

    /**
     * 修改配件申请发货单
     *
     * @param sysRobotAccessoriesApplicationDetail
     * @return
     */
    @Override
    public int editApplicationDetail(SysRobotAccessoriesApplicationDetail sysRobotAccessoriesApplicationDetail) {

        SysRobotAccessoriesApplicationDetail sysRobotAccessoriesApplicationDetailN = sysRobotAccessoriesApplicationDetailMapper.selectById(sysRobotAccessoriesApplicationDetail.getId().toString());
        if ("2".equals(sysRobotAccessoriesApplicationDetailN.getType())) {//故障申请发货单

            SysRobotErrorRecords sysRobotErrorRecords = sysRobotErrorRecordsMapper.selectByPrimaryKey(sysRobotAccessoriesApplicationDetailN.getAccessoriesApplicationId());
            String orderNumber = service.getOrderNumber(2);
            if (sysRobotAccessoriesApplicationDetail.getWarehouseModelId().equals(sysRobotErrorRecords.getScenicSpotId())) {

                SysRobotPartsManagement sysRobotPartsManagement = sysRobotPartsManagementMapper.selectByPrimaryKey(sysRobotAccessoriesApplicationDetailN.getAccessoriesId());


//                //添加入库单
//                InventoryDetail detail1 = new InventoryDetail();
//                detail1.setId(IdUtils.getSeqId());
//                detail1.setOrderNumber(orderNumber);
//                //查询发货地址
//                Address byKey2 = addressMapper.getByKey(Long.parseLong(sysRobotAccessoriesApplicationDetail.getWarehouseModelId()),sysRobotAccessoriesApplicationDetail.getType());
//                detail1.setSpotId(byKey2.getSpotId());//库房发货单位
//                detail1.setSpotName(byKey2.getSpotName());// 发货单位名称
//                detail1.setPhone(byKey2.getPhone());//发货单位联系电话
//                detail1.setSpotAddressTypeId(byKey2.getId());
//                detail1.setDeliveryMan(byKey2.getName());//发货人
//                detail1.setNotes(sysRobotAccessoriesApplicationDetail.getShippingInstructions());//发货说明
//                detail1.setGoodsCode(sysRobotPartsManagement.getAccessoryModel());//配件编码
//                detail1.setGoodsAmount(new Double(sysRobotAccessoriesApplicationDetailN.getAccessoryNumber()).longValue());//数量
//                Double d1 = Double.parseDouble(sysRobotAccessoriesApplicationDetailN.getAccessoryPrice()) / Double.parseDouble(sysRobotAccessoriesApplicationDetail.getAccessoryNumber());
//                detail1.setUnitPirce(d1);//单价
//                detail1.setNotes("自动入库");//备注（自动添加入库记录）
//                detail1.setInStockType("1");
//                detail1.setInStockReason("2");
//                detail1.setTotalAmount(Double.parseDouble(sysRobotAccessoriesApplicationDetailN.getAccessoryPrice()));//合计金额
//                detail1.setGoodsName(sysRobotAccessoriesApplicationDetailN.getAccessoriesName());//商品名称
//                detail1.setModel(sysRobotPartsManagement.getAccessoriesCode());//商品编码
//                detail1.setUnit(sysRobotPartsManagement.getUnit());//单位
//                detail1.setGoodsId(sysRobotPartsManagement.getPartsManagementId());
//                detail1.setReceivingName(byKey2.getSpotName());//收货单位
//                detail1.setReceivingAddress(byKey2.getAddress());//收货地址
//                detail1.setReceivingPhone(byKey2.getPhone());//收货单位手机号
//                detail1.setOrderTime(DateUtil.currentDateTime());
//                detail1.setReceivingId(byKey2.getSpotId());
//                detail1.setReceiver(byKey2.getName());//收货人
//                detail1.setReceivingAddressTypeId(byKey2.getId());
//                detail1.setType(1l);
//                detail1.setCreateTime(DateUtil.currentDateTime());
//                detail1.setUpdateTime(DateUtil.currentDateTime());
//                GoodsStock byGoods1 = goodsStockMapper.getByGoods(sysRobotAccessoriesApplicationDetailN.getAccessoriesId(), sysRobotErrorRecords.getScenicSpotId());
//                if (!StringUtils.isEmpty(byGoods1)) {
//                    byGoods1.setAmount(byGoods1.getAmount() - Long.parseLong(sysRobotAccessoriesApplicationDetail.getAccessoryNumber()));
//                    goodsStockMapper.edit(byGoods1);
//                }
//                //添加入库单
//                int t1 = inventoryDetailMapper.insert(detail1);


                //添加出库单
                InventoryDetail detail = new InventoryDetail();
                detail.setId(IdUtils.getSeqId());
                detail.setOrderNumber(orderNumber);
                //查询发货地址
                Address byKey1 = addressMapper.getByKey(Long.parseLong(sysRobotAccessoriesApplicationDetail.getWarehouseModelId()), sysRobotAccessoriesApplicationDetail.getType());
                detail.setSpotId(byKey1.getSpotId());//库房发货单位
                detail.setSpotName(byKey1.getSpotName());// 发货单位名称
                detail.setPhone(byKey1.getPhone());//发货单位联系电话
                detail.setSpotAddressTypeId(byKey1.getId());
                detail.setDeliveryMan(byKey1.getName());//发货人
                detail.setNotes(sysRobotAccessoriesApplicationDetail.getShippingInstructions());//发货说明
                detail.setGoodsCode(sysRobotPartsManagement.getAccessoryModel());//配件编码
                detail.setGoodsAmount(new Double(sysRobotAccessoriesApplicationDetailN.getAccessoryNumber()).longValue());//数量
                Double d = Double.parseDouble(sysRobotAccessoriesApplicationDetailN.getAccessoryPrice()) / Double.parseDouble(sysRobotAccessoriesApplicationDetail.getAccessoryNumber());
                detail.setUnitPirce(d);//单价
//                detail.setNotes("自动出库");//备注（自动添加出库记录）
                detail.setInStockType("3");//出入库类型，出库：1生产、2销售、3售后
                detail.setInStockReason("4");//出库原因 ：1生产 2配件申请 3故障上报 4损坏上报
                detail.setTotalAmount(Double.parseDouble(sysRobotAccessoriesApplicationDetailN.getAccessoryPrice()));//合计金额
                detail.setGoodsName(sysRobotAccessoriesApplicationDetailN.getAccessoriesName());//商品名称
                detail.setModel(sysRobotPartsManagement.getAccessoriesCode());//商品编码
                detail.setUnit(sysRobotPartsManagement.getUnit());//单位
                detail.setGoodsId(sysRobotPartsManagement.getPartsManagementId());//商品id
                detail.setReceivingName(byKey1.getSpotName());//收货单位
                detail.setReceivingAddress(byKey1.getAddress());//收货地址
                detail.setReceivingPhone(byKey1.getPhone());//收货单位手机号
                detail.setOrderTime(DateUtil.currentDateTime());//订单时间
                detail.setReceivingId(byKey1.getSpotId());//收货单位
                detail.setReceiver(byKey1.getName());//收货人
                detail.setReceivingAddressTypeId(byKey1.getId());//收货地址类型id
                detail.setType(2l);//出库
                detail.setCreateTime(DateUtil.currentDateTime());
                detail.setUpdateTime(DateUtil.currentDateTime());
                GoodsStock byGoods = goodsStockMapper.getByGoods(sysRobotAccessoriesApplicationDetailN.getAccessoriesId(), sysRobotErrorRecords.getScenicSpotId());
                if (!StringUtils.isEmpty(byGoods)) {
                    byGoods.setAmount(byGoods.getAmount() - Long.parseLong(sysRobotAccessoriesApplicationDetail.getAccessoryNumber()));
                    goodsStockMapper.edit(byGoods);
                }
                //添加出库单
                int t = inventoryDetailMapper.insert(detail);
            } else { //从别景区出库调用配件
                // 添加出库单，以及仓库扣除发货配件数量
                SysRobotPartsManagement sysRobotPartsManagement = sysRobotPartsManagementMapper.selectByPrimaryKey(sysRobotAccessoriesApplicationDetailN.getAccessoriesId());
                String orderNumberN = service.getOrderNumber(2);
                //添加出库单
                InventoryDetail detail = new InventoryDetail();
                detail.setId(IdUtils.getSeqId());
                detail.setOrderNumber(orderNumberN);
                Address byKey = addressMapper.getByKey(Long.parseLong(sysRobotAccessoriesApplicationDetail.getWarehouseModelId()), sysRobotAccessoriesApplicationDetail.getType());
                if (byKey == null) {
                    return 0;
                }

                detail.setSpotId(Long.parseLong(sysRobotAccessoriesApplicationDetail.getWarehouseModelId()));//库房发货单位
                detail.setSpotName(byKey.getSpotName());// 发货单位名称
                detail.setPhone(byKey.getPhone());//发货单位联系电话
                detail.setSpotAddressTypeId(byKey.getId());
                detail.setDeliveryMan(byKey.getName());//发货人
                detail.setNotes(sysRobotAccessoriesApplicationDetail.getShippingInstructions());//发货说明
                detail.setGoodsCode(sysRobotAccessoriesApplicationDetailN.getAccessoryModel());//配件编码
                detail.setGoodsAmount(new Double(sysRobotAccessoriesApplicationDetailN.getAccessoryNumber()).longValue());//数量
                Double d = Double.parseDouble(sysRobotAccessoriesApplicationDetailN.getAccessoryPrice()) / Long.parseLong(sysRobotAccessoriesApplicationDetailN.getAccessoryNumber());
                detail.setUnitPirce(d);//单价
                detail.setInStockType("3");
                detail.setInStockReason("4");
                detail.setTotalAmount(Double.parseDouble(sysRobotAccessoriesApplicationDetailN.getAccessoryPrice()));//合计金额
                detail.setGoodsName(sysRobotAccessoriesApplicationDetailN.getAccessoriesName());//商品名称
                detail.setModel(sysRobotPartsManagement.getAccessoriesCode());//商品编码
                detail.setUnit(sysRobotPartsManagement.getUnit());//单位
                detail.setGoodsId(sysRobotPartsManagement.getPartsManagementId());
//            detail.setSpotAddressTypeId(inventory.getSpotId());
//            detail.setReceivingId(addressS.getSpotId());
//            detail.setReceivingAddressTypeId(inventory.getReceivingId());

                //收货单位地址
                Address byKey1 = addressMapper.getByKey(sysRobotErrorRecords.getScenicSpotId(), sysRobotAccessoriesApplicationDetail.getType());
                detail.setReceivingName(byKey1.getSpotName());//收货单位
                detail.setReceivingAddress(byKey1.getAddress());//收货地址
                detail.setReceivingPhone(byKey1.getPhone());//收货单位手机号
                detail.setOrderTime(DateUtil.currentDateTime());
                detail.setReceivingId(byKey1.getSpotId());
                detail.setReceiver(byKey1.getName());//收货人
                detail.setReceivingAddressTypeId(byKey1.getId());
                detail.setType(2l);
                detail.setCreateTime(DateUtil.currentDateTime());
                detail.setUpdateTime(DateUtil.currentDateTime());
                GoodsStock byGoods = goodsStockMapper.getByGoods(sysRobotAccessoriesApplicationDetailN.getAccessoriesId(), Long.parseLong(sysRobotAccessoriesApplicationDetail.getWarehouseModelId()));
                if (!StringUtils.isEmpty(byGoods)) {
                    byGoods.setAmount(new Double(byGoods.getAmount() - Long.parseLong(sysRobotAccessoriesApplicationDetailN.getAccessoryNumber())).longValue());
                    goodsStockMapper.edit(byGoods);
                }
                //添加出库单
                int t = inventoryDetailMapper.insert(detail);
            }
        } else {//配件申请发货单

            SysRobotAccessoriesApplication sysRobotAccessoriesApplication = sysRobotAccessoriesApplicationMapper.selectByPrimaryKey(sysRobotAccessoriesApplicationDetailN.getAccessoriesApplicationId());

            String orderNumber = service.getOrderNumber(2);
            if (sysRobotAccessoriesApplicationDetail.getWarehouseModelId().equals(sysRobotAccessoriesApplication.getScenicSpotId())) {

                SysRobotPartsManagement sysRobotPartsManagement = sysRobotPartsManagementMapper.selectByPrimaryKey(sysRobotAccessoriesApplicationDetailN.getAccessoriesId());
                //添加出库单
                InventoryDetail detail = new InventoryDetail();
                detail.setId(IdUtils.getSeqId());
                detail.setOrderNumber(orderNumber);
                //查询发货地址
                Address byKey1 = addressMapper.getByKey(Long.parseLong(sysRobotAccessoriesApplicationDetail.getWarehouseModelId()), sysRobotAccessoriesApplicationDetail.getType());
                detail.setSpotId(byKey1.getSpotId());//库房发货单位
                detail.setSpotName(byKey1.getSpotName());// 发货单位名称
                detail.setPhone(byKey1.getPhone());//发货单位联系电话
                detail.setSpotAddressTypeId(byKey1.getId());//供货地址类型id
                detail.setDeliveryMan(byKey1.getName());//发货人
                detail.setNotes(sysRobotAccessoriesApplicationDetail.getShippingInstructions());//发货说明
                detail.setGoodsCode(sysRobotPartsManagement.getAccessoryModel());//配件编码
                detail.setGoodsAmount(new Double(sysRobotAccessoriesApplicationDetailN.getAccessoryNumber()).longValue());//数量
                Double d = Double.parseDouble(sysRobotAccessoriesApplicationDetailN.getAccessoryPrice()) / Double.parseDouble(sysRobotAccessoriesApplicationDetail.getAccessoryNumber());
                detail.setUnitPirce(d);//单价
                detail.setInStockType("3");//出库类型：3售后
                detail.setInStockReason("4");//出库原因：4损坏上报
                detail.setTotalAmount(Double.parseDouble(sysRobotAccessoriesApplicationDetailN.getAccessoryPrice()));//合计金额
                detail.setGoodsName(sysRobotAccessoriesApplicationDetailN.getAccessoriesName());//商品名称
                detail.setModel(sysRobotPartsManagement.getAccessoriesCode());//商品编码
                detail.setUnit(sysRobotPartsManagement.getUnit());//单位
                detail.setGoodsId(sysRobotPartsManagement.getPartsManagementId());

                //收货单位地址
//                    Address byKey1 = addressMapper.getByKey(sysRobotErrorRecords1.getScenicSpotId());
                detail.setReceivingName(byKey1.getSpotName());//收货单位
                detail.setReceivingAddress(byKey1.getAddress());//收货地址
                detail.setReceivingPhone(byKey1.getPhone());//收货单位手机号
                detail.setOrderTime(DateUtil.currentDateTime());
                detail.setReceivingId(byKey1.getSpotId());
                detail.setReceiver(byKey1.getName());//收货人
                detail.setReceivingAddressTypeId(byKey1.getId());
                detail.setType(2l);
                detail.setCreateTime(DateUtil.currentDateTime());
                detail.setUpdateTime(DateUtil.currentDateTime());
                GoodsStock byGoods = goodsStockMapper.getByGoods(sysRobotAccessoriesApplicationDetailN.getAccessoriesId(), sysRobotAccessoriesApplication.getScenicSpotId());
                if (!StringUtils.isEmpty(byGoods)) {
                    byGoods.setAmount(byGoods.getAmount() - Long.parseLong(sysRobotAccessoriesApplicationDetail.getAccessoryNumber()));//仓库配件数量-申请配件数量
                    goodsStockMapper.edit(byGoods);
                }
                //添加出库单
                int t = inventoryDetailMapper.insert(detail);

            } else { //从别景区出库调用配件
                // 添加出库单，以及仓库扣除发货配件数量
                SysRobotPartsManagement sysRobotPartsManagement = sysRobotPartsManagementMapper.selectByPrimaryKey(sysRobotAccessoriesApplicationDetailN.getAccessoriesId());

                String orderNumberN = service.getOrderNumber(2);
                //添加出库单
                InventoryDetail detail = new InventoryDetail();
                detail.setId(IdUtils.getSeqId());
                detail.setOrderNumber(orderNumberN);
                //查询发货地址
                Address byKey = addressMapper.getByKey(Long.parseLong(sysRobotAccessoriesApplicationDetail.getWarehouseModelId()), sysRobotAccessoriesApplicationDetail.getType());
                detail.setSpotId(Long.parseLong(sysRobotAccessoriesApplicationDetail.getWarehouseModelId()));//库房发货单位
                detail.setSpotName(byKey.getSpotName());// 发货单位名称
                detail.setPhone(byKey.getPhone());//发货单位联系电话
                detail.setSpotAddressTypeId(byKey.getId());
                detail.setDeliveryMan(byKey.getName());//发货人
                detail.setNotes(sysRobotAccessoriesApplicationDetail.getShippingInstructions());//发货说明
                detail.setGoodsCode(sysRobotAccessoriesApplicationDetailN.getAccessoryModel());//配件编码
                detail.setGoodsAmount(new Double(sysRobotAccessoriesApplicationDetailN.getAccessoryNumber()).longValue());//数量
                Double d = Double.parseDouble(sysRobotAccessoriesApplicationDetailN.getAccessoryPrice()) / Long.parseLong(sysRobotAccessoriesApplicationDetailN.getAccessoryNumber());
                detail.setUnitPirce(d);//单价
                detail.setInStockType("3");
                detail.setInStockReason("4");
                detail.setTotalAmount(Double.parseDouble(sysRobotAccessoriesApplicationDetailN.getAccessoryPrice()));//合计金额
                detail.setGoodsName(sysRobotAccessoriesApplicationDetailN.getAccessoriesName());//商品名称
                detail.setModel(sysRobotPartsManagement.getAccessoriesCode());//商品编码
                detail.setUnit(sysRobotPartsManagement.getUnit());//单位
                detail.setGoodsId(sysRobotPartsManagement.getPartsManagementId());
//            detail.setSpotAddressTypeId(inventory.getSpotId());
//            detail.setReceivingId(addressS.getSpotId());
//            detail.setReceivingAddressTypeId(inventory.getReceivingId());

                //收货单位地址
                Address byKey1 = addressMapper.getByKey(Long.parseLong(sysRobotAccessoriesApplicationDetail.getWarehouseModelId()), sysRobotAccessoriesApplicationDetail.getType());
                detail.setReceivingName(byKey1.getSpotName());//收货单位
                detail.setReceivingAddress(byKey1.getAddress());//收货地址
                detail.setReceivingPhone(byKey1.getPhone());//收货单位手机号
                detail.setOrderTime(DateUtil.currentDateTime());
                detail.setReceivingId(byKey1.getSpotId());
                detail.setReceiver(byKey1.getName());//收货人
                detail.setReceivingAddressTypeId(byKey1.getId());
                detail.setType(2l);
                detail.setCreateTime(DateUtil.currentDateTime());
                detail.setUpdateTime(DateUtil.currentDateTime());


                GoodsStock byGoods = goodsStockMapper.getByGoods(sysRobotAccessoriesApplicationDetailN.getAccessoriesId(), Long.parseLong(sysRobotAccessoriesApplicationDetail.getWarehouseModelId()));
                if (!StringUtils.isEmpty(byGoods)) {
                    byGoods.setAmount(new Double(byGoods.getAmount() - Long.parseLong(sysRobotAccessoriesApplicationDetailN.getAccessoryNumber())).longValue());
                    goodsStockMapper.edit(byGoods);
                }
                //添加出库单
                int t = inventoryDetailMapper.insert(detail);
            }
        }
        sysRobotAccessoriesApplicationDetail.setWarehouseId(sysRobotAccessoriesApplicationDetail.getWarehouseModelId());
        sysRobotAccessoriesApplicationDetail.setIsSendOutGoods("1");
        sysRobotAccessoriesApplicationDetail.setUpdateDate(DateUtil.currentDateTime());
        return sysRobotAccessoriesApplicationDetailMapper.updateByPrimaryKeySelective(sysRobotAccessoriesApplicationDetail);

    }


    /**
     * 发货单签收
     *
     * @param id
     * @param signInPicture
     * @return
     */
    @Override
    public int signForDelivery(String id, String signInPicture) {

        SysRobotAccessoriesApplicationDetail sysRobotAccessoriesApplicationDetail = sysRobotAccessoriesApplicationDetailMapper.selectById(id);

        SysRobotAccessoriesApplication sysRobotAccessoriesApplication = sysRobotAccessoriesApplicationMapper.selectByPrimaryKey(sysRobotAccessoriesApplicationDetail.getAccessoriesApplicationId());

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
        Address byKey = addressMapper.getByKey(sysRobotAccessoriesApplication.getScenicSpotId(), sysRobotAccessoriesApplicationDetail.getType());
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

        GoodsStock byGoodsN = goodsStockMapper.getByGoods(sysRobotAccessoriesApplicationDetail.getAccessoriesId(), sysRobotAccessoriesApplication.getScenicSpotId());
        if (!StringUtils.isEmpty(byGoodsN)) {
            byGoodsN.setAmount(new Double(byGoodsN.getAmount() + Long.parseLong(sysRobotAccessoriesApplicationDetail.getAccessoryNumber())).longValue());
            goodsStockMapper.edit(byGoodsN);
        } else {
            GoodsStock goodsStock = new GoodsStock();
            goodsStock.setId(IdUtils.getSeqId());
            goodsStock.setSpotId(sysRobotAccessoriesApplication.getScenicSpotId());
            goodsStock.setManagementId(sysRobotAccessoriesApplicationDetail.getAccessoriesId());
            goodsStock.setAmount(Long.parseLong(sysRobotAccessoriesApplicationDetail.getAccessoryNumber()));
            goodsStock.setNotes("配件申请入库");
            goodsStock.setUpdateTime(DateUtil.currentDateTime());
            goodsStockMapper.add(goodsStock);
        }
        int g = inventoryDetailMapper.insert(detail);


        int i = sysRobotAccessoriesApplicationDetailMapper.updateByPrimaryKeySelective(sysRobotAccessoriesApplicationDetail);
        return i;
    }

    /**
     * 管理后台，查询配件详情
     *
     * @param accessoriesApplicationId
     * @return
     */
    @Override
    public List<SysRobotAccessoriesApplicationDetail> robotAccessoriesIdByApplication(String accessoriesApplicationId) {

        List<SysRobotAccessoriesApplicationDetail> sysRobotAccessoriesApplicationDetails = sysRobotAccessoriesApplicationDetailMapper.selectByAccessoriesApplicationId(Long.parseLong(accessoriesApplicationId));

        return sysRobotAccessoriesApplicationDetails;

    }
}
