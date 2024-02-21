package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.dto.LoginDTO;
import com.hna.hka.archive.management.system.model.SysOrder;
import com.hna.hka.archive.management.system.model.SysScenicSpotImg;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.service.SysOrderService;
import com.hna.hka.archive.management.system.service.SysScenicSpotImgService;
import com.hna.hka.archive.management.system.shiro.MyRealm;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.ReturnModel;
import com.hna.hka.archive.management.system.util.ToolUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName  LoginControllrt
 * @Description TODO
 * @author 郭凯
 * @date  2020年12月18日 下午1:12:08
 *
 */
@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private SysScenicSpotImgService sysScenicSpotImgService;

    @Autowired
    private MyRealm myRealm;

    @Autowired
    private HttpSession session;

    @Autowired
    private SysOrderService sysOrderService;

    /**
     * 功能描述: 定向登陆页
     * @Param: []
     * @Return: java.lang.String
     * @Author: 郭凯
     * @Date: 2020/4/29 9:41
     */
    @RequestMapping("login")
    public String tologin(){
//        logger.info("定向登陆页");
        return "login";
    }

    /**
     * 功能描述: 定向景区选择页面
     * @Param: [model]
     * @Return: java.lang.String
     * @Author: 郭凯
     * @Date: 2020/4/29 9:42
     */
    @RequestMapping("loginSpot")
    public String loginSpot(Model model, MyRealm myRealm){
        SysUsers user = (SysUsers) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("userName",user.getUserName());
        if (ToolUtil.isNotEmpty(user.getUserRoleState())){
            if ("10".equals(user.getUserRoleState()) || "20".equals(user.getUserRoleState())){
                if (ToolUtil.isEmpty(user.getUserSex()) || ToolUtil.isEmpty(user.getUserBirthday()) || ToolUtil.isEmpty(user.getUserUnitAddress()) || ToolUtil.isEmpty(user.getUserPhone()) || ToolUtil.isEmpty(user.getUserEmail())){
                    return "redirect:userNotice";
                }
            }
        }
        logger.info("定向景区选择页面");
        return "homePage";
    }

    /**
     * @Author 郭凯
     * @Description 跳转通知页面
     * @Date 13:59 2020/12/9
     * @Param []
     * @return java.lang.String
    **/
    @RequestMapping("userNotice")
    public String userNotice(){
        return "userNotice";
    }

    /**
     * @Author 郭凯
     * @Description 跳转信息修改页面
     * @Date 13:59 2020/12/9
     * @Param []
     * @return java.lang.String
    **/
    @RequestMapping("userInfo")
    public String userInfo(){
        return "userInfo";
    }

    /**
     * @Author 郭凯
     * @Description 返回景区选择页面
     * @Date 16:31 2020/8/13
     * @Param []
     * @return java.lang.String
    **/
    @RequestMapping("returnScenicSpot")
    public String returnScenicSpot(){
        myRealm.clearAllCachedAuthorizationInfo2();
        return "redirect:loginSpot";
    }

    /**
     * 功能描述: 定向主页
     * @Param: [scenicSpotId, scenicSpotName, model]
     * @Return: java.lang.String
     * @Author: 郭凯
     * @Date: 2020/4/29 9:42
     */
    @RequestMapping("home")
    public String home(String scenicSpotId,String scenicSpotName,Model model){
        Session session = SecurityUtils.getSubject().getSession();
        session.removeAttribute("scenicSpotId");
        session.setAttribute("scenicSpotId",scenicSpotId);
        Subject subject = SecurityUtils.getSubject();
        if(subject.isPermitted("SYSTEM_USER")){
            System.out.println("//有权限");
        } else{
            System.out.println("//无权限");
        }
        SysUsers user = (SysUsers) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("scenicSpotName",scenicSpotName);
        model.addAttribute("userName",user.getUserName());
        logger.info("定向主页");
        return "home";
    }

    /**
     *  查询景区
     * @Author 郭凯
     * @Description
     * @Date 17:31 2020/4/29
     * @Param [scenicSpotId]
     * @return java.util.Map<java.lang.String,java.lang.Object>
    **/
    @RequestMapping("homePage")
    @ResponseBody
    public Map<String,Object> homePage(String scenicSpotId){
        SysUsers user = (SysUsers) SecurityUtils.getSubject().getPrincipal();
        Map<String,Object> data = new HashMap<>();
        Map<String,String> search = new HashMap<>();
        search.put("userId",String.valueOf(user.getUserId()));
        search.put("scenicSpotId",scenicSpotId);
        //根据用户ID查询景区名称和图片
        List<SysScenicSpotImg> SysScenicSpotImg = sysScenicSpotImgService.getScenicSpotImgByUserId(search);
        data.put("data",SysScenicSpotImg);
        data.put("code",1);
        data.put("scenicSpotId",session.getAttribute("scenicSpotId"));
        return data;
    }
    /**
     *  退出系统
     * @Author 郭凯
     * @Description
     * @Date 17:36 2020/4/29
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping({"/" , "logout"})
    public String logout(){
        logger.info("退出系统");
        Subject subject = SecurityUtils.getSubject();
        subject.logout(); // shiro底层删除session的会话信息
        return "redirect:login";
    }

    /**
     *  登陆
     * @Author 郭凯
     * @Description
     * @Date 17:36 2020/4/29
     * @Param [request, loginDTO, session]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @RequestMapping("loginSystem")
    @ResponseBody
    public Map<String,Object> loginSystem(HttpServletRequest request, LoginDTO loginDTO,HttpSession session){
        logger.info("进行登陆");
        Map<String,Object> data = new HashMap<>();
        // 使用 shiro 进行登录
        Subject subject = SecurityUtils.getSubject();

        String userName = loginDTO.getUsername().trim();
        String password = loginDTO.getPassword().trim();
        String host = request.getRemoteAddr();

        //获取token
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password,host);
        try {
//            token.setRememberMe(true);
            subject.login(token);
            // 登录成功
            SysUsers user = (SysUsers) subject.getPrincipal();

            session.setAttribute("userName", user.getUserName());
            //查看登陆过期时间
            long timeout = SecurityUtils.getSubject().getSession().getTimeout();
            System.out.println(timeout+"毫秒");
            data.put("code",1);
            data.put("url","/loginSpot");
            //data.put("message","登陆成功");
            logger.info(user.getUserName()+"登陆成功");
            return data;
        } catch (UnknownAccountException e) {
            data.put("code",0);
            data.put("message",loginDTO.getUsername()+"账号不存在");
            logger.error(loginDTO.getUsername()+"账号不存在");
            return data;
        }catch (DisabledAccountException e){
            data.put("code",0);
            data.put("message",loginDTO.getUsername()+"账号异常");
            logger.error(loginDTO.getUsername()+"账号异常");
            return data;
        }
        catch (AuthenticationException e){
            data.put("code",0);
            data.put("message",loginDTO.getUsername()+"密码错误");
            logger.error(loginDTO.getUsername()+"密码错误");
            return data;
        }
    }



    /**
    * @Author 郭凯
    * @Description: 查询景区收入数据
    * @Title: getOrderAmount
    * @date  2020年12月18日 下午1:26:45
    * @param @return
    * @return ReturnModel
    * @throws
    */
    @RequestMapping("/getOrderAmount")
    @ResponseBody
    public ReturnModel getOrderAmount() {
    	ReturnModel returnModel = new ReturnModel();
    	try {
			String scenicSpotId = session.getAttribute("scenicSpotId").toString();
			SysOrder order = sysOrderService.getOrderAmounts(scenicSpotId);
			if (ToolUtil.isNotEmpty(order)) {
				returnModel.setData(order);
				returnModel.setMsg("查询成功！");
				returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
			}else {
				returnModel.setData("");
				returnModel.setMsg("查询失败！");
				returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("getOrderAmount",e);
			returnModel.setData("");
			returnModel.setMsg("查询失败！");
			returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
		}
    }

}
