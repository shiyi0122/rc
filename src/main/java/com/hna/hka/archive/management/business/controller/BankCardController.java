package com.hna.hka.archive.management.business.controller;

import com.hna.hka.archive.management.business.model.BusinessBankCard;
import com.hna.hka.archive.management.business.model.BusinessBankCardHis;
import com.hna.hka.archive.management.business.service.BusinessBankCardService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import com.hna.hka.archive.management.system.util.ToolUtil;

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
 * @ClassName: BankCardController
 * @Author: 郭凯
 * @Description: 银行卡管理控制层
 * @Date: 2020/8/12 13:54
 * @Version: 1.0
 */
@RequestMapping("/business/bankCard")
@Controller
public class BankCardController extends PublicUtil {

    @Autowired
    private BusinessBankCardService businessBankCardService;

    /**
     * @Author 郭凯
     * @Description 银行卡管理列表查询
     * @Date 14:24 2020/8/12
     * @Param [pageNum, pageSize, businessBankCard]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getBankCardList")
    @ResponseBody
    public PageDataResult getBankCardList(@RequestParam("pageNum") Integer pageNum,
                                        @RequestParam("pageSize") Integer pageSize, BusinessBankCard businessBankCard) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("telephone",businessBankCard.getTelephone());
            search.put("userName",businessBankCard.getUserName());
            search.put("state", businessBankCard.getState());
            pageDataResult = businessBankCardService.getBankCardList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("银行卡管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 修改通过或者驳回状态
     * @Date 15:49 2020/8/12
     * @Param [businessBankCard]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editAdopt")
    @ResponseBody
    public ReturnModel editAdopt(BusinessBankCard businessBankCard){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessBankCardService.editAdopt(businessBankCard);
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
        }catch (Exception e){
            logger.error("editAdopt", e);
            returnModel.setData("");
            returnModel.setMsg("状态修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }
    
    /**
    * @Author 郭凯
    * @Description: 查询银行卡最新提交记录
    * @Title: getBankCardByUserId
    * @date  2021年1月13日 下午1:03:05 
    * @param @param userId
    * @param @return
    * @return ReturnModel
    * @throws
     */
    @RequestMapping("/getBankCardByUserId")
    @ResponseBody
    public ReturnModel getBankCardByUserId(Long userId){
    	ReturnModel returnModel = new ReturnModel();
    	try {
    		BusinessBankCardHis bankCardHis = businessBankCardService.getBankCardByUserId(userId);
    		if (ToolUtil.isNotEmpty(bankCardHis)) {
    			returnModel.setData(bankCardHis);
    			returnModel.setMsg("查询成功！");
    			returnModel.setState(Constant.STATE_SUCCESS);
    			return returnModel;
    		}else {
    			returnModel.setData("");
    			returnModel.setMsg("查询失败！");
    			returnModel.setState(Constant.STATE_FAILURE);
    			return returnModel;
    		}
    	}catch (Exception e){
    		logger.error("getBankCardByUserId", e);
    		returnModel.setData("");
    		returnModel.setMsg("查询失败！");
    		returnModel.setState(Constant.STATE_FAILURE);
    		return returnModel;
    	}
    }

}
