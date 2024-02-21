package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysCurrentUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysCurrentUserMapper {
    int deleteByPrimaryKey(Long currentUserId);

    int insert(SysCurrentUser record);

    int insertSelective(SysCurrentUser record);

    SysCurrentUser selectByPrimaryKey(Long currentUserId);

    int updateByPrimaryKeySelective(SysCurrentUser record);

    int updateByPrimaryKey(SysCurrentUser record);

    /**
     * 查询客户列表
     * @param search
     * @return
     */
    List<SysCurrentUser> getCurrentUserList(Map<String,Object> search);

	SysCurrentUser getCurrenUserByPhone(@Param("phone") String phone);
}