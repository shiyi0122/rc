package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysRobotMapResMapper;
import com.hna.hka.archive.management.system.model.SysRobotMapRes;
import com.hna.hka.archive.management.system.service.SysRobotMapResService;
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
 * @ClassName: SysRobotMapResServiceImpl
 * @Author: 郭凯
 * @Description: 地图资源管理业务层（实现）
 * @Date: 2020/11/16 15:58
 * @Version: 1.0
 */
@Service
@Transactional
public class SysRobotMapResServiceImpl implements SysRobotMapResService {

    @Autowired
    private SysRobotMapResMapper sysRobotMapResMapper;

    @Value("${GET_MAP_PAHT}")
    private String GET_MAP_PAHT;

    @Value("${GET_MAP_URL}")
    private String GET_MAP_URL;

    @Value("${GET_APP_MAP_PAHT}")
    private String GET_APP_MAP_PAHT;


    /**
     * @Author 郭凯
     * @Description 地图资源管理列表查询
     * @Date 15:59 2020/11/16
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getRobotMapResList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotMapRes> sysRobotMapResList = sysRobotMapResMapper.getRobotMapResList(search);
        if (sysRobotMapResList.size() != 0){
            PageInfo<SysRobotMapRes> pageInfo = new PageInfo<>(sysRobotMapResList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 新增地图资源
     * @Date 16:44 2020/11/16
     * @Param [file, sysRobotMapRes]
     * @return int
    **/
    @Override
    public int addRobotMapRes(MultipartFile file, SysRobotMapRes sysRobotMapRes) {
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".zip")) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = GET_MAP_PAHT + filename;// 存放位置
            File destFile = new File(path);
            try {
                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
                //阿里文件存储
//                String upload = fileUploadUtil.upload(file, GET_MAP_PAHT.substring(1) + filename);
//                System.out.println(upload);
            } catch (IOException e) {
                e.printStackTrace();
            }//复制文件到指定目录
            sysRobotMapRes.setResId(IdUtils.getSeqId());
            sysRobotMapRes.setResUrl(GET_MAP_URL+filename);
            String size = Exam_getSize.getSize(file);
            sysRobotMapRes.setResSize(size);
            sysRobotMapRes.setCreateDate(DateUtil.currentDateTime());
            sysRobotMapRes.setUpdateDate(DateUtil.currentDateTime());
            return sysRobotMapResMapper.insertSelective(sysRobotMapRes);
        }else {
            return 2;
        }
    }

    /**
     * @Author 郭凯
     * @Description 删除地图资源
     * @Date 17:09 2020/11/16
     * @Param [resId]
     * @return int
    **/
    @Override
    public int delRobotMapRes(Long resId) {
        return sysRobotMapResMapper.deleteByPrimaryKey(resId);
    }

    /**
     * @Author 郭凯
     * @Description 修改启用禁用状态
     * @Date 17:20 2020/11/16
     * @Param [sysRobotMapRes]
     * @return int
    **/
    @Override
    public int editResType(SysRobotMapRes sysRobotMapRes) {
        sysRobotMapRes.setUpdateDate(DateUtil.currentDateTime());
        return sysRobotMapResMapper.updateByPrimaryKeySelective(sysRobotMapRes);
    }

    /**
     * @Author 郭凯
     * @Description 地图资源查询
     * @Date 15:36 2020/11/23
     * @Param [scenicSpotId]
     * @return com.hna.hka.archive.management.system.model.SysRobotMapRes
    **/
    @Override
    public SysRobotMapRes getSysRobotMapResByScenicSpotId(String scenicSpotId) {
        SysRobotMapRes SysRobotMapRes = sysRobotMapResMapper.getSysRobotMapResByScenicSpotId(scenicSpotId);
        SysRobotMapRes.setResUrl(GET_APP_MAP_PAHT+SysRobotMapRes.getResUrl());
        return null;
    }
}
