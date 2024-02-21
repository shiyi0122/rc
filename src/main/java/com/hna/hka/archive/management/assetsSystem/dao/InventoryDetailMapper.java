package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.appSystem.model.SysRobotErrorRecordDetaIl;
import com.hna.hka.archive.management.assetsSystem.model.Inventory;
import com.hna.hka.archive.management.assetsSystem.model.InventoryDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-10-21 14:40
 **/
public interface InventoryDetailMapper {


    List<InventoryDetail> list(@Param("type") Integer type, @Param("goodId") Long goodId,@Param("spotId") Long spotId,@Param("orderNumber") String orderNumber,@Param("inStockReason") String inStockReason,@Param("inStockType") String inStockType,@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("signInState") String signInState);

    Integer add(Inventory inventory);

    Long getGoodAmountById(Long id);

    void delete(Long id);

    InventoryDetail getById(Long id);

    void updateStatus(Long id, Integer status, Long userId);

    List<InventoryDetail> getByOrderNumber(String orderNumber);

    int update(InventoryDetail inventoryDetailO);

    int insert(InventoryDetail inventoryDetail);

    Map<String, Object> total(@Param("type")Integer type,@Param("goodId") Long goodId,@Param("spotId") Long spotId,@Param("orderNumber") String orderNumber,@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("inStockReason") String inStockReason,@Param("inStockType") String inStockType,@Param("signInState")String signInState);

    int updateSignInStatus(Long id, Integer status);

    List<InventoryDetail> getByOrderNumberN(String orderNumber, String type);

    List<InventoryDetail> listById();

    int updateSys(SysRobotErrorRecordDetaIl sysRobotErrorRecordDetaIl);

    int listByIds(@Param("goodsId") Long goodsId);
}
