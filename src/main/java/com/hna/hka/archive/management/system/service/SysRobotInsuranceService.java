package com.hna.hka.archive.management.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hna.hka.archive.management.system.model.SysRobotInsurance;
import com.hna.hka.archive.management.system.util.PageDataResult;

/**
* @ClassName: SysRobotInsuranceService
* @Description: 机器人保险管理业务层（接口）
* @author 郭凯
* @date 2020年12月23日
* @version V1.0
 */
public interface SysRobotInsuranceService {

	PageDataResult getRobotInsuranceList(Integer pageNum, Integer pageSize, Map<String, String> search);

	int addRobotInsurance(MultipartFile file, SysRobotInsurance sysRobotInsurance);

	int updateRobotInsurance(MultipartFile file, SysRobotInsurance sysRobotInsurance);

	SysRobotInsurance getRobotInsuranceById(Long robotId);

	int insertRobotInsurance(SysRobotInsurance sysRobotInsurance);

	int uploadPdf(MultipartFile file, String ids);

	int updateRobotInsurance(SysRobotInsurance sysRobotInsurance);

	List<SysRobotInsurance> uploadRobotInsuranceExcel(Map<String, String> search);

}
