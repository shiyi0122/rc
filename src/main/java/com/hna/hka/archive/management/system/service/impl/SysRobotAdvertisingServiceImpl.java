package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysRobotAdvertisingMapper;
import com.hna.hka.archive.management.system.model.SysRobotAdvertising;
import com.hna.hka.archive.management.system.service.SysRobotAdvertisingService;
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
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysRobotAdvertisingServiceImpl
 * @Author: 郭凯
 * @Description: 轮播图管理业务层（实现）
 * @Date: 2020/6/4 15:25
 * @Version: 1.0
 */
@Service
@Transactional
public class SysRobotAdvertisingServiceImpl implements SysRobotAdvertisingService {

    @Autowired
    private SysRobotAdvertisingMapper sysRobotAdvertisingMapper;

    @Value("${filePatheGetPicPaht}")
    private String filePatheGetPicPaht;

    @Value("${filePatheGetPicUrl}")
    private String filePatheGetPicUrl;

    @Autowired
    private FileUploadUtil fileUploadUtil;


    /**
     * @Author 郭凯
     * @Description 轮播图管理列表查询
     * @Date 15:30 2020/6/4
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getAdvertisingList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotAdvertising> SysRobotAdvertisingList = sysRobotAdvertisingMapper.getAdvertisingList(search);
        if (SysRobotAdvertisingList.size() != 0){
            PageInfo<SysRobotAdvertising> pageInfo = new PageInfo<>(SysRobotAdvertisingList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 新增轮播图
     * @Date 17:18 2020/6/4
     * @Param [sysRobotAdvertising, file]
     * @return int
    **/
    @Override
    public int addAdvertising(SysRobotAdvertising sysRobotAdvertising, MultipartFile file) {
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".png") || type.equals(".jpg")) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = filePatheGetPicPaht + filename;// 存放位置
            File destFile = new File(path);
            try {
                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//              上传阿里文件存储
//                String upload = fileUploadUtil.upload(file, filePatheGetPicPaht.substring(1) + filename);
//                System.out.println(upload);
            } catch (IOException e) {
                e.printStackTrace();
            }//复制文件到指定目录
            SysRobotAdvertising adverising = new SysRobotAdvertising();
            adverising.setAdvertisingId(IdUtils.getSeqId());
            adverising.setAdvertisingScenicSpotId(sysRobotAdvertising.getAdvertisingScenicSpotId());
            adverising.setAdvertisingUrl(filePatheGetPicUrl+filename);
            adverising.setAdvertisingValid("1");
            adverising.setAdvertisingOrder(sysRobotAdvertising.getAdvertisingOrder());
            adverising.setAdvertisingName(sysRobotAdvertising.getAdvertisingName());
            adverising.setCreateTime(DateUtil.currentDateTime());
            adverising.setUpdateTime(DateUtil.currentDateTime());
            return sysRobotAdvertisingMapper.insertSelective(adverising);
        }else {
            return 2;
        }
    }

    /**
     * @Author 郭凯
     * @Description 删除轮播图
     * @Date 15:20 2020/6/5
     * @Param [advertisingId]
     * @return int
    **/
    @Override
    public int delAdvertising(Long advertisingId) {
        return sysRobotAdvertisingMapper.deleteByPrimaryKey(advertisingId);
    }

    /**
     * @Author 郭凯
     * @Description 修改轮播图
     * @Date 15:59 2020/6/5
     * @Param [sysRobotAdvertising, file]
     * @return int
    **/
    @Override
    public int editAdvertising(SysRobotAdvertising sysRobotAdvertising, MultipartFile file) {
        SysRobotAdvertising adverising = new SysRobotAdvertising();
        if(!file.isEmpty()){
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".png") || type.equals(".jpg")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = filePatheGetPicPaht + filename;// 存放位置
                File destFile = new File(path);
                try {
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
                    //上传阿里文件存储
//                String upload = fileUploadUtil.upload(file, filePatheGetPicPaht.substring(1) + filename);
//                System.out.println(upload);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                adverising.setAdvertisingUrl(filePatheGetPicUrl+filename);
            }else{
                return 2;
            }
        }
        adverising.setAdvertisingId(sysRobotAdvertising.getAdvertisingId());
        adverising.setAdvertisingOrder(sysRobotAdvertising.getAdvertisingOrder());
        adverising.setAdvertisingName(sysRobotAdvertising.getAdvertisingName());
        adverising.setUpdateTime(DateUtil.currentDateTime());
        return sysRobotAdvertisingMapper.updateByPrimaryKeySelective(adverising);
    }

    /**
     * @Author 郭凯
     * @Description 修改启用禁用状态
     * @Date 16:37 2020/6/5
     * @Param [sysRobotAdvertising]
     * @return int
    **/
    @Override
    public int editValid(SysRobotAdvertising sysRobotAdvertising) {
        return sysRobotAdvertisingMapper.updateByPrimaryKeySelective(sysRobotAdvertising);
    }
}
