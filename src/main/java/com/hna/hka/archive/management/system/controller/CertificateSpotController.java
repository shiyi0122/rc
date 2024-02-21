package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysScenicSpotCertificateSpot;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.model.WechatBusinessManagement;
import com.hna.hka.archive.management.system.service.SysManagementService;
import com.hna.hka.archive.management.system.service.SysScenicSpotCertificateSpotService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: CertificateSpotListController
 * @Author: 郭凯
 * @Description: 景区证书管理控制层
 * @Date: 2020/5/28 9:37
 * @Version: 1.0
 */
@RequestMapping("/system/certificateSpot")
@Controller
public class CertificateSpotController extends PublicUtil {

    @Autowired
    private SysScenicSpotCertificateSpotService sysScenicSpotCertificateSpotService;

    @Autowired
    private SysManagementService sysManagementService;

    /**
     * @Author 郭凯
     * @Description 景区证书列表查询
     * @Date 9:44 2020/5/28
     * @Param [pageNum, pageSize]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getCertificateSpotList")
    @ResponseBody
    public PageDataResult getCertificateSpotList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize,SysScenicSpotCertificateSpot sysScenicSpotCertificateSpot) {
        PageDataResult pageDataResult = new PageDataResult();
        SysUsers SysUsers = this.getSysUser();
        Map<String,Object> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("userId",SysUsers.getUserId().toString());
            search.put("scenicSpotId",sysScenicSpotCertificateSpot.getScenicSpotId());
            pageDataResult = sysScenicSpotCertificateSpotService.getCertificateSpotList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("景区证书列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 查询所有证书
     * @Date 14:45 2020/5/28
     * @Param []
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/getCertificate")
    @ResponseBody
    public ReturnModel getCertificate(){
        ReturnModel returnModel = new ReturnModel();
        try {
            List<WechatBusinessManagement> WechatBusinessManagement = sysManagementService.getCertificate();
            if (ToolUtil.isNotEmpty(WechatBusinessManagement)){
                returnModel.setData(WechatBusinessManagement);
                returnModel.setMsg("成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("证书查询失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("getCertificate",e);
            returnModel.setData("");
            returnModel.setMsg("证书查询失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 新增景区证书
     * @Date 14:56 2020/5/28
     * @Param [sysScenicSpotCertificateSpot]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addCertificateSpot")
    @ResponseBody
    public ReturnModel addCertificateSpot(SysScenicSpotCertificateSpot sysScenicSpotCertificateSpot){
        ReturnModel returnModel = new ReturnModel();
        try {
            //查询此景区是否存在证书
            SysScenicSpotCertificateSpot scenicSpotCertificateSpot = sysScenicSpotCertificateSpotService.selectCertificateSpotById(sysScenicSpotCertificateSpot.getScenicSpotId());
            if (ToolUtil.isNotEmpty(scenicSpotCertificateSpot)){
                returnModel.setData("");
                returnModel.setMsg("此景区已有证书！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
            int i = sysScenicSpotCertificateSpotService.addCertificateSpot(sysScenicSpotCertificateSpot);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("景区证书新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("景区证书新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("addCertificateSpot",e);
            returnModel.setData("");
            returnModel.setMsg("景区证书新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 修改景区证书
     * @Date 15:46 2020/5/28
     * @Param [sysScenicSpotCertificateSpot]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editCertificateSpot")
    @ResponseBody
    public ReturnModel editCertificateSpot(SysScenicSpotCertificateSpot sysScenicSpotCertificateSpot){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotCertificateSpotService.editCertificateSpot(sysScenicSpotCertificateSpot);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("景区证书修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("景区证书修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("editCertificateSpot",e);
            returnModel.setData("");
            returnModel.setMsg("景区证书修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 删除景区证书
     * @Date 15:51 2020/5/28
     * @Param [certificateSpotId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/delCertificateSpot")
    @ResponseBody
    public ReturnModel delCertificateSpot(@NotBlank(message = "景区证书ID不能为空")Long certificateSpotId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotCertificateSpotService.delCertificateSpot(certificateSpotId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("景区证书删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("景区证书删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delCertificateSpot",e);
            returnModel.setData("");
            returnModel.setMsg("景区证书删除失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Method uploadExcelCertificateSpot
     * @Author 郭凯
     * @Version  1.0
     * @Description 导出Excel表
     * @Return void
     * @Date 2021/4/7 13:55
     */
    @RequestMapping(value = "/uploadExcelCertificateSpot")
    public void  uploadExcelCertificateSpot(HttpServletResponse response, SysScenicSpotCertificateSpot certificateSpot){
        Map<String,Object> search = new HashMap<>();
        SysUsers SysUsers = this.getSysUser();
        search.put("userId",SysUsers.getUserId().toString());
        search.put("scenicSpotId",certificateSpot.getScenicSpotId());
        List<SysScenicSpotCertificateSpot>  certificateSpotListByExample = sysScenicSpotCertificateSpotService.getUploadExcelCertificateSpot(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("景区证书", "景区证书", SysScenicSpotCertificateSpot.class, certificateSpotListByExample),"景区证书"+ dateTime +".xls",response);
    }

}
