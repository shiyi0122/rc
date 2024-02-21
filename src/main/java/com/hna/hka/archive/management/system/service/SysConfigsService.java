package com.hna.hka.archive.management.system.service;

import java.util.Map;

import com.hna.hka.archive.management.system.model.SysConfigs;
import com.hna.hka.archive.management.system.util.PageDataResult;

/**
 * 
* @ClassName: SysConfigsService
* @Description: 用户协议管理业务层（接口）
* @author 郭凯
* @date 2020年12月29日
* @version V1.0
 */
public interface SysConfigsService {

	PageDataResult getConfigsList(Integer pageNum, Integer pageSize, Map<String, Object> search);

	int addConfigs(SysConfigs sysConfigs);

	int editConfigs(SysConfigs sysConfigs);

	int delConfigs(Long configsId);

}
