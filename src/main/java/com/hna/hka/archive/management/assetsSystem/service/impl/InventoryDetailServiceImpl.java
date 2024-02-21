package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.model.SysRobotErrorRecordDetaIl;
import com.hna.hka.archive.management.assetsSystem.dao.AddressMapper;
import com.hna.hka.archive.management.assetsSystem.dao.GoodsStockMapper;
import com.hna.hka.archive.management.assetsSystem.dao.InventoryDetailMapper;
import com.hna.hka.archive.management.assetsSystem.model.Address;
import com.hna.hka.archive.management.assetsSystem.model.GoodsStock;
import com.hna.hka.archive.management.assetsSystem.model.Inventory;
import com.hna.hka.archive.management.assetsSystem.model.InventoryDetail;
import com.hna.hka.archive.management.assetsSystem.service.InventoryDetailService;
import com.hna.hka.archive.management.system.dao.SysScenicSpotMapper;
import com.hna.hka.archive.management.system.dao.SysUsersMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.util.unit.DataUnit;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Map;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-10-21 14:40
 **/
@Service
public class InventoryDetailServiceImpl implements InventoryDetailService {

    @Autowired
    InventoryDetailMapper mapper;

    @Autowired
    GoodsStockMapper stockMapper;

    @Autowired
    SysUsersMapper sysUsersMapper;

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    SysScenicSpotMapper sysScenicSpotMapper;

    @Override
    public PageInfo<InventoryDetail> list(Integer type, Long goodId, Long spotId, String orderNumber, String startDate, String endDate, String inStockReason, String inStockType, String signInState, Integer pageNum, Integer pageSize) {
        PageHelper.offsetPage(pageNum, pageSize);
        List<InventoryDetail> list = mapper.list(type, goodId, spotId, orderNumber, inStockReason, inStockType, startDate, endDate, signInState);
        return new PageInfo(list);
    }

    //旧版本入库添加
//    @Override
//    @Transactional
//    public Boolean add(Inventory inventory) throws Exception {
//        if (inventory == null) {
//            throw new RuntimeException("单据为空,不允许添加");
//        } else {
//            if (inventory.getDetails() == null || inventory.getDetails().length == 0) {
//                throw new RuntimeException("商品为空,不允许添加");
//            } else {
//                for (InventoryDetail detail : inventory.getDetails()) {
//                    detail.setId(IdUtils.getSeqId());
//                    detail.setOrderNumber(inventory.getOrderNumber());
//                    detail.setSpotId(inventory.getSpotId());
//                    detail.setSpotName(inventory.getSpotName());
//                    detail.setReceivingId(inventory.getReceivingId());
//                    detail.setReceivingName(inventory.getReceivingName());
//                    detail.setPhone(inventory.getPhone());
//                    detail.setOrderTime(inventory.getOrderTime());
//                    detail.setDeliveryMan(inventory.getDeliveryMan());
//                    detail.setReceiver(inventory.getReveiver());
//                    detail.setType(inventory.getType());
//                    detail.setReceivingAddress(inventory.getReceivingAddress());
//                }
//
//
//                Integer result = mapper.add(inventory);
//                if (result == inventory.getDetails().length) {
//                    for (InventoryDetail detail : inventory.getDetails()) {
//                        GoodsStock stock = stockMapper.getByGoods(detail.getGoodsId(), detail.getSpotId());
//                        if (stock != null) {
//                            stock.setAmount(detail.getType() == 1 ? (detail.getGoodsAmount() + stock.getAmount()) : (stock.getAmount() - detail.getGoodsAmount()));
//                            stockMapper.edit(stock);
//                        } else {
//                            if (detail.getType() == 1) {
//                                stock = new GoodsStock();
//                                stock.setId(IdUtils.getSeqId());
//                                stock.setSpotId(detail.getReceivingId());
//                                stock.setAmount(detail.getGoodsAmount());
//                                stock.setManagementId(detail.getGoodsId());
//                                stock.setNotes("入库单自动生成,入库单号:" + detail.getOrderNumber());
//                                stockMapper.add(stock);
//                            } else {
//                                throw new RuntimeException("库房:" + detail.getSpotName() + ",商品编码:" + detail.getGoodsName() + ",库存信息未维护,请维护后进行操作");
//                            }
//                        }
//                    }
//                    return true;
//                } else {
//                    throw new RuntimeException("添加出错,请重新添加");
//                }
//            }
//        }
//    }


