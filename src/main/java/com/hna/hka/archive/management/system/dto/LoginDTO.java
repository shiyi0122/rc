package com.hna.hka.archive.management.system.dto;

import lombok.Data;

@Data
public class LoginDTO {

    private String username;

    private String password;

    private String rememberMe;
}
