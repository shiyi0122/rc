package com.hna.hka.archive.management.system.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.ScenicSpot;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcastExtendVo;
import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcastExtendWithBLOBs;
import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcastHunt;
import com.hna.hka.archive.management.system.service.SysScenicSpotBroadcastHuntService;
import com.hna.hka.archive.management.system.service.SysScenicSpotService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "景点寻宝相关(新)")
@RestController
@RequestMapping("/system/treasureHuntNew")

public class TreasureHuntNewController extends PublicUtil {


    @Autowired
    private SysScenicSpotBroadcastHuntService sysScenicSpotBroadcastHuntService;

    @Autowired
    private SysScenicSpotService sysScenicSpotService;

    @Autowired
    private HttpSession session;

    @ApiOperation("查询寻宝景点列表")
    @ResponseBody
    @GetMapping("/getTreasureHuntListNew")
    public ReturnModel getTreasureHuntListNew(@RequestParam("pageNum") Integer pageNum,
                                              @RequestParam("pageSize") Integer pageSize, SysScenicSpotBroadcastHunt
                                                      sysScenicSpotBroadcastHunt) {
        ReturnModel returnModel = new ReturnModel();
        try {
            if (sysScenicSpotBroadcastHunt != null) {
                if (sysScenicSpotBroadcastHunt.getScenicSpotId() == null) {
                    long scenicSpotId = Long.parseLong(session.getAttribute("scenicSpotId").toString());
                    sysScenicSpotBroadcastHunt.setScenicSpotId(scenicSpotId);
                }
            }
//        long scenicSpotId = Long.valueOf("15698320289682");
            //查询当前景区的寻宝活动开关
            ScenicSpot scenicSpot = new ScenicSpot();
            scenicSpot.setScenicSpotId(String.valueOf(sysScenicSpotBroadcastHunt.getScenicSpotId()));
            List<SysScenicSpot> scenicSpotById = sysScenicSpotService.getScenicSpotById(scenicSpot);
            PageInfo<SysScenicSpotBroadcastHunt> broadcastList = sysScenicSpotBroadcastHuntService.getBroadcastList(pageNum, pageSize, sysScenicSpotBroadcastHunt);
            returnModel.setData(broadcastList);
            returnModel.setMsg("寻宝景点查询成功！");
            returnModel.setType(scenicSpotById.get(0).getHuntSwitch());
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("寻宝景点查询失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    @ApiOperation("添加寻宝景点")
    @ResponseBody
    @RequestMapping("/addBroadcastHuntNew")
    public ReturnModel addBroadcastHuntNew(@RequestBody SysScenicSpotBroadcastHunt broadcastHunt) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotBroadcastHuntService.addBroadcast(broadcastHunt);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("addBroadcast", e);
            returnModel.setData("");
            returnModel.setMsg("寻宝景点新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @ApiOperation("修改寻宝景点")
    @ResponseBody
    @RequestMapping("/editBroadcastHuntNew")
    public ReturnModel editBroadcastHuntNew(SysScenicSpotBroadcastHunt broadcastHunt) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotBroadcastHuntService.editBroadcast(broadcastHunt);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("editBroadcast", e);
            returnModel.setData("");
            returnModel.setMsg("寻宝景点修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author zhang
     * @Description 删除寻宝景点
     * @Param [broadcastId]
     **/
    @RequestMapping("/delBroadcastHuntNew")
    @ResponseBody
    public ReturnModel delBroadcastHuntNew(@NotBlank(message = "景点ID不能为空") Long broadcastId, @NotBlank(message = "景区ID不能为空") Long scenicSpotId) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotBroadcastHuntService.delBroadcast(broadcastId, scenicSpotId);
            if (i > 0) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("寻宝景点删除失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    @ApiOperation("启用/禁用寻宝景点")
    @ResponseBody
    @RequestMapping("/switchBroadcastHuntNew")
    public ReturnModel switchBroadcastHuntNew(SysScenicSpotBroadcastHunt broadcastHunt) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotBroadcastHuntService.switchBroadcast(broadcastHunt);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("editBroadcast", e);
            returnModel.setData("");
            returnModel.setMsg("寻宝景点修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     * @Author 张
     * @Description 寻宝景点内容详情列表查询
     * @Date 10:43 2020/11/6
     * @Param [pageNum, pageSize, sysScenicSpotBroadcastExtendWithBLOBs]
     **/
    @RequestMapping("/getBroadcastContentHuntListNew")
    @ResponseBody
    public PageDataResult getBroadcastContentHuntListNew(@RequestParam("pageNum") Integer pageNum,
                                                         @RequestParam("pageSize") Integer pageSize, SysScenicSpotBroadcastExtendWithBLOBs
                                                                 sysScenicSpotBroadcastExtendWithBLOBs) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        try {
            if (null == pageNum) {
                pageNum = 1;
            }
            if (null == pageSize) {
                pageSize = 10;
            }
            search.put("broadcastId", sysScenicSpotBroadcastExtendWithBLOBs.getBroadcastId());
            pageDataResult = sysScenicSpotBroadcastHuntService.getBroadcastContentHuntList(pageNum, pageSize, search);
        } catch (Exception e) {
            logger.info("景点内容详情列表查询失败", e);
        }
        return pageDataResult;
    }


    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 张
     * @Description 寻宝景点内容新增（文字）
     * @Date
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     **/
    @RequestMapping("/addBroadcastHuntContentExtendImageNew")
    @ResponseBody
    public ReturnModel addBroadcastHuntContentExtendImageNew(@RequestParam MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {
//            sysScenicSpotBroadcastExtendWithBLOBs.setScenicSpotId(15698320289682l);
            sysScenicSpotBroadcastExtendWithBLOBs.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            int i = sysScenicSpotBroadcastHuntService.addBroadcastHuntContentExtendImage(file, sysScenicSpotBroadcastExtendWithBLOBs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点内容新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else if (i == 2) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点播报图片文件上传格式不正确！(支持:png，jpg格式)！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else if (i == 3) {

                returnModel.setData("");
                returnModel.setMsg("图片大小大于2M！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点内容新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("addBroadcastContentExtendImage", e);
            returnModel.setData("");
            returnModel.setMsg("寻宝景点内容新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 张
     * @Description 寻宝景点内容新增（视频）
     * @Date
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     **/
    @RequestMapping("/addBroadcastHuntContentExtendVideoNew")
    @ResponseBody
    public ReturnModel addBroadcastHuntContentExtendVideoNew(@RequestParam("file") MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {
            if (!file.isEmpty()) {
                sysScenicSpotBroadcastExtendWithBLOBs.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
//                sysScenicSpotBroadcastExtendWithBLOBs.setScenicSpotId(15698320289682l);
                int i = sysScenicSpotBroadcastHuntService.addBroadcastHuntContentExtendVideo(file, sysScenicSpotBroadcastExtendWithBLOBs);
                if (i == 1) {
                    returnModel.setData("");
                    returnModel.setMsg("寻宝景点内容新增成功！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                } else if (i == 2) {
                    returnModel.setData("");
                    returnModel.setMsg("寻宝景点播报视频文件上传格式不正确！(支持:mp4，flv，avi，rm，rmvb，wmv格式)！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                } else {
                    returnModel.setData("");
                    returnModel.setMsg("寻宝景点内容新增失败！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
            } else {
                returnModel.setData("");
                returnModel.setMsg("请选择要上传的视频文件！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("addBroadcastContentExtendVideo", e);
            returnModel.setData("");
            returnModel.setMsg("寻宝景点内容新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author 张
     * @Description 寻宝景点内容新增（音频）
     * @Date
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     **/
    @RequestMapping("/addBroadcastHuntContentExtendAudioNew")
    @ResponseBody
    public ReturnModel addBroadcastHuntContentExtendAudioNew(@RequestParam MultipartFile fileAudio, @RequestParam MultipartFile fileImage, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {
//            sysScenicSpotBroadcastExtendWithBLOBs.setScenicSpotId(15698320289682l);
            sysScenicSpotBroadcastExtendWithBLOBs.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
//            int i = sysScenicSpotBroadcastHuntService.addBroadcastHuntContentExtendAudio(file, sysScenicSpotBroadcastExtendWithBLOBs);
            int i = sysScenicSpotBroadcastHuntService.addBroadcastHuntContentExtendAudioNew(fileAudio, fileImage, sysScenicSpotBroadcastExtendWithBLOBs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点内容新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else if (i == 2) {
                returnModel.setData("");
                returnModel.setMsg("寻宝音频文件不能为空");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else if (i == 3) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点播报音频文件上传格式不正确！(支持:mp3格式)");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else if (i == 4) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点播报图片文件上传格式不正确！(支持:png，jpg格式)");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else if (i == 5) {
                returnModel.setData("");
                returnModel.setMsg("请上传文件");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点内容新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("addBroadcastContentExtendVideo", e);
            returnModel.setData("");
            returnModel.setMsg("寻宝景点内容新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author zhang
     * @Description 景点内容修改（文字）
     * @Date
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     **/
    @RequestMapping("/editBroadcastHuntContentExtendImageNew")
    @ResponseBody
    public ReturnModel editBroadcastHuntContentExtendImageNew(@RequestParam MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {
            if (sysScenicSpotBroadcastExtendWithBLOBs.getScenicSpotId() == null) {
                sysScenicSpotBroadcastExtendWithBLOBs.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            }
//            sysScenicSpotBroadcastExtendWithBLOBs.setScenicSpotId(15698320289682l);
            int i = sysScenicSpotBroadcastHuntService.editBroadcastHuntContentExtendImage(file, sysScenicSpotBroadcastExtendWithBLOBs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点内容修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else if (i == 2) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点播报图片文件上传格式不正确！(支持:png，jpg格式)！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else if (i == 3) {
                returnModel.setData("");
                returnModel.setMsg("图片大小大于2M！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点内容修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("editBroadcastContentExtendImage", e);
            returnModel.setData("");
            returnModel.setMsg("寻宝景点内容修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author zhang
     * @Description 景点内容修改（视频）
     * @Date
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     **/
    @RequestMapping("/editBroadcastHuntContentExtendVideoNew")
    @ResponseBody
    public ReturnModel editBroadcastHuntContentExtendVideoNew(@RequestParam MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {
            if (!file.isEmpty()) {
//                sysScenicSpotBroadcastExtendWithBLOBs.setScenicSpotId(15698320289682l);
                sysScenicSpotBroadcastExtendWithBLOBs.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
                int i = sysScenicSpotBroadcastHuntService.editBroadcastHuntContentExtendVideo(file, sysScenicSpotBroadcastExtendWithBLOBs);
                if (i == 1) {
                    returnModel.setData("");
                    returnModel.setMsg("寻宝景点内容修改成功！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                } else if (i == 2) {
                    returnModel.setData("");
                    returnModel.setMsg("寻宝景点播报视频文件上传格式不正确！(支持:mp4，flv，avi，rm，rmvb，wmv格式)！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                } else {
                    returnModel.setData("");
                    returnModel.setMsg("寻宝景点内容修改失败！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
            } else {
                returnModel.setData("");
                returnModel.setMsg("请选择要上传的视频文件！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("editBroadcastContentExtendVideo", e);
            returnModel.setData("");
            returnModel.setMsg("寻宝景点内容修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author zhang
     * @Description 景点内容修改（音频）
     * @Date
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     **/
    @RequestMapping("/editBroadcastHuntContentExtendAudioNew")
    @ResponseBody
    public ReturnModel editBroadcastHuntContentExtendAudioNew(@RequestParam MultipartFile fileAudio, @RequestParam MultipartFile fileImage, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {
//            sysScenicSpotBroadcastExtendWithBLOBs.setScenicSpotId(15698320289682l);
            sysScenicSpotBroadcastExtendWithBLOBs.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            int i = sysScenicSpotBroadcastHuntService.editBroadcastHuntContentExtendAudioNew(fileAudio, fileImage, sysScenicSpotBroadcastExtendWithBLOBs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点内容新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else if (i == 2) {
                returnModel.setData("");
                returnModel.setMsg("寻宝音频文件不能为空");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else if (i == 3) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点播报音频文件上传格式不正确！(支持:mp3格式)");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else if (i == 4) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点播报图片文件上传格式不正确！(支持:png，jpg格式)");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else if (i == 5) {
                returnModel.setData("");
                returnModel.setMsg("请上传文件");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点内容新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("addBroadcastContentExtendVideo", e);
            returnModel.setData("");
            returnModel.setMsg("寻宝景点内容新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     * @Author zhang
     * @Description 景点内容删除
     * @Date
     * @Param [broadcastResId]
     **/
    @RequestMapping("/delBroadcastHuntContentExtendNew")
    @ResponseBody
    public ReturnModel delBroadcastHuntContentExtendNew(@RequestParam("broadcastResId") Long broadcastResId) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotBroadcastHuntService.delBroadcastHuntContentExtend(broadcastResId);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点内容删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            } else {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点内容删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.info("delBroadcastContentExtend", e);
            returnModel.setData("");
            returnModel.setMsg("寻宝景点内容删除失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @return void
     * @Author zhang
     * @Description 下载寻宝景点Excel表
     * @Param [response, sysScenicSpotBroadcastExtendWithBLOBs]
     **/
    @RequestMapping(value = "/uploadExcelBroadcastHuntNew")
    public void uploadExcelBroadcastHuntNew(HttpServletResponse response, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) throws Exception {
        List<SysScenicSpotBroadcastExtendWithBLOBs> broadcastExtendWithBLOBsListByExample = null;
        Map<String, Object> search = new HashMap<>();
        search.put("scenicSpotId", session.getAttribute("scenicSpotId"));
//        search.put("scenicSpotId", 15698320289682l);
        broadcastExtendWithBLOBsListByExample = sysScenicSpotBroadcastHuntService.getBroadcastExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("寻宝景点列表", "寻宝景点列表", SysScenicSpotBroadcastExtendWithBLOBs.class, broadcastExtendWithBLOBsListByExample), "寻宝景点列表" + dateTime + ".xls", response);
    }

    /**
     * @Method importExcel
     * @Author zhang
     * @Description 导入寻宝景点Excel表
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/4/2 13:38
     */
    @RequestMapping("/importExcelNew")
    @ResponseBody
    public ReturnModel importExcelNew(@RequestParam("file") MultipartFile multipartFile) {
        ReturnModel returnModel = new ReturnModel();
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            List<SysScenicSpotBroadcastExtendVo> scenicSpotBroadcast = ExcelImportUtil.importExcel(multipartFile.getInputStream(), SysScenicSpotBroadcastExtendVo.class, params);
            for (SysScenicSpotBroadcastExtendVo broadcastExtendVo : scenicSpotBroadcast) {
                SysScenicSpotBroadcastHunt broadcast = new SysScenicSpotBroadcastHunt();
                broadcast.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
//                broadcast.setScenicSpotId(15698320289682l);
                broadcast.setBroadcastName(broadcastExtendVo.getBroadcastName());
                broadcast.setBroadcastGps(broadcastExtendVo.getBroadcastGps());
                broadcast.setBroadcastGpsBaiDu(broadcastExtendVo.getBroadcastGpsBaiDu());
                broadcast.setIntegralNum(broadcastExtendVo.getIntegralNum());
                broadcast.setTreasureType(broadcastExtendVo.getTreasureType());
                broadcast.setScenicSpotRange(broadcastExtendVo.getScenicSpotRange());
                sysScenicSpotBroadcastHuntService.addBroadcast(broadcast);
            }
            returnModel.setData("");
            returnModel.setMsg("导入成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } catch (Exception e) {
            logger.info("景点列表导入Excel", e);
            returnModel.setData("");
            returnModel.setMsg("导入失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }
}







