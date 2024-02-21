package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.managerApp.dao.SysRobotAdministratorsVersionMapper;
import com.hna.hka.archive.management.managerApp.model.SysRobotAdministratorsVersion;
import com.hna.hka.archive.management.system.service.SysRobotAdministratorsVersionService;
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
 * @ClassName: SysRobotAdministratorsVersionServiceImpl
 * @Author: 郭凯
 * @Description: APP版本管理业务层（实现）
 * @Date: 2020/11/19 9:12
 * @Version: 1.0
 */
@Service
@Transactional
public class SysRobotAdministratorsVersionServiceImpl implements SysRobotAdministratorsVersionService {

    @Autowired
    private SysRobotAdministratorsVersionMapper sysRobotAdministratorsVersionMapper;

    @Value("${filePathPadPaht}")
    private String filePathPadPaht;

    @Value("${filePathPadUrl}")
    private String filePathPadUrl;
    
    @Value("${GET_APP_MAP_PAHT}")
    private String GET_APP_MAP_PAHT;

    @Autowired
    FileUploadUtil fileUploadUtil;

    /**
     * @Author 郭凯
     * @Description APP版本管理列表查询
     * @Date 9:15 2020/11/19
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getAdministratorsVersionList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotAdministratorsVersion> sysRobotAdministratorsVersionList = sysRobotAdministratorsVersionMapper.getAdministratorsVersionList(search);
        if (sysRobotAdministratorsVersionList.size() != 0){
            PageInfo<SysRobotAdministratorsVersion> pageInfo = new PageInfo<>(sysRobotAdministratorsVersionList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description APP删除
     * @Date 9:38 2020/11/19
     * @Param [versionId]
     * @return int
    **/
    @Override
    public int delAdministratorsVersion(Long versionId) {
    	SysRobotAdministratorsVersion administratorsVersion = sysRobotAdministratorsVersionMapper.selectByPrimaryKey(versionId);
    	int i = sysRobotAdministratorsVersionMapper.deleteByPrimaryKey(versionId);
    	if (i == 1) {
    		String versionUrl = administratorsVersion.getVersionUrl();
        	String result = versionUrl.substring(versionUrl.lastIndexOf('/') + 1).trim();
    		FileUtilOne.deleteServerFile(filePathPadPaht + result);
		}
        return i;
    }

    /**
     * @Author 郭凯
     * @Description APP上传
     * @Date 12:42 2020/11/19
     * @Param [file, sysRobotAdministratorsVersion]
     * @return int
    **/
    @Override
    public int addAdministratorsVersion(MultipartFile file, SysRobotAdministratorsVersion sysRobotAdministratorsVersion) {
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".apk"));// 取文件格式后缀名
        if (type.equals(".apk")) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = filePathPadPaht + filename;// 存放位置
            File destFile = new File(path);
            try {
                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

//                String upload = fileUploadUtil.upload(file, filePathPadPaht.substring(1) + filename);

            } catch (IOException e) {
                e.printStackTrace();
            }//复制文件到指定目录
            sysRobotAdministratorsVersion.setVersionId(IdUtils.getSeqId());
            sysRobotAdministratorsVersion.setVersionUrl(filePathPadUrl + filename);
            sysRobotAdministratorsVersion.setCreateDate(DateUtil.currentDateTime());
            return sysRobotAdministratorsVersionMapper.insertSelective(sysRobotAdministratorsVersion);
        }else {
            return 2;
        }
    }

    /**
     * @Author 郭凯
     * @Description 查询最新版本号
     * @Date 12:46 2020/11/19
     * @Param []
     * @return com.hna.hka.archive.management.managerApp.model.SysRobotAdministratorsVersion
    **/
    @Override
    public SysRobotAdministratorsVersion getAdministratorsVersion() {
    	SysRobotAdministratorsVersion administratorsVersion = sysRobotAdministratorsVersionMapper.getAdministratorsVersion();
    	administratorsVersion.setVersionUrl(GET_APP_MAP_PAHT + administratorsVersion.getVersionUrl());
        return administratorsVersion;
    }

    /**
     * @Author 郭凯
     * @Description 编辑APP
     * @Date 14:02 2020/11/19
     * @Param [sysRobotAdministratorsVersion]
     * @return int
    **/
    @Override
    public int editAdministratorsVersion(SysRobotAdministratorsVersion sysRobotAdministratorsVersion) {
        return sysRobotAdministratorsVersionMapper.updateByPrimaryKeySelective(sysRobotAdministratorsVersion);
    }
}
