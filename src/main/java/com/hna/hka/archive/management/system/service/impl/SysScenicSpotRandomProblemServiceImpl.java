package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysScenicSpotRandomProblemMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotResourceVersionMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpotRandomProblemWithBLOBs;
import com.hna.hka.archive.management.system.model.SysScenicSpotResourceVersion;
import com.hna.hka.archive.management.system.service.SysScenicSpotRandomProblemService;
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
public class SysScenicSpotRandomProblemServiceImpl implements SysScenicSpotRandomProblemService {

    @Autowired
    private SysScenicSpotRandomProblemMapper sysScenicSpotRandomProblemMapper;

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
    public PageDataResult getRandomProblemList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotRandomProblemWithBLOBs> sysScenicSpotRandomProblemWithBLOBsList = sysScenicSpotRandomProblemMapper.getRandomProblemList(search);
        for(SysScenicSpotRandomProblemWithBLOBs sysScenicSpotRandomProble : sysScenicSpotRandomProblemWithBLOBsList) {
            if ("20200210".equals(String.valueOf(sysScenicSpotRandomProble.getScenicSpotId()))) {
                sysScenicSpotRandomProble.setScenicSpotName("通用类型");
            }
        }
        if(sysScenicSpotRandomProblemWithBLOBsList.size() != 0){
            PageInfo<SysScenicSpotRandomProblemWithBLOBs> pageInfo = new PageInfo<>(sysScenicSpotRandomProblemWithBLOBsList);
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
    public int delRandomProblem(Long problemId) {
        return sysScenicSpotRandomProblemMapper.deleteByPrimaryKey(problemId);
    }

    /**
     * @Author 郭凯
     * @Description 修改随机播报状态
     * @Date 9:20 2020/6/22
     * @Param [sysScenicSpotRandomProblemWithBLOBs]
     * @return int
    **/
    @Override
    public int editRandomProblemValid(SysScenicSpotRandomProblemWithBLOBs sysScenicSpotRandomProblemWithBLOBs) {
        sysScenicSpotRandomProblemWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotRandomProblemMapper.updateByPrimaryKeySelective(sysScenicSpotRandomProblemWithBLOBs);
    }

    /**
     * @Author 郭凯
     * @Description 新增随机播报(文字)
     * @Date 13:59 2020/7/29
     * @Param [file, sysScenicSpotRandomProblemWithBLOBs]
     * @return int
    **/
    @Override
    public int addRandomProblemImage(MultipartFile file, SysScenicSpotRandomProblemWithBLOBs sysScenicSpotRandomProblemWithBLOBs) {
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
                sysScenicSpotRandomProblemWithBLOBs.setRandomResRulPic(filePatheGetPicUrl + filename);
            }else {
                return 2;
            }
        }
        sysScenicSpotRandomProblemWithBLOBs.setProblemId(IdUtils.getSeqId());
        sysScenicSpotRandomProblemWithBLOBs.setRandomType("1");
        sysScenicSpotRandomProblemWithBLOBs.setRandomResRul("");
        sysScenicSpotRandomProblemWithBLOBs.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotRandomProblemWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotRandomProblemMapper.insertSelective(sysScenicSpotRandomProblemWithBLOBs);
        if (!"20200210".equals(String.valueOf(sysScenicSpotRandomProblemWithBLOBs.getScenicSpotId()))) {
            if(i == 1) {
                SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
                sysScenicSpotResourceVersion.setScenicSpotId(sysScenicSpotRandomProblemWithBLOBs.getScenicSpotId());
                sysScenicSpotResourceVersion.setResType("3");
                SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
                double resVersion = Double.parseDouble(resourceVersion.getResVersion());
                double s = 0.1;
                BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
                BigDecimal p2 = new BigDecimal(Double.toString(s));
                double a = p1.add(p2).doubleValue();
                SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
                sysScenicSpotResourceVersion2.setScenicSpotId(sysScenicSpotRandomProblemWithBLOBs.getScenicSpotId());
                sysScenicSpotResourceVersion2.setResType("3");
                sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
                sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
                sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
            }
        }
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
    public int addRandomProblemVideo(MultipartFile file, SysScenicSpotRandomProblemWithBLOBs sysScenicSpotRandomProblemWithBLOBs) {
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
            sysScenicSpotRandomProblemWithBLOBs.setProblemId(IdUtils.getSeqId());
            sysScenicSpotRandomProblemWithBLOBs.setRandomResRul(filePatheGetVodioUrl+filename);
            sysScenicSpotRandomProblemWithBLOBs.setRandomType("2");
            sysScenicSpotRandomProblemWithBLOBs.setRandomAnswers("");
            sysScenicSpotRandomProblemWithBLOBs.setCreateDate(DateUtil.currentDateTime());
            sysScenicSpotRandomProblemWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
            int i = sysScenicSpotRandomProblemMapper.insertSelective(sysScenicSpotRandomProblemWithBLOBs);
            if (!"20200210".equals(sysScenicSpotRandomProblemWithBLOBs.getScenicSpotId())) {
                if (i == 1) {
                    SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
                    sysScenicSpotResourceVersion.setScenicSpotId(sysScenicSpotRandomProblemWithBLOBs.getScenicSpotId());
                    sysScenicSpotResourceVersion.setResType("3");
                    SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
                    double resVersion = Double.parseDouble(resourceVersion.getResVersion());
                    double s = 0.1;
                    BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
                    BigDecimal p2 = new BigDecimal(Double.toString(s));
                    double a = p1.add(p2).doubleValue();
                    SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
                    sysScenicSpotResourceVersion2.setScenicSpotId(sysScenicSpotRandomProblemWithBLOBs.getScenicSpotId());
                    sysScenicSpotResourceVersion2.setResType("3");
                    sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
                    sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
                    sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
                }
            }
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
    public int addRandomProblemAudio(MultipartFile[] file, SysScenicSpotRandomProblemWithBLOBs problem) {
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
                            problem.setRandomResRulPic(filePatheGetPicUrl+filename);
                        }else {
                            return 4;
                        }
                    }else {
                        problem.setRandomResRulPic("");
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
                    problem.setRandomResRul(filePatheGetAudioUrl+filename);
                    problem.setRandomType("3");
                    problem.setRandomAnswers("");
                    problem.setCreateDate(DateUtil.currentDateTime());
                    problem.setUpdateDate(DateUtil.currentDateTime());
                    int i = sysScenicSpotRandomProblemMapper.insertSelective(problem);
                    if (String.valueOf(problem.getScenicSpotId()) != "20200210" || !"20200210".equals(String.valueOf(problem.getScenicSpotId()))) {
                        if(i == 1) {
                            SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
                            sysScenicSpotResourceVersion.setScenicSpotId(problem.getScenicSpotId());
                            sysScenicSpotResourceVersion.setResType("3");
                            SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
                            double resVersion = Double.parseDouble(resourceVersion.getResVersion());
                            double s = 0.1;
                            BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
                            BigDecimal p2 = new BigDecimal(Double.toString(s));
                            double a = p1.add(p2).doubleValue();
                            SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
                            sysScenicSpotResourceVersion2.setScenicSpotId(problem.getScenicSpotId());
                            sysScenicSpotResourceVersion2.setResType("3");
                            sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
                            sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
                            sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
                        }
                    }
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
    public int editRandomProblemImage(MultipartFile file, SysScenicSpotRandomProblemWithBLOBs sysScenicSpotRandomProblemWithBLOBs) {
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
                sysScenicSpotRandomProblemWithBLOBs.setRandomResRulPic(filePatheGetPicUrl + filename);
            }else {
                return 2;
            }
        }
        sysScenicSpotRandomProblemWithBLOBs.setRandomType("1");
        sysScenicSpotRandomProblemWithBLOBs.setRandomResRul("");
        sysScenicSpotRandomProblemWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotRandomProblemMapper.updateByPrimaryKeySelective(sysScenicSpotRandomProblemWithBLOBs);
        if (ToolUtil.isNotEmpty(sysScenicSpotRandomProblemWithBLOBs.getScenicSpotId()) && !"20200210".equals(sysScenicSpotRandomProblemWithBLOBs.getScenicSpotId().toString())) {
            if(i == 1) {
                SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
                sysScenicSpotResourceVersion.setScenicSpotId(sysScenicSpotRandomProblemWithBLOBs.getScenicSpotId());
                sysScenicSpotResourceVersion.setResType("3");
                SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
                double resVersion = Double.parseDouble(resourceVersion.getResVersion());
                double s = 0.1;
                BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
                BigDecimal p2 = new BigDecimal(Double.toString(s));
                double a = p1.add(p2).doubleValue();
                SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
                sysScenicSpotResourceVersion2.setScenicSpotId(sysScenicSpotRandomProblemWithBLOBs.getScenicSpotId());
                sysScenicSpotResourceVersion2.setResType("3");
                sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
                sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
                sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
            }
        }
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
    public int editRandomProblemVideo(MultipartFile file, SysScenicSpotRandomProblemWithBLOBs sysScenicSpotRandomProblemWithBLOBs) {
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
                sysScenicSpotRandomProblemWithBLOBs.setRandomResRul(filePatheGetVodioUrl+filename);
            }else {
                return 2;
            }
        }
        sysScenicSpotRandomProblemWithBLOBs.setRandomType("2");
        sysScenicSpotRandomProblemWithBLOBs.setRandomAnswers("");
        sysScenicSpotRandomProblemWithBLOBs.setRandomResRulPic("");
        sysScenicSpotRandomProblemWithBLOBs.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotRandomProblemMapper.updateByPrimaryKeySelective(sysScenicSpotRandomProblemWithBLOBs);
        if (ToolUtil.isNotEmpty(sysScenicSpotRandomProblemWithBLOBs.getScenicSpotId()) && !"20200210".equals(sysScenicSpotRandomProblemWithBLOBs.getScenicSpotId().toString())) {
            if (i == 1) {
                SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
                sysScenicSpotResourceVersion.setScenicSpotId(sysScenicSpotRandomProblemWithBLOBs.getScenicSpotId());
                sysScenicSpotResourceVersion.setResType("3");
                SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
                double resVersion = Double.parseDouble(resourceVersion.getResVersion());
                double s = 0.1;
                BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
                BigDecimal p2 = new BigDecimal(Double.toString(s));
                double a = p1.add(p2).doubleValue();
                SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
                sysScenicSpotResourceVersion2.setScenicSpotId(sysScenicSpotRandomProblemWithBLOBs.getScenicSpotId());
                sysScenicSpotResourceVersion2.setResType("3");
                sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
                sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
                sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
            }
        }
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
    public int editRandomProblemAudio(MultipartFile[] file, SysScenicSpotRandomProblemWithBLOBs problem) {
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
                    problem.setRandomResRul(filePatheGetAudioUrl+filename);
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
                    problem.setRandomResRulPic(filePatheGetPicUrl+filename);
                }else {
                    return 3;
                }
            }
        }
        problem.setRandomType("3");
        problem.setRandomAnswers("");
        problem.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotRandomProblemMapper.updateByPrimaryKeySelective(problem);
        if (String.valueOf(problem.getScenicSpotId()) != "20200210" || !"20200210".equals(String.valueOf(problem.getScenicSpotId()))) {
            if(i == 1) {
                SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
                sysScenicSpotResourceVersion.setScenicSpotId(problem.getScenicSpotId());
                sysScenicSpotResourceVersion.setResType("3");
                SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
                double resVersion = Double.parseDouble(resourceVersion.getResVersion());
                double s = 0.1;
                BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
                BigDecimal p2 = new BigDecimal(Double.toString(s));
                double a = p1.add(p2).doubleValue();
                SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
                sysScenicSpotResourceVersion2.setScenicSpotId(problem.getScenicSpotId());
                sysScenicSpotResourceVersion2.setResType("3");
                sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
                sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
                sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
            }
        }
        return i;
    }

}
