package com.hna.hka.archive.management.appSystem.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hna.hka.archive.management.appSystem.model.SysAppLoginLog;
import com.hna.hka.archive.management.appSystem.model.SysAppRole;
import com.hna.hka.archive.management.appSystem.service.AppUserService;
import com.hna.hka.archive.management.appSystem.service.AuthorizationService;
import com.hna.hka.archive.management.appSystem.service.SysAppLoginLogService;
import com.hna.hka.archive.management.appSystem.service.SysAppRoleService;
import com.hna.hka.archive.management.managerApp.model.SysAppUsers;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hna.hka.archive.management.system.model.SysResource;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.model.SysScenicSpotBinding;
import com.hna.hka.archive.management.system.service.SysScenicSpotService;

@RequestMapping("/appSystem/login")
@Controller
public class AppLoginController extends PublicUtil {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private SysAppLoginLogService sysAppLoginLogService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private SysAppRoleService sysAppRoleService;

    @Autowired
    private SysScenicSpotService sysScenicSpotService;


    /**
     * 管理者APP登录功能
     *
     * @throws
     * @Title: login
     * @date: 2019年11月20日 下午3:27:35
     * @author: 郭凯
     * @param: @param request
     * @param: @param model
     * @param: @return
     * @return: ReturnDataModel
     */
    @RequestMapping(value = "/apploginSystem.do", method = RequestMethod.POST)
    @ResponseBody
    public String login(String content, HttpServletRequest request) {
        ReturnModel dataModel = new ReturnModel();

        // 根据用户名查询当前登录用户
        SysAppUsers loginUser = null;
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
            String loginName = jsonobject.getString("loginName");
            String loginPassword = jsonobject.getString("loginPassword");
            // 判断数据合法性
            if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(loginPassword)) {
                dataModel.setData("");
                dataModel.setMsg("用户名或者密码为空!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            loginUser = appUserService.getSysUserByLoginName(loginName);
            if (null == loginUser) {
                //记录登录失败日志
                logUserLoginInfo("failed", loginName + "||" + "未知用户");
                dataModel.setData("");
                dataModel.setMsg("此用户不存在，请联系管理员注册！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            if (loginUser.getUserState().equals("90")) {
                dataModel.setData("");
                dataModel.setMsg("用户已被删除,请联系管理员!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            // 用户认证
            PasswordHelper pp = new PasswordHelper();
            String md5Password = pp.getMD5Password(loginPassword, loginUser.getSaltValue());
            if (md5Password.equals(loginUser.getPassword())) {
                //记录登录日志
                logUserLoginInfo("success", loginUser.getLoginName() + "||" + loginUser.getUserName());
                request.getSession().setAttribute("loginName", loginName);
                SysAppUsers sysAppUsers = new SysAppUsers();
                sysAppUsers.setUserId(loginUser.getUserId());
                sysAppUsers.setLonginTokenId(IdUtils.getUUID());
                sysAppUsers.setUpdateDate(DateUtil.currentDateTime());
                appUserService.updateAppUserTokenId(sysAppUsers);
                SysAppUsers appUsers = appUserService.getSysUserByLoginName(loginName);
                dataModel.setData(appUsers);
                dataModel.setMsg("登陆成功!");
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            } else {
                logUserLoginInfo("failed", loginUser.getLoginName() + "||" + loginUser.getUserName());
                dataModel.setData("");
                dataModel.setMsg("用户或者密码错误!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
        } catch (AuthenticationException ae) {
            dataModel.setData("");
            dataModel.setMsg("未知原因，登录失败!");
            dataModel.setState(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }


    /**
     * APP用户登录日志（新增）
     *
     * @throws
     * @Title: logUserLoginInfo
     * @date: 2019年11月20日 下午3:37:09
     * @author: 郭凯
     * @param: @param loginStatus
     * @param: @param loginName
     * @return: void
     */
    private void logUserLoginInfo(String loginStatus, String loginName) {
        SysAppLoginLog sysLoginLog = new SysAppLoginLog();
        sysLoginLog.setLoginLogId(IdUtils.getSeqId());
        sysLoginLog.setOperationTime(new Date());
        sysLoginLog.setOperationPeople(loginName);
        sysLoginLog.setOperationAction(loginStatus);
        sysAppLoginLogService.insertSysAppLoginLog(sysLoginLog);
    }

    /**
     * 登录成功后景区查询
     *
     * @throws
     * @Title: index
     * @date: 2019年11月22日 上午10:51:04
     * @author: 郭凯
     * @param: @param content
     * @param: @return
     * @return: String
     */
    @RequestMapping(value = "/index.do", method = RequestMethod.POST)
    @ResponseBody
    public String index(String content) {
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
            String loginName = jsonobject.getString("loginName");
            //判断loginName是否为空，如果为空，直接return
            if (loginName == "" || loginName == null) {
                dataModel.setData("");
                dataModel.setMsg("loginName为空，请传入loginName!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //判断longinTokenId是否为空，如果为空，直接return
            String longinTokenId = jsonobject.getString("longinTokenId");
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询节点
            List<SysScenicSpotBinding> bindingsList = sysAppLoginLogService.selectbindingsList(loginName);
            if (bindingsList.isEmpty()) {
                dataModel.setData("");
                dataModel.setMsg("当前用户没有可管理景区(请分配景区)!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            dataModel.setData(bindingsList);
            dataModel.setMsg("景区查询成功!");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("E", e);
            dataModel.setData("");
            dataModel.setMsg("景区查询失败!");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }


    /**
     * 用户所拥有的菜单
     *
     * @throws
     * @Title: loginSpotList
     * @date: 2019年11月22日 下午2:41:37
     * @author: 郭凯
     * @param: @param content
     * @param: @return
     * @return: String
     */
    @RequestMapping(value = "/loginSpotList.do", method = RequestMethod.POST)
    @ResponseBody
    public String loginSpotList(String content) {
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
            String loginName = jsonobject.getString("loginName");
            //判断loginName是否为空，如果为空，直接return
            if (loginName == "" || loginName == null) {
                dataModel.setData("");
                dataModel.setMsg("loginName为空，请传入loginName!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
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
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            List<SysResource> menuList = null;
            //查询节点
            if ("3".equals(appUsers.getUserType())) {
                menuList = authorizationService.getMenuByAppLoginName(null);
            } else {
                menuList = authorizationService.getMenuByAppLoginName(loginName);
            }
            if (menuList.isEmpty()) {
                dataModel.setData("");
                dataModel.setMsg("当前用户没有权限(请分配权限)!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            dataModel.setData(menuList);
            dataModel.setMsg("菜单查询成功!");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            // TODO: handle exception
            dataModel.setData("");
            dataModel.setMsg("菜单查询失败!");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * 查询所拥有的权限
     *
     * @throws
     * @Title: loginRoleList
     * @date: 2019年11月22日 下午4:27:52
     * @author: 郭凯
     * @param: @param content
     * @param: @return
     * @return: String
     */
    @RequestMapping(value = "/loginRoleList.do", method = RequestMethod.POST)
    @ResponseBody
    public String loginRoleList(String content) {
        ReturnModel dataModel = new ReturnModel();
        SysAppRole rolesByLoginName = new SysAppRole();
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData(rolesByLoginName);
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            String loginName = jsonobject.getString("loginName");
            //判断loginName是否为空，如果为空，直接return
            if (loginName == "" || loginName == null) {
                dataModel.setData(rolesByLoginName);
                dataModel.setMsg("loginName为空，请传入loginName!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String scenicSpotId = jsonobject.getString("scenicSpotId");
            //判断scenicSpotId是否为空，如果为空，直接return
            if (scenicSpotId == "" || scenicSpotId == null) {
                dataModel.setData(rolesByLoginName);
                dataModel.setMsg("scenicSpotId为空，请传入scenicSpotId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData(rolesByLoginName);
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                dataModel.setData(rolesByLoginName);
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询节点
            Map<String, Object> map = new HashMap<String, Object>();
            if ("3".equals(appUsers.getUserType())) {
                map.put("id", "14036814806551");
            } else {
                map.put("loginName", loginName);
                map.put("scenicSpotId", scenicSpotId);
            }
            rolesByLoginName = sysAppRoleService.getRolesByLoginName(map);
            if (rolesByLoginName == null) {
                dataModel.setData(rolesByLoginName);
                dataModel.setMsg("当前用户没有权限(请分配权限)!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            dataModel.setData(rolesByLoginName);
            dataModel.setMsg("用户权限查询成功!");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            // TODO: handle exception
            dataModel.setData(rolesByLoginName);
            dataModel.setMsg("用户权限查询失败!");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }
    }

    /**
     * 查询景区
     *
     * @throws
     * @Title: loginScenicSpotList
     * @date: 2019年11月22日 下午5:40:03
     * @author: 郭凯
     * @param: @param content
     * @param: @return
     * @return: String
     */
    @RequestMapping(value = "/loginScenicSpotList.do", method = RequestMethod.POST)
    @ResponseBody
    public String loginScenicSpotList(String content) {
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
            String scenicSpotId = jsonobject.getString("scenicSpotId");
            //判断scenicSpotId是否为空，如果为空，直接return
            if (scenicSpotId == "" || scenicSpotId == null) {
                dataModel.setData("");
                dataModel.setMsg("scenicSpotId为空，请传入scenicSpotId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询景区
            SysScenicSpot scenicSpot = sysScenicSpotService.getSysScenicSpotById(Long.parseLong(scenicSpotId));
            dataModel.setData(scenicSpot);
            dataModel.setMsg("景区查询成功!");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            // TODO: handle exception
            dataModel.setData("");
            dataModel.setMsg("景区查询失败!");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }

    }

    /**
     * 查询当前用户所拥有的的权限（增删改查）
     *
     * @throws
     * @Title: roleResourcePrms
     * @date: 2019年11月24日 下午2:36:22
     * @author: 郭凯
     * @param: @param content
     * @param: @return
     * @return: String
     */
    @RequestMapping(value = "/roleResourcePrms.do", method = RequestMethod.POST)
    @ResponseBody
    public String roleResourcePrms(String content) {
        ReturnModel dataModel = new ReturnModel();
        List<SysResource> sysResourceList = null;
        try {
            if (content == null || "".equals(content)) {
                dataModel.setData(sysResourceList);
                dataModel.setMsg("加密参数不能为空！");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            String decode = AES.decode(content);//参数加密
            //解密后进行JSON解析
            JSONObject jsonobject = JSON.parseObject(decode);
            //用户名称
            String loginName = jsonobject.getString("loginName");
            //判断loginName是否为空，如果为空，直接return
            if (loginName == "" || loginName == null) {
                dataModel.setData(sysResourceList);
                dataModel.setMsg("loginName为空，请传入loginName!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData(sysResourceList);
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //资源ID
            String resId = jsonobject.getString("resId");
            //判断resId是否为空，如果为空，直接return
            if (resId == "" || resId == null) {
                dataModel.setData(sysResourceList);
                dataModel.setMsg("resId为空，请传入resId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //权限ID
            String roleId = jsonobject.getString("roleId");
            //判断roleId是否为空，如果为空，直接return
            if (roleId == "" || roleId == null) {
                dataModel.setData(sysResourceList);
                dataModel.setMsg("roleId为空，请传入roleId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                dataModel.setData(sysResourceList);
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询当前用户所拥有的的权限（增删改查）
            if ("3".equals(appUsers.getUserType())) {
                loginName = null;
                resId = null;
                roleId = null;
            }
            sysResourceList = authorizationService.selectroleResourcePrms(loginName, resId, roleId);
            dataModel.setData(sysResourceList);
            dataModel.setMsg("操作权限查询成功!");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            // TODO: handle exception
            dataModel.setData(sysResourceList);
            dataModel.setMsg("操作权限查询失败!");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }

    }

    /**
     * 判断当前用户是否需要重新登录
     *
     * @throws
     * @Title: roleResourcePrm11
     * @date: 2019年12月17日 上午10:48:23
     * @author: 郭凯
     * @param: @param content
     * @param: @return
     * @return: String
     */
    @RequestMapping(value = "/effectiveInterface.do", method = RequestMethod.POST)
    @ResponseBody
    public String effectiveInterface(String content) {
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
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);
            if (appUsers == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            dataModel.setData("");
            dataModel.setMsg("登陆成功!");
            dataModel.setState(Constant.STATE_SUCCESS);
            dataModel.setType(Constant.STATE_SUCCESS);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        } catch (Exception e) {
            // TODO: handle exception
            dataModel.setData("");
            dataModel.setMsg("登陆失败，请联系管理员!");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }

    }

    @RequestMapping(value = "/loginOut.do", method = RequestMethod.POST)
    @ResponseBody
    public String loginOut(String content) {
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
            int i = appUserService.loginOut(longinTokenId);

            if (i == 1) {
                dataModel.setData("");
                dataModel.setMsg("退出成功");
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            } else {
                dataModel.setData("");
                dataModel.setMsg("退出失败");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }


        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("退出失败");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }

    }

    /**
     * 登录成功后保存个推id
     *
     * @param content
     * @return
     */

    @RequestMapping(value = "/preservePushId.do", method = RequestMethod.POST)
    @ResponseBody
    public String preservePushId(String content) {
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
            //TokenId
            String longinTokenId = jsonobject.getString("longinTokenId");
            String userClientGtId = jsonobject.getString("userClientGtId");
            //判断longinTokenId是否为空，如果为空，直接return
            if (longinTokenId == "" || longinTokenId == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId为空，请传入TokenId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            if ("".equals(userClientGtId) || userClientGtId == null) {
                dataModel.setData("");
                dataModel.setMsg("userClientGtId为空，请传入userClientGtId!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }
            //查询longinTokenId，是否一致
            SysAppUsers appUsers = appUserService.getSysUserBylonginTokenId(longinTokenId);

            if (appUsers == null) {
                dataModel.setData("");
                dataModel.setMsg("TokenId已过期，请重新登陆!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

            int i = appUserService.preservePushId(appUsers.getUserId(), userClientGtId);
            if (i > 0) {
                dataModel.setData(i);
                dataModel.setMsg("保存成功!");
                dataModel.setState(Constant.STATE_SUCCESS);
                dataModel.setType(Constant.STATE_SUCCESS);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            } else {
                dataModel.setData("");
                dataModel.setMsg("保存失败!");
                dataModel.setState(Constant.STATE_FAILURE);
                dataModel.setType(Constant.STATE_FAILURE);
                String model = JsonUtils.toString(dataModel);//对象转JSON
                return AES.encode(model);//加密返回
            }

        } catch (Exception e) {
            // TODO: handle exception
            dataModel.setData("");
            dataModel.setMsg("保存失败，请联系管理员!");
            dataModel.setState(Constant.STATE_FAILURE);
            dataModel.setType(Constant.STATE_FAILURE);
            String model = JsonUtils.toString(dataModel);//对象转JSON
            return AES.encode(model);//加密返回
        }

    }


}
