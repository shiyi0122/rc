package com.hna.hka.archive.management.business.service;

import com.hna.hka.archive.management.business.model.*;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service
 * @ClassName: BusinessUsersService
 * @Author: 郭凯
 * @Description: 招商系统用户管理业务层（接口）
 * @Date: 2020/6/19 13:05
 * @Version: 1.0
 */
public interface BusinessUsersService {

    PageDataResult getBusinessUsersList(Integer pageNum, Integer pageSize, Map<String, String> search);

    List<BusinessScenicSpotExpand> getScenicSpotExpandList(Long userId);

    int addAllocateScenicSpot(BusinessUsersScenicSpot BusinessUsersScenicSpot);

    List<BusinessRole> getRoleList();

    BusinessUsersRole getBusinessUsersByUserId(Long userId);

    int addAllocateRole(BusinessUsersRole businessUsersRole);

    PageDataResult getBusinessUsersScenicSpotsList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    int editAllocateScenicSpot(BusinessUsersScenicSpot businessUsersScenicSpot);

    int delBusinessUsersScenicSpot(Long id);

    int addAllocateScenicSpotList(BusinessUsersScenicSpot businessUsersScenicSpot);

    int editBusinessUsersFilling(Long userId, Long examineType);

    BusinessUsers getBusinessUsersFilingMessage(String userId);

    int editBusinessUsersDeposit(Long userId, Long roleId);

}
