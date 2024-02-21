package com.hna.hka.archive.management.system.controller;

import com.alibaba.fastjson.JSON;
import com.hna.hka.archive.management.assetsSystem.model.ScenicSpot;
import com.hna.hka.archive.management.business.model.BusinessScenicSpotArea;
import com.hna.hka.archive.management.business.model.BusinessWorldArea;
import com.hna.hka.archive.management.business.service.BusinessScenicSpotExpandService;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.service.SysScenicSpotBroadcastService;
import com.hna.hka.archive.management.system.service.SysScenicSpotCapPriceLogService;
import com.hna.hka.archive.management.system.service.SysScenicSpotGpsCoordinateService;
import com.hna.hka.archive.management.system.service.SysScenicSpotService;
import com.hna.hka.archive.management.system.util.*;
import com.rabbitmq.tools.json.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: ScenicSpotController
 * @Author: 郭凯
 * @Description: 景区管理控制层
 * @Date: 2020/5/21 13:31
 * @Version: 1.0
 */
@Api(tags = "电子围栏")
@RequestMapping("/system/scenicSpot")
@Controller
public class ScenicSpotController extends PublicUtil {

    @Autowired
    private SysScenicSpotService sysScenicSpotService;

    @Autowired
    private SysScenicSpotGpsCoordinateService sysScenicSpotGpsCoordinateService;

    @Autowired
    private SysScenicSpotBroadcastService sysScenicSpotBroadcastService;

    @Autowired
    private BusinessScenicSpotExpandService businessScenicSpotExpandService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private HttpSession session;

    @Autowired
    private HttpServletRequest request;

