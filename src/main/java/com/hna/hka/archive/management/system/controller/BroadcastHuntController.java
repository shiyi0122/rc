package com.hna.hka.archive.management.system.controller;


import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.ScenicSpot;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.service.SysScenicSpotService;
import com.hna.hka.archive.management.system.service.SysScenicSpotTreasureHuntService;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.text.ParseException;
import java.util.*;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: TreasureHuntController
 * @Author: xjl
 * @Description: 寻宝
 * @Date: 2023/1/23
 * @Version: 1.0
 */
@Api(tags = "景点寻宝奖品相关（新）")
@RestController
@RequestMapping("/system/broadcastHunt")
@CrossOrigin(maxAge = 3600)
public class BroadcastHuntController extends PublicUtil {

    @Autowired
    private SysScenicSpotTreasureHuntService sysScenicSpotTreasureHuntService;

    @Autowired
    private SysScenicSpotService sysScenicSpotService;

    @Autowired
    private HttpSession session;

    @ApiOperation("查询寻宝景点奖品列表")
    @ResponseBody
    @RequestMapping("/getTreasureHuntList")
    public PageDataResult getTreasureList(@RequestParam("pageNum") Integer pageNum,
                                          @RequestParam("pageSize") Integer pageSize, SysScenicSpotTreasureHunt sysScenicSpotTreasureHunt) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String, String> search = new HashMap<>();
        try {
            if (null == pageNum) {
                pageNum = 1;
            }
            if (null == pageSize) {
                pageSize = 10;
            }
            sysScenicSpotTreasureHunt.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            search.put("scenicSpotId", sysScenicSpotTreasureHunt.getScenicSpotId().toString());
            search.put("scenicSpotName", sysScenicSpotTreasureHunt.getScenicSpotName());
            search.put("treasureName", sysScenicSpotTreasureHunt.getTreasureName());
            // search.put("scenicSpotId",sysScenicSpotTreasureHunt.getScenicSpotId().toString());
            pageDataResult = sysScenicSpotTreasureHuntService.getTreasureList(pageNum, pageSize, search);
        } catch (Exception e) {
            logger.info("景点列表查询失败", e);
        }
        return pageDataResult;
    }

    @ApiOperation("查询寻宝景点奖品列表(新)")
    @ResponseBody
    @RequestMapping("/getTreasureHuntNewList")
    public PageDataResult getTreasureHuntNewList(@RequestParam("pageNum") Integer pageNum,
                                                 @RequestParam("pageSize") Integer pageSize, SysScenicSpotTreasureHunt sysScenicSpotTreasureHunt) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String, String> search = new HashMap<>();
        try {
            if (null == pageNum) {
                pageNum = 1;
            }
            if (null == pageSize) {
                pageSize = 10;
            }
            sysScenicSpotTreasureHunt.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            search.put("scenicSpotId", sysScenicSpotTreasureHunt.getScenicSpotId().toString());
            search.put("scenicSpotName", sysScenicSpotTreasureHunt.getScenicSpotName());
            search.put("treasureName", sysScenicSpotTreasureHunt.getTreasureName());
            // search.put("scenicSpotId",sysScenicSpotTreasureHunt.getScenicSpotId().toString());
            pageDataResult = sysScenicSpotTreasureHuntService.getTreasureList(pageNum, pageSize, search);
        } catch (Exception e) {
            logger.info("景点列表查询失败", e);
        }
        return pageDataResult;
    }

    @ApiOperation("添加寻宝景点奖品")
    @ResponseBody
    @RequestMapping("/addTreasureHunt")
    public ReturnModel addScenicTreasure(@RequestParam("file") MultipartFile file, SysScenicSpotTreasureHunt treasureHunt) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotTreasureHuntService.addTreasure(treasureHunt, file);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点奖品新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else if (i == 3) {
                returnModel.setData("");
                returnModel.setMsg("图片大小大于2M！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点奖品新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("addBroadcast", e);
            returnModel.setData("");
            returnModel.setMsg("寻宝景点奖品新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @ApiOperation("修改寻宝景点奖品")
    @ResponseBody
    @RequestMapping("/editTreasureHunt")
    public ReturnModel editScenicTreasure(@RequestParam("file") MultipartFile file, SysScenicSpotTreasureHunt treasureHunt) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotTreasureHuntService.editTreasure(treasureHunt, file);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点奖品修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else if (i == 3) {
                returnModel.setData("");
                returnModel.setMsg("图片大小大于2M！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点奖品修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("editBroadcast", e);
            returnModel.setData("");
            returnModel.setMsg("寻宝景点奖品修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author zhang
     * @Description 删除寻宝景点奖品
     * @Param [broadcastId]
     **/
    @RequestMapping("/delTreasureHunt")
    @ResponseBody
    public ReturnModel delTreasure(@NotBlank(message = "景点ID不能为空") Long treasureId, @NotBlank(message = "景区ID不能为空") Long scenicSpotId) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotTreasureHuntService.delTreasure(treasureId, scenicSpotId);
            if (i > 0) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点奖品删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点奖品删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("寻宝景点奖品删除失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * @Method importExcel
     * @Author zhang
     * @Description 导入寻宝景点奖品Excel表
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/4/2 13:38
     */
//    @RequestMapping("/importExcel")
//    @ResponseBody
//    public ReturnModel importExcel(@RequestParam("file") MultipartFile multipartFile){
//        ReturnModel returnModel = new ReturnModel();
//        try {
//            ImportParams params = new ImportParams();
//            params.setTitleRows(1);
//            params.setHeadRows(1);
//            List<SysScenicSpotBroadcastExtendVo> scenicSpotBroadcast = ExcelImportUtil.importExcel(multipartFile.getInputStream(),SysScenicSpotBroadcastExtendVo.class, params);
//            for (SysScenicSpotBroadcastExtendVo broadcastExtendVo : scenicSpotBroadcast){
//                SysScenicSpotTreasureHunt broadcast = new SysScenicSpotTreasureHunt();
//                broadcast.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
////                broadcast.setBroadcastName(broadcastExtendVo.getBroadcastName());
////                broadcast.setBroadcastGps(broadcastExtendVo.getBroadcastGps());
////                broadcast.setBroadcastGpsBaiDu(broadcastExtendVo.getBroadcastGpsBaiDu());
//                sysScenicSpotTreasureHuntService.addTreasure(broadcast);
//            }
//            returnModel.setData("");
//            returnModel.setMsg("导入成功");
//            returnModel.setState(Constant.STATE_SUCCESS);
//            return returnModel;
//        }catch (Exception e){
//            logger.info("景点奖品列表导入Excel",e);
//            returnModel.setData("");
//            returnModel.setMsg("导入失败！（请联系管理员）");
//            returnModel.setState(Constant.STATE_FAILURE);
//            return returnModel;
//        }
//    }
    @ApiOperation("根据ID查询景区")
    @GetMapping("/getSpotId")
    public ReturnModel getSpotId(Long spotId) {
        ReturnModel returnModel = new ReturnModel();
        List<SysScenicSpot> spotId1 = sysScenicSpotTreasureHuntService.getSpotId(spotId);
        returnModel.setData(spotId1);
        returnModel.setMsg("景区查询成功！");
        returnModel.setState(Constant.STATE_SUCCESS);
        return returnModel;
    }

    @ApiOperation("创建奖池")
    @PostMapping("/addJackpotNew")
    public ReturnModel addJackpotNew(@RequestBody SysScenicSpotTreasureNewJackpot treasureNewJackpot) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotTreasureHuntService.addJackpotNew(treasureNewJackpot);
            if (i > 0) {
                returnModel.setData("");
                returnModel.setMsg("寻宝池创建成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("寻宝池创建失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("寻宝池创建失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    @ApiOperation("修改奖池")
    @PostMapping("/updateJackpotNew")
    public ReturnModel updateJackpotNew(@RequestBody SysScenicSpotTreasureNewJackpot treasureNewJackpot) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotTreasureHuntService.updateJackpotNew(treasureNewJackpot);
            if (i > 0) {
                returnModel.setData("");
                returnModel.setMsg("寻宝池修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("寻宝池修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("寻宝池创建失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    @ApiOperation("查看奖池")
    @PostMapping("/getJackpotNew")
    public ReturnModel getJackpotNew(@RequestBody SysScenicSpotTreasureNewJackpot treasureNewJackpot) {
        ReturnModel returnModel = new ReturnModel();
        try {
            PageInfo<SysScenicSpotTreasureNewJackpot> jackpotNew = sysScenicSpotTreasureHuntService.getJackpotNew(treasureNewJackpot);
            returnModel.setData(jackpotNew);
            returnModel.setMsg("寻宝池查询成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;

        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("寻宝池查询失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    @ApiOperation("删除奖池")
    @PostMapping("/delectJackpotNew")
    public ReturnModel delectJackpotNew(@RequestBody SysScenicSpotTreasureNewJackpot treasureNewJackpot) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotTreasureHuntService.delectJackpotNew(treasureNewJackpot);
            if (i > 0) {
                returnModel.setData("");
                returnModel.setMsg("寻宝池删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("寻宝池删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("寻宝池删除失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    @ApiOperation("查询用户积分")
    @PostMapping("/getUserInteGral")
    public ReturnModel getUserInteGral(@RequestBody SysCurrentUserIntegral userIntegral) {
        ReturnModel returnModel = new ReturnModel();
        try {
            PageInfo<SysCurrentUserIntegral> userInteGral = sysScenicSpotTreasureHuntService.getUserInteGral(userIntegral);
            returnModel.setData(userInteGral);
            returnModel.setMsg("查询成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;

        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("查询失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    @ApiOperation("修改用户积分表")
    @PostMapping("/updateUserInteGral")
    public ReturnModel updateUserInteGral(@RequestBody SysCurrentUserIntegral userIntegral) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotTreasureHuntService.updateUserInteGral(userIntegral);
            if (i > 0) {
                returnModel.setData("");
                returnModel.setMsg("用户积分修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("用户积分修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("用户积分修改失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @ApiOperation("查询积分修改日志表")
    @PostMapping("/getUserInteGralLog")
    public ReturnModel getUserInteGralLog(@RequestBody SysCurrentUserIntegralLog userIntegralLog) {
        ReturnModel returnModel = new ReturnModel();
        try {
            PageInfo<SysCurrentUserIntegralLog> userInteGralLog = sysScenicSpotTreasureHuntService.getUserInteGralLog(userIntegralLog);
            returnModel.setData(userInteGralLog);
            returnModel.setMsg("查询成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;

        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("查询失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    @ApiOperation("新增用户收货地址")
    @PostMapping("/addUserAddress")
    public ReturnModel addUserAddress(@RequestBody SysCurrentUserAddress userAddress) {
        ReturnModel returnModel = new ReturnModel();
        try {
            if (userAddress == null) {
                returnModel.setData("");
                returnModel.setMsg("新增失败,参数为空！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
            //用手机号查询用户是否存在，如果存在继续进行，不存在，查无此用户


            int i = sysScenicSpotTreasureHuntService.addUserAddress(userAddress);
            if (i > 0) {
                returnModel.setData("");
                returnModel.setMsg("新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("新增失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    @ApiOperation("查询用户收货地址表")
    @PostMapping("/getUserAddress")
    public ReturnModel getUserAddress(@RequestBody SysCurrentUserAddress userAddress) {
        ReturnModel returnModel = new ReturnModel();
        try {
            PageInfo<SysCurrentUserAddress> userAddress1 = sysScenicSpotTreasureHuntService.getUserAddress(userAddress);
            returnModel.setData(userAddress1);
            returnModel.setMsg("查询成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;

        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("查询失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @ApiOperation("修改用户收货地址表")
    @PostMapping("/editUserAddress")
    public ReturnModel editUserAddress(@RequestBody SysCurrentUserAddress userAddress) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotTreasureHuntService.editUserAddress(userAddress);
            if (i > 0) {
                returnModel.setData("");
                returnModel.setMsg("修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("修改失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    @ApiOperation("删除收货地址")
    @PostMapping("/delectUserAddress")
    public ReturnModel delectUserAddress(@RequestBody SysCurrentUserAddress userAddress) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotTreasureHuntService.delectUserAddress(userAddress);
            if (i > 0) {
                returnModel.setData("");
                returnModel.setMsg("删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("删除失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    @ApiOperation("查询用户奖品")
    @PostMapping("/getUserExchange")
    public ReturnModel getUserExchange(@RequestBody SysCurrentUserExchange userExchange) {
        ReturnModel returnModel = new ReturnModel();
        try {
            PageInfo<SysCurrentUserExchange> userExchanges = sysScenicSpotTreasureHuntService.getUserExchange(userExchange);
            returnModel.setData(userExchanges);
            returnModel.setMsg("查询成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;

        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("查询失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @ApiOperation("导出用户奖品Excel表")
    @GetMapping("/downloadUserExchange")
    public void downloadUserExchange(HttpServletResponse response, SysCurrentUserExchange userExchange) {
        List<SysCurrentUserExchange> userExchanges = null;
        userExchanges = sysScenicSpotTreasureHuntService.downloadUserExchange(userExchange);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("用户奖品列表", "用户奖品列表", SysCurrentUserExchange.class, userExchanges), "用户奖品列表" + dateTime + ".xls", response);
    }


    @ApiOperation("修改用户奖品")
    @PostMapping("/editUserExchange")
    public ReturnModel editUserExchange(MultipartFile file, SysCurrentUserExchange userExchange) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotTreasureHuntService.editUserExchange(file, userExchange);
            if (i > 0) {
                returnModel.setData("");
                returnModel.setMsg("修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else if (i == -1) {
                returnModel.setData("");
                returnModel.setMsg("未绑定地址！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("修改失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    @ApiOperation("查询宝箱")
    @PostMapping("/getBroadcastHunt")
    public ReturnModel getBroadcastHunt(@RequestBody SysScenicSpotBroadcastHunt broadcastHunt) {
        ReturnModel returnModel = new ReturnModel();
        try {
            PageInfo<SysScenicSpotBroadcastHunt> broadcastHunt1 = sysScenicSpotTreasureHuntService.getBroadcastHunt(broadcastHunt);
            returnModel.setData(broadcastHunt1);
            returnModel.setMsg("查询成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;

        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("查询失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    @ApiOperation("修改宝箱")
    @PostMapping("/editBroadcastHunt")
    public ReturnModel editBroadcastHunt(@RequestBody SysScenicSpotBroadcastHunt broadcastHunt) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotTreasureHuntService.editBroadcastHunt(broadcastHunt);
            if (i > 0) {
                returnModel.setData("");
                returnModel.setMsg("修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("修改失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    @ApiOperation("新增宝箱")
    @PostMapping("/addBroadcastHunt")
    public ReturnModel addBroadcastHunt(@RequestBody SysScenicSpotBroadcastHunt broadcastHunt) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotTreasureHuntService.addBroadcastHunt(broadcastHunt);
            if (i > 0) {
                returnModel.setData("");
                returnModel.setMsg("添加成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("添加失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("添加失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    @ApiOperation("删除宝箱")
    @PostMapping("/delectBroadcastHunt")
    public ReturnModel delectBroadcastHunt(@RequestBody SysScenicSpotBroadcastHunt broadcastHunt) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotTreasureHuntService.delectBroadcastHunt(broadcastHunt);
            if (i > 0) {
                returnModel.setData("");
                returnModel.setMsg("删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("删除失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    @ApiOperation("查询用户触发宝箱日志记录")
    @PostMapping("/getBroadcastHuntLog")
    public PageDataResult getBroadcastHuntLog(@RequestBody SysScenicSpotBroadcastHuntLog broadcastHuntLog) {
        PageDataResult pageDataResult = new PageDataResult();
        try {

            pageDataResult = sysScenicSpotTreasureHuntService.getBroadcastHuntLog(broadcastHuntLog);

        } catch (Exception e) {
            logger.info("查询用户触发宝箱日志记录查询失败", e);
        }
        return pageDataResult;
    }

    @GetMapping("/getbroadCast")
    @ApiOperation("景点查询")
    public ReturnModel getbroadCast(Long broadcastId, Long scenicSpotId) {
        ReturnModel returnModel = new ReturnModel();
        List<SysScenicSpotBroadcast> broadcasts = sysScenicSpotTreasureHuntService.getbroadCast(broadcastId, scenicSpotId);
        returnModel.setData(broadcasts);
        returnModel.setMsg("查询成功！");
        returnModel.setState(Constant.STATE_SUCCESS);
        return returnModel;
    }


    @ApiOperation("查询奖品列表")
    @PostMapping("/getTreasureNewHunt")
    public ReturnModel getTreasureNewHunt(@RequestBody SysScenicSpotTreasureNewHunt treasureNewHunt) {
        ReturnModel returnModel = new ReturnModel();
        try {
            PageInfo<SysScenicSpotTreasureNewHunt> treasureNewHunt1 = sysScenicSpotTreasureHuntService.getTreasureNewHunt(treasureNewHunt);
            returnModel.setData(treasureNewHunt1);
            returnModel.setMsg("查询成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;

        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("查询失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    @ApiOperation("新增奖品")
    @PostMapping("/addTreasureNewHunt")
    public ReturnModel addTreasureNewHunt(MultipartFile file, SysScenicSpotTreasureNewHunt treasureNewHunt) {
        ReturnModel returnModel = new ReturnModel();

            int i = sysScenicSpotTreasureHuntService.addTreasureNewHunt(file, treasureNewHunt);
            if (i > 0) {
                returnModel.setData("");
                returnModel.setMsg("新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
    }


    @ApiOperation("修改奖品")
    @PostMapping("/editTreasureNewHunt")
    public ReturnModel editTreasureNewHunt(MultipartFile file, SysScenicSpotTreasureNewHunt treasureNewHunt) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotTreasureHuntService.editTreasureNewHunt(file, treasureNewHunt);
            if (i > 0) {
                returnModel.setData("");
                returnModel.setMsg("修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("修改失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    @ApiOperation("删除奖品")
    @PostMapping("/delectTreasureNewHunt")
    public ReturnModel delectTreasureNewHunt(@RequestBody SysScenicSpotTreasureNewHunt treasureNewHunt) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotTreasureHuntService.delectTreasureNewHunt(treasureNewHunt);
            if (i > 0) {
                returnModel.setData("");
                returnModel.setMsg("删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("删除失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    @ApiOperation("用户获得某项奖品日志记录")
    @PostMapping("/getUserIntegralHuntLog")
    public ReturnModel getUserIntegralHuntLog(@RequestBody SysCurrentUserIntegralHuntLog integralHuntLog) {
        ReturnModel returnModel = new ReturnModel();
        try {
            PageInfo<SysCurrentUserIntegralHuntLog> userIntegralHuntLog = sysScenicSpotTreasureHuntService.getUserIntegralHuntLog(integralHuntLog);
            returnModel.setData(userIntegralHuntLog);
            returnModel.setMsg("查询成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;

        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("查询失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @PostMapping("/getScenicSpots")
    @ResponseBody
    public ReturnModel getScenicSpots(ScenicSpot scenicSpot) {
        ReturnModel returnModel = new ReturnModel();
        List<SysScenicSpot> list = sysScenicSpotService.getScenicSpotById(scenicSpot);
        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        return returnModel;
    }


    @ApiOperation("批量修改景区开关")
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

    @ApiOperation("查询用户兑换奖品日志")
    @PostMapping("/getUserExchangeLog")
    public ReturnModel getUserExchangeLog(@RequestBody SysCurrentUserExchangeLog userExchangeLog) {
        ReturnModel returnModel = new ReturnModel();
        try {
            PageInfo<SysCurrentUserExchangeLog> userExchangeLogs = sysScenicSpotTreasureHuntService.getUserExchangeLog(userExchangeLog);
            returnModel.setData(userExchangeLogs);
            returnModel.setMsg("查询成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;

        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("查询失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @ApiOperation("查询修改奖品日志")
    @PostMapping("/getUpdateTreasureLog")
    public ReturnModel getUpdateTreasureLog(@RequestBody SysCurrentUserUpdateTreasureLog userUpdateTreasureLog) {
        ReturnModel returnModel = new ReturnModel();
        try {
            PageInfo<SysCurrentUserUpdateTreasureLog> updateTreasureLog = sysScenicSpotTreasureHuntService.getUpdateTreasureLog(userUpdateTreasureLog);
            returnModel.setData(updateTreasureLog);
            returnModel.setMsg("查询成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("查询失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    @ApiOperation("查询寻宝统计详情")
    @PostMapping("/getTreasureHuntDetail")
    public ReturnModel getTreasureHuntDetail(@RequestBody SysOrder sysOrder) throws ParseException {
        ReturnModel returnModel = new ReturnModel();

        PageInfo<SysOrderDetail> sysOrderDetails = null;
        if (("").equals(sysOrder.getOrderStartTime()) || ("").equals(sysOrder.getOrderEndTime())) {
            sysOrder.setOrderStartTime(DateUtil.currentDateTime());
            sysOrder.setOrderEndTime(DateUtil.currentDateTime());
        }
        sysOrderDetails = sysScenicSpotTreasureHuntService.getTreasureHuntDetail(sysOrder);
        returnModel.setData(sysOrderDetails);
        returnModel.setMsg("查询成功！");
        returnModel.setState(Constant.STATE_SUCCESS);
        return returnModel;

    }

    @ApiOperation("寻宝详情导出Excel")
    @GetMapping("/exportTreasureHuntDetail")
    public void exportTreasureHuntDetail(HttpServletResponse response, SysOrder sysOrder) throws Exception {
        if (("").equals(sysOrder.getPageNum()) || ("").equals(sysOrder.getPageSize()) || sysOrder.getPageNum() == null || sysOrder.getPageSize() == null) {
            sysOrder.setPageNum(1);
            sysOrder.setPageSize(10000);
        }
        List<SysOrderDetail> sysOrderDetails = null;
        if (("").equals(sysOrder.getOrderStartTime()) || ("").equals(sysOrder.getOrderEndTime()) || sysOrder.getOrderStartTime() == null || sysOrder.getOrderEndTime() == null) {
            sysOrder.setOrderStartTime(DateUtil.currentDateTime());
            sysOrder.setOrderEndTime(DateUtil.currentDateTime());
        }
        PageInfo<SysOrderDetail> detail = sysScenicSpotTreasureHuntService.getTreasureHuntDetail(sysOrder);
        sysOrderDetails = detail.getList();
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("寻宝详情列表", "寻宝详情列表", SysOrderDetail.class, sysOrderDetails), "寻宝详情列表" + dateTime, response);

    }
}
