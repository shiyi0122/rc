package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysUsers;

public interface LoginService {

    /**
     * 登陆认证
     * @param username
     * @return
     */
    SysUsers findByLoginName(String username);
}
