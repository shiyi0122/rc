package com.hna.hka.archive.management.system.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hna.hka.archive.management.system.model.SysCurrentUserCoupons;
import com.hna.hka.archive.management.system.service.SysCurrentUserCouponsService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;

/**
 * 
* @ClassName: CurrentUserCouponsController
* @Description: 用户优惠卷控制层 
* @author 郭凯
* @date 2020年12月22日
* @version V1.0
 */

@RequestMapping("/system/currentUserCoupons")
@Controller
public class CurrentUserCouponsController extends PublicUtil{
	
	@Autowired
	private SysCurrentUserCouponsService sysCurrentUserCouponsService;
	
	/**
	* @Author 郭凯
	* @Description: 用户分配优惠卷
	* @Title: addCurrentUserCoupons
	* @date  2020年12月22日 下午1:12:37 
	* @param @param sysCurrentUserCoupons
	* @param @return
	* @return ReturnModel
	* @throws
	 */
	@RequestMapping("/addCurrentUserCoupons")
	@ResponseBody
	public ReturnModel addCurrentUserCoupons(SysCurrentUserCoupons sysCurrentUserCoupons) {
		ReturnModel returnModel = new ReturnModel();
		try {
			int i = sysCurrentUserCouponsService.addCurrentUserCoupons(sysCurrentUserCoupons);
			if (i == 1) {
				returnModel.setData("");
                returnModel.setMsg("优惠卷分配成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
			}else if(i == 2) {
				returnModel.setData("");
                returnModel.setMsg("用户信息获取失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
			}else if (i == 3) {
				returnModel.setData("");
				returnModel.setMsg("优惠卷剩余数量为0！");
				returnModel.setState(Constant.STATE_FAILURE);
            	return returnModel;
			}else if (i == 4) {
				returnModel.setData("");
				returnModel.setMsg("此用户已有此优惠卷！");
				returnModel.setState(Constant.STATE_FAILURE);
	        	return returnModel;
			}else {
				returnModel.setData("");
				returnModel.setMsg("优惠卷分配失败！");
				returnModel.setState(Constant.STATE_FAILURE);
	        	return returnModel;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("addCurrentUserCoupons", e);
			returnModel.setData("");
			returnModel.setMsg("优惠卷分配失败！（请联系管理员）");
			returnModel.setState(Constant.STATE_FAILURE);
        	return returnModel;
		}
	}
	
	/**
	 * 
	* @Author 郭凯
	* @Description: 查询用户优惠卷列表
	* @Title: getCurrentUserCouponsList
	* @date  2020年12月22日 下午5:39:04 
	* @param @param pageNum
	* @param @param pageSize
	* @param @param sysCurrentUserCoupons
	* @param @return
	* @return PageDataResult
	* @throws
	 */
	@RequestMapping("/getCurrentUserCouponsList")
    @ResponseBody
    public PageDataResult getCurrentUserCouponsList(@RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize, SysCurrentUserCoupons sysCurrentUserCoupons) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("phone",sysCurrentUserCoupons.getPhone());
            pageDataResult = sysCurrentUserCouponsService.getCurrentUserCouponsList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("用户优惠卷列表查询失败",e);
        }
        return pageDataResult;
    }
	
	/**
	 * 
	* @Author 郭凯
	* @Description: 删除用户优惠卷
	* @Title: delCurrentUserCoupons
	* @date  2020年12月23日 上午9:09:48 
	* @param @param userCouponsId
	* @param @return
	* @return ReturnModel
	* @throws
	 */
	@RequestMapping("/delCurrentUserCoupons")
	@ResponseBody
	public ReturnModel delCurrentUserCoupons(Long userCouponsId) {
		ReturnModel returnModel = new ReturnModel();
		try {
			int i = sysCurrentUserCouponsService.delCurrentUserCoupons(userCouponsId);
			if (i == 1) {
				returnModel.setData("");
                returnModel.setMsg("用户优惠卷删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
			}else {
				returnModel.setData("");
				returnModel.setMsg("用户优惠卷删除失败！");
				returnModel.setState(Constant.STATE_FAILURE);
	        	return returnModel;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("delCurrentUserCoupons", e);
			returnModel.setData("");
			returnModel.setMsg("用户优惠卷删除失败！（请联系管理员）");
			returnModel.setState(Constant.STATE_FAILURE);
        	return returnModel;
		}
	}

}
