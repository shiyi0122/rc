package com.hna.hka.archive.management.appSystem.model;

import lombok.Data;

@Data
public class AppUser {

    private String userId;

    private String loginName;

    private String password;

    private String newPassword;

    private String saltValue;


}
