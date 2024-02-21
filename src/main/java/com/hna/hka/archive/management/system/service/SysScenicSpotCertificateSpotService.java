package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysScenicSpotCertificateSpot;
import com.hna.hka.archive.management.system.model.WechatBusinessManagement;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysScenicSpotCertificateSpotService
 * @Author: 郭凯
 * @Description: 景区证书管理业务层（接口）
 * @Date: 2020/5/28 9:42
 * @Version: 1.0
 */
public interface SysScenicSpotCertificateSpotService {

    /**
     * 景区证书列表查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageDataResult getCertificateSpotList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    /**
     * 新增景区证书
     * @param sysScenicSpotCertificateSpot
     * @return
     */
    int addCertificateSpot(SysScenicSpotCertificateSpot sysScenicSpotCertificateSpot);

    /**
     * 查询此景区是否存在证书
     * @param scenicSpotId
     * @return
     */
    SysScenicSpotCertificateSpot selectCertificateSpotById(Long scenicSpotId);

    /**
     * 修改景区证书
     * @param sysScenicSpotCertificateSpot
     * @return
     */
    int editCertificateSpot(SysScenicSpotCertificateSpot sysScenicSpotCertificateSpot);

    /**
     * 删除景区证书
     * @param certificateSpotId
     * @return
     */
    int delCertificateSpot(Long certificateSpotId);

    WechatBusinessManagement getBusinessManagementByScenicSpotId(Long orderScenicSpotId);

    List<SysScenicSpotCertificateSpot> getUploadExcelCertificateSpot(Map<String, Object> search);

    WechatBusinessManagement selectById(Map<String, Object> search);
}
