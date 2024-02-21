package com.hna.hka.archive.management.system.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hna.hka.archive.management.system.model.SysRobotAudioAndVideo;
import com.hna.hka.archive.management.system.util.PageDataResult;

/**
* @ClassName: SysRobotAudioAndVideoService
* @Description: 媒体资源业务层（接口）
* @author 郭凯
* @date 2020年12月31日
* @version V1.0
 */
public interface SysRobotAudioAndVideoService {

	PageDataResult getRobotAudioAndVideoList(Integer pageNum, Integer pageSize, Map<String, Object> search);

	int addRobotAudio(MultipartFile file, SysRobotAudioAndVideo sysRobotAudioAndVideo);

	int addRobotVideo(MultipartFile file, SysRobotAudioAndVideo sysRobotAudioAndVideo);

	int editRobotAudio(MultipartFile file, SysRobotAudioAndVideo sysRobotAudioAndVideo);

	int editRobotVideo(MultipartFile file, SysRobotAudioAndVideo sysRobotAudioAndVideo);

	int delRobotAudioAndVideo(Long mediaId);

}
