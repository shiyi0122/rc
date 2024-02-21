package com.hna.hka.archive.management.system.service;

import java.util.List;

import com.hna.hka.archive.management.system.model.SysRobotAudioAndVideo;
import com.hna.hka.archive.management.system.model.SysSpotResources;

/**
* @ClassName: SysSemanticResourcesService
* @Description: 景区语义交互资源管理业务层（接口）
* @author 郭凯
* @date 2020年12月30日
* @version V1.0
 */
public interface SysSemanticResourcesService {

	List<SysRobotAudioAndVideo> getSemanticResources();

	int addScenicSpotSemanticResources(Long scenicSpotId, String scenicSpotIds);

	List<SysSpotResources> getSemanticResourcesZtree(Long scenicSpotId);

}
