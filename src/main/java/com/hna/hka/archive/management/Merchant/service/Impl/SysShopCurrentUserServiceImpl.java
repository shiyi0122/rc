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
        if (sysShopCurrentUser.getShopId() != null) {
            //添加到中间表
            SysShopBindingUser shopBindingUser = new SysShopBindingUser();
            shopBindingUser.setShopUserId(sysShopCurrentUser.getShopUserId());
            SysShopBindingUser user = sysShopBindingUserMapper.selectById(shopBindingUser);
            if (user != null) {
                user.setUpdateDate(DateUtil.currentDateTime());
                user.setShopId(Long.valueOf(sysShopCurrentUser.getShopId()));
                sysShopBindingUserMapper.updateShopBindingUser(shopBindingUser);
            } else {
                SysShopBindingUser user1 = new SysShopBindingUser();
                user1.setBindingId(IdUtils.getSeqId());
                user1.setShopId(Long.valueOf(sysShopCurrentUser.getShopId()));
                user1.setShopUserId(sysShopCurrentUser.getShopUserId());
                user1.setCreateDate(DateUtil.currentDateTime());
                user1.setUpdateDate(DateUtil.currentDateTime());
                sysShopBindingUserMapper.addShopBindingUser(user1);
            }
        }
        return sysShopCurrentUserMapper.addShopUser(sysShopCurrentUser);
    }

    @Override
    public int updateShopUser(SysShopCurrentUser sysShopCurrentUser) {
        if (sysShopCurrentUser.getShopId() != null) {
            //添加到中间表
            SysShopBindingUser shopBindingUser = new SysShopBindingUser();
            shopBindingUser.setShopUserId(sysShopCurrentUser.getShopUserId());
            SysShopBindingUser user = sysShopBindingUserMapper.selectById(shopBindingUser);
            if (user != null) {
                user.setUpdateDate(DateUtil.currentDateTime());
                user.setShopId(Long.valueOf(sysShopCurrentUser.getShopId()));
                sysShopBindingUserMapper.updateShopBindingUser(user);
            } else {
                SysShopBindingUser user1 = new SysShopBindingUser();
                user1.setBindingId(IdUtils.getSeqId());
                user1.setShopId(Long.valueOf(sysShopCurrentUser.getShopId()));
                user1.setShopUserId(sysShopCurrentUser.getShopUserId());
                user1.setCreateDate(DateUtil.currentDateTime());
                user1.setUpdateDate(DateUtil.currentDateTime());
                sysShopBindingUserMapper.addShopBindingUser(user1);
            }
        }
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
}
