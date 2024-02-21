package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysResource;
import com.hna.hka.archive.management.system.service.SysResourceService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.ReturnModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotBlank;
import java.util.*;

@RequestMapping("/system/res")
@Controller
public class ResourceController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysResourceService sysResourceService;

    /**
     * 功能描述: 查询头部菜单栏
     * @Param: []
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: 郭凯
     * @Date: 2020/4/26 14:04
     */
    @RequestMapping("/getResourceNav")
    @ResponseBody
    public Map<String,Object> getResourceNav() {
        Map<String, Object> data = new HashMap<>();
        try {
            List<SysResource> resourcesNav = sysResourceService.getResourceNav();
            data.put("data", resourcesNav);
            data.put("code", 1);
        } catch (Exception e) {
            logger.info("getResourceNav",e);
        }
        return data;
    }

    /**
     * 功能描述: 根据顶级菜单查询二三级菜单
     * @Param: [pid]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: 郭凯
     * @Date: 2020/4/26 14:04
     */
    @RequestMapping("/getResourceLeft")
    @ResponseBody
    public Map<String,Object> getResourceLeft(String pid){
        Map<String,Object> data = new HashMap<>();
        Map<String,String> search = new HashMap<>();
        search.put("pid",pid);
        List<SysResource> resourceLeftList = sysResourceService.getResourceLeft(search);

        data.put("pid",getMenuTreeList(resourceLeftList,pid));
        return data;
    }

    /**
     * 功能描述: 递归循环遍历菜单栏
     * @Param:
     * @Return:
     * @Author: 郭凯
     * @Date: 2020/4/26 14:03
     */
    private List<SysResource> getMenuTreeList(List<SysResource> menuList,String pid) {
        // 查找所有菜单
        List<SysResource> childrenList = new ArrayList<>();
        menuList.forEach(menu -> {
            if (Objects.equals(pid, menu.getResPcode())) {
                menu.setChild(getMenuTreeList(menuList, menu.getResCode()));
                childrenList.add(menu);
            }
        });
        return childrenList;
    }

    /**
     * 功能描述: 菜单列表查询
     * @Param: []
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: 郭凯
     * @Date: 2020/4/26 14:03
     */
    @RequestMapping("/getResourceList")
    @ResponseBody
    public Map<String,Object> getResourceList(SysResource sysResource) {
        Map<String, Object> data = new HashMap<>();
        try {
            List<SysResource> resourcesNav = sysResourceService.getResourceList(sysResource);
            data.put("code",0);
            data.put("msg","");
            data.put("count",resourcesNav.size());
            data.put("data",resourcesNav);
        } catch (Exception e) {
            logger.info("getResourceList",e);
        }
        return data;
    }

    /**
     * 功能描述: 添加菜单栏
     * @Param: [sysResource]
     * @Return: com.hna.hka.archive.management.system.util.ReturnModel
     * @Author: 郭凯
     * @Date: 2020/4/26 14:02
     */
    @RequestMapping("/addResource")
    @ResponseBody
    public ReturnModel addResource(SysResource sysResource){
        ReturnModel returnModel = null;
        try {
            returnModel = new ReturnModel();
            int i= sysResourceService.addResource(sysResource);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("菜单栏新增成功");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("菜单栏新增错误，请联系管理员");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnModel.setData("");
            returnModel.setMsg("菜单栏新增错误，请联系管理员");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 功能描述: 删除菜单栏
     * @Param:
     * @Return:
     * @Author: 郭凯
     * @Date: 2020/4/26 15:50
     */
    @RequestMapping("delResource")
    @ResponseBody
    public ReturnModel delResource(@NotBlank(message = "菜单ID不能为空")Long resId){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysResourceService.delResource(resId);
            if (i == 1){
                returnModel.setData("");
                returnModel.setMsg("菜单栏删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
                returnModel.setData("");
                returnModel.setMsg("菜单栏删除错误，请联系管理员！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
        	logger.info("delResource", e);
            returnModel.setData("");
            returnModel.setMsg("菜单栏删除错误，请联系管理员！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }
    /**
     * 功能描述: 菜单栏修改数据查询回显
     * @Param:
     * @Return: 
     * @Author: 郭凯
     * @Date: 2020/4/27 14:51
     */
    @RequestMapping("/LoadEditResource")
    @ResponseBody
    public ReturnModel LoadEditResource(@NotBlank(message = "菜单ID不能为空")Long resId){
        ReturnModel returnModel = new ReturnModel();
        try {
            SysResource sysResource = sysResourceService.getResourceById(resId);
            if (sysResource == null){
                returnModel.setData("");
                returnModel.setMsg("数据查询失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }else{
                returnModel.setData(sysResource);
                returnModel.setMsg("数据查询成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }
        }catch (Exception e){
        	logger.info("LoadEditResource", e);
            returnModel.setData("");
            returnModel.setMsg("数据查询失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 功能描述:菜单栏修改
     * @Param:
     * @Return:
     * @Author: 郭凯
     * @Date: 2020/4/27 15:47
     */
    @RequestMapping("/editResource")
    @ResponseBody
    public ReturnModel editResource(SysResource sysResource){
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = sysResourceService.updateResource(sysResource);
            if (i == 1){
                returnModel.setData(sysResource);
                returnModel.setMsg("菜单栏修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else{
            	returnModel.setData("");
                returnModel.setMsg("菜单栏修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        }catch (Exception e){
        	logger.info("editResource", e);
            returnModel.setData("");
            returnModel.setMsg("菜单栏修改失败！");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * 根据角色获取角色下的所有权限
     * zhang
     * @param roleId
     * @return
     */

    @RequestMapping("/getRoleResourceList")
    @ResponseBody
    public Map<String,Object> getRoleResourceList(Long roleId) {
        Map<String, Object> data = new HashMap<>();
        try {
            List<SysResource> resourcesNav = sysResourceService.getRoleResourceList(roleId);
            data.put("code",0);
            data.put("msg","");
            data.put("count",resourcesNav.size());
            data.put("data",resourcesNav);
        } catch (Exception e) {
            logger.info("getResourceList",e);
        }
        return data;
    }
}
