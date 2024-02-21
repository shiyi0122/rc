package com.hna.hka.archive.management.appSystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hna.hka.archive.management.appSystem.model.SysAppLoginLog;
import com.hna.hka.archive.management.appSystem.service.AppUserService;
import com.hna.hka.archive.management.appSystem.service.SysAppLoginLogService;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.system.model.SysRobot;
import com.hna.hka.archive.management.system.service.SysOrderService;
import com.hna.hka.archive.management.system.service.SysRobotService;
import com.hna.hka.archive.management.system.util.*;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author zhang
 * @Date 2022/5/9 17:06
 */


@RequestMapping("/system/appRobotAbnormal")
@Controller
@CrossOrigin
public class AppRobotAbnormalController extends PublicUtil {


    @Autowired
    private HttpSession session;

    @Autowired
    private SysRobotService sysRobotService;

    @Autowired
    private SysOrderService sysOrderService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private SysAppLoginLogService sysAppLoginLogService;


    /**
     * 查看异常机器人列表
     * @param content
     * @param
     * @return
     */

    @RequestMapping(value = "/appRestrictedAretList.do", method = RequestMethod.POST)
    @ResponseBody
    public String appRestrictedAretList(BaseQueryVo BaseQueryVo ,String content) {
        ReturnModel dataModel = new ReturnModel();

        // 根据用户名查询当前登录用户
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData("");
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);

            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            Integer pageNum = BaseQueryVo.getPageNum();
            if (pageNum == 0) {
                dataModel.setData("");
                dataModel.setMsg("pageNum为空，请传入pageSize!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            Integer pageSize = BaseQueryVo.getPageSize();
            if (pageSize == 0) {
                dataModel.setData("");
                dataModel.setMsg("pageSize为空，请传入pageSize!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }



            //景区ID
            String scenicSpotId = jsonobject.getString("scenicSpotId");
            String robotCode = jsonobject.getString("robotCode");
            HashMap<String, Object> search = new HashMap<>();
            search.put("scenicSpotId",scenicSpotId);
            search.put("robotCode",robotCode);


            PageDataResult restrictedAretList = sysRobotService.getRestrictedAretList(pageNum,pageSize,search);
            List<SysRobot> list = (List<SysRobot>)restrictedAretList.getList();

            dataModel.setData(list);
            dataModel.setMsg("异常机器人列表查询成功!");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JSON.toJSONString(dataModel, SerializerFeature.WriteNullStringAsEmpty);
            return AES.encode(model);//加密返回
        } catch (AuthenticationException ae) {
            dataModel.setData("");
            dataModel.setMsg("未知原因,查询失败失败!");
            dataModel.setState(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }



    /**
     * 	APP用户登录日志（新增）
     * @Title: logUserLoginInfo
     * @date:   2019年11月20日 下午3:37:09
     * @author: 郭凯
     * @param: @param loginStatus
     * @param: @param loginName
     * @return: void
     * @throws
     */
    private void logUserLoginInfo(String loginStatus, String loginName){
        SysAppLoginLog sysLoginLog = new SysAppLoginLog();
        sysLoginLog.setLoginLogId(IdUtils.getSeqId());
        sysLoginLog.setOperationTime(new Date());
        sysLoginLog.setOperationPeople(loginName);
        sysLoginLog.setOperationAction(loginStatus);
        sysAppLoginLogService.insertSysAppLoginLog(sysLoginLog);
    }




}