    @Override
    @Transactional
    public int add(Inventory inventory) throws Exception {
        if (inventory == null) {
            throw new RuntimeException("单据为空,不允许添加");
        } else {
            if (inventory.getDetails() == null || inventory.getDetails().length == 0) {
                throw new RuntimeException("商品为空,不允许添加");
            } else {
                int insert = 0;
//                //故障申请
//                if ("0".equals(inventory.getOutType())){
//                    addressMapper.selectByOut(inventory.getAccessoriesApplicationId());
//                }
                //发货地址
                Address addressF = addressMapper.selectById(inventory.getSpotId());
                //收货地址
                Address addressS = addressMapper.selectById(inventory.getReceivingId());
                SysRobotErrorRecordDetaIl sysRobotErrorRecordDetaIl = new SysRobotErrorRecordDetaIl();
                for (InventoryDetail detail : inventory.getDetails()) {

                    detail.setId(IdUtils.getSeqId());
                    detail.setOrderNumber(inventory.getOrderNumber());
                    detail.setSpotId(addressF.getSpotId());
                    detail.setSpotName(addressF.getSpotName());
                    detail.setSpotAddressTypeId(inventory.getSpotId());
                    detail.setReceivingId(addressS.getSpotId());
                    detail.setReceivingAddressTypeId(inventory.getReceivingId());
                    detail.setReceivingName(addressS.getSpotName());
                    detail.setReceivingPhone(inventory.getReceivingPhone());
                    detail.setPhone(inventory.getPhone());
                    detail.setOrderTime(inventory.getOrderTime());
                    detail.setDeliveryMan(inventory.getDeliveryMan());
                    detail.setReceiver(inventory.getReveiver());
                    detail.setType(inventory.getType());
                    detail.setCreateTime(DateUtil.currentDateTime());
                    detail.setUpdateTime(DateUtil.currentDateTime());
                    detail.setReceivingAddress(inventory.getReceivingAddress());

                }

                for (InventoryDetail detail : inventory.getDetails()) {

                    //供货库存
                    GoodsStock stock = stockMapper.getByGoods(detail.getGoodsId(), detail.getSpotId());
                    //收货库存
                    GoodsStock byGoods = stockMapper.getByGoods(detail.getGoodsId(), detail.getReceivingId());

                    if (detail.getType() == 1) {
                        if (byGoods != null) {
                            byGoods.setAmount(detail.getType() == 1 ? (detail.getGoodsAmount() + byGoods.getAmount()) : (byGoods.getAmount() - detail.getGoodsAmount()));
                            stockMapper.edit(byGoods);
                        } else {
                            byGoods = new GoodsStock();
                            byGoods.setId(IdUtils.getSeqId());
                            byGoods.setSpotId(detail.getReceivingId());
                            byGoods.setManagementId(detail.getGoodsId());
                            byGoods.setAmount(detail.getGoodsAmount());
                            byGoods.setNotes(detail.getNotes());
                            byGoods.setUpdateTime(DateUtil.currentDateTime());
                            stockMapper.add(byGoods);
                        }
                    } else {
                        if (stock != null) {
                            if (stock.getAmount() < detail.getGoodsAmount()) {//供货单位配件数量小于添加数量，则无法添加
                                return 2;
                            }
                            stock.setAmount(detail.getType() == 1 ? (detail.getGoodsAmount() + stock.getAmount()) : (stock.getAmount() - detail.getGoodsAmount()));
                            stockMapper.edit(stock);
                        } else {
                            return 2;
                        }
                    }


//                        if (byGoods != null){
//                            byGoods.setAmount(detail.getGoodsAmount() + byGoods.getAmount());
//                            stockMapper.edit(byGoods);
//                        }else{
//                            if (detail.getType() == 1) {
//                                stock = new GoodsStock();
//                                stock.setId(IdUtils.getSeqId());
//                                stock.setSpotId(detail.getReceivingId());
//                                stock.setAmount(detail.getGoodsAmount());
//                                stock.setManagementId(detail.getGoodsId());
//                                stock.setNotes("入库单自动生成,入库单号:" + detail.getOrderNumber());
//                                stockMapper.add(stock);
//                            } else {
//                                stock = new GoodsStock();
//                                stock.setId(IdUtils.getSeqId());
//                                stock.setSpotId(detail.getReceivingId());
//                                stock.setAmount(detail.getGoodsAmount());
//                                stock.setManagementId(detail.getGoodsId());
//                                stock.setNotes("出库单自动生成,入库单号:" + detail.getOrderNumber());
//                                stockMapper.add(stock);
////                            throw new RuntimeException("库房:" + detail.getSpotName() + ",商品编码:" + detail.getGoodsName() + ",库存信息未维护,请维护后进行操作");
//                            }
//
//                        }
//                        stock.setAmount(detail.getType() == 1 ? (detail.getGoodsAmount() + stock.getAmount()) : (stock.getAmount() - detail.getGoodsAmount()));
//                        stockMapper.edit(stock);
                    insert = mapper.insert(detail);
                    //添加到关联表中
                    sysRobotErrorRecordDetaIl.setGoodsInventoryDetailId(detail.getId());
                    mapper.updateSys(sysRobotErrorRecordDetaIl);
                }
                return insert;
            }
        }

    }

