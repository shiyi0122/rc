package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysRobotCorpusAudioAndVideoMapper;
import com.hna.hka.archive.management.system.dao.SysRobotCorpusMediaExtendMapper;
import com.hna.hka.archive.management.system.model.SysRobotCorpusAudioAndVideo;
import com.hna.hka.archive.management.system.model.SysRobotCorpusMediaExtend;
import com.hna.hka.archive.management.system.service.SysRobotCorpusAudioAndVideoService;
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
 * @ClassName: SysRobotCorpusAudioAndVideoServiceImpl
 * @Author: 郭凯
 * @Description: 语音媒体资源业务层（实现）
 * @Date: 2020/6/15 13:30
 * @Version: 1.0
 */
@Service
@Transactional
public class SysRobotCorpusAudioAndVideoServiceImpl implements SysRobotCorpusAudioAndVideoService {

    @Autowired
    private SysRobotCorpusAudioAndVideoMapper sysRobotCorpusAudioAndVideoMapper;

    @Autowired
    private SysRobotCorpusMediaExtendMapper sysRobotCorpusMediaExtendMapper;

    @Value("${semanticsAudioPaht}")
    private String semanticsAudioPaht;

    @Value("${semanticsAudioUrl}")
    private String semanticsAudioUrl;

    @Value("${semanticsVideoPaht}")
    private String semanticsVideoPaht;

    @Value("${semanticsVideoUrl}")
    private String semanticsVideoUrl;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    /**
     * @Author 郭凯
     * @Description 语音媒体资源列表查询
     * @Date 13:33 2020/6/15
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getCorpusAudioAndVideoList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotCorpusAudioAndVideo> sysRobotCorpusAudioAndVideoList = sysRobotCorpusAudioAndVideoMapper.getCorpusAudioAndVideoList(search);
        for(SysRobotCorpusAudioAndVideo sysRobotCorpus : sysRobotCorpusAudioAndVideoList) {
            if ("2019924".equals(String.valueOf(sysRobotCorpus.getScenicSpotId()))) {
                sysRobotCorpus.setScenicSpotName("通用类型");
                sysRobotCorpus.setMediaCurrencyType("2");
            }else{
                sysRobotCorpus.setMediaCurrencyType("1");
            }
        }
        if (sysRobotCorpusAudioAndVideoList.size() != 0){
            PageInfo<SysRobotCorpusAudioAndVideo> pageInfo = new PageInfo<>(sysRobotCorpusAudioAndVideoList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 语音媒体资源查看详情列表查询
     * @Date 10:33 2020/7/21
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getViewDetailsList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotCorpusMediaExtend> sysRobotCorpusMediaExtendList = sysRobotCorpusMediaExtendMapper.getViewDetailsList(search);
        if (sysRobotCorpusMediaExtendList.size() != 0){
            PageInfo<SysRobotCorpusMediaExtend> pageInfo = new PageInfo<>(sysRobotCorpusMediaExtendList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 语音媒体资源删除
     * @Date 11:36 2020/7/21
     * @Param [mediaId]
     * @return int
    **/
    @Override
    public int delCorpusAudioAndVideo(Long mediaId) {
        return sysRobotCorpusAudioAndVideoMapper.deleteByPrimaryKey(mediaId);
    }

