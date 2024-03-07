package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysScenicSpotTreasureHuntRule;
import com.hna.hka.archive.management.system.service.SysScenicSpotTreasureHuntRuleService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: TreasureHuntController
 * @Author: 张
 * @Description: 寻宝
 * @Date: 2021/12/9 9:31
 * @Version: 1.0
 */
@Api(tags = "景点寻宝规则相关")
@RestController
@RequestMapping("/system/treasureHuntRule")
public class TreasureHuntRuleController extends PublicUtil {

    @Autowired
    private SysScenicSpotTreasureHuntRuleService sysScenicSpotTreasureHuntRuleService;

    @Autowired
    private HttpSession session;

    @ApiOperation("查询寻宝景点规则列表")
    @ResponseBody
    @GetMapping("/getTreasureHuntRuleList")
    public PageDataResult getBroadcastList(@RequestParam("pageNum") Integer pageNum,
                                           @RequestParam("pageSize") Integer pageSize, SysScenicSpotTreasureHuntRule sysScenicSpotTreasureHuntRule) {
        PageDataResult pageDataResult = new PageDataResult();
        try {
            if (null == pageNum) {
                pageNum = 1;
            }
            if (null == pageSize) {
                pageSize = 10;
            }
//            sysScenicSpotTreasureHuntRule.getRuleType();
            sysScenicSpotTreasureHuntRule.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            pageDataResult = sysScenicSpotTreasureHuntRuleService.getBroadcastRuleList(pageNum, pageSize, sysScenicSpotTreasureHuntRule);
        } catch (Exception e) {
            logger.info("景点规则列表查询失败", e);
        }
        return pageDataResult;
    }

    @ApiOperation("查询寻宝景点规则列表(新)")
    @ResponseBody
    @GetMapping("/getTreasureHuntRuleNewList")
    public PageDataResult getBroadcastNewList(@RequestParam("pageNum") Integer pageNum,
                                              @RequestParam("pageSize") Integer pageSize, SysScenicSpotTreasureHuntRule sysScenicSpotTreasureHuntRule) {
        PageDataResult pageDataResult = new PageDataResult();
        try {
            if (null == pageNum) {
                pageNum = 1;
            }
            if (null == pageSize) {
                pageSize = 10;
            }
//            sysScenicSpotTreasureHuntRule.setScenicSpotId(Long.valueOf("15698320289682"));
            sysScenicSpotTreasureHuntRule.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            sysScenicSpotTreasureHuntRule.setRuleType("1");
            pageDataResult = sysScenicSpotTreasureHuntRuleService.getBroadcastRuleList(pageNum, pageSize, sysScenicSpotTreasureHuntRule);
        } catch (Exception e) {
            logger.info("景点规则列表查询失败", e);
        }
        return pageDataResult;
    }

    @ApiOperation("添加寻宝景点规则")
    @ResponseBody
    @RequestMapping("/addBroadcastHuntRule")
    public ReturnModel addScenicBroadcastRule(@RequestParam("file") MultipartFile file, SysScenicSpotTreasureHuntRule sysScenicSpotTreasureHuntRule) {
        ReturnModel returnModel = new ReturnModel();
        try {

            SysScenicSpotTreasureHuntRule broadcastRuleBySpotId = sysScenicSpotTreasureHuntRuleService.selectBroadcastRuleBySpotId(sysScenicSpotTreasureHuntRule.getScenicSpotId(), sysScenicSpotTreasureHuntRule.getRuleType());
            if (!StringUtils.isEmpty(broadcastRuleBySpotId)) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点规则添加失败，已有寻宝景点规则！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
            int i = sysScenicSpotTreasureHuntRuleService.addBroadcastRule(sysScenicSpotTreasureHuntRule, file);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点规则新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点规则新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("addBroadcast", e);
            returnModel.setData("");
            returnModel.setMsg("寻宝景点规则新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @ApiOperation("修改寻宝景点规则")
    @ResponseBody
    @RequestMapping("/editBroadcastHuntRule")
    public ReturnModel editScenicBroadcastRule(@RequestParam("file") MultipartFile file, SysScenicSpotTreasureHuntRule sysScenicSpotTreasureHuntRule) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotTreasureHuntRuleService.editBroadcastRule(sysScenicSpotTreasureHuntRule, file);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点规则修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点规则修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("editBroadcast", e);
            returnModel.setData("");
            returnModel.setMsg("寻宝景点规则修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author zhang
     * @Description 删除寻宝景点规则
     * @Param [broadcastId]
     **/
    @RequestMapping("/delBroadcastHuntRule")
    @ResponseBody
    public ReturnModel delBroadcastRule(@NotBlank(message = "id不能为空") Long ruleId, @NotBlank(message = "景区ID不能为空") Long scenicSpotId) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotTreasureHuntRuleService.delBroadcastRule(ruleId, scenicSpotId);
            if (i > 0) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点规则删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点规则删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("寻宝景点规则删除失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


}
