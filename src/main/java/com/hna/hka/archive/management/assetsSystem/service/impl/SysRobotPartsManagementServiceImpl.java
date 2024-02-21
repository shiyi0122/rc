package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.dao.SysRobotPartsManagementMapper;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotPartsManagement;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotPartsManagementService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.DictUtils;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service.impl
 * @ClassName: SysRobotPartsManagementServiceImpl
 * @Author: 郭凯
 * @Description: 机器人配件管理业务层（实现）
 * @Date: 2021/5/28 18:05
 * @Version: 1.0
 */
@Service
@Transactional
public class SysRobotPartsManagementServiceImpl implements SysRobotPartsManagementService {

    @Autowired
    private SysRobotPartsManagementMapper sysRobotPartsManagementMapper;

    /**
     * @Method getRobotPartsManagementList
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人配件管理列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/5/31 9:50
     */
    @Override
    public PageDataResult getRobotPartsManagementList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotPartsManagement> robotPartsManagementList = sysRobotPartsManagementMapper.getRobotPartsManagementList(search);
//        for (SysRobotPartsManagement sysRobotPartsManagement : robotPartsManagementList){
//            sysRobotPartsManagement.setAccessoriesTypeName(DictUtils.getAccessoriesTypeMap().get(sysRobotPartsManagement.getAccessoriesType()));
//        }
        if (robotPartsManagementList.size() > 0){
            PageInfo<SysRobotPartsManagement> pageInfo = new PageInfo<>(robotPartsManagementList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method addRobotPartsManagement
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人配件信息新增
     * @Return int
     * @Date 2021/5/31 11:02
     */
    @Override
    public int addRobotPartsManagement(SysRobotPartsManagement sysRobotPartsManagement) {
        sysRobotPartsManagement.setPartsManagementId(IdUtils.getSeqId());
        sysRobotPartsManagement.setCreateTime(DateUtil.currentDateTime());
        sysRobotPartsManagement.setUpdateTime(DateUtil.currentDateTime());

        return sysRobotPartsManagementMapper.insertSelective(sysRobotPartsManagement);
    }

    /**
     * @Method editRobotPartsManagement
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人配件信息编辑
     * @Return int
     * @Date 2021/5/31 11:15
     */
    @Override
    public int editRobotPartsManagement(SysRobotPartsManagement sysRobotPartsManagement) {
        sysRobotPartsManagement.setUpdateTime(DateUtil.currentDateTime());
        return sysRobotPartsManagementMapper.updateByPrimaryKeySelective(sysRobotPartsManagement);
    }

    /**
     * @Method delRobotPartsManagement
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人配件信息删除
     * @Return int
     * @Date 2021/5/31 11:22
     */
    @Override
    public int delRobotPartsManagement(Long partsManagementId) {
        return sysRobotPartsManagementMapper.deleteByPrimaryKey(partsManagementId);
    }

    /**
     * @Method getRobotPartsManagementExcel
     * @Author 郭凯
     * @Version  1.0
     * @Description 机器人配件信息下载Excel表数据查询
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.SysRobotPartsManagement>
     * @Date 2021/5/31 13:37
     */
    @Override
    public List<SysRobotPartsManagement> getRobotPartsManagementExcel(Map<String, Object> search) {
        List<SysRobotPartsManagement> robotPartsManagementList = sysRobotPartsManagementMapper.getRobotPartsManagementList(search);
//        for (SysRobotPartsManagement sysRobotPartsManagement : robotPartsManagementList){
//            sysRobotPartsManagement.setAccessoriesTypeName(DictUtils.getAccessoriesTypeMap().get(sysRobotPartsManagement.getAccessoriesType()));
//        }
        return robotPartsManagementList;
    }

    /**
     * @Method getAppAccessoriesApplicationName
     * @Author 郭凯
     * @Version  1.0
     * @Description 获取配件名称(APP接口)
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.SysRobotPartsManagement>
     * @Date 2021/6/10 17:42
     */
    @Override
    public List<SysRobotPartsManagement> getAppAccessoriesApplicationName(Map<String,Object> search) {
        List<SysRobotPartsManagement> applicationName = sysRobotPartsManagementMapper.getAppAccessoriesApplicationName(search);
        if (applicationName.size() == 0){
            return applicationName;
        }

//        if (applicationName.size() == 0){
//            search.put("spotId","");
//            sysRobotPartsManagementMapper.getAppAccessoriesApplicationName(search);
//        }
        for (int i = 0; i < applicationName.size(); i++) {
            Long amount = applicationName.get(i).getAmount();
            if (amount == null){
                applicationName.get(i).setAmount(0l);
            }
        }
        return applicationName;
    }

    /**
     *  根据配件名称，获取配件id
     * @param partsManagementName
     * @return
     */
    @Override
    public SysRobotPartsManagement getAccessoryNameByParts(String partsManagementName) {


        List<SysRobotPartsManagement> sysRobotPartsManagements = sysRobotPartsManagementMapper.selectRobotByName(partsManagementName,null );

        if (sysRobotPartsManagements.size()>0){
            return sysRobotPartsManagements.get(0);
        }
        return null;
    }

    @Override
    public Long getAmount(Map<String, Object> search) {
        SysRobotPartsManagement amount = sysRobotPartsManagementMapper.getAmount(search);
        if (amount == null){
            return 0l;
        }else {
            return amount.getAmount();
        }

    }
}
