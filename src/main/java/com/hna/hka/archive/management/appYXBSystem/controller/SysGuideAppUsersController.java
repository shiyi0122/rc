package com.hna.hka.archive.management.appYXBSystem.controller;


import com.hna.hka.archive.management.appYXBSystem.model.SysGuideAppUsers;
import com.hna.hka.archive.management.appYXBSystem.model.UserOperationLog;
import com.hna.hka.archive.management.appYXBSystem.model.UserOperationLogDTO;
import com.hna.hka.archive.management.appYXBSystem.service.SysGuideAppUsersService;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(tags = "游小伴app用户")
@RestController
@RequestMapping("/system/guideAppUsers")
public class SysGuideAppUsersController extends PublicUtil {

    @Autowired
    SysGuideAppUsersService sysGuideAppUsersService;


    /**
     * 添加用户
     *
     * @param sysGuideAppUsers
     * @return
     */
    @RequestMapping("/addGuideAppUsers")
    @ResponseBody
    public ReturnModel addGuideAppUsers(SysGuideAppUsers sysGuideAppUsers) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysGuideAppUsersService.addGuideAppUsers(sysGuideAppUsers);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("用户新增成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else if (i == 2) {
                returnModel.setData("");
                returnModel.setMsg("此账号已存在！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else if (i != 1 && i != 2) {
                returnModel.setData("");
                returnModel.setMsg("用户新增失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("addUser", e);
            returnModel.setData("");
            returnModel.setMsg("用户新增失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
        return returnModel;
    }


    /**
     * 修改用户
     *
     * @param sysGuideAppUsers
     * @return
     */
    @RequestMapping("/editGuideAppUsers")
    @ResponseBody
    public ReturnModel editGuideAppUsers(SysGuideAppUsers sysGuideAppUsers) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysGuideAppUsersService.editGuideAppUsers(sysGuideAppUsers);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("用户修改成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("用户修改失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("addUser", e);
            returnModel.setData("");
            returnModel.setMsg("用户修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 删除
     *
     * @param userId
     * @return
     */
    @RequestMapping("/delGuideAppUsers")
    @ResponseBody
    public ReturnModel delGuideAppUsers(@NotBlank(message = "用户ID不能为空") Long userId) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysGuideAppUsersService.delGuideAppUsers(userId);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("用户删除成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("用户删除失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("delUser", e);
            returnModel.setData("");
            returnModel.setMsg("用户删除失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 查询用户列表
     *
     * @param pageNum
     * @param pageSize
     * @param sysGuideAppUsers
     * @return
     */

    @RequestMapping(value = "/getGuideAppUsersList", method = RequestMethod.GET)
    @ResponseBody
    public PageDataResult getGuideAppUsersList(@RequestParam("pageNum") Integer pageNum,
                                               @RequestParam("pageSize") Integer pageSize, SysGuideAppUsers sysGuideAppUsers) {
        PageDataResult pageDataResult = new PageDataResult();
        try {
            if (null == pageNum) {
                pageNum = 1;
            }
            if (null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysGuideAppUsersService.getGuideAppUsersList(pageNum, pageSize, sysGuideAppUsers);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户列表查询异常！", e);
        }
        return pageDataResult;
    }

    @RequestMapping("/getGuideAppUsersPhone")
    @ResponseBody
    public ReturnModel getGuideAppUsersPhone() {
        ReturnModel returnModel = new ReturnModel();
        List<SysGuideAppUsers> list = sysGuideAppUsersService.getGuideAppUsersPhone();
        returnModel.setData(list);
        returnModel.setMsg("成功");
        returnModel.setState(Constant.STATE_SUCCESS);

        return returnModel;
    }

    @ApiOperation("查询用户操作记录")
    @PostMapping("/getUserOperation")
    public ReturnModel getUserOperation(@RequestBody UserOperationLogDTO userOperationLogDTO) {
        ReturnModel returnModel = new ReturnModel();
        List<UserOperationLog> userOperation = sysGuideAppUsersService.getUserOperation(userOperationLogDTO);
        returnModel.setData(userOperation);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");
        return returnModel;
    }
}