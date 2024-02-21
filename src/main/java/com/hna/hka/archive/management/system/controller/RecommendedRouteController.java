package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysScenicSpotRecommendedRoute;
import com.hna.hka.archive.management.system.service.SysScenicSpotRecommendedRouteService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: RecommendedRouteController
 * @Author: 郭凯
 * @Description: 经典路线控制层
 * @Date: 2020/6/22 9:34
 * @Version: 1.0
 */
@RequestMapping("/system/recommendedRoute")
@Controller
public class RecommendedRouteController extends PublicUtil {

    @Autowired
    private HttpSession session;

    @Autowired
    private SysScenicSpotRecommendedRouteService sysScenicSpotRecommendedRouteService;

    /**
     * @Author 郭凯
     * @Description 经典路线列表查询
     * @Date 9:38 2020/6/22
     * @Param [pageNum, pageSize, sysScenicSpotRecommendedRoute]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getRecommendedRouteList")
    @ResponseBody
    public PageDataResult getRecommendedRouteList(@RequestParam("pageNum") Integer pageNum,
                                               @RequestParam("pageSize") Integer pageSize, SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute) {
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
            search.put("routeName",sysScenicSpotRecommendedRoute.getRouteName());
            pageDataResult = sysScenicSpotRecommendedRouteService.getRecommendedRouteList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("经典路线列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 经典路线新增
     * @Date 10:24 2020/6/22
     * @Param [sysScenicSpotRecommendedRoute]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
     **/
    @RequestMapping(value = "/addRecommendedRoute")
    @ResponseBody
    public ReturnModel addRecommendedRoute(SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute) {
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysScenicSpotRecommendedRouteService.addRecommendedRoute(sysScenicSpotRecommendedRoute);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("经典路线新增成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("经典路线新增失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("经典路线新增失败！（请联系管理员）");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("addRecommendedRoute", e);
            return dataModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 删除景点路线
     * @Date 10:30 2020/6/22
     * @Param [routeId]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delRecommendedRoute")
    @ResponseBody
    public ReturnModel delRecommendedRoute(@RequestParam("routeId") Long routeId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysScenicSpotRecommendedRouteService.delRecommendedRoute(routeId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("经典路线删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("经典路线删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
            logger.info("delRecommendedRoute",e);
            returnModel.setData("");
            returnModel.setMsg("经典路线删除失败！(请联系管理员)");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 修改经典路线
     * @Date 10:37 2020/6/22
     * @Param [sysScenicSpotRecommendedRoute]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping(value = "/editRecommendedRoute")
    @ResponseBody
    public ReturnModel editRecommendedRoute(SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute) {
        ReturnModel dataModel = new ReturnModel();
        try {
            int i = sysScenicSpotRecommendedRouteService.editRecommendedRoute(sysScenicSpotRecommendedRoute);
            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("经典路线修改成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                return dataModel;
            }else{
                dataModel.setData("");
                dataModel.setMsg("经典路线修改失败！");
                dataModel.setState(Constant.STATE_FAILURE);
                return dataModel;
            }
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("经典路线修改失败！（请联系管理员）");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("editRecommendedRoute", e);
            return dataModel;
        }
    }

}