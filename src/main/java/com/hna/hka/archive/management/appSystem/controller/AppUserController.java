package com.hna.hka.archive.management.appSystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hna.hka.archive.management.appSystem.model.AppUser;
import com.hna.hka.archive.management.appSystem.service.UserService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/appSystem/user")
public class AppUserController extends PublicUtil {

    @Autowired
    private UserService userService;

    @RequestMapping("/upUser")
    public String upUser(String content){
        ReturnModel returnModel=new ReturnModel();
        try {
            AppUser appUser=new AppUser();
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            appUser.setLoginName(jsonobject.getString("loginName"));
            appUser.setPassword(jsonobject.getString("password"));
            appUser.setNewPassword(jsonobject.getString("newPassword"));
            appUser.setUserId(jsonobject.getString("userId"));
            int i = userService.upUser(appUser);
            if (i>0){
                returnModel.setData("");
                returnModel.setMsg("修改成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
            }else if (i==-1){
                returnModel.setData("");
                returnModel.setMsg("密码错误");
                returnModel.setState(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
            }else if (i==0){
                returnModel.setData("");
                returnModel.setMsg("新密码不能与原密码相同");
                returnModel.setState(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
            }else {
                returnModel.setData("");
                returnModel.setMsg("修改失败");
                returnModel.setState(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(returnModel);//对象转JSON
                return AES.encode(model);
            }
        }catch (AuthenticationException e){
            returnModel.setData("");
            returnModel.setMsg("未知原因，修改失败!");
            returnModel.setState(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(returnModel);//对象转JSON
            return AES.encode(model);//加密返回
        }

    }
}
