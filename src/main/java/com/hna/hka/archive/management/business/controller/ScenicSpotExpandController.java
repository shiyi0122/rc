package com.hna.hka.archive.management.business.controller;

import com.hna.hka.archive.management.business.model.BusinessScenicSpotArea;
import com.hna.hka.archive.management.business.model.BusinessScenicSpotExpand;
import com.hna.hka.archive.management.business.model.BusinessWorldArea;
import com.hna.hka.archive.management.business.service.BusinessScenicSpotExpandService;
import com.hna.hka.archive.management.system.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.controller
 * @ClassName: ScenicSpotExpandController
 * @Author: 郭凯
 * @Description: 景区拓展管理控制层
 * @Date: 2020/8/6 17:48
 * @Version: 1.0
 */
@RequestMapping("/business/scenicSpotExpand")
@Controller
public class ScenicSpotExpandController extends PublicUtil {

    @Autowired
    private BusinessScenicSpotExpandService businessScenicSpotExpandService;

    @Autowired
    private HttpServletRequest request;

    /**
     * @Author 郭凯
     * @Description 景区拓展管理列表查询
     * @Date 10:11 2020/8/7
     * @Param [pageNum, pageSize, businessScenicSpotExpand]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getScenicSpotExpandList")
    @ResponseBody
    public PageDataResult getScenicSpotExpandList(@RequestParam("pageNum") Integer pageNum,
                                        @RequestParam("pageSize") Integer pageSize, BusinessScenicSpotExpand businessScenicSpotExpand) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("scenicSpotId",businessScenicSpotExpand.getScenicSpotId());
            pageDataResult = businessScenicSpotExpandService.getScenicSpotExpandList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("景区拓展管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 查询地区名称
     * @Date 11:15 2020/8/10
     * @Param [pid]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/getProvince")
    @ResponseBody
    public ReturnModel getProvince(@RequestParam("pid") Long pid) {
        ReturnModel returnModel = new ReturnModel();
        try {
            List<BusinessWorldArea> BusinessWorldArea = businessScenicSpotExpandService.getProvince(pid);
            if (ToolUtil.isNotEmpty(BusinessWorldArea)) {
                returnModel.setData(BusinessWorldArea);
                returnModel.setMsg("成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("查询失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("getProvince", e);
            returnModel.setData("");
            returnModel.setMsg("查询失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * @Author 郭凯
     * @Description 景区拓展信息新增
     * @Date 13:36 2020/8/11
     * @Param [businessScenicSpotExpand]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addScenicSpotExpand")
    @ResponseBody
    public ReturnModel addScenicSpotExpand(BusinessScenicSpotExpand businessScenicSpotExpand) {
        ReturnModel returnModel = new ReturnModel();
        try {
            String province = request.getParameter("province");
            String city = request.getParameter("city");
            String county = request.getParameter("county");
            BusinessScenicSpotArea businessScenicSpotArea = new BusinessScenicSpotArea();
            String mergerName= "";
            if (ToolUtil.isNotEmpty(province)) {
                businessScenicSpotArea.setProvinceId(Integer.parseInt(province));
                //根据ID查询地区名称
                BusinessWorldArea worldArea = businessScenicSpotExpandService.selectProvinceId(province);
                mergerName += worldArea.getName();
            }
            if (ToolUtil.isNotEmpty(city)) {
                businessScenicSpotArea.setCityId(Integer.parseInt(city));
                //根据ID查询地区名称
                BusinessWorldArea worldArea = businessScenicSpotExpandService.selectProvinceId(city);
                mergerName +="-"+ worldArea.getName();
            }
            if (ToolUtil.isNotEmpty(county)) {
                businessScenicSpotArea.setRegionId(Integer.parseInt(county));
                //根据ID查询地区名称
                BusinessWorldArea worldArea = businessScenicSpotExpandService.selectProvinceId(county);
                mergerName +="-"+ worldArea.getName();
            }
            businessScenicSpotArea.setMergerName(mergerName);
            int i = businessScenicSpotExpandService.addScenicSpotExpand(businessScenicSpotExpand,businessScenicSpotArea);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("景区拓展信息新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (i == 2){
                returnModel.setData("");
                returnModel.setMsg("此景区已经有拓展业务！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("景区拓展信息新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("addScenicSpotExpand", e);
            returnModel.setData("");
            returnModel.setMsg("景区拓展信息新增失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }
    
    /**
    * @Author 郭凯
    * @Description: 景区拓展信息修改
    * @Title: editScenicSpotExpand
    * @date  2020年12月29日 下午3:33:34 
    * @param @param businessScenicSpotExpand
    * @param @return
    * @return ReturnModel
    * @throws
     */
    @RequestMapping("/editScenicSpotExpand")
    @ResponseBody
    public ReturnModel editScenicSpotExpand(BusinessScenicSpotExpand businessScenicSpotExpand) {
    	ReturnModel returnModel = new ReturnModel();
    	try {
    		String province = request.getParameter("province");
    		String city = request.getParameter("city");
    		String county = request.getParameter("county");
    		BusinessScenicSpotArea businessScenicSpotArea = new BusinessScenicSpotArea();
    		String mergerName= "";
    		if (ToolUtil.isNotEmpty(province)) {
    			businessScenicSpotArea.setProvinceId(Integer.parseInt(province));
    			//根据ID查询地区名称
    			BusinessWorldArea worldArea = businessScenicSpotExpandService.selectProvinceId(province);
    			mergerName += worldArea.getName();
    		}
    		if (ToolUtil.isNotEmpty(city)) {
    			businessScenicSpotArea.setCityId(Integer.parseInt(city));
    			//根据ID查询地区名称
    			BusinessWorldArea worldArea = businessScenicSpotExpandService.selectProvinceId(city);
    			mergerName +="-"+ worldArea.getName();
    		}
    		if (ToolUtil.isNotEmpty(county)) {
    			businessScenicSpotArea.setRegionId(Integer.parseInt(county));
    			//根据ID查询地区名称
    			BusinessWorldArea worldArea = businessScenicSpotExpandService.selectProvinceId(county);
    			mergerName +="-"+ worldArea.getName();
    		}
    		businessScenicSpotArea.setMergerName(mergerName);
    		int i = businessScenicSpotExpandService.editScenicSpotExpand(businessScenicSpotExpand,businessScenicSpotArea);
    		if (i == 1) {
    			returnModel.setData("");
    			returnModel.setMsg("景区拓展信息修改成功！");
    			returnModel.setState(Constant.STATE_SUCCESS);
    			return returnModel;
    		}else{
    			returnModel.setData("");
    			returnModel.setMsg("景区拓展信息修改失败！");
    			returnModel.setState(Constant.STATE_FAILURE);
    			return returnModel;
    		}
    	} catch (Exception e) {
    		logger.error("editScenicSpotExpand", e);
    		returnModel.setData("");
    		returnModel.setMsg("景区拓展信息修改失败！（请联系管理员）");
    		returnModel.setState(Constant.STATE_FAILURE);
    		return returnModel;
    	}
    }
    
