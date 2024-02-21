package com.hna.hka.archive.management.system.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.service.SysScenicSpotBroadcastService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.util.*;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: BroadcastController
 * @Author: 郭凯
 * @Description: 景点管理控制层
 * @Date: 2020/6/8 9:32
 * @Version: 1.0
 */
@RequestMapping("/system/broadcast")
@Controller
public class BroadcastController extends PublicUtil {

    @Autowired
    private SysScenicSpotBroadcastService sysScenicSpotBroadcastService;

    @Autowired
    private HttpSession session;

    /**
     * @Author 郭凯
     * @Description 景点管理列表查询
     * @Date 9:38 2020/6/8
     * @Param [pageNum, pageSize, sysScenicSpotBroadcast]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getBroadcastList")
    @ResponseBody
    public PageDataResult getBroadcastList(@RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("pageSize") Integer pageSize, SysScenicSpotBroadcast sysScenicSpotBroadcast) {
        PageDataResult pageDataResult = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            sysScenicSpotBroadcast.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            pageDataResult = sysScenicSpotBroadcastService.getBroadcastList(pageNum,pageSize,sysScenicSpotBroadcast);
        }catch (Exception e){
            logger.info("景点列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 新增景点
     * @Date 10:52 2020/6/8
     * @Param [broadcast, session]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addBroadcast")
    @ResponseBody
    public ReturnModel addScenicBroadcast(SysScenicSpotBroadcast broadcast) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotBroadcastService.addBroadcast(broadcast);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("景点新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("景点新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("addBroadcast", e);
            returnModel.setData("");
            returnModel.setMsg("景点新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 删除景点
     * @Date 11:22 2020/6/8
     * @Param [broadcastId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delBroadcast")
    @ResponseBody
    public ReturnModel delBroadcast(@NotBlank(message = "景点ID不能为空")Long broadcastId,@NotBlank(message = "景区ID不能为空")Long scenicSpotId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotBroadcastService.delBroadcast(broadcastId,scenicSpotId);
            if (i > 0){
                returnModel.setData("");
                returnModel.setMsg("景点删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("景点删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("景点删除失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 编辑景点
     * @Date 13:12 2020/6/8
     * @Param [broadcast]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editBroadcast")
    @ResponseBody
    public ReturnModel editBroadcast(SysScenicSpotBroadcast broadcast) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotBroadcastService.editBroadcast(broadcast);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("景点修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("景点修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("editBroadcast", e);
            returnModel.setData("");
            returnModel.setMsg("景点修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 景点内容详情列表查询
     * @Date 10:43 2020/11/6
     * @Param [pageNum, pageSize, sysScenicSpotBroadcastExtendWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getBroadcastContentList")
    @ResponseBody
    public PageDataResult getBroadcastContentList(@RequestParam("pageNum") Integer pageNum,
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
            pageDataResult = sysScenicSpotBroadcastService.getBroadcastContentList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("景点内容详情列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 景点内容新增（文字）
     * @Date 13:21 2020/11/6
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addBroadcastContentExtendImage")
    @ResponseBody
    public ReturnModel addBroadcastContentExtendImage(@RequestParam MultipartFile file,SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {
            sysScenicSpotBroadcastExtendWithBLOBs.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            int i = sysScenicSpotBroadcastService.addBroadcastContentExtendImage(file,sysScenicSpotBroadcastExtendWithBLOBs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("景点内容新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (i == 2){
                returnModel.setData("");
                returnModel.setMsg("景点播报图片文件上传格式不正确！(支持:png，jpg格式)！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else if (i==3){
                returnModel.setData("");
                returnModel.setMsg("图片大小不可大于2M！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("景点内容新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("addBroadcastContentExtendImage", e);
            returnModel.setData("");
            returnModel.setMsg("景点内容新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 景点内容新增（视频）
     * @Date 13:33 2020/11/6
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addBroadcastContentExtendVideo")
    @ResponseBody
    public ReturnModel addBroadcastContentExtendVideo(@RequestParam("file") MultipartFile file,SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {
            if (!file.isEmpty()){
                sysScenicSpotBroadcastExtendWithBLOBs.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
                int i = sysScenicSpotBroadcastService.addBroadcastContentExtendVideo(file,sysScenicSpotBroadcastExtendWithBLOBs);
                if (i == 1) {
                    returnModel.setData("");
                    returnModel.setMsg("景点内容新增成功！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                }else if (i == 2){
                    returnModel.setData("");
                    returnModel.setMsg("景点播报视频文件上传格式不正确！(支持:mp4，flv，avi，rm，rmvb，wmv格式)！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }else{
                    returnModel.setData("");
                    returnModel.setMsg("景点内容新增失败！");
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
            returnModel.setMsg("景点内容新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 景点内容新增（音频）
     * @Date 13:37 2020/11/6
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addBroadcastContentExtendAudio")
    @ResponseBody
    public ReturnModel addBroadcastContentExtendAudio(@RequestParam MultipartFile[] file,SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {
            sysScenicSpotBroadcastExtendWithBLOBs.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            int i = sysScenicSpotBroadcastService.addBroadcastContentExtendAudio(file,sysScenicSpotBroadcastExtendWithBLOBs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("景点内容新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (i == 2){
                returnModel.setData("");
                returnModel.setMsg("音频文件不能为空");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else if (i == 3){
                returnModel.setData("");
                returnModel.setMsg("景点播报音频文件上传格式不正确！(支持:mp3格式)");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else if (i == 4){
                returnModel.setData("");
                returnModel.setMsg("景点播报图片文件上传格式不正确！(支持:png，jpg格式)");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else if (i == 5){
                returnModel.setData("");
                returnModel.setMsg("请上传文件");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("景点内容新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("addBroadcastContentExtendVideo", e);
            returnModel.setData("");
            returnModel.setMsg("景点内容新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 景点内容修改（文字）
     * @Date 15:03 2020/11/6
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editBroadcastContentExtendImage")
    @ResponseBody
    public ReturnModel editBroadcastContentExtendImage(@RequestParam MultipartFile file,SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {
            sysScenicSpotBroadcastExtendWithBLOBs.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            int i = sysScenicSpotBroadcastService.editBroadcastContentExtendImage(file,sysScenicSpotBroadcastExtendWithBLOBs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("景点内容修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (i == 2){
                returnModel.setData("");
                returnModel.setMsg("景点播报图片文件上传格式不正确！(支持:png，jpg格式)！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else if (i == 3){
                returnModel.setData("");
                returnModel.setMsg("图片大小不可大于2M！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("景点内容修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("editBroadcastContentExtendImage", e);
            returnModel.setData("");
            returnModel.setMsg("景点内容修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 景点内容修改（视频）
     * @Date 15:09 2020/11/6
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editBroadcastContentExtendVideo")
    @ResponseBody
    public ReturnModel editBroadcastContentExtendVideo(@RequestParam MultipartFile file,SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {
            if (!file.isEmpty()){
                sysScenicSpotBroadcastExtendWithBLOBs.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
                int i = sysScenicSpotBroadcastService.editBroadcastContentExtendVideo(file,sysScenicSpotBroadcastExtendWithBLOBs);
                if (i == 1) {
                    returnModel.setData("");
                    returnModel.setMsg("景点内容修改成功！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                }else if (i == 2){
                    returnModel.setData("");
                    returnModel.setMsg("景点播报视频文件上传格式不正确！(支持:mp4，flv，avi，rm，rmvb，wmv格式)！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }else{
                    returnModel.setData("");
                    returnModel.setMsg("景点内容修改失败！");
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
            returnModel.setMsg("景点内容修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 景点内容修改（视频）
     * @Date 15:14 2020/11/6
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editBroadcastContentExtendAudio")
    @ResponseBody
    public ReturnModel editBroadcastContentExtendAudio(@RequestParam MultipartFile[] file,SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        ReturnModel returnModel = new ReturnModel();
        try {
            sysScenicSpotBroadcastExtendWithBLOBs.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            int i = sysScenicSpotBroadcastService.editBroadcastContentExtendAudio(file,sysScenicSpotBroadcastExtendWithBLOBs);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("景点内容新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (i == 2){
                returnModel.setData("");
                returnModel.setMsg("音频文件不能为空");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else if (i == 3){
                returnModel.setData("");
                returnModel.setMsg("景点播报音频文件上传格式不正确！(支持:mp3格式)");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else if (i == 4){
                returnModel.setData("");
                returnModel.setMsg("景点播报图片文件上传格式不正确！(支持:png，jpg格式)");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else if (i == 5){
                returnModel.setData("");
                returnModel.setMsg("请上传文件");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("景点内容新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("addBroadcastContentExtendVideo", e);
            returnModel.setData("");
            returnModel.setMsg("景点内容新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 景点内容删除
     * @Date 15:39 2020/11/6
     * @Param [broadcastResId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delBroadcastContentExtend")
    @ResponseBody
    public ReturnModel delBroadcastContentExtend(@RequestParam("broadcastResId") Long broadcastResId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotBroadcastService.delBroadcastContentExtend(broadcastResId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("景点内容删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("景点内容删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delBroadcastContentExtend",e);
            returnModel.setData("");
            returnModel.setMsg("景点内容删除失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 下载景点Excel表
     * @Date 16:13 2020/11/6
     * @Param [response, sysScenicSpotBroadcastExtendWithBLOBs]
     * @return void
    **/
    @RequestMapping(value = "/uploadExcelBroadcast")
    public void  uploadExcelBroadcast(HttpServletResponse response, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) throws Exception {
        List<SysScenicSpotBroadcastExtendWithBLOBs> broadcastExtendWithBLOBsListByExample = null;
        Map<String,Object> search = new HashMap<>();
        search.put("scenicSpotId",session.getAttribute("scenicSpotId"));
        broadcastExtendWithBLOBsListByExample = sysScenicSpotBroadcastService.getBroadcastExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("景点列表", "景点列表", SysScenicSpotBroadcastExtendWithBLOBs.class, broadcastExtendWithBLOBsListByExample),"景点列表"+ dateTime +".xls",response);
    }

    /**
     * @Method importExcel
     * @Author 郭凯
     * @Version  1.0
     * @Description 导入景点Excel表
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
                SysScenicSpotBroadcast broadcast = new SysScenicSpotBroadcast();
                broadcast.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
                broadcast.setBroadcastName(broadcastExtendVo.getBroadcastName());
                broadcast.setBroadcastGps(broadcastExtendVo.getBroadcastGps());
                broadcast.setBroadcastGpsBaiDu(broadcastExtendVo.getBroadcastGpsBaiDu());
                sysScenicSpotBroadcastService.addBroadcast(broadcast);
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