    @Transactional
    @Override
    public boolean edit(Inventory inventory) {
        if (inventory == null) {
            throw new RuntimeException("单据为空,不允许修改");
        } else {
            if (inventory.getDetails() == null || inventory.getDetails().length == 0) {
                throw new RuntimeException("商品为空,不允许修改");
            } else {
                //发货地址
                Address addressF = addressMapper.selectById(inventory.getSpotId());
                //收货地址
                Address addressS = addressMapper.selectById(inventory.getReceivingId());

                for (InventoryDetail detail : inventory.getDetails()) {
                    if (detail.getId() == null) {
                        detail.setId(IdUtils.getSeqId());
                    }
                    detail.setOrderNumber(inventory.getOrderNumber());
                    detail.setSpotId(addressF.getSpotId());
                    detail.setSpotName(addressF.getSpotName());
                    detail.setReceivingId(addressS.getSpotId());
                    detail.setReceivingName(addressS.getSpotName());
                    detail.setReceivingPhone(inventory.getReceivingPhone());
                    detail.setPhone(inventory.getPhone());
                    detail.setOrderTime(inventory.getOrderTime());
                    detail.setDeliveryMan(inventory.getDeliveryMan());
                    detail.setReceiver(inventory.getReveiver());
                    detail.setType(inventory.getType());
                    detail.setSpotAddressTypeId(inventory.getSpotId());
                    detail.setReceivingAddressTypeId(inventory.getReceivingId());
                    detail.setOldGoodAmount(mapper.getGoodAmountById(detail.getId()));
                    mapper.delete(detail.getId());
                }
                Integer result = mapper.add(inventory);
                if (result == inventory.getDetails().length) {
                    for (InventoryDetail detail : inventory.getDetails()) {
                        GoodsStock stock = stockMapper.getByGoods(detail.getGoodsId(), detail.getSpotId());
                        if (stock != null) {
                            stock.setAmount(detail.getType() == 1 ? (stock.getAmount() + detail.getGoodsAmount() - detail.getOldGoodAmount()) : (stock.getAmount() - detail.getGoodsAmount() + detail.getOldGoodAmount()));
                            stockMapper.edit(stock);
                        } else {
                            throw new RuntimeException("库房:" + detail.getSpotName() + ",商品编码:" + detail.getGoodsName() + ",库存信息未维护,请维护后进行操作");
                        }
                    }
                    return true;
                } else {
                    throw new RuntimeException("修改出错,请重新操作");
                }
            }
        }
    }

    @Override
    public boolean updateStatus(Long id, Integer status, Long userId) {
        InventoryDetail detail = mapper.getById(id);
        if (detail == null) {
            throw new RuntimeException("数据不存在或已被删除,请刷新后重试");
        } else {
            if (status == 2) {
                GoodsStock stock = stockMapper.getByGoods(detail.getGoodsId(), detail.getReceivingId());
                if (stock != null) {
                    stock.setAmount(detail.getType() == 1 ? (stock.getAmount() - detail.getGoodsAmount()) : (stock.getAmount() + detail.getGoodsAmount()));
                    stockMapper.edit(stock);
                } else {
                    throw new RuntimeException("库房:" + detail.getSpotName() + ",商品编码:" + detail.getGoodsName() + ",库存信息未维护,请维护后进行操作");
                }
            }
            mapper.updateStatus(id, status, userId);
            return true;
        }
    }

