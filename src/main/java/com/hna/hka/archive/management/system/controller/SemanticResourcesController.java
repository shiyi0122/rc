package com.hna.hka.archive.management.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hna.hka.archive.management.system.model.SysRobotAudioAndVideo;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.model.SysSpotResources;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.service.SysScenicSpotService;
import com.hna.hka.archive.management.system.service.SysSemanticResourcesService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;

/**
 * 
* @ClassName: SemanticResourcesController
* @Description: 景区语义交互资源管理控制层
* @author 郭凯
* @date 2020年12月30日
* @version V1.0
 */
@RequestMapping("/system/semanticResources")
@Controller
public class SemanticResourcesController extends PublicUtil{
	
	@Autowired
	private SysScenicSpotService sysScenicSpotService;
	
	@Autowired
	private SysSemanticResourcesService sysSemanticResourcesService;
	
	/**
	* @Author 郭凯
	* @Description: 景区语义交互资源管理列表查询
	* @Title: getSemanticResourcesList
	* @date  2020年12月30日 上午10:22:53 
	* @param @param pageNum
	* @param @param pageSize
	* @param @param sysScenicSpot
	* @param @return
	* @return PageDataResult
	* @throws
	 */
	@RequestMapping("/getSemanticResourcesList")
    @ResponseBody
    public PageDataResult getSemanticResourcesList(@RequestParam("pageNum") Integer pageNum,
                                             @RequestParam("pageSize") Integer pageSize, SysScenicSpot sysScenicSpot) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        SysUsers user = this.getSysUser();
        search.put("userId",user.getUserId().toString());
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysScenicSpotService.getSemanticResourcesList(pageNum, pageSize, search);
        }catch (Exception e){
            logger.info("景区语义交互资源管理列表查询失败",e);
        }
        return pageDataResult;
    }
	
	/**
	* @Author 郭凯
	* @Description:回显zTree树 
	* @Title: getSemanticResources
	* @date  2021年1月6日 下午12:53:49 
	* @param @return
	* @return ReturnModel
	* @throws
	 */
	@RequestMapping("/getSemanticResources")
	@ResponseBody
	public ReturnModel getSemanticResources() {
		ReturnModel dataModel = new ReturnModel();
		try {
			List<SysRobotAudioAndVideo> audioAndVideoList = sysSemanticResourcesService.getSemanticResources();
			dataModel.setData(audioAndVideoList);
			dataModel.setMsg("查询成功！");
			dataModel.setState(Constant.STATE_SUCCESS);
            return dataModel;
		} catch (Exception e) {
			// TODO: handle exception
			dataModel.setData("");
			dataModel.setMsg("查询失败！");
			dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
		}
	}
	
	/**
	* @Author 郭凯
	* @Description:景区分配资源 
	* @Title: addScenicSpotSemanticResources
	* @date  2021年1月6日 下午2:04:47 
	* @param @param scenicSpotId
	* @param @param scenicSpotIds
	* @param @return
	* @return ReturnModel
	* @throws
	 */
	@RequestMapping("/addScenicSpotSemanticResources")
	@ResponseBody
	public ReturnModel addScenicSpotSemanticResources(Long scenicSpotId,String scenicSpotIds) {
		ReturnModel dataModel = new ReturnModel();
		try {
			int i = sysSemanticResourcesService.addScenicSpotSemanticResources(scenicSpotId,scenicSpotIds);
			if (i == 1) {
				dataModel.setData("");
				dataModel.setMsg("新增成功！");
				dataModel.setState(Constant.STATE_SUCCESS);
	            return dataModel;
			}else {
				dataModel.setData("");
				dataModel.setMsg("新增失败！");
				dataModel.setState(Constant.STATE_FAILURE);
	            return dataModel;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("addScenicSpotSemanticResources", e);
			dataModel.setData("");
			dataModel.setMsg("新增失败！");
			dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
		}
	}
	
	/**
	* @Author 郭凯
	* @Description: 查询zTree树回显数据
	* @Title: getSemanticResourcesZtree
	* @date  2021年1月6日 下午2:19:24 
	* @param @param scenicSpotId
	* @param @return
	* @return ReturnModel
	* @throws
	 */
	@RequestMapping("/getSemanticResourcesZtree")
	@ResponseBody
	public ReturnModel getSemanticResourcesZtree(Long scenicSpotId) {
		ReturnModel dataModel = new ReturnModel();
		try {
			List<SysSpotResources> resourcesList = sysSemanticResourcesService.getSemanticResourcesZtree(scenicSpotId);
			dataModel.setData(resourcesList);
			dataModel.setMsg("查询成功！");
			dataModel.setState(Constant.STATE_SUCCESS);
			return dataModel;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("getSemanticResourcesZtree", e);
			dataModel.setData("");
			dataModel.setMsg("查询失败！");
			dataModel.setState(Constant.STATE_FAILURE);
			return dataModel;
		}
	}

}
