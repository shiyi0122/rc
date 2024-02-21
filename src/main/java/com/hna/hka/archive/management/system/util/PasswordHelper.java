package com.hna.hka.archive.management.system.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;

/**
 * 密码
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.util
 * @ClassName: PasswordHelper
 * @Author: 郭凯
 * @Description:
 * @Date: 2020/5/18 9:18
 * @Version: 1.0
 */
public class PasswordHelper {

    @Value("${password.algorithmName}")
    private String algorithmName = "md5";

    @Value("${password.hashIterations}")
    private int hashIterations = 2;

    public String getMD5Password(String loginPassword,String credentialsSalt){
        return new SimpleHash(algorithmName, loginPassword, ByteSource.Util.bytes(credentialsSalt),hashIterations).toHex();
    }

}
