package com.hna.hka.archive.management.appYXBSystem.controller;


import com.hna.hka.archive.management.appYXBSystem.model.SysGuideAppNews;
import com.hna.hka.archive.management.appYXBSystem.service.SysGuideAppNewsService;
import com.hna.hka.archive.management.appYXBSystem.service.SysGuideAppUsersService;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "游小伴app消息")
@RestController
@RequestMapping("/system/guideAppNews")
public class SysGuideAppNewsController extends PublicUtil {

//    @Autowired
//    private HttpServletRequest request;

    @Autowired
    SysGuideAppNewsService sysGuideAppNewsService;
    @Autowired
    SysGuideAppUsersService sysGuideAppUsersService;


    /**
     * 游小伴app消息列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/getGuideAppNewsList")
    @ResponseBody
    public PageDataResult guideAppNewsList(@RequestParam("pageNum") Integer pageNum,
                                           @RequestParam("pageSize") Integer pageSize, SysGuideAppNews sysGuideAppNews){

        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if (null == pageNum) {
                pageNum = 1;
            }
            if (null == pageSize) {
                pageSize = 10;
            }
//            String startTime = request.getParameter("startTime");
//            String endTime = request.getParameter("endTime");
            if (ToolUtil.isNotEmpty(sysGuideAppNews.getGuideContent())){
                search.put("guideContent",sysGuideAppNews.getGuideContent());
            }
//            search.put("guideUserId",sysGuideAppNews.getGuideUserId().toString());
//            search.put("guideState",sysGuideAppNews.getGuideState());
//            search.put("startTime",startTime);
//            search.put("endTime",endTime);
            pageDataResult = sysGuideAppNewsService.getGuideAppNewsList(pageNum,pageSize,search);

        }catch (Exception e) {
            e.printStackTrace();
            logger.error("用户消息列表查询异常！", e);
        }
    return pageDataResult ;
    }


    /**
     * 修改消息
     * @param sysGuideAppNews
     * @return
     */
    @RequestMapping("/editGuideAppNews")
    @ResponseBody
    public ReturnModel editGuideAppNews(SysGuideAppNews sysGuideAppNews){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysGuideAppNewsService.editGuideAppNews(sysGuideAppNews);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("用户消息修改成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("用户消息修改失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("editUserNews",e);
            returnModel.setData("");
            returnModel.setMsg("用户消息修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * 删除消息
     * @param guideId
     * @return
     */
    @RequestMapping("/delGuideAppNews")
    @ResponseBody
    public ReturnModel delGuideAppNews(@NotBlank(message = "用户ID不能为空")Long  guideId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysGuideAppNewsService.delGuideAppNews(guideId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("用户消息删除成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("用户消息删除失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delUserNews",e);
            returnModel.setData("");
            returnModel.setMsg("用户消息删除失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 添加消息
     * @param sysGuideAppNews
     * @return
     */

    @RequestMapping("/addGuideAppNews")
    @ResponseBody
    public ReturnModel addGuideAppNews(SysGuideAppNews sysGuideAppNews){
        ReturnModel returnModel = new ReturnModel();
        try {

            int i = sysGuideAppNewsService.addGuideAppNews(sysGuideAppNews);

            List<String> list = sysGuideAppUsersService.getGuideAppUsersByClientGtId();

            String s = WeChatGtRobotAppPush.pushToList(list, sysGuideAppNews.getGuideTitle(), sysGuideAppNews.getGuideContent());
            if (!"1".equals(s)){
                returnModel.setData("");
                returnModel.setMsg("用户消息推送失败");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }

            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("用户消息添加成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("用户消息添加失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("addUserNews",e);
            returnModel.setData("");
            returnModel.setMsg("用户消息添加失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }



}
