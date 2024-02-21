package com.hna.hka.archive.management.business.controller;

import com.hna.hka.archive.management.business.model.BusinessFeedBack;
import com.hna.hka.archive.management.business.service.BusinessFeedBackService;
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
 * @ClassName: FeedBackController
 * @Author: 郭凯
 * @Description: 意见反馈控制层
 * @Date: 2020/8/12 9:10
 * @Version: 1.0
 */
@RequestMapping("/business/feedBack")
@Controller
public class FeedBackController extends PublicUtil {

    @Autowired
    private BusinessFeedBackService businessFeedBackService;

    /**
     * @Author 郭凯
     * @Description 意见反馈列表查询
     * @Date 10:09 2020/8/12
     * @Param [pageNum, pageSize, businessFeedBack]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getFeedBackList")
    @ResponseBody
    public PageDataResult getFeedBackList(@RequestParam("pageNum") Integer pageNum,
                                              @RequestParam("pageSize") Integer pageSize, BusinessFeedBack businessFeedBack) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("contact",businessFeedBack.getContact());
            pageDataResult = businessFeedBackService.getFeedBackList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("意见反馈列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 内容回复
     * @Date 11:05 2020/8/12
     * @Param [businessFeedBack]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/editReply")
    @ResponseBody
    public ReturnModel editReply(BusinessFeedBack businessFeedBack) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessFeedBackService.editReply(businessFeedBack);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("内容回复成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("内容回复失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("editReply", e);
            returnModel.setData("");
            returnModel.setMsg("内容回复失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 意见反馈删除
     * @Date 11:40 2020/8/12
     * @Param [id]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delFeedBack")
    @ResponseBody
    public ReturnModel delFeedBack(@RequestParam("id") Long id) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessFeedBackService.delFeedBack(id);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("意见反馈删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("意见反馈删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("delFeedBack", e);
            returnModel.setData("");
            returnModel.setMsg("意见反馈删除失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

}
