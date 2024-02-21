package com.hna.hka.archive.management.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.business.dao.*;
import com.hna.hka.archive.management.business.model.*;
import com.hna.hka.archive.management.business.service.BusinessUsersService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service.impl
 * @ClassName: BusinessUsersServiceImpl
 * @Author: 郭凯
 * @Description: 招商系统用户管理控制层（实现）
 * @Date: 2020/6/19 13:06
 * @Version: 1.0
 */
@Service
public class BusinessUsersServiceImpl implements BusinessUsersService {

    @Autowired
    private BusinessUsersMapper businessUsersMapper;

    @Autowired
    private BusinessScenicSpotExpandMapper businessScenicSpotExpandMapper;

    @Autowired
    private BusinessUsersScenicSpotMapper businessUsersScenicSpotMapper;

    @Autowired
    private BusinessRoleMapper businessRoleMapper;

    @Autowired
    private BusinessUsersRoleMapper businessUsersRoleMapper;

    /**
     * @Author 郭凯
     * @Description 招商系统用户管理列表查询
     * @Date 13:08 2020/6/19
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getBusinessUsersList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<BusinessUsers> businessUsersList = businessUsersMapper.getBusinessUsersList(search);
        if (businessUsersList.size() != 0){
            PageInfo<BusinessUsers> pageInfo = new PageInfo<>(businessUsersList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 查询拓展景区列表
     * @Date 15:16 2020/11/26
     * @Param []
     * @return java.util.List<com.hna.hka.archive.management.business.model.BusinessScenicSpotExpand>
    **/
    @Override
    public List<BusinessScenicSpotExpand> getScenicSpotExpandList(Long userId) {
        Map<String, Object> search = new HashMap<>();
        List<BusinessScenicSpotExpand> scenicSpotExpand = businessScenicSpotExpandMapper.getScenicSpotExpandList(search);
        search.put("userId",userId);
        List<BusinessScenicSpotExpand> scenicSpotExpands = businessScenicSpotExpandMapper.getScenicSpotExpandListByUserId(search);
        //取集合结果差
        List<BusinessScenicSpotExpand> scenicSpotExpandList = scenicSpotExpand.stream().filter(item -> !scenicSpotExpands.contains(item)).collect(toList());
        return scenicSpotExpandList;
    }

    /**
     * @Author 郭凯
     * @Description 分配景区
     * @Date 15:33 2020/11/26
     * @Param [businessUsers]
     * @return int
    **/
    @Override
    public int addAllocateScenicSpot(BusinessUsersScenicSpot businessUsersScenicSpot) {
        //查询此用户是不是有合同
        BusinessUsersScenicSpot usersScenicSpot = businessUsersScenicSpotMapper.getBusinessUsersScenicSpot(businessUsersScenicSpot.getUserId(),businessUsersScenicSpot.getScenicSpotId());
        if (ToolUtil.isNotEmpty(usersScenicSpot)) {
            businessUsersScenicSpotMapper.deleteByPrimaryKey(usersScenicSpot.getId());
        }
        //修改合伙人类型
        BusinessUsers businessUsers = new BusinessUsers();
        businessUsers.setUserType("1");
        businessUsers.setScenicType("1");
        businessUsers.setUpdateTime(DateUtil.currentDateTime());
        businessUsers.setId(businessUsersScenicSpot.getUserId());
        businessUsersMapper.updateByPrimaryKeySelective(businessUsers);
        businessUsersScenicSpot.setId(IdUtils.getSeqId());
        businessUsersScenicSpot.setCreateTime(DateUtil.currentDateTime());
        businessUsersScenicSpot.setUpdateTime(DateUtil.currentDateTime());
        return businessUsersScenicSpotMapper.insertSelective(businessUsersScenicSpot);
    }

    /**
     * @Author 郭凯
     * @Description 查询角色
     * @Date 9:39 2020/11/27
     * @Param []
     * @return java.util.List<com.hna.hka.archive.management.business.model.BusinessRole>
    **/
    @Override
    public List<BusinessRole> getRoleList() {
        Map<String, String> search = new HashMap<>();
        return businessRoleMapper.getBusinessRoleList(search);
    }

    /**
     * @Author 郭凯
     * @Description 查询用户拥有的角色
     * @Date 9:54 2020/11/27
     * @Param [userId]
     * @return com.hna.hka.archive.management.business.model.BusinessUsersRole
    **/
    @Override
    public BusinessUsersRole getBusinessUsersByUserId(Long userId) {
        return businessUsersRoleMapper.getBusinessUsersByUserId(userId);
    }

