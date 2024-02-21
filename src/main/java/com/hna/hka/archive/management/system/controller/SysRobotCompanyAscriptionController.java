package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysRobotCompanyAscription;
import com.hna.hka.archive.management.system.model.SysScenicSpotAscriptionCompany;
import com.hna.hka.archive.management.system.service.SysRobotCompanyAscriptionService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.ReturnModel;
import com.hna.hka.archive.management.system.util.ToolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/2/25 14:22
 * 机器人归属公司
 *
 */
@Api(tags = "机器人归属")
@RequestMapping("/system/sysRobotCompanyAscription")
@Controller
public class SysRobotCompanyAscriptionController {

    @Autowired
    SysRobotCompanyAscriptionService sysRobotCompanyAscriptionService;


    /**
     * 添加机器人归属公司
     * @param
     * @param
     * @param robotCompanyAscription
     * @return
     */
    @ApiOperation("添加机器人公司归属")
    @RequestMapping("/addRobotCompanyAscription")
    @ResponseBody
    public ReturnModel addAscriptionCompany(SysRobotCompanyAscription robotCompanyAscription) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotCompanyAscriptionService.addRobotCompanyAscription(robotCompanyAscription);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("机器人归属公司新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (i==2){
                returnModel.setData("");
                returnModel.setMsg("机器人已有归属公司,请勿重复添加！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("机器人归属公司新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
//            logger.error("addAscriptionCompany", e);
            returnModel.setData("");
            returnModel.setMsg("机器人归属公司新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 修改机器人归属公司
     * @param
     * @param
     * @param robotCompanyAscription
     * @return
     */
    @ApiOperation("修改机器人公司归属")
    @RequestMapping("/editRobotCompanyAscription")
    @ResponseBody
    public ReturnModel editAscriptionCompany(SysRobotCompanyAscription robotCompanyAscription) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotCompanyAscriptionService.editAscriptionCompany(robotCompanyAscription);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("机器人归属公司修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("机器人归属公司修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
//            logger.error("addAscriptionCompany", e);
            returnModel.setData("");
            returnModel.setMsg("机器人归属公司修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 机器人归属列表
     * zhang
     * @param pageNum
     * @param pageSize
     * @param sysRobotCompanyAscription
     * @return
     */
    @ApiOperation("机器人公司归属列表")
    @RequestMapping("/getAscriptionCompanyList")
    @ResponseBody
    public PageDataResult getAscriptionCompanyList(@RequestParam("pageNum") Integer pageNum,
                                                   @RequestParam("pageSize") Integer pageSize, SysRobotCompanyAscription sysRobotCompanyAscription) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysRobotCompanyAscriptionService.getAscriptionCompanyList(pageNum,pageSize,search);
        }catch (Exception e){
           e.printStackTrace();
        }
        return pageDataResult;
    }



}
