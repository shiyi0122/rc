package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysScenicSpotImg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysScenicSpotImgMapper {
    int deleteByPrimaryKey(Long scneicSpotImgId);

    int insert(SysScenicSpotImg record);

    int insertSelective(SysScenicSpotImg record);

    SysScenicSpotImg selectByPrimaryKey(Long scneicSpotImgId);

    int updateByPrimaryKeySelective(SysScenicSpotImg record);

    int updateByPrimaryKey(SysScenicSpotImg record);

    /**
     * 根据用户ID查询景区名称和图片
     * @param search
     * @return
     */
    List<SysScenicSpotImg> getScenicSpotImgByUserId(Map<String, String> search);

    SysScenicSpotImg getScenicSpotImgByScenicSpotId(Long scenicSpotId);
}