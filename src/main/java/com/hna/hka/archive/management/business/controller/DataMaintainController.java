package com.hna.hka.archive.management.business.controller;

import com.hna.hka.archive.management.business.model.BusinessDataMaintain;
import com.hna.hka.archive.management.business.service.BusinessDataMaintainService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
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
 * @ClassName: DataMaintainController
 * @Author: 郭凯
 * @Description: 数据维护控制层
 * @Date: 2020/8/11 16:05
 * @Version: 1.0
 */
@RequestMapping("/business/dataMaintain")
@Controller
public class DataMaintainController extends PublicUtil {

    @Autowired
    private BusinessDataMaintainService businessDataMaintainService;

    /**
     * @Author 郭凯
     * @Description 数据维护列表查询
     * @Date 16:37 2020/8/11
     * @Param [pageNum, pageSize, businessDataMaintain]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getDataMaintainList")
    @ResponseBody
    public PageDataResult getDataMaintainList(@RequestParam("pageNum") Integer pageNum,
                                               @RequestParam("pageSize") Integer pageSize, BusinessDataMaintain businessDataMaintain) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("phone",businessDataMaintain.getPhone());
            pageDataResult = businessDataMaintainService.getDataMaintainList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("数据维护列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 修改已读状态
     * @Date 17:26 2020/8/11
     * @Param [businessDataMaintain]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editState")
    @ResponseBody
    public ReturnModel editState(BusinessDataMaintain businessDataMaintain) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessDataMaintainService.editState(businessDataMaintain);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("状态修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("状态修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("editState", e);
            returnModel.setData("");
            returnModel.setMsg("状态修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 数据维护删除
     * @Date 17:41 2020/8/11
     * @Param [id]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delDataMaintain")
    @ResponseBody
    public ReturnModel delDataMaintain(@RequestParam("id") Long id) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessDataMaintainService.delDataMaintain(id);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("数据维护删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("数据维护删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("delDataMaintain", e);
            returnModel.setData("");
            returnModel.setMsg("数据维护删除失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

}
