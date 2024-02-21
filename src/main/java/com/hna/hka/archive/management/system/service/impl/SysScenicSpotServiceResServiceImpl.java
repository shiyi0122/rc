package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysScenicSpotCustomTypeMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotServiceResMapper;
import com.hna.hka.archive.management.system.model.SysResExcel;
import com.hna.hka.archive.management.system.model.SysScenicSpotCustomType;
import com.hna.hka.archive.management.system.model.SysScenicSpotServiceRes;
import com.hna.hka.archive.management.system.service.SysScenicSpotServiceResService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysScenicSpotServiceResServiceImpl
 * @Author: 郭凯
 * @Description: 服务项业务层（实现）
 * @Date: 2020/7/25 10:21
 * @Version: 1.0
 */
@Service
@Transactional
public class SysScenicSpotServiceResServiceImpl implements SysScenicSpotServiceResService {

    @Autowired
    private SysScenicSpotServiceResMapper sysScenicSpotServiceResMapper;

    @Autowired
    private SysScenicSpotCustomTypeMapper sysScenicSpotCustomTypeMapper;

    @Value("${filePatheGetPicUrl}")
    private String filePatheGetPicUrl;

    @Value("${filePatheGetPicPaht}")
    private String filePatheGetPicPaht;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    /**
     * @Author 郭凯
     * @Description 服务项管理列表查询
     * @Date 10:25 2020/7/25
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getServiceResList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotServiceRes> sysScenicSpotServiceResList = sysScenicSpotServiceResMapper.getServiceResList(search);
        if (sysScenicSpotServiceResList.size() != 0){
            PageInfo<SysScenicSpotServiceRes> pageInfo = new PageInfo<>(sysScenicSpotServiceResList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 服务项删除
     * @Date 11:04 2020/7/25
     * @Param [serviceId]
     * @return int
    **/
    @Override
    public int delServiceRes(Long serviceId) {
        return sysScenicSpotServiceResMapper.deleteByPrimaryKey(serviceId);
    }

    /**
     * @Author 郭凯
     * @Description 新增服务项
     * @Date 16:55 2020/8/1
     * @Param [file, sysScenicSpotServiceRes]
     * @return int
    **/
    @Override
    public int addServiceRes(MultipartFile file, SysScenicSpotServiceRes sysScenicSpotServiceRes) {
        //查询服务项
        SysScenicSpotCustomType sysScenicSpotCustomType = sysScenicSpotCustomTypeMapper.selectCustomTypeByType(sysScenicSpotServiceRes.getServiceType());
        if (!file.isEmpty()){
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".png") || type.equals(".jpg")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = filePatheGetPicPaht + filename;// 存放位置
                File destFile = new File(path);
                try {
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
                    String upload = fileUploadUtil.upload(file, filePatheGetPicPaht.substring(1) + filename);
                    System.out.println(upload);

                } catch (IOException e) {
                    e.printStackTrace();
                }//复制文件到指定目录
                sysScenicSpotServiceRes.setServiceId(IdUtils.getSeqId());
                sysScenicSpotServiceRes.setServiceName(sysScenicSpotCustomType.getTypeName());
                sysScenicSpotServiceRes.setServiceNamePinYin(Tinypinyin.tinypinyin(sysScenicSpotServiceRes.getServiceName()));
                sysScenicSpotServiceRes.setServicePic(filePatheGetPicUrl + filename);
                sysScenicSpotServiceRes.setCreateDate(DateUtil.currentDateTime());
                sysScenicSpotServiceRes.setUpdateDate(DateUtil.currentDateTime());
                return sysScenicSpotServiceResMapper.insertSelective(sysScenicSpotServiceRes);
            }else {
                return 2;
            }
        }else{
            sysScenicSpotServiceRes.setServiceId(IdUtils.getSeqId());
            sysScenicSpotServiceRes.setServiceName(sysScenicSpotCustomType.getTypeName());
            sysScenicSpotServiceRes.setServiceNamePinYin(Tinypinyin.tinypinyin(sysScenicSpotServiceRes.getServiceName()));
            sysScenicSpotServiceRes.setServicePic("");
            sysScenicSpotServiceRes.setCreateDate(DateUtil.currentDateTime());
            sysScenicSpotServiceRes.setUpdateDate(DateUtil.currentDateTime());
            return sysScenicSpotServiceResMapper.insertSelective(sysScenicSpotServiceRes);
        }
    }

    /**
     * @Author 郭凯
     * @Description 查询自定义类型
     * @Date 10:03 2020/11/2
     * @Param []
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysScenicSpotCustomType>
    **/
    @Override
    public List<SysScenicSpotCustomType> selectCustomType() {
        return sysScenicSpotCustomTypeMapper.selectCustomType();
    }

    /**
     * @Author 郭凯
     * @Description 修改服务项
     * @Date 10:29 2020/11/2
     * @Param [file, sysScenicSpotServiceRes]
     * @return int
    **/
    @Override
    public int editServiceRes(MultipartFile file, SysScenicSpotServiceRes sysScenicSpotServiceRes) {
        //查询服务项
        SysScenicSpotCustomType sysScenicSpotCustomType = sysScenicSpotCustomTypeMapper.selectCustomTypeByType(sysScenicSpotServiceRes.getServiceType());
        if (!file.isEmpty()){
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".png") || type.equals(".jpg")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = filePatheGetPicPaht + filename;// 存放位置
                File destFile = new File(path);
                try {
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
                    String upload = fileUploadUtil.upload(file, filePatheGetPicPaht.substring(1) + filename);
                    System.out.println(upload);

                } catch (IOException e) {
                    e.printStackTrace();
                }//复制文件到指定目录
                sysScenicSpotServiceRes.setServiceName(sysScenicSpotCustomType.getTypeName());
                sysScenicSpotServiceRes.setServiceNamePinYin(Tinypinyin.tinypinyin(sysScenicSpotServiceRes.getServiceName()));
                sysScenicSpotServiceRes.setServicePic(filePatheGetPicUrl + filename);
                sysScenicSpotServiceRes.setUpdateDate(DateUtil.currentDateTime());
                return sysScenicSpotServiceResMapper.updateByPrimaryKeySelective(sysScenicSpotServiceRes);
            }else {
                return 2;
            }
        }else{
            sysScenicSpotServiceRes.setServiceName(sysScenicSpotCustomType.getTypeName());
            sysScenicSpotServiceRes.setServiceNamePinYin(Tinypinyin.tinypinyin(sysScenicSpotServiceRes.getServiceName()));
            sysScenicSpotServiceRes.setUpdateDate(DateUtil.currentDateTime());
            return sysScenicSpotServiceResMapper.updateByPrimaryKeySelective(sysScenicSpotServiceRes);
        }
    }

    @Override
    public int addSysRes(SysResExcel res) {
        return sysScenicSpotServiceResMapper.addSysRes(res);
    }

    /**
     * 查询全部服务项
     * @return
     */
    @Override
    public List<SysScenicSpotServiceRes> getScenicSpotServiceResAll() {
        List<SysScenicSpotServiceRes> list = sysScenicSpotServiceResMapper.getScenicSpotServiceResAll();
        return list;
    }

    @Override
    public int upSysRes(SysResExcel res) {
        return sysScenicSpotServiceResMapper.upSysRes(res);
    }

    @Override
    public SysResExcel selSysRes(SysResExcel res) {
        return sysScenicSpotServiceResMapper.selSysRes(res);
    }

    @Override
    public List<SysResExcel> getSysResExcel(Map<String, Object> search) {
        return sysScenicSpotServiceResMapper.getSysResExcel(search);
    }
}