    /**
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     * @Author 郭凯
     * @Description 景区列表查询
     * @Date 13:35 2020/5/21
     * @Param [pageNum, pageSize, sysScenicSpot]
     **/
    @RequestMapping("/getScenicSpotList")
    @ResponseBody
    public PageDataResult getScenicSpotList(@RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("pageSize") Integer pageSize, SysScenicSpot sysScenicSpot) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String, String> search = new HashMap<>();
        SysUsers user = this.getSysUser();
        search.put("userId", user.getUserId().toString());
        search.put("scenicSpotName", sysScenicSpot.getScenicSpotName());
        search.put("robotWakeupWords", sysScenicSpot.getRobotWakeupWords());
        try {
            if (null == pageNum) {
                pageNum = 1;
            }
            if (null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysScenicSpotService.getScenicSpotList(pageNum, pageSize, search);
        } catch (Exception e) {
            logger.info("景区列表查询失败", e);
        }
        return pageDataResult;
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 查询景区归属地
     * @Date 14:28 2020/5/21
     * @Param []
     **/
    @RequestMapping("/getScenicSpotNameList")
    @ResponseBody
    public ReturnModel getScenicSpotNameList() {
        ReturnModel returnModel = new ReturnModel();
//        Map<String,String> search = new HashMap<>();
//        SysUsers user = this.getSysUser();
//        search.put("userId",user.getUserId().toString());
//        search.put("scenicSpotName",sysScenicSpot.getScenicSpotName());
//        search.put("robotWakeupWords",sysScenicSpot.getRobotWakeupWords());
        try {
//            if(null == pageNum) {
//                pageNum = 1;
//            }
//            if(null == pageSize) {
//                pageSize = 10;
//            }
            List<SysScenicSpot> scenicSpotNameList = sysScenicSpotService.getScenicSpotNameList();
            if (!ToolUtil.isEmpty(scenicSpotNameList)) {
                returnModel.setData(scenicSpotNameList);
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
            logger.info("getScenicSpotBindingList", e);
            returnModel.setData("");
            returnModel.setMsg("查询失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 查询景区归属地
     * @Date 14:28 2020/5/21
     * @Param []
     **/
    @RequestMapping("/getScenicSpotBindingList")
    @ResponseBody
    public ReturnModel getScenicSpotBindingList() {
        ReturnModel returnModel = new ReturnModel();
        try {
            List<SysScenicSpotBinding> SysScenicSpotBinding = sysScenicSpotService.getScenicSpotBindingList();
            if (!ToolUtil.isEmpty(SysScenicSpotBinding)) {
                returnModel.setData(SysScenicSpotBinding);
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
            logger.info("getScenicSpotBindingList", e);
            returnModel.setData("");
            returnModel.setMsg("查询失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 新增景区
     * @Date 15:21 2020/5/21
     * @Param [sysScenicSpot]
     **/
    @RequestMapping("/addScenicSpot")
    @ResponseBody
    public ReturnModel addScenicSpot(SysScenicSpot sysScenicSpot, String type) {
        ReturnModel returnModel = new ReturnModel();
        try {
            SysUsers currentUser = this.getSysUser();
            SysScenicSpotPriceModificationLog modificationLog = new SysScenicSpotPriceModificationLog();
            if (ToolUtil.isNotEmpty(type)) {
                modificationLog.setType(type);
            }
            modificationLog.setPriceModificationUserName(currentUser.getUserName());
            int i = sysScenicSpotService.addScenicSpot(sysScenicSpot, modificationLog);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("景区新增成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("景区新增失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("addScenicSpot", e);
            returnModel.setData("");
            returnModel.setMsg("景区新增失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author zhang
     * @Description 新增景区(添加了扩展景区的添加)
     * @Date
     * @Param [sysScenicSpot]
     **/
    @RequestMapping("/addScenicSpotNew")
    @ResponseBody
    public ReturnModel addScenicSpotNew(SysScenicSpotExpand sysScenicSpotExpand, String type) {
        ReturnModel returnModel = new ReturnModel();
        try {
            SysUsers currentUser = this.getSysUser();
            SysScenicSpotPriceModificationLog modificationLog = new SysScenicSpotPriceModificationLog();
            if (ToolUtil.isNotEmpty(type)) {
                modificationLog.setType(type);
            }

            String province = request.getParameter("province");
            String city = request.getParameter("city");
            String county = request.getParameter("county");
            BusinessScenicSpotArea businessScenicSpotArea = new BusinessScenicSpotArea();
            String mergerName = "";
            if (ToolUtil.isNotEmpty(province)) {
                businessScenicSpotArea.setProvinceId(Integer.parseInt(province));
                //根据ID查询地区名称
                BusinessWorldArea worldArea = businessScenicSpotExpandService.selectProvinceId(province);
                mergerName += worldArea.getName();
            }
            if (ToolUtil.isNotEmpty(city)) {
                businessScenicSpotArea.setCityId(Integer.parseInt(city));
                //根据ID查询地区名称
                BusinessWorldArea worldArea = businessScenicSpotExpandService.selectProvinceId(city);
                mergerName += "-" + worldArea.getName();
            }
            if (ToolUtil.isNotEmpty(county)) {
                businessScenicSpotArea.setRegionId(Integer.parseInt(county));
                //根据ID查询地区名称
                BusinessWorldArea worldArea = businessScenicSpotExpandService.selectProvinceId(county);
                mergerName += "-" + worldArea.getName();
            }
            businessScenicSpotArea.setMergerName(mergerName);


            modificationLog.setPriceModificationUserName(currentUser.getUserName());
            int i = sysScenicSpotService.addScenicSpotNew(sysScenicSpotExpand, modificationLog, businessScenicSpotArea);
            Boolean set = false;
            if (i > 0) {
                SysScenicSpotAndCap sysScenicSpotAndCap = sysScenicSpotService.getSysScenicSpotAndCap(sysScenicSpotExpand.getScenicSpotId());
                JSONObject jsonArray = JSONObject.fromObject(sysScenicSpotAndCap);
                String s = JsonUtils.toString(jsonArray);
                set = redisUtil.set(sysScenicSpotExpand.getScenicSpotId().toString(), s);
            }
            if (i == 1 && set == true) {
                returnModel.setData("");
                returnModel.setMsg("景区新增成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else if (i == 1 && set == false) {
                returnModel.setData("");
                returnModel.setMsg("景区新增成功,缓存存入失败");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("景区修改失败");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("addScenicSpot", e);
            returnModel.setData("");
            returnModel.setMsg("景区新增失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 修改景区信息
     * @Date 17:18 2020/5/21
     * @Param [sysScenicSpot]
     **/
    @RequestMapping("/editScenicSpot")
    @ResponseBody
    public ReturnModel editScenicSpot(SysScenicSpot sysScenicSpot, String type) {
        ReturnModel returnModel = new ReturnModel();
        try {
            SysUsers currentUser = this.getSysUser();
            SysScenicSpotPriceModificationLog modificationLog = new SysScenicSpotPriceModificationLog();
            if (ToolUtil.isNotEmpty(type)) {
                modificationLog.setType(type);
            }
            //判断宝箱是否超过景点
            if (ToolUtil.isNotEmpty(sysScenicSpot.getHuntQuantity())) {
                int j = sysScenicSpotBroadcastService.getBroadcastSumByScenicSpotId(sysScenicSpot.getScenicSpotId());
                if (j < Integer.parseInt(sysScenicSpot.getHuntQuantity())) {
                    returnModel.setData("");
                    returnModel.setMsg("宝箱数量多于景点数量，请修改宝箱数量");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
            }
            modificationLog.setPriceModificationUserName(currentUser.getUserName());
            int i = sysScenicSpotService.editScenicSpot(sysScenicSpot, modificationLog);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("景区修改成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("景区修改失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("editScenicSpot", e);
            returnModel.setData("");
            returnModel.setMsg("景区修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author zhang
     * @Description 修改景区信息(带扩展景区)
     * @Date
     * @Param [sysScenicSpot]
     **/
    @RequestMapping("/editScenicSpotNew")
    @ResponseBody
    public ReturnModel editScenicSpotNew(SysScenicSpotExpand sysScenicSpotExpand, String type) {
        ReturnModel returnModel = new ReturnModel();
        try {
            SysUsers currentUser = this.getSysUser();
            SysScenicSpotPriceModificationLog modificationLog = new SysScenicSpotPriceModificationLog();
            if (ToolUtil.isNotEmpty(type)) {
                modificationLog.setType(type);
            }
            //判断宝箱是否超过景点
            if (ToolUtil.isNotEmpty(sysScenicSpotExpand.getHuntQuantity())) {
                int j = sysScenicSpotBroadcastService.getBroadcastSumByScenicSpotId(sysScenicSpotExpand.getScenicSpotId());
                if (j < Integer.parseInt(sysScenicSpotExpand.getHuntQuantity())) {
                    returnModel.setData("");
                    returnModel.setMsg("宝箱数量多于景点数量，请修改宝箱数量");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
            }

            String province = request.getParameter("province");
            String city = request.getParameter("city");
            String county = request.getParameter("county");
            //扩展景区内容
            BusinessScenicSpotArea businessScenicSpotArea = new BusinessScenicSpotArea();
            String mergerName = "";
            if (ToolUtil.isNotEmpty(province)) {
                businessScenicSpotArea.setProvinceId(Integer.parseInt(province));
                //根据ID查询地区名称
                BusinessWorldArea worldArea = businessScenicSpotExpandService.selectProvinceId(province);
                mergerName += worldArea.getName();
            }
            if (ToolUtil.isNotEmpty(city)) {
                businessScenicSpotArea.setCityId(Integer.parseInt(city));
                //根据ID查询地区名称
                BusinessWorldArea worldArea = businessScenicSpotExpandService.selectProvinceId(city);
                mergerName += "-" + worldArea.getName();
            }
            if (ToolUtil.isNotEmpty(county)) {
                businessScenicSpotArea.setRegionId(Integer.parseInt(county));
                //根据ID查询地区名称
                BusinessWorldArea worldArea = businessScenicSpotExpandService.selectProvinceId(county);
                mergerName += "-" + worldArea.getName();
            }
            businessScenicSpotArea.setMergerName(mergerName);


            modificationLog.setPriceModificationUserName(currentUser.getUserName());
            int i = sysScenicSpotService.editScenicSpotNew(businessScenicSpotArea, sysScenicSpotExpand, modificationLog);
            Boolean set = false;
            if (i > 0) {
                SysScenicSpotAndCap sysScenicSpotAndCap = sysScenicSpotService.getSysScenicSpotAndCap(sysScenicSpotExpand.getScenicSpotId());
                JSONObject jsonArray = JSONObject.fromObject(sysScenicSpotAndCap);
                String s = JsonUtils.toString(jsonArray);
                set = redisUtil.set(sysScenicSpotExpand.getScenicSpotId().toString(), s);

            }
            if (i == 1 && set == true) { //
                returnModel.setData("");
                returnModel.setMsg("景区修改成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else if (i == 1 && set == false) {    //
                returnModel.setData("");
                returnModel.setMsg("景区修改成功，缓存存入失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("景区修改失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("editScenicSpot", e);
            returnModel.setData("");
            returnModel.setMsg("景区修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 删除景区
     * @Date 17:32 2020/5/21
     * @Param [scenicSpotId]
     **/
    @RequestMapping("/delScenicSpot")
    @ResponseBody
    public ReturnModel delScenicSpot(@NotBlank(message = "景区ID不能为空") Long scenicSpotId) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotService.delScenicSpot(scenicSpotId);
            if (i > 0) {
                returnModel.setData("");
                returnModel.setMsg("景区删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("景区删除失败，请联系管理员！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("景区删除失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 查询景区状态
     * @Date 9:55 2020/5/22
     * @Param []
     **/
    @RequestMapping("/getScenicSpotStateName")
    @ResponseBody
    public ReturnModel getScenicSpotStateName() {
        ReturnModel returnModel = new ReturnModel();
        try {
            List<SysScenicSpot> sysRobotList = new ArrayList<SysScenicSpot>();
            for (String key : DictUtils.getScenicSpotStateMap().keySet()) {
                String value = DictUtils.getScenicSpotStateMap().get(key);
                SysScenicSpot sysScenicSpot = new SysScenicSpot();
                sysScenicSpot.setRobotWakeupWords(key);
                sysScenicSpot.setRobotWakeupWordsName(value);
                sysRobotList.add(sysScenicSpot);
            }
            returnModel.setData(sysRobotList);
        } catch (Exception e) {
            logger.info("getScenicSpotStateName", e);
        }
        return returnModel;
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 回显景区状态
     * @Date 10:04 2020/5/22
     * @Param [scenicSpotId]
     **/
    @RequestMapping("/getScenicSpotState")
    @ResponseBody
    public ReturnModel getScenicSpotState(@RequestParam("scenicSpotId") Long scenicSpotId) {
        ReturnModel returnModel = new ReturnModel();
        try {
            SysScenicSpot sysScenicSpot = sysScenicSpotService.getSysScenicSpotById(scenicSpotId);
            if (!ToolUtil.isEmpty(sysScenicSpot)) {
                returnModel.setData(sysScenicSpot);
                returnModel.setMsg("成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("景区类型查询失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("getScenicSpotState", e);
        }
        return returnModel;
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 修改景区状态
     * @Date 10:11 2020/5/22
     * @Param [sysScenicSpot]
     **/
    @RequestMapping("/updateScenicSpotState")
    @ResponseBody
    public ReturnModel updateScenicSpotState(SysScenicSpot sysScenicSpot) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotService.updateScenicSpotState(sysScenicSpot);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("景区状态修改成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("景区状态修改失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("updateScenicSpotState", e);
            returnModel.setData("");
            returnModel.setMsg("景区状态修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 新增景区围栏
     * @Date 11:21 2020/5/22
     * @Param [sysScenicSpotGpsCoordinateWithBLOBs]
     **/
    @RequestMapping("/addBtnSubmitScenicSpotGpsCoordinate")
    @ResponseBody
    public ReturnModel addBtnSubmitScenicSpotGpsCoordinate(SysScenicSpotGpsCoordinateWithBLOBs sysScenicSpotGpsCoordinateWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {
            //查询此景区是否已经有电子围栏
            SysScenicSpotGpsCoordinateWithBLOBs coordinate = sysScenicSpotGpsCoordinateService.selectByPrimaryKey(sysScenicSpotGpsCoordinateWithBLOBs.getCoordinateScenicSpotId());
            if (!ToolUtil.isEmpty(coordinate)) {
                returnModel.setData("");
                returnModel.setMsg("此景区已有电子围栏!");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else {
                SysScenicSpot SysScenicSpot = sysScenicSpotService.getSysScenicSpotById(sysScenicSpotGpsCoordinateWithBLOBs.getCoordinateScenicSpotId());
                //添加景区围栏
                int i = sysScenicSpotGpsCoordinateService.addBtnSubmitScenicSpotGpsCoordinate(sysScenicSpotGpsCoordinateWithBLOBs);
                if (i == 1) {
                    SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
                    sysLog.setLogId(IdUtils.getSeqId());
                    sysLog.setUserName(getSysUser().getUserName());
                    sysLog.setLogCoordinateOuterring(sysScenicSpotGpsCoordinateWithBLOBs.getCoordinateOuterring());
                    sysLog.setLogCoordinateOuterringBaiDu(sysScenicSpotGpsCoordinateWithBLOBs.getCoordinateOuterringBaiDu());
                    sysLog.setLogScenicSpotId(SysScenicSpot.getScenicSpotId());
                    sysLog.setLogScenicSpotName(SysScenicSpot.getScenicSpotName());
                    sysLog.setLogType("2");
                    sysScenicSpotGpsCoordinateService.addSysLog(sysLog);
                    returnModel.setData("");
                    returnModel.setMsg("景区围栏新增成功");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                } else {
                    returnModel.setData("");
                    returnModel.setMsg("景区围栏新增失败");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
            }

        } catch (Exception e) {
            logger.info("addBtnSubmitScenicSpotGpsCoordinate", e);
            returnModel.setData("");
            returnModel.setMsg("景区围栏新增失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 根据景区ID查询电子围栏
     * @Date 13:15 2020/5/22
     * @Param [scenicSpotId]
     **/
    @RequestMapping("/LoadEditScenicSpot")
    @ResponseBody
    public ReturnModel LoadEditScenicSpot(@RequestParam("scenicSpotId") Long scenicSpotId) {
        ReturnModel returnModel = new ReturnModel();
        try {
            SysScenicSpotGpsCoordinateWithBLOBs coordinate = sysScenicSpotGpsCoordinateService.selectByPrimaryKey(scenicSpotId);
            if (!ToolUtil.isEmpty(coordinate)) {
                returnModel.setData(coordinate);
                returnModel.setMsg("");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("此景区暂无景区围栏，请添加！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("LoadEditScenicSpot", e);
            returnModel.setData("");
            returnModel.setMsg("景区围栏信息查询失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 修改景区电子围栏
     * @Date 14:09 2020/5/22
     * @Param [sysScenicSpotGpsCoordinateWithBLOBs]
     **/
    @RequestMapping("/editBtnSubmitScenicSpotGpsCoordinate")
    @ResponseBody
    public ReturnModel editBtnSubmitScenicSpotGpsCoordinate(SysScenicSpotGpsCoordinateWithBLOBs sysScenicSpotGpsCoordinateWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotGpsCoordinateService.editBtnSubmitScenicSpotGpsCoordinate(sysScenicSpotGpsCoordinateWithBLOBs);
            if (i == 1) {
                SysScenicSpot sysScenicSpot = sysScenicSpotService.getSysScenicSpotById(sysScenicSpotGpsCoordinateWithBLOBs.getCoordinateScenicSpotId());
                SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
                sysLog.setLogId(IdUtils.getSeqId());
                sysLog.setUserName(getSysUser().getUserName());
                sysLog.setLogCoordinateOuterring(sysScenicSpotGpsCoordinateWithBLOBs.getCoordinateOuterring());
                sysLog.setLogCoordinateOuterringBaiDu(sysScenicSpotGpsCoordinateWithBLOBs.getCoordinateOuterringBaiDu());
                sysLog.setLogScenicSpotId(sysScenicSpot.getScenicSpotId());
                sysLog.setLogScenicSpotName(sysScenicSpot.getScenicSpotName());
                sysLog.setLogType("2");
                sysScenicSpotGpsCoordinateService.addSysLog(sysLog);
                returnModel.setData("");
                returnModel.setMsg("景区围栏修改成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("景区围栏修改失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("editBtnSubmitScenicSpotGpsCoordinate", e);
            returnModel.setData("");
            returnModel.setMsg("景区围栏修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 获取当前景区
     * @Date 10:57 2020/5/31
     * @Param [sysScenicSpotGpsCoordinateWithBLOBs]
     **/
    @RequestMapping("/getCurrentScenicSpot")
    @ResponseBody
    public ReturnModel getCurrentScenicSpot() {
        ReturnModel returnModel = new ReturnModel();
        try {
            SysScenicSpot SysScenicSpot = sysScenicSpotService.getCurrentScenicSpot(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            if (ToolUtil.isNotEmpty(SysScenicSpot)) {
                returnModel.setData(SysScenicSpot);
                returnModel.setMsg("当前景区获取成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("当前景区获取失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("getCurrentScenicSpot", e);
            returnModel.setData("");
            returnModel.setMsg("当前景区获取失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 新增景区封顶价格
     * @Date 11:51 2020/7/23
     * @Param [capPrice]
     **/
    @RequestMapping("/addCapPrice")
    @ResponseBody
    public ReturnModel addCapPrice(SysScenicSpotCapPrice capPrice) {
        logger.info("addCapPrice");
        ReturnModel dataModel = new ReturnModel();
        try {
            //查询当前景区是否有封顶价格
            SysScenicSpotCapPrice spotCapPrice = sysScenicSpotService.getCapPriceByScenicSpotId(capPrice.getScenicSpotId());
            if (ToolUtil.isNotEmpty(spotCapPrice)) {
                dataModel.setData("");
                dataModel.setMsg("此景区已有封顶价格，请修改！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
            SysScenicSpot sysScenicSpot = sysScenicSpotService.getSysScenicSpotById(capPrice.getScenicSpotId());
            if (ToolUtil.isNotEmpty(sysScenicSpot.getScenicSpotWeekendRentPrice()) && ToolUtil.isNotEmpty(sysScenicSpot.getScenicSpotWeekendPrice()) && ToolUtil.isNotEmpty(sysScenicSpot.getScenicSpotRentTime())) {
                BigDecimal weekendRentPrice = new BigDecimal(sysScenicSpot.getScenicSpotWeekendRentPrice());//周六日起租价格
                BigDecimal weekendPrice = new BigDecimal(sysScenicSpot.getScenicSpotWeekendPrice());//周六日单价
                BigDecimal rentTime = new BigDecimal(sysScenicSpot.getScenicSpotRentTime());//周六日起租时间
                BigDecimal weekendCapOnePrice = new BigDecimal(capPrice.getWeekendCapOnePrice());//周六日封顶价格
                BigDecimal rentPrice = weekendCapOnePrice.subtract(weekendRentPrice);
                BigDecimal price = rentPrice.divide(weekendPrice, BigDecimal.ROUND_HALF_UP);
                BigDecimal normalCapOneTime = price.add(rentTime);
                normalCapOneTime = normalCapOneTime.setScale(0, BigDecimal.ROUND_HALF_UP);
                capPrice.setWeekendCapOneTime(normalCapOneTime.toString());
            }
            if (ToolUtil.isNotEmpty(sysScenicSpot.getScenicSpotNormalRentPrice()) && ToolUtil.isNotEmpty(sysScenicSpot.getScenicSpotNormalPrice()) && ToolUtil.isNotEmpty(sysScenicSpot.getScenicSpotRentTime())) {
                BigDecimal normalRentPrice = new BigDecimal(sysScenicSpot.getScenicSpotNormalRentPrice());//工作日起租价格
                BigDecimal normalPrice = new BigDecimal(sysScenicSpot.getScenicSpotNormalPrice());//工作日单价
                BigDecimal rentTime = new BigDecimal(sysScenicSpot.getScenicSpotRentTime());//工作日起租时间
                BigDecimal normalCapOnePrice = new BigDecimal(capPrice.getNormalCapOnePrice());//工作日封顶价格
                BigDecimal rentPrice = normalCapOnePrice.subtract(normalRentPrice);
                BigDecimal price = rentPrice.divide(normalPrice, BigDecimal.ROUND_HALF_UP);
                BigDecimal normalCapOneTime = price.add(rentTime);
                normalCapOneTime = normalCapOneTime.setScale(0, BigDecimal.ROUND_HALF_UP);
                capPrice.setNormalCapOneTime(normalCapOneTime.toString());
            }
            SysUsers user = this.getSysUser();
            int i = sysScenicSpotService.addCapPrice(capPrice, user);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("景区封顶价格新增成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            } else {
                dataModel.setData("");
                dataModel.setMsg("景区封顶价格新增失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("addCapPrice", e);
            dataModel.setData("");
            dataModel.setMsg("景区封顶价格新增失败！（请联系管理员）");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 查询景区封顶价格
     * @Date 11:39 2020/7/24
     * @Param [scenicSpotId]
     **/
    @RequestMapping("/loadEditScenicSpotCapPrice")
    @ResponseBody
    public ReturnModel loadEditScenicSpotCapPrice(@RequestParam("scenicSpotId") Long scenicSpotId) {
        ReturnModel dataModel = new ReturnModel();
        try {
            SysScenicSpotCapPrice capPrice = sysScenicSpotService.getScenicSpotCapPriceByScenicSpotId(scenicSpotId);
            if (ToolUtil.isNotEmpty(capPrice)) {
                dataModel.setData(capPrice);
                dataModel.setMsg("景区封顶价格查询成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            } else {
                dataModel.setData("");
                dataModel.setMsg("景区封顶价格查询失败！（请添加）");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("景区封顶价格查询失败！（请联系管理员）");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 编辑景区封顶价格
     * @Date 11:51 2020/7/23
     * @Param [capPrice]
     **/
    @RequestMapping("/editCapPrice")
    @ResponseBody
    public ReturnModel editCapPrice(SysScenicSpotCapPrice capPrice) {
        logger.info("editCapPrice");
        ReturnModel dataModel = new ReturnModel();
        try {
            SysScenicSpot sysScenicSpot = sysScenicSpotService.getSysScenicSpotById(capPrice.getScenicSpotId());
            if (ToolUtil.isNotEmpty(sysScenicSpot.getScenicSpotWeekendRentPrice()) && ToolUtil.isNotEmpty(sysScenicSpot.getScenicSpotWeekendPrice()) && ToolUtil.isNotEmpty(sysScenicSpot.getScenicSpotRentTime())) {
                BigDecimal weekendRentPrice = new BigDecimal(sysScenicSpot.getScenicSpotWeekendRentPrice());//周六日起租价格
                BigDecimal weekendPrice = new BigDecimal(sysScenicSpot.getScenicSpotWeekendPrice());//周六日单价
                BigDecimal rentTime = new BigDecimal(sysScenicSpot.getScenicSpotRentTime());//周六日起租时间
                BigDecimal weekendCapOnePrice = new BigDecimal(capPrice.getWeekendCapOnePrice());//周六日封顶价格
                BigDecimal rentPrice = weekendCapOnePrice.subtract(weekendRentPrice);
                BigDecimal price = rentPrice.divide(weekendPrice, BigDecimal.ROUND_HALF_UP);
                BigDecimal normalCapOneTime = price.add(rentTime);
                normalCapOneTime = normalCapOneTime.setScale(0, BigDecimal.ROUND_HALF_UP);
                capPrice.setWeekendCapOneTime(normalCapOneTime.toString());
            }
            if (ToolUtil.isNotEmpty(sysScenicSpot.getScenicSpotNormalRentPrice()) && ToolUtil.isNotEmpty(sysScenicSpot.getScenicSpotNormalPrice()) && ToolUtil.isNotEmpty(sysScenicSpot.getScenicSpotRentTime())) {
                BigDecimal normalRentPrice = new BigDecimal(sysScenicSpot.getScenicSpotNormalRentPrice());//工作日起租价格
                BigDecimal normalPrice = new BigDecimal(sysScenicSpot.getScenicSpotNormalPrice());//工作日单价
                BigDecimal rentTime = new BigDecimal(sysScenicSpot.getScenicSpotRentTime());//工作日起租时间
                BigDecimal normalCapOnePrice = new BigDecimal(capPrice.getNormalCapOnePrice());//工作日封顶价格
                BigDecimal rentPrice = normalCapOnePrice.subtract(normalRentPrice);
                BigDecimal price = rentPrice.divide(normalPrice, BigDecimal.ROUND_HALF_UP);
                BigDecimal normalCapOneTime = price.add(rentTime);
                normalCapOneTime = normalCapOneTime.setScale(0, BigDecimal.ROUND_HALF_UP);
                capPrice.setNormalCapOneTime(normalCapOneTime.toString());
            }
            SysUsers user = this.getSysUser();
            int i = sysScenicSpotService.editCapPrice(capPrice, user);
            Boolean set = false;
            if (i > 0) {
                SysScenicSpotAndCap sysScenicSpotAndCap = sysScenicSpotService.getSysScenicSpotAndCap(capPrice.getScenicSpotId());
                JSONObject jsonArray = JSONObject.fromObject(sysScenicSpotAndCap);
                String s = JsonUtils.toString(jsonArray);
                Long scenicSpotId = sysScenicSpotAndCap.getScenicSpotId();
                set = redisUtil.set(sysScenicSpotAndCap.getScenicSpotId().toString(), s);
            }

            if (i == 1 && set == true) {  //
                dataModel.setData("");
                dataModel.setMsg("景区封顶价格修改成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            } else if (i == 1 && set == false) {
                dataModel.setData("");
                dataModel.setMsg("景区封顶价格修改成功！缓存存入失败");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            } else {
                dataModel.setData("");
                dataModel.setMsg("景区封顶价格修改失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("editCapPrice", e);
            dataModel.setData("");
            dataModel.setMsg("景区封顶价格修改失败！（请联系管理员）");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }


    /**
     * 删除封顶价格
     *
     * @param capPrice
     * @return
     */
    @RequestMapping("/delCapPrice")
    @ResponseBody
    public ReturnModel delCapPrice(SysScenicSpotCapPrice capPrice) {

        ReturnModel dataModel = new ReturnModel();
        Boolean set = false;
        try {
            int i = sysScenicSpotService.delCapPrice(capPrice);


//            if (i>0){
////                SysScenicSpotAndCap sysScenicSpotAndCap = sysScenicSpotService.getSysScenicSpotAndCap(capPrice.getScenicSpotId());
//                boolean exists = redisUtil.exists(capPrice.getScenicSpotId().toString());
//                if (exists){
//                 set =   redisUtil.removeN(capPrice.getScenicSpotId().toString());
//                }
//            }

            if (i == 1 && set == true) {  //
                dataModel.setData("");
                dataModel.setMsg("景区封顶价格删除成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            } else if (i == 1 && set == false) {
                dataModel.setData("");
                dataModel.setMsg("景区封顶价格删除成功！缓存删除失败");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            } else {
                dataModel.setData("");
                dataModel.setMsg("景区封顶价格删除失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }

        } catch (Exception e) {
            logger.info("delCapPrice", e);
            dataModel.setData("");
            dataModel.setMsg("景区封顶价格删除失败！（请联系管理员）");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;

        }


    }


    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 添加景区图片
     * @Date 16:35 2020/11/25
     * @Param [file, sysScenicSpotImg]
     **/
    @RequestMapping("/addScenicSpotPicture")
    @ResponseBody
    public ReturnModel addScenicSpotPicture(@RequestParam("file") MultipartFile file, SysScenicSpotImg sysScenicSpotImg) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if (!file.isEmpty()) {
                int i = sysScenicSpotService.addScenicSpotPicture(file, sysScenicSpotImg);
                if (i == 1) {
                    dataModel.setData("");
                    dataModel.setMsg("景区图片添加成功！");
                    dataModel.setState(Constant.STATE_SUCCESS);
                    return dataModel;
                } else if (i == 2) {
                    dataModel.setData("");
                    dataModel.setMsg("图片格式不正确！（只支持png，jpg文件）");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                } else if (i == 3) {
                    dataModel.setData("");
                    dataModel.setMsg("景区图片无法大于2M！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                } else {
                    dataModel.setData("");
                    dataModel.setMsg("景区图片添加失败！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
            } else {
                dataModel.setData("");
                dataModel.setMsg("请选择上传的图片！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("景区图片添加失败！（请联系管理员）");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     * @Author 郭凯
     * @Description 查询电子围栏列表
     * @Date 16:59 2020/12/5
     * @Param [pageNum, pageSize, sysScenicSpotGpsCoordinate]
     **/
    @ApiOperation("电子围栏")
    @RequestMapping("/getScenicSpotGpsCoordinateList")
    @ResponseBody
    public PageDataResult getScenicSpotGpsCoordinateList(@RequestParam("pageNum") Integer pageNum,
                                                         @RequestParam("pageSize") Integer pageSize, SysScenicSpotGpsCoordinate sysScenicSpotGpsCoordinate) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String, String> search = new HashMap<>();
        SysUsers user = this.getSysUser();
        search.put("userId", user.getUserId().toString());
        search.put("scenicSpotName", sysScenicSpotGpsCoordinate.getScenicSpotName());
        try {
            if (null == pageNum) {
                pageNum = 1;
            }
            if (null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysScenicSpotGpsCoordinateService.getScenicSpotGpsCoordinateList(pageNum, pageSize, search);
        } catch (Exception e) {
            logger.info("查询电子围栏列表查询失败", e);
        }
        return pageDataResult;
    }

    /**
     * @return void
     * @Author 郭凯
     * @Description 下载景区计费规则Excel表
     * @Date 13:09 2020/12/7
     * @Param [response, sysScenicSpot]
     **/
    @RequestMapping(value = "/uploadScenicSpotBillingRulesExcelOrder")
    public void uploadScenicSpotBillingRulesExcelOrder(HttpServletResponse response, SysScenicSpot sysScenicSpot) throws Exception {
        List<SysScenicSpot> scenicSpotListByExample = null;
        Map<String, Object> search = new HashMap<>();
        SysUsers user = this.getSysUser();
        search.put("userId", user.getUserId().toString());
        search.put("scenicSpotName", sysScenicSpot.getScenicSpotName());
        search.put("robotWakeupWords", sysScenicSpot.getRobotWakeupWords());
        scenicSpotListByExample = sysScenicSpotService.getScenicSpotBillingRulesList(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("景区列表", "景区列表", SysScenicSpotBillingRules.class, scenicSpotListByExample), "景区列表" + dateTime + ".xls", response);
    }

    /**
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     * @Author 郭凯
     * @Description 景区封顶价格列表查询
     * @Date 15:31 2020/12/7
     * @Param [pageNum, pageSize, sysScenicSpotCapPrice]
     **/
    @RequestMapping("/getScenicSpotCapPriceList")
    @ResponseBody
    public PageDataResult getScenicSpotCappriceList(@RequestParam("pageNum") Integer pageNum,
                                                    @RequestParam("pageSize") Integer pageSize, SysScenicSpotCapPrice sysScenicSpotCapPrice) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String, String> search = new HashMap<>();
        SysUsers user = this.getSysUser();
        search.put("userId", user.getUserId().toString());
        search.put("scenicSpotName", sysScenicSpotCapPrice.getScenicSpotName());
        try {
            if (null == pageNum) {
                pageNum = 1;
            }
            if (null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysScenicSpotService.getScenicSpotCapPriceList(pageNum, pageSize, search);
        } catch (Exception e) {
            logger.info("景区封顶价格列表查询失败", e);
        }
        return pageDataResult;
    }

    /**
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     * @Author 郭凯
     * @Description 景区PAD管理列表查询
     * @Date 12:41 2020/12/15
     * @Param [pageNum, pageSize, sysRobotAppVersion]
     **/
    @RequestMapping("/getScenicSpotPadList")
    @ResponseBody
    public PageDataResult getScenicSpotPadList(@RequestParam("pageNum") Integer pageNum,
                                               @RequestParam("pageSize") Integer pageSize, SysRobotAppVersion sysRobotAppVersion) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String, String> search = new HashMap<>();
        SysUsers user = this.getSysUser();
        search.put("userId", user.getUserId().toString());
        search.put("versionNumber", sysRobotAppVersion.getVersionNumber());
        search.put("scenicSpotName", sysRobotAppVersion.getScenicSpotName());
        try {
            if (null == pageNum) {
                pageNum = 1;
            }
            if (null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysScenicSpotService.getScenicSpotPadList(pageNum, pageSize, search);
        } catch (Exception e) {
            logger.info("景区PAD管理列表查询失败", e);
        }
        return pageDataResult;
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 添加景区PAD
     * @Date 13:54 2020/12/15
     * @Param [scenicSpotId, padId]
     **/
    @RequestMapping("/addScenicSpotPad")
    @ResponseBody
    public ReturnModel addScenicSpotPad(Long scenicSpotId, Long padId) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if (ToolUtil.isEmpty(scenicSpotId)) {
                dataModel.setData("");
                dataModel.setMsg("景区ID为空！（请联系后台管理人员）");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
            if (ToolUtil.isEmpty(padId)) {
                dataModel.setData("");
                dataModel.setMsg("PAD ID为空！（请联系后台管理人员）");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
            //根据景区ID查询景区PAD信息
            SysRobotAppVersion robotAppVersion = sysScenicSpotService.getRobotAppVersionById(scenicSpotId);
            if (ToolUtil.isNotEmpty(robotAppVersion)) {
                dataModel.setData("");
                dataModel.setMsg("此景区已有PAD信息，请修改！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
            int i = sysScenicSpotService.addScenicSpotPad(scenicSpotId, padId);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("景区PAD添加成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            } else if (i == 2) {
                dataModel.setData("");
                dataModel.setMsg("PAD信息查询失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            } else {
                dataModel.setData("");
                dataModel.setMsg("景区PAD添加失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            logger.info("addScenicSpotPad", e);
            dataModel.setData("");
            dataModel.setMsg("景区PAD添加失败！（请联系管理员）");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 编辑景区PAD
     * @Date 14:28 2020/12/15
     * @Param [versionId, scenicSpotId, padId]
     **/
    @RequestMapping("/editScenicSpotPad")
    @ResponseBody
    public ReturnModel editScenicSpotPad(Long versionId, Long scenicSpotId, Long padId) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if (ToolUtil.isEmpty(scenicSpotId)) {
                dataModel.setData("");
                dataModel.setMsg("请添加后编辑！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
            if (ToolUtil.isEmpty(padId)) {
                dataModel.setData("");
                dataModel.setMsg("请添加后编辑！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
            //根据景区ID查询景区PAD信息
            SysRobotAppVersion robotAppVersion = sysScenicSpotService.getRobotAppVersionById(scenicSpotId);
            if (ToolUtil.isEmpty(robotAppVersion)) {
                int i = sysScenicSpotService.addScenicSpotPad(scenicSpotId, padId);
                if (i == 1) {
                    dataModel.setData("");
                    dataModel.setMsg("景区PAD添加成功！");
                    dataModel.setState(Constant.STATE_SUCCESS);
                    return dataModel;
                } else if (i == 2) {
                    dataModel.setData("");
                    dataModel.setMsg("PAD信息查询失败！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                } else {
                    dataModel.setData("");
                    dataModel.setMsg("景区PAD添加失败！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
            } else {
                if (ToolUtil.isEmpty(versionId)) {
                    dataModel.setData("");
                    dataModel.setMsg("PAD ID为空！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
                int i = sysScenicSpotService.editScenicSpotPad(versionId, scenicSpotId, padId);
                if (i == 1) {
                    dataModel.setData("");
                    dataModel.setMsg("景区PAD编辑成功！");
                    dataModel.setState(Constant.STATE_SUCCESS);
                    return dataModel;
                } else if (i == 2) {
                    dataModel.setData("");
                    dataModel.setMsg("PAD信息查询失败！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                } else {
                    dataModel.setData("");
                    dataModel.setMsg("景区PAD编辑失败！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
            }
        } catch (Exception e) {
            logger.info("editScenicSpotPad", e);
            dataModel.setData("");
            dataModel.setMsg("景区PAD编辑失败！（请联系管理员）");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @param @param  response
     * @param @param  sysScenicSpot
     * @param @throws Exception
     * @return void
     * @throws
     * @Author 郭凯
     * @Description: 景区档案下载Excel表
     * @Title: uploadScenicSpotExcel
     * @date 2021年1月11日 下午4:37:11
     */
    @RequestMapping(value = "/uploadScenicSpotExcel")
    public void uploadScenicSpotExcel(HttpServletResponse response, SysScenicSpot sysScenicSpot) throws Exception {
        List<SysScenicSpot> scenicSpotListByExample = null;
        Map<String, String> search = new HashMap<>();
        SysUsers user = this.getSysUser();
        search.put("userId", user.getUserId().toString());
        search.put("scenicSpotName", sysScenicSpot.getScenicSpotName());
        search.put("robotWakeupWords", sysScenicSpot.getRobotWakeupWords());
        scenicSpotListByExample = sysScenicSpotService.getScenicSpotExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("景区列表", "景区列表", SysScenicSpot.class, scenicSpotListByExample), "景区列表" + dateTime + ".xls", response);
    }

    /**
     * @return void
     * @throws
     * @Author zhang
     * @Description: 封顶价格下载Excel表
     * @Title: uploadScenicSpotCapPriceExcel
     * @date
     */

    @RequestMapping(value = "/uploadScenicSpotCapPriceExcel")
    public void uploadScenicSpotCapPriceExcel(HttpServletResponse response, SysScenicSpot sysScenicSpot) throws Exception {
        List<SysScenicSpotCapPrice> scenicSpotCapPriceByExample = null;
        Map<String, String> search = new HashMap<>();
        SysUsers user = this.getSysUser();
        search.put("userId", user.getUserId().toString());
//        search.put("scenicSpotId",sysScenicSpot.getScenicSpotId().toString());
        search.put("scenicSpotName", sysScenicSpot.getScenicSpotName());
        scenicSpotCapPriceByExample = sysScenicSpotService.getScenicSpotCapPriceExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        //     FileUtil.exportExcel(FileUtil.getWorkbook("封顶价格", "封顶价格", SysScenicSpotCapPriceLog.class, scenicSpotCapPriceLogByExample),"封顶价格"+ dateTime +".xls",response);
        FileUtil.exportExcel(FileUtil.getWorkbook("封顶价格", "封顶价格", SysScenicSpotCapPrice.class, scenicSpotCapPriceByExample), "封顶价格" + dateTime + ".xls", response);
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 修改景区寻宝开启关闭状态
     * @Date 10:11 2020/5/22
     * @Param [sysScenicSpot]
     **/
    @RequestMapping("/updateScenicSpotSwitchs")
    @ResponseBody
    public ReturnModel updateScenicSpotSwitchs(String switchs,Long scenicSpotId) {
        ReturnModel returnModel = new ReturnModel();
        try {
            if (scenicSpotId == null){
                scenicSpotId = Long.parseLong(session.getAttribute("scenicSpotId").toString());
            }
//            long scenicSpotId = 15698320289682l;

            int i = sysScenicSpotService.updateScenicSpotSwitchs(scenicSpotId, switchs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("寻宝活动开启成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else if (i == 2) {
                returnModel.setData("");
                returnModel.setMsg("随机寻宝活动开启成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else if (i == 0) {
                returnModel.setData("");
                returnModel.setMsg("寻宝活动关闭成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("寻宝活动修改失败！（请联系管理员）");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("updateScenicSpotState", e);
            returnModel.setData("");
            returnModel.setMsg("寻宝活动修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * @Author xjl
     * @Version 1.0
     * @DOC 更新景点开关
     * @Date 2023/09/09
     */
    @PostMapping("/updateScenicSpots")
    @ResponseBody
    public ReturnModel updateScenicSpots(@RequestParam(value = "scenicSpotIds", required = true) List<Long> scenicSpotIds,
                                         @RequestParam(value = "switchs", required = true) String switchs) {
        ReturnModel returnModel = new ReturnModel();
        try {
            String[] switchArray = switchs.split(",");
            int successCount = 0;
            List<String> successMsgs = new ArrayList<String>();
            for (int i = 0; i < scenicSpotIds.size(); i++) {
                int i1 = sysScenicSpotService.updateScenicSpotSwitchs(scenicSpotIds.get(i), switchArray[i]);
                if (i1 == 1) {
                    successCount++;
                    successMsgs.add("寻宝活动开启成功");
                } else if (i1 == 2) {
                    successCount++;
                    successMsgs.add("随机寻宝活动开启成功");
                } else if (i1 == 0) {
                    successCount++;
                    successMsgs.add("寻宝活动关闭成功");
                }
            }
            if (successCount == switchArray.length) {
                returnModel.setData("");
                returnModel.setState(Constant.STATE_SUCCESS);
                if (successCount == 1) {
                    returnModel.setMsg(successMsgs.get(0));
                } else {
                    returnModel.setMsg("批量修改成功");
                }
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("批量修改失败！（请联系管理员）");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("updateScenicSpotState", e);
            returnModel.setData("");
            returnModel.setMsg("批量修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * @Author xjl
     * @Version 1.0
     * @DOC 查询景点
     * @Date 2023/09/10
     */
    @PostMapping("/getScenicSpots")
    @ResponseBody
    public ReturnModel getScenicSpots(ScenicSpot scenicSpot) {
        ReturnModel returnModel = new ReturnModel();
        List<SysScenicSpot> list = sysScenicSpotService.getScenicSpotById(scenicSpot);
        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        return returnModel;
    }


    /**
     * @Author xjl
     * @Version 1.0
     * @DOC 当前景区是否寻宝活动状态
     * @Date 2023/09/10
     */
    @RequestMapping("/getScenicSpotSwitchs")
    @ResponseBody
    public ReturnModel getScenicSpotSwitchs() {

        ReturnModel returnModel = new ReturnModel();
        long scenicSpotId = Long.parseLong(session.getAttribute("scenicSpotId").toString());

        int i = sysScenicSpotService.getScenicSpotSwitchs(scenicSpotId);
        if (i == 1) {
            returnModel.setData("1");
            returnModel.setMsg("寻宝活动开启中");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } else if (i == 2) {
            returnModel.setData("2");
            returnModel.setMsg("随机寻宝活动开启中");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } else {

        }
        returnModel.setData("0");
        returnModel.setMsg("寻宝活动关闭中");
        returnModel.setState(Constant.STATE_SUCCESS);
        return returnModel;

    }


    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 郭凯
     * @Description 一键修改景区寻宝开启关闭状态
     * @Date 10:11 2020/5/22
     * @Param [sysScenicSpot]
     **/
    @RequestMapping("/oneTouchUpdateScenicSpotSwitchs")
    @ResponseBody
    public ReturnModel oneTouchUpdateScenicSpotSwitchs(String switchs) {
        ReturnModel returnModel = new ReturnModel();
        try {
//            long scenicSpotId = Long.parseLong(session.getAttribute("scenicSpotId").toString());

            int i = sysScenicSpotService.oneTouchUpdateScenicSpotSwitchs(switchs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("寻宝活动一键开启成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else if (i == 2) {
                returnModel.setData("");
                returnModel.setMsg("随机寻宝活一键动开启成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else if (i == 0) {
                returnModel.setData("");
                returnModel.setMsg("活动一键关闭成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("寻宝活动一键修改失败！（请联系管理员）");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("updateScenicSpotState", e);
            returnModel.setData("");
            returnModel.setMsg("寻宝活动一键修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * 测试
     */
    @ApiModelProperty("测试")
    @RequestMapping("/test")
    @ResponseBody
    public void test() {

        sysScenicSpotService.timingScenicSpotOrder();

    }


}
