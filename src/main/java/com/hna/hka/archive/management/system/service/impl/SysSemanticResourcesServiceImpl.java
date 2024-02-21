package com.hna.hka.archive.management.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hna.hka.archive.management.system.dao.SysRobotAudioAndVideoMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotCustomTypeMapper;
import com.hna.hka.archive.management.system.dao.SysSpotResourcesMapper;
import com.hna.hka.archive.management.system.model.SysRobotAudioAndVideo;
import com.hna.hka.archive.management.system.model.SysScenicSpotCustomType;
import com.hna.hka.archive.management.system.model.SysSpotResources;
import com.hna.hka.archive.management.system.service.SysSemanticResourcesService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.ToolUtil;
import org.springframework.transaction.annotation.Transactional;

/**
* @ClassName: SysSemanticResourcesServiceImpl
* @Description: 景区语义交互资源管理业务层（实现）
* @author 郭凯
* @date 2020年12月30日
* @version V1.0
 */
@Service
@Transactional
public class SysSemanticResourcesServiceImpl implements SysSemanticResourcesService {
	
	@Autowired
	private SysRobotAudioAndVideoMapper sysRobotAudioAndVideoMapper;
	
	@Autowired
	private SysScenicSpotCustomTypeMapper sysScenicSpotCustomTypeMapper;
	
	@Autowired
	private SysSpotResourcesMapper sysSpotResourcesMapper;

	/**
	* @Author 郭凯
	* @Description: 回显zTree树 数据查询
	* @Title: getSemanticResources
	* @date  2021年1月6日 下午1:16:30 
	* @param @return
	* @throws
	 */
	@Override
	public List<SysRobotAudioAndVideo> getSemanticResources() {
		// TODO Auto-generated method stub
		List<SysRobotAudioAndVideo> semanticResources = sysRobotAudioAndVideoMapper.getSemanticResources();
		List<SysScenicSpotCustomType> customTypes = sysScenicSpotCustomTypeMapper.getCueToneTypeByResId();
		for(SysScenicSpotCustomType customType : customTypes) {
			SysRobotAudioAndVideo audioAndVideo = new SysRobotAudioAndVideo();
			audioAndVideo.setMediaId(Long.parseLong(customType.getTypeNameValue()));
			audioAndVideo.setMediaName(customType.getTypeName());
			semanticResources.add(audioAndVideo);
		}
		return semanticResources;
	}

	/**
	* @Author 郭凯
	* @Description:景区分配资源组
	* @Title: addScenicSpotSemanticResources
	* @date  2021年1月6日 下午1:18:02 
	* @param @param scenicSpotId
	* @param @param scenicSpotIds
	* @param @return
	* @throws
	 */
	@Override
	public int addScenicSpotSemanticResources(Long scenicSpotId, String scenicSpotIds) {
		// TODO Auto-generated method stub
		List<SysSpotResources> list = sysSpotResourcesMapper.getSpotResourcesByScenicSpotId(scenicSpotId);
		if (list.size() > 0) {
			for(SysSpotResources resources : list) {
				sysSpotResourcesMapper.deleteByPrimaryKey(resources.getSpotResourcesId());
			}
		}
		if (ToolUtil.isNotEmpty(scenicSpotIds)) {
			String substring = scenicSpotIds.substring(0, scenicSpotIds.length());
	        String[] split = substring.split(",");//以逗号分割
	        for (String resourcesId : split) {
	        	SysSpotResources spotResources = new SysSpotResources();
	        	spotResources.setSpotResourcesId(IdUtils.getSeqId());
	        	spotResources.setScenicSpotId(scenicSpotId);
	        	spotResources.setResourcesId(Long.parseLong(resourcesId));
	        	spotResources.setCreateDate(DateUtil.currentDateTime());
	        	sysSpotResourcesMapper.insertSelective(spotResources);
	        }
		}
		return 1;
	}

	/**
	* @Author 郭凯
	* @Description: 查询zTree树回显数据
	* @Title: getSemanticResourcesZtree
	* @date  2021年1月6日 下午2:10:43 
	* @param @param scenicSpotId
	* @param @return
	* @throws
	 */
	@Override
	public List<SysSpotResources> getSemanticResourcesZtree(Long scenicSpotId) {
		// TODO Auto-generated method stub
		return sysSpotResourcesMapper.getSpotResourcesByScenicSpotId(scenicSpotId);
	}

}
