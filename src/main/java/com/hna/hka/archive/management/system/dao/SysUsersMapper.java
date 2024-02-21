package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.SysUsers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysUsersMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(SysUsers record);

    int insertSelective(SysUsers record);

    SysUsers selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SysUsers record);

    int updateByPrimaryKey(SysUsers record);

    /**
     * 登陆认证
     * @return
     */
    SysUsers findByLoginName(Map<String,Object> search);

    /**
     * 查询用户列表
     * @param sysUsers
     * @return
     */
    List<SysUsers> getUserList(@Param("sysUsers") SysUsers sysUsers);

    /**
     * 判断用户账号是否存在
     * @param loginName
     * @return
     */
    SysUsers getUserByLoginName(String loginName);

    List<SysUsers> getUsersVoExcel(Map<String, Object> search);
}