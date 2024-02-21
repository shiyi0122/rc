package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysScenicSpotDateTreasureHunt;
import com.hna.hka.archive.management.system.model.SysScenicSpotTreasureHunt;
import com.hna.hka.archive.management.system.service.SysScenicSpotDateTreasureHuntService;
import com.hna.hka.archive.management.system.service.SysScenicSpotTreasureHuntService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/3/10 9:45
 * 随机宝箱奖品
 *
 */

@RequestMapping("/system/scenicSpotDateTreasureHunt")
@Controller
public class ScenicSpotDateTreasureHuntController {

//    @Autowired
    private SysScenicSpotTreasureHuntService sysScenicSpotTreasureHuntService;

    @Autowired
    private SysScenicSpotDateTreasureHuntService sysScenicSpotDateTreasureHuntService;

    @Autowired
    private HttpSession session;

    @ApiOperation("查询随机寻宝景点奖品列表")
    @ResponseBody
    @RequestMapping("/getDateTreasureHuntList")
    public PageDataResult getTreasureList(@RequestParam("pageNum") Integer pageNum,
                                          @RequestParam("pageSize") Integer pageSize, SysScenicSpotDateTreasureHunt sysScenicSpotDateTreasureHunt) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
//            sysScenicSpotTreasureHunt.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
            search.put("scenicSpotId",session.getAttribute("scenicSpotId").toString());
            search.put("scenicSpotName",sysScenicSpotDateTreasureHunt.getScenicSpotName());
            search.put("treasureName",sysScenicSpotDateTreasureHunt.getTreasureName());
            search.put("randomTime",sysScenicSpotDateTreasureHunt.getRandomTime());
            // search.put("scenicSpotId",sysScenicSpotTreasureHunt.getScenicSpotId().toString());
            pageDataResult = sysScenicSpotDateTreasureHuntService.getDateTreasureList(pageNum,pageSize,search);
        }catch (Exception e){
//            logger.info("景点列表查询失败",e);
            e.printStackTrace();
        }
        return pageDataResult;
    }

    @ApiOperation("添加随机寻宝景点奖品")
    @ResponseBody
    @RequestMapping("/addDateTreasureHunt")
    public ReturnModel addScenicTreasure(@RequestParam("file") MultipartFile file, SysScenicSpotDateTreasureHunt dateTreasureHunt) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotDateTreasureHuntService.addDateTreasure(dateTreasureHunt,file);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点奖品新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if ( i == 3){
                returnModel.setData("");
                returnModel.setMsg("图片大小大于2M！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("寻宝景点奖品新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
//            logger.error("addBroadcast", e);
            e.printStackTrace();
            returnModel.setData("");
            returnModel.setMsg("寻宝景点奖品新增失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    @ApiOperation("修改随机寻宝景点奖品")
    @ResponseBody
    @RequestMapping("/editDateTreasureHunt")
    public ReturnModel editScenicTreasure(@RequestParam("file") MultipartFile file ,SysScenicSpotDateTreasureHunt dateTreasureHunt) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotDateTreasureHuntService.editDateTreasure(dateTreasureHunt,file);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点奖品修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if (i==3){
                returnModel.setData("");
                returnModel.setMsg("图片大小大于2M！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("寻宝景点奖品修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
//            logger.error("editBroadcast", e);
            returnModel.setData("");
            returnModel.setMsg("寻宝景点奖品修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * @Author zhang
     * @Description 删除随机寻宝景点奖品
     * @Param [broadcastId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping("/delDateTreasureHunt")
    @ResponseBody
    public ReturnModel delDateTreasure(@NotBlank(message = "景点ID不能为空")Long treasureId, @NotBlank(message = "景区ID不能为空")Long scenicSpotId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotDateTreasureHuntService.delDateTreasure(treasureId,scenicSpotId);
            if (i > 0){
                returnModel.setData("");
                returnModel.setMsg("寻宝景点奖品删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("寻宝景点奖品删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            returnModel.setData("");
            returnModel.setMsg("寻宝景点奖品删除失败，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }


    /**
     * @Method importExcel
     * @Author zhang
     * @Description 导入寻宝景点奖品Excel表
     * @Return com.hna.hka.archive.management.system.util.ReturnModel
     * @Date 2021/4/2 13:38
     */
//    @RequestMapping("/importExcel")
//    @ResponseBody
//    public ReturnModel importExcel(@RequestParam("file") MultipartFile multipartFile){
//        ReturnModel returnModel = new ReturnModel();
//        try {
//            ImportParams params = new ImportParams();
//            params.setTitleRows(1);
//            params.setHeadRows(1);
//            List<SysScenicSpotBroadcastExtendVo> scenicSpotBroadcast = ExcelImportUtil.importExcel(multipartFile.getInputStream(),SysScenicSpotBroadcastExtendVo.class, params);
//            for (SysScenicSpotBroadcastExtendVo broadcastExtendVo : scenicSpotBroadcast){
//                SysScenicSpotTreasureHunt broadcast = new SysScenicSpotTreasureHunt();
//                broadcast.setScenicSpotId(Long.parseLong(session.getAttribute("scenicSpotId").toString()));
////                broadcast.setBroadcastName(broadcastExtendVo.getBroadcastName());
////                broadcast.setBroadcastGps(broadcastExtendVo.getBroadcastGps());
////                broadcast.setBroadcastGpsBaiDu(broadcastExtendVo.getBroadcastGpsBaiDu());
//                sysScenicSpotTreasureHuntService.addTreasure(broadcast);
//            }
//            returnModel.setData("");
//            returnModel.setMsg("导入成功");
//            returnModel.setState(Constant.STATE_SUCCESS);
//            return returnModel;
//        }catch (Exception e){
//            logger.info("景点奖品列表导入Excel",e);
//            returnModel.setData("");
//            returnModel.setMsg("导入失败！（请联系管理员）");
//            returnModel.setState(Constant.STATE_FAILURE);
//            return returnModel;
//        }
//    }


}
