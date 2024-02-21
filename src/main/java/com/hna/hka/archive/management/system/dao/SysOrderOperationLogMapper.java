package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysOrderOperationLog;
import com.hna.hka.archive.management.system.model.SysOrderOperationLogExcel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysOrderOperationLogMapper {
    int deleteByPrimaryKey(Long operationId);

    int insert(SysOrderOperationLog record);

    int insertSelective(SysOrderOperationLog record);

    SysOrderOperationLog selectByPrimaryKey(Long operationId);

    int updateByPrimaryKeySelective(SysOrderOperationLog record);

    int updateByPrimaryKey(SysOrderOperationLog record);

    /**
     * 订单操作日志列表查询
     * @param search
     * @return
     */
    List<SysOrderOperationLog> getOrderOperationLogList(Map<String, Object> search);

    /**
     * 订单操作日志列表下载
     * @param search
     * @return
     */
    List<SysOrderOperationLogExcel> getOrderOperationLogExcel(Map<String, Object> search);


}