package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysRobotSoundsMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotCustomTypeMapper;
import com.hna.hka.archive.management.system.model.SysRobotSounds;
import com.hna.hka.archive.management.system.model.SysScenicSpotCustomType;
import com.hna.hka.archive.management.system.service.SysRobotSoundsService;
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
 * @ClassName: SysRobotSoundsServiceImpl
 * @Author: 郭凯
 * @Description: 机器人提示音管理业务层（实现）
 * @Date: 2020/11/12 10:22
 * @Version: 1.0
 */
@Service
@Transactional
public class SysRobotSoundsServiceImpl implements SysRobotSoundsService {

    @Autowired
    private SysRobotSoundsMapper sysRobotSoundsMapper;

    @Autowired
    private SysScenicSpotCustomTypeMapper sysScenicSpotCustomTypeMapper;

    @Value("${GET_SOUNDS_VODIO_PAHT}")
    private String GET_SOUNDS_VODIO_PAHT;

    @Value("${GET_SOUNDS_VODIO_URL}")
    private String GET_SOUNDS_VODIO_URL;

    @Value("${GET_SOUNDS_AUDIO_PAHT}")
    private String GET_SOUNDS_AUDIO_PAHT;

    @Value("${GET_SOUNDS_AUDIO_URL}")
    private String GET_SOUNDS_AUDIO_URL;

    @Autowired
    private FileUploadUtil fileUploadUtil;


    /**
     * @Author 郭凯
     * @Description 机器人提示音管理列表查询
     * @Date 10:24 2020/11/12
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getRobotSoundsList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotSounds> sysRobotSoundsList = sysRobotSoundsMapper.getRobotSoundsList(search);
        for(SysRobotSounds sysRobotSounds : sysRobotSoundsList) {
            SysScenicSpotCustomType typeNameValue = sysScenicSpotCustomTypeMapper.getCustomTypeByType(sysRobotSounds.getSoundsType());
            sysRobotSounds.setSoundsTypeName(typeNameValue.getTypeName());
        }
        if (sysRobotSoundsList.size() != 0){
            PageInfo<SysRobotSounds> pageInfo = new PageInfo<>(sysRobotSoundsList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 机器人提示音删除
     * @Date 9:57 2020/11/16
     * @Param [soundsId]
     * @return int
    **/
    @Override
    public int delRobotSounds(Long soundsId) {
        return sysRobotSoundsMapper.deleteByPrimaryKey(soundsId);
    }

    /**
     * @Author 郭凯
     * @Description 新增机器人提示音(文字)
     * @Date 11:18 2020/11/16
     * @Param [sysRobotSounds]
     * @return int
    **/
    @Override
    public int addRobotSoundsWrittenWords(SysRobotSounds sysRobotSounds) {
        sysRobotSounds.setSoundsId(IdUtils.getSeqId());
        sysRobotSounds.setSoundsMediaType("1");
        sysRobotSounds.setCreateDate(DateUtil.currentDateTime());
        sysRobotSounds.setUpdateDate(DateUtil.currentDateTime());
        return sysRobotSoundsMapper.insertSelective(sysRobotSounds);
    }

