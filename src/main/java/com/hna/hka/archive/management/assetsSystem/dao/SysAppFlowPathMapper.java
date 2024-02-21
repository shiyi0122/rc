package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.SysAppFlowPath;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/6/27 16:51
 */
public interface SysAppFlowPathMapper {

    List<SysAppFlowPath> getSysAppFlowPathList(Map<String, Object> search);

    int insertSelective(SysAppFlowPath sysAppFlowPath);


    int updateByPrimaryKeySelective(SysAppFlowPath sysAppFlowPath);

    int deleteByPrimaryKey(Long id);

    List<SysAppFlowPath> getSysAppFlowPathDrop(@Param("scenicSpotId") String scenicSpotId);



}
