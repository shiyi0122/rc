package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.WechatBusinessManagement;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WechatBusinessManagementMapper {
    int deleteByPrimaryKey(Long merchantId);

    int insert(WechatBusinessManagement record);

    int insertSelective(WechatBusinessManagement record);

    WechatBusinessManagement selectByPrimaryKey(Long merchantId);

    int updateByPrimaryKeySelective(WechatBusinessManagement record);

    int updateByPrimaryKey(WechatBusinessManagement record);

    /**
     * 证书列表查询
     * @param wechatBusinessManagement
     * @return
     */
    List<WechatBusinessManagement> getManagementList(WechatBusinessManagement wechatBusinessManagement);

    WechatBusinessManagement getBusinessManagementByScenicSpotId(Long orderScenicSpotId);

    WechatBusinessManagement selectWechatBusinessManagementByName(String filename);
}