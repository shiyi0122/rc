package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.WechatBusinessManagement;
import com.hna.hka.archive.management.system.service.SysManagementService;
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

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: ManagementController
 * @Author: 郭凯
 * @Description: 证书管理控制层
 * @Date: 2020/5/22 16:47
 * @Version: 1.0
 */
@RequestMapping("/system/management")
@Controller
public class ManagementController extends PublicUtil {

    @Autowired
    private SysManagementService sysManagementService;

    /**
     * @Author 郭凯
     * @Description 证书列表查询
     * @Date 17:03 2020/5/22
     * @Param [pageNum, pageSize, wechatBusinessManagement]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getManagementList")
    @ResponseBody
    public PageDataResult getManagementList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize, WechatBusinessManagement wechatBusinessManagement) {
        PageDataResult pageDataResult = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysManagementService.getManagementList(pageNum,pageSize,wechatBusinessManagement);
        }catch (Exception e){
            logger.info("证书列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 新增证书
     * @Date 17:51 2020/9/11
     * @Param [file, wechatBusinessManagement]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/addManagement")
    @ResponseBody
    public ReturnModel addManagement(@RequestParam("file") MultipartFile file, WechatBusinessManagement wechatBusinessManagement) {
        ReturnModel dataModel = new ReturnModel();
        try {
            if(!file.isEmpty()){
                int a = sysManagementService.addManagement(wechatBusinessManagement,file);
                if (a == 1) {
                    dataModel.setData("");
                    dataModel.setMsg("证书新增成功！");
                    dataModel.setState(Constant.STATE_SUCCESS);
                    return dataModel;
                }
                if (a == 2) {
                    dataModel.setData("");
                    dataModel.setMsg("证书格式不正确！（只支持p12文件）");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
                if (a == 0) {
                    dataModel.setData("");
                    dataModel.setMsg("证书新增失败！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
                if (a == 3) {
                    dataModel.setData("");
                    dataModel.setMsg("此证书已存在，请修改证书名称！");
                    dataModel.setState(Constant.STATE_FAILURE);
                    return dataModel;
                }
            }else{
                dataModel.setData("");
                dataModel.setMsg("请选择要上传的证书！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("证书新增失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("addAdvertising", e);
            return dataModel;
        }
        return dataModel;
    }

    /**
     * @Author 郭凯
     * @Description 删除证书
     * @Date 9:39 2020/9/14
     * @Param [merchantId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delManagement")
    @ResponseBody
    public ReturnModel delManagement(@RequestParam("merchantId") Long merchantId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysManagementService.delManagement(merchantId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("证书删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("证书删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delManagement",e);
            returnModel.setData("");
            returnModel.setMsg("证书删除失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

}
