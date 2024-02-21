package com.hna.hka.archive.management.assetsSystem.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.hna.hka.archive.management.assetsSystem.model.SysAssetsRobotExcel;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotBelarcAdvisor;
import com.hna.hka.archive.management.system.model.SysRobot;
import com.hna.hka.archive.management.system.model.SysRobotExcel;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.service.SysRobotService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.controller
 * @ClassName: AssetsRobotController
 * @Author: 郭凯
 * @Description: 机器人资产管理控制层
 * @Date: 2021/5/26 15:03
 * @Version: 1.0
 */
@RequestMapping("/system/assetsRobot")
@Controller
public class AssetsRobotController extends PublicUtil {

    @Autowired
    private SysRobotService sysRobotService;

    /**
     * @Method getRobotOperatingInformationList
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人运营信息列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/5/26 15:20
     */
    @RequestMapping("/getRobotOperatingInformationList")
    @ResponseBody
    public PageDataResult getRobotOperatingInformationList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize, SysRobot sysRobot) {
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
            search.put("scenicSpotId",sysRobot.getScenicSpotId());
            search.put("robotCode",sysRobot.getRobotCode());
            search.put("robotCodeSim",sysRobot.getRobotCodeSim());
            pageDataResult = sysRobotService.getRobotOperatingInformationList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("机器人运营信息列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Method uploadExcelRobot
     * @Author 郭凯
     * @Version  1.0
     * @Description 导出机器人运营信息Excel表
     * @Return void
     * @Date 2021/5/27 14:31
     */
    @RequestMapping(value = "/uploadExcelAssetsRobot")
    public void  uploadExcelAssetsRobot(HttpServletResponse response, @ModelAttribute SysRobot SysRobot) throws Exception {
        List<SysAssetsRobotExcel> assetsRobotExcelList = null;
        SysUsers SysUsers = this.getSysUser();
        Map<String,Object> search = new HashMap<>();
        search.put("userId",SysUsers.getUserId().toString());
        search.put("scenicSpotId",SysRobot.getScenicSpotId());
        search.put("robotCode",SysRobot.getRobotCode());
        assetsRobotExcelList = sysRobotService.getAssetsRobotExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("机器人运营信息", "机器人运营信息", SysAssetsRobotExcel.class, assetsRobotExcelList),"机器人运营信息"+ dateTime +".xls",response);
    }

    /**
     * @Method upload
     * @Author 郭凯
     * @Version  1.0
     * @Description 导入Excel表
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/7/5 20:05
     */
    @RequestMapping("/upload")
    @ResponseBody
    public ReturnModel upload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        ReturnModel returnModel = new ReturnModel();
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            List<SysRobot> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(),SysRobot.class, params);
            for (SysRobot robot:result){
                //查询机器人是否存在软硬件信息
                SysRobot sysRobot = sysRobotService.getRobotCodeBy(robot.getRobotCode());
                sysRobot.setRobotModel(robot.getRobotModel());
                sysRobot.setUpdateDate(DateUtil.currentDateTime());
                sysRobotService.updateSysRobot(sysRobot);
            }
            returnModel.setData("");
            returnModel.setMsg("导入成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            logger.info("upload", e);
            returnModel.setData("");
            returnModel.setMsg("导入失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


}
