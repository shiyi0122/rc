package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysScenicSpotBinding;
import com.hna.hka.archive.management.system.service.SysScenicSpotBindingService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: ScenicSpotBindingController
 * @Author: 郭凯
 * @Description: 景区归属地控制层
 * @Date: 2020/5/23 23:59
 * @Version: 1.0
 */
@RequestMapping("/system/scenicSpotBinding")
@Controller
public class ScenicSpotBindingController extends PublicUtil {

    @Autowired
    private SysScenicSpotBindingService sysScenicSpotBindingService;

    /**
     * @Author 郭凯
     * @Description 景区归属地列表查询
     * @Date 0:03 2020/5/24
     * @Param [pageNum, pageSize, sysScenicSpotBinding]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping(value = "/getScenicSpotBindingList", method = RequestMethod.GET)
    @ResponseBody
    public PageDataResult getScenicSpotBindingList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize, SysScenicSpotBinding sysScenicSpotBinding) {
        PageDataResult pageDataResult = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysScenicSpotBindingService.getScenicSpotBindingList(pageNum,pageSize,sysScenicSpotBinding);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("归属地列表查询异常！", e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 新增景区归属地(省)
     * @Date 0:19 2020/5/24
     * @Param [sysScenicSpotBinding]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addScenicSpotBinding")
    @ResponseBody
    public ReturnModel addScenicSpotBinding(@RequestParam("file") MultipartFile file, SysScenicSpotBinding sysScenicSpotBinding){
        ReturnModel returnModel = null;
        try {
            returnModel = new ReturnModel();
            if (!file.isEmpty()){
                int i = sysScenicSpotBindingService.addScenicSpotBinding(sysScenicSpotBinding,file);
                if (i == 1){
                    returnModel.setData("");
                    returnModel.setMsg("景区归属地新增成功");
                    returnModel.setState(Constant.STATE_SUCCESS);
                    return returnModel;
                }else if(i == 2){
                    returnModel.setData("");
                    returnModel.setMsg("景区归属地修改失败");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }else if (i==3){
                    returnModel.setData("");
                    returnModel.setMsg("图片已大于3M,请更换图片");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }else{
                    returnModel.setData("");
                    returnModel.setMsg("图片分辨率不在1120*600,请更换图片");
                    returnModel.setState(Constant.STATE_FAILURE);
                    return returnModel;
                }
            }else{
                returnModel.setData("");
                returnModel.setMsg("请选择要上传的图片！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }

        }catch (Exception e){
            logger.info("addScenicSpotBinding",e);
            returnModel.setData("");
            returnModel.setMsg("景区归属地新增失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }
    /**
     * @Author 郭凯
     * @Description 编辑景区归属地
     * @Date 0:37 2020/5/24
     * @Param [sysScenicSpotBinding]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editScenicSpotBinding")
    @ResponseBody
    public ReturnModel editScenicSpotBinding(@RequestParam("file") MultipartFile file,SysScenicSpotBinding sysScenicSpotBinding){
        ReturnModel returnModel = null;
        try {
            returnModel = new ReturnModel();
            int i = sysScenicSpotBindingService.editScenicSpotBinding(sysScenicSpotBinding,file);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("景区归属地修改成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else if(i == 2){
                returnModel.setData("");
                returnModel.setMsg("景区归属地修改失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else if (i==3){
                returnModel.setData("");
                returnModel.setMsg("图片已大于3M,请更换图片");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("图片分辨率不在1120*600,请更换图片");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("editScenicSpotBinding",e);
            returnModel.setData("");
            returnModel.setMsg("景区归属地修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 删除景区归属地
     * @Date 0:40 2020/5/24
     * @Param [scenicSpotFid]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delScenicSpotBinding")
    @ResponseBody
    public ReturnModel delScenicSpotBinding(@NotBlank(message = "归属地ID不能为空")Long scenicSpotFid){
        ReturnModel returnModel = null;
        try {
            returnModel = new ReturnModel();
            int i = sysScenicSpotBindingService.delScenicSpotBinding(scenicSpotFid);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("景区归属地删除成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("景区归属地删除失败");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delScenicSpotBinding",e);
            returnModel.setData("");
            returnModel.setMsg("景区归属地删除失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }

    }


    /**
     * 归属省下拉选
     * @return
     */
    @RequestMapping(value = "/getSpotBindingProvince")
    @ResponseBody
    public ReturnModel getSpotBindingProvince(String pid) {
        ReturnModel returnModel = new ReturnModel();

        try {
            List<SysScenicSpotBinding> list  = sysScenicSpotBindingService.getSpotBindingProvince(pid);
            returnModel.setData(list);
            returnModel.setMsg("查询成功");
            returnModel.setState(Constant.STATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("归属地列表查询异常！", e);
        }
        return returnModel;
    }


    /**
     * 归属市下拉选
     * @return
     */
    @RequestMapping(value = "/getSpotBindingCity")
    @ResponseBody
    public ReturnModel getSpotBindingCity(String pid) {
        ReturnModel returnModel = new ReturnModel();

        try {
            List<SysScenicSpotBinding> list  = sysScenicSpotBindingService.getSpotBindingCity(pid);
            returnModel.setData(list);
            returnModel.setMsg("查询成功");
            returnModel.setState(Constant.STATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("归属地列表查询异常！", e);
        }
        return returnModel;
    }


    /**
     * 归属区下拉选
     * @return
     */
    @RequestMapping(value = "/getSpotBindingArea")
    @ResponseBody
    public ReturnModel getSpotBindingArea(String pid) {
        ReturnModel returnModel = new ReturnModel();

        try {
            List<SysScenicSpotBinding> list  = sysScenicSpotBindingService.getSpotBindingArea(pid);
            returnModel.setData(list);
            returnModel.setMsg("查询成功");
            returnModel.setState(Constant.STATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("归属地列表查询异常！", e);
        }
        return returnModel;
    }



}
