package com.hna.hka.archive.management.business.controller;


import com.hna.hka.archive.management.business.model.BusinessFilingMessage;
import com.hna.hka.archive.management.business.model.BusinessFilingSpot;
import com.hna.hka.archive.management.business.service.BusinessFilingSpotService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/3/15 11:03
 * 优质景区列表
 */

@RestController
@RequestMapping("/business/filingSpot")
@Slf4j
@Validated
public class BusinessFilingSpotController extends PublicUtil {

    @Autowired
    BusinessFilingSpotService businessFilingSpotService;


    /**
     * 优质景区列表
     *
     * @return
     */
    @RequestMapping("/getFilingSpotList")
    @ResponseBody
    public PageDataResult getFilingSpotList(@RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("pageSize") Integer pageSize, BusinessFilingSpot businessFilingSpot) {

        Map<String, Object> search = new HashMap<>();
        search.put("highQuality",businessFilingSpot.getHighQuality());
        PageDataResult pageDataResult  = businessFilingSpotService.getFilingSpotList(pageNum,pageSize,search);

        return pageDataResult;

    }

    /**
     * 优质景区添加
     *
     * @return
     */
    @RequestMapping("/addFilingSpot")
    @ResponseBody
    public ReturnModel addFilingSpot( BusinessFilingSpot businessFilingSpot) {
        ReturnModel returnModel = new ReturnModel();
        int i = businessFilingSpotService.addFilingSpot(businessFilingSpot);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("添加成功");
            return returnModel;
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("添加失败");
            return returnModel;
        }

    }

    /**
     * 优质景区修改
     *
     * @return
     */
    @RequestMapping("/editFilingSpot")
    @ResponseBody
    public ReturnModel editFilingSpot( BusinessFilingSpot businessFilingSpot) {
        ReturnModel returnModel = new ReturnModel();
        int i = businessFilingSpotService.editFilingSpot(businessFilingSpot);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功");
            return returnModel;
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败");
            return returnModel;
        }

    }


    /**
     * 优质景区删除
     *
     * @return
     */
    @RequestMapping("/delFilingSpot")
    @ResponseBody
    public ReturnModel delFilingSpot(Long id) {
        ReturnModel returnModel = new ReturnModel();
        int i = businessFilingSpotService.delFilingSpot(id);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("删除成功");
            return returnModel;
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("删除失败");
            return returnModel;
        }

    }





}
