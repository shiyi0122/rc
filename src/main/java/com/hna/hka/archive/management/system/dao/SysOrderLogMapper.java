package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.ExternalData.RefundOrderList;
import com.hna.hka.archive.management.system.model.SysOrderLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysOrderLogMapper {
    int deleteByPrimaryKey(Long orderLogId);

    int insert(SysOrderLog record);

    int insertSelective(SysOrderLog record);

    SysOrderLog selectByPrimaryKey(Long orderLogId);

    int updateByPrimaryKeySelective(SysOrderLog record);

    int updateByPrimaryKey(SysOrderLog record);

    /**
     * 订单退款日志列表查询
     * @param search
     * @return
     */
    List<SysOrderLog> getOperationLogRefundOrderList(Map<String, Object> search);

    SysOrderLog selectByOrderLogNumber(String OrderLogNumber);

    List<RefundOrderList> getOrderNumberLogList(String orderNumber);

}