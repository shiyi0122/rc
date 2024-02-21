package com.hna.hka.archive.management.business.controller;

import com.hna.hka.archive.management.business.model.BusinessBanner;
import com.hna.hka.archive.management.business.model.BusinessConfigs;
import com.hna.hka.archive.management.business.service.BusinessConfigsService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/7/11 13:24
 *商务平台配置表（只修改关于我们）
 */

@RequestMapping("/business/businessConfigs")
@Controller
public class BusinessConfigsController extends PublicUtil {

    @Autowired
    BusinessConfigsService businessConfigsService;


    /**
     * 修改关于我们
     * @param businessConfigs
     * @return
     */
    @RequestMapping("/editConfigs")
    @ResponseBody
    public ReturnModel editConfigs(BusinessConfigs businessConfigs) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessConfigsService.editConfigs(businessConfigs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("关于我们修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("关于我们修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("editConfigs", e);
            returnModel.setData("");
            returnModel.setMsg("关于我们修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 查询关于我们
     * @param pageNum
     * @param pageSize
     * @param
     * @return
     */
    @RequestMapping("/getConfigsList")
    @ResponseBody
    public PageDataResult getConfigsList(@RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize, BusinessConfigs businessConfigs) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
//            search.put("type",businessConfigs.getType());
            pageDataResult = businessConfigsService.getConfigsList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("关于我们列表查询失败",e);
        }
        return pageDataResult;
    }


//    /**
//     * 添加关于我们
//     * @param
//     * @return
//     */
//    @RequestMapping("/addConfigs")
//    @ResponseBody
//    public ReturnModel addConfigs(BusinessConfigs businessConfigs) {
//        ReturnModel returnModel = new ReturnModel();
//        try {
//            int i = businessConfigsService.addConfigs(businessConfigs);
//            if (i == 1) {
//                returnModel.setData("");
//                returnModel.setMsg("Banner图新增成功！");
//                returnModel.setState(Constant.STATE_SUCCESS);
//                return returnModel;
//            }else {
//                returnModel.setData("");
//                returnModel.setMsg("Banner图新增失败！");
//                returnModel.setState(Constant.STATE_FAILURE);
//                return returnModel;
//            }
//        } catch (Exception e) {
//            logger.error("addBanner", e);
//            returnModel.setData("");
//            returnModel.setMsg("Banner图新增失败！（请联系管理员）");
//            returnModel.setState(Constant.STATE_FAILURE);
//            return returnModel;
//        }
//    }






}