    /**
    * @Author 郭凯
    * @Description: 删除景区拓展信息
    * @Title: delScenicSpotExpand
    * @date  2020年12月29日 下午3:45:17 
    * @param @param id
    * @param @return
    * @return ReturnModel
    * @throws
     */
    @RequestMapping("/delScenicSpotExpand")
    @ResponseBody
    public ReturnModel delScenicSpotExpand(Long id) {
    	ReturnModel returnModel = new ReturnModel();
    	try {
			int i = businessScenicSpotExpandService.delScenicSpotExpand(id);
			if (i == 1) {
    			returnModel.setData("");
    			returnModel.setMsg("景区拓展信息删除成功！");
    			returnModel.setState(Constant.STATE_SUCCESS);
    			return returnModel;
    		}else{
    			returnModel.setData("");
    			returnModel.setMsg("景区拓展信息删除失败！");
    			returnModel.setState(Constant.STATE_FAILURE);
    			return returnModel;
    		}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("delScenicSpotExpand", e);
    		returnModel.setData("");
    		returnModel.setMsg("景区拓展信息删除失败！（请联系管理员）");
    		returnModel.setState(Constant.STATE_FAILURE);
    		return returnModel;
		}
    }
    
    /**
    * @Author 郭凯
    * @Description: 查询景区下拉选，已分配的景区不显示
    * @Title: getScenicSpot
    * @date  2021年1月5日 下午1:55:20 
    * @param @return
    * @return ReturnModel
    * @throws
     */
    @RequestMapping("/getScenicSpot")
    @ResponseBody
    public ReturnModel getScenicSpot() {
    	ReturnModel returnModel = new ReturnModel();
    	try {
			List<BusinessScenicSpotExpand> spotList = businessScenicSpotExpandService.getScenicSpot();
			returnModel.setData(spotList);
			returnModel.setMsg("查询成功！");
			returnModel.setState(Constant.STATE_SUCCESS);
			return returnModel;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getScenicSpot", e);
    		returnModel.setData("");
    		returnModel.setMsg("查询失败！（请联系管理员）");
    		returnModel.setState(Constant.STATE_FAILURE);
    		return returnModel;
		}
    }
    	
}
