package com.hna.hka.archive.management.managerApp.controller;

import com.hna.hka.archive.management.managerApp.model.SysManagerAppRes;
import com.hna.hka.archive.management.managerApp.service.SysManagerAppResService;
import com.hna.hka.archive.management.system.model.SysResource;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.managerApp.controller
 * @ClassName: ManagerAppResController
 * @Author: 郭凯
 * @Description: 管理者APP菜单栏控制层
 * @Date: 2021/6/4 10:21
 * @Version: 1.0
 */
@RequestMapping("/system/managerAppRes")
@Controller
public class ManagerAppResController extends PublicUtil {

    @Autowired
    private SysManagerAppResService sysManagerAppResService;


    /**
     * @Method getManagerAppResList
     * @Author 郭凯
     * @Version  1.0
     * @Description 管理者APP菜单栏列表查询
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     * @Date 2021/6/4 10:33
     */
    @RequestMapping("/getManagerAppResList")
    @ResponseBody
    public Map<String,Object> getManagerAppResList(SysManagerAppRes managerAppRes) {
        Map<String, Object> data = new HashMap<>();
        try {
            List<SysManagerAppRes> managerAppResList = sysManagerAppResService.getManagerAppResList(managerAppRes);
            data.put("code",0);
            data.put("msg","");
            data.put("count",managerAppResList.size());
            data.put("data",managerAppResList);
        } catch (Exception e) {
            logger.info("getManagerAppResList",e);
        }
        return data;
    }

    /**
     * @Method addManagerAppRes
     * @Author 郭凯
     * @Version  1.0
     * @Description APP菜单栏新增
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/4 14:16
     */
    @RequestMapping("/addManagerAppRes")
    @ResponseBody
    public ReturnModel addManagerAppRes(SysManagerAppRes sysResource){
        ReturnModel returnModel = null;
        try {
            returnModel = new ReturnModel();
            int i= sysManagerAppResService.addManagerAppRes(sysResource);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("APP菜单栏新增成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("APP菜单栏新增错误，请联系管理员");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("addManagerAppRes",e);
            returnModel.setData("");
            returnModel.setMsg("APP菜单栏新增错误，请联系管理员");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method addManagerAppRes
     * @Author 郭凯
     * @Version  1.0
     * @Description APP菜单栏删除
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/4 14:16
     */
    @RequestMapping("/delManagerAppRes")
    @ResponseBody
    public ReturnModel delManagerAppRes(SysManagerAppRes sysResource){
        ReturnModel returnModel = null;
        try {
            returnModel = new ReturnModel();
            int i= sysManagerAppResService.delManagerAppRes(sysResource.getResId());
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("APP菜单栏删除成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("APP菜单栏删除错误，请联系管理员");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("addManagerAppRes",e);
            returnModel.setData("");
            returnModel.setMsg("APP菜单栏删除错误，请联系管理员");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }
}
