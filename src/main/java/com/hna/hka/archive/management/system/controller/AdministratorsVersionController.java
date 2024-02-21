package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.managerApp.model.SysRobotAdministratorsVersion;
import com.hna.hka.archive.management.system.service.SysRobotAdministratorsVersionService;
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
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: AdministratorsVersionController
 * @Author: 郭凯
 * @Description: APP版本管理控制层
 * @Date: 2020/11/19 9:10
 * @Version: 1.0
 */
@RequestMapping("/system/administratorsVersion")
@Controller
public class AdministratorsVersionController extends PublicUtil {

    @Autowired
    private SysRobotAdministratorsVersionService sysRobotAdministratorsVersionService;

    @Autowired
    private HttpServletRequest request;

    @Value("${filePathPadPaht}")
    private String filePathPadPaht;

    /**
     * @Author 郭凯
     * @Description APP版本管理列表查询
     * @Date 9:15 2020/11/19
     * @Param [pageNum, pageSize, sysRobotAdministratorsVersion]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getAdministratorsVersionList")
    @ResponseBody
    public PageDataResult getAdministratorsVersionList(@RequestParam("pageNum") Integer pageNum,
                                             @RequestParam("pageSize") Integer pageSize, SysRobotAdministratorsVersion sysRobotAdministratorsVersion) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysRobotAdministratorsVersionService.getAdministratorsVersionList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("APP版本管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description APP删除
     * @Date 9:38 2020/11/19
     * @Param [versionId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delAdministratorsVersion")
    @ResponseBody
    public ReturnModel delAdministratorsVersion(@RequestParam("versionId") Long versionId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotAdministratorsVersionService.delAdministratorsVersion(versionId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("APP删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("APP删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delAdministratorsVersion",e);
            returnModel.setData("");
            returnModel.setMsg("APP删除失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description APP上传
     * @Date 11:31 2020/11/19
     * @Param [file, sysRobotAdministratorsVersion]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addAdministratorsVersion")
    @ResponseBody
    public ReturnModel addAdministratorsVersion(@RequestParam("file") MultipartFile file, SysRobotAdministratorsVersion sysRobotAdministratorsVersion) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if (!file.isEmpty()){
                //查询最新版本号
                SysRobotAdministratorsVersion appVersion = sysRobotAdministratorsVersionService.getAdministratorsVersion();
                if (ToolUtil.isEmpty(appVersion)){
                    dataModel.setData("");
                    dataModel.setMsg("版本号查询失败！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
                int j = VersionCompareUtil.compareVersion(sysRobotAdministratorsVersion.getVersionNumber(), appVersion.getVersionNumber());
                //i == 0 版本相同  i == 1 当前版本小于输入版本  i == -1 当前版本大于输入版本
                if (j == 0){
                    dataModel.setData("");
                    dataModel.setMsg("文件版本相同！请修改");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }else if (j == -1){
                    dataModel.setData("");
                    dataModel.setMsg("当前版本大于输入版本！请修改");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
                int i = sysRobotAdministratorsVersionService.addAdministratorsVersion(file,sysRobotAdministratorsVersion);
                if (i == 1) {
                    dataModel.setData("");
                    dataModel.setMsg("APP上传成功！");
                    dataModel.setState(Constant.STATE_SUCCESS);
                    return dataModel;
                }
                if (i == 2) {
                    dataModel.setData("");
                    dataModel.setMsg("APP格式不正确！（只支持apk文件）");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
                if (i == 0) {
                    dataModel.setData("");
                    dataModel.setMsg("APP上传失败！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
            }else{
                dataModel.setData("");
                dataModel.setMsg("请选择上传的文件！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("APP上传失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("addAdministratorsVersion", e);
            return dataModel;
        }
        return dataModel;
    }

    /**
     * @Author 郭凯
     * @Description 下载APP文件
     * @Date 13:02 2020/11/19
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
        File file = new File(filePathPadPaht  + result);
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
            out.println("window.location.href='/page/managerApp/administratorsVersion/html/administratorsVersionList.html';");
            out.println("</script>");
        }
    }

    /**
     * @Author 郭凯
     * @Description APP修改
     * @Date 13:36 2020/11/19
     * @Param [file, sysRobotAdministratorsVersion]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editAdministratorsVersion")
    @ResponseBody
    public ReturnModel editAdministratorsVersion(SysRobotAdministratorsVersion sysRobotAdministratorsVersion) {
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysRobotAdministratorsVersionService.editAdministratorsVersion(sysRobotAdministratorsVersion);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("APP编辑成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }
            if (i == 0) {
                dataModel.setData("");
                dataModel.setMsg("APP编辑失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("APP修改失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("editAdministratorsVersion", e);
            return dataModel;
        }
        return dataModel;
    }

}