    /**
     * @Author 郭凯
     * @Description 新增机器人提示音（视频）
     * @Date 11:31 2020/11/16
     * @Param [file, sysRobotSounds]
     * @return int
    **/
    @Override
    public int addRobotSoundsVideo(MultipartFile file, SysRobotSounds sysRobotSounds) {
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".mp4") || type.equals(".flv") || type.equals(".avi") || type.equals(".rm") || type.equals(".rmvb") || type.equals(".wmv")) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = GET_SOUNDS_VODIO_PAHT + filename;// 存放位置
            File destFile = new File(path);
            try {
                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

//                String upload = fileUploadUtil.upload(file, GET_SOUNDS_VODIO_PAHT.substring(1) + filename);

            } catch (IOException e) {
                e.printStackTrace();
            }//复制文件到指定目录
            sysRobotSounds.setSoundsId(IdUtils.getSeqId());
            sysRobotSounds.setSoundsMediaType("3");
            sysRobotSounds.setSoundsContent(GET_SOUNDS_VODIO_URL+filename);
            sysRobotSounds.setCreateDate(DateUtil.currentDateTime());
            sysRobotSounds.setUpdateDate(DateUtil.currentDateTime());
            return sysRobotSoundsMapper.insertSelective(sysRobotSounds);
        }else {
            return 2;
        }
    }

    /**
     * @Author 郭凯
     * @Description 新增机器人提示音（音频）
     * @Date 12:33 2020/11/16
     * @Param [file, sysRobotSounds]
     * @return int
    **/
    @Override
    public int addRobotSoundsAudio(MultipartFile file, SysRobotSounds sysRobotSounds) {
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".mp3")) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = GET_SOUNDS_AUDIO_PAHT + filename;// 存放位置
            File destFile = new File(path);
            try {
                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

                //String upload = fileUploadUtil.upload(file, GET_SOUNDS_AUDIO_PAHT.substring(1) + filename);

            } catch (IOException e) {
                e.printStackTrace();
            }//复制文件到指定目录
            sysRobotSounds.setSoundsId(IdUtils.getSeqId());
            sysRobotSounds.setSoundsMediaType("2");
            sysRobotSounds.setSoundsContent(GET_SOUNDS_AUDIO_URL+filename);
            sysRobotSounds.setCreateDate(DateUtil.currentDateTime());
            sysRobotSounds.setUpdateDate(DateUtil.currentDateTime());
            return sysRobotSoundsMapper.insertSelective(sysRobotSounds);
        }else {
            return 2;
        }
    }

    /**
     * @Author 郭凯
     * @Description 编辑机器人提示音（文字）
     * @Date 13:31 2020/11/16
     * @Param [sysRobotSounds]
     * @return int
    **/
    @Override
    public int editRobotSoundsWrittenWords(SysRobotSounds sysRobotSounds) {
        sysRobotSounds.setUpdateDate(DateUtil.currentDateTime());
        sysRobotSounds.setSoundsMediaType("1");
        return sysRobotSoundsMapper.updateByPrimaryKeySelective(sysRobotSounds);
    }

    /**
     * @Author 郭凯
     * @Description 编辑机器人提示音（视频）
     * @Date 13:36 2020/11/16
     * @Param [sysRobotSounds]
     * @return int
    **/
    @Override
    public int editRobotSoundsVideo(MultipartFile file,SysRobotSounds sysRobotSounds) {
        if (file.getSize() == 0) {
            sysRobotSounds.setUpdateDate(DateUtil.currentDateTime());
            sysRobotSounds.setSoundsMediaType("3");
            return sysRobotSoundsMapper.updateByPrimaryKeySelective(sysRobotSounds);
        }else {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".mp4") || type.equals(".flv") || type.equals(".avi") || type.equals(".rm") || type.equals(".rmvb") || type.equals(".wmv")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = GET_SOUNDS_VODIO_PAHT + filename;// 存放位置
                File destFile = new File(path);
                try {
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

                    //String upload = fileUploadUtil.upload(file, GET_SOUNDS_VODIO_PAHT.substring(1) + filename);

                } catch (IOException e) {
                    e.printStackTrace();
                }//复制文件到指定目录
                sysRobotSounds.setSoundsMediaType("3");
                sysRobotSounds.setSoundsContent(GET_SOUNDS_VODIO_URL+filename);
                sysRobotSounds.setUpdateDate(DateUtil.currentDateTime());
                return sysRobotSoundsMapper.updateByPrimaryKeySelective(sysRobotSounds);
            }else {
                return 2;
            }
        }
    }

    /**
     * @Author 郭凯
     * @Description 编辑机器人提示音（音频）
     * @Date 13:49 2020/11/16
     * @Param [file, sysRobotSounds]
     * @return int
    **/
    @Override
    public int editRobotSoundsAudio(MultipartFile file, SysRobotSounds sysRobotSounds) {
        if (file.getSize() == 0) {
            sysRobotSounds.setUpdateDate(DateUtil.currentDateTime());
            sysRobotSounds.setSoundsMediaType("2");
            return sysRobotSoundsMapper.updateByPrimaryKeySelective(sysRobotSounds);
        }else {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".mp3")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = GET_SOUNDS_AUDIO_PAHT + filename;// 存放位置
                File destFile = new File(path);
                try {
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

                    //String upload = fileUploadUtil.upload(file, GET_SOUNDS_AUDIO_PAHT.substring(1) + filename);


                } catch (IOException e) {
                    e.printStackTrace();
                }//复制文件到指定目录
                sysRobotSounds.setSoundsMediaType("2");
                sysRobotSounds.setSoundsContent(GET_SOUNDS_AUDIO_URL+filename);
                sysRobotSounds.setUpdateDate(DateUtil.currentDateTime());
                return sysRobotSoundsMapper.updateByPrimaryKeySelective(sysRobotSounds);
            }else {
                return 2;
            }
        }
    }
}
