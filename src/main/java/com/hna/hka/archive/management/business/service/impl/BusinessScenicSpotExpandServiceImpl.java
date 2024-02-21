package com.hna.hka.archive.management.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.business.dao.BusinessScenicSpotAreaMapper;
import com.hna.hka.archive.management.business.dao.BusinessScenicSpotExpandMapper;
import com.hna.hka.archive.management.business.dao.BusinessWorldAreaMapper;
import com.hna.hka.archive.management.business.model.BusinessScenicSpotArea;
import com.hna.hka.archive.management.business.model.BusinessScenicSpotExpand;
import com.hna.hka.archive.management.business.model.BusinessWorldArea;
import com.hna.hka.archive.management.business.service.BusinessScenicSpotExpandService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service.impl
 * @ClassName: BusinessScenicSpotExpandServiceImpl
 * @Author: 郭凯
 * @Description: 景区拓展管理业务层（实现）
 * @Date: 2020/8/7 10:10
 * @Version: 1.0
 */
@Service
public class BusinessScenicSpotExpandServiceImpl implements BusinessScenicSpotExpandService {

    @Autowired
    private BusinessScenicSpotExpandMapper businessScenicSpotExpandMapper;

    @Autowired
    private BusinessWorldAreaMapper businessWorldAreaMapper;

    @Autowired
    private BusinessScenicSpotAreaMapper businessScenicSpotAreaMapper;

    /**
     * @Author 郭凯
     * @Description 景区拓展管理列表查询
     * @Date 10:11 2020/8/7
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getScenicSpotExpandList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<BusinessScenicSpotExpand> businessScenicSpotExpandList = businessScenicSpotExpandMapper.getScenicSpotExpandList(search);
        if (businessScenicSpotExpandList.size() != 0){
            PageInfo<BusinessScenicSpotExpand> pageInfo = new PageInfo<>(businessScenicSpotExpandList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 查询地区名称
     * @Date 11:16 2020/8/10
     * @Param [pid]
     * @return com.hna.hka.archive.management.business.model.BusinessWorldArea
    **/
    @Override
    public List<BusinessWorldArea> getProvince(Long pid) {
        return businessWorldAreaMapper.getProvince(pid);
    }

    /**
     * @Author 郭凯
     * @Description 景区拓展信息新增
     * @Date 13:36 2020/8/11
     * @Param [businessScenicSpotExpand]
     * @return int
    **/
    @Override
    public int addScenicSpotExpand(BusinessScenicSpotExpand businessScenicSpotExpand,BusinessScenicSpotArea businessScenicSpotArea) {
        //查询此景区是否存在拓展信息
        BusinessScenicSpotExpand scenicSpotExpand = businessScenicSpotExpandMapper.selectScenicSpotExpandByScenicId(businessScenicSpotExpand.getScenicSpotId());
        if (ToolUtil.isNotEmpty(scenicSpotExpand)){
            return 2;
        }else{
            //查询此景区是否存在所在地
            BusinessScenicSpotArea scenicSpotArea = businessScenicSpotAreaMapper.selectAreaScenicSpotId(businessScenicSpotExpand.getScenicSpotId());
            if (scenicSpotArea != null) {
                businessScenicSpotAreaMapper.deleteByPrimaryKey(scenicSpotArea.getId());
            }
            businessScenicSpotArea.setId(IdUtils.getSeqId());
            businessScenicSpotArea.setScenicSpotId(businessScenicSpotExpand.getScenicSpotId());
            businessScenicSpotArea.setCreateTime(DateUtil.currentDateTime());
            businessScenicSpotArea.setUpdateTime(DateUtil.currentDateTime());
            businessScenicSpotAreaMapper.insertSelective(businessScenicSpotArea);
            businessScenicSpotExpand.setId(IdUtils.getSeqId());
            businessScenicSpotExpand.setCreateTime(DateUtil.currentDateTime());
            businessScenicSpotExpand.setUpdateTime(DateUtil.currentDateTime());
        }
        return businessScenicSpotExpandMapper.insertSelective(businessScenicSpotExpand);
    }

    /**
     * @Author 郭凯
     * @Description 根据ID查询地区名称
     * @Date 13:58 2020/8/11
     * @Param [province]
     * @return com.hna.hka.archive.management.business.model.BusinessWorldArea
    **/
    @Override
    public BusinessWorldArea selectProvinceId(String province) {
        return businessWorldAreaMapper.selectByPrimaryKey(Integer.parseInt(province));
    }

    /**
    * @Author 郭凯
    * @Description: 景区拓展信息修改
    * @Title: editScenicSpotExpand
    * @date  2020年12月29日 下午3:35:27 
    * @param @param businessScenicSpotExpand
    * @param @param businessScenicSpotArea
    * @param @return
    * @throws
     */
	@Override
	public int editScenicSpotExpand(BusinessScenicSpotExpand businessScenicSpotExpand,
			BusinessScenicSpotArea businessScenicSpotArea) {
		//查询此景区是否存在所在地
        BusinessScenicSpotArea scenicSpotArea = businessScenicSpotAreaMapper.selectAreaScenicSpotId(businessScenicSpotExpand.getScenicSpotId());
        if (ToolUtil.isNotEmpty(scenicSpotArea)) {
            businessScenicSpotAreaMapper.deleteByPrimaryKey(scenicSpotArea.getId());
        }
    	businessScenicSpotArea.setId(IdUtils.getSeqId());
        businessScenicSpotArea.setScenicSpotId(businessScenicSpotExpand.getScenicSpotId());
        businessScenicSpotArea.setCreateTime(DateUtil.currentDateTime());
        businessScenicSpotArea.setUpdateTime(DateUtil.currentDateTime());
        businessScenicSpotAreaMapper.insertSelective(businessScenicSpotArea);
        businessScenicSpotExpand.setUpdateTime(DateUtil.currentDateTime());
		return businessScenicSpotExpandMapper.updateByPrimaryKeySelective(businessScenicSpotExpand);
	}

	/**
	* @Author 郭凯
	* @Description: 删除景区拓展信息
	* @Title: delScenicSpotExpand
	* @date  2020年12月29日 下午3:46:19 
	* @param @param id
	* @param @return
	* @throws
	 */
	@Override
	public int delScenicSpotExpand(Long id) {
		// TODO Auto-generated method stub
		return businessScenicSpotExpandMapper.deleteByPrimaryKey(id);
	}

	/**
	* @Author 郭凯
	* @Description: 查询景区下拉选，已分配的景区不显示
	* @Title: getScenicSpot
	* @date  2021年1月5日 下午1:48:42 
	* @param @return
	* @throws
	 */
	@Override
	public List<BusinessScenicSpotExpand> getScenicSpot() {
		// TODO Auto-generated method stub
		return businessScenicSpotExpandMapper.getScenicSpot();
	}
}
