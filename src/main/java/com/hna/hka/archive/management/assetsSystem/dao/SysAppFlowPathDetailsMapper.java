package com.hna.hka.archive.management.assetsSystem.dao;

import com.hna.hka.archive.management.assetsSystem.model.SysAppFlowPath;
import com.hna.hka.archive.management.assetsSystem.model.SysAppFlowPathDetails;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/6/27 17:10
 */
public interface SysAppFlowPathDetailsMapper {

    int insertSelective(SysAppFlowPathDetails sysAppFlowPathDetails);

    int  updateByPrimaryKeySelective(SysAppFlowPathDetails sysAppFlowPathDetails);

    int deleteByFlowPathId(Long flowPathId);

    List<SysAppFlowPathDetails> selectFlowPathIdByList(Long flowPathId,String flowPathType,String type);


    Long selectFlowPathIdByNumber(@Param("flowPathId")Long flowPathId,@Param("type") String type);

    List<SysAppFlowPathDetails> getAppFlowPathIdByPeople(Long id);

    List<SysAppFlowPathDetails> getErrorRecordApprovalResults(@Param("errorRecordsId") String errorRecordsId,@Param("flowPathId") String flowPathId,@Param("type") String type);
}
