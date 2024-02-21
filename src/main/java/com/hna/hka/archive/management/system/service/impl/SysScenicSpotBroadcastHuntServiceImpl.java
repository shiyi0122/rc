package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysScenicSpotBroadcastHuntExtendMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotBroadcastHuntMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotResourceVersionMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcastExtendWithBLOBs;
import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcastHunt;
import com.hna.hka.archive.management.system.model.SysScenicSpotResourceVersion;
import com.hna.hka.archive.management.system.service.SysScenicSpotBroadcastHuntService;
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
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysScenicSpotBroadcastHuntServiceImpl
 * @Author: 郭凯
 * @Description: 寻宝
 * @Date: 2021/12/9 11:21
 * @Version: 1.0
 */
@Service
@Transactional
public class SysScenicSpotBroadcastHuntServiceImpl implements SysScenicSpotBroadcastHuntService {
    @Autowired
    private SysScenicSpotBroadcastHuntMapper sysScenicSpotBroadcastHuntMapper;
    @Autowired
    private SysScenicSpotBroadcastHuntExtendMapper sysScenicSpotBroadcastHuntExtendMapper;
    @Autowired
    private SysScenicSpotResourceVersionMapper sysScenicSpotResourceVersionMapper;

    @Value("${GET_BROADCASTHUNT_PIC_PAHT}")
    private String GET_BROADCASTHUNT_PIC_PAHT;
    @Value("${GET_BROADCASTHUNT_PIC_URL}")
    private String GET_BROADCASTHUNT_PIC_URL;
    @Value("${GET_BROADCASTHUNT_VODIO_URL}")
    private String GET_BROADCASTHUNT_VODIO_URL;

    @Value("${GET_BROADCASTHUNT_VODIO_PAHT}")
    private String GET_BROADCASTHUNT_VODIO_PAHT;

    @Value("${GET_BROADCASTHUNT_AUDIO_URL}")
    private String GET_BROADCASTHUNT_AUDIO_URL;

    @Value("${GET_BROADCASTHUNT_AUDIO_PAHT}")
    private String GET_BROADCASTHUNT_AUDIO_PAHT;

    @Autowired
    private FileUploadUtil fileUploadUtil;


    @Override
    public PageInfo<SysScenicSpotBroadcastHunt> getBroadcastList(Integer pageNum, Integer pageSize, SysScenicSpotBroadcastHunt sysScenicSpotBroadcastHunt) {

        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotBroadcastHunt> broadcastList = sysScenicSpotBroadcastHuntMapper.getBroadcastListNew(sysScenicSpotBroadcastHunt);
            PageInfo<SysScenicSpotBroadcastHunt> pageInfo = new PageInfo<>(broadcastList);

        return pageInfo;
    }

    /**
     * @Method 新增寻宝景点
     * @Author 曲绍备
     * @Version 1.0
     * @Description
     * @Return
     * @Date 2021/12/9 17:43
     */
    public int addBroadcast(SysScenicSpotBroadcastHunt broadcastHunt) {
        broadcastHunt.setBroadcastId(IdUtils.getSeqId());
        broadcastHunt.setPinYinName(Tinypinyin.tinypinyin(broadcastHunt.getBroadcastName()));
        if (broadcastHunt.getIntegralNum() == null) {
            broadcastHunt.setIntegralNum(Long.valueOf(broadcastHunt.getTreasureType()));
        }
        broadcastHunt.setCreateDate(DateUtil.currentDateTime());
        broadcastHunt.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotBroadcastHuntMapper.insertSelective(broadcastHunt);
        if (i == 1) {
            //更新版本号
            SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
            sysScenicSpotResourceVersion.setScenicSpotId(broadcastHunt.getScenicSpotId());
            sysScenicSpotResourceVersion.setResType("2");
            //查询版本号
            SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
            double resVersion = Double.parseDouble(resourceVersion.getResVersion());
            double s = 0.1;
            BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
            BigDecimal p2 = new BigDecimal(Double.toString(s));
            double a = p1.add(p2).doubleValue();
            SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
            sysScenicSpotResourceVersion2.setScenicSpotId(broadcastHunt.getScenicSpotId());
            sysScenicSpotResourceVersion2.setResType("2");
            sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
            sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
            //修改版本号
            sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
        }
        return i;
    }

