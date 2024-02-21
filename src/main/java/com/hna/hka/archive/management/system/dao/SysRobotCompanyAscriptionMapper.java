package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotSoftAssetInformation;
import com.hna.hka.archive.management.system.model.SysRobotCompanyAscription;
import com.hna.hka.archive.management.system.model.SysScenicSpotAscriptionCompany;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/2/25 14:25
 */
@Mapper
public interface SysRobotCompanyAscriptionMapper {


    int insertSelective(SysRobotCompanyAscription robotCompanyAscription);


    int updateByPrimaryKeySelective(SysRobotCompanyAscription robotCompanyAscription);

    List<SysScenicSpotAscriptionCompany> getAscriptionCompanyList(Map<String, Object> search);

    SysRobotCompanyAscription getAscriptionCompanyId(String robotId);


    List<SysRobotCompanyAscription> getRobotCompanyList(Long spotId);

}
