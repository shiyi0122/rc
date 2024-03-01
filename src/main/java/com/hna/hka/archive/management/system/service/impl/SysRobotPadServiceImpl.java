package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysRobotPadMapper;
import com.hna.hka.archive.management.system.model.SysRobotPad;
import com.hna.hka.archive.management.system.service.SysRobotPadService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysRobotPadServiceImpl
 * @Author: 郭凯
 * @Description: 景区PAD版本管理业务层（实现）
 * @Date: 2020/12/14 16:26
 * @Version: 1.0
 */
@Service
@Transactional
public class SysRobotPadServiceImpl implements SysRobotPadService {

    @Autowired
    private SysRobotPadMapper sysRobotPadMapper;

    @Value("${filePathPadPaht}")
    private String filePathPadPaht;

    @Value("${filePathPadUrl}")
    private String filePathPadUrl;

    @Autowired
    private FileUploadUtil fileUploadUtil;


    /**
     * @Author 郭凯
     * @Description PAD版本管理列表查询
     * @Date 16:27 2020/12/14
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getRobotPadList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotPad> sysRobotPadList = sysRobotPadMapper.getRobotMapResList(search);
        if (sysRobotPadList.size() != 0){
            PageInfo<SysRobotPad> pageInfo = new PageInfo<>(sysRobotPadList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 查询最新版本号
     * @Date 9:12 2020/12/15
     * @Param []
     * @return com.hna.hka.archive.management.system.model.SysRobotPad
    **/
    @Override
    public SysRobotPad getAppPadNumber() {
        return sysRobotPadMapper.getAppPadNumber();
    }

    /**
     * @Author 郭凯
     * @Description 新增PAD
     * @Date 9:29 2020/12/15
     * @Param [sysRobotPad, file]
     * @return int
    **/
    @Override
    public int addRobotPad(SysRobotPad sysRobotPad, MultipartFile file) {
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (".apk".equals(type)) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = filePathPadPaht+ filename;// 存放位置
            File destFile = new File(path);
            try {
                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

                String upload = fileUploadUtil.upload(file, filePathPadPaht.substring(1) + filename);
                System.out.println("PAD下载地址："+upload);
            } catch (IOException e) {
                e.printStackTrace();
            }//复制文件到指定目录
            sysRobotPad.setPadId(IdUtils.getSeqId());
            sysRobotPad.setPadUrl(filePathPadUrl+filename);
            sysRobotPad.setCreateDate(DateUtil.currentDateTime());
            return sysRobotPadMapper.insertSelective(sysRobotPad);
        }else{
            return 2;
        }
    }

    /**
     * @Author 郭凯
     * @Description 编辑PAD信息
     * @Date 10:13 2020/12/15
     * @Param [sysRobotPad]
     * @return int
    **/
    @Override
    public int editRobotPad(SysRobotPad sysRobotPad) {
        sysRobotPad.setUpdateDate(DateUtil.currentDateTime());
        return sysRobotPadMapper.updateByPrimaryKeySelective(sysRobotPad);
    }

    /**
     * @Author 郭凯
     * @Description 删除PAD
     * @Date 10:16 2020/12/15
     * @Param [padId]
     * @return int
    **/
    @Override
    public int delRobotPad(Long padId) {
    	SysRobotPad robotPad = sysRobotPadMapper.selectByPrimaryKey(padId);
    	int i = sysRobotPadMapper.deleteByPrimaryKey(padId);
    	if (i == 1) {
    		String padUrl = robotPad.getPadUrl();
        	String result = padUrl.substring(padUrl.lastIndexOf('/') + 1).trim();
    		FileUtilOne.deleteServerFile(filePathPadPaht + result);
		}
        return i;
    }

    /**
     * @Author 郭凯
     * @Description 查询所有PAD信息
     * @Date 13:22 2020/12/15
     * @Param []
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysRobotPad>
    **/
    @Override
    public List<SysRobotPad> getRobotPad() {
        Map<String, String> search = new HashMap<>();
        List<SysRobotPad> sysRobotPadList = sysRobotPadMapper.getRobotMapResList(search);
        return sysRobotPadList;
    }
}