    /**
     * @Method 修改寻宝景点
     * @Author zhang
     * @Version 1.0
     * @Description
     * @Return
     * @Date 2021/12/9 18:01
     */
    @Override
    public int editBroadcast(SysScenicSpotBroadcastHunt broadcastHunt) {
        if (broadcastHunt.getIntegralNum() == null) {
            broadcastHunt.setIntegralNum(Long.valueOf(broadcastHunt.getTreasureType()));
        }
        broadcastHunt.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotBroadcastHuntMapper.updateByPrimaryKeySelective(broadcastHunt);
        if (i == 1) {
            //更新版本号
            SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
            sysScenicSpotResourceVersion.setScenicSpotId(broadcastHunt.getScenicSpotId());
            sysScenicSpotResourceVersion.setResType("2");
            //查询版本号
            SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
            double resVersion = Double.parseDouble(resourceVersion.getResVersion());
            double s = 0.1;
            BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
            BigDecimal p2 = new BigDecimal(Double.toString(s));
            double a = p1.add(p2).doubleValue();
            SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
            sysScenicSpotResourceVersion2.setScenicSpotId(broadcastHunt.getScenicSpotId());
            sysScenicSpotResourceVersion2.setResType("2");
            sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
            sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
            //修改版本号
            sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
        }
        return i;

    }

