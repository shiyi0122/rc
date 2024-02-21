package com.hna.hka.archive.management.appSystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.model.SysCurrentUserExchange;
import com.hna.hka.archive.management.appSystem.service.AppCodeScanCodePrizeCashingService;
import com.hna.hka.archive.management.appSystem.service.AppUserService;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.system.model.SysCurrentUser;
import com.hna.hka.archive.management.system.util.*;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/4/11 14:47
 */

@RequestMapping("/appSystem/AppCodeScanCodePrizeCash")
@Controller
@CrossOrigin
public class AppCodeScanCodePrizeCashingController extends PublicUtil {


    @Autowired
    AppCodeScanCodePrizeCashingService appCodeScanCodePrizeCashingService;
    @Autowired
    private AppUserService appUserService;


    /**
     * 扫码兑奖
     *
     * @param: exchangeNumber 兑奖编号
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2021/11/25 0025
     */
//    @ApiOperation("兑换奖品")
//    @ApiImplicitParams({@ApiImplicitParam(name ="exchangeNumber", value = "兑奖编号",dataType="string", required = true)})
    @RequestMapping(value = "/exchangePrize", method = RequestMethod.POST)
    @ResponseBody
    public ReturnModel exchangePrize(String exchangeNumber, String content) {
        ReturnModel returnModel = new ReturnModel();
        Map<String, Object> search = new HashMap<>();
        try {

            if (content == null || "".equals(content)) {
                returnModel.setData("");
                returnModel.setMsg("加密参数不能为空！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                return returnModel;
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (longinTokenId == "" || longinTokenId == null) {
                returnModel.setData("");
                returnModel.setMsg("TokenId为空，请传入TokenId!");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                return returnModel;
            }

            search.put("exchangeNumber", exchangeNumber);
            SysCurrentUserExchange exchange = appCodeScanCodePrizeCashingService.exchangePrize(search);
            if (exchange.getAddressId() != null) {
                returnModel.setData("");
                returnModel.setMsg("您已经绑定地址，无法现场兑换，请现场工作人员和游客确认兑换方式！");
                returnModel.setState(Constant.STATE_FAILURE);
                returnModel.setType(Constant.STATE_FAILURE);
                return returnModel;
            }
            if (exchange.getExchangeState().equals("0") && DateUtil.isEffectiveDates(exchange.getStartValidity(), exchange.getEndValidity())) {

                SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
                exchange.setAccountName(appUsers.getLoginName());
                int i = appCodeScanCodePrizeCashingService.updateExchangePrizeState(exchange);
                returnModel.setData(exchange);
                returnModel.setMsg("兑奖成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
            } else if (exchange.getExchangeState().equals("1") && exchange.getShipmentStatus().equals("4")) {
                returnModel.setData("");
                returnModel.setMsg("此奖品现场已兑换！");
                returnModel.setState(Constant.STATE_FAILURE);
            } else if (!DateUtil.isEffectiveDates(exchange.getStartValidity(), exchange.getEndValidity())) {
                returnModel.setData("");
                returnModel.setMsg("此奖品已过期!");
                returnModel.setState(Constant.STATE_FAILURE);
            }
            return returnModel;
        } catch (Exception e) {
            logger.info("exchangePrize", e);
            returnModel.setData("");
            returnModel.setMsg("兑奖失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * 查询奖品列表
     *
     * @param: exchangeState
     * @param: scenicSpotId
     * @param: pageNum
     * @param: pageSize
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.util.ReturnModel
     * @author: qushaobei
     * @date: 2021/11/25 0025
     */
//    @ApiOperation("奖品列表")
    @RequestMapping(value = "/queryExchangePrizeList", method = RequestMethod.POST)
    @ResponseBody
    public ReturnModel queryExchangePrizeList(String exchangeState,
                                              String scenicSpotId,
                                              int pageNum,
                                              int pageSize) {
        ReturnModel returnModel = new ReturnModel();
        Map<String, Object> search = new HashMap<>();
        try {
            search.put("scenicSpotId", scenicSpotId);
            search.put("exchangeState", exchangeState);
            List<SysCurrentUserExchange> exchangeList = appCodeScanCodePrizeCashingService.queryExchangePrizeList(pageNum, pageSize, search);
            //PageInfo就是一个分页Bean
            PageInfo pageInfo = new PageInfo(exchangeList);
            returnModel.setData(pageInfo);
            returnModel.setMsg("成功获取奖品列表！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } catch (Exception e) {
            logger.info("queryExchangePrizeList", e);
            returnModel.setData("");
            returnModel.setMsg("获取奖品列表失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


}
