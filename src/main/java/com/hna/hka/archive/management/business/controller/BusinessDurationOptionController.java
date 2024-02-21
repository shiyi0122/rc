package com.hna.hka.archive.management.business.controller;

import com.hna.hka.archive.management.business.model.BusinessArticle;
import com.hna.hka.archive.management.business.model.BusinessDurationOption;
import com.hna.hka.archive.management.business.service.BusinessDurationOptionService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/19 16:53
 * 直播收费金额相关配置
 */
@RequestMapping("/business/durationOption")
@Controller
public class BusinessDurationOptionController extends PublicUtil {

    @Autowired
    BusinessDurationOptionService businessDurationOptionService;

    /**
     * 直播收费金额列表
     * @param pageNum
     * @param pageSize
     * @param durationOption
     * @return
     */
    @RequestMapping("/getDurationOptionList")
    @ResponseBody
    public PageDataResult getDurationOptionList(@RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize, BusinessDurationOption durationOption) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            if (!StringUtils.isEmpty(durationOption.getScenicSpotId())){
                search.put("scenicSpotId",durationOption.getScenicSpotId().toString());
            }
            pageDataResult = businessDurationOptionService.getDurationOptionList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * 添加直播收费金额
     * @param durationOption
     * @return
     */
    @RequestMapping("/addDurationOption")
    @ResponseBody
    public ReturnModel addDurationOption( BusinessDurationOption durationOption) {

        ReturnModel returnModel = new ReturnModel();

        int i = businessDurationOptionService.addDurationOption(durationOption);

        if (i> 0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功!");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败!");
        }
        return returnModel;
    }

    /**
     * 修改直播收费金额
     * @param durationOption
     * @return
     */
    @RequestMapping("/editDurationOption")
    @ResponseBody
    public ReturnModel editDurationOption(BusinessDurationOption durationOption) {

        ReturnModel returnModel = new ReturnModel();

        int i = businessDurationOptionService.editDurationOption(durationOption);

        if (i> 0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功!");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败!");
        }
        return returnModel;
    }


    /**
     * 删除直播金额选项
     * @param durationOption
     * @return
     */
    @RequestMapping("/delDurationOption")
    @ResponseBody
    public ReturnModel delDurationOption( BusinessDurationOption durationOption) {

        ReturnModel returnModel = new ReturnModel();

        int i = businessDurationOptionService.delDurationOption(durationOption);

        if (i> 0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功!");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败!");
        }
        return returnModel;
    }


}
