package com.hna.hka.archive.management.business.dao;


import com.hna.hka.archive.management.business.model.BusinessChartDataRecord;

import java.util.List;
import java.util.Map;

public interface BusinessChartDataRecordMapper {
    int deleteByPrimaryKey(Long recordId);

    int insert(BusinessChartDataRecord record);

    int insertSelective(BusinessChartDataRecord record);

    int insertSelectiveNew(BusinessChartDataRecord record);

    BusinessChartDataRecord selectByPrimaryKey(Long recordId);

    int updateByPrimaryKeySelective(BusinessChartDataRecord record);

    int updateByPrimaryKey(BusinessChartDataRecord record);

    /**
     * 获取当前景区在线订单数量
     * @param search
     * @return
     */
    List<BusinessChartDataRecord> getOnlineOrder(Map<String, String> search);

    /**
     * 获取当前景区在线订单数量
     * @param search
     * @return
     */
    List<BusinessChartDataRecord> getOnlineOrderNew(Map<String, String> search);

    /**
     * 根据用户查询用户景区
     * @param search
     * @return
     */
    List<BusinessChartDataRecord> getOnlineOrderByUserId(Map<String, String> search);
    /**
     * 根据用户查询用户景区
     * @param search
     * @return
     */
    List<BusinessChartDataRecord> getOnlineOrderByUserIdNew(Map<String, String> search);


    /**
     * 根据配置时间获取饱和度列表
     * @param saturationTime
     * @param scenicSpotId
     * @param crutDate
     * @return
     */
    List<BusinessChartDataRecord> getTimeByRecords(String saturationTime, Long scenicSpotId, String crutDate);

    /**
     * 根据景区获取最新的一天时间数据
     * @param scenicSpotFid
     * @return
     */
    BusinessChartDataRecord getSpotIdByRecordList(Long scenicSpotFid);

}
