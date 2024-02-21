package com.hna.hka.archive.management.business.controller;

import com.hna.hka.archive.management.business.model.BusinessOrder;
import com.hna.hka.archive.management.business.service.BusinessOrderService;
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
 * @ClassName: OrderController
 * @Author: 郭凯
 * @Description: 订单管理控制层（招商）
 * @Date: 2020/8/13 13:50
 * @Version: 1.0
 */
@RequestMapping("/business/order")
@Controller
public class BusinessOrderController extends PublicUtil {

    @Autowired
    private BusinessOrderService businessOrderService;

    /**
     * @Author 郭凯
     * @Description 订单管理列表查询
     * @Date 13:55 2020/8/13
     * @Param [pageNum, pageSize, businessOrder]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getOrderList")
    @ResponseBody
    public PageDataResult getOrderList(@RequestParam("pageNum") Integer pageNum,
                                               @RequestParam("pageSize") Integer pageSize, BusinessOrder businessOrder) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("orderType",businessOrder.getOrderType());
            search.put("userName",businessOrder.getUserName());
            pageDataResult = businessOrderService.getOrderList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("订单管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 修改订单
     * @Date 13:17 2020/8/15
     * @Param [businessOrder]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editOrder")
    @ResponseBody
    public ReturnModel editOrder(BusinessOrder businessOrder) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessOrderService.editOrder(businessOrder);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("订单修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("订单修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("editOrder", e);
            returnModel.setData("");
            returnModel.setMsg("订单修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 删除订单
     * @Date 13:24 2020/8/15
     * @Param [id]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delOrder")
    @ResponseBody
    public ReturnModel delOrder(@RequestParam("id") Long id) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessOrderService.delOrder(id);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("订单删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("订单删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("delOrder", e);
            returnModel.setData("");
            returnModel.setMsg("订单删除失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

}
