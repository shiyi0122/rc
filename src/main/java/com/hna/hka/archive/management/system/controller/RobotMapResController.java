package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysRobotMapRes;
import com.hna.hka.archive.management.system.service.SysRobotMapResService;
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
 * @ClassName: RobotMapResController
 * @Author: 郭凯
 * @Description: 地图资源管理控制层
 * @Date: 2020/11/16 15:55
 * @Version: 1.0
 */
@RequestMapping("/system/robotMapRes")
@Controller
public class RobotMapResController extends PublicUtil {

    @Autowired
    private HttpSession session;

    @Autowired
    private SysRobotMapResService sysRobotMapResService;

    @Autowired
    private HttpServletRequest request;

    @Value("${GET_MAP_PAHT}")
    private String GET_MAP_PAHT;

    /**
     * @Author 郭凯
     * @Description 地图资源管理列表查询
     * @Date 15:58 2020/11/16
     * @Param [pageNum, pageSize, sysRobotMapRes]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getRobotMapResList")
    @ResponseBody
    public PageDataResult getRobotMapResList(@RequestParam("pageNum") Integer pageNum,
                                                      @RequestParam("pageSize") Integer pageSize, SysRobotMapRes sysRobotMapRes) {
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
            pageDataResult = sysRobotMapResService.getRobotMapResList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("地图资源管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 新增地图资源
     * @Date 16:31 2020/11/16
     * @Param [file, sysRobotMapRes]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addRobotMapRes")
    @ResponseBody
    public ReturnModel addRobotMapRes(@RequestParam("file") MultipartFile file , SysRobotMapRes sysRobotMapRes){
        ReturnModel dataModel = new ReturnModel();
        try {
            if (!file.isEmpty()){
                sysRobotMapRes.setResScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
                int i = sysRobotMapResService.addRobotMapRes(file,sysRobotMapRes);
                if (i == 1){
                    dataModel.setData("");
                    dataModel.setMsg("地图资源新增成功！");
                    dataModel.setState(Constant.STATE_SUCCESS);
                    return dataModel;
                }else if(i == 2){
                    dataModel.setData("");
                    dataModel.setMsg("文件上传格式不正确！(支持:zip格式)");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }else{
                    dataModel.setData("");
                    dataModel.setMsg("地图资源新增失败！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
            }else{
                dataModel.setData("");
                dataModel.setMsg("请上传文件！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("addRobotMapRes",e);
            dataModel.setData("");
            dataModel.setMsg("地图资源新增失败！(请联系管理员)");
            dataModel.setState(Constant.STATE_FAILURE);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 下载地图资源
     * @Date 10:43 2020/11/25
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
        File file = new File(GET_MAP_PAHT  + result);
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
            out.println("window.location.href='/page/system/robotMapRes/html/robotMapResList.html';");
            out.println("</script>");
        }
    }

    /**
     * @Author 郭凯
     * @Description 删除地图资源
     * @Date 17:09 2020/11/16
     * @Param [resId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delRobotMapRes")
    @ResponseBody
    public ReturnModel delRobotMapRes(@RequestParam("resId") Long resId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotMapResService.delRobotMapRes(resId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("地图资源删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("地图资源删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delRobotMapRes",e);
            returnModel.setData("");
            returnModel.setMsg("地图资源删除失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 修改启用禁用状态
     * @Date 17:18 2020/11/16
     * @Param [sysRobotMapRes]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editResType")
    @ResponseBody
    public ReturnModel editResType(SysRobotMapRes sysRobotMapRes){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysRobotMapResService.editResType(sysRobotMapRes);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("地图资源状态修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("地图资源状态修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delRobotMapRes",e);
            returnModel.setData("");
            returnModel.setMsg("地图资源状态修改失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

}
