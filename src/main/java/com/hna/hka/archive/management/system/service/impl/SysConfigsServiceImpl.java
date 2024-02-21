package com.hna.hka.archive.management.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysConfigsMapper;
import com.hna.hka.archive.management.system.model.SysConfigs;
import com.hna.hka.archive.management.system.service.SysConfigsService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
* @ClassName: SysConfigsServiceImpl
* @Description: 用户协议管理业务层（实现）
* @author 郭凯
* @date 2020年12月29日
* @version V1.0
 */
@Service
@Transactional
public class SysConfigsServiceImpl implements SysConfigsService {
	
	@Autowired
	private SysConfigsMapper sysConfigsMapper;

	/**
	* @Author 郭凯
	* @Description: 用户协议管理列表查询
	* @Title: getConfigsList
	* @date  2020年12月29日 上午11:34:07 
	* @param @param pageNum
	* @param @param pageSize
	* @param @param search
	* @param @return
	* @throws
	 */
	@Override
	public PageDataResult getConfigsList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
		// TODO Auto-generated method stub
		PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysConfigs> configsList = sysConfigsMapper.getConfigsList(search);
        if (configsList.size() > 0){
            PageInfo<SysConfigs> pageInfo = new PageInfo<>(configsList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
		return pageDataResult;
	}

	/**
	* @Author 郭凯
	* @Description: 用户协议新增
	* @Title: addConfigs
	* @date  2020年12月29日 下午1:08:37 
	* @param @param sysConfigs
	* @param @return
	* @throws
	 */
	@Override
	public int addConfigs(SysConfigs sysConfigs) {
		// TODO Auto-generated method stub
		sysConfigs.setConfigsId(IdUtils.getSeqId());
		sysConfigs.setCreateTime(DateUtil.currentDateTime());
		sysConfigs.setUpdateTime(DateUtil.currentDateTime());
		return sysConfigsMapper.insertSelective(sysConfigs);
	}

	/**
	* @Author 郭凯
	* @Description: 用户协议编辑
	* @Title: editConfigs
	* @date  2020年12月29日 下午1:33:14 
	* @param @param sysConfigs
	* @param @return
	* @throws
	 */
	@Override
	public int editConfigs(SysConfigs sysConfigs) {
		// TODO Auto-generated method stub
		sysConfigs.setUpdateTime(DateUtil.currentDateTime());
		return sysConfigsMapper.updateByPrimaryKeySelective(sysConfigs);
	}

	/**
	* @Author 郭凯
	* @Description: 用户协议删除
	* @Title: delConfigs
	* @date  2020年12月29日 下午1:37:29 
	* @param @param configsId
	* @param @return
	* @throws
	 */
	@Override
	public int delConfigs(Long configsId) {
		// TODO Auto-generated method stub
		return sysConfigsMapper.deleteByPrimaryKey(configsId);
	}

}
