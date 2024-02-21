package com.hna.hka.archive.management.appYXBSystem.controller;


import com.hna.hka.archive.management.appYXBSystem.model.SysGuideAppUsersFeedback;
import com.hna.hka.archive.management.appYXBSystem.service.SysGuideAppUsersFeedbackService;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Api(tags = "游小伴app用户反馈")
@RestController
@RequestMapping("/system/guideAppUsersFeedback")
public class SysGuideAppUsersFeedbackController extends PublicUtil {
    @Autowired
    SysGuideAppUsersFeedbackService sysGuideAppFeedbackService;

    /**
     * 添加反馈
     * @param sysGuideAppUsersFeedback
     * @return
     */
    @RequestMapping("/addGuideAppUsersFeedback")
    @ResponseBody
    public ReturnModel addGuideAppUsersFeedback(SysGuideAppUsersFeedback sysGuideAppUsersFeedback) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysGuideAppFeedbackService.addGuideAppUsersFeedback(sysGuideAppUsersFeedback);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("反馈新增成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("反馈新增失败");
                returnModel.setState(Constant.STATE_SUCCESS);

            }
        } catch (Exception e) {
            logger.info("addUser", e);
            returnModel.setData("");
            returnModel.setMsg("反馈新增失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
       return  returnModel;
    }


    /**
     * 修改反馈
     * @param sysGuideAppUsersFeedback
     * @return
     */
    @RequestMapping("/editGuideAppUsersFeedback")
    @ResponseBody
    public ReturnModel editGuideAppUsersFeedback(SysGuideAppUsersFeedback sysGuideAppUsersFeedback) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysGuideAppFeedbackService.editGuideAppUsersFeedback(sysGuideAppUsersFeedback);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("反馈修改成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("反馈修改失败");
                returnModel.setState(Constant.STATE_SUCCESS);

            }
        } catch (Exception e) {
            logger.info("editGuideAppUsersFeedback", e);
            returnModel.setData("");
            returnModel.setMsg("反馈修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
        return  returnModel;
    }


    /**
     * 删除反馈
     * @param
     * @return
     */
    @RequestMapping("/delGuideAppUsersFeedback")
    @ResponseBody
    public ReturnModel delGuideAppUsersFeedback(@NotBlank(message = "反馈ID不能为空")Long appUserId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysGuideAppFeedbackService.delGuideAppUsersFeedback(appUserId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("反馈删除成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("反馈删除失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delGuideAppUsersFeedback",e);
            returnModel.setData("");
            returnModel.setMsg("反馈删除失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * 反馈列表
     * @param pageNum
     * @param pageSize
     * @param sysGuideAppUsersFeedback
     * @return
     */
    @RequestMapping(value = "/getGuideAppUsersFeedbackList", method = RequestMethod.GET)
    @ResponseBody
    public PageDataResult getGuideAppUsersFeedbackList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize, SysGuideAppUsersFeedback sysGuideAppUsersFeedback) {
        PageDataResult pageDataResult = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysGuideAppFeedbackService.getGuideAppUsersFeedbackList(pageNum,pageSize,sysGuideAppUsersFeedback);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户列表查询异常！", e);
        }
        return pageDataResult;
    }



}
