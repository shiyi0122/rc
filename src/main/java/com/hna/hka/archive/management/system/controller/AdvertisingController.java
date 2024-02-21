package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysRobotAdvertising;
import com.hna.hka.archive.management.system.service.SysRobotAdvertisingService;
import com.hna.hka.archive.management.system.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: AdvertisingController
 * @Author: 郭凯
 * @Description: 轮播图管理控制层
 * @Date: 2020/6/4 15:10
 * @Version: 1.0
 */
@RequestMapping("/system/advertising")
@Controller
public class AdvertisingController extends PublicUtil {

    @Autowired
    private HttpSession session;

    @Autowired
    private SysRobotAdvertisingService sysRobotAdvertisingService;

    @Autowired
    private HttpServletRequest request;

    @Value("${filePatheGetPicPaht}")
    private String filePatheGetPicPaht;

    /**
     * @Author 郭凯
     * @Description 轮播图管理列表查询
     * @Date 15:30 2020/6/4
     * @Param [pageNum, pageSize, sysRobotAdvertising]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getAdvertisingList")
    @ResponseBody
    public PageDataResult getAdvertisingList(@RequestParam("pageNum") Integer pageNum,
                                             @RequestParam("pageSize") Integer pageSize, SysRobotAdvertising sysRobotAdvertising) {
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
            pageDataResult = sysRobotAdvertisingService.getAdvertisingList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("轮播图管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 新增轮播图
     * @Date 17:18 2020/6/4
     * @Param [file, sysRobotAdvertising]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/addAdvertising")
    @ResponseBody
    public ReturnModel addAdvertising(@RequestParam("file") MultipartFile file, SysRobotAdvertising sysRobotAdvertising) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if(!file.isEmpty()){
                int a = sysRobotAdvertisingService.addAdvertising(sysRobotAdvertising,file);
                if (a == 1) {
                    dataModel.setData("");
                    dataModel.setMsg("轮播图新增成功！");
                    dataModel.setState(Constant.STATE_SUCCESS);
                    return dataModel;
                }
                if (a == 2) {
                    dataModel.setData("");
                    dataModel.setMsg("图片格式不正确！（只支持png，jpg文件）");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
                if (a == 0) {
                    dataModel.setData("");
                    dataModel.setMsg("轮播图新增失败！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
            }else{
                dataModel.setData("");
                dataModel.setMsg("请选择要上传的图片！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("轮播图新增失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("addAdvertising", e);
            return dataModel;
        }
        return dataModel;
    }

    /**
     * @Author 郭凯
     * @Description 下载轮播图
     * @Date 9:47 2020/6/5
     * @Param [response]
     * @return void
    **/
    @RequestMapping("/download")
    public void downLoad(HttpServletResponse response) throws IOException {
        String fileName = request.getParameter("fileName").toString();
        //获得第一个点的位置
        int index=fileName.indexOf("/");
        //根据第一个点的位置 获得第二个点的位置
        index=fileName.indexOf("/", index+1);
        //根据第二个点的位置，截取 字符串。得到结果 result
        String result=fileName.substring(index+1);
        File file = new File(filePatheGetPicPaht  + result);
        if(file.exists()){
            response.setContentType("application/octet-stream");
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(result,"utf8"));
            byte[] buffer = new byte[1024];
            //输出流
            OutputStream os = null;
            try(FileInputStream fis= new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);) {
                os = response.getOutputStream();
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            response.setContentType("text/html; charset=UTF-8"); //转码
            PrintWriter out = response.getWriter();
            out.flush();
            out.println("<script defer='defer' type='text/javascript'>");
            out.println("alert('文件不存在或已经被删除！');");
            out.println("window.location.href='/page/system/advertising/html/advertisingList.html';");
            out.println("</script>");
        }
    }

    /**
     * @Author 郭凯
     * @Description 删除轮播图
     * @Date 15:20 2020/6/5
     * @Param [advertisingId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delAdvertising")
    @ResponseBody
    public ReturnModel delAdvertising(@RequestParam("advertisingId") Long advertisingId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotAdvertisingService.delAdvertising(advertisingId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("轮播图删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("轮播图删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delAdvertising",e);
            returnModel.setData("");
            returnModel.setMsg("轮播图删除失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 修改轮播图
     * @Date 15:57 2020/6/5
     * @Param [file, sysRobotAdvertising]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/editAdvertising")
    @ResponseBody
    public ReturnModel editAdvertising(@RequestParam("file") MultipartFile file, SysRobotAdvertising sysRobotAdvertising) {
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysRobotAdvertisingService.editAdvertising(sysRobotAdvertising,file);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("轮播图修改成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }
            if (i == 2) {
                dataModel.setData("");
                dataModel.setMsg("图片格式不正确！（只支持png，jpg文件）");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
            if (i == 0) {
                dataModel.setData("");
                dataModel.setMsg("轮播图修改失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("轮播图修改失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("editAdvertising", e);
            return dataModel;
        }
        return dataModel;
    }

    /**
     * @Author 郭凯
     * @Description 修改启用禁用状态
     * @Date 16:36 2020/6/5
     * @Param [sysRobotAdvertising]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/editValid")
    @ResponseBody
    public ReturnModel editValid(SysRobotAdvertising sysRobotAdvertising) {
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysRobotAdvertisingService.editValid(sysRobotAdvertising);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("轮播图状态修改成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("轮播图状态修改失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("轮播图状态修改失败！（请联系管理员）");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("editValid", e);
            return dataModel;
        }
    }
}