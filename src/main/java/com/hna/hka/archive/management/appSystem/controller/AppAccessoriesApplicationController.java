package com.hna.hka.archive.management.appSystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.model.SysAppAccessoriesType;
import com.hna.hka.archive.management.appSystem.model.SysRobotAccessoriesApplicationDetail;
import com.hna.hka.archive.management.appSystem.service.AppUserService;
import com.hna.hka.archive.management.assetsSystem.model.*;
import com.hna.hka.archive.management.assetsSystem.service.AddressService;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotAccessoriesApplicationService;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotPartsManagementService;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotPartsManagementTypeService;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.system.model.SysRobot;
import com.hna.hka.archive.management.system.service.SysRobotService;
import com.hna.hka.archive.management.system.util.*;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.appSystem.controller
 * @ClassName: AppAccessoriesApplicationController
 * @Author: 郭凯
 * @Description: APP配件管理控制层
 * @Date: 2021/6/10 17:06
 * @Version: 1.0
 */
@Api(tags = "管理者app配件申请相关接口")
@RequestMapping("/system/appAccessoriesApplication")
@Controller
public class AppAccessoriesApplicationController extends PublicUtil {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private SysRobotPartsManagementService sysRobotPartsManagementService;

    @Autowired
    private SysRobotAccessoriesApplicationService sysRobotAccessoriesApplicationService;

    @Autowired
    private SysRobotService sysRobotService;

    @Autowired
    private SysRobotPartsManagementTypeService sysRobotPartsManagementTypeService;

    @Autowired
    private AddressService service;

    @Value("${PHOTOS_OF_ACCESSORIES_PAHT}")
    private String photosOfAccessoriesPaht;

    @Value("${PHOTOS_OF_ACCESSORIES_URL}")
    private String photosOfAccessoriesUrl;

    @Value("${GET_APP_MAP_PAHT}")
    private String getAppMapPaht;

    /**
     * @Method getAppRobotList
     * @Author 郭凯
     * @Version 1.0
     * @Description 获取配件类型
     * @Return java.lang.String
     * @Date 2021/6/10 17:28
     */
    @ApiOperation("获取配件类型")
    @RequestMapping("/getAppAccessoriesApplicationType")
    @ResponseBody
    public String getAppAccessoriesApplicationType(String content) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
//            List<SysAppAccessoriesType> sysAppAccessoriesType = new ArrayList<>();
            List<SysRobotPartsManagementType> partsManagementTypeDropDown = sysRobotPartsManagementTypeService.getPartsManagementTypeDropDown();

