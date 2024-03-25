package com.hna.hka.archive.management.Merchant.dao;


import com.hna.hka.archive.management.Merchant.model.SysShopCurrentUser;

import java.util.List;
import java.util.Map;

public interface SysShopCurrentUserMapper {
    int deleteShopUser(Long shopUserId);

    int addShopUser(SysShopCurrentUser record);

    SysShopCurrentUser getShopUser(Map<String, String> search);

    int updateShopUser(SysShopCurrentUser record);

    List<SysShopCurrentUser> getShopUserList(SysShopCurrentUser sysShopCurrentUser);

    List<SysShopCurrentUser> getShopUserPermission(SysShopCurrentUser sysShopCurrentUser);
}