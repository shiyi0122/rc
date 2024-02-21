package com.hna.hka.archive.management.assetsSystem.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.model.SysRobotAccessoriesApplicationDetail;
import com.hna.hka.archive.management.appSystem.model.SysRobotErrorRecordDetaIl;
import com.hna.hka.archive.management.assetsSystem.model.Address;
import com.hna.hka.archive.management.assetsSystem.model.GoodsStock;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotAccessoriesApplication;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotPartsManagement;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service
 * @ClassName: SysRobotAccessoriesApplicationService
 * @Author: 郭凯
 * @Description: 配件申请业务层（接口）
 * @Date: 2021/6/16 11:21
 * @Version: 1.0
 */
public interface SysRobotAccessoriesApplicationService {

    int addRobotAccessoriesApplication(SysRobotAccessoriesApplication sysRobotAccessoriesApplication);

    PageInfo<SysRobotAccessoriesApplication> getRobotAccessoriesApplicationList(BaseQueryVo baseQueryVo);

    int editApproval(SysRobotAccessoriesApplication sysRobotAccessoriesApplication);

    PageDataResult getRobotAccessoriesApplicationVoList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    List<SysRobotAccessoriesApplication> getRobotAccessoriesApplicationExcel(Map<String, Object> search);

    int editRobotAccessoriesApplication(SysRobotAccessoriesApplication sysRobotAccessoriesApplication);

    //新添加
    List<SysRobotPartsManagement> robotAccessoriesManagementApplication(String  accessoryName,String accessoriesCode);

    SysRobotAccessoriesApplication accessoriesDetails(String accessoriesApplicationId,String scenicSpotName);

    int  update(Map<String, Object> dataMap);

    List<Address> selectWarehouse(Map<String, Object> dataMap);

    /**
     * 根据配件申请获取配件仓库
     * @param accessoriesApplicationId
     * @return
     */
    List<GoodsStock> getGoodsStock(String accessoriesApplicationId);

    /**
     * 添加快递单号
     * @param dataMap
     * @return
     */
    int updateExPressfee(Map<String, Object> dataMap);


    /**
     * 根据景区id，获取收货地址
     * @param spotId
     * @return
     */
    Address getSpotIdByAddress(String spotId,String type);

    /**
     * 根据配件id，获取配件详情中的配件列表
     * @param accessoriesApplicationId
     * @return
     */
    List<SysRobotAccessoriesApplicationDetail> getApplicationIdByDetailList(String accessoriesApplicationId);

    /**
     * 多配件发货
     * @param sysRobotAccessoriesApplication
     * @return
     */
    int updateMany(SysRobotAccessoriesApplication sysRobotAccessoriesApplication);

    /**
     * 多配件添加快递单号
     * @param sysRobotAccessoriesApplication
     * @return
     */
    int updateExPressfeeMany(SysRobotAccessoriesApplication sysRobotAccessoriesApplication);


    /**
     * 修改为已确认收货(多配件)
     * @param sysRobotAccessoriesApplication
     * @return
     */
    int editApprovalMany(SysRobotAccessoriesApplication sysRobotAccessoriesApplication);

    /**
     * 根据配件id获取有此配件的仓库
     * @param managementId
     * num
     * @return
     */
    List<GoodsStock> getManagementByGoodsStock(String managementId,Double num);

    /**
     * 获取 本景区的发货单
     *
     * @param userId
     * @return
     */
    List<SysRobotAccessoriesApplicationDetail> getSpotIdSendOutGoodsList(Long userId,String scenicSpotId,String isSendOutGoods);

    /**
     * 修改配件申请发货单
     * @param sysRobotAccessoriesApplicationDetail
     * @return
     */
    int editApplicationDetail(SysRobotAccessoriesApplicationDetail sysRobotAccessoriesApplicationDetail);

    /**
     * 发货单签收
     * @param id
     * @param signInPicture
     * @return
     */
    int signForDelivery(String id, String signInPicture);

    /**
     * 管理后台查询配件详情
     * @param accessoriesApplicationId
     * @return
     */
    List<SysRobotAccessoriesApplicationDetail> robotAccessoriesIdByApplication(String accessoriesApplicationId);


}
