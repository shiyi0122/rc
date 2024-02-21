package com.hna.hka.archive.management.assetsSystem.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.Inventory;
import com.hna.hka.archive.management.assetsSystem.model.InventoryDetail;

import java.util.List;
import java.util.Map;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-10-21 14:40
 **/
public interface InventoryDetailService {
    PageInfo<InventoryDetail> list(Integer type, Long goodId, Long spotId, String orderNumber, String startDate, String endDate, String inStockReason,String inStockType,String signInState ,Integer pageNum, Integer pageSize);

    int add(Inventory inventory) throws Exception;

    boolean edit(Inventory inventory);

    boolean updateStatus(Long id, Integer status,Long userId);

    List<InventoryDetail> detailList(Integer type, Long goodId, Long spotId, String orderNumber,String inStockType ,String startDate, String endDate,String signInState,String outStockReason);

    List<InventoryDetail> detailList(Integer type, String orderNumber);

    List<InventoryDetail> getByOrderNumber(String orderNumber);

    int  importExcelEnter(InventoryDetail inventoryDetail);


    int  importExcelOut(InventoryDetail inventoryDetail);

    /**
     * 计算合计
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
    Map<String, Object> total( Integer type, Long goodId, Long spotId, String orderNumber, String startDate, String endDate, String inStockReason, String inStockType,String signInState);

    /**
     * 修改签收状态
     * @param id
     * @param status
     * @param userId
     * @return
     */
    boolean updateSignInStatus(Long id, Integer status, Long userId);

    List<InventoryDetail> getByOrderNumberN(String orderNumber, String type);

    List<InventoryDetail> listById();
}
