package com.hna.hka.archive.management.wenYuRiverInterface.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.service.SysOrderService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;
import com.hna.hka.archive.management.wenYuRiverInterface.model.WenYuRiverOrder;
import com.hna.hka.archive.management.wenYuRiverInterface.util.ReflectionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.wenYuRiverInterface.controller
 * @ClassName: OrderInterfaceController
 * @Author: 郭凯
 * @Description: 外部订单接口提供
 * @Date: 2021/5/27 17:05
 * @Version: 1.0
 */
@Api(tags = "外部订单接口提供")
@RequestMapping("/system/orderInterface")
@Controller
public class OrderInterfaceController extends PublicUtil {

    @Autowired
    private SysOrderService sysOrderService;

    /**
     * @Method getOrderList
     * @Author 郭凯
     * @Version  1.0
     * @Description 外部订单接口数据查询
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/5/27 17:09
     */
    @ApiOperation("外部订单接口数据查询")
    @RequestMapping("/getOrderList")
    @ResponseBody
    public ReturnModel getOrderList(BaseQueryVo BaseQueryVo , @NotNull(message = "scenicSpotId") String scenicSpotId, @NotNull(message = "开始时间为空")String startDate , @NotNull(message = "结束时间为空")String endDate, String orderRobotCode){
        ReturnModel returnModel = new ReturnModel();
        try {
            Map<String,String> search = new HashMap<>();

            if (org.springframework.util.StringUtils.isEmpty(startDate) || org.springframework.util.StringUtils.isEmpty(endDate)){
                search.put("startDate", DateUtil.crutDate());
                search.put("endDate",DateUtil.addDay(DateUtil.crutDate(),1));
            }else{
                search.put("startDate", startDate);
                search.put("endDate",DateUtil.addDay(endDate,1));
            }
//            search.put("startDate",startDate);
//            search.put("endDate",endDate);
            search.put("orderRobotCode",orderRobotCode);
            search.put("orderScenicSpotId",scenicSpotId);
            BaseQueryVo.setSearch(search);
            PageInfo<WenYuRiverOrder> page = sysOrderService.getOrderInterfaceList(BaseQueryVo);
            List<Map<String,Object>> dataList = dealOrderData(page.getList());
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("list",dataList);
            dataMap.put("pages",page.getPages());
            dataMap.put("pageNum",page.getPageNum());
            returnModel.setData(dataMap);
            returnModel.setMsg("");
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setType(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("接口超时");
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setType(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description 数据格式封装
     * @Return
     * @Date 2021/5/19 16:50
     */
    public List<Map<String,Object>> dealOrderData(List<WenYuRiverOrder> list){
        List<Map<String,Object>> dataList = list.stream().map(orders -> {
            Map<String,Object> map = ReflectionUtils.getDestJson(new String[]{"currentUserPhone:currentUserPhone","orderRobotCode:orderRobotCode","orderScenicSpotName:orderScenicSpotName",
                    "orderStartTime:orderStartTime","orderEndTime:orderEndTime","totalTime:totalTime","actualAmount:actualAmount","dispatchingFee:dispatchingFee",
                    "orderAndDeductible:orderAndDeductible","orderRefundAmount:orderRefundAmount","realIncome:realIncome","orderStatus:orderStatus"},orders);
            return map;
        }).collect(Collectors.toList());
        return dataList;
    }
}
