package com.hna.hka.archive.management.system.dao;

import java.util.List;
import java.util.Map;

import com.hna.hka.archive.management.system.model.SysRobotInsurance;

public interface SysRobotInsuranceMapper {
    int deleteByPrimaryKey(Long insuranceId);

    int insert(SysRobotInsurance record);

    int insertSelective(SysRobotInsurance record);

    SysRobotInsurance selectByPrimaryKey(Long insuranceId);

    int updateByPrimaryKeySelective(SysRobotInsurance record);

    int updateByPrimaryKey(SysRobotInsurance record);

	List<SysRobotInsurance> getRobotInsuranceList(Map<String, String> search);

	SysRobotInsurance getRobotInsuranceByRobotId(Long robotId);

	//导入机器人保险表
    List<SysRobotInsurance> uploadRobotInsuranceExcel(Map<String, String> search);
}