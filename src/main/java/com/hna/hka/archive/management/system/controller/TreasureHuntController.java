package com.hna.hka.archive.management.system.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcast;
import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcastExtendVo;
import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcastExtendWithBLOBs;
import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcastHunt;
import com.hna.hka.archive.management.system.service.SysScenicSpotBroadcastHuntService;
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

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: TreasureHuntController
 * @Author: 曲绍备
 * @Description: 寻宝
 * @Date: 2021/12/9 9:31
 * @Version: 1.0
 */
@Api(tags = "景点寻宝相关")
@RestController
@RequestMapping("/system/treasureHunt")
public class TreasureHuntController extends PublicUtil {

    @Autowired
    private SysScenicSpotBroadcastHuntService sysScenicSpotBroadcastHuntService;

    @Autowired
    private HttpSession session;

    @ApiOperation("查询寻宝景点列表")
    @ResponseBody
    @GetMapping("/getTreasureHuntList")
    public ReturnModel getTreasureHuntList(@RequestParam("pageNum") Integer pageNum,
                                              @RequestParam("pageSize") Integer pageSize, SysScenicSpotBroadcastHunt
                                                      sysScenicSpotBroadcastHunt) {
        ReturnModel returnModel = new ReturnModel();
        try {
            if (null == pageNum) {
                pageNum = 1;
            }
            if (null == pageSize) {
                pageSize = 10;
            }
            sysScenicSpotBroadcastHunt.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            PageInfo<SysScenicSpotBroadcastHunt> broadcastList = sysScenicSpotBroadcastHuntService.getBroadcastList(pageNum, pageSize, sysScenicSpotBroadcastHunt);
            returnModel.setData(broadcastList);
            returnModel.setMsg("寻宝景点查询成功！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        } catch (Exception e) {
            returnModel.setData("");
            returnModel.setMsg("寻宝景点查询失败！");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }
    }

    @ApiOperation("添加寻宝景点")
    @ResponseBody
    @RequestMapping("/addBroadcastHunt")
    public ReturnModel addScenicBroadcast(SysScenicSpotBroadcastHunt broadcastHunt) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotBroadcastHuntService.addBroadcast(broadcastHunt);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
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
    @RequestMapping("/editBroadcastHunt")
    public ReturnModel editScenicBroadcast(SysScenicSpotBroadcastHunt broadcastHunt) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotBroadcastHuntService.editBroadcast(broadcastHunt);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
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
     * @Author zhang
     * @Description 删除寻宝景点
     * @Param [broadcastId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/delBroadcastHunt")
    @ResponseBody
    public ReturnModel delBroadcast(@NotBlank(message = "景点ID不能为空")Long broadcastId,@NotBlank(message = "景区ID不能为空")Long scenicSpotId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotBroadcastHuntService.delBroadcast(broadcastId,scenicSpotId);
            if (i > 0){
                returnModel.setData("");
                returnModel.setMsg("寻宝景点删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("寻宝景点删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("寻宝景点删除失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    @ApiOperation("启用/禁用寻宝景点")
    @ResponseBody
    @RequestMapping("/switchBroadcastHunt")
    public ReturnModel switchEditScenicBroadcast(SysScenicSpotBroadcastHunt broadcastHunt) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotBroadcastHuntService.switchBroadcast(broadcastHunt);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
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
     * @Author 张
     * @Description 寻宝景点内容详情列表查询
     * @Date 10:43 2020/11/6
     * @Param [pageNum, pageSize, sysScenicSpotBroadcastExtendWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     **/
    @RequestMapping("/getBroadcastContentHuntList")
    @ResponseBody
    public PageDataResult getBroadcastContentHuntList(@RequestParam("pageNum") Integer pageNum,
                                                  @RequestParam("pageSize") Integer pageSize, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("broadcastId",sysScenicSpotBroadcastExtendWithBLOBs.getBroadcastId());
            pageDataResult = sysScenicSpotBroadcastHuntService.getBroadcastContentHuntList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("景点内容详情列表查询失败",e);
        }
        return pageDataResult;
    }


    /**
     * @Author 张
     * @Description 寻宝景点内容新增（文字）
     * @Date
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/addBroadcastHuntContentExtendImage")
    @ResponseBody
    public ReturnModel addBroadcastContentHuntExtendImage(@RequestParam MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {
            sysScenicSpotBroadcastExtendWithBLOBs.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            int i = sysScenicSpotBroadcastHuntService.addBroadcastHuntContentExtendImage(file,sysScenicSpotBroadcastExtendWithBLOBs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点内容新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (i == 2){
                returnModel.setData("");
                returnModel.setMsg("寻宝景点播报图片文件上传格式不正确！(支持:png，jpg格式)！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else if (i == 3){

                returnModel.setData("");
                returnModel.setMsg("图片大小大于2M！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else{
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
     * @Author 张
     * @Description 寻宝景点内容新增（视频）
     * @Date
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/addBroadcastHuntContentExtendVideo")
    @ResponseBody
    public ReturnModel addBroadcastHuntContentExtendVideo(@RequestParam("file") MultipartFile file,SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {
            if (!file.isEmpty()){
                sysScenicSpotBroadcastExtendWithBLOBs.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
                int i = sysScenicSpotBroadcastHuntService.addBroadcastHuntContentExtendVideo(file,sysScenicSpotBroadcastExtendWithBLOBs);
                if (i == 1) {
                    returnModel.setData("");
                    returnModel.setMsg("寻宝景点内容新增成功！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                }else if (i == 2){
                    returnModel.setData("");
                    returnModel.setMsg("寻宝景点播报视频文件上传格式不正确！(支持:mp4，flv，avi，rm，rmvb，wmv格式)！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }else{
                    returnModel.setData("");
                    returnModel.setMsg("寻宝景点内容新增失败！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
            }else{
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
     * @Author 张
     * @Description 寻宝景点内容新增（音频）
     * @Date
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/addBroadcastHuntContentExtendAudio")
    @ResponseBody
    public ReturnModel addBroadcastHuntContentExtendAudio(@RequestParam MultipartFile[] file,SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {
            sysScenicSpotBroadcastExtendWithBLOBs.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            int i = sysScenicSpotBroadcastHuntService.addBroadcastHuntContentExtendAudio(file,sysScenicSpotBroadcastExtendWithBLOBs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点内容新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (i == 2){
                returnModel.setData("");
                returnModel.setMsg("寻宝音频文件不能为空");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else if (i == 3){
                returnModel.setData("");
                returnModel.setMsg("寻宝景点播报音频文件上传格式不正确！(支持:mp3格式)");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else if (i == 4){
                returnModel.setData("");
                returnModel.setMsg("寻宝景点播报图片文件上传格式不正确！(支持:png，jpg格式)");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else if (i == 5){
                returnModel.setData("");
                returnModel.setMsg("请上传文件");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else{
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
     * @Author zhang
     * @Description 景点内容修改（文字）
     * @Date
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/editBroadcastHuntContentExtendImage")
    @ResponseBody
    public ReturnModel editBroadcastHuntContentExtendImage(@RequestParam MultipartFile file,SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {
            sysScenicSpotBroadcastExtendWithBLOBs.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            int i = sysScenicSpotBroadcastHuntService.editBroadcastHuntContentExtendImage(file,sysScenicSpotBroadcastExtendWithBLOBs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点内容修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (i == 2){
                returnModel.setData("");
                returnModel.setMsg("寻宝景点播报图片文件上传格式不正确！(支持:png，jpg格式)！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else if (i==3){
                returnModel.setData("");
                returnModel.setMsg("图片大小大于2M！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else{
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
     * @Author zhang
     * @Description 景点内容修改（视频）
     * @Date
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/editBroadcastHuntContentExtendVideo")
    @ResponseBody
    public ReturnModel editBroadcastHuntContentExtendVideo(@RequestParam MultipartFile file,SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {
            if (!file.isEmpty()){
                sysScenicSpotBroadcastExtendWithBLOBs.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
                int i = sysScenicSpotBroadcastHuntService.editBroadcastHuntContentExtendVideo(file,sysScenicSpotBroadcastExtendWithBLOBs);
                if (i == 1) {
                    returnModel.setData("");
                    returnModel.setMsg("寻宝景点内容修改成功！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                }else if (i == 2){
                    returnModel.setData("");
                    returnModel.setMsg("寻宝景点播报视频文件上传格式不正确！(支持:mp4，flv，avi，rm，rmvb，wmv格式)！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }else{
                    returnModel.setData("");
                    returnModel.setMsg("寻宝景点内容修改失败！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
            }else{
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
     * @Author zhang
     * @Description 景点内容修改（音频）
     * @Date
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/editBroadcastHuntContentExtendAudio")
    @ResponseBody
    public ReturnModel editBroadcastHuntContentExtendAudio(@RequestParam MultipartFile[] file,SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {
            sysScenicSpotBroadcastExtendWithBLOBs.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            int i = sysScenicSpotBroadcastHuntService.editBroadcastHuntContentExtendAudio(file,sysScenicSpotBroadcastExtendWithBLOBs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点内容新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (i == 2){
                returnModel.setData("");
                returnModel.setMsg("寻宝音频文件不能为空");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else if (i == 3){
                returnModel.setData("");
                returnModel.setMsg("寻宝景点播报音频文件上传格式不正确！(支持:mp3格式)");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else if (i == 4){
                returnModel.setData("");
                returnModel.setMsg("寻宝景点播报图片文件上传格式不正确！(支持:png，jpg格式)");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else if (i == 5){
                returnModel.setData("");
                returnModel.setMsg("请上传文件");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else{
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
     * @Author zhang
     * @Description 景点内容删除
     * @Date
     * @Param [broadcastResId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/delBroadcastHuntContentExtend")
    @ResponseBody
    public ReturnModel delBroadcastHuntContentExtend(@RequestParam("broadcastResId") Long broadcastResId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotBroadcastHuntService.delBroadcastHuntContentExtend(broadcastResId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("寻宝景点内容删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("寻宝景点内容删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delBroadcastContentExtend",e);
            returnModel.setData("");
            returnModel.setMsg("寻宝景点内容删除失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author zhang
     * @Description 下载寻宝景点Excel表
     * @Param [response, sysScenicSpotBroadcastExtendWithBLOBs]
     * @return void
     **/
    @RequestMapping(value = "/uploadExcelBroadcastHunt")
    public void  uploadExcelBroadcastHunt(HttpServletResponse response, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) throws Exception {
        List<SysScenicSpotBroadcastExtendWithBLOBs> broadcastExtendWithBLOBsListByExample = null;
        Map<String,Object> search = new HashMap<>();
        search.put("scenicSpotId",session.getAttribute("scenicSpotId"));
        broadcastExtendWithBLOBsListByExample = sysScenicSpotBroadcastHuntService.getBroadcastExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("寻宝景点列表", "寻宝景点列表", SysScenicSpotBroadcastExtendWithBLOBs.class, broadcastExtendWithBLOBsListByExample),"寻宝景点列表"+ dateTime +".xls",response);
    }

    /**
     * @Method importExcel
     * @Author zhang
     * @Description 导入寻宝景点Excel表
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/4/2 13:38
     */
    @RequestMapping("/importExcel")
    @ResponseBody
    public ReturnModel importExcel(@RequestParam("file") MultipartFile multipartFile){
        ReturnModel returnModel = new ReturnModel();
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            List<SysScenicSpotBroadcastExtendVo> scenicSpotBroadcast = ExcelImportUtil.importExcel(multipartFile.getInputStream(),SysScenicSpotBroadcastExtendVo.class, params);
            for (SysScenicSpotBroadcastExtendVo broadcastExtendVo : scenicSpotBroadcast){
                SysScenicSpotBroadcastHunt broadcast = new SysScenicSpotBroadcastHunt();
                broadcast.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
                broadcast.setBroadcastName(broadcastExtendVo.getBroadcastName());
                broadcast.setBroadcastGps(broadcastExtendVo.getBroadcastGps());
                broadcast.setBroadcastGpsBaiDu(broadcastExtendVo.getBroadcastGpsBaiDu());
                sysScenicSpotBroadcastHuntService.addBroadcast(broadcast);
            }
            returnModel.setData("");
            returnModel.setMsg("导入成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            logger.info("景点列表导入Excel",e);
            returnModel.setData("");
            returnModel.setMsg("导入失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }






}
