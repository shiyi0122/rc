package com.hna.hka.archive.management.appSystem.service.Impl;

import com.google.inject.internal.asm.$ClassAdapter;
import com.hna.hka.archive.management.appSystem.dao.UserMapper;
import com.hna.hka.archive.management.appSystem.model.AppUser;
import com.hna.hka.archive.management.appSystem.service.UserService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.PasswordHelper;
import com.hna.hka.archive.management.system.util.ReturnModel;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int upUser(AppUser appUser) {
        AppUser user = userMapper.selUser(appUser);
        PasswordHelper pp = new PasswordHelper();
        pp.getMD5Password(appUser.getPassword(),user.getSaltValue());
        if (user==null){
            return -1;
        }else if (user.getPassword().equals(pp)){
            return 0;
        }else {
            RandomNumberGenerator rr = new SecureRandomNumberGenerator();
            user.setSaltValue(rr.nextBytes().toHex());
            user.setPassword(pp.getMD5Password(appUser.getNewPassword(),user.getSaltValue()));
            int i = userMapper.upUser(user);
            if (i>0){
                return 1;
            }else {
                return -2;
            }
        }
    }
}