    /**
     * @Method 删除寻宝景点
     * @Author zhang
     * @Version 1.0
     * @Description
     * @Return
     */
    @Override
    public int delBroadcast(Long broadcastId, Long scenicSpotId) {
        sysScenicSpotBroadcastHuntMapper.deleteById(broadcastId);
        int i = sysScenicSpotBroadcastHuntMapper.deleteByPrimaryKey(broadcastId);
        if (i == 1) {
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
     * @Method 启用/修改寻宝景点
     * @Author 张
     * @Version 1.0
     * @Description
     * @Return
     */
    @Override
    public int switchBroadcast(SysScenicSpotBroadcastHunt broadcastHunt) {

        return sysScenicSpotBroadcastHuntMapper.updateByPrimaryKeySelective(broadcastHunt);
    }

    /**
     * 景点内容详情列表查询
     *
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getBroadcastContentHuntList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotBroadcastExtendWithBLOBs> broadcastExtendWithBLOBsList = sysScenicSpotBroadcastHuntExtendMapper.getBroadcastContentList(search);
        if (broadcastExtendWithBLOBsList.size() != 0) {
            PageInfo<SysScenicSpotBroadcastExtendWithBLOBs> pageInfo = new PageInfo<>(broadcastExtendWithBLOBsList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 活动景点内容新增（文字）
     *
     * @param file
     * @param sysScenicSpotBroadcastExtendWithBLOBs
     * @return
     */
    @Override
    public int addBroadcastHuntContentExtendImage(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        if (!file.isEmpty()) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".png") || type.equals(".jpg")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = GET_BROADCASTHUNT_PIC_PAHT + filename;// 存放位置
                File destFile = new File(path);
                try {

                    // 限制大小
                    long size = file.getSize() / 1024;//kb
                    if (size >= 2048) {
                        return 3;
                    }
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//                    String upload = fileUploadUtil.upload(file, GET_BROADCASTHUNT_PIC_PAHT.substring(1) + filename);
//                    System.out.println(upload);

                } catch (IOException e) {
                    e.printStackTrace();
                }//复制文件到指定目录
                sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastResId(IdUtils.getSeqId());
                sysScenicSpotBroadcastExtendWithBLOBs.setMediaType("1");
                sysScenicSpotBroadcastExtendWithBLOBs.setPictureUrl(GET_BROADCASTHUNT_PIC_URL + filename);
                sysScenicSpotBroadcastExtendWithBLOBs.setCreateDate(DateUtil.currentDateTime());
                sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
                int i = sysScenicSpotBroadcastHuntExtendMapper.insertSelective(sysScenicSpotBroadcastExtendWithBLOBs);
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
            } else {
                return 2;
            }
        } else {
            sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastResId(IdUtils.getSeqId());
            sysScenicSpotBroadcastExtendWithBLOBs.setMediaType("1");
            sysScenicSpotBroadcastExtendWithBLOBs.setPictureUrl("");
            sysScenicSpotBroadcastExtendWithBLOBs.setCreateDate(DateUtil.currentDateTime());
            sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
            int i = sysScenicSpotBroadcastHuntExtendMapper.insertSelective(sysScenicSpotBroadcastExtendWithBLOBs);
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
     * 活动景点内容新增（视频）
     *
     * @param file
     * @param sysScenicSpotBroadcastExtendWithBLOBs
     * @return
     */
    @Override
    public int addBroadcastHuntContentExtendVideo(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {

        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".mp4") || type.equals("flv") || type.equals("avi") || type.equals("rm") || type.equals("rmvb") || type.equals("wmv")) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = GET_BROADCASTHUNT_VODIO_PAHT + filename;// 存放位置
            File destFile = new File(path);
            try {
                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//                String upload = fileUploadUtil.upload(file, GET_BROADCASTHUNT_VODIO_PAHT.substring(1) + filename);
//                System.out.println(upload);
            } catch (IOException e) {
                e.printStackTrace();
            }//复制文件到指定目录
            sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastResId(IdUtils.getSeqId());
            sysScenicSpotBroadcastExtendWithBLOBs.setMediaType("2");
            sysScenicSpotBroadcastExtendWithBLOBs.setMediaResourceUrl(GET_BROADCASTHUNT_VODIO_URL + filename);
            sysScenicSpotBroadcastExtendWithBLOBs.setCreateDate(DateUtil.currentDateTime());
            sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
            int i = sysScenicSpotBroadcastHuntExtendMapper.insertSelective(sysScenicSpotBroadcastExtendWithBLOBs);
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
        } else {
            return 2;
        }
    }

    /**
     * 活动景点内容新增（音频）
     *
     * @param file
     * @param sysScenicSpotBroadcastExtendWithBLOBs
     * @return
     */
    @Override
    public int addBroadcastHuntContentExtendAudio(MultipartFile[] file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        //判断file数组不能为空并且长度大于0
        //判断file数组不能为空并且长度大于0
        if (file != null && file.length > 0) {
            MultipartFile fileAudio = null;
            MultipartFile fileImage = null;
            //循环获取file数组中得文件
            for (int i = 0; i < file.length; i++) {
                fileAudio = file[0];
                fileImage = file[1];
            }
            if (fileAudio.getSize() != 0) {
                String type = fileAudio.getOriginalFilename().substring(fileAudio.getOriginalFilename().indexOf("."));// 取文件格式后缀名
                if (type.equals(".mp3")) {
                    if (fileImage.getSize() != 0) {
                        String typepic = fileImage.getOriginalFilename().substring(fileImage.getOriginalFilename().indexOf("."));// 取文件格式后缀名
                        if (typepic.equals(".png") || typepic.equals(".jpg")) {
                            String filename = System.currentTimeMillis() + typepic;// 取当前时间戳作为文件名
                            String path = GET_BROADCASTHUNT_PIC_PAHT + filename;// 存放位置
                            File destFile = new File(path);
                            try {
                                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                                FileUtils.copyInputStreamToFile(fileImage.getInputStream(), destFile);
//                                String upload = fileUploadUtil.upload(fileImage, GET_BROADCAST_PIC_PAHT.substring(1) + filename);
//                                System.out.println(upload);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            sysScenicSpotBroadcastExtendWithBLOBs.setPictureUrl(GET_BROADCASTHUNT_PIC_URL + filename);
                        } else {
                            return 4;
                        }
                    } else {
                        sysScenicSpotBroadcastExtendWithBLOBs.setPictureUrl("");
                    }
                    String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                    String path = GET_BROADCASTHUNT_AUDIO_PAHT + filename;// 存放位置
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
                    sysScenicSpotBroadcastExtendWithBLOBs.setMediaResourceUrl(GET_BROADCASTHUNT_AUDIO_URL + filename);
                    sysScenicSpotBroadcastExtendWithBLOBs.setCreateDate(DateUtil.currentDateTime());
                    sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
                    int i = sysScenicSpotBroadcastHuntExtendMapper.insertSelective(sysScenicSpotBroadcastExtendWithBLOBs);
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
                } else {
                    return 3;
                }
            } else {
                return 2;
            }
        } else {
            return 5;
        }

    }

    /**
     * 景点内容修改（文字）
     *
     * @param file
     * @param
     * @return
     */
    @Override
    public int editBroadcastHuntContentExtendImage(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs broadcastExtendWithBLOBs) {

        if (!file.isEmpty()) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".png") || type.equals(".jpg")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = GET_BROADCASTHUNT_PIC_PAHT + filename;// 存放位置
                File destFile = new File(path);
                try {
                    // 限制大小
                    long size = file.getSize() / 1024;//kb
                    if (size >= 2048) {
                        return 3;
                    }
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//                    String upload = fileUploadUtil.upload(file, GET_BROADCASTHUNT_PIC_PAHT.substring(1) + filename);
//                    System.out.println(upload);

                } catch (IOException e) {
                    e.printStackTrace();
                }//复制文件到指定目录
                broadcastExtendWithBLOBs.setPictureUrl(GET_BROADCASTHUNT_PIC_URL + filename);
            } else {
                return 2;
            }
        }
        broadcastExtendWithBLOBs.setMediaType("1");
        broadcastExtendWithBLOBs.setMediaResourceUrl("");
        broadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotBroadcastHuntExtendMapper.updateByPrimaryKeySelective(broadcastExtendWithBLOBs);
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
     * 景点内容修改（视频）
     *
     * @param file
     * @param
     * @return
     */
    @Override
    public int editBroadcastHuntContentExtendVideo(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs broadcastExtendWithBLOBs) {
        if (!file.isEmpty()) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".mp4") || type.equals("flv") || type.equals("avi") || type.equals("rm") || type.equals("rmvb") || type.equals("wmv")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = GET_BROADCASTHUNT_VODIO_PAHT + filename;// 存放位置
                File destFile = new File(path);
                try {
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//                    String upload = fileUploadUtil.upload(file, GET_BROADCASTHUNT_VODIO_PAHT.substring(1) + filename);
//                    System.out.println(upload);

                } catch (IOException e) {
                    e.printStackTrace();
                }//复制文件到指定目录
                broadcastExtendWithBLOBs.setMediaResourceUrl(GET_BROADCASTHUNT_VODIO_URL + filename);
            } else {
                return 2;
            }
        }
        broadcastExtendWithBLOBs.setMediaType("2");
        broadcastExtendWithBLOBs.setPictureUrl("");
        broadcastExtendWithBLOBs.setBroadcastContent("");
        broadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotBroadcastHuntExtendMapper.updateByPrimaryKeySelective(broadcastExtendWithBLOBs);
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
     * 景点内容修改（视频）
     *
     * @param file
     * @param sysScenicSpotBroadcastExtendWithBLOBs
     * @return
     */
    @Override
    public int editBroadcastHuntContentExtendAudio(MultipartFile[] file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        if (file != null && file.length > 0) {
            MultipartFile fileAudio = null;
            MultipartFile fileImage = null;
            //循环获取file数组中得文件
            for (int i = 0; i < file.length; i++) {
                fileAudio = file[0];
                fileImage = file[1];
            }
            if (!fileAudio.isEmpty()) {
                String type = fileAudio.getOriginalFilename().substring(fileAudio.getOriginalFilename().indexOf("."));// 取文件格式后缀名
                if (type.equals(".mp3")) {
                    String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                    String path = GET_BROADCASTHUNT_AUDIO_PAHT + filename;// 存放位置
                    File destFile = new File(path);
                    try {
                        //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                        FileUtils.copyInputStreamToFile(fileAudio.getInputStream(), destFile);
//                        String upload = fileUploadUtil.upload(fileAudio, GET_BROADCAST_AUDIO_PAHT.substring(1) + filename);
//                        System.out.println(upload);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }//复制文件到指定目录
                    sysScenicSpotBroadcastExtendWithBLOBs.setMediaResourceUrl(GET_BROADCASTHUNT_AUDIO_URL + filename);
                } else {
                    return 3;
                }
            }
            if (!fileImage.isEmpty()) {
                String types = fileImage.getOriginalFilename().substring(fileImage.getOriginalFilename().indexOf("."));// 取文件格式后缀名
                if (types.equals(".png") || types.equals(".jpg")) {
                    String filename = System.currentTimeMillis() + types;// 取当前时间戳作为文件名
                    String path = GET_BROADCASTHUNT_PIC_PAHT + filename;// 存放位置
                    File destFile = new File(path);
                    try {
                        //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                        FileUtils.copyInputStreamToFile(fileImage.getInputStream(), destFile);
//                        String upload = fileUploadUtil.upload(fileImage, GET_BROADCAST_PIC_PAHT.substring(1) + filename);
//                        System.out.println(upload);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sysScenicSpotBroadcastExtendWithBLOBs.setPictureUrl(GET_BROADCASTHUNT_PIC_URL + filename);
                } else {
                    return 4;
                }
            }
            sysScenicSpotBroadcastExtendWithBLOBs.setMediaType("3");
            sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastContent("");
            sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
            int i = sysScenicSpotBroadcastHuntExtendMapper.updateByPrimaryKeySelective(sysScenicSpotBroadcastExtendWithBLOBs);
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
        } else {
            return 5;
        }
    }

    /**
     * 景点内容删除
     */
    @Override
    public int delBroadcastHuntContentExtend(Long broadcastResId) {
        return sysScenicSpotBroadcastHuntExtendMapper.deleteByPrimaryKey(broadcastResId);

    }


    @Override
    public List<SysScenicSpotBroadcastExtendWithBLOBs> getBroadcastExcel(Map<String, Object> search) {
        return sysScenicSpotBroadcastHuntExtendMapper.getBroadcastExcel(search);
    }

    @Override
    public int addBroadcastHuntContentExtendAudioNew(MultipartFile fileAudio, MultipartFile fileImage, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        if (!fileAudio.isEmpty()) {
            String type = fileAudio.getOriginalFilename().substring(fileAudio.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".mp3")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = GET_BROADCASTHUNT_AUDIO_PAHT + filename;// 存放位置
                File destFile = new File(path);
                try {
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(fileAudio.getInputStream(), destFile);
                        String upload = fileUploadUtil.upload(fileAudio, GET_BROADCASTHUNT_AUDIO_PAHT.substring(1) + filename);
                        System.out.println(upload);

                } catch (IOException e) {
                    e.printStackTrace();
                }//复制文件到指定目录
                sysScenicSpotBroadcastExtendWithBLOBs.setMediaResourceUrl(GET_BROADCASTHUNT_AUDIO_URL + filename);
            } else {
                return 3;
            }
        }
        if (!fileImage.isEmpty()) {
            String types = fileImage.getOriginalFilename().substring(fileImage.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (types.equals(".png") || types.equals(".jpg")) {
                String filename = System.currentTimeMillis() + types;// 取当前时间戳作为文件名
                String path = GET_BROADCASTHUNT_PIC_PAHT + filename;// 存放位置
                File destFile = new File(path);
                try {
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(fileImage.getInputStream(), destFile);
                        String upload = fileUploadUtil.upload(fileImage, GET_BROADCASTHUNT_PIC_PAHT.substring(1) + filename);
                        System.out.println(upload);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                sysScenicSpotBroadcastExtendWithBLOBs.setPictureUrl(GET_BROADCASTHUNT_PIC_URL + filename);
            } else {
                return 4;
            }
        }
        sysScenicSpotBroadcastExtendWithBLOBs.setMediaType("3");
        sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastContent("");
        sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastResId(IdUtils.getSeqId());
        sysScenicSpotBroadcastExtendWithBLOBs.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotBroadcastHuntExtendMapper.insertSelective(sysScenicSpotBroadcastExtendWithBLOBs);
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

    @Override
    public int editBroadcastHuntContentExtendAudioNew(MultipartFile fileAudio, MultipartFile fileImage, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs) {
        if (!fileAudio.isEmpty()) {
            String type = fileAudio.getOriginalFilename().substring(fileAudio.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".mp3")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = GET_BROADCASTHUNT_AUDIO_PAHT + filename;// 存放位置
                File destFile = new File(path);
                try {
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(fileAudio.getInputStream(), destFile);
                    String upload = fileUploadUtil.upload(fileAudio, GET_BROADCASTHUNT_AUDIO_PAHT.substring(1) + filename);
                    System.out.println(upload);

                } catch (IOException e) {
                    e.printStackTrace();
                }//复制文件到指定目录
                sysScenicSpotBroadcastExtendWithBLOBs.setMediaResourceUrl(GET_BROADCASTHUNT_AUDIO_URL + filename);
            } else {
                return 3;
            }
        }
        if (!fileImage.isEmpty()) {
            String types = fileImage.getOriginalFilename().substring(fileImage.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (types.equals(".png") || types.equals(".jpg")) {
                String filename = System.currentTimeMillis() + types;// 取当前时间戳作为文件名
                String path = GET_BROADCASTHUNT_PIC_PAHT + filename;// 存放位置
                File destFile = new File(path);
                try {
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(fileImage.getInputStream(), destFile);
                    String upload = fileUploadUtil.upload(fileImage, GET_BROADCASTHUNT_PIC_PAHT.substring(1) + filename);
                    System.out.println(upload);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                sysScenicSpotBroadcastExtendWithBLOBs.setPictureUrl(GET_BROADCASTHUNT_PIC_URL + filename);
            } else {
                return 4;
            }
        }
        sysScenicSpotBroadcastExtendWithBLOBs.setMediaType("3");
        sysScenicSpotBroadcastExtendWithBLOBs.setBroadcastContent("");
        sysScenicSpotBroadcastExtendWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotBroadcastHuntExtendMapper.updateByPrimaryKeySelective(sysScenicSpotBroadcastExtendWithBLOBs);
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
