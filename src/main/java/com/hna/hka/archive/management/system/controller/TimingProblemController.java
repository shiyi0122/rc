package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysScenicSpotRandomProblemWithBLOBs;
import com.hna.hka.archive.management.system.model.SysScenicSpotTimingProblemWithBLOBs;
import com.hna.hka.archive.management.system.service.SysScenicSpotRandomProblemService;
import com.hna.hka.archive.management.system.service.SysScenicSpotTimingProblemService;
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
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: RandomProblemController
 * @Author: 郭凯
 * @Description: 随机播报管理控制层
 * @Date: 2020/6/20 10:19
 * @Version: 1.0
 */
@RequestMapping("/system/timingProblem")
@Controller
public class TimingProblemController extends PublicUtil {

    @Autowired
    private HttpSession session;

    @Autowired
    private SysScenicSpotTimingProblemService sysScenicSpotTimingProblemService;

    /**
     * @Author 郭凯
     * @Description 随机播报管理列表查询
     * @Date 10:25 2020/6/20
     * @Param [pageNum, pageSize, sysScenicSpotRandomProblemWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getTimingProblemList")
    @ResponseBody
    public PageDataResult getTimingProblemList(@RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize, SysScenicSpotTimingProblemWithBLOBs sysScenicSpotTimingProblemWithBLOBs) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("timingProblem",sysScenicSpotTimingProblemWithBLOBs.getTimingProblem());
//            search.put("scenicSpotId",session.getAttribute("scenicSpotId").toString());
            pageDataResult = sysScenicSpotTimingProblemService.getTimingProblemList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("随机播报管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 删除随机播报
     * @Date 9:11 2020/6/22
     * @Param [problemId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/delTimingProblem")
    @ResponseBody
    public ReturnModel delTimingProblem(@RequestParam("problemId") Long problemId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotTimingProblemService.delTimingProblem(problemId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("随机播报删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("随机播报删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delRandomProblem",e);
            returnModel.setData("");
            returnModel.setMsg("随机播报删除失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 修改随机播报状态
     * @Date 9:19 2020/6/22
     * @Param [sysScenicSpotRandomProblemWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editTimingProblemValid")
    @ResponseBody
    public ReturnModel editTimingProblemValid(SysScenicSpotTimingProblemWithBLOBs sysScenicSpotTimingProblemWithBLOBs){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotTimingProblemService.editTimingProblemValid(sysScenicSpotTimingProblemWithBLOBs);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("随机播报状态修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("随机播报状态修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("editRandomProblemValid",e);
            returnModel.setData("");
            returnModel.setMsg("随机播报状态修改失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 新增随机播报(文字)
     * @Date 13:57 2020/7/29
     * @Param [file, sysScenicSpotRandomProblemWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addTimingProblemImage")
    @ResponseBody
    public ReturnModel addTimingProblemImage(@RequestParam MultipartFile file, SysScenicSpotTimingProblemWithBLOBs sysScenicSpotTimingProblemWithBLOBs){
        ReturnModel dataModel = new ReturnModel();
        try {
//            if ("1".equals(sysScenicSpotTimingProblemWithBLOBs.getScenicSpotId().toString())) {
//                sysScenicSpotTimingProblemWithBLOBs.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
//            }else if ("2".equals(sysScenicSpotTimingProblemWithBLOBs.getScenicSpotId().toString())){
//                sysScenicSpotTimingProblemWithBLOBs.setScenicSpotId(Long.parseLong("20200210"));
//            }
            int i = sysScenicSpotTimingProblemService.addTimingProblemImage(file, sysScenicSpotTimingProblemWithBLOBs);
            if (i == 1){
                dataModel.setData("");
                dataModel.setMsg("随机播报新增成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else if (i == 2){
                dataModel.setData("");
                dataModel.setMsg("图片格式不正确！（只支持png，jpg文件）");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("随机播报新增失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        }catch (Exception e){
            logger.info("新增随机播报(文字)",e);
            dataModel.setData("");
            dataModel.setMsg("随机播报新增失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 新增随机播报(视频)
     * @Date 15:31 2020/7/29
     * @Param [file, sysScenicSpotRandomProblemWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/addTimingProblemVideo")
    @ResponseBody
    public ReturnModel addTimingProblemVideo(@RequestParam("file") MultipartFile file, SysScenicSpotTimingProblemWithBLOBs sysScenicSpotTimingProblemWithBLOBs){
        ReturnModel dataModel = new ReturnModel();
        try {
            if (!file.isEmpty()){
//                if ("1".equals(sysScenicSpotTimingProblemWithBLOBs.getScenicSpotId().toString())) {
//                    sysScenicSpotTimingProblemWithBLOBs.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
//                }else if ("2".equals(sysScenicSpotTimingProblemWithBLOBs.getScenicSpotId().toString())){
//                    sysScenicSpotTimingProblemWithBLOBs.setScenicSpotId(Long.parseLong("20200210"));
//                }
                int i = sysScenicSpotTimingProblemService.addTimingProblemVideo(file, sysScenicSpotTimingProblemWithBLOBs);
                if (i == 1){
                    dataModel.setData("");
                    dataModel.setMsg("随机播报新增成功！");
                    dataModel.setState(Constant.STATE_SUCCESS);
                    return dataModel;
                }else if (i == 2){
                    dataModel.setData("");
                    dataModel.setMsg("视频文件上传格式不正确！(支持:mp4，flv，avi，rm，rmvb，wmv格式）");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }else{
                    dataModel.setData("");
                    dataModel.setMsg("随机播报新增失败！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
            }else{
                dataModel.setData("");
                dataModel.setMsg("请选择上传的视频！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        }catch (Exception e){
            logger.info("新增随机播报(视频)",e);
            dataModel.setData("");
            dataModel.setMsg("随机播报新增失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 新增随机播报(音频)
     * @Date 16:05 2020/7/29
     * @Param [file, files, sysScenicSpotRandomProblemWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/addTimingProblemAudio")
    @ResponseBody
    public ReturnModel addTimingProblemAudio(@RequestParam MultipartFile[] file, SysScenicSpotTimingProblemWithBLOBs timingProblem){
        ReturnModel dataModel = new ReturnModel();
        try {
//            if ("1".equals(timingProblem.getScenicSpotId().toString())) {
//                timingProblem.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
//            }else if ("2".equals(timingProblem.getScenicSpotId().toString())){
//                timingProblem.setScenicSpotId(Long.parseLong("20200210"));
//            }
            int i = sysScenicSpotTimingProblemService.addTimingProblemAudio(file,timingProblem);
            if (i == 1){
                dataModel.setData("");
                dataModel.setMsg("随机播报新增成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else if (i == 2){
                dataModel.setData("");
                dataModel.setMsg("音频文件不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }else if (i == 3){
                dataModel.setData("");
                dataModel.setMsg("音频文件上传格式不正确！(支持:mp3格式)");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }else if (i == 4){
                dataModel.setData("");
                dataModel.setMsg("图片文件上传格式不正确！(支持:png，jpg格式)");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("随机播报新增失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        }catch (Exception e){
            logger.info("新增随机播报(音频)",e);
            dataModel.setData("");
            dataModel.setMsg("随机播报新增失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 编辑随机播报(文字)
     * @Date 15:35 2020/11/2
     * @Param [file, sysScenicSpotRandomProblemWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editTimingProblemImage")
    @ResponseBody
    public ReturnModel editTimingProblemImage(@RequestParam MultipartFile file, SysScenicSpotTimingProblemWithBLOBs sysScenicSpotTimingProblemWithBLOBs){
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysScenicSpotTimingProblemService.editTimingProblemImage(file, sysScenicSpotTimingProblemWithBLOBs);
            if (i == 1){
                dataModel.setData("");
                dataModel.setMsg("随机播报编辑成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else if (i == 2){
                dataModel.setData("");
                dataModel.setMsg("图片格式不正确！（只支持png，jpg文件）");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("随机播报编辑失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        }catch (Exception e){
            logger.info("editRandomProblemImage",e);
            dataModel.setData("");
            dataModel.setMsg("随机播报编辑失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 编辑随机播报(视频)
     * @Date 15:48 2020/11/2
     * @Param [file, sysScenicSpotRandomProblemWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editTimingProblemVideo")
    @ResponseBody
    public ReturnModel editTimingProblemVideo(@RequestParam MultipartFile file, SysScenicSpotTimingProblemWithBLOBs sysScenicSpotTimingProblemWithBLOBs){
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysScenicSpotTimingProblemService.editTimingProblemVideo(file, sysScenicSpotTimingProblemWithBLOBs);
            if (i == 1){
                dataModel.setData("");
                dataModel.setMsg("随机播报编辑成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else if (i == 2){
                dataModel.setData("");
                dataModel.setMsg("视频文件上传格式不正确！(支持:mp4，flv，avi，rm，rmvb，wmv格式）");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("随机播报编辑失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        }catch (Exception e){
            dataModel.setData("");
            dataModel.setMsg("随机播报编辑失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 编辑随机播报(音频)
     * @Date 17:39 2020/11/2
     * @Param [file, randomProblem]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editTimingProblemAudio")
    @ResponseBody
    public ReturnModel editTimingProblemAudio(@RequestParam MultipartFile[] file, SysScenicSpotTimingProblemWithBLOBs timingProblem){
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysScenicSpotTimingProblemService.editTimingProblemAudio(file,timingProblem);
            if (i == 1){
                dataModel.setData("");
                dataModel.setMsg("随机播报编辑成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else if (i == 2){
                dataModel.setData("");
                dataModel.setMsg("音频文件上传格式不正确！(支持:mp3格式)");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }else if (i == 3){
                dataModel.setData("");
                dataModel.setMsg("图片文件上传格式不正确！(支持:png，jpg格式)");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("随机播报编辑失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        }catch (Exception e){
            logger.info("editRandomProblemAudio",e);
            dataModel.setData("");
            dataModel.setMsg("随机播报编辑失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

}
