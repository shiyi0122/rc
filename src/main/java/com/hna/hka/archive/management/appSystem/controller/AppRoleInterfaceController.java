package com.hna.hka.archive.management.appSystem.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hna.hka.archive.management.appSystem.service.AppUserService;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.managerApp.model.SysManagerAppRes;
import com.hna.hka.archive.management.managerApp.service.SysManagerAppResService;
import com.hna.hka.archive.management.system.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.appSystem.controller
 * @ClassName: AppRoleController
 * @Author: 郭凯
 * @Description: APP用户角色管理控制层
 * @Date: 2021/6/7 16:51
 * @Version: 1.0
 */
@RequestMapping("/system/appRoleInterface")
@Controller
@CrossOrigin
public class AppRoleInterfaceController extends PublicUtil {

    @Autowired
    private SysManagerAppResService  sysManagerAppResService;

    @Autowired
    private AppUserService appUserService;

    /**
     * @Method getAppUserResource
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询APP用户拥有的菜单权限
     * @Return java.lang.String
     * @Date 2021/6/7 16:55
     */
    @RequestMapping(value = "/getAppUserResource",method = RequestMethod.POST)
    @ResponseBody
    public String getAppUserResource(String content) {
        ReturnModel dataModel = new ReturnModel();
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
            //景区ID
            String scenicSpotId = jsonobject.getString("scenicSpotId");
            //景区ID不能为空
            if (ToolUtil.isEmpty(scenicSpotId)) {
                dataModel.setData("");
                dataModel.setMsg("景区ID为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //用户唯一标识
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            Map<String , Object> search = new HashMap<>();
            search.put("userId",appUsers.getUserId());
            search.put("scenicSpotId",scenicSpotId);
            //查询用户菜单
            List<SysManagerAppRes> managerAppResList = sysManagerAppResService.getAppUserResource(search);
            if(ToolUtil.isNotEmpty(managerAppResList)){
                dataModel.setData(getMenuTreeList(managerAppResList,"APP_SYSTEM"));
                dataModel.setMsg("成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }else{
                dataModel.setData("");
                dataModel.setMsg("查询菜单为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
        }catch (Exception e){
            dataModel.setData("");
            dataModel.setMsg("系统错误！");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * @Method
     * @Author 郭凯
     * @Version  1.0
     * @Description 递归循环遍历菜单栏
     * @Return
     * @Date 2021/6/7 17:09
     */
    private List<SysManagerAppRes> getMenuTreeList(List<SysManagerAppRes> menuList, String pid) {
        // 查找所有菜单
        List<SysManagerAppRes> childrenList = new ArrayList<>();
        menuList.forEach(menu -> {
            if (Objects.equals(pid, menu.getResPcode())) {
                menu.setChild(getMenuTreeList(menuList, menu.getResCode()));
                childrenList.add(menu);
            }
        });
        return childrenList;
    }

    /**
     * @Method getAppUserRole
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询遍历用户所拥有的操作按钮权限
     * @Return java.lang.String
     * @Date 2021/6/8 10:37
     */
    @RequestMapping(value = "/getAppUserPermissions",method = RequestMethod.POST)
    @ResponseBody
    public String getAppUserPermissions(String content) {
        ReturnModel dataModel = new ReturnModel();
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
            //景区ID
            String scenicSpotId = jsonobject.getString("scenicSpotId");
            //景区ID不能为空
            if (ToolUtil.isEmpty(scenicSpotId)) {
                dataModel.setData("");
                dataModel.setMsg("景区ID为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //用户唯一标识
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (ToolUtil.isEmpty(longinTokenId)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (ToolUtil.isEmpty(appUsers)) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            Map<String , Object> search = new HashMap<>();
            search.put("userId",appUsers.getUserId());
            search.put("scenicSpotId",scenicSpotId);
            //查询用户角色
            Set<String> permissionsSet = sysManagerAppResService.getAppUserPermissions(search);
            if(ToolUtil.isNotEmpty(permissionsSet)){
                dataModel.setData(permissionsSet);
                dataModel.setMsg("成功！");
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }else{
                dataModel.setData("");
                dataModel.setMsg("查询按钮权限为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
        }catch (Exception e){
            dataModel.setData("");
            dataModel.setMsg("系统错误！");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

}
