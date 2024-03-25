package com.hna.hka.archive.management.Merchant.dao;


import com.hna.hka.archive.management.Merchant.model.SysShopBindingUser;

import java.util.List;

public interface SysShopBindingUserMapper {
    int deleteByUserId(Long bindingId);

    int addShopBindingUser(SysShopBindingUser shopBindingUser);

    List<SysShopBindingUser> selectById(SysShopBindingUser shopBindingUser);

    int updateShopBindingUser(SysShopBindingUser shopBindingUser);

    int updateByPrimaryKey(SysShopBindingUser record);

    int deleteShopBindingUser(SysShopBindingUser shopBindingUser);
}