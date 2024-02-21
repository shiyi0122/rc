package com.hna.hka.archive.management.business.controller;

import com.hna.hka.archive.management.business.model.BusinessMemberLevel;
import com.hna.hka.archive.management.business.service.BusinessMemberLevelService;
import com.hna.hka.archive.management.system.util.*;
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
 * @ClassName: MemberLevelController
 * @Author: 郭凯
 * @Description: 积分规则管理控制层
 * @Date: 2020/8/15 17:08
 * @Version: 1.0
 */
@RequestMapping("/business/memberLevel")
@Controller
public class MemberLevelController extends PublicUtil {

    @Autowired
    private BusinessMemberLevelService businessMemberLevelService;

    /**
     * @Author 郭凯
     * @Description 积分规则管理列表查询
     * @Date 17:32 2020/8/15
     * @Param [pageNum, pageSize, businessMemberLevel]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getMemberLevelList")
    @ResponseBody
    public PageDataResult getMemberLevelList(@RequestParam("pageNum") Integer pageNum,
                                        @RequestParam("pageSize") Integer pageSize, BusinessMemberLevel businessMemberLevel) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = businessMemberLevelService.getMemberLevelList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("积分规则管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 删除积分规则
     * @Date 10:01 2020/8/17
     * @Param [id]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delMemberLevel")
    @ResponseBody
    public ReturnModel delMemberLevel(@RequestParam("id") Long id) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessMemberLevelService.delMemberLevel(id);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("积分规则删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("积分规则删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("delMemberLevel", e);
            returnModel.setData("");
            returnModel.setMsg("积分规则删除失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 新增积分规则
     * @Date 10:16 2020/8/17
     * @Param [businessMemberLevel]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addMemberLevel")
    @ResponseBody
    public ReturnModel addMemberLevel(BusinessMemberLevel businessMemberLevel) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessMemberLevelService.addMemberLevel(businessMemberLevel);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("积分规则新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("积分规则新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("addMemberLevel", e);
            returnModel.setData("");
            returnModel.setMsg("积分规则新增失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 修改积分规则
     * @Date 10:30 2020/8/17
     * @Param [businessMemberLevel]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editMemberLevel")
    @ResponseBody
    public ReturnModel editMemberLevel(BusinessMemberLevel businessMemberLevel) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessMemberLevelService.editMemberLevel(businessMemberLevel);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("积分规则修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("积分规则修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("editMemberLevel", e);
            returnModel.setData("");
            returnModel.setMsg("积分规则修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

}
