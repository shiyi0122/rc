package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysScenicSpotBroadcastExtendMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotBroadcastMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotResourceVersionMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcast;
import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcastExtendWithBLOBs;
import com.hna.hka.archive.management.system.model.SysScenicSpotResourceVersion;
import com.hna.hka.archive.management.system.service.SysScenicSpotBroadcastService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysScenicSpotBroadcastServiceImpl
 * @Author: 郭凯
 * @Description: 景点管理业务层（实现）
 * @Date: 2020/6/8 9:34
 * @Version: 1.0
 */
@Service
@Transactional
public class SysScenicSpotBroadcastServiceImpl implements SysScenicSpotBroadcastService {

    @Autowired
    private SysScenicSpotBroadcastMapper sysScenicSpotBroadcastMapper;

    @Autowired
    private SysScenicSpotResourceVersionMapper sysScenicSpotResourceVersionMapper;

    @Autowired
    private SysScenicSpotBroadcastExtendMapper sysScenicSpotBroadcastExtendMapper;

    @Value("${GET_BROADCAST_PIC_URL}")
    private String GET_BROADCAST_PIC_URL;

    @Value("${GET_BROADCAST_PIC_PAHT}")
    private String GET_BROADCAST_PIC_PAHT;

    @Value("${GET_BROADCAST_VODIO_URL}")
    private String GET_BROADCAST_VODIO_URL;

    @Value("${GET_BROADCAST_VODIO_PAHT}")
    private String GET_BROADCAST_VODIO_PAHT;

    @Value("${GET_BROADCAST_AUDIO_URL}")
    private String GET_BROADCAST_AUDIO_URL;

    @Value("${GET_BROADCAST_AUDIO_PAHT}")
    private String GET_BROADCAST_AUDIO_PAHT;

    @Autowired
    FileUploadUtil fileUploadUtil;

    /**
     * @Author 郭凯
     * @Description 景点管理列表查询
     * @Date 9:39 2020/6/8
     * @Param [pageNum, pageSize, sysScenicSpotBroadcast]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getBroadcastList(Integer pageNum, Integer pageSize, SysScenicSpotBroadcast sysScenicSpotBroadcast) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotBroadcast> broadcastList = sysScenicSpotBroadcastMapper.getBroadcastList(sysScenicSpotBroadcast);
        if(broadcastList.size() != 0){
            PageInfo<SysScenicSpotBroadcast> pageInfo = new PageInfo<>(broadcastList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 新增景点（同时更新版本号）
     * @Date 10:53 2020/6/8
     * @Param [broadcast]
     * @return int
    **/
    @Override
    public int addBroadcast(SysScenicSpotBroadcast broadcast) {
        broadcast.setBroadcastId(IdUtils.getSeqId());
        broadcast.setPinYinName(Tinypinyin.tinypinyin(broadcast.getBroadcastName()));
        broadcast.setCreateDate(DateUtil.currentDateTime());
        broadcast.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotBroadcastMapper.insertSelective(broadcast);
        if (i == 1) {
            //更新版本号
            SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
            sysScenicSpotResourceVersion.setScenicSpotId(broadcast.getScenicSpotId());
            sysScenicSpotResourceVersion.setResType("2");
            //查询版本号
            SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
            double resVersion = Double.parseDouble(resourceVersion.getResVersion());
            double s = 0.1;
            BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
            BigDecimal p2 = new BigDecimal(Double.toString(s));
            double a = p1.add(p2).doubleValue();
            SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
            sysScenicSpotResourceVersion2.setScenicSpotId(broadcast.getScenicSpotId());
            sysScenicSpotResourceVersion2.setResType("2");
            sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
            sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
            //修改版本号
            sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
        }
        return i;
    }

    /**
     * @Author 郭凯
     * @Description 删除景点(同时更新版本号)
     * @Date 11:23 2020/6/8
     * @Param [broadcastId]
     * @return int
    **/
    @Override
    public int delBroadcast(Long broadcastId,Long scenicSpotId) {
        int i = sysScenicSpotBroadcastMapper.deleteByPrimaryKey(broadcastId);
        if (i == 1){
            //更新版本号
            SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
            sysScenicSpotResourceVersion.setScenicSpotId(scenicSpotId);
            sysScenicSpotResourceVersion.setResType("2");
            //查询版本号
            SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
            double resVersion = Double.parseDouble(resourceVersion.getResVersion());
            double s = 0.1;
            BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
            BigDecimal p2 = new BigDecimal(Double.toString(s));
            double a = p1.add(p2).doubleValue();
            SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
            sysScenicSpotResourceVersion2.setScenicSpotId(scenicSpotId);
            sysScenicSpotResourceVersion2.setResType("2");
            sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
            sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
            //修改版本号
            sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
        }
        return i;
    }

