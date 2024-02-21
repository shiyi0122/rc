package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysRobotCorpusAudioAndVideo;
import com.hna.hka.archive.management.system.model.SysRobotCorpusMediaExtend;
import com.hna.hka.archive.management.system.service.SysRobotCorpusAudioAndVideoService;
import com.hna.hka.archive.management.system.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: CorpusAudioAndVideoController
 * @Author: 郭凯
 * @Description: 语音媒体资源控制层
 * @Date: 2020/6/15 13:17
 * @Version: 1.0
 */
@RequestMapping("/system/corpusAudioAndVideo")
@Controller
public class CorpusAudioAndVideoController extends PublicUtil {

    @Autowired
    private SysRobotCorpusAudioAndVideoService sysRobotCorpusAudioAndVideoService;

    @Autowired
    private HttpSession session;

    /**
     * @Author 郭凯
     * @Description 语音媒体资源列表查询
     * @Date 13:32 2020/6/15
     * @Param [pageNum, pageSize, sysRobotCorpusAudioAndVideo]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getCorpusAudioAndVideoList")
    @ResponseBody
    public PageDataResult getCorpusAudioAndVideoList(@RequestParam("pageNum") Integer pageNum,
                                                     @RequestParam("pageSize") Integer pageSize, SysRobotCorpusAudioAndVideo sysRobotCorpusAudioAndVideo) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("scenicSpotId",session.getAttribute("scenicSpotId").toString());
            pageDataResult = sysRobotCorpusAudioAndVideoService.getCorpusAudioAndVideoList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("语音媒体资源列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 语音媒体资源查看详情列表查询
     * @Date 10:32 2020/7/21
     * @Param [pageNum, pageSize, sysRobotCorpusMediaExtend]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getViewDetailsList")
    @ResponseBody
    public PageDataResult getViewDetailsList(@RequestParam("pageNum") Integer pageNum,
                                                     @RequestParam("pageSize") Integer pageSize, SysRobotCorpusMediaExtend sysRobotCorpusMediaExtend) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("mediaId",sysRobotCorpusMediaExtend.getMediaId().toString());
            pageDataResult = sysRobotCorpusAudioAndVideoService.getViewDetailsList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("语音媒体资源查看详情列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 语音媒体资源删除
     * @Date 11:36 2020/7/21
     * @Param [mediaId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/delCorpusAudioAndVideo")
    @ResponseBody
    public ReturnModel delCorpusAudioAndVideo(@RequestParam("mediaId") Long mediaId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotCorpusAudioAndVideoService.delCorpusAudioAndVideo(mediaId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("语音媒体资源删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("语音媒体资源删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delCorpusAudioAndVideo",e);
            returnModel.setData("");
            returnModel.setMsg("语音媒体资源删除失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 添加语音媒体资源音频
     * @Date 10:36 2020/7/28
     * @Param [file, sysRobotCorpusMediaExtend]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addCorpusAudio")
    @ResponseBody
    public ReturnModel addCorpusAudio(@RequestParam("file") MultipartFile file,SysRobotCorpusAudioAndVideo sysRobotCorpusAudioAndVideo){
        ReturnModel dataModel = new ReturnModel();
        try {
            if(!file.isEmpty()){
                if ("1".equals(sysRobotCorpusAudioAndVideo.getScenicSpotId().toString())) {
                    sysRobotCorpusAudioAndVideo.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
                }else if ("2".equals(sysRobotCorpusAudioAndVideo.getScenicSpotId().toString())){
                    sysRobotCorpusAudioAndVideo.setScenicSpotId(Long.parseLong("2019924"));
                }
                int i = sysRobotCorpusAudioAndVideoService.addCorpusAudio(file, sysRobotCorpusAudioAndVideo);
                if (i == 1){
                    dataModel.setData("");
                    dataModel.setMsg("语音媒体资源新增成功！");
                    dataModel.setState(Constant.STATE_SUCCESS);
                    return dataModel;
                }else if (i == 2){
                    dataModel.setData("");
                    dataModel.setMsg("音频文件上传格式不正确！(支持:mp3格式)");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }else{
                    dataModel.setData("");
                    dataModel.setMsg("语音媒体资源新增失败！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
            }else{
                dataModel.setData("");
                dataModel.setMsg("请选择要上传的音频！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        }catch (Exception e){
            dataModel.setData("");
            dataModel.setMsg("语音媒体资源新增失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 添加语音媒体资源视频
     * @Date 11:21 2020/7/28
     * @Param [file, sysRobotCorpusAudioAndVideo]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addCorpusVideo")
    @ResponseBody
    public ReturnModel addCorpusVideo(@RequestParam("file") MultipartFile file,SysRobotCorpusAudioAndVideo sysRobotCorpusAudioAndVideo){
        ReturnModel dataModel = new ReturnModel();
        try {
            if(!file.isEmpty()){
                if ("1".equals(sysRobotCorpusAudioAndVideo.getScenicSpotId().toString())) {
                    sysRobotCorpusAudioAndVideo.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
                }else if ("2".equals(sysRobotCorpusAudioAndVideo.getScenicSpotId().toString())){
                    sysRobotCorpusAudioAndVideo.setScenicSpotId(Long.parseLong("2019924"));
                }
                int i = sysRobotCorpusAudioAndVideoService.addCorpusVideo(file, sysRobotCorpusAudioAndVideo);
                if (i == 1){
                    dataModel.setData("");
                    dataModel.setMsg("语音媒体资源新增成功！");
                    dataModel.setState(Constant.STATE_SUCCESS);
                    return dataModel;
                }else if (i == 2){
                    dataModel.setData("");
                    dataModel.setMsg("视频文件上传格式不正确！(支持:mp4格式)");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }else{
                    dataModel.setData("");
                    dataModel.setMsg("语音媒体资源新增失败！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
            }else{
                dataModel.setData("");
                dataModel.setMsg("请选择要上传的视频！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        }catch (Exception e){
            dataModel.setData("");
            dataModel.setMsg("语音媒体资源新增失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 查询媒体资源回显数据
     * @Date 14:38 2020/7/28
     * @Param [mediaId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/getCorpusAudioAndVideoById")
    @ResponseBody
    public ReturnModel getCorpusAudioAndVideoById(@RequestParam("mediaId") Long mediaId){
        ReturnModel dataModel = new ReturnModel();
        try {
            SysRobotCorpusAudioAndVideo sysRobotCorpusAudioAndVideo = sysRobotCorpusAudioAndVideoService.getCorpusAudioAndVideoById(mediaId);
            if (ToolUtil.isNotEmpty(sysRobotCorpusAudioAndVideo)){
                dataModel.setData(sysRobotCorpusAudioAndVideo);
                dataModel.setMsg("媒体资源回显数据查询成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("媒体资源回显数据查询失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        }catch (Exception e){
            dataModel.setData("");
            dataModel.setMsg("媒体资源回显数据查询失败！（请联系管理员）");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 修改语音媒体资源（音频）
     * @Date 16:07 2020/7/28
     * @Param [file, sysRobotCorpusAudioAndVideo]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editCorpusAudio")
    @ResponseBody
    public ReturnModel editCorpusAudio(@RequestParam("file") MultipartFile file,SysRobotCorpusAudioAndVideo sysRobotCorpusAudioAndVideo){
        ReturnModel dataModel = new ReturnModel();
        try {
            if ("1".equals(sysRobotCorpusAudioAndVideo.getScenicSpotId().toString())) {
                sysRobotCorpusAudioAndVideo.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            }else if ("2".equals(sysRobotCorpusAudioAndVideo.getScenicSpotId().toString())){
                sysRobotCorpusAudioAndVideo.setScenicSpotId(Long.parseLong("2019924"));
            }
            int i = sysRobotCorpusAudioAndVideoService.editCorpusAudio(file, sysRobotCorpusAudioAndVideo);
            if (i == 1){
                dataModel.setData("");
                dataModel.setMsg("语音媒体资源修改成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else if (i == 2){
                dataModel.setData("");
                dataModel.setMsg("音频文件上传格式不正确！(支持:mp3格式)");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("语音媒体资源修改失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        }catch (Exception e){
            dataModel.setData("");
            dataModel.setMsg("语音媒体资源修改失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 修改语音媒体资源（视频）
     * @Date 16:26 2020/7/28
     * @Param [file, sysRobotCorpusAudioAndVideo]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editCorpusVideo")
    @ResponseBody
    public ReturnModel editCorpusVideo(@RequestParam("file") MultipartFile file,SysRobotCorpusAudioAndVideo sysRobotCorpusAudioAndVideo){
        ReturnModel dataModel = new ReturnModel();
        try {
            if ("1".equals(sysRobotCorpusAudioAndVideo.getScenicSpotId().toString())) {
                sysRobotCorpusAudioAndVideo.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            }else if ("2".equals(sysRobotCorpusAudioAndVideo.getScenicSpotId().toString())){
                sysRobotCorpusAudioAndVideo.setScenicSpotId(Long.parseLong("2019924"));
            }
            int i = sysRobotCorpusAudioAndVideoService.editCorpusVideo(file, sysRobotCorpusAudioAndVideo);
            if (i == 1){
                dataModel.setData("");
                dataModel.setMsg("语音媒体资源修改成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else if (i == 2){
                dataModel.setData("");
                dataModel.setMsg("视频文件上传格式不正确！(支持:mp4格式)");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("语音媒体资源修改失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        }catch (Exception e){
            dataModel.setData("");
            dataModel.setMsg("语音媒体资源修改失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 新增资源详情
     * @Date 16:55 2020/7/28
     * @Param [file, sysRobotCorpusAudioAndVideo]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addCorpusMediaExtend")
    @ResponseBody
    public ReturnModel addCorpusMediaExtend(SysRobotCorpusMediaExtend sysRobotCorpusMediaExtend){
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysRobotCorpusAudioAndVideoService.addCorpusMediaExtend(sysRobotCorpusMediaExtend);
            if (i == 1){
                dataModel.setData("");
                dataModel.setMsg("资源详情新增成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("资源详情新增失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        }catch (Exception e){
            dataModel.setData("");
            dataModel.setMsg("资源详情新增失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 删除语音媒体资源详情
     * @Date 17:16 2020/7/28
     * @Param [mediaExtendId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/delCorpusMediaExtend")
    @ResponseBody
    public ReturnModel delCorpusMediaExtend(@RequestParam("mediaExtendId") Long mediaExtendId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotCorpusAudioAndVideoService.delCorpusMediaExtend(mediaExtendId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("资源详情删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("资源详情删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delCorpusMediaExtend",e);
            returnModel.setData("");
            returnModel.setMsg("资源详情删除失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 编辑资源详情
     * @Date 17:47 2020/7/28
     * @Param [sysRobotCorpusMediaExtend]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/editCorpusMediaExtend")
    @ResponseBody
    public ReturnModel editCorpusMediaExtend(SysRobotCorpusMediaExtend sysRobotCorpusMediaExtend){
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysRobotCorpusAudioAndVideoService.editCorpusMediaExtend(sysRobotCorpusMediaExtend);
            if (i == 1){
                dataModel.setData("");
                dataModel.setMsg("资源详情编辑成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("资源详情编辑失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        }catch (Exception e){
            dataModel.setData("");
            dataModel.setMsg("资源详情编辑失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }
}
