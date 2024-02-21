package com.hna.hka.archive.management.system.service.impl;

import com.hna.hka.archive.management.system.dao.SysUsersMapper;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUsersMapper sysUsersMapper;

    /**
     * 登陆认证
     * @param username
     * @return
     */
    @Override
    public SysUsers findByLoginName(String username) {
        Map<String,Object> search = new HashMap<>();
        search.put("username",username);
        search.put("userState","10");
        return sysUsersMapper.findByLoginName(search);
    }
}
