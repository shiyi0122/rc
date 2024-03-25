package com.hna.hka.archive.management.Merchant.service.Impl;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.Merchant.dao.SysShopBindingUserMapper;
import com.hna.hka.archive.management.Merchant.dao.SysShopCurrentUserMapper;
import com.hna.hka.archive.management.Merchant.model.SysShopBindingUser;
import com.hna.hka.archive.management.Merchant.model.SysShopCurrentUser;
import com.hna.hka.archive.management.Merchant.service.SysShopCurrentUserService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service("SysShopCurrentUserService")
public class SysShopCurrentUserServiceImpl implements SysShopCurrentUserService {

    @Autowired
    private SysShopCurrentUserMapper sysShopCurrentUserMapper;

    @Autowired
    private SysShopBindingUserMapper sysShopBindingUserMapper;

    @Override
    public PageInfo<SysShopCurrentUser> getShopUserList(SysShopCurrentUser sysShopCurrentUser) {
        List<SysShopCurrentUser> shopUserList = sysShopCurrentUserMapper.getShopUserList(sysShopCurrentUser);
        return new PageInfo<>(shopUserList);
    }

    @Override
    public SysShopCurrentUser getShopUser(Map<String, String> search) {
        return sysShopCurrentUserMapper.getShopUser(search);
    }

    @Override
    public int addShopUser(SysShopCurrentUser sysShopCurrentUser) {
        sysShopCurrentUser.setShopUserId(IdUtils.getSeqId());
        sysShopCurrentUser.setCreateDate(DateUtil.currentDateTime());
        sysShopCurrentUser.setUpdateDate(DateUtil.currentDateTime());
        return sysShopCurrentUserMapper.addShopUser(sysShopCurrentUser);
    }

    @Override
    public int updateShopUser(SysShopCurrentUser sysShopCurrentUser) {
        sysShopCurrentUser.setUpdateDate(DateUtil.currentDateTime());
        return sysShopCurrentUserMapper.updateShopUser(sysShopCurrentUser);
    }

    @Override
    public int deleteShopUser(Long shopUserId, String shopId) {
        if (shopId != null && shopUserId != null) {
            //删除中间表
            SysShopBindingUser shopBindingUser = new SysShopBindingUser();
            shopBindingUser.setShopId(Long.valueOf(shopId));
            shopBindingUser.setShopUserId(shopUserId);
            sysShopBindingUserMapper.deleteShopBindingUser(shopBindingUser);
            return sysShopCurrentUserMapper.deleteShopUser(shopUserId);
        } else {
            return 0;
        }
    }

    @Override
    public List<SysShopCurrentUser> getShopUserPermission(SysShopCurrentUser sysShopCurrentUser) {
        return sysShopCurrentUserMapper.getShopUserPermission(sysShopCurrentUser);
    }

    @Override
    public int addUserPermission(SysShopCurrentUser sysShopCurrentUser) {
        //判断用户权限
        SysShopBindingUser shopBindingUser = new SysShopBindingUser();
        shopBindingUser.setShopUserId(sysShopCurrentUser.getShopUserId());
        List<SysShopBindingUser> users = sysShopBindingUserMapper.selectById(shopBindingUser);
        if (sysShopCurrentUser.getUserRole().equals("0")) {
            return -2;
        }
        if (sysShopCurrentUser.getUserRole().equals("2") && users.size() != 0) {
            //对方已经绑定一个店铺
            return -1;
        }
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getShopId().equals(Long.valueOf(sysShopCurrentUser.getShopId()))) {
                //对方已经绑定了这个店铺
                return -3;
            }
        }
        shopBindingUser.setBindingId(IdUtils.getSeqId());
        shopBindingUser.setShopId(Long.valueOf(sysShopCurrentUser.getShopId()));
        shopBindingUser.setShopUserId(sysShopCurrentUser.getShopUserId());
        shopBindingUser.setUpdateDate(DateUtil.currentDateTime());
        shopBindingUser.setCreateDate(DateUtil.currentDateTime());
        return sysShopBindingUserMapper.addShopBindingUser(shopBindingUser);

    }

    @Override
    public int delectUserPermission(Long bindingId) {
        return sysShopBindingUserMapper.deleteByUserId(bindingId);
    }
}
