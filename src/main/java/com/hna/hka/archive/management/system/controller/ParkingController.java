package com.hna.hka.archive.management.system.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.service.SysScenicSpotParkingService;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: ParkingController
 * @Author: 郭凯
 * @Description: 停放点管理控制层
 * @Date: 2020/6/12 14:20
 * @Version: 1.0
 */
@Api(tags = "停放点")
@RequestMapping("/system/parking")
@Controller
public class    ParkingController extends PublicUtil {

    @Autowired
    private SysScenicSpotParkingService sysScenicSpotParkingService;

    @Autowired
    private HttpSession session;


    /**
     * @Author 郭凯
     * @Description 停放点管理列表查询
     * @Date 14:26 2020/6/12
     * @Param [pageNum, pageSize, sysScenicSpotParkingWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     **/
    @RequestMapping("/getParkingList")
    @ResponseBody
    public PageDataResult getParkingList(@RequestParam("pageNum") Integer pageNum,
                                       @RequestParam("pageSize") Integer pageSize, SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("parkingName",sysScenicSpotParkingWithBLOBs.getParkingName());
            search.put("parkingScenicSpotId",session.getAttribute("scenicSpotId").toString());
            pageDataResult = sysScenicSpotParkingService.getParkingList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("停放点管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 库房坐标管理列表查询
     * @Date 14:26 2020/6/12
     * @Param [pageNum, pageSize, sysScenicSpotParkingWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     **/
    @RequestMapping("/getParkingCoordList")
    @ResponseBody
    public PageDataResult getParkingCoordList(@RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize, SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("parkingName",sysScenicSpotParkingWithBLOBs.getParkingName());
            search.put("parkingScenicSpotId",session.getAttribute("scenicSpotId").toString());
            pageDataResult = sysScenicSpotParkingService.getParkingCoordList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("库房坐标管理列表查询失败",e);
        }
        return pageDataResult;
    }



    /**
     * @Author 郭凯
     * @Description 添加停放点信息
     * @Date 15:52 2020/6/12
     * @Param [sysScenicSpotParkingWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/addParking")
    @ResponseBody
    public ReturnModel addParking(SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs) {
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysScenicSpotParkingService.addParking(sysScenicSpotParkingWithBLOBs);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("停放点新增成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("停放点新增失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("停放点新增失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("addParking", e);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 添加库房坐标信息
     * @Date 15:52 2020/6/12
     * @Param [sysScenicSpotParkingWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping(value = "/addParkingCoord")
    @ResponseBody
    public ReturnModel addParkingCoord(SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs) {
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysScenicSpotParkingService.addParkingCoord(sysScenicSpotParkingWithBLOBs);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("库房坐标新增成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("库房坐标新增失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("停放点新增失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("addParking", e);
            return dataModel;
        }
    }






    /**
     * @Author 郭凯
     * @Description 修改停放点信息
     * @Date 16:08 2020/6/12
     * @Param [sysScenicSpotParkingWithBLOBs]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/editParking")
    @ResponseBody
    public ReturnModel editParking(SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs) {
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysScenicSpotParkingService.editParking(sysScenicSpotParkingWithBLOBs);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("停放点修改成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("停放点修改失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("停放点修改失败！");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("editParking", e);
            return dataModel;
        }
    }


    /**
     * 修改库房信息
     * @param sysScenicSpotParkingWithBLOBs
     * @return
     */
//    @RequestMapping(value = "/editParking")
//    @ResponseBody
//    public ReturnModel editParkingCoord(SysScenicSpotParkingWithBLOBs sysScenicSpotParkingWithBLOBs) {
//        ReturnModel dataModel = new ReturnModel();
//        try {
//            int i = sysScenicSpotParkingService.editParkingCoord(sysScenicSpotParkingWithBLOBs);
//            if (i == 1) {
//                dataModel.setData("");
//                dataModel.setMsg("库房坐标修改成功！");
//                dataModel.setState(Constant.STATE_SUCCESS);
//                return dataModel;
//            }else{
//                dataModel.setData("");
//                dataModel.setMsg("库房坐标修改失败！");
//                dataModel.setState(Constant.STATE_FAILURE);
//                return dataModel;
//            }
//        } catch (Exception e) {
//            dataModel.setData("");
//            dataModel.setMsg("库房坐标修改失败！");
//            dataModel.setState(Constant.STATE_FAILURE);
//            logger.error("editParkingCoord", e);
//            return dataModel;
//        }
//    }

    /**
     * @Author 郭凯
     * @Description 删除停放点信息
     * @Date 16:14 2020/6/12
     * @Param [parkingId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delParking")
    @ResponseBody
    public ReturnModel delParking(@RequestParam("parkingId") Long parkingId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotParkingService.delParking(parkingId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("停放点删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("停放点删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delParking",e);
            returnModel.setData("");
            returnModel.setMsg("停放点删除失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * 导出停放点
     */
    @ApiModelProperty("导出")
    @RequestMapping(value = "/uploadExcel")
    public void  uploadScenicSpotCapPriceExcel(HttpServletResponse response, SysScenicSpotCapPrice sysScenicSpotCapPrice) throws Exception {
        List<SysScenicSpotParkingWithBLOBs> scenicSpotCapPriceByExample = null;
        Map<String,String> search = new HashMap<>();
        SysUsers user = this.getSysUser();
        search.put("userId",user.getUserId().toString());
        search.put("scenicSpotId",sysScenicSpotCapPrice.getScenicSpotId().toString());
        search.put("scenicSpotName",sysScenicSpotCapPrice.getScenicSpotName());
        scenicSpotCapPriceByExample = sysScenicSpotParkingService.uploadScenicSpotCapPriceExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        //     FileUtil.exportExcel(FileUtil.getWorkbook("封顶价格", "封顶价格", SysScenicSpotCapPriceLog.class, scenicSpotCapPriceLogByExample),"封顶价格"+ dateTime +".xls",response);
        FileUtil.exportExcel(FileUtil.getWorkbook("停放点","停放点",SysScenicSpotParkingWithBLOBs.class,scenicSpotCapPriceByExample),"停放点"+ dateTime +".xls",response);
    }

    //停放点导出
    @RequestMapping("/getParkingExcel")
    @ResponseBody
    private void getParkingExcel(HttpServletResponse response, SysParkingExcel parkingExcel){
        Map<String,Object> search=new HashMap<>();
        search.put("scenicSpotId",session.getAttribute("scenicSpotId"));
        List<SysParkingExcel> parking = sysScenicSpotParkingService.getParkingExcel(search);
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        FileUtil.exportExcel(FileUtil.getWorkbook("停放点","停放点",parkingExcel.getClass(),parking),"停放点列表"+ dateTime +".xls",response);
    }

    //停放点导入
    @RequestMapping("/upParkingExcel")
    @ResponseBody
    public ReturnModel upParkingExcel(@RequestParam("file") MultipartFile multipartFile){
        ReturnModel returnModel=new ReturnModel();
        try {
            ImportParams params=new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            List<SysParkingExcel> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(), SysParkingExcel.class, params);
            for (SysParkingExcel parking:result){
                parking.setParkingScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
                SysParkingExcel sysParkingExcel = sysScenicSpotParkingService.selScenicSpotParking(parking);
                if (ToolUtil.isEmpty(sysParkingExcel)){
                    parking.setCreateDate(DateUtil.currentDateTime());
                    Long seqId = IdUtils.getSeqId();
                    parking.setParkingId(seqId);
                    sysScenicSpotParkingService.addParkingExcel(parking);
                }else {
                    parking.setUpdateDate(DateUtil.currentDateTime());
                    parking.setParkingId(sysParkingExcel.getParkingId());
                    sysScenicSpotParkingService.upParking(parking);
                }
            }
            returnModel.setData("");
            returnModel.setMsg("导入成功");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("导入失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 停放点下拉选
     */
    @RequestMapping("/parkingDropDown")
    @ResponseBody
    public ReturnModel parkingDropDown(Long scenicSpotId) {

        ReturnModel returnModel = new ReturnModel();

        List<SysScenicSpotParking> list = sysScenicSpotParkingService.parkingDropDown(scenicSpotId);

        returnModel.setData(list);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("获取成功");
        return returnModel;
    }



    }
