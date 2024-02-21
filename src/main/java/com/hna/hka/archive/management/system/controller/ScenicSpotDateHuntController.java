package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.model.SysScenicSpotDateHunt;
import com.hna.hka.archive.management.system.model.SysScenicSpotName;
import com.hna.hka.archive.management.system.service.SysScenicSpotDateHuntService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.ReturnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/3/10 9:13
 * 随机寻宝活动宝箱时间管理
 *
 */


@RequestMapping("/system/scenicSpotDateHunt")
@Controller
public class ScenicSpotDateHuntController {

    @Autowired
    SysScenicSpotDateHuntService sysScenicSpotDateHuntService;

    @Autowired
    private HttpSession session;
    /**
     * 新增
     * @param sysScenicSpotDateHunt
     * @return
     */
    @RequestMapping("addSpotDateHunt")
    @ResponseBody
    public ReturnModel addSpotDateHunt(SysScenicSpotDateHunt sysScenicSpotDateHunt){

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotDateHuntService.addSpotDateHunt(sysScenicSpotDateHunt);
        if (i==1){
            returnModel.setData("");
            returnModel.setMsg("新增成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }else{
            returnModel.setData("");
            returnModel.setMsg("新增失败");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 修改
     */
    @RequestMapping("exitSpotDateHunt")
    @ResponseBody
    public ReturnModel exitSpotDateHunt(SysScenicSpotDateHunt sysScenicSpotDateHunt){

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotDateHuntService.editSpotDateHunt(sysScenicSpotDateHunt);
        if (i==1){
            returnModel.setData("");
            returnModel.setMsg("修改成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }else{
            returnModel.setData("");
            returnModel.setMsg("修改失败");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }

    }

    /**
     * 删除
     */
    @RequestMapping("delSpotDateHunt")
    @ResponseBody
    public ReturnModel delSpotDateHunt(Long dateTreasureId){
        ReturnModel returnModel = new ReturnModel();

        int i =  sysScenicSpotDateHuntService.delSpotDateHunt(dateTreasureId);
        if (i==1){
            returnModel.setData("");
            returnModel.setMsg("删除成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }else{
            returnModel.setData("");
            returnModel.setMsg("删除失败");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }

    }

    /**
     * 查询
     */
    @RequestMapping("/getSpotDateHuntList")
    @ResponseBody
    public PageDataResult getSpotDateHuntList(@RequestParam("pageNum") Integer pageNum,
                                          @RequestParam("pageSize") Integer pageSize, SysScenicSpotDateHunt sysScenicSpotDateHunt){
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();


        search.put("spotId",session.getAttribute("scenicSpotId").toString());

        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysScenicSpotDateHuntService.getSpotDateHuntList(pageNum,pageSize,search);
        }catch (Exception e){
            e.printStackTrace();
        }

        return pageDataResult;

    }

    /**
     * 获取当前景区所有的时间段id和对应时间段
     * zhang
     */

    @RequestMapping("/getSpotDateHuntIdList")
    @ResponseBody
    public PageDataResult getSpotDateHuntIdList() {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        search.put("spotId",session.getAttribute("scenicSpotId"));
        try {
//            if(null == pageNum) {
//                pageNum = 1;
//            }
//            if(null == pageSize) {
//                pageSize = 10;
//            }
            pageDataResult = sysScenicSpotDateHuntService.getSpotDateHuntIdList(search);
        }catch (Exception e){
            e.printStackTrace();
//            logger.info("景区名称列表查询失败",e);
        }
        return pageDataResult;
    }






}
