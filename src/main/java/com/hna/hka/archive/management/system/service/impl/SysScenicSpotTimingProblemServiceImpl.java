package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysScenicSpotRandomProblemMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotResourceVersionMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotTimingProblemMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpotRandomProblemWithBLOBs;
import com.hna.hka.archive.management.system.model.SysScenicSpotResourceVersion;
import com.hna.hka.archive.management.system.model.SysScenicSpotTimingProblemWithBLOBs;
import com.hna.hka.archive.management.system.service.SysScenicSpotRandomProblemService;
import com.hna.hka.archive.management.system.service.SysScenicSpotTimingProblemService;
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
 * @ClassName: SysScenicSpotRandomProblemServiceImpl
 * @Author: 郭凯
 * @Description: 随机播报管理业务层（实现）
 * @Date: 2020/6/20 10:24
 * @Version: 1.0
 */
@Service
@Transactional
public class SysScenicSpotTimingProblemServiceImpl implements SysScenicSpotTimingProblemService {

    @Autowired
    private SysScenicSpotTimingProblemMapper sysScenicSpotTimingProblemMapper;

    @Autowired
    private SysScenicSpotResourceVersionMapper sysScenicSpotResourceVersionMapper;

    @Value("${filePatheGetPicUrl}")
    private String filePatheGetPicUrl;

    @Value("${filePatheGetPicPaht}")
    private String filePatheGetPicPaht;

    @Value("${filePatheGetVodioUrl}")
    private String filePatheGetVodioUrl;

    @Value("${filePatheGetVodioPaht}")
    private String filePatheGetVodioPaht;

    @Value("${filePatheGetAudioUrl}")
    private String filePatheGetAudioUrl;

    @Value("${filePatheGetAudioPaht}")
    private String filePatheGetAudioPaht;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    /**
     * @Author 郭凯
     * @Description 随机播报管理列表查询
     * @Date 10:26 2020/6/20
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getTimingProblemList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotTimingProblemWithBLOBs> sysScenicSpotTimingProblemWithBLOBsList = sysScenicSpotTimingProblemMapper.getTimingProblemList(search);
//        for(SysScenicSpotTimingProblemWithBLOBs sysScenicSpotTimingProble : sysScenicSpotTimingProblemWithBLOBsList) {
//            if ("20200210".equals(String.valueOf(sysScenicSpotTimingProble.getScenicSpotId()))) {
//                sysScenicSpotTimingProble.setScenicSpotName("通用类型");
//            }
//        }
        if(sysScenicSpotTimingProblemWithBLOBsList.size() != 0){
            PageInfo<SysScenicSpotTimingProblemWithBLOBs> pageInfo = new PageInfo<>(sysScenicSpotTimingProblemWithBLOBsList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 删除随机播报
     * @Date 9:12 2020/6/22
     * @Param [problemId]
     * @return int
    **/
    @Override
    public int delTimingProblem(Long problemId) {
        return sysScenicSpotTimingProblemMapper.deleteByPrimaryKey(problemId);
    }

    /**
     * @Author 郭凯
     * @Description 修改随机播报状态
     * @Date 9:20 2020/6/22
     * @Param [sysScenicSpotRandomProblemWithBLOBs]
     * @return int
    **/
    @Override
    public int editTimingProblemValid(SysScenicSpotTimingProblemWithBLOBs sysScenicSpotTimingProblemWithBLOBs) {
        sysScenicSpotTimingProblemWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotTimingProblemMapper.updateByPrimaryKeySelective(sysScenicSpotTimingProblemWithBLOBs);
    }

