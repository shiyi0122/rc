package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcast;
import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcastExtendWithBLOBs;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysScenicSpotBroadcastService
 * @Author: 郭凯
 * @Description: 景点管理业务层（接口）
 * @Date: 2020/6/8 9:33
 * @Version: 1.0
 */
public interface SysScenicSpotBroadcastService {

    PageDataResult getBroadcastList(Integer pageNum, Integer pageSize, SysScenicSpotBroadcast sysScenicSpotBroadcast);

    int addBroadcast(SysScenicSpotBroadcast broadcast);

    int delBroadcast(Long broadcastId , Long scenicSpotId);

    int editBroadcast(SysScenicSpotBroadcast broadcast);

    List<SysScenicSpotBroadcast> getBroadcastListByScenicSpotId(Long scenicSpotId);

    PageDataResult getBroadcastContentList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    int addBroadcastContentExtendImage(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs);

    int addBroadcastContentExtendVideo(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs);

    int addBroadcastContentExtendAudio(MultipartFile[] file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs);

    int editBroadcastContentExtendImage(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs);

    int editBroadcastContentExtendVideo(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs);

    int editBroadcastContentExtendAudio(MultipartFile[] file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs);

    int delBroadcastContentExtend(Long broadcastResId);

    List<SysScenicSpotBroadcastExtendWithBLOBs> getBroadcastExcel(Map<String, Object> search);

    List<SysScenicSpotBroadcast> getBroadcastByScenicSpotId(String scenicSpotId);

    int getBroadcastSumByScenicSpotId(Long scenicSpotId);

    List<SysScenicSpotBroadcast> getScenicSpotBroadcastAll();



}
