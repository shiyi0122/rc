package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.assetsSystem.model.ChinaMap;
import com.hna.hka.archive.management.system.model.SysScenicSpotBinding;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysScenicSpotBindingService
 * @Author: 郭凯
 * @Description: 景区归属地控制层（接口）
 * @Date: 2020/5/24 0:02
 * @Version: 1.0
 */
public interface SysScenicSpotBindingService {

    /**
     * 景区归属地列表查询
     * @param pageNum
     * @param pageSize
     * @param sysScenicSpotBinding
     * @return
     */
    PageDataResult getScenicSpotBindingList(Integer pageNum, Integer pageSize, SysScenicSpotBinding sysScenicSpotBinding);

    /**
     * 新增景区归属地
     * @param sysScenicSpotBinding
     * @return
     */
    int addScenicSpotBinding(SysScenicSpotBinding sysScenicSpotBinding, MultipartFile file);

    /**
     * 编辑景区归属地
     * @param sysScenicSpotBinding
     * @return
     */
    int editScenicSpotBinding(SysScenicSpotBinding sysScenicSpotBinding,MultipartFile file);

    /**
     * 删除景区归属地
     * @param scenicSpotFid
     * @return
     */
    int delScenicSpotBinding(Long scenicSpotFid);

    /**
     * 查询所有景区
     * @return
     */
    List<SysScenicSpotBinding> getScenicSpotBinding();

    List<ChinaMap> getChinaMapList();

    List<SysScenicSpotBinding> getScenicSpotById(String id);

    List<SysScenicSpotBinding> getSpotBindingProvince(String pid);

    List<SysScenicSpotBinding> getSpotBindingCity(String pid);


    List<SysScenicSpotBinding> getSpotBindingArea(String pid);
}