    /**
     * @Author 郭凯
     * @Description 新增随机播报(文字)
     * @Date 13:59 2020/7/29
     * @Param [file, sysScenicSpotRandomProblemWithBLOBs]
     * @return int
    **/
    @Override
    public int addTimingProblemImage(MultipartFile file, SysScenicSpotTimingProblemWithBLOBs sysScenicSpotTimingProblemWithBLOBs) {
        if(!file.isEmpty()){
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".png") || type.equals(".jpg")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = filePatheGetPicPaht + filename;// 存放位置
                File destFile = new File(path);
                try {
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//                    String upload = fileUploadUtil.upload(file, filePatheGetPicPaht.substring(1) + filename);
//                    System.out.println(upload);

                } catch (IOException e) {
                    e.printStackTrace();
                }//复制文件到指定目录
                sysScenicSpotTimingProblemWithBLOBs.setTimingResRulPic(filePatheGetPicUrl + filename);

            }else {
                return 2;
            }
        }
        sysScenicSpotTimingProblemWithBLOBs.setProblemId(IdUtils.getSeqId());
        sysScenicSpotTimingProblemWithBLOBs.setTimingType("1");
        sysScenicSpotTimingProblemWithBLOBs.setTimingResRul("");
        sysScenicSpotTimingProblemWithBLOBs.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotTimingProblemWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotTimingProblemMapper.insertSelective(sysScenicSpotTimingProblemWithBLOBs);
//        if (!"20200210".equals(String.valueOf(sysScenicSpotTimingProblemWithBLOBs.getScenicSpotId()))) {
//            if(i == 1) {
//                SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
//                sysScenicSpotResourceVersion.setScenicSpotId(sysScenicSpotTimingProblemWithBLOBs.getScenicSpotId());
//                sysScenicSpotResourceVersion.setResType("3");
//                SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
//                double resVersion = Double.parseDouble(resourceVersion.getResVersion());
//                double s = 0.1;
//                BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
//                BigDecimal p2 = new BigDecimal(Double.toString(s));
//                double a = p1.add(p2).doubleValue();
//                SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
//                sysScenicSpotResourceVersion2.setScenicSpotId(sysScenicSpotTimingProblemWithBLOBs.getScenicSpotId());
//                sysScenicSpotResourceVersion2.setResType("3");
//                sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
//                sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
//                sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
//            }
//        }
        return i;
    }

    /**
     * @Author 郭凯
     * @Description 新增随机播报(视频)
     * @Date 15:32 2020/7/29
     * @Param [file, sysScenicSpotRandomProblemWithBLOBs]
     * @return int
    **/
    @Override
    public int addTimingProblemVideo(MultipartFile file, SysScenicSpotTimingProblemWithBLOBs sysScenicSpotTimingProblemWithBLOBs) {
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".mp4") || type.equals("flv") || type.equals("avi") || type.equals("rm") || type.equals("rmvb") || type.equals("wmv")) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = filePatheGetVodioPaht + filename;// 存放位置
            File destFile = new File(path);
            try {
                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//                String upload = fileUploadUtil.upload(file, filePatheGetVodioPaht.substring(1) + filename);
//                System.out.println(upload);

            } catch (IOException e) {
                e.printStackTrace();
            }//复制文件到指定目录
            sysScenicSpotTimingProblemWithBLOBs.setProblemId(IdUtils.getSeqId());
            sysScenicSpotTimingProblemWithBLOBs.setTimingResRul(filePatheGetVodioUrl+filename);
            sysScenicSpotTimingProblemWithBLOBs.setTimingType("2");
            sysScenicSpotTimingProblemWithBLOBs.setTimingAnswers("");
            sysScenicSpotTimingProblemWithBLOBs.setCreateDate(DateUtil.currentDateTime());
            sysScenicSpotTimingProblemWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
            int i = sysScenicSpotTimingProblemMapper.insertSelective(sysScenicSpotTimingProblemWithBLOBs);
