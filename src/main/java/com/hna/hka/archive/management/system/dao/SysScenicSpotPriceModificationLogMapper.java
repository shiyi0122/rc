package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotPriceModificationLog;
import com.hna.hka.archive.management.system.model.SysScenicSpotPriceModificationLogVo;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysScenicSpotPriceModificationLogMapper {
    int deleteByPrimaryKey(Long priceModificationId);

    int insert(SysScenicSpotPriceModificationLog record);

    int insertSelective(SysScenicSpotPriceModificationLog record);

    SysScenicSpotPriceModificationLog selectByPrimaryKey(Long priceModificationId);

    int updateByPrimaryKeySelective(SysScenicSpotPriceModificationLog record);

    int updateByPrimaryKey(SysScenicSpotPriceModificationLog record);

    /**
     * 景区操作日志列表查询
     * @param search
     * @return
     */
    List<SysScenicSpotPriceModificationLog> getPriceModificationLogList(Map<String, Object> search);

	List<SysScenicSpotPriceModificationLogVo> getSysScenicSpotPriceModificationLogVoExcel(Map<String, Object> search);
}