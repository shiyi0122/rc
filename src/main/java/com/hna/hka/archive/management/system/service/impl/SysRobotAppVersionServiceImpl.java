package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysRobotAppVersionMapper;
import com.hna.hka.archive.management.system.model.SysRobotAppVersion;
import com.hna.hka.archive.management.system.service.SysRobotAppVersionService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.FileUploadUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysRobotAppVersionServiceImpl
 * @Author: 郭凯
 * @Description: PAD管理业务层（实现）
 * @Date: 2020/5/29 16:56
 * @Version: 1.0
 */
@Service
@Transactional
public class SysRobotAppVersionServiceImpl implements SysRobotAppVersionService {

    @Autowired
    private SysRobotAppVersionMapper sysRobotAppVersionMapper;

    @Value("${filePathPadPaht}")
    private String filePathPadPaht;

    @Value("${filePathPadUrl}")
    private String filePathPadUrl;

    @Autowired
    private FileUploadUtil fileUploadUtil;


    /**
     * @Author 郭凯
     * @Description PAD版本管理列表查询
     * @Date 17:00 2020/5/29
     * @Param [pageNum, pageSize, sysRobotAppVersion]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     **/
    @Override
    public PageDataResult getRobotVersionPadList(Integer pageNum, Integer pageSize, SysRobotAppVersion sysRobotAppVersion) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotAppVersion> sysRobotAppVersionList = sysRobotAppVersionMapper.getRobotVersionPadList(sysRobotAppVersion);
        if(sysRobotAppVersionList.size() != 0){
            PageInfo<SysRobotAppVersion> pageInfo = new PageInfo<>(sysRobotAppVersionList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 查询当前版本号
     * @Date 13:41 2020/5/31
     * @Param [scenicSpotId]
     * @return com.hna.hka.archive.management.system.model.SysRobotAppVersion
    **/
    @Override
    public SysRobotAppVersion getAppVersionNumber(Long scenicSpotId) {
        return sysRobotAppVersionMapper.getAppVersionNumber(scenicSpotId);
    }

    /**
     * @Author 郭凯
     * @Description 新增PAD信息
     * @Date 13:53 2020/5/31
     * @Param [sysRobotAppVersion, file]
     * @return int
    **/
    @Override
    public int addRobotVersionPad(SysRobotAppVersion sysRobotAppVersion, MultipartFile file) {
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (".apk".equals(type)) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = filePathPadPaht+ filename;// 存放位置
            File destFile = new File(path);
            try {
                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

                String upload = fileUploadUtil.upload(file, filePathPadPaht.substring(1) + filename);
                System.out.println(upload);
            } catch (IOException e) {
                e.printStackTrace();
            }//复制文件到指定目录
            SysRobotAppVersion sysRobotVersion = new SysRobotAppVersion();
            sysRobotVersion.setVersionId(IdUtils.getSeqId());
            sysRobotVersion.setScenicSpotId(sysRobotAppVersion.getScenicSpotId());
            sysRobotVersion.setVersionNumber(sysRobotAppVersion.getVersionNumber());
            sysRobotVersion.setVersionDescription(sysRobotAppVersion.getVersionDescription());
            sysRobotVersion.setVersionUrl(filePathPadUrl+filename);
            sysRobotVersion.setCreateDate(DateUtil.currentDateTime());
            sysRobotAppVersionMapper.insert(sysRobotVersion);
            return sysRobotAppVersionMapper.updateById(sysRobotAppVersion.getScenicSpotId(),sysRobotAppVersion.getAutoUpdateMonitor());
        }else{
            return 2;
        }
    }

    /**
     * @Author 郭凯
     * @Description 删除PAD信息
     * @Date 15:46 2020/5/31
     * @Param [versionId]
     * @return int
    **/
    @Override
    public int delRobotVersionPad(Long versionId) {
        return sysRobotAppVersionMapper.deleteByPrimaryKey(versionId);
    }

    /**
     * @Author 郭凯
     * @Description 修改PAD信息
     * @Date 10:26 2020/9/9
     * @Param [sysRobotAppVersion]
     * @return int
    **/
    @Override
    public int editRobotVersionPad(SysRobotAppVersion sysRobotAppVersion) {
        sysRobotAppVersionMapper.updateById(sysRobotAppVersion.getScenicSpotId(),sysRobotAppVersion.getAutoUpdateMonitor());
        return sysRobotAppVersionMapper.updateByPrimaryKeySelective(sysRobotAppVersion);
    }
}