//            if (!"20200210".equals(sysScenicSpotTimingProblemWithBLOBs.getScenicSpotId())) {
//                if (i == 1) {
//                    SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
//                    sysScenicSpotResourceVersion.setScenicSpotId(sysScenicSpotTimingProblemWithBLOBs.getScenicSpotId());
//                    sysScenicSpotResourceVersion.setResType("3");
//                    SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
//                    double resVersion = Double.parseDouble(resourceVersion.getResVersion());
//                    double s = 0.1;
//                    BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
//                    BigDecimal p2 = new BigDecimal(Double.toString(s));
//                    double a = p1.add(p2).doubleValue();
//                    SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
//                    sysScenicSpotResourceVersion2.setScenicSpotId(sysScenicSpotTimingProblemWithBLOBs.getScenicSpotId());
//                    sysScenicSpotResourceVersion2.setResType("3");
//                    sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
//                    sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
//                    sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
//                }
//            }
            return i;
        }else {
            return 2;
        }
    }

    /**
     * @Author 郭凯
     * @Description 新增随机播报(音频)
     * @Date 11:15 2020/11/2
     * @Param [file, randomProblem]
     * @return int
    **/
    @Override
    public int addTimingProblemAudio(MultipartFile[] file, SysScenicSpotTimingProblemWithBLOBs problem) {
        //判断file数组不能为空并且长度大于0
        if(file != null && file.length > 0){
            MultipartFile fileAudio = null;
            MultipartFile fileImage = null;
            //循环获取file数组中得文件
            for(int i = 0;i < file.length;i++){
                fileAudio = file[0];
                fileImage = file[1];
            }
            if (fileAudio.getSize() != 0){
                String type = fileAudio.getOriginalFilename().substring(fileAudio.getOriginalFilename().indexOf("."));// 取文件格式后缀名
                if (type.equals(".mp3")) {
                    if (fileImage.getSize() != 0) {
                        String types = fileImage.getOriginalFilename().substring(fileImage.getOriginalFilename().indexOf("."));// 取文件格式后缀名
                        if (types.equals(".png") || types.equals(".jpg")) {
                            String filename = System.currentTimeMillis() + types;// 取当前时间戳作为文件名
                            String path = filePatheGetPicPaht + filename;// 存放位置
                            File destFile = new File(path);
                            try {
                                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                                FileUtils.copyInputStreamToFile(fileImage.getInputStream(), destFile);
//                                String upload = fileUploadUtil.upload(fileImage, filePatheGetPicPaht.substring(1) + filename);
//                                System.out.println(upload);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            problem.setTimingResRulPic(filePatheGetPicUrl+filename);
                        }else {
                            return 4;
                        }
                    }else {
                        problem.setTimingResRulPic("");
                    }
                    String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                    String path = filePatheGetAudioPaht + filename;// 存放位置
                    File destFile = new File(path);
                    try {
                        //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                        FileUtils.copyInputStreamToFile(fileAudio.getInputStream(), destFile);
//                        String upload = fileUploadUtil.upload(fileAudio, filePatheGetAudioPaht.substring(1) + filename);
//                        System.out.println(upload);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }//复制文件到指定目录
                    problem.setProblemId(IdUtils.getSeqId());
                    problem.setTimingResRul(filePatheGetAudioUrl+filename);
                    problem.setTimingType("3");
                    problem.setTimingAnswers("");
                    problem.setCreateDate(DateUtil.currentDateTime());
                    problem.setUpdateDate(DateUtil.currentDateTime());
                    int i = sysScenicSpotTimingProblemMapper.insertSelective(problem);
//                    if (String.valueOf(problem.getScenicSpotId()) != "20200210" || !"20200210".equals(String.valueOf(problem.getScenicSpotId()))) {
//                        if(i == 1) {
//                            SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
//                            sysScenicSpotResourceVersion.setScenicSpotId(problem.getScenicSpotId());
//                            sysScenicSpotResourceVersion.setResType("3");
//                            SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
//                            double resVersion = Double.parseDouble(resourceVersion.getResVersion());
//                            double s = 0.1;
//                            BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
//                            BigDecimal p2 = new BigDecimal(Double.toString(s));
//                            double a = p1.add(p2).doubleValue();
//                            SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
//                            sysScenicSpotResourceVersion2.setScenicSpotId(problem.getScenicSpotId());
//                            sysScenicSpotResourceVersion2.setResType("3");
//                            sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
//                            sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
//                            sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
//                        }
//                    }
                    return i;
                }else {
                    return 3;
                }
            }else{
                return 2;
            }
        }else{
            return 0;
        }
    }

    /**
     * @Author 郭凯
     * @Description 编辑随机播报(文字)
     * @Date 15:40 2020/11/2
     * @Param [file, sysScenicSpotRandomProblemWithBLOBs]
     * @return int
    **/
    @Override
    public int editTimingProblemImage(MultipartFile file, SysScenicSpotTimingProblemWithBLOBs sysScenicSpotTimingProblemWithBLOBs) {
        if(!file.isEmpty()){
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".png") || type.equals(".jpg")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = filePatheGetPicPaht + filename;// 存放位置
                File destFile = new File(path);
                try {
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//                    String upload = fileUploadUtil.upload(file, filePatheGetPicPaht.substring(1) + filename);
//                    System.out.println(upload);

                } catch (IOException e) {
                    e.printStackTrace();
                }//复制文件到指定目录
                sysScenicSpotTimingProblemWithBLOBs.setTimingResRulPic(filePatheGetPicUrl + filename);
            }else {
                return 2;
            }
        }
        sysScenicSpotTimingProblemWithBLOBs.setTimingType("1");
        sysScenicSpotTimingProblemWithBLOBs.setTimingResRul("");
        sysScenicSpotTimingProblemWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotTimingProblemMapper.updateByPrimaryKeySelective(sysScenicSpotTimingProblemWithBLOBs);
//        if (ToolUtil.isNotEmpty(sysScenicSpotTimingProblemWithBLOBs.getScenicSpotId()) && !"20200210".equals(sysScenicSpotTimingProblemWithBLOBs.getScenicSpotId().toString())) {
//            if(i == 1) {
//                SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
//                sysScenicSpotResourceVersion.setScenicSpotId(sysScenicSpotTimingProblemWithBLOBs.getScenicSpotId());
//                sysScenicSpotResourceVersion.setResType("3");
//                SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
//                double resVersion = Double.parseDouble(resourceVersion.getResVersion());
//                double s = 0.1;
//                BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
//                BigDecimal p2 = new BigDecimal(Double.toString(s));
//                double a = p1.add(p2).doubleValue();
//                SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
//                sysScenicSpotResourceVersion2.setScenicSpotId(sysScenicSpotTimingProblemWithBLOBs.getScenicSpotId());
//                sysScenicSpotResourceVersion2.setResType("3");
//                sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
//                sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
//                sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
//            }
//        }
        return i;
    }

    /**
     * @Author 郭凯
     * @Description 编辑随机播报(视频)
     * @Date 15:50 2020/11/2
     * @Param [file, sysScenicSpotRandomProblemWithBLOBs]
     * @return int
    **/
    @Override
    public int editTimingProblemVideo(MultipartFile file, SysScenicSpotTimingProblemWithBLOBs sysScenicSpotTimingProblemWithBLOBs) {
        if (!file.isEmpty()){
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".mp4") || type.equals("flv") || type.equals("avi") || type.equals("rm") || type.equals("rmvb") || type.equals("wmv")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = filePatheGetVodioPaht + filename;// 存放位置
                File destFile = new File(path);
                try {
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//                    String upload = fileUploadUtil.upload(file, filePatheGetVodioPaht.substring(1) + filename);
//                    System.out.println(upload);

                } catch (IOException e) {
                    e.printStackTrace();
                }//复制文件到指定目录
                sysScenicSpotTimingProblemWithBLOBs.setTimingResRul(filePatheGetVodioUrl+filename);
            }else {
                return 2;
            }
        }
        sysScenicSpotTimingProblemWithBLOBs.setTimingType("2");
        sysScenicSpotTimingProblemWithBLOBs.setTimingAnswers("");
        sysScenicSpotTimingProblemWithBLOBs.setTimingResRulPic("");
        sysScenicSpotTimingProblemWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotTimingProblemMapper.updateByPrimaryKeySelective(sysScenicSpotTimingProblemWithBLOBs);
