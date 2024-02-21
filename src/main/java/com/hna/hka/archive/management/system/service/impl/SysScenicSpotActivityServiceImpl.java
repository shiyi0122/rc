package com.hna.hka.archive.management.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysScenicSpotActivityMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.model.SysScenicSpotActivity;
import com.hna.hka.archive.management.system.service.SysScenicSpotActivityService;
import com.hna.hka.archive.management.system.util.BelongCalendar;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.ToolUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataUnit;

@Service
@Transactional
public class SysScenicSpotActivityServiceImpl implements SysScenicSpotActivityService{
	
	@Autowired
	private SysScenicSpotActivityMapper sysScenicSpotActivityMapper;
	
	@Autowired
	private SysScenicSpotMapper sysScenicSpotMapper;

	
	/**
	 * 
	* @Author 郭凯
	* @Description: 优惠政策列表查询
	* @Title: getScenicSpotActivityList
	* @date  2020年12月21日 下午3:19:22 
	* @param @param pageNum
	* @param @param pageSize
	* @param @param search
	* @param @return
	* @throws
	 */
	@Override
	public PageDataResult getScenicSpotActivityList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
		// TODO Auto-generated method stub
		PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotActivity> scenicSpotActivityList = sysScenicSpotActivityMapper.getScenicSpotActivityList(search);
        for(SysScenicSpotActivity spotActivity : scenicSpotActivityList) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
			Date now = null;
			Date beginTime = null;
			Date endTime = null;
			try {
				now = new Date();
				beginTime = df.parse(spotActivity.getActivityStartTime());
				endTime = df.parse(spotActivity.getActivityEndTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
			Boolean flag = BelongCalendar.belongCalendar(now, beginTime, endTime);
			if (flag) {
				spotActivity.setTermOfValidity("正常");
			}else {
				spotActivity.setTermOfValidity("已过期");
			}
		}
        if(scenicSpotActivityList.size() != 0){
            PageInfo<SysScenicSpotActivity> pageInfo = new PageInfo<>(scenicSpotActivityList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
	}

	/**
	 * 
	* @Author 郭凯
	* @Description: 优惠政策新增
	* @Title: addScenicSpotActivity
	* @date  2020年12月21日 下午5:44:43 
	* @param @param sysScenicSpotActivity
	* @param @return
	* @throws
	 */
	@Override
	public int addScenicSpotActivity(SysScenicSpotActivity sysScenicSpotActivity) {
		// TODO Auto-generated method stub
		SysScenicSpot scenicSpot = sysScenicSpotMapper.selectByPrimaryKey(sysScenicSpotActivity.getActivityScenicSpotId());
		sysScenicSpotActivity.setActivityScenicSpotName(scenicSpot.getScenicSpotName());
		sysScenicSpotActivity.setActivityId(IdUtils.getSeqId());
		sysScenicSpotActivity.setClaimConditions(sysScenicSpotActivity.getActivityStandard());
		sysScenicSpotActivity.setCreateDate(DateUtil.currentDateTime());
		sysScenicSpotActivity.setUpdateDate(DateUtil.currentDateTime());
		if ("1".equals(sysScenicSpotActivity.getActivityType())) {
			SysScenicSpotActivity activity = sysScenicSpotActivityMapper.getScenicSpotActivityByType(sysScenicSpotActivity.getActivityType());
			if (ToolUtil.isNotEmpty(activity)) {
				return 2;
			}
		}else if ("3".equals(sysScenicSpotActivity.getActivityType())) {
			if (ToolUtil.isEmpty(sysScenicSpotActivity.getClaimConditions())) {
				return 3;
			}
		}


		return sysScenicSpotActivityMapper.insertSelective(sysScenicSpotActivity);
	}

	/**
	 * 
	* @Author 郭凯
	* @Description: 优惠政策删除
	* @Title: delScenicSpotActivity
	* @date  2020年12月21日 下午5:57:57 
	* @param @param activityId
	* @param @return
	* @throws
	 */
	@Override
	public int delScenicSpotActivity(Long activityId) {
		// TODO Auto-generated method stub
		return sysScenicSpotActivityMapper.deleteByPrimaryKey(activityId);
	}

	/**
	 * 
	* @Author 郭凯
	* @Description: 查询优惠卷列表
	* @Title: getScenicSpotActivity
	* @date  2020年12月22日 下午5:30:48 
	* @param @return
	* @throws
	 */
	@Override
	public List<SysScenicSpotActivity> getScenicSpotActivity() {
		// TODO Auto-generated method stub
		Map<String, Object> search = new HashMap<>();
		List<SysScenicSpotActivity> scenicSpotActivityList = sysScenicSpotActivityMapper.getScenicSpotActivityList(search);
		return scenicSpotActivityList;
	}

	/**
	 * 修改政策启用禁用
	 * @param sysScenicSpotActivity
	 * @return
	 */
	@Override
	public int getActivityFailure(SysScenicSpotActivity sysScenicSpotActivity) {

		SysScenicSpotActivity sysScenicSpotActivityN = sysScenicSpotActivityMapper.selectByPrimaryKey(sysScenicSpotActivity.getActivityId());

		sysScenicSpotActivityN.setActivityFailure(sysScenicSpotActivity.getActivityFailure());

		sysScenicSpotActivityN.setUpdateDate(DateUtil.currentDateTime());
		int i = sysScenicSpotActivityMapper.updateByPrimaryKeySelective(sysScenicSpotActivityN);

		return i;

	}

	@Override
	public int editScenicSpotActivity(SysScenicSpotActivity sysScenicSpotActivity) {

		sysScenicSpotActivity.setUpdateDate(DateUtil.currentDateTime());
		sysScenicSpotActivity.setClaimConditions(sysScenicSpotActivity.getActivityStandard());
		int i = sysScenicSpotActivityMapper.updateByPrimaryKeySelective(sysScenicSpotActivity);
		return i;
	}

	/**
	 * 每晚定时查询修改优惠是否过期状态
	 */
	@Override
	public void timingEditActivityFailure() {
		Map<String, Object> search = new HashMap<>();

		List<SysScenicSpotActivity> list = sysScenicSpotActivityMapper.getScenicSpotActivityList(search);
		try {
			for (SysScenicSpotActivity spotActivity : list) {

				if ("1".equals(spotActivity.getActivityUseType())) { //工作日
					boolean weekend = DateUtil.getWeekend();
					if (weekend == false) {//工作日

						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
						Date now = null;
						Date beginTime = null;
						Date endTime = null;
						Date date = new Date();
						String s = df.format(date);
						now = df.parse(s);
						beginTime = df.parse(spotActivity.getActivityStartTime());
						endTime = df.parse(spotActivity.getActivityEndTime());
						Boolean flag = BelongCalendar.belongCalendar(now, beginTime, endTime);
						if (flag) {
							spotActivity.setActivityFailure("1");
						} else {
							spotActivity.setActivityFailure("0");
						}
						sysScenicSpotActivityMapper.updateByPrimaryKeySelective(spotActivity);

					} else {//周六日
						spotActivity.setActivityFailure("0");
						sysScenicSpotActivityMapper.updateByPrimaryKeySelective(spotActivity);
					}
				} else if ("2".equals(spotActivity.getActivityUseType())) {//周六日

					boolean weekend = DateUtil.getWeekend();
					if (weekend == true) {//周六日
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
						Date now = null;
						Date beginTime = null;
						Date endTime = null;
						Date date = new Date();
						String s = df.format(date);
						now = df.parse(s);
						beginTime = df.parse(spotActivity.getActivityStartTime());
						endTime = df.parse(spotActivity.getActivityEndTime());
						Boolean flag = BelongCalendar.belongCalendar(now, beginTime, endTime);
						if (flag) {
							spotActivity.setActivityFailure("1");
						} else {
							spotActivity.setActivityFailure("0");
						}
						sysScenicSpotActivityMapper.updateByPrimaryKeySelective(spotActivity);
					} else {//工作日
						spotActivity.setActivityFailure("0");
						sysScenicSpotActivityMapper.updateByPrimaryKeySelective(spotActivity);
					}
				} else if ("3".equals(spotActivity.getActivityUseType())) {//每天

					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
					Date now = null;
					Date beginTime = null;
					Date endTime = null;
					Date date = new Date();
					String s = df.format(date);
					now = df.parse(s);
					beginTime = df.parse(spotActivity.getActivityStartTime());
					endTime = df.parse(spotActivity.getActivityEndTime());
					Boolean flag = BelongCalendar.belongCalendar(now, beginTime, endTime);
					if (flag) {
						spotActivity.setActivityFailure("1");
					} else {
						spotActivity.setActivityFailure("0");
					}
					sysScenicSpotActivityMapper.updateByPrimaryKeySelective(spotActivity);
				}
			 }
			}catch (Exception e){

				e.printStackTrace();
		}

	}

}