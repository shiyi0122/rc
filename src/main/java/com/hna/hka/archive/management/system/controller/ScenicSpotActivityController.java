package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysScenicSpotActivity;
import com.hna.hka.archive.management.system.service.SysScenicSpotActivityService;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 郭凯
 * @version V1.0
 * @ClassName: ScenicSpotActivityController
 * @Description: 优惠卷管理控制层
 * @date 2020年12月21日
 */

@RequestMapping("/system/scenicSpotActivity")
@Controller
public class ScenicSpotActivityController extends PublicUtil {

    @Autowired
    private SysScenicSpotActivityService sysScenicSpotActivityService;


    /**
     * @param @param  pageNum
     * @param @param  pageSize
     * @param @param  sysScenicSpotActivity
     * @param @return
     * @return PageDataResult
     * @throws
     * @Author 郭凯
     * @Description: 优惠政策列表查询
     * @Title: getScenicSpotActivityList
     * @date 2020年12月21日 下午3:18:17
     */
    @RequestMapping("/getScenicSpotActivityList")
    @ResponseBody
    public PageDataResult getScenicSpotActivityList(@RequestParam("pageNum") Integer pageNum,
                                                    @RequestParam("pageSize") Integer pageSize, SysScenicSpotActivity sysScenicSpotActivity) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        try {
            if (null == pageNum) {
                pageNum = 1;
            }
            if (null == pageSize) {
                pageSize = 10;
            }
            search.put("activityScenicSpotId", sysScenicSpotActivity.getActivityScenicSpotId());
            pageDataResult = sysScenicSpotActivityService.getScenicSpotActivityList(pageNum, pageSize, search);
        } catch (Exception e) {
            logger.info("优惠政策列表查询失败", e);
        }
        return pageDataResult;
    }

    /**
     * @param @param  sysRole
     * @param @return
     * @return ReturnModel
     * @throws
     * @Author 郭凯
     * @Description: 新增优惠政策
     * @Title: addScenicSpotActivity
     * @date 2020年12月21日 下午5:42:54
     */
    @RequestMapping("/addScenicSpotActivity")
    @ResponseBody
    public ReturnModel addScenicSpotActivity(SysScenicSpotActivity sysScenicSpotActivity) {
        ReturnModel returnModel = null;
        try {
            returnModel = new ReturnModel();
            int i = sysScenicSpotActivityService.addScenicSpotActivity(sysScenicSpotActivity);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("优惠政策新增成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else if (i == 2) {
                returnModel.setData("");
                returnModel.setMsg("优惠政策新增失败,(此优惠劵已存在)");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else if (i == 3) {
                returnModel.setData("");
                returnModel.setMsg("优惠政策新增失败!(活动类型为满多少钱可以领取时，优惠基础必须填写！)");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("优惠政策新增失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("addScenicSpotActivity", e);
            returnModel.setData("");
            returnModel.setMsg("优惠政策新增失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @param @param  activityId
     * @param @return
     * @return ReturnModel
     * @throws
     * @Author 郭凯
     * @Description: 优惠政策删除
     * @Title: delScenicSpotActivity
     * @date 2020年12月21日 下午5:57:38
     */
    @RequestMapping("/delScenicSpotActivity")
    @ResponseBody
    public ReturnModel delScenicSpotActivity(Long activityId) {
        ReturnModel returnModel = null;
        try {
            returnModel = new ReturnModel();
            int i = sysScenicSpotActivityService.delScenicSpotActivity(activityId);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("优惠政策删除成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("优惠政策删除失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("delScenicSpotActivity", e);
            returnModel.setData("");
            returnModel.setMsg("优惠政策删除失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @param @return
     * @return ReturnModel
     * @throws
     * @Author 郭凯
     * @Description: 查询优惠列表
     * @Title: getScenicSpotActivity
     * @date 2020年12月22日 下午5:29:55
     */
    @RequestMapping("/getScenicSpotActivity")
    @ResponseBody
    public ReturnModel getScenicSpotActivity() {
        ReturnModel returnModel = null;
        try {
            returnModel = new ReturnModel();
            List<SysScenicSpotActivity> activityList = sysScenicSpotActivityService.getScenicSpotActivity();
            if (ToolUtil.isNotEmpty(activityList)) {
                returnModel.setData(activityList);
                returnModel.setMsg("查询成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("查询失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("getScenicSpotActivity", e);
            returnModel.setData("");
            returnModel.setMsg("查询失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 修改政策启用禁用
     *
     * @return
     */
    @RequestMapping("/editActivityFailure")
    @ResponseBody
    public ReturnModel getActivityFailure(SysScenicSpotActivity sysScenicSpotActivity) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotActivityService.getActivityFailure(sysScenicSpotActivity);

        if (i < 1) {
            returnModel.setData("");
            returnModel.setMsg("状态修改失败");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        } else {
            returnModel.setData("");
            returnModel.setMsg("状态修改成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }

    }

    /**
     * 编辑优惠政策
     *
     * @param sysScenicSpotActivity
     * @return
     */
    @RequestMapping("/editScenicSpotActivity")
    @ResponseBody
    public ReturnModel editScenicSpotActivity(SysScenicSpotActivity sysScenicSpotActivity) {

        ReturnModel returnModel = new ReturnModel();

        int i = sysScenicSpotActivityService.editScenicSpotActivity(sysScenicSpotActivity);
        if (i < 1) {
            returnModel.setData("");
            returnModel.setMsg("编辑失败");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        } else {
            returnModel.setData("");
            returnModel.setMsg("编辑成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }

    }


    @ApiOperation("VIP优惠券发放")
    @PostMapping("/addvipCoupon")
    public ReturnModel addvipCoupon(String userPhone, Long scenicSpotId, Long number) {
        ReturnModel returnModel = new ReturnModel();
        int i = sysScenicSpotActivityService.addvipCoupon(userPhone, scenicSpotId, number);
        if (i == 1) {
            returnModel.setData("");
            returnModel.setMsg("VIP优惠券发放成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } else if (i == -1) {
            returnModel.setData("");
            returnModel.setMsg("用户不存在！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        } else if (i == -2) {
            returnModel.setData("");
            returnModel.setMsg("景区不存在！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        } else {
            returnModel.setData("");
            returnModel.setMsg("VIP优惠券发放失败！（/addvipCoupon）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }
}
