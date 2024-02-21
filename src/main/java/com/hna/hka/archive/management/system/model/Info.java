package com.hna.hka.archive.management.system.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.model
 * @ClassName: Info
 * @Author: 郭凯
 * @Description: 获取yml配置路径
 * @Date: 2020/6/18 10:50
 * @Version: 1.0
 */
@Component
@Data
public class Info {

    @Value("${qrWechatPath}")
    private String staticQrWechatPath;

    @Value("${logoPath}")
    private String staticLogoPath;

    @Value("${uploadPath}")
    private String staticUploadPath;

    private static String qrWechatPath;

    private static String logoPath;

    private static String uploadPath;

    @PostConstruct
    public void setQrWechatPath(){
        qrWechatPath = this.staticQrWechatPath;
    }
    @PostConstruct
    public void setLogoPath(){
        logoPath = this.staticLogoPath;
    }
    @PostConstruct
    public void setUploadPath(){
        uploadPath = this.staticUploadPath;
    }

    public static String getQrWechatPath(){
        return qrWechatPath;
    }

    public static String getLogoPath(){
        return logoPath;
    }

    public static String getUploadPath(){
        return uploadPath;
    }

}