    /**
     * @Author 郭凯
     * @Description 用户角色分配
     * @Date 10:17 2020/11/27
     * @Param [businessUsersRole]
     * @return int
    **/
    @Override
    public int addAllocateRole(BusinessUsersRole businessUsersRole) {
        BusinessUsersRole usersRole = businessUsersRoleMapper.getBusinessUsersByUserId(businessUsersRole.getUserId());
        if (ToolUtil.isNotEmpty(usersRole)){
            businessUsersRoleMapper.deleteByPrimaryKey(usersRole.getId());
        }
        businessUsersRole.setId(IdUtils.getSeqId());
        businessUsersRole.setCreateDate(DateUtil.currentDateTime());
        return businessUsersRoleMapper.insertSelective(businessUsersRole);
    }

    /**
     * @Author 郭凯
     * @Description 查询用户绑定景区列表
     * @Date 10:00 2020/12/1
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getBusinessUsersScenicSpotsList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String format = formatter.format(new Date());
        PageHelper.startPage(pageNum, pageSize);
        List<BusinessUsers> businessUsersList = businessUsersScenicSpotMapper.getBusinessUsersScenicSpotsList(search);
//        for (BusinessUsers businessUsers : businessUsersList) {
//            try {
//                int dates = Integer.parseInt(DateUtil.findDates( format,businessUsers.getContractEndTime()));
//                if (dates <= 0){
//                    businessUsers.setTimeType("1");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        if (businessUsersList.size() != 0){
            PageInfo<BusinessUsers> pageInfo = new PageInfo<>(businessUsersList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 修改用户景区
     * @Date 11:14 2020/12/1
     * @Param [businessUsersScenicSpot]
     * @return int
    **/
    @Override
    public int editAllocateScenicSpot(BusinessUsersScenicSpot businessUsersScenicSpot) {
        businessUsersScenicSpot.setUpdateTime(DateUtil.currentDateTime());
        return businessUsersScenicSpotMapper.updateByPrimaryKeySelective(businessUsersScenicSpot);
    }

    /**
     * @Author 郭凯
     * @Description 用户解绑景区
     * @Date 11:19 2020/12/1
     * @Param [id]
     * @return int
    **/
    @Override
    public int delBusinessUsersScenicSpot(Long id) {
        return businessUsersScenicSpotMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量分配景区
     * @param businessUsersScenicSpot
     * @return
     */
    @Override
    public int addAllocateScenicSpotList(BusinessUsersScenicSpot businessUsersScenicSpot) {

        //查询此用户是不是有合同
        BusinessUsersScenicSpot usersScenicSpot = businessUsersScenicSpotMapper.getBusinessUsersScenicSpot(businessUsersScenicSpot.getUserId(),businessUsersScenicSpot.getScenicSpotId());
        if (ToolUtil.isNotEmpty(usersScenicSpot)) {
            businessUsersScenicSpotMapper.deleteByPrimaryKey(usersScenicSpot.getId());
        }
        //修改合伙人类型
        BusinessUsers businessUsers = new BusinessUsers();
        businessUsers.setUserType("1");
        businessUsers.setScenicType("1");
        businessUsers.setUpdateTime(DateUtil.currentDateTime());
        businessUsers.setId(businessUsersScenicSpot.getUserId());
        businessUsersMapper.updateByPrimaryKeySelective(businessUsers);

        Long[] scenicSpotIds = businessUsersScenicSpot.getScenicSpotIds();
        int i = 0;
        for (Long  scenicSpotId : scenicSpotIds) {
            businessUsersScenicSpot.setId(IdUtils.getSeqId());
            businessUsersScenicSpot.setCreateTime(DateUtil.currentDateTime());
            businessUsersScenicSpot.setUpdateTime(DateUtil.currentDateTime());
            businessUsersScenicSpot.setScenicSpotId(scenicSpotId);
            i = businessUsersScenicSpotMapper.insertSelective(businessUsersScenicSpot);
        }

        return i ;

    }

    /**
     * 修改用户报备状态权限
     * @param userId
     * @param examineType
     * @return
     */
    @Override
    public int editBusinessUsersFilling(Long userId, Long examineType) {

       int i = businessUsersMapper.editBusinessUsersFilling(userId,examineType);
       return i;
    }

    /**
     * 获取用户数据
     * @param userId
     * @return
     */
    @Override
    public BusinessUsers getBusinessUsersFilingMessage(String userId) {

        BusinessUsers businessUsers = businessUsersMapper.selectByPrimaryKey(Long.valueOf(userId));
        return businessUsers;
    }

    /**
     *  修改用户查看押金收入权限
     * @param userId
     * @param depositCheckType
     * @return
     */
    @Override
    public int editBusinessUsersDeposit(Long userId, Long depositCheckType) {

        int i = businessUsersMapper.editBusinessUsersDeposit(userId,depositCheckType);
        return i;
    }
}
