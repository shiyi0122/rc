package com.hna.hka.archive.management.Merchant.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.Merchant.model.SysShopCurrentUser;

import java.util.Map;

public interface SysShopCurrentUserService {

    PageInfo<SysShopCurrentUser> getShopUserList(SysShopCurrentUser sysShopCurrentUser);

    SysShopCurrentUser getShopUser(Map<String, String> search);

    int addShopUser(SysShopCurrentUser sysShopCurrentUser);

    int updateShopUser(SysShopCurrentUser sysShopCurrentUser);

    int deleteShopUser(Long shopUserId,String shopId);
}
