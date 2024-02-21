package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotCertificateSpot;
import com.hna.hka.archive.management.system.model.WechatBusinessManagement;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysScenicSpotCertificateSpotMapper {
    int deleteByPrimaryKey(Long certificateSpotId);

    int insert(SysScenicSpotCertificateSpot record);

    int insertSelective(SysScenicSpotCertificateSpot record);

    SysScenicSpotCertificateSpot selectByPrimaryKey(Long certificateSpotId);

    int updateByPrimaryKeySelective(SysScenicSpotCertificateSpot record);

    int updateByPrimaryKey(SysScenicSpotCertificateSpot record);

    /**
     * 景区证书列表查询
     * @param search
     * @return
     */
    List<SysScenicSpotCertificateSpot> getCertificateSpotList(Map<String, Object> search);

    /**
     * 查询此景区是否存在证书
     * @param scenicSpotId
     * @return
     */
    SysScenicSpotCertificateSpot selectByScenicSpotId(Long scenicSpotId);

    int  deleteBySpotId(Long scenicSpotId);

    WechatBusinessManagement selectById(Map<String, Object> search);
}