package com.hna.hka.archive.management.business.service;

import com.hna.hka.archive.management.business.model.BusinessScenicSpotArea;
import com.hna.hka.archive.management.business.model.BusinessScenicSpotExpand;
import com.hna.hka.archive.management.business.model.BusinessWorldArea;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service
 * @ClassName: BusinessScenicSpotExpandService
 * @Author: 郭凯
 * @Description: 景区拓展管理业务层（接口）
 * @Date: 2020/8/7 10:09
 * @Version: 1.0
 */
public interface BusinessScenicSpotExpandService {

    PageDataResult getScenicSpotExpandList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    List<BusinessWorldArea> getProvince(Long pid);

    int addScenicSpotExpand(BusinessScenicSpotExpand businessScenicSpotExpand, BusinessScenicSpotArea businessScenicSpotArea);

    BusinessWorldArea selectProvinceId(String province);

	int editScenicSpotExpand(BusinessScenicSpotExpand businessScenicSpotExpand,
			BusinessScenicSpotArea businessScenicSpotArea);

	int delScenicSpotExpand(Long id);

	List<BusinessScenicSpotExpand> getScenicSpot();
}
