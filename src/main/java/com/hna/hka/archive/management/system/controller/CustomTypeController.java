package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysScenicSpotCustomType;
import com.hna.hka.archive.management.system.service.SysScenicSpotCustomTypeService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: CustomTypeController
 * @Author: 郭凯
 * @Description: 自定义类型控制层
 * @Date: 2020/7/27 15:37
 * @Version: 1.0
 */
@RequestMapping("/system/customType")
@Controller
public class CustomTypeController extends PublicUtil {

    @Autowired
    private SysScenicSpotCustomTypeService sysScenicSpotCustomTypeService;

    /**
     * @Author 郭凯
     * @Description 自定义类型列表查询
     * @Date 15:41 2020/7/27
     * @Param [pageNum, pageSize, sysScenicSpotCustomType]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping(value = "/getCustomTypeList", method = RequestMethod.GET)
    @ResponseBody
    public PageDataResult getCustomTypeList(@RequestParam("pageNum") Integer pageNum,
                                             @RequestParam("pageSize") Integer pageSize, SysScenicSpotCustomType sysScenicSpotCustomType) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysScenicSpotCustomTypeService.getCustomTypeList(pageNum,pageSize,search);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("自定义类型列表查询异常！", e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 删除自定义类型
     * @Date 16:54 2020/7/27
     * @Param [typeId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delCustomType")
    @ResponseBody
    public ReturnModel delCustomType(@RequestParam("typeId") Long typeId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotCustomTypeService.delCustomType(typeId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("自定义类型删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("自定义类型删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("自定义类型删除失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 添加自定义类型
     * @Date 9:33 2020/7/28
     * @Param [sysScenicSpotCustomType]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addCustomType")
    @ResponseBody
    public ReturnModel addCustomType(SysScenicSpotCustomType sysScenicSpotCustomType){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotCustomTypeService.addCustomType(sysScenicSpotCustomType);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("自定义类型添加成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (i == 2){
                returnModel.setData("");
                returnModel.setMsg("当前菜单已存在此编号，请修改！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("自定义类型添加失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("自定义类型添加失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 修改自定义类型
     * @Date 10:00 2020/7/28
     * @Param [sysScenicSpotCustomType]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editCustomType")
    @ResponseBody
    public ReturnModel editCustomType(SysScenicSpotCustomType sysScenicSpotCustomType){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotCustomTypeService.editCustomType(sysScenicSpotCustomType);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("自定义类型修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("自定义类型修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("自定义类型修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

}
