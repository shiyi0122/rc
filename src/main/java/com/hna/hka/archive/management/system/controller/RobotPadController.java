package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysRobotPad;
import com.hna.hka.archive.management.system.service.SysRobotPadService;
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
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: RobotPadController
 * @Author: 郭凯
 * @Description: PAD版本管理控制层
 * @Date: 2020/12/14 16:20
 * @Version: 1.0
 */
@RequestMapping("/system/robotPad")
@Controller
public class RobotPadController extends PublicUtil {

    @Autowired
    private SysRobotPadService sysRobotPadService;

    @Autowired
    private HttpServletRequest request;

    @Value("${filePathPadPaht}")
    private String filePathPadPaht;

    /**
     * @Author 郭凯
     * @Description PAD版本管理列表查询
     * @Date 16:25 2020/12/14
     * @Param [pageNum, pageSize, sysRobotPad]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getRobotPadList")
    @ResponseBody
    public PageDataResult getRobotPadList(@RequestParam("pageNum") Integer pageNum,
                                             @RequestParam("pageSize") Integer pageSize, SysRobotPad sysRobotPad) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysRobotPadService.getRobotPadList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("PAD版本管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 新增PAD
     * @Date 9:27 2020/12/15
     * @Param [file, sysRobotPad]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/addRobotPad")
    @ResponseBody
    public ReturnModel addRobotPad(@RequestParam("file") MultipartFile file, SysRobotPad sysRobotPad) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if(!file.isEmpty()){
                if (ToolUtil.isEmpty(sysRobotPad.getPadNumber())) {
                    dataModel.setData("");
                    dataModel.setMsg("请输入版本号！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
                //查询当前版本号
                SysRobotPad robotPad = sysRobotPadService.getAppPadNumber();
                if (ToolUtil.isNotEmpty(robotPad)){
                    int i = VersionCompareUtil.compareVersion(sysRobotPad.getPadNumber(), robotPad.getPadNumber());
                    //i == 0 版本相同  i == 1 当前版本小于输入版本  i == -1 当前版本大于输入版本
                    if (i == 1) {
                        int a = sysRobotPadService.addRobotPad(sysRobotPad,file);
                        if (a == 1) {
                            dataModel.setData("");
                            dataModel.setMsg("添加成功！");
                            dataModel.setState(Constant.STATE_SUCCESS);
                            return dataModel;
                        }else if (a == 2) {
                            dataModel.setData("");
                            dataModel.setMsg("文件格式不正确！（只支持APK文件）");
                            dataModel.setState(Constant.STATE_FAILURE);
                            return dataModel;
                        }else{
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
                    int a = sysRobotPadService.addRobotPad(sysRobotPad,file);
                    if (a == 1) {
                        dataModel.setData("");
                        dataModel.setMsg("添加成功！");
                        dataModel.setState(Constant.STATE_SUCCESS);
                        return dataModel;
                    }else if (a == 2) {
                        dataModel.setData("");
                        dataModel.setMsg("文件格式不正确！（只支持APK文件）");
                        dataModel.setState(Constant.STATE_FAILURE);
                        return dataModel;
                    }else {
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
            logger.error("addRobotPad", e);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 编辑PAD信息
     * @Date 10:12 2020/12/15
     * @Param [sysRobotPad]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/editRobotPad")
    @ResponseBody
    public ReturnModel editRobotPad(SysRobotPad sysRobotPad) {
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysRobotPadService.editRobotPad(sysRobotPad);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("PAD修改成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else {
                dataModel.setData("");
                dataModel.setMsg("PAD修改失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("PAD修改失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("editRobotPad", e);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 删除PAD信息
     * @Date 10:15 2020/12/15
     * @Param [sysRobotPad]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/delRobotPad")
    @ResponseBody
    public ReturnModel delRobotPad(Long padId) {
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysRobotPadService.delRobotPad(padId);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("PAD删除成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else {
                dataModel.setData("");
                dataModel.setMsg("PAD删除失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("PAD删除失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("editRobotPad", e);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 下载PAD文件
     * @Date 10:18 2020/12/15
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
            out.println("window.location.href='/page/system/robotPad/html/robotPadList.html';");
            out.println("</script>");
        }
    }

    /**
     * @Author 郭凯
     * @Description 查询所有PAD信息
     * @Date 13:21 2020/12/15
     * @Param []
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/getRobotPad")
    @ResponseBody
    public ReturnModel getRobotPad() {
        ReturnModel dataModel = new ReturnModel();
        try {
            List<SysRobotPad> sysRobotPadList  = sysRobotPadService.getRobotPad();
            if (ToolUtil.isNotEmpty(sysRobotPadList)) {
                dataModel.setData(sysRobotPadList);
                dataModel.setMsg("PAD信息查询成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else {
                dataModel.setData("");
                dataModel.setMsg("PAD信息查询失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("PAD信息查询失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("getRobotPad", e);
            return dataModel;
        }
    }

}
