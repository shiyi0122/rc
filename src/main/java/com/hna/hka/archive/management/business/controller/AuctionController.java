package com.hna.hka.archive.management.business.controller;

import com.hna.hka.archive.management.business.model.BusinessAuction;
import com.hna.hka.archive.management.business.service.BusinessAuctionService;
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
 * @ClassName: AuctionController
 * @Author: 郭凯
 * @Description: 竞拍\限时购控制层
 * @Date: 2020/10/14 13:40
 * @Version: 1.0
 */
@RequestMapping("/business/auction")
@Controller
public class AuctionController extends PublicUtil{

    @Autowired
    private BusinessAuctionService businessAuctionService;

    /**
     * @Author 郭凯
     * @Description 竞拍\限时购列表查询
     * @Date 13:44 2020/10/14
     * @Param [pageNum, pageSize, businessAuction]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getAuctionList")
    @ResponseBody
    public PageDataResult getAuctionList(@RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize, BusinessAuction businessAuction) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("type",businessAuction.getType());
            pageDataResult = businessAuctionService.getAuctionList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("竞拍/限时购列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 限时购新增
     * @Date 9:30 2020/10/16
     * @Param [businessAuction]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addRushPurchase")
    @ResponseBody
    public ReturnModel addRushPurchase(BusinessAuction businessAuction) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessAuctionService.addRushPurchase(businessAuction);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("限时购新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("限时购新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("addRushPurchase", e);
            returnModel.setData("");
            returnModel.setMsg("限时购新增失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 限时购修改
     * @Date 14:42 2020/10/16
     * @Param [businessAuction]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editRushPurchase")
    @ResponseBody
    public ReturnModel editRushPurchase(BusinessAuction businessAuction) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessAuctionService.editRushPurchase(businessAuction);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("限时购修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("限时购修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("editRushPurchase", e);
            returnModel.setData("");
            returnModel.setMsg("限时购修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 竞拍\限时购删除
     * @Date 14:46 2020/10/16
     * @Param [businessAuction]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delRushPurchase")
    @ResponseBody
    public ReturnModel delRushPurchase(Long id) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessAuctionService.delRushPurchase(id);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("竞拍/限时购删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("竞拍/限时购删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("delRushPurchase", e);
            returnModel.setData("");
            returnModel.setMsg("竞拍/限时购删除失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 新增竞拍
     * @Date 10:02 2020/10/21
     * @Param [businessAuction]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/addAuction")
    @ResponseBody
    public ReturnModel addAuction(BusinessAuction businessAuction) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessAuctionService.addRushPurchase(businessAuction);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("竞拍新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("竞拍新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("addAuction", e);
            returnModel.setData("");
            returnModel.setMsg("竞拍新增失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 修改竞拍
     * @Date 10:23 2020/10/21
     * @Param [businessAuction]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editAuction")
    @ResponseBody
    public ReturnModel editAuction(BusinessAuction businessAuction) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessAuctionService.editRushPurchase(businessAuction);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("竞拍修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("竞拍修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("editAuction", e);
            returnModel.setData("");
            returnModel.setMsg("竞拍修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

}
