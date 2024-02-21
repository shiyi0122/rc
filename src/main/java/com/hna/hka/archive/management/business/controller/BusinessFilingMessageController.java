package com.hna.hka.archive.management.business.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.google.inject.internal.cglib.core.$DuplicatesPredicate;
import com.hna.hka.archive.management.assetsSystem.model.GoodsStock;
import com.hna.hka.archive.management.business.model.*;
import com.hna.hka.archive.management.business.service.BusinessFilingMessageService;
import com.hna.hka.archive.management.business.service.BusinessScenicSpotExpandService;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/9/21 15:43
 */

@Api(tags = "景区报备相关接口")
@RequestMapping("/business/filingMessage")
@Controller
public class BusinessFilingMessageController extends PublicUtil {

    @Autowired
    BusinessFilingMessageService businessFilingMessageService;

    @Autowired
    BusinessScenicSpotExpandService businessScenicSpotExpandService;


    @Autowired
    private HttpServletRequest request;

    /**
     * @Author
     * @Description 报备景区管理列表查询
     * @Date 13:55 2020/8/13
     * @Param [pageNum, pageSize, businessOrder]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     **/
    @RequestMapping("/getFilingMessageList")
    @ResponseBody
    public PageDataResult getFilingMessageList(@RequestParam("pageNum") Integer pageNum,
                                               @RequestParam("pageSize") Integer pageSize, BusinessFilingMessage businessFilingMessage) {

        PageDataResult pageDataResult = new PageDataResult();
        Map<String, Object> search = new HashMap<>();
        if (!StringUtils.isEmpty(businessFilingMessage.getFilingName())){
            search.put("filingName",businessFilingMessage.getFilingName());
        }
        if (!StringUtils.isEmpty(businessFilingMessage.getFilingPhone())){
            search.put("filingPhone",businessFilingMessage.getFilingPhone());
        }
        if (!(StringUtils.isEmpty(businessFilingMessage.getFindingsOfAudit()))){
            search.put("findingsOfAudit",businessFilingMessage.getFindingsOfAudit());
        }
        pageDataResult = businessFilingMessageService.getFilingMessageList(pageNum,pageSize,search);

        return pageDataResult;
    }


    /**
     * @Author
     * @Description 报备景区管理导出
     * @Date 13:55 2020/8/13
     * @Param [pageNum, pageSize, businessOrder]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     **/
    @RequestMapping(value = "/uploadExcelFilingMessage")
    public void  uploadExcelFilingMessage(HttpServletResponse response, BusinessFilingMessage businessFilingMessage) throws Exception {
        Map<String,Object> search = new HashMap<>();
        search.put("filingName",businessFilingMessage.getFilingName());
        search.put("filingPhone", businessFilingMessage.getFilingPhone());
        search.put("findingsOfAudit",businessFilingMessage.getFindingsOfAudit());
        List<BusinessFilingMessage> list = businessFilingMessageService.uploadExcelFilingMessage(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("景区报备", "景区报备", BusinessFilingMessage.class, list),"景区报备"+ dateTime +".xls",response);
    }

    /**
     * @Author
     * @Description 报备景区审核列表导出
     * @Date 13:55 2020/8/13
     * @Param [pageNum, pageSize, businessOrder]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     **/
    @RequestMapping(value = "/uploadExcelFilingMessageAuditLog")
    public void  uploadExcelFilingMessageAuditLog(HttpServletResponse response, BusinessFilingMessage businessFilingMessage) throws Exception {
        Map<String,Object> search = new HashMap<>();
        search.put("messageId",businessFilingMessage.getId());
        List<BusinessFilingMessageLog> list = businessFilingMessageService.uploadExcelFilingMessageAuditLog(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("景区审核", "景区审核", BusinessFilingMessageLog.class, list),"景区审核"+ dateTime +".xls",response);
    }

    /**
     * 审核报备景区状态
     * @param businessFilingMessage
     * @return
     */
    @RequestMapping("/editFilingMessageResult")
    @ResponseBody
    public ReturnModel editFilingMessageResult(BusinessFilingMessage businessFilingMessage) {

        ReturnModel returnModel = new ReturnModel();

        SysUsers sysUser = this.getSysUser();
        String userName = sysUser.getUserName();
        Long userId = sysUser.getUserId();
        businessFilingMessage.setApprovalId(userId.toString());
        businessFilingMessage.setApprovalName(userName);
        int i = businessFilingMessageService.editFilingMessageResult(businessFilingMessage);

        if (i>0){
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setMsg("修改成功");
        }else{
            returnModel.setData(i);
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setMsg("修改失败");
        }
        return returnModel;
    }


    /**
     * 添加景区报备相关信息
     */
    @RequestMapping("/addFilingMessage")
    @ResponseBody
    public ReturnModel addFilingMessage(BusinessFilingMessage businessFilingMessage) {

        ReturnModel returnModel =  new ReturnModel();

        String mergerName= "";
        if (ToolUtil.isNotEmpty(businessFilingMessage.getProvince())) {
            //根据ID查询地区名称
            BusinessWorldArea worldArea = businessScenicSpotExpandService.selectProvinceId(businessFilingMessage.getProvince());
            mergerName += worldArea.getName();
        }
        if (ToolUtil.isNotEmpty(businessFilingMessage.getCity())) {
            //根据ID查询地区名称
            BusinessWorldArea worldArea = businessScenicSpotExpandService.selectProvinceId(businessFilingMessage.getCity());
            mergerName +="-"+ worldArea.getName();
        }
        if (ToolUtil.isNotEmpty(businessFilingMessage.getCounty())) {
            //根据ID查询地区名称
            BusinessWorldArea worldArea = businessScenicSpotExpandService.selectProvinceId(businessFilingMessage.getCounty());
            mergerName +="-"+ worldArea.getName();
        }

        businessFilingMessage.setFilingRegion(mergerName);
        int i = businessFilingMessageService.addFilingMessage(businessFilingMessage);

        if (i > 0) {
            returnModel.setState(Constant.STATE_SUCCESS);
            returnModel.setData(i);
            returnModel.setMsg("添加成功！");
        }else{
            returnModel.setState(Constant.STATE_FAILURE);
            returnModel.setData(i);
            returnModel.setMsg("添加失败！");
        }
        return returnModel;
    }

    /**
     * @Author
     * @Description 查询审核日志
     * @Date 13:55 2020/8/13
     * @Param [pageNum, pageSize, businessOrder]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     **/
    @RequestMapping("/getFilingMessageLogList")
    @ResponseBody
    public PageDataResult getFilingMessageLogList(@RequestParam("pageNum") Integer pageNum,
                                               @RequestParam("pageSize") Integer pageSize, BusinessFilingMessage businessFilingMessage) {
         PageDataResult pageDataResult =  businessFilingMessageService.getFilingMessageLogList(pageNum,pageSize,businessFilingMessage.getId());

      return pageDataResult;

    }


    @ApiOperation("导入报备景区")
    @RequestMapping("/importExcel")
    @ResponseBody
    public ReturnModel importExcelEnter(@RequestPart("file") MultipartFile multipartFile) throws Exception {
        ReturnModel returnModel = new ReturnModel();

        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            List<BusinessFilingMessage> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(),BusinessFilingMessage.class, params);
            for (BusinessFilingMessage businessFilingMessage:result){

                businessFilingMessageService.importExcelEnter(businessFilingMessage);
            }
            returnModel.setData("");
            returnModel.setMsg("导入成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            logger.info("importExcel", e);
            returnModel.setData("");
            returnModel.setMsg("导入失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }



}
