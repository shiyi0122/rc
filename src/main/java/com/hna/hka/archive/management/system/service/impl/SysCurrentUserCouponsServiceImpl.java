package com.hna.hka.archive.management.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysCurrentUserCouponsMapper;
import com.hna.hka.archive.management.system.dao.SysCurrentUserMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotActivityMapper;
import com.hna.hka.archive.management.system.model.SysCurrentUser;
import com.hna.hka.archive.management.system.model.SysCurrentUserCoupons;
import com.hna.hka.archive.management.system.model.SysScenicSpotActivity;
import com.hna.hka.archive.management.system.service.SysCurrentUserCouponsService;
import com.hna.hka.archive.management.system.util.BelongCalendar;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.ToolUtil;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysCurrentUserCouponsServiceImpl implements SysCurrentUserCouponsService {
	
	@Autowired
	private SysCurrentUserMapper sysCurrenUserMapper;
	
	@Autowired
	private SysScenicSpotActivityMapper sysScenicSpotActivityMapper;
	
	@Autowired
	private SysCurrentUserCouponsMapper sysCurrentUserCouponsMapper;

	/**
	 * 
	* @Author 郭凯
	* @Description: 用户分配优惠卷
	* @Title: addCurrentUserCoupons
	* @date  2020年12月22日 下午1:13:54 
	* @param @param sysCurrentUserCoupons
	* @param @return
	* @throws
	 */
	@Override
	public int addCurrentUserCoupons(SysCurrentUserCoupons sysCurrentUserCoupons) {
		// TODO Auto-generated method stub
		//根据手机号查询用户信息
		SysCurrentUser currenUser = sysCurrenUserMapper.getCurrenUserByPhone(sysCurrentUserCoupons.getPhone());
		if (ToolUtil.isEmpty(currenUser)) {
			return 2;
		}
		//获取优惠卷剩余数量
		SysScenicSpotActivity scenicSpotActivity = sysScenicSpotActivityMapper.selectByPrimaryKey(sysCurrentUserCoupons.getActivityId());
		int parseInt = Integer.parseInt(scenicSpotActivity.getNumberCoupons());
		Integer numberCoupons = new Integer(parseInt);
		if (numberCoupons.compareTo(0) == -1 && numberCoupons.compareTo(0) == 0) {
			return 3;
		}
		SysCurrentUserCoupons currentUserCoupons = sysCurrentUserCouponsMapper.getCurrentUserCouponsById(currenUser.getCurrentUserId(),scenicSpotActivity.getActivityId());
		if (ToolUtil.isNotEmpty(currentUserCoupons)) {
			return 4;
		}
		sysCurrentUserCoupons.setUserCouponsId(IdUtils.getSeqId());
		sysCurrentUserCoupons.setUserId(currenUser.getCurrentUserId());
		sysCurrentUserCoupons.setUserCouponsName(scenicSpotActivity.getActivityName());
		sysCurrentUserCoupons.setCouponsScenicSpotId(scenicSpotActivity.getActivityScenicSpotId());
		sysCurrentUserCoupons.setCouponsScenicSpotName(scenicSpotActivity.getActivityScenicSpotName());
		sysCurrentUserCoupons.setCouponsStandard(scenicSpotActivity.getActivityStandard());
		sysCurrentUserCoupons.setCouponsAmount(scenicSpotActivity.getActivityAmount());
		sysCurrentUserCoupons.setCouponsStartTime(scenicSpotActivity.getActivityStartTime());
		sysCurrentUserCoupons.setCouponsEndTime(scenicSpotActivity.getActivityEndTime());
		sysCurrentUserCoupons.setCouponsType(scenicSpotActivity.getActivityType());
		sysCurrentUserCoupons.setCreateDate(DateUtil.currentDateTime());
		sysCurrentUserCoupons.setUpdateDate(DateUtil.currentDateTime());
		int i = sysCurrentUserCouponsMapper.insertSelective(sysCurrentUserCoupons);
		if (i == 1) {
			int a = Integer.parseInt(scenicSpotActivity.getNumberCoupons()) - 1;
			if (ToolUtil.isNotEmpty(String.valueOf(a))) {
				SysScenicSpotActivity activity = new SysScenicSpotActivity();
				activity.setActivityId(scenicSpotActivity.getActivityId());
				activity.setNumberCoupons(String.valueOf(a));
				activity.setUpdateDate(DateUtil.currentDateTime());
				sysScenicSpotActivityMapper.updateByPrimaryKeySelective(activity);
			}
		}
		return i;
	}

	/**
	 * 
	* @Author 郭凯
	* @Description: 查询用户优惠卷列表
	* @Title: getCurrentUserCouponsList
	* @date  2020年12月22日 下午5:40:17 
	* @param @param pageNum
	* @param @param pageSize
	* @param @param search
	* @param @return
	* @throws
	 */
	@Override
	public PageDataResult getCurrentUserCouponsList(Integer pageNum, Integer pageSize, Map<String, String> search) {
		// TODO Auto-generated method stub
		PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysCurrentUserCoupons> currentUserCouponsList = sysCurrentUserCouponsMapper.getCurrentUserCouponsList(search);
        for(SysCurrentUserCoupons currentUserCoupons : currentUserCouponsList) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
			Date now = null;
			Date beginTime = null;
			Date endTime = null;
			try {
				now = new Date();
				beginTime = df.parse(currentUserCoupons.getCouponsStartTime());
				endTime = df.parse(currentUserCoupons.getCouponsEndTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
			Boolean flag = BelongCalendar.belongCalendar(now, beginTime, endTime);
			if (flag) {
				currentUserCoupons.setTermOfValidity("正常");
			}else {
				currentUserCoupons.setTermOfValidity("已过期");
			}
		}
        if(currentUserCouponsList.size() != 0){
            PageInfo<SysCurrentUserCoupons> pageInfo = new PageInfo<>(currentUserCouponsList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
	}

	/**
	 * 
	* @Author 郭凯
	* @Description: 删除用户优惠卷
	* @Title: delCurrentUserCoupons
	* @date  2020年12月23日 上午9:10:18 
	* @param @param userCouponsId
	* @param @return
	* @throws
	 */
	@Override
	public int delCurrentUserCoupons(Long userCouponsId) {
		// TODO Auto-generated method stub
		return sysCurrentUserCouponsMapper.deleteByPrimaryKey(userCouponsId);
	}

	@Override
	public int insertCurrentUserCoupons(SysCurrentUserCoupons sysCurrentUserCoupons) {

		int i = sysCurrentUserCouponsMapper.insertSelective(sysCurrentUserCoupons);

		return i;
	}

}
