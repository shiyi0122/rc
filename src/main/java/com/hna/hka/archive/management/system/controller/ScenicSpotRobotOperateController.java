package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysScenicSpotRobotOperate;
import com.hna.hka.archive.management.system.service.SysScenicSpotRobotOperateService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.ldap.PagedResultsControl;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @Author zhang
 * @Date 2022/2/11 15:26
 * 机器人运营日志管理
 *
 */

@RequestMapping("/system/scenicSpotRobotOperate")
@Controller
public class ScenicSpotRobotOperateController extends PublicUtil {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    SysScenicSpotRobotOperateService sysScenicSpotRobotOperateService;

    @RequestMapping(value = "/getSpotRobotOperateList")
    @ResponseBody()
    public PageDataResult getSpotRobotOperateList(@RequestParam("pageNum") Integer pageNum,
                                                  @RequestParam("pageSize") Integer pageSize, SysScenicSpotRobotOperate sysScenicSpotRobotOperate){


        PageDataResult pageDataResult = new PageDataResult();
        HashMap<String, Object> search = new HashMap<>();

        try{

            if (null == pageNum){
                pageNum = 1;
            }
            if (null == pageSize){
                pageSize = 10;
            }
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            search.put("startTime",startTime);
            search.put("endTime",endTime);
            search.put("scenicSpotId",sysScenicSpotRobotOperate.getScenicSpotId());
            if (ToolUtil.isEmpty(startTime) && ToolUtil.isEmpty(endTime)){
                search.put("time", DateUtil.crutDate());
            }

           pageDataResult =  sysScenicSpotRobotOperateService.getSpotRobotOperateList(pageNum,pageSize,search);

        }catch (Exception e){
            e.printStackTrace();
            logger.error("景区封顶价格修改日志列表查询异常！", e);
        }
        return pageDataResult;
    }




}
