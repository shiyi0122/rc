package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.WechatBusinessManagementMapper;
import com.hna.hka.archive.management.system.model.WechatBusinessManagement;
import com.hna.hka.archive.management.system.service.SysManagementService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.FileUploadUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysManagementServiceImpl
 * @Author: 郭凯
 * @Description: 证书管理业务层（实现）
 * @Date: 2020/5/22 17:01
 * @Version: 1.0
 */
@Service
@Transactional
public class SysManagementServiceImpl implements SysManagementService {

    @Autowired
    private WechatBusinessManagementMapper wechatBusinessManagementMapper;

    @Value("${GET_CER_PAHT}")
    private String GET_CER_PAHT;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    /**
     * @Author 郭凯
     * @Description 证书列表查询
     * @Date 17:04 2020/5/22
     * @Param [pageNum, pageSize, wechatBusinessManagement]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getManagementList(Integer pageNum, Integer pageSize, WechatBusinessManagement wechatBusinessManagement) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<WechatBusinessManagement> managementList = wechatBusinessManagementMapper.getManagementList(wechatBusinessManagement);
        if (managementList.size() != 0){
            PageInfo<WechatBusinessManagement> pageInfo = new PageInfo<>(managementList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 查询证书列表
     * @Date 14:43 2020/5/28
     * @Param []
     * @return java.util.List<com.hna.hka.archive.management.system.model.WechatBusinessManagement>
    **/
    @Override
    public List<WechatBusinessManagement> getCertificate() {
        WechatBusinessManagement wechatBusinessManagement = new WechatBusinessManagement();
        List<WechatBusinessManagement> managementList = wechatBusinessManagementMapper.getManagementList(wechatBusinessManagement);
        return managementList;
    }

    /**
     * @Author 郭凯
     * @Description 新增证书
     * @Date 17:53 2020/9/11
     * @Param [wechatBusinessManagement, file]
     * @return int
    **/
    @Override
    public int addManagement(WechatBusinessManagement wechatBusinessManagement, MultipartFile file) {
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".p12")) {
            String filename = wechatBusinessManagement.getMerchantFileName();//文件名称
            filename += type;
            String path = GET_CER_PAHT + filename;// 存放位置
            File destFile = new File(path);
            try {
                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//                String upload = fileUploadUtil.upload(file, GET_CER_PAHT.substring(1) + filename);
//                System.out.println(upload);

            } catch (IOException e) {
                e.printStackTrace();
            }//复制文件到指定目录
            //查询文件名称是否唯一
            WechatBusinessManagement management = wechatBusinessManagementMapper.selectWechatBusinessManagementByName(filename);
            if (management != null) {
                return 3;
            }
            wechatBusinessManagement.setMerchantId(IdUtils.getSeqId());
            wechatBusinessManagement.setCertFileName(filename);
            wechatBusinessManagement.setCreateDate(DateUtil.currentDateTime());
            wechatBusinessManagement.setUpdateDate(DateUtil.currentDateTime());
            return wechatBusinessManagementMapper.insertSelective(wechatBusinessManagement);
        }else {
            return 2;
        }
    }

    /**
     * @Author 郭凯
     * @Description 删除证书
     * @Date 9:40 2020/9/14
     * @Param [merchantId]
     * @return int
    **/
    @Override
    public int delManagement(Long merchantId) {
        return wechatBusinessManagementMapper.deleteByPrimaryKey(merchantId);
    }
}
