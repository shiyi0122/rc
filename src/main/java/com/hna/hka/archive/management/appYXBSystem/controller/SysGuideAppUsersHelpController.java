package com.hna.hka.archive.management.appYXBSystem.controller;


import com.hna.hka.archive.management.appYXBSystem.model.SysGuideAppUsersHelp;
import com.hna.hka.archive.management.appYXBSystem.service.SysGuideAppUsersHelpService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Api(tags = "游小伴app使用帮助")
@RestController
@RequestMapping("/system/guideAppUsersHelp")
public class SysGuideAppUsersHelpController extends PublicUtil {

    @Autowired
    SysGuideAppUsersHelpService sysGuideAppHelpService;

    /**
     * 添加使用帮助
     * @param sysGuideAppUsersHelp
     * @return
     */
    @RequestMapping("/addGuideAppUsersHelp")
    @ResponseBody
    public ReturnModel addGuideAppUsersHelp(SysGuideAppUsersHelp sysGuideAppUsersHelp){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysGuideAppHelpService.addGuideAppUsersHelp(sysGuideAppUsersHelp);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("帮助新增成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (i == 2){
                returnModel.setData("");
                returnModel.setMsg("帮助新增失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else if (i != 1 && i != 2){
                returnModel.setData("");
                returnModel.setMsg("帮助新增失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("addGuideAppUsersHelp",e);
            returnModel.setData("");
            returnModel.setMsg("帮助新增失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
        return returnModel;
    }



    /**
     * 修改使用帮助
     * @param sysGuideAppUsersHelp
     * @return
     */
    @RequestMapping("/editGuideAppUsersHelp")
    @ResponseBody
    public ReturnModel editGuideAppUsersHelp(SysGuideAppUsersHelp sysGuideAppUsersHelp){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysGuideAppHelpService.editGuideAppUsersHelp(sysGuideAppUsersHelp);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("帮助修改成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (i == 2){
                returnModel.setData("");
                returnModel.setMsg("帮助修改失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else if (i != 1 && i != 2){
                returnModel.setData("");
                returnModel.setMsg("帮助修改失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("addGuideAppUsersHelp",e);
            returnModel.setData("");
            returnModel.setMsg("帮助修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
        return returnModel;
    }


    /**
     * 删除使用帮助
     * @param helpId
     * @return
     */
        @RequestMapping("/delGuideAppUsersHelp")
    @ResponseBody
    public ReturnModel delGuideAppUsersHelp(@NotBlank(message = "用户ID不能为空")Long helpId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysGuideAppHelpService.delGuideAppUsersHelp(helpId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("帮助删除成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("帮助删除失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delUser",e);
            returnModel.setData("");
            returnModel.setMsg("帮助删除失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * 使用帮助列表查询
     * @param pageNum
     * @param pageSize
     * @param sysGuideAppUsersHelp
     * @return
     */
    @RequestMapping(value = "/getGuideAppUsersHelpList", method = RequestMethod.GET)
    @ResponseBody
    public PageDataResult getGuideAppUsersHelpList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize, SysGuideAppUsersHelp sysGuideAppUsersHelp) {
        PageDataResult pageDataResult = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysGuideAppHelpService.getGuideAppUsersHelpList(pageNum,pageSize,sysGuideAppUsersHelp);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("使用帮助查询异常！", e);
        }
        return pageDataResult;
    }


    

}
