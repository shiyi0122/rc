package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.WechatSysDepositLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface WechatSysDepositLogMapper {
    int deleteByPrimaryKey(Long depositLogId);

    int insert(WechatSysDepositLog record);

    int insertSelective(WechatSysDepositLog record);

    WechatSysDepositLog selectByPrimaryKey(Long depositLogId);

    int updateByPrimaryKeySelective(WechatSysDepositLog record);

    int updateByPrimaryKey(WechatSysDepositLog record);

    /**
     * 押金退款日志列表查询
     * @param search
     * @return
     */
    List<WechatSysDepositLog> getSysDepositLogList(Map<String, Object> search);
}