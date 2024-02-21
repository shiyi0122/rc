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
 * @ClassName: CueToneTypeController
 * @Author: 郭凯
 * @Description: 提示音类型控制层
 * @Date: 2020/9/10 17:35
 * @Version: 1.0
 */
@RequestMapping("/system/cueToneType")
@Controller
public class CueToneTypeController extends PublicUtil {

    @Autowired
    private SysScenicSpotCustomTypeService sysScenicSpotCustomTypeService;

    /**
     * @Author 郭凯
     * @Description 提示音类型列表查询
     * @Date 17:38 2020/9/10
     * @Param [pageNum, pageSize, sysScenicSpotCustomType]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping(value = "/getCueToneTypeList", method = RequestMethod.GET)
    @ResponseBody
    public PageDataResult getCueToneTypeList(@RequestParam("pageNum") Integer pageNum,
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
            pageDataResult = sysScenicSpotCustomTypeService.getCueToneTypeList(pageNum,pageSize,search);
        } catch (Exception e) {
            logger.error("提示音类型列表查询异常！", e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 提示音类型新增
     * @Date 10:57 2020/9/11
     * @Param [sysScenicSpotCustomType]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addCueToneType")
    @ResponseBody
    public ReturnModel addCueToneType(SysScenicSpotCustomType sysScenicSpotCustomType){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotCustomTypeService.addCueToneType(sysScenicSpotCustomType);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("提示音类型添加成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (i == 2){
                returnModel.setData("");
                returnModel.setMsg("此状态已被占用，请修改！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("提示音类型添加失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("提示音类型添加失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 提示音类型修改
     * @Date 11:17 2020/9/11
     * @Param [sysScenicSpotCustomType]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editCueToneType")
    @ResponseBody
    public ReturnModel editCueToneType(SysScenicSpotCustomType sysScenicSpotCustomType){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotCustomTypeService.editCustomType(sysScenicSpotCustomType);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("提示音类型修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("提示音类型修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("提示音类型修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

}
