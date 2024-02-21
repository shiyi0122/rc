package com.hna.hka.archive.management.system.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hna.hka.archive.management.system.model.SysConfigs;
import com.hna.hka.archive.management.system.service.SysConfigsService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;

/**
 * 
* @ClassName: ConfigsController
* @Description: 用户协议管理控制层 
* @author 郭凯
* @date 2020年12月29日
* @version V1.0
 */
@RequestMapping("/system/configs")
@Controller
public class ConfigsController extends PublicUtil{
	
	@Autowired
	private SysConfigsService sysConfigsService;
	
	/**
	* @Author 郭凯
	* @Description: 用户协议管理列表查询
	* @Title: getConfigsList
	* @date  2020年12月29日 上午11:33:30 
	* @param @param pageNum
	* @param @param pageSize
	* @param @param sysConfigs
	* @param @return
	* @return PageDataResult
	* @throws
	 */
	@RequestMapping("/getConfigsList")
    @ResponseBody
    public PageDataResult getConfigsList(@RequestParam("pageNum") Integer pageNum,
                                             @RequestParam("pageSize") Integer pageSize, SysConfigs sysConfigs) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysConfigsService.getConfigsList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("用户协议管理列表查询失败",e);
        }
        return pageDataResult;
    }
	
	/**
	* @Author 郭凯
	* @Description: 用户协议新增
	* @Title: addConfigs
	* @date  2020年12月29日 下午1:07:23 
	* @param @param sysConfigs
	* @param @return
	* @return ReturnModel
	* @throws
	 */
	@RequestMapping("/addConfigs")
    @ResponseBody
    public ReturnModel addConfigs(SysConfigs sysConfigs) {
		ReturnModel returnModel = new ReturnModel();
		try {
			int i = sysConfigsService.addConfigs(sysConfigs);
			if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("用户协议新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("用户协议新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("addConfigs", e);
			returnModel.setData("");
            returnModel.setMsg("用户协议新增失败！（请联系后台管理人员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
		}
	}
	
	/**
	* @Author 郭凯
	* @Description: 用户协议编辑
	* @Title: editConfigs
	* @date  2020年12月29日 下午1:32:34 
	* @param @param sysConfigs
	* @param @return
	* @return ReturnModel
	* @throws
	 */
	@RequestMapping("/editConfigs")
	@ResponseBody
	public ReturnModel editConfigs(SysConfigs sysConfigs) {
		ReturnModel returnModel = new ReturnModel();
		try {
			int i = sysConfigsService.editConfigs(sysConfigs);
			if (i == 1) {
				returnModel.setData("");
				returnModel.setMsg("用户协议编辑成功！");
				returnModel.setState(Constant.STATE_SUCCESS);
				return returnModel;
			}else{
				returnModel.setData("");
				returnModel.setMsg("用户协议编辑失败！");
				returnModel.setState(Constant.STATE_FAILURE);
				return returnModel;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("editConfigs", e);
			returnModel.setData("");
			returnModel.setMsg("用户协议编辑失败！（请联系后台管理人员）");
			returnModel.setState(Constant.STATE_FAILURE);
			return returnModel;
		}
	}
	
	/**
	* @Author 郭凯
	* @Description: 用户协议删除
	* @Title: delConfigs
	* @date  2020年12月29日 下午1:36:05 
	* @param @param configsId
	* @param @return
	* @return ReturnModel
	* @throws
	 */
	@RequestMapping("/delConfigs")
	@ResponseBody
	public ReturnModel delConfigs(Long configsId) {
		ReturnModel returnModel = new ReturnModel();
		try {
			int i = sysConfigsService.delConfigs(configsId);
			if (i == 1) {
				returnModel.setData("");
				returnModel.setMsg("用户协议删除成功！");
				returnModel.setState(Constant.STATE_SUCCESS);
				return returnModel;
			}else{
				returnModel.setData("");
				returnModel.setMsg("用户协议删除失败！");
				returnModel.setState(Constant.STATE_FAILURE);
				return returnModel;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("delConfigs", e);
			returnModel.setData("");
			returnModel.setMsg("用户协议删除失败！（请联系后台管理人员）");
			returnModel.setState(Constant.STATE_FAILURE);
			return returnModel;
		}
	}

}
