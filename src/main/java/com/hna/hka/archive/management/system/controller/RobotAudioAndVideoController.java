package com.hna.hka.archive.management.system.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hna.hka.archive.management.system.model.SysRobotAudioAndVideo;
import com.hna.hka.archive.management.system.service.SysRobotAudioAndVideoService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;

@RequestMapping("/system/robotAudioAndVideo")
@Controller
public class RobotAudioAndVideoController extends PublicUtil{
	
	@Autowired
	private SysRobotAudioAndVideoService sysRobotAudioAndVideoService;
	
	/**
	* @Author 郭凯
	* @Description: 媒体资源管理列表查询
	* @Title: getRobotAudioAndVideoList
	* @date  2020年12月31日 下午1:48:37 
	* @param @param pageNum
	* @param @param pageSize
	* @param @param sysRobotAudioAndVideo
	* @param @return
	* @return PageDataResult
	* @throws
	 */
	@RequestMapping(value = "/getRobotAudioAndVideoList")
    @ResponseBody
    public PageDataResult getRobotAudioAndVideoList(@RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("pageSize") Integer pageSize, SysRobotAudioAndVideo sysRobotAudioAndVideo) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysRobotAudioAndVideoService.getRobotAudioAndVideoList(pageNum,pageSize,search);
        } catch (Exception e) {
            logger.error("媒体资源管理列表查询异常！", e);
        }
        return pageDataResult;
    }
	
	/**
	* @Author 郭凯
	* @Description: 媒体资源新增(音频)
	* @Title: addRobotAudio
	* @date  2020年12月31日 下午2:48:56 
	* @param @param file
	* @param @param sysRobotAudioAndVideo
	* @param @return
	* @return ReturnModel
	* @throws
	 */
	@RequestMapping("/addRobotAudio")
    @ResponseBody
    public ReturnModel addRobotAudio(@RequestParam("file") MultipartFile file,SysRobotAudioAndVideo sysRobotAudioAndVideo){
        ReturnModel dataModel = new ReturnModel();
        try {
            if(!file.isEmpty()){
                int i = sysRobotAudioAndVideoService.addRobotAudio(file, sysRobotAudioAndVideo);
                if (i == 1){
                    dataModel.setData("");
                    dataModel.setMsg("媒体资源新增成功！");
                    dataModel.setState(Constant.STATE_SUCCESS);
                    return dataModel;
                }else if (i == 2){
                    dataModel.setData("");
                    dataModel.setMsg("音频文件上传格式不正确！(支持:mp3格式)");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }else{
                    dataModel.setData("");
                    dataModel.setMsg("媒体资源新增失败！");
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
        	logger.info("addRobotAudio", e);
            dataModel.setData("");
            dataModel.setMsg("媒体资源新增失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }
    
	/**
	* @Author 郭凯
	* @Description: 媒体资源新增(视频)
	* @Title: addRobotVideo
	* @date  2020年12月31日 下午3:15:07 
	* @param @param file
	* @param @param sysRobotAudioAndVideo
	* @param @return
	* @return ReturnModel
	* @throws
	 */
    @RequestMapping("/addRobotVideo")
    @ResponseBody
    public ReturnModel addRobotVideo(@RequestParam("file") MultipartFile file,SysRobotAudioAndVideo sysRobotAudioAndVideo){
        ReturnModel dataModel = new ReturnModel();
        try {
            if(!file.isEmpty()){
                int i = sysRobotAudioAndVideoService.addRobotVideo(file, sysRobotAudioAndVideo);
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
        	logger.info("addRobotVideo", e);
            dataModel.setData("");
            dataModel.setMsg("语音媒体资源新增失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }
    
    /**
    * @Author 郭凯
    * @Description: 媒体资源编辑(音频)
    * @Title: editRobotAudio
    * @date  2020年12月31日 下午3:47:54 
    * @param @param file
    * @param @param sysRobotAudioAndVideo
    * @param @return
    * @return ReturnModel
    * @throws
     */
    @RequestMapping("/editRobotAudio")
    @ResponseBody
    public ReturnModel editRobotAudio(@RequestParam("file") MultipartFile file,SysRobotAudioAndVideo sysRobotAudioAndVideo){
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysRobotAudioAndVideoService.editRobotAudio(file, sysRobotAudioAndVideo);
            if (i == 1){
                dataModel.setData("");
                dataModel.setMsg("媒体资源修改成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else if (i == 2){
                dataModel.setData("");
                dataModel.setMsg("音频文件上传格式不正确！(支持:mp3格式)");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("媒体资源修改失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        }catch (Exception e){
        	logger.info("editRobotAudio", e);
            dataModel.setData("");
            dataModel.setMsg("媒体资源修改失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }
    
    /**
    * @Author 郭凯
    * @Description: 媒体资源编辑(视频)
    * @Title: editRobotVideo
    * @date  2020年12月31日 下午3:50:21 
    * @param @param file
    * @param @param sysRobotAudioAndVideo
    * @param @return
    * @return ReturnModel
    * @throws
     */
    @RequestMapping("/editRobotVideo")
    @ResponseBody
    public ReturnModel editRobotVideo(@RequestParam("file") MultipartFile file,SysRobotAudioAndVideo sysRobotAudioAndVideo){
    	ReturnModel dataModel = new ReturnModel();
    	try {
    		int i = sysRobotAudioAndVideoService.editRobotVideo(file, sysRobotAudioAndVideo);
    		if (i == 1){
    			dataModel.setData("");
    			dataModel.setMsg("媒体资源修改成功！");
    			dataModel.setState(Constant.STATE_SUCCESS);
    			return dataModel;
    		}else if (i == 2){
    			dataModel.setData("");
    			dataModel.setMsg("音频文件上传格式不正确！(支持:mp4格式)");
    			dataModel.setState(Constant.STATE_FAILURE);
    			return dataModel;
    		}else{
    			dataModel.setData("");
    			dataModel.setMsg("媒体资源修改失败！");
    			dataModel.setState(Constant.STATE_FAILURE);
    			return dataModel;
    		}
    	}catch (Exception e){
    		logger.info("editRobotVideo", e);
    		dataModel.setData("");
    		dataModel.setMsg("媒体资源修改失败！(请联系管理员)");
    		dataModel.setState(Constant.STATE_FAILURE);
    		return dataModel;
    	}
    }
    
    /**
    * @Author 郭凯
    * @Description: 删除媒体资源
    * @Title: delRobotAudioAndVideo
    * @date  2020年12月31日 下午4:04:38 
    * @param @param mediaId
    * @param @return
    * @return ReturnModel
    * @throws
     */
    @RequestMapping("/delRobotAudioAndVideo")
    @ResponseBody
    public ReturnModel delRobotAudioAndVideo(@RequestParam("mediaId") Long mediaId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotAudioAndVideoService.delRobotAudioAndVideo(mediaId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("媒体资源删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("媒体资源删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delRobotAudioAndVideo",e);
            returnModel.setData("");
            returnModel.setMsg("媒体资源删除失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

}