    /**
     * @Author 郭凯
     * @Description 编辑景点
     * @Date 13:13 2020/6/8
     * @Param [broadcast]
     * @return int
    **/
    @Override
    public int editBroadcast(SysScenicSpotBroadcast broadcast) {
        broadcast.setPinYinName(Tinypinyin.tinypinyin(broadcast.getBroadcastName()));
        broadcast.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotBroadcastMapper.updateByPrimaryKeySelective(broadcast);
        if (i == 1){
            //更新版本号
            SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
            sysScenicSpotResourceVersion.setScenicSpotId(broadcast.getScenicSpotId());
            sysScenicSpotResourceVersion.setResType("2");
            //查询版本号
            SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
            double resVersion = Double.parseDouble(resourceVersion.getResVersion());
            double s = 0.1;
            BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
            BigDecimal p2 = new BigDecimal(Double.toString(s));
            double a = p1.add(p2).doubleValue();
            SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
            sysScenicSpotResourceVersion2.setScenicSpotId(broadcast.getScenicSpotId());
            sysScenicSpotResourceVersion2.setResType("2");
            sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
            sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
            //修改版本号
            sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
        }
        return i;
    }

    /**
     * @Author 郭凯
     * @Description 景点坐标查询接口
     * @Date 14:00 2020/7/24
     * @Param [scenicSpotId]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysScenicSpotBroadcast>
    **/
    @Override
    public List<SysScenicSpotBroadcast> getBroadcastListByScenicSpotId(Long scenicSpotId) {
        return sysScenicSpotBroadcastMapper.getBroadcastListByScenicSpotId(scenicSpotId);
    }

