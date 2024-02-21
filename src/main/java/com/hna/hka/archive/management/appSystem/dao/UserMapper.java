package com.hna.hka.archive.management.appSystem.dao;

import com.hna.hka.archive.management.appSystem.model.AppUser;

public interface UserMapper {
    AppUser selUser(AppUser appUser);

    int upUser(AppUser appUser);
}
