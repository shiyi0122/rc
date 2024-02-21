package com.hna.hka.archive.management.business.controller;

import com.hna.hka.archive.management.business.model.BusinessCooperate;
import com.hna.hka.archive.management.business.service.BusinessCooperateService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.controller
 * @ClassName: BusinessCooperateController
 * @Author: 郭凯
 * @Description: 供应商和已有景区我想合作管理控制层
 * @Date: 2020/6/19 14:31
 * @Version: 1.0
 */
@RequestMapping("/business/businessCooperate")
@Controller
public class BusinessCooperateController extends PublicUtil {

    @Autowired
    private BusinessCooperateService businessCooperateService;

    /**
     * @Author 郭凯
     * @Description 供应商管理列表查询
     * @Date 14:57 2020/6/19
     * @Param [pageNum, pageSize, businessCooperate]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getBusinessCooperateList")
    @ResponseBody
    public PageDataResult getBusinessCooperateList(@RequestParam("pageNum") Integer pageNum,
                                               @RequestParam("pageSize") Integer pageSize, BusinessCooperate businessCooperate) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("phone",businessCooperate.getPhone());
            search.put("type","0");
            pageDataResult = businessCooperateService.getBusinessCooperateList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("供应商管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 删除
     * @Date 16:14 2020/6/19
     * @Param [id]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delCooperate")
    @ResponseBody
    public ReturnModel delCooperate(@NotBlank(message = "ID不能为空")Long id){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessCooperateService.delCooperate(id);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("删除失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 已有景区想合作列表查询
     * @Date 16:31 2020/6/19
     * @Param [pageNum, pageSize, businessCooperate]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getBusinessCooperationList")
    @ResponseBody
    public PageDataResult getBusinessCooperationList(@RequestParam("pageNum") Integer pageNum,
                                                   @RequestParam("pageSize") Integer pageSize, BusinessCooperate businessCooperate) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("phone",businessCooperate.getPhone());
            search.put("type","1");
            pageDataResult = businessCooperateService.getBusinessCooperateList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("已有景区想合作列表查询失败",e);
        }
        return pageDataResult;
    }

}