            dataModel.setData(partsManagementTypeDropDown);
            dataModel.setMsg("");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            logger.info("getAppAccessoriesApplicationType", e);
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }


    /**
     * @Method getAppAccessoriesApplicationName
     * @Author 郭凯
     * @Version 1.0
     * @Description 获取配件名称
     * @Return java.lang.String
     * @Date 2021/6/10 17:31
     */
    @RequestMapping("/getAppAccessoriesApplicationName")
    @ResponseBody
    public String getAppAccessoriesApplicationName(String content) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //配件类型
            String accessoriesType = jsonobject.getString("accessoriesType");
            //判断配件类型是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(accessoriesType)) {
                dataModel.setData("");
                dataModel.setMsg("配件类型为空，请传入配件类型!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //机器人编号
            String robotCode = jsonobject.getString("robotCode");
            if (ToolUtil.isNotEmpty(robotCode)) {
                SysRobot robot = sysRobotService.getRobotCodeBy(robotCode);
                if (ToolUtil.isEmpty(robot)) {
                    dataModel.setData("");
                    dataModel.setMsg("未查询到此机器人！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    dataModel.setType(Constant.STATE_FAILURE);
                    String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
                    return AES.encode(model);//加密返回
                }
            }
            String spotId = jsonobject.getString("spotId");
            String accessoryName = jsonobject.getString("accessoryName");
            Map<String, Object> search = new HashMap<>();
            search.put("accessoriesType", accessoriesType);
            search.put("robotCode", robotCode);
            search.put("spotId", spotId);
            search.put("accessoryName",accessoryName);
            List<SysRobotPartsManagement> partsManagementList = sysRobotPartsManagementService.getAppAccessoriesApplicationName(search);
            if (ToolUtil.isEmpty(partsManagementList)) {
                dataModel.setData("");
                dataModel.setMsg("未查询到配件！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
                return AES.encode(model);//加密返回
            } else {
                dataModel.setData(partsManagementList);
                dataModel.setMsg("");
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
                return AES.encode(model);//加密返回
            }
        } catch (Exception e) {
            logger.info("getAppAccessoriesApplicationName", e);
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }


    /**
     * 获取配件数量
     */

    @RequestMapping("/getAmount")
    @ResponseBody
    public String getAmount(String content) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //配件唯一标识
            String accessoriesCode = jsonobject.getString("accessoriesCode");
            //判断配件类型是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(accessoriesCode)) {
                dataModel.setData("");
                dataModel.setMsg("配件唯一标识为空，请传入配件唯一标识!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String spotId = jsonobject.getString("spotId");
            Map<String, Object> search = new HashMap<>();
            search.put("accessoriesCode", accessoriesCode);
            search.put("spotId", spotId);
            Long amount = sysRobotPartsManagementService.getAmount(search);
            if (ToolUtil.isEmpty(amount)) {
                dataModel.setData("");
                dataModel.setMsg("未查询到配件！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
                return AES.encode(model);//加密返回
            } else {
                dataModel.setData(amount);
                dataModel.setMsg("");
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
                return AES.encode(model);//加密返回
            }
        } catch (Exception e) {
            logger.info("getAppAccessoriesApplicationName", e);
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }


    /**
     * @Method uploadPictures
     * @Author 郭凯
     * @Version 1.0
     * @Description APP上传图片
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/6/15 15:32
     */
    @RequestMapping("/uploadPictures")
    @ResponseBody
    public String uploadPictures(@RequestParam("file") MultipartFile file) {
        ReturnModel dataModel = new ReturnModel();
        try {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);// 取文件格式后缀名
            type = "." + type;
            if (type.equals(".jpg") || type.equals(".png")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = photosOfAccessoriesPaht + filename;// 存放位置
                File destFile = new File(path);
                try {
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dataModel.setData(getAppMapPaht + photosOfAccessoriesUrl + filename);
                dataModel.setMsg("");
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            } else {
                dataModel.setData("");
                dataModel.setMsg("文件类型有误！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
        } catch (Exception e) {
            logger.info("uploadPictures", e);
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * @Method addRobotAccessoriesApplication
     * @Author 郭凯
     * @Version 1.0
     * @Description 上传配件申请信息
     * @Return java.lang.String
     * @Date 2021/6/16 11:25
     */
    @RequestMapping("/addRobotAccessoriesApplication")
    @ResponseBody
    public String addRobotAccessoriesApplication(SysRobotAccessoriesApplication sysRobotAccessoriesApplication) {
        ReturnModel dataModel = new ReturnModel();
        try {
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(sysRobotAccessoriesApplication.getLonginTokenId())) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(sysRobotAccessoriesApplication.getLonginTokenId());
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            int i = sysRobotAccessoriesApplicationService.addRobotAccessoriesApplication(sysRobotAccessoriesApplication);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("");
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                Map<String, Object> search = new HashMap<>();
                search.put("scenicSpotId", sysRobotAccessoriesApplication.getScenicSpotId());
                search.put("resCode", "ACCESSORIES_APPLICATION");
                List<SysAppUsers> appUsersList = appUserService.getAppUsersByScenicIdList(search);
                if (ToolUtil.isNotEmpty(appUsersList) && appUsersList.size() > 0) {
                    for (SysAppUsers sysAppUsers : appUsersList) {
                        // 个推推送消息到APP端
                        String isSuccess = WeChatGtRobotAppPush.singlePushApp(sysAppUsers.getUserClientGtId(), "配件申请", sysAppUsers.getScenicSpotName() + "," + sysRobotAccessoriesApplication.getAccessoriesName() + "," + sysRobotAccessoriesApplication.getAccessoryNumber() + "个");
                        if ("1".equals(isSuccess)) {
                            dataModel.setData("");
                            dataModel.setMsg("发送成功！");
                            dataModel.setState(Constant.STATE_SUCCESS);
                        } else {
                            dataModel.setData("");
                            dataModel.setMsg("发送失败！");
                            dataModel.setState(Constant.STATE_FAILURE);
                        }
                    }
                }
                return AES.encode(model);//加密返回
            } else {
                dataModel.setData("");
                dataModel.setMsg("接口超时");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
        } catch (Exception e) {
            logger.info("addRobotAccessoriesApplication", e);
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * @Method getRobotAccessoriesApplicationList
     * @Author 郭凯
     * @Version 1.0
     * @Description APP查询配件申请列表
     * @Return java.lang.String
     * @Date 2021/6/16 14:18
     */
    @RequestMapping("/getRobotAccessoriesApplicationList")
    @ResponseBody
    public String getRobotAccessoriesApplicationList(BaseQueryVo BaseQueryVo, String content) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //景区ID
            String scenicSpotId = jsonobject.getString("scenicSpotId");
            //处理进度
            String processingProgress = jsonobject.getString("processingProgress");
            //景区ID，如果为空，直接return
//            if (ToolUtil.isEmpty(scenicSpotId)) {
//                dataModel.setData("");
//                dataModel.setMsg("景区ID为空，请传入景区ID!");
//                dataModel.setState(Constant.STATE_FAILURE);
//                dataModel.setType(Constant.STATE_FAILURE);
//                String model = JsonUtils.toString(dataModel);//对象转JSON
//                return AES.encode(model);//加密返回
//            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            Map<String, String> search = new HashMap<>();
            search.put("scenicSpotId", scenicSpotId);
            //
            search.put("processingProgress", processingProgress);
            search.put("userId", appUsers.getUserId().toString());
            BaseQueryVo.setSearch(search);
            PageInfo<SysRobotAccessoriesApplication> page = sysRobotAccessoriesApplicationService.getRobotAccessoriesApplicationList(BaseQueryVo);
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("list", page.getList());
            dataMap.put("pages", page.getPages());
            dataMap.put("pageNum", page.getPageNum());
            dataMap.put("total", page.getTotal());
            dataModel.setData(dataMap);
            dataModel.setMsg("");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
            System.out.println(model);
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * @Method editApproval
     * @Author 郭凯
     * @Version 1.0
     * @Description APP审批配件申请
     * @Return java.lang.String
     * @Date 2021/6/16 14:54
     */
    @RequestMapping("/editApproval")
    @ResponseBody
    public String editApproval(@NotNull(message = "配件申请ID不能为空") String accessoriesApplicationId, @NotNull(message = "请选择审批") String processingProgress, @NotNull(message = "请输入审批意见") String approvalRecord,
                               @NotNull(message = "longinTokenId不能为空") String longinTokenId, @NotNull(message = "longinTokenId不能为空") String scenicSpotId, String approvalProgress) {
        ReturnModel dataModel = new ReturnModel();
        try {
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            if ("0".equals(appUsers.getUserApproval())) {
                dataModel.setData("");
                dataModel.setMsg("没有审核权限，无法审核");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            SysRobotAccessoriesApplication SysRobotAccessoriesApplication = new SysRobotAccessoriesApplication();
            int i = 0;
            if ("2".equals(approvalProgress)) {
                SysRobotAccessoriesApplication.setAccessoriesApplicationId(Long.parseLong(accessoriesApplicationId));
                SysRobotAccessoriesApplication.setProcessingProgress("8");
                SysRobotAccessoriesApplication.setApprovalRecord(approvalRecord);
                SysRobotAccessoriesApplication.setApprovalProgress(approvalProgress);
                SysRobotAccessoriesApplication.setUpdateDate(DateUtil.currentDateTime());
                i = sysRobotAccessoriesApplicationService.editApproval(SysRobotAccessoriesApplication);

            } else {
                SysRobotAccessoriesApplication.setAccessoriesApplicationId(Long.parseLong(accessoriesApplicationId));
                SysRobotAccessoriesApplication.setProcessingProgress(processingProgress);
                SysRobotAccessoriesApplication.setApprovalRecord(approvalRecord);
                SysRobotAccessoriesApplication.setApprovalProgress(approvalProgress);
                SysRobotAccessoriesApplication.setUpdateDate(DateUtil.currentDateTime());
                i = sysRobotAccessoriesApplicationService.editApproval(SysRobotAccessoriesApplication);
            }
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("成功");
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                Map<String, Object> search = new HashMap<>();
                search.put("scenicSpotId", scenicSpotId);
                search.put("resCode", "AFTER_SALES_PERSON_IN_CHARGE");
                List<SysAppUsers> appUsersList = appUserService.getAppUsersByScenicIdList(search);
                if (ToolUtil.isNotEmpty(appUsersList) && appUsersList.size() > 0) {
                    for (SysAppUsers sysAppUsers : appUsersList) {
                        // 个推推送消息到APP端
                        String isSuccess = WeChatGtRobotAppPush.singlePushApp(sysAppUsers.getUserClientGtId(), "审批结果", sysAppUsers.getScenicSpotName() + "," + "配件审批结果" + "," + DictUtils.getApplicantProcessingProgressMap().get(approvalProgress));
                        if ("1".equals(isSuccess)) {
                            dataModel.setData("");
                            dataModel.setMsg("发送成功！");
                            dataModel.setState(Constant.STATE_SUCCESS);
                        } else {
                            dataModel.setData("");
                            dataModel.setMsg("发送失败！");
                            dataModel.setState(Constant.STATE_FAILURE);
                        }
                    }
                }
                return AES.encode(model);//加密返回
            } else {
                dataModel.setData("");
                dataModel.setMsg("接口超时");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }


    /**
     * @Method confirmReceipt
     * @Author 郭凯
     * @Version 1.0
     * @Description 修改为已确认收货
     * @Return java.lang.String
     * @Date 2021/6/16 15:56
     */
    @RequestMapping("/confirmReceipt")
    @ResponseBody
    public String confirmReceipt(@NotNull(message = "配件申请ID不能为空") String accessoriesApplicationId, String accessoriesReceivedType, String signInPicture, @NotNull(message = "longinTokenId不能为空") String longinTokenId) {
        ReturnModel dataModel = new ReturnModel();
        try {
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            SysRobotAccessoriesApplication SysRobotAccessoriesApplication = new SysRobotAccessoriesApplication();
            SysRobotAccessoriesApplication.setAccessoriesApplicationId(Long.parseLong(accessoriesApplicationId));
            SysRobotAccessoriesApplication.setAccessoriesReceivedType("1");
            SysRobotAccessoriesApplication.setProcessingProgress("7");
            SysRobotAccessoriesApplication.setUpdateDate(DateUtil.currentDateTime());
            SysRobotAccessoriesApplication.setSignInPicture(signInPicture);
            int i = sysRobotAccessoriesApplicationService.editApproval(SysRobotAccessoriesApplication);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("成功");
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            } else {
                dataModel.setData("");
                dataModel.setMsg("接口超时");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * @Method confirmReceipt
     * @Author 郭凯
     * @Version 1.0
     * @Description 修改为已确认收货(多配件)
     * @Return java.lang.String
     * @Date 2021/6/16 15:56
     */
    @RequestMapping("/confirmReceiptMany")
    @ResponseBody
    public String confirmReceiptMany(@NotNull(message = "配件申请ID不能为空") String accessoriesApplicationId, String signInPicture, String content) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            SysRobotAccessoriesApplication SysRobotAccessoriesApplication = new SysRobotAccessoriesApplication();
            SysRobotAccessoriesApplication.setAccessoriesApplicationId(Long.parseLong(accessoriesApplicationId));
            SysRobotAccessoriesApplication.setAccessoriesReceivedType("1");
            SysRobotAccessoriesApplication.setProcessingProgress("7");
            SysRobotAccessoriesApplication.setUpdateDate(DateUtil.currentDateTime());
            SysRobotAccessoriesApplication.setSignInPicture(signInPicture);
            int i = sysRobotAccessoriesApplicationService.editApprovalMany(SysRobotAccessoriesApplication);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("成功");
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            } else {
                dataModel.setData("");
                dataModel.setMsg("接口超时");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * @Method confirmReceipt
     * @Author 郭凯
     * @Version 1.0
     * @Description 配件发货单为已确认收货
     * @Return java.lang.String
     * @Date 2021/6/16 15:56
     */
    @RequestMapping("/SignForDelivery")
    @ResponseBody
    public String signForDelivery(@NotNull(message = "配件申请ID不能为空") String id, String signInPicture, String content) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
//            SysRobotAccessoriesApplication SysRobotAccessoriesApplication = new SysRobotAccessoriesApplication();
//            SysRobotAccessoriesApplication.setAccessoriesApplicationId(Long.parseLong(accessoriesApplicationId));
//            SysRobotAccessoriesApplication.setAccessoriesReceivedType("1");
//            SysRobotAccessoriesApplication.setProcessingProgress("7");
//            SysRobotAccessoriesApplication.setUpdateDate(DateUtil.currentDateTime());
//            SysRobotAccessoriesApplication.setSignInPicture(signInPicture);
            int i = sysRobotAccessoriesApplicationService.signForDelivery(id, signInPicture);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("成功");
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            } else {
                dataModel.setData("");
                dataModel.setMsg("接口超时");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }


    /**
     * @Author zhang
     * @Version 1.0
     * @Description 机器人配件申请详情
     * @Return
     * @Date
     */
    @RequestMapping("/getAccessoriesApplicationDetails")
    @ResponseBody
    public String accessoriesDetails(String content) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            //申请配件详情
            String accessoriesApplicationId = jsonobject.getString("accessoriesApplicationId");
            //判断配件申请id是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(accessoriesApplicationId)) {
                dataModel.setData("");
                dataModel.setMsg("配件申请id为空，请传入配件申请id!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }


            String scenicSpotName = jsonobject.getString("scenicSpotName");


            SysRobotAccessoriesApplication sysRobotAccessoriesApplication = sysRobotAccessoriesApplicationService.accessoriesDetails(accessoriesApplicationId,scenicSpotName    );
            dataModel.setData(sysRobotAccessoriesApplication);
            dataModel.setMsg("");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回

        } catch (Exception e) {
            logger.info("getAppAccessoriesApplicationType", e);
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }


    /**
     * @Description: 获取地址信息
     * @data:[spotId, name, phone, type]
     * @return: java.lang.String
     * @Author: 自定义作者名称
     * @Date: 2023-01-05 16:32:24
     */
    @RequestMapping("/list")
    @ResponseBody
//    String spotId , String name , String phone , Integer type
    public String list(String content) {
//        log.info("前端传参为{}",content);
        ReturnModel dataModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String spotId = jsonobject.getString("spotId");
            String name = jsonobject.getString("name");
            String phone = jsonobject.getString("phone");
            String type = jsonobject.getString("type");
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("spotId", spotId);
            dataMap.put("name", name);
            dataMap.put("phone", phone);
            dataMap.put("type", type);
            List<Address> info = service.lists(dataMap);
            dataModel.setData(info);
            dataModel.setMsg("");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回

        } catch (Exception e) {
            logger.info("getAppAccessoriesApplicationType", e);
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }


    /**
     * @Description: 机器人配件发货
     * @data:[content]
     * @return: java.lang.String
     * @Author: 吴顺顺
     * @Date: 2023-01-06 14:57:12
     */
    @RequestMapping("/partsDelivery")
    @ResponseBody
//    传值：发货厂房id、收件信息、发货说明，longinTokenId
    public String partsDelivery(String content) {
//        log.info("前端传参为{}",content);
        ReturnModel dataModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                String model = JsonUtils.toString(new ReturnModel("", "加密参数不能为空", Constant.STATE_FAILURE, Constant.STATE_FAILURE));//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                String model = JsonUtils.toString(new ReturnModel("", "TokenId为空，请传入TokenId!", Constant.STATE_FAILURE, Constant.STATE_FAILURE));
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                String model = JsonUtils.toString(new ReturnModel("", "TokenId已过期，请重新登陆!", Constant.STATE_FAILURE, Constant.STATE_FAILURE));
                return AES.encode(model);//加密返回
            }
            String accessoriesApplicationId = jsonobject.getString("accessoriesApplicationId");
            String warehouseId = jsonobject.getString("warehouseId");
            String shippingInstructions = jsonobject.getString("shippingInstructions");
            String courierNumber = jsonobject.getString("courierNumber");
            String expressfee = jsonobject.getString("expressfee");
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("accessoriesApplicationId", accessoriesApplicationId);
            dataMap.put("warehouseId", warehouseId);
            dataMap.put("shippingInstructions", shippingInstructions);
            dataMap.put("processingProgress", 5);
            dataMap.put("courierNumber", courierNumber);
            dataMap.put("expressfee", expressfee);
            int i = sysRobotAccessoriesApplicationService.update(dataMap);
            String model = JsonUtils.toString(new ReturnModel(i, "提交发货单成功", Constant.STATE_SUCCESS, Constant.STATE_SUCCESS));
            return AES.encode(model);//加密返回

        } catch (Exception e) {
            logger.info("getAppAccessoriesApplicationType", e);
            String model = JsonUtils.toString(new ReturnModel("", "接口超时", Constant.STATE_FAILURE, Constant.STATE_FAILURE));
            return AES.encode(model);//加密返回
        }
    }

    /**
     * 机器人配件发货(多配件)(审核)
     *
     * @param content
     * @return
     */
    @RequestMapping("/partsDeliveryMany")
    @ResponseBody
//    传值：发货厂房id、收件信息、发货说明，longinTokenId
    public String partsDeliveryMany(String content, SysRobotAccessoriesApplication sysRobotAccessoriesApplication) {
//        log.info("前端传参为{}",content);
        ReturnModel dataModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                String model = JsonUtils.toString(new ReturnModel("", "加密参数不能为空", Constant.STATE_FAILURE, Constant.STATE_FAILURE));//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                String model = JsonUtils.toString(new ReturnModel("", "TokenId为空，请传入TokenId!", Constant.STATE_FAILURE, Constant.STATE_FAILURE));
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                String model = JsonUtils.toString(new ReturnModel("", "TokenId已过期，请重新登陆!", Constant.STATE_FAILURE, Constant.STATE_FAILURE));
                return AES.encode(model);//加密返回
            }
//            String accessoriesApplicationId = jsonobject.getString("accessoriesApplicationId");
//            String warehouseId = jsonobject.getString("warehouseId");
//            String shippingInstructions = jsonobject.getString("shippingInstructions");
//            String courierNumber = jsonobject.getString("courierNumber");
//            String expressfee = jsonobject.getString("expressfee");
//            Map<String,Object> dataMap = new HashMap<>();
//            dataMap.put("accessoriesApplicationId",accessoriesApplicationId);
//            dataMap.put("warehouseId",warehouseId);
//            dataMap.put("shippingInstructions",shippingInstructions);
//            dataMap.put("processingProgress",5);
//            dataMap.put("courierNumber",courierNumber);
//            dataMap.put("expressfee",expressfee);
            int i = sysRobotAccessoriesApplicationService.updateMany(sysRobotAccessoriesApplication);
            String model = JsonUtils.toString(new ReturnModel(i, "提交发货单成功", Constant.STATE_SUCCESS, Constant.STATE_SUCCESS));
            return AES.encode(model);//加密返回

        } catch (Exception e) {
            logger.info("getAppAccessoriesApplicationType", e);
            String model = JsonUtils.toString(new ReturnModel("", "接口超时", Constant.STATE_FAILURE, Constant.STATE_FAILURE));
            return AES.encode(model);//加密返回
        }
    }


    //添加快递单号
    @RequestMapping("/partsDeliveryOrder")
    @ResponseBody
//    传值：发货厂房id、收件信息、发货说明，longinTokenId
    public String partsDelivery0rder(String content) {
//        log.info("前端传参为{}", content);
        ReturnModel dataModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String accessoriesApplicationId = jsonobject.getString("accessoriesApplicationId");
//            String warehouseId = jsonobject.getString("warehouseId");
//            String shippingInstructions = jsonobject.getString("shippingInstructions");
            String courierNumber = jsonobject.getString("courierNumber");
            String expressfee = jsonobject.getString("expressfee");
            Map<String, Object> dataMap = new HashMap<>();
//            if (Objects.equals(warehouseId,"")&&Objects.equals(shippingInstructions,"")){
            dataMap.put("accessoriesApplicationId", accessoriesApplicationId);
            dataMap.put("expressfee", expressfee);
            dataMap.put("courierNumber", courierNumber);
            dataMap.put("processingProgress", 6);
//            }else if (Objects.equals(expressfee,"")&&Objects.equals(expressfee,"")){
//            dataMap.put("accessoriesApplicationId", accessoriesApplicationId);
//            dataMap.put("warehouseId", warehouseId);
//            dataMap.put("shippingInstructions", shippingInstructions);
//            dataMap.put("processingProgressName", 5);
//            }
            int i = sysRobotAccessoriesApplicationService.updateExPressfee(dataMap);
            dataModel.setData(i);
            dataModel.setMsg("提交发货单成功");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSO
            return AES.encode(model);//加密返回

        } catch (Exception e) {
            logger.info("getAppAccessoriesApplicationType", e);
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    //添加快递单号(多配件)
    @RequestMapping("/partsDeliveryOrderMany")
    @ResponseBody
//    传值：发货厂房id、收件信息、发货说明，longinTokenId
    public String partsDelivery0rderMany(String content, SysRobotAccessoriesApplication sysRobotAccessoriesApplication) {
//        log.info("前端传参为{}", content);
        ReturnModel dataModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
//            String accessoriesApplicationId = jsonobject.getString("accessoriesApplicationId");
////            String warehouseId = jsonobject.getString("warehouseId");
////            String shippingInstructions = jsonobject.getString("shippingInstructions");
//            String courierNumber = jsonobject.getString("courierNumber");
//            String expressfee = jsonobject.getString("expressfee");
//            Map<String, Object> dataMap = new HashMap<>();
////            if (Objects.equals(warehouseId,"")&&Objects.equals(shippingInstructions,"")){
//            dataMap.put("accessoriesApplicationId",accessoriesApplicationId);
//            dataMap.put("expressfee",expressfee);
//            dataMap.put("courierNumber",courierNumber);
//            dataMap.put("processingProgress",6);
//            }else if (Objects.equals(expressfee,"")&&Objects.equals(expressfee,"")){
//            dataMap.put("accessoriesApplicationId", accessoriesApplicationId);
//            dataMap.put("warehouseId", warehouseId);
//            dataMap.put("shippingInstructions", shippingInstructions);
//            dataMap.put("processingProgressName", 5);
//            }
            int i = sysRobotAccessoriesApplicationService.updateExPressfeeMany(sysRobotAccessoriesApplication);
            dataModel.setData(i);
            dataModel.setMsg("添加快递单号成功");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSO
            return AES.encode(model);//加密返回

        } catch (Exception e) {
            logger.info("getAppAccessoriesApplicationType", e);
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }


    @RequestMapping("/selectWarehouse")
    @ResponseBody
//    传值：发货厂房id、收件信息、发货说明，longinTokenId
    public String selectWarehouse(String content) {
//        log.info("前端传参为{}",content);
        ReturnModel dataModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String spotId = jsonobject.getString("spotId");
            String name = jsonobject.getString("name");
            String phone = jsonobject.getString("phone");
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("spotId", spotId);
            dataMap.put("name", name);
            dataMap.put("phone", phone);
            dataMap.put("type", 3);
            List<Address> list = sysRobotAccessoriesApplicationService.selectWarehouse(dataMap);
            dataModel.setData(list);
            dataModel.setMsg("发货库房列表展示成功");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回

        } catch (Exception e) {
            logger.info("getAppAccessoriesApplicationType", e);
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * @Method getRobotAccessoriesApplicationList
     * @Author 郭凯
     * @Version 1.0
     * @Description 根据配件申请id获取有此配件的仓库
     * @Return java.lang.String
     * @Date 2021/6/16 14:18
     */
    @RequestMapping("/getGoodsStock")
    @ResponseBody
    public String getGoodsStock(String accessoriesApplicationId, String content) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //景区ID
            String scenicSpotId = jsonobject.getString("scenicSpotId");
            //处理进度
            String processingProgress = jsonobject.getString("processingProgress");
            //景区ID，如果为空，直接return
//            if (ToolUtil.isEmpty(scenicSpotId)) {
//                dataModel.setData("");
//                dataModel.setMsg("景区ID为空，请传入景区ID!");
//                dataModel.setState(Constant.STATE_FAILURE);
//                dataModel.setType(Constant.STATE_FAILURE);
//                String model = JsonUtils.toString(dataModel);//对象转JSON
//                return AES.encode(model);//加密返回
//            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            List<GoodsStock> list = sysRobotAccessoriesApplicationService.getGoodsStock(accessoriesApplicationId);

            dataModel.setData(list);
            dataModel.setMsg("");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
            System.out.println(model);
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            e.printStackTrace();
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * @Method getRobotAccessoriesApplicationList
     * @Author 郭凯
     * @Version 1.0
     * @Description 根据配件id获取有此配件的仓库
     * @Return java.lang.String
     * @Date 2021/6/16 14:18
     */
    @RequestMapping("/getManagementByGoodsStock")
    @ResponseBody
    public String getManageMentByGoodsStock(String managementId, Double num, String content) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //景区ID
            String scenicSpotId = jsonobject.getString("scenicSpotId");
            //处理进度
            String processingProgress = jsonobject.getString("processingProgress");
            //景区ID，如果为空，直接return
//            if (ToolUtil.isEmpty(scenicSpotId)) {
//                dataModel.setData("");
//                dataModel.setMsg("景区ID为空，请传入景区ID!");
//                dataModel.setState(Constant.STATE_FAILURE);
//                dataModel.setType(Constant.STATE_FAILURE);
//                String model = JsonUtils.toString(dataModel);//对象转JSON
//                return AES.encode(model);//加密返回
//            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            List<GoodsStock> list = sysRobotAccessoriesApplicationService.getManagementByGoodsStock(managementId, num);

            dataModel.setData(list);
            dataModel.setMsg("");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
            System.out.println(model);
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            e.printStackTrace();
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }


    /**
     * @Method getRobotAccessoriesApplicationList
     * @Author 郭凯
     * @Version 1.0
     * @Description 根据景区id获取收货地址
     * @Return java.lang.String
     * @Date 2021/6/16 14:18
     */
    @RequestMapping("/getSpotIdByAddress")
    @ResponseBody
    public String getSpotIdByAddress(String content) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //景区ID
            String scenicSpotId = jsonobject.getString("scenicSpotId");
            String type = jsonobject.getString("type");
            //处理进度
            String processingProgress = jsonobject.getString("processingProgress");
            //景区ID，如果为空，直接return
//            if (ToolUtil.isEmpty(scenicSpotId)) {
//                dataModel.setData("");
//                dataModel.setMsg("景区ID为空，请传入景区ID!");
//                dataModel.setState(Constant.STATE_FAILURE);
//                dataModel.setType(Constant.STATE_FAILURE);
//                String model = JsonUtils.toString(dataModel);//对象转JSON
//                return AES.encode(model);//加密返回
//            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            Address address = sysRobotAccessoriesApplicationService.getSpotIdByAddress(scenicSpotId, type);

            dataModel.setData(address);
            dataModel.setMsg("");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
            System.out.println(model);
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            e.printStackTrace();
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }


    /**
     * @Method getRobotAccessoriesApplicationList
     * @Author 郭凯
     * @Version 1.0
     * @Description 根据配件申请id获取申请详情中配件列表
     * @Return java.lang.String
     * @Date 2021/6/16 14:18
     */
    @RequestMapping("/getApplicationIdByDetailList")
    @ResponseBody
    public String getApplicationIdByDetailList(String accessoriesApplicationId, String content) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //景区ID
            String scenicSpotId = jsonobject.getString("scenicSpotId");
            //处理进度
            String processingProgress = jsonobject.getString("processingProgress");
            //景区ID，如果为空，直接return
//            if (ToolUtil.isEmpty(scenicSpotId)) {
//                dataModel.setData("");
//                dataModel.setMsg("景区ID为空，请传入景区ID!");
//                dataModel.setState(Constant.STATE_FAILURE);
//                dataModel.setType(Constant.STATE_FAILURE);
//                String model = JsonUtils.toString(dataModel);//对象转JSON
//                return AES.encode(model);//加密返回
//            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            List<SysRobotAccessoriesApplicationDetail> list = sysRobotAccessoriesApplicationService.getApplicationIdByDetailList(accessoriesApplicationId);

            dataModel.setData(list);
            dataModel.setMsg("");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
            System.out.println(model);
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            e.printStackTrace();
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }


    /**
     * @Method getRobotAccessoriesApplicationList
     * @Author 郭凯
     * @Version 1.0
     * @Description 获取景区发货单列表
     * @Return java.lang.String
     * @Date 2021/6/16 14:18
     */
    @RequestMapping("/getSpotIdSendOutGoodsList")
    @ResponseBody
    public String getSpotIdSendOutGoodsList(String content, Integer pageNum, Integer pageSize) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //景区ID
            String scenicSpotId = jsonobject.getString("scenicSpotId");
//            //处理进度
            String isSendOutGoods = jsonobject.getString("isSendOutGoods");
            //景区ID，如果为空，直接return
//            if (ToolUtil.isEmpty(scenicSpotId)) {
//                dataModel.setData("");
//                dataModel.setMsg("景区ID为空，请传入景区ID!");
//                dataModel.setState(Constant.STATE_FAILURE);
//                dataModel.setType(Constant.STATE_FAILURE);
//                String model = JsonUtils.toString(dataModel);//对象转JSON
//                return AES.encode(model);//加密返回
//            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            List<SysRobotAccessoriesApplicationDetail> list = sysRobotAccessoriesApplicationService.getSpotIdSendOutGoodsList(appUsers.getUserId(),scenicSpotId,isSendOutGoods);

            dataModel.setData(list);
            dataModel.setMsg("");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
            System.out.println(model);
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            e.printStackTrace();
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }


    /**
     * @Method getRobotAccessoriesApplicationList
     * @Author 郭凯
     * @Version 1.0
     * @Description 添加配件发货单快递单号
     * @Return java.lang.String
     * @Date 2021/6/16 14:18
     */
    @RequestMapping("/editApplicationDetail")
    @ResponseBody
    public String editApplicationDetail(String content, SysRobotAccessoriesApplicationDetail sysRobotAccessoriesApplicationDetail) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            int i = sysRobotAccessoriesApplicationService.editApplicationDetail(sysRobotAccessoriesApplicationDetail);

            if (i == 0) {
                dataModel.setData(i);
                dataModel.setMsg("没有工厂地址");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
                System.out.println(model);
                return AES.encode(model);//加密返回
            }

            dataModel.setData(i);
            dataModel.setMsg("");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
            System.out.println(model);
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            e.printStackTrace();
            dataModel.setData("");
            dataModel.setMsg("接口超时");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }


}
