package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysScenicSpotInfraredBroadcastMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpotInfraredBroadcast;
import com.hna.hka.archive.management.system.service.SysScenicSpotInfraredBroadcastService;
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
 * @ClassName: SysScenicSpotInfraredBroadcastServiceImpl
 * @Author: 郭凯
 * @Description: 红外播报管理业务层（实现）
 * @Date: 2020/11/12 9:28
 * @Version: 1.0
 */
@Service
@Transactional
public class SysScenicSpotInfraredBroadcastServiceImpl implements SysScenicSpotInfraredBroadcastService {

    @Autowired
    private SysScenicSpotInfraredBroadcastMapper sysScenicSpotInfraredBroadcastMapper;

    @Value("${GET_INFRARED_BROADCAST_PAHT}")
    private String GET_INFRARED_BROADCAST_PAHT;

    @Value("${GET_INFRARED_BROADCAST_URL}")
    private String GET_INFRARED_BROADCAST_URL;
    @Autowired
    private FileUploadUtil fileUploadUtil;


    /**
     * @Author 郭凯
     * @Description 红外播报管理列表查询
     * @Date 9:30 2020/11/12
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getInfraredBroadcastList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotInfraredBroadcast> sysScenicSpotInfraredBroadcastList = sysScenicSpotInfraredBroadcastMapper.getInfraredBroadcastList(search);
        if (sysScenicSpotInfraredBroadcastList.size() != 0){
            PageInfo<SysScenicSpotInfraredBroadcast> pageInfo = new PageInfo<>(sysScenicSpotInfraredBroadcastList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 新增红外播报
     * @Date 14:36 2020/11/16
     * @Param [sysScenicSpotInfraredBroadcast]
     * @return int
    **/
    @Override
    public int addInfraredBroadcastWrittenWords(SysScenicSpotInfraredBroadcast sysScenicSpotInfraredBroadcast) {
        sysScenicSpotInfraredBroadcast.setInfraredId(IdUtils.getSeqId());
        sysScenicSpotInfraredBroadcast.setType("1");
        sysScenicSpotInfraredBroadcast.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotInfraredBroadcast.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotInfraredBroadcastMapper.insertSelective(sysScenicSpotInfraredBroadcast);
    }

    /**
     * @Author 郭凯
     * @Description 新增红外播报（音频）
     * @Date 15:05 2020/11/16
     * @Param [file, sysScenicSpotInfraredBroadcast]
     * @return int
    **/
    @Override
    public int addInfraredBroadcastAudio(MultipartFile file,SysScenicSpotInfraredBroadcast sysScenicSpotInfraredBroadcast) {
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".mp3")) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = GET_INFRARED_BROADCAST_PAHT + filename;// 存放位置
            File destFile = new File(path);
            try {
                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//                String upload = fileUploadUtil.upload(file, GET_INFRARED_BROADCAST_PAHT.substring(1) + filename);
//                System.out.println(upload);
            } catch (IOException e) {
                e.printStackTrace();
            }
            sysScenicSpotInfraredBroadcast.setInfraredId(IdUtils.getSeqId());
            sysScenicSpotInfraredBroadcast.setType("2");
            sysScenicSpotInfraredBroadcast.setInfraredContent(GET_INFRARED_BROADCAST_URL + filename);
            sysScenicSpotInfraredBroadcast.setCreateDate(DateUtil.currentDateTime());
            sysScenicSpotInfraredBroadcast.setUpdateDate(DateUtil.currentDateTime());
            return sysScenicSpotInfraredBroadcastMapper.insertSelective(sysScenicSpotInfraredBroadcast);
        }else {
            return 2;
        }
    }

    /**
     * @Author 郭凯
     * @Description 编辑红外播报（文字）
     * @Date 15:27 2020/11/16
     * @Param [sysScenicSpotInfraredBroadcast]
     * @return int
    **/
    @Override
    public int editInfraredBroadcastWrittenWords(SysScenicSpotInfraredBroadcast sysScenicSpotInfraredBroadcast) {
        sysScenicSpotInfraredBroadcast.setType("1");
        sysScenicSpotInfraredBroadcast.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotInfraredBroadcastMapper.updateByPrimaryKeySelective(sysScenicSpotInfraredBroadcast);
    }

    /**
     * @Author 郭凯
     * @Description 红外播报编辑（音频）
     * @Date 15:31 2020/11/16
     * @Param [file, sysScenicSpotInfraredBroadcast]
     * @return int
    **/
    @Override
    public int editInfraredBroadcastAudio(MultipartFile file, SysScenicSpotInfraredBroadcast sysScenicSpotInfraredBroadcast) {
        if (file.getSize() > 0) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".mp3")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = GET_INFRARED_BROADCAST_PAHT + filename;// 存放位置
                File destFile = new File(path);
                try {
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//                    String upload = fileUploadUtil.upload(file, GET_INFRARED_BROADCAST_PAHT.substring(1) + filename);
//                    System.out.println(upload);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                sysScenicSpotInfraredBroadcast.setType("2");
                sysScenicSpotInfraredBroadcast.setInfraredContent(GET_INFRARED_BROADCAST_URL + filename);
                sysScenicSpotInfraredBroadcast.setUpdateDate(DateUtil.currentDateTime());
            }else {
                return 2;
            }
        }else {
            sysScenicSpotInfraredBroadcast.setType("2");
            sysScenicSpotInfraredBroadcast.setUpdateDate(DateUtil.currentDateTime());
        }
        return sysScenicSpotInfraredBroadcastMapper.updateByPrimaryKeySelective(sysScenicSpotInfraredBroadcast);
    }

    /**
     * @Author 郭凯
     * @Description 红外播报删除
     * @Date 15:35 2020/11/16
     * @Param [infraredId]
     * @return int
    **/
    @Override
    public int delInfraredBroadcast(Long infraredId) {
        return sysScenicSpotInfraredBroadcastMapper.deleteByPrimaryKey(infraredId);
    }
}
