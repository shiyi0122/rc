package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysRobotAppVersion;
import com.hna.hka.archive.management.system.service.SysRobotAppVersionService;
import com.hna.hka.archive.management.system.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: RobotVersionPadController
 * @Author: 郭凯
 * @Description: PAD版本管理控制层
 * @Date: 2020/5/29 16:48
 * @Version: 1.0
 */

@RequestMapping("/system/robotVersionPad")
@Controller
public class RobotVersionPadController extends PublicUtil {

    @Autowired
    private SysRobotAppVersionService sysRobotAppVersionService;

    @Autowired
    private HttpSession session;

    @Autowired
    private HttpServletRequest request;

    @Value("${filePathPadPaht}")
    private String filePathPadPaht;

    /**
     * @Author 郭凯
     * @Description PAD版本管理列表查询
     * @Date 16:49 2020/5/29
     * @Param [pageNum, pageSize, sysRole]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping(value = "/getRobotVersionPadList", method = RequestMethod.GET)
    @ResponseBody
    public PageDataResult getRobotVersionPadList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize, SysRobotAppVersion sysRobotAppVersion) {
        PageDataResult pageDataResult = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            sysRobotAppVersion.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            pageDataResult = sysRobotAppVersionService.getRobotVersionPadList(pageNum,pageSize,sysRobotAppVersion);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("PAD版本管理列表查询异常！", e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 新增PAD信息
     * @Date 13:37 2020/5/31
     * @Param [file, sysRobotAppVersion]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/addRobotVersionPad")
    @ResponseBody
    public ReturnModel addRobotVersionPad(@RequestParam("file") MultipartFile file, SysRobotAppVersion sysRobotAppVersion) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if(!file.isEmpty()){
                if (ToolUtil.isEmpty(sysRobotAppVersion.getVersionNumber())) {
                    dataModel.setData("");
                    dataModel.setMsg("请输入版本号！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
                //查询当前版本号
                SysRobotAppVersion appVersion = sysRobotAppVersionService.getAppVersionNumber(sysRobotAppVersion.getScenicSpotId());
                if (ToolUtil.isNotEmpty(appVersion)){
                    int i = VersionCompareUtil.compareVersion(sysRobotAppVersion.getVersionNumber(), appVersion.getVersionNumber());
                    //i == 0 版本相同  i == 1 当前版本小于输入版本  i == -1 当前版本大于输入版本
                    if (i == 1) {
                        int a = sysRobotAppVersionService.addRobotVersionPad(sysRobotAppVersion,file);
                        if (a == 1) {
                            dataModel.setData("");
                            dataModel.setMsg("添加成功！");
                            dataModel.setState(Constant.STATE_SUCCESS);
                            return dataModel;
                        }
                        if (a == 2) {
                            dataModel.setData("");
                            dataModel.setMsg("文件格式不正确！（只支持APK文件）");
                            dataModel.setState(Constant.STATE_FAILURE);
                            return dataModel;
                        }
                        if (a == 0) {
                            dataModel.setData("");
                            dataModel.setMsg("添加失败！");
                            dataModel.setState(Constant.STATE_FAILURE);
                            return dataModel;
                        }
                    }else {
                        dataModel.setData("");
                        dataModel.setMsg("输入版本号小于当前版本号！");
                        dataModel.setState(Constant.STATE_FAILURE);
                        return dataModel;
                    }
                }else{
                    int a = sysRobotAppVersionService.addRobotVersionPad(sysRobotAppVersion,file);
                    if (a == 1) {
                        dataModel.setData("");
                        dataModel.setMsg("添加成功！");
                        dataModel.setState(Constant.STATE_SUCCESS);
                        return dataModel;
                    }
                    if (a == 2) {
                        dataModel.setData("");
                        dataModel.setMsg("文件格式不正确！（只支持APK文件）");
                        dataModel.setState(Constant.STATE_FAILURE);
                        return dataModel;
                    }
                    if (a == 0) {
                        dataModel.setData("");
                        dataModel.setMsg("添加失败！");
                        dataModel.setState(Constant.STATE_FAILURE);
                        return dataModel;
                    }
                }
            }else{
                dataModel.setData("");
                dataModel.setMsg("请选择要上传的apk文件！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("新增失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("addRobotVersionPad", e);
            return dataModel;
        }
        return dataModel;
    }

    /**
     * @Author 郭凯
     * @Description 删除PAD信息
     * @Date 15:45 2020/5/31
     * @Param [robotId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delRobotVersionPad")
    @ResponseBody
    public ReturnModel delRobotVersionPad(@RequestParam("versionId") Long versionId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotAppVersionService.delRobotVersionPad(versionId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("PAD删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("PAD删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delRobotVersionPad",e);
            returnModel.setData("");
            returnModel.setMsg("PAD删除失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 下载文件
     * @Date 13:31 2020/6/3
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
            out.println("window.location.href='/page/system/robotVersionPad/html/robotVersionPadList.html';");
            out.println("</script>");
        }
    }

    /**
     * @Author 郭凯
     * @Description 修改PAD信息
     * @Date 10:25 2020/9/9
     * @Param [file, sysRobotAppVersion]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/editRobotVersionPad")
    @ResponseBody
    public ReturnModel editRobotVersionPad(SysRobotAppVersion sysRobotAppVersion) {
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysRobotAppVersionService.editRobotVersionPad(sysRobotAppVersion);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("PAD修改成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }
            if (i == 2) {
                dataModel.setData("");
                dataModel.setMsg("文件格式不正确！（只支持APK文件）");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
            if (i == 0) {
                dataModel.setData("");
                dataModel.setMsg("PAD修改失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("PAD修改失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("editRobotVersionPad", e);
            return dataModel;
        }
        return dataModel;
    }

}
