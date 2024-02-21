package com.hna.hka.archive.management.business.controller;

import com.hna.hka.archive.management.business.model.BusinessBanner;
import com.hna.hka.archive.management.business.service.BusinessBannerService;
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
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.controller
 * @ClassName: BannerController
 * @Author: 郭凯
 * @Description: Banner图管理控制层
 * @Date: 2020/8/4 14:17
 * @Version: 1.0
 */
@RequestMapping("/business/banner")
@Controller
public class BannerController extends PublicUtil {

    @Autowired
    private BusinessBannerService businessBannerService;

    /**
     * @Author 郭凯
     * @Description Banner图管理列表查询
     * @Date 14:24 2020/8/4
     * @Param [pageNum, pageSize, businessBanner]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getBannerList")
    @ResponseBody
    public PageDataResult getBannerList(@RequestParam("pageNum") Integer pageNum,
                                                   @RequestParam("pageSize") Integer pageSize, BusinessBanner businessBanner) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("type",businessBanner.getType());
            pageDataResult = businessBannerService.getBannerList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("Banner图管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description Banner图新增
     * @Date 14:53 2020/8/4
     * @Param [businessBanner]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addBanner")
    @ResponseBody
    public ReturnModel addBanner(BusinessBanner businessBanner) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessBannerService.addBanner(businessBanner);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("Banner图新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("Banner图新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("addBanner", e);
            returnModel.setData("");
            returnModel.setMsg("Banner图新增失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description Banner图删除
     * @Date 15:34 2020/8/4
     * @Param [businessBanner]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delBanner")
    @ResponseBody
    public ReturnModel delBanner(@RequestParam("id") Long id) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessBannerService.delBanner(id);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("Banner图删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("Banner图删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("delBanner", e);
            returnModel.setData("");
            returnModel.setMsg("Banner图删除失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }
    /**
     * @Author 郭凯
     * @Description Banner图编辑
     * @Date 16:00 2020/8/4
     * @Param [businessBanner]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editBanner")
    @ResponseBody
    public ReturnModel editBanner(BusinessBanner businessBanner) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessBannerService.editBanner(businessBanner);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("Banner图编辑成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("Banner图编辑失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("editBanner", e);
            returnModel.setData("");
            returnModel.setMsg("Banner图编辑失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

}
