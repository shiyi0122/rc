package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysScenicSpotAscriptionCompany;
import com.hna.hka.archive.management.system.service.SysScenicSpotAscriptionCompanyService;
import com.hna.hka.archive.management.system.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: AscriptionCompanyController
 * @Author: 郭凯
 * @Description: 景区归属公司控制层
 * @Date: 2021/5/20 15:40
 * @Version: 1.0
 */
@RequestMapping("/system/ascriptionCompany")
@Controller
public class AscriptionCompanyController extends PublicUtil {

    @Autowired
    private SysScenicSpotAscriptionCompanyService sysScenicSpotAscriptionCompanyService;

    /**
     * @Method getAscriptionCompanyList
     * @Author 郭凯
     * @Version  1.0
     * @Description 景区归属公司列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/5/20 16:06
     */
    @RequestMapping("/getAscriptionCompanyList")
    @ResponseBody
    public PageDataResult getAscriptionCompanyList(@RequestParam("pageNum") Integer pageNum,
                                           @RequestParam("pageSize") Integer pageSize, SysScenicSpotAscriptionCompany ascriptionCompany) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysScenicSpotAscriptionCompanyService.getAscriptionCompanyList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("景点列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Method addAscriptionCompany
     * @Author 郭凯
     * @Version  1.0
     * @Description 景区归属公司新增
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/5/20 16:41
     */
    @RequestMapping("/addAscriptionCompany")
    @ResponseBody
    public ReturnModel addAscriptionCompany(SysScenicSpotAscriptionCompany ascriptionCompany) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotAscriptionCompanyService.addAscriptionCompany(ascriptionCompany);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("归属公司新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("归属公司新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("addAscriptionCompany", e);
            returnModel.setData("");
            returnModel.setMsg("归属公司新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method editAscriptionCompany
     * @Author 郭凯
     * @Version  1.0
     * @Description 景区归属公司修改
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/5/20 16:56
     */
    @RequestMapping("/editAscriptionCompany")
    @ResponseBody
    public ReturnModel editAscriptionCompany(SysScenicSpotAscriptionCompany ascriptionCompany) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotAscriptionCompanyService.editAscriptionCompany(ascriptionCompany);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("归属公司修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("归属公司修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("editAscriptionCompany", e);
            returnModel.setData("");
            returnModel.setMsg("归属公司修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method delAscriptionCompany
     * @Author 郭凯
     * @Version  1.0
     * @Description 景区归属公司删除
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/5/20 16:58
     */
    @RequestMapping("/delAscriptionCompany")
    @ResponseBody
    public ReturnModel delAscriptionCompany(@RequestParam Long companyId) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotAscriptionCompanyService.delAscriptionCompany(companyId);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("归属公司删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("归属公司删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("delAscriptionCompany", e);
            returnModel.setData("");
            returnModel.setMsg("归属公司删除失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * @Method getAscriptionCompany
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询所有的公司信息
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/5/20 17:15
     */
    @RequestMapping("/getAscriptionCompany")
    @ResponseBody
    public ReturnModel getAscriptionCompany() {
        ReturnModel returnModel = new ReturnModel();
        try {
            List<SysScenicSpotAscriptionCompany> ascriptionCompanyList = sysScenicSpotAscriptionCompanyService.getAscriptionCompany();
            if (ToolUtil.isNotEmpty(ascriptionCompanyList)) {
                returnModel.setData(ascriptionCompanyList);
                returnModel.setMsg("查询成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("查询失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("getAscriptionCompany", e);
            returnModel.setData("");
            returnModel.setMsg("查询失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

}