    @Override
    public List<InventoryDetail> detailList(Integer type, Long goodId, Long spotId, String orderNumber, String inStockType, String startDate, String endDate, String signInState, String outStockReason) {
        List<InventoryDetail> list = mapper.list(type, goodId, spotId, orderNumber, outStockReason, inStockType, startDate, endDate, signInState);
        for (InventoryDetail inventoryDetail : list) {

            if (!StringUtils.isEmpty(inventoryDetail.getConfirmedById())) {
                SysUsers sysUsers = sysUsersMapper.selectByPrimaryKey(Long.parseLong(inventoryDetail.getConfirmedById()));
                inventoryDetail.setConfirmedByName(sysUsers.getUserName());
            }

            if (StringUtils.isEmpty(inventoryDetail.getInStockType())) {
                continue;
            } else {
                if (inventoryDetail.getType() == 1) {
                    if ("1".equals(inventoryDetail.getInStockType())) {
                        inventoryDetail.setInStockType("采购");
                    } else if ("2".equals(inventoryDetail.getInStockType())) {
                        inventoryDetail.setInStockType("回收");
                    }


                } else {
                    if ("1".equals(inventoryDetail.getInStockType())) {
                        inventoryDetail.setInStockType("生产");
                    } else if ("2".equals(inventoryDetail.getInStockType())) {
                        inventoryDetail.setInStockType("销售");
                    } else if ("3".equals(inventoryDetail.getInStockType())) {
                        inventoryDetail.setInStockType("售后");
                    }

                    if ("1".equals(inventoryDetail.getInStockReason())) {
                        inventoryDetail.setInStockReason("生产");
                    } else if ("2".equals(inventoryDetail.getInStockReason())) {
                        inventoryDetail.setInStockReason("配件申请");
                    } else if ("3".equals(inventoryDetail.getInStockReason())) {
                        inventoryDetail.setInStockReason("故障上报");
                    } else if ("4".equals(inventoryDetail.getInStockReason())) {
                        inventoryDetail.setInStockReason("损坏上报");
                    }
                }
            }


        }
        return list;
    }

    @Override
    public List<InventoryDetail> detailList(Integer type, String orderNumber) {
        List<InventoryDetail> list = mapper.list(type, null, null, orderNumber, null, null, null, null, null);
        return list;
    }

    @Override
    public List<InventoryDetail> getByOrderNumber(String orderNumber) {
        List<InventoryDetail> list = mapper.getByOrderNumber(orderNumber);
        return list;
    }

    /**
     * 入库导入
     *
     * @param inventoryDetail
     * @return
     */
    @Override
    public int importExcelEnter(InventoryDetail inventoryDetail) {

        int i = 0;
        List<InventoryDetail> byOrderNumber = mapper.getByOrderNumber(inventoryDetail.getOrderNumber());
        if (byOrderNumber.size() > 0) {
            InventoryDetail inventoryDetailO = byOrderNumber.get(0);
            inventoryDetailO.setOrderNumber(inventoryDetail.getOrderNumber());
            inventoryDetailO.setGoodsCode(inventoryDetail.getGoodsCode());
            inventoryDetailO.setGoodsName(inventoryDetail.getGoodsName());
            inventoryDetailO.setModel(inventoryDetail.getModel());
            inventoryDetailO.setUnit(inventoryDetail.getUnit());
            inventoryDetailO.setGoodsAmount(inventoryDetail.getGoodsAmount());
            inventoryDetailO.setUnitPirce(inventoryDetail.getUnitPirce());
            inventoryDetailO.setTotalAmount(inventoryDetail.getTotalAmount());
            inventoryDetailO.setNotes(inventoryDetail.getNotes());
            inventoryDetailO.setPhone(inventoryDetail.getPhone());
            inventoryDetailO.setDeliveryMan(inventoryDetail.getDeliveryMan());
            inventoryDetailO.setReceiver(inventoryDetail.getReceiver());
            inventoryDetailO.setReceivingName(inventoryDetail.getReceivingName());
            inventoryDetailO.setCourierNumber(inventoryDetail.getCourierNumber());
            inventoryDetailO.setExpressFee(inventoryDetail.getExpressFee());
            inventoryDetailO.setInStockType(inventoryDetail.getInStockType());

            i = mapper.update(inventoryDetailO);
        } else {

            //发货地址
            SysScenicSpot spotNameById = sysScenicSpotMapper.getSpotNameById(inventoryDetail.getSpotName());
            //收货地址
            SysScenicSpot spotNameById1 = sysScenicSpotMapper.getSpotNameById(inventoryDetail.getReceivingName());

            inventoryDetail.setSpotId(spotNameById.getScenicSpotId());
            inventoryDetail.setReceivingId(spotNameById1.getScenicSpotId());
            inventoryDetail.setId(IdUtils.getSeqId());
            inventoryDetail.setCreateTime(DateUtil.currentDateTime());
            inventoryDetail.setUpdateTime(DateUtil.currentDateTime());
            inventoryDetail.setType(1l);

            i = mapper.insert(inventoryDetail);
        }

        return i;
    }