    /**
     * @Author 郭凯
     * @Description 添加语音媒体资源
     * @Date 10:40 2020/7/28
     * @Param [file, sysRobotCorpusMediaExtend]
     * @return int
    **/
    @Override
    public int addCorpusAudio(MultipartFile file, SysRobotCorpusAudioAndVideo sysRobotCorpusAudioAndVideo) {
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".mp3")) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = semanticsAudioPaht + filename;// 存放位置
            File destFile = new File(path);
            try {
                // FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
                //阿里文件存储
//                String upload = fileUploadUtil.upload(file, semanticsAudioPaht.substring(1) + filename);
//                System.out.println(upload);
            } catch (IOException e) {
                e.printStackTrace();
            } // 复制文件到指定目录
            sysRobotCorpusAudioAndVideo.setMediaId(IdUtils.getSeqId());
            sysRobotCorpusAudioAndVideo.setMediaNamePingYin(Tinypinyin.tinypinyin(sysRobotCorpusAudioAndVideo.getMediaName()));
            sysRobotCorpusAudioAndVideo.setMediaAuthorPingYin(Tinypinyin.tinypinyin(sysRobotCorpusAudioAndVideo.getMediaAuthor()));
            sysRobotCorpusAudioAndVideo.setMediaResourcesType("1");
            sysRobotCorpusAudioAndVideo.setMediaUrl(semanticsAudioUrl+filename);
            sysRobotCorpusAudioAndVideo.setCreateDate(DateUtil.currentDateTime());
            sysRobotCorpusAudioAndVideo.setUpdateDate(DateUtil.currentDateTime());
            return sysRobotCorpusAudioAndVideoMapper.insertSelective(sysRobotCorpusAudioAndVideo);
        } else {
            return 2;
        }
    }

    /**
     * @Author 郭凯
     * @Description 添加语音媒体资源视频
     * @Date 11:37 2020/7/28
     * @Param [file, sysRobotCorpusAudioAndVideo]
     * @return int
    **/
    @Override
    public int addCorpusVideo(MultipartFile file, SysRobotCorpusAudioAndVideo sysRobotCorpusAudioAndVideo) {
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".mp4")) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = semanticsVideoPaht + filename;// 存放位置
            File destFile = new File(path);
            try {
                // FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
                //阿里文件存储
//                String upload = fileUploadUtil.upload(file, semanticsVideoPaht.substring(1) + filename);
//                System.out.println(upload);
            } catch (IOException e) {
                e.printStackTrace();
            } // 复制文件到指定目录
            sysRobotCorpusAudioAndVideo.setMediaId(IdUtils.getSeqId());
            sysRobotCorpusAudioAndVideo.setMediaNamePingYin(Tinypinyin.tinypinyin(sysRobotCorpusAudioAndVideo.getMediaName()));
            sysRobotCorpusAudioAndVideo.setMediaAuthorPingYin(Tinypinyin.tinypinyin(sysRobotCorpusAudioAndVideo.getMediaAuthor()));
            sysRobotCorpusAudioAndVideo.setMediaResourcesType("2");
            sysRobotCorpusAudioAndVideo.setMediaUrl(semanticsVideoUrl+filename);
            sysRobotCorpusAudioAndVideo.setCreateDate(DateUtil.currentDateTime());
            sysRobotCorpusAudioAndVideo.setUpdateDate(DateUtil.currentDateTime());
            return sysRobotCorpusAudioAndVideoMapper.insertSelective(sysRobotCorpusAudioAndVideo);
        } else {
            return 2;
        }
    }

    /**
     * @Author 郭凯
     * @Description 查询媒体资源回显数据
     * @Date 14:39 2020/7/28
     * @Param [mediaId]
     * @return com.hna.hka.archive.management.system.model.SysRobotCorpusAudioAndVideo
    **/
    @Override
    public SysRobotCorpusAudioAndVideo getCorpusAudioAndVideoById(Long mediaId) {
        SysRobotCorpusAudioAndVideo sysRobotCorpusAudioAndVideo = sysRobotCorpusAudioAndVideoMapper.selectByPrimaryKey(mediaId);
        if ("2019924".equals(sysRobotCorpusAudioAndVideo.getScenicSpotId().toString())){
            sysRobotCorpusAudioAndVideo.setScenicSpotId(Long.parseLong("2"));
        }else{
            sysRobotCorpusAudioAndVideo.setScenicSpotId(Long.parseLong("1"));
        }
        return sysRobotCorpusAudioAndVideo;
    }

    /**
     * @Author 郭凯
     * @Description 修改语音媒体资源（音频）
     * @Date 16:08 2020/7/28
     * @Param [file, sysRobotCorpusAudioAndVideo]
     * @return int
    **/
    @Override
    public int editCorpusAudio(MultipartFile file, SysRobotCorpusAudioAndVideo sysRobotCorpusAudioAndVideo) {
        if (!file.isEmpty()){
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".mp3")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = semanticsAudioPaht + filename;// 存放位置
                File destFile = new File(path);
                try {
                    // FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
                    //阿里文件存储
//                String upload = fileUploadUtil.upload(file, semanticsAudioPaht.substring(1) + filename);
//                System.out.println(upload);
                } catch (IOException e) {
                    e.printStackTrace();
                } // 复制文件到指定目录
                sysRobotCorpusAudioAndVideo.setMediaUrl(semanticsAudioUrl+filename);
            } else {
                return 2;
            }
        }
        sysRobotCorpusAudioAndVideo.setMediaNamePingYin(Tinypinyin.tinypinyin(sysRobotCorpusAudioAndVideo.getMediaName()));
        sysRobotCorpusAudioAndVideo.setMediaAuthorPingYin(Tinypinyin.tinypinyin(sysRobotCorpusAudioAndVideo.getMediaAuthor()));
        sysRobotCorpusAudioAndVideo.setMediaResourcesType("1");
        sysRobotCorpusAudioAndVideo.setUpdateDate(DateUtil.currentDateTime());
        return sysRobotCorpusAudioAndVideoMapper.updateByPrimaryKeySelective(sysRobotCorpusAudioAndVideo);
    }

    /**
     * @Author 郭凯
     * @Description 修改语音媒体资源（视频）
     * @Date 16:28 2020/7/28
     * @Param [file, sysRobotCorpusAudioAndVideo]
     * @return int
    **/
    @Override
    public int editCorpusVideo(MultipartFile file, SysRobotCorpusAudioAndVideo sysRobotCorpusAudioAndVideo) {
        if (!file.isEmpty()){
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".mp4")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = semanticsVideoPaht + filename;// 存放位置
                File destFile = new File(path);
                try {
                    // FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
                    //阿里文件存储
//                String upload = fileUploadUtil.upload(file, semanticsVideoPaht.substring(1) + filename);
//                System.out.println(upload);
                } catch (IOException e) {
                    e.printStackTrace();
                } // 复制文件到指定目录
                sysRobotCorpusAudioAndVideo.setMediaUrl(semanticsVideoUrl+filename);
            } else {
                return 2;
            }
        }
        sysRobotCorpusAudioAndVideo.setMediaNamePingYin(Tinypinyin.tinypinyin(sysRobotCorpusAudioAndVideo.getMediaName()));
        sysRobotCorpusAudioAndVideo.setMediaAuthorPingYin(Tinypinyin.tinypinyin(sysRobotCorpusAudioAndVideo.getMediaAuthor()));
        sysRobotCorpusAudioAndVideo.setMediaResourcesType("2");
        sysRobotCorpusAudioAndVideo.setUpdateDate(DateUtil.currentDateTime());
        return sysRobotCorpusAudioAndVideoMapper.updateByPrimaryKeySelective(sysRobotCorpusAudioAndVideo);
    }

    /**
     * @Author 郭凯
     * @Description 新增资源详情
     * @Date 16:58 2020/7/28
     * @Param [sysRobotCorpusMediaExtend]
     * @return int
    **/
    @Override
    public int addCorpusMediaExtend(SysRobotCorpusMediaExtend sysRobotCorpusMediaExtend) {
        sysRobotCorpusMediaExtend.setMediaExtendId(IdUtils.getSeqId());
        sysRobotCorpusMediaExtend.setMediaExtendNamePingYin(Tinypinyin.tinypinyin(sysRobotCorpusMediaExtend.getMediaExtendName()));
        sysRobotCorpusMediaExtend.setMediaExtendAuthorPingYin(Tinypinyin.tinypinyin(sysRobotCorpusMediaExtend.getMediaExtendAuthor()));
        sysRobotCorpusMediaExtend.setCreateDate(DateUtil.currentDateTime());
        sysRobotCorpusMediaExtend.setUpdateDate(DateUtil.currentDateTime());
        return sysRobotCorpusMediaExtendMapper.insertSelective(sysRobotCorpusMediaExtend);
    }

    /**
     * @Author 郭凯
     * @Description 删除语音媒体资源详情
     * @Date 17:17 2020/7/28
     * @Param [mediaExtendId]
     * @return int
    **/
    @Override
    public int delCorpusMediaExtend(Long mediaExtendId) {
        return sysRobotCorpusMediaExtendMapper.deleteByPrimaryKey(mediaExtendId);
    }

    /**
     * @Author 郭凯
     * @Description 编辑资源详情
     * @Date 17:48 2020/7/28
     * @Param [sysRobotCorpusMediaExtend]
     * @return int
     **/
    @Override
    public int editCorpusMediaExtend(SysRobotCorpusMediaExtend sysRobotCorpusMediaExtend) {
        sysRobotCorpusMediaExtend.setMediaExtendNamePingYin(Tinypinyin.tinypinyin(sysRobotCorpusMediaExtend.getMediaExtendName()));
        sysRobotCorpusMediaExtend.setMediaExtendAuthorPingYin(Tinypinyin.tinypinyin(sysRobotCorpusMediaExtend.getMediaExtendAuthor()));
        sysRobotCorpusMediaExtend.setUpdateDate(DateUtil.currentDateTime());
        return sysRobotCorpusMediaExtendMapper.updateByPrimaryKeySelective(sysRobotCorpusMediaExtend);
    }
}
