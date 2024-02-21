package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysScenicSpotInfraredBroadcast;
import com.hna.hka.archive.management.system.service.SysScenicSpotInfraredBroadcastService;
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
 * @ClassName: InfraredBroadcastController
 * @Author: 郭凯
 * @Description: 红外播报管理控制层
 * @Date: 2020/11/12 9:25
 * @Version: 1.0
 */
@RequestMapping("/system/infraredBroadcast")
@Controller
public class InfraredBroadcastController extends PublicUtil {

    @Autowired
    private SysScenicSpotInfraredBroadcastService sysScenicSpotInfraredBroadcastService;

    @Autowired
    private HttpSession session;

    /**
     * @Author 郭凯
     * @Description 红外播报管理列表查询
     * @Date 9:27 2020/11/12
     * @Param [pageNum, pageSize, sysScenicSpotInfraredBroadcast]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getInfraredBroadcastList")
    @ResponseBody
    public PageDataResult getDepositRefundLogList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, SysScenicSpotInfraredBroadcast sysScenicSpotInfraredBroadcast) {
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
            pageDataResult = sysScenicSpotInfraredBroadcastService.getInfraredBroadcastList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("红外播报管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 新增红外播报（文字）
     * @Date 14:30 2020/11/16
     * @Param [sysScenicSpotInfraredBroadcast]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addInfraredBroadcastWrittenWords")
    @ResponseBody
    public ReturnModel addInfraredBroadcastWrittenWords(SysScenicSpotInfraredBroadcast sysScenicSpotInfraredBroadcast){
        ReturnModel returnModel = new ReturnModel();
        try {
            sysScenicSpotInfraredBroadcast.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            int i = sysScenicSpotInfraredBroadcastService.addInfraredBroadcastWrittenWords(sysScenicSpotInfraredBroadcast);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("红外播报新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("红外播报新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("红外播报新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 新增红外播报（音频）
     * @Date 15:03 2020/11/16
     * @Param [file, sysScenicSpotInfraredBroadcast]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addInfraredBroadcastAudio")
    @ResponseBody
    public ReturnModel addInfraredBroadcastAudio(@RequestParam("file") MultipartFile file,SysScenicSpotInfraredBroadcast sysScenicSpotInfraredBroadcast){
        ReturnModel returnModel = new ReturnModel();
        try {
            if (!file.isEmpty()){
                sysScenicSpotInfraredBroadcast.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
                int i = sysScenicSpotInfraredBroadcastService.addInfraredBroadcastAudio(file,sysScenicSpotInfraredBroadcast);
                if (i == 1){
                    returnModel.setData("");
                    returnModel.setMsg("红外播报新增成功！");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                }else if(i == 2){
                    returnModel.setData("");
                    returnModel.setMsg("文件上传格式不正确！(支持:mp3格式)");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }else{
                    returnModel.setData("");
                    returnModel.setMsg("红外播报新增失败！");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
            }else{
                returnModel.setData("");
                returnModel.setMsg("请上传音频文件！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("红外播报新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 编辑红外播报（文字）
     * @Date 15:27 2020/11/16
     * @Param [sysScenicSpotInfraredBroadcast]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editInfraredBroadcastWrittenWords")
    @ResponseBody
    public ReturnModel editInfraredBroadcastWrittenWords(SysScenicSpotInfraredBroadcast sysScenicSpotInfraredBroadcast){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotInfraredBroadcastService.editInfraredBroadcastWrittenWords(sysScenicSpotInfraredBroadcast);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("红外播报编辑成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("红外播报编辑失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("红外播报编辑失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 红外播报编辑（音频）
     * @Date 15:29 2020/11/16
     * @Param [file, sysScenicSpotInfraredBroadcast]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editInfraredBroadcastAudio")
    @ResponseBody
    public ReturnModel editInfraredBroadcastAudio(@RequestParam("file") MultipartFile file,SysScenicSpotInfraredBroadcast sysScenicSpotInfraredBroadcast){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotInfraredBroadcastService.editInfraredBroadcastAudio(file,sysScenicSpotInfraredBroadcast);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("红外播报编辑成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if(i == 2){
                returnModel.setData("");
                returnModel.setMsg("文件上传格式不正确！(支持:mp3格式)");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("红外播报编辑失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("红外播报编辑失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 红外播报删除
     * @Date 15:34 2020/11/16
     * @Param [infraredId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delInfraredBroadcast")
    @ResponseBody
    public ReturnModel delInfraredBroadcast(@RequestParam("infraredId") Long infraredId){
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysScenicSpotInfraredBroadcastService.delInfraredBroadcast(infraredId);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("红外播报删除成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("红外播报删除失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("delInfraredBroadcast",e);
            dataModel.setData("");
            dataModel.setMsg("红外播报删除失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

}
