package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysScenicSpotRechargeAmount;
import com.hna.hka.archive.management.system.service.SysScenicSpotRechargeAmountService;
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
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: RechargeAmountController
 * @Author: 郭凯
 * @Description: 储值充值管理控制层
 * @Date: 2020/7/11 16:00
 * @Version: 1.0
 */
@RequestMapping("/system/rechargeAmount")
@Controller
public class RechargeAmountController extends PublicUtil {

    @Autowired
    private SysScenicSpotRechargeAmountService sysScenicSpotRechargeAmountService;

    /**
     * @Author 郭凯
     * @Description 储值充值列表查询
     * @Date 17:06 2020/7/11
     * @Param [pageNum, pageSize, sysScenicSpotRechargeAmount]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getRechargeAmountList")
    @ResponseBody
    public PageDataResult getRechargeAmountList(@RequestParam("pageNum") Integer pageNum,
                                               @RequestParam("pageSize") Integer pageSize, SysScenicSpotRechargeAmount sysScenicSpotRechargeAmount) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysScenicSpotRechargeAmountService.getRechargeAmountList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("储值充值列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 储值充值新增成功
     * @Date 15:15 2020/7/13
     * @Param [sysScenicSpotRechargeAmount]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addRechargeAmount")
    @ResponseBody
    public ReturnModel addRechargeAmount(SysScenicSpotRechargeAmount sysScenicSpotRechargeAmount) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotRechargeAmountService.addRechargeAmount(sysScenicSpotRechargeAmount);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("储值充值新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("储值充值新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("addRechargeAmount", e);
            returnModel.setData("");
            returnModel.setMsg("储值充值新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 删除储值充值
     * @Date 16:04 2020/7/13
     * @Param [rechargeId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delRechargeAmount")
    @ResponseBody
    public ReturnModel delRechargeAmount(@NotBlank(message = "ID不能为空")Long rechargeId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotRechargeAmountService.delRechargeAmount(rechargeId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("储值充值删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("储值充值删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("储值充值删除失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 修改储值充值
     * @Date 16:25 2020/7/13
     * @Param [sysScenicSpotRechargeAmount]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editRechargeAmount")
    @ResponseBody
    public ReturnModel editRechargeAmount(SysScenicSpotRechargeAmount sysScenicSpotRechargeAmount) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotRechargeAmountService.editRechargeAmount(sysScenicSpotRechargeAmount);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("储值充值修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("储值充值修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("editRechargeAmount", e);
            returnModel.setData("");
            returnModel.setMsg("储值充值修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

}