//        if (ToolUtil.isNotEmpty(sysScenicSpotTimingProblemWithBLOBs.getScenicSpotId()) && !"20200210".equals(sysScenicSpotTimingProblemWithBLOBs.getScenicSpotId().toString())) {
//            if (i == 1) {
//                SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
//                sysScenicSpotResourceVersion.setScenicSpotId(sysScenicSpotTimingProblemWithBLOBs.getScenicSpotId());
//                sysScenicSpotResourceVersion.setResType("3");
//                SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
//                double resVersion = Double.parseDouble(resourceVersion.getResVersion());
//                double s = 0.1;
//                BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
//                BigDecimal p2 = new BigDecimal(Double.toString(s));
//                double a = p1.add(p2).doubleValue();
//                SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
//                sysScenicSpotResourceVersion2.setScenicSpotId(sysScenicSpotTimingProblemWithBLOBs.getScenicSpotId());
//                sysScenicSpotResourceVersion2.setResType("3");
//                sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
//                sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
//                sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
//            }
//        }
        return i;

    }

    /**
     * @Author 郭凯
     * @Description 编辑随机播报(音频)
     * @Date 17:42 2020/11/2
     * @Param [file, randomProblem]
     * @return int
    **/
    @Override
    public int editTimingProblemAudio(MultipartFile[] file, SysScenicSpotTimingProblemWithBLOBs problem) {
        //判断file数组不能为空并且长度大于0
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
                    String path = filePatheGetAudioPaht + filename;// 存放位置
                    File destFile = new File(path);
                    try {
                        //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                        FileUtils.copyInputStreamToFile(fileAudio.getInputStream(), destFile);
//                        String upload = fileUploadUtil.upload(fileAudio, filePatheGetAudioPaht.substring(1) + filename);
//                        System.out.println(upload);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }//复制文件到指定目录
                    problem.setTimingResRul(filePatheGetAudioUrl+filename);
                }else{
                    return 2;
                }
            }
            if (!fileImage.isEmpty()){
                String types = fileImage.getOriginalFilename().substring(fileImage.getOriginalFilename().indexOf("."));// 取文件格式后缀名
                if (types.equals(".png") || types.equals(".jpg")) {
                    String filename = System.currentTimeMillis() + types;// 取当前时间戳作为文件名
                    String path = filePatheGetPicPaht + filename;// 存放位置
                    File destFile = new File(path);
                    try {
                        //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                        FileUtils.copyInputStreamToFile(fileImage.getInputStream(), destFile);
//                        String upload = fileUploadUtil.upload(fileImage, filePatheGetPicPaht.substring(1) + filename);
//                        System.out.println(upload);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    problem.setTimingResRulPic(filePatheGetPicUrl+filename);
                }else {
                    return 3;
                }
            }
        }
        problem.setTimingType("3");
        problem.setTimingAnswers("");
        problem.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotTimingProblemMapper.updateByPrimaryKeySelective(problem);
//        if (String.valueOf(problem.getScenicSpotId()) != "20200210" || !"20200210".equals(String.valueOf(problem.getScenicSpotId()))) {
//            if(i == 1) {
//                SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
//                sysScenicSpotResourceVersion.setScenicSpotId(problem.getScenicSpotId());
//                sysScenicSpotResourceVersion.setResType("3");
//                SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
//                double resVersion = Double.parseDouble(resourceVersion.getResVersion());
//                double s = 0.1;
//                BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
//                BigDecimal p2 = new BigDecimal(Double.toString(s));
//                double a = p1.add(p2).doubleValue();
//                SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
//                sysScenicSpotResourceVersion2.setScenicSpotId(problem.getScenicSpotId());
//                sysScenicSpotResourceVersion2.setResType("3");
//                sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
//                sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
//                sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
//            }
//        }
        return i;
    }

}