    /**
     * @Author 郭凯
     * @Description 景点内容详情列表查询
     * @Date 10:43 2020/11/6
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getBroadcastContentList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotBroadcastExtendWithBLOBs> broadcastExtendWithBLOBsList = sysScenicSpotBroadcastExtendMapper.getBroadcastContentList(search);
        if(broadcastExtendWithBLOBsList.size() != 0){
            PageInfo<SysScenicSpotBroadcastExtendWithBLOBs> pageInfo = new PageInfo<>(broadcastExtendWithBLOBsList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 景点内容新增（文字）
     * @Date 13:22 2020/11/6
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     * @return int
    **/
    @Override
    public int addBroadcastContentExtendImage(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        if (!file.isEmpty()){
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".png") || type.equals(".jpg")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = GET_BROADCAST_PIC_PAHT + filename;// 存放位置
                File destFile = new File(path);
                try {

                    // 限制大小
                    long size = file.getSize() / 1024;//kb
                    if (size >= 2048){
                        return 3;
                    }
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

                    //压缩上传
//                    CompressUtils.compress(file.getInputStream(),destFile,10000);

                    //阿里OSS文件存储_图片上传
                //  String upload = fileUploadUtil.upload(file, GET_BROADCAST_PIC_PAHT.substring(1) + filename);
                //  System.out.println(upload);

                } catch (Exception e) {
                    e.printStackTrace();
                }//复制文件到指定目录
                sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastResId(IdUtils.getSeqId());
                sysScenicSpotBroadcastExtendWithBLOBs.setMediaType("1");
                sysScenicSpotBroadcastExtendWithBLOBs.setPictureUrl(GET_BROADCAST_PIC_URL + filename);
                sysScenicSpotBroadcastExtendWithBLOBs.setCreateDate(DateUtil.currentDateTime());
                sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
                int i = sysScenicSpotBroadcastExtendMapper.insertSelective(sysScenicSpotBroadcastExtendWithBLOBs);
                if (i == 1) {
                    SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
                    sysScenicSpotResourceVersion.setScenicSpotId(sysScenicSpotBroadcastExtendWithBLOBs.getScenicSpotId());
                    sysScenicSpotResourceVersion.setResType("2");
                    SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
                    double resVersion = Double.parseDouble(resourceVersion.getResVersion());
                    double s = 0.1;
                    BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
                    BigDecimal p2 = new BigDecimal(Double.toString(s));
                    double a = p1.add(p2).doubleValue();
                    SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
                    sysScenicSpotResourceVersion2.setScenicSpotId(sysScenicSpotBroadcastExtendWithBLOBs.getScenicSpotId());
                    sysScenicSpotResourceVersion2.setResType("2");
                    sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
                    sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
                    sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
                }
                return i;
            }else {
                return 2;
            }
        }else{
            sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastResId(IdUtils.getSeqId());
            sysScenicSpotBroadcastExtendWithBLOBs.setMediaType("1");
            sysScenicSpotBroadcastExtendWithBLOBs.setPictureUrl("");
            sysScenicSpotBroadcastExtendWithBLOBs.setCreateDate(DateUtil.currentDateTime());
            sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
            int i = sysScenicSpotBroadcastExtendMapper.insertSelective(sysScenicSpotBroadcastExtendWithBLOBs);
            if (i == 1) {
                SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
                sysScenicSpotResourceVersion.setScenicSpotId(sysScenicSpotBroadcastExtendWithBLOBs.getScenicSpotId());
                sysScenicSpotResourceVersion.setResType("2");
                SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
                double resVersion = Double.parseDouble(resourceVersion.getResVersion());
                double s = 0.1;
                BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
                BigDecimal p2 = new BigDecimal(Double.toString(s));
                double a = p1.add(p2).doubleValue();
                SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
                sysScenicSpotResourceVersion2.setScenicSpotId(sysScenicSpotBroadcastExtendWithBLOBs.getScenicSpotId());
                sysScenicSpotResourceVersion2.setResType("2");
                sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
                sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
                sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
            }
            return i;
        }
    }

    /**
     * @Author 郭凯
     * @Description 景点内容新增（视频）
     * @Date 13:34 2020/11/6
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     * @return int
    **/
    @Override
    public int addBroadcastContentExtendVideo(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".mp4") || type.equals("flv") || type.equals("avi") || type.equals("rm") || type.equals("rmvb") || type.equals("wmv")) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = GET_BROADCAST_VODIO_PAHT + filename;// 存放位置
            File destFile = new File(path);
            try {
                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//                String upload = fileUploadUtil.upload(file, GET_BROADCAST_VODIO_PAHT.substring(1) + filename);
//                System.out.println(upload);

            } catch (IOException e) {
                e.printStackTrace();
            }//复制文件到指定目录
            sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastResId(IdUtils.getSeqId());
            sysScenicSpotBroadcastExtendWithBLOBs.setMediaType("2");
            sysScenicSpotBroadcastExtendWithBLOBs.setMediaResourceUrl(GET_BROADCAST_VODIO_URL + filename);
            sysScenicSpotBroadcastExtendWithBLOBs.setCreateDate(DateUtil.currentDateTime());
            sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
            int i = sysScenicSpotBroadcastExtendMapper.insertSelective(sysScenicSpotBroadcastExtendWithBLOBs);
            if (i == 1) {
                SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
                sysScenicSpotResourceVersion.setScenicSpotId(sysScenicSpotBroadcastExtendWithBLOBs.getScenicSpotId());
                sysScenicSpotResourceVersion.setResType("2");
                SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
                double resVersion = Double.parseDouble(resourceVersion.getResVersion());
                double s = 0.1;
                BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
                BigDecimal p2 = new BigDecimal(Double.toString(s));
                double a = p1.add(p2).doubleValue();
                SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
                sysScenicSpotResourceVersion2.setScenicSpotId(sysScenicSpotBroadcastExtendWithBLOBs.getScenicSpotId());
                sysScenicSpotResourceVersion2.setResType("2");
                sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
                sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
                sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
            }
            return i;
        }else {
            return 2;
        }
    }

    /**
     * @Author 郭凯
     * @Description 景点内容新增（音频）
     * @Date 13:38 2020/11/6
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     * @return int
    **/
    @Override
    public int addBroadcastContentExtendAudio(MultipartFile[] file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        //判断file数组不能为空并且长度大于0
        if(file != null && file.length > 0) {
            MultipartFile fileAudio = null;
            MultipartFile fileImage = null;
            //循环获取file数组中得文件
            for (int i = 0; i < file.length; i++) {
                fileAudio = file[0];
                fileImage = file[1];
            }
            if (fileAudio.getSize() != 0){
                String type = fileAudio.getOriginalFilename().substring(fileAudio.getOriginalFilename().indexOf("."));// 取文件格式后缀名
                if (type.equals(".mp3")) {
                    if (fileImage.getSize() != 0) {
                        String typepic = fileImage.getOriginalFilename().substring(fileImage.getOriginalFilename().indexOf("."));// 取文件格式后缀名
                        if (typepic.equals(".png") || typepic.equals(".jpg")) {
                            String filename = System.currentTimeMillis() + typepic;// 取当前时间戳作为文件名
                            String path = GET_BROADCAST_PIC_PAHT + filename;// 存放位置
                            File destFile = new File(path);
                            try {
                                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                                FileUtils.copyInputStreamToFile(fileImage.getInputStream(), destFile);
//                                String upload = fileUploadUtil.upload(fileImage, GET_BROADCAST_PIC_PAHT.substring(1) + filename);
//                                System.out.println(upload);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            sysScenicSpotBroadcastExtendWithBLOBs.setPictureUrl(GET_BROADCAST_PIC_URL + filename);
                        }else {
                            return 4;
                        }
                    }else {
                        sysScenicSpotBroadcastExtendWithBLOBs.setPictureUrl("");
                    }
                    String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                    String path = GET_BROADCAST_AUDIO_PAHT + filename;// 存放位置
                    File destFile = new File(path);
                    try {
                        //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                        FileUtils.copyInputStreamToFile(fileAudio.getInputStream(), destFile);
//                        String upload = fileUploadUtil.upload(fileImage, GET_BROADCAST_AUDIO_PAHT.substring(1) + filename);
//                        System.out.println(upload);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }//复制文件到指定目录
                    sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastResId(IdUtils.getSeqId());
                    sysScenicSpotBroadcastExtendWithBLOBs.setMediaType("3");
                    sysScenicSpotBroadcastExtendWithBLOBs.setMediaResourceUrl(GET_BROADCAST_AUDIO_URL + filename);
                    sysScenicSpotBroadcastExtendWithBLOBs.setCreateDate(DateUtil.currentDateTime());
                    sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
                    int i = sysScenicSpotBroadcastExtendMapper.insertSelective(sysScenicSpotBroadcastExtendWithBLOBs);
                    if (i == 1) {
                        SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
                        sysScenicSpotResourceVersion.setScenicSpotId(sysScenicSpotBroadcastExtendWithBLOBs.getScenicSpotId());
                        sysScenicSpotResourceVersion.setResType("2");
                        SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
                        double resVersion = Double.parseDouble(resourceVersion.getResVersion());
                        double s = 0.1;
                        BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
                        BigDecimal p2 = new BigDecimal(Double.toString(s));
                        double a = p1.add(p2).doubleValue();
                        SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
                        sysScenicSpotResourceVersion2.setScenicSpotId(sysScenicSpotBroadcastExtendWithBLOBs.getScenicSpotId());
                        sysScenicSpotResourceVersion2.setResType("2");
                        sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
                        sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
                        sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
                    }
                    return i;
                }else{
                    return 3;
                }
            }else{
                return 2;
            }
        }else{
            return 5;
        }
    }

    /**
     * @Author 郭凯
     * @Description 景点内容修改（文字）
     * @Date 15:04 2020/11/6
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     * @return int
    **/
    @Override
    public int editBroadcastContentExtendImage(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs broadcastExtendWithBLOBs) {
        if (!file.isEmpty()){
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".png") || type.equals(".jpg")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = GET_BROADCAST_PIC_PAHT + filename;// 存放位置
                File destFile = new File(path);
                try {

                    // 限制大小
                    long size = file.getSize() / 1024;//kb
                    if (size >= 2048){
                        return 3;
                    }
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                   FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

                    //              压缩图片上传
//                    CompressUtils.compress(file.getInputStream(),destFile,10000);

                    //阿里OSS文件存储_文件上传
                    //String upload = fileUploadUtil.upload(file, GET_BROADCAST_PIC_PAHT.substring(1) + filename);
                    //System.out.println(upload);

                } catch (Exception e) {
                    e.printStackTrace();
                }//复制文件到指定目录
                broadcastExtendWithBLOBs.setPictureUrl(GET_BROADCAST_PIC_URL + filename);
            }else {
                return 2;
            }
        }
        broadcastExtendWithBLOBs.setMediaType("1");
        broadcastExtendWithBLOBs.setMediaResourceUrl("");
        broadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotBroadcastExtendMapper.updateByPrimaryKeySelective(broadcastExtendWithBLOBs);
        if (i == 1) {
            SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
            sysScenicSpotResourceVersion.setScenicSpotId(broadcastExtendWithBLOBs.getScenicSpotId());
            sysScenicSpotResourceVersion.setResType("2");
            SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
            double resVersion = Double.parseDouble(resourceVersion.getResVersion());
            double s = 0.1;
            BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
            BigDecimal p2 = new BigDecimal(Double.toString(s));
            double a = p1.add(p2).doubleValue();
            SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
            sysScenicSpotResourceVersion2.setScenicSpotId(broadcastExtendWithBLOBs.getScenicSpotId());
            sysScenicSpotResourceVersion2.setResType("2");
            sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
            sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
            sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
        }
        return i;
    }

    /**
     * @Author 郭凯
     * @Description 景点内容修改（视频）
     * @Date 15:11 2020/11/6
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     * @return int
    **/
    @Override
    public int editBroadcastContentExtendVideo(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs broadcastExtendWithBLOBs) {
        if (!file.isEmpty()){
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".mp4") || type.equals("flv") || type.equals("avi") || type.equals("rm") || type.equals("rmvb") || type.equals("wmv")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = GET_BROADCAST_VODIO_PAHT + filename;// 存放位置
                File destFile = new File(path);
                try {
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//                    String upload = fileUploadUtil.upload(file, GET_BROADCAST_VODIO_PAHT.substring(1) + filename);
//                    System.out.println(upload);

                } catch (IOException e) {
                    e.printStackTrace();
                }//复制文件到指定目录
                broadcastExtendWithBLOBs.setMediaResourceUrl(GET_BROADCAST_VODIO_URL + filename);
            }else {
                return 2;
            }
        }
        broadcastExtendWithBLOBs.setMediaType("2");
        broadcastExtendWithBLOBs.setPictureUrl("");
        broadcastExtendWithBLOBs.setBroadcastContent("");
        broadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotBroadcastExtendMapper.updateByPrimaryKeySelective(broadcastExtendWithBLOBs);
        if (i == 1) {
            SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
            sysScenicSpotResourceVersion.setScenicSpotId(broadcastExtendWithBLOBs.getScenicSpotId());
            sysScenicSpotResourceVersion.setResType("2");
            SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
            double resVersion = Double.parseDouble(resourceVersion.getResVersion());
            double s = 0.1;
            BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
            BigDecimal p2 = new BigDecimal(Double.toString(s));
            double a = p1.add(p2).doubleValue();
            SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
            sysScenicSpotResourceVersion2.setScenicSpotId(broadcastExtendWithBLOBs.getScenicSpotId());
            sysScenicSpotResourceVersion2.setResType("2");
            sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
            sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
            sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
        }
        return i;
    }

    /**
     * @Author 郭凯
     * @Description 景点内容修改（视频）
     * @Date 15:15 2020/11/6
     * @Param [file, sysScenicSpotBroadcastExtendWithBLOBs]
     * @return int
    **/
    @Override
    public int editBroadcastContentExtendAudio(MultipartFile[] file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        if(file != null && file.length > 0) {
            MultipartFile fileAudio = null;
            MultipartFile fileImage = null;
            //循环获取file数组中得文件
            for (int i = 0; i < file.length; i++) {
                fileAudio = file[0];
                fileImage = file[1];
            }
            if (!fileAudio.isEmpty()){
                String type = fileAudio.getOriginalFilename().substring(fileAudio.getOriginalFilename().indexOf("."));// 取文件格式后缀名
                if (type.equals(".mp3")) {
                    String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                    String path = GET_BROADCAST_AUDIO_PAHT + filename;// 存放位置
                    File destFile = new File(path);
                    try {
                        //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                        FileUtils.copyInputStreamToFile(fileAudio.getInputStream(), destFile);
//                        String upload = fileUploadUtil.upload(fileAudio, GET_BROADCAST_AUDIO_PAHT.substring(1) + filename);
//                        System.out.println(upload);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }//复制文件到指定目录
                    sysScenicSpotBroadcastExtendWithBLOBs.setMediaResourceUrl(GET_BROADCAST_AUDIO_URL+filename);
                }else{
                    return 3;
                }
            }
            if (!fileImage.isEmpty()){
                String types = fileImage.getOriginalFilename().substring(fileImage.getOriginalFilename().indexOf("."));// 取文件格式后缀名
                if (types.equals(".png") || types.equals(".jpg")) {
                    String filename = System.currentTimeMillis() + types;// 取当前时间戳作为文件名
                    String path = GET_BROADCAST_PIC_PAHT + filename;// 存放位置
                    File destFile = new File(path);
                    try {
                        //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                        FileUtils.copyInputStreamToFile(fileImage.getInputStream(), destFile);
//                        String upload = fileUploadUtil.upload(fileImage, GET_BROADCAST_PIC_PAHT.substring(1) + filename);
//                        System.out.println(upload);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sysScenicSpotBroadcastExtendWithBLOBs.setPictureUrl(GET_BROADCAST_PIC_URL+filename);
                }else {
                    return 4;
                }
            }
            sysScenicSpotBroadcastExtendWithBLOBs.setMediaType("3");
            sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastContent("");
            sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
            int i = sysScenicSpotBroadcastExtendMapper.updateByPrimaryKeySelective(sysScenicSpotBroadcastExtendWithBLOBs);
            if (i == 1) {
                SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
                sysScenicSpotResourceVersion.setScenicSpotId(sysScenicSpotBroadcastExtendWithBLOBs.getScenicSpotId());
                sysScenicSpotResourceVersion.setResType("2");
                SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
                double resVersion = Double.parseDouble(resourceVersion.getResVersion());
                double s = 0.1;
                BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
                BigDecimal p2 = new BigDecimal(Double.toString(s));
                double a = p1.add(p2).doubleValue();
                SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
                sysScenicSpotResourceVersion2.setScenicSpotId(sysScenicSpotBroadcastExtendWithBLOBs.getScenicSpotId());
                sysScenicSpotResourceVersion2.setResType("2");
                sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
                sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
                sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
            }
            return i;
        }else{
            return 5;
        }
    }

    /**
     * @Author 郭凯
     * @Description 景点内容删除
     * @Date 15:40 2020/11/6
     * @Param [broadcastResId]
     * @return int
    **/
    @Override
    public int delBroadcastContentExtend(Long broadcastResId) {
        return sysScenicSpotBroadcastExtendMapper.deleteByPrimaryKey(broadcastResId);
    }

    /**
     * @Author 郭凯
     * @Description 下载景点Excel表
     * @Date 16:14 2020/11/6
     * @Param [search]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysScenicSpotBroadcastExtendWithBLOBs>
    **/
    @Override
    public List<SysScenicSpotBroadcastExtendWithBLOBs> getBroadcastExcel(Map<String, Object> search) {
        return sysScenicSpotBroadcastExtendMapper.getBroadcastExcel(search);
    }

    /**
     * @Author 郭凯
     * @Description 查询景点坐标
     * @Date 15:20 2020/11/23
     * @Param [scenicSpotId]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysScenicSpotBroadcast>
    **/
    @Override
    public List<SysScenicSpotBroadcast> getBroadcastByScenicSpotId(String scenicSpotId) {
       return sysScenicSpotBroadcastMapper.getBroadcastListByScenicSpotId(Long.parseLong(scenicSpotId));
      // return sysScenicSpotBroadcastMapper.getBroadcastByScenicSpotIdNew(Long.parseLong(scenicSpotId));
    }

    /**
     * 景点数量
     * @param scenicSpotId
     * @return
     */
    @Override
    public int getBroadcastSumByScenicSpotId(Long scenicSpotId) {

        return sysScenicSpotBroadcastMapper.getBroadcastSumByScenicSpotId(scenicSpotId);
    }

    /**
     *   /全部景点
     */

    @Override
    public List<SysScenicSpotBroadcast> getScenicSpotBroadcastAll() {

       List<SysScenicSpotBroadcast> list =  sysScenicSpotBroadcastMapper.getScenicSpotBroadcastAll();

       return list;
    }
}
