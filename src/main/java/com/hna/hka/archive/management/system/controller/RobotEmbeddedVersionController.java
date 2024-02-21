package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysRobotEmbeddedVersion;
import com.hna.hka.archive.management.system.service.SysRobotEmbeddedVersionService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
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
 * @ClassName: RobotEmbeddedVersionController
 * @Author: 郭凯
 * @Description: 嵌入式软件管理控制层
 * @Date: 2020/9/8 13:41
 * @Version: 1.0
 */
@RequestMapping("/system/robotEmbeddedVersion")
@Controller
public class RobotEmbeddedVersionController extends PublicUtil {

    @Autowired
    private SysRobotEmbeddedVersionService sysRobotEmbeddedVersionService;

    @Autowired
    private HttpSession session;

    @Autowired
    private HttpServletRequest request;

    @Value("${getEmbeddedPaht}")
    private String getEmbeddedPaht;

    /**
     * @Author 郭凯
     * @Description 嵌入式软件管理列表查询
     * @Date 13:44 2020/9/8
     * @Param [pageNum, pageSize, sysRobotEmbeddedVersion]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getRobotEmbeddedVersionList")
    @ResponseBody
    public PageDataResult getRobotEmbeddedVersionList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize, SysRobotEmbeddedVersion sysRobotEmbeddedVersion) {
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
            pageDataResult = sysRobotEmbeddedVersionService.getRobotEmbeddedVersionList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("嵌入式软件管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 新增嵌入式软件
     * @Date 15:06 2020/9/8
     * @Param [file, sysRobotEmbeddedVersion]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addRobotEmbeddedVersion")
    @ResponseBody
    public ReturnModel addRobotEmbeddedVersion(@RequestParam("file") MultipartFile file, SysRobotEmbeddedVersion sysRobotEmbeddedVersion) {
        ReturnModel dataModel = new ReturnModel();
        try {
        	if (!file.isEmpty()) {
	            int upload = sysRobotEmbeddedVersionService.addRobotEmbeddedVersion(sysRobotEmbeddedVersion,file);
	            if (upload == 1) {
	                dataModel.setData("");
	                dataModel.setMsg("嵌入式软件新增成功！");
	                dataModel.setState(Constant.STATE_SUCCESS);
	                return dataModel;
	            }else if (upload == 2) {
	                dataModel.setData("");
	                dataModel.setMsg("文件上传格式不正确！(支持:bin格式)");
	                dataModel.setState(Constant.STATE_FAILURE);
	                return dataModel;
	            }else if (upload == 3) {
	                dataModel.setData("");
	                dataModel.setMsg("请选择对应的文件类型");
	                dataModel.setState(Constant.STATE_FAILURE);
	                return dataModel;
	            }else if (upload == 4) {
	                dataModel.setData("");
	                dataModel.setMsg("文件命名错误");
	                dataModel.setState(Constant.STATE_FAILURE);
	                return dataModel;
	            }else if (upload == 5) {
	                dataModel.setData("");
	                dataModel.setMsg("文件命名错误");
	                dataModel.setState(Constant.STATE_FAILURE);
	                return dataModel;
	            }else if (upload == 6) {
	                dataModel.setData("");
	                dataModel.setMsg("文件命名错误");
	                dataModel.setState(Constant.STATE_FAILURE);
	                return dataModel;
	            }else if (upload == 7) {
	                dataModel.setData("");
	                dataModel.setMsg("存在相同版本号，请修改文件名");
	                dataModel.setState(Constant.STATE_FAILURE);
	                return dataModel;
	            }else{
	                dataModel.setData("");
	                dataModel.setMsg("嵌入式软件新增失败！");
	                dataModel.setState(Constant.STATE_FAILURE);
	                return dataModel;
	            }
        	}else {
        		dataModel.setData("");
                dataModel.setMsg("请选择版本文件！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
        	}
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("addRobotEmbeddedVersion",e);
            dataModel.setData("");
            dataModel.setMsg("嵌入式软件新增失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 嵌入式软件删除
     * @Date 15:50 2020/9/8
     * @Param [embeddedId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delRobotEmbeddedVersion")
    @ResponseBody
    public ReturnModel delRobotEmbeddedVersion(@RequestParam("embeddedId") Long embeddedId) {
        ReturnModel dataModel = new ReturnModel();
        try {
            int upload = sysRobotEmbeddedVersionService.delRobotEmbeddedVersion(embeddedId);
            if (upload == 1) {
                dataModel.setData("");
                dataModel.setMsg("嵌入式软件删除成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else if (upload == 0) {
                dataModel.setData("");
                dataModel.setMsg("嵌入式软件删除失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("addRobotEmbeddedVersion",e);
            dataModel.setData("");
            dataModel.setMsg("嵌入式软件删除失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
        return dataModel;
    }

    /**
     * @Author 郭凯
     * @Description 嵌入式软件修改
     * @Date 16:23 2020/9/8
     * @Param [file, sysRobotEmbeddedVersion]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editRobotEmbeddedVersion")
    @ResponseBody
    public ReturnModel editRobotEmbeddedVersion(MultipartFile file, SysRobotEmbeddedVersion sysRobotEmbeddedVersion) {
        ReturnModel dataModel = new ReturnModel();
        try {
            int upload = sysRobotEmbeddedVersionService.editRobotEmbeddedVersion(sysRobotEmbeddedVersion,file);
            if (upload == 1) {
                dataModel.setData("");
                dataModel.setMsg("嵌入式软件修改成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }
            if (upload == 2) {
                dataModel.setData("");
                dataModel.setMsg("文件上传格式不正确！(支持:bin格式)");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
            if (upload == 0) {
                dataModel.setData("");
                dataModel.setMsg("嵌入式软件修改失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("editRobotEmbeddedVersion",e);
            dataModel.setData("");
            dataModel.setMsg("嵌入式软件修改失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
        return dataModel;
    }

    /**
     * @Author 郭凯
     * @Description 下载文件
     * @Date 16:53 2020/9/8
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
        File file = new File(getEmbeddedPaht  + result);
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
            out.println("window.location.href='/page/system/robotEmbeddedVersion/html/robotEmbeddedVersionList.html';");
            out.println("</script>");
        }
    }

}
