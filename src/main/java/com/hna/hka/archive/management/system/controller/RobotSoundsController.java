package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysRobotSounds;
import com.hna.hka.archive.management.system.model.SysScenicSpotCustomType;
import com.hna.hka.archive.management.system.service.SysRobotSoundsService;
import com.hna.hka.archive.management.system.service.SysScenicSpotCustomTypeService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: RobotSoundsController
 * @Author: 郭凯
 * @Description: 机器人提示音管理控制层
 * @Date: 2020/11/12 10:19
 * @Version: 1.0
 */
@RequestMapping("/system/robotSounds")
@Controller
public class RobotSoundsController extends PublicUtil {

    @Autowired
    private HttpSession session;

    @Autowired
    private SysRobotSoundsService sysRobotSoundsService;

    @Autowired
    private SysScenicSpotCustomTypeService sysScenicSpotCustomTypeService;

    /**
     * @Author 郭凯
     * @Description 机器人提示音管理列表查询
     * @Date 10:23 2020/11/12
     * @Param [pageNum, pageSize, sysRobotSounds]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getRobotSoundsList")
    @ResponseBody
    public PageDataResult getRobotSoundsList(@RequestParam("pageNum") Integer pageNum,
                                                      @RequestParam("pageSize") Integer pageSize, SysRobotSounds sysRobotSounds) {
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
            pageDataResult = sysRobotSoundsService.getRobotSoundsList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("机器人提示音管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 机器人提示音删除
     * @Date 9:55 2020/11/16
     * @Param [soundsId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delRobotSounds")
    @ResponseBody
    public ReturnModel delRobotSounds(@RequestParam("soundsId") Long soundsId){
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysRobotSoundsService.delRobotSounds(soundsId);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("机器人提示音删除成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("机器人提示音删除失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("delRobotSounds",e);
            dataModel.setData("");
            dataModel.setMsg("机器人提示音删除失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 查询机器人提示音类型
     * @Date 10:56 2020/11/16
     * @Param []
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/getRobotSoundsCueToneTypeList")
    @ResponseBody
    public ReturnModel getRobotSoundsCueToneTypeList(){
        ReturnModel dataModel = new ReturnModel();
        try {
            Map<String,String> search = new HashMap<>();
            List<SysScenicSpotCustomType> sysScenicSpotCustomTypeList = sysScenicSpotCustomTypeService.getRobotSoundsCueToneTypeList(search);
            dataModel.setData(sysScenicSpotCustomTypeList);
            dataModel.setMsg("器人提示音类型查询成功！");
            dataModel.setState(Constant.STATE_SUCCESS);
            return dataModel;
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("getRobotSoundsCueToneTypeList",e);
            dataModel.setData("");
            dataModel.setMsg("器人提示音类型查询失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }


    /**
     * @Author 郭凯
     * @Description 新增机器人提示音(文字)
     * @Date 11:15 2020/11/16
     * @Param [sysRobotSounds]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addRobotSoundsWrittenWords")
    @ResponseBody
    public ReturnModel addRobotSoundsWrittenWords(SysRobotSounds sysRobotSounds){
        ReturnModel dataModel = new ReturnModel();
        try {
            sysRobotSounds.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            int i = sysRobotSoundsService.addRobotSoundsWrittenWords(sysRobotSounds);
            if (i == 1){
                dataModel.setData("");
                dataModel.setMsg("机器人提示音新增成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("机器人提示音新增失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("addRobotSounds",e);
            dataModel.setData("");
            dataModel.setMsg("机器人提示音新增失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 新增机器人提示音（视频）
     * @Date 11:26 2020/11/16
     * @Param [sysRobotSounds]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addRobotSoundsVideo")
    @ResponseBody
    public ReturnModel addRobotSoundsVideo(@RequestParam("file") MultipartFile file ,SysRobotSounds sysRobotSounds){
        ReturnModel dataModel = new ReturnModel();
        try {
            if (!file.isEmpty()){
                sysRobotSounds.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
                int i = sysRobotSoundsService.addRobotSoundsVideo(file,sysRobotSounds);
                if (i == 1){
                    dataModel.setData("");
                    dataModel.setMsg("机器人提示音新增成功！");
                    dataModel.setState(Constant.STATE_SUCCESS);
                    return dataModel;
                }else if(i == 2){
                    dataModel.setData("");
                    dataModel.setMsg("文件上传格式不正确！(支持:mp4，flv，avi，rm，rmvb，wmv格式)");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }else{
                    dataModel.setData("");
                    dataModel.setMsg("机器人提示音新增失败！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
            }else{
                dataModel.setData("");
                dataModel.setMsg("请上传视频文件！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("addRobotSounds",e);
            dataModel.setData("");
            dataModel.setMsg("机器人提示音新增失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 新增机器人提示音（音频）
     * @Date 12:33 2020/11/16
     * @Param [file, sysRobotSounds]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addRobotSoundsAudio")
    @ResponseBody
    public ReturnModel addRobotSoundsAudio(@RequestParam("file") MultipartFile file ,SysRobotSounds sysRobotSounds){
        ReturnModel dataModel = new ReturnModel();
        try {
            if (!file.isEmpty()){
                sysRobotSounds.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
                int i = sysRobotSoundsService.addRobotSoundsAudio(file,sysRobotSounds);
                if (i == 1){
                    dataModel.setData("");
                    dataModel.setMsg("机器人提示音新增成功！");
                    dataModel.setState(Constant.STATE_SUCCESS);
                    return dataModel;
                }else if(i == 2){
                    dataModel.setData("");
                    dataModel.setMsg("文件上传格式不正确！(支持:mp3格式)");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }else{
                    dataModel.setData("");
                    dataModel.setMsg("机器人提示音新增失败！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
            }else{
                dataModel.setData("");
                dataModel.setMsg("请上传音频文件！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("addRobotSounds",e);
            dataModel.setData("");
            dataModel.setMsg("机器人提示音新增失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 编辑机器人提示音（文字）
     * @Date 13:31 2020/11/16
     * @Param [sysRobotSounds]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editRobotSoundsWrittenWords")
    @ResponseBody
    public ReturnModel editRobotSoundsWrittenWords(SysRobotSounds sysRobotSounds){
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysRobotSoundsService.editRobotSoundsWrittenWords(sysRobotSounds);
            if (i == 1){
                dataModel.setData("");
                dataModel.setMsg("机器人提示音编辑成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("机器人提示音编辑失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("editRobotSoundsWrittenWords",e);
            dataModel.setData("");
            dataModel.setMsg("机器人提示音编辑失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 编辑机器人提示音（视频）
     * @Date 13:35 2020/11/16
     * @Param [sysRobotSounds]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editRobotSoundsVideo")
    @ResponseBody
    public ReturnModel editRobotSoundsVideo(@RequestParam MultipartFile file,SysRobotSounds sysRobotSounds){
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysRobotSoundsService.editRobotSoundsVideo(file,sysRobotSounds);
            if (i == 1){
                dataModel.setData("");
                dataModel.setMsg("机器人提示音编辑成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else if(i == 2){
                dataModel.setData("");
                dataModel.setMsg("文件上传格式不正确！(支持:mp4，flv，avi，rm，rmvb，wmv格式)");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("机器人提示音编辑失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("editRobotSoundsVideo",e);
            dataModel.setData("");
            dataModel.setMsg("机器人提示音编辑失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 编辑机器人提示音（音频）
     * @Date 13:44 2020/11/16
     * @Param [file, sysRobotSounds]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editRobotSoundsAudio")
    @ResponseBody
    public ReturnModel editRobotSoundsAudio(@RequestParam MultipartFile file,SysRobotSounds sysRobotSounds){
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysRobotSoundsService.editRobotSoundsAudio(file,sysRobotSounds);
            if (i == 1){
                dataModel.setData("");
                dataModel.setMsg("机器人提示音编辑成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else if(i == 2){
                dataModel.setData("");
                dataModel.setMsg("文件上传格式不正确！(支持:mp3格式)");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("机器人提示音编辑失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("editRobotSoundsAudio",e);
            dataModel.setData("");
            dataModel.setMsg("机器人提示音编辑失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

}