    /**
     * 出库导入
     *
     * @param inventoryDetail
     * @return
     */
    @Override
    public int importExcelOut(InventoryDetail inventoryDetail) {

        int i = 0;
        List<InventoryDetail> byOrderNumber = mapper.getByOrderNumber(inventoryDetail.getOrderNumber());
        if (byOrderNumber.size() > 0) {
            InventoryDetail inventoryDetailO = byOrderNumber.get(0);
            inventoryDetailO.setOrderNumber(inventoryDetail.getOrderNumber());
            inventoryDetailO.setGoodsCode(inventoryDetail.getGoodsCode());
            inventoryDetailO.setGoodsName(inventoryDetail.getGoodsName());
            inventoryDetailO.setModel(inventoryDetail.getModel());
            inventoryDetailO.setUnit(inventoryDetail.getUnit());
            inventoryDetailO.setGoodsAmount(inventoryDetail.getGoodsAmount());
            inventoryDetailO.setUnitPirce(inventoryDetail.getUnitPirce());
            inventoryDetailO.setTotalAmount(inventoryDetail.getTotalAmount());
            inventoryDetailO.setNotes(inventoryDetail.getNotes());
            inventoryDetailO.setPhone(inventoryDetail.getPhone());
            inventoryDetailO.setDeliveryMan(inventoryDetail.getDeliveryMan());
            inventoryDetailO.setReceiver(inventoryDetail.getReceiver());
            inventoryDetailO.setReceivingName(inventoryDetail.getReceivingName());
            inventoryDetailO.setCourierNumber(inventoryDetail.getCourierNumber());
            inventoryDetailO.setExpressFee(inventoryDetail.getExpressFee());
            inventoryDetailO.setInStockType(inventoryDetail.getInStockType());
            inventoryDetailO.setInStockReason(inventoryDetail.getInStockReason());

            i = mapper.update(inventoryDetailO);
        } else {

            //发货地址
            SysScenicSpot spotNameById = sysScenicSpotMapper.getSpotNameById(inventoryDetail.getSpotName());
            //收货地址
            SysScenicSpot spotNameById1 = sysScenicSpotMapper.getSpotNameById(inventoryDetail.getReceivingName());

            inventoryDetail.setId(IdUtils.getSeqId());
            inventoryDetail.setCreateTime(DateUtil.currentDateTime());
            inventoryDetail.setUpdateTime(DateUtil.currentDateTime());
            inventoryDetail.setType(2l);

            i = mapper.insert(inventoryDetail);
        }

        return i;
    }

    /**
     * 计算合计
     *
     * @param type
     * @param goodId
     * @param spotId
     * @param orderNumber
     * @param startDate
     * @param endDate
     * @param inStockReason
     * @param inStockType
     * @return
     */
    @Override
    public Map<String, Object> total(Integer type, Long goodId, Long spotId, String orderNumber, String startDate, String endDate, String inStockReason, String inStockType, String signInState) {

        Map<String, Object> map = mapper.total(type, goodId, spotId, orderNumber, startDate, endDate, inStockReason, inStockType, signInState);

        return map;
    }

    /**
     * 修改签收状态
     *
     * @param id
     * @param status
     * @param userId
     * @return
     */
    @Override
    public boolean updateSignInStatus(Long id, Integer status, Long userId) {

        InventoryDetail detail = mapper.getById(id);
        if (detail == null) {
            throw new RuntimeException("数据不存在或已被删除,请刷新后重试");
        } else {
            mapper.updateSignInStatus(id, status);
            return true;
        }


    }

    @Override
    public List<InventoryDetail> getByOrderNumberN(String orderNumber, String type) {

        List<InventoryDetail> list = mapper.getByOrderNumberN(orderNumber, type);
        String id = "";
        for (InventoryDetail inventoryDetail : list) {
            Address address = addressMapper.selectById(inventoryDetail.getSpotAddressTypeId());
            String s = address.getType() + "," + address.getCompanyId() + "," + address.getSpotId() + "," + address.getId();
            inventoryDetail.setSpotLevelId(s);
            address = addressMapper.selectById(inventoryDetail.getReceivingAddressTypeId());
            s = address.getType() + "," + address.getCompanyId() + "," + address.getSpotId() + "," + address.getId();
            inventoryDetail.setReceivingLevelId(s);
        }
        return list;

    }

    @Override
    public List<InventoryDetail> listById() {
        return mapper.listById();
    }
}
