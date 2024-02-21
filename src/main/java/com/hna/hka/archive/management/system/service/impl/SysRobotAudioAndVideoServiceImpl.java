package com.hna.hka.archive.management.system.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysRobotAudioAndVideoMapper;
import com.hna.hka.archive.management.system.model.SysRobotAudioAndVideo;
import com.hna.hka.archive.management.system.service.SysRobotAudioAndVideoService;

/**
* @ClassName: SysRobotAudioAndVideoServiceImpl
* @Description: 媒体资源业务层（实现）
* @author 郭凯
* @date 2020年12月31日
* @version V1.0
 */
@Service
@Transactional
public class SysRobotAudioAndVideoServiceImpl implements SysRobotAudioAndVideoService {
	
	@Autowired
	private SysRobotAudioAndVideoMapper sysRobotAudioAndVideoMapper;
	
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
	* @Description: 媒体资源管理列表查询
	* @Title: getRobotAudioAndVideoList
	* @date  2020年12月31日 下午1:50:33 
	* @param @param pageNum
	* @param @param pageSize
	* @param @param search
	* @param @return
	* @throws
	 */
	@Override
	public PageDataResult getRobotAudioAndVideoList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
		// TODO Auto-generated method stub
		PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotAudioAndVideo> robotAudioAndVideoList = sysRobotAudioAndVideoMapper.getRobotAudioAndVideoList(search);
        if(robotAudioAndVideoList.size() != 0){
            PageInfo<SysRobotAudioAndVideo> pageInfo = new PageInfo<>(robotAudioAndVideoList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
	}

	/**
	* @Author 郭凯
	* @Description: 媒体资源新增(音频)
	* @Title: addRobotAudioAndVideo
	* @date  2020年12月31日 下午2:49:58 
	* @param @param file
	* @param @param sysRobotAudioAndVideo
	* @param @return
	* @throws
	 */
	@Override
	public int addRobotAudio(MultipartFile file, SysRobotAudioAndVideo sysRobotAudioAndVideo) {
		// TODO Auto-generated method stub
		String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".mp3")) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = semanticsAudioPaht + filename;// 存放位置
            File destFile = new File(path);
            try {
                // FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

//				String upload = fileUploadUtil.upload(file, semanticsAudioPaht.substring(1) + filename);

			} catch (IOException e) {
                e.printStackTrace();
            } // 复制文件到指定目录
            sysRobotAudioAndVideo.setMediaId(IdUtils.getSeqId());
            sysRobotAudioAndVideo.setMediaNamePingYin(Tinypinyin.tinypinyin(sysRobotAudioAndVideo.getMediaName()));
            sysRobotAudioAndVideo.setMediaAuthorPingYin(Tinypinyin.tinypinyin(sysRobotAudioAndVideo.getMediaAuthor()));
            sysRobotAudioAndVideo.setMediaResourcesType("1");
            sysRobotAudioAndVideo.setMediaUrl(semanticsAudioUrl+filename);
            sysRobotAudioAndVideo.setCreateDate(DateUtil.currentDateTime());
            sysRobotAudioAndVideo.setUpdateDate(DateUtil.currentDateTime());
            return sysRobotAudioAndVideoMapper.insertSelective(sysRobotAudioAndVideo);
        } else {
            return 2;
        }
	}

	/**
	* @Author 郭凯
	* @Description: 媒体资源新增(视频)
	* @Title: addRobotVideo
	* @date  2020年12月31日 下午3:15:46 
	* @param @param file
	* @param @param sysRobotAudioAndVideo
	* @param @return
	* @throws
	 */
	@Override
	public int addRobotVideo(MultipartFile file, SysRobotAudioAndVideo sysRobotAudioAndVideo) {
		// TODO Auto-generated method stub
		String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".mp4")) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = semanticsVideoPaht + filename;// 存放位置
            File destFile = new File(path);
            try {
                // FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

				//String upload = fileUploadUtil.upload(file, semanticsVideoPaht.substring(1) + filename);


			} catch (IOException e) {
                e.printStackTrace();
            } // 复制文件到指定目录
            sysRobotAudioAndVideo.setMediaId(IdUtils.getSeqId());
            sysRobotAudioAndVideo.setMediaNamePingYin(Tinypinyin.tinypinyin(sysRobotAudioAndVideo.getMediaName()));
            sysRobotAudioAndVideo.setMediaAuthorPingYin(Tinypinyin.tinypinyin(sysRobotAudioAndVideo.getMediaAuthor()));
            sysRobotAudioAndVideo.setMediaResourcesType("2");
            sysRobotAudioAndVideo.setMediaUrl(semanticsVideoUrl+filename);
            sysRobotAudioAndVideo.setCreateDate(DateUtil.currentDateTime());
            sysRobotAudioAndVideo.setUpdateDate(DateUtil.currentDateTime());
            return sysRobotAudioAndVideoMapper.insertSelective(sysRobotAudioAndVideo);
        } else {
            return 2;
        }
	}

	/**
	* @Author 郭凯
	* @Description: 媒体资源编辑(音频)
	* @Title: editRobotAudio
	* @date  2020年12月31日 下午3:48:31 
	* @param @param file
	* @param @param sysRobotAudioAndVideo
	* @param @return
	* @throws
	 */
	@Override
	public int editRobotAudio(MultipartFile file, SysRobotAudioAndVideo sysRobotAudioAndVideo) {
		// TODO Auto-generated method stub
		if (!file.isEmpty()){
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".mp3")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = semanticsAudioPaht + filename;// 存放位置
                File destFile = new File(path);
                try {
                    // FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

					//String upload = fileUploadUtil.upload(file, semanticsAudioPaht.substring(1) + filename);

				} catch (IOException e) {
                    e.printStackTrace();
                } // 复制文件到指定目录
                sysRobotAudioAndVideo.setMediaUrl(semanticsAudioUrl+filename);
            } else {
                return 2;
            }
        }
		sysRobotAudioAndVideo.setMediaNamePingYin(Tinypinyin.tinypinyin(sysRobotAudioAndVideo.getMediaName()));
		sysRobotAudioAndVideo.setMediaAuthorPingYin(Tinypinyin.tinypinyin(sysRobotAudioAndVideo.getMediaAuthor()));
		sysRobotAudioAndVideo.setMediaResourcesType("1");
		sysRobotAudioAndVideo.setUpdateDate(DateUtil.currentDateTime());
        return sysRobotAudioAndVideoMapper.updateByPrimaryKeySelective(sysRobotAudioAndVideo);
	}
	
	/**
	* @Author 郭凯
	* @Description: 媒体资源编辑(视频)
	* @Title: editRobotVideo
	* @date  2020年12月31日 下午3:51:03 
	* @param @param file
	* @param @param sysRobotAudioAndVideo
	* @param @return
	* @throws
	 */
	@Override
	public int editRobotVideo(MultipartFile file, SysRobotAudioAndVideo sysRobotAudioAndVideo) {
		// TODO Auto-generated method stub
		if (!file.isEmpty()){
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".mp4")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = semanticsVideoPaht + filename;// 存放位置
                File destFile = new File(path);
                try {
                    // FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

					//String upload = fileUploadUtil.upload(file, semanticsVideoPaht.substring(1) + filename);

				} catch (IOException e) {
                    e.printStackTrace();
                } // 复制文件到指定目录
                sysRobotAudioAndVideo.setMediaUrl(semanticsVideoUrl+filename);
            } else {
                return 2;
            }
        }
		sysRobotAudioAndVideo.setMediaNamePingYin(Tinypinyin.tinypinyin(sysRobotAudioAndVideo.getMediaName()));
		sysRobotAudioAndVideo.setMediaAuthorPingYin(Tinypinyin.tinypinyin(sysRobotAudioAndVideo.getMediaAuthor()));
		sysRobotAudioAndVideo.setMediaResourcesType("2");
		sysRobotAudioAndVideo.setUpdateDate(DateUtil.currentDateTime());
        return sysRobotAudioAndVideoMapper.updateByPrimaryKeySelective(sysRobotAudioAndVideo);
	}

	/**
	* @Author 郭凯
	* @Description: 删除媒体资源
	* @Title: delRobotAudioAndVideo
	* @date  2020年12月31日 下午4:05:05 
	* @param @param mediaId
	* @param @return
	* @throws
	 */
	@Override
	public int delRobotAudioAndVideo(Long mediaId) {
		// TODO Auto-generated method stub
		return sysRobotAudioAndVideoMapper.deleteByPrimaryKey(mediaId);
	}

}
