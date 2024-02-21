package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.WechatBusinessManagement;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysManagementService
 * @Author: 郭凯
 * @Description: 证书管理业务层（接口）
 * @Date: 2020/5/22 17:01
 * @Version: 1.0
 */
public interface SysManagementService {

    /**
     * 证书列表查询
     * @param pageNum
     * @param pageSize
     * @param wechatBusinessManagement
     * @return
     */
    PageDataResult getManagementList(Integer pageNum, Integer pageSize, WechatBusinessManagement wechatBusinessManagement);

    /**
     * 查询证书
     * @return
     */
    List<WechatBusinessManagement> getCertificate();

    int addManagement(WechatBusinessManagement wechatBusinessManagement, MultipartFile file);

    int delManagement(Long merchantId);
}
